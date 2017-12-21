package com.gamejelly.game.gong2.login.utils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang.time.DateUtils;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;
import org.springframework.util.Assert;
import org.springframework.util.NumberUtils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsRequest;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsResponse;
import com.google.gson.reflect.TypeToken;
import com.hadoit.game.common.framework.http.SimpleHttpClient;
import com.hadoit.game.common.framework.utils.SimpleWebUtils;
import com.hadoit.game.common.lang.DataUtils;
import com.hadoit.game.common.lang.Pair;
import com.hadoit.game.common.lang.ReflectionUtils;
import com.hadoit.game.common.lang.Triple;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateModelException;

public class FsGameLoginUtils {
	private static final MTRandom mtRandom = new MTRandom();
	public final static String aliAccessKey = "lWlvn1bYyniSX4W3";
	public final static String aliAccessSecret = "SQtOkdETuENXKhsgLRqvN9yrFaf1Kv";

	private static final char[] symbols;

	private static DbSearcher ipSearcher;

	static {
		SimpleHttpClient.DEFAULT.setSocketTimeout(10000);
		SimpleHttpClient.DEFAULT.setConnectTimeout(10000);

		try {
			ipSearcher = new DbSearcher(resolveSourceFileName("ip2region"));
			ipSearcher.memorySearch("0.0.0.0"); // 程序启动需要先search一下
			LoggerUtils.info("Ip DbSearcher inited!");
		} catch (Exception e) {
			LoggerUtils.error(e);
			System.exit(1); // 非正常退出
		}
	}

	static {
		StringBuilder tmp = new StringBuilder();
		for (char ch = '0'; ch <= '9'; ++ch)
			if (ch != '0') {
				tmp.append(ch);
			}
		for (char ch = 'A'; ch <= 'Z'; ++ch)
			if (ch != 'O') {
				tmp.append(ch);
			}
		symbols = tmp.toString().toCharArray();
	}

	public static String randomString(int length) {
		char[] buf = new char[length];
		for (int idx = 0; idx < buf.length; ++idx)
			buf[idx] = symbols[RandomUtils.nextInt(symbols.length)];
		return new String(buf);
	}

	public static Set<String> randomStringSet(int count, int length) {
		Set<String> ret = new HashSet<String>();
		for (int i = 0; i < count; i++) {
			ret.add(randomString(length));
		}
		return ret;
	}

	public static String decodeParameter(String v, String... encodings) {
		if (v == null) {
			return null;
		}
		String encoding = ArrayUtils.isEmpty(encodings) ? "UTF-8" : encodings[0];
		return DataUtils.encodeString(v, encoding);
	}

	public static int parseRequestInt(HttpServletRequest request, String paramName, int defaultValue) {
		String sParamValue = request.getParameter(paramName);
		int paramValue = defaultValue;
		if (StringUtils.isNotEmpty(sParamValue)) {
			paramValue = Integer.valueOf(sParamValue);
		}
		return paramValue;
	}

	public static String parseRequestString(HttpServletRequest request, String paramName, String defaultValue) {
		String sParamValue = request.getParameter(paramName);
		if (null != sParamValue) {
			return sParamValue;
		}
		return defaultValue;
	}

	/**
	 * @param request
	 * @param paramName
	 * @param format
	 * @return
	 */
	// {"yyyy-MM","yyyyMM","yyyy/MM", "yyyyMMdd","yyyy-MM-dd","yyyy/MM/dd",
	// "yyyyMMddHHmmss", , "yyyy/MM/dd HH:mm:ss"};
	public static Date parseRequestDate(HttpServletRequest request, String paramName, String format) {
		String sParamValue = request.getParameter(paramName);
		String[] formats = null;
		if (format == null) {
			formats = new String[] { "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH", "yyyy-MM-dd" };
		} else {
			formats = new String[] { format };
		}
		if (StringUtils.isNotEmpty(sParamValue)) {
			try {
				return DateUtils.parseDate(sParamValue, formats);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		Date date = new Date();
		date.setTime(0);
		return date;
	}

	public static Date parseDate(String valus, String format) {
		String[] formats = null;
		if (format == null) {
			formats = new String[] { "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH", "yyyy-MM-dd" };
		} else {
			formats = new String[] { format };
		}
		if (StringUtils.isNotEmpty(valus)) {
			try {
				return DateUtils.parseDate(valus, formats);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		Date date = new Date();
		date.setTime(0);
		return date;
	}

	public static boolean parseRequestBool(HttpServletRequest request, String paramName, boolean defaultValue) {
		String sParamValue = request.getParameter(paramName);
		boolean paramValue = defaultValue;
		if (StringUtils.isNotEmpty(sParamValue)) {
			paramValue = Boolean.valueOf(sParamValue);
		}
		return paramValue;
	}

	public static long parseRequestLong(HttpServletRequest request, String paramName, long defaultValue) {
		String sParamValue = request.getParameter(paramName);
		long paramValue = defaultValue;
		if (StringUtils.isNotEmpty(sParamValue)) {
			try {
				paramValue = Long.valueOf(sParamValue);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return paramValue;
	}

	public static void setStaticModel(Class<?> clz, Map<String, Object> map) {
		String name = clz.getSimpleName();
		map.put(name, getStaticModel(clz));
	}

	public static void setStaticModel(Class<?> clz, HttpServletRequest request) {
		String name = clz.getSimpleName();
		request.setAttribute(name, getStaticModel(clz));
	}

	public static Object getStaticModel(Class<?> clz) {
		BeansWrapper wrapper = BeansWrapper.getDefaultInstance();
		try {
			return wrapper.getStaticModels().get(clz.getName());
		} catch (TemplateModelException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String formatDate(long pastTime, String formatStr) {
		if (pastTime == 0)
			return "";
		return DataUtils.formatDate(pastTime, formatStr);
	}

	public static int getYear(Date date) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(date);
		return cl.get(Calendar.YEAR);
	}

	public static int getMonth(Date date) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(date);
		return cl.get(Calendar.MONTH) + 1;
	}

	public static int getDay(Date date) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(date);
		return cl.get(Calendar.DAY_OF_MONTH);
	}

	public static <V> V parseRequestObject(HttpServletRequest request, final Class<V> templateClass) {
		V obj = null;
		try {
			obj = templateClass.newInstance();
			List<Field> fields = ReflectionUtils.getAllDeclaredFields(obj.getClass(), true);
			String column;
			String value;
			String[] vs1;
			for (Field field : fields) {
				column = field.getName();
				value = parseRequestString(request, column, "");
				if (StringUtils.isBlank(value)) {
					value = parseRequestString(request, "data[" + column + "]", "");
				}
				value = value.trim();
				if (field.getType().isArray()) {
					vs1 = StringUtils.splitByWholeSeparatorPreserveAllTokens(value, "");
					trimStrings(vs1);
					BeanUtils.setProperty(obj, column, vs1);
				} else {
					BeanUtils.setProperty(obj, column, value);
				}

			}
		} catch (Exception e) {
		}
		return obj;
	}

	private static void trimStrings(String[] values) {
		for (int i = 0; i < values.length; ++i) {
			values[i] = values[i].trim();
		}
	}

	public static long addDay(long time, int dayCount) {
		Calendar cl = Calendar.getInstance();
		cl.setTimeInMillis(time);
		cl.add(Calendar.DAY_OF_MONTH, dayCount);
		return cl.getTimeInMillis();
	}

	public static long addMonth(long time, int monthCount) {
		Calendar cl = Calendar.getInstance();
		cl.setTimeInMillis(time);
		cl.add(Calendar.MONTH, monthCount);
		return cl.getTimeInMillis();
	}

	public static Pair<Long, Long> getMonthTime(long time) {
		Calendar cl = Calendar.getInstance();
		cl.setTimeInMillis(time);
		cl = DateUtils.truncate(cl, Calendar.MONTH);
		long startTime = cl.getTimeInMillis();
		cl.setTime(DateUtils.addMonths(new Date(startTime), 1));
		long endTime = cl.getTimeInMillis() - 1000;
		long cur = System.currentTimeMillis();
		endTime = endTime > cur ? cur : endTime;
		return Pair.newInstance(startTime, endTime);
	}

	public static int getCurMonth() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MONTH) + 1;
	}

	public static long getMonthFirstDay(int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis();
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] convertArray(Object array, Class<T> compType) {
		if (array == null) {
			return null;
		}
		int len = Array.getLength(array);
		Object objs = Array.newInstance(compType, len);
		Object obj;
		if (compType.isArray()) {
			for (int i = 0; i < len; ++i) {
				obj = Array.get(array, i);
				Array.set(objs, i, convertArray(obj, compType.getComponentType()));
			}
		} else if (compType.isPrimitive() || Number.class.isAssignableFrom(compType)) {
			for (int i = 0; i < len; ++i) {
				obj = Array.get(array, i);
				Array.set(objs, i, convertPrimitive(obj, compType));
			}
		} else {
			System.arraycopy(array, 0, objs, 0, len);
		}
		return (T[]) objs;
	}

	public static Object convertPrimitive(Object v, Class<?> clazz) {
		if (Boolean.TYPE.equals(clazz) || Boolean.class.equals(clazz)) {
			return v == null ? false : ((Boolean) v).booleanValue();
		} else if (Character.TYPE.equals(clazz) || Character.class.equals(clazz)) {
			return v == null ? 0 : ((Character) v).charValue();
		} else if (Byte.TYPE.equals(clazz) || Byte.class.equals(clazz)) {
			return v == null ? 0 : ((Number) v).byteValue();
		} else if (Short.TYPE.equals(clazz) || Short.class.equals(clazz)) {
			return v == null ? 0 : ((Number) v).shortValue();
		} else if (Integer.TYPE.equals(clazz) || Integer.class.equals(clazz)) {
			return v == null ? 0 : ((Number) v).intValue();
		} else if (Long.TYPE.equals(clazz) || Long.class.equals(clazz)) {
			return v == null ? 0 : ((Number) v).longValue();
		} else if (Float.TYPE.equals(clazz) || Float.class.equals(clazz)) {
			return v == null ? 0 : ((Number) v).floatValue();
		} else if (Double.TYPE.equals(clazz) || Double.class.equals(clazz)) {
			return v == null ? 0 : ((Number) v).doubleValue();
		}
		return v;
	}

	public static boolean checkAccount(String accountName) {
		if (StringUtils.isBlank(accountName)) {
			return false;
		}
		accountName = StringUtils.trim(accountName);
		int len = accountName.length();
		if (len > FsGameLoginConst.ACCOUNT_NAME_LEN_MAX || len < FsGameLoginConst.ACCOUNT_NAME_LEN_MIN) {
			return false;
		}
		return accountName.matches(FsGameLoginConst.ACCOUNT_NAME_REG);
	}

	public static boolean checkMD5Password(String pass) {
		if (StringUtils.isBlank(pass)) {
			return false;
		}
		return pass.matches(FsGameLoginConst.ACCOUNT_PASS_REG);
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getRequestParameterMap(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String[]> pms = request.getParameterMap();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("request", request);
		result.put("response", response);
		if (MapUtils.isEmpty(pms)) {
			return result;
		}
		for (String key : pms.keySet()) {
			String[] values = pms.get(key);
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < values.length; i++) {
				sb.append(",").append(values[i]);
			}
			result.put(key, sb.substring(1));
		}
		return result;
	}

	/**
	 * 获取不带-的UUID
	 * 
	 * @return
	 */
	public static String getUUID() {
		String s = UUID.randomUUID().toString();
		return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
	}

	/**
	 * Upper case md5 hex string
	 * 
	 * @param input
	 * @return
	 */
	public static String md5up(String input) {
		return new String(Hex.encodeHex(DigestUtils.md5(input), false));
	}

	/**
	 * Lower case md5 hex string
	 * 
	 * @param input
	 * @return
	 */
	public static String md5low(String input) {
		return new String(Hex.encodeHex(DigestUtils.md5(input), true));
	}

	public static <T extends Number> T toNumber(Object v, Class<T> clazz, T... dv) {
		T d = ArrayUtils.isEmpty(dv) ? null : dv[0];
		if (v != null) {
			Number nb;
			if (v instanceof Number) {
				nb = (Number) v;
			} else {
				nb = Double.valueOf(v.toString());
			}
			return NumberUtils.convertNumberToTargetClass(nb, clazz);
		}
		return d;
	}

	public static Short getMapShort(Map<?, ?> model, Object key, Short... dv) {
		Object v = model.get(key);
		return toNumber(v, Short.class, dv);
	}

	public static Integer getMapInteger(Map<?, ?> model, Object key, Integer... dv) {
		Object v = model.get(key);
		return toNumber(v, Integer.class, dv);
	}

	public static Long getMapLong(Map<?, ?> model, Object key, Long... dv) {
		Object v = model.get(key);
		return toNumber(v, Long.class, dv);
	}

	public static Float getMapFloat(Map<?, ?> model, Object key, Float... dv) {
		Object v = model.get(key);
		return toNumber(v, Float.class, dv);
	}

	public static Double getMapDouble(Map<?, ?> model, Object key, Double... dv) {
		Object v = model.get(key);
		return toNumber(v, Double.class, dv);
	}

	public static String getMapString(Map<?, ?> model, Object key, String... dv) {
		Object v = model.get(key);
		String d = ArrayUtils.isEmpty(dv) ? null : dv[0];
		if (v != null) {
			if (v instanceof String) {
				return (String) v;
			}
			return v + "";
		}
		return d;
	}

	public static Boolean getMapBoolean(Map<?, ?> model, Object key, Boolean... dv) {
		Object v = model.get(key);
		Boolean d = ArrayUtils.isEmpty(dv) ? null : dv[0];
		if (v != null) {
			if (v instanceof Boolean) {
				return (Boolean) v;
			}
			return new Boolean((v + "").trim());
		}
		return d;
	}

	public static Byte getMapByte(Map<?, ?> model, Object key, Byte... dv) {
		Object v = model.get(key);
		Byte d = ArrayUtils.isEmpty(dv) ? null : dv[0];
		if (v != null) {
			if (v instanceof Number) {
				return ((Number) v).byteValue();
			}
			return new Byte((v + "").trim());
		}
		return d;
	}

	public static <V> V getMapData(Map<?, V> model, Object key, V... dv) {
		V value = model.get(key);
		if (value != null) {
			return value;
		}
		return ArrayUtils.isEmpty(dv) ? null : dv[0];
	}

	public static double halfUpScaleToDouble(double val, int scale) {
		if (scale < 0) {
			return val;
		}
		BigDecimal bd = new BigDecimal(val).setScale(scale, BigDecimal.ROUND_HALF_UP);
		return bd.doubleValue();
	}

	public static long halfUpScaleToLong(double val) {
		return new Double(halfUpScaleToDouble(val, 0)).longValue();
	}

	public static int halfUpScaleToInt(double val) {
		return new Double(halfUpScaleToDouble(val, 0)).intValue();
	}

	public static float halfUpScaleToFloat(double val, int scale) {
		return new Double(halfUpScaleToDouble(val, scale)).floatValue();
	}

	/**
	 * 从键值序列的参数列表生成映射表
	 * 
	 * @param args
	 * @return
	 */
	public static Map<String, Object> createHashMap(Object... args) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < args.length; i += 2) {
			map.put((String) args[i], args[i + 1]);
		}
		return map;
	}

	public static Map<String, String> createStringHashMap(String... args) {
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < args.length; i += 2) {
			map.put((String) args[i], args[i + 1]);
		}
		return map;
	}

	/**
	 * 从参数列表生成列表
	 * 
	 * @param args
	 * @return
	 */
	public static <T> List<T> createList(T... args) {
		List<T> list = new ArrayList<T>();
		for (T arg : args) {
			list.add(arg);
		}
		return list;
	}

	public static String mapToQueryStr(Map<String, Object> m) {
		if (MapUtils.isEmpty(m)) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (String key : m.keySet()) {
			sb.append("&").append(key).append("=").append(getMapString(m, key, ""));
		}
		return sb.substring(1);
	}

	public static String getIpAddr(HttpServletRequest request) {
		return SimpleWebUtils.getRequestIp(request);
	}

	public static String compAccountName(String accountName, String opr) {
		return accountName + "@" + opr;
	}

	public static String decompAccountName(String name) {
		return StringUtils.substringBeforeLast(name, "@");
	}

	public static String decompAccountNameToOpr(String name) {
		return StringUtils.substringAfterLast(name, "@");
	}

	public static boolean isCompedName(String name, String opr) {
		return StringUtils.endsWith(name, "@" + opr);
	}

	public static List<Pair<String, String>> mapToParamBody(Map<String, Object> m) {
		if (MapUtils.isEmpty(m)) {
			return Collections.emptyList();
		}
		List<Pair<String, String>> params = new ArrayList<Pair<String, String>>();
		for (String key : m.keySet()) {
			params.add(Pair.newInstance(key, DataUtils.getMapString(m, key, "")));
		}
		return params;
	}

	/**
	 * 解决gson默认解析数字出double的问题
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static Map<String, String> fromJsonToStrMap(String jsonStr) {
		return GsonFactory.getDefault().fromJson(jsonStr, new TypeToken<Map<String, String>>() {
		}.getType());
	}

	private static String parseName(String s, StringBuffer sb) {
		sb.setLength(0);
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
			case '+':
				sb.append(' ');
				break;
			case '%':
				try {
					sb.append((char) Integer.parseInt(s.substring(i + 1, i + 3), 16));
					i += 2;
				} catch (NumberFormatException e) {
					// XXX
					// need to be more specific about illegal arg
					throw new IllegalArgumentException();
				} catch (StringIndexOutOfBoundsException e) {
					String rest = s.substring(i);
					sb.append(rest);
					if (rest.length() == 2)
						i++;
				}

				break;
			default:
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}

	public static Map<String, String> parseQueryString(String s) {
		// String valArray[] = null;

		if (s == null) {
			throw new IllegalArgumentException();
		}
		Map<String, String> ht = new HashMap<String, String>();
		StringBuffer sb = new StringBuffer();
		StringTokenizer st = new StringTokenizer(s, "&");
		while (st.hasMoreTokens()) {
			String pair = (String) st.nextToken();
			int pos = pair.indexOf('=');
			if (pos == -1) {
				// XXX
				// should give more detail about the illegal argument
				throw new IllegalArgumentException();
			}
			String key = parseName(pair.substring(0, pos), sb);
			String val = parseName(pair.substring(pos + 1, pair.length()), sb);
			// if (ht.containsKey(key)) {
			// String oldVals[] = (String[]) ht.get(key);
			// valArray = new String[oldVals.length + 1];
			// for (int i = 0; i < oldVals.length; i++)
			// valArray[i] = oldVals[i];
			// valArray[oldVals.length] = val;
			// } else {
			// valArray = new String[1];
			// valArray[0] = val;
			// }
			ht.put(key, val);
		}
		return ht;
	}

	/**
	 * 去掉字符串中的特殊的Unicode字符
	 */
	public static String trimUnicode(String text) {
		if (StringUtils.isEmpty(text)) {
			return StringUtils.EMPTY;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (!Character.isHighSurrogate(c) && !Character.isLowSurrogate(c)) {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static Object[] buildCommonSqlData(Map<String, Object> params, int limit, long offset, String orderStr) {
		StringBuilder qrySb = new StringBuilder("1 = 1");
		List<Object> qryParams = new ArrayList<Object>();
		if (params != null) {
			for (String key : params.keySet()) {
				qrySb.append(" and " + key + " = ?");
				qryParams.add(params.get(key));
			}
		}

		String countSql = qrySb.toString();
		Object[] countParams = qryParams.toArray();
		if (StringUtils.isNotEmpty(orderStr)) {
			qrySb.append(" " + orderStr);
		}

		if (limit > 0 && offset >= 0) {
			qrySb.append(" limit ? offset ?");
			qryParams.add(limit);
			qryParams.add(offset);
		}
		String dataSql = qrySb.toString();
		Object[] dataParams = qryParams.toArray();
		return new Object[] { countSql, countParams, dataSql, dataParams };
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		System.out.println(md5low("testxxxx@test"));
		Map m = new HashMap();
		m.put("a_1.0", "1.0");
		m.put("a_1.1", "1.1");
		m.put("b_2.0", 2.0);
		m.put("b_3", 3);
		for (Object key : m.keySet()) {
			System.out.println("key=" + key + ", " + getMapInteger(m, key, 0));
		}

		System.out.println("===========");

		for (Object key : m.keySet()) {
			System.out.println("key=" + key + ", " + getMapShort(m, key, (short) 0));
		}

		System.out.println("===========");

		for (Object key : m.keySet()) {
			System.out.println("key=" + key + ", " + getMapLong(m, key, 0l));
		}

		System.out.println("===========");

		for (Object key : m.keySet()) {
			System.out.println("key=" + key + ", " + getMapDouble(m, key, 0d));
		}

		System.out.println("===========");

		for (Object key : m.keySet()) {
			System.out.println("key=" + key + ", " + getMapFloat(m, key, 0f));
		}

		System.out.println(DigestUtils.md5Hex("__DEFAULTPASS__" + "j4AZDS1Y"));

		System.out.println(DigestUtils.md5Hex(DigestUtils.md5Hex("123456") + "WI1Fea8K"));
		System.out.println(DigestUtils.md5Hex("__DEFAULTPASS__" + "0FxpydVj"));

		System.out.println(decompAccountName("sdf@123456@hd"));
		System.out.println(isCompedName("sdf@123456@hd", "@hd"));

		System.out.println("360 private_key = " + FsGameLoginUtils
				.md5low("918f62db977aa0eec2f0cbcbee6bcc24" + "#" + "b3f1cb3176cb7218ef3e6ad6a9feb392"));

		System.out.println(parseQueryString("a=3243&b=xxio"));
		// DigestUtils.md5Hex(pass + salt)
		System.out.println(DigestUtils.md5Hex(FsGameLoginUtils.md5low("123456") + "5AKph2F."));
		System.out.println(decompAccountNameToOpr("test@ios"));
		System.out.println(decompAccountNameToOpr("test@"));
		System.out.println(decompAccountNameToOpr("test"));
		System.out.println(decompAccountNameToOpr("test@ii@ios"));

	}

	/**
	 * 获取得到排序好的查询字符串
	 * 
	 * @param params
	 *            请求参数
	 * @param isContainSignature
	 *            是否包含signature参数
	 * @return
	 */
	public static String getSortQueryString(Map<String, Object> params) throws Exception {

		Object[] keys = params.keySet().toArray();
		Arrays.sort(keys);
		StringBuffer sb = new StringBuffer();
		for (Object key : keys) {
			sb.append(String.valueOf(key)).append("=").append(params.get(String.valueOf(key))).append("&");
		}

		String text = sb.toString();
		if (text.endsWith("&")) {
			text = text.substring(0, text.length() - 1);
		}
		return text;
	}

	public static void transferParams(Map<String, Object> requestParams) {
		if (requestParams == null) {
			return;
		}
		try {
			for (Map.Entry<String, Object> e : requestParams.entrySet()) {
				if (e.getValue() instanceof String) {
					String v = e.getValue().toString();
					if (v.equals(new String(v.getBytes("iso8859-1"), "iso8859-1"))) {
						e.setValue(FsGameLoginUtils.decodeParameter(v, "utf-8"));
					}
				}
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
	}

	public static String resolveSourceFileName(String path) {
		path = FilenameUtils.normalize(path, true);
		if (path == null) {
			return null;
		}
		if (path.startsWith("/")) {
			File file = new File(path);
			if (file.exists()) {
				return path;
			}
			return null;
		} else {
			URL url = Thread.currentThread().getContextClassLoader().getResource(path);
			if (url == null) {
				return null;
			}
			path = url.getFile();
			if (path.startsWith("file:")) {
				if (path.contains("!/")) {
					return StringUtils.substringAfterLast(path, "!/");
				}
				return StringUtils.substringAfter(path, "file:");
			}
			return path;
		}
	}

	public static String getIpCountry(String ip) {
		if (Util.isIpAddress(ip) == false) {
			return "未知";
		}
		try {
			DataBlock dataBlock = ipSearcher.memorySearch(ip);
			if (dataBlock == null || StringUtils.isBlank(dataBlock.getRegion())) {
				return "未知";
			}
			String[] dbArr = StringUtils.splitByWholeSeparator(dataBlock.getRegion(), "|");
			if (ArrayUtils.getLength(dbArr) <= 0) {
				return "未知";
			}
			return dbArr[0];
		} catch (IOException e) {
			LoggerUtils.error(e);
			return "未知";
		}
	}

	public static boolean isChina(String ip) {
		String country = getIpCountry(ip);
		if (country == null) {
			return false;
		}
		return "中国".equalsIgnoreCase(country);
	}

	public static boolean isSameDayForOffset(long time1, long time2, long offsetTime) {
		return DateUtils.isSameDay(new Date(time1 - offsetTime), new Date(time2 - offsetTime));
	}

	public static Triple<Boolean, String, String> sendSingleSms(String phone, String signName, String templateCode,
			Map<String, String> params) {
		boolean success = false;
		String errCode = "";
		String errMsg = "";
		try {
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", aliAccessKey, aliAccessSecret);
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Sms", "sms.aliyuncs.com");
			IAcsClient client = new DefaultAcsClient(profile);
			SingleSendSmsRequest request = new SingleSendSmsRequest();
			request.setSignName(signName);
			request.setTemplateCode(templateCode);
			request.setParamString(GsonFactory.getDefault().toJson(params));
			request.setRecNum(phone);
			SingleSendSmsResponse httpResponse = client.getAcsResponse(request);
			LoggerUtils.info("Send sms to " + phone + " success! signName=" + signName + ", templateCode="
					+ templateCode + ", params=" + params, httpResponse.getModel(), httpResponse.getRequestId());
			success = true;
		} catch (ClientException e) {
			success = false;
			errCode = e.getErrCode();
			errMsg = e.getErrMsg();
			LoggerUtils.error(e, "Send sms to " + phone + " error! signName=" + signName + ", templateCode="
					+ templateCode + ", params=" + params);
		}
		return Triple.newInstance(success, errCode, errMsg);
	}

	public static int getRandomInt(int max) {
		if (max <= 0) {
			return 0;
		}
		return mtRandom.nextInt(max);
	}

	public static int randomIntBetweenInclusive(int v1, int v2, int step) {
		Assert.isTrue(step > 0);
		if (v2 > v1) {
			int stepCount = (v2 - v1) / step;
			return v1 + step * getRandomInt(stepCount + 1);
		} else {
			int stepCount = (v1 - v2) / step;
			return v2 + step * getRandomInt(stepCount + 1);
		}
	}

	/**
	 * 将Map数据组装成待签名字符串
	 * 
	 * @param params
	 *            待签名的参数列表
	 * @param notIn
	 *            不参与签名的参数名列表
	 * @return 待签名字符串。如果参数params为null，将返回null
	 */
	public static String createSignData(Map<String, Object> params, List<String> notInList) {
		if (null == params) {
			return null;
		}

		StringBuilder content = new StringBuilder(200);

		// 按照key排序
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);

			if (notInList != null && notInList.contains(key))
				continue;

			String value = params.get(key) == null ? "" : params.get(key).toString();
			content.append(key).append("=").append(value);
		}

		String result = content.toString();
		return result;
	}

	/**
	 * MD5 加密
	 */
	public static String getMD5Str(String str) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");

			messageDigest.reset();

			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5StrBuff.toString();
	}

	// sha1加密
	public static String getSha1(String str) {
		if (null == str || 0 == str.length()) {
			return null;
		}
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
			mdTemp.update(str.getBytes("UTF-8"));

			byte[] md = mdTemp.digest();
			int j = md.length;
			char[] buf = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
				buf[k++] = hexDigits[byte0 & 0xf];
			}
			str = new String(buf);
			return str;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

}
