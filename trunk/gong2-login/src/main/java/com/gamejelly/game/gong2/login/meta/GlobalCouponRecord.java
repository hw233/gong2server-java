package com.gamejelly.game.gong2.login.meta;

import com.hadoit.game.common.framework.dao.annotation.Column;
import com.hadoit.game.common.framework.dao.annotation.Table;

@Table()
public class GlobalCouponRecord implements Bean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;

	/**
	 * 兑换码
	 */
	@Column(unique = true)
	private String couponCode;

	/**
	 * 子渠道
	 */
	private String opr;

	/**
	 * 礼包ID
	 */
	private int giftId;

	/**
	 * 是否启用
	 */
	private int enable;

	private long createTime;

	public String getCouponCode() {
		return couponCode;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public int getGiftId() {
		return giftId;
	}

	public void setGiftId(int giftId) {
		this.giftId = giftId;
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

	public String getOpr() {
		return opr;
	}

	public void setOpr(String opr) {
		this.opr = opr;
	}

}
