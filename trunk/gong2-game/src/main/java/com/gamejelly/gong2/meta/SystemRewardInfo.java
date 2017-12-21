package com.gamejelly.gong2.meta;

import java.util.List;

import com.hadoit.game.engine.guardian.core.RemoteType;

public class SystemRewardInfo implements RemoteType {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 奖励类型
	 * GongConstants.PLAYER_REWARD_FST 等类型
	 * rewardType != 0 时根据rewardData的值发奖，忽略gold等参数
	 * rewardType == 0 时忽略rewardData的值，更具gold等参数发奖
	 */
	private int rewardType;
	
	/**
	 * 奖励信息rewardType != 0时有效
	 */
	private String rewardData;
	
	/**
	 * 额外信息和rewardData一起使用
	 */
	private String extraData;

	private long gold;

	private long leijiGold;

	private long money;

	private List<Integer> itemCounts;

	private List<Integer> itemIds;

	public SystemRewardInfo() {
	}

	public long getGold() {
		return gold;
	}

	public void setGold(long gold) {
		this.gold = gold;
	}

	public long getLeijiGold() {
		return leijiGold;
	}

	public void setLeijiGold(long leijiGold) {
		this.leijiGold = leijiGold;
	}

	public long getMoney() {
		return money;
	}

	public void setMoney(long money) {
		this.money = money;
	}

	public List<Integer> getItemCounts() {
		return itemCounts;
	}

	public void setItemCounts(List<Integer> itemCounts) {
		this.itemCounts = itemCounts;
	}

	public List<Integer> getItemIds() {
		return itemIds;
	}

	public void setItemIds(List<Integer> itemIds) {
		this.itemIds = itemIds;
	}

	public int getRewardType() {
		return rewardType;
	}

	public void setRewardType(int rewardType) {
		this.rewardType = rewardType;
	}

	public String getRewardData() {
		return rewardData;
	}

	public void setRewardData(String rewardData) {
		this.rewardData = rewardData;
	}

	public String getExtraData() {
		return extraData;
	}

	public void setExtraData(String extraData) {
		this.extraData = extraData;
	}

	@Override
	public String toString() {
		return "SystemRewardInfo [rewardType=" + rewardType + ", rewardData=" + rewardData + ", extraData=" + extraData
				+ ", gold=" + gold + ", leijiGold=" + leijiGold + ", money=" + money + ", itemCounts=" + itemCounts
				+ ", itemIds=" + itemIds + "]";
	}
	
}
