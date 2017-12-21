package com.hadoit.game.engine.guardian.gas.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang.time.DateUtils;

import com.hadoit.game.engine.core.rpc.simple.context.ServerChannelContext;
import com.hadoit.game.engine.guardian.core.Persistable;
import com.hadoit.game.engine.guardian.core.exception.GuardianServerException;
import com.hadoit.game.engine.guardian.gas.GasManager;
import com.hadoit.game.engine.guardian.utils.GuardianConstants;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;

/**
 * @author bezy 2012-08-27
 * 
 */
public class EntityManager {
	private final AtomicBoolean disposed = new AtomicBoolean(false);
	private final Map<String, Entity> entityMap = new ConcurrentHashMap<String, Entity>();
	private final int archivePeriodInTicks;
	private final long archiveMaxTime;
	private int archiveIndex = 0;
	private final LinkedList<String> toArchiveEntityList = new LinkedList<String>();

	private final GasManager context;

	public EntityManager(GasManager context) {
		this.context = context;
		this.archivePeriodInTicks = Math.round(context.getArchivePeriod() * context.getServerFreq());
		this.archiveMaxTime = (long) (context.getArchivePeriod() * DateUtils.MILLIS_PER_SECOND);
	}

	void addEntity(Entity entity) {
		entity.setContext(context);
		entity.setLastArchiveTime(System.currentTimeMillis());
		entityMap.put(entity.getId(), entity);
		entity.inited();
	}

	void removeEntity(Entity entity) {
		entityMap.remove(entity.getId());
		entity.destroyed();
	}

	public static String getEntityId(ServerChannelContext channelContext) {
		return (String) channelContext.getAttribute(GuardianConstants.ATTR_GAS_ENTITY_ID);
	}

	public Entity getEntity(String entityId) {
		return entityMap.get(entityId);
	}

	public Entity getEntity(ServerChannelContext channelContext) {
		String entityId = getEntityId(channelContext);
		return entityId != null ? getEntity(entityId) : null;
	}

	public List<Entity> getAllEntities() {
		return new ArrayList<Entity>(entityMap.values());
	}

	public List<String> getAllEntityIds() {
		return new ArrayList<String>(entityMap.keySet());
	}

	private <T extends Entity> T instanceEntity(Class<T> entityClass) {
		try {
			return entityClass.newInstance();
		} catch (Exception e) {
			throw new GuardianServerException("Instance entity exception for type '" + entityClass.getSimpleName()
					+ "'", e);
		}
	}

	public <T extends Entity> void createEntityFromDb(Class<T> entityClass, Persistable dbModel,
			EntityCallback<T> callback) {
		createEntityFromDb(entityClass, dbModel, null, callback);
	}

	public <T extends Entity> void createEntityFromDb(Class<T> entityClass, Persistable dbModel,
			ServerChannelContext channelContext, EntityCallback<T> callback) {
		T entity = instanceEntity(entityClass);
		entity.setDbModel(dbModel);
		entity.setChannelContext(channelContext);
		context.saveGameObject(entity.getEntityType(), entity.getDbModel(), new CreateEntityOnSaveFutureCallback<T>(
				context.getEntityManager(), entity, callback));
	}

	public <T extends Entity> void loadEntityFromDb(Class<T> entityClass, long dbId, EntityCallback<T> callback) {
		loadEntityFromDb(entityClass, dbId, null, callback);
	}

	public <T extends Entity> void loadEntityFromDb(Class<T> entityClass, long dbId,
			ServerChannelContext channelContext, EntityCallback<T> callback) {
		T entity = instanceEntity(entityClass);
		entity.setChannelContext(channelContext);
		context.loadGameObject(entity.getEntityType(), dbId,
				new CreateEntityOnLoadFutureCallback<T>(context.getEntityManager(), entity, callback));
	}

	public void dispose(boolean awaitFinish) {
		if (disposed.getAndSet(true)) {
			return;
		}
		context.getMainExecutor().execute(new Runnable() {
			@Override
			public void run() {
				List<Entity> allEntities = getAllEntities();
				GuardianLogger.info("begin destroy entities size: " + allEntities.size());
				for (Entity entity : allEntities) {
					try {
						entity.destroy();
					} catch (Exception e) {
						GuardianLogger.error(e, "Catch exception on destroy entity: " + entity.getId());
					}
				}
				GuardianLogger.info("destroy entities done!");
			}
		});
		if (awaitFinish) {
			awaitFinish();
		}
		entityMap.clear();
	}

	private void awaitFinish() {
		int i = 0;
		int size;
		while (true) {
			size = entityMap.size();
			if (size == 0) {
				break;
			}
			GuardianLogger.info("entity manager remain entities " + size + " and await finish " + (i++) + "s... ");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				GuardianLogger.error(e, "entity manager interrupted!");
			}
		}
		GuardianLogger.info("entity manager finished!");
	}

	public void flushAll() {
		List<Entity> allEntities = getAllEntities();
		GuardianLogger.info("begin flushAll entities size: " + allEntities.size());
		long curTime = System.currentTimeMillis();
		for (Entity entity : allEntities) {
			if (!entity.isPersistent()) {
				continue;
			}
			try {
				entity.setLastArchiveTime(curTime);
				entity.writeToDB();
			} catch (Exception e) {
				GuardianLogger.error(e, "Catch exception on flush entity: " + entity.getId());
			}
		}
		GuardianLogger.info("flushAll entities done!");
	}

	public void archiveOnTick() {
		if (disposed.get()) {
			return;
		}
		if (archivePeriodInTicks <= 0) {
			return;
		}
		if (archiveIndex >= archivePeriodInTicks) {
			archiveIndex = 0;
			toArchiveEntityList.clear();
			for (Entity entity : entityMap.values()) {
				toArchiveEntityList.add(entity.getId());
			}
			Collections.shuffle(toArchiveEntityList);
		}
		int startIndex = toArchiveEntityList.size() * archiveIndex / archivePeriodInTicks;
		archiveIndex++;
		int endIndex = toArchiveEntityList.size() * archiveIndex / archivePeriodInTicks;
		if (startIndex == endIndex) {
			return;
		}
		// GuardianLogger.info("Archive on tick", toArchiveEntityList.size(),
		// "entities for", startIndex, endIndex);
		ListIterator<String> iter = toArchiveEntityList.listIterator(startIndex);
		String id;
		Entity entity;
		long curTime = System.currentTimeMillis();
		while (iter.hasNext() && startIndex++ < endIndex) {
			id = iter.next();
			entity = entityMap.get(id);
			if (entity == null || !entity.isPersistent()) {
				continue;
			}
			if (curTime - entity.getLastArchiveTime() >= archiveMaxTime) {
				try {
					entity.setLastArchiveTime(curTime);
					entity.writeToDB();
				} catch (Exception e) {
					GuardianLogger.error(e, "Catch exception on archiveOnTick for entity: " + entity.getId());
				}
			}
		}
	}

}
