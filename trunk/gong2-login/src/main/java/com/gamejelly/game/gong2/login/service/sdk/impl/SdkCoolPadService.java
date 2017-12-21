package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.service.sdk.SdkGroup;
import com.gamejelly.game.gong2.login.service.sdk.SdkService;
import com.gamejelly.game.gong2.login.service.sdk.impl.coolpad.CpTransSyncSignValid;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.gamejelly.game.gong2.login.utils.SdkConst;
import com.hadoit.game.common.framework.http.SimpleHttpRequest;
import com.hadoit.game.common.lang.DataUtils;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

@SdkGroup({ SdkConst.CC_SDK_ANDROID_COOLPAD })
@Component("SdkCoolPadService")
public class SdkCoolPadService extends SdkBaseService implements SdkService {

	private static String AppID = "5000007209";
	private static String AppKey = "8216a8ac4ec94cf6847967ba57928010";
	private static String PayKey = "QTkxOTk5ODRDQjg2NkIzQzg5RUQzOEMzQUMzNDZDQzhCQkE1OTFBQk1UVTFORGc0TVRNNE9EazFOelUzT0RFek16Y3JNVEEyTlRjNU1EY3dNVFV5T1RneU56UTFOVEV6TVRFeE56WXlNelEwTWpneE5UWTFOakV4";

	@SuppressWarnings("unchecked")
	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {
		String Uin = FsGameLoginUtils.getMapString(requestParams, "Uin", "");
		String mAuthCode = FsGameLoginUtils.getMapString(requestParams, "mAuthCode", "");

		// login rm =
		// {access_token=7.0494e6f6fd9aa95f534f8f7b5e67ed58.768e6896e5c23eaf96cb9797b9ba4406.1493197643244,
		// refresh_token=7.5bddabc38af62e29013e9b2babdd9f14, openid=84017063,
		// expires_in=7776000}
		// 2017-04-27 12:22:56,857 [1540054533@qtp-474307635-3] INFO login -
		// login userResult =
		// {"sex":"","nickname":"CC84017063","brithday":"","highDefUrl":"","rtn_code":"0","headIconUrl":""}
		// 2017-04-27 12:22:58,250 [1540054533@qtp-474307635-3] INFO login -
		// account loginByTicket 84017063@coolpad
		// dq8g6kdqXtbs0rpMh5LrIPqZZEXoZn9RaeqShV9KHJ9Y22WF3nIWFQ 2 coolpad
		// 1.0.11
		// {"loginUrl":"http:\/\/10.10.10.79:9090\/gonglogin","deviceVersion":"5.1","opr":"coolpad","deviceId":"869540023301984","sdkId":147,"deviceName":"Meizu
		// m2 note","platform":"android","serverId":0} 10.10.10.19
		//

		LoggerUtils.info("loginAuth coolpad mAuthCode =", mAuthCode);
		// https://openapi.coolyun.com/oauth2/token?grant_type=authorization_code&client_id=12345633&redirect_uri=449f3a232e687fcfa8302585614f84a1&client_secret=449f3a232e687fcfa8302585614f84a1&code=b48d3a258e687fcha840258g614f8df1

		String baseUrl = "https://openapi.coolyun.com/oauth2";
		String endUrl = baseUrl + "/token?" + "grant_type=" + "authorization_code" + "&" + "client_id=" + AppID + "&"
				+ "redirect_uri=" + AppKey + "&" + "client_secret=" + AppKey + "&" + "code=" + mAuthCode;

		SimpleHttpRequest shr = SimpleHttpRequest.createGet(endUrl.toString());
		String tokenResult = shr.sendGetString();

		Map<String, Object> rm = GsonFactory.getDefault().fromJson(tokenResult, Map.class);
		LoggerUtils.info("login rm =", rm);
		String access_token = (String) rm.get("access_token");

		// {"access_token":"7.0494e6f6fd9aa95f534f8f7b5e67ed58.768e6896e5c23eaf96cb9797b9ba4406.1493197643244",
		// "refresh_token":"7.5bddabc38af62e29013e9b2babdd9f14","openid":"84017063","expires_in":"7776000"}

		String openid = (String) rm.get("openid");
		if (StringUtils.isEmpty(access_token) || StringUtils.isEmpty(openid)) {
			return new PlatformRetData(false, null);
		}

		// https://openapi.coolyun.com/oauth2/api/get_user_info?access_token=0.635ee11725c1faf22ea581981fb5b151.cf0243b77a2ba505a1ab22fs2574736.1410768079313&oauth_consumer_key=1010219&openid=661

		String userInfoUrl = baseUrl + "/api/get_user_info?access_token=" + access_token + "&" + "oauth_consumer_key="
				+ AppID + "&" + "openid=" + openid;

		SimpleHttpRequest shrUser = SimpleHttpRequest.createGet(userInfoUrl.toString());
		String userResult = shrUser.sendGetString();
		LoggerUtils.info("login userResult =", userResult);
		Map<String, Object> rmUser = GsonFactory.getDefault().fromJson(userResult, Map.class);

		// {"sex":"","nickname":"CC84017063","brithday":"","highDefUrl":"","rtn_code":"0","headIconUrl":""}
		boolean result = false;
		String nickName = (String) rmUser.get("nickname");
		if (StringUtils.isNotEmpty(nickName)) {
			result = true;
		}

		requestParams.put("name", openid);
		PlatformRetData retData = new PlatformRetData(result, null);
		rm.put("payKey", PayKey);

		retData.setOtherData(rm);
		return retData;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		LoggerUtils.info("coolpad payCallback", requestParams);
		String transdata = DataUtils.getMapString(requestParams, "transdata", "");
		String sign = DataUtils.getMapString(requestParams, "sign", "");
		String result = "FAILURE";
		try {
			if (transdata == null || sign == null || "".equalsIgnoreCase(transdata) || "".equalsIgnoreCase(sign)) {
				LoggerUtils.info("error:支付结果通知内容格式不对，请确认格式为tranddate={}&sing=。");
			} else {
				boolean checkFlag = CpTransSyncSignValid.validSign(transdata, sign, PayKey);
				if (checkFlag) {
					/* 4、业务处理 */
					result = "SUCCESS";
					Map<String, Object> tranMap = GsonFactory.getDefault().fromJson(transdata, Map.class);
					processOrder(DataUtils.getMapString(tranMap, "exorderno", ""),
							DataUtils.getMapString(tranMap, "money", ""), false);
					LoggerUtils.info("info:支付结果通知内容验签成功");
				} else {
					LoggerUtils.info("error:支付结果通知内容验签失败");
				}
			}

		} catch (Exception e) {
			LoggerUtils.error(e);
			result = "FAILURE";
		}

		return result;
	}

}
