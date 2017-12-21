package com.gamejelly.game.gong2.login.web.api;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gamejelly.game.gong2.login.dao.GameAccountDao;
import com.gamejelly.game.gong2.login.dao.IdfaDeviceDataDao;
import com.gamejelly.game.gong2.login.meta.AppstorePurchasedRecord;
import com.gamejelly.game.gong2.login.meta.Feedback;
import com.gamejelly.game.gong2.login.meta.GameAccount;
import com.gamejelly.game.gong2.login.meta.GooglePurchasedRecord;
import com.gamejelly.game.gong2.login.meta.IdfaDeviceData;
import com.gamejelly.game.gong2.login.meta.PlatformPayRecord;
import com.gamejelly.game.gong2.login.meta.ServerInfo;
import com.gamejelly.game.gong2.login.net.GasAdminClientManager;
import com.gamejelly.game.gong2.login.service.AppleAccountRelationService;
import com.gamejelly.game.gong2.login.service.AppstorePurchasedRecordService;
import com.gamejelly.game.gong2.login.service.CouponRecordService;
import com.gamejelly.game.gong2.login.service.FeedbackService;
import com.gamejelly.game.gong2.login.service.GameAccountService;
import com.gamejelly.game.gong2.login.service.GooglePurchasedRecordService;
import com.gamejelly.game.gong2.login.service.PlatformPayRecordService;
import com.gamejelly.game.gong2.login.service.SdkAccountRelationService;
import com.gamejelly.game.gong2.login.service.SdkServiceGroups;
import com.gamejelly.game.gong2.login.service.ServerInfoService;
import com.gamejelly.game.gong2.login.service.SystemDataService;
import com.gamejelly.game.gong2.login.service.sdk.IdfaService;
import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.service.sdk.SdkService;
import com.gamejelly.game.gong2.login.utils.BadRequestException;
import com.gamejelly.game.gong2.login.utils.FsGameLoginConst;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.hadoit.game.common.framework.http.SimpleHttpRequest;
import com.hadoit.game.common.framework.utils.SimpleWebUtils;
import com.hadoit.game.common.lang.Pair;
import com.hadoit.game.common.lang.Triple;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

@Controller
public class AccountApiController {
	/**
	 * 热云账号信息
	 */
	private static String HIappkey = "9deb671596127f3afa06d8f2cd753afa";
	private static String YDappkey = "ee17e2753b3baef0f5ae88ba42d4ce46";// 游动热云key
	private static String HAappkey = "88105298504512c096429f2ed17ff5fc";
	private static String HIwho = "我的宫廷iOS";
	private static String HAwho = "我的宫廷Android";
	@Autowired
	private ServerInfoService serverInfoService;

	@Autowired
	private GameAccountService gameAccountService;
	@Autowired
	protected PlatformPayRecordService platformPayRecordService;
	@Autowired
	private SdkServiceGroups sdkServiceGroups;

	@Autowired
	private FeedbackService feedbackService;

	@Autowired
	private SystemDataService systemDataService;

	@Autowired
	private CouponRecordService couponRecordService;

	@Autowired
	private AppleAccountRelationService appleAccountRelationService;

	@Autowired
	private SdkAccountRelationService sdkAccountRelationService;

	@Autowired
	private IdfaService idfaService;

	@Autowired
	private AppstorePurchasedRecordService appstorePurchasedRecordService;

	@Autowired
	private GooglePurchasedRecordService googlePurchasedRecordService;

	@Autowired
	protected GasAdminClientManager gasAdminClientManager;
	@Autowired
	private GameAccountDao gameAccountDao;
	@Autowired
	private IdfaDeviceDataDao idfaDeviceDataDao;
	@Autowired
	private CacheManager cacheManager;

	@Value("${config.apple_review}")
	private boolean appleReview;

	@RequestMapping(value = "/account/log", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Map<String, Object> logClient(@RequestParam(value = "msg", defaultValue = "") String msg,
			@RequestParam(value = "version", defaultValue = "") String version,
			@RequestParam(value = "extra", defaultValue = "") String extra) {
		msg = FsGameLoginUtils.decodeParameter(msg);
		version = FsGameLoginUtils.decodeParameter(version);
		extra = FsGameLoginUtils.decodeParameter(extra);
		LoggerUtils.logClient("client", msg, version, extra);
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("ret", 0);
		return retMap;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/account/list", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Map<String, Object> list(@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "group", defaultValue = "") String group,
			@RequestParam(value = "version", defaultValue = "") String version,
			@RequestParam(value = "extra", defaultValue = "") String extra, HttpServletRequest request) {
		name = FsGameLoginUtils.decodeParameter(name);
		group = FsGameLoginUtils.decodeParameter(group);
		version = FsGameLoginUtils.decodeParameter(version);
		extra = FsGameLoginUtils.decodeParameter(extra);

		LoggerUtils.info("account list", name, group, version, extra, FsGameLoginUtils.getIpAddr(request));

		Map<String, Object> paramsExtra = GsonFactory.getDefault().fromJson(extra, Map.class);
		if (paramsExtra == null) {
			paramsExtra = new HashMap<String, Object>();
		}

		String platform = FsGameLoginUtils.getMapString(paramsExtra, "platform", "");
		String deviceName = FsGameLoginUtils.getMapString(paramsExtra, "deviceName", "");
		String deviceVersion = FsGameLoginUtils.getMapString(paramsExtra, "deviceVersion", "");
		String deviceId = FsGameLoginUtils.getMapString(paramsExtra, "deviceId", "");
		String idfa = FsGameLoginUtils.getMapString(paramsExtra, "idfa", "");
		int skdId = FsGameLoginUtils.getMapInteger(paramsExtra, "sdkId", 0);
		SdkService service = sdkServiceGroups.getService(skdId);
		if (service == null) {
			throw new BadRequestException("Paramter(sdkId) is illegal");
		}

		if (StringUtils.isNotBlank(idfa)) {
			idfaService.tryAddNewDevice(group, idfa, deviceId);
		}

		Map<String, Object> retMap = new HashMap<String, Object>();
		String ip = SimpleWebUtils.getRequestIp(request);
		retMap.put("allSrv", serverInfoService.getServerListByGroup(group, ip, true));
		retMap.put("lastLoginSrv", serverInfoService.getLastServerList(name, group, ip, true));
		retMap.put("appleReview", appleReview);

		boolean thirdPayOpenState = serverInfoService.curOprThirdPayState(group);
		LoggerUtils.info("thirdPayOpenState =  ", thirdPayOpenState);

		if (FsGameLoginUtils.isChina(ip) == false) {
			// 临时处理, 不是中国区的ip不开放第三方充值
			// thirdPayOpenState = false;
			LoggerUtils.info("account ip region is not china! ", ip);
		}
		retMap.put("thirdPayOpenState", thirdPayOpenState);

		return retMap;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/account/create", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Map<String, Object> create(@RequestParam("name") String name, @RequestParam("pass") String pass,
			@RequestParam(value = "group", defaultValue = "") String group,
			@RequestParam(value = "version", defaultValue = "") String version,
			@RequestParam(value = "extra", defaultValue = "") String extra, HttpServletRequest request) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		if (!systemDataService.isOpenRegist()) {
			retMap.put("retCode", FsGameLoginConst.ERROR_REGIST_SHOW_MSG);
			retMap.put("ticket", null);
			retMap.put("message", "注册功能暂未开启，敬请期待！");
			return retMap;
		}

		name = FsGameLoginUtils.decodeParameter(name);
		pass = FsGameLoginUtils.decodeParameter(pass);
		group = FsGameLoginUtils.decodeParameter(group);
		version = FsGameLoginUtils.decodeParameter(version);
		extra = FsGameLoginUtils.decodeParameter(extra);

		LoggerUtils.info("account create", name, group, version, extra);
		if (StringUtils.isEmpty(name)) {
			throw new BadRequestException("Paramter(name) is empty");
		}
		if (StringUtils.isEmpty(pass)) {
			throw new BadRequestException("Paramter(pass) is empty");
		}
		Map<String, Object> paramsExtra = GsonFactory.getDefault().fromJson(extra, Map.class);
		if (paramsExtra == null) {
			paramsExtra = new HashMap<String, Object>();
		}
		String platform = FsGameLoginUtils.getMapString(paramsExtra, "platform", "");
		String deviceName = FsGameLoginUtils.getMapString(paramsExtra, "deviceName", "");
		String deviceVersion = FsGameLoginUtils.getMapString(paramsExtra, "deviceVersion", "");
		String deviceId = FsGameLoginUtils.getMapString(paramsExtra, "deviceId", "");
		String email = FsGameLoginUtils.getMapString(paramsExtra, "email", "");
		String ip = FsGameLoginUtils.getIpAddr(request);
		int skdId = FsGameLoginUtils.getMapInteger(paramsExtra, "sdkId", 0);
		SdkService service = sdkServiceGroups.getService(skdId);
		if (service == null) {
			throw new BadRequestException("Paramter(sdkId) is illegal");
		}

		if (!FsGameLoginUtils.checkAccount(name)) {
			retMap.put("retCode", FsGameLoginConst.ERROR_REGIST_NAME_ILLEGAL);
			return retMap;
		}

		name = FsGameLoginUtils.compAccountName(name, group);
		retMap.put("accountName", name);
		retMap.put("email", email);
		service.createAccount(retMap, name, pass, platform, deviceName, deviceVersion, deviceId, ip);
		return retMap;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/account/reqTicket", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Map<String, Object> reqTicket(@RequestParam("name") String name, @RequestParam("pass") String pass,
			@RequestParam(value = "group", defaultValue = "") String group,
			@RequestParam(value = "version", defaultValue = "") String version,
			@RequestParam(value = "extra", defaultValue = "") String extra, HttpServletRequest request) {
		name = FsGameLoginUtils.decodeParameter(name);
		pass = FsGameLoginUtils.decodeParameter(pass);
		group = FsGameLoginUtils.decodeParameter(group);
		version = FsGameLoginUtils.decodeParameter(version);
		extra = FsGameLoginUtils.decodeParameter(extra);

		LoggerUtils.info("account reqTicket", name, pass, group, version, extra, FsGameLoginUtils.getIpAddr(request));

		// 确保客户端不传nil
		if (StringUtils.isNotBlank(name) && name.equals("nil")) {
			throw new BadRequestException("Paramter(name) is nil illegal");
		}

		Map<String, Object> paramsExtra = GsonFactory.getDefault().fromJson(extra, Map.class);
		if (paramsExtra == null) {
			paramsExtra = new HashMap<String, Object>();
		}

		String platform = FsGameLoginUtils.getMapString(paramsExtra, "platform", "");
		String deviceName = FsGameLoginUtils.getMapString(paramsExtra, "deviceName", "");
		String deviceVersion = FsGameLoginUtils.getMapString(paramsExtra, "deviceVersion", "");
		String deviceId = FsGameLoginUtils.getMapString(paramsExtra, "deviceId", "");
		String idfa = FsGameLoginUtils.getMapString(paramsExtra, "idfa", "");
		String ip = FsGameLoginUtils.getIpAddr(request);
		int skdId = FsGameLoginUtils.getMapInteger(paramsExtra, "sdkId", 0);
		SdkService service = sdkServiceGroups.getService(skdId);
		if (service == null) {
			throw new BadRequestException("Paramter(sdkId) is illegal");
		}

		Map<String, Object> params = new HashMap<String, Object>(paramsExtra);
		params.put("name", name);
		params.put("pass", pass);
		params.put("group", group);
		PlatformRetData r = (PlatformRetData) service.loginAuth(params);
		if (params.containsKey("fullAccountName")) {
			// 这个fullAccountName是loginAuth之后产生的, 它是comp之后的
			name = (String) params.get("fullAccountName");
		} else {
			// 这里name要么是自己输入要么是平台返回
			name = FsGameLoginUtils.compAccountName((String) params.get("name"), group);
		}
		boolean accountSuitable = serverInfoService.checkAccountAndOpr(name, group);

		Pair<Integer, String> retData;
		if (!serverInfoService.isShehe(group, version) && accountSuitable == false) {
			// 账号和平台不匹配
			retData = Pair.newInstance(FsGameLoginConst.ERROR_ACCOUNT_NOT_MATCH_OPR, "");
			LoggerUtils.info("reqTicket accountSuitable false", name, pass, group, version, extra,
					FsGameLoginUtils.getIpAddr(request));
		} else {
			if (r.isSuccess()) {
				retData = gameAccountService.reqTicketWithCreate(name, service.createAccountIfAbsent(), platform,
						deviceName, deviceVersion, deviceId, ip);
			} else {
				if (r.getPtData() instanceof Integer) {
					retData = Pair.newInstance((Integer) r.getPtData(), "");
				} else {
					retData = Pair.newInstance(FsGameLoginConst.ERROR_REGIST_PASS_PLATFORM, "");
				}
			}
		}

		Map<String, Object> retMap = new HashMap<String, Object>();
		List<ServerInfo> lastSrvList = serverInfoService.getLastServerList(name, group, ip, true);
		retMap.put("retCode", retData.getFirst());
		retMap.put("ticket", retData.getSecond());
		retMap.put("lastLoginSrv", lastSrvList);
		retMap.put("accountName", name); // 告诉客户端平台的账号，comp之后的
		if (params.get("message") != null) {
			retMap.put("message", params.get("message"));
		}
		if (r.getOtherData() != null) {
			// 把平台返回的额外值塞进去
			retMap.putAll(r.getOtherData());
		}

		if (StringUtils.isNotBlank(idfa)) {
			idfaService.tryAddNewDevice(group, idfa, deviceId);
		}

		// 平台accountName和设备id的绑定关系
		sdkAccountRelationService.checkAndAddSdkAccountRelation(name, deviceId);

		return retMap;
	}

	@RequestMapping(value = "/account/checkTicket", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object checkTicket(@RequestParam("name") String name, @RequestParam("ticket") String ticket,
			@RequestParam(value = "group", defaultValue = "") String group,
			@RequestParam(value = "version", defaultValue = "") String version,
			@RequestParam(value = "extra", defaultValue = "") String extra) {
		name = FsGameLoginUtils.decodeParameter(name);
		ticket = FsGameLoginUtils.decodeParameter(ticket);
		group = FsGameLoginUtils.decodeParameter(group);
		version = FsGameLoginUtils.decodeParameter(version);
		extra = FsGameLoginUtils.decodeParameter(extra);

		LoggerUtils.info("account checkTicket", name, ticket, group, version, extra);
		if (StringUtils.isEmpty(name)) {
			throw new BadRequestException("Paramter(name) is empty");
		}
		if (StringUtils.isEmpty(ticket)) {
			throw new BadRequestException("Paramter(ticket) is empty");
		}

		Map<String, Object> retMap = new HashMap<String, Object>();
		int retCode = gameAccountService.checkTicket(name, ticket);
		retMap.put("retCode", retCode);
		return retMap;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/account/loginByTicket", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object loginByTicket(@RequestParam("name") String name, @RequestParam("ticket") String ticket,
			@RequestParam(value = "serverId", defaultValue = "0") int serverId,
			@RequestParam(value = "group", defaultValue = "") String group,
			@RequestParam(value = "version", defaultValue = "") String version,
			@RequestParam(value = "extra", defaultValue = "") String extra, HttpServletRequest request) {
		name = FsGameLoginUtils.decodeParameter(name);
		ticket = FsGameLoginUtils.decodeParameter(ticket);
		group = FsGameLoginUtils.decodeParameter(group);
		version = FsGameLoginUtils.decodeParameter(version);
		extra = FsGameLoginUtils.decodeParameter(extra);

		LoggerUtils.info("account loginByTicket", name, ticket, serverId, group, version, extra,
				FsGameLoginUtils.getIpAddr(request));
		if (StringUtils.isEmpty(name)) {
			throw new BadRequestException("Paramter(name) is empty");
		}
		if (StringUtils.isEmpty(ticket)) {
			throw new BadRequestException("Paramter(ticket) is empty");
		}
		if (serverId <= 0) {
			throw new BadRequestException("Paramter(serverId) is error");
		}

		Map<String, Object> paramsExtra = GsonFactory.getDefault().fromJson(extra, Map.class);
		if (paramsExtra == null) {
			paramsExtra = new HashMap<String, Object>();
		}
		String platform = FsGameLoginUtils.getMapString(paramsExtra, "platform", "");
		String deviceName = FsGameLoginUtils.getMapString(paramsExtra, "deviceName", "");
		String deviceVersion = FsGameLoginUtils.getMapString(paramsExtra, "deviceVersion", "");
		String deviceId = FsGameLoginUtils.getMapString(paramsExtra, "deviceId", "");
		String ip = FsGameLoginUtils.getIpAddr(request);
		String opr = FsGameLoginUtils.getMapString(paramsExtra, "opr", "");

		Map<String, Object> retMap = new HashMap<String, Object>();
		Pair<Integer, String> retData = gameAccountService.loginByTicket(name, ticket, serverId, platform, deviceName,
				deviceVersion, deviceId, ip, opr);
		boolean accountSuitable = serverInfoService.checkAccountAndOpr(name, group);
		if (!serverInfoService.isShehe(group, version) && accountSuitable == false) {
			// 账号和平台不匹配
			retData = Pair.newInstance(FsGameLoginConst.ERROR_ACCOUNT_NOT_MATCH_OPR, "");
			LoggerUtils.info("loginByTicket accountSuitable false", name, group, version, extra,
					FsGameLoginUtils.getIpAddr(request));
		}
		retMap.put("retCode", retData.getFirst());
		retMap.put("ticket", retData.getSecond());

		return retMap;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/account/resetPassword", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object resetPass(@RequestParam("name") String name, @RequestParam("oldPass") String oldPass,
			@RequestParam("newPass") String newPass, @RequestParam(value = "group", defaultValue = "") String group,
			@RequestParam(value = "version", defaultValue = "") String version,
			@RequestParam(value = "extra", defaultValue = "") String extra, HttpServletRequest request) {
		Map<String, Object> retMap = new HashMap<String, Object>();

		name = FsGameLoginUtils.decodeParameter(name);
		oldPass = FsGameLoginUtils.decodeParameter(oldPass);
		newPass = FsGameLoginUtils.decodeParameter(newPass);
		group = FsGameLoginUtils.decodeParameter(group);
		version = FsGameLoginUtils.decodeParameter(version);
		extra = FsGameLoginUtils.decodeParameter(extra);

		LoggerUtils.info("account resetPassword", name, group, version, extra);
		if (StringUtils.isEmpty(name)) {
			throw new BadRequestException("Paramter(name) is empty");
		}
		if (StringUtils.isEmpty(oldPass)) {
			throw new BadRequestException("Paramter(oldPass) is empty");
		}
		if (StringUtils.isEmpty(newPass)) {
			throw new BadRequestException("Paramter(newPass) is empty");
		}

		Map<String, Object> paramsExtra = GsonFactory.getDefault().fromJson(extra, Map.class);
		if (paramsExtra == null) {
			paramsExtra = new HashMap<String, Object>();
		}

		int skdId = FsGameLoginUtils.getMapInteger(paramsExtra, "sdkId", 0);
		SdkService service = sdkServiceGroups.getService(skdId);
		if (service == null) {
			throw new BadRequestException("Paramter(sdkId) is illegal");
		}

		name = FsGameLoginUtils.compAccountName(name, group);
		service.resetPassword(retMap, name, oldPass, newPass);
		String ip = SimpleWebUtils.getRequestIp(request);
		retMap.put("lastLoginSrv", serverInfoService.getLastServerList(name, group, ip, true));
		return retMap;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/account/addfb", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Map<String, Object> addfb(@RequestParam(value = "accountName") String accountName,
			@RequestParam(value = "avatarId") String avatarId, @RequestParam(value = "roleName") String roleName,
			@RequestParam(value = "vipLv", defaultValue = "0") int vipLv,
			@RequestParam(value = "contact", defaultValue = "") String contact,
			@RequestParam(value = "content", defaultValue = "") String content,
			@RequestParam(value = "version", defaultValue = "") String version,
			@RequestParam(value = "extra", defaultValue = "") String extra, HttpServletRequest request) {
		accountName = FsGameLoginUtils.decodeParameter(accountName);
		avatarId = FsGameLoginUtils.decodeParameter(avatarId);
		roleName = FsGameLoginUtils.decodeParameter(roleName);
		contact = FsGameLoginUtils.decodeParameter(contact);
		content = FsGameLoginUtils.decodeParameter(content);
		version = FsGameLoginUtils.decodeParameter(version);
		extra = FsGameLoginUtils.decodeParameter(extra);
		LoggerUtils.info("account addfb", accountName, avatarId, roleName, vipLv, contact, content, version, extra);

		Map<String, Object> paramsExtra = GsonFactory.getDefault().fromJson(extra, Map.class);
		if (paramsExtra == null) {
			paramsExtra = new HashMap<String, Object>();
		}
		String platform = FsGameLoginUtils.getMapString(paramsExtra, "platform", "");
		String deviceName = FsGameLoginUtils.getMapString(paramsExtra, "deviceName", "");
		String deviceVersion = FsGameLoginUtils.getMapString(paramsExtra, "deviceVersion", "");
		String deviceId = FsGameLoginUtils.getMapString(paramsExtra, "deviceId", "");
		String ip = FsGameLoginUtils.getIpAddr(request);

		Feedback fb = new Feedback();
		fb.setAccountName(accountName);
		fb.setAvatarId(avatarId);
		fb.setRoleName(roleName);
		fb.setVipLv(vipLv);
		fb.setContact(contact);
		fb.setContent(content);
		fb.setPlatform(platform);
		fb.setDeviceName(deviceName);
		fb.setDeviceVersion(deviceVersion);
		fb.setDeviceId(deviceId);
		fb.setIp(ip);
		fb.setCreateTime(System.currentTimeMillis());
		feedbackService.addFeedback(fb);
		Map<String, Object> retMap = new HashMap<String, Object>();
		return retMap;
	}

	@RequestMapping(value = "/account/getfb", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Map<String, Object> getfb(@RequestParam(value = "avatarId") String avatarId) {
		avatarId = FsGameLoginUtils.decodeParameter(avatarId);
		LoggerUtils.info("account getfb", avatarId);
		List<Feedback> rectFbs = feedbackService.getFeedbackBy(avatarId);
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("fbs", rectFbs);
		return retMap;
	}

	@RequestMapping(value = "/account/useCoupon", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Map<String, Object> useCoupon(@RequestParam(value = "couponCode") String couponCode,
			@RequestParam(value = "accountName") String accountName, @RequestParam(value = "avatarId") String avatarId,
			@RequestParam(value = "serverId", defaultValue = "0") int serverId,
			@RequestParam(value = "group", defaultValue = "") String group, // 这个就是opr
			@RequestParam(value = "extra", defaultValue = "") String extra, HttpServletRequest request) {
		accountName = FsGameLoginUtils.decodeParameter(accountName);
		avatarId = FsGameLoginUtils.decodeParameter(avatarId);
		LoggerUtils.info("account getCoupon", couponCode, accountName, avatarId, serverId, group, extra);
		Pair<String, Integer> result = couponRecordService.useCoupon(avatarId, accountName, couponCode, serverId,
				group);
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("result", result.getFirst());
		retMap.put("giftId", result.getSecond());
		return retMap;
	}

	@RequestMapping(value = "/account/clientConfig", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Map<String, Object> clientConfig(
			@RequestParam(value = "resVersion", defaultValue = "", required = false) String resVersion,
			@RequestParam(value = "clientVersion", defaultValue = "", required = false) String clientVersion,
			@RequestParam(value = "clientOpr", defaultValue = "", required = false) String clientOpr,
			HttpServletRequest request) {
		resVersion = FsGameLoginUtils.decodeParameter(resVersion);
		clientVersion = FsGameLoginUtils.decodeParameter(clientVersion);
		clientOpr = FsGameLoginUtils.decodeParameter(clientOpr);

		Map<String, Object> retMap = new HashMap<String, Object>();

		// badVersion
		if (StringUtils.isEmpty(resVersion)) {
			retMap.put("badVersion", false);
		} else {
			retMap.put("badVersion", false);
			for (String element : serverInfoService.getBadResVersionList()) {
				if (resVersion.equals(element)) {
					retMap.put("badVersion", true);
					break;
				}
			}
		}

		// review param
		if (StringUtils.isNotEmpty(clientVersion) && StringUtils.isNotEmpty(clientOpr)) {
			if (serverInfoService.isShehe(clientOpr, clientVersion)) {
				retMap.put("needUpdate", false);
			} else {
				retMap.put("needUpdate", true);
			}
		} else {
			retMap.put("needUpdate", true);
		}

		return retMap;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/account/bindGameCenter", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object bindGameCenter(@RequestParam(value = "gameCenterId") String gameCenterId,
			@RequestParam(value = "gameCenterName") String gameCenterName,
			@RequestParam(value = "accountName") String accountName,
			@RequestParam(value = "extra", defaultValue = "") String extra, HttpServletRequest request) {
		gameCenterId = FsGameLoginUtils.decodeParameter(gameCenterId);
		gameCenterName = FsGameLoginUtils.decodeParameter(gameCenterName);
		accountName = FsGameLoginUtils.decodeParameter(accountName);
		extra = FsGameLoginUtils.decodeParameter(extra);
		LoggerUtils.info("account bindGameCenter", gameCenterId, gameCenterName, accountName, extra);
		Map<String, Object> paramsExtra = GsonFactory.getDefault().fromJson(extra, Map.class);
		if (paramsExtra == null) {
			paramsExtra = new HashMap<String, Object>();
		}
		Map<String, Object> params = new HashMap<String, Object>(paramsExtra);
		params.put("gameCenterId", gameCenterId);
		params.put("gameCenterName", gameCenterName);
		params.put("accountName", accountName);
		PlatformRetData r = (PlatformRetData) appleAccountRelationService.bindGameCenter(params);
		return r;
	}

	@RequestMapping(value = "/account/getAppleAccountRelation", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object getAppleAccountRelation(@RequestParam("gameAccount") String gameAccount) {
		LoggerUtils.info("getAppleAccountRelation", gameAccount);
		Object r = appleAccountRelationService.getAppleAccountRelationByGameAccount(gameAccount);
		LoggerUtils.info("getAppleAccountRelation result", r);
		return r;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/account/appstorePurchased", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Map<String, Object> appstorePurchased(@RequestParam(value = "data", defaultValue = "") String data) {
		data = FsGameLoginUtils.decodeParameter(data);
		Map<String, Object> paramsData = GsonFactory.getDefault().fromJson(data, Map.class);
		if (paramsData == null) {
			paramsData = new HashMap<String, Object>();
			return Collections.emptyMap();
		}

		AppstorePurchasedRecord apr = new AppstorePurchasedRecord();
		apr.setServerId(FsGameLoginUtils.getMapInteger(paramsData, "serverId", 0));
		apr.setTransactionId(FsGameLoginUtils.getMapString(paramsData, "transactionId", ""));
		apr.setProductId(FsGameLoginUtils.getMapString(paramsData, "productId", ""));
		apr.setAccountName(FsGameLoginUtils.getMapString(paramsData, "accountName", ""));
		apr.setAvatarId(FsGameLoginUtils.getMapString(paramsData, "avatarId", ""));
		apr.setRoleName(FsGameLoginUtils.getMapString(paramsData, "roleName", ""));
		apr.setGbId(FsGameLoginUtils.getMapLong(paramsData, "gbId", 0l));
		apr.setCreateTime(System.currentTimeMillis());
		appstorePurchasedRecordService.addAppstorePurchasedRecord(apr);

		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("ret", 0);
		return retMap;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/account/googlePurchased", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Map<String, Object> goolePurchased(@RequestParam(value = "data", defaultValue = "") String data) {
		data = FsGameLoginUtils.decodeParameter(data);
		Map<String, Object> paramsData = GsonFactory.getDefault().fromJson(data, Map.class);
		if (paramsData == null) {
			paramsData = new HashMap<String, Object>();
			return Collections.emptyMap();
		}

		GooglePurchasedRecord apr = new GooglePurchasedRecord();
		apr.setServerId(FsGameLoginUtils.getMapInteger(paramsData, "serverId", 0));
		apr.setTransactionId(FsGameLoginUtils.getMapString(paramsData, "transactionId", ""));
		apr.setProductId(FsGameLoginUtils.getMapString(paramsData, "productId", ""));
		apr.setAccountName(FsGameLoginUtils.getMapString(paramsData, "accountName", ""));
		apr.setAvatarId(FsGameLoginUtils.getMapString(paramsData, "avatarId", ""));
		apr.setRoleName(FsGameLoginUtils.getMapString(paramsData, "roleName", ""));
		apr.setGbId(FsGameLoginUtils.getMapLong(paramsData, "gbId", 0l));
		apr.setCreateTime(System.currentTimeMillis());
		googlePurchasedRecordService.addGooglePurchasedRecord(apr);

		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("ret", 0);
		return retMap;
	}

	@RequestMapping(value = "/account/shareUrl", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Map<String, Object> shareUrl(@RequestParam(value = "uid", defaultValue = "") String uid,
			@RequestParam(value = "srvId", defaultValue = "") String srvId,
			@RequestParam(value = "oprGroup", defaultValue = "") String oprGroup,
			@RequestParam(value = "clientIp", defaultValue = "") String clientIp,
			@RequestParam(value = "createTime", defaultValue = "") String createTime, HttpServletRequest request) {

		LoggerUtils.info("account shareUrl before uid= ", uid, " srvId=", srvId, " oprGroup=", oprGroup, " clientIp=",
				clientIp);

		if (StringUtils.isEmpty(oprGroup) || StringUtils.isEmpty(srvId) || StringUtils.isEmpty(clientIp)
				|| StringUtils.isEmpty(uid)) {
			LoggerUtils.info("account shareUrl oprGroup or srvId  or uid clientIp or createTime null ");
			return null;
		}

		uid = FsGameLoginUtils.decodeParameter(uid);
		oprGroup = FsGameLoginUtils.decodeParameter(oprGroup);
		srvId = FsGameLoginUtils.decodeParameter(srvId);
		clientIp = FsGameLoginUtils.decodeParameter(clientIp);
		createTime = FsGameLoginUtils.decodeParameter(createTime);
		int serverId = Integer.valueOf(srvId);
		long curTime = System.currentTimeMillis();
		LoggerUtils.info("account shareUrl after uid= ", uid, " srvId=", srvId, " oprGroup=", oprGroup, " clientIp=",
				clientIp, " createTime=", createTime, " curTime=", curTime);

		long urlCreateTime = Long.valueOf(createTime);
		if (!FsGameLoginUtils.isSameDayForOffset(curTime, urlCreateTime, 0)) {
			LoggerUtils.info("account shareUrl  createTime not same day ");
			return null;
		}

		Object gasRes = null;
		try {
			// 给物品, 有可能不在线不一定能给成功
			if (gasAdminClientManager.getSimpleRpcClientFromSubOpr(serverId, oprGroup) == null) {
				LoggerUtils.info("account shareUrl serverId, oprGroup error");
				return null;
			}

			gasRes = gasAdminClientManager.getSimpleRpcClientFromSubOpr(serverId, oprGroup).getSimpleRpcClient()
					.invokeRpc(FsGameLoginConst.ADMIN_LOGIN_REQ_SHARE_ADD_ITEM, new Object[] { uid, clientIp }).get();

		} catch (Exception e) {

			LoggerUtils.error(e, "account shareUrl uid = ", uid, " srvId=", srvId, " oprGroup = ", oprGroup,
					"clientIp = ", clientIp);
			return null;
		}
		return GsonFactory.getDefault().fromJson((JsonElement) gasRes, new TypeToken<Map<String, Object>>() {
		}.getType());

	}

	@RequestMapping(value = "/account/loginByUserPhone", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object loginByUserPhone(@RequestParam(value = "phone") String phone,
			@RequestParam(value = "extra", defaultValue = "") String extra, HttpServletRequest request) {

		phone = FsGameLoginUtils.decodeParameter(phone);
		extra = FsGameLoginUtils.decodeParameter(extra);
		LoggerUtils.info("loginByUserPhone  phone =", phone, "extra = ", extra);
		if (StringUtils.isEmpty(phone)) {
			throw new BadRequestException("Paramter(phone) is illegal");
		}

		GameAccount ga = gameAccountService.getGameAccountByPhone(phone);
		if (ga == null) {
			return Collections.singletonMap("retCode", (Object) FsGameLoginConst.ERROR_BIND_ACCOUNT_NOT_EXISTS);
		} else {
			return doCaptcha(phone, FsGameLoginConst.BIND_PHONE_KEY_FMT);
		}
	}

	private Map<String, Object> doCaptcha(String phone, String keyFmt) {
		Ehcache ecc = ((EhCacheCache) (cacheManager.getCache("loginCache"))).getNativeCache();
		String key = MessageFormat.format(keyFmt, phone);
		int cacheSec = FsGameLoginConst.CAPTCHA_EXPIRE_TIME_SEC;
		int ret = FsGameLoginConst.CODE_OK;
		String retMsg = "";
		// 下发验证码
		String code = String.valueOf(FsGameLoginUtils.randomIntBetweenInclusive(1000, 9999, 1));
		Triple<Boolean, String, String> platRet = FsGameLoginUtils.sendSingleSms(phone, FsGameLoginConst.GONG_SIGN_NAME,
				FsGameLoginConst.CAPTCHA_TEMPLATE_CODE,
				FsGameLoginUtils.createStringHashMap("code", code, "product", "我的宫廷"));
		if (platRet.getFirst()) {
			// 下发成功才放到cache
			ecc.put(new Element(key, code, 0, cacheSec));
			ret = FsGameLoginConst.CODE_OK;
		} else {
			ret = FsGameLoginConst.ERROR_REGIST_SHOW_MSG;
			retMsg = platRet.getThird();
		}

		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", ret);
		retMap.put("message", retMsg);
		return retMap;
	}

	@RequestMapping(value = "/account/checkCaptchaLogin", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object checkCaptchaLogin(@RequestParam(value = "phone") String phone,
			@RequestParam(value = "code") String code, @RequestParam(value = "extra", defaultValue = "") String extra,
			HttpServletRequest request) {

		phone = FsGameLoginUtils.decodeParameter(phone);
		code = FsGameLoginUtils.decodeParameter(code);
		extra = FsGameLoginUtils.decodeParameter(extra);

		LoggerUtils.info("checkCaptchaLogin  phone =", phone, " code=", code, "extra = ", extra);

		LoggerUtils.info("checkCaptchaLogin ", phone, code, FsGameLoginUtils.getIpAddr(request));

		Ehcache ecc = ((EhCacheCache) (cacheManager.getCache("loginCache"))).getNativeCache();
		String key = MessageFormat.format(FsGameLoginConst.BIND_PHONE_KEY_FMT, phone);
		Element val = ecc.get(key);
		if (val == null) {
			return Collections.singletonMap("retCode", (Object) FsGameLoginConst.ERROR_REGIST_CAPTCHA_NOT_EXISTS);// 验证码不存在
		}

		if (!code.equals((String) val.getObjectValue())) {
			return Collections.singletonMap("retCode", (Object) FsGameLoginConst.ERROR_REGIST_CAPTCHA_ERROR);// 验证码错误
		}

		ecc.remove(key); // 移除验证码

		GameAccount ga = gameAccountService.getGameAccountByPhone(phone);
		if (ga == null) {
			return Collections.singletonMap("retCode", (Object) FsGameLoginConst.ERROR_BIND_ACCOUNT_NOT_EXISTS);
		} else {
			int retCode = FsGameLoginConst.CODE_OK;
			return Collections.singletonMap("retCode", retCode);//
		}

	}

	// 用于获取手机绑定的用户信息
	@RequestMapping(value = "/account/loginWechat", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object loginWechatBYandroid(@RequestParam(value = "phone", defaultValue = "") String phone,
			@RequestParam(value = "code", defaultValue = "") String code, HttpServletRequest request) {
		// @phone编码转换
		phone = FsGameLoginUtils.decodeParameter(phone);
		// 日志输出
		LoggerUtils.info("loginWechatBYandroid  phone =", phone, " code=", code);
		// 获得游戏里账号信息
		if (!StringUtils.isEmpty(phone)) {
			GameAccount accountinfo = gameAccountService.getGameAccountByPhone(phone);
			if (accountinfo != null) {
				String accountname = accountinfo.getAccount();
				return Collections.singletonMap("accountname", accountname);
			} else {
				return Collections.singletonMap("accountname", "");
			}
		} else {

			return Collections.singletonMap("accountname", "");

		}
	}

	// 用于获取服务器列表
	@RequestMapping(value = "/account/loginservicelist", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object loginServicelist(HttpServletRequest request) {

		List<ServerInfo> servicelist = serverInfoService.getAllServer();
		return Collections.singletonMap("servicelist", servicelist);

	}

	// 用于发放游动回调
	@RequestMapping(value = "/account/postYdong", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String PostYdong(@RequestParam(value = "opr", defaultValue = "ios11") String opr,
			@RequestParam(value = "serverState", defaultValue = "2") int serverState,
			@RequestParam(value = "begin", defaultValue = "2017-04-01") String begin,
			@RequestParam(value = "end", defaultValue = "2017-06-01") String end, HttpServletRequest request) {

		List<PlatformPayRecord> list = platformPayRecordService.getorderByopr(opr, serverState, begin, end);
		if (list != null) {
			for (PlatformPayRecord ppr : list) {
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("appid", 188);
				data.put("paytype", "zfb-ydxcm");
				data.put("trade_no", ppr.getOrderSn());
				data.put("money", ppr.getProductAmount());
				data.put("paytime", ppr.getCreateTime() / 1000);
				data.put("extrainfo", ppr.getOrderSn());
				data.put("serverid", ppr.getServerId());
				data.put("roleid", ppr.getAvatarId());
				String date = "appid=" + data.get("appid") + "&paytype=" + data.get("paytype") + "&trade_no="
						+ data.get("trade_no") + "&money=" + data.get("money") + "&paytime=" + data.get("paytime")
						+ "&extrainfo=" + data.get("extrainfo") + "&serverid=" + data.get("serverid") + "&roleid="
						+ data.get("roleid");
				String getUrl = "http://usercenter.tuziyouxi.com/payment/zhichong.php?" + date;
				SimpleHttpRequest shr = SimpleHttpRequest.createPost(getUrl.toString());
				shr.sendGetString();
			}
			return "ok";

		} else {

			return "false";

		}

	}

	// 用于发放游动回调
	@RequestMapping(value = "/account/postReyun", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object postReyun(@RequestParam(value = "out_trade_no", defaultValue = "1000000234163251") String orderId,
			@RequestParam(value = "paytype", defaultValue = "alipay") String paytype, HttpServletRequest request) {
		String url = "http://log.reyun.com/receive/track/payment";
		PlatformPayRecord ppr = platformPayRecordService.getByOrderSn(orderId);
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> context = new HashMap<String, Object>();
		String result = "";
		if (ppr != null) {
			if (ppr.getPlatform().equals("ios")) {
				context.put("currencyAmount", String.valueOf(ppr.getProductAmount() / 100));
				context.put("deviceid", ppr.getIdfa());
				context.put("transactionid", orderId);
				context.put("paymenttype", paytype);
				context.put("currencytype", "CNY");
				context.put("idfa", ppr.getIdfa());
				context.put("idfv", "");
				if (ppr.getOprGroup().equals("ios11")) {
					data.put("appid", YDappkey);
				} else {
					data.put("appid", HIappkey);
				}

				data.put("who", ppr.getAccountName());
				data.put("context", context);

				try {
					String json = GsonFactory.getDefault().toJson(data);
					SimpleHttpRequest shr = SimpleHttpRequest.createPost(url.toString()).createBody(json);
					shr.setHeader("Content-Type", "application/json");
					shr.setCharset("utf-8");
					result = shr.sendGetString();

				} catch (Exception e) {
					LoggerUtils.error("同步到热云失败", e);

				}

			}
			// } else if (ppr.getPlatform().equals("android")) {
			// context.put("currencyAmount",
			// String.valueOf(ppr.getProductAmount() / 100));
			// context.put("deviceid", ppr.getDeviceId());
			// context.put("transactionid", orderId);
			// context.put("paymenttype", paytype);
			// context.put("currencytype", "CNY");
			// context.put("imei", ppr.getDeviceId());
			// data.put("appid", HAappkey);
			// data.put("who", ppr.getAccountName());
			// data.put("context", context);
			// }

		}
		return result;
	}

	/*
	 * 用于获取游戏内login服务器的登录数据
	 * 
	 */
	@RequestMapping(value = "/account/postIDFA", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object postIDFA(@RequestParam(value = "idfa", defaultValue = "") String idfa, HttpServletRequest request) {
		GameAccount m = new GameAccount();
		IdfaDeviceData idfadata = idfaDeviceDataDao.getIdfa(idfa);
		if (idfadata != null) {
			String device = idfadata.getDeviceId();
			GameAccount account = gameAccountDao.getGameAccountBydevice(device);
			if (account != null)
				m = account;
		}
		return m;
	}

	@RequestMapping(value = "/account/posttuling", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String posttuling(@RequestParam(value = "data", defaultValue = "") String data,
			@RequestParam(value = "user", defaultValue = "") String user,
			@RequestParam(value = "token", defaultValue = "") String token, HttpServletRequest request) {
		return "ok";
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/account/testTicket", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Map<String, Object> testTicket(HttpServletRequest request) {
		String name = "test_1111@test";
		String pass = "111";
		String group = "test";
		String version = "";
		String extra = "";

		LoggerUtils.info("account reqTicket", name, pass, group, version, extra, FsGameLoginUtils.getIpAddr(request));

		// 确保客户端不传nil
		if (StringUtils.isNotBlank(name) && name.equals("nil")) {
			throw new BadRequestException("Paramter(name) is nil illegal");
		}

		Map<String, Object> paramsExtra = GsonFactory.getDefault().fromJson(extra, Map.class);
		if (paramsExtra == null) {
			paramsExtra = new HashMap<String, Object>();
		}

		String platform = FsGameLoginUtils.getMapString(paramsExtra, "platform", "");
		String deviceName = FsGameLoginUtils.getMapString(paramsExtra, "deviceName", "");
		String deviceVersion = FsGameLoginUtils.getMapString(paramsExtra, "deviceVersion", "");
		String deviceId = FsGameLoginUtils.getMapString(paramsExtra, "deviceId", "");
		String idfa = FsGameLoginUtils.getMapString(paramsExtra, "idfa", "");
		String ip = FsGameLoginUtils.getIpAddr(request);
		int skdId = FsGameLoginUtils.getMapInteger(paramsExtra, "sdkId", 0);
		SdkService service = sdkServiceGroups.getService(skdId);
		if (service == null) {
			throw new BadRequestException("Paramter(sdkId) is illegal");
		}

		Map<String, Object> params = new HashMap<String, Object>(paramsExtra);
		params.put("name", name);
		params.put("pass", pass);
		params.put("group", group);
		PlatformRetData r = (PlatformRetData) service.loginAuth(params);
		if (params.containsKey("fullAccountName")) {
			// 这个fullAccountName是loginAuth之后产生的, 它是comp之后的
			name = (String) params.get("fullAccountName");
		} else {
			// 这里name要么是自己输入要么是平台返回
			name = FsGameLoginUtils.compAccountName((String) params.get("name"), group);
		}
		boolean accountSuitable = serverInfoService.checkAccountAndOpr(name, group);

		Pair<Integer, String> retData;
		if (!serverInfoService.isShehe(group, version) && accountSuitable == false) {
			// 账号和平台不匹配
			retData = Pair.newInstance(FsGameLoginConst.ERROR_ACCOUNT_NOT_MATCH_OPR, "");
			LoggerUtils.info("reqTicket accountSuitable false", name, pass, group, version, extra,
					FsGameLoginUtils.getIpAddr(request));
		} else {
			retData = gameAccountService.reqTicketWithCreate(name, service.createAccountIfAbsent(), platform,
					deviceName, deviceVersion, deviceId, ip);
		}

		Map<String, Object> retMap = new HashMap<String, Object>();
		List<ServerInfo> lastSrvList = serverInfoService.getLastServerList(name, group, ip, true);
		retMap.put("retCode", retData.getFirst());
		retMap.put("ticket", retData.getSecond());
		retMap.put("lastLoginSrv", lastSrvList);
		retMap.put("accountName", name); // 告诉客户端平台的账号，comp之后的
		if (params.get("message") != null) {
			retMap.put("message", params.get("message"));
		}
		if (r.getOtherData() != null) {
			// 把平台返回的额外值塞进去
			retMap.putAll(r.getOtherData());
		}

		if (StringUtils.isNotBlank(idfa)) {
			idfaService.tryAddNewDevice(group, idfa, deviceId);
		}

		// 平台accountName和设备id的绑定关系
		sdkAccountRelationService.checkAndAddSdkAccountRelation(name, deviceId);

		return retMap;
	}

}
