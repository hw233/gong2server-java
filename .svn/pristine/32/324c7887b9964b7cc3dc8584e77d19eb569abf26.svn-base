package com.hadoit.game.engine.guardian.core.invoke;

import com.hadoit.game.engine.core.utils.callback.FutureCallback;
import com.hadoit.game.engine.guardian.core.Persistable;

/**
 * @author bezy 2012-9-5
 * 
 */
public interface DbsInvoke extends Invoke {

	/**
	 * 加载数据
	 * 
	 * @param entityType
	 * @param dbId
	 * @param callback
	 * @return
	 */
	public void loadGameObject(String entityType, long dbId, FutureCallback<Persistable> callback);

	/**
	 * 存储数据
	 * 
	 * @param entityType
	 * @param entityData
	 * @param callback
	 */
	public void saveGameObject(String entityType, Persistable entityData, FutureCallback<Long> callback);

	/**
	 * 删除数据
	 * 
	 * @param entityType
	 * @param dbId
	 * @param callback
	 */
	public void deleteGameObject(String entityType, long dbId, FutureCallback<Boolean> callback);

	/**
	 * @param command
	 * @param args
	 * @param callback
	 */
	public void executeRawCommand(String balanceId, String command, Object[] args, FutureCallback<Object[]> callback);

}
