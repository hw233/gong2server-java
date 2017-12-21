package com.hadoit.game.engine.guardian.core.client;

import com.hadoit.game.engine.core.rpc.base.context.IContext;

public interface GuardianClientContext extends IContext {

	public String getIdentify();

	public String getName();

	public String getClientId();

}
