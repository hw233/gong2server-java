package com.gamejelly.game.gong2.login.dao;

import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.meta.GlobalCouponUse;

@Component("globalCouponRecordDao")
public class GlobalCouponUseDao extends BaseDomainDao<GlobalCouponUse> {
	@Override
	protected Class<GlobalCouponUse> getDomainClass() {
		return GlobalCouponUse.class;
	}

	public GlobalCouponUse getCouponUseRecord(String userId, String couponCode, int serverId) {
		return getObjectByCondition("userId = ? and couponCode = ? and serverId = ?", userId, couponCode, serverId);
	}
}