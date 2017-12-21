package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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

@SdkGroup({ SdkConst.CC_SDK_ANROID_PENGYOUWAN })
@Component("sdkPyWanService")
public class SdkPyWanService extends SdkBaseService implements SdkService {

	private static String GAME_KEY = "a6e4c1c4";
	private static String PAY_KEY = "d1eada119bdb441d";
	private static String LOGIN_CHECK_URL = "http://api.sdk.pyw.cn/Cpapi/check";

	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		LoggerUtils.info("pyWan payCallback", requestParams);

		int ack = 0;
		String msg = "";
		Map<String, Object> ret = new HashMap<String, Object>();
		List<String> keys = new ArrayList<String>(requestParams.keySet());
		String params = "";
		if (keys.size() <= 4) {
			params = keys.get(3);
		}

		Map<String, Object> keyResult = GsonFactory.getDefault().fromJson(params, Map.class);
		LoggerUtils.info("pyWan payCallback keyResult=", keyResult);
		if (keyResult == null) {
			return null;
		}
		String ver = FsGameLoginUtils.getMapString(keyResult, "ver", "");
		String tid = FsGameLoginUtils.getMapString(keyResult, "tid", "");
		String sign = FsGameLoginUtils.getMapString(keyResult, "sign", "");
		String gamekey = FsGameLoginUtils.getMapString(keyResult, "gamekey", "");
		String channel = FsGameLoginUtils.getMapString(keyResult, "channel", "");
		String cp_orderid = FsGameLoginUtils.getMapString(keyResult, "cp_orderid", "");
		String ch_orderid = FsGameLoginUtils.getMapString(keyResult, "ch_orderid", "");
		String cp_param = FsGameLoginUtils.getMapString(keyResult, "cp_param", "");
		String amount = FsGameLoginUtils.getMapString(keyResult, "amount", "");

		String signSource = PAY_KEY + cp_orderid + ch_orderid + amount;
		LoggerUtils.info("pyWan payCallback signSource=", signSource);
		String signRet = FsGameLoginUtils.md5low(signSource);
		LoggerUtils.info("pyWan payCallback signRet=", signRet);

		if (StringUtils.equals(signRet, sign)) {
			PlatformPayRecord ppr = platformPayRecordService.getByOrderSn(cp_orderid);
			if (ppr != null) {
				ack = 200;
				processOrder(cp_orderid, String.valueOf(amount), true);
			}
		} else {
			msg = "sign error";
		}

		ret.put("ack", ack);
		ret.put("msg", msg);

		String retJson = GsonFactory.getDefault().toJson(ret);
		LoggerUtils.info("pyWan payCallback retJson=", retJson);
		return retJson;
	}

	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {
		LoggerUtils.info("pyWan loginAuth", requestParams);

		long tid = System.currentTimeMillis();
		String uid = FsGameLoginUtils.getMapString(requestParams, "Uin", "");
		String token = FsGameLoginUtils.getMapString(requestParams, "SessionId", "");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tid", tid);
		params.put("token", token);
		params.put("uid", uid);

		String transData = GsonFactory.getDefault().toJson(params);
		SimpleHttpRequest shr = SimpleHttpRequest.createPost(LOGIN_CHECK_URL);
		shr.createBody(transData);
		String resultGet = shr.sendGetString();

		LoggerUtils.info("pyWan login transData = ", transData);
		LoggerUtils.info("pyWan login resultGet = ", resultGet);
		boolean ret = false;
		@SuppressWarnings("unchecked")
		Map<String, Object> rmResult = GsonFactory.getDefault().fromJson(resultGet, Map.class);
		if (rmResult != null) {
			double ack = (Double) rmResult.get("ack");
			if (ack == 200) {
				ret = true;
			}
		}
		return new PlatformRetData(ret, null);
	}

	public static void orderCheck() {

		String cp_orderid = "18964e9183004f148e9cf1506af387b4";
		String ch_orderid = "S1708026N2217868";
		String amount = "1.00";
		String sign = "8122cc08ab4a4a2ec4d2e8313ded53a4";

		String signSource = PAY_KEY + cp_orderid + ch_orderid + amount;
		LoggerUtils.info("pyWan payCallback signSource=", signSource);
		String signRet = FsGameLoginUtils.md5low(signSource);
		LoggerUtils.info("pyWan payCallback signRet=", signRet);

		int ack = 0;
		String msg = "";
		Map<String, Object> ret = new HashMap<String, Object>();
		if (StringUtils.equals(signRet, sign)) {

		} else {
			msg = "sign error";
		}

		ret.put("ack", ack);
		ret.put("msg", msg);

		String retJson = GsonFactory.getDefault().toJson(ret);
		LoggerUtils.info("pyWan payCallback retJson=", retJson);
	}

	public static void loginCheck() {
		long tid = System.currentTimeMillis();
		String uid = "1b8cd59682285ba40a538175610b81ba";
		String token = "adb7303ee412fbdc6fd1687ec810b5b4";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tid", tid);
		params.put("token", token);
		params.put("uid", uid);

		String transData = GsonFactory.getDefault().toJson(params);
		SimpleHttpRequest shr = SimpleHttpRequest.createPost(LOGIN_CHECK_URL);
		shr.createBody(transData);
		String resultGet = shr.sendGetString();
		LoggerUtils.info("pyWan login transData = ", transData);
		LoggerUtils.info("pyWan login resultGet = ", resultGet);
		boolean ret = false;
		@SuppressWarnings("unchecked")
		Map<String, Object> rmResult = GsonFactory.getDefault().fromJson(resultGet, Map.class);
		if (rmResult != null) {
			double ack = (Double) rmResult.get("ack");
			if (ack == 200) {
				ret = true;
			}
		}
	}

	public static void main(String[] agrs) {
		orderCheck();
	}

}