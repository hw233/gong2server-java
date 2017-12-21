
package com.gamejelly.gong2.gas.service.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.gamejelly.gong2.config.data.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.gamejelly.gong2.config.data.base.LList;
import com.gamejelly.gong2.config.data.base.LMap;
import com.gamejelly.gong2.gas.entity.AvatarEntity;
import com.gamejelly.gong2.gas.entity.MonsterEntity;
import com.gamejelly.gong2.gas.entity.ServantEntity;
import com.gamejelly.gong2.gas.entity.fight.FightContext;
import com.gamejelly.gong2.gas.entity.fight.FightFabao;
import com.gamejelly.gong2.gas.entity.fight.Fightable;
import com.gamejelly.gong2.gas.entity.fight.Fighter;
import com.gamejelly.gong2.gas.entity.fight.action.AddBuffData;
import com.gamejelly.gong2.gas.entity.fight.action.AttackAction;
import com.gamejelly.gong2.gas.entity.fight.action.BeAttackData;
import com.gamejelly.gong2.gas.entity.fight.action.RoundActionData;
import com.gamejelly.gong2.gas.entity.fight.action.RoundBuffData;
import com.gamejelly.gong2.meta.ItemModel;
import com.gamejelly.gong2.utils.FormulaHelper;
import com.gamejelly.gong2.utils.GongConstants;
import com.gamejelly.gong2.utils.GongRuntimeException;
import com.gamejelly.gong2.utils.GongUtils;
import com.gamejelly.gong2.utils.gen.FightablePropHelper;
import com.hadoit.game.common.lang.Pair;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;

@Component("fightService")
public class FightService {

    @Autowired
    @Qualifier("monsterService")
    protected MonsterService monsterService;

    private List<FightFabao> convertMtAllFabao(List<ItemModel> imList) {
        if (CollectionUtils.isEmpty(imList)) {
            return Collections.emptyList();
        }
        List<FightFabao> ret = new ArrayList<FightFabao>();
        for (ItemModel im : imList) {
            ret.add(new FightFabao(im.getTemplateId(), im.getLv()));
        }
        return ret;
    }

    private List<Integer> convertFbTemplateIds(List<ItemModel> fbList) {
        if (CollectionUtils.isEmpty(fbList)) {
            return Collections.emptyList();
        }
        List<Integer> ret = new ArrayList<Integer>();
        for (ItemModel im : fbList) {
            ret.add(im.getTemplateId());
        }
        return ret;
    }

    private List<FightFabao> convertMonsterFabao(int monsterTemplateId) {
        LMap monsterTemplate = MonsterData.data.getMap(monsterTemplateId);
        LList fbIds = monsterTemplate.getList("fbId");
        if (CollectionUtils.isEmpty(fbIds)) {
            return Collections.emptyList();
        }
        List<FightFabao> ret = new ArrayList<FightFabao>();
        for (int i = 0; i < fbIds.size(); i++) {
            LMap fbTemplate = FabaoData.data.getMap(fbIds.getInt(i));
            if (fbTemplate == null) {
                GuardianLogger.error("FabaoData not exist! templateId=" + fbIds.getInt(i));
                continue;
            }

            if (fbTemplate.getInt("fbKind") == SysConstData.data.getInt("FB_KIND_1")
                    || fbTemplate.getInt("fbKind") == SysConstData.data.getInt("FB_KIND_3")) {
                ret.add(new FightFabao(fbIds.getInt(i), monsterTemplate.getList("fbLv").getInt(i)));
            }
        }
        return ret;
    }

    public Pair<List<Fighter>, Pair<Integer, Integer>> convertMentuToFighter(AvatarEntity avatarEntity, int side) {
        List<ServantEntity> lstSe = avatarEntity.getShangzhenServant();
        if (CollectionUtils.isEmpty(avatarEntity.getShangzhenServant())) {
            return null;
        }
        Collections.sort(lstSe, new Comparator<ServantEntity>() {
            @Override
            public int compare(ServantEntity o1, ServantEntity o2) {
                if (o1 == null || o2 == null) {
                    return 0;
                }
                return o1.getModel().getPos() - o2.getModel().getPos();
            }
        });
        List<Fighter> fighters = new ArrayList<Fighter>();
        for (ServantEntity se : lstSe) {

            Fighter f = new Fighter();
            f.setPropType(se.getPropType());
            f.setZhanLi(se.getZhanli());
            f.setFightable(se);
            f.setIndex(se.getModel().getPos());
            f.setRow(GongUtils.getRow(f.getIndex()));
            f.setCol(GongUtils.getCol(f.getIndex()));
            f.setId(se.getId());
            f.setSide(side);
            f.setFbList(convertMtAllFabao(avatarEntity.getInFbListFromOwner(f.getId())));
            f.setPaFbList(convertFbTemplateIds(avatarEntity.getPaFbListFromOwner(f.getId())));

            f.setFinalHp(se.getFinalHp());
            f.setInitFinalHp(se.getFinalHp());
            f.setFinalCp(se.getFinalCp());
            f.setInitFinalCp(se.getFinalCp());

            f.setLv(se.getLv());
            f.setName(se.getName());
            int job = se.getTemplate().getInt("job");
            f.setJob(job);
            f.setAvatar(se.getModel().isAvatar());
            if (se.getModel().isAvatar()) {
                f.setSex(se.getEntity().getAvatarModel().getSex());
                if (f.getSex() == GongConstants.SEX_MALE) {
                    f.setDress(se.getEntity().getAvatarModel().getDressOnMale());
                } else {
                    f.setDress(se.getEntity().getAvatarModel().getDressOnFemale());
                }
            }
            fighters.add(f);
        }

        int zhenFaTemplateID = 0;
        int zhenFaLv = 0;
        ItemModel zhenFa = avatarEntity.getUsedZhenfa();
        if (zhenFa != null) {
            zhenFaTemplateID = zhenFa.getTemplateId();
            zhenFaLv = zhenFa.getLv();
        }

        return Pair.newInstance(fighters, Pair.newInstance(zhenFaTemplateID, zhenFaLv));
    }

    private Pair<List<Fighter>, Pair<Integer, Integer>> convertMonsterToFighter(int monsterGroupTemplateId, int side,
                                                                                int outLv, boolean openingFight) {
        LMap mgTmpData = MonsterGroupData.data.getMap(monsterGroupTemplateId);
        LMap zfTmpData = ZhenfaData.data.getMap(mgTmpData.getInt("zfId"));
        LList openPosList = zfTmpData.getList("openPosition");
        LList monstTmpList = mgTmpData.getList("monsterList");
        LList propKindList = zfTmpData.getList("propKind");
        // LList propValueList =
        // zfTmpData.getList("propValue").getList(mgTmpData.getInt("zfLv", 1) -
        // 1);

        List<Fighter> fighters = new ArrayList<Fighter>();
        for (int i = 0, len = openPosList.size(); i < len; i++) {
            int monstTmpId = monstTmpList.getInt(i);
            if (monstTmpId > 0) {
                Fighter f = convertSingleMonsterToFighter(monstTmpId, openPosList.getInt(i), side, outLv, openingFight);
                int row = GongUtils.getRow(openPosList.getInt(i));
                if (CollectionUtils.isNotEmpty(propKindList)) {
                    // 阵法加成
                    // FightablePropHelper.incrNumProp(f.getFightable(),
                    // PropData.data.getMap(propKindList.getInt(row -
                    // 1)).getString("funName"),
                    // propValueList.getFloat(row - 1));
                }


                f.getFightable().calcAll();
                fighters.add(f);
            }
        }

        return Pair.newInstance(fighters, Pair.newInstance(mgTmpData.getInt("zfId"), mgTmpData.getInt("zfLv", 1)));
    }

    private Fighter convertSingleMonsterToFighter(int monsterTemplateId, int zfPos, int side, int outLv,
                                                  boolean openingFight) {
        MonsterEntity m = monsterService.createMonster(monsterTemplateId, outLv);
        int row = GongUtils.getRow(zfPos);
        Fighter f = new Fighter();
        f.setPropType(m.getPropType());
        f.setFightable(m);
        f.setIndex(zfPos);
        f.setRow(row);
        f.setCol(GongUtils.getCol(zfPos));
        f.setId(m.getId());
        f.setSide(side);
        f.setFinalHp(m.getFinalHp());
        f.setInitFinalHp(m.getFinalHp());
        f.setFinalCp(m.getFinalCp());
        f.setInitFinalCp(m.getFinalCp());
        f.setFbList(convertMonsterFabao(monsterTemplateId));
        f.setName(m.getName());
        f.setLv(m.getLv());

        return f;
    }

    private long getAllFighterTotalZhanLi(List<Fighter> fighterList) {
        long ret = 0;
        for (Fighter figther : fighterList) {
            ret += figther.getZhanLi();
        }
        return ret;
    }

    private FightContext createFightContext(Pair<List<Fighter>, Pair<Integer, Integer>> inFighters,
                                            Pair<List<Fighter>, Pair<Integer, Integer>> paFighters) {
        FightContext fightContext = new FightContext(inFighters.getFirst(), paFighters.getFirst());
        fightContext.setInZhenfa(inFighters.getSecond());
        fightContext.setPaZhenfa(paFighters.getSecond());
        fightContext.setInitiativeTotalZhanLi(getAllFighterTotalZhanLi(inFighters.getFirst()));
        fightContext.setPassiveTotalZhanLi(getAllFighterTotalZhanLi(paFighters.getFirst()));

        return fightContext;
    }

    public FightContext fightMonster(AvatarEntity avatarEntity, int guankaMonsterId, int fightType) {

        LMap gkMonster = GuankaMonsterData.data.getMap(guankaMonsterId);
        int monsterGroupTemplateId = gkMonster.getInt("monsterGroupId");

        Pair<List<Fighter>, Pair<Integer, Integer>> mtFighters = convertMentuToFighter(avatarEntity,
                GongConstants.SIDE_INITIATIVE);
        Pair<List<Fighter>, Pair<Integer, Integer>> monsterFighters = convertMonsterToFighter(monsterGroupTemplateId,
                GongConstants.SIDE_PASSIVE, avatarEntity.getAvatarModel().getLv(), false);
        FightContext fc = createFightContext(mtFighters, monsterFighters);

        doFight(fc, false, GongConstants.FIGHT_MAX_ROUND);

        if (avatarEntity.isWudi()) {
            fc.setWin(true);
        }
        return fc;
    }

    private void handleBuff(FightFabao selectedFb, AttackAction attackAction, Fighter roundFighter,
                            List<Fighter> beHurtList, FightContext fightContext) {
        // 添加buff
        if (CollectionUtils.isNotEmpty(selectedFb.getTemplateBufferIds())) {
            int buffIdx = GongUtils.randomProb(selectedFb.getFbTemplate().getList("prop").toFloatArray());
            int buffTemplateId = selectedFb.getTemplateBufferIds().getInt(buffIdx);
            if (buffTemplateId > 0) {
                LMap buffTemplate = BuffData.data.getMap(buffTemplateId);
                int buffEffect = selectedFb.getFbTemplate().getInt("buffEffect");
                float buffProb = selectedFb.getTemplateBuffProb();
                LList buffNumRange = selectedFb.getFbTemplate().getList("buffNumRange");
                List<Fighter> buffSelectList = new ArrayList<Fighter>();
                if (buffEffect == SysConstData.data.getInt("BUFF_TGT_1")) {// 自身
                    buffSelectList.add(roundFighter);
                } else if (buffEffect == SysConstData.data.getInt("BUFF_TGT_2")) { // 己方全体
                    buffSelectList.addAll(fightContext.getOwnerList(roundFighter.getSide()));
                } else if (buffEffect == SysConstData.data.getInt("BUFF_TGT_3")) { // 受击方
                    buffSelectList.addAll(beHurtList);
                } else if (buffEffect == SysConstData.data.getInt("BUFF_TGT_4")) {// 敌方全体
                    buffSelectList.addAll(fightContext.getOppositeList(roundFighter.getSide()));
                } else if (buffEffect == SysConstData.data.getInt("BUFF_TGT_9")) {// 随机敌方
                    buffSelectList.addAll(GongUtils
                            .getRandomsFromList(fightContext.getOppositeList(roundFighter.getSide()), 1, true));
                }
                List<AddBuffData> buffDataList = new ArrayList<AddBuffData>();
                if (CollectionUtils.isNotEmpty(buffSelectList)) {
                    if (attackAction == null) {
                        attackAction = new AttackAction(roundFighter.getId(), selectedFb.getTemplateId());
                    }
                    attackAction.setBuffDataList(buffDataList);
                }
                List<Fighter> buffAddableFighter = getAddBuffFighter(buffSelectList, buffTemplate);
                List<Fighter> addBuffFighter = getAddBuffFighterByProb(buffAddableFighter, buffProb);
                if (buffNumRange != null) {
                    int minNum = buffNumRange.getInt(0);
                    int maxNum = buffNumRange.getInt(1);
                    updateAddBuffFighterByNumRange(addBuffFighter, buffAddableFighter, minNum, maxNum);
                }
                for (Fighter buffFighter : addBuffFighter) {
                    AddBuffData buffData = new AddBuffData(buffFighter.getId(), buffTemplate.getInt("id"));
                    fightContext.addFightBuff(buffFighter, roundFighter, buffTemplate, selectedFb);
                    buffDataList.add(buffData);
                }
            }
        } // 添加buff结束

    }

    private boolean checkCanAddBuff(LMap buffTemplate, Fighter f) {
        if (buffTemplate.getInt("skip", 0) == 1) {
            if (f.getFightable() instanceof MonsterEntity) {
                MonsterEntity m = ((MonsterEntity) f.getFightable());
                if (m.getTemplate().getInt("isStun", 0) == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private List<Fighter> getAddBuffFighter(List<Fighter> buffSelectList, LMap buffTemplate) {
        List<Fighter> addBuffFighterList = new ArrayList<Fighter>();
        for (Fighter buffFighter : buffSelectList) {
            if (buffFighter.isDead()) {
                continue;
            }
            if (!checkCanAddBuff(buffTemplate, buffFighter)) {
                continue;
            }
            addBuffFighterList.add(buffFighter);
        }
        return addBuffFighterList;
    }

    private void updateAddBuffFighterByNumRange(List<Fighter> addBuffFighter, List<Fighter> buffAddableFighter,
                                                int minNum, int maxNum) {
        int addBuffNum = addBuffFighter.size();
        if (addBuffNum < minNum) {
            int numGap = minNum - addBuffNum;
            for (int i = 0; i < buffAddableFighter.size(); i++) {
                Fighter fighter = buffAddableFighter.get(i);
                if (!addBuffFighter.contains(fighter)) {
                    addBuffFighter.add(fighter);
                    numGap--;
                }
                if (numGap <= 0) {
                    break;
                }
            }
        } else if (addBuffNum > maxNum) {
            for (int i = maxNum; i < addBuffNum; i++) {
                addBuffFighter.remove(addBuffFighter.size() - 1);
            }
        }
    }

    private List<Fighter> getAddBuffFighterByProb(List<Fighter> buffSelectList, float buffProb) {
        List<Fighter> addBuffFighterList = new ArrayList<Fighter>();
        for (Fighter buffFighter : buffSelectList) {
            if (GongUtils.canTakePlaceBase1(buffProb)) {
                addBuffFighterList.add(buffFighter);
            }
        }
        return addBuffFighterList;
    }

    private FightContext doFight(FightContext fightContext, boolean openingFight, int fightRound) {

        List<Fighter> allFighters = new ArrayList<Fighter>();
        int firstFightSide = fightContext.getFirstFightSide();
        // 排序
        fightContext.sortAllFighter(firstFightSide, allFighters);

        // 回合数
        for (int i = 0; i < fightRound; i++) {
            RoundActionData roundActionData = new RoundActionData();
            fightContext.addRoundActionData(roundActionData);
            fightContext.incrRoundIdx();

            // 判断战斗是否结束
            if (fightContext.isSideAllDead()) {
                break;
            }

            for (int k = 0; k < allFighters.size(); k++) {
                Fighter roundFighter = allFighters.get(k);
                if (roundFighter.isDead()) {
                    continue;
                }
                // 结算需回合前生效的buff
                List<RoundBuffData> roundBuffData = fightContext.handleBuffPerRound(roundFighter, true);
                if (CollectionUtils.isNotEmpty(roundBuffData)) {
                    roundActionData.addRoundBuffDatas(roundBuffData);
                }
            }

            for (int j = 0; j < allFighters.size(); j++) {
                Fighter roundFighter = allFighters.get(j);
                if (roundFighter.isDead()) {
                    continue;
                }
                // 结算buff
                fightContext.handlePerRoundBegin(roundFighter);

                // 判断被控制
                if (roundFighter.isSkip()) {
                    continue;
                }

                // 选择技能
                Pair<FightFabao, List<Fighter>> ret = fightContext.selectFabaoAndTargets(roundFighter, openingFight); // 被技能选择的目标
                if (ret == null) {
                    for (FightFabao ff : roundFighter.getFbList()) {
                        GuardianLogger.info("can not find target ! fbTemplateId=", ff.getTemplateId());
                    }

                    throw new GongRuntimeException("can not find fabao and target");
                }
                FightFabao selectedFb = ret.getFirst();
                List<Fighter> selectList = ret.getSecond();
                // 一定会找到一个技能和目标，优先级最低的是普攻
                if (selectedFb == null || CollectionUtils.isEmpty(selectList)) {
                    throw new GongRuntimeException("can not find target, fabao id = " + selectedFb.getTemplateId());
                }

                // selectedFb.setCd(selectedFb.getFbTemplate().getInt("fightCd",
                // 0) + 1); // 因为每回合起始结算cd,所以需要加1

                List<Fighter> beHitedList = new ArrayList<Fighter>(); // 命中的目标
                AttackAction attackAction = new AttackAction(roundFighter.getId(), selectedFb.getTemplateId());
                // 扣除技能消耗
                doFabaoConsume(fightContext, roundFighter, attackAction, selectedFb);


                setFaoBaoAttackData(selectList, fightContext, roundFighter, attackAction, selectedFb, beHitedList);

                if (attackAction != null) {
                    roundActionData.addAction(attackAction);
                }

                // 判断战斗是否结束
                if (fightContext.isSideAllDead()) {
                    break;
                }
            } // 攻击者循环

            // 判断战斗是否结束
            if (fightContext.isSideAllDead()) {
                break;
            } else {
                // 判断是否需要助威门徒上阵
                // fc.checkZwMtSz(roundActionData, all);
            }
        } // 回合循环

        GuardianLogger.info("！！！！！！！！！！战斗结束:");
        GuardianLogger.info("总回合数：" + fightContext.getRoundActionDataList().size());
        for (Fighter fighter : allFighters) {
            GuardianLogger.info(
                    "attackAction: id = " + fighter.getId() + " name = " + fighter.getName() + " side = "
                            + fighter.getSide() + " hp = " + fighter.getFinalHp(),
                    " cp=", fighter.getFinalCp(), " initFinalHp=" + fighter.getInitFinalHp());
        }

        // 清除BUFF属性(只针对门徒)
        List<Fighter> allMtFighters = fightContext.getAllMtFighterList();
        for (Fighter f : allMtFighters) {
            if (f.getFightable() instanceof ServantEntity) {
                resetInFightProp(f.getFightable());
            }
        }

        // 计算胜负关系
        fightContext.calcFightWld();
        return fightContext;
    }


    //设置技能增加的属性
    private void setFaBaoAddProp(Fighter roundFighter, FightFabao selectedFb) {
        LMap fbTmp = selectedFb.getFbTemplate();
        if (fbTmp.containsKey("skillpropid") && fbTmp.containsKey("skillDamege")) {
            LList bdPropList = fbTmp.getList("skillpropid");
            LList bdPropValueList = fbTmp.getList("skillDamege");
            if (bdPropList != null && bdPropValueList != null && bdPropList.size() == bdPropValueList.size()) {
                for (int i = 0; i < bdPropList.size(); i++) {
                    int bdPropID = bdPropList.getInt(i, 0);
                    if (bdPropID != 0) {
                        float bdPropValue = 0.0f;
                        if (fbTmp.containsKey("skillDamege")) {
                            bdPropValue = bdPropValueList.getInt(i, 0);
                            String propName = PropData.data.getMap(bdPropID).getString("funName");
                            FightablePropHelper.incrNumProp(roundFighter.getFightable(), propName, bdPropValue);
                        }
                    }

                }
            }
        }
    }

    //重置技能增加的属性值
    private void resetFaBaoAddProp(Fighter roundFighter, FightFabao selectedFb) {
        LMap fbTmp = selectedFb.getFbTemplate();
        if (fbTmp.containsKey("skillpropid") && fbTmp.containsKey("skillDamege")) {
            LList bdPropList = fbTmp.getList("skillpropid");
            LList bdPropValueList = fbTmp.getList("skillDamege");
            if (bdPropList != null && bdPropValueList != null && bdPropList.size() == bdPropValueList.size()) {
                for (int i = 0; i < bdPropList.size(); i++) {
                    int bdPropID = bdPropList.getInt(i, 0);
                    if (bdPropID != 0) {
                        String propName = PropData.data.getMap(bdPropID).getString("funName");
                        FightablePropHelper.setNumProp(roundFighter.getFightable(), propName, 0.0f);
                    }
                }
            }
        }
    }


    private void setFaoBaoAttackData(List<Fighter> selectList, FightContext fightContext, Fighter roundFighter,
                                     AttackAction attackAction, FightFabao selectedFb, List<Fighter> beHitedList) {

        //设置技能增加的属性
        setFaBaoAddProp(roundFighter, selectedFb);


        List<BeAttackData> beAttackDataList = new ArrayList<BeAttackData>(selectList.size());
        attackAction.setBeAttackDataList(beAttackDataList);

        int tgtEffect = selectedFb.getFbTemplate().getInt("tgtEffect");
        for (Fighter f : selectList) {
            BeAttackData beAttackData = new BeAttackData(f.getId());
            beAttackDataList.add(beAttackData);
            if (tgtEffect == 1) {
                // 治疗
                int treatDmg = 100;
                fightContext.changeHpPa(f, treatDmg, 3, beAttackData);
                beHitedList.add(f);
            } else {
                // 伤害
                setSelectFaBaoDamage(fightContext, selectedFb, roundFighter, f, beHitedList, beAttackData);
            }
        }

        //重置技能增加的属性值
        resetFaBaoAddProp(roundFighter, selectedFb);


        // 处理buff
        handleBuff(selectedFb, attackAction, roundFighter, beHitedList, fightContext);
    }

    // 计算当前攻击状态，0普攻，1暴击，2格挡
    private int getCurAttackState(Fighter roundFighter, Fighter f) {
        int ret = 0;
        // 暴击率
        Number criRate = FormulaHelper.calcSFormulaNumber(GongConstants.FORMULA_CRI_RATE,
                GongUtils.createHashMap("o", roundFighter.getFightable(), "t", f.getFightable()));

        // 格挡率
        Number parRate = FormulaHelper.calcSFormulaNumber(GongConstants.FORMULA_PAR_RATE,
                GongUtils.createHashMap("o", roundFighter.getFightable(), "t", f.getFightable()));
        float norRate = 1 - criRate.floatValue() - parRate.floatValue();

        int normalRateInt = (int) (GongUtils.ROUND_TABLE_BASE_100 * norRate);
        int criRateInt = (int) (GongUtils.ROUND_TABLE_BASE_100 * criRate.floatValue());
        int parRateInt = (int) (GongUtils.ROUND_TABLE_BASE_100 * parRate.floatValue());
        Integer[] damageRateList = new Integer[]{normalRateInt, criRateInt, parRateInt};
        GuardianLogger.info("curSelectFaBaoDamage norRate =", normalRateInt, " criRate=", criRateInt, " parRate=",
                parRateInt);

        int randomIndex = (int) GongUtils.randomRoundTableBase100(damageRateList);
        GuardianLogger.info("randomIndex =", randomIndex);
        if (randomIndex < 0) {
            // 如果没有随机到，就默认普通攻击
            ret = 0;
        }
        ret = randomIndex;
        return ret;
    }

    // 当前攻击类型
    private String getCurAttackDamageValue(int attackState) {
        String dmageKey = "norDamageId";
        if (attackState == GongConstants.CUR_FIGHTER_ATTACK_STATE_CRI) {
            // 暴击
            dmageKey = "criDamageId";
        } else if (attackState == GongConstants.CUR_FIGHTER_ATTACK_STATE_PAR) {
            // 格挡
            dmageKey = "parDamageId";
        }
        return dmageKey;
    }

    /**
     * @param fightContext
     * @param selectedFb
     * @param roundFighter 主动人
     * @param f            被动人
     * @param beHitedList
     * @param beAttackData void
     * @author: wacenn
     * @createTime: 2017年11月13日 下午3:46:00
     * @history:
     */
    private void setSelectFaBaoDamage(FightContext fightContext, FightFabao selectedFb, Fighter roundFighter, Fighter f,
                                      List<Fighter> beHitedList, BeAttackData beAttackData) {

        int dmgByFb = 0;
        boolean isHit = false; // 是否命中
        int mustHit = selectedFb.getFbTemplate().getInt("mustHit", 0);
        if (mustHit == 1) {
            isHit = true;
        } else {
            isHit = true;
            // Number hitRate =
            // FormulaHelper.calcSFormulaNumber(GongConstants.FORMULA_HIT_RATE,
            // GongUtils.createHashMap("o",
            // roundFighter.getFightable(), "t", f.getFightable(),
            // "lv",
            // selectedFb.getLv()));
            // isHit =
            // GongUtils.canTakePlaceBase1(hitRate.floatValue());
        }
        if (isHit) {
            float fbDmg;
            int damageCount = selectedFb.getFbTemplate().getInt("damageCount", 1);
            for (int i = 0; i < damageCount; i++) {
                // 先判断 普攻 暴击 格挡
                int curAttackState = getCurAttackState(roundFighter, f);
                String curDmageKey = getCurAttackDamageValue(curAttackState);

                int damgeFormulaId = selectedFb.getFbTemplate().getInt(curDmageKey);
                if (damgeFormulaId == 0) {
                    continue;
                }
                fbDmg = ((Number) FormulaHelper.calcSFormulaNumber(damgeFormulaId,
                        GongUtils.createHashMap("o", roundFighter.getFightable(), "t", f.getFightable())))
                        .floatValue();
                dmgByFb = Double.valueOf(Math.floor(fbDmg)).intValue();
                dmgByFb = dmgByFb * (-1);
                // 计算阵法伤害加成和减免加成
                // 主动人 roundFighter f被动人
                // dmgByFb = changeDmg(roundFighter, f,
                // dmgByFb,selectedFb);
                fightContext.changeHpPa(f, dmgByFb, curAttackState, beAttackData);

            }
            beHitedList.add(f);
        } else {
            beAttackData.setDodge(true);
        }
    }

    private void resetInFightProp(Fightable fightable) {
        List<String> propNameList = GongUtils.getInFightPropList();
        if (CollectionUtils.isEmpty(propNameList)) {
            return;
        }
        for (String s : propNameList) {
            FightablePropHelper.setNumProp(fightable, s, 0);
        }
        if (fightable instanceof ServantEntity) {
            ((ServantEntity) fightable).calcAll();
        }
    }

    /**
     * 法宝消耗
     *
     * @param attackAction
     * @param selectedFb
     */
    private void doFabaoConsume(FightContext fc, Fighter roundFighter, AttackAction attackAction,
                                FightFabao selectedFb) {

        // 消耗怒气值
        if (selectedFb.getConsumeFinalCp() != 0) {
            attackAction.setConsumeFinalCp(selectedFb.getConsumeFinalCp());
            fc.changeFinalCpIn(roundFighter, selectedFb.getConsumeFinalCp(), attackAction, true);
        }

        // 增加怒气值
        if (selectedFb.getAddFinalCp() != 0) {
            attackAction.setAddFinalCp(selectedFb.getAddFinalCp());
            fc.changeFinalCpIn(roundFighter, selectedFb.getAddFinalCp(), attackAction, false);
        }
    }
}
