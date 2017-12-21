package com.gamejelly.game.gong2.login.web.sdk;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gamejelly.game.gong2.login.meta.GameAccount;
import com.gamejelly.game.gong2.login.meta.IdCardNameError;
import com.gamejelly.game.gong2.login.meta.InvitationCodeRecord;
import com.gamejelly.game.gong2.login.meta.InvitationPlayerModel;
import com.gamejelly.game.gong2.login.service.AppleAccountRelationService;
import com.gamejelly.game.gong2.login.service.GameAccountService;
import com.gamejelly.game.gong2.login.service.InvitationService;
import com.gamejelly.game.gong2.login.service.PlatformPayRecordService;
import com.gamejelly.game.gong2.login.service.SdkServiceGroups;
import com.gamejelly.game.gong2.login.service.SystemDataService;
import com.gamejelly.game.gong2.login.service.UserCreditService2;
import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.service.sdk.SdkService;
import com.gamejelly.game.gong2.login.utils.BadRequestException;
import com.gamejelly.game.gong2.login.utils.FsGameLoginConst;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.hadoit.game.common.lang.Pair;
import com.hadoit.game.common.lang.Triple;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

/**
 * 暴露给内部服务器使用
 * 
 * @author apple
 * 
 */
@Controller
public class SdkInnerController {

	@Autowired
	private SdkServiceGroups sdkServiceGroups;

	@Autowired
	private InvitationService invitationservice;

	@Autowired
	private AppleAccountRelationService appleAccountRelationService;

	@Autowired
	private GameAccountService gameAccountService;

	@Autowired
	private CacheManager cacheManager;
	@Autowired
	private UserCreditService2 userCreditService2;

	@Resource
	private PlatformPayRecordService platformPayRecordService;

	@Autowired
	private SystemDataService systemDataService;

	/**
	 * 验证订单
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/inner/verifyOrder", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object verifyOrder(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = FsGameLoginUtils.getRequestParameterMap(request, response);
		int skdId = FsGameLoginUtils.getMapInteger(params, "sdkId", 0);
		SdkService service = sdkServiceGroups.getService(skdId);
		if (service == null) {
			throw new BadRequestException("Paramter(sdkId) is illegal");
		}
		Object r = service.verifyOrder(params);
		return r;
	}

	/**
	 * 同步邀请码
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/inner/syncInvitationCode", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object syncInvitationCode(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = FsGameLoginUtils.getRequestParameterMap(request, response);

		String crossServerId = FsGameLoginUtils.getMapString(params, "crossServerId", "");
		String avatarId = FsGameLoginUtils.getMapString(params, "avatarId", "");
		String accountName = FsGameLoginUtils.getMapString(params, "accountName", "");
		String invitationCode = FsGameLoginUtils.getMapString(params, "invitationCode", "");
		Long createTime = FsGameLoginUtils.getMapLong(params, "createTime", Long.valueOf(0));

		LoggerUtils.info("avatarId----syncInvitationCode-----", avatarId);

		// /写入 loginDb
		InvitationCodeRecord codeRecord = new InvitationCodeRecord();
		codeRecord.setCrossServerId(crossServerId);
		codeRecord.setAvatarId(avatarId);
		codeRecord.setInvitationCode(invitationCode);
		codeRecord.setAccountName(accountName);
		codeRecord.setCreateTime(createTime);

		invitationservice.addInvitationCode(codeRecord);

		Object r = new PlatformRetData(true, "success-------syncInvitationCode------");
		return r;
	}

	/**
	 * 邀请码认证
	 * 
	 */
	@RequestMapping(value = "/inner/verifyInvitateCode", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object verifyInvitateCode(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = FsGameLoginUtils.getRequestParameterMap(request, response);

		String crossServerId = FsGameLoginUtils.getMapString(params, "crossServerId", "");
		String avatarId = FsGameLoginUtils.getMapString(params, "avatarId", "");
		String accountName = FsGameLoginUtils.getMapString(params, "accountName", "");
		String invitationCode = FsGameLoginUtils.getMapString(params, "invitationCode", "");
		String roleName = FsGameLoginUtils.getMapString(params, "roleName", "");
		Long createTime = FsGameLoginUtils.getMapLong(params, "createTime", Long.valueOf(0));
		int maxInviteCount = FsGameLoginUtils.getMapInteger(params, "maxInviteCount", 30);

		int lv = FsGameLoginUtils.getMapInteger(params, "lv", 0);
		int vipLV = FsGameLoginUtils.getMapInteger(params, "vipLv", 0);
		int iconIdex = FsGameLoginUtils.getMapInteger(params, "iconIdex", 0);
		int sex = FsGameLoginUtils.getMapInteger(params, "sex", 0);
		// /写入 loginDb
		InvitationPlayerModel verifyModel = new InvitationPlayerModel();
		verifyModel.setCrossServerId(crossServerId);
		verifyModel.setAvatarId(avatarId);
		verifyModel.setInvitationCode(invitationCode);
		verifyModel.setAccountName(accountName);
		verifyModel.setCreateTime(createTime);
		verifyModel.setLv(lv);
		verifyModel.setVipLv(vipLV);
		verifyModel.setRoleName(roleName);
		verifyModel.setSex(sex);
		verifyModel.setIconIdex(iconIdex);
		Pair<Integer, String> ret = invitationservice.verifyAndUseCode(verifyModel, maxInviteCount);

		return ret;
	}

	/**
	 * 获取邀请码所邀请到的玩家列表
	 * 
	 */
	@RequestMapping(value = "/inner/getPlayerList", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object getPlayerList(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = FsGameLoginUtils.getRequestParameterMap(request, response);

		String code = FsGameLoginUtils.getMapString(params, "codeId", "");
		List<InvitationPlayerModel> ret = invitationservice.getPlayersByCode(code);

		return ret;
	}

	/**
	 * 同步玩家数等级Lv and vipLv到被邀请列表invitationPlayerModel
	 * 
	 */
	@RequestMapping(value = "/inner/sysInvitationPlayerModel", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object sysInvitationPlayerModel(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = FsGameLoginUtils.getRequestParameterMap(request, response);

		String crossServerId = FsGameLoginUtils.getMapString(params, "crossServerId", "");
		String avatarId = FsGameLoginUtils.getMapString(params, "avatarId", "");
		int lv = FsGameLoginUtils.getMapInteger(params, "lv", 0);
		int vipLv = FsGameLoginUtils.getMapInteger(params, "vipLv", 0);
		LoggerUtils.info("crossServerId---------", crossServerId);

		invitationservice.changeInvitePlayerData(crossServerId, avatarId, lv, vipLv);
		Object ret = new PlatformRetData(true, "success---sysInvitationPlayerModel---");
		return ret;
	}

	/**
	 * 所邀请到的玩家等级达到 40
	 * 
	 */
	@RequestMapping(value = "/inner/playerGetToLv", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object playerGetToLv(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = FsGameLoginUtils.getRequestParameterMap(request, response);

		String inviteCode = FsGameLoginUtils.getMapString(params, "inviteCode", "");
		int level = FsGameLoginUtils.getMapInteger(params, "level", 0);

		LoggerUtils.info("inviteCode---------", inviteCode);

		Triple<Integer, Integer, Integer> t = invitationservice.calcInvitationByCode(inviteCode, level);

		return t;
	}

	/**
	 * 创建订单(appstore不需要)
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/inner/createOrder", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object createOrder(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = FsGameLoginUtils.getRequestParameterMap(request, response);
		int skdId = FsGameLoginUtils.getMapInteger(params, "sdkId", 0);
		SdkService service = sdkServiceGroups.getService(skdId);
		if (service == null) {
			throw new BadRequestException("Paramter(sdkId) is illegal");
		}
		LoggerUtils.info("createOrder", skdId);
		Object r = service.createOrder(params);
		return r;
	}

	@RequestMapping(value = "/inner/verifyAllOrderWithCheck", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object verifyAllOrderWithCheck(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = FsGameLoginUtils.getRequestParameterMap(request, response);
		int skdId = FsGameLoginUtils.getMapInteger(params, "sdkId", 0);
		SdkService service = sdkServiceGroups.getService(skdId);
		if (service == null) {
			throw new BadRequestException("Paramter(sdkId) is illegal");
		}
		Object r = service.verifyAllOrderWithCheck(params);
		return r;
	}

	/**
	 * 处理一些sdk特殊的数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/inner/handleSdkData", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object handleSdkData(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = FsGameLoginUtils.getRequestParameterMap(request, response);
		int skdId = FsGameLoginUtils.getMapInteger(params, "sdkId", 0);
		SdkService service = sdkServiceGroups.getService(skdId);
		if (service == null) {
			throw new BadRequestException("Paramter(sdkId) is illegal");
		}
		LoggerUtils.info("handleSdkData", skdId);
		Object r = service.handleSdkData(params);
		return r;
	}

	@RequestMapping(value = "/inner/checkAndClaimReward", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object checkAndClaimReward(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = FsGameLoginUtils.getRequestParameterMap(request, response);
		String accountName = FsGameLoginUtils.getMapString(params, "accountName", "");

		LoggerUtils.info("checkAndClaimReward ", accountName);

		boolean r = appleAccountRelationService.checkAndClaimReward(accountName);
		return Collections.singletonMap("result", r);
	}

	@RequestMapping(value = "/inner/checkEnableBind", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object checkEnableBind(HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> params = FsGameLoginUtils.getRequestParameterMap(request, response);
		String account = FsGameLoginUtils.getMapString(params, "account", "");
		if (StringUtils.isEmpty(account)) {
			throw new BadRequestException("Paramter(account) is illegal");
		}
		GameAccount ga = gameAccountService.getGameAccountByAccount(account);
		if (ga == null || StringUtils.isBlank(ga.getPhone())) {
			return "";
		} else {
			return ga.getPhone();
		}
	}

	@RequestMapping(value = "/inner/bindUserPhone", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object bindUserPhone(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = FsGameLoginUtils.getRequestParameterMap(request, response);
		String account = FsGameLoginUtils.getMapString(params, "account", "");
		String phone = FsGameLoginUtils.getMapString(params, "phone", "");
		LoggerUtils.info("bindUserPhone", phone);
		if (StringUtils.isEmpty(phone)) {
			throw new BadRequestException("Paramter(phone) is illegal");
		}
		GameAccount ga = gameAccountService.getGameAccountByPhone(phone);
		if (ga != null) {
			return Collections.singletonMap("retCode", (Object) FsGameLoginConst.ERROR_BIND_PHONE_BINDED);
		} else {
			ga = gameAccountService.getGameAccountByAccount(account);
			if (ga == null) {
				return Collections.singletonMap("retCode", (Object) FsGameLoginConst.ERROR_BIND_ACCOUNT_NOT_EXISTS);
			} else if (StringUtils.isNotBlank(ga.getPhone())) {
				return Collections.singletonMap("retCode", (Object) FsGameLoginConst.ERROR_BIND_ACCOUNT_BINDED);
			} else {
				return doCaptcha(phone, FsGameLoginConst.BIND_PHONE_KEY_FMT);
			}
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

	@RequestMapping(value = "/inner/checkCaptcha", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object checkCaptcha(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = FsGameLoginUtils.getRequestParameterMap(request, response);
		String account = FsGameLoginUtils.getMapString(params, "account", "");
		String phone = FsGameLoginUtils.getMapString(params, "phone", "");
		String code = FsGameLoginUtils.getMapString(params, "code", "");

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
		GameAccount ga = gameAccountService.getGameAccountByAccount(account);
		if (StringUtils.isNotBlank(ga.getPhone())) {
			return Collections.singletonMap("retCode", FsGameLoginConst.ERROR_BIND_ACCOUNT_BINDED);
		} else {
			ga.setPhone(phone);
			gameAccountService.updateGameAccount(ga);
			return Collections.singletonMap("retCode", FsGameLoginConst.CODE_OK);// 绑定成功
		}
	}

	@RequestMapping(value = "/inner/loginByUserPhone", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object loginByUserPhone(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = FsGameLoginUtils.getRequestParameterMap(request, response);
		String phone = FsGameLoginUtils.getMapString(params, "phone", "");
		LoggerUtils.info("loginByUserPhone", phone);
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

	@RequestMapping(value = "/inner/checkCaptchaLogin", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object checkCaptchaLogin(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = FsGameLoginUtils.getRequestParameterMap(request, response);

		String phone = FsGameLoginUtils.getMapString(params, "phone", "");
		String code = FsGameLoginUtils.getMapString(params, "code", "");

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

	/**
	 * 实名验证
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/inner/getShimingData", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object getShimingData(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = FsGameLoginUtils.getRequestParameterMap(request, response);

		String name = FsGameLoginUtils.getMapString(params, "name", "");
		String idCardNo = FsGameLoginUtils.getMapString(params, "idCardNo", "");
		String accountName = FsGameLoginUtils.getMapString(params, "accountName", "");

		LoggerUtils.info("getShimingData ", accountName, name, idCardNo, FsGameLoginUtils.getIpAddr(request));

		Map<String, Object> retMap = new HashMap<String, Object>();

		GameAccount account = gameAccountService.getGameAccountByAccount(accountName);
		if (account == null) {
			retMap.put("retCode", FsGameLoginConst.ERROR_REGIST_NAME_ILLEGAL);

			return retMap;
		}

		GameAccount accountByIdCard = gameAccountService.getGameAccountByIdCardNo(idCardNo);
		if (accountByIdCard != null) {
			// 验证过
			retMap.put("retCode", FsGameLoginConst.ERROR_BIND_ID_CARD_NO_BINDED);
			return retMap;
		}

		IdCardNameError idCardError = gameAccountService.getIdCardNameError(name, idCardNo);
		if (idCardError != null) {

			account.setSmyzErrorCount(account.getSmyzErrorCount() + 1);
			account.setSmyzErrorTime(System.currentTimeMillis());
			gameAccountService.updateGameAccount(account);

			retMap.put("smyzErrorCount", account.getSmyzErrorCount());
			retMap.put("smyzErrorTime", account.getSmyzErrorTime());

			retMap.put("retCode", FsGameLoginConst.ERROR_BIND_ID_CARD_NAME_ERROR);
			LoggerUtils.info("getShimingData IdCardNameError", name, idCardNo);
			return retMap;// 验证错误
		}

		Object r = userCreditService2.getShimingData2(name, idCardNo);
		if (r.equals("1")) {
			account.setName(name);
			account.setIdCardNo(idCardNo);
			gameAccountService.updateGameAccount(account);
		} else {

			account.setSmyzErrorCount(account.getSmyzErrorCount() + 1);
			account.setSmyzErrorTime(System.currentTimeMillis());
			gameAccountService.updateGameAccount(account);

			IdCardNameError model = new IdCardNameError(account.getAccount(), name, idCardNo);
			gameAccountService.addIdCardNameError(model);

			retMap.put("smyzErrorCount", account.getSmyzErrorCount());
			retMap.put("smyzErrorTime", account.getSmyzErrorTime());
			retMap.put("retCode", FsGameLoginConst.ERROR_BIND_ID_CARD_NAME_ERROR);
			return retMap;// 验证错误
		}
		LoggerUtils.info("getShimingData", r);

		retMap.put("retCode", FsGameLoginConst.CODE_OK);
		return retMap;

	}

	@RequestMapping(value = "/inner/getGameAccountNeedData", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object getGameAccountNeedData(HttpServletRequest request, HttpServletResponse response) {

		// 检查绑定手机状态和实名认证状态
		Map<String, Object> params = FsGameLoginUtils.getRequestParameterMap(request, response);
		String account = FsGameLoginUtils.getMapString(params, "account", "");
		if (StringUtils.isEmpty(account)) {
			throw new BadRequestException("Paramter(account) is illegal");
		}

		Map<String, Object> retMap = new HashMap<String, Object>();
		GameAccount ga = gameAccountService.getGameAccountByAccount(account);
		if (ga != null) {
			retMap.put("phone", ga.getPhone());
			retMap.put("name", ga.getName());
			retMap.put("idCardNo", ga.getIdCardNo());
			retMap.put("smyzErrorCount", ga.getSmyzErrorCount());
			retMap.put("smyzErrorTime", ga.getSmyzErrorTime());
			retMap.put("openShiming", systemDataService.isOpenShiMing());
			retMap.put("openShimingShow", systemDataService.isOpenShiMingShow());

		}

		LoggerUtils.info("getGameAccountNeedData retMap=", retMap);
		return retMap;
	}

	@RequestMapping(value = "/inner/canSendRedPackageByOrderId", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object canSendRedPackageByOrderId(HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> params = FsGameLoginUtils.getRequestParameterMap(request, response);
		String orderSn = FsGameLoginUtils.getMapString(params, "orderSn", "");
		if (StringUtils.isBlank(orderSn)) {
			return false;
		}
		Object result = platformPayRecordService.canSendRedPackageByOrderId(orderSn);
		LoggerUtils.info("canSendRedPackageByOrderId,orderSn,result: ", orderSn, result);
		return result;
	}

	@RequestMapping(value = "/inner/setSendRedPackage", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object setSendRedPackage(HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> params = FsGameLoginUtils.getRequestParameterMap(request, response);
		String orderSn = FsGameLoginUtils.getMapString(params, "orderSn", "");
		if (StringUtils.isBlank(orderSn)) {
			return false;
		}
		Object result = platformPayRecordService.setSendRedPackage(orderSn);
		LoggerUtils.info("setSendRedPackage,orderSn,result: ", orderSn, result);
		return result;
	}
}
