package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.service.sdk.SdkGroup;
import com.gamejelly.game.gong2.login.service.sdk.SdkService;
import com.gamejelly.game.gong2.login.service.sdk.impl.haima.HttpClientUtil;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.gamejelly.game.gong2.login.utils.SdkConst;

@SdkGroup({ SdkConst.CC_SDK_IOS_HM })
@Component("sdkHMService")
public class SdkHMService extends SdkBaseService implements SdkService {

	private String AppId = "7591ba98f1503959ff7b126f7bf8c9cd";
	private String AppKey = "83fe3f0580f0b1e599d30d80efb5473e";

	private String loginCheckUrl = "http://api.haimawan.com/index.php?m=api&a=validate_token";

	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		LoggerUtils.info("hm payCallback", requestParams);

		//
		String notify_time = FsGameLoginUtils.getMapString(requestParams, "notify_time", "");
		String appid = FsGameLoginUtils.getMapString(requestParams, "appid", "");
		String out_trade_no = FsGameLoginUtils.getMapString(requestParams, "out_trade_no", "");
		String total_fee = FsGameLoginUtils.getMapString(requestParams, "total_fee", "");
		String subject = FsGameLoginUtils.getMapString(requestParams, "subject", "");
		String body = FsGameLoginUtils.getMapString(requestParams, "body", "");
		String trade_status = FsGameLoginUtils.getMapString(requestParams, "trade_status", "");
		String sign = FsGameLoginUtils.getMapString(requestParams, "sign", "");

		try {
			String signSource = "notify_time=" + URLEncoder.encode(notify_time, "UTF-8")
					+ "&appid=" + URLEncoder.encode(appid, "UTF-8")
					+ "&out_trade_no=" + URLEncoder.encode(out_trade_no, "UTF-8")
					+ "&total_fee=" + URLEncoder.encode(total_fee, "UTF-8")
					+ "&subject=" + URLEncoder.encode(subject, "UTF-8")
					+ "&body=" + URLEncoder.encode(body, "UTF-8")
					+ "&trade_status=" + URLEncoder.encode(trade_status, "UTF-8")
					+ URLEncoder.encode(AppKey, "UTF-8");
			String signCal = FsGameLoginUtils.md5up(signSource);
			if (signCal.equals(sign.toUpperCase()) && appid.equals(AppId)) {
				processOrder(out_trade_no, total_fee, true);
				return "success";
			} else {
				LoggerUtils.info("anqu 验签失败", signSource, signCal, sign);
			}
		} catch (Exception e) {
			LoggerUtils.error(e);
			return "fail";
		}

		return "FAILURE";
	}

	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {
		String Uin = FsGameLoginUtils.getMapString(requestParams, "Uin", "");
		String SessionId = FsGameLoginUtils.getMapString(requestParams, "SessionId", "");

		boolean result = false;

		Map<String, String> params = new HashMap<String, String>(3);
		params.put("appid", AppId);
		params.put("t", SessionId);
		params.put("uid", Uin);
		String resultGet = "";
		try {
			resultGet = HttpClientUtil.doPostHttpClient(loginCheckUrl, null, params);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// success&cpd12626146                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
		if (resultGet == null || resultGet.equals("")) {
			result = false;
		} else {
			if (resultGet.contains("success")) {
				result = true;
			} else {
				result = false;
			}
		}

		return new PlatformRetData(result, null);
	}

	private static Map<String, String> parseParams(String qryStr) {
		Map<String, String> m = new HashMap<String, String>();
		if (StringUtils.isBlank(qryStr)) {
			return m;
		}
		String[] ua = StringUtils.splitByWholeSeparator(qryStr, "&");
		for (String ui : ua) {
			String[] pa = StringUtils.splitByWholeSeparator(ui, "=");
			if (pa.length == 2) {
				m.put(pa[0], pa[1]);
			} else {
				m.put(pa[0], "");
			}
		}
		return m;
	}

}
