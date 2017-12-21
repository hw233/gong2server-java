package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.service.sdk.SdkGroup;
import com.gamejelly.game.gong2.login.service.sdk.SdkService;
import com.gamejelly.game.gong2.login.service.sdk.impl.coolpad.MD5;
import com.gamejelly.game.gong2.login.service.sdk.impl.mumayi.MD5Util;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.gamejelly.game.gong2.login.utils.SdkConst;
import com.hadoit.game.common.framework.http.SimpleHttpRequest;
import com.hadoit.game.common.lang.DataUtils;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

@SdkGroup({ SdkConst.CC_SDK_ANROID_MEITU })
@Component("sdkMeiTuService")
public class SdkMeiTuService extends SdkBaseService implements SdkService {

	private static String APP_ID = "1276";
	private static String APP_KEY = "yWpx3hWQHFhSnTCj#1276#6KuRKuaAjLJ5sYRy";
	private static String LOGIN_KEY = "3CenL3VuMEunxzsDthNrSbvFa5$01276";
	private static String PAY_KEY = "f8893db9ba23e2f25cee6fafd6e2ba53";
	private static String AUTH_URL = "http://www.91wan.com/api/mobile/sdk_oauth.php";

	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {

		String uid = FsGameLoginUtils.getMapString(requestParams, "Uin", "");
		String session = FsGameLoginUtils.getMapString(requestParams, "SessionId", "");
		// $appid.$uid.$state.$login_key

		String signSource = APP_ID + uid + session + LOGIN_KEY;
		String flag = FsGameLoginUtils.md5low(signSource);
		LoggerUtils.info("meitu loginAuth md5Source=", signSource, " flag=", flag);

		String endUrl = AUTH_URL + "?" + "appid=" + APP_ID + "&uid=" + uid + "&state=" + session + "&flag=" + flag;
		LoggerUtils.info("meitu loginAuth endUrl=", endUrl);

		SimpleHttpRequest shr = SimpleHttpRequest.createGet(endUrl);
		String resultGet = shr.sendGetString();
		LoggerUtils.info("meitu loginAuth resultGet=", resultGet);
		boolean result = false;
		Map<String, Object> rmResult = GsonFactory.getDefault().fromJson(resultGet, Map.class);
		if (rmResult != null) {
			// { "ret":100, "uid":uid, "msg":"验证成功"}
			// ret 状态码:ret=100 验证成功;ret=-1 参数不全;ret=-2 签名错误;ret=-3 验证失败
			double codeRet = (Double) rmResult.get("ret");
			if (codeRet == 100) {
				result = true;
			}
		}
		return new PlatformRetData(result, null);
	}

	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		LoggerUtils.info("meitu payCallback", requestParams);

		String uid = DataUtils.getMapString(requestParams, "uid", "");
		String money = DataUtils.getMapString(requestParams, "money","");
		int time = DataUtils.getMapInteger(requestParams, "time", 0);
		int sid = DataUtils.getMapInteger(requestParams, "sid", 0);
		String orderid = DataUtils.getMapString(requestParams, "orderid", "");
		String ext = DataUtils.getMapString(requestParams, "ext", "");
		String flag = DataUtils.getMapString(requestParams, "flag", "");
		String trueflag = FsGameLoginUtils.getMD5Str(uid + money + time + sid + orderid + ext + PAY_KEY);
		LoggerUtils.info("meitu payCallback endUrl=", uid,money,time,sid,orderid,ext,PAY_KEY,flag);
		int ret;
		try {
			if (flag.equals(trueflag)) {
				processOrder(ext, String.valueOf(money), true);
				ret = 1;
			} else {

				ret = -1;

			}
		} catch (Exception e) {
			LoggerUtils.error(e);
			ret = -1;
		}

		return ret; // 回应的 ok 表示应 用已经正常接到消息, 无需继续发送通知

	}

	public static void main(String[] args) {

		String uid = "213835069";
		String session = "qlgjpvhqb2njguad2p82keahr5";
		String signSource = APP_ID + uid + session + LOGIN_KEY;
		String flag = FsGameLoginUtils.md5low(signSource);
		LoggerUtils.info("meitu loginAuth md5Source=", signSource, " flag=", flag);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appid", APP_ID);
		params.put("uid", uid);
		params.put("state", session);
		params.put("flag", flag);

		String endUrl = AUTH_URL + "?" + "appid=" + APP_ID + "&uid=" + uid + "&state=" + session + "&flag=" + flag;
		LoggerUtils.info("meitu loginAuth endUrl=", endUrl);

		String endData = GsonFactory.getDefault().toJson(params);

		SimpleHttpRequest shr = SimpleHttpRequest.createPost(endUrl);
		shr.setHeader("Content-Type", "application/x-www-form-urlencoded");
		shr.setCharset("utf-8");
		// shr.createBody(endData);
		String resultGet = shr.sendGetString();
		LoggerUtils.info("meitu loginAuth resultGet=", resultGet);
		boolean result = false;
		Map<String, Object> rmResult = GsonFactory.getDefault().fromJson(resultGet, Map.class);
		if (rmResult != null) {

		}
	}

}
