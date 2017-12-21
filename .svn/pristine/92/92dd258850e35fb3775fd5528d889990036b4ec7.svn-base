package com.gamejelly.game.gong2.login.service.sdk;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gamejelly.game.gong2.login.dao.IdfaDeviceDataDao;
import com.gamejelly.game.gong2.login.meta.IdfaDeviceData;

@Transactional
@Component("idfaService")
public class IdfaService {

	public static final int IDFA_TYPE_QIANKA = 0; // 钱咖
	public static final int IDFA_TYPE_QIANLU = 1; // 钱鹿
	public static final int IDFA_TYPE_NHHB = 2; // 内涵红包(畅娱)
	public static final int IDFA_TYPE_SHIKE = 3; // 试客
	public static final int IDFA_TYPE_PPHONGBAO = 4; // pp红包 aso100

	public static final String DEFAULT_APP_ID = "981761694";

	public static final Map<String, String> oprToAppIdMap = new HashMap<String, String>();

	static {
		oprToAppIdMap.put("ios", DEFAULT_APP_ID);
		oprToAppIdMap.put("ios2", "1129611792");
		oprToAppIdMap.put("ios3", "1140043330");
		oprToAppIdMap.put("ios4", "1140020719");
		oprToAppIdMap.put("ios5", "981761116");
		oprToAppIdMap.put("ios6", "1190569202");
		oprToAppIdMap.put("ios7", "1191246268");
		oprToAppIdMap.put("ios8", "1224002352");
		oprToAppIdMap.put("ios9", "1226927952");
		oprToAppIdMap.put("ios11", "1228060306");
	}

	@Autowired
	private IdfaDeviceDataDao idfaDeviceDataDao;

	public Object idfaQueryForType(String appid, String idfaListStr, int idType) {
		if (!oprToAppIdMap.values().contains(appid)) {
			return null;
		}
		String[] idArr = StringUtils.splitByWholeSeparator(idfaListStr, ",");
		if (ArrayUtils.isEmpty(idArr)) {
			return null;
		}
		Map<String, Integer> allIdfaMap = new HashMap<String, Integer>();
		for (String id : idArr) {
			String rid = StringUtils.trim(id);
			allIdfaMap.put(rid, 0);
		}

		for (String id : allIdfaMap.keySet()) {
			IdfaDeviceData idfaData = idfaDeviceDataDao.getIdfaData(appid, id);
			if (idfaData != null) {
				allIdfaMap.put(id, 1);
			} else {
				allIdfaMap.put(id, 0);
			}
		}

		if (idType == IDFA_TYPE_QIANKA) {
			return allIdfaMap;
		} else if (idType == IDFA_TYPE_QIANLU) {
			return allIdfaMap;
		} else if (idType == IDFA_TYPE_NHHB) {
			return allIdfaMap;
		} else if (idType == IDFA_TYPE_SHIKE) {
			return allIdfaMap;
		} else if (idType == IDFA_TYPE_PPHONGBAO) {
			return allIdfaMap;
		}

		return allIdfaMap;
	}

	public boolean tryAddNewDevice(String opr, String idfa, String deviceId) {
		String appId = oprToAppIdMap.get(opr);
		if (StringUtils.isEmpty(appId)) {
			appId = DEFAULT_APP_ID;
		}

		IdfaDeviceData data = idfaDeviceDataDao.getIdfaDeviceData(appId, idfa, deviceId);
		if (data != null) {
			return false;
		}
		data = new IdfaDeviceData();
		data.setAppId(appId);
		data.setIdfa(idfa);
		data.setDeviceId(deviceId);
		data.setCreateTime(System.currentTimeMillis());
		idfaDeviceDataDao.addObject(data);
		return true;
	}
}
