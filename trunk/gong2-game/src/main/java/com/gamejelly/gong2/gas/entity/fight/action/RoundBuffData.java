
package com.gamejelly.gong2.gas.entity.fight.action;

import java.util.HashMap;
import java.util.Map;

import com.hadoit.game.engine.guardian.core.RemoteType;

public class RoundBuffData implements RemoteType {

	private static final long serialVersionUID = 1L;

	private String ownerId;

	private int buffId;

	private long buffValue;

	private boolean dead;

	/**
	 * 属性ID-效果值 表示这个buff下触发的技能效果
	 */
	private Map<Integer, Long> fbHurtMap;

	public RoundBuffData(String ownerId) {
		this.ownerId = ownerId;
	}

	public void addBuff(int buffId, long buffValue) {
		this.buffId = buffId;
		this.buffValue = buffValue;
	}

	public void addFbHurt(int hurtId, long value) {
		fbHurtMap = getFbHurtMap();
		if (fbHurtMap.containsKey(hurtId)) {
			fbHurtMap.put(hurtId, fbHurtMap.get(hurtId) + value);
		} else {
			fbHurtMap.put(hurtId, value);
		}
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public int getBuffId() {
		return buffId;
	}

	public void setBuffId(int buffId) {
		this.buffId = buffId;
	}

	public long getBuffValue() {
		return buffValue;
	}

	public void setBuffValue(long buffValue) {
		this.buffValue = buffValue;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public Map<Integer, Long> getFbHurtMap() {
		if (fbHurtMap == null) {
			fbHurtMap = new HashMap<Integer, Long>();
		}
		return fbHurtMap;
	}

	public void setFbHurtMap(Map<Integer, Long> fbHurtMap) {
		this.fbHurtMap = fbHurtMap;
	}
}