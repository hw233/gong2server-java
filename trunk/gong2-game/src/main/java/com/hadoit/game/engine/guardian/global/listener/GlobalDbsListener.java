package com.hadoit.game.engine.guardian.global.listener;

import com.hadoit.game.engine.core.rpc.base.annotation.RpcEvent;
import com.hadoit.game.engine.core.rpc.base.constant.RpcEventType;
import com.hadoit.game.engine.core.rpc.base.handler.RpcHandler;
import com.hadoit.game.engine.core.rpc.simple.context.ClientChannelContext;
import com.hadoit.game.engine.guardian.global.GlobalManager;
import com.hadoit.game.engine.guardian.utils.GuardianConstants;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;

/**
 * @author bezy 2012-9-4
 * 
 */
public class GlobalDbsListener implements RpcHandler {
	private GlobalManager globalManager;

	public GlobalDbsListener(GlobalManager globalManager) {
		this.globalManager = globalManager;
	}

	@RpcEvent(RpcEventType.CHANNEL_CONNECTED)
	public void onConnect(final ClientChannelContext channelContext) throws Exception {
		GuardianLogger.info("global dbs connected!");
		channelContext.invokeRpc(GuardianConstants.RPC_DBS_HANDLER_REGISTER,
				new Object[] { globalManager.getIdentify() });
		if (globalManager.getGlobalServerProxy() != null) {
			globalManager.getGlobalServerProxy().onRegistered();
		}
		// 启动global server
		globalManager.startGlobalServer();
	}

	@RpcEvent(RpcEventType.CHANNEL_DISCONNECTED)
	public void onDisconnected(final ClientChannelContext channelContext) throws Exception {
		GuardianLogger.warn("dbs disconnected and to dispose global...");
		globalManager.dispose();
	}

}
