package com.gamejelly.game.gong2.login.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gamejelly.game.gong2.login.dao.PlatformPayRecordDao;
import com.gamejelly.game.gong2.login.meta.PlatformPayRecord;
import com.gamejelly.game.gong2.login.utils.FsGameLoginConst;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Transactional
@Component("platformPayRecordService")
public class PlatformPayRecordService {
	@Autowired
	private PlatformPayRecordDao platformPayRecordDao;
	@Resource
	private PlatformPayAmountRecordService platformPayAmountRecordService;

	public void addPlatformPayRecord(PlatformPayRecord ppr) {
		platformPayRecordDao.addObject(ppr);
	}

	public PlatformPayRecord getByOrderSn(String orderSn) {
		return platformPayRecordDao.getByOrderSn(orderSn);
	}

	public void updateServerState(long id, int serverState) {
		platformPayRecordDao.updateServerState(id, serverState);
	}

	public void updatePayRecord(PlatformPayRecord ppr) {
		platformPayRecordDao.updateObject(ppr);
	}
	
	public void updateSource(String out_trade_no,String source) {
		PlatformPayRecord ppr=getByOrderSn(out_trade_no);
		platformPayRecordDao.updateSource(ppr.getId(), source);
	}
	
	public void updatePayPlatformOrderId(String out_trade_no,String payPlatformOrderId) {
		PlatformPayRecord ppr=getByOrderSn(out_trade_no);
		platformPayRecordDao.updatePayPlatformOrderId(ppr.getId(), payPlatformOrderId);
	}

	public List<PlatformPayRecord> getNoCommitOrderBy(String avatarId, int serverId) {
		return platformPayRecordDao.getListByFullCondition(
				"avatarId=? and serverId=? and (serverState=? or serverState=?)", avatarId, serverId, 0,
				FsGameLoginConst.AS_SERVER_STATE_VERIFIED);
	}
	public List<PlatformPayRecord> getorderByopr(String opr, int serverState,String begin,String end) {
		return platformPayRecordDao.getListByFullCondition(
				"oprGroup=? and serverState=? and createTime >=? and  createTime <=?", opr, serverState,begin,end);
	}
	public void updateExtData(String extData1, String extData2, String orderSn) {
		platformPayRecordDao.updateColumnValueByCondition("orderSn=?", new String[] { "extData1", "extData2" },
				new Object[] { extData1, extData2 }, orderSn);
	}
	
	public void updateExtData(String extData1,String orderSn){
		platformPayRecordDao.updateColumnValueByCondition("orderSn=?", new String[] { "extData1"},
				new Object[] { extData1}, orderSn);
	}

	/**
	 * 更新或者保存记录
	 * @param platformPayRecord
	 */
	private void addOrUpdate(PlatformPayRecord platformPayRecord) {
		platformPayRecordDao.addOrUpdateObject(platformPayRecord);
	}

	/**
	 * 根据订单id 返回是否可以发送红包
	 * @param orderSn
	 * @return
	 */
	public Map<String, Object> canSendRedPackageByOrderId(String orderSn) {
		Map<String, Object> result = new HashMap<String, Object>();

		PlatformPayRecord order = getByOrderSn(orderSn);

		if (order==null||order.getServerState()!=FsGameLoginConst.AS_SERVER_STATE_GIVED_ITEM||order.isSendedRedPackage()) {
			result.put("flag", false);
			result.put("originalGetGold", 0);
			LoggerUtils.error("canSendRedPackageByOrderId order",order);
			return result;
		}
		result.put("flag", true);
		result.put("originalGetGold", order.getProductAmount()/10);
		return result;
	}

	/**
	 * 将是否发过红包标志位设置成true:已经发过
	 * @param orderSn
	 * @return
	 */
	public boolean setSendRedPackage(String orderSn) {
		PlatformPayRecord order = getByOrderSn(orderSn);
		if (order==null) {
			return false;
		}
		order.setSendedRedPackage(true);
		addOrUpdate(order);
		return true;
	}

}
