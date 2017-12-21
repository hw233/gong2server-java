package com.gamejelly.game.gong2.login.dao;

import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.meta.GooglePurchasedRecord;

@Component("GooglePurchasedRecordDao")
public class GooglePurchasedRecordDao extends BaseDomainDao<GooglePurchasedRecord> {

	@Override
	protected Class<GooglePurchasedRecord> getDomainClass() {
		// TODO Auto-generated method stub
		return GooglePurchasedRecord.class;
	}

}
