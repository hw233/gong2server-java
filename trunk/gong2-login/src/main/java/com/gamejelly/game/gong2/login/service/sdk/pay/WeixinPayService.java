package com.gamejelly.game.gong2.login.service.sdk.pay;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.meta.PlatformPayRecord;
import com.gamejelly.game.gong2.login.service.PlatformPayRecordService;
import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.service.sdk.impl.SdkBaseService;
import com.gamejelly.game.gong2.login.service.sdk.impl.weixin.HttpUtil;
import com.gamejelly.game.gong2.login.service.sdk.impl.weixin.PayCommonUtil;
import com.gamejelly.game.gong2.login.service.sdk.impl.weixin.XMLUtil;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.hadoit.game.common.lang.Pair;

@Component("weixinPayService")
public class WeixinPayService {
	public final static String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";// 微信支付统一接口(POST)
	public final static String SIGN_TYPE = "MD5";// 签名加密方式
	public final static String TRADE_TYPE = "APP";// 支付类型
	
	
//	清宫梦的微信支付参数如下：
//	微信APPID是
//	wx4b8178f685bb8768
//
//	微信APPSECRET是
//	8730fc59f881b7c08c085999d6ca32d1
//
//	商户MCHID是
//	1462284202
//
//	商户api秘钥KEY是
//	f8gkt35jkh85412g161bh75624f67jkt
	/**
	 * 默认微信(gd)
	 */
	private static String DefaultAppId = "wx98c50c01581fbf70";
	private static String DefaultMchId = "1378140402";
	private static String DefaultApiKey = "ayiuenklai34otnnjsavia49sklmvavj";
	
	private static String YouDongAppId = "wx4b8178f685bb8768";
	private static String YouDongMchId = "1462284202";
	private static String YouDongApiKey = "f8gkt35jkh85412g161bh75624f67jkt";

	// 下单接口
	private static Map<String, Pair<String, String>> weixinMap;
	static {
		weixinMap = new HashMap<String, Pair<String, String>>();
		weixinMap.put(DefaultAppId, Pair.newInstance(DefaultMchId, DefaultApiKey));
		weixinMap.put(YouDongAppId, Pair.newInstance(YouDongMchId, YouDongApiKey));
		//myj TODO
	}
	
	@Autowired
	protected PlatformPayRecordService platformPayRecordService;
	
	
	private static String getMchId(String appId) {
		Pair<String, String> currData = weixinMap.get(appId);
		String curr = DefaultMchId;
		if (currData != null) {
			if (StringUtils.isNotBlank(currData.getFirst())) {
				curr = currData.getFirst();
			}
		}
		return curr;
	}
	
	private static String getApiKey(String appId) {
		Pair<String, String> currData = weixinMap.get(appId);
		String curr = DefaultApiKey;
		if (currData != null) {
			if (StringUtils.isNotBlank(currData.getSecond())) {
				curr = currData.getSecond();
			}
		}
		return curr;
	}
	

	
	/**
	 * 生成订单信息
	 * 
	 * @param ip
	 * @param orderId
	 * @return
	 */
	private SortedMap<String, Object> prepareOrder(String ip, String orderId, int price, String orderName,
			String notifyUrl, String appId, String mchId) {
		Map<String, Object> oparams = new HashMap<String, Object>();

		oparams.put("appid", appId);// 服务号的应用号
		oparams.put("body", orderName);// 商品描述
		oparams.put("mch_id", mchId);// 商户号 ？
		oparams.put("nonce_str", PayCommonUtil.CreateNoncestr());// 16随机字符串(大小写字母加数字)
		oparams.put("out_trade_no", orderId);// 商户订单号
		oparams.put("total_fee", String.valueOf(price));// 银行币种 price
		oparams.put("spbill_create_ip", ip);// IP地址
		oparams.put("notify_url", notifyUrl); // 微信回调地址
		oparams.put("trade_type", TRADE_TYPE);// 支付类型 app
		return PayCommonUtil.sortMap(oparams);
	}

	/**
	 * 生成预付快订单完成，返回给android,ios唤起微信所需要的参数。
	 * 
	 * @param resutlMap
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private SortedMap<String, Object> buildClientJson(Map<String, Object> resutlMap, String appId, String mchId, String apiKey)
			throws UnsupportedEncodingException {
		// 获取微信返回的签名
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appid", appId);
		params.put("noncestr", PayCommonUtil.CreateNoncestr());
		params.put("package", "Sign=WXPay");
		params.put("partnerid", mchId);
		params.put("prepayid", resutlMap.get("prepay_id"));
		params.put("timestamp", String.valueOf((int) (System.currentTimeMillis() / 1000)));
		// key ASCII排序
		SortedMap<String, Object> sortMap = PayCommonUtil.sortMap(params);
		sortMap.put("package", "Sign=WXPay");
		// paySign的生成规则和Sign的生成规则同理
		String paySign = PayCommonUtil.createSign("UTF-8", sortMap, apiKey);
		sortMap.put("sign", paySign);
		return sortMap;
	}

	public PlatformRetData handleSdkData(Map<String, Object> requestParams) {
		try {
			String orderId = FsGameLoginUtils.getMapString(requestParams, "orderId", "");
			String orderName = FsGameLoginUtils.getMapString(requestParams, "orderName", "");
			String notifyUrl = FsGameLoginUtils.getMapString(requestParams, "notifyUrl", "");
			String ip = FsGameLoginUtils.getMapString(requestParams, "ip", "");
			String appId = FsGameLoginUtils.getMapString(requestParams, "appId", "");
			
			String mchId = getMchId(appId);
			String apiKey = getApiKey(appId);
			PlatformPayRecord ppr = platformPayRecordService.getByOrderSn(orderId);
			if (ppr == null) {
				return new PlatformRetData(false, null);
			}

			int price = ppr.getProductAmount();

			LoggerUtils.info("统一下定单开始");
			// 设置订单参数
			SortedMap<String, Object> parameters = prepareOrder(ip, orderId, price, orderName, notifyUrl, appId, mchId);
			parameters.put("sign", PayCommonUtil.createSign("UTF-8", parameters, apiKey));// sign签名
																					// key
			String requestXML = PayCommonUtil.getRequestXml(parameters);// 生成xml格式字符串
			String responseStr = HttpUtil.httpsRequest(UNIFIED_ORDER_URL, "POST", requestXML);// 带上post
			// 检验API返回的数据里面的签名是否合法，避免数据在传输的过程中被第三方篡改
			if (!PayCommonUtil.checkIsSignValidFromResponseString(responseStr, apiKey)) {
				LoggerUtils.error("微信统一下单失败,签名可能被篡改");
				return new PlatformRetData(false, null);
			}
			// 解析结果 resultStr
			SortedMap<String, Object> resutlMap = XMLUtil.doXMLParse(responseStr);
			if (resutlMap != null && "FAIL".equals(resutlMap.get("return_code"))) {
				LoggerUtils.error("微信统一下单失败,订单编号:" + orderId + ",失败原因:" + resutlMap.get("return_msg"));
				return new PlatformRetData(false, null);
			}
			// 获取到 prepayid
			// 商户系统先调用该接口在微信支付服务后台生成预支付交易单，返回正确的预支付交易回话标识后再在APP里面调起支付。
			SortedMap<String, Object> map = buildClientJson(resutlMap, appId, mchId, apiKey);
			map.put("outTradeNo", orderId);
			LoggerUtils.info("统一下定单结束");
			return new PlatformRetData(true, map);
		} catch (Exception e) {
			LoggerUtils.error(
					"com.fs.module.weixin.logic.WeixinLogic receipt(String userId,String proId,String ip)：{},{}", e);
			return new PlatformRetData(false, null);
		}
	}
	
	private String parseWeixinCallback(HttpServletRequest request) throws IOException {
		InputStream inStream = request.getInputStream();
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inStream.close();
		String result = new String(outSteam.toByteArray(), "utf-8");// 获取微信调用我们notify_url的返回信息
		return result;
	}
	 
	public Object payCallback(Map<String, Object> requestParams, SdkBaseService sdkService) {
		LoggerUtils.info("Weixin_pay payCallback before", requestParams);
		HttpServletRequest request = (HttpServletRequest)requestParams.get("request");
		try {
			String responseStr = parseWeixinCallback(request);
			String appId = FsGameLoginUtils.getMapString(requestParams, "appid", "");
			String apiKey = getApiKey(appId);
			Map<String, Object> map = XMLUtil.doXMLParse(responseStr);
			LoggerUtils.info("Weixin_pay payCallback map", map);
			// 校验签名 防止数据泄漏导致出现“假通知”，造成资金损失
			if (!PayCommonUtil.checkIsSignValidFromResponseString(responseStr, apiKey)) {
				LoggerUtils.error("微信回调失败,签名可能被篡改");
				return PayCommonUtil.setXML("FAIL", "invalid sign");
			}
			if ("FAIL".equalsIgnoreCase(map.get("result_code")
					.toString())) {
				LoggerUtils.error("微信回调失败");
				return PayCommonUtil.setXML("FAIL", "weixin pay fail");
			}
			if ("SUCCESS".equalsIgnoreCase(map.get("result_code")
					.toString())) {
				// 对数据库的操作
				String outTradeNo = (String) map.get("out_trade_no");
				//若为微信支付，更新支付方式和商家订单号，字段
				platformPayRecordService.updateSource(outTradeNo, "iosweixin");
				String transactionId = (String) map.get("transaction_id");
				//若为微信支付，更新支付方式和商家订单号，字段
				platformPayRecordService.updatePayPlatformOrderId(outTradeNo, transactionId);
				String totlaFee = (String) map.get("total_fee");
				sdkService.processOrder(outTradeNo, totlaFee, false,"weixinpay");
				return PayCommonUtil.setXML("SUCCESS", "OK");
			}
		} catch (Exception e) {
			LoggerUtils.error("支付失败", e);
			return PayCommonUtil.setXML("FAIL",
					"weixin pay server exception");
		}
		return PayCommonUtil.setXML("FAIL", "weixin pay fail");
	}
}
