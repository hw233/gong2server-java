package com.gamejelly.game.gong2.login.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.hadoit.game.common.lang.DataUtils;

public class LoggerUtils {
	private static final Logger logger = Logger.getLogger("login");
	private static final Logger payLogger = Logger.getLogger("pay");
	private static final Logger clientLogger = Logger.getLogger("client");

	private static final Map<String, Long> timeMap = new ConcurrentHashMap<String, Long>();

	public static void trace(Object... params) {
		if (logger.isTraceEnabled()) {
			List<String> ss = new ArrayList<String>();
			for (Object param : params) {
				ss.add(DataUtils.toPrettyString(param));
			}
			logger.trace(StringUtils.join(ss, ' '));
		}
	}

	public static void trace(Throwable t, Object... params) {
		if (logger.isTraceEnabled()) {
			List<String> ss = new ArrayList<String>();
			for (Object param : params) {
				ss.add(DataUtils.toPrettyString(param));
			}
			logger.trace(StringUtils.join(ss, ' '), t);
		}
	}

	public static void debug(Object... params) {
		if (logger.isDebugEnabled()) {
			logger.debug(StringUtils.join(params, ' '));
		}
	}

	public static void debug(Throwable t, Object... params) {
		if (logger.isDebugEnabled()) {
			logger.debug(StringUtils.join(params, ' '), t);
		}
	}

	public static void time(String name) {
		if (logger.isDebugEnabled()) {
			timeMap.put(name, System.currentTimeMillis());
			debug("TIME [" + name + "] start");
		}
	}

	public static void timeEnd(String name) {
		if (logger.isDebugEnabled()) {
			Long start = timeMap.remove(name);
			if (start != null) {
				debug("TIME [" + name + "] takes: " + ((System.currentTimeMillis() - start) / 1000.0f) + "s");
			}
		}
	}

	public static void info(Object... params) {
		if (logger.isInfoEnabled()) {
			logger.info(StringUtils.join(params, ' '));
		}
	}

	public static void info(Throwable t, Object... params) {
		if (logger.isInfoEnabled()) {
			logger.info(StringUtils.join(params, ' '), t);
		}
	}

	public static void warn(Object... params) {
		logger.warn(StringUtils.join(params, ' '));
	}

	public static void warn(Throwable t, Object... params) {
		logger.warn(StringUtils.join(params, ' '), t);
	}

	public static void error(Object... params) {
		logger.error(StringUtils.join(params, ' '));
	}

	public static void error(Throwable t, Object... params) {
		logger.error(StringUtils.join(params, ' '), t);
	}

	public static void logPayCallback(Object... params) {
		payLogger.info(StringUtils.join(params, ' '));
	}

	public static void logClient(Object... params) {
		clientLogger.info(StringUtils.join(params, ' '));
	}

	public static void main(String[] args) {
		logPayCallback("ddd");
	}

}
