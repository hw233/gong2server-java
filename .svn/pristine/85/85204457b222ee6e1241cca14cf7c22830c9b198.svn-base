package com.gamejelly.gong2.utils;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.gamejelly.gong2.config.data.ItemData;
import com.gamejelly.gong2.config.data.base.LMap;
import com.gamejelly.gong2.gas.entity.AvatarEntity;
import com.gamejelly.gong2.meta.AvatarModel;
import com.gamejelly.gong2.meta.ItemModel;
import com.gamejelly.gong2.meta.log.AvatarBaseLog;
import com.gamejelly.gong2.meta.log.BaseLog;
import com.gamejelly.gong2.meta.log.BehaviorLog;
import com.gamejelly.gong2.meta.log.CriticalLog;
import com.gamejelly.gong2.meta.log.CurrencyChangeLog;
import com.gamejelly.gong2.meta.log.ExpChangeLog;
import com.gamejelly.gong2.meta.log.GoldChangeLog;
import com.gamejelly.gong2.meta.log.ItemChangeLog;
import com.gamejelly.gong2.meta.log.LoginLog;
import com.gamejelly.gong2.meta.log.LogoutLog;
import com.gamejelly.gong2.meta.log.MoneyChangeLog;
import com.gamejelly.gong2.meta.log.OnlineLog;
import com.hadoit.game.engine.guardian.gas.GasManager;
import com.hadoit.game.engine.guardian.gas.entity.RawCommandCallback;

public class GongLogger {

	private static void doLog(final AvatarEntity entity, final BaseLog baseLog) {
		GasManager gasManager = GongUtils.getGasManager();
		if (gasManager == null) {
			return;
		}

		if (!GongConstants.enableDbLog()) {
			return;
		}

		final String avatarId;
		if (entity != null) {
			avatarId = entity.getId();

			String opr;
			if (entity.getChannelContext() != null && entity.getChannelContext().containsAttribute("opr")) {
				opr = (String) entity.getChannelContext().getAttribute("opr");
			} else {
				opr = GongUtils.decompOpr(entity.getAvatarModel().getAccountName());
			}
			baseLog.setOpr(opr);

		} else {
			avatarId = "";
		}

		gasManager.executeRawCommand(GongDbConstants.CMD_LOG, new Object[] { baseLog }, new RawCommandCallback() {
			@Override
			public void onResult(Object result, int num, String error) {
				GasManager gasManager = GongUtils.getGasManager();
				if (gasManager != null) {
					AvatarEntity et = (AvatarEntity) gasManager.getEntityManager().getEntity(avatarId);
					if (et != null) {
						checkLogCritical(et, baseLog);
					}
				}
			}
		});
	}

	private static void logBase(BaseLog baseLog) {
		baseLog.setServerId(GongConstants.getServerId());
		baseLog.setCreateTime(System.currentTimeMillis());
		baseLog.setDayCreateTime(GongUtils.getTimeInDay00(new Date()));
	}

	private static void logAvatarBase(AvatarBaseLog baseLog, AvatarModel am) {
		logBase(baseLog);
		baseLog.setGbId(am.getGbId());
		baseLog.setAccountName(am.getAccountName());
		baseLog.setAvatarId(am.getId());
		baseLog.setRoleName(am.getRoleName());
		baseLog.setLv(am.getLv());
		baseLog.setVipLv(am.getVipLv());
		baseLog.setOpr(GongUtils.decompOpr(am.getAccountName()));
	}

	private static void doLogCritical(final AvatarEntity entity, final String tableName, final long delta,
			final String extra1) {
		GasManager gasManager = GongUtils.getGasManager();
		if (gasManager == null) {
			return;
		}

		if (!GongConstants.enableDbLog()) {
			return;
		}

		CriticalLog log = new CriticalLog();
		AvatarModel am = entity.getAvatarModel();
		logAvatarBase(log, am);
		log.setTableName(tableName);
		log.setDelta(delta);
		log.setExtra1(extra1);
		gasManager.executeRawCommand(GongDbConstants.CMD_LOG, new Object[] { log }, (RawCommandCallback) null);
	}

	public static void logLogin(AvatarEntity entity, boolean newAvatar, String source) {
		LoginLog log = new LoginLog();
		AvatarModel am = entity.getAvatarModel();
		logAvatarBase(log, am);
		log.setAvatarCreateTime(am.getCreateTime());
		log.setNewAvatar(newAvatar);
		String platform = (String) entity.getChannelContext().getAttribute("platform", "");
		String deviceName = (String) entity.getChannelContext().getAttribute("deviceName", "");
		String deviceVersion = (String) entity.getChannelContext().getAttribute("deviceVersion", "");
		String deviceId = (String) entity.getChannelContext().getAttribute("deviceId", "");
		String ip = (String) entity.getChannelContext().getAttribute("ip", "");
		log.setPlatform(platform);
		log.setDeviceName(deviceName);
		log.setDeviceVersion(deviceVersion);
		log.setDeviceId(deviceId);
		log.setIp(ip);
		log.setSource(source);
		doLog(entity, log);
	}

	public static void logLogout(AvatarEntity entity) {
		LogoutLog log = new LogoutLog();
		AvatarModel am = entity.getAvatarModel();
		logAvatarBase(log, am);
		doLog(entity, log);
	}

	public static void logOnline(int onlineCount) {
		OnlineLog log = new OnlineLog();
		logBase(log);
		log.setOnlineCount(onlineCount);
		doLog(null, log);
	}

	public static void logMoneyChange(AvatarEntity entity, long oldMoney, int sourceType, int sourceId1,
			int sourceId2) {
		MoneyChangeLog log = new MoneyChangeLog();
		AvatarModel am = entity.getAvatarModel();
		logAvatarBase(log, am);
		log.setDelta(am.getMoney() - oldMoney);
		log.setOldMoney(oldMoney);
		log.setSourceType(sourceType);
		log.setSourceId1(sourceId1);
		log.setSourceId2(sourceId2);
		doLog(entity, log);
	}

	public static void logExpChange(AvatarEntity entity, int added, int oldLv, int sourceType, int sourceId1,
			int sourceId2) {
		if (!GongConstants.enableLogMore()) {
			return;
		}

		ExpChangeLog log = new ExpChangeLog();
		AvatarModel am = entity.getAvatarModel();
		logAvatarBase(log, am);
		log.setExpDelta(added);
		log.setOldLv(oldLv);
		log.setLvDelta(am.getLv() - oldLv);
		log.setSourceType(sourceType);
		log.setSourceId1(sourceId1);
		log.setSourceId2(sourceId2);
		doLog(entity, log);
	}

	public static void logCurrencyChange(AvatarEntity entity, int key, long oldVal, long newVal, int sourceType,
			int sourceId1, int sourceId2) {
		CurrencyChangeLog log = new CurrencyChangeLog();
		AvatarModel am = entity.getAvatarModel();
		logAvatarBase(log, am);
		log.setCurrencyId(key);
		log.setDelta(newVal - oldVal);
		log.setOldVal(oldVal);
		log.setSourceType(sourceType);
		log.setSourceId1(sourceId1);
		log.setSourceId2(sourceId2);
		doLog(entity, log);
	}

	public static void logBehaviorChange(AvatarEntity entity, int sourceType, int sourceId1, int sourceId2,
			int sourceId3, String behaviorData) {
		if (!GongConstants.enableLogMore()) {
			return;
		}

		BehaviorLog log = new BehaviorLog();
		AvatarModel am = entity.getAvatarModel();
		logAvatarBase(log, am);
		log.setSourceType(sourceType);
		log.setSourceId1(sourceId1);
		log.setSourceId2(sourceId2);
		log.setSourceId3(sourceId3);
		log.setBehaviorData(behaviorData);
		doLog(entity, log);
	}

	public static void logGoldChange(AvatarEntity entity, long oldFreeGold, long oldGold, int sourceType, int sourceId1,
			int sourceId2, String desc) {
		if (StringUtils.isEmpty(desc)) {
			desc = "";
		}
		GoldChangeLog log = new GoldChangeLog();
		AvatarModel am = entity.getAvatarModel();
		logAvatarBase(log, am);
		log.setFreeGoldDelta(am.getFreeGold() - oldFreeGold);
		log.setOldFreeGold(oldFreeGold);
		log.setGoldDelta(am.getGold() - oldGold);
		log.setOldGold(oldGold);
		log.setHistoryGold(am.getHistoryGold());
		log.setSourceType(sourceType);
		log.setSourceId1(sourceId1);
		log.setSourceId2(sourceId2);
		log.setDescribe1(desc);
		log.setFirstCz(!entity.getAvatarModel().getPayInfoModel().isHistoryFirstPayed()); // 只给充值用
		doLog(entity, log);
	}

	public static void logItemChange(AvatarEntity entity, int delta, int itemTemplateId, String itemInstId,
			int sourceType, int sourceId1, int sourceId2) {
		ItemChangeLog log = new ItemChangeLog();
		AvatarModel am = entity.getAvatarModel();
		logAvatarBase(log, am);

		int newCount = 0;

		LMap template = ItemData.data.getMap(itemTemplateId);
		List<ItemModel> tmpItems = entity.getItemsFromTemplateId(itemTemplateId);
		boolean stack = template.getBool("stack");
		if (stack) {
			// 可堆叠的永远只有一个
			if (CollectionUtils.isNotEmpty(tmpItems)) {
				ItemModel aim = tmpItems.get(0);
				newCount = aim.getCount();
			}
		} else {
			if (CollectionUtils.isNotEmpty(tmpItems)) {
				newCount = tmpItems.size();
			}
		}

		log.setOldCount(newCount - delta);
		log.setDelta(delta);
		log.setItemTemplateId(itemTemplateId);
		log.setItemInstId(itemInstId);
		log.setSourceType(sourceType);
		log.setSourceId1(sourceId1);
		log.setSourceId2(sourceId2);
		doLog(entity, log);
	}

	private static void checkLogCritical(AvatarEntity entity, BaseLog log) {
		if (entity == null) {
			return;
		}
		if (log instanceof ItemChangeLog) {
			long delta = ((ItemChangeLog) log).getDelta();
			if (delta > 99) {
				doLogCritical(entity, log.getClass().getSimpleName(), delta, null);
			}
		} else if (log instanceof MoneyChangeLog) {
			long delta = ((MoneyChangeLog) log).getDelta();
			if (delta > 9999999) {
				doLogCritical(entity, log.getClass().getSimpleName(), delta, null);
			}
		} else if (log instanceof GoldChangeLog) {
			long freeDelta = ((GoldChangeLog) log).getFreeGoldDelta();
			if (freeDelta > 99999) {
				doLogCritical(entity, log.getClass().getSimpleName(), freeDelta, "free");
			}
			long delta = ((GoldChangeLog) log).getGoldDelta();
			if (delta > 99999) {
				doLogCritical(entity, log.getClass().getSimpleName(), delta, null);
			}
		} else if (log instanceof CurrencyChangeLog) {
			long delta = ((CurrencyChangeLog) log).getDelta();
			int cId = ((CurrencyChangeLog) log).getCurrencyId();

			long threshold = 9999999;
			if (delta > threshold) {
				doLogCritical(entity, log.getClass().getSimpleName(), delta, String.valueOf(cId));
			}
		}
	}
}
