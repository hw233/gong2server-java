package com.gamejelly.gong2.meta.log;

import com.hadoit.game.common.framework.dao.annotation.Index;
import com.hadoit.game.common.framework.dao.annotation.Table;

@Table(recursion = true, indexs = { @Index(columns = { "serverId", "dayCreateTime" }) })
public class ExpChangeLog extends AvatarBaseLog {
	private static final long serialVersionUID = 1L;

	private int oldLv;

	private int lvDelta;

	private long expDelta;

	private int sourceType;

	private int sourceId1;

	private int sourceId2;

	public int getOldLv() {
		return oldLv;
	}

	public void setOldLv(int oldLv) {
		this.oldLv = oldLv;
	}

	public int getLvDelta() {
		return lvDelta;
	}

	public void setLvDelta(int lvDelta) {
		this.lvDelta = lvDelta;
	}

	public long getExpDelta() {
		return expDelta;
	}

	public void setExpDelta(long expDelta) {
		this.expDelta = expDelta;
	}

	public int getSourceType() {
		return sourceType;
	}

	public void setSourceType(int sourceType) {
		this.sourceType = sourceType;
	}

	public int getSourceId1() {
		return sourceId1;
	}

	public void setSourceId1(int sourceId1) {
		this.sourceId1 = sourceId1;
	}

	public int getSourceId2() {
		return sourceId2;
	}

	public void setSourceId2(int sourceId2) {
		this.sourceId2 = sourceId2;
	}

}
