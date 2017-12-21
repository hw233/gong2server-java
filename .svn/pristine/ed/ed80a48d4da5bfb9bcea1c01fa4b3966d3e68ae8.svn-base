package com.gamejelly.game.gong2.login.dao;

import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.meta.IdfaDeviceData;

@Component("idfaDeviceDataDao")
public class IdfaDeviceDataDao extends BaseDomainDao<IdfaDeviceData> {

	@Override
	protected Class<IdfaDeviceData> getDomainClass() {
		return IdfaDeviceData.class;
	}

	public IdfaDeviceData getIdfaDeviceData(String appId, String idfa, String deviceId) {
		return getObjectByCondition("appId = ? and idfa = ? and deviceId = ? limit 1", appId, idfa, deviceId);
	}
	public IdfaDeviceData getIdfa(String idfa) {
		return getObjectByCondition("idfa = ? limit 1",idfa);
	}
	public IdfaDeviceData getIdfaData(String appId, String idfa) {
		return getObjectByCondition("appId = ? and idfa = ? limit 1", appId, idfa);
	}

}
