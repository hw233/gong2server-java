package com.gamejelly.gong2.meta.share;


import com.gamejelly.gong2.meta.base.BaseModel;
import com.hadoit.game.common.framework.dao.annotation.Column;
import com.hadoit.game.common.framework.dao.annotation.Table;

import java.util.ArrayList;
import java.util.List;

@Table(recursion = true)
public class SharedModel extends BaseModel {
    private static final long serialVersionUID = 1L;

    /**
     * 表示唯一的id
     */
    @Column(value = "uid", index = true)
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * 临时值
     */
    @Column(ignore = true)
    private List<AvatarTempValueModel> avatarTempValueList = new ArrayList<AvatarTempValueModel>();

    public List<AvatarTempValueModel> getAvatarTempValueList() {
        return avatarTempValueList;
    }

    public void setAvatarTempValueList(List<AvatarTempValueModel> avatarTempValueList) {
        this.avatarTempValueList = avatarTempValueList;
    }
}
