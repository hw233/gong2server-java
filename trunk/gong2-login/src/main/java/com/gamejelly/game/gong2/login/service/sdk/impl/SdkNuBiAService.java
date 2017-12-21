package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.meta.PlatformPayRecord;
import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.service.sdk.SdkGroup;
import com.gamejelly.game.gong2.login.service.sdk.SdkService;
import com.gamejelly.game.gong2.login.service.sdk.impl.nubia.MD5Util;
import com.gamejelly.game.gong2.login.service.sdk.impl.nubia.ParameterUtil;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.gamejelly.game.gong2.login.utils.SdkConst;
import com.hadoit.game.common.framework.http.SimpleHttpRequest;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

@SdkGroup({ SdkConst.CC_SDK_ANROID_NUBIA })
@Component("sdkNuBiAService")
public class SdkNuBiAService extends SdkBaseService implements SdkService {

	private static String AppID = "1356665";
	private static String AppKey = "63756004b9c54b3689731b1d4629fc7d";
	private static String AppSecret = "bbf111ecacbe458ea5f063aab998a69c";
	private static String Login_Check_URL = "https://niugamecenter.nubia.com/VerifyAccount/CheckLogined";

	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {
		LoggerUtils.info("nubia loginAuth ");

		String uid = FsGameLoginUtils.getMapString(requestParams, "Uin", "");
		String sessionId = FsGameLoginUtils.getMapString(requestParams, "SessionId", "");
		String gameId = FsGameLoginUtils.getMapString(requestParams, "gameId", "");

		long timestamp = System.currentTimeMillis() / 1000;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uid", uid);
		params.put("session_id", sessionId);
		params.put("data_timestamp", timestamp + "");
		params.put("game_id", gameId);

		String verifyData = ParameterUtil.getSignData(params);
		LoggerUtils.info("nubia loginAuth verifyData = ", verifyData);

		boolean result = false;
		try {
			String signk = MD5Util.sign(verifyData, ":" + AppID + ":" + AppSecret);
			params.put("sign", signk);
			String body = ParameterUtil.mapToUrl(params);
			System.out.println("[请求参数]" + body);
			SimpleHttpRequest shr = SimpleHttpRequest.createPost(Login_Check_URL).createBody(body);
			shr.setHeader("Content-Type", "application/x-www-form-urlencoded");
			shr.setCharset("utf-8");
			String resultShr = shr.sendGetString();
			LoggerUtils.info("nubia loginAuth resultShr = ", resultShr);
			Map<String, Object> rmResult = GsonFactory.getDefault().fromJson(resultShr, Map.class);
			if (rmResult != null) {
				if (rmResult.containsKey("code")) {
					double code = (Double) rmResult.get("code");
					if (code == 0) {
						result = true;
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new PlatformRetData(result, null);
	}

	@Override
	public PlatformRetData handleSdkData(Map<String, Object> requestParams) {

		LoggerUtils.info("kuaikan handleSdkData:", requestParams);

		String orderId = FsGameLoginUtils.getMapString(requestParams, "orderId", "");
		String orderName = FsGameLoginUtils.getMapString(requestParams, "orderName", "");
		String uid = FsGameLoginUtils.getMapString(requestParams, "Uin", "");

		PlatformPayRecord ppr = platformPayRecordService.getByOrderSn(orderId);
		if (ppr == null) {
			return new PlatformRetData(false, null);
		}

		long createTime = ppr.getCreateTime() / 1000;
		BigDecimal amount = new BigDecimal(Double.toString(ppr.getProductAmount() / 100f));
		String price = String.format("%.2f", amount.doubleValue());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("app_id", AppID);
		params.put("uid", uid);
		params.put("amount", price.toString());
		params.put("product_name", orderName);
		params.put("product_des", ppr.getProductId());
		params.put("number", 1 + "");
		params.put("data_timestamp", createTime + "");
		params.put("cp_order_id", orderId);

		String verifyData = ParameterUtil.getSignData(params);
		LoggerUtils.info("nubia handleSdk verifyData = ", verifyData);
		String signk = "";
		try {
			signk = MD5Util.sign(verifyData, ":" + AppID + ":" + AppSecret);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LoggerUtils.info("nubia handleSdk signk = ", signk);

		params.put("cp_order_sign", signk);
		return new PlatformRetData(true, params);
	}

	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		LoggerUtils.info("nubia payCallback", requestParams);

		String orderNo = FsGameLoginUtils.getMapString(requestParams, "order_no", "");
		String data_timestamp = FsGameLoginUtils.getMapString(requestParams, "data_timestamp", "");
		String pay_success = FsGameLoginUtils.getMapString(requestParams, "pay_success", ""); // 成功为1
		String sign = FsGameLoginUtils.getMapString(requestParams, "sign", "");
		String app_id = FsGameLoginUtils.getMapString(requestParams, "app_id", "");
		String uid = FsGameLoginUtils.getMapString(requestParams, "uid", "");
		String amonut = FsGameLoginUtils.getMapString(requestParams, "amount", "");
		String product_name = FsGameLoginUtils.getMapString(requestParams, "product_name", "");
		String product_des = FsGameLoginUtils.getMapString(requestParams, "product_des", "");
		int number = FsGameLoginUtils.getMapInteger(requestParams, "number", 0);
		String order_serial = FsGameLoginUtils.getMapString(requestParams, "order_serial", "");
		String order_sign = FsGameLoginUtils.getMapString(requestParams, "order_sign", "");

		BigDecimal paAmonut = new BigDecimal(amonut);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("order_no", orderNo);
		params.put("data_timestamp", data_timestamp);
		params.put("pay_success", pay_success + "");
		params.put("app_id", app_id);
		params.put("uid", uid);
		params.put("amount", amonut);
		params.put("product_name", product_name);
		params.put("product_des", product_des);
		params.put("number", number + "");
		// params.put("order_serial", order_serial);

		String verifyData = ParameterUtil.getSignData(params);
		int code = 10000;
		String data = "";
		String message = "";
		Map<String, Object> ret = new HashMap<String, Object>();
		LoggerUtils.info("nubia payCallback verifyData=", verifyData);
		PlatformPayRecord ppr = platformPayRecordService.getByOrderSn(orderNo);
		if (ppr != null) {
			boolean retVerify = false;
			try {
				retVerify = MD5Util.verify(verifyData, order_sign, ":" + AppID + ":" + AppSecret);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (retVerify && StringUtils.equals(pay_success, "1")) {
				processOrder(orderNo, paAmonut.toString(), true);
				code = 0;
			}
		}

		ret.put("code", code);
		ret.put("data", data);
		ret.put("message", message);
		return ret;
	}

	public static void orderCheck() {
		String verifyData = "amount=0.10&app_id=1356665&data_timestamp=1501148034&number=1&order_no=5e1e1baa69064f618f7102b95fa44881&pay_success=1&product_des=cn.gamejelly.gong.nubia_60yb&product_name=我的宫廷:60元宝&uid=8127563";
		String order_sign = "eb61ad47b541f0dc4cc59e5f81bcb3fb";
		String sign = "43d625a9898dcba417d2cc8bdaaf8817";
		boolean retVerify = false;
		try {
			retVerify = MD5Util.verify(verifyData, order_sign, ":" + AppID + ":" + AppSecret);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LoggerUtils.info("nubia  retVerify = ", retVerify);
	}

	public static void main(String[] args) {

		orderCheck();

		// long timestamp = System.currentTimeMillis() / 1000;
		// Map<String, Object> params = new HashMap<String, Object>();
		// params.put("uid", 8127563 + "");
		// params.put("session_id", "adc99bdb66e9cf1a19d1cad160e753db");
		// params.put("data_timestamp", "1501143274");
		// params.put("game_id", 8127563 + "");
		//
		// String verifyData = ParameterUtil.getSignData(params);
		// LoggerUtils.info("nubia loginAuth verifyData = ", verifyData);
		//
		// boolean result = false;
		// try {
		// String signk = MD5Util.sign(verifyData, ":" + AppID + ":" +
		// AppSecret);
		// params.put("sign", signk);
		// String body = UCUtil.encodeJson(params);// 把参数序列化成一个json字符串
		// System.out.println("[请求参数]" + body);
		// System.out.println("[请求参数]" +
		// GsonFactory.getDefault().toJson(params));
		//
		// String body1 = ParameterUtil.mapToUrl(params);
		// System.out.println("[请求参数1]" + body1);
		// SimpleHttpRequest shr =
		// SimpleHttpRequest.createPost(Login_Check_URL).createBody(body1);
		// shr.setHeader("Content-Type", "application/x-www-form-urlencoded");
		// shr.setCharset("utf-8");
		// String resultShr = shr.sendGetString();
		// LoggerUtils.info("nubia loginAuth Login_Check_URL = ",
		// Login_Check_URL);
		// LoggerUtils.info("nubia loginAuth resultShr = ", resultShr);
		// Map<String, Object> rmResult =
		// GsonFactory.getDefault().fromJson(resultShr, Map.class);
		// if (rmResult != null) {
		// if (rmResult.containsKey("code")) {
		// double code = (Double) rmResult.get("code");
		// if (code == 0) {
		// result = true;
		// }
		// }
		// }
		//
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}
}
