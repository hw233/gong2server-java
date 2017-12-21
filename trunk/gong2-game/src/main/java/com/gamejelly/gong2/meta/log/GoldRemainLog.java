package com.gamejelly.gong2.meta.log;

import com.hadoit.game.common.framework.dao.annotation.Index;
import com.hadoit.game.common.framework.dao.annotation.Table;

/**
 * 元宝保有量日志
 * @author PAULWONG(wangdihua@163.com)
 *
 */
@Table(recursion = true, indexs = { @Index(columns = { "serverId", "dayCreateTime" }) })
public class GoldRemainLog extends BaseLog {
	private static final long serialVersionUID = 1L;

	private long totalGold;

	private long totalFreeGold;

	public long getTotalGold() {
		return totalGold;
	}

	public void setTotalGold(long totalGold) {
		this.totalGold = totalGold;
	}

	public long getTotalFreeGold() {
		return totalFreeGold;
	}

	public void setTotalFreeGold(long totalFreeGold) {
		this.totalFreeGold = totalFreeGold;
	}

}
