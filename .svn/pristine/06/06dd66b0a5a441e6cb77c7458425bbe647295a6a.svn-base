package com.gamejelly.gong2.meta.log;

import com.hadoit.game.common.framework.dao.annotation.Index;
import com.hadoit.game.common.framework.dao.annotation.Table;

@Table(recursion = true, indexs = { @Index(columns = { "currencyId", "serverId", "dayCreateTime" }),
		@Index(columns = { "accountName" }) })
public class CurrencyChangeLog extends AvatarBaseLog {
	private static final long serialVersionUID = 1L;

	private int sourceType;

	private int currencyId;

	private long delta;

	private long oldVal;

	private int sourceId1;

	private int sourceId2;

	public int getSourceType() {
		return sourceType;
	}

	public void setSourceType(int sourceType) {
		this.sourceType = sourceType;
	}

	public int getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(int currencyId) {
		this.currencyId = currencyId;
	}

	public long getDelta() {
		return delta;
	}

	public void setDelta(long delta) {
		this.delta = delta;
	}

	public long getOldVal() {
		return oldVal;
	}

	public void setOldVal(long oldVal) {
		this.oldVal = oldVal;
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
