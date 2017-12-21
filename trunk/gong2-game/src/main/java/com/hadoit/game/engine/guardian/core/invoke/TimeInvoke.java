package com.hadoit.game.engine.guardian.core.invoke;

import com.hadoit.game.engine.core.executor.TimerListener;

/**
 * @author bezy 2012-9-6
 * 
 */
public interface TimeInvoke extends Invoke {
	/**
	 * @param listener
	 * @param initialDelay
	 *            seconds
	 * @param delay
	 *            seconds
	 * @param params
	 * @return
	 */
	public long addTimer(TimerListener listener, float initialDelay, float delay, Object... params);

	/**
	 * @param listener
	 * @param initialDelay
	 *            seconds
	 * @param period
	 *            seconds
	 * @param params
	 * @return
	 */
	public long addFixedRateTimer(TimerListener listener, float initialDelay, float period, Object... params);

	/**
	 * @param id
	 */
	public void clearTimer(long id);

}
