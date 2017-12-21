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
import com.google.gson.reflect.TypeToken;
import com.hadoit.game.common.framework.http.SimpleHttpRequest;
import com.hadoit.game.common.lang.DataUtils;
import com.hadoit.game.common.lang.codec.EncryptUtils;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

@SdkGroup({ SdkConst.CC_SDK_ANDROID_BAIDU })
@Component("sdkBaiduService")
public class SdkBaiduService extends SdkBaseService implements SdkService {

	private static final String appId = "9103113";
	private static final String appKey = "6DTqGAVlWMlXGOFjAgVVHzQK";
	private static final String appSecret = "Ze82GRPxrFhoESolwLTGXgmeGvAwN6FL";

	private static final String loginUrl = "http://querysdkapi.baidu.com/query/cploginstatequery";

	// private static final String payUrl =
	// "http://querysdkapi.91.com/CpOrderQuery.ashx";

	public static String decode(String s) {
		byte buf[];
		try {
			buf = EncryptUtils.base64Decode(s);
			s = new String(buf, "UTF-8");
			s = s.replaceAll("[\\n|\\r]", "");
			;
		} catch (Exception e) {
			return "";
		}
		return s;
	}

	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		LoggerUtils.info("baidu payCallback", requestParams);
		int resultCode = 1;
		String resultMsg = "";
		// 获取参数
		String appid = DataUtils.getMapString(requestParams, "AppID");
		String orderSerial = DataUtils.getMapString(requestParams, "OrderSerial");
		String cooperatorOrderSerial = DataUtils.getMapString(requestParams, "CooperatorOrderSerial");
		String content = DataUtils.getMapString(requestParams, "Content");
		try {
			String sign = DataUtils.getMapString(requestParams, "Sign");
			// 参数判断
			if (appid == null || orderSerial == null || cooperatorOrderSerial == null || content == null
					|| sign == null) {
				resultCode = 4;
				resultMsg = "参数错误";
			} else {
				// 先对接收到的通知进行验证
				StringBuilder strSign = new StringBuilder();
				strSign.append(appid);
				strSign.append(orderSerial);
				strSign.append(cooperatorOrderSerial);
				strSign.append(content);
				strSign.append(appSecret);
				// appID验证
				if (!appId.equals(appid)) {
					resultCode = 2; // appid无效
					resultMsg = "AppID无效";
				}
				// 签名验证
				if (!FsGameLoginUtils.md5up(strSign.toString()).equalsIgnoreCase(sign)) {
					resultCode = 3; // sign无效
					resultMsg = "Sign无效";
				}
			}
			// 正确的通知则执行业务处理
			if (resultCode == 1) {
				Map<String, String> rm = GsonFactory.getDefault().fromJson(
						new String(EncryptUtils.base64Decode(content), "utf-8"), new TypeToken<Map<String, String>>() {
						}.getType());
				LoggerUtils.info("baidu payCallback rm", rm);
				processOrder(cooperatorOrderSerial, rm.get("OrderMoney"), true);
				resultMsg = "成功";
			}
		} catch (Exception e) {
			LoggerUtils.error(e);
			resultCode = -1;
			resultMsg = "失败";
		}

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("AppID", appId);
		resultMap.put("ResultCode", resultCode + "");
		resultMap.put("ResultMsg", resultMsg);
		resultMap.put("Sign", FsGameLoginUtils.md5up(appId + resultCode + appKey));
		resultMap.put("Content", "");
		//
		// String result = "{\"AppID\":" + sdk.getAppID() + ",\"ResultCode\":" +
		// resultCode + ",\"ResultMsg\":\""
		// + resultMsg + "\",\"Sign\":\"" + sdk.md5(sdk.getAppID() + resultCode
		// + sdk.getAppKey())
		// + "\",\"Content\":\"\"}";
		// out.println(result);
		return GsonFactory.getDefault().toJson(resultMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {
		String Uin = FsGameLoginUtils.getMapString(requestParams, "Uin", "");
		String SessionId = FsGameLoginUtils.getMapString(requestParams, "SessionId", "");

		StringBuilder strSign = new StringBuilder();
		strSign.append(appId);
		strSign.append(SessionId);
		strSign.append(appSecret);
		String sign = FsGameLoginUtils.md5low(strSign.toString());

		StringBuilder getUrl = new StringBuilder(loginUrl);
		getUrl.append("?AppID=");
		getUrl.append(appId);
		getUrl.append("&AccessToken=");
		getUrl.append(SessionId);
		getUrl.append("&Sign=");
		getUrl.append(sign);
		SimpleHttpRequest shr = SimpleHttpRequest.createPost(getUrl.toString());
		String result = shr.sendGetString();
		Map<String, String> rm = GsonFactory.getDefault().fromJson(result, Map.class);
		// int AppId = FsGameLoginUtils.getMapInteger(rm, "AppID", 0);
		// int ResultCode = FsGameLoginUtils.getMapInteger(rm, "ResultCode", 0);
		// String ResultMsg = FsGameLoginUtils.getMapString(rm, "ResultMsg",
		// "");
		// String Sign = FsGameLoginUtils.getMapString(rm, "Sign", "");
		String Content = FsGameLoginUtils.getMapString(rm, "Content", "");

		boolean loginResult = false;

		try {
			Map<String, String> rmContent = GsonFactory.getDefault()
					.fromJson(new String(EncryptUtils.base64Decode(Content), "utf-8"), Map.class);
			long uid = FsGameLoginUtils.getMapLong(rmContent, "UID", 0L);
			if (Uin.equals(String.valueOf(uid))) {
				loginResult = true;
			}
		} catch (Exception e) {
			LoggerUtils.error(e);
			loginResult = false;
		}

		return new PlatformRetData(loginResult, null);
	}

}
