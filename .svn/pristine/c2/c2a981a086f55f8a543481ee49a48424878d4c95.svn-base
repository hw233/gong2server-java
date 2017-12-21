package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.gamejelly.game.gong2.login.meta.PlatformPayRecord;
import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.service.sdk.SdkGroup;
import com.gamejelly.game.gong2.login.service.sdk.SdkService;
import com.gamejelly.game.gong2.login.service.sdk.impl.kuaikan.KuaikanSignUtil;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.gamejelly.game.gong2.login.utils.SdkConst;
import com.hadoit.game.common.framework.http.SimpleHttpRequest;
import com.hadoit.game.common.lang.DataUtils;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

@SdkGroup({ SdkConst.CC_SDK_ANROID_KUAIKAN })
@Component("sdkKuaiKanService")
public class SdkKuaiKanService extends SdkBaseService implements SdkService {

	private static String AppID = "1043";
	private static String AppSecret = "lYRg21s7a43ROkMQIjT1eG7wGRGFqoot";

	private static String BaseUrl = "http://api.kkmh.com";
	private static String AuthUrl = "/v1/game/oauth/check_open_id";
	private static String UserUrl = "/v1/game/oauth/user_info";
	private static String OrderQueryUrl = "/v2/game_pay/query_order";

	public static boolean checkSign(String key, String sign, Map<String, Object> paramMap) {
		if (CollectionUtils.isEmpty(paramMap)) {
			return false;
		}
		if (StringUtils.isBlank(sign)) {
			return false;
		}
		String checkSign = KuaikanSignUtil.getSign(key, paramMap, false);
		if (StringUtils.isBlank(checkSign)) {
			return false;
		}
		if (!sign.equals(checkSign)) {
			return false;
		}
		return true;
	}

	@Override
	public Object payCallback(Map<String, Object> requestParams) {

		// payCallback {sign=+Xg4nrxT7qUisqWmoKRWAQ==,
		// response=org.tuckey.web.filters.urlrewrite.UrlRewriteWrappedResponse@3101a159,
		// trans_data={"wares_id":1,"pay_status":2,"trans_result":1,"out_order_id":"b8c634fa51b54d169e4291224145a61a","trans_money":6.0,"trans_id":"32061707271429142807","currency":"RMB","pay_type":403,"trans_time":1501136980000,"open_uid":"28488851043","order_id":"15328438756012032028488851043","app_id":"1043"},
		// sdkId=148,
		// request=org.apache.catalina.connector.RequestFacade@3258dba3}

		LoggerUtils.info("kuaikan payCallback1 ", requestParams);
		String transdata = DataUtils.getMapString(requestParams, "trans_data", "");
		String signData = DataUtils.getMapString(requestParams, "sign", "");
		String result = "FAILURE";

		Map<String, Object> transData = GsonFactory.getDefault().fromJson(transdata, Map.class);
		if (transData == null) {
			return result;
		}

		String app_id = (String) transData.get("app_id");
		String out_order_id = (String) transData.get("out_order_id");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("app_id", app_id);
		params.put("out_order_id", out_order_id);

		String sign = KuaikanSignUtil.getSign(AppSecret, params, true);

		String data = "trans_data=" + GsonFactory.getDefault().toJson(params) + "&sign=" + sign;
		String getUrl = BaseUrl + OrderQueryUrl;

		// {code=200.0,
		// data={trans_data={"wares_id":1,"pay_status":3,"out_order_id":"a0a840e904e247308416d04cc089d541","trans_money":0.0,"trans_id":"32051707191727093405","currency":"RMB","pay_type":0,"trans_time":0,"open_uid":"a0a840e904e247308416d04cc089d541","order_id":"15043006044113305627640361043","app_id":20},
		// sign=wNz6WCi78zQ+xKO3Sa0G/A==}, message=ok}

		SimpleHttpRequest shr = SimpleHttpRequest.createPost(getUrl);
		shr.createBody(data);
		shr.setHeader("Content-Type", "application/x-www-form-urlencoded");
		shr.setCharset("utf-8");
		String resultGet = shr.sendGetString();
		LoggerUtils.info("kuaikan query order resultGet = ", resultGet);
		@SuppressWarnings("unchecked")
		Map<String, Object> rmResult = GsonFactory.getDefault().fromJson(resultGet, Map.class);
		if (rmResult != null) {
			LoggerUtils.info("kuaikan query order rmResult = ", rmResult);
			double codeRet = (Double) rmResult.get("code");
			if (codeRet == 200) {
				Map<String, Object> dataResult = (Map<String, Object>) rmResult.get("data");
				if (dataResult != null) {
					String trans_data = (String) dataResult.get("trans_data");
					Map<String, Object> transdataResult = GsonFactory.getDefault().fromJson(trans_data, Map.class);

					// Map<String, Object> transdataResult = (Map<String,
					// Object>) dataResult.get("trans_data");
					if (transdataResult != null) {
						String out_trade_no = (String) transdataResult.get("out_order_id");
						PlatformPayRecord ppr = platformPayRecordService.getByOrderSn(out_trade_no);
						if (ppr != null) {
							double payStatus = (Double) transdataResult.get("pay_status");
							// 1-WAIT_BUYER_PAY 2- SUCCESS 3-CLOSED
							if (payStatus == 2) {
								double trans_money = (Double) transdataResult.get("trans_money");
								processOrder(out_trade_no, String.valueOf(trans_money), true);
								result = "SUCCESS";
							}
						}
					}
				}
			}

		}
		return result;
	}

	@Override
	public PlatformRetData handleSdkData(Map<String, Object> requestParams) {

		LoggerUtils.info("kuaikan handleSdkData:", requestParams);

		String orderId = FsGameLoginUtils.getMapString(requestParams, "orderId", "");
		String orderName = FsGameLoginUtils.getMapString(requestParams, "orderName", "");
		String notifyUrl = FsGameLoginUtils.getMapString(requestParams, "notifyUrl", "");
		String appId = FsGameLoginUtils.getMapString(requestParams, "appId", "");
		String openid = FsGameLoginUtils.getMapString(requestParams, "openId", "");
		int waresId = FsGameLoginUtils.getMapInteger(requestParams, "waresId", 1);
		PlatformPayRecord ppr = platformPayRecordService.getByOrderSn(orderId);
		if (ppr == null) {
			return new PlatformRetData(false, null);
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("app_id", appId);
		params.put("out_order_id", orderId);
		params.put("out_notify_url", notifyUrl);
		params.put("open_uid", openid);
		params.put("wares_id", waresId);
		String sign = KuaikanSignUtil.getSign(AppSecret, params, false);

		String transData = GsonFactory.getDefault().toJson(params);

		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("transData", transData);
		ret.put("transSercet", sign);

		LoggerUtils.info("kuaikan handleSdkData sign = ", sign, " transData=", transData);
		return new PlatformRetData(true, ret);
	}

	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {

		String openid = FsGameLoginUtils.getMapString(requestParams, "Uin", "");
		String access_token = FsGameLoginUtils.getMapString(requestParams, "SessionId", "");
		// boolean isNewAccount = FsGameLoginUtils.getMapBoolean(requestParams,
		// "isNewAccount", false);
		// isNewAccount false 游客 true 为注册账户 游戏不需要验证第二步

		// {"code":200,"data":{"access_token":"8c6e876bbd6529336be491a4f54eff02","aibei_app_id":"301085831",
		// "avatar_url":"http://f2.kkmh.com/default_avatar_image.jpg-w180","open_id":"27633281043","is_new_account":true,
		// "nickname":"游客S:73996f2e49f9da65"},"message":"ok"}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("app_id", AppID);
		params.put("open_id", openid);
		params.put("access_token", access_token);

		LoggerUtils.info("kuaikan login ", "open_id=", openid, "access_token=", access_token);
		String sign = KuaikanSignUtil.getSign(AppSecret, params, true);

		LoggerUtils.info("kuaikan login ", "sign=", sign);

		String endUrl = BaseUrl + AuthUrl + "?" + "app_id=" + AppID + "&" + "open_id=" + openid + "&" + "access_token="
				+ access_token + "&" + "sign=" + sign;

		LoggerUtils.info("kuaikan url", endUrl);
		SimpleHttpRequest shr = SimpleHttpRequest.createGet(endUrl);
		String resultGet = shr.sendGetString();
		LoggerUtils.info("kuaikan login result =", resultGet);
		boolean openIdRet = false;

		Map<String, Object> rmResult = GsonFactory.getDefault().fromJson(resultGet, Map.class);
		if (rmResult != null) {
			if (rmResult.containsKey("data")) {
				Map<String, Object> dataResult = (Map<String, Object>) rmResult.get("data");
				if (dataResult != null && dataResult.containsKey("ret")) {
					if ((Boolean) dataResult.get("ret")) {
						openIdRet = true;
					}
				}
			}
		}

		boolean result = false;
		// if (openIdRet) {
		// String userUrl = BaseUrl + UserUrl + "?" + "access_token=" +
		// access_token;
		// SimpleHttpRequest shUser = SimpleHttpRequest.createGet(userUrl);
		// String rmUserStr = shUser.sendGetString();
		// LoggerUtils.info("kuaikan login rmUserStr =", rmUserStr);
		// Map<String, Object> rmUser =
		// GsonFactory.getDefault().fromJson(rmUserStr, Map.class);
		// double codeDouble = (Double) rmUser.get("code");
		// int code = (int) codeDouble;
		// if (code == 200) {
		// result = true;
		// }
		// requestParams.put("name", openid);
		// }

		if (openIdRet) {
			result = true;
			requestParams.put("name", openid);
		}

		return new PlatformRetData(result, null);
	}

	public static void orderCheck() {

		String getUrl = BaseUrl + OrderQueryUrl;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("app_id", "1043");
		params.put("out_order_id", "315558be1126477291aa2786c06a3d0e");
		// params.put("out_notify_url",
		// "http://gongloginaz.gamejelly.cn/gonglogin/sdk/outter/payCallback/148");
		// params.put("open_uid", "28488851043");
		// params.put("wares_id", 1);
		// params.put("trans_id", "32061707271429142807");

		String sign = KuaikanSignUtil.getSign(AppSecret, params, true);
		sign = "&sign=" + sign;

		String endData = "trans_data=" + GsonFactory.getDefault().toJson(params) + sign;
		LoggerUtils.info("kuaikan query order resultG ", "data = ", endData);

		SimpleHttpRequest shr = SimpleHttpRequest.createPost(getUrl);
		shr.createBody(endData);
		shr.setHeader("Content-Type", "application/x-www-form-urlencoded");
		shr.setCharset("utf-8");
		String resultGet = shr.sendGetString();
		LoggerUtils.info("kuaikan query order resultGet = ", resultGet);
		Map<String, Object> rmResult = GsonFactory.getDefault().fromJson(resultGet, Map.class);
		if (rmResult != null) {
			LoggerUtils.info("kuaikan query order rmResult = ", rmResult);
		}
	}

	public static void main(String[] args) throws ClientProtocolException, IOException {

		orderCheck();

		// String getUrl = BaseUrl + OrderQueryUrl;
		//
		// Map<String, Object> params = new HashMap<String, Object>();
		// params.put("app_id", "1043");
		// params.put("out_order_id", "a0a840e904e247308416d04cc089d541");
		// params.put("out_notify_url",
		// "http://112.4.91.14:4400/gonglogin/sdk/outter/payCallback/148");
		// params.put("open_uid", "27640361043");
		// params.put("wares_id", 1);
		//
		// String signRet = "&sign=Uu3U4gipyKCQYha1ZU5LUg==";
		// String endData = "trans_data=" +
		// GsonFactory.getDefault().toJson(params) + signRet;
		// LoggerUtils.info("kuaikan query order resultG ", "data = ", endData);
		// // trans_data=
		// //
		// {"wares_id":1,"out_notify_url":"http://112.4.91.14:4400/gonglogin/sdk/outter/payCallback/148","out_order_id":"a0a840e904e247308416d04cc089d541","open_uid":"27640361043","app_id":"1043"}&sign=Uu3U4gipyKCQYha1ZU5LUg==
		//
		// SimpleHttpRequest shr = SimpleHttpRequest.createPost(getUrl);
		// shr.createBody(endData);
		// shr.setHeader("Content-Type", "application/x-www-form-urlencoded");
		// shr.setCharset("utf-8");
		// String resultGet = shr.sendGetString();
		// LoggerUtils.info("kuaikan query order resultGet = ", resultGet);
		// Map<String, Object> rmResult =
		// GsonFactory.getDefault().fromJson(resultGet, Map.class);
		// if (rmResult != null) {
		// LoggerUtils.info("kuaikan query order rmResult = ", rmResult);
		// }
	}

}
