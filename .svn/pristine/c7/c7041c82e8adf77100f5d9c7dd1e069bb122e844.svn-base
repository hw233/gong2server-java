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

@SdkGroup({ SdkConst.CC_SDK_ANDROID_SINA })
@Component("sdkSinaService")
public class SdkSinaService extends SdkBaseService implements SdkService {

	private static String APP_KEY = "143666493";
//	private static String APP_KEY = "3470157549";
	private static String APP_SECRECT = "bca7f1125ab8b5de4fdab8d1d0318129";
	private static String SIGN_KEY = "73426717efa481c22a264b2509e8812b";
	private static String REDIRECT_URL = "http://m.game.weibo.cn/oauth2/default";// 跳转地址
	private static String AUTH_URL = "http://m.game.weibo.cn/ddsdk/distsvc/1/user/serververify_v2";// 用户验证接口

	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		int status = FsGameLoginUtils.getMapInteger(requestParams, "status", 0);
		String channel = FsGameLoginUtils.getMapString(requestParams, "channel", "");
		String uid = FsGameLoginUtils.getMapString(requestParams, "uid", "");
		String suid = FsGameLoginUtils.getMapString(requestParams, "suid", "");
		String appkey = FsGameLoginUtils.getMapString(requestParams, "appkey", "");
		String channel_order_id = FsGameLoginUtils.getMapString(requestParams, "channel_order_id", "");
		int amount = FsGameLoginUtils.getMapInteger(requestParams, "amount", 0);
		int actual_amount = FsGameLoginUtils.getMapInteger(requestParams, "actual_amount", 0);
		String cpext = FsGameLoginUtils.getMapString(requestParams, "cpext", "");
		String cp_order_id = FsGameLoginUtils.getMapString(requestParams, "cp_order_id", "");
		String updatetime = FsGameLoginUtils.getMapString(requestParams, "updatetime", "");
		String sign = FsGameLoginUtils.getMapString(requestParams, "sign", "");
		String reString = "";
		if (appkey.equals(APP_KEY)&&status==1) {
			String rsign = encoderes(status, channel, uid, suid, appkey, channel_order_id, amount, actual_amount, cpext,
					cp_order_id, updatetime, sign);
			if (rsign.equals(sign)) {
				processOrder(cp_order_id, String.valueOf(amount), false);
				reString="OK";
			} else {

				reString = "false";
			}

		} else {
			reString = "false";
		}

		return reString;
	}

	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {
		String suid = FsGameLoginUtils.getMapString(requestParams, "Uin", "");
		String token = FsGameLoginUtils.getMapString(requestParams, "SessionId", "");
		String appkey = APP_KEY;
		String signature = encode(suid, token, appkey);
		String url = AUTH_URL + "?suid=" + suid + "&appkey=" + appkey + "&token=" + token + "&signature=" + signature;
		LoggerUtils.info("sina loginAuth endUrl=", url);
		SimpleHttpRequest shr = SimpleHttpRequest.createGet(url);
		String resultGet = shr.sendGetString();
		LoggerUtils.info("sina loginAuth resultGet=", resultGet);
		boolean result = false;
		Map<String, Object> rmResult = GsonFactory.getDefault().fromJson(resultGet, Map.class);
		if (rmResult != null) {
			// 10001 | 获取渠道信息失败
			// 10002 | 对应参数格式错误
			// 10003 | 渠道信息未配置
			// 10015 | 获取用户信息失败
			// 10025 | 签名验证失败
			String rsuid = (String) rmResult.get("suid");
			if (rsuid.equals(suid)) {
				result = true;
			}
		}
		return new PlatformRetData(result, null);
	}

	// 用户认证签名生成
	public String encode(String suid, String token, String appkey) {
		String code = "appkey|" + appkey + "|suid|" + suid + "|token|" + token + "|" + SIGN_KEY;
		code = FsGameLoginUtils.getSha1(code);
		return code;
	}

	// 回调签名生成
	public String encoderes(int status, String channel, String uid, String suid, String appkey, String channel_order_id,
			int amount, int actual_amount, String cpext, String cp_order_id, String updatetime, String sign) {
		String code = "actual_amount|" + actual_amount + "|amount|" + amount + "|appkey|" + appkey + "|channel|"
				+ channel + "|channel_order_id|" + channel_order_id + "|cp_order_id|" + cp_order_id + "|cpext|" + cpext
				+ "|status|" + status + "|suid|" + suid + "|uid|" + uid + "|updatetime|" + updatetime + "|" + APP_SECRECT;
		code = FsGameLoginUtils.getSha1(code);
		return code;
	}

}
