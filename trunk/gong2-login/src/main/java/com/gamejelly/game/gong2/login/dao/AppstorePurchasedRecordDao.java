package com.gamejelly.game.gong2.login.dao;

import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.meta.AppstorePurchasedRecord;


@Component("appstorePurchasedRecordDao")
public class AppstorePurchasedRecordDao extends BaseDomainDao<AppstorePurchasedRecord> {

	@Override
	protected Class<AppstorePurchasedRecord> getDomainClass() {
		return AppstorePurchasedRecord.class;
	}

}