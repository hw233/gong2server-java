package com.hadoit.game.engine.guardian.dbs.proxy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang.ArrayUtils;

import com.hadoit.game.common.lang.ReflectionUtils;
import com.hadoit.game.engine.guardian.core.Persistable;
import com.hadoit.game.engine.guardian.dbs.base.RawCommandResult;

/**
 * 内存实现
 * 
 * @author bezy 2012-9-4
 * 
 */
public class MemoryDbsServerProxy implements DbsServerProxy {
	protected final AtomicLong idx = new AtomicLong(0);
	protected final Map<Long, Persistable> dataSource = new ConcurrentHashMap<Long, Persistable>();

	@Override
	public void onRegistered() {
	}

	@Override
	public void onUnregistered() {
	}

	@Override
	public void saveGameObject(String entityType, Persistable entityData) {
		if (entityData.getDbId() == 0) {
			entityData.setDbId(idx.incrementAndGet());
		}
		dataSource.put(entityData.getDbId(), entityData);
	}

	@Override
	public Persistable loadGameObject(String entityType, long dbId) {
		return dataSource.get(dbId);
	}

	@Override
	public boolean deleteGameObject(String entityType, long dbId) {
		return dataSource.remove(dbId) != null;
	}

	@Override
	public RawCommandResult executeRawCommand(String command, Object[] args) {
		if ("get".equalsIgnoreCase(command)) {
			String key = args[0] + "";
			Object[] values = ArrayUtils.remove(args, 0);
			List<Object> objs = new ArrayList<Object>();
			Object obj;
			if ("dbId".equals(key)) {
				for (Object value : values) {
					obj = dataSource.get(Long.parseLong(value + ""));
					if (obj != null) {
						objs.add(obj);
					}
				}
			} else {
				for (Object value : values) {
					objs.addAll(findObject(key, value));
				}
			}
			return RawCommandResult.newResult(objs);
		} else if ("delete".equalsIgnoreCase(command)) {
			String key = args[0] + "";
			Object[] values = ArrayUtils.remove(args, 0);
			int count = 0;
			if ("dbId".equals(key)) {
				for (Object value : values) {
					count += (dataSource.remove(Long.parseLong(value + "")) != null) ? 1 : 0;
				}
			} else {
				for (Object value : values) {
					count += deleteObject(key, value);
				}
			}
			return RawCommandResult.newUpdateResult(count);
		} else {
			throw new UnsupportedOperationException("unsupported command: " + command);
		}
	}

	private List<Object> findObject(String key, Object value) {
		List<Object> objs = new ArrayList<Object>();
		Collection<Persistable> ps = dataSource.values();
		Object propValue;
		try {
			for (Persistable p : ps) {
				if (value instanceof String) {
					propValue = ReflectionUtils.getBeanUtilsInstance().getProperty(p, key);
				} else {
					propValue = ReflectionUtils.getProperty(p, key);
				}
				if ((value == null && propValue == null) || (value != null && value.equals(propValue))) {
					objs.add(p);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("findObject exception for key: " + key + ", value: " + value, e);
		}
		return objs;
	}

	private int deleteObject(String key, Object value) {
		Collection<Persistable> ps = dataSource.values();
		Object propValue;
		int count = 0;
		try {
			for (Persistable p : ps) {
				if (value instanceof String) {
					propValue = ReflectionUtils.getBeanUtilsInstance().getProperty(p, key);
				} else {
					propValue = ReflectionUtils.getProperty(p, key);
				}
				if ((value == null && propValue == null) || (value != null && value.equals(propValue))) {
					if (dataSource.remove(p.getDbId()) != null) {
						count++;
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("deleteObject exception for key: " + key + ", value: " + value, e);
		}
		return count;
	}

}
