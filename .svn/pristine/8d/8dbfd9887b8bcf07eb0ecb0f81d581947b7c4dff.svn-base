package com.gamejelly.gong2.meta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gamejelly.gong2.meta.base.EmbedModel;
import com.hadoit.game.common.framework.dao.annotation.Column;
import com.hadoit.game.common.framework.dao.annotation.Embeddable;
import com.hadoit.game.common.framework.dao.convert.impl.MapNumberOrStringColumnConvert;

@Embeddable(recursion = true)
public class GangBossModel implements EmbedModel {

    private static final long serialVersionUID = 1L;

    /**
     * 派系bossId
     */
    private int bossId;

    /**
     * 本周已打boss
     */
    @Column(convertClazz = MapNumberOrStringColumnConvert.class, length = 65535)
    private Map<Integer,String> alreadyFightBoss = new HashMap<Integer,String>();

    /**
     * 派系boss血量
     */
    private long gangBossHP;

    /**
     * 派系boss最大血量
     */
    private long gangBossMaxHP;

    /**
     * 派系伤害榜
     */
    @Column(ignore = true)
    List<DamageRankModel> damageRankModel = new ArrayList<DamageRankModel>();

    public int getBossId() {
        return bossId;
    }

    public void setBossId(int bossId) {
        this.bossId = bossId;
    }

    public Map<Integer,String> getAlreadyFightBoss() {
        if (alreadyFightBoss == null) {
            alreadyFightBoss = new HashMap<Integer,String>();
        }
        return alreadyFightBoss;
    }

    public void setAlreadyFightBoss(Map<Integer,String> alreadyFightBoss) {
        this.alreadyFightBoss = alreadyFightBoss;
    }

    public long getGangBossHP() {
        return gangBossHP;
    }

    public void setGangBossHP(long gangBossHP) {
        this.gangBossHP = gangBossHP;
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

    public long getGangBossMaxHP() {
        return gangBossMaxHP;
    }

    public void setGangBossMaxHP(long gangBossMaxHP) {
        this.gangBossMaxHP = gangBossMaxHP;
    }

}
