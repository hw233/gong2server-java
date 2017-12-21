package com.gamejelly.game.gong2.login.service.sdk.impl.TTYuYin;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

public class SignUtils {
	@SuppressWarnings("finally")
	public static String sign(String data, String key) {
		if (StringUtils.isEmpty(data) || StringUtils.isEmpty(key)) {
			return null;
		}

		String sign = "";
		try {
			sign = encodeBASE64(digestMD5((data + key).getBytes("UTF-8")));
		} catch (Exception e) {
			return null;
		} finally {
			return sign;
		}
	}

	public static String encodeBASE64(byte[] key) {
		return Base64.encodeBase64String(key);
	}

	public static byte[] digestMD5(byte[] data) throws NoSuchAlgorithmException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(data);
		return md5.digest();
	}

}
