package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.service.sdk.SdkGroup;
import com.gamejelly.game.gong2.login.service.sdk.SdkService;
import com.gamejelly.game.gong2.login.service.sdk.impl.huawei.CommonUtil;
import com.gamejelly.game.gong2.login.service.sdk.impl.huawei.RSA;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.gamejelly.game.gong2.login.utils.SdkConst;
import com.hadoit.game.common.lang.DataUtils;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

@SdkGroup({ SdkConst.CC_SDK_ANDROID_HUAWEI })
@Component("SdkHuaweiService")
public class SdkHuaweiService extends SdkBaseService implements SdkService {

	private String AppID = "10697394";
	private String AppSecret = "915c56dcf86a81be0a042cb0efc01e1a";

	// 支付ID
	private String PayID = "890086000102034263";
	// 支付测试公钥
	private String PayPublicKeyTest = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIW1g+KAqqOeC1ypte8L3qTDk2nz6jUbM6o6Jg9obvivPnCAm/wZvV3jWbYWfOuO/wrFJygn/jZqf8cR1T1CQa8CAwEAAQ==";
	// 支付公钥
	private String PayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgwy+Blmgqf2RVdcwEkVMIKVkOLRoJyUF9fAkrfJaxnZkzDZFDwsi4fc4MQdRTnpnWx/t8vlJ4AlXfzJaeLXlep41pYdQA63DiJe+nEOTPhnVxRVTRb0iHxVeSWu37hOD67De6haXi4lXO8Ry55mayiMn3mtrBJz80pgpjgIakqHhY9LQBhRvR1QVEQxmu2kTjRj9HE3hpEl17PeJ5HNtNIfK8IUig6a60b1j8Dheimk2wXEHN30hls8fyeM/tg1nbq2v+0QvUVWq3ePXTpsXnHdXyAd0pd+3IchLYsB/SGMv5Zo2nkLo1EMpvOHc+IGFa4IlkPQUZeMJN46pwenp0wIDAQAB";
	// 支付私钥
	private String PayPrivateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCDDL4GWaCp/ZFV1zASRUwgpWQ4tGgnJQX18CSt8lrGdmTMNkUPCyLh9zgxB1FOemdbH+3y+UngCVd/Mlp4teV6njWlh1ADrcOIl76cQ5M+GdXFFVNFvSIfFV5Ja7fuE4PrsN7qFpeLiVc7xHLnmZrKIyfea2sEnPzSmCmOAhqSoeFj0tAGFG9HVBURDGa7aRONGP0cTeGkSXXs94nkc200h8rwhSKDprrRvWPwOF6KaTbBcQc3fSGWzx/J4z+2DWdura/7RC9RVard49dOmxecd1fIB3Sl37chyEtiwH9IYy/lmjaeQujUQym84dz4gYVrgiWQ9BRl4wk3jqnB6enTAgMBAAECggEAdOFhfeJkRBiujePtsO3zVs6ME3L7zaXcqhvzatzTj1w6zFO+xgsFoonOqRPAtYDAO+q/W8xw0gsi6YQsYqrAr9r5N9f2SkGpOmM0ksRGR8Xc/fjmGzXzf8ucG1miYwkuyIAoYJmjscTdc6XjYaKDBrPSNDKT8Tpe1yj1Lv27W3kVBl4EMh6lQdpYoDilQMzfV9xO6vAmwTLkzf2i/+yTMhcGmKL9j7sIna5J2n2yZmzByxVIx5L0HMA8gt0YJENM6StEOb7vFROv7hoxNldZzJX6saI3KEvM0i7wiCYKwIf0Ghlclv+srlYEXuSGtoqqiqYi0mHWsNMDJZ67QTtJwQKBgQC3s+XukrRtfugfkTlxg+pDtX3oJNcgSfZpQ+FVDUFIl96YMATuQNXomcL9rPZYwZ1eXClP5aVM9SHkL37lumz/HQ6WMrdDaq+HR/d9GXdFSziaI1KGHKn9IbtX6DUtEcKb8nZCgAJS7MHaaKXOGPW4/6+deo6IYKzR4r8H2hiQoQKBgQC2oAtRP0vYayF0A6hGd7t/NCNVBOwqC2YSFUcHAjKvgg2VTCnlbC4B3xd/AB03PEWPxYLpyJ0slRQ7/PJSFa4BfUPJLb09T7zGATVwpsGwRQJSM41iOFjTRFhJxtG3k1R+p77hHyYjQqGZPMyQmujZnI+/Ty+SHTJPPexPvwgB8wKBgQC1vwdihMR7fE4dRSnG9BMmz/30WdZ4vJRMyvq5kfaf4pk4inGmYoD/sninT6MeZtfSDm5U/MgRHolcX901Xb8Om2neEekciwWEWyAxJavJ48TpQr8Xrm5D4Uafjz5lC6g1b6mu20TxVAnqkxRPloQtdtIBhMpVhQgWUzZah108YQKBgHIMCmVHsPbLGE4aBplTJm0uwy3R8CXljm2Wig+vaLW/8vmcYcGbSDP0/PhbuuMGqa46OyICHV19D/DSawWl8k+Nyao1/Srvw8SndpcOHIEW53csKIwBAj+VO8l+9vK8eBTMbuCpkiiM8NwAYwOfKfDT29Ut/1gxEXz4/71hX//PAoGAMbQMr6EYmgtQKlH0N4mGXoBC15eNA3CKE+Oi4BkF0mzk8kYRn9sgZ0+txo6gMIhU7Xpdr1YQUkxMvCHFQ9w0cgoElxwFGDwHeOXsm17ZIHM/OWFFtNIXKKlx6wEmUlxIRSv7MaY8kSkx5fx40jaeRBqI61sXBoxJtf5FwYlNZrA=";
	// CPID
	private String CpID = "890086000102034263";

	public static final String LOGIN_RSA_PUBLIC = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmKLBMs2vXosqSR2rojMzioTRVt8oc1ox2uKjyZt6bHUK0u+OpantyFYwF3w1d0U3mCF6rGUnEADzXiX/2/RgLQDEXRD22er31ep3yevtL/r0qcO8GMDzy3RJexdLB6z20voNM551yhKhB18qyFesiPhcPKBQM5dnAOdZLSaLYHzQkQKANy9fYFJlLDo11I3AxefCBuoG+g7ilti5qgpbkm6rK2lLGWOeJMrF+Hu+cxd9H2y3cXWXxkwWM1OZZTgTq3Frlsv1fgkrByJotDpRe8SwkiVuRycR0AHsFfIsuZCFwZML16EGnHqm2jLJXMKIBgkZTzL8Z+201RmOheV4AQIDAQAB";

	public Map<String, Object> getValue(Map<String, Object> requestParams) {
		// String line = null;
		// StringBuffer sb = new StringBuffer();
		// try {
		// request.setCharacterEncoding("UTF-8");
		// InputStream stream = request.getInputStream();
		// InputStreamReader isr = new InputStreamReader(stream);
		// BufferedReader br = new BufferedReader(isr);
		// while ((line = br.readLine()) != null) {
		// sb.append(line).append("\r\n");
		// }
		// System.out.println("The original data is : " + sb.toString());
		// br.close();
		// } catch (Exception e) {
		// e.printStackTrace();
		// } catch (Throwable e) {
		// e.printStackTrace();
		// }
		//
		// String str = sb.toString();
		// Map<String, Object> valueMap = new HashMap<String, Object>();
		// if (null == str || "".equals(str)) {
		// return valueMap;
		// }
		//
		// String[] valueKey = str.split("&");
		// for (String temp : valueKey) {
		// String[] single = temp.split("=");
		// valueMap.put(single[0], single[1]);
		// }

		Map<String, Object> valueMap = new HashMap<String, Object>(requestParams);
		valueMap.remove("request");
		valueMap.remove("response");

		// 接口中，如下参数sign和extReserved、sysReserved是URLEncode的，所以需要decode，其他参数直接是原始信息发送，不需要decode
		// try {
		// String sign = (String) valueMap.get("sign");
		// String extReserved = (String) valueMap.get("extReserved");
		// String sysReserved = (String) valueMap.get("sysReserved");
		//
		// if (null != sign) {
		// // sign = URLDecoder.decode(sign, "utf-8");
		// // valueMap.put("sign", sign);
		// }
		// if (null != extReserved) {
		// extReserved = URLDecoder.decode(extReserved, "utf-8");
		// valueMap.put("extReserved", extReserved);
		// }
		//
		// if (null != sysReserved) {
		// sysReserved = URLDecoder.decode(sysReserved, "utf-8");
		// valueMap.put("sysReserved", sysReserved);
		// }
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		return valueMap;

	}

	@Override
	public Object payCallback(Map<String, Object> requestParams) {

		LoggerUtils.info("huawei payCallback before", requestParams);

		// {result=0, notifyTime=1484889967792, accessMode=0, sdkId=119,
		// requestId=134214321, tradeTime=2017-01-20 05:26:07,
		// orderTime=2017-01-20 05:26:07, amount=10.00,
		// sign=Yw1uZpXjvrs3Q0Hl+EQsXpH23hxfHmXbeeZ5hLS6f/4lKCQbaoVCAbCheEWCzAYdQdYlvgpXtT8/3Dq78qIJtw==,
		// payType=0, userName=890086000102034263, productName=1元宝,
		// orderId=134214321, spending=0.01}

		String sign = FsGameLoginUtils.getMapString(requestParams, "sign", "");
		String requestId = FsGameLoginUtils.getMapString(requestParams, "requestId", "");

		Map<String, Object> signParams = new HashMap<String, Object>();
		for (Map.Entry<String, Object> entry : requestParams.entrySet()) {
			if (!"sign".equals(entry.getKey()) && !"request".equals(entry.getKey())
					&& !"response".equals(entry.getKey()) && (!"sdkId".equals(entry.getKey()))) {
				signParams.put(entry.getKey(), entry.getValue().toString());
			}
		}

		LoggerUtils.info("huawei payCallback signParams", signParams);

		Map<String, String> result = new HashMap<String, String>();

		result.put("result", "1");

		try {

			// 验签成功
			if (CommonUtil.rsaDoCheck(signParams, sign, PayPublicKey)) {
				processOrder(requestId, DataUtils.getMapString(signParams, "amount", ""), true);
				result.put("result", "0");
			} else { // 验签失败
				result.put("result", "1");
			}
		} catch (Exception e) {
			LoggerUtils.error(e);
			result.put("result", "1");
		}

		LoggerUtils.info("huawei payCallBack  result = ", result);

		// 构造返回值，如："{\"result\":0}"
		return GsonFactory.getDefault().toJson(result);
	}

	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {

		String appId = FsGameLoginUtils.getMapString(requestParams, "appId", "");
		String playerId = FsGameLoginUtils.getMapString(requestParams, "playerId", "");
		String gameAuthSign = FsGameLoginUtils.getMapString(requestParams, "gameAuthSign", "");
		String ts = FsGameLoginUtils.getMapString(requestParams, "ts", "");

		LoggerUtils.info("huawei loginAuth  appId = ", appId, " playerId = ", playerId, " gameAuthSign =",
				gameAuthSign, " ts", ts);

		boolean result = false;
		boolean checkResult = checkSign(appId + ts + playerId, gameAuthSign, LOGIN_RSA_PUBLIC);

		if (checkResult) {
			result = true;
		}

		LoggerUtils.info("huawei loginAuth  checkResult = ", checkResult);

		return new PlatformRetData(result, null);
	}

	/**
	 * 校验签名 check the
	 */
	protected boolean checkSign(String data, String gameAuthSign, String publicKey) {
		try {
			return RSA.verify(data.getBytes("UTF-8"), publicKey, gameAuthSign);
		} catch (Exception e) {
			return false;
		}
	}

	private String parseHuaweiTypeDouble(String value) {
		DecimalFormat df = new DecimalFormat();
		double data = Double.valueOf(value);
		String style = "0.00"; // 定义要显示的数字的格式
		df.applyPattern(style); // 将格式应用于格式化器
		value = df.format(data);

		return value;
	}

	@Override
	public PlatformRetData handleSdkData(Map<String, Object> requestParams) {

		LoggerUtils.info("huawei handleSdkData  requestParams = ", requestParams);

		String requestId = FsGameLoginUtils.getMapString(requestParams, "requestId", "");
		String amount = FsGameLoginUtils.getMapString(requestParams, "amount", "");
		String productName = FsGameLoginUtils.getMapString(requestParams, "productName", "");
		String productDesc = FsGameLoginUtils.getMapString(requestParams, "productDesc", "");

		amount = parseHuaweiTypeDouble(amount);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userID", PayID);
		params.put("applicationID", AppID);
		params.put("amount", amount);
		params.put("productName", productName);
		params.put("productDesc", productDesc);
		params.put("requestId", requestId);

		final String noSign = RSA.getSignData(params);
		LoggerUtils.info("huawei handleSdkData  noSign = ", noSign);
		String sign = RSA.sign(noSign, PayPrivateKey);
		LoggerUtils.info("huawei handleSdkData  sign = ", sign);
		return new PlatformRetData(true, sign);
	}
}
