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
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
import com.gamejelly.game.gong2.login.service.sdk.impl.anzhi.Base64;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.gamejelly.game.gong2.login.utils.SdkConst;
import com.google.gson.reflect.TypeToken;
import com.hadoit.game.common.framework.http.SimpleHttpRequest;
import com.hadoit.game.common.lang.codec.EncryptUtils;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

@SdkGroup({ SdkConst.CC_SDK_IOS_KY })
@Component("sdkKYService")
public class SdkKYService extends SdkBaseService implements SdkService {

	private String loginUrl = "http://f_signin.bppstore.com/loginCheck.php";
	private String appKey = "06369ee8f40bdd312d94a17be39bd1de";

	private String rsaPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDdqxTP3YB3p0NBXiWOZ5jWgynjiFAL54uVz2la/jtz/XKP+jYp3mOXkuSL6oGoSZCxO0q3lJ550EANxiMsY6yHPJGFBHliqNlncb6N4izf1vNXcICwtGZs7b2jMoLv1+LNbeXLfAesShm677/4io6cOD/3ONihkb8ZbFNLCB3OowIDAQAB";

	private static String getSignData(Map<String, String> params) {
		StringBuffer content = new StringBuffer();
		List<String> keys = new ArrayList<String>(params.keySet());
		// 按照自然升序处理
		Collections.sort(keys);

		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			// sign参数不参与签名数据
			if ("sign".equals(key)) {
				continue;
			}
			String value = (String) params.get(key);
			if (value != null) {
				content.append((i == 0 ? "" : "&") + key + "=" + value);
			} else {
				content.append((i == 0 ? "" : "&") + key + "=");
			}
		}
		return content.toString();
	}

	private static Map<String, String> parseParams(String qryStr) {
		Map<String, String> m = new HashMap<String, String>();
		if (StringUtils.isBlank(qryStr)) {
			return m;
		}
		String[] ua = StringUtils.splitByWholeSeparator(qryStr, "&");
		for (String ui : ua) {
			String[] pa = StringUtils.splitByWholeSeparator(ui, "=");
			if (pa.length == 2) {
				m.put(pa[0], pa[1]);
			} else {
				m.put(pa[0], "");
			}
		}
		return m;
	}

	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		HttpServletRequest request = (HttpServletRequest) requestParams.get("request");
		// 获取快用POST过来异步通知信息
		Map<String, String> transformedMap = new HashMap<String, String>();
		String notify_data = request.getParameter("notify_data");
		transformedMap.put("notify_data", notify_data);
		String orderid = request.getParameter("orderid");
		transformedMap.put("orderid", orderid);
		String sign = request.getParameter("sign");
		transformedMap.put("sign", sign);
		String dealseq = request.getParameter("dealseq");
		transformedMap.put("dealseq", dealseq);
		String uid = request.getParameter("uid");
		transformedMap.put("uid", uid);
		String subject = request.getParameter("subject");
		transformedMap.put("subject", subject);
		String v = request.getParameter("v");
		transformedMap.put("v", v);

		LoggerUtils.info("ky payCallback", requestParams);

		// 将sign除外的参数按自然升序排序后组装成验签数据
		String signData = getSignData(transformedMap);
		// RSA验证签名
		// 分配给开发商公钥

		if (!RSASignature.doCheck(signData, sign, rsaPublicKey, "utf-8")) {
			// RSA验签失败，数据不可信
			// 开发商业务逻辑处理
			// 响应给快用
			return "failed";
		} else {

			// "RSA验签成功，数据可信
			RSAEncrypt rsaEncrypt = new RSAEncrypt();

			// 加载公钥
			try {
				rsaEncrypt.loadPublicKey(rsaPublicKey);
				// 加载公钥成功
			} catch (Exception e) {
				// 加载公钥失败
				LoggerUtils.error(e);
				return "failed";
			}

			// 公钥解密通告加密数据
			byte[] dcDataStr = EncryptUtils.base64Decode(notify_data);
			try {
				byte[] plainData = rsaEncrypt.decrypt(rsaEncrypt.getPublicKey(), dcDataStr);
				// 获取到加密通告信息
				String notifyData = new String(plainData, "UTF-8");
				// 开发商业务逻辑处理
				Map<String, String> notifyMap = parseParams(notifyData);
				LoggerUtils.info("ky payCallback notifyMap", notifyMap);
				int payresult = Double.valueOf(notifyMap.get("payresult")).intValue();
				if (payresult == 0) {
					processOrder(notifyMap.get("dealseq"), notifyMap.get("fee"), true);
				}
				return "success";
			} catch (Exception e) {
				LoggerUtils.error(e);
				return "failed";
			}
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {
		// String Uin = FsGameLoginUtils.getMapString(requestParams, "Uin", "");
		String SessionId = FsGameLoginUtils.getMapString(requestParams, "SessionId", "");
		
		boolean result = false;
		if (SessionId.equals("")) {
			return new PlatformRetData(result, null);
		}

		String sign = appKey + SessionId;
		sign = FsGameLoginUtils.md5low(sign);

		// {"code":0,"msg":"\u9a8c\u8bc1\u6210\u529f","data":{"guid":"s5487ed041d909","username":"martian1"}}
		
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("tokenKey", SessionId);
		m.put("sign", sign);
		SimpleHttpRequest shr = SimpleHttpRequest.createPost(loginUrl.toString());
		shr.createFormBody(FsGameLoginUtils.mapToParamBody(m));
		String resultGet = shr.sendGetString();
		if (resultGet == null || "".equals(resultGet)) {
			result = false;
		} else {
			Map<String, Object> rm = GsonFactory.getDefault().fromJson(resultGet, Map.class);
			Map<String, Object> data = (Map<String, Object>) rm.get("data");
			int code = Double.valueOf(String.valueOf(rm.get("code"))).intValue();
			if (code == 0) {
				result = true;
				String guid = String.valueOf(data.get("guid"));
				requestParams.put("name", guid);
			} else {
				result = false;
			}
		}

		return new PlatformRetData(result, null);
	}

	/**
	 * RSA签名验签类
	 */
	static class RSASignature {

		public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

		/**
		 * RSA签名
		 * 
		 * @param content
		 *            待签名数据
		 * @param privateKey
		 *            商户私钥
		 * @param encode
		 *            字符集编码
		 * @return 签名值
		 */
		public static String sign(String content, String privateKey, String encode) {
			String charset = "utf-8";
			if (!StringUtils.isBlank(encode)) {
				charset = encode;
			}
			try {
				PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(EncryptUtils.base64Decode(privateKey));

				KeyFactory keyf = KeyFactory.getInstance("RSA");
				PrivateKey priKey = keyf.generatePrivate(priPKCS8);

				java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

				signature.initSign(priKey);
				signature.update(content.getBytes(charset));

				byte[] signed = signature.sign();

				return EncryptUtils.base64Encode(signed);
			} catch (Exception e) {
				LoggerUtils.error(e);
			}

			return null;
		}

		public static String sign(String content, String privateKey) {
			try {
				PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(EncryptUtils.base64Decode(privateKey));
				KeyFactory keyf = KeyFactory.getInstance("RSA");
				PrivateKey priKey = keyf.generatePrivate(priPKCS8);
				java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
				signature.initSign(priKey);
				signature.update(content.getBytes());
				byte[] signed = signature.sign();
				return EncryptUtils.base64Encode(signed);
			} catch (Exception e) {
				LoggerUtils.error(e);
			}
			return null;
		}

		/**
		 * RSA验签名检查
		 * 
		 * @param content
		 *            待签名数据
		 * @param sign
		 *            签名值
		 * @param publicKey
		 *            分配给开发商公钥
		 * @param encode
		 *            字符集编码
		 * @return 布尔值
		 */
		public static boolean doCheck(String content, String sign, String publicKey, String encode) {
			String charset = "utf-8";
			if (!StringUtils.isBlank(encode)) {
				charset = encode;
			}
			try {
				KeyFactory keyFactory = KeyFactory.getInstance("RSA");
				byte[] encodedKey = EncryptUtils.base64Decode(publicKey);
				PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

				java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

				signature.initVerify(pubKey);
				signature.update(content.getBytes(charset));

				boolean bverify = signature.verify(EncryptUtils.base64Decode(sign));
				return bverify;

			} catch (Exception e) {
				LoggerUtils.error(e);
			}

			return false;
		}

		public static boolean doCheck(String content, String sign, String publicKey) {
			try {
				KeyFactory keyFactory = KeyFactory.getInstance("RSA");
				byte[] encodedKey = EncryptUtils.base64Decode(publicKey);
				PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

				java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

				signature.initVerify(pubKey);
				signature.update(content.getBytes());

				boolean bverify = signature.verify(EncryptUtils.base64Decode(sign));
				return bverify;

			} catch (Exception e) {
				LoggerUtils.error(e);
			}

			return false;
		}
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
		 * 从文件中加载私钥
		 * 
		 * @param keyFileName
		 *            私钥文件名
		 * @return 是否成功
		 * @throws Exception
		 */
		public void loadPrivateKey(InputStream in) throws Exception {
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
				loadPrivateKey(sb.toString());
			} catch (IOException e) {
				throw new Exception("私钥数据读取错误");
			} catch (NullPointerException e) {
				throw new Exception("私钥输入流为空");
			}
		}

		public void loadPrivateKey(String privateKeyStr) throws Exception {
			// System.out.println("privateKeyStr:"+ privateKeyStr);
			try {
				byte[] buffer = EncryptUtils.base64Decode(privateKeyStr);
				PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
				KeyFactory keyFactory = KeyFactory.getInstance("RSA");
				this.privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
			} catch (NoSuchAlgorithmException e) {
				throw new Exception("无此算法");
			} catch (InvalidKeySpecException e) {
				throw new Exception("私钥非法");
			} catch (NullPointerException e) {
				throw new Exception("私钥数据为空");
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
		 * 私钥加密过程
		 * 
		 * @param privateKey
		 *            私钥
		 * @param plainTextData
		 *            明文数据
		 * @return
		 * @throws Exception
		 *             加密过程中的异常信息
		 */
		public byte[] encrypt(RSAPrivateKey privateKey, byte[] plainTextData) throws Exception {
			if (privateKey == null) {
				throw new Exception("加密私钥为空, 请设置");
			}
			Cipher cipher = null;
			try {
				// 使用默认RSA
				cipher = Cipher.getInstance("RSA");
				// cipher= Cipher.getInstance("RSA", new
				// BouncyCastleProvider());
				cipher.init(Cipher.ENCRYPT_MODE, privateKey);
				byte[] output = cipher.doFinal(plainTextData);
				return output;
			} catch (NoSuchAlgorithmException e) {
				throw new Exception("无此加密算法");
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
				return null;
			} catch (InvalidKeyException e) {
				throw new Exception("加密私钥非法,请检查");
			} catch (IllegalBlockSizeException e) {
				throw new Exception("明文长度非法");
			} catch (BadPaddingException e) {
				throw new Exception("明文数据已损坏");
			}
		}

		/**
		 * 私钥解密过程
		 * 
		 * @param privateKey
		 *            私钥
		 * @param cipherData
		 *            密文数据
		 * @return 明文
		 * @throws Exception
		 *             解密过程中的异常信息
		 */
		public byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData) throws Exception {
			if (privateKey == null) {
				throw new Exception("解密私钥为空, 请设置");
			}
			Cipher cipher = null;
			try {
				// 使用默认RSA
				cipher = Cipher.getInstance("RSA");
				// cipher= Cipher.getInstance("RSA", new
				// BouncyCastleProvider());
				cipher.init(Cipher.DECRYPT_MODE, privateKey);
				byte[] output = cipher.doFinal(cipherData);
				return output;
			} catch (NoSuchAlgorithmException e) {
				throw new Exception("无此解密算法");
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
				return null;
			} catch (InvalidKeyException e) {
				throw new Exception("解密私钥非法,请检查");
			} catch (IllegalBlockSizeException e) {
				throw new Exception("密文长度非法");
			} catch (BadPaddingException e) {
				throw new Exception("密文数据已损坏");
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

	public static void main(String[] args) {
		String d1 = GsonFactory.getDefault().toJson(Collections.singletonMap("data", 1));
		System.out.println(d1);
		System.out.println(GsonFactory.getDefault().fromJson(d1, new TypeToken<Map<String, String>>() {
		}.getType()));
	}

}
