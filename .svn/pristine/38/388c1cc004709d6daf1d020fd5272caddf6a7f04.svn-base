package com.gamejelly.gong2.meta;

import com.gamejelly.gong2.meta.base.ChildModel;
import com.gamejelly.gong2.utils.GongConstants;
import com.gamejelly.gong2.utils.GongUtils;
import com.hadoit.game.common.framework.dao.annotation.Table;

@Table(recursion = true)
public class CycleOperateModel extends ChildModel {
	private static final long serialVersionUID = 1L;

	private int operateType;

	private long lastCdTime;

	private long offsetTime;

	private long cdCount;

	private int cdType;

	/**
	 * 一个周期的时间
	 */
	private long cdPeriod;

	/**
	 * 周期内的时间间隔(记录当前周期内下一次可用的时间)
	 */
	private long cdPeriodGapTime;

	/**
	 * 是否忽略周期内的间隔
	 * 
	 * 封神来了，封神坛是每天10次, 每次间隔15分钟 其他的重置一般都是每天N次, 但是每次之间没有时间间隔限制
	 */
	private boolean ignorePeriodGap = true;

	public CycleOperateModel() {
	}

	public CycleOperateModel(int operateType, int cdType, long lastCdTime) {
		this.operateType = operateType;
		this.cdType = cdType;
		this.lastCdTime = lastCdTime;
	}

	public int getOperateType() {
		return operateType;
	}

	public void setOperateType(int operateType) {
		this.operateType = operateType;
	}

	public long getLastCdTime() {
		return lastCdTime;
	}

	public void setLastCdTime(long lastCdTime) {
		this.lastCdTime = lastCdTime;
	}

	public long getCdCount() {
		return cdCount;
	}

	public void setCdCount(long cdCount) {
		this.cdCount = cdCount;
	}

	public int getCdType() {
		return cdType;
	}

	public void setCdType(int cdType) {
		this.cdType = cdType;
	}

	public long getCdPeriod() {
		return cdPeriod;
	}

	public void setCdPeriod(long cdPeriod) {
		this.cdPeriod = cdPeriod;
	}

	public long getCdPeriodGapTime() {
		return cdPeriodGapTime;
	}

	public void setCdPeriodGapTime(long cdPeriodGapTime) {
		this.cdPeriodGapTime = cdPeriodGapTime;
	}

	public boolean isIgnorePeriodGap() {
		return ignorePeriodGap;
	}

	public void setIgnorePeriodGap(boolean ignorePeriodGap) {
		this.ignorePeriodGap = ignorePeriodGap;
	}

	public long getOffsetTime() {
		return offsetTime;
	}

	public void setOffsetTime(long offsetTime) {
		this.offsetTime = offsetTime;
	}

	public void incrCdCount(long v) {
		setCdCount(GongUtils.adjustNumberInRange(getCdCount() + v, 0, Integer.MAX_VALUE));
	}

	public boolean isInSameCd(long curTime) {
		boolean sameCd;
		if (getCdType() == GongConstants.OPERATE_CD_TYPE_DAY) {
			sameCd = GongUtils.isSameDayForOffset(getLastCdTime(), curTime, getOffsetTime());
		} else if (getCdType() == GongConstants.OPERATE_CD_TYPE_WEEK) {
			sameCd = GongUtils.isSameWeekForOffset(getLastCdTime(), curTime, getOffsetTime());
		} else if (getCdType() == GongConstants.OPERATE_CD_TYPE_FOREVER) {
			sameCd = true;
		} else if (getCdType() == GongConstants.OPERATE_CD_TYPE_HOUR) {
			sameCd = GongUtils.isSameHourForOffset(getLastCdTime(), curTime, getOffsetTime());
		} else {
			// 只能处理周期内一次cd的情况
			sameCd = (curTime <= getCdPeriodGapTime());
		}
		return sameCd;
	}

	public boolean isInPeriodGap(long curTime) {
		// 是否忽略cd内周期间隔
		if (isIgnorePeriodGap()) {
			return false;
		}
		if (getCdPeriodGapTime() >= curTime) {
			// 周期间隔判断
			return true;
		}
		return false;
	}

}
