package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.service.sdk.SdkGroup;
import com.gamejelly.game.gong2.login.service.sdk.SdkService;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.gamejelly.game.gong2.login.utils.SdkConst;
import com.google.gson.reflect.TypeToken;
import com.hadoit.game.common.framework.http.SimpleHttpRequest;
import com.hadoit.game.common.lang.DataUtils;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

@SdkGroup({ SdkConst.CC_SDK_ANDROID_ANQU })
@Component("sdkAnquService")
public class SdkAnquService extends SdkBaseService implements SdkService {

	private static final String AppId = "G100055";
	private static final String AppKey = "e1ab2ddf871559741cac9d74dfe10b3d";

	private static final String loginCheckUrl = "http://i.anqu.com/index.php/user/checkUser";

	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		LoggerUtils.info("anqu payCallback", requestParams);

		String uid = FsGameLoginUtils.getMapString(requestParams, "uid", "");
		String cporder = FsGameLoginUtils.getMapString(requestParams, "cporder", "");
		String cpappid = FsGameLoginUtils.getMapString(requestParams, "cpappid", "");
		String money = FsGameLoginUtils.getMapString(requestParams, "money", "");
		String order = FsGameLoginUtils.getMapString(requestParams, "order", "");
		String sign = FsGameLoginUtils.getMapString(requestParams, "sign", "");

		try {
			String signSource = uid + cporder + money + order + AppKey;
			String signCal = FsGameLoginUtils.md5up(signSource);
			if (signCal.equals(sign.toUpperCase()) && cpappid.equals(AppId)) {
				processOrder(cporder, money, true);
				return "success";
			} else {
				LoggerUtils.info("anqu 验签失败", signSource, signCal, sign);
			}
		} catch (Exception e) {
			LoggerUtils.error(e);
			return "fail";
		}
		return "fail";
	}

	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {
		String Uin = FsGameLoginUtils.getMapString(requestParams, "Uin", "");
		String SessionId = FsGameLoginUtils.getMapString(requestParams, "SessionId", "");

		StringBuilder getUrl = new StringBuilder(loginCheckUrl);
		getUrl.append("/uid/");
		getUrl.append(Uin);
		getUrl.append("/vkey/");
		getUrl.append(SessionId);
		getUrl.append("/appid/");
		getUrl.append(AppId);
		getUrl.append("/sign/");
		getUrl.append(FsGameLoginUtils.md5low(Uin + SessionId + AppId + AppKey));

		boolean result = false;
		SimpleHttpRequest shr = SimpleHttpRequest.createGet(getUrl.toString());
		String resultGet = shr.sendGetString();
		if (resultGet == null || "".equals(resultGet)) {
			result = false;
		} else {
			Map<String, Object> rm = GsonFactory.getDefault().fromJson(resultGet, new TypeToken<Map<String, Object>>() {
			}.getType());
			int code = DataUtils.getMapInteger(rm, "status", -1);
			if (code == 0) {
				result = true;
			} else {
				result = false;
			}
		}
		return new PlatformRetData(result, null);
	}

}
