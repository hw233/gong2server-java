package com.gamejelly.gong2.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gamejelly.gong2.gas.entity.MonsterEntity;
import com.gamejelly.gong2.gas.entity.ServantEntity;
import com.gamejelly.gong2.gas.entity.fight.FightContext;
import com.gamejelly.gong2.gas.entity.fight.FightResult;
import com.gamejelly.gong2.gas.entity.fight.Fighter;
import com.gamejelly.gong2.gas.entity.fight.action.RoundActionData;
import com.gamejelly.gong2.utils.GongConstants;
import com.hadoit.game.common.lang.Triple;
import com.hadoit.game.engine.guardian.core.RemoteType;

public class FightResultVO implements RemoteType {
	private static final long serialVersionUID = 1L;

	private String avatarId;

	private int roundCount;

	private boolean win;

	private int guankaMonsterId;

	private long exp;

	private long money;

	private Map<Integer, Integer> currencys = new HashMap<Integer, Integer>();

	private int fightType;

	private int beginSubtitle;

	private int endSubtitle;

	private List<FighterVO> initiativeList;

	private List<FighterVO> passiveList;

	private List<FighterVO> npcList;

	private List<RoundActionData> roundActionDataList;

	private List<Triple<Integer, Integer, String>> allDrops;

	private Map<String, Integer> fightTalks;

	private List<FighterVO> initiativeZwList;

	private List<FighterVO> passiveZwList;

	public FightResultVO(FightContext fightContext, FightResult fr) {
		this.avatarId = fr.getAvatarId();
		this.roundCount = fightContext.getCurrRoundIdx();
		this.win = fightContext.isWin();
		this.guankaMonsterId = fr.getGuankaMonsterId();
		this.roundActionDataList = fightContext.getRoundActionDataList();
		this.exp = fr.getExp();
		this.money = fr.getMoney();
		this.currencys = fr.getCurrencys();

		this.fightType = fr.getFightType();
		this.beginSubtitle = fr.getBeginSubtitle();
		this.endSubtitle = fr.getEndSubtitle();

		this.initiativeList = convertToFighterVOList(fightContext.getPreInitiativeList());
		this.passiveList = convertToFighterVOList(fightContext.getPrePassiveList());
		this.allDrops = fr.getAllDrops();
		this.fightTalks = fightContext.getFightTalks();

	}

	private List<FighterVO> convertToFighterVOList(List<Fighter> fs) {
		if (fs == null) {
			return null;
		}
		List<FighterVO> ret = new ArrayList<FighterVO>();
		for (Fighter f : fs) {
			FighterVO fv = new FighterVO(f);
			if (f.getFightable() instanceof ServantEntity) {
				fv.setTemplateId(((ServantEntity) f.getFightable()).getTemplateId());
				fv.setName(((ServantEntity) f.getFightable()).getName());
				fv.setSex(f.getSex());
				fv.setDress(f.getDress());
				fv.setType(GongConstants.FIGHTER_TYPE_MENTU);

				if (((ServantEntity) f.getFightable()).getModel().getType() != GongConstants.SERVANT_TYPE_DEFAULT_0) {
					fv.setName(f.getName());
				}

			} else if (f.getFightable() instanceof MonsterEntity) {
				fv.setTemplateId(((MonsterEntity) f.getFightable()).getTemplateId());
				fv.setName(((MonsterEntity) f.getFightable()).getName());
				fv.setType(GongConstants.FIGHTER_TYPE_MONSTER);
			}

			ret.add(fv);
		}
		return ret;
	}

	public String getAvatarId() {
		return avatarId;
	}

	public void setAvatarId(String avatarId) {
		this.avatarId = avatarId;
	}

	public int getRoundCount() {
		return roundCount;
	}

	public void setRoundCount(int roundCount) {
		this.roundCount = roundCount;
	}

	public boolean isWin() {
		return win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}

	public List<FighterVO> getInitiativeList() {
		return initiativeList;
	}

	public void setInitiativeList(List<FighterVO> initiativeList) {
		this.initiativeList = initiativeList;
	}

	public List<FighterVO> getPassiveList() {
		return passiveList;
	}

	public void setPassiveList(List<FighterVO> passiveList) {
		this.passiveList = passiveList;
	}

	public List<RoundActionData> getRoundActionDataList() {
		return roundActionDataList;
	}

	public void setRoundActionDataList(List<RoundActionData> roundActionDataList) {
		this.roundActionDataList = roundActionDataList;
	}

	public int getGuankaMonsterId() {
		return guankaMonsterId;
	}

	public void setGuankaMonsterId(int guankaMonsterId) {
		this.guankaMonsterId = guankaMonsterId;
	}

	public long getMoney() {
		return money;
	}

	public void setMoney(long money) {
		this.money = money;
	}

	public int getFightType() {
		return fightType;
	}

	public void setFightType(int fightType) {
		this.fightType = fightType;
	}

	public List<Triple<Integer, Integer, String>> getAllDrops() {
		return allDrops;
	}

	public void setAllDrops(List<Triple<Integer, Integer, String>> allDrops) {
		this.allDrops = allDrops;
	}

	public Map<String, Integer> getFightTalks() {
		return fightTalks;
	}

	public void setFightTalks(Map<String, Integer> fightTalks) {
		this.fightTalks = fightTalks;
	}

	public int getBeginSubtitle() {
		return beginSubtitle;
	}

	public void setBeginSubtitle(int beginSubtitle) {
		this.beginSubtitle = beginSubtitle;
	}

	public int getEndSubtitle() {
		return endSubtitle;
	}

	public void setEndSubtitle(int endSubtitle) {
		this.endSubtitle = endSubtitle;
	}

	public List<FighterVO> getNpcList() {
		return npcList;
	}

	public void setNpcList(List<FighterVO> npcList) {
		this.npcList = npcList;
	}

	public long getExp() {
		return exp;
	}

	public void setExp(long exp) {
		this.exp = exp;
	}

	public Map<Integer, Integer> getCurrencys() {
		return currencys;
	}

	public void setCurrencys(Map<Integer, Integer> currencys) {
		this.currencys = currencys;
	}

	public List<FighterVO> getInitiativeZwList() {
		return initiativeZwList;
	}

	public void setInitiativeZwList(List<FighterVO> initiativeZwList) {
		this.initiativeZwList = initiativeZwList;
	}

	public List<FighterVO> getPassiveZwList() {
		return passiveZwList;
	}

	public void setPassiveZwList(List<FighterVO> passiveZwList) {
		this.passiveZwList = passiveZwList;
	}

}
