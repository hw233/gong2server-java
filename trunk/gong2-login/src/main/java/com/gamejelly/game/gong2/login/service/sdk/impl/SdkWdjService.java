package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.service.sdk.SdkGroup;
import com.gamejelly.game.gong2.login.service.sdk.SdkService;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.gamejelly.game.gong2.login.utils.SdkConst;
import com.hadoit.game.common.framework.http.SimpleHttpRequest;
import com.hadoit.game.common.lang.codec.EncryptUtils;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

@SdkGroup({ SdkConst.CC_SDK_ANDROID_WDJ })
@Component("sdkWdjService")
public class SdkWdjService extends SdkBaseService implements SdkService {

	private String appKeyId = "100019893";
	private String SecretKey = "97b832eb3481c8145eb2e821ac4addad";

	private String tokenCheckUrl = "https://pay.wandoujia.com/api/uid/check";

	@SuppressWarnings("unchecked")
	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		LoggerUtils.info("wdj payCallback", requestParams);
		String content = FsGameLoginUtils.getMapString(requestParams, "content", "");
		String signType = FsGameLoginUtils.getMapString(requestParams, "signType", ""); // 值固定
																						// RSA
		String sign = FsGameLoginUtils.getMapString(requestParams, "sign", "");

		boolean check = false;
		check = SdkWdjService.doRsaCheck(content, sign);
		if (!check) {
			return "fail";
		}

		Map<String, String> rm = GsonFactory.getDefault().fromJson(content, Map.class);
		String timeStamp = FsGameLoginUtils.getMapString(rm, "timeStamp", "");
		String wjdOrderId = FsGameLoginUtils.getMapString(rm, "orderId", ""); // 豌豆荚订单ID
		String money = FsGameLoginUtils.getMapString(rm, "money", ""); // 分
		String chargeType = FsGameLoginUtils.getMapString(rm, "chargeType", "");
		String appKeyId = FsGameLoginUtils.getMapString(rm, "appKeyId", "");
		String buyerId = FsGameLoginUtils.getMapString(rm, "buyerId", "");
		String selfOrderId = FsGameLoginUtils.getMapString(rm, "out_trade_no", ""); // 自定义游戏ID
		String cardNo = FsGameLoginUtils.getMapString(rm, "cardNo", "");

		try {
			processOrder(selfOrderId, money, false);
			return "success";
		} catch (Exception e) {
			LoggerUtils.error(e);
			return "fail";
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {
		String Uin = FsGameLoginUtils.getMapString(requestParams, "Uin", "");
		String SessionId = FsGameLoginUtils.getMapString(requestParams, "SessionId", "");

		StringBuilder getUrl = new StringBuilder(tokenCheckUrl);
		getUrl.append("?uid=");
		getUrl.append(Uin);
		getUrl.append("&token=");
		getUrl.append(SessionId);
		getUrl.append("&appkey_id=");
		getUrl.append(appKeyId);

		SimpleHttpRequest shr = SimpleHttpRequest.createGet(getUrl.toString());
		String resultGet = shr.sendGetString();
		boolean result = false;
		if (resultGet != null && resultGet.equals("true")) {
			result = true;
		} else {
			result = false;
		}

		return new PlatformRetData(result, null);
	}

	public static final String WandouPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCd95FnJFhPinpNiE/h4VA6bU1rzRa5+a25BxsnFX8TzquWxqDCoe4xG6QKXMXuKvV57tTRpzRo2jeto40eHKClzEgjx9lTYVb2RFHHFWio/YGTfnqIPTVpi7d7uHY+0FZ0lYL5LlW4E2+CQMxFOPRwfqGzMjs1SDlH7lVrLEVy6QIDAQAB";
	public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

	public static boolean doRsaCheck(String content, String sign) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			byte[] encodedKey = EncryptUtils.base64Decode(WandouPublicKey);
			PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

			java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

			signature.initVerify(pubKey);
			signature.update(content.getBytes("utf-8"));

			boolean bverify = signature.verify(EncryptUtils.base64Decode(sign));
			return bverify;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

}
