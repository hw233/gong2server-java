package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.gamejelly.game.gong2.login.meta.PlatformPayAmountRecord;
import com.gamejelly.game.gong2.login.meta.PlatformPayRecord;
import com.gamejelly.game.gong2.login.net.GasAdminClientManager;
import com.gamejelly.game.gong2.login.service.GameAccountService;
import com.gamejelly.game.gong2.login.service.PlatformPayAmountRecordService;
import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.service.sdk.SdkService;
import com.gamejelly.game.gong2.login.utils.FsGameLoginConst;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

public abstract class SdkBaseAmountService implements SdkService {

	@Autowired
	protected PlatformPayAmountRecordService platformPayAmountRecordService;

	@Autowired
	protected GameAccountService gameAccountService;

	@Autowired
	protected GasAdminClientManager gasAdminClientManager;

	@SuppressWarnings("unchecked")
	protected void doGivePayItem(PlatformPayAmountRecord ppr) {
		LoggerUtils.info("Give amount item orderSn=", ppr.getOrderSn(), ppr.getAccountName(), ppr.getServerId(),
				ppr.getOprGroup(), ppr.getFinalGetGold(), ppr.getOriginalGetGold());

		if (ppr.getFinalGetGold() < ppr.getOriginalGetGold() || ppr.getOriginalGetGold() < 0) {
			return;
		}
		Object gasRes = null;
		long currTime = System.currentTimeMillis();
		try {
			// 给物品, 有可能不在线不一定能给成功
			gasRes = gasAdminClientManager
					.getSimpleRpcClientFromSubOpr(ppr.getServerId(), ppr.getOprGroup())
					.getSimpleRpcClient()
					.invokeRpc(
							FsGameLoginConst.ADMIN_LOGIN_REQ_PAY_AMOUNT_ADD_ITEM,
							new Object[] { ppr.getAvatarId(), ppr.getFinalGetGold(), ppr.getOriginalGetGold(),
									ppr.getOprGroup(), ppr.getOrderSn() }).get();
		} catch (Exception e) {
			LoggerUtils.error(e, "Give amount item failed! orderSn=", ppr.getOrderSn(), ppr.getAccountName(),
					ppr.getServerId(), ppr.getOprGroup(), ppr.getFinalGetGold(), ppr.getOriginalGetGold());
			return;
		}
		Map<String, Object> mRes = GsonFactory.getDefault().fromJson((JsonElement) gasRes, Map.class);
		boolean bRes = FsGameLoginUtils.getMapBoolean(mRes, "result", false);
		if (!bRes) {
			LoggerUtils.error("Give amount item return failed! orderSn=", ppr.getOrderSn(), ppr.getAccountName(),
					ppr.getServerId(), ppr.getOprGroup(), ppr.getFinalGetGold(), ppr.getOriginalGetGold());
			return;
		}
		ppr.setServerState(FsGameLoginConst.AS_SERVER_STATE_GIVED_ITEM);
		ppr.setSuccessTime(currTime);
		platformPayAmountRecordService.updatePayRecord(ppr);
	}

	protected Map<String, Object> doOutterAddItem(String avatarId, int serverId, String opr, String mail,
			Object[] items) {
		Object gasRes = null;
		try {
			gasRes = gasAdminClientManager
					.getSimpleRpcClientFromSubOpr(serverId, opr)
					.getSimpleRpcClient()
					.invokeRpc(FsGameLoginConst.ADMIN_LOGIN_REQ_OUTTER_ADD_ITEM,
							new Object[] { avatarId, serverId, mail, items }).get();
		} catch (Exception e) {
			LoggerUtils.error(e, "Outter add item failed!", avatarId, serverId, opr);
			return null;
		}
		return GsonFactory.getDefault().fromJson((JsonElement) gasRes, new TypeToken<Map<String, Object>>() {
		}.getType());
	}

	protected Map<String, Object> doOutterSubItem(String avatarId, int serverId, String opr, Object[] items) {
		Object gasRes = null;
		try {
			gasRes = gasAdminClientManager.getSimpleRpcClientFromSubOpr(serverId, opr).getSimpleRpcClient()
					.invokeRpc(FsGameLoginConst.ADMIN_LOGIN_REQ_OUTTER_SUB_ITEM, new Object[] { avatarId, serverId, items })
					.get();
		} catch (Exception e) {
			LoggerUtils.error(e, "Outter sub item failed!", avatarId, serverId, opr);
			return null;
		}
		return GsonFactory.getDefault().fromJson((JsonElement) gasRes, new TypeToken<Map<String, Object>>() {
		}.getType());
	}

	protected Map<String, Object> doOutterGetUser(String avatarId, int serverId, String opr) {
		Object gasRes = null;
		try {
			gasRes = gasAdminClientManager.getSimpleRpcClientFromSubOpr(serverId, opr).getSimpleRpcClient()
					.invokeRpc(FsGameLoginConst.ADMIN_LOGIN_REQ_OUTTER_GET_USER, new Object[] { avatarId, serverId }).get();
		} catch (Exception e) {
			LoggerUtils.error(e, "Outter get user failed!", avatarId, serverId, opr);
			return null;
		}
		return GsonFactory.getDefault().fromJson((JsonElement) gasRes, new TypeToken<Map<String, Object>>() {
		}.getType());
	}

	protected Map<String, Object> doOutterGetServerInfo(int serverId, String opr) {
		Object gasRes = null;
		try {
			gasRes = gasAdminClientManager.getSimpleRpcClientFromSubOpr(serverId, opr).getSimpleRpcClient()
					.invokeRpc(FsGameLoginConst.ADMIN_LOGIN_REQ_OUTTER_GET_SERVER_INFO, new Object[] {}).get();
		} catch (Exception e) {
			LoggerUtils.error(e, "Outter get server info failed!", serverId, opr);
			return null;
		}
		return GsonFactory.getDefault().fromJson((JsonElement) gasRes, new TypeToken<Map<String, Object>>() {
		}.getType());
	}

	public PlatformRetData verifyAllOrderWithCheck(Map<String, Object> requestParams) {
		String avatarId = FsGameLoginUtils.getMapString(requestParams, "avatarId", "");
		int serverId = FsGameLoginUtils.getMapInteger(requestParams, "serverId", 0);
		List<PlatformPayAmountRecord> noCommits = platformPayAmountRecordService.getNoCommitOrderBy(avatarId,
				serverId);
		if (CollectionUtils.isEmpty(noCommits)) {
			return new PlatformRetData(true, "No pending order!");
		}
		boolean b;
		for (PlatformPayAmountRecord ppr : noCommits) {
			if (ppr.getServerState() == FsGameLoginConst.AS_SERVER_STATE_VERIFIED) {
				// 内部处理掉单,直接给东西
				doGivePayItem(ppr);
			} else {
				// 平台处理掉单, 这个接口不一定所有渠道都有, 如果没有对应接口则返回false
				b = verifyOrderInternal(ppr.getOrderSn());
				if (!b) {
					LoggerUtils.warn("Order [", ppr.getOrderSn(), "] is not handle! ");
				}
			}
		}
		return new PlatformRetData(true, "All pending order handled!");
	}

	protected boolean verifyOrderInternal(String orderSn) {
		return false;
	}

	@Override
	public PlatformRetData verifyOrder(Map<String, Object> requestParams) {
		throw new UnsupportedOperationException("Not support verifyOrder!");
	}

	public PlatformRetData createOrder(Map<String, Object> requestParams) {
		throw new UnsupportedOperationException("Not support createOrder!");
	}

	public Object getXmfbDataCallback(Map<String, Object> requestParams) {
		throw new UnsupportedOperationException("Not support getXmfbDataCallback!");
	}

	public Object getServersCallback(Map<String, Object> requestParams) {
		throw new UnsupportedOperationException("Not support getServersCallback!");
	}

	public void createAccount(Map<String, Object> requestParams, String name, String pass, String platform,
			String deviceName, String deviceVersion, String deviceId, String ip) {
		throw new UnsupportedOperationException("Not support createAccount!");
	}

	public void resetPassword(Map<String, Object> requestParams, String name, String oldPass, String newPass) {
		throw new UnsupportedOperationException("Not support resetPassword!");
	}

	public boolean createAccountIfAbsent() {
		return true;
	}

	public PlatformRetData handleSdkData(Map<String, Object> requestParams) {
		return new PlatformRetData(true, null);
	}
	
	public void processOrder(String orderId, String money, boolean yuan) {
		PlatformPayAmountRecord ppr = platformPayAmountRecordService.getByOrderSn(orderId);
		if (ppr != null) {
			if (ppr.getServerState() != FsGameLoginConst.AS_SERVER_STATE_GIVED_ITEM) {
				platformPayAmountRecordService.updateServerState(ppr.getId(), FsGameLoginConst.AS_SERVER_STATE_VERIFIED);
				doGivePayItem(ppr);
			}
		}
	}

}
