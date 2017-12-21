package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.service.sdk.SdkGroup;
import com.gamejelly.game.gong2.login.service.sdk.SdkService;
import com.gamejelly.game.gong2.login.service.sdk.impl.sky.RSASignature;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.gamejelly.game.gong2.login.utils.SdkConst;
import com.hadoit.game.common.framework.http.SimpleHttpRequest;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

@SdkGroup({ SdkConst.CC_SDK_IOS_SKY })
@Component("sdkSKYService")
public class SdkSKYService extends SdkBaseService implements SdkService {

	private String AppId = "788";
	private String AppKey = "B41E677220413F925AF9FF528444F285";

	private String loginCheckUrl = "https://pay.slooti.com/?r=auth/verify";

	@SuppressWarnings("unchecked")
	@Override
	public Object payCallback(Map<String, Object> requestParams) {

		// 获得通知参数
		HttpServletRequest req = (HttpServletRequest) requestParams.get("request");
		if (req == null) {
			return "fail";
		}
		LoggerUtils.info("sky payCallback", requestParams);

		Map map = req.getParameterMap();
		String notifyData = (String) ((Object[]) map.get("notify_data"))[0];
		String sign = (String) ((Object[]) map.get("sign"))[0];

		boolean verified = false;
		String notifyJson = "";

		try {
			// 公钥RSA解密后的json字符串
			// 解密后的json格式:
			// {"order_id_com":"2013050900000712","user_id":"10010","amount":"0.10","account":"test001","order_id":"2013050900000713","result":"success"}
			notifyJson = RSASignature.decrypt(notifyData);
			LoggerUtils.info("sky payCallback notifyJson", notifyJson);

			// 公钥对数据进行RSA签名校验
			verified = RSASignature.verify(notifyJson, sign);
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}

		if (verified) {
			// 签名校验成功, 可以解析notifyJson, 做逻辑处理, 比如给相应用户增加宝石
			try {
				Map<String, Object> rm = GsonFactory.getDefault().fromJson(notifyJson, Map.class);
				processOrder(String.valueOf(rm.get("order_id_com")), String.valueOf(rm.get("amount")), true);
				return "success";
			} catch (Exception e) {
				LoggerUtils.error(e);
				return "fail";
			}
		}

		return "fail";
	}

	@SuppressWarnings("unchecked")
	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {

		String Uin = FsGameLoginUtils.getMapString(requestParams, "Uin", "");
		String SessionId = FsGameLoginUtils.getMapString(requestParams, "SessionId", "");

		String sign = "appid=" + AppId + "&sessionid=" + SessionId;
		sign = FsGameLoginUtils.md5low(sign);

		String url = loginCheckUrl + "&appid=" + AppId + "&sessionid=" + SessionId + "&sign=" + sign;

		SimpleHttpRequest shr = SimpleHttpRequest.createGet(url);
		String resultGet = shr.sendGetString();

		boolean result = false;
		if (resultGet == null || resultGet.equals("")) {
			result = false;
		} else {
			Map<String, String> rm = GsonFactory.getDefault().fromJson(resultGet, Map.class);
			if (rm.get("status").equals("success")) {
				result = true;
			}
		}

		return new PlatformRetData(result, null);
	}

}
