package com.gamejelly.gong2.gas.entity.fight.action;

import com.hadoit.game.engine.guardian.core.RemoteType;

public class BaseAction implements RemoteType {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 动作主体的标识
	 */
	private String objectId;

	private int actionType;

	public BaseAction(String objectId, int actionType) {
		this.objectId = objectId;
		this.actionType = actionType;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public int getActionType() {
		return actionType;
	}

	public void setActionType(int actionType) {
		this.actionType = actionType;
	}

}
