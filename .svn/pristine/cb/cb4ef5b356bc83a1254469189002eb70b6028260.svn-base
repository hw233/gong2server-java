package com.gamejelly.game.gong2.login.dao;

import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.meta.GlobalCouponRecord;

@Component("globalCouponUseDao")
public class GlobalCouponRecordDao extends BaseDomainDao<GlobalCouponRecord> {
	@Override
	protected Class<GlobalCouponRecord> getDomainClass() {
		return GlobalCouponRecord.class;
	}

	public GlobalCouponRecord getCouponRecordByCode(String couponCode) {
		return getObjectByCondition("couponCode = ?", couponCode);
	}
	
	public GlobalCouponRecord getCouponRecordByCodeForUpdate(String couponCode) {
		return getObjectForUpdateByCondition("couponCode = ?", couponCode);
	}
}