package com.hadoit.game.engine.guardian.global.client.listener;

import com.hadoit.game.engine.core.rpc.base.annotation.Rpc;
import com.hadoit.game.engine.core.rpc.base.annotation.RpcEvent;
import com.hadoit.game.engine.core.rpc.base.constant.RpcEventType;
import com.hadoit.game.engine.core.rpc.base.handler.RpcHandler;
import com.hadoit.game.engine.core.rpc.simple.context.ClientChannelContext;
import com.hadoit.game.engine.core.utils.callback.DefaultFutureCallback;
import com.hadoit.game.engine.guardian.global.client.GlobalClient;
import com.hadoit.game.engine.guardian.utils.GuardianConstants;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;

/**
 * @author bezy 2012-9-4
 * 
 */
public class GlobalClientGlobalListener implements RpcHandler {
	private GlobalClient globalClient;

	public GlobalClientGlobalListener(GlobalClient globalClient) {
		this.globalClient = globalClient;
	}

	@RpcEvent(RpcEventType.CHANNEL_CONNECTED)
	public void onConnect(final ClientChannelContext channelContext) throws Exception {
		GuardianLogger.info("global client connected!");
		channelContext.invokeRpc(GuardianConstants.RPC_GLOBAL_HANDLER_REGISTER,
				new Object[] { globalClient.getIdentify() }, new DefaultFutureCallback<Object>() {
					@Override
					public void onSuccess(Object result) throws Exception {
						GuardianLogger.info("global client register success!");
						if (globalClient.getGlobalClientListener() != null) {
							globalClient.getGlobalClientListener().onConnect();
						}
					}

					@Override
					public void onException(Throwable cause) throws Exception {
						GuardianLogger.error(cause, "global client register exception!");
						channelContext.close();
					}
				});
	}

	@RpcEvent(RpcEventType.CHANNEL_DISCONNECTED)
	public void onDisconnected(final ClientChannelContext channelContext) throws Exception {
		GuardianLogger.warn("global client disconnected!");
		if (globalClient.getGlobalClientListener() != null) {
			globalClient.getGlobalClientListener().onDisconnect();
		}
	}

	@Rpc(fullAlias = GuardianConstants.RPC_GLOBAL_LISTENER_CALL_CLIENT)
	public void callClient(int clientChannelId, String op, Object[] params) {
		if (globalClient.getGlobalClientListener() != null) {
			globalClient.getGlobalClientListener().callClient(clientChannelId, op, params);
		}
	}

	@Rpc(fullAlias = GuardianConstants.RPC_GLOBAL_LISTENER_CLOSE_CLIENT)
	public void closeClient(int clientChannelId) {
		if (globalClient.getGlobalClientListener() != null) {
			globalClient.getGlobalClientListener().closeClient(clientChannelId);
		}
	}
}
