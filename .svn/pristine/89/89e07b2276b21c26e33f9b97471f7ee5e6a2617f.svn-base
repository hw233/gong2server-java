package com.gamejelly.gong2.utils.gen;

import com.gamejelly.gong2.gas.entity.ServantEntity;
import com.gamejelly.gong2.gas.entity.MonsterEntity;
import com.gamejelly.gong2.gas.entity.fight.Fightable;
import com.gamejelly.gong2.utils.GongUtils;;

public class FightablePropHelper {

	public static Number getNumProp(Fightable o, String propName) {
		if (o instanceof ServantEntity) {
			Number r = getServantEntityProp((ServantEntity) o, propName);
			return r != null ? r : 0;
		}
		if (o instanceof MonsterEntity) {
			Number r = getMonsterEntityProp((MonsterEntity) o, propName);
			return r != null ? r : 0;
		}
		throw new UnsupportedOperationException("Unknown read Fightable bean " + o);
	}

	public static void setNumProp(Fightable o, String propName, Number v) {
		if (o instanceof ServantEntity) {
			setServantEntityProp((ServantEntity) o, propName, v);
			return;
		}
		if (o instanceof MonsterEntity) {
			setMonsterEntityProp((MonsterEntity) o, propName, v);
			return;
		}
		throw new UnsupportedOperationException("Unknown write Fightable bean " + o);
	}

	public static void incrNumProp(Fightable o, String propName, Number v) {
		float old = getNumProp(o, propName).floatValue();
		setNumProp(o, propName, old + v.floatValue());
	}

	public static void incrNumProp(Fightable o, String propName, Number v, Number maxV) {
		float old = getNumProp(o, propName).floatValue();
		float newV = old + v.floatValue();
		if (maxV != null) {
			newV = GongUtils.adjustNumberInRange(old + v.floatValue(), Float.MIN_VALUE, maxV.floatValue());
		}
		setNumProp(o, propName, newV);
	}

	private static Number getServantEntityProp(ServantEntity o, String propName) {
		if ("lv".equals(propName)) {
			return o.getLv();
		}
		if ("basAtk".equals(propName)) {
			return o.getBasAtk();
		}
		if ("basPreAtk".equals(propName)) {
			return o.getBasPreAtk();
		}
		if ("basDef".equals(propName)) {
			return o.getBasDef();
		}
		if ("basPreDef".equals(propName)) {
			return o.getBasPreDef();
		}
		if ("basDpower".equals(propName)) {
			return o.getBasDpower();
		}
		if ("basPreDpower".equals(propName)) {
			return o.getBasPreDpower();
		}
		if ("basHp".equals(propName)) {
			return o.getBasHp();
		}
		if ("basPreHp".equals(propName)) {
			return o.getBasPreHp();
		}
		if ("basCp".equals(propName)) {
			return o.getBasCp();
		}
		if ("basCri".equals(propName)) {
			return o.getBasCri();
		}
		if ("basDcri".equals(propName)) {
			return o.getBasDcri();
		}
		if ("basFocus".equals(propName)) {
			return o.getBasFocus();
		}
		if ("basDparry".equals(propName)) {
			return o.getBasDparry();
		}
		if ("basParry".equals(propName)) {
			return o.getBasParry();
		}
		if ("basTen".equals(propName)) {
			return o.getBasTen();
		}
		if ("basPen".equals(propName)) {
			return o.getBasPen();
		}
		if ("basDeHIT".equals(propName)) {
			return o.getBasDeHIT();
		}
		if ("basEahit".equals(propName)) {
			return o.getBasEahit();
		}
		if ("basPreDeepen".equals(propName)) {
			return o.getBasPreDeepen();
		}
		if ("basPreEase".equals(propName)) {
			return o.getBasPreEase();
		}
		if ("bufAtk".equals(propName)) {
			return o.getBufAtk();
		}
		if ("bufPreAtk".equals(propName)) {
			return o.getBufPreAtk();
		}
		if ("bufDef".equals(propName)) {
			return o.getBufDef();
		}
		if ("bufPreDef".equals(propName)) {
			return o.getBufPreDef();
		}
		if ("bufDpower".equals(propName)) {
			return o.getBufDpower();
		}
		if ("bufPreDpower".equals(propName)) {
			return o.getBufPreDpower();
		}
		if ("bufHp".equals(propName)) {
			return o.getBufHp();
		}
		if ("bufPreHp".equals(propName)) {
			return o.getBufPreHp();
		}
		if ("bufCp".equals(propName)) {
			return o.getBufCp();
		}
		if ("bufCri".equals(propName)) {
			return o.getBufCri();
		}
		if ("bufDcri".equals(propName)) {
			return o.getBufDcri();
		}
		if ("bufFocus".equals(propName)) {
			return o.getBufFocus();
		}
		if ("bufDparry".equals(propName)) {
			return o.getBufDparry();
		}
		if ("bufParry".equals(propName)) {
			return o.getBufParry();
		}
		if ("bufTen".equals(propName)) {
			return o.getBufTen();
		}
		if ("bufPen".equals(propName)) {
			return o.getBufPen();
		}
		if ("bufDeHIT".equals(propName)) {
			return o.getBufDeHIT();
		}
		if ("bufEahit".equals(propName)) {
			return o.getBufEahit();
		}
		if ("bufPreDeepen".equals(propName)) {
			return o.getBufPreDeepen();
		}
		if ("bufPreEase".equals(propName)) {
			return o.getBufPreEase();
		}
		if ("rankAtk".equals(propName)) {
			return o.getRankAtk();
		}
		if ("rankDef".equals(propName)) {
			return o.getRankDef();
		}
		if ("rankDpower".equals(propName)) {
			return o.getRankDpower();
		}
		if ("rankHp".equals(propName)) {
			return o.getRankHp();
		}
		if ("equAtk".equals(propName)) {
			return o.getEquAtk();
		}
		if ("equDef".equals(propName)) {
			return o.getEquDef();
		}
		if ("equDpower".equals(propName)) {
			return o.getEquDpower();
		}
		if ("equHp".equals(propName)) {
			return o.getEquHp();
		}
		if ("skillDamegeAdd".equals(propName)) {
			return o.getSkillDamegeAdd();
		}
		if ("skillDamegeProb".equals(propName)) {
			return o.getSkillDamegeProb();
		}
		if ("atkLv".equals(propName)) {
			return o.getAtkLv();
		}
		if ("defLv".equals(propName)) {
			return o.getDefLv();
		}
		if ("dpowerLv".equals(propName)) {
			return o.getDpowerLv();
		}
		if ("hpLv".equals(propName)) {
			return o.getHpLv();
		}
		if ("equPreAtk".equals(propName)) {
			return o.getEquPreAtk();
		}
		if ("equPreDef".equals(propName)) {
			return o.getEquPreDef();
		}
		if ("equPreDpower".equals(propName)) {
			return o.getEquPreDpower();
		}
		if ("equPreHp".equals(propName)) {
			return o.getEquPreHp();
		}
		if ("totAtk".equals(propName)) {
			return o.getTotAtk();
		}
		if ("totPreAtk".equals(propName)) {
			return o.getTotPreAtk();
		}
		if ("totDef".equals(propName)) {
			return o.getTotDef();
		}
		if ("totPreDef".equals(propName)) {
			return o.getTotPreDef();
		}
		if ("totDpower".equals(propName)) {
			return o.getTotDpower();
		}
		if ("totPreDpower".equals(propName)) {
			return o.getTotPreDpower();
		}
		if ("totHp".equals(propName)) {
			return o.getTotHp();
		}
		if ("totPreHp".equals(propName)) {
			return o.getTotPreHp();
		}
		if ("totCp".equals(propName)) {
			return o.getTotCp();
		}
		if ("totCri".equals(propName)) {
			return o.getTotCri();
		}
		if ("totDcri".equals(propName)) {
			return o.getTotDcri();
		}
		if ("totFocus".equals(propName)) {
			return o.getTotFocus();
		}
		if ("totDparry".equals(propName)) {
			return o.getTotDparry();
		}
		if ("totParry".equals(propName)) {
			return o.getTotParry();
		}
		if ("totTen".equals(propName)) {
			return o.getTotTen();
		}
		if ("totPen".equals(propName)) {
			return o.getTotPen();
		}
		if ("totDeHIT".equals(propName)) {
			return o.getTotDeHIT();
		}
		if ("totEahit".equals(propName)) {
			return o.getTotEahit();
		}
		if ("totPreDeepen".equals(propName)) {
			return o.getTotPreDeepen();
		}
		if ("totPreEase".equals(propName)) {
			return o.getTotPreEase();
		}
		if ("disAtk".equals(propName)) {
			return o.getDisAtk();
		}
		if ("disDef".equals(propName)) {
			return o.getDisDef();
		}
		if ("disDpower".equals(propName)) {
			return o.getDisDpower();
		}
		if ("disHp".equals(propName)) {
			return o.getDisHp();
		}
		if ("disCri".equals(propName)) {
			return o.getDisCri();
		}
		if ("disDcri".equals(propName)) {
			return o.getDisDcri();
		}
		if ("disFocus".equals(propName)) {
			return o.getDisFocus();
		}
		if ("disDparry".equals(propName)) {
			return o.getDisDparry();
		}
		if ("disParry".equals(propName)) {
			return o.getDisParry();
		}
		if ("disTen".equals(propName)) {
			return o.getDisTen();
		}
		if ("disPen".equals(propName)) {
			return o.getDisPen();
		}
		if ("disDeHIT".equals(propName)) {
			return o.getDisDeHIT();
		}
		if ("disEahit".equals(propName)) {
			return o.getDisEahit();
		}
		if ("disPreDeepen".equals(propName)) {
			return o.getDisPreDeepen();
		}
		if ("disPreEase".equals(propName)) {
			return o.getDisPreEase();
		}
		if ("criProb".equals(propName)) {
			return o.getCriProb();
		}
		if ("parryProb".equals(propName)) {
			return o.getParryProb();
		}
		if ("finalAtk".equals(propName)) {
			return o.getFinalAtk();
		}
		if ("finalDef".equals(propName)) {
			return o.getFinalDef();
		}
		if ("finalDpower".equals(propName)) {
			return o.getFinalDpower();
		}
		if ("finalHp".equals(propName)) {
			return o.getFinalHp();
		}
		if ("finalPen".equals(propName)) {
			return o.getFinalPen();
		}
		if ("finalDeHIT".equals(propName)) {
			return o.getFinalDeHIT();
		}
		if ("finalEahit".equals(propName)) {
			return o.getFinalEahit();
		}
		if ("finalPreDeepen".equals(propName)) {
			return o.getFinalPreDeepen();
		}
		if ("finalPreEase".equals(propName)) {
			return o.getFinalPreEase();
		}
		if ("finalCp".equals(propName)) {
			return o.getFinalCp();
		}
		throw new UnsupportedOperationException("Unknown read property " + propName + " in ServantEntity");
	}

	private static void setServantEntityProp(ServantEntity o, String propName, Number v) {
		if ("bufAtk".equals(propName)) {
			o.setBufAtk(v.floatValue());
			return;
		}
		if ("bufPreAtk".equals(propName)) {
			o.setBufPreAtk(v.floatValue());
			return;
		}
		if ("bufDef".equals(propName)) {
			o.setBufDef(v.floatValue());
			return;
		}
		if ("bufPreDef".equals(propName)) {
			o.setBufPreDef(v.floatValue());
			return;
		}
		if ("bufDpower".equals(propName)) {
			o.setBufDpower(v.floatValue());
			return;
		}
		if ("bufPreDpower".equals(propName)) {
			o.setBufPreDpower(v.floatValue());
			return;
		}
		if ("bufHp".equals(propName)) {
			o.setBufHp(v.floatValue());
			return;
		}
		if ("bufPreHp".equals(propName)) {
			o.setBufPreHp(v.floatValue());
			return;
		}
		if ("bufCp".equals(propName)) {
			o.setBufCp(v.floatValue());
			return;
		}
		if ("bufCri".equals(propName)) {
			o.setBufCri(v.floatValue());
			return;
		}
		if ("bufDcri".equals(propName)) {
			o.setBufDcri(v.floatValue());
			return;
		}
		if ("bufFocus".equals(propName)) {
			o.setBufFocus(v.floatValue());
			return;
		}
		if ("bufDparry".equals(propName)) {
			o.setBufDparry(v.floatValue());
			return;
		}
		if ("bufParry".equals(propName)) {
			o.setBufParry(v.floatValue());
			return;
		}
		if ("bufTen".equals(propName)) {
			o.setBufTen(v.floatValue());
			return;
		}
		if ("bufPen".equals(propName)) {
			o.setBufPen(v.floatValue());
			return;
		}
		if ("bufDeHIT".equals(propName)) {
			o.setBufDeHIT(v.floatValue());
			return;
		}
		if ("bufEahit".equals(propName)) {
			o.setBufEahit(v.floatValue());
			return;
		}
		if ("bufPreDeepen".equals(propName)) {
			o.setBufPreDeepen(v.floatValue());
			return;
		}
		if ("bufPreEase".equals(propName)) {
			o.setBufPreEase(v.floatValue());
			return;
		}
		if ("rankAtk".equals(propName)) {
			o.setRankAtk(v.floatValue());
			return;
		}
		if ("rankDef".equals(propName)) {
			o.setRankDef(v.floatValue());
			return;
		}
		if ("rankDpower".equals(propName)) {
			o.setRankDpower(v.floatValue());
			return;
		}
		if ("rankHp".equals(propName)) {
			o.setRankHp(v.floatValue());
			return;
		}
		if ("equAtk".equals(propName)) {
			o.setEquAtk(v.floatValue());
			return;
		}
		if ("equDef".equals(propName)) {
			o.setEquDef(v.floatValue());
			return;
		}
		if ("equDpower".equals(propName)) {
			o.setEquDpower(v.floatValue());
			return;
		}
		if ("equHp".equals(propName)) {
			o.setEquHp(v.floatValue());
			return;
		}
		if ("skillDamegeAdd".equals(propName)) {
			o.setSkillDamegeAdd(v.floatValue());
			return;
		}
		if ("skillDamegeProb".equals(propName)) {
			o.setSkillDamegeProb(v.floatValue());
			return;
		}
		if ("equPreAtk".equals(propName)) {
			o.setEquPreAtk(v.floatValue());
			return;
		}
		if ("equPreDef".equals(propName)) {
			o.setEquPreDef(v.floatValue());
			return;
		}
		if ("equPreDpower".equals(propName)) {
			o.setEquPreDpower(v.floatValue());
			return;
		}
		if ("equPreHp".equals(propName)) {
			o.setEquPreHp(v.floatValue());
			return;
		}
		if ("totAtk".equals(propName)) {
			o.setTotAtk(v.longValue());
			return;
		}
		if ("totPreAtk".equals(propName)) {
			o.setTotPreAtk(v.floatValue());
			return;
		}
		if ("totDef".equals(propName)) {
			o.setTotDef(v.longValue());
			return;
		}
		if ("totPreDef".equals(propName)) {
			o.setTotPreDef(v.floatValue());
			return;
		}
		if ("totDpower".equals(propName)) {
			o.setTotDpower(v.longValue());
			return;
		}
		if ("totPreDpower".equals(propName)) {
			o.setTotPreDpower(v.floatValue());
			return;
		}
		if ("totHp".equals(propName)) {
			o.setTotHp(v.longValue());
			return;
		}
		if ("totPreHp".equals(propName)) {
			o.setTotPreHp(v.floatValue());
			return;
		}
		if ("totCp".equals(propName)) {
			o.setTotCp(v.longValue());
			return;
		}
		if ("totCri".equals(propName)) {
			o.setTotCri(v.floatValue());
			return;
		}
		if ("totDcri".equals(propName)) {
			o.setTotDcri(v.floatValue());
			return;
		}
		if ("totFocus".equals(propName)) {
			o.setTotFocus(v.longValue());
			return;
		}
		if ("totDparry".equals(propName)) {
			o.setTotDparry(v.floatValue());
			return;
		}
		if ("totParry".equals(propName)) {
			o.setTotParry(v.floatValue());
			return;
		}
		if ("totTen".equals(propName)) {
			o.setTotTen(v.longValue());
			return;
		}
		if ("totPen".equals(propName)) {
			o.setTotPen(v.longValue());
			return;
		}
		if ("totDeHIT".equals(propName)) {
			o.setTotDeHIT(v.longValue());
			return;
		}
		if ("totEahit".equals(propName)) {
			o.setTotEahit(v.longValue());
			return;
		}
		if ("totPreDeepen".equals(propName)) {
			o.setTotPreDeepen(v.floatValue());
			return;
		}
		if ("totPreEase".equals(propName)) {
			o.setTotPreEase(v.floatValue());
			return;
		}
		if ("disAtk".equals(propName)) {
			o.setDisAtk(v.floatValue());
			return;
		}
		if ("disDef".equals(propName)) {
			o.setDisDef(v.floatValue());
			return;
		}
		if ("disDpower".equals(propName)) {
			o.setDisDpower(v.floatValue());
			return;
		}
		if ("disHp".equals(propName)) {
			o.setDisHp(v.floatValue());
			return;
		}
		if ("disCri".equals(propName)) {
			o.setDisCri(v.floatValue());
			return;
		}
		if ("disDcri".equals(propName)) {
			o.setDisDcri(v.floatValue());
			return;
		}
		if ("disFocus".equals(propName)) {
			o.setDisFocus(v.floatValue());
			return;
		}
		if ("disDparry".equals(propName)) {
			o.setDisDparry(v.floatValue());
			return;
		}
		if ("disParry".equals(propName)) {
			o.setDisParry(v.floatValue());
			return;
		}
		if ("disTen".equals(propName)) {
			o.setDisTen(v.floatValue());
			return;
		}
		if ("disPen".equals(propName)) {
			o.setDisPen(v.floatValue());
			return;
		}
		if ("disDeHIT".equals(propName)) {
			o.setDisDeHIT(v.floatValue());
			return;
		}
		if ("disEahit".equals(propName)) {
			o.setDisEahit(v.floatValue());
			return;
		}
		if ("disPreDeepen".equals(propName)) {
			o.setDisPreDeepen(v.floatValue());
			return;
		}
		if ("disPreEase".equals(propName)) {
			o.setDisPreEase(v.floatValue());
			return;
		}
		if ("criProb".equals(propName)) {
			o.setCriProb(v.longValue());
			return;
		}
		if ("parryProb".equals(propName)) {
			o.setParryProb(v.longValue());
			return;
		}
		if ("finalAtk".equals(propName)) {
			o.setFinalAtk(v.longValue());
			return;
		}
		if ("finalDef".equals(propName)) {
			o.setFinalDef(v.longValue());
			return;
		}
		if ("finalDpower".equals(propName)) {
			o.setFinalDpower(v.longValue());
			return;
		}
		if ("finalHp".equals(propName)) {
			o.setFinalHp(v.longValue());
			return;
		}
		if ("finalPen".equals(propName)) {
			o.setFinalPen(v.longValue());
			return;
		}
		if ("finalDeHIT".equals(propName)) {
			o.setFinalDeHIT(v.longValue());
			return;
		}
		if ("finalEahit".equals(propName)) {
			o.setFinalEahit(v.longValue());
			return;
		}
		if ("finalPreDeepen".equals(propName)) {
			o.setFinalPreDeepen(v.floatValue());
			return;
		}
		if ("finalPreEase".equals(propName)) {
			o.setFinalPreEase(v.floatValue());
			return;
		}
		if ("finalCp".equals(propName)) {
			o.setFinalCp(v.longValue());
			return;
		}
		throw new UnsupportedOperationException("Unknown write property " + propName + " in ServantEntity");
	}

	private static Number getMonsterEntityProp(MonsterEntity o, String propName) {
		if ("lv".equals(propName)) {
			return o.getLv();
		}
		if ("basAtk".equals(propName)) {
			return o.getBasAtk();
		}
		if ("basPreAtk".equals(propName)) {
			return o.getBasPreAtk();
		}
		if ("basDef".equals(propName)) {
			return o.getBasDef();
		}
		if ("basPreDef".equals(propName)) {
			return o.getBasPreDef();
		}
		if ("basDpower".equals(propName)) {
			return o.getBasDpower();
		}
		if ("basPreDpower".equals(propName)) {
			return o.getBasPreDpower();
		}
		if ("basHp".equals(propName)) {
			return o.getBasHp();
		}
		if ("basPreHp".equals(propName)) {
			return o.getBasPreHp();
		}
		if ("basCp".equals(propName)) {
			return o.getBasCp();
		}
		if ("basCri".equals(propName)) {
			return o.getBasCri();
		}
		if ("basDcri".equals(propName)) {
			return o.getBasDcri();
		}
		if ("basFocus".equals(propName)) {
			return o.getBasFocus();
		}
		if ("basDparry".equals(propName)) {
			return o.getBasDparry();
		}
		if ("basParry".equals(propName)) {
			return o.getBasParry();
		}
		if ("basTen".equals(propName)) {
			return o.getBasTen();
		}
		if ("basPen".equals(propName)) {
			return o.getBasPen();
		}
		if ("basDeHIT".equals(propName)) {
			return o.getBasDeHIT();
		}
		if ("basEahit".equals(propName)) {
			return o.getBasEahit();
		}
		if ("basPreDeepen".equals(propName)) {
			return o.getBasPreDeepen();
		}
		if ("basPreEase".equals(propName)) {
			return o.getBasPreEase();
		}
		if ("bufAtk".equals(propName)) {
			return o.getBufAtk();
		}
		if ("bufPreAtk".equals(propName)) {
			return o.getBufPreAtk();
		}
		if ("bufDef".equals(propName)) {
			return o.getBufDef();
		}
		if ("bufPreDef".equals(propName)) {
			return o.getBufPreDef();
		}
		if ("bufDpower".equals(propName)) {
			return o.getBufDpower();
		}
		if ("bufPreDpower".equals(propName)) {
			return o.getBufPreDpower();
		}
		if ("bufHp".equals(propName)) {
			return o.getBufHp();
		}
		if ("bufPreHp".equals(propName)) {
			return o.getBufPreHp();
		}
		if ("bufCp".equals(propName)) {
			return o.getBufCp();
		}
		if ("bufCri".equals(propName)) {
			return o.getBufCri();
		}
		if ("bufDcri".equals(propName)) {
			return o.getBufDcri();
		}
		if ("bufFocus".equals(propName)) {
			return o.getBufFocus();
		}
		if ("bufDparry".equals(propName)) {
			return o.getBufDparry();
		}
		if ("bufParry".equals(propName)) {
			return o.getBufParry();
		}
		if ("bufTen".equals(propName)) {
			return o.getBufTen();
		}
		if ("bufPen".equals(propName)) {
			return o.getBufPen();
		}
		if ("bufDeHIT".equals(propName)) {
			return o.getBufDeHIT();
		}
		if ("bufEahit".equals(propName)) {
			return o.getBufEahit();
		}
		if ("bufPreDeepen".equals(propName)) {
			return o.getBufPreDeepen();
		}
		if ("bufPreEase".equals(propName)) {
			return o.getBufPreEase();
		}
		if ("rankAtk".equals(propName)) {
			return o.getRankAtk();
		}
		if ("rankDef".equals(propName)) {
			return o.getRankDef();
		}
		if ("rankDpower".equals(propName)) {
			return o.getRankDpower();
		}
		if ("rankHp".equals(propName)) {
			return o.getRankHp();
		}
		if ("equAtk".equals(propName)) {
			return o.getEquAtk();
		}
		if ("equDef".equals(propName)) {
			return o.getEquDef();
		}
		if ("equDpower".equals(propName)) {
			return o.getEquDpower();
		}
		if ("equHp".equals(propName)) {
			return o.getEquHp();
		}
		if ("skillDamegeAdd".equals(propName)) {
			return o.getSkillDamegeAdd();
		}
		if ("skillDamegeProb".equals(propName)) {
			return o.getSkillDamegeProb();
		}
		if ("atkLv".equals(propName)) {
			return o.getAtkLv();
		}
		if ("defLv".equals(propName)) {
			return o.getDefLv();
		}
		if ("dpowerLv".equals(propName)) {
			return o.getDpowerLv();
		}
		if ("hpLv".equals(propName)) {
			return o.getHpLv();
		}
		if ("equPreAtk".equals(propName)) {
			return o.getEquPreAtk();
		}
		if ("equPreDef".equals(propName)) {
			return o.getEquPreDef();
		}
		if ("equPreDpower".equals(propName)) {
			return o.getEquPreDpower();
		}
		if ("equPreHp".equals(propName)) {
			return o.getEquPreHp();
		}
		if ("totAtk".equals(propName)) {
			return o.getTotAtk();
		}
		if ("totPreAtk".equals(propName)) {
			return o.getTotPreAtk();
		}
		if ("totDef".equals(propName)) {
			return o.getTotDef();
		}
		if ("totPreDef".equals(propName)) {
			return o.getTotPreDef();
		}
		if ("totDpower".equals(propName)) {
			return o.getTotDpower();
		}
		if ("totPreDpower".equals(propName)) {
			return o.getTotPreDpower();
		}
		if ("totHp".equals(propName)) {
			return o.getTotHp();
		}
		if ("totPreHp".equals(propName)) {
			return o.getTotPreHp();
		}
		if ("totCp".equals(propName)) {
			return o.getTotCp();
		}
		if ("totCri".equals(propName)) {
			return o.getTotCri();
		}
		if ("totDcri".equals(propName)) {
			return o.getTotDcri();
		}
		if ("totFocus".equals(propName)) {
			return o.getTotFocus();
		}
		if ("totDparry".equals(propName)) {
			return o.getTotDparry();
		}
		if ("totParry".equals(propName)) {
			return o.getTotParry();
		}
		if ("totTen".equals(propName)) {
			return o.getTotTen();
		}
		if ("totPen".equals(propName)) {
			return o.getTotPen();
		}
		if ("totDeHIT".equals(propName)) {
			return o.getTotDeHIT();
		}
		if ("totEahit".equals(propName)) {
			return o.getTotEahit();
		}
		if ("totPreDeepen".equals(propName)) {
			return o.getTotPreDeepen();
		}
		if ("totPreEase".equals(propName)) {
			return o.getTotPreEase();
		}
		if ("disAtk".equals(propName)) {
			return o.getDisAtk();
		}
		if ("disDef".equals(propName)) {
			return o.getDisDef();
		}
		if ("disDpower".equals(propName)) {
			return o.getDisDpower();
		}
		if ("disHp".equals(propName)) {
			return o.getDisHp();
		}
		if ("disCri".equals(propName)) {
			return o.getDisCri();
		}
		if ("disDcri".equals(propName)) {
			return o.getDisDcri();
		}
		if ("disFocus".equals(propName)) {
			return o.getDisFocus();
		}
		if ("disDparry".equals(propName)) {
			return o.getDisDparry();
		}
		if ("disParry".equals(propName)) {
			return o.getDisParry();
		}
		if ("disTen".equals(propName)) {
			return o.getDisTen();
		}
		if ("disPen".equals(propName)) {
			return o.getDisPen();
		}
		if ("disDeHIT".equals(propName)) {
			return o.getDisDeHIT();
		}
		if ("disEahit".equals(propName)) {
			return o.getDisEahit();
		}
		if ("disPreDeepen".equals(propName)) {
			return o.getDisPreDeepen();
		}
		if ("disPreEase".equals(propName)) {
			return o.getDisPreEase();
		}
		if ("criProb".equals(propName)) {
			return o.getCriProb();
		}
		if ("parryProb".equals(propName)) {
			return o.getParryProb();
		}
		if ("finalAtk".equals(propName)) {
			return o.getFinalAtk();
		}
		if ("finalDef".equals(propName)) {
			return o.getFinalDef();
		}
		if ("finalDpower".equals(propName)) {
			return o.getFinalDpower();
		}
		if ("finalHp".equals(propName)) {
			return o.getFinalHp();
		}
		if ("finalPen".equals(propName)) {
			return o.getFinalPen();
		}
		if ("finalDeHIT".equals(propName)) {
			return o.getFinalDeHIT();
		}
		if ("finalEahit".equals(propName)) {
			return o.getFinalEahit();
		}
		if ("finalPreDeepen".equals(propName)) {
			return o.getFinalPreDeepen();
		}
		if ("finalPreEase".equals(propName)) {
			return o.getFinalPreEase();
		}
		if ("finalCp".equals(propName)) {
			return o.getFinalCp();
		}
		throw new UnsupportedOperationException("Unknown read property " + propName + " in MonsterEntity");
	}

	private static void setMonsterEntityProp(MonsterEntity o, String propName, Number v) {
		if ("lv".equals(propName)) {
			o.setLv(v.intValue());
			return;
		}
		if ("bufAtk".equals(propName)) {
			o.setBufAtk(v.floatValue());
			return;
		}
		if ("bufPreAtk".equals(propName)) {
			o.setBufPreAtk(v.floatValue());
			return;
		}
		if ("bufDef".equals(propName)) {
			o.setBufDef(v.floatValue());
			return;
		}
		if ("bufPreDef".equals(propName)) {
			o.setBufPreDef(v.floatValue());
			return;
		}
		if ("bufDpower".equals(propName)) {
			o.setBufDpower(v.floatValue());
			return;
		}
		if ("bufPreDpower".equals(propName)) {
			o.setBufPreDpower(v.floatValue());
			return;
		}
		if ("bufHp".equals(propName)) {
			o.setBufHp(v.floatValue());
			return;
		}
		if ("bufPreHp".equals(propName)) {
			o.setBufPreHp(v.floatValue());
			return;
		}
		if ("bufCp".equals(propName)) {
			o.setBufCp(v.floatValue());
			return;
		}
		if ("bufCri".equals(propName)) {
			o.setBufCri(v.floatValue());
			return;
		}
		if ("bufDcri".equals(propName)) {
			o.setBufDcri(v.floatValue());
			return;
		}
		if ("bufFocus".equals(propName)) {
			o.setBufFocus(v.floatValue());
			return;
		}
		if ("bufDparry".equals(propName)) {
			o.setBufDparry(v.floatValue());
			return;
		}
		if ("bufParry".equals(propName)) {
			o.setBufParry(v.floatValue());
			return;
		}
		if ("bufTen".equals(propName)) {
			o.setBufTen(v.floatValue());
			return;
		}
		if ("bufPen".equals(propName)) {
			o.setBufPen(v.floatValue());
			return;
		}
		if ("bufDeHIT".equals(propName)) {
			o.setBufDeHIT(v.floatValue());
			return;
		}
		if ("bufEahit".equals(propName)) {
			o.setBufEahit(v.floatValue());
			return;
		}
		if ("bufPreDeepen".equals(propName)) {
			o.setBufPreDeepen(v.floatValue());
			return;
		}
		if ("bufPreEase".equals(propName)) {
			o.setBufPreEase(v.floatValue());
			return;
		}
		if ("rankAtk".equals(propName)) {
			o.setRankAtk(v.floatValue());
			return;
		}
		if ("rankDef".equals(propName)) {
			o.setRankDef(v.floatValue());
			return;
		}
		if ("rankDpower".equals(propName)) {
			o.setRankDpower(v.floatValue());
			return;
		}
		if ("rankHp".equals(propName)) {
			o.setRankHp(v.floatValue());
			return;
		}
		if ("equAtk".equals(propName)) {
			o.setEquAtk(v.floatValue());
			return;
		}
		if ("equDef".equals(propName)) {
			o.setEquDef(v.floatValue());
			return;
		}
		if ("equDpower".equals(propName)) {
			o.setEquDpower(v.floatValue());
			return;
		}
		if ("equHp".equals(propName)) {
			o.setEquHp(v.floatValue());
			return;
		}
		if ("skillDamegeAdd".equals(propName)) {
			o.setSkillDamegeAdd(v.floatValue());
			return;
		}
		if ("skillDamegeProb".equals(propName)) {
			o.setSkillDamegeProb(v.floatValue());
			return;
		}
		if ("equPreAtk".equals(propName)) {
			o.setEquPreAtk(v.floatValue());
			return;
		}
		if ("equPreDef".equals(propName)) {
			o.setEquPreDef(v.floatValue());
			return;
		}
		if ("equPreDpower".equals(propName)) {
			o.setEquPreDpower(v.floatValue());
			return;
		}
		if ("equPreHp".equals(propName)) {
			o.setEquPreHp(v.floatValue());
			return;
		}
		if ("totAtk".equals(propName)) {
			o.setTotAtk(v.longValue());
			return;
		}
		if ("totPreAtk".equals(propName)) {
			o.setTotPreAtk(v.floatValue());
			return;
		}
		if ("totDef".equals(propName)) {
			o.setTotDef(v.longValue());
			return;
		}
		if ("totPreDef".equals(propName)) {
			o.setTotPreDef(v.floatValue());
			return;
		}
		if ("totDpower".equals(propName)) {
			o.setTotDpower(v.longValue());
			return;
		}
		if ("totPreDpower".equals(propName)) {
			o.setTotPreDpower(v.floatValue());
			return;
		}
		if ("totHp".equals(propName)) {
			o.setTotHp(v.longValue());
			return;
		}
		if ("totPreHp".equals(propName)) {
			o.setTotPreHp(v.floatValue());
			return;
		}
		if ("totCp".equals(propName)) {
			o.setTotCp(v.longValue());
			return;
		}
		if ("totCri".equals(propName)) {
			o.setTotCri(v.floatValue());
			return;
		}
		if ("totDcri".equals(propName)) {
			o.setTotDcri(v.floatValue());
			return;
		}
		if ("totFocus".equals(propName)) {
			o.setTotFocus(v.longValue());
			return;
		}
		if ("totDparry".equals(propName)) {
			o.setTotDparry(v.floatValue());
			return;
		}
		if ("totParry".equals(propName)) {
			o.setTotParry(v.floatValue());
			return;
		}
		if ("totTen".equals(propName)) {
			o.setTotTen(v.longValue());
			return;
		}
		if ("totPen".equals(propName)) {
			o.setTotPen(v.longValue());
			return;
		}
		if ("totDeHIT".equals(propName)) {
			o.setTotDeHIT(v.longValue());
			return;
		}
		if ("totEahit".equals(propName)) {
			o.setTotEahit(v.longValue());
			return;
		}
		if ("totPreDeepen".equals(propName)) {
			o.setTotPreDeepen(v.floatValue());
			return;
		}
		if ("totPreEase".equals(propName)) {
			o.setTotPreEase(v.floatValue());
			return;
		}
		if ("disAtk".equals(propName)) {
			o.setDisAtk(v.floatValue());
			return;
		}
		if ("disDef".equals(propName)) {
			o.setDisDef(v.floatValue());
			return;
		}
		if ("disDpower".equals(propName)) {
			o.setDisDpower(v.floatValue());
			return;
		}
		if ("disHp".equals(propName)) {
			o.setDisHp(v.floatValue());
			return;
		}
		if ("disCri".equals(propName)) {
			o.setDisCri(v.floatValue());
			return;
		}
		if ("disDcri".equals(propName)) {
			o.setDisDcri(v.floatValue());
			return;
		}
		if ("disFocus".equals(propName)) {
			o.setDisFocus(v.floatValue());
			return;
		}
		if ("disDparry".equals(propName)) {
			o.setDisDparry(v.floatValue());
			return;
		}
		if ("disParry".equals(propName)) {
			o.setDisParry(v.floatValue());
			return;
		}
		if ("disTen".equals(propName)) {
			o.setDisTen(v.floatValue());
			return;
		}
		if ("disPen".equals(propName)) {
			o.setDisPen(v.floatValue());
			return;
		}
		if ("disDeHIT".equals(propName)) {
			o.setDisDeHIT(v.floatValue());
			return;
		}
		if ("disEahit".equals(propName)) {
			o.setDisEahit(v.floatValue());
			return;
		}
		if ("disPreDeepen".equals(propName)) {
			o.setDisPreDeepen(v.floatValue());
			return;
		}
		if ("disPreEase".equals(propName)) {
			o.setDisPreEase(v.floatValue());
			return;
		}
		if ("criProb".equals(propName)) {
			o.setCriProb(v.longValue());
			return;
		}
		if ("parryProb".equals(propName)) {
			o.setParryProb(v.longValue());
			return;
		}
		if ("finalAtk".equals(propName)) {
			o.setFinalAtk(v.longValue());
			return;
		}
		if ("finalDef".equals(propName)) {
			o.setFinalDef(v.longValue());
			return;
		}
		if ("finalDpower".equals(propName)) {
			o.setFinalDpower(v.longValue());
			return;
		}
		if ("finalHp".equals(propName)) {
			o.setFinalHp(v.longValue());
			return;
		}
		if ("finalPen".equals(propName)) {
			o.setFinalPen(v.longValue());
			return;
		}
		if ("finalDeHIT".equals(propName)) {
			o.setFinalDeHIT(v.longValue());
			return;
		}
		if ("finalEahit".equals(propName)) {
			o.setFinalEahit(v.longValue());
			return;
		}
		if ("finalPreDeepen".equals(propName)) {
			o.setFinalPreDeepen(v.floatValue());
			return;
		}
		if ("finalPreEase".equals(propName)) {
			o.setFinalPreEase(v.floatValue());
			return;
		}
		if ("finalCp".equals(propName)) {
			o.setFinalCp(v.longValue());
			return;
		}
		throw new UnsupportedOperationException("Unknown write property " + propName + " in MonsterEntity");
	}

}