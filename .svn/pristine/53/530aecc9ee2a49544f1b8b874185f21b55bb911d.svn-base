package com.gamejelly.gong2.config.data.base;

import java.util.ArrayList;
import java.util.Collection;

import com.hadoit.game.common.lang.DataUtils;

/**
 * @author bezy 2013-11-29
 * 
 */
public class LList extends ArrayList<Object> {
	private static final long serialVersionUID = 1L;

	private boolean frozen;

	public boolean isFrozen() {
		return frozen;
	}

	public void setFrozen(boolean frozen) {
		this.frozen = frozen;
	}

	public LList() {
		super();
	}

	public LList(Collection<? extends Object> c) {
		super(c);
	}

	public LList(int initialCapacity) {
		super(initialCapacity);
	}

	@Override
	public Object set(int index, Object element) {
		if (frozen) {
			throw new UnsupportedOperationException("Attempt to modify read-only List");
		} else {
			return super.set(index, element);
		}
	}

	@Override
	public boolean add(Object e) {
		if (frozen) {
			throw new UnsupportedOperationException("Attempt to modify read-only List");
		} else {
			return super.add(e);
		}
	}

	@Override
	public void add(int index, Object element) {
		if (frozen) {
			throw new UnsupportedOperationException("Attempt to modify read-only List");
		} else {
			super.add(index, element);
		}
	}

	@Override
	public boolean addAll(Collection<? extends Object> c) {
		if (frozen) {
			throw new UnsupportedOperationException("Attempt to modify read-only List");
		} else {
			return super.addAll(c);
		}
	}

	@Override
	public boolean addAll(int index, Collection<? extends Object> c) {
		if (frozen) {
			throw new UnsupportedOperationException("Attempt to modify read-only List");
		} else {
			return super.addAll(index, c);
		}
	}

	@Override
	public Object remove(int index) {
		if (frozen) {
			throw new UnsupportedOperationException("Attempt to modify read-only List");
		} else {
			return super.remove(index);
		}
	}

	@Override
	public boolean remove(Object o) {
		if (frozen) {
			throw new UnsupportedOperationException("Attempt to modify read-only List");
		} else {
			return super.remove(o);
		}
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		if (frozen) {
			throw new UnsupportedOperationException("Attempt to modify read-only List");
		} else {
			return super.removeAll(c);
		}
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		if (frozen) {
			throw new UnsupportedOperationException("Attempt to modify read-only List");
		} else {
			return super.retainAll(c);
		}
	}

	@Override
	public void clear() {
		if (frozen) {
			throw new UnsupportedOperationException("Attempt to modify read-only List");
		} else {
			super.clear();
		}
	}

	public boolean isNull(int index) {
		return this.get(index) == null;
	}

	public Boolean getBool(int index, Boolean... dv) {
		if (index < 0 || index >= this.size()) {
			return DataUtils.toBoolean(null, dv);
		}
		Object v = this.get(index);
		return DataUtils.toBoolean(v, dv);
	}

	public Integer getInt(int index, Integer... dv) {
		if (index < 0 || index >= this.size()) {
			return DataUtils.toInteger(null, dv);
		}
		Object v = this.get(index);
		return DataUtils.toInteger(v, dv);
	}

	public Long getLong(int index, Long... dv) {
		if (index < 0 || index >= this.size()) {
			return DataUtils.toLong(null, dv);
		}
		Object v = this.get(index);
		return DataUtils.toLong(v, dv);
	}

	public Float getFloat(int index, Float... dv) {
		if (index < 0 || index >= this.size()) {
			return DataUtils.toFloat(null, dv);
		}
		Object v = this.get(index);
		return DataUtils.toFloat(v, dv);
	}

	public String getString(int index, String... dv) {
		if (index < 0 || index >= this.size()) {
			return DataUtils.toString(null, dv);
		}
		Object v = this.get(index);
		return DataUtils.toString(v, dv);
	}

	public LMap getMap(int key) {
		return (LMap) this.get(key);
	}

	public LMap getMap(int key, boolean createOnNull) {
		Object v = this.get(key);
		if (v == null && createOnNull) {
			return new LMap();
		}
		return (LMap) v;
	}

	public LList getList(int key) {
		return (LList) this.get(key);
	}

	public LList getList(int key, boolean createOnNull) {
		Object v = this.get(key);
		if (v == null && createOnNull) {
			return new LList();
		}
		return (LList) v;
	}

	public void list(Object... params) {
		for (Object param : params) {
			add(param);
		}
	}

	public Boolean[] toBoolArray() {
		Boolean[] array = new Boolean[this.size()];
		for (int i = 0; i < this.size(); ++i) {
			array[i] = getBool(i);
		}
		return array;
	}

	public Integer[] toIntArray() {
		Integer[] array = new Integer[this.size()];
		for (int i = 0; i < this.size(); ++i) {
			array[i] = getInt(i);
		}
		return array;
	}

	public Long[] toLongArray() {
		Long[] array = new Long[this.size()];
		for (int i = 0; i < this.size(); ++i) {
			array[i] = getLong(i);
		}
		return array;
	}

	public Float[] toFloatArray() {
		Float[] array = new Float[this.size()];
		for (int i = 0; i < this.size(); ++i) {
			array[i] = getFloat(i);
		}
		return array;
	}

	public String[] toStringArray() {
		String[] array = new String[this.size()];
		for (int i = 0; i < this.size(); ++i) {
			array[i] = getString(i);
		}
		return array;
	}
}
