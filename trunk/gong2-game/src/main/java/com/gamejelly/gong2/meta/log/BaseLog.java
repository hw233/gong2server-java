package com.gamejelly.gong2.meta.log;

import com.hadoit.game.common.framework.dao.annotation.Column;
import com.hadoit.game.engine.guardian.core.RemoteType;

public class BaseLog implements RemoteType {
	private static final long serialVersionUID = 1L;

	private long id;

	private String opr;

	private int serverId;

	@Column(index=true)
	private long dayCreateTime;

	private long createTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getDayCreateTime() {
		return dayCreateTime;
	}

	public void setDayCreateTime(long dayCreateTime) {
		this.dayCreateTime = dayCreateTime;
	}

	public String getOpr() {
		return opr;
	}

	public void setOpr(String opr) {
		this.opr = opr;
	}

}
