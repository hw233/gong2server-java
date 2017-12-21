package com.hadoit.game.engine.guardian.core.client;

import java.util.concurrent.atomic.AtomicBoolean;

import com.hadoit.game.engine.core.executor.TimerExecutor;
import com.hadoit.game.engine.core.rpc.base.codec.factory.MessageCodecFactory;
import com.hadoit.game.engine.core.rpc.base.context.Context;
import com.hadoit.game.engine.core.rpc.base.handler.RpcHandler;
import com.hadoit.game.engine.core.rpc.simple.SimpleRpcClient;
import com.hadoit.game.engine.core.rpc.simple.SingleSimpleRpcClient;
import com.hadoit.game.engine.core.rpc.simple.handler.DefaultSimpleRpcClientHandler;
import com.hadoit.game.engine.core.rpc.simple.handler.SimpleRpcClientHandler;
import com.hadoit.game.engine.guardian.core.exception.GuardianServerException;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;

/**
 * @author bezy 2015-3-9
 * 
 */
public abstract class GuardianClient extends Context implements Client, GuardianClientContext {
	/** 主线程 */
	private TimerExecutor mainExecutor;

	private String clientId;

	private int idleTime = 60;

	private MessageCodecFactory codecFactory;

	private final AtomicBoolean disposed = new AtomicBoolean(false);

	public GuardianClient() {
	}

	public GuardianClient(TimerExecutor mainExecutor) {
		setMainExecutor(mainExecutor);
	}

	public TimerExecutor getMainExecutor() {
		return mainExecutor;
	}

	public void setMainExecutor(TimerExecutor mainExecutor) {
		this.mainExecutor = mainExecutor;
	}

	@Override
	public String getIdentify() {
		return getName() + "-" + getClientId();
	}

	@Override
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	protected abstract short getScope();

	public int getIdleTime() {
		return idleTime;
	}

	public void setIdleTime(int idleTime) {
		this.idleTime = idleTime;
	}

	public MessageCodecFactory getCodecFactory() {
		return codecFactory;
	}

	public void setCodecFactory(MessageCodecFactory innerCodecFactory) {
		this.codecFactory = innerCodecFactory;
	}

	@Override
	public void init() {
		if (disposed.get()) {
			throw new IllegalStateException(getIdentify() + " already disposed!");
		}
		GuardianLogger.info(getIdentify() + " to init...");
		doInit();
	}

	protected void doInit() {

	}

	@Override
	public void connect() {
		if (disposed.get()) {
			throw new IllegalStateException(getIdentify() + " already disposed!");
		}
		GuardianLogger.info(getIdentify() + " to connect...");
		doConnect();
	}

	protected void doConnect() {

	}

	@Override
	public void dispose() {
		if (disposed.getAndSet(true)) {
			return;
		}
		GuardianLogger.info(getIdentify() + " to dispose...");
		doDispose();
	}

	protected void doDispose() {

	}

	protected SimpleRpcClient initRpcClient(boolean single, String clientName, MessageCodecFactory codecFactory,
			SimpleRpcClientHandler... clientHandlers) {
		SimpleRpcClient rpcClient = single ? new SingleSimpleRpcClient(clientName) : new SimpleRpcClient(clientName);
		rpcClient.setBigEndian(false);
		rpcClient.setMessageCodecFactory(codecFactory);
		rpcClient.setScope(getScope());
		rpcClient.setIdleTime(getIdleTime());
		rpcClient.setMainExecutor(getMainExecutor());
		if (clientHandlers != null && clientHandlers.length > 0) {
			rpcClient.setClientHandler(clientHandlers[0]);
		} else {
			rpcClient.setClientHandler(new DefaultSimpleRpcClientHandler());
		}
		return rpcClient;
	}

	protected void registerToRpc(SimpleRpcClient rpcClient, String name, String host, int port, boolean await) {
		GuardianLogger.info("connect to " + name + "@" + host + ":" + port + "...");
		try {
			if (rpcClient instanceof SingleSimpleRpcClient) {
				if (await) {
					((SingleSimpleRpcClient) rpcClient).singleConnectAwait(host, port);
				} else {
					((SingleSimpleRpcClient) rpcClient).singleConnect(host, port);
				}
			} else {
				if (await) {
					rpcClient.connectAwait(host, port);
				} else {
					rpcClient.connect(host, port);

				}
			}
		} catch (Exception e) {
			throw new GuardianServerException("connect to " + name + "@" + host + ":" + port + " exception!", e);
		}
	}

	protected static void registerClientRpcHandler(SimpleRpcClient client, String handlerName, RpcHandler handler) {
		((DefaultSimpleRpcClientHandler) client.getClientHandler()).registerRpcHandler(handlerName, handler);
	}

}
