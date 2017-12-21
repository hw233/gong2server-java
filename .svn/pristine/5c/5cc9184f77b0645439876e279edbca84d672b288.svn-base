package com.hadoit.game.engine.guardian.core;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang.StringUtils;

import com.hadoit.game.engine.core.executor.TimerExecutor;
import com.hadoit.game.engine.core.rpc.base.codec.factory.MessageCodecFactory;
import com.hadoit.game.engine.core.rpc.base.context.Context;
import com.hadoit.game.engine.core.rpc.base.handler.RpcHandler;
import com.hadoit.game.engine.core.rpc.simple.SimpleRpcClient;
import com.hadoit.game.engine.core.rpc.simple.SimpleRpcServer;
import com.hadoit.game.engine.core.rpc.simple.SingleSimpleRpcClient;
import com.hadoit.game.engine.core.rpc.simple.handler.DefaultSimpleRpcClientHandler;
import com.hadoit.game.engine.core.rpc.simple.handler.DefaultSimpleRpcServerHandler;
import com.hadoit.game.engine.core.rpc.simple.handler.SimpleRpcClientHandler;
import com.hadoit.game.engine.core.rpc.simple.handler.SimpleRpcServerHandler;
import com.hadoit.game.engine.guardian.core.exception.GuardianServerException;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;

public abstract class GuardianContainer extends Context implements Container, GuardianContext {
	/** 主线程 */
	private final TimerExecutor mainExecutor;

	private String serverId;

	private String listenHost;

	private int listenPort;

	private int idleTime = 60;

	private boolean detailError = false;

	private MessageCodecFactory codecFactory;

	private final AtomicBoolean disposed = new AtomicBoolean(false);

	public GuardianContainer(TimerExecutor mainExecutor) {
		this.mainExecutor = mainExecutor;
	}

	public TimerExecutor getMainExecutor() {
		return mainExecutor;
	}

	@Override
	public String getIdentify() {
		return getName() + "-" + getServerId();
	}

	@Override
	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	protected abstract short getScope();

	public String getListenHost() {
		return listenHost;
	}

	public void setListenHost(String listenHost) {
		this.listenHost = listenHost;
	}

	public int getListenPort() {
		return listenPort;
	}

	public void setListenPort(int listenPort) {
		this.listenPort = listenPort;
	}

	public int getIdleTime() {
		return idleTime;
	}

	public void setIdleTime(int idleTime) {
		this.idleTime = idleTime;
	}

	public boolean isDetailError() {
		return detailError;
	}

	public void setDetailError(boolean detailError) {
		this.detailError = detailError;
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
		GuardianLogger.info("init " + getIdentify() + "...");
		doInit();
	}

	protected void doInit() {

	}

	@Override
	public void start() {
		if (disposed.get()) {
			throw new IllegalStateException(getIdentify() + " already disposed!");
		}
		GuardianLogger.info("start " + getIdentify() + "@"
				+ (StringUtils.isNotBlank(getListenHost()) ? (getListenHost() + ":") : "") + getListenPort() + "...");
		doStart();
	}

	protected void doStart() {

	}

	@Override
	public void dispose() {
		if (disposed.getAndSet(true)) {
			return;
		}
		GuardianLogger.info("dispose " + getIdentify() + "...");
		doDispose();
	}

	protected void doDispose() {

	}

	protected SimpleRpcServer initRpcServer(String serverName, MessageCodecFactory codecFactory,
			SimpleRpcServerHandler... serverHandlers) {
		SimpleRpcServer rpcServer = new SimpleRpcServer(serverName);
		rpcServer.setBigEndian(false);
		rpcServer.setMessageCodecFactory(codecFactory);
		rpcServer.setScope(getScope());
		rpcServer.setHost(getListenHost());
		rpcServer.setPort(getListenPort());
		rpcServer.setIdleTime(getIdleTime());
		rpcServer.setDetailError(isDetailError());
		rpcServer.setMainExecutor(getMainExecutor());
		rpcServer.getFactory().setMaxFrameSize(1024 * 1024 * 1024);
		if (serverHandlers != null && serverHandlers.length > 0) {
			rpcServer.setServerHandler(serverHandlers[0]);
		} else {
			rpcServer.setServerHandler(new DefaultSimpleRpcServerHandler());
		}
		return rpcServer;
	}

	protected SimpleRpcClient initRpcClient(boolean single, String clientName, MessageCodecFactory codecFactory,
			SimpleRpcClientHandler... clientHandlers) {
		SimpleRpcClient rpcClient = single ? new SingleSimpleRpcClient(clientName) : new SimpleRpcClient(clientName);
		rpcClient.setBigEndian(false);
		rpcClient.setMessageCodecFactory(codecFactory);
		rpcClient.setScope(getScope());
		rpcClient.setIdleTime(getIdleTime());
		rpcClient.setMainExecutor(getMainExecutor());
		rpcClient.getFactory().setMaxFrameSize(1024 * 1024 * 1024);
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

	protected static void registerServerRpcHandler(SimpleRpcServer server, String handlerName, RpcHandler handler) {
		((DefaultSimpleRpcServerHandler) server.getServerHandler()).registerRpcHandler(handlerName, handler);
	}

	protected static void registerClientRpcHandler(SimpleRpcClient client, String handlerName, RpcHandler handler) {
		((DefaultSimpleRpcClientHandler) client.getClientHandler()).registerRpcHandler(handlerName, handler);
	}

}
