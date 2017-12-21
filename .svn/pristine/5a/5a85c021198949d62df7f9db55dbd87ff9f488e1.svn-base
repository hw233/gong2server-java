package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.net.URLEncoder;
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

@SdkGroup({ SdkConst.CC_SDK_ANDROID_AZHM })
@Component("sdkAzhmService")
public class SdkAzhmService extends SdkBaseService implements SdkService {

	private static final String AppId = "70600f83ea5e5957c770dd2f2eb2a40f";
	private static final String AppKey = "b39f52ec4638fc9a232c7d8fc6163f36";

	private static final String loginCheckUrl = "http://api.haimawan.com/index.php?m=api&a=validate_token";

	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		LoggerUtils.info("azhm payCallback", requestParams);

		String notify_time = FsGameLoginUtils.getMapString(requestParams, "notify_time", "");
		String appid = FsGameLoginUtils.getMapString(requestParams, "appid", "");
		String out_trade_no = FsGameLoginUtils.getMapString(requestParams, "out_trade_no", "");
		String total_fee = FsGameLoginUtils.getMapString(requestParams, "total_fee", "");
		String subject = FsGameLoginUtils.getMapString(requestParams, "subject", "");
		String body = FsGameLoginUtils.getMapString(requestParams, "body", "");
		String trade_status = FsGameLoginUtils.getMapString(requestParams, "trade_status", "");
		String sign = FsGameLoginUtils.getMapString(requestParams, "sign", "");

		try {
			String signSource = "notify_time=" + URLEncoder.encode(notify_time, "utf-8") + "&appid="
					+ URLEncoder.encode(appid, "utf-8") + "&out_trade_no=" + URLEncoder.encode(out_trade_no, "utf-8")
					+ "&total_fee=" + URLEncoder.encode(total_fee, "utf-8") + "&subject="
					+ URLEncoder.encode(subject, "utf-8") + "&body=" + URLEncoder.encode(body, "utf-8")
					+ "&trade_status=" + URLEncoder.encode(trade_status, "utf-8") + AppKey;
			String signCal = FsGameLoginUtils.md5up(signSource);

			if (signCal.equals(sign.toUpperCase()) && "1".equals(trade_status)) {
				processOrder(out_trade_no, total_fee, true);
				return "success";
			}
		} catch (Exception e) {
			LoggerUtils.error(e);
			return "fail";
		}

		return "fail";
	}

	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {
		String Uin = FsGameLoginUtils.getMapString(requestParams, "Uin", "");
		String SessionId = FsGameLoginUtils.getMapString(requestParams, "SessionId", "");

		boolean result = false;

		Map<String, Object> paramUrl = new HashMap<String, Object>();
		paramUrl.put("appid", AppId);
		paramUrl.put("t", SessionId);
		SimpleHttpRequest shr = SimpleHttpRequest.createPost(loginCheckUrl.toString());

		shr.createFormBody(FsGameLoginUtils.mapToParamBody(paramUrl));
		String resultGet = shr.sendGetString();

		if ("success".equals(resultGet)) {
			result = true;
		}
		return new PlatformRetData(result, null);
	}

}
