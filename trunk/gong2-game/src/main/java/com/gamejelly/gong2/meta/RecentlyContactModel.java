package com.gamejelly.gong2.meta;

import com.gamejelly.gong2.meta.base.BaseModel;
import com.hadoit.game.common.framework.dao.annotation.Column;
import com.hadoit.game.common.framework.dao.annotation.Table;

@Table(recursion = true)
public class RecentlyContactModel extends BaseModel{

	private static final long serialVersionUID = 1L;
	
	@Column(index = true)
	private String avatarId;
	
	/*
	 * 聊天对象
	 */
	private String targetAvatarId;
	
	/**
	 * 聊天创建时间
	 */
	private long createTime;
	
	/*
	 * 聊天信息类型（未读：1 已读：0）
	 */
	private int msgType;
	
	/*
	 * 聊天窗口的状态（关闭：0 打开：1）
	 */
	private int msgWindowStatus;

	public String getAvatarId() {
		return avatarId;
	}

	public void setAvatarId(String avatarId) {
		this.avatarId = avatarId;
	}

	public String getTargetAvatarId() {
		return targetAvatarId;
	}

	public void setTargetAvatarId(String targetAvatarId) {
		this.targetAvatarId = targetAvatarId;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public int getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	public int getMsgWindowStatus() {
		return msgWindowStatus;
	}

	public void setMsgWindowStatus(int msgWindowStatus) {
		this.msgWindowStatus = msgWindowStatus;
	}
	
	

}
