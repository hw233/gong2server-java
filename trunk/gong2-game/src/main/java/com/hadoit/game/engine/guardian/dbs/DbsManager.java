package com.hadoit.game.engine.guardian.dbs;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;

import com.hadoit.game.common.lang.ReflectionUtils;
import com.hadoit.game.engine.core.executor.TimerExecutor;
import com.hadoit.game.engine.core.executor.TimerExecutors;
import com.hadoit.game.engine.core.executor.TimerListener;
import com.hadoit.game.engine.core.rpc.simple.SimpleRpcServer;
import com.hadoit.game.engine.guardian.core.GuardianContainer;
import com.hadoit.game.engine.guardian.dbs.base.BalanceExecutorManager;
import com.hadoit.game.engine.guardian.dbs.base.CommandRpc;
import com.hadoit.game.engine.guardian.dbs.base.CommandRpcService;
import com.hadoit.game.engine.guardian.dbs.base.MethodCommandRpcService;
import com.hadoit.game.engine.guardian.dbs.handler.DbsRpcHandler;
import com.hadoit.game.engine.guardian.dbs.proxy.DbsCommandProxy;
import com.hadoit.game.engine.guardian.dbs.proxy.DbsServerProxy;
import com.hadoit.game.engine.guardian.dbs.proxy.MemoryDbsServerProxy;
import com.hadoit.game.engine.guardian.utils.GuardianConstants;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;

/**
 * @author bezy 2012-9-2
 * 
 */
public class DbsManager extends GuardianContainer implements DbsContext {
	private final Map<String, CommandRpcService> commandRpcServiceMap = new ConcurrentHashMap<String, CommandRpcService>();

	private int workThreads = 50;
	private long logLatency = 50;
	private BalanceExecutorManager balanceExecutorManager;
	private SimpleRpcServer dbsServer;

	private DbsServerProxy dbsServerProxy;

	private TimerExecutor timerExecutor;

	public DbsManager() {
		super(TimerExecutors.newSingleTimerExecutor());
		setDbsServerProxy(new MemoryDbsServerProxy());
		timerExecutor = TimerExecutors.newTimerExecutorPool(5);
	}

	@Override
	public String getName() {
		return GuardianConstants.SN_DBS;
	}

	@Override
	protected short getScope() {
		return GuardianConstants.SN_SCOPE_DBS;
	}

	public BalanceExecutorManager getBalanceExecutorManager() {
		return balanceExecutorManager;
	}

	public void setWorkThreads(int workThreads) {
		assert workThreads > 0;
		this.workThreads = workThreads;
	}

	public long getLogLatency() {
		return logLatency;
	}

	public void setLogLatency(long logLatency) {
		this.logLatency = logLatency;
	}

	public DbsServerProxy getDbsServerProxy() {
		return dbsServerProxy;
	}

	public void setDbsServerProxy(DbsServerProxy dbsServerProxy) {
		this.dbsServerProxy = dbsServerProxy;
	}

	@Override
	protected void doInit() {
		assert dbsServerProxy != null;
		balanceExecutorManager = new BalanceExecutorManager(workThreads);
		// init dbs server
		dbsServer = initRpcServer("DbsServer", getCodecFactory());
		dbsServer.setOrder(false);
		dbsServer.setPoolSize(16);
		registerServerRpcHandler(dbsServer, "dbsRpcHandler", new DbsRpcHandler(this));
	}

	@Override
	protected void doStart() {
		balanceExecutorManager.start();
		dbsServerProxy.onRegistered();
		// dbs start
		dbsServer.start();
		GuardianLogger.info(getIdentify() + " dbs server started!");
	}

	@Override
	protected void doDispose() {
		if (dbsServer != null) {
			dbsServer.dispose();
		}
		if (balanceExecutorManager != null) {
			balanceExecutorManager.dispose(true);
		}
		dbsServerProxy.onUnregistered();
	}

	public void registerDbsCommandProxy(String handlerName, DbsCommandProxy dbsCommandProxy) {
		Method[] methods = dbsCommandProxy.getClass().getMethods();
		CommandRpc rpc;
		String opName;
		for (Method method : methods) {
			rpc = ReflectionUtils.findAnnotation(method, CommandRpc.class);
			if (rpc != null) {
				if (StringUtils.isNotEmpty(rpc.fullAlias())) {
					opName = rpc.fullAlias();
				} else {
					opName = handlerName + "." + StringUtils.defaultIfEmpty(rpc.alias(), method.getName());
				}
				addCommandRpcService(opName, new MethodCommandRpcService(opName, method, dbsCommandProxy, rpc.unpack()));
			}
		}
	}

	public void addCommandRpcService(String cmdName, CommandRpcService commandRpcService) {
		commandRpcServiceMap.put(cmdName, commandRpcService);
	}

	public CommandRpcService getCommandRpcService(String cmdName) {
		return commandRpcServiceMap.get(cmdName);
	}

	public void schedRepeatAtFix(long delay, long period, final Runnable runnable) {
		timerExecutor.addFixedRateTimer(new TimerListener(){
			@Override
			public void onTimer(long id, Object[] params) throws Exception {
				try {
					runnable.run();
				} catch (Exception e) {
					GuardianLogger.error(e, "DBS timer is error!");
				}
			}
		}, delay, period);
	}

	public void schedOnce(long delay, final Runnable runnable) {
		timerExecutor.addFixedRateTimer(new TimerListener(){
			@Override
			public void onTimer(long id, Object[] params) throws Exception {
				try {
					runnable.run();
				} catch (Exception e) {
					GuardianLogger.error(e, "DBS timer is error!");
				}
			}
		}, delay, 0);
	}

}
