package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
import com.hadoit.game.engine.core.protocol.json.GsonFactory;
import com.uudev.payment.cloud.util.DesUtils;

@SdkGroup({ SdkConst.CC_SDK_ANDROID_UUCUN })
@Component("sdkUUCunService")
public class SdkUUCunService extends SdkBaseService implements SdkService {
	private static String secretKey = "fGrfHt";
	private static String appKey = "8t6vBBlZFLRmDRILRCNhcssKgdMLpKbC";
	private static String desKey = "3sFoxFe7";

	private static final String TOKEN_URL = "http://passport.uuserv20.com/checkSSOToken.do";

	private static DesUtils desUtils = new DesUtils();
	static {
		desUtils.setDesKEY(desKey);
	}

	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		LoggerUtils.info("uucun payCallback", requestParams);

		// 1）callback_rsp：此参数传递的值为加密后的值，加密方式为DES加密(DES密钥请向商务人员索取)
		// 2）callback_user：此参数传递的值为加密后的值，加密方式为DES加密；该参数传递的是客户端上传的argsMaps参数储存的值
		// 3）callback_appkey：此参数传递的值为加密后的app_key值，加密方式为DES加密(解密密钥为 2SoXIhFB)；

		String callback_rsp = FsGameLoginUtils.getMapString(requestParams, "callback_rsp", "");
		String callback_user = FsGameLoginUtils.getMapString(requestParams, "callback_user", "");
		String callback_appkey = FsGameLoginUtils.getMapString(requestParams, "callback_appkey", "");
		String ret = "-1";
		try {
			// app_key=…& txn_seq=…& order_id=…& rsp_code=…
			// & txn_time=…& actual_txn_amt=…& time_stamp=…
			// & key=…
			String rawRsp = desUtils.decrypt(callback_rsp);

			Map<String, String> rawParams = FsGameLoginUtils.parseQueryString(rawRsp);
			String app_key = FsGameLoginUtils.getMapString(rawParams, "app_key", "");
			String txn_seq = FsGameLoginUtils.getMapString(rawParams, "txn_seq", "");
			String order_id = FsGameLoginUtils.getMapString(rawParams, "order_id", "");
			String rsp_code = FsGameLoginUtils.getMapString(rawParams, "rsp_code", "");
			String txn_time = FsGameLoginUtils.getMapString(rawParams, "txn_time", "");
			String actual_txn_amt = FsGameLoginUtils.getMapString(rawParams, "actual_txn_amt", "");
			String time_stamp = FsGameLoginUtils.getMapString(rawParams, "time_stamp", "");
			String signMsg = FsGameLoginUtils.getMapString(rawParams, "signMsg", "");
			StringBuilder signSb = new StringBuilder();
			signSb.append("app_key=").append(app_key).append("&txn_seq=").append(txn_seq).append("&order_id=")
					.append(order_id).append("&rsp_code=").append(rsp_code).append("&txn_time=").append(txn_time)
					.append("&actual_txn_amt=").append(actual_txn_amt).append("&time_stamp=").append(time_stamp)
					.append("&key=").append(secretKey);
			String mySign = FsGameLoginUtils.md5low(signSb.toString());

			LoggerUtils.info("uucun payCallback rawRsp", rawRsp, mySign);
			if (mySign.equalsIgnoreCase(signMsg) && "000000".equalsIgnoreCase(rsp_code)) {
				processOrder(order_id, actual_txn_amt, false);
				ret = "1";
			}
		} catch (Exception e) {
			LoggerUtils.error(e);
			ret = "-1";
		}

		return ret;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {
		String SessionId = FsGameLoginUtils.getMapString(requestParams, "SessionId", "");
		Map<String, String> tm = new HashMap<String, String>();
		tm.put("token", SessionId);
		String jsonString = null;
		try {
			jsonString = URLEncoder.encode(GsonFactory.getDefault().toJson(tm), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String url = TOKEN_URL + "?jsonString=" + jsonString;
		SimpleHttpRequest shr = SimpleHttpRequest.createGet(url);
		String resultGet = shr.sendGetString();

		Map<String, Object> rm = GsonFactory.getDefault().fromJson(resultGet, Map.class);

		LoggerUtils.info("uucun loginAuth", url, resultGet);
		int errorCode = Double.valueOf(rm.get("errorCode").toString()).intValue();
		boolean result = false;
		if (errorCode == 0) {
			result = true;
		}
		return new PlatformRetData(result, null);
	}

}
