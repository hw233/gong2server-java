package com.gamejelly.gong2.meta;

import com.gamejelly.gong2.meta.base.BaseModel;
import com.hadoit.game.common.framework.dao.annotation.Index;
import com.hadoit.game.common.framework.dao.annotation.Table;

@Table(recursion = true, indexs = {@Index(columns = {"avatarId", "targetAvatarId"}, unique = true)})
public class JiejiaoModel extends BaseModel{

	private static final long serialVersionUID = 1L;
	
	private String avatarId;
	
	private String targetAvatarId;
	
	private long createTime;

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
	
}
