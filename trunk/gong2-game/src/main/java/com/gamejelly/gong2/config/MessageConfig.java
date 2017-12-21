package com.gamejelly.gong2.config;

import com.gamejelly.gong2.config.data.ServerTishiData;
import com.gamejelly.gong2.config.data.base.LMap;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;

public class MessageConfig {

	public static String getCommonMsg(int msgId, Object... args) {
		LMap msgData = ServerTishiData.data.getMap(msgId);
		if (msgData == null) {
			return String.valueOf(msgId);
		}
		String ret = msgData.getString("content");
		try {
			ret = String.format(ret, args);
		} catch (Exception e) {
			GuardianLogger.error(e);
		}
		return ret;
	}
}
