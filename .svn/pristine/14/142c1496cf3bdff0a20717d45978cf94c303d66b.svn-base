package com.gamejelly.gong2.meta;

import com.gamejelly.gong2.meta.base.BaseModel;
import com.hadoit.game.common.framework.dao.annotation.Column;
import com.hadoit.game.common.framework.dao.annotation.Index;
import com.hadoit.game.common.framework.dao.annotation.Table;

@Table(recursion = true, indexs = { @Index(columns = { "avatarId", "targetAvatarId", "createTime" }) })
public class ChatModel extends BaseModel{

	private static final long serialVersionUID = 1L;
	
	private String avatarId;
	
	private String targetAvatarId;
	
	@Column(length = 65535)
	private String content;
	
	private long createTime;
	
	private boolean selfSay;

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public boolean isSelfSay() {
		return selfSay;
	}

	public void setSelfSay(boolean selfSay) {
		this.selfSay = selfSay;
	}
	
	

}
