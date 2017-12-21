package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.meta.PlatformPayRecord;
import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.service.sdk.SdkGroup;
import com.gamejelly.game.gong2.login.service.sdk.SdkService;
import com.gamejelly.game.gong2.login.utils.FsGameLoginConst;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.gamejelly.game.gong2.login.utils.SdkConst;
import com.hadoit.game.common.framework.http.SimpleHttpRequest;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

@SdkGroup({ SdkConst.CC_SDK_ANDROID_360 })
@Component("sdk360Service")
public class Sdk360Service extends SdkBaseService implements SdkService {

	private String mAppId = "203372361";
	private String mAppKey = "79546e6f2bcdae35793880a2ccfe1cad";
	private String mAppSecret = "ea73f69da6b93dbb253ceaaa5f6f8967";

	private static final String VERIFY_URL = "http://mgame.360.cn/pay/order_verify.json";
	private static final String VERIFIED = "verified";

	/**
	 * 平台回调
	 * 
	 * @param requestParams
	 * @return
	 */
	public Object payCallback(Map<String, Object> params) {
		LoggerUtils.info("360 payCallback", params);

		if (!this._isValidRequest(params)) {
			return "invalid request";
		}

		if (!this._verifyOrder(params)) {
			return "verify failed";
		}

		String app_key = FsGameLoginUtils.getMapString(params, "app_key", "");
		String product_id = FsGameLoginUtils.getMapString(params, "product_id", "");
		String order_id = FsGameLoginUtils.getMapString(params, "order_id", ""); // 360自己的订单号
		String gateway_flag = FsGameLoginUtils.getMapString(params, "gateway_flag", "");
		String app_order_id = FsGameLoginUtils.getMapString(params, "app_order_id", ""); // 游戏生成的订单号
		String sign_return = FsGameLoginUtils.getMapString(params, "sign_return", "");

		if (!app_key.equals(mAppKey)) {
			return "verify failed";
		}

		platformPayRecordService.updateExtData(order_id, sign_return, app_order_id);

		String ret;
		try {
			if ("success".equals(gateway_flag)) { // 充值结果成功
				processOrder(app_order_id, FsGameLoginUtils.getMapString(params, "amount", ""), false);
				ret = "ok";
			} else { // 充值结果失败
				ret = "error";
			}
		} catch (Exception e) {
			LoggerUtils.error(e);
			ret = "net error";
		}

		return ret; // 回应的 ok 表示应 用已经正常接到消息, 无需继续发送通知
	}

	/**
	 * 向360服务器发起请求验证订单是否有效
	 * 
	 * @param params
	 * @return Boolean 是否有效
	 */
	@SuppressWarnings("unchecked")
	private Boolean _verifyOrder(Map<String, Object> params) {

		String arrFields[] = { "app_key", "product_id", "amount", "app_uid", "order_id", "app_order_id", "sign_type",
				"sign_return" };

		HashMap<String, String> mapRequest = new HashMap();
		List<String> fields = new ArrayList<String>(Arrays.asList(arrFields));
		for (String f : fields) {
			if (params.containsKey(f)) {
				mapRequest.put(f, (String) params.get(f));
			}
		}

		mapRequest.put("sign", getSign(params, this.mAppSecret, arrFields));

		String ret;
		try {
			ret = requestUrl(VERIFY_URL, mapRequest);
		} catch (IOException e) {
			return false;
		} catch (Exception e1) {
			return false;
		}

		try {
			Boolean verified;
			Map<String, String> rm = GsonFactory.getDefault().fromJson(ret, Map.class);
			String strRet = FsGameLoginUtils.getMapString(rm, "ret", "");
			verified = strRet.equals(VERIFIED);
			if (!verified) {
				//
			}
			return verified;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 检查request完整性
	 * 
	 * @param params
	 * @return Boolean
	 */
	private Boolean _isValidRequest(Map params) {

		String arrFields[] = { "app_key", "product_id", "app_uid", "order_id", "sign_type", "gateway_flag", "sign",
				"sign_return", "amount" };

		ArrayList fields = new ArrayList(Arrays.asList(arrFields));

		String key;
		String value;
		Iterator iterator = fields.iterator();
		while (iterator.hasNext()) {
			key = (String) iterator.next();
			value = (String) params.get(key);
			if (value == null || value.equals("")) {
				return false;
			}
		}

		if (!params.get("app_key").equals(this.mAppKey)) {
			return false;
		}

		String arrFieldsSign[] = { "app_key", "product_id", "amount", "app_uid", "app_ext1", "app_ext2", "user_id",
				"order_id", "gateway_flag", "sign_type", "app_order_id" };
		String sign = getSign(params, this.mAppSecret, arrFieldsSign);
		String paramSign = (String) params.get("sign");
		return sign.equals(paramSign);
	}

	/**
	 * 签名计算
	 * 
	 * @param params
	 * @param appSecret
	 * @return
	 */
	public String getSign(Map params, String appSecret, String arrFields[]) {
		HashMap<String, String> mapSign = new HashMap();
		List<String> fields = new ArrayList<String>(Arrays.asList(arrFields));
		for (String f : fields) {
			if (params.containsKey(f)) {
				mapSign.put(f, (String) params.get(f));
			}
		}

		Object[] keys = mapSign.keySet().toArray();
		Arrays.sort(keys);
		String k, v;

		String str = "";
		for (int i = 0; i < keys.length; i++) {
			k = (String) keys[i];
			if (k.equals("sign")) {
				continue;
			}
			if (!params.containsKey(k)) {
				continue;
			}
			v = (String) params.get(k);

			if (v == null || v.equals("0") || v.equals("")) {
				continue;
			}
			str += v + "#";
		}
		return FsGameLoginUtils.md5low(str + appSecret);
	}

	/**
	 * http请求
	 * 
	 * @param url
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public static String requestUrl(String url, HashMap<String, String> data) throws IOException {

		HttpURLConnection conn;
		try {
			// if GET....
			// URL requestUrl = new URL(url + "?" + httpBuildQuery(data));
			URL requestUrl = new URL(url);
			conn = (HttpURLConnection) requestUrl.openConnection();
		} catch (MalformedURLException e) {
			return e.getMessage();
		}

		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		conn.setDoInput(true);
		conn.setDoOutput(true);

		PrintWriter writer = new PrintWriter(conn.getOutputStream());
		writer.print(httpBuildQuery(data));
		writer.flush();
		writer.close();

		String line;
		BufferedReader bufferedReader;
		StringBuilder sb = new StringBuilder();
		InputStreamReader streamReader = null;
		try {
			streamReader = new InputStreamReader(conn.getInputStream(), "UTF-8");
		} catch (IOException e) {
			/*
			 * Boolean ret2 = true; if (ret2) { return e.getMessage(); }
			 */
			streamReader = new InputStreamReader(conn.getErrorStream(), "UTF-8");
		} finally {
			if (streamReader != null) {
				bufferedReader = new BufferedReader(streamReader);
				sb = new StringBuilder();
				while ((line = bufferedReader.readLine()) != null) {
					sb.append(line);
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 参数编码
	 * 
	 * @param data
	 * @return
	 */
	public static String httpBuildQuery(HashMap<String, String> data) {
		String ret = "";
		String k, v;
		Iterator<String> iterator = data.keySet().iterator();
		while (iterator.hasNext()) {
			k = iterator.next();
			v = data.get(k);
			try {
				ret += URLEncoder.encode(k, "utf8") + "=" + URLEncoder.encode(v, "utf8");
			} catch (UnsupportedEncodingException e) {
			}
			ret += "&";
		}
		return ret.substring(0, ret.length() - 1);
	}

	/**
	 * 登录
	 * 
	 * @param requestParams
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {

		String SessionId = FsGameLoginUtils.getMapString(requestParams, "SessionId", "");

		String strUrl = "https://open.mgame.360.cn/user/me.json?access_token=%s&fields=id,name,avatar,sex,area";
		String getUserDataUrl = String.format(strUrl, SessionId);
		LoggerUtils.info("login check id url =", getUserDataUrl);

		SimpleHttpRequest shr = SimpleHttpRequest.createGet(getUserDataUrl.toString());
		String result = shr.sendGetString();
		LoggerUtils.info("login result =", result);
		Map<String, String> rm = GsonFactory.getDefault().fromJson(result, Map.class);
		String id = FsGameLoginUtils.getMapString(rm, "id", "");

		if (id == null || id.equals("") || id.equals("0")) {
			return new PlatformRetData(false, rm);
		}

		requestParams.put("name", id);

		return new PlatformRetData(true, rm);
	}

	@Override
	protected boolean verifyOrderInternal(String orderSn) {
		return doVerifyOrder(orderSn) == 1;
	}

	private int doVerifyOrder(String orderSn) {
		int payResult = SdkConst.PAY_RET_STATUS_ERROR;
		try {
			if (queryPayResult(Collections.singletonMap("CooOrderSerial", (Object) orderSn)) == 1) {
				PlatformPayRecord ppr = platformPayRecordService.getByOrderSn(orderSn);
				if (ppr == null) {
					payResult = SdkConst.PAY_RET_STATUS_NO_ORDER; // 无订单
				} else {
					if (ppr.getServerState() == FsGameLoginConst.AS_SERVER_STATE_GIVED_ITEM) {
						payResult = SdkConst.PAY_RET_STATUS_ORDER_COMPLETE; // 给过物品了
					} else {
						platformPayRecordService.updateServerState(ppr.getId(),
								FsGameLoginConst.AS_SERVER_STATE_VERIFIED);
						payResult = SdkConst.PAY_RET_STATUS_SUCCESS; // 有订单
						doGivePayItem(ppr);
					}
				}
			} else {
				payResult = SdkConst.PAY_RET_STATUS_NO_ORDER; // 无订单
			}
		} catch (Exception e) {
			payResult = SdkConst.PAY_RET_STATUS_NET_ERROR; // 自定义：网络问题
			e.printStackTrace();
		}
		return payResult;
	}

	@SuppressWarnings("unchecked")
	private int queryPayResult(Map<String, Object> requestParams) throws Exception {

		String orderSn = FsGameLoginUtils.getMapString(requestParams, "CooOrderSerial", "");
		PlatformPayRecord ppr = platformPayRecordService.getByOrderSn(orderSn);

		HashMap<String, Object> mapParams = new HashMap();
		mapParams.put("app_key", mAppKey);
		mapParams.put("product_id", ppr.getProductId());
		mapParams.put("amount", String.valueOf(ppr.getProductAmount()));
		mapParams.put("app_uid", FsGameLoginUtils.decompAccountName(ppr.getAccountName()));
		mapParams.put("order_id", ppr.getExtData1());
		mapParams.put("app_order_id", orderSn);
		mapParams.put("sign_type", "md5");
		mapParams.put("sign_return", ppr.getExtData2());

		Boolean bResult = _verifyOrder(mapParams);
		if (bResult) {
			return 1;
		}

		return 0;
	}

	public static void main(String[] args) {

		// http://next.gamebox.360.cn/7/role/rolecheck?qid=265204032&package=com.chujian.luanhongsgz.s360&lt=
		// 1475984384&&sign=6df8366ba449452ee0701414f24cd51d

		// http://next.gamebox.360.cn/7/role/rolecheck?qid=15862427105&package=cn.gamejelly.gong.qihoo&lt=1493096247&&sign=e2fff88ed5216fbd4f0df1244f52749b
		// 角色数据自测
		String checkUrl = "http://next.gamebox.360.cn/7/role/rolecheck?";

		// 用户ID
		String qid = "2878484488";
		long lt = System.currentTimeMillis();
		String packAgeName = "cn.gamejelly.gong.qihoo";
		String signSource = "qid=" + qid + "&" + "package=" + packAgeName + "&" + "lt=" + lt;
		String sign = FsGameLoginUtils.md5low(signSource);
		String params = signSource + "&&sign=" + sign;

		String endCheckUrl = checkUrl + params;
		LoggerUtils.info("rolecheck signSource=", signSource);
		LoggerUtils.info("rolecheck sign=", sign);
		LoggerUtils.info("rolecheck endCheckUrl=", endCheckUrl);

		SimpleHttpRequest shr = SimpleHttpRequest.createGet(endCheckUrl.toString());
		String result = shr.sendGetString();
		LoggerUtils.info("rolecheck result=", result);
	}

}
