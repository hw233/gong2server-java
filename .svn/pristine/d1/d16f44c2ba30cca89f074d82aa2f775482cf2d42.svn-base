
package com.gamejelly.gong2.gas.service.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import com.hadoit.game.common.lang.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.gamejelly.gong2.config.data.GuankaBaseData;
import com.gamejelly.gong2.config.data.GuankaMonsterData;
import com.gamejelly.gong2.config.data.SceneData;
import com.gamejelly.gong2.config.data.base.LList;
import com.gamejelly.gong2.config.data.base.LMap;
import com.gamejelly.gong2.gas.entity.AvatarEntity;
import com.gamejelly.gong2.gas.entity.ServantEntity;
import com.gamejelly.gong2.gas.entity.fight.FightContext;
import com.gamejelly.gong2.gas.entity.fight.FightResult;
import com.gamejelly.gong2.meta.GuankaModel;
import com.gamejelly.gong2.utils.GongConstants;
import com.gamejelly.gong2.utils.GongRpcConstants;
import com.gamejelly.gong2.utils.GongLogConstants;
import com.gamejelly.gong2.utils.GongRuntimeException;
import com.gamejelly.gong2.utils.GongUtils;
import com.gamejelly.gong2.vo.AvatarVO;
import com.gamejelly.gong2.vo.FightResultVO;
import com.gamejelly.gong2.vo.InstanceChangeVO;
import com.gamejelly.gong2.vo.ServantVO;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;

@Component("sceneService")
public class SceneService {

    @Autowired
    @Qualifier("fightService")
    private FightService fightService;

    @Autowired
    @Qualifier("itemService")
    private ItemService itemService;

    public boolean fightGuanka(AvatarEntity entity, int guankaMonsterId) {

        if (GuankaMonsterData.data.getMap(guankaMonsterId) == null) {
            GuardianLogger.error("fightGuanka GuankaMonsterData not exist guankaMonsterId=", guankaMonsterId);
            return false;
        }
        int guankaBaseId = GuankaMonsterData.data.getMap(guankaMonsterId).getInt("guankaBaseId", 0);
        GuankaModel gkm = entity.getGuanka(guankaBaseId);
        if (gkm == null) {
            gkm = entity.addGuanka(guankaBaseId);
            if (gkm != null) {
                entity.notify(GongRpcConstants.RES_USER_GUANKA_CHANGE,
                        new InstanceChangeVO(Arrays.asList(gkm), null, null));
            }
        }

        if (gkm == null) {
            throw new GongRuntimeException(
                    "Error : fightGuanka guankaMonsterId = " + guankaMonsterId + " guankaBaseId = " + guankaBaseId);
        }

        FightContext fightContext = fightService.fightMonster(entity, guankaMonsterId, GongConstants.FIGHT_TYPE_GK);
        calcAfterFightForGk(entity, fightContext, guankaMonsterId);
        return true;
    }

    private void handleNextScene(AvatarEntity entity, int nextSceneId, List<GuankaModel> addGkList) {
        List<Integer> nextGks = GongUtils.getGkListByScene(nextSceneId);
        for (Integer nagkId : nextGks) {
            LMap agk = GuankaBaseData.data.getMap(nagkId);
            if (agk.getInt("order") == 1) {
                if (entity.getGuanka(nagkId) == null) {
                    addGkList.add(entity.addGuanka(agk.getInt("id")));
                    break;
                }
            }
        }
    }


    /**
     * @param fc
     * @return 根据主动方死亡人数获取星数
     */
    private int getCurGuanKaStar(FightContext fc) {
        int ret = GongConstants.FUBEN_ONE_STAR;
        int initiativeDeadCount = fc.getInitiativeDeadCount();
        if (initiativeDeadCount == 0) {
            ret = GongConstants.FUBEN_THREE_STAR;
        } else if (initiativeDeadCount == 1) {
            ret = GongConstants.FUBEN_TWO_STAR;
        }
        return ret;
    }


    @SuppressWarnings("unchecked")
    private void calcAfterFightForGk(AvatarEntity avatarEntity, FightContext fc, int guankaMonsterId) {
        LMap gkMonster = GuankaMonsterData.data.getMap(guankaMonsterId);
        if (gkMonster == null) {
            return;
        }
        int guankaBaseId = gkMonster.getInt("guankaBaseId", 0);
        LMap guankaTmp = GuankaBaseData.data.getMap(guankaBaseId);
        LMap currScene = SceneData.data.getMap(guankaTmp.getInt("scene", 0));
        LList gkMonsterIds = guankaTmp.getList("gkMonsterId");
        int gkNd = gkMonsterIds.indexOf(guankaMonsterId) + 1; // 这个怪的难度

        GuankaModel oldGm = avatarEntity.getGuanka(guankaBaseId);
        if (oldGm == null) {
            GuardianLogger.warn("Guanka not exists! ", guankaBaseId);
            return;
        }


        boolean firstFightThisGk = false;
        boolean firstCompThisGk = false;
        if (oldGm.getTotalFightCount() == 0) {
            firstFightThisGk = true;
        }
        oldGm.incrTotalFightCount(1);
        if (fc.isWin()) {
            // 赢了才扣次数
            oldGm.incrFightCount(1);
            if (gkNd - oldGm.getPlan() > 0) {
                // 是否是下一个难度
                oldGm.setPlan(gkNd);
                firstCompThisGk = true;
            }
            //关卡星
            int star = getCurGuanKaStar(fc);
            oldGm.getStarMap().put(gkNd, star);
        }
        avatarEntity.notify(GongRpcConstants.RES_USER_GUANKA_CHANGE,
                new InstanceChangeVO(null, Arrays.asList(oldGm), null));
        Object[] allRewards = null;
        if (fc.isWin()) {
            int nextGk = guankaTmp.getInt("nextGuanka", 0);
            boolean hasNextScene = false;
            List<GuankaModel> addGkList = new ArrayList<GuankaModel>();
            if (nextGk <= 0) {
                int nextSceneId = currScene.getInt("nextScene", 0);
                // 有后续场景
                if (nextSceneId > 0) {
                    handleNextScene(avatarEntity, nextSceneId, addGkList);
                    hasNextScene = true;
                }
            } else {
                if (avatarEntity.getGuanka(nextGk) == null) {
                    addGkList.add(avatarEntity.addGuanka(nextGk));
                }
            }
            if (CollectionUtils.isNotEmpty(addGkList)) {
                avatarEntity.notify(GongRpcConstants.RES_USER_GUANKA_CHANGE,
                        new InstanceChangeVO(addGkList, null, null), true, hasNextScene);
            }
            allRewards = doGuankaRewardAndExpend(avatarEntity, guankaMonsterId, firstFightThisGk);
        }
        FightResult fr = new FightResult();
        fr.setAvatarId(avatarEntity.getId());
        if (allRewards != null) {
            fr.setExp((Integer) allRewards[0]);
            fr.setMoney((Integer) allRewards[1]);
        }

        fr.setGuankaMonsterId(guankaMonsterId);
        fr.setFightType(GongConstants.FIGHT_TYPE_GK);

        avatarEntity.notify(GongRpcConstants.RES_USER_AVATAR_CHANGE, new AvatarVO(avatarEntity));
        List<ServantVO> updateMts = new ArrayList<ServantVO>();
        for (ServantEntity mt : avatarEntity.getShangzhenServant()) {
            updateMts.add(new ServantVO(mt));
        }

        FightResultVO frv = new FightResultVO(fc, fr);
        avatarEntity.notify(GongRpcConstants.RES_USER_SERVANT_CHANGE, new InstanceChangeVO(null, updateMts, null));
        avatarEntity.notify(GongRpcConstants.RES_USER_FIGHT_RESULT, frv);

    }

    private Object[] doGuankaRewardAndExpend(AvatarEntity avatarEntity, int guankaMonsterId, boolean firstFightThisGk) {

        LMap gkMonster = GuankaMonsterData.data.getMap(guankaMonsterId);
        int guankaBaseId = gkMonster.getInt("guankaBaseId", 0);
        GuankaModel gkModel = avatarEntity.getGuanka(guankaBaseId);
        LMap guankaTmp = GuankaBaseData.data.getMap(guankaBaseId);
        LList gkMonsterIds = guankaTmp.getList("gkMonsterId");
        int gkNd = gkMonsterIds.indexOf(guankaMonsterId) + 1; // 这个怪的难度

        int addExp = gkMonster.getInt("basExp", 0);
        int addMoney = gkMonster.getInt("basMoney", 0);

        return new Object[]{addExp, addMoney};
    }


    /**
     * @param entity
     * @param sceneTemplateId 场景ID
     * @param awardIdx        奖励ID
     * @param plan            普通1 困难2
     * @return
     */

    public boolean claimSceneReward(AvatarEntity entity, int sceneTemplateId, int awardIdx, int plan) {
        if (awardIdx < 1 || awardIdx > 3) {
            return false;
        }


        Map<Integer, Integer> useSceneMap = entity.getAvatarModel().getSceneNorAwards();
        if (plan == 2) {
            useSceneMap = entity.getAvatarModel().getSceneDifAwards();
        }


        int claimData = DataUtils.getMapInteger(useSceneMap, sceneTemplateId, 0);
        StringBuffer sb = new StringBuffer(claimData + "");
        char[] obj = sb.reverse().toString().toCharArray();
        if (obj.length >= awardIdx && String.valueOf(obj[awardIdx - 1]).equals("1")) {
            return false;
        }

        LMap sc = SceneData.data.getMap(sceneTemplateId);
        int starCon = sc.getList("starCon").getList(awardIdx - 1).getInt(awardIdx - 1, 0);

        int giftId = sc.getList("starAward").getList(awardIdx - 1).getInt(awardIdx - 1, 0);
        if (giftId <= 0) {
            return false;
        }

        Collection<GuankaModel> scs = entity.getGuankaByScene(sceneTemplateId);
        if (CollectionUtils.isEmpty(scs)) {
            return false;
        }
        int totalStar = 0;
        for (GuankaModel gkm : scs) {
            if(gkm.getStarMap().containsKey(plan)) {
                totalStar += gkm.getStarMap().get(plan);
            }
        }

        if (totalStar < starCon) {
            return false;
        }
        List<InstanceChangeVO> itemChanges = new ArrayList<InstanceChangeVO>();
        itemService.giveItemFromGift(entity, giftId, 1, itemChanges, GongLogConstants.LOG_ITEM_CHANGE_SYSTEM_FIX_ITEM);
        GongUtils.addUpValuesToMap(useSceneMap, sceneTemplateId,
                (int) Math.pow(10, awardIdx - 1));
        itemService.notifyItemChanges(entity, itemChanges);
        entity.notify(GongRpcConstants.RES_USER_AVATAR_CHANGE, new AvatarVO(entity));
        return true;
    }


    /**
     * @param entity
     * @param guankaBaseTemplateId 关卡ID
     * @param plan                 普通1 困难2
     * @return
     */

    public boolean claimGuankaBoxReward(AvatarEntity entity, int guankaBaseTemplateId, int plan) {

        GuankaModel gkm = entity.getGuanka(guankaBaseTemplateId);
        if (gkm == null) {
            return false;
        }

        if (gkm.getPlan() < plan) {
            //当前关卡未通关
            return false;
        }

        if(gkm.getBoxMap().containsKey(plan)){
            //已经领取了
            return false;
        }

        gkm.getBoxMap().put(plan, 1);

        entity.notify(GongRpcConstants.RES_USER_GUANKA_CHANGE,
                new InstanceChangeVO(null, Arrays.asList(gkm), null));
        return true;
    }
}
