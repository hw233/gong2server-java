package com.gamejelly.game.gong2.login.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.meta.CouponRecord;

@Component("couponRecordDao")
public class CouponRecordDao extends BaseDomainDao<CouponRecord> {
	@Override
	protected Class<CouponRecord> getDomainClass() {
		return CouponRecord.class;
	}

	public CouponRecord getCouponRecordByCode(String couponCode) {
		return getObjectByCondition("couponCode = ?", couponCode);
	}

	public CouponRecord getCouponRecordByCodeForUpdate(String couponCode) {
		return getObjectForUpdateByCondition("couponCode = ?", couponCode);
	}

	public int deleteByBatchCode(String batchCode) {
		return deleteByCondition("batchCode = ?", batchCode);
	}

	public int updateEnableStateByBatchCode(int enableState, String batchCode) {
		return updateColumnValueByCondition("batchCode = ?", "enable", enableState, batchCode);
	}

	public List<CouponRecord> getByBatchCode(String batchCode) {
		return getListByFullCondition("batchCode = ?", batchCode);
	}

	public boolean existUserGiftType(String giftType, String claimUser, int serverId) {
		return existByCondition("giftType = ? and claimUser = ? and claimServerId = ?", giftType, claimUser, serverId);
	}
}