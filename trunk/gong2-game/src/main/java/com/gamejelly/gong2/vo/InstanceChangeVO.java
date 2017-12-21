package com.gamejelly.gong2.vo;

import java.util.Collection;

import com.hadoit.game.engine.guardian.core.RemoteType;

public class InstanceChangeVO implements RemoteType {
	private static final long serialVersionUID = 1L;

	private Collection<?> addList;
	private Collection<?> updateList;
	private Collection<?> deleteList;

	public InstanceChangeVO() {
	}

	public InstanceChangeVO(Collection<?> addList, Collection<?> updateList, Collection<?> deleteList) {
		this.addList = addList;
		this.updateList = updateList;
		this.deleteList = deleteList;
	}

	public Collection<?> getAddList() {
		return addList;
	}

	public void setAddList(Collection<?> addList) {
		this.addList = addList;
	}

	public Collection<?> getUpdateList() {
		return updateList;
	}

	public void setUpdateList(Collection<?> updateList) {
		this.updateList = updateList;
	}

	public Collection<?> getDeleteList() {
		return deleteList;
	}

	public void setDeleteList(Collection<?> deleteList) {
		this.deleteList = deleteList;
	}

}
