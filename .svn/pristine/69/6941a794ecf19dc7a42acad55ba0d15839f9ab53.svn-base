package com.gamejelly.game.gong2.login.meta;

import com.hadoit.game.common.framework.dao.annotation.Column;
import com.hadoit.game.common.framework.dao.annotation.Index;
import com.hadoit.game.common.framework.dao.annotation.Table;

/**
 * 第三方sdk和设备id的关联表
 * 
 * @author PAULWONG(wangdihua@163.com)
 *
 */
@Table(recursion = true, indexs = { @Index(columns = { "accountName", "deviceId" }) })
public class SdkAccountRelation implements Bean {
	private static final long serialVersionUID = 1L;

	private long id;

	@Column(index = true)
	private String accountName;

	private String deviceId;

	private long createTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

}
