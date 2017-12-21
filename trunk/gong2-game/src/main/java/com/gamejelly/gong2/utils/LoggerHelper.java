package com.gamejelly.gong2.utils;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.gamejelly.gong2.meta.AvatarModel;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;

/**
 * 一些日志
 * 
 * @author bezy 2014-2-21
 * 
 */
public class LoggerHelper {
	private static final Logger cterrLogger = Logger.getLogger("cterr");
	private static final Logger slowLogger = Logger.getLogger("slow");

	/**
	 * 这个必须由具有avatarModel的对象调用
	 * 
	 * @param op
	 * @param model
	 * @param params
	 */
	public static void info(String op, AvatarModel model, Object... params) {
		if (model != null) {
			GuardianLogger.info(op, model.getAccountName(), model.getRoleName(), model.getId(), model.getDbId(),
					StringUtils.join(params, ' '));
		} else {
			GuardianLogger.info(op, StringUtils.join(params, ' '));
		}
	}

	public static void infoParams(Object... params) {
		GuardianLogger.info(params);
	}

	public static void slowTimeLocal(String name, long start) {
		long f = System.currentTimeMillis() - start;
		if (f > 25) {
			slowLogger.info("TIME [" + name + "] takes: " + (f / 1000.0f) + "s");
		}
	}

	public static void clientError(Object... params) {
		if (ArrayUtils.isEmpty(params)) {
			return;
		}
		cterrLogger.error(StringUtils.join(params, ' '));
	}
}
