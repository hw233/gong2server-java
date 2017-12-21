package com.hadoit.game.engine.guardian.global.handler;

import java.net.InetSocketAddress;

import com.hadoit.game.engine.core.rpc.base.annotation.Rpc;
import com.hadoit.game.engine.core.rpc.base.annotation.RpcEvent;
import com.hadoit.game.engine.core.rpc.base.constant.RpcEventType;
import com.hadoit.game.engine.core.rpc.base.handler.RpcHandler;
import com.hadoit.game.engine.core.rpc.simple.context.ServerChannelContext;
import com.hadoit.game.engine.guardian.global.GlobalManager;
import com.hadoit.game.engine.guardian.utils.GuardianConstants;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;

/**
 * @author bezy 2015-3-9
 * 
 */
public class GlobalRpcHandler implements RpcHandler {

	private GlobalManager globalManager;

	public GlobalRpcHandler(GlobalManager globalManager) {
		this.globalManager = globalManager;
	}

	@RpcEvent(RpcEventType.CHANNEL_DISCONNECTED)
	public void onDisconnected(final ServerChannelContext channelContext) {
		String inetAddr = ((InetSocketAddress) channelContext.getChannel().getRemoteAddress()).getAddress()
				.getHostAddress();
		String identify = globalManager.getServerInfoManager().getServerIdentify(channelContext);
		if (identify != null) {
			globalManager.getServerInfoManager().unregisterServer(identify);
			GuardianLogger.info("global client " + identify + "@" + inetAddr + " disconnected!");
		}
	}

	@Rpc(fullAlias = GuardianConstants.RPC_GLOBAL_HANDLER_REGISTER)
	public void register(final ServerChannelContext channelContext, final String identify) {
		String inetAddr = ((InetSocketAddress) channelContext.getChannel().getRemoteAddress()).getAddress()
				.getHostAddress();
		globalManager.getServerInfoManager().registerServer(identify, inetAddr, channelContext);
		GuardianLogger.info("global client " + identify + "@" + inetAddr + " connected!");
	}

	@Rpc(fullAlias = GuardianConstants.RPC_GLOBAL_HANDLER_CALL_OTHER_SERVER)
	public void callOtherServer(final String identify, final String op, final Object[] params) {
		globalManager.callServer(identify, op, params);
	}

	@Rpc(fullAlias = GuardianConstants.RPC_GLOBAL_HANDLER_CALL_OTHER_CLIENT)
	public void callOtherClient(final String identify, int clientChannelId, final String op, final Object[] params) {
		globalManager.callClient(identify, clientChannelId, op, params);
	}

	@Rpc(fullAlias = GuardianConstants.RPC_GLOBAL_HANDLER_CLOSE_OTHER_CLIENT)
	public void closeOtherClient(final String identify, int clientChannelId) {
		globalManager.closeClient(identify, clientChannelId);
	}

}
