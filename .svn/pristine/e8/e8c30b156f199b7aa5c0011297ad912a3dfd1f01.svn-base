package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.meta.PlatformPayRecord;
import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.service.sdk.SdkGroup;
import com.gamejelly.game.gong2.login.service.sdk.SdkService;
import com.gamejelly.game.gong2.login.service.sdk.impl.qq.SnsSigCheck;
import com.gamejelly.game.gong2.login.utils.FsGameLoginConst;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.gamejelly.game.gong2.login.utils.SdkConst;
import com.hadoit.game.common.framework.http.SimpleHttpRequest;
import com.hadoit.game.common.lang.DataUtils;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

@SdkGroup({ SdkConst.CC_SDK_ANDROID_QQ })
@Component("sdkQQService")
public class SdkQQService extends SdkBaseService implements SdkService {

	// 测试环境10.194.146.218 msdktest.qq.com
	// 正式环境客户端使用 msdk.qq.com
	//
	// 正式环境（内网IDC）的gameSvr访问MSDK域名：msdk.tencent-cloud.net (TGW)
	// 压测环境：10.217.143.5：8080

	private static String baseUrl = "http://ysdk.qq.com";
	// private static String baseUrl = "http://ysdktest.qq.com";
	private static int qqAppId = 1106119896;
	private static String qqAppKey = "nqbqlBv1drk03Vg5";
	// 沙箱key充值的时候用登陆用正式的
	// private static String payKey = "3WNNlwcvRQ1PeDR1is3HNIGixnMNwkmE"; //测试
	private static String payKey = "mwSN5YzCZ7laffCf4iQcRfuvoaheHEbL";

	private static String wxAppId = "wx4e9cde6a7c20e8d2";
	private static String wxAppKey = "c0662a4add31641bf52706186f1ef567";

	private static String qqAuthUrl = "/auth/qq_check_token";
	private static String wxAuthUrl = "/auth/wx_check_token";

	private static int ePlatform_QQ = 1;
	private static int ePlatform_Weixin = 2;

	private static String balanceQryUri = "/mpay/get_balance_m";
	private static String payUri = "/mpay/pay_m";

	// private static String cancelPayUri = "/mpay/cancel_pay_m";

	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		LoggerUtils.info("qq payCallback", requestParams);
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {
		LoggerUtils.info("qq loginAuth", requestParams);

		String openid = FsGameLoginUtils.getMapString(requestParams, "Uin", "");
		String openkey = FsGameLoginUtils.getMapString(requestParams, "SessionId", "");
		int qqPlatform = FsGameLoginUtils.getMapInteger(requestParams, "qqPlatform", 1);
		String clientIp = FsGameLoginUtils.getMapString(requestParams, "clientIp", "");

		// appid：指游戏在微信或者手Q平台所对应的唯一ID。
		// timestamp：是指当前的标准时间戳（秒）。
		// sig：是加密串。由appid对应的appkey,连接上timestamp参数，md5加密而成32位小写的字符串。
		// $sig = md5 ( $appkey . $timestamp )
		// encode：值必须为1（表示http+json格式）,不可缺少。.
		// conn:表示是否启用长连接。注意，只有gameSvr的请求才可以使用conn=1.客户端的请求请不要使用长连接。
		// msdkExtInfo:表示透传的参数，会在返回的json中带上该透传参数，注意，msdkExtInfo不能带特殊字符，只能由英文字母，数字，下划线组成。
		// openid:表示用户在应用中的唯一标识

		// /auth/verify_login/?timestamp=*&appid=**&sig=***&openid=**&encode=1

		boolean result = false;
		long timestamp = System.currentTimeMillis() / 1000;
		Object retOtherData = null;

		if (qqPlatform == ePlatform_Weixin) {
			String wxSig = FsGameLoginUtils.md5low(wxAppKey + timestamp);
			String url = baseUrl + wxAuthUrl + "?timestamp=" + timestamp + "&appid=" + wxAppId + "&sig=" + wxSig
					+ "&openid=" + openid + "&openkey=" + openkey + "&userip";

			LoggerUtils.info("wx url", url, ", wxSig", wxSig);
			SimpleHttpRequest shr = SimpleHttpRequest.createGet(url);
			// Map<String, Object> m = new HashMap<String, Object>();
			// m.put("accessToken", openkey);
			// m.put("openid", openid);
			// shr.createBody(GsonFactory.getDefault().toJson(m));
			String resultGet = shr.sendGetString();
			LoggerUtils.info("wx resultGet", resultGet);
			Map<String, Object> rm = GsonFactory.getDefault().fromJson(resultGet, Map.class);
			int ret = DataUtils.getMapInteger(rm, "ret", -1);
			retOtherData = DataUtils.getMapString(rm, "msg", "");
			if (ret == 0) {
				result = true;
			}
		} else if (qqPlatform == ePlatform_QQ) {
			// ?timestamp=&appid=&sig=&openid=&openkey=&userip
			// qqAppKey = "nqbqlBv1drk03Vg5";
			String qqSig = FsGameLoginUtils.md5low(qqAppKey + timestamp);
			String url = baseUrl + qqAuthUrl + "?timestamp=" + timestamp + "&appid=" + qqAppId + "&sig=" + qqSig
					+ "&openid=" + openid + "&openkey=" + openkey + "&userip";
			LoggerUtils.info("qq url", url, ", qqSig", qqSig);
			SimpleHttpRequest shr = SimpleHttpRequest.createGet(url);
			// Map<String, Object> m = new HashMap<String, Object>();
			// m.put("appid", qqAppId);
			// m.put("openid", openid);
			// m.put("openkey", openkey);
			// m.put("userip", clientIp);
			// shr.createBody(GsonFactory.getDefault().toJson(m));
			String resultGet = shr.sendGetString();
			LoggerUtils.info("qq resultGet", resultGet);
			Map<String, Object> rm = GsonFactory.getDefault().fromJson(resultGet, Map.class);
			int ret = DataUtils.getMapInteger(rm, "ret", -1);
			retOtherData = DataUtils.getMapString(rm, "msg", "");
			if (ret == 0) {
				result = true;
			}
		}
		return new PlatformRetData(result, retOtherData);
	}

	@SuppressWarnings("unchecked")
	private long getBalance(Map<String, Object> balanceParams) {
		long balance = -1;
		try {
			long ts = System.currentTimeMillis() / 1000;

			// test沙箱环境正式的要注释掉

			String appKey = payKey + "&";
			String uri = balanceQryUri;

			// http下订单请求参数:部分参数是从request中获取，有些是固定的（有可能需要申请），具体以文档为准
			HashMap<String, String> params = new HashMap<String, String>();
			// 必选参数
			params.put("openid", FsGameLoginUtils.getMapString(balanceParams, "openid", ""));
			params.put("openkey", FsGameLoginUtils.getMapString(balanceParams, "openkey", ""));
			params.put("appid", String.valueOf(qqAppId));
			params.put("ts", Long.toString(ts));
			params.put("pf", FsGameLoginUtils.getMapString(balanceParams, "pf", ""));
			params.put("pfkey", FsGameLoginUtils.getMapString(balanceParams, "pfkey", ""));
			params.put("zoneid", "1");
			params.put("format", "json");
			// params.put("userip", FsGameLoginUtils.getMapString(balanceParams,
			// "ip", ""));

			LoggerUtils.info("qq params =", params);
			LoggerUtils.info("qq uri =", uri);

			// 计算签名 SnsSigCheck为官方提供sdk中自带，demo不提供
			String sig = SnsSigCheck.makeSig("get", "/v3/r" + uri, params, appKey);
			params.put("sig", SnsSigCheck.encodeUrl(sig));
			LoggerUtils.info("qq sig =", sig);

			String url = baseUrl + uri + "?" + toParams(params, "&");
			LoggerUtils.info("qq getBalance url", url);

			Map<String, String> cookies = new HashMap<String, String>();
			cookies.put("session_id", FsGameLoginUtils.getMapString(balanceParams, "session_id", ""));
			cookies.put("session_type", FsGameLoginUtils.getMapString(balanceParams, "session_type", ""));
			cookies.put("org_loc", SnsSigCheck.encodeUrl(uri));

			String cookie = toParams(cookies, ";");
			SimpleHttpRequest shr = SimpleHttpRequest.createGet(url);
			shr.addCookie(cookie);
			String ret = shr.sendGetString();

			LoggerUtils.info("qq getBalance ret", ret);

			Map<String, Object> retMap = GsonFactory.getDefault().fromJson(ret, Map.class);
			int retCode = DataUtils.getMapInteger(retMap, "ret", -1);
			if (retCode == 0) {
				// 余额, 游戏币
				balance = DataUtils.getMapLong(retMap, "balance", 0l);
			}
		} catch (Exception e) {
			LoggerUtils.error(e);
		}

		return balance;
	}

	@SuppressWarnings("unchecked")
	private boolean mpay(Map<String, Object> payParams, int amt, String orderSn) {
		boolean result = false;
		try {
			long ts = System.currentTimeMillis() / 1000;
			String appKey = payKey + "&";
			String uri = payUri;

			// http下订单请求参数:部分参数是从request中获取，有些是固定的（有可能需要申请），具体以文档为准
			HashMap<String, String> params = new HashMap<String, String>();
			// 必选参数
			params.put("openid", FsGameLoginUtils.getMapString(payParams, "openid", ""));
			params.put("openkey", FsGameLoginUtils.getMapString(payParams, "openkey", ""));
			params.put("appid", String.valueOf(qqAppId));
			params.put("ts", Long.toString(ts));
			params.put("pf", FsGameLoginUtils.getMapString(payParams, "pf", ""));
			params.put("pfkey", FsGameLoginUtils.getMapString(payParams, "pfkey", ""));
			params.put("zoneid", "1");
			params.put("format", "json");
			params.put("amt", Integer.toString(amt));
			params.put("billno", orderSn);

			// 计算签名 SnsSigCheck为官方提供sdk中自带，demo不提供
			String sig = SnsSigCheck.makeSig("GET", "/v3/r" + uri, params, appKey);
			params.put("sig", SnsSigCheck.encodeUrl(sig));

			String url = baseUrl + uri + "?" + toParams(params, "&");
			LoggerUtils.info("qq mpay url", url);

			Map<String, String> cookies = new HashMap<String, String>();
			cookies.put("session_id", FsGameLoginUtils.getMapString(payParams, "session_id", ""));
			cookies.put("session_type", FsGameLoginUtils.getMapString(payParams, "session_type", ""));
			cookies.put("org_loc", SnsSigCheck.encodeUrl(uri));

			String cookie = toParams(cookies, ";");
			SimpleHttpRequest shr = SimpleHttpRequest.createGet(url);
			shr.addCookie(cookie);
			String ret = shr.sendGetString();

			LoggerUtils.info("qq mpay ret", ret);

			Map<String, Object> retMap = GsonFactory.getDefault().fromJson(ret, Map.class);
			int retCode = DataUtils.getMapInteger(retMap, "ret", -1);
			if (retCode == 0) {
				// 扣费成功
				result = true;
			}
		} catch (Exception e) {
			LoggerUtils.error(e);
		}

		return result;
	}

	@Override
	public PlatformRetData verifyOrder(Map<String, Object> requestParams) {
		LoggerUtils.info("qq verifyOrder", requestParams);
		String orderId = FsGameLoginUtils.getMapString(requestParams, "orderId", "");
		PlatformPayRecord ppr = platformPayRecordService.getByOrderSn(orderId);

		if (ppr == null || ppr.getServerState() == FsGameLoginConst.AS_SERVER_STATE_GIVED_ITEM
				|| ppr.getServerState() == FsGameLoginConst.AS_SERVER_STATE_VERIFIED) {
			return new PlatformRetData(false, -1);
		}
		// 游戏币
		long balance = getBalance(requestParams);
		// 价格是分 除以10之后就是元宝, 和游戏币等价
		BigDecimal productAmountBd = new BigDecimal(Integer.toString(ppr.getProductAmount()))
				.divide((new BigDecimal(Integer.toString(10))));
		int yb = productAmountBd.intValue();
		LoggerUtils.info("qq verifyOrder check balance ", balance, yb, ppr.getProductAmount());

		if (yb > balance) {
			return new PlatformRetData(false, -1);
		}

		boolean result = this.mpay(requestParams, yb, ppr.getOrderSn());
		if (!result) {
			return new PlatformRetData(false, -1);
		}
		processOrder(ppr.getOrderSn(), String.valueOf(ppr.getProductAmount()), false);
		return new PlatformRetData(result, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected boolean verifyOrderInternal(String orderSn, Map<String, Object> requestParams) {
		PlatformPayRecord ppr = platformPayRecordService.getByOrderSn(orderSn);
		if (ppr == null) {
			return false; // 无订单
		} else {
			if (ppr.getServerState() == FsGameLoginConst.AS_SERVER_STATE_GIVED_ITEM) {
				return false; // 给过物品了
			} else {
				Map<String, Object> extraLoginParams = GsonFactory.getDefault()
						.fromJson(FsGameLoginUtils.getMapString(requestParams, "extraLoginParams", ""), Map.class);
				// 游戏币
				long balance = getBalance(extraLoginParams);
				// 价格*10之后就是元宝, 和游戏币等价
				int yb = ppr.getProductAmount() * 10;
				if (yb > balance) {
					return false;
				}
				boolean result = this.mpay(extraLoginParams, yb, ppr.getOrderSn());
				if (!result) {
					return false;
				}
				processOrder(ppr.getOrderSn(), String.valueOf(ppr.getProductAmount()), false);
				return true;
			}
		}
	}

	private static String toParams(Map<String, String> m, String splitter) {
		if (MapUtils.isEmpty(m)) {
			return "";
		}
		Object[] keys = m.keySet().toArray();
		Arrays.sort(keys);
		StringBuilder buffer2 = new StringBuilder();

		for (int i = 0; i < keys.length; i++) {
			buffer2.append(keys[i]).append("=").append(m.get(keys[i]));

			if (i != keys.length - 1) {
				buffer2.append(splitter);
			}
		}

		String ret = buffer2.toString();
		LoggerUtils.info("qq toParams ret", ret);
		return ret;
	}

	public static void main(String[] args) {
		//
		// qq mpay url
		// http://msdktest.qq.com/mpay/pay_m?amt=1&appid=1105319328&billno=7056ad0025f643199212ec1b1deb307c
		// &format=json&openid=olj-mxL_qaJ8XBsUEtVLzL0xwx50&openkey=OezXcEiiBSKSxW0eoylIePXh33wegeByDdoNaZaaJZaVTYl_-QQb3ntuObpJHn3jR2fJgrio0-EQ3DRcQn9pANlrt-_8TfTprJk7zM-hWGw6zCckhfBKka4645iji1B_OpUrrgJlevMciLk5E7l8CQ
		// &pf=wechat_wx-73213123-android-73213123-wx-wxd9a1ba1b45d35c0d-olj-mxL_qaJ8XBsUEtVLzL0xwx50&pfkey=eaa85d3cb5d2494a0413f2fb2269db88
		// &sig=7A6PfpoY32s104OCrAjx0YCGkbw%3D&ts=1461122900&zoneid=1
		// [2016-04-20 11:28:20] 123112 [btpool0-0] INFO login - qq toParams ret
		// org_loc=%2Fmpay%2Fpay_m;session_id=hy_gameid;session_type=wc_actoken

		long balance = -1;
		try {
			long ts = System.currentTimeMillis() / 1000;
			String appKey = qqAppKey + "&";

			// http下订单请求参数:部分参数是从request中获取，有些是固定的（有可能需要申请），具体以文档为准
			HashMap<String, String> params = new HashMap<String, String>();
			// 必选参数
			params.put("openid", "5E14AF2DD9FB483934036E8DE3229596");
			params.put("openkey", "FFA8D89454E85767C035A7284A7AB056");
			params.put("appid", String.valueOf(qqAppId));
			params.put("ts", Long.toString(ts));
			params.put("pf", "desktop_m_qq-00000000-android-00000000-867049029851093");
			params.put("pfkey", "97693eaf69b79c9747d1807883eee79c");
			params.put("zoneid", "1");
			params.put("format", "json");

			// params.put("billno", "7056ad0025f643199212ec1b1deb307c");

			// 计算签名 SnsSigCheck为官方提供sdk中自带，demo不提供
			String sig = SnsSigCheck.makeSig("GET", "/v3/r" + balanceQryUri, params, appKey);
			params.put("sig", SnsSigCheck.encodeUrl(sig));
			LoggerUtils.info("qq getBalance sig=", SnsSigCheck.encodeUrl(sig));

			String url = baseUrl + balanceQryUri + "?" + toParams(params, "&");
			LoggerUtils.info("qq getBalance url", url);

			Map<String, String> cookies = new HashMap<String, String>();
			cookies.put("session_id", "openid");
			cookies.put("session_type", "kp_actoken");
			cookies.put("org_loc", SnsSigCheck.encodeUrl(balanceQryUri));

			String cookie = toParams(cookies, ";");
			SimpleHttpRequest shr = SimpleHttpRequest.createGet(url);
			shr.addCookie(cookie);
			String ret = shr.sendGetString();

			LoggerUtils.info("qq payUri ret", ret);

			Map<String, Object> retMap = GsonFactory.getDefault().fromJson(ret, Map.class);
			int retCode = DataUtils.getMapInteger(retMap, "ret", -1);
			if (retCode == 0) {
				// 余额, 游戏币
				balance = DataUtils.getMapLong(retMap, "balance", 0l);
			}
		} catch (Exception e) {
			LoggerUtils.error(e);
		}

	}

}
