package com.hadoit.game.engine.guardian.gas.entity;

import com.hadoit.game.engine.core.executor.TimerListener;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;

/**
 * @author bezy 2012-9-16
 * 
 */
public class EntityTimer implements Runnable {
	private final long id;
	private final TimerListener listener;
	private final Object[] params;
	private long startTick;
	private final int periodTick;

	public EntityTimer(long id, TimerListener listener, Object[] params, long startTick, int periodTick) {
		this.id = id;
		this.listener = listener;
		this.params = params;
		this.startTick = startTick;
		this.periodTick = periodTick;
	}

	public long getId() {
		return id;
	}

	public long getStartTick() {
		return startTick;
	}

	public void setStartTick(long startTick) {
		this.startTick = startTick;
	}

	public int getPeriodTick() {
		return periodTick;
	}

	@Override
	public void run() {
		try {
			listener.onTimer(id, params);
		} catch (Throwable t) {
			GuardianLogger.error(t, "Entity onTimer exception for timer id: " + id);
		}
	}

}
