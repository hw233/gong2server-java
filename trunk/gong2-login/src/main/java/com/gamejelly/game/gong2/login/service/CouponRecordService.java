package com.gamejelly.game.gong2.login.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gamejelly.game.gong2.login.dao.CouponRecordDao;
import com.gamejelly.game.gong2.login.dao.GlobalCouponRecordDao;
import com.gamejelly.game.gong2.login.dao.GlobalCouponUseDao;
import com.gamejelly.game.gong2.login.meta.CouponRecord;
import com.gamejelly.game.gong2.login.meta.GlobalCouponRecord;
import com.gamejelly.game.gong2.login.meta.GlobalCouponUse;
import com.gamejelly.game.gong2.login.net.GasAdminClientManager;
import com.gamejelly.game.gong2.login.utils.FsGameLoginConst;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.google.gson.JsonElement;
import com.hadoit.game.common.lang.Pair;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

@Transactional
@Component("couponRecordService")
public class CouponRecordService {

	@Autowired
	private CouponRecordDao couponRecordDao;

	@Autowired
	private GlobalCouponRecordDao globalCouponRecordDao;

	@Autowired
	private GlobalCouponUseDao globalCouponUseDao;

	@Autowired
	private GasAdminClientManager gasAdminClientManager;

	@Autowired
	private GameAccountService gameAccountService;

	private static String MSG_ID1 = "20402"; // 兑换码不存在，请重新输入
	private static String MSG_ID2 = "20403"; // 该兑换码禁止使用
	private static String MSG_ID3 = "20404"; // 兑换码已兑换
	private static String MSG_ID4 = "20405"; // 玩家不存在
	private static String MSG_ID5 = "20406"; // 此类礼包已领取，不能再次领取
	private static String MSG_ID6 = "20407"; // 无法激活
	private static String MSG_ID7 = "20408"; // 非本渠道激活码, 无法激活

	public Pair<String, Integer> useCoupon(String avatarId, String accountName, String couponCode, int serverId,
			String opr) {
		couponCode = StringUtils.trim(couponCode);
		couponCode = StringUtils.upperCase(couponCode);
		CouponRecord cr = couponRecordDao.getCouponRecordByCode(couponCode);
		GlobalCouponRecord gcr = globalCouponRecordDao.getCouponRecordByCode(couponCode);
		if (cr != null) {
			// 判断是否存在子渠道标识，如果不存在判断是否存在主渠道
			if (StringUtils.isEmpty(cr.getOpr())) {
				// 判断主渠道标识
				return useNormalCoupon(cr, avatarId, accountName, couponCode, serverId, opr);
			} else {
				// 判断子渠道标识
				if (cr.getOpr().equals(opr)) {
					return useNormalCoupon(cr, avatarId, accountName, couponCode, serverId, opr);
				} else {
					return Pair.newInstance(MSG_ID7, 0);

				}
			}
		} else if (gcr != null) {
			return useGlobalCoupon(gcr, avatarId, accountName, couponCode, serverId, opr);
		} else {
			return Pair.newInstance(MSG_ID1, 0);
		}

	}

	@SuppressWarnings("unchecked")
	private Pair<String, Integer> useNormalCoupon(CouponRecord cr, String avatarId, String accountName,
			String couponCode, int serverId, String opr) {
		if (cr.getEnable() == 0) {
			return Pair.newInstance(MSG_ID2, 0);
		}
		if (StringUtils.isNotBlank(cr.getClaimUser())) {
			return Pair.newInstance(MSG_ID3, 0);
		}
		if (gameAccountService.getGameAccountByAccount(accountName) == null) {
			return Pair.newInstance(MSG_ID4, 0);
		}
		if (couponRecordDao.existUserGiftType(cr.getGiftType(), accountName, serverId)) {
			return Pair.newInstance(MSG_ID5, 0);
		}
		couponRecordDao.getCouponRecordByCodeForUpdate(couponCode);

		// 给物品, 有可能不在线不一定能给成功
		Object gasRes = null;
		try {
			gasRes = gasAdminClientManager.getSimpleRpcClientFromSubOpr(serverId, opr).getSimpleRpcClient().invokeRpc(
					FsGameLoginConst.ADMIN_LOGIN_REQ_COUPON_ADD_ITEM, new Object[] { avatarId, cr.getGiftId() }).get();
		} catch (Exception e) {
			LoggerUtils.error(e, "Give item failed! couponCode=", couponCode);
			return Pair.newInstance(MSG_ID6, 0);
		}
		Map<String, Object> mRes = GsonFactory.getDefault().fromJson((JsonElement) gasRes, Map.class);
		boolean bRes = FsGameLoginUtils.getMapBoolean(mRes, "result", false);
		if (!bRes) {
			return Pair.newInstance(MSG_ID6, 0);
		}

		cr.setClaimUser(accountName);
		cr.setClaimTime(System.currentTimeMillis());
		cr.setClaimServerId(serverId);
		couponRecordDao.updateObject(cr);
		return Pair.newInstance("SUCCESS", cr.getGiftId());
	}

	@SuppressWarnings("unchecked")
	private Pair<String, Integer> useGlobalCoupon(GlobalCouponRecord cr, String avatarId, String accountName,
			String couponCode, int serverId, String opr) {
		if (cr.getEnable() == 0) {
			return Pair.newInstance(MSG_ID2, 0);
		}
		if (StringUtils.isNotBlank(cr.getOpr())) {
			if (!cr.getOpr().equalsIgnoreCase(opr)) {
				return Pair.newInstance(MSG_ID7, 0);
			}
		}
		if (gameAccountService.getGameAccountByAccount(accountName) == null) {
			return Pair.newInstance(MSG_ID4, 0);
		}
		GlobalCouponUse gcUse = globalCouponUseDao.getCouponUseRecord(avatarId, couponCode, serverId);
		if (gcUse != null) {
			return Pair.newInstance(MSG_ID3, 0);
		}

		// 给物品, 有可能不在线不一定能给成功
		Object gasRes = null;
		try {
			gasRes = gasAdminClientManager.getSimpleRpcClientFromSubOpr(serverId, opr).getSimpleRpcClient().invokeRpc(
					FsGameLoginConst.ADMIN_LOGIN_REQ_COUPON_ADD_ITEM, new Object[] { avatarId, cr.getGiftId() }).get();
		} catch (Exception e) {
			LoggerUtils.error(e, "Give item failed! couponCode=", couponCode);
			return Pair.newInstance(MSG_ID6, 0);
		}
		Map<String, Object> mRes = GsonFactory.getDefault().fromJson((JsonElement) gasRes, Map.class);
		boolean bRes = FsGameLoginUtils.getMapBoolean(mRes, "result", false);
		if (!bRes) {
			return Pair.newInstance(MSG_ID6, 0);
		}

		GlobalCouponUse addedRecord = new GlobalCouponUse();
		addedRecord.setUserId(avatarId);
		addedRecord.setAccountName(accountName);
		addedRecord.setCouponCode(couponCode);
		addedRecord.setServerId(serverId);
		addedRecord.setCreateTime(System.currentTimeMillis());
		globalCouponUseDao.addObject(addedRecord);

		return Pair.newInstance("SUCCESS", cr.getGiftId());
	}

	public boolean addAndCheckCouponRecord(CouponRecord cr) {
		int r = couponRecordDao.getCommonJdbcTemplate().update(
				"insert ignore into CouponRecord (couponCode, giftType, giftId, batchCode, enable, createTime,oprGroup,opr) values (?,?,?,?,?,?,?,?)",
				new Object[] { cr.getCouponCode(), cr.getGiftType(), cr.getGiftId(), cr.getBatchCode(), cr.getEnable(),
						cr.getCreateTime(), cr.getOprGroup(), cr.getOpr() });
		return r > 0;
	}

	public int addCouponRecordsByBatch(Map<String, Object> cpTmpMap) {
		int count = FsGameLoginUtils.getMapInteger(cpTmpMap, "count", 0);
		if (count <= 0) {
			return 0;
		}
		int addedCount = 0;
		Set<String> cps = FsGameLoginUtils.randomStringSet(count, 8);
		for (String cp : cps) {
			CouponRecord cpr = new CouponRecord();
			cpr.setCouponCode(cp);
			cpr.setBatchCode(FsGameLoginUtils.getMapString(cpTmpMap, "batchCode"));
			cpr.setGiftType(FsGameLoginUtils.getMapString(cpTmpMap, "giftType"));
			cpr.setOprGroup(FsGameLoginUtils.getMapString(cpTmpMap, "oprGroup", ""));
			cpr.setOpr(FsGameLoginUtils.getMapString(cpTmpMap, "opr", ""));
			cpr.setEnable(FsGameLoginUtils.getMapInteger(cpTmpMap, "enable", 0));
			cpr.setGiftId(FsGameLoginUtils.getMapInteger(cpTmpMap, "giftId", 0));
			cpr.setCreateTime(FsGameLoginUtils.getMapLong(cpTmpMap, "createTime", 0l));
			LoggerUtils.info("add cpr", cpr.getOprGroup(), cpr.getGiftId(), cpr.getOpr());
			if (addAndCheckCouponRecord(cpr)) {
				++addedCount;
			}
		}
		return addedCount;
	}

	public void deleteByBatchCode(String batchCode) {
		couponRecordDao.deleteByBatchCode(batchCode);
	}

	public void enableByBatchCode(String batchCode) {
		couponRecordDao.updateEnableStateByBatchCode(1, batchCode);
	}

	public void diableByBatchCode(String batchCode) {
		couponRecordDao.updateEnableStateByBatchCode(0, batchCode);
	}

	public List<CouponRecord> getByBatchCode(String batchCode) {
		return couponRecordDao.getByBatchCode(batchCode);
	}

	public int getUsedCouponsCount(String batchCode) {
		return couponRecordDao.getCountByCondition("batchCode=? and claimTime > 0", batchCode);
	}

	public CouponRecord getCouponsByCode(String couponCode) {
		return couponRecordDao.getCouponRecordByCode(couponCode);
	}

	public boolean addAndCheckGlobalCouponRecord(GlobalCouponRecord cr) {
		if (globalCouponRecordDao.getCouponRecordByCode(cr.getCouponCode()) == null) {
			globalCouponRecordDao.addObject(cr);
			return true;
		}
		return false;
	}

	public GlobalCouponRecord getGlobalCouponByCode(String couponCode) {
		return globalCouponRecordDao.getCouponRecordByCode(couponCode);
	}

	public void updateGlobalCoupon(GlobalCouponRecord cr) {
		globalCouponRecordDao.updateObject(cr);
	}

	public boolean deleteGlobalCoupon(GlobalCouponRecord cr) {
		return globalCouponRecordDao.deleteById(cr.getId());
	}

	public GlobalCouponRecord getCouponRecordByCodeForUpdate(String couponCode) {
		return globalCouponRecordDao.getCouponRecordByCodeForUpdate(couponCode);
	}

}
