package com.gamejelly.game.gong2.login.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.dao.GooglePurchasedRecordDao;
import com.gamejelly.game.gong2.login.meta.GooglePurchasedRecord;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.hadoit.game.common.lang.Pair;

@Component("GooglePurchasedRecordService")
public class GooglePurchasedRecordService {
	
	@Autowired
	private GooglePurchasedRecordDao googlePurchasedRecordDao;
	
	public void addGooglePurchasedRecord(GooglePurchasedRecord apr) {
		googlePurchasedRecordDao.addObject(apr);
	}

	public Pair<Integer, List<GooglePurchasedRecord>> searchGooglePurchasedRecord(int serverId, long gbId,
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

		int total = googlePurchasedRecordDao.getCountByCondition((String) sqlDatas[0], (Object[]) sqlDatas[1]);
		List<GooglePurchasedRecord> ds = googlePurchasedRecordDao.getListByFullCondition((String) sqlDatas[2],
				(Object[]) sqlDatas[3]);
		return Pair.newInstance(total, ds);
	}
}
