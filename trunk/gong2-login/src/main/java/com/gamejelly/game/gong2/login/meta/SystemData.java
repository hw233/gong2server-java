package com.gamejelly.game.gong2.login.meta;

import com.hadoit.game.common.framework.dao.annotation.Table;

@Table
public class SystemData implements Bean {
	private static final long serialVersionUID = 1L;

	private long id;

	/**
	 * 注册是否开放
	 */
	private boolean openRegist;

	/**
	 * 实名是否开放（true 真，false假）
	 */
	private boolean openShiming;

	/**
	 * 实名认证是否显示
	 */
	private boolean openShimingShow;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isOpenRegist() {
		return openRegist;
	}

	public void setOpenRegist(boolean openRegist) {
		this.openRegist = openRegist;
	}

	public boolean isOpenShiming() {
		return openShiming;
	}

	public void setOpenShiming(boolean openShiming) {
		this.openShiming = openShiming;
	}

	public boolean isOpenShimingShow() {
		return openShimingShow;
	}

	public void setOpenShimingShow(boolean openShimingShow) {
		this.openShimingShow = openShimingShow;
	}
}
