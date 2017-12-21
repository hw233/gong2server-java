package com.hadoit.game.engine.guardian.core;

/**
 * every can be persisted need implements this
 * 
 * @author bezy 2012-9-2
 * 
 */
public interface Persistable extends RemoteType {

	public long getDbId();

	public void setDbId(long dbId);

}
