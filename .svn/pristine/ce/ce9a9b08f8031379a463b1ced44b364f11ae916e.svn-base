package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.service.sdk.SdkGroup;
import com.gamejelly.game.gong2.login.service.sdk.SdkService;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.gamejelly.game.gong2.login.utils.SdkConst;

@SdkGroup({ SdkConst.CC_SDK_ANDROID_OUWAN })
@Component("sdkOuwanService")
public class SdkOuwanService extends SdkBaseService implements SdkService {

	private static final String AppKey = "fa87be530bffd38b";

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
			content.append(key + "=" + StringUtils.defaultString(params.get(key)));
		}
		return content.toString();
	}

	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		LoggerUtils.info("ouwan payCallback", requestParams);

		String serverId = FsGameLoginUtils.getMapString(requestParams, "serverId", "");
		String callbackInfo = FsGameLoginUtils.getMapString(requestParams, "callbackInfo", "");
		String openId = FsGameLoginUtils.getMapString(requestParams, "openId", "");
		String orderId = FsGameLoginUtils.getMapString(requestParams, "orderId", "");
		String orderStatus = FsGameLoginUtils.getMapString(requestParams, "orderStatus", "");
		String payType = FsGameLoginUtils.getMapString(requestParams, "payType", "");
		String amount = FsGameLoginUtils.getMapString(requestParams, "amount", "");
		String remark = FsGameLoginUtils.getMapString(requestParams, "remark", "");
		String sign = FsGameLoginUtils.getMapString(requestParams, "sign", "");

		Map<String, String> transParams = new HashMap<String, String>();
		transParams.put("serverId", serverId);
		transParams.put("callbackInfo", callbackInfo);
		transParams.put("openId", openId);
		transParams.put("orderId", orderId);
		transParams.put("orderStatus", orderStatus);
		transParams.put("payType", payType);
		transParams.put("amount", amount);
		transParams.put("remark", remark);

		try {
			String signSource = getSignData(transParams) + AppKey;
			String signCal = FsGameLoginUtils.md5up(signSource);
			if (signCal.equals(sign.toUpperCase()) && "1".equals(orderStatus)) {
				processOrder(callbackInfo, amount, true);
				return "success";
			} else {
				LoggerUtils.info("ouwan 验签失败", signSource, signCal, sign);
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
		String timestamp = FsGameLoginUtils.getMapString(requestParams, "timestamp", "");

		boolean result = false;
		if (Math.abs(System.currentTimeMillis() / 1000 - Long.parseLong(timestamp)) <= 600) {
			if (FsGameLoginUtils.md5up(Uin + "&" + timestamp + "&" + AppKey).equals(SessionId.toUpperCase())) {
				result = true;
			}
		}
		return new PlatformRetData(result, null);
	}

}
