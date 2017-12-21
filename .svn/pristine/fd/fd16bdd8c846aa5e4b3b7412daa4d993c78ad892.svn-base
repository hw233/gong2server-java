package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.meta.PlatformPayRecord;
import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.service.sdk.SdkGroup;
import com.gamejelly.game.gong2.login.service.sdk.SdkService;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.gamejelly.game.gong2.login.utils.SdkConst;
import com.hadoit.game.common.framework.http.SimpleHttpRequest;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

@SdkGroup({ SdkConst.CC_SDK_ANROID_AIQIYI })
@Component("sdkAiqiyiService")
public class SdkAiqiyiService extends SdkBaseService implements SdkService {

	private static String APP_ID = "5691";
	private static String LOGIN_KEY = "74974bf301ff7e270d0e1e6860735f38";
	private static String PAY_KEY = "a72b91fe299f9a5d9f04eaccd176343e";
	private static String VOUCHER_ORDER = "http://pay.game.iqiyi.com/interface/orderinfo/voucherOrders";

	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		LoggerUtils.info("aiqiyi payCallback", requestParams);

		String user_id = FsGameLoginUtils.getMapString(requestParams, "user_id", "");
		String role_id = FsGameLoginUtils.getMapString(requestParams, "role_id", "");
		String order_id = FsGameLoginUtils.getMapString(requestParams, "order_id", "");
		String money = FsGameLoginUtils.getMapString(requestParams, "money", "");
		String time = FsGameLoginUtils.getMapString(requestParams, "time", "");
		String userData = FsGameLoginUtils.getMapString(requestParams, "userData", "");
		String sign = FsGameLoginUtils.getMapString(requestParams, "sign", "");

		// sign= MD5($user_id.$role_id.$order_id.$money.$time.$key)
		try {
			userData = URLEncoder.encode(userData, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String signSource = user_id + role_id + order_id + money + time + PAY_KEY;
		String signRet = FsGameLoginUtils.md5low(signSource);
		LoggerUtils.info("aiqiyi payCallback", " signSource=", signSource, " signRet=", signRet);

		Map<String, Object> ret = new HashMap<String, Object>();
		int result = -6;
		String message = "";
		if (StringUtils.equals(sign, signRet)) {
			PlatformPayRecord ppr = platformPayRecordService.getByOrderSn(userData);
			if (ppr != null) {
				result = 0;
				message = "ok";
				processOrder(userData, String.valueOf(money), true);
			} else {
				message = "order not exist";
			}

		} else {
			result = -1;
			message = "sign error";
		}

		ret.put("result", result);
		ret.put("message", message);

		String resultData = GsonFactory.getDefault().toJson(ret);

		LoggerUtils.info("aiqiyi payCallback ", " resultData=", resultData);
		return resultData;
	}

	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {

		LoggerUtils.info("aiqiyi loginAuth", requestParams);

		String uid = FsGameLoginUtils.getMapString(requestParams, "Uin", "");
		String timestamp = FsGameLoginUtils.getMapString(requestParams, "Time", "");
		String sign = FsGameLoginUtils.getMapString(requestParams, "Sign", "");

		String signSource = uid + "&" + timestamp + "&" + LOGIN_KEY;
		String signRet = FsGameLoginUtils.md5low(signSource);
		boolean ret = false;
		if (StringUtils.equals(sign, signRet)) {
			ret = true;
		}
		LoggerUtils.info("aiqiyi loginAuth signSource=", signSource, " signRet=", signRet, "sign=", sign);
		return new PlatformRetData(ret, null);
	}

	public static void loginTest() {

		String uid = "1437558351";
		String timestamp = "1501310545";
		String sign = "19fea0fc7d586635799c1f8690e97264";
		String signSource = uid + "&" + timestamp + "&" + LOGIN_KEY;
		String signRet = FsGameLoginUtils.md5low(signSource);
		String signRet1 = FsGameLoginUtils.getMD5Str(signSource);
		LoggerUtils.info("aiqiyi loginAuth signSource=", signSource, " signRet=", signRet, "sign=", sign, "signRet1=",
				signRet1);
	}

	public static void orderCheck() {

		// String game_id = "3021";
		// String start_time = "2017-07-31 11:00:00";
		// String end_time = "2017-06-31 11:00:00";
		// String signSource = game_id + start_time + end_time + PAY_KEY;
		// String sign = FsGameLoginUtils.md5low(signSource);
		//
		// Map<String, Object> params = new HashMap<String, Object>();
		// params.put("game_id", game_id);
		// params.put("start_time", "2017-07-31 11:00:00");
		// params.put("end_time", "2017-07-31 11:00:00");
		// params.put("sign", sign);
		//
		// LoggerUtils.info("aiqiyi orderCheck signSource=", signSource, "
		// sign=", sign);
		//
		// SimpleHttpRequest shr = SimpleHttpRequest.createPost(VOUCHER_ORDER);
		// shr.createBody(GsonFactory.getDefault().toJson(params));
		// shr.setHeader("Content-Type", "application/x-www-form-urlencoded");
		// shr.setCharset("utf-8");
		// String resultGet = shr.sendGetString();
		// LoggerUtils.info("aiqiyi query order resultGet = ", resultGet);

		String sign = "c1a0f88387b5cc377f2fd02fa18b498b";
		String signSource = "14375583512-1-1501311237-19072755611501485537a72b91fe299f9a5d9f04eaccd176343e";
		String signRet = FsGameLoginUtils.md5low(signSource);
		LoggerUtils.info("aiqiyi loginAuth signSource=", signSource, " signRet=", signRet, "sign=", sign);
	}

	public static void main(String[] args) {
		orderCheck();
	}

}