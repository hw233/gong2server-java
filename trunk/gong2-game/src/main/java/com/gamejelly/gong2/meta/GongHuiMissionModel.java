package com.gamejelly.gong2.meta;

import com.gamejelly.gong2.meta.base.ChildModel;
import com.hadoit.game.common.framework.dao.annotation.Table;
import com.hadoit.game.engine.core.protocol.json.JsonExclude;

@Table(recursion = true)
public class GongHuiMissionModel extends ChildModel {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private int missionId;

    /**
     * 上次接走任务时间
     */
    private long lastGetTime;

    private long createTime;

    public int getMissionId() {
        return missionId;
    }

    public void setMissionId(int missionId) {
        this.missionId = missionId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getLastGetTime() {
        return lastGetTime;
    }

    public void setLastGetTime(long lastGetTime) {
        this.lastGetTime = lastGetTime;
    }

}
