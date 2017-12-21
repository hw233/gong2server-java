package com.gamejelly.game.gong2.login.service.sdk.impl.i4;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import sun.misc.BASE64Decoder;

/** */
/**
 * RSA安全编码组件
 * 
 * @version 1.0
 * @since 1.0
 */
public class RSADecrypt extends Coder {

	// 默认公钥(openssl)
	public static final String DEFAULT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCR3rEJu/tFKX7wpwdga9qGTTS954yD4f/5pbHT" +
			"RfzpaM32yenjgwTrpr7tgSfVv8FKNmYPc17wJx8uAWKx7vVYSQTzCBZWbm0XDcZ45scSfrfXrFjr" +
			"iglQjV/groiptrr7WZUhpiYDujS8iI1RVq1K+0GEdAGvBr3Gdt2Y0wLhuwIDAQAB";

	public static final String KEY_ALGORITHM = "RSA";
	/** */
	/**
	 * RSA最大解密密文大小
	 */
	private static final int MAX_DECRYPT_BLOCK = 128;

	/** */
	/**
	 * 解密<br>
	 * 用公钥解密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] data, String key) throws Exception {
		byte[] keyBytes = new BASE64Decoder().decodeBuffer(key);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicK = keyFactory.generatePublic(x509KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicK);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}

//	public static void main(String[] args) {
//		// 文档测试数据
//		String testDataStr = "NAJoEWusav9dYwdC4LbB7XPT5AeCuetAYTibxonaDSpdnyk2Xc5GyYAVk9dh9TRr7RIxh6WZBJb/x8GjWZW0aDT3db+9rSXM55KA49ePvAgU8MqHyW3lRUqW9Jk7E2jWEFR6ti7VvMxQTUSivf2/dJAB3zt5R3Cq+EtP4sc98NIpQsqz696MuzJtSHuiZFIohfxIefd+0njdi570aAx4k7vbkg3gbxZXW1Olpf2ttpC/Fy/89evaTMVJOjkjT7M5hRKcfa53b2/GCrzBHhoel38JgZUgFZtzKj9PvY4f5dYHBz8wXJdI0P5pU4eWhuR+emdMggU5Iws/M5bDEtfT+Q==";
//		try {
//			BASE64Decoder base64Decoder = new BASE64Decoder();
//			byte[] dcDataStr = base64Decoder.decodeBuffer(testDataStr);
//			byte[] plainData = RSADecrypt.decryptByPublicKey(dcDataStr, RSADecrypt.DEFAULT_PUBLIC_KEY);
//			System.out.println("文档测试数据明文长度:" + plainData.length);
//			System.out.println(new String(plainData));
//
//		} catch (Exception e) {
//			System.err.println(e.getMessage());
//		}
//	}

}