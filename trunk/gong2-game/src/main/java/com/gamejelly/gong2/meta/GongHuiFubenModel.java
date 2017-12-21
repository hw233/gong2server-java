package com.gamejelly.gong2.meta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gamejelly.gong2.meta.base.ChildModel;
import com.gamejelly.gong2.utils.GongUtils;
import com.gamejelly.gong2.vo.GongHuiMemberVO;
import com.hadoit.game.common.framework.dao.annotation.Column;
import com.hadoit.game.common.framework.dao.annotation.Table;
import com.hadoit.game.common.framework.dao.convert.impl.CollectionNumberOrStringColumnConvert;
import com.hadoit.game.common.framework.dao.convert.impl.MapNumberOrStringColumnConvert;

@Table(recursion = true)
public class GongHuiFubenModel extends ChildModel {

    private static final long serialVersionUID = 1L;

    /**
     * 是否开启此场景
     */
    private boolean finished;

    /**
     * 副本Id
     */
    private int fbId;

    /**
     * 当前关卡Id
     */
    private int gkId;

    /**
     * 当前消耗的血量
     */
    private long wastageBasHp;

    /**
     * 该关卡总血量
     */
    private long totalBasHp;

    private long startTime;

    private long endTime;

    /**
     * 當前光卡怪物血量
     */
    @Column(convertClazz = MapNumberOrStringColumnConvert.class, length = 65535)
    private Map<Integer,Long> monsterHpMap = new HashMap<Integer,Long>();

    /**
     * 当前副本未通关的关卡
     */
    @Column(convertClazz = CollectionNumberOrStringColumnConvert.class, length = 65535)
    private List<Integer> guankaList = new ArrayList<Integer>();

    /**
     * 玩家最高伤害
     */
    @Column(convertClazz = MapNumberOrStringColumnConvert.class, length = 65535)
    private Map<String, Long> maxDamageMap = new HashMap<String, Long>();

    @Column(ignore = true)
    private List<GongHuiMemberVO> memberRankList = new ArrayList<GongHuiMemberVO>();

    public GongHuiFubenModel(){}

    public GongHuiFubenModel(boolean finished,int fbId){
        this.finished = finished;
        this.fbId = fbId;
        this.startTime = System.currentTimeMillis();
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public int getFbId() {
        return fbId;
    }

    public void setFbId(int fbId) {
        this.fbId = fbId;
    }

    public int getGkId() {
        return gkId;
    }

    public void setGkId(int gkId) {
        this.gkId = gkId;
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

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public Map<Integer, Long> getMonsterHpMap() {
        return monsterHpMap;
    }

    public void setMonsterHpMap(Map<Integer, Long> monsterHpMap) {
        this.monsterHpMap = monsterHpMap;
    }

    public List<Integer> getGuankaList() {
        return guankaList;
    }

    public void setGuankaList(List<Integer> guankaList) {
        this.guankaList = guankaList;
    }

    public Map<String, Long> getMaxDamageMap() {
        return maxDamageMap;
    }

    public void setMaxDamageMap(Map<String, Long> maxDamageMap) {
        this.maxDamageMap = maxDamageMap;
    }

    public long getCompletionTime(){
        return endTime - startTime;
    }

    public List<GongHuiMemberVO> getMemberRankList() {
        return memberRankList;
    }

    public void setMemberRankList(List<GongHuiMemberVO> memberRankList) {
        this.memberRankList = memberRankList;
    }

    public void incrWastageBasHp(int v) {
        setWastageBasHp(GongUtils.adjustNumberInRange(getWastageBasHp() + v, 0, this.totalBasHp));
    }

    public void putMonsterHp(Map<Integer,Long> hpMap){
        monsterHpMap.clear();
        monsterHpMap.putAll(hpMap);
    }

    public boolean replaceMaxDamage(String avatarId,long damage){
        if(maxDamageMap.containsKey(avatarId)){
            if(maxDamageMap.get(avatarId)<damage){
                maxDamageMap.put(avatarId, damage);
                return true;
            }
        }else{
            maxDamageMap.put(avatarId, damage);
            return true;
        }
        return false;
    }

}
