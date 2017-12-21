package com.gamejelly.gong2.gas.entity;

import com.gamejelly.gong2.config.data.MonsterData;
import com.gamejelly.gong2.config.data.MonsterPrototypeData;
import com.gamejelly.gong2.config.data.base.LMap;
import com.gamejelly.gong2.gas.entity.fight.BaseFightProperty;
import com.gamejelly.gong2.gas.entity.fight.Fightable;
import com.gamejelly.gong2.utils.FormulaHelper;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;

public class MonsterEntity extends BaseFightProperty implements Fightable {

	private String id;

	private int templateId;

	private int lv;

	@Override
	public void calcAll() {

		if (getTemplate() == null) {
			GuardianLogger.warn("MonsterData not exists on Monster calcAll! templateId=", getTemplateId());
			return;
		}

		this.reset();
		// 汇总运算
		FormulaHelper.calcFightObjectProps(this);

		GuardianLogger.info(this.toString());
	}

	@Override
	public int getTemplateId() {
		return templateId;
	}

	public int getAvatarLv() {
		return lv;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int getLv() {
		return lv;
	}

	public void setLv(int lv) {
		this.lv = lv;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public LMap getTemplate() {
		return MonsterData.data.getMap(templateId);
	}

	public String getName() {
		LMap temp = getTemplate();
		// int proId = temp.getInt("protId", 0);
		// LMap tempPro = MonsterPrototypeData.data.getMap(proId);
		return temp.getString("name", "");
	}

	public LMap getPrototypeTemplate() {
		return MonsterPrototypeData.data.getMap(getTemplate().getInt("protId"));
	}

	public int getBasAtk() {
		return getPrototypeTemplate().getInt("basAtk", 0);
	}

	public float getBasPreAtk() {
		return getPrototypeTemplate().getFloat("basPreAtk", 0f);
	}

	public int getBasDef() {
		return getPrototypeTemplate().getInt("basDef", 0);
	}

	public float getBasPreDef() {
		return getPrototypeTemplate().getFloat("basPreDef", 0f);
	}

	public int getBasDpower() {
		return getPrototypeTemplate().getInt("basDpower", 0);
	}

	public float getBasPreDpower() {
		return getPrototypeTemplate().getFloat("basPreDpower", 0f);
	}

	public int getBasHp() {
		return getPrototypeTemplate().getInt("basHp", 0);
	}

	public float getBasPreHp() {
		return getPrototypeTemplate().getFloat("basPreHp", 0f);
	}

	public int getBasCp() {
		return getPrototypeTemplate().getInt("basCp", 0);
	}

	public float getBasCri() {
		return getPrototypeTemplate().getFloat("basCri", 0f);
	}

	public float getBasDcri() {
		return getPrototypeTemplate().getFloat("basDcri", 0f);
	}

	public int getBasFocus() {
		return getPrototypeTemplate().getInt("basFocus", 0);
	}

	public float getBasDparry() {
		return getPrototypeTemplate().getFloat("basDparry", 0f);
	}

	public float getBasParry() {
		return getPrototypeTemplate().getFloat("basParry", 0f);
	}

	public int getBasTen() {
		return getPrototypeTemplate().getInt("basTen", 0);
	}

	public int getBasPen() {
		return getPrototypeTemplate().getInt("basPen", 0);
	}

	public int getBasDeHIT() {
		return getPrototypeTemplate().getInt("basDeHIT", 0);
	}

	public int getBasEahit() {
		return getPrototypeTemplate().getInt("basEahit", 0);
	}

	public float getBasPreDeepen() {
		return getPrototypeTemplate().getFloat("basPreDeepen", 0f);
	}

	public float getBasPreEase() {
		return getPrototypeTemplate().getFloat("basPreEase", 0f);
	}

	public float getAtkPat() {
		return getTemplate().getFloat("atkPat", 1f);
	}

	public float getDefPat() {
		return getTemplate().getFloat("defPat", 1f);
	}

	public float getHpPat() {
		return getTemplate().getFloat("hpPat", 1f);
	}

	public float getSpdPat() {
		return getTemplate().getFloat("spdPat", 1f);
	}

	public float getPowerPat() {
		return getTemplate().getFloat("powerPat", 1f);
	}

	public float getDpowerPat() {
		return getTemplate().getFloat("dpowerPat", 1f);
	}

	public float getMpPat() {
		return getTemplate().getFloat("mpPat", 1f);
	}

	public float getHitPat() {
		return getTemplate().getFloat("hitPat", 1f);
	}

	public float getDgePat() {
		return getTemplate().getFloat("dgePat", 1f);
	}

	public float getCriPat() {
		return getTemplate().getFloat("criPat", 1f);
	}

	public float getDcriPat() {
		return getTemplate().getFloat("dcriPat", 1f);
	}

	public float getFocusPat() {
		return getTemplate().getFloat("focusPat", 1f);
	}

	public float getDatkPat() {
		return getTemplate().getFloat("datkPat", 1f);
	}

	public int getHzHpLv() {
		return getTemplate().getInt("hzHpLv", 0);
	}

	public int getHzMpLv() {
		return getTemplate().getInt("hzMpLv", 0);
	}

	public int getHzAtkLv() {
		return getTemplate().getInt("hzAtkLv", 0);
	}

	public int getHzDefLv() {
		return getTemplate().getInt("hzDefLv", 0);
	}

	public int getHzDpowerLv() {
		return getTemplate().getInt("hzDpowerLv", 0);
	}

	public int getHzSpdLv() {
		return getTemplate().getInt("hzSpdLv", 0);
	}

	public int getHzPowerLv() {
		return getTemplate().getInt("hzPowerLv", 0);
	}

	public float getAtkLv() {
		return getTemplate().getFloat("atkLv", 0f);
	}

	public float getDefLv() {
		return getTemplate().getFloat("defLv", 0f);
	}

	public float getDpowerLv() {
		return getTemplate().getFloat("dpowerLv", 0f);
	}

	public float getHpLv() {
		return getTemplate().getFloat("hpLv", 0f);
	}

	public int getPropType() {
		return getTemplate().getInt("propType", 0);
	}

	@Override
	public String toString() {
		return "MonsterEntity [id=" + id + ", templateId=" + templateId + ", lv=" + lv + ", totAtk=" + totAtk
				+ ", totPreAtk=" + totPreAtk + ", totDef=" + totDef + ", totPreDef=" + totPreDef + ", totDpower="
				+ totDpower + ", totPreDpower=" + totPreDpower + ", totHp=" + totHp + ", totPreHp=" + totPreHp
				+ ", totCp=" + totCp + ", totCri=" + totCri + ", totDcri=" + totDcri + ", totFocus=" + totFocus
				+ ", totDparry=" + totDparry + ", totParry=" + totParry + ", totTen=" + totTen + ", totPen=" + totPen
				+ ", totDeHIT=" + totDeHIT + ", totEahit=" + totEahit + ", totPreDeepen=" + totPreDeepen
				+ ", totPreEase=" + totPreEase + ", criProb=" + criProb + ", parryProb=" + parryProb + ", finalAtk="
				+ finalAtk + ", finalDef=" + finalDef + ", finalDpower=" + finalDpower + ", finalHp=" + finalHp
				+ ", finalCp=" + finalCp + ", finalPen=" + finalPen + ", finalDeHIT=" + finalDeHIT + ", finalEahit="
				+ finalEahit + ", finalPreDeepen=" + finalPreDeepen + ", finalPreEase=" + finalPreEase + ", bufAtk="
				+ bufAtk + ", bufPreAtk=" + bufPreAtk + ", bufDef=" + bufDef + ", bufPreDef=" + bufPreDef
				+ ", bufDpower=" + bufDpower + ", bufPreDpower=" + bufPreDpower + ", bufHp=" + bufHp + ", bufPreHp="
				+ bufPreHp + ", bufCp=" + bufCp + ", bufCri=" + bufCri + ", bufDcri=" + bufDcri + ", bufFocus="
				+ bufFocus + ", bufDparry=" + bufDparry + ", bufParry=" + bufParry + ", bufTen=" + bufTen + ", bufPen="
				+ bufPen + ", bufDeHIT=" + bufDeHIT + ", bufEahit=" + bufEahit + ", bufPreDeepen=" + bufPreDeepen
				+ ", bufPreEase=" + bufPreEase + ", rankAtk=" + rankAtk + ", rankDef=" + rankDef + ", rankDpower="
				+ rankDpower + ", rankHp=" + rankHp + ", equAtk=" + equAtk + ", equDef=" + equDef + ", equDpower="
				+ equDpower + ", equHp=" + equHp + ", skillDamegeAdd=" + skillDamegeAdd + ", skillDamegeProb="
				+ skillDamegeProb + ", disAtk=" + disAtk + ", disDef=" + disDef + ", disDpower=" + disDpower
				+ ", disHp=" + disHp + ", disCri=" + disCri + ", disDcri=" + disDcri + ", disFocus=" + disFocus
				+ ", disDparry=" + disDparry + ", disParry=" + disParry + ", disTen=" + disTen + ", disPen=" + disPen
				+ ", disDeHIT=" + disDeHIT + ", disEahit=" + disEahit + ", disPreDeepen=" + disPreDeepen
				+ ", disPreEase=" + disPreEase + ", getTemplateId()=" + getTemplateId() + ", getAvatarLv()="
				+ getAvatarLv() + ", getId()=" + getId() + ", getLv()=" + getLv() + ", getTemplate()=" + getTemplate()
				+ ", getName()=" + getName() + ", getPrototypeTemplate()=" + getPrototypeTemplate() + ", getBasAtk()="
				+ getBasAtk() + ", getBasPreAtk()=" + getBasPreAtk() + ", getBasDef()=" + getBasDef()
				+ ", getBasPreDef()=" + getBasPreDef() + ", getBasDpower()=" + getBasDpower() + ", getBasPreDpower()="
				+ getBasPreDpower() + ", getBasHp()=" + getBasHp() + ", getBasPreHp()=" + getBasPreHp()
				+ ", getBasCp()=" + getBasCp() + ", getBasCri()=" + getBasCri() + ", getBasDcri()=" + getBasDcri()
				+ ", getBasFocus()=" + getBasFocus() + ", getBasDparry()=" + getBasDparry() + ", getBasParry()="
				+ getBasParry() + ", getBasTen()=" + getBasTen() + ", getBasPen()=" + getBasPen() + ", getBasDeHIT()="
				+ getBasDeHIT() + ", getBasEahit()=" + getBasEahit() + ", getBasPreDeepen()=" + getBasPreDeepen()
				+ ", getBasPreEase()=" + getBasPreEase() + ", getAtkPat()=" + getAtkPat() + ", getDefPat()="
				+ getDefPat() + ", getHpPat()=" + getHpPat() + ", getSpdPat()=" + getSpdPat() + ", getPowerPat()="
				+ getPowerPat() + ", getDpowerPat()=" + getDpowerPat() + ", getMpPat()=" + getMpPat() + ", getHitPat()="
				+ getHitPat() + ", getDgePat()=" + getDgePat() + ", getCriPat()=" + getCriPat() + ", getDcriPat()="
				+ getDcriPat() + ", getFocusPat()=" + getFocusPat() + ", getDatkPat()=" + getDatkPat()
				+ ", getHzHpLv()=" + getHzHpLv() + ", getHzMpLv()=" + getHzMpLv() + ", getHzAtkLv()=" + getHzAtkLv()
				+ ", getHzDefLv()=" + getHzDefLv() + ", getHzDpowerLv()=" + getHzDpowerLv() + ", getHzSpdLv()="
				+ getHzSpdLv() + ", getHzPowerLv()=" + getHzPowerLv() + ", getAtkLv()=" + getAtkLv() + ", getDefLv()="
				+ getDefLv() + ", getDpowerLv()=" + getDpowerLv() + ", getHpLv()=" + getHpLv() + ", getPropType()="
				+ getPropType() + "]";
	}
}
