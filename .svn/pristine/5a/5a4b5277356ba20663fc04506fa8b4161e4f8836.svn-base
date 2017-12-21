package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.service.sdk.SdkGroup;
import com.gamejelly.game.gong2.login.service.sdk.SdkService;
import com.gamejelly.game.gong2.login.service.sdk.impl.nubia.ParameterUtil;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.gamejelly.game.gong2.login.utils.SdkConst;
import com.hadoit.game.common.framework.http.SimpleHttpRequest;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

@SdkGroup({ SdkConst.CC_SDK_ANROID_GUAIMAO })
@Component("sdkGuaiMaoService")
public class SdkGuaiMaoService extends SdkBaseService implements SdkService {

	private static String APP_ID = "1169";
	private static String SECRET_KEY = "MBYwQWZZJK7Z7Ei7";
	private static String CHECK_URL = "http://m.gm88.com/api/index.php";

	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		LoggerUtils.info("guaimao payCallback", requestParams);
		String order_id = FsGameLoginUtils.getMapString(requestParams, "order_id", "");
		String server_id = FsGameLoginUtils.getMapString(requestParams, "server_id", "");
		String role_id = FsGameLoginUtils.getMapString(requestParams, "role_id", "");
		String developerinfo = FsGameLoginUtils.getMapString(requestParams, "developerinfo", "");
		String coin = FsGameLoginUtils.getMapString(requestParams, "coin", "");
		String signature = FsGameLoginUtils.getMapString(requestParams, "signature", "");

		// 以上字段都参与签名，签名方式为MD5，签名串规则为action=verify&game_id=xxx&order_id=xxx&支付通知密钥
		String result = "FAILURE";

		String signSource = "action=verify&" + "game_id=" + APP_ID + "&order_id=" + order_id + "&" + SECRET_KEY;
		String signRet = FsGameLoginUtils.md5low(signSource);

		LoggerUtils.info("guaimao payCallback signSource = ", signSource);
		LoggerUtils.info("guaimao payCallback signRet = ", signRet);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("action", "verify");
		params.put("game_id", APP_ID);
		params.put("order_id", order_id);
		params.put("signature", signRet);

		String verifyData = ParameterUtil.getSignData(params);
		String endUrl = CHECK_URL + "?" + verifyData;
		SimpleHttpRequest shr = SimpleHttpRequest.createPost(endUrl);
		String resultGet = shr.sendGetString();
		LoggerUtils.info("guaimao query order resultGet = ", resultGet);
		Map<String, Object> rmResult = GsonFactory.getDefault().fromJson(resultGet, Map.class);
		if (rmResult != null) {
			boolean statues = (Boolean) rmResult.get("status");
			if (statues) {
				Map<String, Object> dataResult = (Map<String, Object>) rmResult.get("data");
				if (dataResult != null) {
					String dataStatus = (String) dataResult.get("status");
					if (StringUtils.equals("Y", dataStatus)) {
						processOrder(developerinfo, coin, true);
						result = "ok";
					}
				}
			}

		}

		return result;
	}

	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {

		LoggerUtils.info("guaimao loginAuth", requestParams);

		String uid = FsGameLoginUtils.getMapString(requestParams, "Uin", "");
		String token = FsGameLoginUtils.getMapString(requestParams, "SessionId", "");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("token", token);
		params.put("action", "user.info");

		String verifyData = ParameterUtil.getSignData(params);

		boolean ret = false;
		String endUrl = CHECK_URL + "?" + verifyData;
		SimpleHttpRequest shr = SimpleHttpRequest.createPost(endUrl);
		String resultGet = shr.sendGetString();
		LoggerUtils.info("guaimao login endUrl = ", endUrl);
		LoggerUtils.info("guaimao login resultGet = ", resultGet);

		Map<String, Object> rmResult = GsonFactory.getDefault().fromJson(resultGet, Map.class);
		if (rmResult != null) {
			LoggerUtils.info("guaimao login  rmResult = ", rmResult);
			String uidRet = (String) rmResult.get("uid");
			boolean status = (Boolean) rmResult.get("status");
			if (StringUtils.equals(uidRet, uid) && status) {
				ret = true;
			}
		}

		return new PlatformRetData(ret, null);
	}

	public static void orderCheck() {

		String order_id = "2017073101116900002";
		String server_id = "2";
		String role_id = "2";
		String developerinfo = "developerinfo1501508660453";
		String coin = "0.10";
		String signature = "";

		// 以上字段都参与签名，签名方式为MD5，签名串规则为action=verify&game_id=xxx&order_id=xxx&支付通知密钥
		String result = "FAILURE";

		String signSource = "action=verify&" + "game_id=" + APP_ID + "&order_id=" + order_id + "&" + SECRET_KEY;
		String signRet = FsGameLoginUtils.md5low(signSource);

		LoggerUtils.info("guaimao payCallback signSource = ", signSource);
		LoggerUtils.info("guaimao payCallback signRet = ", signRet);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("action", "verify");
		params.put("game_id", APP_ID);
		params.put("order_id", order_id);
		params.put("signature", signRet);

		String verifyData = ParameterUtil.getSignData(params);
		String endUrl = CHECK_URL + "?" + verifyData;
		SimpleHttpRequest shr = SimpleHttpRequest.createPost(endUrl);
		String resultGet = shr.sendGetString();
		LoggerUtils.info("guaimao query order resultGet = ", resultGet);
		Map<String, Object> rmResult = GsonFactory.getDefault().fromJson(resultGet, Map.class);
		if (rmResult != null) {
			boolean statues = (Boolean) rmResult.get("status");
			if (statues) {
				Map<String, Object> dataResult = (Map<String, Object>) rmResult.get("data");
				if (dataResult != null) {
					String dataStatus = (String) dataResult.get("status");
					if (StringUtils.equals("Y", dataStatus)) {
						result = "ok";
					}
				}

			}

		}

	}

	public static void loginCheck() {
		String SessionId = "YWY0NzJYY0RGRHBHRGlHV3BZR21NRjlGaG5YM3doNHNCYk5PU0wyTWpzY1U2QjR2UTBhWFdtckh0RVBCOVZ4ai1QLTVYY3JMVy1QLUh2WWxpeS1TLTNNVEFSQUtj";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("token", SessionId);
		params.put("action", "user.info");
		String uid = "1548287";

		String verifyData = ParameterUtil.getSignData(params);
		LoggerUtils.info("guaimao login verifyData = ", verifyData);

		String endUrl = CHECK_URL + "?" + verifyData;
		SimpleHttpRequest shr = SimpleHttpRequest.createPost(endUrl);
		LoggerUtils.info("guaimao login endUrl = ", endUrl);
		String resultGet = shr.sendGetString();
		LoggerUtils.info("guaimao login resultGet = ", resultGet);

		Map<String, Object> rmResult = GsonFactory.getDefault().fromJson(resultGet, Map.class);
		if (rmResult != null) {
			LoggerUtils.info("guaimao login  rmResult = ", rmResult);
			String uidRet = (String) rmResult.get("uid");
			if (StringUtils.equals(uidRet, uid)) {

			}
		}
	}

	public static void main(String[] args) {
		// loginCheck();
		orderCheck();
	}
}
