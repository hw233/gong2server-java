
package com.gamejelly.gong2.gas.entity.fight.action;

import com.gamejelly.gong2.utils.GongConstants;

public class RemoveBuffAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int buffId;

	public RemoveBuffAction(String objectId, int buffId) {
		super(objectId, GongConstants.FIGHT_ACTION_REMOVE_BUFF);
		this.buffId = buffId;
	}

	public int getBuffId() {
		return buffId;
	}

	public void setBuffId(int buffId) {
		this.buffId = buffId;
	}
}
