package com.gamejelly.gong2.meta;

import com.gamejelly.gong2.meta.base.ChildModel;
import com.gamejelly.gong2.utils.GongUtils;
import com.hadoit.game.common.framework.dao.annotation.Column;
import com.hadoit.game.common.framework.dao.annotation.Table;
import com.hadoit.game.common.framework.dao.convert.impl.MapNumberOrStringColumnConvert;

import java.util.HashMap;
import java.util.Map;

@Table(recursion = true)
public class ServantModel extends ChildModel {
    private static final long serialVersionUID = 1L;

    /**
     * 表示唯一的id
     */
    @Column(value = "uid")
    private String id;

    /**
     * 是否是主角
     */
    private boolean avatar;

    private int templateId;

    /**
     * 等级
     */
    private int lv;

    /**
     * 进阶等级
     */
    private int advanceLv;

    /**
     * 所处阵位
     */
    private int pos;

    private long createTime;

    /**
     * 仆从类型type 0招募的仆从 1 结婚对象 2小妾
     */
    private int type;

    /**
     * 仆从性别sex
     */
    private int sex;

    /**
     * 仆从姓名name
     */
    private String name;

    /**
     * iconIndex
     */
    private int iconIndex;

    private long lastCustomPhotoTime;

    /**
     * canFight是否出战
     */
    private boolean canFight;

    /**
     * 当前经验
     */
    private long exp;


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

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getLv() {
        return lv;
    }

    public void setLv(int lv) {
        this.lv = lv;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public boolean isAvatar() {
        return avatar;
    }

    public void setAvatar(boolean avatar) {
        this.avatar = avatar;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconIndex() {
        return iconIndex;
    }

    public void setIconIndex(int iconIndex) {
        this.iconIndex = iconIndex;
    }

    public long getLastCustomPhotoTime() {
        return lastCustomPhotoTime;
    }

    public void setLastCustomPhotoTime(long lastCustomPhotoTime) {
        this.lastCustomPhotoTime = lastCustomPhotoTime;
    }

    public boolean isCanFight() {
        return canFight;
    }

    public void setCanFight(boolean canFight) {
        this.canFight = canFight;
    }

    public void incrLv(int v) {
        setLv(GongUtils.adjustNumberInRange(getLv() + v, 0, Integer.MAX_VALUE));
    }

    public void incrAdvanceLv(int v) {
        setAdvanceLv(GongUtils.adjustNumberInRange(getAdvanceLv() + v, 0, Integer.MAX_VALUE));
    }

    public long getExp() {
        return exp;
    }

    public void setExp(long exp) {
        this.exp = exp;
    }

    public void incrExp(long val) {
        setExp(GongUtils.adjustNumberInRange(getExp() + val, 0, Long.MAX_VALUE));
    }

    public int getAdvanceLv() {
        return advanceLv;
    }

    public void setAdvanceLv(int advanceLv) {
        this.advanceLv = advanceLv;
    }
}
