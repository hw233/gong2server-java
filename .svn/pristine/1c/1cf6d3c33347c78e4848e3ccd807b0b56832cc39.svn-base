package com.hadoit.game.engine.guardian.gas.entity;

import java.util.HashSet;
import java.util.Set;

import com.hadoit.game.engine.core.executor.TimerListener;
import com.hadoit.game.engine.core.rpc.simple.context.ServerChannelContext;
import com.hadoit.game.engine.guardian.core.Persistable;
import com.hadoit.game.engine.guardian.core.exception.GuardianServerException;
import com.hadoit.game.engine.guardian.gas.GasContext;
import com.hadoit.game.engine.guardian.utils.GuardianConstants;

/**
 * @author bezy 2013-11-21
 * 
 */
public abstract class Entity implements Timerable, TimerListener {
	private Persistable dbModel;

	/**
	 * ` context
	 */
	private transient GasContext context;

	/**
	 * channelContext
	 */
	private transient ServerChannelContext channelContext;

	/**
	 * 正在销毁
	 */
	private boolean destroying;

	/**
	 * 是否已销毁
	 */
	private boolean destroyed;

	/**
	 * 上次archive时间
	 */
	private long lastArchiveTime;

	/**
	 * timer set
	 */
	private final Set<Long> timerIdSet = new HashSet<Long>();

	public abstract String getId();

	public abstract String getEntityType();

	public Persistable getDbModel() {
		return dbModel;
	}

	public void setDbModel(Persistable dbModel) {
		this.dbModel = dbModel;
	}

	public long getDbId() {
		return getDbModel() != null ? getDbModel().getDbId() : 0;
	}

	public GasContext getContext() {
		return context;
	}

	void setContext(GasContext context) {
		this.context = context;
	}

	public ServerChannelContext getChannelContext() {
		return channelContext;
	}

	void setChannelContext(ServerChannelContext channelContext) {
		this.channelContext = channelContext;
	}

	public boolean isDestroying() {
		return destroying;
	}

	public boolean isDestroyed() {
		return destroyed;
	}

	long getLastArchiveTime() {
		return lastArchiveTime;
	}

	void setLastArchiveTime(long lastArchiveTime) {
		this.lastArchiveTime = lastArchiveTime;
	}

	boolean isPersistent() {
		return getDbId() > 0;
	}

	/**
	 * entity 初始化完毕，并且已经加入manager
	 */
	protected void onInit() {
	}

	/**
	 * entity开始write to db
	 */
	protected void onWriteToDB() {

	}

	/**
	 * entity开始destroy
	 */
	protected void onDestroy() {
	}

	void inited() {
		//
		onInit();
		//
		if (channelContext != null) {
			channelContext.putAttribute(GuardianConstants.ATTR_GAS_ENTITY_ID, getId());
		}
	}

	void destroyed() {
		if (channelContext != null) {
			channelContext.removeAttribute(GuardianConstants.ATTR_GAS_ENTITY_ID);
			channelContext = null;
		}
		clearAllTimers();
		destroyed = true;
	}

	void destroyFailed() {
		destroying = false;
	}

	public void writeToDB() {
		writeToDB(null);
	}

	/**
	 * 写入到数据库中
	 * 
	 * @param callback
	 */
	public void writeToDB(EntityCallback<Entity> callback) {
		if (isDestroying() || isDestroyed()) {
			if (callback != null) {
				callback.onResult(false, null);
			}
			return;
		}
		onWriteToDB();
		context.saveGameObject(getEntityType(), getDbModel(), new SaveEntityFutureCallback(this, callback));
	}

	/**
	 * 销毁entity getModel().getDbId()>0,则会回写到数据库
	 * 
	 */
	public void destroy() {
		destroy(null);
	}

	/**
	 * 销毁entity getModel().getDbId()>0,则会回写到数据库
	 * 
	 */
	public void destroy(EntityCallback<Entity> callback) {
		destroy(false, true, callback);
	}

	/**
	 * 销毁entity
	 * 
	 * @param deleteFromDB
	 *            是否要从数据库中删除该entity,只针对getModel().getDbId()>0
	 * @param writeToDB
	 *            是否要把该entity的信息写入到数据库中,只针对getModel().getDbId()>0
	 * @param callback
	 *            回调
	 */
	public void destroy(boolean deleteFromDB, boolean writeToDB, EntityCallback<Entity> callback) {
		if (isDestroying() || isDestroyed()) {
			if (callback != null) {
				callback.onResult(false, null);
			}
			return;
		}
		destroying = true;
		onDestroy();
		if (!isPersistent()) {
			context.getEntityManager().removeEntity(this);
			if (callback != null) {
				callback.onResult(true, this);
			}
			return;
		}
		if (deleteFromDB) {
			context.deleteGameObject(getEntityType(), getDbId(), new DestroyEntityOnDeleteFutureCallback<Entity>(
					context.getEntityManager(), this, callback));
		} else if (writeToDB) {
			context.saveGameObject(getEntityType(), getDbModel(),
					new DestroyEntityOnSaveFutureCallback<Entity>(context.getEntityManager(), this, callback));
		}
	}

	/**
	 * 向对应的clientSession推送消息
	 * 
	 * @param op
	 * @param params
	 */
	public void notify(String op, Object... params) {
		if (isUnused()) {
			return;
		}
		if (channelContext == null) {
			throw new GuardianServerException("need set channelContext first!");
		}
		channelContext.notify(op, params);
	}

	public Entity findEntity(String entityId) {
		return context.getEntityManager().getEntity(entityId);
	}

	public void notifyEntity(String entityId, String op, Object... params) {
		Entity entity = findEntity(entityId);
		if (entity != null) {
			entity.notify(op, params);
		}
	}

	/**
	 * @param start
	 *            seconds
	 * @param interval
	 *            seconds
	 * @param params
	 * @return
	 */
	public long addTimer(float start, float interval, Object... params) {
		long timerId = context.addTimer(this, start, interval, params);
		timerIdSet.add(timerId);
		return timerId;
	}

	@Override
	public void onTimer(long id, Object[] params) throws Exception {
	}

	@Override
	public long addTimer(TimerListener listener, float start, float interval, Object... params) {
		long timerId = context.addTimer(listener, start, interval, params);
		timerIdSet.add(timerId);
		return timerId;
	}

	@Override
	public void clearTimer(long timerId) {
		context.clearTimer(timerId);
		timerIdSet.remove(timerId);
	}

	private void clearAllTimers() {
		for (long timerId : timerIdSet) {
			context.clearTimer(timerId);
		}
		timerIdSet.clear();
	}
	
	public boolean isUnused(){
		return isDestroying() || isDestroyed();
	}

}
