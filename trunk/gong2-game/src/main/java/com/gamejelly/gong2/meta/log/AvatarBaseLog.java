package com.gamejelly.gong2.meta.log;

import com.hadoit.game.common.framework.dao.annotation.Column;

public abstract class AvatarBaseLog extends BaseLog {
	private static final long serialVersionUID = 1L;

	private long gbId;

	@Column(nullable = false)
	private String accountName;

	@Column(nullable = false)
	private String avatarId;

	@Column(nullable = false)
	private String roleName;

	private int lv;

	private int vipLv;

	public long getGbId() {
		return gbId;
	}

	public void setGbId(long gbId) {
		this.gbId = gbId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAvatarId() {
		return avatarId;
	}

	public void setAvatarId(String avatarId) {
		this.avatarId = avatarId;
	}

	public int getLv() {
		return lv;
	}

	public void setLv(int lv) {
		this.lv = lv;
	}

	public int getVipLv() {
		return vipLv;
	}

	public void setVipLv(int vipLv) {
		this.vipLv = vipLv;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
