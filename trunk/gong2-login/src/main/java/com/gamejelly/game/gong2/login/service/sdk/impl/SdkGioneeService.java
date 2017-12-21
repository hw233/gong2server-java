package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.service.sdk.SdkGroup;
import com.gamejelly.game.gong2.login.service.sdk.SdkService;
import com.gamejelly.game.gong2.login.service.sdk.impl.gionee.Base64;
import com.gamejelly.game.gong2.login.service.sdk.impl.gionee.bean.Order;
import com.gamejelly.game.gong2.login.service.sdk.impl.gionee.util.PayUtil;
import com.gamejelly.game.gong2.login.service.sdk.impl.gionee.util.RSASignature;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.gamejelly.game.gong2.login.utils.SdkConst;
import com.hadoit.game.common.framework.http.SimpleHttpRequest;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

@SdkGroup({ SdkConst.CC_SDK_ANDROID_GIONEE })
@Component("sdkGioneeService")
public class SdkGioneeService extends SdkBaseService implements SdkService {

	// 登录
	static String port = "443";
	static String verify_url = "https://id.gionee.com:" + port + "/account/verify.do";
	static String apiKey = "3CE8586B45D342D896E0480929367AEC"; // 替换成商户申请获取的APIKey
	static String secretKey = "17B5C2B9044F404BBA81DAD8B24EB753"; // 替换成商户申请获取的SecretKey
	static String host = "id.gionee.com";
	static String url = "/account/verify.do";
	static String method = "POST";

	// 支付
	private static final String GIONEE_PAY_INIT = "https://pay.gionee.com/amigo/create/order";
	// 跑通demo后替换成商户自己的api_key
	private static final String API_KEY = "3CE8586B45D342D896E0480929367AEC";
	// 跑通demo后，替换成商户自己的private_key
	private static final String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANVqkK9NfHKJ1PeXw2widVPxSgLtBankthiVmc2lqqFN1jN9KUFZKm/7oypAaftCZshauZ9/OCGvgrakJ6QCXbbuuZKg/PbySwB2s2wNE4KEKk4tDlLWmlFAtyXPaY6Nd2mvOwsdAFnHJb4YecfTBxjwBRXjKO8OPrz34JbIh3VlAgMBAAECgYBPicTcoMtVwMrPcA9UOM9zdoT37DBlLikzpmXJAApEzcacL/R1N28ahzU7ZQBatVerKizuJbLc0xWrTii79mnIOpacxQL9zBVxNQ3WbBwSCEzHH+DEz24RNiKvVKb7xZu1t4YmoO2kDV39GvSGxMMVKGslXXfpKg3I/2yttLJzAQJBAO1y4zQyB2NhXnAHqJOslBsZanPTTRwYBQA+f9ZGtN2cvUau8qjp1/Um39yRPRUCKS7JS8uYckJQqcExt3h4KaUCQQDmFwN8rzx/u8ki3VLTBT8MP2ZqYfPLGg6UQgwuVbCFhs+h/s8l6CZKANrD73u6+InM3UYA0/1IyptJfVEEktDBAkEAvJKkC9y7b1pxlIkQgQ7qpEbf+tJRh8Evn40DG1oV43XJ/6uKvUjRIDlaSaHyn4+SwzxL/Im6HB5RYkOokHzlnQJAcFKBwEdneE+6w8tZmGwyOA9nNr20nsyMm3GWsrPgJX8HM2TJyQ6ISo4aPZZWR5L26kyG1Pan6fYGUpzbhSkKwQJAd4iv/EkIuVr9DUFJhgoL3TI2RkR9oy3l0I7nmkPPqdYwecBcO1rhHGDMCbzruUxLSPgNxxrvsfJ4D/1B9IgHRg==";
	// 网游类型接入时固定值
	private static final String DELIVER_TYPE = "1";
	// 成功响应状态码
	private static final String CREATE_SUCCESS_RESPONSE_CODE = "200010000";

	private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCMJtfP6dghiomGDUT16o2yCoYW2ZSfra/1Kpc/MsgWbdJFJEN1io//GZ3xVtwF0VrktQUYYTTXO/lF+JFhrQcT4zjCGbdDNYTCTbAJw2gDBTx86L0A9+RcNv/DTY358n7K7GiqMzljTlA5AL+Du7UuPGwCeE/3A8fBP8GudhcqeQIDAQAB";

	@SuppressWarnings("unchecked")
	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		LoggerUtils.info("ginoee payCallback", requestParams);

		String api_key = FsGameLoginUtils.getMapString(requestParams, "api_key", "");
		String close_time = FsGameLoginUtils.getMapString(requestParams, "close_time", "");
		String create_time = FsGameLoginUtils.getMapString(requestParams, "create_time", "");
		String deal_price = FsGameLoginUtils.getMapString(requestParams, "deal_price", "");
		String out_order_no = FsGameLoginUtils.getMapString(requestParams, "out_order_no", "");
		String pay_channel = FsGameLoginUtils.getMapString(requestParams, "pay_channel", "");
		String submit_time = FsGameLoginUtils.getMapString(requestParams, "submit_time", "");
		String user_id = FsGameLoginUtils.getMapString(requestParams, "user_id", "");
		String sign = FsGameLoginUtils.getMapString(requestParams, "sign", "");

		String strSign = "";
		strSign = strSign + "api_key=" + api_key + "&";
		strSign = strSign + "close_time=" + close_time + "&";
		strSign = strSign + "create_time=" + create_time + "&";
		strSign = strSign + "deal_price=" + deal_price + "&";
		strSign = strSign + "out_order_no=" + out_order_no + "&";
		strSign = strSign + "pay_channel=" + pay_channel + "&";
		strSign = strSign + "submit_time=" + submit_time + "&";
		strSign = strSign + "user_id=" + user_id;

		boolean signOk = false;
		try {
			signOk = RSASignature.doCheck(strSign, sign, PUBLIC_KEY, "utf-8");
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String result = "fail";
		try {
			if (signOk) {
				processOrder(out_order_no, deal_price, true);
				result = "success";
			}
		} catch (Exception e) {
			LoggerUtils.error(e);
			result = "fail";
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {
		String Uin = FsGameLoginUtils.getMapString(requestParams, "Uin", "");
		String SessionId = FsGameLoginUtils.getMapString(requestParams, "SessionId", "");

		boolean result = false;

		// {"tn":"+8613588478318","u":"259F7299094945018C3C65A00E8FCA6D","ul":30,"na":"","e":"","ply":[{"na":"Amigo_64293","a":"1EBFA53DB6504852A82F5992520F780C","pid":"78B01CA52B314AD48A43AF7DE2BCF5D1"}],"sty":0,"ptr":""}
		String verifyResult = verify(SessionId);
		if (verifyResult == null || verifyResult.equals("")) {
			result = false;
		} else {
			Map<String, Object> mapTransdata = GsonFactory.getDefault().fromJson(verifyResult, Map.class);
			if (mapTransdata != null) {
				if (!mapTransdata.containsKey("r")) {
					result = true;
				} else {
					String strR = (String) mapTransdata.get("r");
					if (strR != null && strR.equals("0")) {
						result = true;
					}
				}
			}
		}

		return new PlatformRetData(result, null);
	}

	// verify 方法封装了 验证方法，调用此方法即可完成帐号安全验证
	public static String verify(String amigoToken) {
		HttpsURLConnection httpURLConnection = null;
		OutputStream out;

		TrustManager[] tm = { new MyX509TrustManager() };
		try {
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL sendUrl = new URL(verify_url);
			httpURLConnection = (HttpsURLConnection) sendUrl.openConnection();
			httpURLConnection.setSSLSocketFactory(ssf);
			httpURLConnection.setDoInput(true); // true表示允许获得输入流,读取服务器响应的数据,该属性默认值为true
			httpURLConnection.setDoOutput(true); // true表示允许获得输出流,向远程服务器发送数据,该属性默认值为false
			httpURLConnection.setUseCaches(false); // 禁止缓存
			int timeout = 30000;
			httpURLConnection.setReadTimeout(timeout); // 30秒读取超时
			httpURLConnection.setConnectTimeout(timeout); // 30秒连接超时
			String method = "POST";
			httpURLConnection.setRequestMethod(method);
			httpURLConnection.setRequestProperty("Content-Type", "application/json");
			httpURLConnection.setRequestProperty("Authorization", builderAuthorization());
			out = httpURLConnection.getOutputStream();
			out.write(amigoToken.getBytes());
			out.flush();
			out.close();
			InputStream in = httpURLConnection.getInputStream();
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			byte[] buff = new byte[1024];
			int len = -1;
			while ((len = in.read(buff)) != -1) {
				buffer.write(buff, 0, len);
			}
			System.out.println(String.format("verify sucess response:%s", buffer.toString()));

			return buffer.toString();

		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "";
	}

	private static String builderAuthorization() {

		Long ts = System.currentTimeMillis() / 1000;
		String nonce = StringUtil.randomStr().substring(0, 8);
		String mac = CryptoUtility.macSig(host, port, secretKey, ts.toString(), nonce, method, url);
		mac = mac.replace("\n", "");
		StringBuilder authStr = new StringBuilder();
		authStr.append("MAC ");
		authStr.append(String.format("id=\"%s\"", apiKey));
		authStr.append(String.format(",ts=\"%s\"", ts));
		authStr.append(String.format(",nonce=\"%s\"", nonce));
		authStr.append(String.format(",mac=\"%s\"", mac));
		return authStr.toString();
	}

	@Override
	public PlatformRetData handleSdkData(Map<String, Object> requestParams) {

		LoggerUtils.info("Gionee handleSdkData", requestParams);
		super.handleSdkData(requestParams);

		// TODO Auto-generated method stub
		String account = FsGameLoginUtils.getMapString(requestParams, "account", "");
		String orderId = FsGameLoginUtils.getMapString(requestParams, "orderId", "");
		String name = FsGameLoginUtils.getMapString(requestParams, "name", "");
		int price = FsGameLoginUtils.getMapInteger(requestParams, "price", 0);
		String url = FsGameLoginUtils.getMapString(requestParams, "url", "");
		String userId = FsGameLoginUtils.getMapString(requestParams, "userId", "");

		SimpleDateFormat sfd = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String strDate = sfd.format(date);
		Timestamp tt = new Timestamp(date.getTime());

		BigDecimal bd = new BigDecimal(price);

		// Order order = new Order(orderId, account, name, apiKey, bd, bd, tt,
		// null, url);

		Order order = new Order(API_KEY, orderId, userId, name, bd, bd, DELIVER_TYPE, tt, null, url, null, null, null,
				account);

		LoggerUtils.info("Gionee orderToString=", order.toString());

		String strParam = "";
		try {
			strParam = PayUtil.wrapCreateOrder(order, PRIVATE_KEY, DELIVER_TYPE);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SimpleHttpRequest shr = SimpleHttpRequest.createPost(GIONEE_PAY_INIT);
		shr.createBody(strParam);
		String resultGet = shr.sendGetString();

		LoggerUtils.info("Gionee resultGet=", resultGet);

		if (StringUtils.isEmpty(resultGet)) {
			new PlatformRetData(false, null);
		}

		Map<String, String> rm = GsonFactory.getDefault().fromJson(resultGet, Map.class);

		if (rm.get("status").equals(CREATE_SUCCESS_RESPONSE_CODE)) {
			String orderNo = (String) rm.get("order_no");
			return new PlatformRetData(true, rm.get("submit_time"));
		}

		return new PlatformRetData(false, null);
	}

	static class MyX509TrustManager implements X509TrustManager {

		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			// TODO Auto-generated method stub

		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			// TODO Auto-generated method stub

		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			// TODO Auto-generated method stub
			return null;
		}

	}

	static class CryptoUtility {

		private static final String MAC_NAME = "HmacSHA1";

		public static String macSig(String host, String port, String macKey, String timestamp, String nonce,
				String method, String uri) {
			// 1. build mac string
			// 2. hmac-sha1
			// 3. base64-encoded

			StringBuffer buffer = new StringBuffer();
			buffer.append(timestamp).append("\n");
			buffer.append(nonce).append("\n");
			buffer.append(method.toUpperCase()).append("\n");
			buffer.append(uri).append("\n");
			buffer.append(host.toLowerCase()).append("\n");
			buffer.append(port).append("\n");
			buffer.append("\n");
			String text = buffer.toString();

			byte[] ciphertext = null;
			try {
				ciphertext = hmacSHA1Encrypt(macKey, text);
			} catch (Throwable e) {
				e.printStackTrace();
				return null;
			}

			String sigString = Base64.encodeToString(ciphertext, Base64.DEFAULT);
			return sigString;
		}

		public static byte[] hmacSHA1Encrypt(String encryptKey, String encryptText)
				throws InvalidKeyException, NoSuchAlgorithmException {
			Mac mac = Mac.getInstance(MAC_NAME);
			mac.init(new SecretKeySpec(StringUtil.getBytes(encryptKey), MAC_NAME));
			return mac.doFinal(StringUtil.getBytes(encryptText));
		}

	}

	static class StringUtil {
		public static final String UTF8 = "UTF-8";
		private static final byte[] BYTEARRAY = new byte[0];

		public static boolean isNullOrEmpty(String s) {
			if (s == null || s.isEmpty() || s.trim().isEmpty())
				return true;
			return false;
		}

		public static String randomStr() {
			return CamelUtility.uuidToString(UUID.randomUUID());
		}

		public static byte[] getBytes(String value) {
			return getBytes(value, UTF8);
		}

		public static byte[] getBytes(String value, String charset) {
			if (isNullOrEmpty(value))
				return BYTEARRAY;
			if (isNullOrEmpty(charset))
				charset = UTF8;
			try {
				return value.getBytes(charset);
			} catch (UnsupportedEncodingException e) {
				return BYTEARRAY;
			}
		}
	}

	static class CamelUtility {
		public static final int SizeOfUUID = 16;
		private static final int SizeOfLong = 8;
		private static final int BitsOfByte = 8;
		private static final int MBLShift = (SizeOfLong - 1) * BitsOfByte;

		private static final char[] HEX_CHAR_TABLE = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C',
				'D', 'E', 'F' };

		public static String uuidToString(UUID uuid) {
			long[] ll = { uuid.getMostSignificantBits(), uuid.getLeastSignificantBits() };
			StringBuilder str = new StringBuilder(SizeOfUUID * 2);
			for (int m = 0; m < ll.length; ++m) {
				for (int i = MBLShift; i > 0; i -= BitsOfByte)
					formatAsHex((byte) (ll[m] >>> i), str);
				formatAsHex((byte) (ll[m]), str);
			}
			return str.toString();
		}

		public static void formatAsHex(byte b, StringBuilder s) {
			s.append(HEX_CHAR_TABLE[(b >>> 4) & 0x0F]);
			s.append(HEX_CHAR_TABLE[b & 0x0F]);
		}

	}
}
