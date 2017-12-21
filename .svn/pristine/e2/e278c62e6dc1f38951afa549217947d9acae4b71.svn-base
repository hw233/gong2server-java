package com.gamejelly.gong2.meta.base;

import com.hadoit.game.common.framework.dao.annotation.Column;
import com.hadoit.game.common.framework.dao.domain.model.IBaseModel;
import com.hadoit.game.engine.guardian.core.Persistable;

/**
 * @author bezy 2013-12-4
 * 
 */

public abstract class BaseModel implements IBaseModel, Persistable {
	private static final long serialVersionUID = 1L;

	/**
	 * 数据库组建
	 */
	@Column(value = "id")
	private long dbId;

	@Override
	@Deprecated
	public long $$getId() {
		return getDbId();
	}

	@Override
	@Deprecated
	public void $$setId(long id) {
		setDbId(id);
	}

	@Override
	public long getDbId() {
		return dbId;
	}

	@Override
	public void setDbId(long dbId) {
		this.dbId = dbId;
	}

}
