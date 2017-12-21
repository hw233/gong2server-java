package com.gamejelly.game.gong2.login.service.sdk.pay;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.meta.PlatformPayRecord;
import com.gamejelly.game.gong2.login.service.PlatformPayRecordService;
import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.service.sdk.impl.SdkBaseService;
import com.gamejelly.game.gong2.login.service.sdk.impl.AliPay.OrderInfoUtil2_0;
import com.gamejelly.game.gong2.login.service.sdk.impl.AliPay.SignUtils;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.hadoit.game.common.lang.Triple;

@Component("alipayPayService")
public class AlipayPayService {

	/**
	 * 默认支付宝(gd)
	 */
	private static String DefaultAppId = "2016122904721955";
	private static String DefaultPid = "2088521545290063";
	private static String DefaultPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
	private static String DefaultPrivateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDQ0FAbw5NJPEl2Ug47Ob1iOlvx7P51/TFnH6zK6wu8TKoQuXqEcWISayIDndSbfrfwIz2tddRk5eobJoctCaXsfFTXOd/4tCHgkig7IpfIsmBKB+ytNJIrwPUHmPYwv4LdxH6f/Nz2W1b20wLkvtRoN5TMbKP+GH8ptZXNws119lGtc3w2VXC6hY+RMAUm3P4gfm+sSp32zfJtU1RNwuxetDDnFLmJ/o7XEFpNbjw3PjqKzS51DvfVSvJDlmH3df5QSlyyB78H7ukqCKqKnOfJe8OxpDP0+SSvbB9o2IZqaoL896NfqUcjct4hLBLMZJPB9hycornM3cGnUe5r9J5dAgMBAAECggEARxjGTc23VlYqhRjHM68jJlDMgYC52x6MNZp5UNXatxA7T1p3tqSMSc0kVarletQYeLDnDSpzWK9XmErz94ezcOOcV3OjKKDccAQTop7So0jotAwir46YvkECrMWN/WcXfb6rhnmoGlFbOMr7zzqSQ7G4/il3ZMqecWscSGStZB4ORBzw4fCGvT3SDdACt1znwCiPdeNhxWvKuRkrFoY+BSW7maaEX4IRwec8lmdqvBDia6RaITPrfmWCPqvcdNYdVyvsgf+PF9QxLsrTHfKJjyXLU+M8l4wp7AofhEOkIdQ3tTmLRJRt62F/mHJTudtB7eRBjBrVbJ19Urd5zMEo2QKBgQD28eDTz0l8EklRWCrvMV7zlv0cIteuCy8lAEW/3/IFWyfRPRfX4yklucv1miY9kQ+40hp51mDD/+P/3ECHkUTdbmqULFqxrSBpnpK1GmcOHleBwQQDhMp8/88G1VuMrc0ki+9zCkb5gMu73Z2eOxbhmSu15utC9Dn8mRqx/+ZnYwKBgQDYeH13y167/C9+W994868btf4PnSY2VIIM574Sih18wu/4auMXOoEMpTTO038M27G/dgiF+i99qvEjgR/lW+fofboE5FjmQ1eD/B8uT4pRPWEIt9pN0nKxmJN8rob/gIpCxqY0/XA5rNjkD1tglIcJ3aSSKfnN6CPliyqWV4IvPwKBgQChQpDsIIg/Un24dhgLfTUhYy9EqWgpCrqtsgDabPbSEpXs6LViUMB1F2CesRxZxhn7OpD5kUo9lCQi2IT3QqEy7MECo/hR4Nmyv0/UnjpAGQZSAGaLmbLVcAOU1c1kD7dQL2WdvU9d7YKd2vnr83BcwDawIezuGyim0KCDLrYaOwKBgCpwzSCchdFTazhZ6OF83VsQfmQvSluii/Ddyp016PwZ+BR7WFSoir6g8U6Plrw9oobILHmgCImZ5zcvzMIaF8i0RbFJ18glcdYVq0NYIuU0pH6iFTHmzcfWAyyRxzP2zqSy78k52zPjmI52GFNCK4uaofZqNmjRV6BcfZgGRibfAoGAEzXTxTcvjxCP+6FkBURaXFB1yoAlJqSFCJPArX8zNX3Un5ZFL86IvlAmR1uxIK0tqSNOU+do36J6eUOfgFuUL8oXqeaaonbuKe0EY3i87LEKIgyA2W1DDPQdhQ+BmelYenRpvTneqRjq6yLqtPOO86cU6m4b8ZLyKk3QMW4gU/o=";

	/**
	 * 游动支付宝
	 */
	private static String YouDongAppId = "2017050907184188";
	private static String YouDongPid = "2088721016618583";
	private static String YouDongPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
	private static String YouDongPrivateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANJOCdbxcPeLcnfwdcVJ91ZHaKX3RuIOEbZvZ9Sz5ttX6j6ucr1tKgqnRVqAJPO+g7UuOABu8Ew8N8rVz/BbB1F4qncTMhUlDEW3pRdihwEOTYxhlUZws5UnaQnaidp+TI7t8rclxmrrJHCgQcWvYEV2MQ/RDKmOCEop8aidmKQ5AgMBAAECgYAdV7YXv2g7ocE+6tYDyOYxq5T2UyIYuE3QXormKpOzVvjf9gccVvbetaf/kJGXUKWfyesWZGQyvtQqANsadhj3DDyEo/ddhnolRTCEWF2w8/DMquPNWgNu997DoEpvaf7qnpVKvCnXmkJuae4U1Y6uW3+YW8Ni3vzsY6f0bVl14QJBAPWbab3cGfEc75NPXVXfymRMoUHdCPdaQZF+bf+0ZKvGCMrsEjJxVE2dN3cpezojB0E2CU+2aLWW3fr+HwNonfMCQQDbNDT2e8pv9/drnrLbi9oeMizeToTCHiugSFrt7PxONGvHMy2IXp0bkUh+FBHqRzZOkhk5j6Dc7aHro1prb8QjAkEA3e0KRSYmwrmUuRUTj07iAHNFms/xFCCX8rG9DzqfZJ9SvplFcBL7TA5tWd0/na6dFY2QKFj4kGy8M9l/iIL1hQJARvH3rkrUczbDKl67rpjt6FX+9ZheGWGgZwjj/Rt1cW4/78Hdm8Rbft+focanIbcYmYdpiJq2yqpSfKxrCFitFwJAfAnT9h9JZNKDcRLvKYy0Q/jSFXDryO5h0QPpx3YZaEuX12YCGI641qFKUSycpFkOTuwPc5FEcPHuuITCIRHIXQ==";

	// 下单接口
	private static Map<String, Triple<String, String, String>> alipayMap;
	static {
		alipayMap = new HashMap<String, Triple<String, String, String>>();
		alipayMap.put(DefaultAppId, Triple.newInstance(DefaultPid, DefaultPublicKey, DefaultPrivateKey));
		// myj TODO

		// 游动
		alipayMap.put(YouDongAppId, Triple.newInstance(YouDongPid, YouDongPublicKey, YouDongPrivateKey));
	}

	private static String getPrvKey(String appId) {
		Triple<String, String, String> currData = alipayMap.get(appId);
		String curr = DefaultPrivateKey;
		if (currData != null) {
			if (StringUtils.isNotBlank(currData.getThird())) {
				curr = currData.getThird();
			}
		}
		return curr;
	}

	private static String getPubKey(String appId) {
		Triple<String, String, String> currData = alipayMap.get(appId);
		String curr = DefaultPublicKey;
		if (currData != null) {
			if (StringUtils.isNotBlank(currData.getSecond())) {
				curr = currData.getSecond();
			}
		}
		return curr;
	}

	private static String getPid(String appId) {
		Triple<String, String, String> currData = alipayMap.get(appId);
		String curr = DefaultPid;
		if (currData != null) {
			if (StringUtils.isNotBlank(currData.getFirst())) {
				curr = currData.getFirst();
			}
		}
		return curr;
	}

	@Autowired
	protected PlatformPayRecordService platformPayRecordService;

	public PlatformRetData handleSdkData(Map<String, Object> requestParams) {
		LoggerUtils.info("Alipay handleSdkData:", requestParams);

		String orderId = FsGameLoginUtils.getMapString(requestParams, "orderId", "");
		String orderName = FsGameLoginUtils.getMapString(requestParams, "orderName", "");
		String notifyUrl = FsGameLoginUtils.getMapString(requestParams, "notifyUrl", "");
		String appId = FsGameLoginUtils.getMapString(requestParams, "appId", "");
		PlatformPayRecord ppr = platformPayRecordService.getByOrderSn(orderId);
		if (ppr == null) {
			return new PlatformRetData(false, null);
		}

		String priKey = getPrvKey(appId);
		String amount = Float.toString(ppr.getProductAmount() / 100f);
		Map<String, String> paramsTest = OrderInfoUtil2_0.buildOrderParamMap(appId, amount, orderId, orderName,
				ppr.getAccountName() + "_" + ppr.getServerId(), notifyUrl);
		String orderParam = OrderInfoUtil2_0.buildOrderParam(paramsTest);
		String sign = OrderInfoUtil2_0.getSign(paramsTest, priKey);
		final String orderInfo = orderParam + "&" + sign;
		LoggerUtils.info("Ali_pay orderInfo = ", orderInfo);

		return new PlatformRetData(true, orderInfo);
	}

	public Object payCallback(Map<String, Object> requestParams, SdkBaseService sdkService) {
		LoggerUtils.info("Ali_pay payCallback before", requestParams);

		String app_id = FsGameLoginUtils.getMapString(requestParams, "app_id", "");
		String sign = FsGameLoginUtils.getMapString(requestParams, "sign", "");
		String out_trade_no = FsGameLoginUtils.getMapString(requestParams, "out_trade_no", "");
		String total_amount = FsGameLoginUtils.getMapString(requestParams, "total_amount", "");
		String seller_id = FsGameLoginUtils.getMapString(requestParams, "seller_id", "");
		String pubKey = getPubKey(app_id);
		String pid = getPid(app_id);

		FsGameLoginUtils.transferParams(requestParams);

		Map<String, Object> signParams = new HashMap<String, Object>();
		for (Map.Entry<String, Object> entry : requestParams.entrySet()) {
			if (!"sign".equals(entry.getKey()) && !"sign_type".equals(entry.getKey())
					&& !"request".equals(entry.getKey()) && !"response".equals(entry.getKey())
					&& (!"sdkId".equals(entry.getKey()))) {
				signParams.put(entry.getKey(), entry.getValue().toString());
			}
		}
		LoggerUtils.info("Ali_pay payCallback signParams = ", signParams);
		LoggerUtils.info("Ali_pay payCallback sign = ", sign);
		String trade_status = FsGameLoginUtils.getMapString(requestParams, "trade_status", "");

		// 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，2、判断total_fee是否确实为该订单的实际金额（即商户订单创建时的金额），3、校验通知中的seller_id（或者seller_email)
		// 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email），4、判断partner_id是否为商户本身。上述步骤1、2、3、4任何一个验证不通过，则表明本次通知是异常通知，务必忽略。在上述验证通过后商户必须根据支付宝不同类型的业务通知，正确的进行不同的业务处理，并且过滤重复的通知结果数据。在支付宝的业务通知中，只有交易通知状态为TRADE_SUCCESS或TRADE_FINISHED时，支付宝才会认定为买家付款成功。
		// 只有交易通知状态为TRADE_SUCCESS或TRADE_FINISHED时，支付宝才会认定为买家付款成功。

		boolean tradeState = false;
		LoggerUtils.info("Ali_pay payCallback trade_status =", trade_status);
		if (StringUtils.equals(trade_status, "TRADE_SUCCESS") || StringUtils.equals(trade_status, "TRADE_FINISHED")) {
			tradeState = true;
		}

		if (!tradeState) {
			LoggerUtils.error("Ali_pay payCallback tradeState = ", tradeState);
			return "failure";
		}

		try {
			// 签名字符串"
			boolean checkResult = SignUtils.rsaDoCheck(signParams, sign, pubKey);
			SdkBaseService sss;
			if (checkResult) {
				// 二次验证
				PlatformPayRecord ppr = platformPayRecordService.getByOrderSn(out_trade_no);
				if (ppr == null || !sdkService.checkPrice(ppr, total_amount, true)) {
					LoggerUtils.error("Ali_pay payCallback check ppr null!");
					return "failure";
				}

				if (!seller_id.equalsIgnoreCase(pid)) {
					LoggerUtils.error("check seller_id error!", ppr.getAccountName(), ppr.getServerId(),
							ppr.getOprGroup(), ppr.getProductId(), ppr.getOrderSn(), pid, seller_id);
					return "failure";
				}
				if (app_id.equals(YouDongAppId)) {
					sdkService.postYdong(out_trade_no, total_amount, true);
				}
                
				sdkService.processOrder(out_trade_no, total_amount, true,"alipay");
				return "success";
			} else { // 验签失败
				return "failure";
			}

		} catch (Exception e) {
			LoggerUtils.error(e);
		}

		return "success";
	}

	public static void main(String[] args) {
		System.out.println(getPubKey(YouDongAppId));
		System.out.println(getPrvKey(YouDongAppId));
		System.out.println(getPid(YouDongAppId));

		// lback before {body=8EE447B7-46D8-4667-9F5F-0289B40BE525@ios11_80,
		// subject=10元宝,
		// sign_type=RSA, buyer_logon_id=per***@hotmail.com,
		// auth_app_id=2015112900888487,
		// notify_type=trade_status_sync,
		// out_trade_no=73c1479403e34f14ab45f978ff85b96f,
		// version=1.0, point_amount=0.00,
		// response=org.tuckey.web.filters.urlrewrite.UrlRewriteWrappedResponse@5508f8c1,
		// request=org.apache.catalina.connector.RequestFacade@40037284,
		// fund_bill_list=[{"amount":"1.00","fundChannel":"ALIPAYACCOUNT"}],
		// buyer_id=2088002150091729, total_amount=1.00,
		// trade_no=2017050921001004720290775756,
		// notify_time=2017-05-09 17:13:54, sdkId=23, charset=utf-8,
		// invoice_amount=1.00,
		// trade_status=TRADE_SUCCESS, gmt_payment=2017-05-09 17:13:53,
		// sign=Zt2Wd/olKf61m842v35LkpBaRAz/y/nS/uEaJyHeQfKkfAJPTEocFMZRvRAC+FT1VjTGA4UosEas89M0yLCJdNo6IUoCEoVvqx85PRRFquo1DehQ96pFf01ZR65fKrLmTSQE9XPb2qPZaFHSEmayR/dx0jrGtpDDh1HYsg8ZjeU=,
		// gmt_create=2017-05-09 17:13:53, buyer_pay_amount=1.00,
		// receipt_amount=1.00, seller_id=2088111082856435,
		// app_id=2015112900888487, seller_email=funing@youdong.com,
		// notify_id=feb02edacf288369acf2e338027e8dblk2}
		// boolean checkResult = SignUtils.rsaDoCheck(signParams, sign, pubKey);

		Map<String, Object> requestParams = new HashMap<String, Object>();
		requestParams.put("body", "8EE447B7-46D8-4667-9F5F-0289B40BE525@ios11_80");
		requestParams.put("subject", "10元宝");
		requestParams.put("sign_type", "RSA");
		requestParams.put("buyer_logon_id", "per***@hotmail.com");
		requestParams.put("auth_app_id", "2015112900888487");
		requestParams.put("notify_type", "trade_status_sync");
		requestParams.put("out_trade_no", "73c1479403e34f14ab45f978ff85b96f");
		requestParams.put("version", "1.0");
		requestParams.put("point_amount", "0.00");
		requestParams.put("fund_bill_list", "[{\"amount\":\"1.00\",\"fundChannel\":\"ALIPAYACCOUNT\"}]");
		requestParams.put("buyer_id", "2088002150091729");
		requestParams.put("total_amount", "1.00");
		requestParams.put("trade_no", "2017050921001004720290775756");
		requestParams.put("notify_time", "2017-05-09 17:13:54");
		requestParams.put("sdkId", "23");
		requestParams.put("charset", "utf-8");
		requestParams.put("invoice_amount", "1.00");
		requestParams.put("trade_status", "TRADE_SUCCESS");
		requestParams.put("gmt_payment", "2017-05-09 17:13:53");
		requestParams.put("sign",
				"Zt2Wd/olKf61m842v35LkpBaRAz/y/nS/uEaJyHeQfKkfAJPTEocFMZRvRAC+FT1VjTGA4UosEas89M0yLCJdNo6IUoCEoVvqx85PRRFquo1DehQ96pFf01ZR65fKrLmTSQE9XPb2qPZaFHSEmayR/dx0jrGtpDDh1HYsg8ZjeU=");
		requestParams.put("gmt_create", "2017-05-09 17:13:53");
		requestParams.put("buyer_pay_amount", "1.00");
		requestParams.put("receipt_amount", "1.00");
		requestParams.put("seller_id", "2088111082856435");
		requestParams.put("app_id", "2015112900888487");
		requestParams.put("seller_email", "funing@youdong.com");
		requestParams.put("notify_id", "feb02edacf288369acf2e338027e8dblk2");

		String app_id = FsGameLoginUtils.getMapString(requestParams, "app_id", "");
		String sign = FsGameLoginUtils.getMapString(requestParams, "sign", "");
		String out_trade_no = FsGameLoginUtils.getMapString(requestParams, "out_trade_no", "");
		String total_amount = FsGameLoginUtils.getMapString(requestParams, "total_amount", "");
		String seller_id = FsGameLoginUtils.getMapString(requestParams, "seller_id", "");
		String pubKey = getPubKey(app_id);
		String pid = getPid(app_id);

		FsGameLoginUtils.transferParams(requestParams);

		Map<String, Object> signParams = new HashMap<String, Object>();
		for (Map.Entry<String, Object> entry : requestParams.entrySet()) {
			if (!"sign".equals(entry.getKey()) && !"sign_type".equals(entry.getKey())
					&& !"request".equals(entry.getKey()) && !"response".equals(entry.getKey())
					&& (!"sdkId".equals(entry.getKey()))) {
				signParams.put(entry.getKey(), entry.getValue().toString());
			}
		}
		LoggerUtils.info("Ali_pay payCallback signParams = ", signParams);
		LoggerUtils.info("Ali_pay payCallback sign = ", sign);
		String trade_status = FsGameLoginUtils.getMapString(requestParams, "trade_status", "");

		// 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，2、判断total_fee是否确实为该订单的实际金额（即商户订单创建时的金额），3、校验通知中的seller_id（或者seller_email)
		// 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email），4、判断partner_id是否为商户本身。上述步骤1、2、3、4任何一个验证不通过，则表明本次通知是异常通知，务必忽略。在上述验证通过后商户必须根据支付宝不同类型的业务通知，正确的进行不同的业务处理，并且过滤重复的通知结果数据。在支付宝的业务通知中，只有交易通知状态为TRADE_SUCCESS或TRADE_FINISHED时，支付宝才会认定为买家付款成功。
		// 只有交易通知状态为TRADE_SUCCESS或TRADE_FINISHED时，支付宝才会认定为买家付款成功。

		boolean tradeState = false;
		LoggerUtils.info("Ali_pay payCallback trade_status =", trade_status);
		if (StringUtils.equals(trade_status, "TRADE_SUCCESS") || StringUtils.equals(trade_status, "TRADE_FINISHED")) {
			tradeState = true;
		}

		if (!tradeState) {
			LoggerUtils.error("Ali_pay payCallback tradeState = ", tradeState);
		}

		boolean checkResult = SignUtils.rsaDoCheck(signParams, sign, pubKey);

		System.out.println(checkResult);
	}
}
