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
import com.hadoit.game.common.lang.DataUtils;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

@SdkGroup({ SdkConst.CC_SDK_ANDROID_DANGLE })
@Component("sdkDangleService")
public class SdkDangleService extends SdkBaseService implements SdkService {

	private final String AppId = "2498";
	private final String MerchantId = "1067";
	private final String AppKey = "t1uy4AXc";
	private final String PaymentKey = "zrdn4e4rtFem";

	private String loginCheckUrl = "http://connect.d.cn/open/member/info/";

	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		LoggerUtils.info("dangle payCallback", requestParams);
		String result = DataUtils.getMapString(requestParams, "result");
		String money = DataUtils.getMapString(requestParams, "money");
		String orderNo = DataUtils.getMapString(requestParams, "order");
		String memberId = DataUtils.getMapString(requestParams, "mid");
		String dateTime = DataUtils.getMapString(requestParams, "time");
		String signature = DataUtils.getMapString(requestParams, "signature");
		String ext = DataUtils.getMapString(requestParams, "ext"); // 自己传递的订单号
		String str = "order=" + orderNo + "&money=" + money + "&mid=" + memberId + "&time=" + dateTime + "&result="
				+ result + "&ext=" + ext + "&key=" + PaymentKey;
		try {
			String sig = FsGameLoginUtils.md5low(str);
			if (sig.equalsIgnoreCase(signature)) { // 验证通过
				if ("1".equals(result)) {// 支付成功
					processOrder(ext, money, true);
				}
				return "success";
			}
		} catch (Exception e) {
			LoggerUtils.error(e);
		}
		return "fail";
	}

	@SuppressWarnings("unchecked")
	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {
		String Uin = FsGameLoginUtils.getMapString(requestParams, "Uin", "");
		String SessionId = FsGameLoginUtils.getMapString(requestParams, "SessionId", "");

		String sig = SessionId + "|" + AppKey;
		sig = FsGameLoginUtils.md5low(sig);

		StringBuilder getUrl = new StringBuilder(loginCheckUrl);
		getUrl.append("?app_id=");
		getUrl.append(AppId);
		getUrl.append("&mid=");
		getUrl.append(Uin);
		getUrl.append("&token=");
		getUrl.append(SessionId);
		getUrl.append("&sig=");
		getUrl.append(sig);

		SimpleHttpRequest shr = SimpleHttpRequest.createGet(getUrl.toString());
		String resultGet = shr.sendGetString();
		boolean result = false;

		if (resultGet != null) {
			Map<String, Object> rm = GsonFactory.getDefault().fromJson(resultGet, Map.class);
			int code = Double.valueOf(String.valueOf(rm.get("error_code"))).intValue();
			if (code == 0) {
				result = true;
			}
		}

		return new PlatformRetData(result, null);
	}

}
