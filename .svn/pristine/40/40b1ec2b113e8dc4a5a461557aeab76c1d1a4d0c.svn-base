package com.gamejelly.game.gong2.login.service.sdk.impl.weixin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.xml.parsers.ParserConfigurationException;

import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;

/**
 * 
 * @desc:微信支付随机字符串和签名工具
 */
public class PayCommonUtil {

	/**
	 * 自定义长度随机字符串
	 * 
	 * @param length
	 * @return
	 */
	public static String CreateNoncestr(int length) {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String res = "";
		for (int i = 0; i < length; i++) {
			Random rd = new Random();
			res += chars.indexOf(rd.nextInt(chars.length() - 1));
		}
		return res;
	}

	/**
	 * 默认16 位随机字符串
	 * 
	 * @return
	 */
	public static String CreateNoncestr() {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String res = "";
		for (int i = 0; i < 16; i++) {
			Random rd = new Random();
			res += chars.charAt(rd.nextInt(chars.length() - 1));
		}
		return res;
	}

	public static void main(String[] args) {
		String createNoncestr = PayCommonUtil.CreateNoncestr();
		System.out.println(createNoncestr);
	}

	/**
	 * 签名工具
	 * 
	 * @author wangkai
	 * @date 2014-12-5下午2:29:34
	 * @Description：sign签名
	 * @param characterEncoding
	 *            编码格式 UTF-8
	 * @param parameters
	 *            请求参数
	 * @return
	 */
	public static String createSign(String characterEncoding, Map<String, Object> parameters, String apiKey) {
		StringBuffer sb = new StringBuffer();
		Iterator<Entry<String, Object>> it = parameters.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
			String key = (String) entry.getKey();
			Object value = entry.getValue();// 去掉带sign的项
			if (null != value && !"".equals(value) && !"sign".equals(key) && !"key".equals(key)
					 && !"sdkId".equals(key) && !"request".equals(key) && !"response".equals(key)) {
				sb.append(key + "=" + value + "&");
			}
		}
		sb.append("key=" + apiKey);
		// 注意sign转为大写
		System.out.println(sb.toString());
		return FsGameLoginUtils.md5up(sb.toString());
	}

	/**
	 * 签名工具 不含商户密钥 －暂时不用
	 * 
	 * @param characterEncoding
	 *            编码格式 UTF-8
	 * @param parameters
	 * @return
	 */
	public static String createSignNoKey(String characterEncoding, Map<String, Object> parameters) {
		StringBuffer sb = new StringBuffer();
		Iterator<Entry<String, Object>> it = parameters.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
			String key = (String) entry.getKey();
			Object value = entry.getValue();// 去掉带sign的项
			if (null != value && !"".equals(value) && !"sign".equals(key) && !"key".equals(key)) {
				sb.append(key + "=" + value + "&");
			}
		}
		String signStr = sb.toString();
		String subStr = signStr.substring(0, signStr.length() - 1);
		// 注意sign转为大写
		return FsGameLoginUtils.md5up(sb.toString());
	}

	/**
	 * @author wangkai
	 * @date
	 * @Description：将请求参数转换为xml格式的string
	 * @param parameters
	 *            请求参数
	 * @return
	 */
	public static String getRequestXml(SortedMap<String, Object> parameters) {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Iterator<Entry<String, Object>> iterator = parameters.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			if ("attach".equalsIgnoreCase(key) || "body".equalsIgnoreCase(key) || "sign".equalsIgnoreCase(key)) {
				sb.append("<" + key + ">" + "<![CDATA[" + value + "]]></" + key + ">");
			} else {
				sb.append("<" + key + ">" + value + "</" + key + ">");
			}
		}
		sb.append("</xml>");
		return sb.toString();
	}

	/**
	 * @author wangkai
	 * @date
	 * @Description：返回给微信的参数
	 * @param return_code
	 *            返回编码
	 * @param return_msg
	 *            返回信息
	 * @return
	 */
	public static String setXML(String return_code, String return_msg) {
		return "<xml><return_code><![CDATA[" + return_code + "]]></return_code><return_msg><![CDATA[" + return_msg
				+ "]]></return_msg></xml>";
	}

	/**
	 * 从API返回的XML数据里面重新计算一次签名
	 *
	 * @param responseString
	 *            API返回的XML数据
	 * @return 新鲜出炉的签名
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 * @throws JDOMException
	 */
	public static String getSignFromResponseString(String responseString, String apiKey) throws IOException, SAXException,
			ParserConfigurationException, JDOMException {
		SortedMap<String, Object> map = XMLUtil.doXMLParse(responseString);
		// 清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
		map.put("sign", "");
		// map.remove("sign");
		// 将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
		return PayCommonUtil.createSign("UTF-8", map, apiKey);
	}

	/**
	 * 检验API返回的数据里面的签名是否合法，避免数据在传输的过程中被第三方篡改
	 *
	 * @param responseString
	 *            API返回的XML数据字符串
	 * @return API签名是否合法
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public static boolean checkIsSignValidFromResponseString(String responseString, String apiKey) {

		try {
			SortedMap<String, Object> map = XMLUtil.doXMLParse(responseString);
			// Map<String, Object> map =
			// XMLParser.getMapFromXML(responseString);
			LoggerUtils.info(map.toString());
			String signFromAPIResponse = map.get("sign").toString();
			if ("".equals(signFromAPIResponse) || signFromAPIResponse == null) {
				LoggerUtils.info("API返回的数据签名数据不存在，有可能被第三方篡改!!!");
				return false;
			}
			LoggerUtils.info("服务器回包里面的签名是:" + signFromAPIResponse);
			// 清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
			map.put("sign", "");
			// 将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
			String signForAPIResponse = PayCommonUtil.createSign("UTF-8", map, apiKey);
			if (!signForAPIResponse.equals(signFromAPIResponse)) {
				// 签名验不过，表示这个API返回的数据有可能已经被篡改了
				LoggerUtils.info("API返回的数据签名验证不通过，有可能被第三方篡改!!!");
				return false;
			}
			LoggerUtils.info("恭喜，API返回的数据签名验证通过!!!");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 对map根据key进行排序 ASCII 顺序
	 * 
	 * @param 无序的map
	 * @return
	 */
	public static SortedMap<String, Object> sortMap(Map<String, Object> map) {
		List<Map.Entry<String, Object>> infoIds = new ArrayList<Map.Entry<String, Object>>(map.entrySet());
		// 排序前
		/*
		 * for (int i = 0; i < infoIds.size(); i++) {
		 * System.out.println(infoIds.get(i).toString()); }
		 */

		// 排序
		Collections.sort(infoIds, new Comparator<Map.Entry<String, Object>>() {
			public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
				// return (o2.getValue() - o1.getValue());//value处理
				return (o1.getKey()).toString().compareTo(o2.getKey());
			}
		});
		// 排序后
		SortedMap<String, Object> sortmap = new TreeMap<String, Object>();
		for (int i = 0; i < infoIds.size(); i++) {
			String[] split = infoIds.get(i).toString().split("=");
			sortmap.put(split[0], split[1]);
		}
		return sortmap;
	}

}
