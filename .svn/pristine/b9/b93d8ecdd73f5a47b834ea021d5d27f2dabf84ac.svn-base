package com.gamejelly.gong2.config.data.base;

import java.util.LinkedHashMap;
import java.util.Map;

import com.hadoit.game.common.lang.DataUtils;

/**
 * @author bezy 2013-11-29
 * 
 */
public class LMap extends LinkedHashMap<Object, Object> {
	private static final long serialVersionUID = 1L;

	private boolean frozen;

	public boolean isFrozen() {
		return frozen;
	}

	public void setFrozen(boolean frozen) {
		this.frozen = frozen;
	}

	public LMap() {
		super();
	}

	public LMap(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	public LMap(int initialCapacity) {
		super(initialCapacity);
	}

	public LMap(Map<? extends Object, ? extends Object> m) {
		super(m);
	}

	@Override
	public Object put(Object key, Object value) {
		if (frozen) {
			throw new UnsupportedOperationException("Attempt to modify read-only Map");
		} else {
			return super.put(key, value);
		}
	}

	@Override
	public Object remove(Object key) {
		if (frozen) {
			throw new UnsupportedOperationException("Attempt to modify read-only Map");
		} else {
			return super.remove(key);
		}
	}

	@Override
	public void putAll(Map<? extends Object, ? extends Object> m) {
		if (frozen) {
			throw new UnsupportedOperationException("Attempt to modify read-only Map");
		} else {
			super.putAll(m);
		}
	}

	@Override
	public void clear() {
		if (frozen) {
			throw new UnsupportedOperationException("Attempt to modify read-only Map");
		} else {
			super.clear();
		}
	}

	public boolean isNull(Object key) {
		return this.get(key) == null;
	}

	public Boolean getBool(Object key, Boolean... dv) {
		return DataUtils.getMapBoolean(this, key, dv);
	}

	public Integer getInt(Object key, Integer... dv) {
		return DataUtils.getMapInteger(this, key, dv);
	}

	public Long getLong(Object key, Long... dv) {
		return DataUtils.getMapLong(this, key, dv);
	}

	public Float getFloat(Object key, Float... dv) {
		return DataUtils.getMapFloat(this, key, dv);
	}

	public String getString(Object key, String... dv) {
		return DataUtils.getMapString(this, key, dv);
	}

	public LMap getMap(Object key) {
		return (LMap) this.get(key);
	}

	public LMap getMap(Object key, boolean createOnNull) {
		Object v = this.get(key);
		if (v == null && createOnNull) {
			return new LMap();
		}
		return (LMap) v;
	}

	public LList getList(Object key) {
		return (LList) this.get(key);
	}

	public LList getList(Object key, boolean createOnNull) {
		Object v = this.get(key);
		if (v == null && createOnNull) {
			return new LList();
		}
		return (LList) v;
	}

	public Integer getListInt(Object key, int idx, Integer... dv) {
		LList list = getList(key);
		Object v = null;
		if (list != null && idx >= 0 && idx < list.size()) {
			v = list.get(idx);
		}
		return DataUtils.toInteger(v, dv);
	}

	public Long getListLong(Object key, int idx, Long... dv) {
		LList list = getList(key);
		Object v = null;
		if (list != null && idx >= 0 && idx < list.size()) {
			v = list.get(idx);
		}
		return DataUtils.toLong(v, dv);
	}

	public Float getListFloat(Object key, int idx, Float... dv) {
		LList list = getList(key);
		Object v = null;
		if (list != null && idx >= 0 && idx < list.size()) {
			v = list.get(idx);
		}
		return DataUtils.toFloat(v, dv);
	}

	public String getListString(Object key, int idx, String... dv) {
		LList list = getList(key);
		Object v = null;
		if (list != null && idx >= 0 && idx < list.size()) {
			v = list.get(idx);
		}
		return DataUtils.toString(v, dv);
	}

	public void map(Object... params) {
		for (int i = 0; i < params.length; i += 2) {
			put(params[i], params[i + 1]);
		}
	}

	public LList valuesToList() {
		return new LList(this.values());
	}

}
