package com.gamejelly.gong2.meta;

import com.gamejelly.gong2.meta.base.SimpleModel;
import com.hadoit.game.common.framework.dao.annotation.Column;
import com.hadoit.game.common.framework.dao.annotation.Table;

@Table(recursion = true)
public class MessageCountModel extends SimpleModel {
	private static final long serialVersionUID = 1L;

	@Column(unique = true)
	private String avatarId;

	private int msgCnt;

	public String getAvatarId() {
		return avatarId;
	}

	public void setAvatarId(String avatarId) {
		this.avatarId = avatarId;
	}

	public int getMsgCnt() {
		return msgCnt;
	}

	public void setMsgCnt(int msgCnt) {
		this.msgCnt = msgCnt;
	}

}
