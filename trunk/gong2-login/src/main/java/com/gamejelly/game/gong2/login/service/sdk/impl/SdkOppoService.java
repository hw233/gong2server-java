package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.service.sdk.SdkGroup;
import com.gamejelly.game.gong2.login.service.sdk.SdkService;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.gamejelly.game.gong2.login.utils.SdkConst;
import com.hadoit.game.common.framework.http.SimpleHttpRequest;
import com.hadoit.game.common.lang.DataUtils;
import com.hadoit.game.common.lang.codec.EncryptUtils;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

@SdkGroup({ SdkConst.CC_SDK_ANDROID_OPPO })
@Component("sdkOppoService")
public class SdkOppoService extends SdkBaseService implements SdkService {

	private String AppId = "3463370";
	private String AppKey = "btnfFs7yAHW04W8kOgowcwcos";
	private String AppSecret = "f0F8b483DFDc1ff5897D85cc6e2835CE";
	private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCmreYIkPwVovKR8rLHWlFVw7YDfm9uQOJKL89Smt6ypXGVdrAKKl0wNYc3/jecAoPi2ylChfa2iRu5gunJyNmpWZzlCNRIau55fxGW0XEu553IiprOZcaw5OuYGlf60ga8QT6qToP0/dpiL/ZbmNUO9kUhosIjEu22uFgR+5cYyQIDAQAB";
	private static final String RESULT_STR = "result=%s&resultMsg=%s";
	private static final String AUTH_URL = "http://i.open.game.oppomobile.com/gameopen/user/fileIdInfo";

	private static final String CALLBACK_OK = "OK";
	private static final String CALLBACK_FAIL = "FAIL";

	public static final String OAUTH_CONSUMER_KEY = "oauthConsumerKey";
	public static final String OAUTH_TOKEN = "oauthToken";
	public static final String OAUTH_SIGNATURE_METHOD = "oauthSignatureMethod";
	public static final String OAUTH_SIGNATURE = "oauthSignature";
	public static final String OAUTH_TIMESTAMP = "oauthTimestamp";
	public static final String OAUTH_NONCE = "oauthNonce";
	public static final String OAUTH_VERSION = "oauthVersion";
	public static final String CONST_SIGNATURE_METHOD = "HMAC-SHA1";
	public static final String CONST_OAUTH_VERSION = "1.0";

	private String getBaseString(Map<String, Object> requestParams) {
		StringBuilder sb = new StringBuilder();
		sb.append("notifyId=").append(DataUtils.getMapString(requestParams, "notifyId"));
		sb.append("&partnerOrder=").append(DataUtils.getMapString(requestParams, "partnerOrder"));
		sb.append("&productName=").append(DataUtils.getMapString(requestParams, "productName"));
		sb.append("&productDesc=").append(DataUtils.getMapString(requestParams, "productDesc"));
		sb.append("&price=").append(DataUtils.getMapString(requestParams, "price"));
		sb.append("&count=").append(DataUtils.getMapString(requestParams, "count"));
		sb.append("&attach=").append(DataUtils.getMapString(requestParams, "attach"));
		return sb.toString();
	}

	public boolean doCheck(String content, String sign, String publicKey) throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		byte[] encodedKey = EncryptUtils.base64Decode(publicKey);
		PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
		java.security.Signature signature = java.security.Signature.getInstance("SHA1WithRSA");
		signature.initVerify(pubKey);
		signature.update(content.getBytes("UTF-8"));
		boolean bverify = signature.verify(EncryptUtils.base64Decode(sign));
		return bverify;
	}

	private String genRetStr(String code, String msg) {
		try {
			return String.format(RESULT_STR, code, URLEncoder.encode(msg, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			LoggerUtils.error(e);
			return String.format(RESULT_STR, code, msg);
		}
	}

	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		LoggerUtils.info("oppo payCallback", requestParams);
		String baseString = getBaseString(requestParams);
		boolean check = false;
		String result = CALLBACK_FAIL;
		String resultMsg = "验签失败";
		try {
			check = doCheck(baseString, DataUtils.getMapString(requestParams, "sign"), PUBLIC_KEY);
			if (check) {
				processOrder(DataUtils.getMapString(requestParams, "partnerOrder"),
						DataUtils.getMapString(requestParams, "price", ""), false);
				result = CALLBACK_OK;
				resultMsg = "回调成功";
			}
		} catch (Exception ex) {
			LoggerUtils.error(ex);
			result = CALLBACK_FAIL;
			resultMsg = "系统异常";
		}
		return genRetStr(result, resultMsg);
	}

	private String generateBaseString(String token, String timestamp, String nonce) {
		StringBuilder sb = new StringBuilder();
		try {
			sb.append(OAUTH_CONSUMER_KEY).append("=").append(URLEncoder.encode(AppKey, "UTF-8")).append("&")
					.append(OAUTH_TOKEN).append("=").append(URLEncoder.encode(token, "UTF-8")).append("&")
					.append(OAUTH_SIGNATURE_METHOD).append("=")
					.append(URLEncoder.encode(CONST_SIGNATURE_METHOD, "UTF-8")).append("&").append(OAUTH_TIMESTAMP)
					.append("=").append(URLEncoder.encode(timestamp, "UTF-8")).append("&").append(OAUTH_NONCE)
					.append("=").append(URLEncoder.encode(nonce, "UTF-8")).append("&").append(OAUTH_VERSION)
					.append("=").append(URLEncoder.encode(CONST_OAUTH_VERSION, "UTF-8")).append("&");
		} catch (UnsupportedEncodingException e1) { // TODO Auto-generated catch
													// block
													// e1.printStackTrace();
		}
		return sb.toString();
	}

	private String generateSign(String baseStr) {
		byte[] byteHMAC = null;
		try {
			Mac mac = Mac.getInstance("HmacSHA1");
			SecretKeySpec spec = null;
			String oauthSignatureKey = AppSecret + "&";
			spec = new SecretKeySpec(oauthSignatureKey.getBytes(), "HmacSHA1");
			mac.init(spec);
			byteHMAC = mac.doFinal(baseStr.getBytes());
			return URLEncoder.encode(String.valueOf(base64Encode(byteHMAC)), "UTF-8");
		} catch (Exception e) {
			LoggerUtils.error(e);
		}
		return "";
	}

	private char[] base64Encode(byte[] data) {
		final char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray();
		char[] out = new char[((data.length + 2) / 3) * 4];
		for (int i = 0, index = 0; i < data.length; i += 3, index += 4) {
			boolean quad = false;
			boolean trip = false;
			int val = (0xFF & (int) data[i]);
			val <<= 8;
			if ((i + 1) < data.length) {
				val |= (0xFF & (int) data[i + 1]);
				trip = true;
			}
			val <<= 8;
			if ((i + 2) < data.length) {
				val |= (0xFF & (int) data[i + 2]);
				quad = true;
			}
			out[index + 3] = alphabet[(quad ? (val & 0x3F) : 64)];
			val >>= 6;
			out[index + 2] = alphabet[(trip ? (val & 0x3F) : 64)];
			val >>= 6;
			out[index + 1] = alphabet[val & 0x3F];
			val >>= 6;
			out[index + 0] = alphabet[val & 0x3F];
		}
		return out;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {
		String Uin = FsGameLoginUtils.getMapString(requestParams, "Uin", "");
		String SessionId = FsGameLoginUtils.getMapString(requestParams, "SessionId", "");

		boolean result = false;
		try {
			long currSecTime = System.currentTimeMillis() / 1000;
			String oauthTimestamp = String.valueOf(currSecTime * 1000);
			String oauthNonce = String.valueOf(currSecTime + RandomUtils.nextInt(10));
			String baseStr = generateBaseString(SessionId, oauthTimestamp, oauthNonce);
			String oauthSignature = generateSign(baseStr);
			String loginUrl = AUTH_URL + "?fileId=" + URLEncoder.encode(Uin, "UTF-8") + "&token="
					+ URLEncoder.encode(SessionId, "UTF-8");
			LoggerUtils.info("oppo sdk auth url=" + loginUrl);

			SimpleHttpRequest shr = SimpleHttpRequest.createGet(loginUrl);
			shr.addHeader("param", baseStr);
			shr.addHeader("oauthSignature", oauthSignature);

			LoggerUtils.info("oppo sdk auth param=" + baseStr);
			LoggerUtils.info("oppo sdk auth oauthSignature=" + oauthSignature);
			String resultGet = shr.sendGetString();
			LoggerUtils.info("oppo sdk auth resultGet=" + resultGet);
			Map<String, Object> rm = GsonFactory.getDefault().fromJson(resultGet, Map.class);

			// {"resultCode":"1001","resultMsg":"Param Error","loginToken":null,"ssoid":null,
			// "appKey":null,"userName":null,"email":null,"mobileNumber":null,"createTime":null
			// ,"userStatus":null}
			String retCode = DataUtils.getMapString(rm, "resultCode", "");
			String ssoid = DataUtils.getMapString(rm, "ssoid", "");
			if (retCode.equals("200") && Uin.equals(ssoid)) {
				result = true;
			}
		} catch (Exception e) {
			LoggerUtils.error(e);
		}
		return new PlatformRetData(result, null);
	}

}
