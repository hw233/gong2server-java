package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.meta.PlatformPayRecord;
import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.service.sdk.SdkGroup;
import com.gamejelly.game.gong2.login.service.sdk.SdkService;
import com.gamejelly.game.gong2.login.utils.BadRequestException;
import com.gamejelly.game.gong2.login.utils.FsGameLoginConst;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.gamejelly.game.gong2.login.utils.SdkConst;
import com.hadoit.game.common.lang.Pair;

@SdkGroup({ SdkConst.CC_SDK_IOS_APPLE_CB })
@Component("sdkChangbaService")
public class SdkChangbaService extends SdkBaseService implements SdkService {

	@Autowired
	private SdkAppleService sdkAppleService;
	
	@Autowired
	private SdkTestService sdkTestService;
	
	private static String appId  = "wdgtios";
	private static String secretKey = "bbe990842928e4478f37d0765c937a65";
	private static String authUrl = "https://openapi.changba.com/oauth2/server/token.php";
	private static String redirectUri = "wdgtios://oauth";
//	应用ID	wdgtios
//	游戏私钥	bbe990842928e4478f37d0765c937a65
//	授权回调地址	wdgtios://oauth
	
	
	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {
		LoggerUtils.info("changba loginAuth requestParams=", requestParams);
		String Uin = FsGameLoginUtils.getMapString(requestParams, "Uin", "");
		String SessionId = FsGameLoginUtils.getMapString(requestParams, "SessionId", "");
		int nativeAppleAuth = FsGameLoginUtils.getMapInteger(requestParams, "appleAuthType", 1);
		boolean result = false;
		if(nativeAppleAuth==1){
			// 原生登录
			return sdkAppleService.loginAuth(requestParams);
		}else if(nativeAppleAuth==2){
			//唱吧登录
			result = true;
		}
		return new PlatformRetData(result, null);
	}

	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		return sdkAppleService.payCallback(requestParams);
	}

	@Override
	public PlatformRetData verifyOrder(Map<String, Object> requestParams) {
		return sdkAppleService.verifyOrder(requestParams);
	}

	@Override
	public PlatformRetData createOrder(Map<String, Object> requestParams) {
		return sdkAppleService.createOrder(requestParams);
	}

	@Override
	public Object addItemCallback(Map<String, Object> requestParams) {
		return sdkAppleService.addItemCallback(requestParams);
	}

	@Override
	public Object getUserCallback(Map<String, Object> requestParams) {
		return sdkAppleService.getUserCallback(requestParams);
	}

	@Override
	public void createAccount(Map<String, Object> requestParams, String name, String pass, String platform,
			String deviceName, String deviceVersion, String deviceId, String ip) {
		Pair<Integer, String> createData = gameAccountService.createAccount(name, pass, platform, deviceName,
				deviceVersion, deviceId, ip);
		requestParams.put("retCode", createData.getFirst());
		requestParams.put("ticket", createData.getSecond());
	}

	@Override
	public void resetPassword(Map<String, Object> requestParams, String name, String oldPass, String newPass) {
		Pair<Integer, String> retData = gameAccountService.resetPass(name, oldPass, newPass);
		requestParams.put("retCode", retData.getFirst());
		requestParams.put("ticket", retData.getSecond());
	}
}
