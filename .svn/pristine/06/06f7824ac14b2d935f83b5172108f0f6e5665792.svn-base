package com.gamejelly.game.gong2.login.service.sdk.impl.kuaikan;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.gamejelly.game.gong2.login.utils.LoggerUtils;

/**
 * Created by weizhiyu on 16/12/19.
 */

public class KuaikanSignUtil {

	public static String getSign(String key, Map<String, Object> paramMap, boolean needUrlEncode) {
		try {
			if (StringUtils.isBlank(key) || CollectionUtils.isEmpty(paramMap)) {
				return null;
			}

			StringBuffer sb = new StringBuffer();
			List<String> mapkeys = new ArrayList<String>(paramMap.keySet());
			Collections.sort(mapkeys);
			for (int i = 0; i < mapkeys.size(); i++) {
				String mapKey = (String) mapkeys.get(i);
				String value = paramMap.get(mapKey) == null ? "" : paramMap.get(mapKey).toString();
				sb.append(mapKey + "=" + value + "&");
			}
			sb.append("key=" + key);
			LoggerUtils.info("kuaikan getSign ", "signSource=", sb);
			String md5Str = sb.toString();
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			Base64 base64en = new Base64();
			byte[] signTemp = base64en.encode(md5.digest(md5Str.getBytes("utf-8")));
			String sign = new String(signTemp, "utf-8");
			// sign = sign.replace("+", "%20");
			LoggerUtils.info("kuaikan sign1 =  ", sign);
			if (needUrlEncode) {
				sign = URLEncoder.encode(sign, "utf-8");
				LoggerUtils.info("kuaikan sign2 =  ", sign);
			}
			return sign;

		} catch (Exception e) {
			String errorMessage = "KuaikanSignUtil getServerSign error";

		}
		return null;

	}

	public static boolean checkSign(String key, String sign, Map<String, Object> paramMap) {
		if (CollectionUtils.isEmpty(paramMap)) {
			return false;
		}
		if (StringUtils.isBlank(sign)) {
			return false;
		}
		String checkSign = getSign(key, paramMap, false);
		if (StringUtils.isBlank(checkSign)) {
			return false;
		}
		if (!sign.equals(checkSign)) {
			return false;
		}
		return true;
	}

	public static boolean isEmpty(String str) {
		return ((str == null) || (str.length() == 0));
	}

	protected static byte[] getContentBytes(String content, String charset) throws UnsupportedEncodingException {
		if (isEmpty(charset)) {
			return content.getBytes();
		}

		return content.getBytes(charset);
	}

	public static String sign(String content) throws Exception {

		String tosign = (content == null ? "" : content);
		try {
			return DigestUtils.md5Hex(getContentBytes(tosign, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			throw new SignatureException("MD5签名发生异常!", e);
		}

	}

	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {

		String md5Str = "access_token=fd89ecd3cee98a17fd898ae11b6b3385&app_id=1043&open_id=27640361043&key=lYRg21s7a43ROkMQIjT1eG7wGRGFqoot";
		md5Str = "access_token=d5839a4e88c3ae8e933f74cbbfe3ca7f&app_id=1043&open_id=27640361043&key=lYRg21s7a43ROkMQIjT1eG7wGRGFqoot";
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// BASE64Encoder base64en = new BASE64Encoder();
		// //
		// String sign = "";
		// try {
		// sign = base64en.encode(md5.digest(md5Str.getBytes("utf-8")));
		// sign = URLEncoder.encode(sign, "utf-8");
		// } catch (UnsupportedEncodingException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// System.out.println(sign);

	}

}
