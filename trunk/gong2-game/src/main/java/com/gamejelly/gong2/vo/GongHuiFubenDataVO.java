package com.gamejelly.gong2.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hadoit.game.engine.core.protocol.json.JsonExclude;

public class GongHuiFubenDataVO {

    private int id;

    private int sceneId;
    /**
     * 开启等级
     */
    private int lv;
    /**
     * 开启所需活跃消耗
     */
    private int hyConsume;
    /**
     * 当前消耗的血量
     */
    private long wastageBasHp;

    /**
     * 该关卡总血量
     */
    private long totalBasHp;
    /**
     * -1-不可开启，0-可开启，1-已开启，2-已通关
     */
    private int state;

    private String ghId;

    private String ghName;
    /**
     * 通关时间
     */
    private long customsTime;

    /**
     * 是否完成第一次通关
     */
    private boolean firstTg;

    private List<Integer> gkIdList;

    @JsonExclude("client")
    private Map<Integer,Map<Integer,Long>> gkMonsterMap;

    public GongHuiFubenDataVO(GongHuiFubenDataVO fb){
        this.id = fb.getId();
        this.sceneId = fb.getSceneId();
        this.lv = fb.getLv();
        this.hyConsume = fb.getHyConsume();
        this.totalBasHp = fb.getTotalBasHp();
        this.state = -1;
    }

    public GongHuiFubenDataVO(int id,int sceneId,int lv,int hyConsume){
        this.id = id;
        this.sceneId = sceneId;
        this.lv = lv;
        this.hyConsume = hyConsume;
        this.state= -1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSceneId() {
        return sceneId;
    }

    public void setSceneId(int sceneId) {
        this.sceneId = sceneId;
    }

    public int getLv() {
        return lv;
    }

    public void setLv(int lv) {
        this.lv = lv;
    }

    public int getHyConsume() {
        return hyConsume;
    }

    public void setHyConsume(int hyConsume) {
        this.hyConsume = hyConsume;
    }

    public long getWastageBasHp() {
        return wastageBasHp;
    }

    public void setWastageBasHp(long wastageBasHp) {
        this.wastageBasHp = wastageBasHp;
    }

    public long getTotalBasHp() {
        return totalBasHp;
    }

    public void setTotalBasHp(long totalBasHp) {
        this.totalBasHp = totalBasHp;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getGhId() {
        return ghId;
    }

    public void setGhId(String ghId) {
        this.ghId = ghId;
    }

    public String getGhName() {
        return ghName;
    }

    public void setGhName(String ghName) {
        this.ghName = ghName;
    }

    public long getCustomsTime() {
        return customsTime;
    }

    public void setCustomsTime(long customsTime) {
        this.customsTime = customsTime;
    }

    public boolean isFirstTg() {
        return firstTg;
    }

    public void setFirstTg(boolean firstTg) {
        this.firstTg = firstTg;
    }

    public List<Integer> getGkIdList() {
        if(gkIdList==null){
            gkIdList = new ArrayList<Integer>();
        }
        return gkIdList;
    }

    public void setGkIdList(List<Integer> gkIdList) {
        this.gkIdList = gkIdList;
    }

    public Map<Integer, Map<Integer,Long>> getGkMonsterMap() {
        if(gkMonsterMap==null){
            gkMonsterMap = new HashMap<Integer, Map<Integer,Long>>();
        }
        return gkMonsterMap;
    }

    public void setGkMonsterMap(Map<Integer, Map<Integer,Long>> gkMonsterMap) {
        this.gkMonsterMap = gkMonsterMap;
    }

}
