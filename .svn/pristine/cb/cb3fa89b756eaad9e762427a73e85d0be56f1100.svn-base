package com.gamejelly.gong2.gas.entity.fight;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import com.gamejelly.gong2.config.data.FabaoData;
import com.gamejelly.gong2.config.data.SysConstData;
import com.gamejelly.gong2.config.data.base.LMap;
import com.gamejelly.gong2.gas.entity.ServantEntity;

public class Fighter {
	private String id;

	/**
	 * 玩家的仆从是true
	 */
	private boolean avatar;

	private int index;

	/**
	 * propType 属性 1 物理 2 法术
	 */
	private int propType;

	/**
	 * 排
	 */
	private int row;

	/**
	 * 列
	 */
	private int col;

	/**
	 * 主动,被动方
	 */
	private int side;

	/**
	 * finalHp 生命
	 */
	private long finalHp;
	private long initFinalHp;

	/**
	 * finalCp 怒气
	 */
	private long finalCp;
	private long initFinalCp;

	private int lv;

	private String name;

	private int sex;

	private List<Integer> dress;

	private int job;

	private Fightable fightable;

	/**
	 * 装备的法宝
	 */
	private List<FightFabao> fbList;

	/**
	 * 战斗内身上的buff
	 */
	private List<FightBuff> buffList = new ArrayList<FightBuff>();

	/**
	 * 需要用到icon列表
	 */
	private List<Integer> iconList = new ArrayList<Integer>();

	/**
	 * 被锁定的目标
	 */
	private boolean attackFixed;

	/**
	 * 携带的被动法宝
	 */
	private List<Integer> paFbList;

	/**
	 * 战力
	 */

	private int zhanLi;

	// 遗忘
	private boolean forget;

	/**
	 * 血量百分比
	 * 
	 * @return
	 */
	public float getFinalHpPer() {
		if (finalHp == 0) {
			return 0;
		}

		return finalHp * 1.0f / initFinalHp;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getSide() {
		return side;
	}

	public void setSide(int side) {
		this.side = side;
	}

	public Fightable getFightable() {
		return fightable;
	}

	public void setFightable(Fightable fightable) {
		this.fightable = fightable;
	}

	public List<Integer> getPaFbList() {
		return paFbList;
	}

	public void setPaFbList(List<Integer> paFbList) {
		this.paFbList = paFbList;
	}

	public boolean isAlive() {
		return getFinalHp() > 0;
	}

	public boolean isDead() {
		return !isAlive();
	}

	public int getGrow() {
		if (getFightable() instanceof ServantEntity) {
			return ((ServantEntity) getFightable()).getTemplate().getInt("grow", 0);
		} else {
			return 0;
		}
	}

	public List<Integer> getIconList() {
		return iconList;
	}

	public void setIconList(List<Integer> iconList) {
		this.iconList = iconList;
	}

	public void addIcon(int iconId) {
		this.iconList.add(iconId);
	}

	public boolean isAttackFixed() {
		return attackFixed;
	}

	public void setAttackFixed(boolean attackFixed) {
		this.attackFixed = attackFixed;
	}

	public int getLv() {
		return lv;
	}

	public void setLv(int lv) {
		this.lv = lv;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getJob() {
		return job;
	}

	public void setJob(int job) {
		this.job = job;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public List<Integer> getDress() {
		return dress;
	}

	public void setDress(List<Integer> dress) {
		this.dress = dress;
	}

	public boolean isAvatar() {
		return avatar;
	}

	public void setAvatar(boolean avatar) {
		this.avatar = avatar;
	}

	public List<FightFabao> getFbList() {

		return fbList;
	}

	public void setFbList(List<FightFabao> fbList) {

		this.fbList = fbList;
	}

	public boolean isSkip() {
		for (FightBuff fb : buffList) {
			int skip = fb.getTemplate().getInt("skip");
			if (skip > 0) {
				return true;
			}
		}
		return false;
	}

	public List<FightBuff> getBuffList() {

		return buffList;
	}

	public void setBuffList(List<FightBuff> buffList) {

		this.buffList = buffList;
	}

	public void removeFightBuff(FightBuff fightBuff) {
		buffList.remove(fightBuff);
	}

	/**
	 * 技能伤害加成
	 * 
	 * @param ff
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public float getFabaoTargetAddSkillDamage(FightFabao ff) {

		float addSkillDamage = 0.0f;
		return addSkillDamage;
	}

	public void incrFinalHp(long hp) {
		setFinalHp(getFinalHp() + hp);
	}

	public List<FightFabao> getAvailableFbList() {
		if (fbList == null) {
			return null;
		}
		List<FightFabao> ret = new ArrayList<FightFabao>();
		for (FightFabao ff : fbList) {

			LMap fabaoTemplate = FabaoData.data.getMap(ff.getTemplateId());
			if (fabaoTemplate == null) {
				continue;
			}

			// 检查怒气
			long curFinalCp = getFinalCp();
			if (curFinalCp < SysConstData.data.getInt("MAX_CP", 0)) {
				// 怒气值不足
				continue;
			}

			// 消耗怒气
			int consumeCp = fabaoTemplate.getInt("conCp", 0);
			if (consumeCp != 0) {
				ff.setConsumeFinalCp(consumeCp * -1);
			}

			// 增加怒气
			int addCp = fabaoTemplate.getInt("addCp", 0);
			if (addCp != 0) {
				ff.setAddFinalCp(addCp);
			}

			ret.add(ff);
		}
		return ret;
	}

	public boolean hasBuff(int templateId) {
		for (FightBuff fightBuff : buffList) {
			if (fightBuff.getTemplateId() == templateId) {
				return true;
			}
		}
		return false;
	}

	public List<FightBuff> getBuffLstByTemplateId(int tempId) {
		List<FightBuff> sel = new ArrayList<FightBuff>();
		for (FightBuff fightBuff : buffList) {
			if (fightBuff.getTemplateId() == tempId) {
				sel.add(fightBuff);
			}
		}

		return sel;
	}

	public int getZhanLi() {
		return zhanLi;
	}

	public void setZhanLi(int zhanLi) {
		this.zhanLi = zhanLi;
	}

	public long getInitFinalHp() {
		return initFinalHp;
	}

	public void setInitFinalHp(long initFinalHp) {
		this.initFinalHp = initFinalHp;
	}

	public long getFinalHp() {
		return finalHp;
	}

	public void setFinalHp(long finalHp) {
		this.finalHp = finalHp;
	}

	public int getPropType() {

		return propType;
	}

	public void setPropType(int propType) {

		this.propType = propType;
	}

	public long getFinalCp() {

		return finalCp;
	}

	public void setFinalCp(long finalCp) {

		this.finalCp = finalCp;
	}

	public long getInitFinalCp() {

		return initFinalCp;
	}

	public void setInitFinalCp(long initFinalCp) {

		this.initFinalCp = initFinalCp;
	}

	public boolean isForget() {

		return forget;
	}

	public void setForget(boolean forget) {

		this.forget = forget;
	}

	public void addFightBuff(final FightBuff fightBuff, FightContext fc) {
		FightBuff oldFb = (FightBuff) CollectionUtils.find(buffList, new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				return ((FightBuff) object).getTemplateId() == fightBuff.getTemplateId();
			}
		});
		if (oldFb != null) {
			fc.removeFightBuffDataOnly(oldFb);
		}

		if (fightBuff.getTemplate().getInt("effectTime") == 0) {
			// 立即生效的buff 都必须先减一回合
			fightBuff.decrAlreadyStanding();
			fc.activeFightBuff(fightBuff);
		}

		buffList.add(fightBuff);
	}

}
