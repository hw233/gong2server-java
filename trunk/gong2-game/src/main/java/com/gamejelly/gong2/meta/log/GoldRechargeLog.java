package com.gamejelly.gong2.meta.log;

import com.hadoit.game.common.framework.dao.annotation.Index;
import com.hadoit.game.common.framework.dao.annotation.Table;

@Table(recursion = true, indexs = { @Index(columns = { "serverId", "dayCreateTime" }),
		@Index(columns = { "opr", "dayCreateTime" }), @Index(columns = { "accountName" }),
		@Index(columns = { "deviceId" }) })
public class GoldRechargeLog extends AvatarBaseLog {
	private static final long serialVersionUID = 1L;

	private long freeGoldDelta;

	private long freeGold;

	private long goldDelta;

	private long gold;

	private long historyGold;

	/**
	 * 是否第一次充值
	 */
	private boolean firstCz;

	private String platform;

	private String deviceName;

	private String deviceVersion;

	private String deviceId;

	private String ip;

	private String productId;

	private int price;

	private String source;

	public long getFreeGoldDelta() {
		return freeGoldDelta;
	}

	public void setFreeGoldDelta(long freeGoldDelta) {
		this.freeGoldDelta = freeGoldDelta;
	}

	public long getFreeGold() {
		return freeGold;
	}

	public void setFreeGold(long freeGold) {
		this.freeGold = freeGold;
	}

	public long getGoldDelta() {
		return goldDelta;
	}

	public void setGoldDelta(long goldDelta) {
		this.goldDelta = goldDelta;
	}

	public long getGold() {
		return gold;
	}

	public void setGold(long gold) {
		this.gold = gold;
	}

	public long getHistoryGold() {
		return historyGold;
	}

	public void setHistoryGold(long historyGold) {
		this.historyGold = historyGold;
	}

	public boolean isFirstCz() {
		return firstCz;
	}

	public void setFirstCz(boolean firstCz) {
		this.firstCz = firstCz;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceVersion() {
		return deviceVersion;
	}

	public void setDeviceVersion(String deviceVersion) {
		this.deviceVersion = deviceVersion;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
