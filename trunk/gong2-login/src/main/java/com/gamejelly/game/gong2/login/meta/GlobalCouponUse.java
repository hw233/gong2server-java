package com.gamejelly.game.gong2.login.meta;

import com.hadoit.game.common.framework.dao.annotation.Index;
import com.hadoit.game.common.framework.dao.annotation.Table;

@Table(indexs = { @Index(columns = { "userId", "couponCode", "serverId" }) })
public class GlobalCouponUse implements Bean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;

	/**
	 * 使用者ID
	 */
	private String userId;
	
	/**
	 * 使用者账号
	 */
	private String accountName;

	/**
	 * 兑换码
	 */
	private String couponCode;
	
	private int serverId;
	
	private long createTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

}
