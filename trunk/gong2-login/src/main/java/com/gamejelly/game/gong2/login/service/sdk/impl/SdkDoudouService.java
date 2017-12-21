package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.service.sdk.SdkGroup;
import com.gamejelly.game.gong2.login.service.sdk.SdkService;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.gamejelly.game.gong2.login.utils.SdkConst;
import com.hadoit.game.common.framework.http.SimpleHttpRequest;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

@SdkGroup({ SdkConst.CC_SDK_ANDROID_DOUDOU })
@Component("sdkDoudouService")
@Deprecated
public class SdkDoudouService extends SdkBaseService implements SdkService {

	private String mAppId = "880000077";
	private String mAppSecret = "6eaf5133c6723ed0ba7e2e8ea847c2d2";

	private static final String AUTH_URL = "http://fh.sdo.com/fh/open/ticket";

	private static String getSignData(Map<String, String> params) {
		StringBuffer content = new StringBuffer();
		List<String> keys = new ArrayList<String>(params.keySet());
		// 按照自然升序处理
		Collections.sort(keys);

		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			// sign参数不参与签名数据
			if ("sign".equals(key)) {
				continue;
			}
			String value = (String) params.get(key);
			if (value != null) {
				content.append((i == 0 ? "" : "&") + key + "=" + value);
			} else {
				content.append((i == 0 ? "" : "&") + key + "=");
			}
		}
		return content.toString();
	}

	/**
	 * 平台回调
	 * 
	 * @param requestParams
	 * @return
	 */
	public Object payCallback(Map<String, Object> params) {
		// orderNo： 服务器的订单号
		// userId：用户ID
		// gameOrderNo： 游戏的订单号
		// itemName：充值项目名，支持中文。
		// money：充值金额，单位为元
		// extend：游戏发送过来的扩展信息，原格式返回
		// sign：签名串（参考“附录A：服务器端签名算法”）
		// time：到账时间

		String orderNo = FsGameLoginUtils.getMapString(params, "orderNo", "");
		String userId = FsGameLoginUtils.getMapString(params, "userId", "");
		String gameOrderNo = FsGameLoginUtils.getMapString(params, "gameOrderNo", ""); // 我们的订单号
		String itemName = FsGameLoginUtils.getMapString(params, "itemName", "");
		String product = FsGameLoginUtils.getMapString(params, "product", "");
		String money = FsGameLoginUtils.getMapString(params, "money", "");
		String extend = FsGameLoginUtils.getMapString(params, "extend", "");
		String sign = FsGameLoginUtils.getMapString(params, "sign", "");
		String time = FsGameLoginUtils.getMapString(params, "time", "");

		Map<String, String> transParams = new HashMap<String, String>();
		transParams.put("orderNo", orderNo);
		transParams.put("userId", userId);
		transParams.put("gameOrderNo", gameOrderNo);
		transParams.put("itemName", itemName);
		transParams.put("product", product);
		transParams.put("money", money);
		transParams.put("extend", extend);
		transParams.put("sign", sign);
		transParams.put("time", time);

		LoggerUtils.info("doudou payCallback", params);

		String pStr = getSignData(transParams);
		String mySign = FsGameLoginUtils.md5low(pStr + mAppSecret);

		String result = "fail";
		try {
			if (sign.equals(mySign)) { // 验签成功
				processOrder(gameOrderNo, money, true);
				result = "success";
			} else {
				LoggerUtils.info("doudou 验签失败", mySign, sign);
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
	@SuppressWarnings("unchecked")
	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {
		// appid=880000043&sequence=1419316323&ticket_id=86056052801419316323&timestamp=1419316323&sign=2ec469cfb01d8aa108045e30e6ca229f
		String ticket_id = FsGameLoginUtils.getMapString(requestParams, "SessionId", "");
		String sequence = FsGameLoginUtils.getUUID();
		long timestamp = System.currentTimeMillis() / 1000;

		Map<String, String> transParams = new HashMap<String, String>();
		transParams.put("appid", mAppId);
		transParams.put("sequence", sequence);
		transParams.put("ticket_id", ticket_id);
		transParams.put("timestamp", timestamp + "");

		String baseParamStr = getSignData(transParams);
		String sign = baseParamStr + mAppSecret;
		sign = FsGameLoginUtils.md5low(sign);
		String url = AUTH_URL + "?" + baseParamStr + "&sign=" + sign;
		LoggerUtils.info("doudou login check id url =", url);
		SimpleHttpRequest shr = SimpleHttpRequest.createGet(url);
		String result = shr.sendGetString();
		Map<String, Object> rm = GsonFactory.getDefault().fromJson(result, Map.class);
		int code = FsGameLoginUtils.getMapInteger(rm, "code", 1);
		if (code != 0) {
			return new PlatformRetData(false, rm);
		}
		return new PlatformRetData(true, rm);
	}

}
