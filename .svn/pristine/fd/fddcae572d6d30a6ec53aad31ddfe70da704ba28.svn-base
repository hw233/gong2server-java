package com.gamejelly.game.gong2.login.meta;

import com.hadoit.game.common.framework.dao.annotation.Column;
import com.hadoit.game.common.framework.dao.annotation.Index;
import com.hadoit.game.common.framework.dao.annotation.Table;

/**
 * 被邀请的玩家信息
 * 
 * @author apple
 * 
 */
@Table(recursion = true, indexs = { @Index(columns = { "crossServerId", "avatarId" }, unique = true) })
public class InvitationPlayerModel implements Bean {
	private static final long serialVersionUID = 1L;

	private long id;

	private String crossServerId;

	private String avatarId;

	private String accountName;

	private String roleName;

	private int lv;

	private int vipLv;

	private int sex;

	private int iconIdex;

	@Column(index = true)
	private String invitationCode;

	private long createTime;

	public String getCrossServerId() {
		return crossServerId;
	}

	public void setCrossServerId(String crossServerId) {
		this.crossServerId = crossServerId;
	}

	public String getAvatarId() {
		return avatarId;
	}

	public void setAvatarId(String avatarId) {
		this.avatarId = avatarId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
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

	public int getIconIdex() {
		return iconIdex;
	}

	public void setIconIdex(int iconIdex) {
		this.iconIdex = iconIdex;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getInvitationCode() {
		return invitationCode;
	}

	public void setInvitationCode(String invitationCode) {
		this.invitationCode = invitationCode;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}
}
