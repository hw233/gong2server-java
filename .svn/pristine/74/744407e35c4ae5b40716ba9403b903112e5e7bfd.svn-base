package com.hadoit.game.engine.guardian.gas.entity;

import com.hadoit.game.engine.core.utils.callback.DefaultFutureCallback;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;

/**
 * @author bezy 2012-9-13
 * 
 * @param <T>
 */
final class DestroyEntityOnSaveFutureCallback<T extends Entity> extends DefaultFutureCallback<Long> {
	private final EntityManager manager;
	private final T entity;
	private final EntityCallback<T> callback;

	public DestroyEntityOnSaveFutureCallback(EntityManager manager, T entity, EntityCallback<T> callback) {
		this.manager = manager;
		this.entity = entity;
		this.callback = callback;
	}

	@Override
	public void onSuccess(Long dbId) throws Exception {
		manager.removeEntity(entity);
		if (callback != null) {
			callback.onResult(true, entity);
		}
	}

	@Override
	public void onException(Throwable cause) throws Exception {
		GuardianLogger.error(cause, "Catch exception on destroy entity on save: " + entity.getId());
		entity.destroyFailed();
		if (callback != null) {
			callback.onResult(false, null);
		}
	}

}