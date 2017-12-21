package com.gamejelly.game.gong2.login.dao;

import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.meta.PlatformPayAmountRecord;

@Component("platformPayAmountRecord")
public class PlatformPayAmountRecordDao extends BaseDomainDao<PlatformPayAmountRecord> {

	@Override
	protected Class<PlatformPayAmountRecord> getDomainClass() {
		return PlatformPayAmountRecord.class;
	}

	public PlatformPayAmountRecord getByOrderSn(String orderSn) {
		return getObjectByCondition("orderSn=?", orderSn);
	}

	public void updateServerState(long id, int serverState) {
		updateColumnValue("serverState", serverState, id);
	}

}
