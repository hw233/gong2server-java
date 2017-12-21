package com.gamejelly.gong2.meta.base;

import com.hadoit.game.common.framework.dao.annotation.Column;
import com.hadoit.game.common.framework.dao.domain.model.IChildModel;
import com.hadoit.game.engine.core.protocol.json.JsonExclude;
import com.hadoit.game.engine.guardian.core.Persistable;

/**
 * @author bezy 2013-12-4
 * 
 */

public abstract class ChildModel implements IChildModel, Persistable {
	private static final long serialVersionUID = 1L;

	@Column(value = "id")
	@JsonExclude("client")
	private long dbId;

	@Column(value = "parentId", parentId = true, index = true)
	@JsonExclude("client")
	private long parentId;

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
	@Deprecated
	public long $$getParentId() {
		return getParentId();
	}

	@Override
	@Deprecated
	public void $$setParentId(long parentId) {
		setParentId(parentId);
	}

	@Deprecated
	public long getDbId() {
		return dbId;
	}

	@Deprecated
	public void setDbId(long dbId) {
		this.dbId = dbId;
	}

	@Deprecated
	public long getParentId() {
		return parentId;
	}

	@Deprecated
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

}
