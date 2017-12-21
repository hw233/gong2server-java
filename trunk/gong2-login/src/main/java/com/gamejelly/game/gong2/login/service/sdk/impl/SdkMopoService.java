package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.service.sdk.SdkGroup;
import com.gamejelly.game.gong2.login.service.sdk.SdkService;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.gamejelly.game.gong2.login.utils.SdkConst;
import com.hadoit.game.common.framework.http.SimpleHttpRequest;

@SdkGroup({ SdkConst.CC_SDK_ANDROID_MOPO })
@Component("sdkMopoService")
public class SdkMopoService extends SdkBaseService implements SdkService {
	static String appId = "7005625";
	static String mNo = "14319";
	static String payChannel = "18_zhiyifu_";
	static String secretKey = "235647567543@@334656";

	private static final String TOKEN_URL = "http://111.1.17.152:10015/skyppa/index!check.action";

	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		LoggerUtils.info("mopo payCallback", requestParams);
		HttpServletRequest request = (HttpServletRequest) requestParams.get("request");
		String myOrderId = FsGameLoginUtils.getMapString(requestParams, "ext1", "");
		final String queryString = request.getQueryString();
		String signMsg = queryString.substring(queryString.lastIndexOf("&signMsg=") + "&signMsg=".length());
		String signSource = queryString.substring(0, queryString.lastIndexOf("&signMsg="));
		signSource = signSource + "&key=" + secretKey;

		String mySign = FsGameLoginUtils.md5up(signSource);

		String ret = "result=1";
		try {
			if (signMsg.equals(mySign)) {
				processOrder(myOrderId, FsGameLoginUtils.getMapString(requestParams, "realAmount", ""), false);
				ret = "result=0";
			}
		} catch (Exception e) {
			LoggerUtils.error(e);
			ret = "result=1";
		}

		/**
		 * 成功处理的订单，一定要返回"result=0"！！！！ 斯凯只有收到result=0的回应才认为此笔订单交易成功，否则都会再次重复通知，
		 * 直到达到5次或其配置的最大次数； 返回result=1的情况主要发生在：
		 * 校验签名失败，CP方暂时无法处理支付结果保存，数据库异常，系统异常等
		 */
		return ret;
	}

	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {
		String Uin = FsGameLoginUtils.getMapString(requestParams, "Uin", "");
		String SessionId = FsGameLoginUtils.getMapString(requestParams, "SessionId", "");
		String url = TOKEN_URL + "?skyid=" + Uin + "&token=" + SessionId;
		SimpleHttpRequest shr = SimpleHttpRequest.createGet(url);
		String resultGet = shr.sendGetString();
		boolean result = false;
		if ("true".equalsIgnoreCase(resultGet)) {
			result = true;
		}
		return new PlatformRetData(result, null);
	}

}
