package com.gamejelly.game.gong2.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gamejelly.game.gong2.login.dao.SdkAccountRelationDao;
import com.gamejelly.game.gong2.login.meta.SdkAccountRelation;

@Transactional
@Component("sdkAccountRelationService")
public class SdkAccountRelationService {
	@Autowired
	private SdkAccountRelationDao sdkAccountRelationDao;

	public void checkAndAddSdkAccountRelation(String accountName, String deviceId) {
		if (sdkAccountRelationDao.existByCondition("accountName = ? and deviceId = ?", accountName, deviceId)) {
			return;
		}
		SdkAccountRelation sar = new SdkAccountRelation();
		sar.setAccountName(accountName);
		sar.setDeviceId(deviceId);
		sar.setCreateTime(System.currentTimeMillis());
		sdkAccountRelationDao.addObject(sar);
	}
}
