package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.meta.PlatformPayRecord;
import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.service.sdk.SdkGroup;
import com.gamejelly.game.gong2.login.service.sdk.SdkService;
import com.gamejelly.game.gong2.login.utils.FsGameLoginConst;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.gamejelly.game.gong2.login.utils.SdkConst;
import com.google.gson.reflect.TypeToken;
import com.hadoit.game.common.framework.http.SimpleHttpRequest;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

class HmacSHA1Encryption {
	private static final String MAC_NAME = "HmacSHA1";
	private static final String ENCODING = "UTF-8";

	/**
	 * 使用 HMAC-SHA1 签名方法对对encryptText进行签名
	 * 
	 * @param encryptText
	 *            被签名的字符串
	 * @param encryptKey
	 *            密钥
	 * @return 返回被加密后的字符串
	 * @throws Exception
	 */
	public static String HmacSHA1Encrypt(String encryptText, String encryptKey) throws Exception {
		byte[] data = encryptKey.getBytes(ENCODING);
		// 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
		SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
		// 生成一个指定 Mac 算法 的 Mac 对象
		Mac mac = Mac.getInstance(MAC_NAME);
		// 用给定密钥初始化 Mac 对象
		mac.init(secretKey);
		byte[] text = encryptText.getBytes(ENCODING);
		// 完成 Mac 操作
		byte[] digest = mac.doFinal(text);
		StringBuilder sBuilder = bytesToHexString(digest);
		return sBuilder.toString();
	}

	/**
	 * 转换成Hex
	 * 
	 * @param bytesArray
	 */
	public static StringBuilder bytesToHexString(byte[] bytesArray) {
		if (bytesArray == null) {
			return null;
		}
		StringBuilder sBuilder = new StringBuilder();
		for (byte b : bytesArray) {
			String hv = String.format("%02x", b);
			sBuilder.append(hv);
		}
		return sBuilder;
	}

	/**
	 * 使用 HMAC-SHA1 签名方法对对encryptText进行签名
	 * 
	 * @param encryptData
	 *            被签名的字符串
	 * @param encryptKey
	 *            密钥
	 * @return 返回被加密后的字符串
	 * @throws Exception
	 */
	public static String hmacSHA1Encrypt(byte[] encryptData, String encryptKey) throws Exception {
		byte[] data = encryptKey.getBytes(ENCODING);
		// 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
		SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
		// 生成一个指定 Mac 算法 的 Mac 对象
		Mac mac = Mac.getInstance(MAC_NAME);
		// 用给定密钥初始化 Mac 对象
		mac.init(secretKey);
		// 完成 Mac 操作
		byte[] digest = mac.doFinal(encryptData);
		StringBuilder sBuilder = bytesToHexString(digest);
		return sBuilder.toString();
	}
}

@SdkGroup({ SdkConst.CC_SDK_ANDROID_MI })
@Component("sdkMiService")
public class SdkMiService extends SdkBaseService implements SdkService {

	private final String appId = "2882303761517536161";
	private final String appKey = "5371753676161";
	private final String appSecret = "7HAuIsLkDP0oO4Y1Fhzl/Q==";

	private final String tokenCheckUrl = "http://mis.migc.xiaomi.com/api/biz/service/verifySession.do";
	private final String queryOrderUrl = "http://mis.migc.xiaomi.com/api/biz/service/queryOrder.do";

	public final int CODE_SUCCESS = 200;
	public final int CODE_SYS_ERROR = 1001;
	public final int CODE_SIGN_ERROR = 1525;

	private void transferParams(Map<String, Object> requestParams) {
		if (requestParams == null) {
			return;
		}
		try {
			for (Map.Entry<String, Object> e : requestParams.entrySet()) {
				if (e.getValue() instanceof String) {
					String v = e.getValue().toString();
					if (v.equals(new String(v.getBytes("iso8859-1"), "iso8859-1"))) {
						e.setValue(FsGameLoginUtils.decodeParameter(v, "utf-8"));
					}
				}
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		LoggerUtils.info("mi payCallback before", requestParams);
		// 先转一下编码
		transferParams(requestParams);

		Map<String, String> signParams = new HashMap<String, String>();
		for (Map.Entry<String, Object> entry : requestParams.entrySet()) {
			if (!"signature".equals(entry.getKey()) && !"request".equals(entry.getKey())
					&& !"response".equals(entry.getKey()) && (!"sdkId".equals(entry.getKey()))) {
				signParams.put(entry.getKey(), entry.getValue().toString());
			}
		}

		LoggerUtils.info("mi payCallback", requestParams);

		int resultCode = CODE_SUCCESS;

		try {
			String tmpSign = getSign(signParams);
			String sign = (String) requestParams.get("signature");

			if (tmpSign.equals(sign)) {
				processOrder((String) requestParams.get("cpOrderId"), (String) requestParams.get("payFee"), false);
				resultCode = CODE_SUCCESS;
			} else {
				resultCode = CODE_SIGN_ERROR;
			}
		} catch (Exception e) {
			LoggerUtils.error(e);
			resultCode = CODE_SYS_ERROR;
		}

		Map<String, Integer> resultObj = new HashMap<String, Integer>();
		resultObj.put("errcode", resultCode);

		return resultObj;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {
		String Uin = FsGameLoginUtils.getMapString(requestParams, "Uin", "");
		String SessionId = FsGameLoginUtils.getMapString(requestParams, "SessionId", "");

		Map<String, String> params = new HashMap<String, String>();
		params.put("appId", appId);
		params.put("session", SessionId);
		params.put("uid", Uin);

		boolean result = false;
		try {
			String requestUrl = getRequestUrl(params, tokenCheckUrl);
			SimpleHttpRequest shr = SimpleHttpRequest.createGet(requestUrl.toString());
			String resultGet = shr.sendGetString();

			if (resultGet != null) {
				Map<String, String> rm = GsonFactory.getDefault().fromJson(resultGet, Map.class);
				int miLoginErrCode = FsGameLoginUtils.getMapInteger(rm, "errcode", 0);
				if (miLoginErrCode == CODE_SUCCESS) {
					result = true;
				} else {
					result = false;
				}
			} else {
				result = false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new PlatformRetData(result, null);
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

	private int queryPayResult(Map<String, Object> requestParams) throws Exception {
		String orderSn = FsGameLoginUtils.getMapString(requestParams, "CooOrderSerial", "");
		PlatformPayRecord ppr = platformPayRecordService.getByOrderSn(orderSn);
		if (ppr == null) {
			return 0;
		}

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("appId", appId);
		params.put("cpOrderId", orderSn);
		params.put("uid", FsGameLoginUtils.decompAccountName(ppr.getAccountName()));

		try {
			String requestUrl = getRequestUrl(params, queryOrderUrl);
			SimpleHttpRequest shr = SimpleHttpRequest.createGet(requestUrl.toString());
			String resultGet = shr.sendGetString();
			if (resultGet != null) {
				Map<String, String> rm = GsonFactory.getDefault().fromJson(resultGet,
						new TypeToken<Map<String, String>>() {
						}.getType());

				if (rm != null) {
					if (rm.containsKey("errcode")) {
						return 0;
					} else {
						// Map<String, String> signParams = new HashMap<String,
						// String>();
						// for (Map.Entry<String, String> entry : rm.entrySet())
						// {
						// if (!"signature".equals(entry.getKey())) {
						// signParams.put(entry.getKey(),
						// URLDecoder.decode(String.valueOf(entry.getValue()),
						// "utf-8"));
						// }
						// }
						//
						// String tmpSign = getSign(signParams);
						// String sign = (String) rm.get("signature");
						// if (tmpSign == null || sign == null) {
						// return 0;
						// } else {
						// if (!tmpSign.equals(sign)) {
						// return 0;
						// }
						// }

						if (rm.containsKey("orderStatus") && rm.get("orderStatus") != null) {
							String type = (String) rm.get("orderStatus");
							if (type.equals("TRADE_SUCCESS")) {
								return 1;
							}
						}
					}
				} else {
					return 0;
				}
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * 获取得到排序好的查询字符串
	 * 
	 * @param params
	 *            请求参数
	 * @param isContainSignature
	 *            是否包含signature参数
	 * @return
	 */
	public String getSortQueryString(Map<String, String> params) throws Exception {

		Object[] keys = params.keySet().toArray();
		Arrays.sort(keys);
		StringBuffer sb = new StringBuffer();
		for (Object key : keys) {
			sb.append(String.valueOf(key)).append("=").append(params.get(String.valueOf(key))).append("&");
		}

		String text = sb.toString();
		if (text.endsWith("&")) {
			text = text.substring(0, text.length() - 1);
		}
		return text;
	}

	private String getRequestUrl(Map<String, String> params, String baseUrl) throws Exception {
		String signString = getSortQueryString(params);
		String signature = getSign(params);
		return baseUrl + "?" + signString + "&signature=" + signature;
	}

	private String getSign(Map<String, String> params) throws Exception {
		String signString = getSortQueryString(params);
		return HmacSHA1Encryption.HmacSHA1Encrypt(signString, appSecret);
	}
}
