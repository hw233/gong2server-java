package com.gamejelly.gong2.gas.entity.fight.action;

import java.util.List;

import com.gamejelly.gong2.utils.GongConstants;

public class AttackAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private int fbId;

	private List<BeAttackData> beAttackDataList;

	private List<AddBuffData> buffDataList;

	private long consumeFinalHp; // 生命

	private long consumeFinalCp; // 怒气

	private long addFinalCp;// 增加怒气

	public AttackAction(String objectId) {
		this(objectId, 0);
	}

	public AttackAction(String objectId, int fbId) {
		super(objectId, GongConstants.FIGHT_ACTION_ATTACK);
		this.fbId = fbId;
	}

	public int getFbId() {
		return fbId;
	}

	public void setFbId(int fbId) {
		this.fbId = fbId;
	}

	public long getConsumeFinalHp() {
		return consumeFinalHp;
	}

	public void setConsumeFinalHp(long consumeFinalHp) {
		this.consumeFinalHp = consumeFinalHp;
	}

	public long getConsumeFinalMp() {
		return consumeFinalHp;
	}

	public List<BeAttackData> getBeAttackDataList() {

		return beAttackDataList;
	}

	public void setBeAttackDataList(List<BeAttackData> beAttackDataList) {

		this.beAttackDataList = beAttackDataList;
	}

	public static long getSerialversionuid() {

		return serialVersionUID;
	}

	public long getAddFinalCp() {

		return addFinalCp;
	}

	public void setAddFinalCp(long addFinalCp) {

		this.addFinalCp = addFinalCp;
	}

	public long getConsumeFinalCp() {

		return consumeFinalCp;
	}

	public void setConsumeFinalCp(long consumeFinalCp) {

		this.consumeFinalCp = consumeFinalCp;
	}

	public List<AddBuffData> getBuffDataList() {
		return buffDataList;
	}

	public void setBuffDataList(List<AddBuffData> buffDataList) {
		this.buffDataList = buffDataList;
	}
}
