package com.hadoit.game.engine.guardian.global.proxy;

import com.hadoit.game.engine.guardian.core.ServerProxy;
import com.hadoit.game.engine.guardian.global.GlobalContext;

/**
 * @author bezy 2012-9-4
 * 
 */
public interface GlobalServerProxy extends ServerProxy {

	public void setContext(GlobalContext context);

}
