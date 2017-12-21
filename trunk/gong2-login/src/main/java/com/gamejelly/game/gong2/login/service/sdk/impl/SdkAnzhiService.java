package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.service.sdk.SdkGroup;
import com.gamejelly.game.gong2.login.service.sdk.SdkService;
import com.gamejelly.game.gong2.login.service.sdk.impl.anzhi.Base64;
import com.gamejelly.game.gong2.login.service.sdk.impl.anzhi.Des3Util;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.gamejelly.game.gong2.login.utils.SdkConst;
import com.google.gson.reflect.TypeToken;
import com.hadoit.game.common.framework.http.SimpleHttpRequest;
import com.hadoit.game.common.lang.DataUtils;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

@SdkGroup({ SdkConst.CC_SDK_ANDROID_ANZHI })
@Component("sdkAnzhiService")
public class SdkAnzhiService extends SdkBaseService implements SdkService {

	private final String AppKey = "1418013712H2OqnEWL5ctKCHxH0EBq";
	private final String AppSecret = "Vr5EC4A036wtyohOe85uE8et";

	private String loginCheckUrl = "http://user.anzhi.com/web/api/sdk/third/1/queryislogin";

	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		String data = DataUtils.getMapString(requestParams, "data", "");
		data = Des3Util.decrypt(data, AppSecret);
		LoggerUtils.info("anzhi payCallback", data);
		try {
			Map<String, String> rm = GsonFactory.getDefault().fromJson(data, new TypeToken<Map<String, String>>() {
			}.getType());
			processOrder(rm.get("cpInfo"), rm.get("orderAmount"), false);
			return "success";
		} catch (Exception e) {
			LoggerUtils.error(e);
		}
		return "fail";
	}

	@SuppressWarnings("unchecked")
	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {
		String Uin = FsGameLoginUtils.getMapString(requestParams, "Uin", "");
		String SessionId = FsGameLoginUtils.getMapString(requestParams, "SessionId", "");

		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");

		Map<String, Object> m = new HashMap<String, Object>();
		m.put("time", df.format(date));
		m.put("appkey", AppKey);
		m.put("sid", SessionId);
		m.put("sign", Base64.encodeToString(AppKey + SessionId + AppSecret));
		SimpleHttpRequest shr = SimpleHttpRequest.createPost(loginCheckUrl.toString());
		shr.createFormBody(FsGameLoginUtils.mapToParamBody(m));
		String resultGet = shr.sendGetString();

		boolean result = false;
		if (resultGet == null || resultGet.equals("")) {
			result = false;
		} else {
			Map<String, Object> rm = GsonFactory.getDefault().fromJson(resultGet, Map.class);
			int code = Double.valueOf(String.valueOf(rm.get("sc"))).intValue();
			if (code == 1) {
				String tem = Base64.decode((String) rm.get("msg"), "UTF-8");
				Map<String, Object> rm2 = GsonFactory.getDefault().fromJson(tem, Map.class);
				if (Uin.equals(rm2.get("uid"))) {
					result = true;
				}
			} else {
				result = false;
			}
		}

		return new PlatformRetData(result, null);
	}

}
