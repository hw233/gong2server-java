package com.gamejelly.gong2.meta;

import java.util.HashMap;
import java.util.Map;

import com.gamejelly.gong2.config.data.GuankaBaseData;
import com.gamejelly.gong2.config.data.base.LMap;
import com.gamejelly.gong2.meta.base.ChildModel;
import com.gamejelly.gong2.utils.GongUtils;
import com.hadoit.game.common.framework.dao.annotation.Column;
import com.hadoit.game.common.framework.dao.annotation.Table;
import com.hadoit.game.common.framework.dao.convert.impl.MapNumberOrStringColumnConvert;
import com.hadoit.game.engine.core.protocol.json.JsonExclude;

@Table(recursion = true)
public class GuankaModel extends ChildModel {
    private static final long serialVersionUID = 1L;

    private int templateId;

    private int fightCount;

    private int totalFightCount;

    /**
     * 关卡进度(0没过任何难度, 1过了普通, 2过了困难, 3过了宗师)
     */
    private int plan;

    @JsonExclude("client")
    private long lastFightTime;

    @JsonExclude("client")
    private int gkbdNum; // 保底物品数量


    @Column(convertClazz = MapNumberOrStringColumnConvert.class, length = 65535)
    private Map<Integer, Integer> starMap = new HashMap<Integer, Integer>();


    @Column(convertClazz = MapNumberOrStringColumnConvert.class, length = 65535)
    private Map<Integer, Integer> boxMap = new HashMap<Integer, Integer>();

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    public int getFightCount() {
        return fightCount;
    }

    public void setFightCount(int fightCount) {
        this.fightCount = fightCount;
    }

    public long getLastFightTime() {
        return lastFightTime;
    }

    public void setLastFightTime(long lastFightTime) {
        this.lastFightTime = lastFightTime;
    }

    public int getPlan() {
        return plan;
    }

    public void setPlan(int plan) {
        this.plan = plan;
    }

    public int getGkbdNum() {
        return gkbdNum;
    }

    public void setGkbdNum(int gkbdNum) {
        this.gkbdNum = gkbdNum;
    }

    public boolean checkCanFight(int incrCount, int maxFightCount) {
        long curTime = System.currentTimeMillis();
        if (GongUtils.isSameDayForOffset(getLastFightTime(), curTime, 0)) {
            if (getFightCount() + incrCount > maxFightCount) {
                return false;
            }
        } else {
            setLastFightTime(curTime);
            setFightCount(0);
        }
        return true;
    }

    public void incrFightCount(int incrCount) {
        // 要隔天清除
        long curTime = System.currentTimeMillis();
        if (!GongUtils.isSameDayForOffset(getLastFightTime(), curTime, 0)) {
            setLastFightTime(curTime);
            setFightCount(0);
        }
        setFightCount(getFightCount() + incrCount);
    }

    public void updateGuankaModelOnLogin(long curTime) {
        if (!GongUtils.isSameDayForOffset(getLastFightTime(), curTime, 0)) {
            setLastFightTime(curTime);
            setFightCount(0);
        }
    }

    public LMap getTemplate() {
        return GuankaBaseData.data.getMap(getTemplateId());
    }

    public void incrGkbdNum(int incr) {
        setGkbdNum(GongUtils.adjustNumberInRange(getGkbdNum() + incr, 0, Integer.MAX_VALUE));
    }

    public Map<Integer, Integer> getStarMap() {
        if(starMap == null){
            starMap = new HashMap<Integer, Integer>();
        }
        return starMap;
    }

    public void setStarMap(Map<Integer, Integer> starMap) {
        this.starMap = starMap;
    }


    public int getTotalFightCount() {

        return totalFightCount;
    }

    public void setTotalFightCount(int totalFightCount) {

        this.totalFightCount = totalFightCount;
    }

    public void incrTotalFightCount(int incrCount) {
        setTotalFightCount(GongUtils.adjustNumberInRange(getTotalFightCount() + incrCount, 0, Integer.MAX_VALUE));
    }


    public Map<Integer, Integer> getBoxMap() {
        if(boxMap == null){
            boxMap = new HashMap<Integer, Integer>();
        }
        return boxMap;
    }

    public void setBoxMap(Map<Integer, Integer> boxMap) {
        this.boxMap = boxMap;
    }
}
