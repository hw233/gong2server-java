package com.hadoit.game.engine.guardian.gas.entity;

import com.hadoit.game.engine.core.utils.callback.DefaultFutureCallback;
import com.hadoit.game.engine.guardian.core.Persistable;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;

/**
 * @author bezy 2012-9-13
 * 
 * @param <T>
 */
final class CreateEntityOnLoadFutureCallback<T extends Entity> extends DefaultFutureCallback<Persistable> {
	private final EntityManager manager;
	private final T entity;
	private final EntityCallback<T> callback;

	public CreateEntityOnLoadFutureCallback(EntityManager manager, T entity, EntityCallback<T> callback) {
		this.manager = manager;
		this.entity = entity;
		this.callback = callback;
	}

	@Override
	public void onSuccess(Persistable data) throws Exception {
		entity.setDbModel(data);
		manager.addEntity(entity);
		if (callback != null) {
			callback.onResult(true, entity);
		}

	}

	@Override
	public void onException(Throwable cause) throws Exception {
		GuardianLogger.error(cause, "Catch exception on create entity on load: " + entity.getId());
		if (callback != null) {
			callback.onResult(false, null);
		}

	}
}