package com.gamejelly.gong2.meta;

import com.gamejelly.gong2.meta.base.ChildModel;
import com.gamejelly.gong2.utils.GongUtils;
import com.hadoit.game.common.framework.dao.annotation.Column;
import com.hadoit.game.common.framework.dao.annotation.Table;

@Table(recursion = true)
public class GongHuiMemberModel extends ChildModel {
    private static final long serialVersionUID = 1L;

    private String avatarId;

    /**
     * 0-非成员，1-普通成员，2-长老，3-副盟主，4-盟主
     */
    private int position;

    /**
     * 0-申请，1-成员，2-被踢/被拒，3-退出
     */
    private int state;

    private long applyTime;

    private long joinTime;

    private long exitTime;

    @Column(ignore = true)
    private boolean online;

    private String name;

    private int lv;

    /**
     * 洞府经验
     */
    private long dfExp;

    private int zhanli;

    /**
     * 历史最高总战力
     */
    private int maxZhanli;

    private long logoutTime;

    private int job;

    private int grade;

    private int sex;

    /**
     * 势力值
     */
    private long shiLi;

    /**
     * 上次捐献时间
     */
    private long lastDonateResetTime;

    /**
     * 历史总贡献
     */
    private long totalDonate;

    /**
     * 本周贡献
     */
    private long weekDonate;

    /**
     * 报名派系战斗状态 0没有,1报名
     */
    private int signFairyWarState;

    /**
     * 派系战出战状态
     */
    private int fairyWarState;

    public GongHuiMemberModel() {
    }

    public GongHuiMemberModel(String avatarId, String name, int lv, long dfExp, int zhanli, long logoutTime,
                              int position, int state, int job, int grade, int sex, long shiLi, int maxZhanLi) {
        this.online = true;
        this.avatarId = avatarId;
        this.name = name;
        this.lv = lv;
        this.dfExp = dfExp;
        this.zhanli = zhanli;
        this.logoutTime = logoutTime;
        this.position = position;
        this.state = state;
        this.applyTime = System.currentTimeMillis();
        this.job = job;
        this.grade = grade;
        this.sex = sex;
        this.shiLi = shiLi;
        this.maxZhanli = maxZhanLi;
    }

    public void updateInfo(boolean online, String name, int lv, long dfExp, int zhanli, long logoutTime, int job,
                           int grade, int sex, long shiLi, int maxZhanLi) {
        this.online = online;
        this.name = name;
        this.lv = lv;
        this.dfExp = dfExp;
        this.zhanli = zhanli;
        this.logoutTime = logoutTime;
        this.job = job;
        this.grade = grade;
        this.sex = sex;
        this.shiLi = shiLi;
        this.maxZhanli = maxZhanLi;
    }

    public String getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(String avatarId) {
        this.avatarId = avatarId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(long applyTime) {
        this.applyTime = applyTime;
    }

    public long getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(long joinTime) {
        this.joinTime = joinTime;
    }

    public long getExitTime() {
        return exitTime;
    }

    public void setExitTime(long exitTime) {
        this.exitTime = exitTime;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLv() {
        return lv;
    }

    public void setLv(int lv) {
        this.lv = lv;
    }

    public long getDfExp() {
        return dfExp;
    }

    public void setDfExp(long dfExp) {
        this.dfExp = dfExp;
    }

    public int getZhanli() {
        return zhanli;
    }

    public void setZhanli(int zhanli) {
        this.zhanli = zhanli;
    }

    public long getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(long logoutTime) {
        this.logoutTime = logoutTime;
    }

    public int getJob() {
        return job;
    }

    public void setJob(int job) {
        this.job = job;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public long getLastDonateResetTime() {
        return lastDonateResetTime;
    }

    public void setLastDonateResetTime(long lastDonateResetTime) {
        this.lastDonateResetTime = lastDonateResetTime;
    }

    public long getTotalDonate() {
        return totalDonate;
    }

    public void setTotalDonate(long totalDonate) {
        this.totalDonate = totalDonate;
    }

    public long getWeekDonate() {
        return weekDonate;
    }

    public void setWeekDonate(long weekDonate) {
        this.weekDonate = weekDonate;
    }

    public void incrWeekDonate(long incr) {
        this.setWeekDonate(GongUtils.adjustNumberInRange(getWeekDonate() + incr, 0, Long.MAX_VALUE));
    }

    public void incrTotalDonate(long incr) {
        this.setTotalDonate(GongUtils.adjustNumberInRange(getTotalDonate() + incr, 0, Long.MAX_VALUE));
    }

    public long getShiLi() {
        return shiLi;
    }

    public void setShiLi(long shiLi) {
        this.shiLi = shiLi;
    }

    public int getSignFairyWarState() {
        return signFairyWarState;
    }

    public void setSignFairyWarState(int signFairyWarState) {
        this.signFairyWarState = signFairyWarState;
    }

    public int getFairyWarState() {
        return fairyWarState;
    }

    public void setFairyWarState(int fairyWarState) {
        this.fairyWarState = fairyWarState;
    }

    public int getMaxZhanli() {
        return maxZhanli;
    }

    public void setMaxZhanli(int maxZhanli) {
        this.maxZhanli = maxZhanli;
    }

}
