package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.net.URLDecoder;
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

@SdkGroup({ SdkConst.CC_SDK_ANDROID_MZW })
@Component("sdkMzwService")
public class SdkMzwService extends SdkBaseService implements SdkService {

	private String AppKey = "d38781ca718deabd90daf3bd83de774a";
	private String SignKey = "54817369cd32d";

	private String tokenCheckUrl = "http://sdk.muzhiwan.com/oauth2/getuser.php";

	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		LoggerUtils.info("mzw payCallback", requestParams);
		try {
			String productName = URLDecoder.decode(DataUtils.getMapString(requestParams, "productName", ""), "UTF-8");
			String productDesc = URLDecoder.decode(DataUtils.getMapString(requestParams, "productDesc", ""), "UTF-8");

			LoggerUtils.info("mzw productName1=", productName);
			if (productName.equals(new String(productName.getBytes("iso8859-1"), "iso8859-1"))) {
				productName = new String(productName.getBytes("iso8859-1"), "utf-8");
				LoggerUtils.info("mzw productName2=", productName);
			}

			if (productDesc.equals(new String(productDesc.getBytes("iso8859-1"), "iso8859-1"))) {
				productDesc = new String(productDesc.getBytes("iso8859-1"), "utf-8");
			}

			LoggerUtils.info("mzw productName3=", productName);
			String orderId = DataUtils.getMapString(requestParams, "extern", "");
			String sign = DataUtils.getMapString(requestParams, "sign", "");
			StringBuilder signParams = new StringBuilder();

			signParams.append(requestParams.get("appkey")).append(requestParams.get("orderID")).append(productName)
					.append(productDesc).append(requestParams.get("productID")).append(requestParams.get("money"))
					.append(requestParams.get("uid")).append(requestParams.get("extern")).append(SignKey);
			String mySign = FsGameLoginUtils.md5up(signParams.toString());
			if (mySign.equalsIgnoreCase(sign)) {
				processOrder(orderId, DataUtils.getMapString(requestParams, "money", ""), true);
				return "SUCCESS";
			}
		} catch (Exception e) {
			LoggerUtils.error(e);
		}
		return "FAILURE";
	}

	@SuppressWarnings("unchecked")
	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {
		String Uin = FsGameLoginUtils.getMapString(requestParams, "Uin", "");
		String SessionId = FsGameLoginUtils.getMapString(requestParams, "SessionId", "");

		StringBuilder getUrl = new StringBuilder(tokenCheckUrl);
		getUrl.append("?token=");
		getUrl.append(SessionId);
		getUrl.append("&appkey=");
		getUrl.append(AppKey);

		boolean result = false;
		SimpleHttpRequest shr = SimpleHttpRequest.createGet(getUrl.toString());
		String resultGet = shr.sendGetString();
		System.out.print(resultGet);
		Map<String, Object> rm = GsonFactory.getDefault().fromJson(resultGet, Map.class);

		if (rm.containsKey("code") && rm.get("code").equals("1")) {
			if (rm.containsKey("user")) {
				Map<String, Object> userInfo = (Map<String, Object>) rm.get("user");
				String uid = (String) userInfo.get("uid");
				requestParams.put("name", uid);
				result = true;
			}
		}

		return new PlatformRetData(result, null);
	}

}
