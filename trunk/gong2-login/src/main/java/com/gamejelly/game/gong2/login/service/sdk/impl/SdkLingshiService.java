package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.service.sdk.SdkGroup;
import com.gamejelly.game.gong2.login.service.sdk.SdkService;
import com.gamejelly.game.gong2.login.utils.BadRequestException;
import com.gamejelly.game.gong2.login.utils.FsGameLoginConst;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.gamejelly.game.gong2.login.utils.SdkConst;
import com.hadoit.game.common.framework.http.SimpleHttpRequest;
import com.hadoit.game.common.lang.Pair;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

@SdkGroup({ SdkConst.CC_SDK_ANDROID_LINGSHI })
@Component("sdkLingshiService")
public class SdkLingshiService extends SdkBaseService implements SdkService {

	/**
	 * 灵石注册地址
	 */
	private String lingshiRegisterUrl = "http://fsgame.030303.com/cgi/reg.aspx";

	/**
	 * 灵石登录地址
	 */
	private String lingshiLoginUrl = "http://fsgame.030303.com/cgi/login.aspx";

	private static String secretKey = "436365743654674788";

	@Value("${config.admin_account}")
	private String adminAccount;

	@Value("${config.external_auth}")
	private boolean externalAuth;

	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		LoggerUtils.info("lingshi payCallback", requestParams);
		HttpServletRequest request = (HttpServletRequest) requestParams.get("request");
		String myOrderId = FsGameLoginUtils.getMapString(requestParams, "ext1", "");
		float realAmount = FsGameLoginUtils.getMapFloat(requestParams, "realAmount", 0.0f);
		final String queryString = request.getQueryString();
		String signMsg = queryString.substring(queryString.lastIndexOf("&signMsg=") + "&signMsg=".length());
		String signSource = queryString.substring(0, queryString.lastIndexOf("&signMsg="));
		signSource = signSource + "&key=" + secretKey;

		String mySign = FsGameLoginUtils.md5up(signSource);

		String ret = "result=1";
		try {
			if (signMsg.equals(mySign) && realAmount > 0.0f) {
				processOrder(myOrderId, realAmount + "", false);
				ret = "result=0";
			}
		} catch (Exception e) {
			LoggerUtils.error(e);
			ret = "result=1";
		}

		/**
		 * 成功处理的订单，一定要返回"result=0"！！！！ 斯凯只有收到result=0的回应才认为此笔订单交易成功，否则都会再次重复通知，
		 * 直到达到5次或其配置的最大次数； 返回result=1的情况主要发生在：
		 * 校验签名失败，CP方暂时无法处理支付结果保存，数据库异常，系统异常等
		 */
		return ret;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {
		String name = FsGameLoginUtils.getMapString(requestParams, "name", "");
		String pass = FsGameLoginUtils.getMapString(requestParams, "pass", "");
		String group = FsGameLoginUtils.getMapString(requestParams, "group", "");
		LoggerUtils.info("account checkPassword", name, pass, group);
		name = FsGameLoginUtils.compAccountName(name, group);
		if (StringUtils.isEmpty(name)) {
			throw new BadRequestException("Paramter(name) is empty");
		}
		if (StringUtils.isEmpty(pass)) {
			throw new BadRequestException("Paramter(pass) is empty");
		}

		// 是否使用灵石账号登录验证
		if (externalAuth) {
			// 去灵石验证
			StringBuilder getUrl = new StringBuilder(lingshiLoginUrl);
			getUrl.append("?username=");
			getUrl.append(name);
			getUrl.append("&userpwd=");
			getUrl.append(pass);

			SimpleHttpRequest shr = SimpleHttpRequest.createGet(getUrl.toString());
			String result = shr.sendGetString();
			Map<String, String> rm = GsonFactory.getDefault().fromJson(result, Map.class);
			String jsonResult = FsGameLoginUtils.getMapString(rm, "success", "");
			String jsonMsg = FsGameLoginUtils.getMapString(rm, "msg", "");

			if (jsonResult.equals("True")) {
				int retCode;
				if (name.equals(adminAccount)) {
					retCode = gameAccountService.checkPassword(name, pass, group); // 游戏服务器验证admin
				} else {
					retCode = FsGameLoginConst.CODE_OK;
				}
				return new PlatformRetData(retCode == FsGameLoginConst.CODE_OK, retCode);
			} else {
				requestParams.put("message", jsonMsg);
				return new PlatformRetData(false, FsGameLoginConst.ERROR_REGIST_SHOW_MSG);
			}
		} else {
			int retCode = gameAccountService.checkPassword(name, pass, group);
			return new PlatformRetData(retCode == FsGameLoginConst.CODE_OK, retCode);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void createAccount(Map<String, Object> requestParams, String name, String pass, String platform,
			String deviceName, String deviceVersion, String deviceId, String ip) {
		// 是否使用灵石账号登录验证
		if (externalAuth) {
			StringBuilder getUrl = new StringBuilder(lingshiRegisterUrl);
			getUrl.append("?username=");
			getUrl.append(name);
			getUrl.append("&userpwd=");
			getUrl.append(pass);

			SimpleHttpRequest shr = SimpleHttpRequest.createGet(getUrl.toString());
			String result = shr.sendGetString();
			Map<String, String> rm = GsonFactory.getDefault().fromJson(result, Map.class);
			String jsonResult = FsGameLoginUtils.getMapString(rm, "success", "");
			String jsonMsg = FsGameLoginUtils.getMapString(rm, "msg", "");

			if (jsonResult.equals("True")) {
				Pair<Integer, String> createData;
				if (name.equals(adminAccount)) {
					createData = gameAccountService.createAccount(name, pass, platform, deviceName, deviceVersion,
							deviceId, ip);
				} else {
					createData = gameAccountService.createAccount(name, FsGameLoginConst.ACCOUNT_PASS_DEFAULT,
							platform, deviceName, deviceVersion, deviceId, ip);
				}
				requestParams.put("retCode", createData.getFirst());
				requestParams.put("ticket", createData.getSecond());
			} else {
				requestParams.put("retCode", FsGameLoginConst.ERROR_REGIST_SHOW_MSG);
				requestParams.put("ticket", null);
				requestParams.put("message", jsonMsg);
			}
		} else {
			Pair<Integer, String> createData = gameAccountService.createAccount(name, pass, platform, deviceName,
					deviceVersion, deviceId, ip);
			requestParams.put("retCode", createData.getFirst());
			requestParams.put("ticket", createData.getSecond());
		}
	}

	@Override
	public boolean createAccountIfAbsent() {
		return externalAuth ? true : false;
	}

}
