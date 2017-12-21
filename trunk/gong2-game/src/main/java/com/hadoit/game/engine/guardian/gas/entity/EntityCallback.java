package com.hadoit.game.engine.guardian.gas.entity;

/**
 * @author bezy 2012-9-13
 * 
 * @param <T>
 */
public interface EntityCallback<T extends Entity> {
	public void onResult(boolean r, T entity);
}
