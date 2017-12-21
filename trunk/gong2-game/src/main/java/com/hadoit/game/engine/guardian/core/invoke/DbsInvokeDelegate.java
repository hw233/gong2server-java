package com.hadoit.game.engine.guardian.core.invoke;

import com.hadoit.game.engine.core.rpc.simple.SingleSimpleRpcClient;
import com.hadoit.game.engine.core.utils.callback.FutureCallback;
import com.hadoit.game.engine.guardian.core.Persistable;
import com.hadoit.game.engine.guardian.utils.GuardianConstants;

/**
 * @author bezy 2012-9-5
 * 
 */
public class DbsInvokeDelegate implements DbsInvoke {
	private SingleSimpleRpcClient dbsClient;

	public DbsInvokeDelegate(SingleSimpleRpcClient dbsClient) {
		this.dbsClient = dbsClient;
	}

	public void loadGameObject(String entityType, long dbId, FutureCallback<Persistable> callback) {
		dbsClient.invokeRpc(GuardianConstants.RPC_DBS_HANDLER_LOAD_GAME_OBJECT, new Object[] { entityType, dbId },
				callback);
	}

	public void saveGameObject(String entityType, Persistable entityData, FutureCallback<Long> callback) {
		dbsClient.invokeRpc(GuardianConstants.RPC_DBS_HANDLER_SAVE_GAME_OBJECT,
				new Object[] { entityType, entityData }, callback);
	}

	public void deleteGameObject(String entityType, long dbId, FutureCallback<Boolean> callback) {
		dbsClient.invokeRpc(GuardianConstants.RPC_DBS_HANDLER_DELETE_GAME_OBJECT, new Object[] { entityType, dbId },
				callback);
	}

	public void executeRawCommand(String balanceId, String command, Object[] args, FutureCallback<Object[]> callback) {
		dbsClient.invokeRpc(GuardianConstants.RPC_DBS_HANDLER_EXECUTE_RAW_COMMAND, new Object[] { balanceId, command,
				args }, callback);
	}

}
