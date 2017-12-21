package com.gamejelly.gong2.meta;

import com.gamejelly.gong2.meta.base.ChildModel;
import com.hadoit.game.common.framework.dao.annotation.Column;
import com.hadoit.game.common.framework.dao.annotation.Table;

@Table(recursion = true)
public class GongHuiMsgModel extends ChildModel {

    private static final long serialVersionUID = 1L;

    // 公会动态
    @Column(length = 65535 * 10)
    private String msg;

    // 时间
    private Long msgTime;

    public GongHuiMsgModel() {
    }

    public GongHuiMsgModel(String msg, long msgTime) {
        this.msg = msg;
        this.msgTime = msgTime;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(Long msgTime) {
        this.msgTime = msgTime;
    }

}
