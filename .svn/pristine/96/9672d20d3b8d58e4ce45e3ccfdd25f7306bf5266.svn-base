package com.gamejelly.gong2.config.data.base;


import java.util.List;

/**
 * @author bezy 2013-11-29
 * 
 */
public abstract class LData {
	protected static LMap map(Object... params) {
		LMap map = new LMap();
		for (int i = 0; i < params.length; i += 2) {
			map.put(params[i], params[i + 1]);
		}
		map.setFrozen(true);
		return map;
	}

	protected static List<Object> list(Object... params) {
		LList list = new LList();
		for (Object param : params) {
			list.add(param);
		}
		list.setFrozen(true);
		return list;
	}

}
