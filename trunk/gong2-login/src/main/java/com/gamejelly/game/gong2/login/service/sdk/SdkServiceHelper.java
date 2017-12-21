package com.gamejelly.game.gong2.login.service.sdk;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gamejelly.game.gong2.login.meta.PlatformPayRecord;
import com.gamejelly.game.gong2.login.net.GasAdminClientManager;
import com.gamejelly.game.gong2.login.service.GameAccountService;
import com.gamejelly.game.gong2.login.service.PlatformPayRecordService;
import com.gamejelly.game.gong2.login.utils.FsGameLoginConst;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.google.gson.JsonElement;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

@Component("sdkServiceHelper")
@Transactional
public class SdkServiceHelper {
	@Autowired
	protected PlatformPayRecordService platformPayRecordService;

	@Autowired
	protected GameAccountService gameAccountService;

	@Autowired
	protected GasAdminClientManager gasAdminClientManager;

	@SuppressWarnings("unchecked")
	public void givePayItem(PlatformPayRecord ppr) {
		LoggerUtils.info("Give item orderSn=", ppr.getOrderSn(), ppr.getAccountName(), ppr.getServerId(),
				ppr.getOprGroup(), ppr.getProductId(), ppr.getProductCount(), ppr.getSource());

		if (ppr.getProductCount() < 1) {
			// 订单购买数量小于1
			return;
		}
		Object gasRes = null;
		final long currTime = System.currentTimeMillis();
		try {
			// 给物品, 有可能不在线不一定能给成功
			gasRes = gasAdminClientManager
					.getSimpleRpcClientFromSubOpr(ppr.getServerId(), ppr.getOprGroup())
					.getSimpleRpcClient()
					.invokeRpc(
							FsGameLoginConst.ADMIN_LOGIN_REQ_PAY_ADD_ITEM,
							new Object[] { ppr.getAvatarId(), ppr.getProductId(), ppr.getProductCount(),
									ppr.getOprGroup(), ppr.getOrderSn(), ppr.getSource(), ppr.getIp() }).get();
		} catch (Exception e) {
			LoggerUtils.error(e, "Give item failed! orderSn=", ppr.getOrderSn(), ppr.getAccountName(),
					ppr.getServerId(), ppr.getOprGroup(), ppr.getProductId());
			return;
		}
		Map<String, Object> mRes = GsonFactory.getDefault().fromJson((JsonElement) gasRes, Map.class);
		boolean bRes = FsGameLoginUtils.getMapBoolean(mRes, "result", false);
		if (!bRes) {
			LoggerUtils.warn("Give item return failed! orderSn=", ppr.getOrderSn(), ppr.getAccountName(),
					ppr.getServerId(), ppr.getOprGroup(), ppr.getProductId());
			return;
		}
		ppr.setServerState(FsGameLoginConst.AS_SERVER_STATE_GIVED_ITEM);
		ppr.setSuccessTime(currTime);
		platformPayRecordService.updatePayRecord(ppr);
	}
}
