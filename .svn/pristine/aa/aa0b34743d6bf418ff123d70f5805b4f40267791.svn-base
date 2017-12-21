package com.gamejelly.gong2.gas.entity.fight;

import com.gamejelly.gong2.config.data.BuffData;
import com.gamejelly.gong2.config.data.base.LMap;

public class FightBuff {

	private String ownerId;

	private int templateId;

	/**
	 * 是否生效
	 */
	private boolean active;

	/**
	 * 已经持续的回合
	 */
	private int alreadyStanding;

	/**
	 * 释放BUFF者的ID
	 */
	private String casterId;

	/**
	 * 释放的Fabao
	 */
	private FightFabao casterFb;

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getAlreadyStanding() {
		return alreadyStanding;
	}

	public void setAlreadyStanding(int alreadyStanding) {
		this.alreadyStanding = alreadyStanding;
	}

	public String getCasterId() {
		return casterId;
	}

	public void setCasterId(String casterId) {
		this.casterId = casterId;
	}

	public FightFabao getCasterFb() {
		return casterFb;
	}

	public void setCasterFb(FightFabao casterFb) {
		this.casterFb = casterFb;
	}

	public LMap getTemplate() {
		return BuffData.data.getMap(templateId);
	}

	/**
	 * 是否每回合都要计算buff，一般用于回血、回魔等
	 * 
	 * @return
	 */
	public boolean isEveryRoundCalculate() {
		LMap template = getTemplate();
		return template.getBool("every", false);
	}

	public void incrAlreadyStanding() {
		this.alreadyStanding += 1;
	}

	public void decrAlreadyStanding() {
		this.alreadyStanding -= 1;
	}
}
