package com.gamejelly.gong2.utils;

import com.hadoit.game.common.framework.http.HttpResponseCallback;
import com.hadoit.game.engine.guardian.core.GuardianContainer;

public abstract class AsyncHttpResponseCallback<T> implements HttpResponseCallback<T> {

	private GuardianContainer container;

	public AsyncHttpResponseCallback(GuardianContainer container) {
		this.container = container;
	}

	@Override
	public void callback(final int code, final T result, final Exception e) {
		container.getMainExecutor().execute(new Runnable() {
			@Override
			public void run() {
				execute(code, result, e);
			}
		});
	}

	protected abstract void execute(int code, T result, Exception e);
}
