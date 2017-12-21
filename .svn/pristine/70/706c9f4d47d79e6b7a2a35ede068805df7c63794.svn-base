package com.gamejelly.game.gong2.login.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.google.gson.reflect.TypeToken;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;
import com.nearme.oauth.net.MySSLSocketFactory;

@Component("userCreditService")
public class UserCreditService {
	private String localurl = "https://api.udcredit.com/api/credit/v1/get_nauth";// 接口地址
																					// 详见api文档（列出的为实名认证url）
	private String product = "B10002"; // 业务类型 详见api文档（列出的为实名认证业务）
	private String merchant = "201603176290"; // 商户号 运营开户后发出
	private String MD5_key = "urVgA92Ee0TfN2QQQVJj"; // 商户原始key值 运营开户后发出

	// 商户订单号 <32位且不重复 没有格式要求 方便查询和对账
	// private String outOrderId = String.valueOf("UD" + product +
	// System.currentTimeMillis()); // 建议略作修改

	public String postPramaList(List<NameValuePair> params, String url) {
		HttpClient httpClient = CustomHttpClient.GetHttpClient();
		HttpPost post = new HttpPost(url);
		post.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		BufferedReader br = null;
		try {
			UrlEncodedFormEntity formEntiry = new UrlEncodedFormEntity(params, HTTP.UTF_8);
			// 设置请求参数
			post.setEntity(formEntiry);
			// 发起交易
			HttpResponse resp = httpClient.execute(post);
			int ret = resp.getStatusLine().getStatusCode();
			if (ret == HttpStatus.SC_OK) {
				// 响应分析
				HttpEntity entity = resp.getEntity();

				br = new BufferedReader(new InputStreamReader(entity.getContent(), "GBK"));
				StringBuffer responseString = new StringBuffer();
				String result = br.readLine();
				while (result != null) {
					responseString.append(result);
					result = br.readLine();
				}
				return responseString.toString();
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// do nothing
				}
			}
		}
	}

	public static String ecodeByMD5(String originstr) {

		String result = null;

		char hexDigits[] = {// 用来将字节转换成 16 进制表示的字符

		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

		if (originstr != null) {

			try {

				// 返回实现指定摘要算法的 MessageDigest 对象

				MessageDigest md = MessageDigest.getInstance("MD5");

				// 使用utf-8编码将originstr字符串编码并保存到source字节数组

				byte[] source = originstr.getBytes("utf-8");

				// 使用指定的 byte 数组更新摘要

				md.update(source);

				// 通过执行诸如填充之类的最终操作完成哈希计算，结果是一个128位的长整数

				byte[] tmp = md.digest();

				// 用16进制数表示需要32位

				char[] str = new char[32];

				for (int i = 0, j = 0; i < 16; i++) {

					// j表示转换结果中对应的字符位置

					// 从第一个字节开始，对 MD5 的每一个字节

					// 转换成 16 进制字符

					byte b = tmp[i];

					// 取字节中高 4 位的数字转换

					// 无符号右移运算符>>> ，它总是在左边补0

					// 0x代表它后面的是十六进制的数字. f转换成十进制就是15

					str[j++] = hexDigits[b >>> 4 & 0xf];

					// 取字节中低 4 位的数字转换

					str[j++] = hexDigits[b & 0xf];

				}

				result = new String(str);// 结果转换成字符串用于返回

			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public Object getShimingData(String name, String idCard, int retType, String callback) {
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("name_card", name); // api接口传参 详见api文档
		body.put("id_card", idCard); // api接口传参 详见api文档

		Map<String, Object> header = new HashMap<String, Object>();
		header.put("version", "1.0");// 固定1.0
		header.put("merchant", merchant);// 商户号 运营开户后发出
		header.put("product", product);// 业务类型
		header.put("outOrderId", "UD" + product + System.currentTimeMillis());// 订单号
																				// <32位且不重复
																				// 没有格式要求

		Map<String, Object> signJson = new HashMap<String, Object>();
		signJson.put("body", body);
		signJson.put("header", header);

		Map<String, Object> common = new HashMap<String, Object>();
		common.put("request", signJson);

		String orginSign = GsonFactory.getDefault().toJson(signJson);
		String sign = ecodeByMD5(orginSign + MD5_key); // 替换MD5_key 加签
		common.put("sign", sign);

		HttpRequestSimple http = new HttpRequestSimple();
		String outbuffer = http.postSendHttp(localurl, GsonFactory.getDefault().toJson(common));
		LoggerUtils.info("请求地址:>>>>>>>>" + localurl);
		LoggerUtils.info("请求报文:>>>>>>>>" + common.toString());
		LoggerUtils.info("返回报文:>>>>>>>>" + outbuffer.toString());
		
		//
		// {"response":
		// {"header":{
		// "version":"1.0",
		// "product":"B10002",
		// "reqTime":"2015-07-10 16:58:00",
		// "resTime":"2015-07-10 16:58:01",
		// "retCode":"0000",
		// "retMsg":"交易成功"
		// },
		// "body":{
		// "status":"1",
		// "order_no"；"2100000000000000000"
		// }
		// },
		// "sign":"3c4937f894b3a99a347b1635571717a0"
		// }
		Map m = GsonFactory.getDefault().fromJson(outbuffer, new TypeToken<Map<String, Object>>() {
		}.getType());
		if(retType==1){
			//response.body.status
			String status = (String)((Map<String, Object>)((Map<String, Object>)m.get("response")).get("body")).get("status");
			return callback+"("+GsonFactory.getDefault().toJson(Collections.singletonMap("status", status))+")";
		}else{
			return m;
		}

		
		
	}

	static class CustomHttpClient {
		private static HttpClient customHttpClient;
		private static final int TIME_OUT = 1000 * 30;

		private CustomHttpClient() {
		}

		public static synchronized HttpClient GetHttpClient() {

			if (customHttpClient == null) {
				return httpClientInstance();
			}
			return customHttpClient;
		}

		private static HttpClient httpClientInstance() {
			SSLSocketFactory ssf = null;
			try {
				KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
				trustStore.load(null, null);
				ssf = new MySSLSocketFactory(trustStore);
				ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			} catch (KeyManagementException e) {
				LoggerUtils.error(e.getMessage(), e);
			} catch (NoSuchAlgorithmException e) {
				LoggerUtils.error(e.getMessage(), e);
			} catch (KeyStoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CertificateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnrecoverableKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
			ConnManagerParams.setTimeout(params, TIME_OUT);
			HttpConnectionParams.setConnectionTimeout(params, TIME_OUT);
			HttpConnectionParams.setSoTimeout(params, TIME_OUT);
			SchemeRegistry schReg = new SchemeRegistry();
			schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			schReg.register(new Scheme("https", ssf, 443));

			ClientConnectionManager conMgr = new ThreadSafeClientConnManager(params, schReg);
			customHttpClient = new DefaultHttpClient(conMgr, params);
			return customHttpClient;
		}

		@Override
		public Object clone() throws CloneNotSupportedException {
			throw new CloneNotSupportedException();
		}

	}

	static class HttpRequestSimple {

		private final static String CONN_TIMEOUT = "ConnectTimeout";

		private final static String CONN_FAIL = "CONN_FAIL";

		public String postSendHttp(String url, String body) {
			if (url == null || "".equals(url)) {
				LoggerUtils.error("request url is empty.");
				return null;
			}
			HttpClient httpClient = CustomHttpClient.GetHttpClient();

			httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.IGNORE_COOKIES);
			HttpPost post = new HttpPost(url);
			HttpResponse resp = null;

			post.setHeader("Content-Type", "application/json;charset=UTF-8");
			try {
				StringEntity stringEntity = new StringEntity(body, "UTF-8");
				// stringEntity.setContentEncoding(new BasicHeader(
				// HTTP.CONTENT_ENCODING, "UTF-8"));
				// 设置请求主体
				post.setEntity(stringEntity);
				// 发起交易
				resp = httpClient.execute(post);
				// 响应分析
				HttpEntity entity = resp.getEntity();

				BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
				StringBuffer responseString = new StringBuffer();
				String result = br.readLine();
				while (result != null) {
					responseString.append(result);
					result = br.readLine();
				}

				return responseString.toString();

			} catch (ConnectTimeoutException cte) {
				LoggerUtils.error(cte.getMessage(), cte);
				return null;
			} catch (SocketTimeoutException cte) {
				LoggerUtils.error(cte.getMessage(), cte);
				return null;
			} catch (Exception e) {
				LoggerUtils.error(e.getMessage(), e);
				return null;
			} finally {
				try {
					resp.getEntity().getContent().close();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		public String getSendHttp(String url) {
			if (url == null || "".equals(url)) {
				LoggerUtils.error("request url is empty.");
				return null;
			}
			HttpClient httpClient = CustomHttpClient.GetHttpClient();
			HttpGet get = new HttpGet(url);
			get.setHeader("Content-Type", "text/html;charset=UTF-8");
			HttpResponse resp = null;
			try {
				// 发起交易
				resp = httpClient.execute(get);
				// 响应分析
				HttpEntity entity = resp.getEntity();

				BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
				StringBuffer responseString = new StringBuffer();
				String result = br.readLine();
				while (result != null) {
					responseString.append(result);
					result = br.readLine();
				}

				return responseString.toString();
			} catch (ConnectTimeoutException cte) {
				LoggerUtils.error(cte.getMessage(), cte);
				return null;
			} catch (SocketTimeoutException cte) {
				LoggerUtils.error(cte.getMessage(), cte);
				return null;
			} catch (Exception e) {
				LoggerUtils.error(e.getMessage(), e);
				return null;
			} finally {
				// httpClient.getConnectionManager().shutdown();
				try {
					resp.getEntity().getContent().close();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		public String postPramaList(List<NameValuePair> params, String url) {
			HttpClient httpClient = CustomHttpClient.GetHttpClient();
			HttpPost post = new HttpPost(url);
			post.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			BufferedReader br = null;
			try {
				UrlEncodedFormEntity formEntiry = new UrlEncodedFormEntity(params, HTTP.UTF_8);
				// 设置请求参数
				post.setEntity(formEntiry);
				// 发起交易
				HttpResponse resp = httpClient.execute(post);
				int ret = resp.getStatusLine().getStatusCode();
				if (ret == HttpStatus.SC_OK) {
					// 响应分析
					HttpEntity entity = resp.getEntity();

					br = new BufferedReader(new InputStreamReader(entity.getContent(), "GBK"));
					StringBuffer responseString = new StringBuffer();
					String result = br.readLine();
					while (result != null) {
						responseString.append(result);
						result = br.readLine();
					}
					return responseString.toString();
				} else {
					return null;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						// do nothing
					}
				}
			}
		}

	}
	
	public static void main(String[] args) {
		UserCreditService s = new UserCreditService();
		Object r =s.getShimingData("廉嘉志", "411722197303195710", 2, "");
		
		System.out.println(r);
//		String ss = "{\"sign\":\"b8131605de1c9af472dda5fd1ee6ea86\",\"response\":{\"body\":{\"status\":\"2\",\"order_no\":\"2776327001999360\"},\"header\":{\"product\":\"B10002\",\"retCode\":\"0000\",\"resTime\":\"2016-03-18 16:09:15\",\"retMsg\":\"请求成功\",\"reqTime\":\"2016-03-18 16:09:15\",\"version\":\"1.0\"}}}";
//		
//		Map<String, Object> m = GsonFactory.getDefault().fromJson(ss, new TypeToken<Map<String, Object>>() {
//							}.getType());
////		((((Map<String, Object>)((Map<String, Object>)m.get("response")).get("body"))).get("status")
//		//response.body.status
//		2017-05-02 13:23:13,715 [main] INFO  login  - 请求地址:>>>>>>>>https://api.udcredit.com/api/credit/v1/get_nauth
//			2017-05-02 13:23:13,718 [main] INFO  login  - 请求报文:>>>>>>>>{sign=75c248cc73c27946a613c541f6653974, request={body={name_card=王翔, id_card=330681198211176254}, header={product=B10002, merchant=201603176290, outOrderId=UDB100021493702592489, version=1.0}}}
//			2017-05-02 13:23:13,718 [main] INFO  login  - 返回报文:>>>>>>>>{"response":{"header":{"resTime":"2017-05-02 13:23:14","product":"B10002","reqTime":"2017-05-02 13:23:14","retCode":"0000","version":"1.0","retMsg":"请求成功"},"body":{"order_no":"180925472113819648","status":"1"}},"sign":"32d6e10ca419487ad057e7185956e7e1"}
//			{response={header={resTime=2017-05-02 13:23:14, product=B10002, reqTime=2017-05-02 13:23:14, retCode=0000, version=1.0, retMsg=请求成功}, body={order_no=180925472113819648, status=1}}, sign=32d6e10ca419487ad057e7185956e7e1}

//		System.out.println(((Map<String, Object>)m.get("response")).get("body"));
	}
}
