package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.service.sdk.SdkGroup;
import com.gamejelly.game.gong2.login.service.sdk.SdkService;
import com.gamejelly.game.gong2.login.service.sdk.impl.mumayi.Base64;
import com.gamejelly.game.gong2.login.service.sdk.impl.mumayi.MD5Util;
import com.gamejelly.game.gong2.login.service.sdk.impl.mumayi.MMYPayResult;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.gamejelly.game.gong2.login.utils.SdkConst;
import com.hadoit.game.common.framework.http.SimpleHttpRequest;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

@SdkGroup({ SdkConst.CC_SDK_ANDROID_MUMAYI })
@Component("sdkMumayiService")
public class SdkMumayiService extends SdkBaseService implements SdkService {

	private String appId = "1830";
	private String appKey = "ecaa5e807654397dPJq51Jf87RsBfXeJUhTB957fQu8hMF5KcFe6x2AobunpfzFBvQ";

	private static final String AUTH_URL = "http://pay.mumayi.com/user/index/validation";

	private MMYPayResult readRequestParams(HttpServletRequest request) {
		try {
			BufferedReader reader = request.getReader();
			String line = null;
			StringBuffer stringBuffer = new StringBuffer();
			while ((line = reader.readLine()) != null)
				stringBuffer.append(line);
			return GsonFactory.getDefault().fromJson(stringBuffer.toString(), MMYPayResult.class);
		} catch (Exception e) {
			LoggerUtils.error(e);
		}
		return new MMYPayResult();
	}

	private boolean verify(String sign, String appKey, String orderId) throws UnsupportedEncodingException {
		if (sign.length() < 14) {
			return false;
		}
		String verityStr = sign.substring(0, 8);
		sign = sign.substring(8);
		String temp = MD5Util.toMD5(sign);
		if (!verityStr.equals(temp.substring(0, 8))) {
			return false;
		}
		String keyB = sign.substring(0, 6);

		String randKey = keyB + appKey;

		randKey = MD5Util.toMD5(randKey);

		byte[] signB = Base64.decodeFast(sign.substring(6));
		int signLength = signB.length;
		String verfic = "";
		for (int i = 0; i < signLength; i++) {
			char b = (char) (signB[i] ^ randKey.getBytes()[i % 32]);
			verfic += String.valueOf(b);
		}
		return verfic.equals(orderId);
	}

	/**
	 * 平台回调
	 * 
	 * @param requestParams
	 * @return
	 */
	public Object payCallback(Map<String, Object> params) {
		// orderID 订单号
		// productName 商品名称
		// productPrice 商品金额
		// productDesc 商品描述
		// orderTime 交易时间
		// tradeSign 交易签名,用来开发判断此回调是不是由木蚂蚁真实请求
		// 具体解签请看
		// tradeState 交易结果 success为成功,error为失败

		HttpServletRequest request = (HttpServletRequest) params.get("request");
		MMYPayResult mmyPayResult = readRequestParams(request);

		String orderID = mmyPayResult.getOrderID();
		String tradeSign = mmyPayResult.getTradeSign();
		String tradeState = mmyPayResult.getTradeState();
		String myOrderId = mmyPayResult.getProductDesc();

		LoggerUtils.info("mumayi payCallback", params);

		String result = "fail";
		try {
			if ("success".equalsIgnoreCase(tradeState) && verify(tradeSign, appKey, orderID)) { // 验签成功
				processOrder(myOrderId, mmyPayResult.getProductPrice(), true);
				result = "success";
			}
		} catch (Exception e) {
			LoggerUtils.error(e);
			result = "fail";
		}
		return result;
	}

	/**
	 * 登录
	 * 
	 * @param requestParams
	 * @return
	 */
	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {
		String token = FsGameLoginUtils.getMapString(requestParams, "SessionId", "");
		String uid = FsGameLoginUtils.getMapString(requestParams, "Uin", "");
		String url = AUTH_URL + "?token=" + token + "&uid=" + uid;
		LoggerUtils.info("mumayi login check id url =", url);
		SimpleHttpRequest shr = SimpleHttpRequest.createPost(url);
		String result = shr.sendGetString();
		if (!"success".equalsIgnoreCase(result)) {
			return new PlatformRetData(false, null);
		}
		return new PlatformRetData(true, null);
	}

}
