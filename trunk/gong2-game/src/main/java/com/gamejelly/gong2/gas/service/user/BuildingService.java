package com.gamejelly.gong2.gas.service.user;

import com.gamejelly.gong2.config.data.BuildingData;
import com.gamejelly.gong2.config.data.BuildingupData;
import com.gamejelly.gong2.config.data.ConsumeData;
import com.gamejelly.gong2.config.data.SysConstData;
import com.gamejelly.gong2.config.data.base.LMap;
import com.gamejelly.gong2.gas.annotation.TestInterface;
import com.gamejelly.gong2.gas.entity.AvatarEntity;
import com.gamejelly.gong2.meta.BuildingModel;
import com.gamejelly.gong2.utils.*;
import com.gamejelly.gong2.vo.AvatarVO;
import com.gamejelly.gong2.vo.InstanceChangeVO;
import com.hadoit.game.engine.core.rpc.simple.context.RpcResult;
import com.hadoit.game.engine.guardian.gas.GasManager;
import com.hadoit.game.engine.guardian.gas.entity.RawCommandCallback;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: liuliwei
 * Date: 2017-12-08
 * Time: 下午12:47
 * Description: ${description}
 */
@TestInterface
@Component("buildingService")
public class BuildingService {

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Autowired
    @Qualifier("gasManager")
    private GasManager gasManager;

    @Autowired
    @Qualifier("itemService")
    private ItemService itemService;


    public BuildingModel createBuilding(final AvatarEntity avatarEntity, final RpcResult rpcResult, int x, int y,
                                        int templateId, String buildingId, float scale, boolean flip, boolean canMoveBuild, boolean canFlip,
                                        boolean delete, boolean newPlayer, boolean openSpace) {
        LMap template = BuildingData.data.getMap(templateId);
        if (template == null) {
            GuardianLogger.error("building templateId not exist! templateId=" + templateId);
            if (rpcResult != null) {
                rpcResult.result(false);
                return null;
            }
        }


//        BuildingModel fatherBuingModel = avatarEntity.getBuildingById(buildingId);
//        // 当前建筑父建筑ID列表
//        LList correspondingBuildingList = template.getList("correspondingBuilding");
//        // 如果创建的建筑有归属建筑但是buildId为空,或者创建的建筑没有归属ID 但是发来的不是空的时候(客户端发来消息错误)
//        if ((correspondingBuildingList != null && StringUtils.isEmpty(buildingId))
//                || (correspondingBuildingList == null && !StringUtils.isEmpty(buildingId))) {
//            rpcResult.result(false);
//            return null;
//        }
//
//        if (correspondingBuildingList != null && !StringUtils.isEmpty(buildingId)) {
//            if (fatherBuingModel == null) {
//                rpcResult.result(false);
//                return null;
//            }
//            int templateFatherID = fatherBuingModel.getTemplateId();
//            // 当前创建的建筑和父建筑不匹配
//            if (!correspondingBuildingList.contains(templateFatherID)) {
//                rpcResult.result(false);
//                return null;
//            }
//
//        }

        if (!template.getBool("canBuild", true)) {
            rpcResult.result(false);
            return null;
        }

        int curBuildCanBuildingCount = BuildingUtils.getBuildingMaxCountByTemplateIdAndAvatarId(templateId, avatarEntity.getAvatarModel().getLv());
        if (avatarEntity.getBuildingCountByTemplateId(templateId) >= curBuildCanBuildingCount) {
            if (rpcResult != null) {
                rpcResult.result(false);
            }
            return null;
        }


//        if (correspondingBuildingList != null && !StringUtils.isEmpty(buildingId)) {
//            // 将父建筑设为可建造区域
//            avatarEntity.setBuildingTiledCollisionData(fatherBuingModel, true);
//
//            // 将父建筑下的建筑区域设置成不可建造区域
//            List<BuildingModel> buildingHaveBuildList = avatarEntity.getBuildListFromOwnerBuilding(buildingId);
//            for (BuildingModel bmChild : buildingHaveBuildList) {
//                avatarEntity.setBuildingTiledCollisionData(bmChild, false);
//            }
//
//        }
//
        if (!BuildingUtils.checkCanBuild(avatarEntity, x, y, template, null, false, false)) {
            if (rpcResult != null) {
                rpcResult.result(false);
            }
            return null;
        }

        if (!avatarEntity.canUseBuildingFromBuildingStore(templateId, 1)) {
            int moneyType = template.getInt("needType", 0);
            int moneyValue = template.getInt("needMoney", 0);
            if (moneyType == GongConstants.CONSUME_TYPE_1) {
                if (!avatarEntity.consumeMoney(moneyValue, GongMoneyLogConstants.LOG_MONEY_CHANGE_CREATE_BUILD, templateId,
                        0)) {
                    GongCommonNotify.notifyMsg(avatarEntity.getChannelContext(), GongConstants.MSG_ID_MONEY_NOT_ENOUGH);
                    return null;
                }
            } else if (moneyType == GongConstants.CONSUME_TYPE_2) {
                if (!avatarEntity.consumeGold(moneyValue, GongGoldLogConstants.LOG_GOLD_CHANGE_BUILDING_CREATE, "")) {
                    GongCommonNotify.notifyMsg(avatarEntity.getChannelContext(), GongConstants.MSG_ID_GOLD_NOT_ENOUGH);
                    return null;
                }
            }
        } else {
            avatarEntity.useBuildingFromBuildingStore(templateId, 1);
        }


        BuildingModel bm = new BuildingModel();
        bm.setId(IdProvider.genId(GongConstants.ID_TYPE_BUILDING));
        bm.setTemplateId(templateId);
        bm.setX(x);
        bm.setY(y);
        bm.setFlip(flip);
        bm.setCreateTime(System.currentTimeMillis());
        bm.setScale(scale);
        bm.setCanMoveBuild(canMoveBuild);
        bm.setCanFlip(canFlip);
        bm.setCanDelete(delete);
        bm.setOwnerBuildingId(buildingId);

        bm.addCurbuildHaveTemplateIDMap(templateId, 1);

        // 建筑繁荣度
        long prosperityVal = template.getInt("prosperity", 0);
        incrBuildingProsperity(avatarEntity, prosperityVal, GongProsperityiLogConstants.LOG_PROSPERITY_CHANGE_FRD_CREATE_BUILDING,
                (int) avatarEntity.getDbId(), 0, GongConstants.PROSPERITY_TYPE_GUDING_1);

        if (template.containsKey("typesOfManufacturing")) {
            // 矿洞和纺织机默认开启的槽
            int openNumber = SysConstData.data.getInt("MATERIAL_START_NUM", 1);
            bm.setOpenMakeMaterialNumber(openNumber);
        }

        avatarEntity.addBuilding(bm);
        avatarEntity.setBuildingTiledCollisionData(bm, false);

            List<BuildingModel> list = new ArrayList<BuildingModel>();
            list.add(bm);

            InstanceChangeVO addBm = new InstanceChangeVO();
            addBm.setAddList(list);

            List<InstanceChangeVO> ret = new ArrayList<InstanceChangeVO>();
            ret.add(addBm);

            BuildingUtils.notifyBuildingChanges(avatarEntity, ret);

//            String op = GongRpcConstants.RES_USER_CHEWEI_CHANGE;
//            entity.notify(op, new InstanceChangeVO(null, cheWeiList, null));

//            MissionManager.getInstance().missionScriptTrigger(entity, MissionHelper.JIANZAO_TRIGGER, null,
//            new Object[]{template.getInt("type", 0), templateId});

        int count = avatarEntity.getBuildingCountByTemplateId(templateId);
//            MissionManager.getInstance().missionScriptTrigger(entity, MissionHelper.JIANZAO_TRIGGER2, null,
//                    new Object[] { template.getInt("type", 0), templateId, count });

        avatarEntity.notify(GongRpcConstants.RES_USER_AVATAR_CHANGE, new AvatarVO(avatarEntity));

        rpcResult.result(true);

        return bm;
    }

    /**
     * 得到建筑列表
     * dbId 为0则为查询自己的建筑列表
     * dbId 和 otherAvatarId 为查询其它人的传的id
     * @param avatarEntity
     * @param rpcResult
     * @param dbId
     * @param otherAvatarId
     */
    @TestInterface(66)
    public void findBuildingList(final AvatarEntity avatarEntity, final RpcResult rpcResult, int dbId,String otherAvatarId) {
        if (dbId <= 0) {
            rpcResult.result(new Object[]{true, avatarEntity.getAvatarModel().getBuildingList()});
        } else {
            if (StringUtils.isBlank(otherAvatarId)) {
                rpcResult.result(new Object[]{false});
                return;
            }
            AvatarEntity otherAvatar = (AvatarEntity) gasManager.getEntityManager().getEntity(otherAvatarId);
            if (otherAvatar != null) {
                rpcResult.result(new Object[]{true, otherAvatar.getAvatarModel().getBuildingList()});
            } else {
                gasManager.executeRawCommand(GongDbConstants.CMD_FIND_BUILDING_LIST_BY_PARENT_ID, new Object[]{dbId},
                        new RawCommandCallback() {
                            @Override
                            public void onResult(Object result, int num, String error) {
                                if (result != null ) {
                                    rpcResult.result(new Object[]{true,result});
                                } else {
                                    rpcResult.result(new Object[]{false});
                                }
                            }
                        });
            }
        }
    }



    /**
     * 建筑升级
     * @param entity
     * @param rpcResult
     * @param buildingId
     */
    public void upgradeBuilding(final AvatarEntity entity, final RpcResult rpcResult, final String buildingId) {

        if (!canUpgrade(entity, buildingId)) {
            rpcResult.result(false);
            return;
        }

        BuildingModel building = entity.getBuildingById(buildingId);
        LMap buildingTemplate = building.getTemplate();

        if (buildingTemplate==null) {
            rpcResult.result(false);
            return;
        }
        LMap map = BuildingupData.data.getMap(building.getTemplateId());
        if (map==null) {
            rpcResult.result(false);
            return;
        }
        Integer nextTemplateId = map.getInt("upgradeId");
        if (nextTemplateId==null) {
            GuardianLogger.error("BuildingService upgradeBuilding lv already max",entity,buildingId);
            rpcResult.result(false);
            return;
        }
        LMap nextTemplate = BuildingData.data.getMap(nextTemplateId);
        if (nextTemplate==null) {
            GuardianLogger.error("BuildingService upgradeBuilding nextTemplate is null",entity,building,nextTemplate);
            rpcResult.result(false);
            return;
        }
        Integer gold = map.getInt("gold",0);


        building.setTemplateId(nextTemplateId);
        building.setLv(nextTemplate.getInt("lv"));

        List<InstanceChangeVO> ret = new ArrayList<>(1);

        // 繁荣度修改
        long oldProsperityVal = buildingTemplate.getInt("prosperity", 0);
        long newProsperityVal = nextTemplate.getInt("prosperity", 0);
        long endProsperityVal = newProsperityVal - oldProsperityVal;

        incrBuildingProsperity(entity, endProsperityVal,GongProsperityiLogConstants.LOG_PROSPERITY_CHANGE_FRD_UPGRADE_BUILDING,
                (int) entity.getDbId(), 0, GongConstants.PROSPERITY_TYPE_GUDING_1);

        // 推送改变
        List<BuildingModel> list = new ArrayList<>();
        list.add(building);
        InstanceChangeVO addBm = new InstanceChangeVO();
        addBm.setUpdateList(list);
        ret.add(addBm);
        BuildingUtils.notifyBuildingChanges(entity, ret);
        entity.notify(GongRpcConstants.RES_USER_AVATAR_CHANGE, new AvatarVO(entity));
        rpcResult.result(true);
    }

    private boolean canUpgrade(AvatarEntity entity, String buildingId) {
        if (StringUtils.isBlank(buildingId)) {
            GuardianLogger.error("BuildingService canUpgrade buildingId is blank", entity);
            return false;
        }
        BuildingModel building = entity.getBuildingById(buildingId);
        if (building == null) {
            GuardianLogger.error("BuildingService canUpgrade building is null", entity);
            return false;
        }
        int buildingLv = building.getLv();
        if (BuildingUtils.isHome(building.getTemplateId())) {
            if (buildingLv == 1) {
                return true;
            }
            List<BuildingModel> initBuilding = BuildingUtils.getInitBuilding(entity);
            if (CollectionUtils.isEmpty(initBuilding)) {
                GuardianLogger.error("BuildingService canUpgrade initBuilding is empty",entity);
                return false;
            }
            for (BuildingModel buildingModel : initBuilding) {
                if (buildingModel.getLv()<buildingLv) {
                    GuardianLogger.error("BuildingService canUpgrade building buildingLv < needLv",entity,buildingModel.getLv(),buildingLv);
                    return false;
                }
            }
            return true;

        } else {
            BuildingModel home = BuildingUtils.getHome(entity);
            if (buildingLv >= home.getLv()) {
                GuardianLogger.info("BuildingService canUpgrade fail",entity,buildingLv,home.getLv());
                return false;
            }
            return true;
        }
    }

    /**
     * 移动建筑
     *
     * @param avatarEntity
     * @param rpcResult
     * @param buildingId
     * @param x
     * @param y
     */
    public void moveBuilding(final AvatarEntity avatarEntity, final RpcResult rpcResult, final String buildingId, final int x,
                             final int y) {
        BuildingModel bm = avatarEntity.getBuildingById(buildingId);

        if (bm == null) {
            rpcResult.result(false);
            return;
        }

        // 是否可以移动
        if (!bm.isCanMoveBuild()) {
            rpcResult.result(false);
            return;
        }

        LMap template = BuildingData.data.getMap(bm.getTemplateId());
        if (template == null) {
            GuardianLogger.error("building templateId not exist! templateId=" + bm.getTemplateId());
            rpcResult.result(false);
            return;
        }

        if (!template.getBool("canMoveBuild", true)) {
            rpcResult.result(false);
            return;
        }

        // 如果当前建筑有父建筑将父建筑区域设置成可建造
        BuildingModel fatherBuingModel = avatarEntity.getBuildingById(bm.getOwnerBuildingId());
        List<BuildingModel> buildingHaveBuildList = avatarEntity.getBuildListFromOwnerBuilding(buildingId);
        if (fatherBuingModel != null) {
            avatarEntity.setBuildingTiledCollisionData(fatherBuingModel, true);

            // 将父建筑下的建筑区域设置成不可建造区域
            for (BuildingModel bmChild : buildingHaveBuildList) {
                if (bmChild.getId().equals(buildingId)) {
                    continue;
                }
                avatarEntity.setBuildingTiledCollisionData(bmChild, false);
            }
        } else {
            for (BuildingModel bmChild : buildingHaveBuildList) {
                avatarEntity.setBuildingTiledCollisionData(bmChild, true);
            }
        }

        if (!BuildingUtils.checkCanBuild(avatarEntity, x, y, template, bm, false, true)) {
            avatarEntity.setBuildingTiledCollisionData(fatherBuingModel, false);
            rpcResult.result(false);
            return;
        }

        // 推送改变
        List<BuildingModel> list = new ArrayList<BuildingModel>();

        for (BuildingModel bmChild : buildingHaveBuildList) {
            int changeX = bm.getX() - x;
            int changeY = bm.getY() - y;
            int newX = bmChild.getX() - changeX;
            int newY = bmChild.getY() - changeY;
            bmChild.setX(newX);
            bmChild.setY(newY);
            list.add(bmChild);
        }

        avatarEntity.setBuildingTiledCollisionData(bm, true);
        bm.setX(x);
        bm.setY(y);
        avatarEntity.setBuildingTiledCollisionData(bm, false);
        avatarEntity.setBuildingTiledCollisionData(fatherBuingModel, false);

        list.add(bm);

        InstanceChangeVO addBm = new InstanceChangeVO();
        addBm.setUpdateList(list);

        List<InstanceChangeVO> ret = new ArrayList<InstanceChangeVO>();
        ret.add(addBm);

        BuildingUtils.notifyBuildingChanges(avatarEntity, ret);

//        MissionManager.getInstance().missionScriptTrigger(entity, MissionHelper.YIDONG_JIANZHU_TRIGGER, null, null);

        avatarEntity.notify(GongRpcConstants.RES_USER_AVATAR_CHANGE, new AvatarVO(avatarEntity));

        rpcResult.result(true);

    }

    public void flipBuilding(final AvatarEntity avatarEntity, final RpcResult rpcResult, final String buildingId) {
        BuildingModel bm = avatarEntity.getBuildingById(buildingId);
        if (bm == null) {
            rpcResult.result(false);
            return;
        }

        LMap template = bm.getTemplate();
        if (template == null) {
            GuardianLogger.error("building templateId not exist! templateId=" + bm.getTemplateId());
            rpcResult.result(false);
            return;
        }

        if (!template.getBool("canFlip", true)) {
            rpcResult.result(false);
            return;
        }

        if (!bm.isCanFlip()) {
            rpcResult.result(false);
            return;
        }

        // 如果当前建筑有父建筑将父建筑区域设置成可建造
        BuildingModel fatherBuingModel = avatarEntity.getBuildingById(bm.getOwnerBuildingId());
        if (fatherBuingModel != null) {
            avatarEntity.setBuildingTiledCollisionData(fatherBuingModel, true);

            // 将父建筑下的建筑区域设置成不可建造区域
            List<BuildingModel> buildingHaveBuildList = avatarEntity.getBuildListFromOwnerBuilding(buildingId);
            for (BuildingModel bmChild : buildingHaveBuildList) {
                if (bmChild.getId().equals(buildingId)) {
                    continue;
                }
                avatarEntity.setBuildingTiledCollisionData(bmChild, false);
            }
        }

        if (!BuildingUtils.checkCanBuild(avatarEntity, bm.getX(), bm.getY(), template, bm, true, false)) {
            if (rpcResult != null) {
                avatarEntity.setBuildingTiledCollisionData(fatherBuingModel, false);
                rpcResult.result(false);
            }
            return;
        }

        avatarEntity.setBuildingTiledCollisionData(bm, true);
        bm.setFlip(!bm.isFlip());
        avatarEntity.setBuildingTiledCollisionData(bm, false);
        avatarEntity.setBuildingTiledCollisionData(fatherBuingModel, false);

        // 推送改变
        List<BuildingModel> list = new ArrayList<BuildingModel>();
        list.add(bm);

        InstanceChangeVO addBm = new InstanceChangeVO();
        addBm.setUpdateList(list);

        List<InstanceChangeVO> ret = new ArrayList<InstanceChangeVO>();
        ret.add(addBm);

        BuildingUtils.notifyBuildingChanges(avatarEntity, ret);

        rpcResult.result(true);
    }

    /**
     * 拆除建筑 不是从数据库删除
     *
     * @param avatarEntity
     * @param rpcResult
     * @param buildingId
     */
    @TestInterface(222)
    public void removeBuilding(final AvatarEntity avatarEntity, final RpcResult rpcResult, final String buildingId) {
        BuildingModel bm = avatarEntity.getBuildingById(buildingId);
        if (bm == null) {
            rpcResult.result(false);
            return;
        }
        LMap template = BuildingData.data.getMap(bm.getTemplateId());
        if (template == null) {
            GuardianLogger.error("building templateId not exist! templateId=" + bm.getTemplateId());
            rpcResult.result(false);
            return;
        }

        if (!template.getBool("delete", true)) {
            rpcResult.result(false);
            return;
        }

        if (!bm.isCanDelete()) {
            rpcResult.result(false);
            return;
        }

        avatarEntity.setBuildingTiledCollisionData(bm, true);

        avatarEntity.addBuildingToBuildingStore(bm.getTemplateId(), 1);

        removeBuilding(avatarEntity, bm.getId());

        // 建筑繁荣度
        long prosperityVal = template.getInt("prosperity", 0);
//        incrBuildingProsperity(entity, -prosperityVal, GongLogConstants.LOG_CURRENCY_CHANGE_FRD_HS_BUILD,
//                (int) entity.getDbId(), 0, GongConstants.PROSPERITY_TYPE_GUDING_1);

        // 推送改变
        List<BuildingModel> list = new ArrayList<BuildingModel>();
        list.add(bm);

        InstanceChangeVO addBm = new InstanceChangeVO();
        addBm.setDeleteList(list);

        List<InstanceChangeVO> ret = new ArrayList<InstanceChangeVO>();
        ret.add(addBm);

        BuildingUtils.notifyBuildingChanges(avatarEntity, ret);

        avatarEntity.notify(GongRpcConstants.RES_USER_AVATAR_CHANGE, new AvatarVO(avatarEntity));
        rpcResult.result(true);
    }

//    public void buildingRemoveAll(AvatarEntity entity, RpcResult rpcResult) {
//        List<BuildingModel> buildingList = entity.getBuildingList();
//        List<BuildingModel> removeBuildingList = new ArrayList<BuildingModel>();
//        // 推送改变
//        for (BuildingModel building : buildingList) {
//            LMap buildingMap = BuildingData.data.getMap(building.getTemplateId());
//            if (buildingMap == null) {
//                continue;
//            }
//            int buildingType = buildingMap.getInt("type", 0);
//            int buildingKind = buildingMap.getInt("kind", 0);
//            if (building.isCanMoveBuild()
//                    && ((buildingType == 1 && ((buildingKind == 2) || (buildingKind == 3))) || (buildingType == 3))) {
//                removeBuildingList.add(building);
//            }
//        }
//        for (BuildingModel buildingModel : removeBuildingList) {
//            buildingRemove(entity, buildingModel);
//        }
//        rpcResult.result(new Object[]{true});
//    }

//    private boolean buildingRemove(AvatarEntity avatarEntity, BuildingModel bm) {
//        LMap template = bm.getTemplate();
//        if (template == null) {
//            GuardianLogger.error("building templateId not exist! templateId=" + bm.getTemplateId());
//            return false;
//        }
//
//        if (!template.getBool("delete", true)) {
//            return false;
//        }
//
//        if (!bm.isCanDelete()) {
//            return false;
//        }
//
//        avatarEntity.setBuildingTiledCollisionData(bm, true);
//
//        avatarEntity.addBuildingToBuildingStore(bm.getTemplateId(), 1);
//
//        removeBuilding(avatarEntity, bm.getId());
//
//        // 建筑繁荣度
//        long prosperityVal = template.getInt("prosperity", 0);
////        incrBuildingProsperity(entity, -prosperityVal, GongLogConstants.LOG_CURRENCY_CHANGE_FRD_HS_BUILD,
////                (int) entity.getDbId(), 0, GongConstants.PROSPERITY_TYPE_GUDING_1);
//
//        return true;
//    }

    public void removeBuilding(AvatarEntity avatarEntity, String buildingId) {
        BuildingModel bm = avatarEntity.removeBuilding(buildingId);
        if (bm == null) {
            GuardianLogger.warn("building not exists! building=", buildingId);
            return;
        }
    }

    /**
     * 建筑开启槽位
     *
     * @param avatarEntity
     * @param buildingId
     * @return
     */
    public boolean openMakeMaterialNumber(AvatarEntity avatarEntity, String buildingId) {
        if (StringUtils.isEmpty(buildingId)) {
            return false;
        }

        BuildingModel build = avatarEntity.getBuildingById(buildingId);
        if (build == null) {
            return false;
        }

        // 获取建筑已开启槽个数
        int nBuildingAlreadyOpenCount = build.getOpenMakeMaterialNumber();
        // 可开启的总个数
        int nTotalCanOpenCount = SysConstData.data.getInt("MATERIAL_MAX_NUM");
        if (nBuildingAlreadyOpenCount >= nTotalCanOpenCount) {
            // 已经全部开启
            return false;
        }
        LMap buildTemp = build.getTemplate();
        if (buildTemp == null) {
            return false;
        }
        int makeType = buildTemp.getInt("typesOfManufacturing", 0);
//        int consumeId = GongConstants.CONSUME_ID_OPEN_BUILD_MATERIAL;
//        if (makeType == GongConstants.CAL_LIAO_TYPE_KUANG_SHI) {
//            consumeId = GongConstants.CONSUME_ID_OPEN_BUILD_MATERIAL;
//        } else if (makeType == GongConstants.CAL_LIAO_TYPE_CLOTH) {
//            consumeId = GongConstants.CONSUME_ID_OPEN_BUILD_MATERIAL;
//        } else if (makeType == GongConstants.CAL_LIAO_TYPE_RAN_LIAO) {
//            consumeId = GongConstants.CONSUME_ID_OPEN_BUILD_MATERIAL;
//        } else if (makeType == GongConstants.CAL_LIAO_TYPE_EXP) {
//            consumeId = GongConstants.CONSUME_ID_OPEN_BUILD_MONEY_EXP;
//        } else if (makeType == GongConstants.CAL_LIAO_TYPE_MONEY) {
//            consumeId = GongConstants.CONSUME_ID_OPEN_BUILD_MONEY_EXP;
//        } else if (makeType == GongConstants.CAL_LIAO_TYPE_YUGOU) {
//            consumeId = GongConstants.CONSUME_ID_OPEN_BUILD_YU_GOU;
//        }

        int consumeId = getConsumeId(makeType);

        // 开启当前槽需要的gold
        LMap consumeData = ConsumeData.data.getMap(consumeId);
        final long consumeRmb = consumeData.getList("consume").getLong(nBuildingAlreadyOpenCount);
        if (!avatarEntity.canConsumeGold(consumeRmb)) {
            GongCommonNotify.notifyMsg(avatarEntity.getChannelContext(), GongConstants.MSG_ID_GOLD_NOT_ENOUGH);
            return false;
        }

        // 扣元宝
//        entity.consumeGold(consumeRmb, GongLogConstants.LOG_GOLD_CHANGE_OPEN_MAKE_MATERIAL_NUMBER, "");
        // 开启槽位
        build.setOpenMakeMaterialNumber(nBuildingAlreadyOpenCount + 1);

        // 推送改变
        List<BuildingModel> list = new ArrayList<BuildingModel>();
        list.add(build);

        InstanceChangeVO addBm = new InstanceChangeVO();
        addBm.setUpdateList(list);

        List<InstanceChangeVO> ret = new ArrayList<InstanceChangeVO>();
        ret.add(addBm);

        BuildingUtils.notifyBuildingChanges(avatarEntity, ret);

        avatarEntity.notify(GongRpcConstants.RES_USER_AVATAR_CHANGE, new AvatarVO(avatarEntity));
        return true;
    }

    private int getConsumeId(int makeType) {
        return 0;
    }


    public void incrBuildingProsperity(AvatarEntity avatarEntity, long val, int sourceType, int sourceId1, int sourceId2,
                                       int type) {
        if (val == 0) {
            return;
        }

        long oldVal =avatarEntity.getProsperity();
        avatarEntity.increaseProsperity(val, type);

//        if (sourceType != 0) {
//            entity.addNotReallyCurrencyValueChangeLog(entity, GongConstants.ITEM_ID_FAN_RONG_DU, oldVal,
//                    entity.getProsperity(), sourceType, sourceId1, sourceId2);
//        }
//
//        MissionManager.getInstance().missionScriptTrigger(entity, MissionHelper.FANRONGDU_TRIGGER, null,
//                new Object[]{entity.getProsperity()});
//
//        userService.rankListKeyValueChange(GongDbConstants.CMD_PROSPERITY_CHANGE, entity.getAvatarModel().getId(),
//                entity.getProsperity());
    }


//    public boolean timeBuildingRisiStar(AvatarEntity avatarEntity, String buildingId) {
//        BuildingModel buildingModel = entity.getBuildingById(buildingId);
//        if (buildingModel == null) {
//            return false;
//        }
//        LMap template = buildingModel.getTemplate();
//        if (template == null) {
//            GuardianLogger.error("building templateId not exist! templateId=" + buildingModel.getTemplateId());
//            return false;
//        }
//
//        LList lvList = SysConstData.data.getList("JIANZHU_SHENGXING_LV");
//        if (buildingModel.getBuildStar() + 1 > lvList.size()) {
//            // 建筑星级，超过策划配置的星级
//            return false;
//        }
//        buildingModel.setBuildStar(buildingModel.getBuildStar() + 1);
//        // 推送改变
//        List<BuildingModel> list = new ArrayList<BuildingModel>();
//        list.add(buildingModel);
//
//        InstanceChangeVO addBm = new InstanceChangeVO();
//        addBm.setUpdateList(list);
//
//        List<InstanceChangeVO> ret = new ArrayList<InstanceChangeVO>();
//        ret.add(addBm);
//
//        BuildingUtils.notifyBuildingChanges(entity, ret);
//
//        return true;
//    }

//    public void goldUpgradeStar(AvatarEntity entity, RpcResult rpcResult, String buildingId, int type) {
//        BuildingModel buildingModel = entity.getBuildingById(buildingId);
//        if (buildingModel == null) {
//            rpcResult.result(false);
//            return;
//        }
//        LMap map = null;
//        if (type == 1) {
////            map = ConsumeData.data.getMap(GongConstants.HOUSE_RISI_STAR_CONSUME_ID);
//        } else {
////            map = ConsumeData.data.getMap(GongConstants.FISH_HOUSE_RISI_STAR_CONSUME_ID);
//        }
//        if (map == null) {
//            rpcResult.result(false);
//            return;
//        }
//        LList consumeList = map.getList("consume");
//        if (consumeList.isEmpty()) {
//            rpcResult.result(false);
//            return;
//        }
//        LList lvList = SysConstData.data.getList("JIANZHU_SHENGXING_LV");
//        if (lvList.isEmpty()) {
//            rpcResult.result(false);
//            return;
//        }
//        if (buildingModel.getBuildStar() + 1 > lvList.size()) {
//            rpcResult.result(false);
//            return;
//        }
//        // 判断金币够不够
//        for (int i = 0; i < lvList.size(); i++) {
//            int starLv = (Integer) lvList.get(i);
//            if (buildingModel.getBuildStar() == starLv - 1) {
//                int consumeGlod = (Integer) consumeList.get(i);
//                if (!entity.canConsumeGold(consumeGlod)) {
//                    rpcResult.result(false);
//                    return;
//                } else {
////                    boolean glodMove = entity.consumeGold(consumeGlod, GongLogConstants.LOG_GOLD_CHANGE_RISING_STAR,
////                            "");
////                    if (!glodMove) {
////                        GongCommonNotify.notifyMsg(entity.getChannelContext(), GongConstants.MSG_ID_GOLD_NOT_ENOUGH);
////                        rpcResult.result(false);
////                        return;
////                    }
//
//                }
//            }
//        }
//        buildingModel.setBuildStar(buildingModel.getBuildStar() + 1);
//        if (type == 1)
//            buildingModel.setRisingStarTime(0);
//        else
//            buildingModel.setFishExp(0);
//        // 推送改变
//        List<BuildingModel> list = new ArrayList<BuildingModel>();
//        list.add(buildingModel);
//
//        InstanceChangeVO addBm = new InstanceChangeVO();
//        addBm.setUpdateList(list);
//
//        List<InstanceChangeVO> ret = new ArrayList<InstanceChangeVO>();
//        ret.add(addBm);
//
//        BuildingUtils.notifyBuildingChanges(entity, ret);
//        entity.notify(GongRpcConstants.RES_USER_AVATAR_CHANGE, new AvatarVO(entity));
//        rpcResult.result(true);
//    }

}

