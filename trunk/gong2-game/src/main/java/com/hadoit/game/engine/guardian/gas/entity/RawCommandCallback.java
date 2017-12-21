package com.hadoit.game.engine.guardian.gas.entity;

/**
 * @author bezy 2013-11-22
 * 
 * @param <T>
 */
public interface RawCommandCallback {

	public void onResult(Object result, int num, String error);

}
