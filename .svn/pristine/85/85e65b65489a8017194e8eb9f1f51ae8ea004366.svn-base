package com.gamejelly.gong2.utils;

import com.gamejelly.gong2.gas.entity.AvatarEntity;
import com.gamejelly.gong2.vo.AvatarVO;
import com.hadoit.game.common.lang.DataUtils;
import org.apache.commons.lang.StringUtils;

public class IdProvider {
	private static volatile int curTimeSec = getCurSeconds();
	private static volatile long curIdIncr = 0;
	private static final int GLOBAL_ID_SHIFT = 20;
	private static final int GLOBAL_SERVER_BASE = 100;

	private static int getCurSeconds() {
		return (int) (System.currentTimeMillis() / 1000);
	}

	public static String genId(int type) {
		// serverId, type, time, incr
		int curSec = getCurSeconds();
		if (curSec > curTimeSec) {
			curTimeSec = curSec;
			curIdIncr = 0;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(GongConstants.getServerId()).append("-").append(type).append("-").append(curTimeSec).append("-")
				.append((curIdIncr = curIdIncr + 1));
		return sb.toString();
	}

	public static long genAvatarGbId(long dbId) {
		return ((GLOBAL_SERVER_BASE + GongConstants.getServerId()) << GLOBAL_ID_SHIFT) + dbId;
	}

	public static long parseAvatarDbId(long gbId) {
		return (gbId % (1 << GLOBAL_ID_SHIFT));
	}

	public static int parseAvatarServerId(long gbId) {
		return (int) ((gbId >> GLOBAL_ID_SHIFT) - GLOBAL_SERVER_BASE);
	}

	private static final String AVATAR_TYPE = "-" + GongConstants.ID_TYPE_AVATAR + "-";

	public static boolean isAvatar(String avatarId) {
		return avatarId.contains(AVATAR_TYPE);
	}

	public static String genInviteId(int oprGroup, int serverId, long dbId) {
		long uid = (10000 + dbId) * 10 + GongUtils.getRandomInt(10);
		String inviteId = String.format("%d%03d%d", oprGroup, serverId, uid);
		return inviteId;
	}



	// 签名修改
	public boolean changeQianming(AvatarEntity entity, String msg) {
		// if (StringUtils.isEmpty(msg)) {
		// return false;
		// }
		msg = GongUtils.trimUnicode(msg); // 去掉Unicode字符
		msg = StringUtils.trimToEmpty(DataUtils.toDBC(msg)); // toDBC
		if (GongUtils.checkTaboo(msg)) {
			return false;
		}
		entity.getAvatarModel().setQianming(msg);
		entity.notify(GongRpcConstants.RES_USER_AVATAR_CHANGE, new AvatarVO(entity));
		return true;
	}

	public static void main(String[] args) {
		System.out.println(genInviteId(1, 49, 526));
		System.out.println(genId(1));
		long uid1 = genAvatarGbId(10001);
		long uid2 = genAvatarGbId(10002);
		System.out.println(uid1);
		System.out.println(uid2);
		System.out.println(parseAvatarDbId(uid1));
		System.out.println(parseAvatarServerId(uid1));
		System.out.println(parseAvatarDbId(uid2));
		System.out.println(parseAvatarServerId(uid2));
	}
}
