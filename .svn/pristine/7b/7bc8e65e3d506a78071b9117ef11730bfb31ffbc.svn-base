package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.dao.MeizuSy37AccountDao;
import com.gamejelly.game.gong2.login.meta.GameAccount;
import com.gamejelly.game.gong2.login.meta.MeizuSy37Account;
import com.gamejelly.game.gong2.login.meta.PlatformPayRecord;
import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.service.sdk.SdkGroup;
import com.gamejelly.game.gong2.login.service.sdk.SdkService;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.gamejelly.game.gong2.login.utils.SdkConst;
import com.hadoit.game.common.framework.http.SimpleHttpRequest;
import com.hadoit.game.common.lang.DataUtils;
import com.hadoit.game.common.lang.Pair;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

@SdkGroup({ SdkConst.CC_SDK_ANDROID_MEIZU })
@Component("sdkMeizuService")
public class SdkMeizuService extends SdkBaseService implements SdkService {

	//
	@Autowired
	private MeizuSy37AccountDao meizuSy37Account;

	private String appId = "3155783";
	private String appKey = "e56a18ed04154d44a722eb19015a0df3";
	private String appSecret = "AwoeBLGhdSp0LQZ5OEWDNPrqlEXzm12a";

	private String tokenCheckUrl = "https://api.game.meizu.com/game/security/checksession";
	private String meizuSy37TransformUrl = "https://api.game.meizu.com/game/sdk37/transform";

	public static String makeSign(HashMap<String, String> params, String secret) {
		Object[] keys = params.keySet().toArray();
		Arrays.sort(keys);
		StringBuilder buffer = new StringBuilder(128);
		for (int i = 0; i < keys.length; i++) {
			buffer.append(keys[i]).append("=").append(params.get(keys[i]));
			if (i != keys.length - 1) {
				buffer.append("&");
			}
		}
		String ret = FsGameLoginUtils.md5low(buffer.toString() + ":" + secret);
		LoggerUtils.info("MEIZU sign_params=", buffer.toString(), ", sign=", ret);
		return ret;
	}

	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		LoggerUtils.info("meizu payCallback", requestParams);

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("notify_time", DataUtils.getMapString(requestParams, "notify_time", ""));
		params.put("notify_id", DataUtils.getMapString(requestParams, "notify_id", ""));
		params.put("order_id", DataUtils.getMapString(requestParams, "order_id", ""));
		params.put("app_id", DataUtils.getMapString(requestParams, "app_id", ""));
		params.put("uid", DataUtils.getMapString(requestParams, "uid", ""));
		params.put("partner_id", DataUtils.getMapString(requestParams, "partner_id", ""));
		params.put("cp_order_id", DataUtils.getMapString(requestParams, "cp_order_id", ""));
		params.put("product_id", DataUtils.getMapString(requestParams, "product_id", ""));
		params.put("product_unit", DataUtils.getMapString(requestParams, "product_unit", ""));
		params.put("buy_amount", DataUtils.getMapString(requestParams, "buy_amount", ""));
		params.put("product_per_price", DataUtils.getMapString(requestParams, "product_per_price", ""));
		params.put("total_price", DataUtils.getMapString(requestParams, "total_price", ""));
		params.put("trade_status", DataUtils.getMapString(requestParams, "trade_status", ""));
		params.put("create_time", DataUtils.getMapString(requestParams, "create_time", ""));
		params.put("pay_time", DataUtils.getMapString(requestParams, "pay_time", ""));
		params.put("pay_type", DataUtils.getMapString(requestParams, "pay_type", ""));
		params.put("buy_amount", DataUtils.getMapString(requestParams, "buy_amount", ""));
		params.put("user_info", DataUtils.getMapString(requestParams, "user_info", ""));

		String sign = DataUtils.getMapString(requestParams, "sign", "");
		String cp_order_id = DataUtils.getMapString(requestParams, "cp_order_id", "");
		String total_price = DataUtils.getMapString(requestParams, "total_price", "");
		String mySign = makeSign(params, appSecret);
		int state = 0;
		try {
			LoggerUtils.info("meizu payCallback sign", sign, ", mySign", mySign);
			if (mySign.equalsIgnoreCase(sign)) {
				processOrder(cp_order_id, total_price, true);
				state = 200;
			}
		} catch (Exception e) {
			LoggerUtils.error(e);
			state = 900000;
		}

		Map<String, String> result = new HashMap<String, String>();
		result.put("code", state + "");
		result.put("value", "");
		result.put("message", "");
		result.put("redirect", "");
		return GsonFactory.getDefault().toJson(result);
	}

	public static String mapToQueryStr(Map m) {
		if (MapUtils.isEmpty(m)) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (Object key : m.keySet()) {
			sb.append("&").append(key).append("=").append(FsGameLoginUtils.getMapString(m, key, ""));
		}
		return sb.substring(1);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {
		LoggerUtils.info("MEIZU loginAuth", requestParams);

		String Uin = FsGameLoginUtils.getMapString(requestParams, "Uin", "");
		String token = FsGameLoginUtils.getMapString(requestParams, "SessionId", "");
		long time = System.currentTimeMillis();

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("app_id", appId);
		params.put("session_id", token);
		params.put("ts", String.valueOf(time));
		params.put("uid", Uin);

		String sign = makeSign(params, appSecret);
		params.put("sign_type", "md5");
		params.put("sign", sign);

		String qryStr = mapToQueryStr(params);
		StringBuilder getUrl = new StringBuilder(tokenCheckUrl);
		getUrl.append("?").append(qryStr);

		LoggerUtils.info("MEIZU loginAuth url", getUrl.toString());

		SimpleHttpRequest shr = SimpleHttpRequest.createPost(getUrl.toString());
		String resultGet = shr.sendGetString();
		LoggerUtils.info("MEIZU loginAuth back", resultGet);
		Map<String, Object> resultMap = (Map<String, Object>) GsonFactory.getDefault().fromJson(resultGet, Map.class);

		boolean result = false;
		int state = Double.valueOf(resultMap.get("code").toString()).intValue();
		if (state == 200) {
			result = true;
		}

		if (!result) {
			// meizu token验证失败
			return new PlatformRetData(result, null);
		}

		// meizu sy37 转换部分
		String ip = FsGameLoginUtils.getMapString(requestParams, "clientIp", "");
		String platform = FsGameLoginUtils.getMapString(requestParams, "platform", "");
		String deviceName = FsGameLoginUtils.getMapString(requestParams, "deviceName", "");
		String deviceVersion = FsGameLoginUtils.getMapString(requestParams, "deviceVersion", "");
		String deviceId = FsGameLoginUtils.getMapString(requestParams, "deviceId", "");

		String meizuUid = Uin;
		String meizuAccountName = mz(meizuUid);
		MeizuSy37Account mzMappingData = meizuSy37Account.getMeizuSy37AccountByMeizuAccountName(meizuAccountName);
		String finalAccountName = "";
		if (mzMappingData != null) {
			// 已经存在对应关系
			finalAccountName = mzMappingData.getMappingAccountName();
		} else {
			// 没有对应关系

			// 找sy37账号
			String sy37Uid = getSy37ByMeizuUid(meizuUid);
			if (StringUtils.isNotBlank(sy37Uid)) {
				// 存在sy37账号
				String sy37AccountName = sy37(sy37Uid);

				// 判断sy37的账号是否在account表里面
				GameAccount gameAccount = gameAccountService.getGameAccountByAccount(sy37AccountName);
				if (gameAccount == null) {
					finalAccountName = meizuAccountName;
				} else {
					finalAccountName = sy37AccountName;
				}
			} else {
				finalAccountName = meizuAccountName;
			}

			meizuSy37Account
					.addObject(new MeizuSy37Account(meizuAccountName, finalAccountName, System.currentTimeMillis()));
		}

		LoggerUtils.info("MEIZU finalAccountName", finalAccountName);

		Pair<Integer, String> customReqTicketData = gameAccountService.reqTicketWithCreate(finalAccountName,
				this.createAccountIfAbsent(), platform, deviceName, deviceVersion, deviceId, ip);

		requestParams.put("customReqTicketData", Pair.newInstance(finalAccountName, customReqTicketData));
		return new PlatformRetData(true, null);
	}

	private String sy37(String uid) {
		return FsGameLoginUtils.compAccountName(uid, "sy37");
	}

	private String mz(String uid) {
		return FsGameLoginUtils.compAccountName(uid, "meizu");
	}

	@SuppressWarnings("unchecked")
	private String getSy37ByMeizuUid(String meizuAccountUid) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("app_id", appId);
		params.put("flyme_uid", meizuAccountUid);

		String sign = makeSign(params, appSecret);
		params.put("sign_type", "md5");
		params.put("sign", sign);

		String qryStr = mapToQueryStr(params);
		StringBuilder getUrl = new StringBuilder(meizuSy37TransformUrl);
		getUrl.append("?").append(qryStr);

		LoggerUtils.info("Meizu getSy37ByMeizuUid url", getUrl.toString());

		SimpleHttpRequest shr = SimpleHttpRequest.createPost(getUrl.toString());
		String resultGet = shr.sendGetString();
		LoggerUtils.info("Meizu getSy37ByMeizuUid back", resultGet);
		Map<String, Object> resultMap = (Map<String, Object>) GsonFactory.getDefault().fromJson(resultGet, Map.class);
		Map<String, Object> valueMap = (Map<String, Object>) resultMap.get("value");
		if (valueMap != null) {
			String flyme_uid = FsGameLoginUtils.getMapLong(valueMap, "flyme_uid", 0L) + "";
			String ext_uid = FsGameLoginUtils.getMapString(valueMap, "ext_uid", "");
			if (flyme_uid.equals(meizuAccountUid)) {
				return ext_uid;
			}
		}
		return "";
	}

	@Override
	public PlatformRetData handleSdkData(Map<String, Object> requestParams) {
		String orderId = FsGameLoginUtils.getMapString(requestParams, "orderId", "");
		String uid = FsGameLoginUtils.getMapString(requestParams, "uid", "");
		String productName = FsGameLoginUtils.getMapString(requestParams, "productName", "");
		PlatformPayRecord ppr = platformPayRecordService.getByOrderSn(orderId);
		if (ppr == null) {
			return new PlatformRetData(false, null);
		}

		String amount = Float.toString(ppr.getProductAmount() / 100f);
		long createTime = System.currentTimeMillis();

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("cp_order_id", orderId);
		params.put("buy_amount", Integer.toString(1));
		params.put("user_info", orderId);
		params.put("total_price", amount);
		params.put("product_id", ppr.getProductId());
		params.put("product_subject", productName);
		params.put("product_body", ppr.getProductId());
		params.put("product_unit", "ge");
		params.put("app_id", appId);
		params.put("uid", uid);
		params.put("product_per_price", amount);
		params.put("create_time", Long.toString(createTime));
		params.put("pay_type", Integer.toString(0));

		String sign = makeSign(params, appSecret);
		params.put("sign", sign);
		params.put("sign_type", "md5");

		String retStr = GsonFactory.getDefault().toJson(params);
		return new PlatformRetData(true, retStr);
	}

	public static void main(String[] args) {
		SdkMeizuService s = new SdkMeizuService();

		System.out.println(s.getSy37ByMeizuUid("117534820"));
	}

}