package com.gamejelly.gong2.meta;

import java.util.HashMap;
import java.util.Map;

import com.gamejelly.gong2.meta.base.EmbedModel;
import com.hadoit.game.common.framework.dao.annotation.Column;
import com.hadoit.game.common.framework.dao.annotation.Embeddable;
import com.hadoit.game.common.framework.dao.convert.impl.MapNumberOrStringColumnConvert;

@Embeddable(recursion = true)
public class PayInfoModel implements EmbedModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 首冲否(历史, 不能清除)
	 */
	private boolean historyFirstPayed;

	/**
	 * 首冲否
	 */
	private boolean firstPayed;

	/**
	 * 首冲奖励id
	 */
	private int firstPayedRewardCzId;

	/**
	 * 上次重置时间
	 */
	private long lastResetTime;

	/**
	 * 各项充值次数记录
	 */
	@Column(convertClazz = MapNumberOrStringColumnConvert.class, length = 65535)
	private Map<String, Integer> payInfos = new HashMap<String, Integer>();

	/**
	 * 月卡截止时间
	 */
	private long monthCardEndTime;

	/**
	 * 季卡截止时间
	 */
	private long seasonCardEndTime;

	public boolean isFirstPayed() {
		return firstPayed;
	}

	public void setFirstPayed(boolean firstPayed) {
		this.firstPayed = firstPayed;
	}

	public Map<String, Integer> getPayInfos() {
		if (payInfos == null) {
			payInfos = new HashMap<String, Integer>();
		}
		return payInfos;
	}

	public void setPayInfos(Map<String, Integer> payInfos) {
		this.payInfos = payInfos;
	}

	public long getMonthCardEndTime() {
		return monthCardEndTime;
	}

	public void setMonthCardEndTime(long monthCardEndTime) {
		this.monthCardEndTime = monthCardEndTime;
	}

	public long getSeasonCardEndTime() {
		return seasonCardEndTime;
	}

	public void setSeasonCardEndTime(long seasonCardEndTime) {
		this.seasonCardEndTime = seasonCardEndTime;
	}

	public boolean isHistoryFirstPayed() {
		return historyFirstPayed;
	}

	public void setHistoryFirstPayed(boolean historyFirstPayed) {
		this.historyFirstPayed = historyFirstPayed;
	}

	public long getLastResetTime() {
		return lastResetTime;
	}

	public void setLastResetTime(long lastResetTime) {
		this.lastResetTime = lastResetTime;
	}

	public int getFirstPayedRewardCzId() {
		return firstPayedRewardCzId;
	}

	public void setFirstPayedRewardCzId(int firstPayedRewardCzId) {
		this.firstPayedRewardCzId = firstPayedRewardCzId;
	}
}
