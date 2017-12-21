package com.hadoit.game.engine.guardian.global.client;

import com.hadoit.game.engine.core.utils.callback.DefaultFutureCallback;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;

public class GlobalInvokeFutureCallback extends DefaultFutureCallback<Object> {

	private final GlobalInvokeCallback callback;

	public GlobalInvokeFutureCallback(GlobalInvokeCallback callback) {
		this.callback = callback;
	}

	@Override
	public void onSuccess(Object result) throws Exception {
		if (callback != null) {
			callback.onResult(result);
		}
	}

	@Override
	public void onException(Throwable cause) throws Exception {
		GuardianLogger.error(cause, "Catch exception on raw command");
		if (callback != null) {
			callback.onResult(null);
		}
	}

}
