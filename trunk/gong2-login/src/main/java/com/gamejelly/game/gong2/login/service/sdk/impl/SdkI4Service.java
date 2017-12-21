package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.service.sdk.SdkGroup;
import com.gamejelly.game.gong2.login.service.sdk.SdkService;
import com.gamejelly.game.gong2.login.service.sdk.impl.i4.PayCore;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.gamejelly.game.gong2.login.utils.SdkConst;
import com.google.gson.reflect.TypeToken;
import com.hadoit.game.common.framework.http.SimpleHttpRequest;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

@SdkGroup({ SdkConst.CC_SDK_IOS_I4 })
@Component("sdkI4Service")
public class SdkI4Service extends SdkBaseService implements SdkService {

	private String AppId = "2084";
	private String AppKey = "1d7ccdc76b6b4f24932bed2e228a3f9a";

	private String loginCheckUrl = "https://pay.i4.cn/member_third.action";

	/**
	 * 异步通知消息验证
	 * 
	 * @param para
	 *            异步通知消息
	 * @return 验证结果
	 */
	public boolean verifySignature(Map<String, String> para) {
		try {
			String respSignature = para.get(PayCore.SIGNATURE);
			// 除去数组中的空值和签名参数
			Map<String, String> filteredReq = PayCore.paraFilter(para);
			Map<String, String> signature = PayCore.parseSignature(respSignature);
			for (String key : filteredReq.keySet()) {
				String value = filteredReq.get(key);
				String signValue = signature.get(key);
				if (!value.equals(signValue)) {
					return false;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		// 获得通知参数
		HttpServletRequest request = (HttpServletRequest) requestParams.get("request");
		if (request == null) {
			return "fail";
		}
		LoggerUtils.info("i4 payCallback", requestParams);
		// 获取快用POST过来异步通知信息
		Map<String, String> transformedMap = new HashMap<String, String>();
		String order_id = request.getParameter("order_id");
		transformedMap.put("order_id", order_id);

		String billno = request.getParameter("billno");
		transformedMap.put("billno", billno);

		String account = request.getParameter("account");
		transformedMap.put("account", account);

		String amount = request.getParameter("amount");
		transformedMap.put("amount", amount);

		String status = request.getParameter("status");
		transformedMap.put("status", status);

		String app_id = request.getParameter("app_id");
		transformedMap.put("app_id", app_id);

		String role = request.getParameter("role");
		transformedMap.put("role", role);

		String zone = request.getParameter("zone");
		transformedMap.put("zone", zone);

		String sign = request.getParameter("sign");
		transformedMap.put("sign", sign);

		if (!verifySignature(transformedMap)) {
			return "fail";
		}

		try {
			processOrder(FsGameLoginUtils.getMapString(requestParams, "billno", ""), amount, true);
			return "success";
		} catch (Exception e) {
			LoggerUtils.error(e);
			return "fail";
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {
		String Uin = FsGameLoginUtils.getMapString(requestParams, "Uin", "");
		String SessionId = FsGameLoginUtils.getMapString(requestParams, "SessionId", "");
		String UserName = FsGameLoginUtils.getMapString(requestParams, "UserName", "");

		Map<String, Object> m = new HashMap<String, Object>();
		m.put("token", SessionId);
		SimpleHttpRequest shr = SimpleHttpRequest.createPost(loginCheckUrl.toString());
		shr.createFormBody(FsGameLoginUtils.mapToParamBody(m));
		String resultGet = shr.sendGetString();

		// {"username":"martian","status":0,"userid":2642987}

		boolean result = false;
		if (resultGet == null || resultGet.equals("")) {
			result = false;
		} else {
			Map<String, Object> rm = GsonFactory.getDefault().fromJson(resultGet, new TypeToken<Map<String, String>>() {
			}.getType());
			int code = Double.valueOf(String.valueOf(rm.get("status"))).intValue();
			if (code == 0 && rm.get("username").equals(UserName) && rm.get("userid").equals(Uin)) {
				result = true;
			} else {
				result = false;
			}
		}

		return new PlatformRetData(result, null);
	}

}
