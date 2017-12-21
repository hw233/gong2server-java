package com.gamejelly.gong2.meta;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.gamejelly.gong2.meta.base.EmbedModel;
import com.hadoit.game.common.framework.dao.annotation.Column;
import com.hadoit.game.common.framework.dao.annotation.Embeddable;
import com.hadoit.game.common.framework.dao.convert.impl.CollectionNumberOrStringColumnConvert;

@Embeddable(recursion = true)
public class GangEndWeekBossModel implements EmbedModel {

    private static final long serialVersionUID = 1L;

    /**
     * 周末派系boss是否开启 0 未开启   1 开启
     * 注意 周末boss结束后 关闭
     */
    private int isOpen;

    /**
     * 开启时间
     */
    private long openTime;

    /**
     * 上次周末派系boss死亡时间
     */
    private long lastBossDeadTime;

    /**
     * 派系伤害榜
     */
    @Column(ignore = true)
    List<DamageRankModel> damageRankModel = new ArrayList<DamageRankModel>();

    // 进入周末派系boss的人, 仅做推送用
    @Column(convertClazz = CollectionNumberOrStringColumnConvert.class, length = 65535)
    private Set<String> scenePlayers = new HashSet<String>();

    public Set<String> getScenePlayers() {
        if (scenePlayers == null) {
            scenePlayers = new HashSet<String>();
        }
        return scenePlayers;
    }

    public void setScenePlayers(Set<String> scenePlayers) {
        this.scenePlayers = scenePlayers;
    }

    public long getEndWeekGangBossHp() {
        return endWeekGangBossHp;
    }

    public void setEndWeekGangBossHp(long endWeekGangBossHp) {
        this.endWeekGangBossHp = endWeekGangBossHp;
    }

    public long getEndWeekGangBossMaxHp() {
        return endWeekGangBossMaxHp;
    }

    public void setEndWeekGangBossMaxHp(long endWeekGangBossMaxHp) {
        this.endWeekGangBossMaxHp = endWeekGangBossMaxHp;
    }

    public long getEndWeekGangBossOverTimerId() {
        return endWeekGangBossOverTimerId;
    }

    public void setEndWeekGangBossOverTimerId(long endWeekGangBossOverTimerId) {
        this.endWeekGangBossOverTimerId = endWeekGangBossOverTimerId;
    }

    private long endWeekGangBossHp;

    private long endWeekGangBossMaxHp;

    private long endWeekGangBossOverTimerId;

    public int getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(int isOpen) {
        this.isOpen = isOpen;
    }

    public long getOpenTime() {
        return openTime;
    }

    public void setOpenTime(long openTime) {
        this.openTime = openTime;
    }

    public List<DamageRankModel> getDamageRankModel() {
        if (damageRankModel == null) {
            damageRankModel = new ArrayList<DamageRankModel>();
        }
        return damageRankModel;
    }

    public void setDamageRankModel(List<DamageRankModel> damageRankModel) {
        this.damageRankModel = damageRankModel;
    }

    public long getLastBossDeadTime() {
        return lastBossDeadTime;
    }

    public void setLastBossDeadTime(long lastBossDeadTime) {
        this.lastBossDeadTime = lastBossDeadTime;
    }


}
