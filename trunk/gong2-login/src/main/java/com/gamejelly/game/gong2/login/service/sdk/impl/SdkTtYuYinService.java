package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.meta.PlatformPayRecord;
import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.service.sdk.SdkGroup;
import com.gamejelly.game.gong2.login.service.sdk.SdkService;
import com.gamejelly.game.gong2.login.service.sdk.impl.TTYuYin.HttpUtil;
import com.gamejelly.game.gong2.login.service.sdk.impl.TTYuYin.SignUtils;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.gamejelly.game.gong2.login.utils.SdkConst;
import com.hadoit.game.common.framework.http.SimpleHttpRequest;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

@SdkGroup({ SdkConst.CC_SDK_ANROID_TTYUYIN })
@Component("sdkTtYuYinService")
public class SdkTtYuYinService extends SdkBaseService implements SdkService {

	private static String GAME_ID = "201707201";
	private static String API_KEY = "21792fc6e1f791d666205a6fe5f28598";
	private static String PAY_KEY = "d4f02012b0780d87367e097227e670d4";
//	private static String BASE_URL = "http://123.59.74.32/server/rest/user/loginstatus.view";
	private static String BASE_URL = "http://usdk.52tt.com/server/rest/user/loginstatus.view";

	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		LoggerUtils.info("ttYuYin payCallback", requestParams);

		HttpServletRequest request = (HttpServletRequest) requestParams.get("request");
		if (request == null) {
			return null;
		}
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String ln;
		StringBuilder stringBuilder = new StringBuilder();
		try {
			while ((ln = in.readLine()) != null) {
				stringBuilder.append(ln);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String urlData = "";
		try {
			urlData = URLDecoder.decode(stringBuilder.toString(), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String ttsign = request.getHeader("sign");
		LoggerUtils.info("ttYuYin payCallback urlData=", urlData);
		LoggerUtils.info("ttYuYin payCallback ttsign=", ttsign);
		double amount = 0.0f;
		String cpOrderId = "";
		String payResult = "";
		Map<String, Object> rmResult = GsonFactory.getDefault().fromJson(urlData, Map.class);
		if (rmResult != null) {
			cpOrderId = (String) rmResult.get("cpOrderId");
			amount = (Double) rmResult.get("payFee");
			payResult = (String) rmResult.get("payResult");
		}

		String httpStr = null;
		Map<String, Object> head = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();

		String key = PAY_KEY;
		String sign = SignUtils.sign(urlData, key);

		map.put("result", "-1");
		map.put("message", "验签失败");
		head.put("head", map);

		if (sign.equals(ttsign) && StringUtils.equals("1", payResult)) {
			PlatformPayRecord ppr = platformPayRecordService.getByOrderSn(cpOrderId);
			if (ppr != null) {
				map.put("result", "0");
				map.put("message", "验签成功");
				head.put("head", map);
				httpStr = GsonFactory.getDefault().toJson(head);
				processOrder(cpOrderId, String.valueOf(amount), true);
			} else {
				map.put("message", "cp订单不存在");
				head.put("head", map);
				httpStr = GsonFactory.getDefault().toJson(head);
			}
		} else {
			httpStr = GsonFactory.getDefault().toJson(head);
		}

		LoggerUtils.info("pyWan payCallback retJson=", httpStr);
		return httpStr;
	}

	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {
		LoggerUtils.info("ttYuYin loginAuth", requestParams);

		String uid = FsGameLoginUtils.getMapString(requestParams, "Uin", "");
		String session = FsGameLoginUtils.getMapString(requestParams, "SessionId", "");
		Map<String, Object> urldata = new HashMap<String, Object>();
		urldata.put("gameId", GAME_ID);
		urldata.put("uid", uid);

		String jsonBody = GsonFactory.getDefault().toJson(urldata);
		String sign = SignUtils.sign(jsonBody, API_KEY);

		LoggerUtils.info("ttYuYin loginAuth jsonBody", jsonBody);
		LoggerUtils.info("ttYuYin loginAuth sign", sign);

		Map<String, Object> header = new HashMap<String, Object>();
		header.put("sid", session);
		header.put("sign", sign);

		SimpleHttpRequest shr = SimpleHttpRequest.createPost(BASE_URL);
		shr.addHeader("sid", session);
		shr.addHeader("sign", sign);
		shr.createBody(jsonBody);
		String resultGet = shr.sendGetString();
		LoggerUtils.info("ttYuYin loginAuth resultGet", resultGet);

		String httpStr = "";
		try {
			httpStr = HttpUtil.doPost(BASE_URL, jsonBody, header);
			LoggerUtils.info("ttYuYin loginAuth httpStr", httpStr);

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		boolean ret = false;
		Map<String, Object> rmResult = GsonFactory.getDefault().fromJson(httpStr, Map.class);
		if (rmResult != null) {
			Map<String, Object> headResult = (Map<String, Object>) rmResult.get("head");
			if (headResult != null && headResult.containsKey("message")) {
				String message = (String) headResult.get("message");
				if (StringUtils.equals(message, "SUCCESS")) {
					ret = true;
					requestParams.put("name", uid);
				}
			}
		}

		return new PlatformRetData(ret, null);
	}

	public static void orderCheck() {

	}

	public static void loginCheck() {
		String session = "TT3RDTK_TT_aaaliYwMqHrM08dCi2D1v";
		String jsonBody = "";
		String sign = "";

		SimpleHttpRequest shr = SimpleHttpRequest.createGet(BASE_URL);
		shr.addHeader("sid", session);
		shr.addHeader("sign", sign);
		shr.createBody(jsonBody);
		String resultGet = shr.sendGetString();
		LoggerUtils.info("ttYuYin loginAuth resultGet", resultGet);

	}

	public static void main(String[] agrs) {

	}

}