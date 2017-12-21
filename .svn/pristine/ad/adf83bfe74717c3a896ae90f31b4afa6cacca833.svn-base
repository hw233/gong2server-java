package com.gamejelly.game.gong2.login.service.sdk.impl;

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

@SdkGroup({ SdkConst.CC_SDK_ANDROID_PAOJIAO })
@Component("sdkPaojiaoService")
public class SdkPaojiaoService extends SdkBaseService implements SdkService {
	static String appId = "1156";
	static String appKey = "h8DOibydmLuCGazbX7C359LVF0WDw9hk";
	static String jpushKey = "943e216e2dcfa785e05605e9";

	// 测试地址 http://test.sdk.paojiao.cn/api/user/token.do 备用
	private static final String TOKEN_URL = "http://ng.sdk.paojiao.cn/api/user/token.do";

	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		LoggerUtils.info("paojiao payCallback", requestParams);
		String uid = FsGameLoginUtils.getMapString(requestParams, "uid", "");
		String price = FsGameLoginUtils.getMapString(requestParams, "price", "");
		String orderNo = FsGameLoginUtils.getMapString(requestParams, "orderNo", "");
		String remark = FsGameLoginUtils.getMapString(requestParams, "remark", "");
		String status = FsGameLoginUtils.getMapString(requestParams, "status", "");
		String subject = FsGameLoginUtils.getMapString(requestParams, "subject", "");
		String gameId = FsGameLoginUtils.getMapString(requestParams, "gameId", "");
		String payTime = FsGameLoginUtils.getMapString(requestParams, "payTime", "");
		String ext = FsGameLoginUtils.getMapString(requestParams, "ext", "");// 我们自己的订单号
		String sign = FsGameLoginUtils.getMapString(requestParams, "sign", "");
		// uid=用户编号price=价格orderNo=泡椒网订单号remark=备注status=状态subject=标题gameId=游戏编号payTime=付款时间ext=扩展信息

		StringBuilder mySb = new StringBuilder();
		mySb.append("uid=").append(uid).append("price=").append(price).append("orderNo=").append(orderNo)
				.append("remark=").append(remark).append("status=").append(status).append("subject=").append(subject)
				.append("gameId=").append(gameId).append("payTime=").append(payTime).append("ext=").append(ext)
				.append(appKey);

		String mySign = FsGameLoginUtils.md5low(mySb.toString());

		String ret = "fail";
		try {
			if (mySign.equalsIgnoreCase(sign)) {
				processOrder(ext, price, true);
				ret = "success";
			}
		} catch (Exception e) {
			LoggerUtils.error(e);
			ret = "fail";
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {
		String SessionId = FsGameLoginUtils.getMapString(requestParams, "SessionId", "");
		String url = TOKEN_URL + "?appId=" + appId + "&token=" + SessionId;
		SimpleHttpRequest shr = SimpleHttpRequest.createGet(url);
		String resultGet = shr.sendGetString();

		boolean result = false;
		if (resultGet != null) {
			Map<String, Object> rm = GsonFactory.getDefault().fromJson(resultGet, Map.class);
			int code = Double.valueOf(String.valueOf(rm.get("code"))).intValue();
			if (code == 1) {
				result = true;
			}
		}
		return new PlatformRetData(result, null);
	}

}
