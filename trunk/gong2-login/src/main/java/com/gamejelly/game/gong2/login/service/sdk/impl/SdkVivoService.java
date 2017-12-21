package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.service.sdk.SdkGroup;
import com.gamejelly.game.gong2.login.service.sdk.SdkService;
import com.gamejelly.game.gong2.login.service.sdk.impl.vivo.VivoSignUtils;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.gamejelly.game.gong2.login.utils.SdkConst;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.hadoit.game.common.framework.http.SimpleHttpRequest;
import com.hadoit.game.common.lang.DataUtils;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

@SdkGroup({ SdkConst.CC_SDK_ANDROID_VIVO })
@Component("sdkVivoService")
public class SdkVivoService extends SdkBaseService implements SdkService {

	private String cpID = "fdd6375f09bf6e17009d";
	private String appKey = "946b7ee8c0bb5af4d64618d297f7f4df";
	private String appID = "a2ffb4a985798fcc4caee9b8f3fb684e";

	private String tokenCheckUrl = "https://usrsys.vivo.com.cn/sdk/user/auth.do";
	private String getVivoOrderUrl = "https://pay.vivo.com.cn/vcoin/trade";

	@SuppressWarnings("unchecked")
	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		LoggerUtils.info("vivo payCallback", requestParams);

		Map vParams = new HashMap(requestParams);
		vParams.remove("request");
		vParams.remove("response");
		vParams.remove("sdkId");
		String cpOrderNumber = DataUtils.getMapString(requestParams, "cpOrderNumber");
		int httpCode = 400;
		try {
			if (VivoSignUtils.verifySignature((Map<String, String>) vParams, appKey)) {
				processOrder(cpOrderNumber, FsGameLoginUtils.getMapString(requestParams, "orderAmount", ""), false);
				httpCode = 200;
			}
		} catch (Exception e) {
			LoggerUtils.error(e);
			httpCode = 400;
		}
		((HttpServletResponse) requestParams.get("response")).setStatus(httpCode);
		return "success";
	}

	@SuppressWarnings("unchecked")
	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {
		String Uin = FsGameLoginUtils.getMapString(requestParams, "Uin", "");
		String SessionId = FsGameLoginUtils.getMapString(requestParams, "SessionId", "");

		Map<String, Object> m = new HashMap<String, Object>();
		m.put("authtoken", SessionId);
		SimpleHttpRequest shr = SimpleHttpRequest.createPost(tokenCheckUrl.toString());
		shr.createFormBody(FsGameLoginUtils.mapToParamBody(m));
		String resultGet = shr.sendGetString();
		LoggerUtils.info("vivo loginAuth resultGet =", resultGet);
		boolean result = false;
		if (resultGet == null || resultGet.equals("")) {
			result = false;
		} else {
			Map<String, Object> rm = GsonFactory.getDefault().fromJson(resultGet, Map.class);

			int retcode = FsGameLoginUtils.getMapInteger(rm, "retcode", 0);
			if (retcode == 0) {
				result = true;
			}
		}

		return new PlatformRetData(result, null);
	}

	@Override
	public PlatformRetData handleSdkData(Map<String, Object> requestParams) {
		LoggerUtils.info("vivo handleSdkData", requestParams);
		String orderId = FsGameLoginUtils.getMapString(requestParams, "orderId", "");
		String notifyUrl = FsGameLoginUtils.getMapString(requestParams, "notifyUrl", "");
		String orderAmount = FsGameLoginUtils.getMapString(requestParams, "orderAmount", "");
		String orderTitle = FsGameLoginUtils.getMapString(requestParams, "orderTitle", "");
		String orderDesc = FsGameLoginUtils.getMapString(requestParams, "orderDesc", "");

		Map<String, String> m = new HashMap<String, String>();
		m.put("version", "1.0.0");
		m.put("cpId", cpID);
		m.put("appId", appID);
		m.put("cpOrderNumber", orderId);
		m.put("notifyUrl", notifyUrl);
		m.put("extInfo", "extInfo");

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = formatter.format(new Date());
		m.put("orderTime", date);

		DecimalFormat df = new DecimalFormat("0"); // 保留2位小数
		orderAmount = df.format(Double.valueOf(orderAmount));
		m.put("orderAmount", orderAmount);

		m.put("orderTitle", orderTitle);
		m.put("orderDesc", orderDesc);

		String sign = VivoSignUtils.getVivoSign(m, appKey);

		m.put("signMethod", "MD5");
		m.put("signature", sign);

		Map<String, Object> m2 = new HashMap<String, Object>();
		for (String key : m.keySet()) {
			String value = m.get(key);
			m2.put(key, value);
		}

		SimpleHttpRequest shr = SimpleHttpRequest.createPost(getVivoOrderUrl.toString());
		shr.createFormBody(FsGameLoginUtils.mapToParamBody(m2));
		String resultGet = shr.sendGetString();
		Map<String, Object> rm = GsonFactory.getDefault().fromJson(resultGet, new TypeToken<Map<String, Object>>() {
		}.getType());
		// {respCode=200, signMethod=MD5,
		// accessKey=87c9450da016464a04e593b6f50dab80, orderAmount=1,
		// orderNumber=2017010917143841700013513992, respMsg=success,
		// signature=532012694813d26cf441a3a805285f5a}
		LoggerUtils.info("vivo handleSdkData  rm =", rm);
		int respCode = Double.valueOf(DataUtils.getMapString(rm, "respCode", "0")).intValue();
		if (respCode == 200) {
			String orderNumber = (String) rm.get("orderNumber");
			String accessKey = (String) rm.get("accessKey");
			JsonObject jobj = new JsonObject();
			jobj.addProperty("orderNumber", orderNumber);
			jobj.addProperty("accessKey", accessKey);
			jobj.addProperty("orderAmount", orderAmount);
			String returnValue = jobj.toString();
			return new PlatformRetData(true, returnValue);
		}

		return new PlatformRetData(true, "");
	}
}
