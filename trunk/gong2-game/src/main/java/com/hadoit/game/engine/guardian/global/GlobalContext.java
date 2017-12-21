package com.hadoit.game.engine.guardian.global;

import com.hadoit.game.engine.guardian.core.GuardianContext;
import com.hadoit.game.engine.guardian.core.invoke.DbsInvoke;
import com.hadoit.game.engine.guardian.gas.entity.Timerable;

/**
 * @author bezy 2015-3-9
 * 
 */
public interface GlobalContext extends GuardianContext, DbsInvoke, Timerable {

	/**
	 * 发送数据给client
	 * 
	 * @param serverIdentify
	 * @param clientChannelId
	 * @param op
	 * @param params
	 */
	public void callClient(String serverIdentify, int clientChannelId, String op, Object... params);

	/**
	 * 关闭client
	 * 
	 * @param serverIdentify
	 * @param clientChannelId
	 */
	public void closeClient(String serverIdentify, int clientChannelId);

	/**
	 * 发送数据给server
	 * 
	 * @param serverIdentify
	 * @param op
	 * @param params
	 */
	public void callServer(String serverIdentify, String op, Object... params);
}
