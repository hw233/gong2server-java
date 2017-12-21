package com.gamejelly.game.gong2.login.meta;

import com.hadoit.game.common.framework.dao.annotation.Column;
import com.hadoit.game.common.framework.dao.annotation.Table;

/**
 * 兑换码
 * 
 * @author apple
 *
 */
@Table()
public class CouponRecord implements Bean {
	private static final long serialVersionUID = 1L;

	private long id;

	/**
	 * 兑换码
	 */
	@Column(unique = true)
	private String couponCode;

	/**
	 * 礼包类型
	 */
	@Column(index = true)
	private String giftType;
	

	/**
	 * 礼包ID
	 */
	private int giftId;
	/**
	 * 渠道
	 */
	private String oprGroup;
	
	/**
	 * 子渠道
	 */
	private String opr;

	/**
	 * 批次
	 */
	@Column(index = true)
	private String batchCode;

	/**
	 * 是否启用
	 */
	private int enable;

	private long createTime;

	/**
	 * 兑换人
	 */
	@Column(index = true)
	private String claimUser;

	/**
	 * 兑换服务器
	 */
	@Column(index = true)
	private int claimServerId;

	/**
	 * 兑换时间
	 */
	private long claimTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public String getGiftType() {
		return giftType;
	}

	public void setGiftType(String giftType) {
		this.giftType = giftType;
	}
	public String getOprGroup() {
		return oprGroup;
	}

	public void setOprGroup(String oprGroup) {
		this.oprGroup = oprGroup;
	}
	public String getOpr() {
		return opr;
	}

	public void setOpr(String opr) {
		this.opr = opr;
	}

	public String getBatchCode() {
		return batchCode;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

	public int getEnable() {
		return enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getClaimUser() {
		return claimUser;
	}

	public void setClaimUser(String claimUser) {
		this.claimUser = claimUser;
	}

	public long getClaimTime() {
		return claimTime;
	}

	public void setClaimTime(long claimTime) {
		this.claimTime = claimTime;
	}

	public int getGiftId() {
		return giftId;
	}

	public void setGiftId(int giftId) {
		this.giftId = giftId;
	}

	public int getClaimServerId() {
		return claimServerId;
	}

	public void setClaimServerId(int claimServerId) {
		this.claimServerId = claimServerId;
	}

}
