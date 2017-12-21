package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.service.sdk.SdkGroup;
import com.gamejelly.game.gong2.login.service.sdk.SdkService;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.gamejelly.game.gong2.login.utils.SdkConst;

@SdkGroup({ SdkConst.CC_SDK_ANDROID_KOUDAI })
@Component("sdkKoudaiService")
public class SdkKoudaiService extends SdkBaseService implements SdkService {

	private static final String AppKey = "43956d10a83381787aa9a809fba1a742";

	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		LoggerUtils.info("koudai payCallback", requestParams);
		String orderid = FsGameLoginUtils.getMapString(requestParams, "orderid", "");
		String username = FsGameLoginUtils.getMapString(requestParams, "username", "");
		String gameid = FsGameLoginUtils.getMapString(requestParams, "gameid", "");
		String roleid = FsGameLoginUtils.getMapString(requestParams, "roleid", "");
		String serverid = FsGameLoginUtils.getMapString(requestParams, "serverid", "");
		String paytype = FsGameLoginUtils.getMapString(requestParams, "paytype", "");
		String amount = FsGameLoginUtils.getMapString(requestParams, "amount", "");
		String paytime = FsGameLoginUtils.getMapString(requestParams, "paytime", "");
		String attach = FsGameLoginUtils.getMapString(requestParams, "attach", "");
		String sign = FsGameLoginUtils.getMapString(requestParams, "sign", "");
		try {
			String signSource = "orderid=" + orderid + "&username=" + username + "&gameid=" + gameid + "&roleid="
					+ roleid + "&serverid=" + serverid + "&paytype=" + paytype + "&amount=" + amount + "&paytime="
					+ paytime + "&attach=" + attach + "&appkey=" + AppKey;
			String signCal = FsGameLoginUtils.md5up(signSource);
			if (signCal.equals(sign.toUpperCase())) {
				processOrder(attach, amount, true);
				return "success";
			} else {
				LoggerUtils.info("koudai 验签失败", signSource, signCal, sign);
				return "errorSign";
			}
		} catch (Exception e) {
			LoggerUtils.error(e);
			return "error";
		}
	}

	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {
		String Uin = FsGameLoginUtils.getMapString(requestParams, "Uin", "");
		String SessionId = FsGameLoginUtils.getMapString(requestParams, "SessionId", "");
		String logintime = FsGameLoginUtils.getMapString(requestParams, "logintime", "");

		boolean result = false;
		if (FsGameLoginUtils.md5up("username=" + Uin + "&appkey=" + AppKey + "&logintime=" + logintime).equals(
				SessionId.toUpperCase())) {
			result = true;
		}
		return new PlatformRetData(result, null);
	}

}
