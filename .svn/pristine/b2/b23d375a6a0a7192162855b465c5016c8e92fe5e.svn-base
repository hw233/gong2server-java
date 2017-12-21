package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.meta.PlatformPayRecord;
import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.service.sdk.SdkGroup;
import com.gamejelly.game.gong2.login.service.sdk.SdkService;
import com.gamejelly.game.gong2.login.service.sdk.pay.AlipayPayService;
import com.gamejelly.game.gong2.login.service.sdk.pay.WeixinPayService;
import com.gamejelly.game.gong2.login.utils.BadRequestException;
import com.gamejelly.game.gong2.login.utils.FsGameLoginConst;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.gamejelly.game.gong2.login.utils.SdkConst;
import com.hadoit.game.common.lang.Pair;

@SdkGroup({ SdkConst.CC_SDK_TEST })
@Component("SdkTestService")
public class SdkTestService extends SdkBaseService implements SdkService {

	public static String PAY_SOURCE_ALIPAY = "iosalipay";
	public static String PAY_SOURCE_WEIXIN = "iosweixin";

	@Autowired
	private AlipayPayService alipayPayService;

	@Autowired
	private WeixinPayService weixinPayService;

	/**
	 * 参数编码
	 * 
	 * @param data
	 * @return
	 */
	public static String httpBuildQuery(Map<String, Object> data) {

		Object[] keys = data.keySet().toArray();
		Arrays.sort(keys);

		String ret = "";
		String k, v;

		for (Object key : keys) {
			k = key.toString();
			v = (String) data.get(String.valueOf(k));
			try {
				ret += k + "=" + URLEncoder.encode(v, "utf-8");
			} catch (UnsupportedEncodingException e) {
			}
			ret += "&";
		}
		return ret.substring(0, ret.length() - 1);
	}

	@Override
	public PlatformRetData handleSdkData(Map<String, Object> requestParams) {
		LoggerUtils.info("SdkAppleService handleSdkData:", requestParams);
		super.handleSdkData(requestParams);
		String source = FsGameLoginUtils.getMapString(requestParams, "source", "");
		if (PAY_SOURCE_ALIPAY.equalsIgnoreCase(source)) {
			return alipayPayService.handleSdkData(requestParams);
		} else if (PAY_SOURCE_WEIXIN.equalsIgnoreCase(source)) {
			return weixinPayService.handleSdkData(requestParams);
		}
		return new PlatformRetData(false, null);
	}

	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		LoggerUtils.info("SdkAppleService payCallback", requestParams);
		// 支付宝和微信都有这个字段
		String out_trade_no = FsGameLoginUtils.getMapString(requestParams, "out_trade_no", "");
		if (StringUtils.isEmpty(out_trade_no)) {
			// 微信直接可能获取不到参数
			LoggerUtils.info("SdkAppleService payCallback out_trade_no null");
			return weixinPayService.payCallback(requestParams, this);
		}
		PlatformPayRecord ppr = platformPayRecordService.getByOrderSn(out_trade_no);
		if (ppr == null) {
			return null;
		}
		if (PAY_SOURCE_ALIPAY.equalsIgnoreCase(ppr.getSource())) {
			return alipayPayService.payCallback(requestParams, this);
		} else if (PAY_SOURCE_WEIXIN.equalsIgnoreCase(ppr.getSource())) {
			return weixinPayService.payCallback(requestParams, this);
		}
		return null;
	}

	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {
		String name = FsGameLoginUtils.getMapString(requestParams, "name", "");
		String pass = FsGameLoginUtils.getMapString(requestParams, "pass", "");
		String group = FsGameLoginUtils.getMapString(requestParams, "group", "");
		LoggerUtils.info("account checkPassword", name, pass, group);
		if (StringUtils.isEmpty(name)) {
			throw new BadRequestException("Paramter(name) is empty");
		}
		if (StringUtils.isEmpty(pass)) {
			throw new BadRequestException("Paramter(pass) is empty");
		}
		int retCode = gameAccountService.checkPassword(name, pass, group);
		return new PlatformRetData(retCode == FsGameLoginConst.CODE_OK, retCode);
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
