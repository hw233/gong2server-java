package com.hadoit.game.engine.guardian.dbs.proxy;

import com.hadoit.game.engine.guardian.core.Persistable;
import com.hadoit.game.engine.guardian.core.ServerProxy;
import com.hadoit.game.engine.guardian.dbs.base.RawCommandResult;

/**
 * @author bezy 2012-9-2
 * 
 */
public interface DbsServerProxy extends ServerProxy {

	public void saveGameObject(String entityType, Persistable entityData);

	public Persistable loadGameObject(String entityType, long dbId);

	public boolean deleteGameObject(String entityType, long dbId);

	public RawCommandResult executeRawCommand(String command, Object[] args);

}
