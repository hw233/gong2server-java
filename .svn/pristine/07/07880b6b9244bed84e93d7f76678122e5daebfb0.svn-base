package com.hadoit.game.engine.guardian.global.client;

import java.util.concurrent.Future;

import com.hadoit.game.engine.core.executor.TimerExecutor;
import com.hadoit.game.engine.core.rpc.simple.SingleSimpleRpcClient;
import com.hadoit.game.engine.core.utils.callback.FutureCallback;
import com.hadoit.game.engine.guardian.core.client.GuardianClient;
import com.hadoit.game.engine.guardian.global.client.listener.GlobalClientGlobalListener;
import com.hadoit.game.engine.guardian.global.client.listener.GlobalClientListener;
import com.hadoit.game.engine.guardian.global.client.listener.GlobalListener;
import com.hadoit.game.engine.guardian.utils.GuardianConstants;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;

/**
 * @author bezy 2015-3-9
 * 
 */
public class GlobalClient extends GuardianClient {

	protected SingleSimpleRpcClient globalClient;

	private GlobalClientListener globalClientListener;

	private String globalHost;

	private int globalPort;

	public GlobalClient() {
	}

	public GlobalClient(TimerExecutor mainExecutor) {
		super(mainExecutor);
	}

	@Override
	public String getName() {
		return GuardianConstants.SN_GLOBAL_CLIENT;
	}

	@Override
	protected short getScope() {
		return GuardianConstants.SN_SCOPE_GLOBAL_CLIENT;
	}

	public GlobalClientListener getGlobalClientListener() {
		return globalClientListener;
	}

	public void setGlobalClientListener(GlobalClientListener globalClientListener) {
		globalClientListener.setContext(this);
		this.globalClientListener = globalClientListener;
	}

	public String getGlobalHost() {
		return globalHost;
	}

	public void setGlobalHost(String globalHost) {
		this.globalHost = globalHost;
	}

	public int getGlobalPort() {
		return globalPort;
	}

	public void setGlobalPort(int globalPort) {
		this.globalPort = globalPort;
	}

	public void registerGlobalListener(String handlerName, GlobalListener globalListener) {
		globalListener.setContext(this);
		registerClientRpcHandler(globalClient, handlerName, globalListener);
	}

	@Override
	protected void doInit() {
		// init global client
		globalClient = (SingleSimpleRpcClient) initRpcClient(true, "GlobalClient", getCodecFactory());
		globalClient.setAutoReconnect(true);
		registerClientRpcHandler(globalClient, "globalClientGlobalListener", new GlobalClientGlobalListener(this));
	}

	@Override
	protected void doConnect() {
		registerToGlobal(getGlobalHost(), getGlobalPort());
	}

	public void registerToGlobal(String host, int port) {
		registerToRpc(globalClient, GuardianConstants.SN_GLOBAL, host, port, true);
	}

	@Override
	protected void doDispose() {
		if (globalClient != null) {
			globalClient.dispose();
		}
	}

	public void callGlobal(String op, Object... params) {
		globalClient.notify(op, params);
	}

	public void invokeGlobal(String op, Object[] params, GlobalInvokeCallback callback) {
		invokeRpcGlobal(op, params, new GlobalInvokeFutureCallback(callback));
	}

	public <T> Future<T> invokeRpcGlobal(String op, Object[] params, FutureCallback<T> callback) {
		return globalClient.invokeRpc(op, params, callback);
	}

	public void callOtherServer(String serverIdentify, String op, Object... params) {
		globalClient.notify(GuardianConstants.RPC_GLOBAL_HANDLER_CALL_OTHER_SERVER, new Object[] { serverIdentify, op,
				params });
	}

	public void callOtherClient(String serverIdentify, int clientChannelId, String op, Object... params) {
		globalClient.notify(GuardianConstants.RPC_GLOBAL_HANDLER_CALL_OTHER_CLIENT, new Object[] { serverIdentify,
				clientChannelId, op, params });
	}

	public void closeOtherClient(String serverIdentify, int clientChannelId) {
		globalClient.notify(GuardianConstants.RPC_GLOBAL_HANDLER_CLOSE_OTHER_CLIENT, new Object[] { serverIdentify,
				clientChannelId });
	}

	public boolean isConnected() {
		if (globalClient == null || !globalClient.isConnected()) {
			GuardianLogger.warn("globalClient is not connected!");
			return false;
		}
		return true;
	}

}
