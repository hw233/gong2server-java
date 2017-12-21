package com.hadoit.game.engine.guardian.gas.entity;

import com.hadoit.game.engine.core.utils.callback.DefaultFutureCallback;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;

/**
 * @author bezy 2012-9-13
 * 
 * @param <T>
 */
final class SaveEntityFutureCallback extends DefaultFutureCallback<Long> {
	private final Entity entity;
	private final EntityCallback<Entity> callback;

	public SaveEntityFutureCallback(Entity entity, EntityCallback<Entity> callback) {
		this.entity = entity;
		this.callback = callback;
	}

	@Override
	public void onSuccess(Long dbId) throws Exception {
		entity.getDbModel().setDbId(dbId);
		if (callback != null) {
			callback.onResult(true, entity);
		}
	}

	@Override
	public void onException(Throwable cause) throws Exception {
		GuardianLogger.error(cause, "Catch exception on save entity: " + entity.getId());
		if (callback != null) {
			callback.onResult(false, null);
		}
	}

}