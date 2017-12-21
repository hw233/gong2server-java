package com.gamejelly.gong2.gas.entity.fight;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import com.gamejelly.gong2.config.data.PropData;
import com.gamejelly.gong2.config.data.SysConstData;
import com.gamejelly.gong2.config.data.base.LList;
import com.gamejelly.gong2.config.data.base.LMap;
import com.gamejelly.gong2.gas.entity.fight.action.AttackAction;
import com.gamejelly.gong2.gas.entity.fight.action.BeAttackData;
import com.gamejelly.gong2.gas.entity.fight.action.RemoveBuffAction;
import com.gamejelly.gong2.gas.entity.fight.action.RoundActionData;
import com.gamejelly.gong2.gas.entity.fight.action.RoundBuffData;
import com.gamejelly.gong2.utils.GongConstants;
import com.gamejelly.gong2.utils.GongUtils;
import com.gamejelly.gong2.utils.gen.FightablePropHelper;
import com.hadoit.game.common.lang.Pair;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;

public class FightContext {

    private List<Fighter> preInitiativeList;

    private List<Fighter> prePassiveList;

    private List<Fighter> initiativeList;

    private List<Fighter> passiveList;

    private int currRoundIdx;

    private boolean win;

    private List<RoundActionData> roundActionDataList = new ArrayList<RoundActionData>();

    private Map<String, Integer> fightTalks;

    private Pair<Integer, Integer> inZhenfa;

    private Pair<Integer, Integer> paZhenfa;

    // 所有对反方造成的伤害
    private int passiveTotalDamage;

    /**
     * initiativeTotalZhanLi主动方总战力
     */
    private long initiativeTotalZhanLi;

    /**
     * passiveTotalZhanLi被动方总战力
     */
    private long passiveTotalZhanLi;

    public FightContext(List<Fighter> initiativeList, List<Fighter> passiveList) {
        this.initiativeList = initiativeList;
        this.passiveList = passiveList;
        this.preInitiativeList = new ArrayList<Fighter>(initiativeList);
        this.prePassiveList = new ArrayList<Fighter>(passiveList);
    }

    public int getCurrRoundIdx() {
        return currRoundIdx;
    }

    public void setCurrRoundIdx(int currRoundIdx) {
        this.currRoundIdx = currRoundIdx;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public List<Fighter> getInitiativeList() {
        return initiativeList;
    }

    public void setInitiativeList(List<Fighter> initiativeList) {
        this.initiativeList = initiativeList;
    }

    public List<Fighter> getPassiveList() {
        return passiveList;
    }

    public void setPassiveList(List<Fighter> passiveList) {
        this.passiveList = passiveList;
    }

    public List<Fighter> getPreInitiativeList() {
        return preInitiativeList;
    }

    public void setPreInitiativeList(List<Fighter> preInitiativeList) {
        this.preInitiativeList = preInitiativeList;
    }

    public List<Fighter> getPrePassiveList() {
        return prePassiveList;
    }

    public void setPrePassiveList(List<Fighter> prePassiveList) {
        this.prePassiveList = prePassiveList;
    }

    public Fighter getFighter(final String id) {
        Fighter f = (Fighter) CollectionUtils.find(this.initiativeList, new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                return ((Fighter) object).getId().equals(id);
            }
        });
        if (f != null)
            return f;

        f = (Fighter) CollectionUtils.find(this.passiveList, new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                return ((Fighter) object).getId().equals(id);
            }
        });

        return f;
    }

    public List<RoundActionData> getRoundActionDataList() {
        return roundActionDataList;
    }

    public void addRoundActionData(RoundActionData rad) {
        roundActionDataList.add(rad);
    }

    public Map<String, Integer> getFightTalks() {
        return fightTalks;
    }

    public void setFightTalks(Map<String, Integer> fightTalks) {
        this.fightTalks = fightTalks;
    }

    private List<Fighter> rejectDeadTarget(List<Fighter> fs) {
        if (CollectionUtils.isEmpty(fs)) {
            return fs;
        }
        Iterator<Fighter> fsIter = fs.iterator();
        while (fsIter.hasNext()) {
            Fighter f = fsIter.next();
            if (f.isDead()) {
                fsIter.remove();
            }
        }
        return fs;
    }

    /**
     * 普攻选择被攻击对象
     */
    private List<Fighter> selectNormalTarget(final Fighter roundFighter, List<Fighter> lstTarget) {

        List<Fighter> retList = new ArrayList<Fighter>();
        if (CollectionUtils.isEmpty(lstTarget)) {
            return retList;
        }

        Fighter selectTarget = FightAI.selectMinDistanceTarget(lstTarget, roundFighter);
        retList.add(selectTarget);
        return retList;
    }

    // 普攻
    private Pair<FightFabao, List<Fighter>> normalFight(final Fighter caster) {
        // 没有技能就是普攻，则默认设置普攻
        // 普攻根据规则选一个
        List<Fighter> lstTarget = new ArrayList<Fighter>();
        List<Fighter> allT = getOppositeList(caster.getSide());
        Iterator<Fighter> fsIter = allT.iterator();
        while (fsIter.hasNext()) {
            Fighter f = fsIter.next();
            if (!f.isDead()) {
                lstTarget.add(f);
            }
        }
        List<Fighter> selectedList = selectNormalTarget(caster, lstTarget);

        if (CollectionUtils.isNotEmpty(selectedList)) {

            int pugongId = GongConstants.FIGHT_DEFAULT_FABAO_ID_ATK;
            int fighterPropType = caster.getPropType();
            if (fighterPropType == GongConstants.SERVANT_PROP_TYPE_2) {
                pugongId = GongConstants.FIGHT_DEFAULT_FABAO_ID_POWER;
            }
            FightFabao fightFabao = new FightFabao(pugongId, 1);
            LMap fabaoTemplate = fightFabao.getFbTemplate();
            int addCp = fabaoTemplate.getInt("addCp", 0);
            if (addCp != 0) {
                fightFabao.setAddFinalCp(addCp);
            }
            return new Pair<FightFabao, List<Fighter>>(fightFabao, selectedList);
        }
        return null;
    }

    public List<Fighter> getOwnerList(int side) {
        if (side == GongConstants.SIDE_INITIATIVE)
            return initiativeList;
        else
            return passiveList;
    }

    public List<Fighter> getOppositeList(int side) {
        if (side == GongConstants.SIDE_INITIATIVE)
            return passiveList;
        else
            return initiativeList;
    }

    public void incrRoundIdx() {
        ++currRoundIdx;
    }

    public boolean isAllInitiativeAlive() {
        for (Fighter f : initiativeList) {
            if (!f.isAlive()) {
                return false;
            }
        }
        return true;
    }


    /**
     * 获取主动方死亡个数
     * @return
     */
    public int getInitiativeDeadCount() {
        int ret = 0;
        for (Fighter f : initiativeList) {
            if(!f.isAlive()){
                ret = ret + 1;
            }
        }
        return ret;
    }


    public boolean isSideAllDead() {
        return isAllDead(initiativeList) || isAllDead(passiveList);
    }

    private boolean isAllDead(List<Fighter> fighters) {
        for (Fighter f : fighters) {
            if (f.isAlive()) {
                return false;
            }
        }
        return true;
    }

    private boolean isHpMpSp(int propKind) {
        return ((propKind == SysConstData.data.getInt("PROP_DATA_HP")
                || propKind == SysConstData.data.getInt("PROP_DATA_MP")
                || propKind == SysConstData.data.getInt("PROP_DATA_SP")));
    }

    /**
     * 被动改变血量
     *
     * @param pa
     * @param val
     * @param curAttackState 当前攻击状态
     * @param beAttackData
     */
    public void changeHpPa(Fighter pa, int val, int curAttackState, BeAttackData beAttackData) {

        long old = pa.getFinalHp();
        long newH = old + val;
        long actDamage = (newH > 0) ? -val : old;
        beAttackData.addDmg(val);
        beAttackData.addAttackState(curAttackState);

        pa.setFinalHp(GongUtils.adjustNumberInRange(newH, 0, pa.getInitFinalHp()));

        beAttackData.setDead(pa.getFinalHp() == 0 ? true : false);

        if (pa.getSide() == GongConstants.SIDE_PASSIVE) {
            this.setPassiveTotalDamage(this.getPassiveTotalDamage() + (int) actDamage);
        }
    }

    /**
     * 被动改变怒气
     *
     * @param pa
     * @param val
     * @param curAttackState 当前攻击状态
     * @param beAttackData
     */
    public void changeCpPa(Fighter pa, int val, int curAttackState, BeAttackData beAttackData) {

        long old = pa.getFinalCp();
        long newC = old + val;

        beAttackData.addDmg(val);
        beAttackData.addAttackState(curAttackState);

        pa.setFinalCp(GongUtils.adjustNumberInRange(newC, 0, SysConstData.data.getInt("MAX_CP", 0)));

    }

    /**
     * 主动改变HP
     *
     * @param in
     * @param val
     * @param aa
     */
    public void changeFinalHpIn(Fighter in, int val, AttackAction aa) {
        long changeFinalHp = doChangeFinalHpIn(in, val);
        aa.setConsumeFinalHp(changeFinalHp);
    }

    private long doChangeFinalHpIn(Fighter in, int val) {

        long old = in.getFinalHp();
        long newH = old + val;
        in.setFinalHp(GongUtils.adjustNumberInRange(newH, 0, in.getInitFinalHp()));

        long changeHP = in.getFinalHp() - old;
        return changeHP;
    }

    /**
     * 主动改变cp
     *
     * @param in
     * @param val
     * @param aa
     */
    public void changeFinalCpIn(Fighter in, int val, AttackAction aa, boolean consume) {
        long changeFinalCp = doChangeFinalCpIn(in, val);
        if (consume) {
            // 如果是消耗
            aa.setConsumeFinalCp(changeFinalCp);
        } else {
            aa.setAddFinalCp(changeFinalCp);
        }

    }

    private long doChangeFinalCpIn(Fighter in, int val) {

        long old = in.getFinalCp();
        long newH = old + val;
        in.setFinalCp(GongUtils.adjustNumberInRange(newH, 0, SysConstData.data.getInt("MAX_CP", 0)));

        long changeTotCP = in.getFinalCp() - old;
        return changeTotCP;
    }

    public void calcFightWld() {
        if (isAllDead(initiativeList)) {
            setWin(false);
        } else if (isAllDead(passiveList)) {
            setWin(true);
        } else {
            // 超过回合数主动方输
            setWin(false);
        }
    }

    public boolean isEnemyAllDead() {
        return isAllDead(passiveList);
    }

    public List<String> fighterToIds(List<Fighter> fs) {
        if (CollectionUtils.isEmpty(fs)) {
            return Collections.emptyList();
        }
        List<String> ret = new ArrayList<String>();
        for (Fighter f : fs) {
            ret.add(f.getId());
        }
        return ret;
    }

    public int getPassiveTotalDamage() {
        return passiveTotalDamage;
    }

    public void setPassiveTotalDamage(int passiveTotalDamage) {
        this.passiveTotalDamage = passiveTotalDamage;
    }

    public void addFighters(List<Fighter> fs) {
        if (CollectionUtils.isEmpty(fs)) {
            return;
        }
        for (Fighter f : fs) {
            if (f.getSide() == GongConstants.SIDE_INITIATIVE) {
                this.initiativeList.add(f);
            } else if (f.getSide() == GongConstants.SIDE_PASSIVE) {
                this.passiveList.add(f);
            }
        }
    }

    public List<Fighter> getAllMtFighterList() {
        List<Fighter> allList = new ArrayList<Fighter>();
        if (this.preInitiativeList != null && this.preInitiativeList.size() > 0) {
            allList.addAll(this.preInitiativeList);
        }
        if (this.prePassiveList != null && this.prePassiveList.size() > 0) {
            allList.addAll(this.prePassiveList);
        }
        return allList;
    }

    public int getInitDeadManCount() {
        int deadCounts = 0;
        for (Fighter f : preInitiativeList) {
            long hp = f.getFinalHp();
            if (hp <= 0) {
                deadCounts += 1;
            }
        }
        return deadCounts;
    }

    public float calInitSideHpRatio() {
        double totalHp = 0;
        double remainHp = 0;
        for (Fighter f : preInitiativeList) {
            long hp = f.getFinalHp();
            if (hp > 0) {
                remainHp += hp;
            }
            totalHp += f.getInitFinalHp();
        }

        return totalHp == 0 ? 0 : (float) (remainHp / totalHp);
    }

    public Pair<FightFabao, List<Fighter>> selectFabaoAndTargets(final Fighter caster, boolean openingFight) {

        if (openingFight || caster.isForget()) {
            return normalFight(caster);
        } else {
            // 正常处理
            // 过滤掉CD中的和需要HP、MP等不足的技能
            List<FightFabao> fbList = caster.getAvailableFbList();
            if (CollectionUtils.isEmpty(fbList)) {
                return normalFight(caster);
            }

            Collections.sort(fbList, new Comparator<FightFabao>() {
                @Override
                public int compare(FightFabao o1, FightFabao o2) {
                    if (o1 == null || o2 == null)
                        return 0;

                    int o1Priority = o1.getFbTemplate().getInt("Priority", 0);
                    int o2Priority = o2.getFbTemplate().getInt("Priority", 0);

                    return o2Priority - o1Priority;
                }
            });

            for (FightFabao fightFabao : fbList) {
                LMap temp = fightFabao.getFbTemplate();

                List<Fighter> allO = getOwnerList(caster.getSide());
                List<Fighter> allT = getOppositeList(caster.getSide());

                List<Fighter> selectedList = null;
                if (temp.getInt("fbKind") == SysConstData.data.getInt("FB_KIND_1")) {
                    // 技能AI
                    selectedList = FightAI.getFabaoTarget(this, allO, allT, caster, fightFabao);
                }

                if (CollectionUtils.isNotEmpty(selectedList)) {
                    return new Pair<FightFabao, List<Fighter>>(fightFabao, selectedList);
                }
            }

            return normalFight(caster);
        }
    }

    public Pair<Integer, Integer> getInZhenfa() {

        return inZhenfa;
    }

    public void setInZhenfa(Pair<Integer, Integer> inZhenfa) {

        this.inZhenfa = inZhenfa;
    }

    public Pair<Integer, Integer> getPaZhenfa() {

        return paZhenfa;
    }

    public void setPaZhenfa(Pair<Integer, Integer> paZhenfa) {

        this.paZhenfa = paZhenfa;
    }

    private void incrBuffCd(Fighter fighter) {
        if (CollectionUtils.isEmpty(fighter.getBuffList())) {
            return;
        }

        for (int i = fighter.getBuffList().size() - 1; i >= 0; --i) {
            FightBuff fightBuff = fighter.getBuffList().get(i);
            fightBuff.incrAlreadyStanding();
            if (fightBuff.getAlreadyStanding() >= fightBuff.getTemplate().getInt("lastTime")) {
                removeFightBuff(fightBuff);
            }
        }
    }

    public FightBuff addFightBuff(Fighter buffOwner, Fighter caster, LMap buffTemplate, FightFabao selectedFb) {
        FightBuff fightBuff = new FightBuff();
        fightBuff.setTemplateId(buffTemplate.getInt("id"));
        fightBuff.setOwnerId(buffOwner.getId());
        fightBuff.setCasterId(caster.getId());
        fightBuff.setCasterFb(selectedFb);
        LList propValue = buffTemplate.getList("propValue");
        if (propValue != null) {
            float val = propValue.getList(selectedFb.getLv() - 1).getFloat(0);
            // fightBuff.setBuffFactor(val);
        }

        buffOwner.addFightBuff(fightBuff, this);
        return fightBuff;
    }

    private RoundBuffData checkBuffEffect(FightBuff fightBuff) {

        return null;
    }

    public void removeFightBuff(FightBuff fightBuff) {
        removeFightBuffDataOnly(fightBuff);
        RoundActionData rad = roundActionDataList.get(roundActionDataList.size() - 1);
        rad.addAction(new RemoveBuffAction(fightBuff.getOwnerId(), fightBuff.getTemplateId()));
    }

    public void removeFightBuffDataOnly(FightBuff fightBuff) {
        getFighter(fightBuff.getOwnerId()).removeFightBuff(fightBuff);
        doFightBuffEffect(fightBuff, false);
    }

    public long getInitiativeTotalZhanLi() {

        return initiativeTotalZhanLi;
    }

    public void setInitiativeTotalZhanLi(long initiativeTotalZhanLi) {

        this.initiativeTotalZhanLi = initiativeTotalZhanLi;
    }

    public long getPassiveTotalZhanLi() {

        return passiveTotalZhanLi;
    }

    public void setPassiveTotalZhanLi(long passiveTotalZhanLi) {

        this.passiveTotalZhanLi = passiveTotalZhanLi;
    }

    /**
     * 根据战力获取先出手的一方
     */
    public int getFirstFightSide() {
        long initiativeTotalZhanLi = this.getInitiativeTotalZhanLi();
        long passiveTotalZhanLi = this.getPassiveTotalZhanLi();
        return initiativeTotalZhanLi >= passiveTotalZhanLi ? GongConstants.SIDE_INITIATIVE : GongConstants.SIDE_PASSIVE;
    }

    /**
     * 获取fighter出手顺序
     */
    public void sortAllFighter(int firstFightSide, List<Fighter> allFighters) {
        List<Fighter> inFighters = this.getInitiativeList();
        List<Fighter> paFighters = this.getPassiveList();

        int maxSideSize = inFighters.size() >= paFighters.size() ? inFighters.size() : paFighters.size();
        boolean initiativeFirst = firstFightSide == GongConstants.SIDE_INITIATIVE ? true : false;

        for (int i = 0; i < maxSideSize; i++) {
            if (initiativeFirst) {
                if (i < inFighters.size()) {
                    allFighters.add(inFighters.get(i));
                }
                if (i < paFighters.size()) {
                    allFighters.add(paFighters.get(i));
                }
            } else {
                if (i < paFighters.size()) {
                    allFighters.add(paFighters.get(i));
                }
                if (i < inFighters.size()) {
                    allFighters.add(inFighters.get(i));
                }
            }
        }

        for (int i = 0; i < allFighters.size(); i++) {
            GuardianLogger.info("sort all fighter side =", allFighters.get(i).getSide(), " index=",
                    allFighters.get(i).getIndex(), " hp=", allFighters.get(i).getFinalHp(), " cp=",
                    allFighters.get(i).getFinalCp(), " initFinalHp=" + allFighters.get(i).getInitFinalHp());
        }
    }

    private void handleFbPerRound(Fighter fighter) {
        List<FightFabao> fbList = fighter.getFbList();
        if (CollectionUtils.isEmpty(fbList)) {
            return;
        }
        for (FightFabao ff : fbList) {
            ff.incrCd(-1);
        }
    }

    public List<RoundBuffData> handleBuffPerRound(Fighter fighter, boolean roundBefore) {
        if (CollectionUtils.isEmpty(fighter.getBuffList())) {
            return null;
        }

        List<RoundBuffData> roundBuffList = new ArrayList<RoundBuffData>();
        // 从后往前遍历,因为可能有删除
        for (int i = fighter.getBuffList().size() - 1; i >= 0; i--) {
            if (i >= fighter.getBuffList().size() || fighter.getFinalHp() <= 0) {
                continue;
            }
            FightBuff fightBuff = fighter.getBuffList().get(i);
            int buffKind = fightBuff.getTemplate().getInt("buffKind", -1);
            if (buffKind == SysConstData.data.getInt("BUFF_KIND_5")) {// 中毒
                if (!roundBefore) {// 跟回合绑定的buff才生效
                    continue;
                }
            } else if (roundBefore) {
                continue;
            }
            // 回合开始需要生效没生效的buff
            if (fightBuff.isActive() == false) {
                RoundBuffData roundBuffData = activeFightBuff(fightBuff);
                if (roundBuffData != null) {
                    roundBuffList.add(roundBuffData);
                }
            } else {
                fightBuff.incrAlreadyStanding();
                if (fightBuff.getAlreadyStanding() == fightBuff.getTemplate().getInt("lastTime")) {
                    removeFightBuff(fightBuff);
                } else {
                    RoundBuffData roundBuffData = checkBuffEffect(fightBuff);
                    if (roundBuffData != null) {
                        roundBuffList.add(roundBuffData);
                    }
                }
            }
        }

        return roundBuffList;
    }

    public RoundBuffData activeFightBuff(FightBuff fightBuff) {
        fightBuff.setActive(true);
        return doFightBuffEffect(fightBuff, true);
    }

    private RoundBuffData doFightBuffEffect(FightBuff fightBuff, boolean apply) {
        Fighter fighter = getFighter(fightBuff.getOwnerId());
        Fighter caster = getFighter(fightBuff.getCasterId());

        if (fighter.getFinalHp() <= 0) {
            return null;
        }
        LMap buffTempate = fightBuff.getTemplate();
        int buffKind = buffTempate.getInt("buffKind", -1);
        if (buffKind == SysConstData.data.getInt("BUFF_KIND_2")) {// 沉默
            fighter.setForget(apply);
        } else {
            LList propKinds = buffTempate.getList("propKind");
            if (CollectionUtils.isNotEmpty(propKinds)) {
                for (int i = 0; i < propKinds.size(); i++) {
                    int propKind = propKinds.getInt(i);
                    if (propKind > 0) {
                        float val = buffTempate.getList("propValue").getList(fightBuff.getCasterFb().getLv() - 1)
                                .getFloat(i);
                        val = apply ? val : -val;
                        Fightable bOwner = fighter.getFightable();
                        FightablePropHelper.incrNumProp(bOwner, PropData.data.getMap(propKind).getString("funName"),
                                val);

                        bOwner.calcAll(); // 属性改变需要重新运算一下
                    }
                }
            }
        }
        return null;
    }

    public void handlePerRoundBegin(Fighter fighter) {
        handleFbPerRound(fighter);
        handleBuffPerRound(fighter, false);
    }
}
