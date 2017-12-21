package com.gamejelly.gong2.gas.entity.fight.action;

import java.util.ArrayList;
import java.util.List;

import com.hadoit.game.engine.guardian.core.RemoteType;

public class BeAttackData implements RemoteType {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String beAttackId;
	private List<Integer> dmgVal = new ArrayList<Integer>();
	private boolean dead;

	// 0普攻 1暴击 2格挡 3治疗
	private List<Integer> attackState = new ArrayList<Integer>();

	private boolean defuse;

	// 闪避
	private boolean dodge;

	/**
	 * 反击数据
	 */
	private BeAttackData fanji;

	public BeAttackData() {

	}

	public BeAttackData(String beAttackId) {
		this.beAttackId = beAttackId;
	}

	public void addDmg(Integer val) {
		dmgVal.add(val);
	}

	public void addAttackState(Integer c) {
		attackState.add(c);
	}

	public String getBeAttackId() {
		return beAttackId;
	}

	public void setBeAttackId(String beAttackId) {
		this.beAttackId = beAttackId;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public boolean isDefuse() {
		return defuse;
	}

	public void setDefuse(boolean defuse) {
		this.defuse = defuse;
	}

	public boolean isDodge() {
		return dodge;
	}

	public void setDodge(boolean dodge) {
		this.dodge = dodge;
	}

	public BeAttackData getFanji() {
		return fanji;
	}

	public void setFanji(BeAttackData fanji) {
		this.fanji = fanji;
	}

}
