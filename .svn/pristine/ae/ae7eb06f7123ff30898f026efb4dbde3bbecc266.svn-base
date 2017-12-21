package com.gamejelly.game.gong2.login.service;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.util.URIUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.google.gson.reflect.TypeToken;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

@Component("userCreditService2")
public class UserCreditService2 {

	@Autowired
	private SystemDataService systemDataService;

	private static String fformatStr = "https://api4.udcredit.com/dsp-front/4.1/dsp-front/default/pubkey/%s/product_code/%s/out_order_id/%s/signature/%s";

	public static String apiCall(String url, String pubkey, String secretkey, String serviceCode, String outOrderId,
			Map<String, String> parameter) throws Exception {

		if (parameter == null || parameter.isEmpty())
			throw new Exception("error ! the parameter Map can't be null.");

		StringBuffer bodySb = new StringBuffer("{");
		for (Map.Entry<String, String> entry : parameter.entrySet()) {
			bodySb.append("'").append(entry.getKey()).append("':'").append(entry.getValue()).append("',");
		}
		String bodyStr = bodySb.substring(0, bodySb.length() - 1) + "}";
		String signature = md5(bodyStr + "|" + secretkey);
		url = String.format(fformatStr, pubkey, serviceCode, System.currentTimeMillis() + " ", signature);
		System.out.println("requestUrl=>" + url);
		System.out.println("request parameter body=>" + bodyStr);
		HttpResponse r = makePostRequest(url, bodyStr);
		return EntityUtils.toString(r.getEntity());
	}

	private static final CloseableHttpClient client = HttpClientBuilder.create().build();

	private static HttpResponse makePostRequest(String uri, String jsonData)
			throws ClientProtocolException, IOException {

		HttpPost httpPost = new HttpPost(URIUtil.encodeQuery(uri, "utf-8"));
		httpPost.setEntity(new StringEntity(jsonData, "UTF-8"));
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-type", "application/json; charset=utf-8");
		return client.execute(httpPost);

	}

	private static String md5(String data) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(data.toString().getBytes());
		return bytesToHex(md.digest());
	}

	private static String bytesToHex(byte[] ch) {
		StringBuffer ret = new StringBuffer("");
		for (int i = 0; i < ch.length; i++)
			ret.append(byteToHex(ch[i]));
		return ret.toString();
	}

	/**
	 * 字节转换�?16进制字符�?
	 */
	private static String byteToHex(byte ch) {
		String str[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };
		return str[ch >> 4 & 0xF] + str[ch & 0xF];
	}

	@SuppressWarnings("unchecked")
	public Object getShimingData2(String name, String idCard) {

		String status = "";
		if (systemDataService != null && !systemDataService.isOpenShiMing()) {
			// 不验证
			LoggerUtils.info("getShimingData2 false not to verify");
			return "1";
		}

		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id_no", idCard);
			map.put("id_name", name);

			String result = UserCreditService2.apiCall("https://api4.udcredit.com",
					"5e67c8b5-68dc-4575-af1a-7b78c2443ca4", "02d625ac-5b4f-420e-9d81-8a020bd2edf2", "O1001S0201",
					System.currentTimeMillis() + " ", map);
			System.err.println(result);

			// status 认证结果状态码，1-认证一致，2-认证不 一致，3-无结果(在公安数据库中查询不 到此条数据)

			// {"header":{"resp_time":"2017-05-10
			// 17:03:02","ret_msg":"操作成功","version":"4.1","ret_code":"000000","req_time":"2017-05-10
			// 17:03:00"},"body":{"status":"2","ud_order_no":"183950786947514368"}}
			Map m = GsonFactory.getDefault().fromJson(result, new TypeToken<Map<String, Object>>() {
			}.getType());
			LoggerUtils.info("result=" + m);

			String retCode = (String) ((Map<String, Object>) ((Map<String, Object>) m).get("header")).get("ret_code");
			if (retCode.equals("000000")) {
				status = (String) ((Map<String, Object>) ((Map<String, Object>) m).get("body")).get("status");
			}
			return status;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return status;
	}

	public static void main(String[] args) {

		// response.body.status
		UserCreditService2 s = new UserCreditService2();
		Object status = s.getShimingData2("王成诚", "411524199111133612");

		LoggerUtils.info("status=" + status);

	}
}
