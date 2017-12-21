package com.gamejelly.game.gong2.login.meta;

import com.hadoit.game.common.framework.dao.annotation.Column;
import com.hadoit.game.common.framework.dao.annotation.Index;
import com.hadoit.game.common.framework.dao.annotation.Table;

/**
 * 邀请码记录表
 * 
 * @author apple
 * 
 */
@Table(recursion = true, indexs = { @Index(columns = { "crossServerId",
		"avatarId" }) })
public class InvitationCodeRecord implements Bean {
	private static final long serialVersionUID = 1L;

	private long id;

	private String crossServerId;

	private String avatarId;

	private String accountName;

	@Column(unique = true)
	private String invitationCode;

	private int inviteCount;

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

	public String getInvitationCode() {
		return invitationCode;
	}

	public void setInvitationCode(String invitationCode) {
		this.invitationCode = invitationCode;
	}

	public int getInviteCount() {
		return inviteCount;
	}

	public void setInviteCount(int inviteCount) {
		this.inviteCount = inviteCount;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
