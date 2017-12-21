package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.meta.PlatformPayRecord;
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

@SdkGroup({ SdkConst.CC_SDK_ANDROID_TIANHONG })
@Component("sdkTianhongService")
@Deprecated
public class SdkTianhongService extends SdkBaseService implements SdkService {

	/**
	 * 灵石注册地址
	 */
	private String lingshiRegisterUrl = "http://fsgame.030303.com/cgi/reg.aspx";

	/**
	 * 灵石登录地址
	 */
	private String lingshiLoginUrl = "http://fsgame.030303.com/cgi/login.aspx";

	private String md5key = "0!$%EFG$13fs3";

	// (商户ID)corpID: 185
	// (商户名称)corpName: tunshicangqiong
	// (用户名)userName: tunshicq
	// (用户标识)userFlag: tscq
	// (md5key)MD5KEY: 0!$%EFG$13fs3
	// (DESkey)DES密钥: Ss4^t!fG

	@Value("${config.admin_account}")
	private String adminAccount;

	@Value("${config.external_auth}")
	private boolean externalAuth;

	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		LoggerUtils.info("Tianhong payCallback ", requestParams);
		String corpID = DataUtils.getMapString(requestParams, "corpID", "");
		String orderID = DataUtils.getMapString(requestParams, "orderID", "");
		String state = DataUtils.getMapString(requestParams, "state", "");
		String payMoney = DataUtils.getMapString(requestParams, "payMoney", "");
		String verifyMD5 = DataUtils.getMapString(requestParams, "verifyMD5", "");
		String myMd5 = FsGameLoginUtils.md5low(corpID + orderID + state + payMoney + md5key);
		String ret = "0";
		if (!state.equalsIgnoreCase("1")) {
			return ret;
		}
		try {
			LoggerUtils.info("Tianhong payCallback sign", verifyMD5, ", mySign", myMd5);
			if (myMd5.equalsIgnoreCase(verifyMD5)) {
				PlatformPayRecord ppr = platformPayRecordService.getByOrderSn(orderID);
				if (ppr != null) {
					if (ppr.getServerState() != FsGameLoginConst.AS_SERVER_STATE_GIVED_ITEM) {
						platformPayRecordService.updateServerState(ppr.getId(),
								FsGameLoginConst.AS_SERVER_STATE_VERIFIED);
						doGivePayItem(ppr);
					}
					ret = "1";
				}
			}
		} catch (Exception e) {
			LoggerUtils.error(e, "");
			ret = "0";
		}

		LoggerUtils.info("Tianhong payCallback ret = ", ret);

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
