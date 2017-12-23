package com.gamejelly.gong2.utils;

import com.gamejelly.gong2.config.data.BuildingData;
import com.gamejelly.gong2.config.data.StartBuildingData;
import com.gamejelly.gong2.config.data.SysConstData;
import com.gamejelly.gong2.config.data.base.LList;
import com.gamejelly.gong2.config.data.base.LMap;
import com.gamejelly.gong2.gas.entity.AvatarEntity;
import com.gamejelly.gong2.gas.service.user.ItemService;
import com.gamejelly.gong2.meta.BuildingModel;
import com.gamejelly.gong2.vo.InstanceChangeVO;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: liuliwei
 * Date: 2017-12-08
 * Time: 下午1:37
 * Description: 建筑工具类
 */
public class BuildingUtils {

    /**
     * 根据人物等级和模板id得到
     * 建筑最大数量
     * @param templateId 建筑id
     * @param avatarLv 玩家等级
     * @return
     */
    public static int getBuildingMaxCountByTemplateIdAndAvatarId(int templateId, int avatarLv) {

        LMap buildingLMap = BuildingData.data.getMap(templateId);

        if (buildingLMap == null) {
            return 0;
        }
        int curBuildingMaxCount = buildingLMap.getInt("maxCount", 0);
        int curBuildCanBuildingCount = 0;
        if (buildingLMap.containsKey("maxCountFormula")) {
            int curBuildingLv = buildingLMap.getInt("lv");
            int curBuildingFormulaID = buildingLMap.getInt("maxCountFormula");
            curBuildCanBuildingCount = FormulaHelper.calcSFormulaNumber(curBuildingFormulaID,
                    GongUtils.createHashMap("lv", avatarLv, "buildLv", curBuildingLv)).intValue();
            if (curBuildCanBuildingCount > curBuildingMaxCount) {
                curBuildCanBuildingCount = curBuildingMaxCount;
            }
            if (curBuildCanBuildingCount <= 0) {
                curBuildCanBuildingCount = 0;
            }

        } else {
            curBuildCanBuildingCount = curBuildingMaxCount;
        }
        return curBuildCanBuildingCount;
    }


    /**
     * 推送建筑更改
     * @param entity
     * @param changes
     * @return
     */
    public static boolean notifyBuildingChanges(AvatarEntity entity, List<InstanceChangeVO> changes) {
        if (CollectionUtils.isNotEmpty(changes)) {
            Set adds = new HashSet();
            Set updates = new HashSet();
            Set deletes = new HashSet();
            for (InstanceChangeVO p : changes) {
                if (p != null) {
                    if (p.getAddList() != null) {
                        adds.addAll(p.getAddList());
                    }
                    if (p.getUpdateList() != null) {
                        updates.addAll(p.getUpdateList());
                    }
                    if (p.getDeleteList() != null) {
                        deletes.addAll(p.getDeleteList());
                    }
                }
            }
            if (adds.size() > 0 || updates.size() > 0 || deletes.size() > 0) {
                entity.notify(GongRpcConstants.RES_USER_BUILDING_CHANGE, new InstanceChangeVO(adds, updates, deletes));
            }

            return true;
        }
        return false;
    }


    public static boolean checkCanBuild(AvatarEntity entity, int x, int y, LMap templateData, BuildingModel bm,
                                  boolean fromFlip, boolean fromMove) {
        LList size = templateData.getList("size");
        if (size.size() != 2) {
            return false;
        }

        if (bm != null) {
            entity.setBuildingTiledCollisionData(bm, true);
        }

        Integer[] size2 = size.toIntArray();
        int width = size2[0];
        int height = size2[1];

        if (fromFlip) {
            if (bm.isFlip()) {
                width = size2[0];
                height = size2[1];
            } else {
                width = size2[1];
                height = size2[0];
            }
        }

        if (fromMove) {
            if (bm.isFlip()) {
                width = size2[1];
                height = size2[0];
            } else {
                width = size2[0];
                height = size2[1];
            }
        }

        int tiledSideLen = SysConstData.data.getInt("TILED_SIDE_LEN",50);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int tempX = x - i;
                int tempY = y - j;

                // 边界判断
                if (tempX < 0 || tempY < 0 || tempX > tiledSideLen - 1 || tempY > tiledSideLen - 1) {
                    if (bm != null) {
                        entity.setBuildingTiledCollisionData(bm, false);
                    }
                    return false;
                }

                String key = entity.getTiledKey(tempX, tempY);
                if (!entity.getTiledCollisionState(key)) {
                    if (bm != null) {
                        entity.setBuildingTiledCollisionData(bm, false);
                    }
                    return false;
                }
            }
        }

        if (bm != null) {
            entity.setBuildingTiledCollisionData(bm, false);
        }
        return true;
    }

    public static void initBuilding(AvatarEntity avatarEntity) {
        ItemService is = GongUtils.getItemService();

        LList lstNewbuilding = StartBuildingData.data.valuesToList();
        List<BuildingModel> landFlowerBuildingList = new ArrayList<BuildingModel>();
        for (int i = 0; i < lstNewbuilding.size(); i++) {
            LMap obj = (LMap) lstNewbuilding.get(i);
            int templateId = obj.getInt("buildingTemplateId");
            LMap template = BuildingData.data.getMap(templateId);
            if (template == null) {
                GuardianLogger.error("building templateId not exist! templateId=" + templateId);
                return;
            }

            LList lstPos = obj.getList("pos");
            int x = lstPos.getInt(0, 0);
            int y = lstPos.getInt(1, 0);
            float scale = obj.getFloat("scale", 1.0f);
            boolean flip = obj.getBool("flip", false);
            boolean canMoveBuild = obj.getBool("canMoveBuild", false);
            boolean canFlip = obj.getBool("canFlip", false);
            boolean delete = obj.getBool("delete", false);
            boolean openSpace = false;
            // 储秀宫
            // 如果是新玩家，储秀宫需要开启坑位
//            if (templateId == GongConstants.BUILDING_ID_CHUXIUGONG) {
//                openSpace = true;
//            }

            // 种花
            // 如果是新玩家，并且ID是土壤就种上玫瑰花
//            if (bm != null && bm.getTemplateId() == GongConstants.BUILDING_ID_TURANG) {
//                landFlowerBuildingList.add(bm);
//            }

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
            bm.setOwnerBuildingId(null);

            bm.addCurbuildHaveTemplateIDMap(templateId, 1);

            // 建筑繁荣度
            long prosperityVal = obj.getInt("prosperity", 0);
//            incrBuildingProsperity(avatarEntity, prosperityVal, GongLogConstants.LOG_CURRENCY_CHANGE_FRD_CREATE_BUILD,
//                    (int) avatarEntity.getDbId(), 0, GongConstants.PROSPERITY_TYPE_GUDING_1);

            if (obj.containsKey("typesOfManufacturing")) {
                // 矿洞和纺织机默认开启的槽
                int openNumber = SysConstData.data.getInt("MATERIAL_START_NUM",1);
                bm.setOpenMakeMaterialNumber(openNumber);
            }

            avatarEntity.addBuilding(bm);
            avatarEntity.setBuildingTiledCollisionData(bm, false);

        }
//        if (landFlowerBuildingList.size() > 0) {
//            List<ItemModel> lstRose = entity.getItemsFromTemplateId(GongConstants.ITEM_ID_ROSE);
//            if (lstRose.size() > 0) {
//                String roseId = lstRose.get(0).getId();
//                String[] buildingIds = new String[landFlowerBuildingList.size()];
//                String[] flowerIds = new String[buildingIds.length];
//                for (int i = 0; i < landFlowerBuildingList.size(); i++) {
//                    buildingIds[i] = landFlowerBuildingList.get(i).getId();
//                    flowerIds[i] = roseId;
//                }
//                is.seedFlower(entity, null, buildingIds, flowerIds, true, true);
//            }
//        }
    }

    public static void afterBuildingChange(AvatarEntity avatarEntity) {

    }

    /**
     * 得到主城
     * @param avatarEntity
     * @return
     */
    public static BuildingModel getHome(AvatarEntity avatarEntity) {
        LList list = SysConstData.data.getList("MAIOR_CITY");
        Map<String, BuildingModel> buildingMap = avatarEntity.getBuildingMap();
        for (Object o : list) {
            if (o==null) {
                break;
            }
            List<BuildingModel> buildingsByTemplateId = avatarEntity.getBuildingsByTemplateId(((int) o));
            if (buildingsByTemplateId.size() == 1) {
                BuildingModel buildingModel = buildingsByTemplateId.get(0);
                return buildingModel;
            }
        }
        GuardianLogger.error("BuildingUtils getHome error,",avatarEntity);
        return null;
    }

    /**
     * 判断此模板id是不是主城
     * @param templateId
     * @return
     */
    public static boolean isHome(int templateId) {
        LList list = SysConstData.data.getList("MAIOR_CITY");
        if (CollectionUtils.isEmpty(list)) {
            GuardianLogger.error("BuildingUtil MAIOR_CITY is empty ");
            return false;
        }
        return list.contains(templateId);
    }

    /**
     * 得到初始建筑
     * @param avatarEntity
     * @return
     */
    public static List<BuildingModel> getInitBuilding(AvatarEntity avatarEntity) {
        List<BuildingModel> buildingList = avatarEntity.getBuildingList();
        Collection<BuildingModel> filter = Collections2.filter(buildingList, new Predicate<BuildingModel>() {
            @Override
            public boolean apply(BuildingModel buildingModel) {
                return buildingModel.isInitBuilding();
            }
        });
        return new ArrayList<>(filter);
    }

}
