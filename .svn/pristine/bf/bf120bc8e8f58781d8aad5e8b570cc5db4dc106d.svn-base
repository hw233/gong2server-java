package com.hadoit.game.engine.guardian.core.invoke;

import org.apache.commons.lang.time.DateUtils;

import com.hadoit.game.engine.core.executor.TimerExecutor;
import com.hadoit.game.engine.core.executor.TimerListener;

/**
 * @author bezy 2012-9-15
 * 
 */
public class TimeInvokeDelegate implements TimeInvoke {

	private TimerExecutor timerExecutor;

	public TimeInvokeDelegate(TimerExecutor timerExecutor) {
		this.timerExecutor = timerExecutor;
	}

	@Override
	public long addTimer(TimerListener listener, float initialDelay, float delay, Object... params) {
		return timerExecutor.addTimer(listener, (long) (initialDelay * DateUtils.MILLIS_PER_SECOND),
				(long) (delay * DateUtils.MILLIS_PER_SECOND), params);
	}

	@Override
	public long addFixedRateTimer(TimerListener listener, float initialDelay, float period, Object... params) {
		return timerExecutor.addFixedRateTimer(listener, (long) (initialDelay * DateUtils.MILLIS_PER_SECOND),
				(long) (period * DateUtils.MILLIS_PER_SECOND), params);
	}

	@Override
	public void clearTimer(long id) {
		timerExecutor.clearTimer(id);
	}

}
