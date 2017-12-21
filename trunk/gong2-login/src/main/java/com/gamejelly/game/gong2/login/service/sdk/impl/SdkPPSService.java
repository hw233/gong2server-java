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
import com.hadoit.game.common.lang.DataUtils;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

@SdkGroup({ SdkConst.CC_SDK_ANDROID_PPS })
@Component("sdkPPSService")
public class SdkPPSService extends SdkBaseService implements SdkService {

	private String gameId = "744";
	private String loginKey = "74974bf301ff7e270d0e1e6860735f38";
	private String payKey = "FSLLsfg1ZHVbqav752(Ppsgames744";

	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		LoggerUtils.info("pps payCallback", requestParams);

		String user_id = DataUtils.getMapString(requestParams, "user_id", "");
		String role_id = DataUtils.getMapString(requestParams, "role_id", "");
		String order_id = DataUtils.getMapString(requestParams, "order_id", "");
		String money = DataUtils.getMapString(requestParams, "money", "");
		String time = DataUtils.getMapString(requestParams, "time", "");
		String userData = DataUtils.getMapString(requestParams, "userData", "");
		String sign = DataUtils.getMapString(requestParams, "sign", "");

		int result = -6;
		String message = "fail";
		try {
			String mySign = user_id + role_id + order_id + money + time + payKey;
			mySign = FsGameLoginUtils.md5low(mySign);

			LoggerUtils.info("pps mySigh", mySign);

			if (mySign.equalsIgnoreCase(sign)) {
				processOrder(userData, money, true);
				result = 0;
				message = "success";
			} else {
				result = -1;
				message = "fail:sign error";
			}
		} catch (Exception e) {
			LoggerUtils.error(e);
		}
		Map<String, String> rm = new HashMap<String, String>();
		rm.put("result", result + "");
		rm.put(message, message);
		return GsonFactory.getDefault().toJson(rm);
	}

	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {
		String uin = FsGameLoginUtils.getMapString(requestParams, "Uin", "");
		int timestamp = FsGameLoginUtils.getMapInteger(requestParams, "Timestamp", 0);
		String sign = FsGameLoginUtils.getMapString(requestParams, "Sign", "");
		String sign2 = FsGameLoginUtils.md5low(uin + "&" + String.valueOf(timestamp) + "&" + loginKey);

		boolean result = false;
		if (sign != null && sign.equals(sign2)) {
			result = true;
		}

		return new PlatformRetData(result, null);
	}

}
