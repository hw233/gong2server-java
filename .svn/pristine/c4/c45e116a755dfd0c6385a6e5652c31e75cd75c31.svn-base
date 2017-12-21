package com.gamejelly.gong2.meta.base;

import com.hadoit.game.common.framework.dao.annotation.Column;
import com.hadoit.game.engine.guardian.core.Persistable;

public abstract class SimpleModel implements Persistable {
	private static final long serialVersionUID = 1L;

	/**
	 * 数据库组建
	 */
	@Column(value = "id")
	private long dbId;

	@Override
	public long getDbId() {
		return dbId;
	}

	@Override
	public void setDbId(long dbId) {
		this.dbId = dbId;
	}

}
