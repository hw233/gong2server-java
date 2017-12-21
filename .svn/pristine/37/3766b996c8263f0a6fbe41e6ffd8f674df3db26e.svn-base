package com.gamejelly.game.gong2.login.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.dao.AppstorePurchasedRecordDao;
import com.gamejelly.game.gong2.login.meta.AppstorePurchasedRecord;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.hadoit.game.common.lang.Pair;

@Component("appstorePurchasedRecordService")
public class AppstorePurchasedRecordService {

	@Autowired
	private AppstorePurchasedRecordDao appstorePurchasedRecordDao;

	public void addAppstorePurchasedRecord(AppstorePurchasedRecord apr) {
		appstorePurchasedRecordDao.addObject(apr);
	}

	public Pair<Integer, List<AppstorePurchasedRecord>> searchAppstorePurchasedRecord(int serverId, long gbId,
			String accountName, String roleName, int limit, long offset) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		if (serverId > 0) {
			params.put("serverId", serverId);
		}
		if (gbId > 0) {
			params.put("gbId", gbId);
		}
		if (StringUtils.isNotBlank(accountName)) {
			params.put("accountName", accountName);
		}
		if (StringUtils.isNotBlank(roleName)) {
			params.put("roleName", roleName);
		}
		Object[] sqlDatas = FsGameLoginUtils.buildCommonSqlData(params, limit, offset, null);

		int total = appstorePurchasedRecordDao.getCountByCondition((String) sqlDatas[0], (Object[]) sqlDatas[1]);
		List<AppstorePurchasedRecord> ds = appstorePurchasedRecordDao.getListByFullCondition((String) sqlDatas[2],
				(Object[]) sqlDatas[3]);
		return Pair.newInstance(total, ds);
	}
}
