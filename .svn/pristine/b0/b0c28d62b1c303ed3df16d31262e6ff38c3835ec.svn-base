package com.gamejelly.gong2.meta;

import java.util.HashMap;
import java.util.Map;

import com.gamejelly.gong2.meta.base.ChildModel;
import com.hadoit.game.common.framework.dao.annotation.Column;
import com.hadoit.game.common.framework.dao.annotation.Table;
import com.hadoit.game.common.framework.dao.convert.impl.MapNumberOrStringColumnConvert;

@Table(recursion = true)
public class DamageRankModel extends ChildModel{

    private static final long serialVersionUID = 1L;

    /**
     * 人物id
     */
    private String avatarId;

    /**
     * 伤害值
     */
    private long damageCount;

    /**
     * boss死亡奖励
     */
    @Column(convertClazz = MapNumberOrStringColumnConvert.class, length = 65535)
    private Map<Integer,Integer> deadBossId = new HashMap<>();

    /**
     * 每次挑战派系boss奖励
     */
    @Column(convertClazz = MapNumberOrStringColumnConvert.class, length = 65535)
    private Map<Integer,Integer> reward = new HashMap<>();

    /**
     * 挑战次数
     */
    private int challengeCount;

    /**
     * 派系boss类型 0：普通派系boss 1:周末派系boss
     */
    private int bossType;

    /**
     * 最后一次挑战时间，用户挑战cd
     */
    private long lastChallengeTime;

    public DamageRankModel(){

    }

    public DamageRankModel(String avatarId){
        this.avatarId = avatarId;
    }

    public String getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(String avatarId) {
        this.avatarId = avatarId;
    }

    public long getDamageCount() {
        return damageCount;
    }

    public void setDamageCount(long damageCount) {
        this.damageCount = damageCount;
    }

    public Map<Integer,Integer> getDeadBossId() {
        if (deadBossId == null) {
            deadBossId = new HashMap<Integer,Integer>();
        }
        return deadBossId;
    }

    public void setDeadBossId(Map<Integer,Integer> deadBossId) {
        this.deadBossId = deadBossId;
    }

    public int getChallengeCount() {
        return challengeCount;
    }

    public void setChallengeCount(int challengeCount) {
        this.challengeCount = challengeCount;
    }

    public Map<Integer, Integer> getReward() {
        if (reward == null) {
            reward = new HashMap<Integer, Integer>();
        }
        return reward;
    }

    public void setReward(Map<Integer, Integer> reward) {
        this.reward = reward;
    }

    public int getBossType() {
        return bossType;
    }

    public void setBossType(int bossType) {
        this.bossType = bossType;
    }

    public long getLastChallengeTime() {
        return lastChallengeTime;
    }

    public void setLastChallengeTime(long lastChallengeTime) {
        this.lastChallengeTime = lastChallengeTime;
    }


}
