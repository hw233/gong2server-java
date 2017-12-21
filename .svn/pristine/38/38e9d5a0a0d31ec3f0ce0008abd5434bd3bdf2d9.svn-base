package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.util.Map;

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
import com.hadoit.game.common.lang.DataUtils;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

@SdkGroup({ SdkConst.CC_SDK_ANDROID_SOGOU })
@Component("sdkSogouService")
@Deprecated
public class SdkSogouService extends SdkBaseService implements SdkService {

	private String gid = "591";

	private String appSecret = "b51c5dfd31cd9410c204b49282f209e570c41283e667fd9bf487cb8d206f913f";

	private String appKey = "7287a7a915b2dded7e85b6fa8521915fbe3931cbd69511cedc3d61b85073c030";

	private String payKey = "{980B52AC-54BF-47E1-B256-9624EAAD5D7B}";

	private String tokenCheckUrl = "http://api.app.wan.sogou.com/api/v1/login/verify";

	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		LoggerUtils.info("Sogou payCallback", requestParams);

		String amount1 = DataUtils.getMapString(requestParams, "amount1", "");
		String amount2 = DataUtils.getMapString(requestParams, "amount2", "");
		String appdata = DataUtils.getMapString(requestParams, "appdata", "");
		String date = DataUtils.getMapString(requestParams, "date", "");
		String gid = DataUtils.getMapString(requestParams, "gid", "");
		String oid = DataUtils.getMapString(requestParams, "oid", "");
		String realAmount = DataUtils.getMapString(requestParams, "realAmount", "");
		String role = DataUtils.getMapString(requestParams, "role", "");
		String sid = DataUtils.getMapString(requestParams, "sid", "");
		String time = DataUtils.getMapString(requestParams, "time", "");
		String uid = DataUtils.getMapString(requestParams, "uid", "");
		String auth = DataUtils.getMapString(requestParams, "auth", "");

		// amount1=10&amount2=100&appdata=123&date=131018&gid=100&oid=T131018_90&
		// realAmount=10&role=&sid=1&time=20131018202859&uid=1&{ECCE11A4-2D0A-4DC0-B4B6-05F94BF0C8FC}

		StringBuilder signSb = new StringBuilder();
		signSb.append("amount1=").append(amount1).append("&amount2=").append(amount2).append("&appdata=")
				.append(appdata).append("&date=").append(date).append("&gid=").append(gid).append("&oid=").append(oid)
				.append("&realAmount=").append(realAmount).append("&role=").append(role).append("&sid=").append(sid)
				.append("&time=").append(time).append("&uid=").append(uid).append("&").append(payKey);

		String myAuth = FsGameLoginUtils.md5low(signSb.toString());

		String resultRet = "ERR_500";
		try {
			LoggerUtils.info("Sogou payCallback sign", auth, ", mySign", myAuth);
			if (myAuth.equalsIgnoreCase(auth)) {
				PlatformPayRecord ppr = platformPayRecordService.getByOrderSn(appdata);
				if (ppr != null) {
					if (ppr.getServerState() != FsGameLoginConst.AS_SERVER_STATE_GIVED_ITEM) {
						platformPayRecordService.updateServerState(ppr.getId(),
								FsGameLoginConst.AS_SERVER_STATE_VERIFIED);
						doGivePayItem(ppr);
					}
					resultRet = "OK";
				}
			} else {
				resultRet = "ERR_200";
			}
		} catch (Exception e) {
			LoggerUtils.error(e, "");
			resultRet = "ERR_500";
		}
		return resultRet;
	}

	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {
		LoggerUtils.info("Sogou loginAuth", requestParams);

		String session_key = FsGameLoginUtils.getMapString(requestParams, "session_key", "");
		String user_id = FsGameLoginUtils.getMapString(requestParams, "user_id", "");

		StringBuilder signSb = new StringBuilder();
		signSb.append("gid=").append(gid).append("&session_key=").append(session_key).append("&user_id=")
				.append(user_id).append("&").append(appSecret);
		String auth = FsGameLoginUtils.md5low(signSb.toString());

		StringBuilder getUrl = new StringBuilder(tokenCheckUrl);
		getUrl.append("?gid=");
		getUrl.append(gid);
		getUrl.append("&user_id=");
		getUrl.append(user_id);
		getUrl.append("&session_key=");
		getUrl.append(session_key);
		getUrl.append("&auth=");
		getUrl.append(auth);

		SimpleHttpRequest shr = SimpleHttpRequest.createPost(getUrl.toString());
		String resultGet = shr.sendGetString();
		Map<String, String> resultMap = GsonFactory.getDefault().fromJson(resultGet,
				new TypeToken<Map<String, String>>() {
				}.getType());
		LoggerUtils.info("Sogou loginAuth back", resultMap);
		if (resultMap.containsKey("result")) {
			boolean res = Boolean.valueOf(resultMap.get("result"));
			return new PlatformRetData(res, null);
		}
		return new PlatformRetData(false, null);
	}

}
