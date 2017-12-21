package com.hadoit.game.engine.guardian.gas.listener;

import com.hadoit.game.engine.core.rpc.base.annotation.RpcEvent;
import com.hadoit.game.engine.core.rpc.base.constant.RpcEventType;
import com.hadoit.game.engine.core.rpc.base.handler.RpcHandler;
import com.hadoit.game.engine.core.rpc.simple.context.ClientChannelContext;
import com.hadoit.game.engine.guardian.gas.GasManager;
import com.hadoit.game.engine.guardian.utils.GuardianConstants;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;

/**
 * @author bezy 2012-9-4
 * 
 */
public class GasDbsListener implements RpcHandler {
	private GasManager gasManager;

	public GasDbsListener(GasManager gasManager) {
		this.gasManager = gasManager;
	}

	@RpcEvent(RpcEventType.CHANNEL_CONNECTED)
	public void onConnect(final ClientChannelContext channelContext) throws Exception {
		GuardianLogger.info("gas dbs connected!");
		channelContext.invokeRpc(GuardianConstants.RPC_DBS_HANDLER_REGISTER, new Object[] { gasManager.getIdentify() });
		if (gasManager.getGasServerProxy() != null) {
			gasManager.getGasServerProxy().onRegistered();
		}
		// 启动tick
		gasManager.startTick();
		gasManager.startGasServer();
	}

	@RpcEvent(RpcEventType.CHANNEL_DISCONNECTED)
	public void onDisconnected(final ClientChannelContext channelContext) throws Exception {
		GuardianLogger.warn("gas dbs disconnected and to dispose gas...");
		gasManager.dispose();
	}

}
