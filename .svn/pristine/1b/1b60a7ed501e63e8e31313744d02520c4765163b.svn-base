package com.gamejelly.game.gong2.login.dao;

import java.awt.List;

import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.meta.PlatformPayRecord;

@Component("platformPayRecordModelDao")
public class PlatformPayRecordDao extends BaseDomainDao<PlatformPayRecord> {

	@Override
	protected Class<PlatformPayRecord> getDomainClass() {
		return PlatformPayRecord.class;
	}

	public PlatformPayRecord getByOrderSn(String orderSn) {
		return getObjectByCondition("orderSn=?", orderSn);
	}
	
	public void updateServerState(long id, int serverState) {
		updateColumnValue("serverState", serverState, id);
	}
	
	public void updateSource(long id, String source) {
		updateColumnValue("source", source, id);
	} 
	public void updatePayPlatformOrderId(long id, String payPlatformOrderId) {
		updateColumnValue("payPlatformOrderId", payPlatformOrderId, id);
	}

}
