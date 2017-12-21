package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.hadoit.game.common.lang.DataUtils;
import com.hadoit.game.common.lang.Pair;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

@SdkGroup({ SdkConst.CC_SDK_ANDROID_ZY })
@Component("sdkZyService")
public class SdkZyService extends SdkBaseService implements SdkService {

	/**
	 * 灵石注册地址
	 */
	private String lingshiRegisterUrl = "http://fsgame.030303.com/cgi/reg.aspx";

	/**
	 * 灵石登录地址
	 */
	private String lingshiLoginUrl = "http://fsgame.030303.com/cgi/login.aspx";

	private String appId = "481";

	private String sdkSecret = "3d462237ddcdaa67705697e08dca60aa";

	private String appSecret = "7325e7594b0ad03f0e3881e0efcf9737";

	// APP ID：481
	// 应用SDK密钥：3d462237ddcdaa67705697e08dca60aa
	// 应用服务端密钥：7325e7594b0ad03f0e3881e0efcf9737

	@Value("${config.admin_account}")
	private String adminAccount;

	@Value("${config.external_auth}")
	private boolean externalAuth;

	private static String getSignData(Map<String, String> params) {
		StringBuffer content = new StringBuffer();
		List<String> keys = new ArrayList<String>(params.keySet());
		// 按照自然升序处理
		Collections.sort(keys);

		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			// sign参数不参与签名数据
			if ("sign".equals(key)) {
				continue;
			}
			String value = (String) params.get(key);
			if (value != null) {
				content.append((i == 0 ? "" : "&") + key + "=" + value);
			} else {
				content.append((i == 0 ? "" : "&") + key + "=");
			}
		}
		return content.toString();
	}

	@Override
	public Object payCallback(Map<String, Object> requestParams) {

		// Recharge_Id=xxx&App_Id=xxx&Uin=xxx&Urecharge_Id=xxx&Extra=xxx&Rechar
		// ge_Money=xxx&Recharge_Gold_Count=xxx&Pay_Status=xxx&Create_Time=xx
		// x&Sign=xxxxxxxxxxxx
		//
		// 响应:status:’success’
		LoggerUtils.info("zy payCallback ", requestParams);

		String Recharge_Id = DataUtils.getMapString(requestParams, "Recharge_Id", "");
		String App_Id = DataUtils.getMapString(requestParams, "App_Id", "");
		String Uin = DataUtils.getMapString(requestParams, "Uin", "");
		String Urecharge_Id = DataUtils.getMapString(requestParams, "Urecharge_Id", "");// 我们的订单号
		String Extra = DataUtils.getMapString(requestParams, "Extra", "");
		String Recharge_Money = DataUtils.getMapString(requestParams, "Recharge_Money", "");
		String Recharge_Gold_Count = DataUtils.getMapString(requestParams, "Recharge_Gold_Count", "");
		String Pay_Status = DataUtils.getMapString(requestParams, "Pay_Status", "");
		String Create_Time = DataUtils.getMapString(requestParams, "Create_Time", "");
		String Sign = DataUtils.getMapString(requestParams, "Sign", "");

		Map<String, String> transParams = new HashMap<String, String>();
		transParams.put("Recharge_Id", Recharge_Id);
		transParams.put("App_Id", App_Id);
		transParams.put("Uin", Uin);
		transParams.put("Urecharge_Id", Urecharge_Id);
		transParams.put("Extra", Extra);
		transParams.put("Recharge_Money", Recharge_Money);
		transParams.put("Recharge_Gold_Count", Recharge_Gold_Count);
		transParams.put("Pay_Status", Pay_Status);
		transParams.put("Create_Time", Create_Time);

		String pStr = getSignData(transParams);
		String mySign = FsGameLoginUtils.md5low(pStr + appSecret);

		String ret = "failure";
		try {
			LoggerUtils.info("zy payCallback sign", Sign, ", mySign", mySign);
			if (Pay_Status.equals("1") && mySign.equalsIgnoreCase(Sign)) {
				processOrder(Urecharge_Id, Recharge_Money, true);
				ret = "success";
			}
		} catch (Exception e) {
			LoggerUtils.error(e);
			ret = "failure";
		}

		LoggerUtils.info("zy payCallback ret = ", ret);

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
