package com.hadoit.game.engine.guardian.gas;

import com.hadoit.game.engine.guardian.core.GuardianContext;
import com.hadoit.game.engine.guardian.core.invoke.DbsInvoke;
import com.hadoit.game.engine.guardian.gas.entity.EntityManager;
import com.hadoit.game.engine.guardian.gas.entity.RawCommandCallback;
import com.hadoit.game.engine.guardian.gas.entity.Timerable;

/**
 * @author bezy
 * 
 */
public interface GasContext extends GuardianContext, DbsInvoke, Timerable {

	public EntityManager getEntityManager();

	public void executeRawCommand(String command, Object[] args, RawCommandCallback callback);

	public void executeRawCommandBalance(String balanceId, String command, Object[] args, RawCommandCallback callback);

	/**
	 * 关闭client
	 * 
	 * @param clientChannelId
	 */
	public void closeClient(int clientChannelId);

	/**
	 * 发送数据给client
	 * 
	 * @param clientChannelId
	 * @param op
	 * @param params
	 */
	public void callClient(int clientChannelId, String op, Object... params);

}
