package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.net.URLEncoder;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.meta.PlatformPayRecord;
import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.service.sdk.SdkGroup;
import com.gamejelly.game.gong2.login.service.sdk.SdkService;
import com.gamejelly.game.gong2.login.service.sdk.impl.google.Security;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.gamejelly.game.gong2.login.utils.SdkConst;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hadoit.game.common.framework.http.SimpleHttpRequest;

@SdkGroup({ SdkConst.CC_SDK_IOS_TW, SdkConst.CC_SDK_IOS_TW_1, SdkConst.CC_SDK_IOS_TW_2, SdkConst.CC_SDK_ANDROID_TW,
		SdkConst.CC_SDK_IOS_TW_HD, SdkConst.CC_SDK_ANDROID_TW_HD })
@Component("sdkTwIosService")
public class SdkTwIosService extends SdkBaseService implements SdkService {

	@Autowired
	private SdkAppleService sdkAppleService;

	public static final String FB_AUTH_LOGIN_URL = "https://graph.facebook.com/oauth/access_token";
	public static final String FB_USERINFO_URL = "https://graph.facebook.com/me";
	public static final String FB_DEBUG_TOKEN = "https://graph.facebook.com/v2.9/debug_token?input_token=%s&access_token=%s";

	// AppId
	public static final String FB_APP_ID = "371971096519788";

	// AppSecret
	public static final String FB_APP_KEY = "ba3de98c0c77ce2addbb69a500bdd5a0";

	// 获取用户的那些信息
	public static final String FB_USER_FIELDS = "id,cover,email,gender,name,languages,timezone,third_party_id,updated_time";

	private String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgQRJ4YFig8fRrX6m/Z3HI9w1Hwm27DTOCQ+rl/tKLFMwHjUVqlKo6ZBVCfxwzRVra/Hgoeq7Zgyfit05JG4whvqJoYZ3RwXZ7lOhzQjhsL7lYK5WhayURxR5p3GSaD50bbfvOWHPy1Z+goKudaS6Hd6OsCh5wMxy6OUeoofGC6qMpzKE7I1Kkk3b9bb6pigVEuHlQaPfXQrlo+RpQJh7NVrMfRmXz2pQTRC9BoeZ+BB1BrEv88iMrJQvxLur3XCULy+De6aOEeiabCwEtFvjClxC8vl+E2fnwJ/dVd0VjaQeXsx8F7kTAtYrKEF2rbW1z5ZhpE21G0kTQYp6OBjZYQIDAQAB";

	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		LoggerUtils.info("skdtw payCallback", requestParams);
		String result = "fail";
		String inappPurchaseData = FsGameLoginUtils.getMapString(requestParams, "inappPurchaseData", "");
		String inappDataSignature = FsGameLoginUtils.getMapString(requestParams, "inappDataSignature", "");
		Gson mGson = new Gson();
		JsonObject json = mGson.fromJson(inappPurchaseData, JsonObject.class);
		String app_order_id = FsGameLoginUtils.getMapString(requestParams, "app_order_id", ""); // 游戏生成的订单号
		String order_id = ""; // google 返回的订单号
		if (json.has("orderId")) {
			order_id = json.get("orderId").getAsString();
		} else {
			order_id = json.get("purchaseToken").getAsString(); // 测试的时候没有orderId，用purchaseToken代替
		}
		PlatformPayRecord platformPayRecord = platformPayRecordService.getByOrderSn(app_order_id);
		platformPayRecordService.updateExtData(order_id, app_order_id);

		if (platformPayRecord.getExtData2().equals(json.get("developerPayload").getAsString())
				&& Security.verifyPurchase(base64EncodedPublicKey, inappPurchaseData, inappDataSignature)) {
			LoggerUtils.info("skdtw payCallback success", requestParams);
			processOrder(app_order_id, FsGameLoginUtils.getMapString(requestParams, "price", ""), false);
			result = "success";
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {
		int nativeAppleAuth = FsGameLoginUtils.getMapInteger(requestParams, "twAuthType", 1);
		if (nativeAppleAuth == 1) {
			// facebook登录模式
			String Uin = FsGameLoginUtils.getMapString(requestParams, "Uin", "");
			String SessionId = FsGameLoginUtils.getMapString(requestParams, "SessionId", "");

			String resultGet = "";
			boolean result = false;
			try {

				String strUrl = String.format(FB_DEBUG_TOKEN, SessionId,
						URLEncoder.encode(FB_APP_ID + "|" + FB_APP_KEY));
				SimpleHttpRequest shr = SimpleHttpRequest.createGet(strUrl.toString());
				// shr.createFormBody(FsGameLoginUtils.mapToParamBody(params));
				String resultGet1 = shr.sendGetString();
				Gson mGson = new Gson();
				JsonObject json = mGson.fromJson(resultGet1, JsonObject.class);
				JsonObject dataJson = json.get("data").getAsJsonObject();
				String app_id = dataJson.get("app_id").getAsString();
				String user_id = dataJson.get("user_id").getAsString();
				if (FB_APP_ID.equals(app_id) && Uin.equals(user_id)) {
					result = true;
				} else {
					result = false;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return new PlatformRetData(result, null);
		} else if (nativeAppleAuth == 2) {
			// 游客登录模式

		} else if (nativeAppleAuth == 3) {
			// gameCenter登录模式
			return sdkAppleService.loginAuth(requestParams);
		}
		return null;
	}

	@Override
	public PlatformRetData handleSdkData(Map<String, Object> requestParams) {
		return sdkAppleService.handleSdkData(requestParams);
	}

	@Override
	public Object addItemCallback(Map<String, Object> requestParams) {
		return sdkAppleService.addItemCallback(requestParams);
	}

	@Override
	public Object getUserCallback(Map<String, Object> requestParams) {
		return sdkAppleService.getUserCallback(requestParams);
	}

	@Override
	public PlatformRetData verifyOrder(Map<String, Object> requestParams) {
		String source = FsGameLoginUtils.getMapString(requestParams, "source", "");

		if ("google".equals(source)) {
			LoggerUtils.info("skdtw verifyOrder ", requestParams);
			String inappPurchaseData = FsGameLoginUtils.getMapString(requestParams, "purchaseData", "");
			String inappDataSignature = FsGameLoginUtils.getMapString(requestParams, "purchaseToken", "");
			LoggerUtils.info("map inappPurchaseData", inappPurchaseData);
			Gson mGson = new Gson();
			JsonObject json = mGson.fromJson(inappPurchaseData.toString(), JsonObject.class);
			LoggerUtils.info("json data", json.toString());
			String app_order_id = FsGameLoginUtils.getMapString(requestParams, "app_order_id", ""); // 游戏生成的订单号
			String order_id = ""; // google 返回的订单号
			if (json.has("orderId")) {
				order_id = json.get("orderId").getAsString();
			} else {
				order_id = json.get("purchaseToken").getAsString(); // 测试的时候没有orderId，用purchaseToken代替
			}
			LoggerUtils.info("order_id", order_id);
			PlatformPayRecord platformPayRecord = platformPayRecordService.getByOrderSn(app_order_id);
			platformPayRecordService.updateExtData(order_id, app_order_id);
			LoggerUtils.info("developerPayload", json.get("developerPayload").getAsString(),
					platformPayRecord.getExtData2());
			if (platformPayRecord.getExtData2().equals(json.get("developerPayload").getAsString())
					&& Security.verifyPurchase(base64EncodedPublicKey, inappPurchaseData, inappDataSignature)) {
				LoggerUtils.info("skdtw payCallback success", requestParams);
				processOrder(app_order_id, FsGameLoginUtils.getMapString(requestParams, "price", ""), false);
				return new PlatformRetData(true, 0);
			}

			return new PlatformRetData(false, -1);
		} else {
			return sdkAppleService.verifyOrder(requestParams);
		}
	}

}
