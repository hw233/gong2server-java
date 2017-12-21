package com.hadoit.game.engine.guardian.gas.entity;

import com.hadoit.game.engine.core.utils.callback.DefaultFutureCallback;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;

/**
 * @author bezy 2013-11-22
 * 
 * @param <T>
 */
public class RawCommandFutureCallback extends DefaultFutureCallback<Object[]> {

	private final RawCommandCallback callback;

	public RawCommandFutureCallback(RawCommandCallback callback) {
		this.callback = callback;
	}

	@Override
	public void onSuccess(Object[] results) throws Exception {
		if (callback != null) {
			callback.onResult(results[0], (Integer) results[1], null);
		}
	}

	@Override
	public void onException(Throwable cause) throws Exception {
		GuardianLogger.error(cause, "Catch exception on raw command");
		if (callback != null) {
			callback.onResult(null, 0, cause.getMessage());
		}
	}

}
