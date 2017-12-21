package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.util.Collections;
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

@SdkGroup({ SdkConst.CC_SDK_IOS_TB })
@Component("sdkTBService")
public class SdkTBService extends SdkBaseService implements SdkService {

	private String AppId = "160404";
	private String AppKey = "FubFAqBkxcC2J0SjIKRXt@62M@uok0wg";

	private String loginCheckUrl = "http://tgi.tongbu.com/api/LoginCheck.ashx";

	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		LoggerUtils.info("tb payCallback", requestParams);
		String source = FsGameLoginUtils.getMapString(requestParams, "source", "");
		String trade_no = FsGameLoginUtils.getMapString(requestParams, "trade_no", "");
		String amount = FsGameLoginUtils.getMapString(requestParams, "amount", "");
		String partner = FsGameLoginUtils.getMapString(requestParams, "partner", "");
		String paydes = FsGameLoginUtils.getMapString(requestParams, "paydes", "");
		String debug = FsGameLoginUtils.getMapString(requestParams, "debug", "");
		String tborder = FsGameLoginUtils.getMapString(requestParams, "tborder", "");
		String sign = FsGameLoginUtils.getMapString(requestParams, "sign", "");

		String succ = GsonFactory.getDefault().toJson(Collections.singletonMap("status", "success"));
		String error = GsonFactory.getDefault().toJson(Collections.singletonMap("status", "error"));

		if (!partner.equals(AppId)) {
			return error;
		}

		String signSource = "source=" + source + "&trade_no=" + trade_no + "&amount=" + amount + "&partner=" + partner
				+ "&paydes=" + paydes + "&debug=" + debug + "&tborder=" + tborder + "&key=" + AppKey;

		String signCal = FsGameLoginUtils.md5up(signSource);
		try {
			if (signCal.equals(sign.toUpperCase())) {
				processOrder(trade_no, amount, false);
				return succ;
			}
		} catch (Exception e) {
			LoggerUtils.error(e);
			return error;
		}

		return error;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {
		String Uin = FsGameLoginUtils.getMapString(requestParams, "Uin", "");
		String SessionId = FsGameLoginUtils.getMapString(requestParams, "SessionId", "");

		Map<String, Object> m = new HashMap<String, Object>();
		m.put("session", SessionId);
		m.put("appid", AppId);
		SimpleHttpRequest shr = SimpleHttpRequest.createPost(loginCheckUrl.toString());
		shr.createFormBody(FsGameLoginUtils.mapToParamBody(m));
		String resultGet = shr.sendGetString();

		boolean result = false;
		if (resultGet == null || resultGet.equals("")) {
			result = false;
		} else {
			int resultValue = Integer.valueOf(resultGet);
			if (resultValue > 0 && resultGet.equals(Uin)) {
				result = true;
			}
		}

		return new PlatformRetData(result, null);
	}

}
