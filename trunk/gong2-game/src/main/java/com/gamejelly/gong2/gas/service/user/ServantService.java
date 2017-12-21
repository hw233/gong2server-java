
package com.gamejelly.gong2.gas.service.user;

import java.util.*;

import com.gamejelly.gong2.config.data.*;
import com.gamejelly.gong2.config.data.base.LList;
import com.gamejelly.gong2.meta.CycleOperateModel;
import com.gamejelly.gong2.utils.*;
import com.hadoit.game.engine.core.rpc.simple.context.RpcResult;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.gamejelly.gong2.config.data.base.LMap;
import com.gamejelly.gong2.gas.entity.AvatarEntity;
import com.gamejelly.gong2.gas.entity.ServantEntity;
import com.gamejelly.gong2.meta.ItemModel;
import com.gamejelly.gong2.meta.ServantModel;
import com.gamejelly.gong2.vo.AvatarVO;
import com.gamejelly.gong2.vo.InstanceChangeVO;
import com.gamejelly.gong2.vo.ServantVO;
import com.hadoit.game.common.lang.Pair;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;

@Component("servantService")
public class ServantService {

    @Autowired
    @Qualifier("itemService")
    private ItemService itemService;

    @Autowired
    @Qualifier("userService")
    private UserService userService;


    public void addServant(AvatarEntity avatarEntity, int templateId, int pos) {

        GuardianLogger.info("addServant templated id=", templateId, " pos=", pos);
        LMap template = ServantData.data.getMap(templateId);
        if (template == null) {
            GuardianLogger.error("ServantData templateId id not exit id=", templateId);
            return;
        }

        Pair<ServantModel, List<ItemModel>> ret = LogicUtils.createServantModel(templateId, avatarEntity.getLv());
        ServantModel sm = ret.getFirst();
        sm.setAvatar(false);
        sm.setPos(pos);
        ServantEntity se = new ServantEntity(avatarEntity, sm);
        se.calcAll();

        ServantEntity oldMt = avatarEntity.getServantFromPos(pos);
        List<ServantVO> updateSer = new ArrayList<ServantVO>();
        if (oldMt != null) {
            oldMt.getModel().setPos(0);
            updateSer.add(new ServantVO(oldMt));
        }

        avatarEntity.addServant(se);

        if (ret.getSecond() != null) {
            avatarEntity.addAllItem(ret.getSecond());
        }
        updateSer.add(new ServantVO(se));
        avatarEntity.notify(GongRpcConstants.RES_USER_SERVANT_CHANGE, new InstanceChangeVO(updateSer, null, null));
        avatarEntity.notify(GongRpcConstants.RES_USER_ITEM_CHANGE, new InstanceChangeVO(ret.getSecond(), null, null));
    }

    /**
     * 英雄换位置
     *
     * @param avatarEntity
     * @param servantid
     * @param pos
     * @return
     */
    public boolean changeServantpos(AvatarEntity avatarEntity, String servantid, int pos) {

        GuardianLogger.info("UP Servant servantid=", servantid, " pos=", pos);
        int lv = avatarEntity.getLv();
        if (pos != 0) {
            LMap template = SysGotoData.data.getMap(pos);
            if (template == null) {
                GuardianLogger.error("SysGotoData templateId id not exit id=", pos);
                return false;
            }
            //todo 判断等级对应的开放的阵法位置
            if (lv < template.getInt("lv")) {
                GuardianLogger.error("pos is not open", pos);
                return false;
            }
        }

        ServantEntity se = avatarEntity.getServant(servantid);
        List<ServantVO> updateSer = new ArrayList<ServantVO>();
        if (se == null) {
            GuardianLogger.error("ServantData servantid not exit id=", servantid);
            return false;
        }
        //todo 主角不能下阵
        if (pos == 0 && se.getModel().isAvatar()) {
            GuardianLogger.error("avatar not remove");
            return false;
        }
        //todo pos 为0 下阵，无需对其他进行操作
        if (pos != 0) {
            ServantEntity oldMt = avatarEntity.getServantFromPos(pos);
            if (oldMt != null) {
                //如果被替换的是主角，并且属于上阵替换，主角不能被替换
                if (se.getModel().getPos() == 0 && oldMt.getModel().isAvatar()) {
                    GuardianLogger.error("avatar not remove");
                    return false;
                }
                oldMt.getModel().setPos(se.getModel().getPos());
                updateSer.add(new ServantVO(oldMt));
            }
        }
        se.getModel().setPos(pos);
        updateSer.add(new ServantVO(se));
        avatarEntity.notify(GongRpcConstants.RES_USER_SERVANT_CHANGE, new InstanceChangeVO(updateSer, null, null));
        avatarEntity.notify(GongRpcConstants.RES_USER_AVATAR_CHANGE, new AvatarVO(avatarEntity));
        return true;
    }


    /**
     * @param avatarEntity
     * @param servantId    仆从ID
     * @param auto         是否一键升级
     */
    public boolean upgradeServant(AvatarEntity avatarEntity, String servantId, boolean auto) {
        ServantEntity se = avatarEntity.getServant(servantId);
        if (se == null) {
            return false;
        }
        ServantModel sm = se.getModel();
        int curLv = sm.getLv();
        int maxCanUpgradeLv = avatarEntity.getLv();
        //等级限制
        if (curLv >= maxCanUpgradeLv) {
            return false;
        }

        //需要道具
        LList needItemTempIds = SysConstData.data.getList("SERVANT_UPGRADE_NEED_ITEMS");
        if (needItemTempIds == null) {
            return false;
        }

        //拥有物品列表
        List<ItemModel> needItemList = new ArrayList<ItemModel>();
        for (Object itemTemplateId : needItemTempIds) {
            Integer itemTempId = (Integer) itemTemplateId;
            List<ItemModel> curNeedItemList = avatarEntity.getItemsFromTemplateId(itemTempId);
            if (CollectionUtils.isNotEmpty(curNeedItemList)) {
                needItemList.addAll(curNeedItemList);
            }
        }

        if (CollectionUtils.isEmpty(needItemList)) {
            //没有道具
            return false;
        }


        //使用道具
        Map<Integer, Integer> itemUseCountMap = new HashMap<Integer, Integer>();
        boolean isOver = false;
        for (ItemModel itemModel : needItemList) {

            if (isOver) {
                break;
            }

            int itemCount = itemModel.getCount();
            int curItemUseCount = 0;
            int curItemAddExp = itemModel.getTemplate().getInt("exp", 0);
            for (int i = 0; i < itemCount; i++) {
                if (sm.getLv() >= avatarEntity.getLv()) {
                    isOver = true;
                    break;
                }
                if (auto) {
                    doAddServantExp(sm, curItemAddExp);
                } else {
                    if (doAddServantExp(sm, curItemAddExp)) {
                        curItemUseCount++;
                        isOver = true;
                        break;
                    }
                }
                curItemUseCount++;
            }
            itemUseCountMap.put(itemModel.getTemplateId(), curItemUseCount);
        }

        List<InstanceChangeVO> itemChanges = new ArrayList<InstanceChangeVO>();
        for (Map.Entry<Integer, Integer> iter : itemUseCountMap.entrySet()) {

            List<InstanceChangeVO> r1 = itemService.deductItem(avatarEntity, iter.getKey(), iter.getValue(),
                    GongLogConstants.LOG_ITEM_CHANGE_UPGRADE_SERVANT, 0, 0);
            if (r1 != null) {
                itemChanges.addAll(r1);
            }
        }

        itemService.notifyItemChanges(avatarEntity, itemChanges);
        avatarEntity.notify(GongRpcConstants.RES_USER_SERVANT_CHANGE, new InstanceChangeVO(null, Arrays.asList(new ServantVO(se)), null));
        return true;
    }


    /**
     * @param sm
     * @param varExp
     * @return boolean 是否升级
     */
    private boolean doAddServantExp(ServantModel sm, int varExp) {

        int nextLv = sm.getLv() + 1;
        LMap nextExpLvData = ExpLvData.data.getMap(nextLv);
        if (nextExpLvData == null) {
            //升到顶级了
            GuardianLogger.error("ExpLvData not exist template id =", nextLv);
            return false;
        }

        LMap curExpLvData = ExpLvData.data.getMap(sm.getLv());
        if (curExpLvData == null) {
            GuardianLogger.error("ExpLvData not exist template id =", curExpLvData);
            return false;
        }

        if (sm.getExp() + varExp < curExpLvData.getInt("upServantExp")) {
            sm.incrExp(varExp);
            return false;
        }

        sm.incrLv(1);
        sm.incrExp(varExp - curExpLvData.getInt("upServantExp"));
        return true;
    }


    public boolean upgradeAdvance(AvatarEntity avatarEntity, String servantId) {

        ServantEntity se = avatarEntity.getServant(servantId);
        if (se == null) {
            return false;
        }
        ServantModel sm = se.getModel();
        int curAdvanceLv = sm.getAdvanceLv();
        int curServantLv = sm.getLv();

        LMap nextDataMap = ServantAdvanceData.data.getMap(curAdvanceLv + 1);
        if (nextDataMap == null) {
            return false;
        }

        //等级限制
        int needServantLv = nextDataMap.getInt("lvcondition", 0);
        if (curServantLv < needServantLv) {
            return false;
        }

        int maxCanAdvanceLv = ServantAdvanceData.data.size();
        //最大等级
        if (curAdvanceLv >= maxCanAdvanceLv) {
            return false;
        }

        //需要道具
        // 所需材料列表
        LList itemNeedList = nextDataMap.getList("item");
        // 所需材料列表数量
        LList itemNeedCountList = nextDataMap.getList("itemnum");
        if (itemNeedList == null || itemNeedCountList == null || (itemNeedList.size() != itemNeedCountList.size())) {
            return false;
        }

        for (int i = 0, len = itemNeedList.size(); i < len; i++) {

            int itemTempID = itemNeedList.getInt(i);
            int totalNeedCount = itemNeedCountList.getInt(i);
            int itemHaveCount = avatarEntity.getItemCountFromTemplateId(itemTempID);
            if (itemHaveCount < totalNeedCount) {
                // 数量不足
                GongCommonNotify.notifyMsg(avatarEntity.getChannelContext(), GongConstants.MSG_ID_ITEM_NOT_ENOUGH);
                return false;
            }
        }

        //需要碎片


        //扣物品
        List<InstanceChangeVO> allChanges = new ArrayList<InstanceChangeVO>();

        // 扣除材料
        for (int i = 0, len = itemNeedList.size(); i < len; i++) {
            int itemTempID = itemNeedList.getInt(i);
            int totalNeedCount = itemNeedCountList.getInt(i);

            List<InstanceChangeVO> r1 = itemService.deductItem(avatarEntity, itemTempID, totalNeedCount,
                    GongLogConstants.LOG_ITEM_CHANGE_ADVANCE_SERVANT, 0, 0);
            if (r1 != null) {
                allChanges.addAll(r1);
            }
        }
        //通知变化
        sm.incrAdvanceLv(1);

        itemService.notifyItemChanges(avatarEntity, allChanges);
        avatarEntity.notify(GongRpcConstants.RES_USER_SERVANT_CHANGE, new InstanceChangeVO(null, Arrays.asList(new
                ServantVO(se)), null));
        return true;
    }


    /**
     * @param avatarEntity
     * @param zhaoMuType   招募类型: 1.选良人  2.选天人 3.十连抽
     * @param usePropType  使用道具方式: 1 按免费次数 2 使用道具 3 使用元宝
     * @return
     */
    public void servantZhaoMu(final RpcResult rpcResult, AvatarEntity avatarEntity, int zhaoMuType, int usePropType) {
        if (zhaoMuType == 1) {

            zhaoMuLiangRen(rpcResult, avatarEntity, usePropType);
        } else if (zhaoMuType == 2) {

            zhaoMuTianRen(rpcResult, avatarEntity, usePropType);
        } else if (zhaoMuType == 3) {

            zhaoMuTianRenTen(rpcResult, avatarEntity, usePropType);
        } else {
            rpcResult.result(new Object[]{false});
        }
        return;
    }

    /**
     * @param rpcResult
     * @param usePropType 使用道具方式: 1 按免费次数 2 使用道具 3 使用元宝
     * @return 招募良人
     */
    private void zhaoMuLiangRen(final RpcResult rpcResult, AvatarEntity avatarEntity, int usePropType) {
        //判断条件
        if (usePropType == 1) {


            CycleOperateModel co = avatarEntity.getCycleOperate(GongConstants.OPERATE_TYPE_DAY_ZHAOMU_LIANG_REN_COUNT);
            if (co == null) {
                rpcResult.result(new Object[]{false});
                return;
            }

            //判断次数
            if (!userService.canCycleOperate(co, 1, SysConstData.data.getInt("SEC_LIANG_REN_FREE_NUM"))) {
                rpcResult.result(new Object[]{false});
                return;
            }

            //当天有抽的情况判断cd时间
            long curTime = System.currentTimeMillis();
            if (co.getCdCount() > 0) {
                if (avatarEntity.getAvatarModel().getSecServantLiangRenCDTime() + SysConstData.data.getLong
                        ("SEC_LIANG_REN_CD_TIME") * 1000 > curTime) {
                    rpcResult.result(new Object[]{false});
                    return;
                }
            }


            List<InstanceChangeVO> allChanges = new ArrayList<InstanceChangeVO>();
            //抽
            Object[] ret = selectServant(SysConstData.data.getInt("SEC_SERVANT_TYPE_1"), rpcResult, avatarEntity, allChanges);
            if (ret != null) {
                userService.updateCycleOperate(co, 1, 0);
                avatarEntity.getAvatarModel().setSecServantLiangRenCDTime(curTime);

                if (CollectionUtils.isNotEmpty(allChanges)) {
                    itemService.notifyItemChanges(avatarEntity, allChanges);
                }
                avatarEntity.notify(GongRpcConstants.RES_USER_AVATAR_CHANGE, new AvatarVO(avatarEntity));

                rpcResult.result(new Object[]{true, ret});

            } else {
                rpcResult.result(new Object[]{false});
                return;
            }

        } else if (usePropType == 2) {


            int tempDataId = SysConstData.data.getInt("SEC_SERVANT_TYPE_2");
            LMap dataTemp = ServantSelectData.data.getMap(tempDataId);
            if (dataTemp == null) {
                GuardianLogger.error(" not exist id=", tempDataId);
                rpcResult.result(new Object[]{false});
                return;
            }

            //判断道具
            int itemNeedId = dataTemp.getInt("item");
            int itemHaveCount = avatarEntity.getItemCountFromTemplateId(itemNeedId);
            if (itemHaveCount <= 0) {
                // 数量不足
                GongCommonNotify.notifyMsg(avatarEntity.getChannelContext(), GongConstants.MSG_ID_ITEM_NOT_ENOUGH);
                rpcResult.result(new Object[]{false});
                return;
            }


            //扣物品
            List<InstanceChangeVO> allChanges = new ArrayList<InstanceChangeVO>();
            List<InstanceChangeVO> r1 = itemService.deductItem(avatarEntity, itemNeedId, 1,
                    GongLogConstants.LOG_ITEM_CHANGE_ZHAOMU_SERVANT, 0, 0);
            if (r1 != null) {
                allChanges.addAll(r1);
            }

            //抽
            Object[] ret = selectServant(tempDataId, rpcResult, avatarEntity, allChanges);
            itemService.notifyItemChanges(avatarEntity, allChanges);
            rpcResult.result(new Object[]{true, ret});


        } else {
            rpcResult.result(new Object[]{false});
        }


        return;
    }


    /**
     * @param rpcResult
     * @param usePropType 使用道具方式: 1 按免费次数 2 使用道具 3 使用元宝
     * @return 招募天人
     */
    private void zhaoMuTianRen(final RpcResult rpcResult, AvatarEntity avatarEntity, int usePropType) {
        //判断条件
        if (usePropType == 1) {
            CycleOperateModel co = avatarEntity.getCycleOperate(GongConstants.OPERATE_TYPE_DAY_ZHAOMU_TIAN_REN_COUNT);
            if (co == null) {
                rpcResult.result(new Object[]{false});
                return;
            }

            //判断次数
            if (!userService.canCycleOperate(co, 1, SysConstData.data.getInt("SEC_LIANG_REN_FREE_NUM"))) {
                rpcResult.result(new Object[]{false});
                return;
            }


            userService.updateCycleOperate(co, 1, 0);

            //抽
            List<InstanceChangeVO> allChanges = new ArrayList<InstanceChangeVO>();
            Object[] ret = selectServant(SysConstData.data.getInt("SEC_SERVANT_TYPE_3"), rpcResult, avatarEntity, allChanges);
            avatarEntity.notify(GongRpcConstants.RES_USER_AVATAR_CHANGE, new AvatarVO(avatarEntity));
            if (CollectionUtils.isNotEmpty(allChanges)) {
                itemService.notifyItemChanges(avatarEntity, allChanges);
            }

            rpcResult.result(new Object[]{true, ret});

        } else if (usePropType == 2) {

            int tempDataId = SysConstData.data.getInt("SEC_SERVANT_TYPE_4");
            LMap dataTemp = ServantSelectData.data.getMap(tempDataId);
            if (dataTemp == null) {
                GuardianLogger.error(" not exist id=", tempDataId);
                rpcResult.result(new Object[]{false});
                return;
            }

            //判断道具
            int itemNeedId = dataTemp.getInt("item");
            int itemHaveCount = avatarEntity.getItemCountFromTemplateId(itemNeedId);
            if (itemHaveCount <= 0) {
                // 数量不足
                GongCommonNotify.notifyMsg(avatarEntity.getChannelContext(), GongConstants.MSG_ID_ITEM_NOT_ENOUGH);
                rpcResult.result(new Object[]{false});
                return;
            }


            //扣物品
            List<InstanceChangeVO> allChanges = new ArrayList<InstanceChangeVO>();
            List<InstanceChangeVO> r1 = itemService.deductItem(avatarEntity, itemNeedId, 1,
                    GongLogConstants.LOG_ITEM_CHANGE_ZHAOMU_SERVANT, 0, 0);
            if (r1 != null) {
                allChanges.addAll(r1);
            }

            //抽
            Object[] ret = selectServant(tempDataId, rpcResult, avatarEntity, allChanges);
            itemService.notifyItemChanges(avatarEntity, allChanges);

            rpcResult.result(new Object[]{true, ret});

        } else if (usePropType == 3) {

            int tempDataId = SysConstData.data.getInt("SEC_SERVANT_TYPE_5");
            LMap dataTemp = ServantSelectData.data.getMap(tempDataId);
            if (dataTemp == null) {
                GuardianLogger.error(" not exist id=", tempDataId);
                rpcResult.result(new Object[]{false});
                return;
            }

            int needGold = dataTemp.getInt("gold");
            if (!avatarEntity.canConsumeGold(needGold)) {
                GongCommonNotify.notifyMsg(avatarEntity.getChannelContext(), GongConstants.MSG_ID_GOLD_NOT_ENOUGH);
                rpcResult.result(false);
                return;
            }


            avatarEntity.consumeGold(needGold, GongGoldLogConstants.LOG_GOLD_CHANGE_ZHAOMU_SERVANT, "");

            //抽
            List<InstanceChangeVO> allChanges = new ArrayList<InstanceChangeVO>();
            Object[] ret = selectServant(tempDataId, rpcResult, avatarEntity, allChanges);

            avatarEntity.notify(GongRpcConstants.RES_USER_AVATAR_CHANGE, new AvatarVO(avatarEntity));

            if (CollectionUtils.isNotEmpty(allChanges)) {
                itemService.notifyItemChanges(avatarEntity, allChanges);
            }

            rpcResult.result(new Object[]{true, ret});

        } else {
            rpcResult.result(new Object[]{false});
        }

        return;
    }

    /**
     * @param rpcResult
     * @param usePropType 使用道具方式 1 按免费次数 2 使用 3 不使用（元宝）
     * @return 十连抽
     */
    private void zhaoMuTianRenTen(final RpcResult rpcResult, AvatarEntity avatarEntity, int usePropType) {
        //判断条件
        if (usePropType == 3) {
            int tempDataId = SysConstData.data.getInt("SEC_SERVANT_TYPE_6");
            LMap dataTemp = ServantSelectData.data.getMap(tempDataId);
            if (dataTemp == null) {
                GuardianLogger.error(" not exist id=", tempDataId);
                rpcResult.result(new Object[]{false});
                return;
            }

            int needGold = dataTemp.getInt("gold");
            if (!avatarEntity.canConsumeGold(needGold)) {
                GongCommonNotify.notifyMsg(avatarEntity.getChannelContext(), GongConstants.MSG_ID_GOLD_NOT_ENOUGH);
                rpcResult.result(false);
                return;
            }


            avatarEntity.consumeGold(needGold, GongGoldLogConstants.LOG_GOLD_CHANGE_ZHAOMU_SERVANT, "");

            //抽
            List<InstanceChangeVO> allChanges = new ArrayList<InstanceChangeVO>();
            Object[] ret = selectServant(tempDataId, rpcResult, avatarEntity, allChanges);


            avatarEntity.notify(GongRpcConstants.RES_USER_AVATAR_CHANGE, new AvatarVO(avatarEntity));
            if (CollectionUtils.isNotEmpty(allChanges)) {
                itemService.notifyItemChanges(avatarEntity, allChanges);
            }

            rpcResult.result(new Object[]{true, ret});


        } else {
            rpcResult.result(new Object[]{false});
        }

        return;
    }


    /**
     * @param tempId
     * @param rpcResult
     * @param avatarEntity
     * @return 返回数组 物品列表 仆从列表
     */
    private Object[] selectServant(int tempId, final RpcResult rpcResult, AvatarEntity avatarEntity, List<InstanceChangeVO> allChanges) {

        LMap dataTemp = ServantSelectData.data.getMap(tempId);
        if (dataTemp == null) {
            GuardianLogger.error(" not exist id=", tempId);
            return null;
        }

        LList dropIdList = dataTemp.getList("dropID");
        LList dropNumLIst = dataTemp.getList("dropNum");
        if (dropIdList == null || dropNumLIst == null || dropIdList.size() != dropNumLIst.size()) {
            GuardianLogger.error(" ServantSelectData config error ", tempId);
            return null;
        }

        Object[] ret = GongUtils.getDropStoreNeedDataFormat();

        for (int i = 0; i < dropIdList.size(); i++) {
            Integer dropTempId = dropIdList.getInt(i);
            int dropCount = dropNumLIst.getInt(i);
            for (int j = 0; j < dropCount; j++) {
               itemService.getItemOrServantFromDropStore(avatarEntity, dropTempId,ret);
            }
        }

        GuardianLogger.info("selectServant ret  itemList=",ret[0]," servantList=",ret[1]);
        itemService.sendDropStoreData(ret, allChanges, avatarEntity, GongLogConstants.LOG_ITEM_CHANGE_ZHAOMU_SERVANT);
        return ret;
    }


}
