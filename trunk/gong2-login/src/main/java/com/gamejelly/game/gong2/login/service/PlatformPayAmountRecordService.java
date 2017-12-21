package com.gamejelly.game.gong2.login.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gamejelly.game.gong2.login.dao.PlatformPayAmountRecordDao;
import com.gamejelly.game.gong2.login.meta.PlatformPayAmountRecord;
import com.gamejelly.game.gong2.login.utils.FsGameLoginConst;

@Transactional
@Component("platformPayAmountRecordService")
public class PlatformPayAmountRecordService {

	@Autowired
	private PlatformPayAmountRecordDao platformPayAmountRecordDao;

	public void addPlatformPayRecord(PlatformPayAmountRecord ppr) {
		platformPayAmountRecordDao.addObject(ppr);
	}

	public PlatformPayAmountRecord getByOrderSn(String orderSn) {
		return platformPayAmountRecordDao.getByOrderSn(orderSn);
	}

	public void updateServerState(long id, int serverState) {
		platformPayAmountRecordDao.updateServerState(id, serverState);
	}

	public void updatePayRecord(PlatformPayAmountRecord ppr) {
		platformPayAmountRecordDao.updateObject(ppr);
	}

	public List<PlatformPayAmountRecord> getNoCommitOrderBy(String avatarId, int serverId) {
		return platformPayAmountRecordDao.getListByFullCondition(
				"avatarId=? and serverId=? and (serverState=? or serverState=?)", avatarId, serverId, 0,
				FsGameLoginConst.AS_SERVER_STATE_VERIFIED);
	}

}
