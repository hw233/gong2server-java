package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.service.sdk.SdkGroup;
import com.gamejelly.game.gong2.login.service.sdk.SdkService;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.gamejelly.game.gong2.login.utils.SdkConst;
import com.hadoit.game.common.framework.http.SimpleHttpRequest;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

@SdkGroup({ SdkConst.CC_SDK_ANDROID_AZXY })
@Component("sdkAzxyService")
public class SdkAzxyService extends SdkBaseService implements SdkService {

	private static final String AppId = "100014434";
	private static final String AppKey = "zumyt2Q9RmGVl0KVe8HiKk3AQt52TsqY";
	private static final String PayKey = "thOGEO3QMBWUk4BjH95MypSuOhCZUO5P";

	private static final String loginCheckUrl = "http://passport.xyzs.com/checkLogin.php";

	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		LoggerUtils.info("azxy payCallback", requestParams);
		String orderid = FsGameLoginUtils.getMapString(requestParams, "orderid", "");
		String uid = FsGameLoginUtils.getMapString(requestParams, "uid", "");
		String serverid = FsGameLoginUtils.getMapString(requestParams, "serverid", "");
		String amount = FsGameLoginUtils.getMapString(requestParams, "amount", "");
		String extra = FsGameLoginUtils.getMapString(requestParams, "extra", "");
		String ts = FsGameLoginUtils.getMapString(requestParams, "ts", "");
		String sign = FsGameLoginUtils.getMapString(requestParams, "sign", "");
		String sig = FsGameLoginUtils.getMapString(requestParams, "sig", "");

		String signSource = AppKey + "amount=" + String.valueOf(amount) + "&extra=" + extra + "&orderid=" + orderid
				+ "&serverid=" + serverid + "&ts=" + String.valueOf(ts) + "&uid=" + String.valueOf(uid);
		String sigSource = PayKey + "amount=" + String.valueOf(amount) + "&extra=" + extra + "&orderid=" + orderid
				+ "&serverid=" + serverid + "&ts=" + String.valueOf(ts) + "&uid=" + String.valueOf(uid);

		String signCal = FsGameLoginUtils.md5up(signSource);
		String sigCal = FsGameLoginUtils.md5up(sigSource);
		try {
			if (signCal.equals(sign.toUpperCase()) && sigCal.equals(sig.toUpperCase())) {
				processOrder(extra, amount, true);
				return "success";
			}
		} catch (Exception e) {
			LoggerUtils.error(e);
			return "fail";
		}

		return "fail";
	}

	@SuppressWarnings("unchecked")
	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {
		String Uin = FsGameLoginUtils.getMapString(requestParams, "Uin", "");
		String SessionId = FsGameLoginUtils.getMapString(requestParams, "SessionId", "");

		Map<String, Object> m = new HashMap<String, Object>();
		m.put("uid", Uin);
		m.put("appid", AppId);
		m.put("token", SessionId);
		SimpleHttpRequest shr = SimpleHttpRequest.createPost(loginCheckUrl.toString());
		shr.createFormBody(FsGameLoginUtils.mapToParamBody(m));
		String resultGet = shr.sendGetString();

		boolean result = false;
		if (resultGet == null || resultGet.equals("")) {
			result = false;
		} else {
			Map<String, Object> rm = GsonFactory.getDefault().fromJson(resultGet, Map.class);
			int code = Double.valueOf(String.valueOf(rm.get("ret"))).intValue();
			if (code == 0) {
				result = true;
			} else {
				result = false;
			}
		}

		return new PlatformRetData(result, null);
	}

}
