package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
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

@SdkGroup({ SdkConst.CC_SDK_ANDROID_YOUKU })
@Component("sdkYoukuService")
public class SdkYoukuService extends SdkBaseService implements SdkService {

	private String APPID = "1133";
	private String APPKEY = "9040db9923f965b5";
	private String APPSECRET = "45b5f567a82282ebf1a0756a88889787";
	private String PAYKEY = "d9f337039960a6ad7e4c39f9014d4269";

	private String tokenCheckUrl = "http://sdk.api.gamex.mobile.youku.com/game/user/infomation";

	private String HmacMd5(String str, String skey) {
		String strSign = "";
		SecretKey key = new SecretKeySpec(skey.getBytes(), "HmacMD5");
		try {
			Mac mac = Mac.getInstance(key.getAlgorithm());
			mac.init(key);
			byte[] code = mac.doFinal(str.getBytes());
			char[] charSign = Hex.encodeHex(code);
			strSign = new String(charSign);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return strSign;
	}

	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		LoggerUtils.info("youku payCallback", requestParams);
		String apporderID = DataUtils.getMapString(requestParams, "apporderID");
		String uid = DataUtils.getMapString(requestParams, "uid");
		String price = DataUtils.getMapString(requestParams, "price");
		String sign = DataUtils.getMapString(requestParams, "sign");
		String passthrough = DataUtils.getMapString(requestParams, "passthrough");

		String code = "failed";
		String resStr = "验签失败";
		try {
			StringBuilder signSb = new StringBuilder();
			String notifyUrl = passthrough;
			signSb.append(notifyUrl).append("?apporderID=").append(apporderID).append("&price=").append(price)
					.append("&uid=").append(uid);
			String newSign = HmacMd5(signSb.toString(), PAYKEY);
			if (newSign.equalsIgnoreCase(sign)) {
				// 验签通过
				processOrder(apporderID, price, false);
				code = "success";
				resStr = "通知成功";
			}
		} catch (Exception e) {
			LoggerUtils.error(e);
			code = "failed";
			resStr = "未知错误";
		}

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("status", code);
		resultMap.put("desc", resStr);
		return GsonFactory.getDefault().toJson(resultMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {
		String Uin = FsGameLoginUtils.getMapString(requestParams, "Uin", "");
		String SessionId = FsGameLoginUtils.getMapString(requestParams, "SessionId", "");

		StringBuilder paramUrl = new StringBuilder();
		paramUrl.append("appkey=");
		paramUrl.append(APPKEY);
		paramUrl.append("&sessionid=");
		paramUrl.append(SessionId);

		String strSign = HmacMd5(paramUrl.toString(), PAYKEY);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("sessionid", SessionId);
		m.put("appkey", APPKEY);
		m.put("sign", strSign);
		SimpleHttpRequest shr = SimpleHttpRequest.createPost(tokenCheckUrl.toString());
		shr.createFormBody(FsGameLoginUtils.mapToParamBody(m));
		String resultGet = shr.sendGetString();

		// {"status":"success","isbind":false,"uid":2508422}

		boolean result = false;
		if (resultGet == null || resultGet.equals("")) {
			result = false;
		} else {
			Map<String, Object> rm = GsonFactory.getDefault().fromJson(resultGet, Map.class);
			String status = FsGameLoginUtils.getMapString(rm, "status", "");
			int uid = FsGameLoginUtils.getMapInteger(rm, "uid", 0);
			if (status.equals("success") && uid != 0) {
				result = true;

				String strUid = String.valueOf(uid);
				requestParams.put("name", strUid);
			}
		}

		return new PlatformRetData(result, null);
	}

}
