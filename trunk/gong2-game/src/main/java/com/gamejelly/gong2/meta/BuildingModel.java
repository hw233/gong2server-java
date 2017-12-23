package com.gamejelly.gong2.meta;

import com.gamejelly.gong2.config.data.BuildingData;
import com.gamejelly.gong2.config.data.base.LList;
import com.gamejelly.gong2.config.data.base.LMap;
import com.gamejelly.gong2.meta.base.ChildModel;
import com.gamejelly.gong2.utils.GongRuntimeException;
import com.hadoit.game.common.framework.dao.annotation.Column;
import com.hadoit.game.common.framework.dao.annotation.Table;
import com.hadoit.game.common.framework.dao.convert.impl.CollectionNumberOrStringColumnConvert;
import com.hadoit.game.common.framework.dao.convert.impl.MapNumberOrStringColumnConvert;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: liuliwei
 * Date: 2017-12-04
 * Time: 下午3:10
 * Description: 建筑
 */
@Table(recursion = true)
public class BuildingModel extends ChildModel {
    private static final long serialVersionUID = 1L;

    /**
     * 表示唯一的id
     */
    @Column(value = "uid")
    private String id;

    private int templateId;

    @Column(ignore = true)
    private String buildname;

    /**
     * 修葺的ID
     */
    private int templateUpgradeId;

    /**
     * x坐标
     */
    private int x;

    /**
     * y坐标
     */
    private int y;

    /**
     * 旋转
     */
    private boolean flip;

    private long createTime;

    /**
     * openMakeMaterialNumber 开启制作材料的槽的数量
     */
    private int openMakeMaterialNumber;

    /**
     * buildMaterials 当前建筑生产材料数组
     */
    @Column(convertClazz = CollectionNumberOrStringColumnConvert.class)
    private List<String> buildMaterials;

    /**
     * scale 当前建筑缩放比例
     */
    private float scale;

    /**
     * 建筑是否可以移动
     */
    private boolean canMoveBuild;

    /**
     * 建筑是否可以翻转
     */
    private boolean canFlip;

    /**
     * 建筑是否可以拆除
     */
    private boolean canDelete;

    /**
     * 建筑上食物foodMap id 数量
     */

    /**
     * 当前建筑归属建筑ownerBuildingId
     */
    private String ownerBuildingId;

    @Column(convertClazz = MapNumberOrStringColumnConvert.class, length = 65535)
    private Map<Integer, Integer> foodMap = new HashMap<Integer, Integer>();

    /**
     * 当亲建筑拥有的模版ID
     */
    @Column(convertClazz = MapNumberOrStringColumnConvert.class, length = 65535)
    private Map<Integer, Integer> curbuildHaveTemplateIDMap = new HashMap<Integer, Integer>();

    /*
     * 当前建筑拥有的星级
     */
    private int buildStar;

    /*
     * 升星时间
     */
    private long risingStarTime;

    private int fishExp;// 钓鱼 熟练度

    private int lv =1;

    public long getRisingStarTime() {
        return risingStarTime;
    }

    public void setRisingStarTime(long risingStarTime) {
        this.risingStarTime = risingStarTime;
    }

    public int getBuildStar() {
        return buildStar;
    }

    public void setBuildStar(int buildStar) {
        this.buildStar = buildStar;
    }

    public LMap getTemplate() {
        LMap map = BuildingData.data.getMap(templateId);
        if (map==null) {
            GuardianLogger.error("Building data miss,templateId is ",templateId);
        }
        return map;
    }

    public Integer[] getBuildingSize() {
        if (getTemplate() == null) {
            return null;
        }

        LList buildingSize = getTemplate().getList("size");
        if (buildingSize.size() != 2) {
            throw new GongRuntimeException("BuildingData is wrong v = " + templateId);
        }
        Integer[] size = buildingSize.toIntArray();
        return size;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isFlip() {
        return flip;
    }

    public void setFlip(boolean flip) {
        this.flip = flip;
    }

    public int getOpenMakeMaterialNumber() {
        return openMakeMaterialNumber;
    }

    public void setOpenMakeMaterialNumber(int openMakeMaterialNumber) {
        this.openMakeMaterialNumber = openMakeMaterialNumber;
    }

    public List<String> getBuildMaterials() {
        return buildMaterials;
    }

    public void setBuildMaterials(List<String> buildMaterials) {
        this.buildMaterials = buildMaterials;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public boolean isCanMoveBuild() {
        return canMoveBuild;
    }

    public void setCanMoveBuild(boolean canMoveBuild) {
        this.canMoveBuild = canMoveBuild;
    }

    public boolean isCanFlip() {
        return canFlip;
    }

    public void setCanFlip(boolean canFlip) {
        this.canFlip = canFlip;
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }

    public Map<Integer, Integer> getFoodMap() {
        return foodMap;
    }

    public void setFoodMap(Map<Integer, Integer> foodMap) {
        this.foodMap = foodMap;
    }

    public Map<Integer, Integer> getCurbuildHaveTemplateIDMap() {
        if (curbuildHaveTemplateIDMap == null) {
            curbuildHaveTemplateIDMap = new HashMap<Integer, Integer>();
        }
        return curbuildHaveTemplateIDMap;
    }

    public void setCurbuildHaveTemplateIDMap(Map<Integer, Integer> curbuildHaveTemplateIDMap) {
        this.curbuildHaveTemplateIDMap = curbuildHaveTemplateIDMap;
    }

    public void addCurbuildHaveTemplateIDMap(int key, int value) {
        getCurbuildHaveTemplateIDMap().put(key, value);
    }

    public int getTemplateUpgradeId() {
        return templateUpgradeId;
    }

    public void setTemplateUpgradeId(int templateUpgradeId) {
        this.templateUpgradeId = templateUpgradeId;
    }

    public String getOwnerBuildingId() {
        return ownerBuildingId;
    }

    public void setOwnerBuildingId(String ownerBuildingId) {
        this.ownerBuildingId = ownerBuildingId;
    }

    public int getFishExp() {
        return fishExp;
    }

    public void setFishExp(int fishExp) {
        this.fishExp = fishExp;
    }

    public String getBuildname() {
        return buildname;
    }

    public void setBuildname(String buildname) {
        this.buildname = buildname;
    }

    public int getLv() {
        if (lv==0) {
            lv=1;
        }
        return lv;
    }

    public void setLv(int lv) {
        this.lv = lv;
    }

    public boolean isInitBuilding() {
        LMap map = getTemplate();
        return map.getInt("type", 0).equals(1);
    }
}
