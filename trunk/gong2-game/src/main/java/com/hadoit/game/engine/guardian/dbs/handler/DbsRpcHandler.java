package com.hadoit.game.engine.guardian.dbs.handler;

import java.net.InetSocketAddress;

import com.hadoit.game.engine.core.rpc.base.annotation.Rpc;
import com.hadoit.game.engine.core.rpc.base.annotation.RpcEvent;
import com.hadoit.game.engine.core.rpc.base.constant.RpcEventType;
import com.hadoit.game.engine.core.rpc.base.handler.RpcHandler;
import com.hadoit.game.engine.core.rpc.simple.context.RpcResult;
import com.hadoit.game.engine.core.rpc.simple.context.ServerChannelContext;
import com.hadoit.game.engine.guardian.core.Persistable;
import com.hadoit.game.engine.guardian.dbs.DbsManager;
import com.hadoit.game.engine.guardian.dbs.base.CommandRpcService;
import com.hadoit.game.engine.guardian.dbs.base.RawCommandResult;
import com.hadoit.game.engine.guardian.utils.GuardianConstants;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;

/**
 * @author bezy 2012-9-2
 * 
 */
public class DbsRpcHandler implements RpcHandler {

	private DbsManager dbsManager;

	public DbsRpcHandler(DbsManager dbsManager) {
		this.dbsManager = dbsManager;
	}

	@RpcEvent(RpcEventType.CHANNEL_DISCONNECTED)
	public void onDisconnected(final ServerChannelContext channelContext) {
		String inetAddr = ((InetSocketAddress) channelContext.getChannel().getRemoteAddress()).getAddress()
				.getHostAddress();
		String identify = (String) channelContext.removeAttribute(GuardianConstants.ATTR_DBS_CLIENT_IDENTIFY);
		GuardianLogger.info("dbs client " + identify + "@" + inetAddr + " disconnected!");
	}

	@Rpc(fullAlias = GuardianConstants.RPC_DBS_HANDLER_REGISTER)
	public void register(final ServerChannelContext channelContext, final String identify) {
		String inetAddr = ((InetSocketAddress) channelContext.getChannel().getRemoteAddress()).getAddress()
				.getHostAddress();
		channelContext.putAttribute(GuardianConstants.ATTR_DBS_CLIENT_IDENTIFY, identify);
		GuardianLogger.info("dbs client " + identify + "@" + inetAddr + " connected!");
	}

	@Rpc(fullAlias = GuardianConstants.RPC_DBS_HANDLER_LOAD_GAME_OBJECT)
	public void loadGameObject(final RpcResult rpcResult, final String entityType, final long dbId) {
		final long startTime = System.currentTimeMillis();
		dbsManager.getBalanceExecutorManager().execBalance(entityType, dbId, new Runnable() {
			@Override
			public void run() {
				try {
					Persistable r = dbsManager.getDbsServerProxy().loadGameObject(entityType, dbId);
					rpcResult.result(r);
				} catch (Exception e) {
					String errorMsg = "loadGameObject exception for type: " + entityType + ", id: " + dbId;
					rpcResult.error(errorMsg + ", message: " + e.getMessage());
					GuardianLogger.error(e, errorMsg);
				} finally {
					long duration = System.currentTimeMillis() - startTime;
					if (dbsManager.getLogLatency() > 0 && duration >= dbsManager.getLogLatency()) {
						GuardianLogger.warn("[Slow] loadGameObject: " + entityType + ", id: " + dbId + ", takes: "
								+ duration / 1000.0f + "s");
					}
				}
			}
		});
	}

	@Rpc(fullAlias = GuardianConstants.RPC_DBS_HANDLER_SAVE_GAME_OBJECT)
	public void saveGameObject(final RpcResult rpcResult, final String entityType, final Persistable entityData) {
		final long startTime = System.currentTimeMillis();
		dbsManager.getBalanceExecutorManager().execBalance(entityType, entityData.getDbId(), new Runnable() {
			@Override
			public void run() {
				try {
					dbsManager.getDbsServerProxy().saveGameObject(entityType, entityData);
					rpcResult.result(entityData.getDbId());
				} catch (Exception e) {
					String errorMsg = "saveGameObject exception for type: " + entityType + ", id: "
							+ entityData.getDbId();
					rpcResult.error(errorMsg + ", message: " + e.getMessage());
					GuardianLogger.error(e, errorMsg);
				} finally {
					long duration = System.currentTimeMillis() - startTime;
					if (dbsManager.getLogLatency() > 0 && duration >= dbsManager.getLogLatency()) {
						GuardianLogger.warn("[Slow] saveGameObject: " + entityType + ", id: " + entityData.getDbId()
								+ ", takes: " + duration / 1000.0f + "s");
					}
				}
			}
		});
	}

	@Rpc(fullAlias = GuardianConstants.RPC_DBS_HANDLER_DELETE_GAME_OBJECT)
	public void deleteGameObject(final RpcResult rpcResult, final String entityType, final long dbId) {
		final long startTime = System.currentTimeMillis();
		dbsManager.getBalanceExecutorManager().execBalance(entityType, dbId, new Runnable() {
			@Override
			public void run() {
				try {
					boolean r = dbsManager.getDbsServerProxy().deleteGameObject(entityType, dbId);
					rpcResult.result(r);
				} catch (Exception e) {
					String errorMsg = "deleteGameObject exception for type: " + entityType + ", id: " + dbId;
					rpcResult.error(errorMsg + ", message: " + e.getMessage());
					GuardianLogger.error(e, errorMsg);
				} finally {
					long duration = System.currentTimeMillis() - startTime;
					if (dbsManager.getLogLatency() > 0 && duration >= dbsManager.getLogLatency()) {
						GuardianLogger.warn("[Slow] deleteGameObject: " + entityType + ", id: " + dbId + ", takes: "
								+ duration / 1000.0f + "s");
					}
				}
			}
		});
	}

	@Rpc(fullAlias = GuardianConstants.RPC_DBS_HANDLER_EXECUTE_RAW_COMMAND)
	public void executeRawCommand(final RpcResult rpcResult, final String balanceId, final String command,
			final Object[] args) {
		final long startTime = System.currentTimeMillis();
		final Runnable r = new Runnable() {
			@Override
			public void run() {
				try {
					RawCommandResult commandResult;
					CommandRpcService rpcService = dbsManager.getCommandRpcService(command);
					if (rpcService != null) {
						commandResult = rpcService.onService(args);
					} else {
						commandResult = dbsManager.getDbsServerProxy().executeRawCommand(command, args);
					}
					if (commandResult != null) {
						rpcResult.result(commandResult.getResults());
					} else {
						throw new IllegalArgumentException("executeRawCommand must return new Object[]{ret, num}");
					}
				} catch (Exception e) {
					String errorMsg = "executeRawCommand exception for command: " + command + ", balanceId: "
							+ balanceId;
					rpcResult.error(errorMsg + ", message: " + e.getMessage());
					GuardianLogger.error(e, errorMsg);
				} finally {
					long duration = System.currentTimeMillis() - startTime;
					if (dbsManager.getLogLatency() > 0 && duration >= dbsManager.getLogLatency()) {
						GuardianLogger.warn("[Slow] executeRawCommand: " + command + ", balanceId: " + balanceId
								+ ", takes: " + duration / 1000.0f + "s");
					}
				}
			}
		};
		
		if (balanceId == null) {
			dbsManager.getBalanceExecutorManager().exec(r);
		} else {
			dbsManager.getBalanceExecutorManager().execBalance(balanceId, r);
		}
	}

}
