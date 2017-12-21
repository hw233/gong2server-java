package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.service.sdk.SdkGroup;
import com.gamejelly.game.gong2.login.service.sdk.SdkService;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.gamejelly.game.gong2.login.utils.SdkConst;
import com.hadoit.game.common.framework.http.SimpleHttpRequest;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

@SdkGroup({ SdkConst.CC_SDK_IOS_GP })
@Component("sdkGuopanService")
public class SdkGuopanService extends SdkBaseService implements SdkService {

	private String AppId = "103793";
	private String server_secret_key = "0E4KMJAFEYDFG126KVOWZ4UP3T1UKV8DE3PVHG6UO4FGMNBOHB95X1V9GVCSLV89";

	private String loginCheckUrl = "http://userapi.guopan.cn/gamesdk/verify/";
	
	@SuppressWarnings("unchecked")
	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {
		String Uin = FsGameLoginUtils.getMapString(requestParams, "Uin", "");
		String SessionId = FsGameLoginUtils.getMapString(requestParams, "SessionId", "");
		long time = System.currentTimeMillis();
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("game_uin", Uin);
		m.put("appid", AppId);
		m.put("token", SessionId);
		m.put("t", time);
		m.put("sign", FsGameLoginUtils.md5low(Uin + AppId + time + server_secret_key));
		SimpleHttpRequest shr = SimpleHttpRequest.createPost(loginCheckUrl.toString());
		shr.createFormBody(FsGameLoginUtils.mapToParamBody(m));
		String resultGet = shr.sendGetString();

		boolean result = false;
		if (resultGet == null || resultGet.equals("")) {
			result = false;
		} else {
			if (resultGet.equals("true")) {
				result = true;
			} else {
				result = false;
			}
		}

		return new PlatformRetData(result, null);
	}

	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		LoggerUtils.info("guopan payCallback", requestParams);
		String trade_no = FsGameLoginUtils.getMapString(requestParams, "trade_no", "");
		String serialNumber = FsGameLoginUtils.getMapString(requestParams, "serialNumber", "");
		String money = FsGameLoginUtils.getMapString(requestParams, "money", "");
		String status = FsGameLoginUtils.getMapString(requestParams, "status", "");
		String t = FsGameLoginUtils.getMapString(requestParams, "t", "");
		String sign = FsGameLoginUtils.getMapString(requestParams, "sign", "");
		String appid = FsGameLoginUtils.getMapString(requestParams, "appid", "");
		String item_id = FsGameLoginUtils.getMapString(requestParams, "item_id", "");
		String item_price = FsGameLoginUtils.getMapString(requestParams, "item_price", "");
		String item_count = FsGameLoginUtils.getMapString(requestParams, "item_count", "");
		String reserved = FsGameLoginUtils.getMapString(requestParams, "reserved", "");

		String signSource = serialNumber + money + status + t + server_secret_key;
		String signCal = FsGameLoginUtils.md5low(signSource);
		
		try {
			if (signCal.equals(sign.toLowerCase())) {
				processOrder(serialNumber, money, true);
				return "success";
			}
		} catch (Exception e) {
			LoggerUtils.error(e);
			return "fail";
		}

		return "fail";
	}

}
