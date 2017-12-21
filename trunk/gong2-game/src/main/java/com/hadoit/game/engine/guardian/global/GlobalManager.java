package com.hadoit.game.engine.guardian.global;

import com.hadoit.game.engine.core.executor.TimerExecutors;
import com.hadoit.game.engine.core.executor.TimerListener;
import com.hadoit.game.engine.core.rpc.simple.SimpleRpcServer;
import com.hadoit.game.engine.core.rpc.simple.SingleSimpleRpcClient;
import com.hadoit.game.engine.core.utils.callback.FutureCallback;
import com.hadoit.game.engine.guardian.core.GuardianContainer;
import com.hadoit.game.engine.guardian.core.Persistable;
import com.hadoit.game.engine.guardian.core.exception.GuardianServerException;
import com.hadoit.game.engine.guardian.core.invoke.DbsInvoke;
import com.hadoit.game.engine.guardian.core.invoke.DbsInvokeDelegate;
import com.hadoit.game.engine.guardian.core.invoke.TimeInvoke;
import com.hadoit.game.engine.guardian.core.invoke.TimeInvokeDelegate;
import com.hadoit.game.engine.guardian.global.base.ServerInfo;
import com.hadoit.game.engine.guardian.global.base.ServerInfoManager;
import com.hadoit.game.engine.guardian.global.handler.GlobalRpcHandler;
import com.hadoit.game.engine.guardian.global.listener.GlobalDbsListener;
import com.hadoit.game.engine.guardian.global.proxy.GlobalProxy;
import com.hadoit.game.engine.guardian.global.proxy.GlobalServerProxy;
import com.hadoit.game.engine.guardian.utils.GuardianConstants;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;

/**
 * @author bezy 2015-3-9
 * 
 */
public class GlobalManager extends GuardianContainer implements GlobalContext {
	private final ServerInfoManager serverInfoManager = new ServerInfoManager();

	private SimpleRpcServer globalServer;
	private SingleSimpleRpcClient dbsClient;
	private DbsInvoke dbsInvoke;
	private TimeInvoke timeInvoke;

	private GlobalServerProxy globalServerProxy;

	private String dbsHost;
	private int dbsPort;

	public GlobalManager() {
		super(TimerExecutors.newSingleTimerExecutor());
	}

	@Override
	public String getName() {
		return GuardianConstants.SN_GLOBAL;
	}

	@Override
	protected short getScope() {
		return GuardianConstants.SN_SCOPE_GLOBAL;
	}

	public ServerInfoManager getServerInfoManager() {
		return serverInfoManager;
	}

	public GlobalServerProxy getGlobalServerProxy() {
		return globalServerProxy;
	}

	public void setGlobalServerProxy(GlobalServerProxy globalServerProxy) {
		globalServerProxy.setContext(this);
		this.globalServerProxy = globalServerProxy;
	}

	public void registerGlobalProxy(String handlerName, GlobalProxy globalProxy) {
		globalProxy.setContext(this);
		registerServerRpcHandler(globalServer, handlerName, globalProxy);
	}

	public String getDbsHost() {
		return dbsHost;
	}

	public void setDbsHost(String dbsHost) {
		this.dbsHost = dbsHost;
	}

	public int getDbsPort() {
		return dbsPort;
	}

	public void setDbsPort(int dbsPort) {
		this.dbsPort = dbsPort;
	}

	@Override
	protected void doInit() {
		// init gas server
		globalServer = initRpcServer("GlobalServer", getCodecFactory());
		registerServerRpcHandler(globalServer, "globalRpcHandler", new GlobalRpcHandler(this));
		// init dbs client
		dbsClient = (SingleSimpleRpcClient) initRpcClient(true, "DbsClient", getCodecFactory());
		registerClientRpcHandler(dbsClient, "dbsListener", new GlobalDbsListener(this));
		dbsInvoke = new DbsInvokeDelegate(dbsClient);
		//
		timeInvoke = new TimeInvokeDelegate(getMainExecutor());
	}

	@Override
	protected void doStart() {
		// connect dbs
		registerToDbs(getDbsHost(), getDbsPort());
	}

	@Override
	protected void doDispose() {
		if (globalServer != null) {
			globalServer.dispose();
		}
		if (dbsClient != null) {
			dbsClient.dispose();
		}
		if (globalServerProxy != null) {
			globalServerProxy.onUnregistered();
		}
	}

	public void registerToDbs(String host, int port) {
		registerToRpc(dbsClient, GuardianConstants.SN_DBS, host, port, true);
	}

	public void startGlobalServer() {
		globalServer.start();
		GuardianLogger.info(getIdentify() + " global server started!");
	}

	@Override
	public void loadGameObject(String entityType, long dbId, FutureCallback<Persistable> callback) {
		dbsInvoke.loadGameObject(entityType, dbId, callback);
	}

	@Override
	public void saveGameObject(String entityType, Persistable entityData, FutureCallback<Long> callback) {
		dbsInvoke.saveGameObject(entityType, entityData, callback);
	}

	@Override
	public void deleteGameObject(String entityType, long dbId, FutureCallback<Boolean> callback) {
		dbsInvoke.deleteGameObject(entityType, dbId, callback);
	}

	@Override
	public void executeRawCommand(String balanceId, String command, Object[] args, FutureCallback<Object[]> callback) {
		dbsInvoke.executeRawCommand(balanceId, command, args, callback);
	}

	@Override
	public long addTimer(TimerListener listener, float start, float interval, Object... params) {
		return timeInvoke.addTimer(listener, start, interval, params);
	}

	@Override
	public void clearTimer(long id) {
		timeInvoke.clearTimer(id);
	}

	@Override
	public void callClient(String serverIdentify, int clientChannelId, String op, Object... params) {
		callServer(serverIdentify, GuardianConstants.RPC_GLOBAL_LISTENER_CALL_CLIENT, new Object[] { clientChannelId,
				op, params });
	}

	@Override
	public void closeClient(String serverIdentify, int clientChannelId) {
		callServer(serverIdentify, GuardianConstants.RPC_GLOBAL_LISTENER_CLOSE_CLIENT, new Object[] { clientChannelId });
	}

	@Override
	public void callServer(String serverIdentify, String op, Object... params) {
		ServerInfo serverInfo = serverInfoManager.getServer(serverIdentify);
		if (serverInfo == null) {
			throw new GuardianServerException("unknown server for identify: " + serverIdentify);
		}
		serverInfo.getChannelContext().notify(op, params);
	}

}
