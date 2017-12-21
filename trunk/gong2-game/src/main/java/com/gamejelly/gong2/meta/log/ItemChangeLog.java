package com.gamejelly.gong2.meta.log;

import com.hadoit.game.common.framework.dao.annotation.Index;
import com.hadoit.game.common.framework.dao.annotation.Table;

@Table(recursion = true, indexs = { @Index(columns = { "serverId", "dayCreateTime" }),
		@Index(columns = { "itemTemplateId", "serverId", "dayCreateTime" }) })
public class ItemChangeLog extends AvatarBaseLog {
	private static final long serialVersionUID = 1L;

	private int oldCount;

	private int delta;

	private int itemTemplateId;

	private String itemInstId;

	private int sourceType;

	private int sourceId1;

	private int sourceId2;

	public int getOldCount() {
		return oldCount;
	}

	public void setOldCount(int oldCount) {
		this.oldCount = oldCount;
	}

	public int getDelta() {
		return delta;
	}

	public void setDelta(int delta) {
		this.delta = delta;
	}

	public int getItemTemplateId() {
		return itemTemplateId;
	}

	public void setItemTemplateId(int itemTemplateId) {
		this.itemTemplateId = itemTemplateId;
	}

	public String getItemInstId() {
		return itemInstId;
	}

	public void setItemInstId(String itemInstId) {
		this.itemInstId = itemInstId;
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
