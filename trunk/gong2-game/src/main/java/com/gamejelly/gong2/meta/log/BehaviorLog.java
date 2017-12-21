package com.gamejelly.gong2.meta.log;

import com.hadoit.game.common.framework.dao.annotation.Index;
import com.hadoit.game.common.framework.dao.annotation.Table;

@Table(recursion = true, indexs = { @Index(columns = { "sourceType", "serverId", "dayCreateTime" }) })
public class BehaviorLog extends AvatarBaseLog {
	private static final long serialVersionUID = 1L;

	private int sourceType;

	private int sourceId1;

	private int sourceId2;

	private int sourceId3;

	private String behaviorData;

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

	public int getSourceId3() {
		return sourceId3;
	}

	public void setSourceId3(int sourceId3) {
		this.sourceId3 = sourceId3;
	}

	public String getBehaviorData() {
		return behaviorData;
	}

	public void setBehaviorData(String behaviorData) {
		this.behaviorData = behaviorData;
	}

}
