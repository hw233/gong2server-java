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
import com.hadoit.game.common.lang.DataUtils;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

@SdkGroup({ SdkConst.CC_SDK_ANDROID_SY37 })
@Component("sdkSY37Service")
public class SdkSY37Service extends SdkBaseService implements SdkService {

	private String pid = "1";

	private String gid = "1001184";

	private String appKey = "qpY7s.emNd0rfua6,bQhZPvCtnkUAz9c";

	private String payKey = "e5BX2WCS0zJLQ?dU6:G9VONxjMZstKvc";

	private String tokenCheckUrl = "http://vt.api.m.37.com/verify/token/";

	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		LoggerUtils.info("sy37 payCallback", requestParams);

		String time = DataUtils.getMapString(requestParams, "time", "");
		String oid = DataUtils.getMapString(requestParams, "oid", "");
		String doid = DataUtils.getMapString(requestParams, "doid", "");
		String dsid = DataUtils.getMapString(requestParams, "dsid", "");
		String uid = DataUtils.getMapString(requestParams, "uid", "");
		String money = DataUtils.getMapString(requestParams, "money", "");
		String coin = DataUtils.getMapString(requestParams, "coin", "");
		String sign = DataUtils.getMapString(requestParams, "sign", "");

		String mySign = FsGameLoginUtils.md5low(time + payKey + oid + doid + dsid + uid + money + coin);
		int state = 0;
		try {
			LoggerUtils.info("sy37 payCallback sign", sign, ", mySign", mySign);
			if (mySign.equalsIgnoreCase(sign)) {
				processOrder(doid, money, true);
				state = 1;
			}
		} catch (Exception e) {
			LoggerUtils.error(e);
			state = 0;
		}

		Map<String, String> result = new HashMap<String, String>();
		result.put("state", state + "");
		result.put("data", "");
		result.put("msg", "");
		return GsonFactory.getDefault().toJson(result);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {
		LoggerUtils.info("SY37 loginAuth", requestParams);

		String Uin = FsGameLoginUtils.getMapString(requestParams, "Uin", "");
		String token = FsGameLoginUtils.getMapString(requestParams, "SessionId", "");
		long time = System.currentTimeMillis() / 1000;
		String sign = FsGameLoginUtils.md5low(gid + time + appKey);

		StringBuilder getUrl = new StringBuilder(tokenCheckUrl);
		getUrl.append("?pid=");
		getUrl.append(pid);
		getUrl.append("&gid=");
		getUrl.append(gid);
		getUrl.append("&time=");
		getUrl.append(time);
		getUrl.append("&sign=");
		getUrl.append(sign);
		getUrl.append("&token=");
		getUrl.append(token);

		SimpleHttpRequest shr = SimpleHttpRequest.createPost(getUrl.toString());
		String resultGet = shr.sendGetString();
		LoggerUtils.info("SY37 loginAuth back", resultGet);
		Map<String, Object> resultMap = (Map<String, Object>) GsonFactory.getDefault().fromJson(resultGet, Map.class);
		// SY37 loginAuth back
		// {"state":1,"data":{"uid":"375440511","disname":"37sy7kbcoi2"},"msg":"\u6210\u529f"}

		boolean result = false;
		int state = Double.valueOf(resultMap.get("state").toString()).intValue();
		if (state == 1) {
			Object dataObj = resultMap.get("data");
			if (dataObj instanceof Map) {
				Map<String, Object> dataMap = (Map<String, Object>) dataObj;
				String retUid = Double.valueOf(dataMap.get("uid").toString()).intValue() + "";
				if (Uin.equalsIgnoreCase(retUid)) {
					result = true;
				}
			}
		}
		return new PlatformRetData(result, null);
	}

}
