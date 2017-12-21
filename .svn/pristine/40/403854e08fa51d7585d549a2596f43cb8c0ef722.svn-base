package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.service.sdk.SdkGroup;
import com.gamejelly.game.gong2.login.service.sdk.SdkService;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.gamejelly.game.gong2.login.utils.SdkConst;
import com.hadoit.game.common.framework.http.SimpleHttpRequest;
import com.hadoit.game.common.lang.DataUtils;
import com.hadoit.game.common.lang.codec.EncryptUtils;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

@SdkGroup({ SdkConst.CC_SDK_IOS_PP })
@Component("sdkPPService")
public class SdkPPService extends SdkBaseService implements SdkService {

	private String appId = "5023";
	private String loginUrl = "http://passport_i.25pp.com:8080/account?tunnel-command=2852126760";
	private String appKey = "529ac69c85f1756af6ae8651c109beab";

	private String rsaPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAo9QcuTVi+C7fLEJ03UuE" + "\r\n"
			+ "4PwtUZQ09ovEbSDT3pDhgY7ls6ukpxWEdyC9lebixjKWzjEMLywQUimQlu+pXyGK" + "\r\n"
			+ "KJ/wSNYYN7wdORQK9NNsLiUOVYiH4hBhvyoOxP8NDKxuHkyMn7uOnD/DWnhqEKVA" + "\r\n"
			+ "M/DFDNlMjFv6Hl2ugnzr2BrY64yC18Dm0vImAD7RX46SHH3uWj4Y7bsK1V+fQ8NR" + "\r\n"
			+ "XMZVIaS7mKPXAAzplA6OL347gMAPxVU9QJUy9MvrJD06ZV4G01bXUw4UD2b668op" + "\r\n"
			+ "UxIY9M1x9l2NWEOIhwXkPRSah+Eb4B6WL+WeouWu/woBFwXkZbTfNcD+TQaXPSbI" + "\r\n" + "8QIDAQAB" + "\r\n";

	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		LoggerUtils.info("pp payCallback", requestParams);
		HttpServletRequest request = (HttpServletRequest) requestParams.get("request");
		String sign = request.getParameter("sign");
		try {
			RSAEncrypt rsaEncrypt = new RSAEncrypt();
			rsaEncrypt.loadPublicKey(rsaPublicKey);
			byte[] dcDataStr = EncryptUtils.base64Decode(sign);
			byte[] plainData = rsaEncrypt.decrypt(rsaEncrypt.getPublicKey(), dcDataStr);
			Map<String, String> plainMap = FsGameLoginUtils.fromJsonToStrMap(new String(plainData, "utf-8"));

			if (StringUtils.equals(plainMap.get("billno"), request.getParameter("billno"))
					&& StringUtils.equals(plainMap.get("status"), request.getParameter("status"))
					&& StringUtils.equals(plainMap.get("amount"), request.getParameter("amount"))) {
				int status = Integer.valueOf(plainMap.get("status"));
				if (status == 0) {
					processOrder(plainMap.get("billno"), plainMap.get("amount"), true);
				}
				return "success";
			}
		} catch (Exception e) {
			LoggerUtils.error(e);
			return "fail";
		}
		return "fail";
	}

	@SuppressWarnings("unchecked")
	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {
		String SessionId = FsGameLoginUtils.getMapString(requestParams, "SessionId", "");
		long currSecTime = System.currentTimeMillis() / 1000;
		boolean result = false;
		if (SessionId.equals("")) {
			return new PlatformRetData(result, null);
		}

		String sign = SessionId + appKey;
		sign = FsGameLoginUtils.md5low(sign);
		Map<String, Object> bodyMap = new HashMap<String, Object>();
		bodyMap.put("id", String.valueOf(currSecTime));
		bodyMap.put("service", "account.verifySession");
		bodyMap.put("game", Collections.singletonMap("gameId", appId));
		bodyMap.put("data", Collections.singletonMap("sid", SessionId));
		bodyMap.put("encrypt", "MD5");
		bodyMap.put("sign", sign);

		SimpleHttpRequest shr = SimpleHttpRequest.createPost(loginUrl);
		shr.createBody(GsonFactory.getDefault().toJson(bodyMap));
		String resultGet = shr.sendGetString();
		int retCode = -1;
		if (resultGet == null || "".equals(resultGet)) {
			result = false;
		} else {
			Map<String, Object> rm = GsonFactory.getDefault().fromJson(resultGet, Map.class);
			Map<String, Object> state = (Map<String, Object>) rm.get("state");
			Map<String, Object> data = (Map<String, Object>) rm.get("data");
			retCode = DataUtils.getMapInteger(state, "code", 0).intValue();
			if (retCode == 1) {
				result = true;
				requestParams.put("name", DataUtils.getMapString(data, "accountId", ""));
			} else {
				result = false;
			}
		}
		return new PlatformRetData(result, retCode);
	}

	static class RSAEncrypt {
		/**
		 * 私钥
		 */
		private RSAPrivateKey privateKey;

		/**
		 * 公钥
		 */
		private RSAPublicKey publicKey;

		/**
		 * 字节数据转字符串专用集合
		 */
		private static final char[] HEX_CHAR = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
				'e', 'f' };

		/**
		 * 获取私钥
		 * 
		 * @return 当前的私钥对象
		 */
		public RSAPrivateKey getPrivateKey() {
			return privateKey;
		}

		/**
		 * 获取公钥
		 * 
		 * @return 当前的公钥对象
		 */
		public RSAPublicKey getPublicKey() {
			return publicKey;
		}

		/**
		 * 随机生成密钥对
		 */
		public void genKeyPair() {
			KeyPairGenerator keyPairGen = null;
			try {
				keyPairGen = KeyPairGenerator.getInstance("RSA");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			keyPairGen.initialize(1024, new SecureRandom());
			KeyPair keyPair = keyPairGen.generateKeyPair();
			this.privateKey = (RSAPrivateKey) keyPair.getPrivate();
			this.publicKey = (RSAPublicKey) keyPair.getPublic();
		}

		/**
		 * 从文件中输入流中加载公钥
		 * 
		 * @param in
		 *            公钥输入流
		 * @throws Exception
		 *             加载公钥时产生的异常
		 */
		public void loadPublicKey(InputStream in) throws Exception {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String readLine = null;
				StringBuilder sb = new StringBuilder();
				while ((readLine = br.readLine()) != null) {
					if (readLine.charAt(0) == '-') {
						continue;
					} else {
						sb.append(readLine);
						sb.append('\r');
					}
				}
				loadPublicKey(sb.toString());
			} catch (IOException e) {
				throw new Exception("公钥数据流读取错误");
			} catch (NullPointerException e) {
				throw new Exception("公钥输入流为空");
			}
		}

		/**
		 * 从字符串中加载公钥
		 * 
		 * @param publicKeyStr
		 *            公钥数据字符串
		 * @throws Exception
		 *             加载公钥时产生的异常
		 */
		public void loadPublicKey(String publicKeyStr) throws Exception {
			// System.out.println("publicKeyStr:"+ publicKeyStr);
			try {
				byte[] buffer = EncryptUtils.base64Decode(publicKeyStr);
				KeyFactory keyFactory = KeyFactory.getInstance("RSA");
				X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
				this.publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
			} catch (NoSuchAlgorithmException e) {
				throw new Exception("无此算法");
			} catch (InvalidKeySpecException e) {
				throw new Exception("公钥非法");
			} catch (NullPointerException e) {
				throw new Exception("公钥数据为空");
			}
		}

		/**
		 * 公钥加密过程
		 * 
		 * @param publicKey
		 *            公钥
		 * @param plainTextData
		 *            明文数据
		 * @return
		 * @throws Exception
		 *             加密过程中的异常信息
		 */
		public byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData) throws Exception {
			if (publicKey == null) {
				throw new Exception("加密公钥为空, 请设置");
			}
			Cipher cipher = null;
			try {
				// 使用默认RSA
				cipher = Cipher.getInstance("RSA");
				// cipher= Cipher.getInstance("RSA", new
				// BouncyCastleProvider());
				cipher.init(Cipher.ENCRYPT_MODE, publicKey);
				byte[] output = cipher.doFinal(plainTextData);
				return output;
			} catch (NoSuchAlgorithmException e) {
				throw new Exception("无此加密算法");
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
				return null;
			} catch (InvalidKeyException e) {
				throw new Exception("加密公钥非法,请检查");
			} catch (IllegalBlockSizeException e) {
				throw new Exception("明文长度非法");
			} catch (BadPaddingException e) {
				throw new Exception("明文数据已损坏");
			}
		}

		/**
		 * 公钥解密过程
		 * 
		 * @param publicKey
		 *            公钥
		 * @param cipherData
		 *            密文数据
		 * @return 明文
		 * @throws Exception
		 *             解密过程中的异常信息
		 */
		public byte[] decrypt(RSAPublicKey publicKey, byte[] cipherData) throws Exception {
			if (publicKey == null) {
				throw new Exception("解密公钥为空, 请设置");
			}
			Cipher cipher = null;
			try {
				// 使用默认RSA
				cipher = Cipher.getInstance("RSA");
				// cipher= Cipher.getInstance("RSA", new
				// BouncyCastleProvider());
				cipher.init(Cipher.DECRYPT_MODE, publicKey);
				byte[] output = cipher.doFinal(cipherData);
				return output;
			} catch (NoSuchAlgorithmException e) {
				throw new Exception("无此解密算法");
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
				return null;
			} catch (InvalidKeyException e) {
				throw new Exception("解密公钥非法,请检查");
			} catch (IllegalBlockSizeException e) {
				throw new Exception("密文长度非法");
			} catch (BadPaddingException e) {
				throw new Exception("密文数据已损坏");
			}
		}

		/**
		 * 字节数据转十六进制字符串
		 * 
		 * @param data
		 *            输入数据
		 * @return 十六进制内容
		 */
		public static String byteArrayToString(byte[] data) {
			StringBuilder stringBuilder = new StringBuilder();
			for (int i = 0; i < data.length; i++) {
				// 取出字节的高四位 作为索引得到相应的十六进制标识符 注意无符号右移
				stringBuilder.append(HEX_CHAR[(data[i] & 0xf0) >>> 4]);
				// 取出字节的低四位 作为索引得到相应的十六进制标识符
				stringBuilder.append(HEX_CHAR[(data[i] & 0x0f)]);
				if (i < data.length - 1) {
					stringBuilder.append(' ');
				}
			}
			return stringBuilder.toString();
		}

	}

}
