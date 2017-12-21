package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.meta.PlatformPayRecord;
import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.service.sdk.SdkGroup;
import com.gamejelly.game.gong2.login.service.sdk.SdkService;
import com.gamejelly.game.gong2.login.service.sdk.impl.uc.Game;
import com.gamejelly.game.gong2.login.service.sdk.impl.uc.PayCallbackResponse;
import com.gamejelly.game.gong2.login.service.sdk.impl.uc.SidInfoResponse;
import com.gamejelly.game.gong2.login.service.sdk.impl.uc.UCUtil;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.gamejelly.game.gong2.login.utils.SdkConst;
import com.hadoit.game.common.framework.http.SimpleHttpRequest;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

@SdkGroup({ SdkConst.CC_SDK_ANDROID_UC })
@Component("sdkUCService")
public class SdkUCService extends SdkBaseService implements SdkService {

	private String apiKey = "a03a14c55e8966f57200e35ef7377959";
	private int cpId = 74947;
	private int gameId = 735668;

	// sdk server的接口地址
	private String serverUrl = "http://sdk.9game.cn";
	// private String serverUrl = "http://sdk.test4.9game.cn"; // 测试接口

	// 游戏发行渠道编号
	private String channelId = "2";

	// 游戏服务器编号
	private int serverId = 0;

	/**
	 * 将Map数据组装成待签名字符串
	 * 
	 * @param params
	 *            待签名的参数列表
	 * @param notIn
	 *            不参与签名的参数名列表
	 * @return 待签名字符串。如果参数params为null，将返回null
	 */
	public static String createSignData(Map<String, Object> params, List<String> notInList) {
		if (null == params) {
			return null;
		}

		StringBuilder content = new StringBuilder(200);

		// 按照key排序
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);

			if (notInList != null && notInList.contains(key))
				continue;

			String value = params.get(key) == null ? "" : params.get(key).toString();
			content.append(key).append("=").append(value);
		}

		String result = content.toString();
		return result;
	}

	@Override
	public PlatformRetData handleSdkData(Map<String, Object> requestParams) {

		LoggerUtils.info("uc handleSdkData:", requestParams);

		String orderId = FsGameLoginUtils.getMapString(requestParams, "orderId", "");
		String notifyUrl = FsGameLoginUtils.getMapString(requestParams, "notifyUrl", "");
		String accountId = FsGameLoginUtils.getMapString(requestParams, "accountId", "");
		PlatformPayRecord ppr = platformPayRecordService.getByOrderSn(orderId);
		if (ppr == null) {
			return new PlatformRetData(false, null);
		}

		String amount = String.format("%.2f", ppr.getProductAmount() / 100f);

		// accountId=123452132amount=100.00callbackInfo=xxxxxcpOrderId=XXXXXXnotifyUrl=http://192.168.1.1/notifypage.do123456
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("amount", amount);
		params.put("accountId", accountId);
		params.put("cpOrderId", orderId);
		// params.put("notifyUrl", notifyUrl);
		// params.put("callbackInfo", orderId);
		// params.put("signType", "MD5");

		String signSource = createSignData(params, null) + apiKey;

		String sign = UCUtil.hexMD5(signSource);// MD5加密签名
		LoggerUtils.info("uc signSource:", signSource);
		LoggerUtils.info("uc sign:", sign);

		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("sign", sign);
		ret.put("amount", amount);
		ret.put("accountId", accountId);

		LoggerUtils.info("uc handleSdkData ret", ret);

		return new PlatformRetData(true, ret);
	}

	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		LoggerUtils.info("uc payCallback", requestParams);
		HttpServletRequest request = (HttpServletRequest) requestParams.get("request");
		BufferedReader reader;
		try {
			reader = request.getReader();
		} catch (IOException e) {
			LoggerUtils.error(e);
			return "FAILURE";
		}

		// sign 的签名规则:
		// MD5(accountId=...+amount=...+callbackInfo=...+cpOrderId=...+creator=...+failedDesc=...+gameId=...
		// +orderId=...+orderStatus=...+payWay=...+apiKey)
		// (去掉+;替换...为实际 值)

		PayCallbackResponse rsp = GsonFactory.getDefault().getGson().fromJson(reader, PayCallbackResponse.class);
		String signSource = "accountId=" + rsp.getData().getAccountId() + "amount=" + rsp.getData().getAmount()
				+ "callbackInfo=" + rsp.getData().getCallbackInfo() + "cpOrderId=" + rsp.getData().getCpOrderId()
				+ "creator=" + rsp.getData().getCreator() + "failedDesc=" + rsp.getData().getFailedDesc() + "gameId="
				+ rsp.getData().getGameId() + "orderId=" + rsp.getData().getOrderId() + "orderStatus="
				+ rsp.getData().getOrderStatus() + "payWay=" + rsp.getData().getPayWay() + apiKey;

		LoggerUtils.info("uc payCallback signSource", signSource);

		String sign = FsGameLoginUtils.md5up(signSource.toString());

		LoggerUtils.info("uc payCallback orderState = ", rsp.getData().getOrderStatus());
		LoggerUtils.info("uc payCallback sign1 = ", sign, " sign2=", rsp.getSign());

		try {
			if (sign.toLowerCase().equals(rsp.getSign())) {
				if (rsp.getData().getOrderStatus().equals("S")) {
					processOrder(rsp.getData().getCpOrderId(), rsp.getData().getAmount(), true);
					return "SUCCESS";
				}
			}
		} catch (Exception e) {
			LoggerUtils.error(e);
			return "FAILURE";
		}

		return "FAILURE";
	}

	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {
		String Uin = FsGameLoginUtils.getMapString(requestParams, "Uin", ""); // 为空字符串，由服务端获取UCID
		String SessionId = FsGameLoginUtils.getMapString(requestParams, "SessionId", "");

		String accountId = "";
		try {
			accountId = sidInfo(SessionId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (accountId == null || accountId.equals("") || accountId.equals("0")) {
			return new PlatformRetData(false, null);
		}

		requestParams.put("name", accountId);

		return new PlatformRetData(true, null);
	}

	/**
	 * sid用户会话验证。
	 * 
	 * @param sid
	 *            从游戏客户端的请求中获取的sid值
	 * @throws Exception
	 */
	private String sidInfo(String sid) throws Exception {
		// System.out.println("开始调用sidInfo接口");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", System.currentTimeMillis());// 当前系统时间
		params.put("service", "ucid.user.sidInfo");

		Game game = new Game();
		game.setCpId(cpId);// cpid是在游戏接入时由UC平台分配，同时分配相对应cpId的apiKey
		game.setGameId(gameId);// gameid是在游戏接入时由UC平台分配
		game.setChannelId(channelId);// channelid是在游戏接入时由UC平台分配
		// serverid是在游戏接入时由UC平台分配，
		// 若存在多个serverid情况，则根据用户选择进入的某一个游戏区而确定。
		// 若在调用该接口时未能获取到具体哪一个serverid，则此时默认serverid=0
		game.setServerId(serverId);

		params.put("game", game);

		Map data = new HashMap();
		data.put("sid", sid);// 在uc sdk登录成功时，游戏客户端通过uc
								// sdk的api获取到sid，再游戏客户端由传到游戏服务器
		params.put("data", data);

		params.put("encrypt", "md5");
		/*
		 * 签名规则签名内容+apiKey 假定apiKey=202cb962234w4ers2aaa,sid=abcdefg123456
		 * 那么签名原文sid=abcdefg123456202cb962234w4ers2aaa
		 * 签名结果6e9c3c1e7d99293dfc0c81442f9a9984
		 */
		String signSource = "sid=" + sid + apiKey;// 组装签名原文
		String sign = UCUtil.getMD5Str(signSource);// MD5加密签名

		// System.out.println("[签名原文]" + signSource);
		// System.out.println("[签名结果]" + sign);

		params.put("sign", sign);

		String body = UCUtil.encodeJson(params);// 把参数序列化成一个json字符串
		// System.out.println("[请求参数]" + body);

		String accountVerUrl = serverUrl + "/cp/account.verifySession";
		SimpleHttpRequest shr = SimpleHttpRequest.createPost(accountVerUrl).createBody(body);
		String result = shr.sendGetString();

		SidInfoResponse rsp = (SidInfoResponse) UCUtil.decodeJson(result, SidInfoResponse.class);// 反序列化
		if (rsp != null) {// 反序列化成功，输出其对象内容

			// System.out.println("[id]" + rsp.getId());
			// System.out.println("[code]" + rsp.getState().getCode());
			// System.out.println("[msg]" + rsp.getState().getMsg());
			// System.out.println("[accountId]" + rsp.getData().getAccountId());
			// System.out.println("[nickName]" + rsp.getData().getNickName());

			return String.valueOf(rsp.getData().getAccountId());
		}

		return "";
	}

}
