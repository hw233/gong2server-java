package com.gamejelly.gong2.vo;

import com.gamejelly.gong2.meta.GongHuiMemberModel;
import com.hadoit.game.engine.guardian.core.RemoteType;

public class GongHuiMemberVO implements RemoteType {

    private static final long serialVersionUID = 1L;

    private String avatarId;

    private String name;

    private int lv;

    private int zhanli;
    /**
     * 0-非成员，1-普通成员，2-副盟主，3-盟主
     */
    private int position;

    //最高伤害
    private long maxDamage;

    public GongHuiMemberVO(String avatarId, String name, long maxDamage){
        this.avatarId = avatarId;
        this.name = name;
        this.maxDamage = maxDamage;
    }

    public GongHuiMemberVO(GongHuiMemberModel member){
        this.avatarId = member.getAvatarId();
        this.name= member.getName();
        this.lv = member.getLv();
        this.zhanli = member.getZhanli();
        this.position = member.getPosition();
    }

    public String getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(String avatarId) {
        this.avatarId = avatarId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getMaxDamage() {
        return maxDamage;
    }

    public void setMaxDamage(long maxDamage) {
        this.maxDamage = maxDamage;
    }

    public int getLv() {
        return lv;
    }

    public void setLv(int lv) {
        this.lv = lv;
    }

    public int getZhanli() {
        return zhanli;
    }

    public void setZhanli(int zhanli) {
        this.zhanli = zhanli;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

}
