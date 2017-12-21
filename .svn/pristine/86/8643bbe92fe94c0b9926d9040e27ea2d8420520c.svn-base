package com.gamejelly.gong2.gas.entity.fight;

import java.util.List;

import com.gamejelly.gong2.config.data.FabaoData;
import com.gamejelly.gong2.config.data.base.LList;
import com.gamejelly.gong2.config.data.base.LMap;
import com.gamejelly.gong2.utils.GongUtils;

public class FightFabao {
	private int templateId;

	private int lv;

	/**
	 * 技能cd暂不需要，相关的代码先注释 如果需要技能cd，人死了cd没清，有bug放不出技能
	 */
	private int cd;

	private List<Integer> fengYinJobSort;

	// 生命
	private int consumeFinalHp;

	// 怒气
	private int consumeFinalCp;

	// 增加怒气
	private int addFinalCp;

	public FightFabao(int templateId, int lv) {
		this.templateId = templateId;
		this.lv = lv;
	}

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public int getLv() {
		return lv;
	}

	public void setLv(int lv) {
		this.lv = lv;
	}

	public int getCd() {
		return cd;
	}

	public void setCd(int cd) {
		this.cd = cd;
	}

	public void incrCd(int incr) {
		setCd(GongUtils.adjustNumberInRange(getCd() + incr, 0, Integer.MAX_VALUE));
	}

	public List<Integer> getFengYinJobSort() {
		return fengYinJobSort;
	}

	public void setFengYinJobSort(List<Integer> fengYinJobSort) {
		this.fengYinJobSort = fengYinJobSort;
	}

	public LMap getFbTemplate() {
		return FabaoData.data.getMap(templateId);
	}

	public int getTemplateRangeKind() {
		return getFbTemplate().getInt("rangeKind", 0);
	}

	public int getTemplateTgtNum() {
		return getFbTemplate().getInt("tgtNum", 0);
	}

	public LList getTemplateBufferIds() {
		return getFbTemplate().getList("buffId");
	}

	public float getTemplateBuffProb() {
		float v1 = getFbTemplate().getListFloat("buffProb", getLv() - 1, 0.0f);
		return v1;
	}

	public int getConsumeFinalHp() {

		return consumeFinalHp;
	}

	public void setConsumeFinalHp(int consumeFinalHp) {

		this.consumeFinalHp = consumeFinalHp;
	}

	public int getConsumeFinalCp() {

		return consumeFinalCp;
	}

	public void setConsumeFinalCp(int consumeFinalCp) {

		this.consumeFinalCp = consumeFinalCp;
	}

	public int getAddFinalCp() {

		return addFinalCp;
	}

	public void setAddFinalCp(int addFinalCp) {

		this.addFinalCp = addFinalCp;
	}

}
