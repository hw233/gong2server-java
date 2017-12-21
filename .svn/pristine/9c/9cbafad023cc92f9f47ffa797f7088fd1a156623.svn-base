package com.hadoit.game.engine.guardian.gas;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang.time.DateUtils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.hadoit.game.engine.core.executor.TimerExecutor;
import com.hadoit.game.engine.core.executor.TimerExecutors;
import com.hadoit.game.engine.core.executor.TimerListener;
import com.hadoit.game.engine.core.executor.TimerThreadPoolExecutor;
import com.hadoit.game.engine.core.rpc.base.codec.factory.MessageCodecFactory;
import com.hadoit.game.engine.core.rpc.base.codec.factory.MessageEncryptFactory;
import com.hadoit.game.engine.core.rpc.simple.SimpleRpcServer;
import com.hadoit.game.engine.core.rpc.simple.SingleSimpleRpcClient;
import com.hadoit.game.engine.core.utils.callback.FutureCallback;
import com.hadoit.game.engine.guardian.core.GuardianContainer;
import com.hadoit.game.engine.guardian.core.Persistable;
import com.hadoit.game.engine.guardian.core.invoke.DbsInvoke;
import com.hadoit.game.engine.guardian.core.invoke.DbsInvokeDelegate;
import com.hadoit.game.engine.guardian.core.invoke.TimeInvoke;
import com.hadoit.game.engine.guardian.core.invoke.TimeInvokeDelegate;
import com.hadoit.game.engine.guardian.gas.entity.EntityManager;
import com.hadoit.game.engine.guardian.gas.entity.RawCommandCallback;
import com.hadoit.game.engine.guardian.gas.entity.RawCommandFutureCallback;
import com.hadoit.game.engine.guardian.gas.entity.TimerManager;
import com.hadoit.game.engine.guardian.gas.listener.GasDbsListener;
import com.hadoit.game.engine.guardian.gas.proxy.GasProxy;
import com.hadoit.game.engine.guardian.gas.proxy.GasServerProxy;
import com.hadoit.game.engine.guardian.utils.GuardianConstants;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;

/**
 * @author bezy 2012-8-29
 * 
 */
public class GasManager extends GuardianContainer implements GasContext {
	private final AtomicLong serverTickIdx = new AtomicLong(0);
	private long mainTickId = 0;

	private int serverFreq = 10;
	private float archivePeriod = 120f;

	private int clientIdleTime = 120;
	private SimpleRpcServer gasServer;
	private MessageCodecFactory clientCodecFactory;
	private MessageEncryptFactory clientEncryptFactory;
	private GasServerProxy gasServerProxy;
	private SingleSimpleRpcClient dbsClient;
	private DbsInvoke dbsInvoke;
	private TimeInvoke timeInvoke;
	private TimerManager timerManager;
	private EntityManager entityManager;

	private String dbsHost;
	private int dbsPort;

	private static TimerExecutor genMainThread() {
		TimerExecutor te = TimerExecutors.newSingleTimerExecutor();
		if (te instanceof TimerThreadPoolExecutor) {
			((TimerThreadPoolExecutor) te)
					.setThreadFactory(new ThreadFactoryBuilder().setNameFormat("Gas-main-%d").setDaemon(false).build());
		}
		return te;
	}

	public GasManager() {
		super(genMainThread());
	}

	@Override
	public String getName() {
		return GuardianConstants.SN_GAS;
	}

	@Override
	protected short getScope() {
		return GuardianConstants.SN_SCOPE_GAS;
	}

	public long getServerTick() {
		return serverTickIdx.get();
	}

	public void startTick() {
		final float heartPeriod = 1.0f / getServerFreq();
		mainTickId = timeInvoke.addFixedRateTimer(new TimerListener() {
			@Override
			public void onTimer(long id, Object[] params) throws Exception {
				long start = System.currentTimeMillis();
				try {
					tick();
					//
					timerManager.runOnTick();
					entityManager.archiveOnTick();
				} finally {
					float duration = (System.currentTimeMillis() - start) * 1.0f / DateUtils.MILLIS_PER_SECOND;
					if (duration > heartPeriod * 0.5f) {
						GuardianLogger.warn("Main tick execute too much time: " + duration + "s");
					}
				}
			}
		}, 0, heartPeriod);
		GuardianLogger.info(getIdentify() + " gas tick started!");
	}

	public void tick() {
		serverTickIdx.incrementAndGet();
	}

	public int getServerFreq() {
		return serverFreq;
	}

	public void setServerFreq(int serverFreq) {
		assert serverFreq > 0;
		this.serverFreq = serverFreq;
	}

	public float getArchivePeriod() {
		return archivePeriod;
	}

	public void setArchivePeriod(float archivePeriod) {
		this.archivePeriod = archivePeriod;
	}

	public int getClientIdleTime() {
		return clientIdleTime;
	}

	public void setClientIdleTime(int clientIdleTime) {
		this.clientIdleTime = clientIdleTime;
	}

	public MessageCodecFactory getClientCodecFactory() {
		return clientCodecFactory;
	}

	public void setClientCodecFactory(MessageCodecFactory clientCodecFactory) {
		this.clientCodecFactory = clientCodecFactory;
	}

	public MessageEncryptFactory getClientEncryptFactory() {
		return clientEncryptFactory;
	}

	public void setClientEncryptFactory(MessageEncryptFactory clientEncryptFactory) {
		this.clientEncryptFactory = clientEncryptFactory;
	}

	public GasServerProxy getGasServerProxy() {
		return gasServerProxy;
	}

	public void setGasServerProxy(GasServerProxy gasServerProxy) {
		gasServerProxy.setContext(this);
		this.gasServerProxy = gasServerProxy;
	}

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
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
		gasServer = initRpcServer("GasServer", getClientCodecFactory());
		gasServer.setMessageEncryptFactory(getClientEncryptFactory());
		gasServer.setIdleTime(getClientIdleTime());

		// init dbs client
		dbsClient = (SingleSimpleRpcClient) initRpcClient(true, "DbsClient", getCodecFactory());
		dbsClient.setOrder(false);
		dbsClient.setPoolSize(16);
		registerClientRpcHandler(dbsClient, "dbsListener", new GasDbsListener(this));
		dbsInvoke = new DbsInvokeDelegate(dbsClient);
		//
		timeInvoke = new TimeInvokeDelegate(getMainExecutor());
		timerManager = new TimerManager(this);
		entityManager = new EntityManager(this);
	}

	@Override
	protected void doStart() {
		// connect dbs
		registerToDbs(getDbsHost(), getDbsPort());
	}

	@Override
	protected void doDispose() {
		if (mainTickId > 0) {
			timeInvoke.clearTimer(mainTickId);
			mainTickId = 0;
		}
		timerManager.destroy();
		//
		if (gasServer != null) {
			gasServer.dispose();
		}
		//
		if (dbsClient != null && dbsClient.isConnected()) {
			entityManager.dispose(true);
		}
		if (dbsClient != null) {
			dbsClient.dispose();
		}
		if (gasServerProxy != null) {
			gasServerProxy.onUnregistered();
		}
	}

	public void registerToDbs(String host, int port) {
		registerToRpc(dbsClient, GuardianConstants.SN_DBS, host, port, true);
	}

	public void startGasServer() {
		gasServer.start();
		GuardianLogger.info(getIdentify() + " gas server started!");
	}

	public void registerGasProxy(String handlerName, GasProxy gasProxy) {
		gasProxy.setContext(this);
		registerServerRpcHandler(gasServer, handlerName, gasProxy);
	}

	@Override
	public void closeClient(int clientChannelId) {
		gasServer.close(clientChannelId);
	}

	@Override
	public void callClient(int clientChannelId, String op, Object... params) {
		gasServer.notify(clientChannelId, op, params);
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
	public void executeRawCommand(String command, Object[] args, RawCommandCallback callback) {
		executeRawCommand(null, command, args, new RawCommandFutureCallback(callback));
	}

	@Override
	public void executeRawCommandBalance(String balanceId, String command, Object[] args, RawCommandCallback callback) {
		executeRawCommand(balanceId, command, args, new RawCommandFutureCallback(callback));
	}

	@Override
	public long addTimer(TimerListener listener, float start, float interval, Object... params) {
		return timerManager.addTimer(listener, start, interval, params);
	}

	@Override
	public void clearTimer(long id) {
		timerManager.clearTimer(id);
	}

}
