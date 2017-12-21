package com.gamejelly.game.gong2.login.meta;

import com.hadoit.game.common.framework.dao.annotation.Column;
import com.hadoit.game.common.framework.dao.annotation.Index;
import com.hadoit.game.common.framework.dao.annotation.Table;

@Table(recursion = true, indexs = { @Index(columns = { "appId", "idfa", "deviceId" }) })
public class IdfaDeviceData implements Bean {
	private static final long serialVersionUID = 1L;

	private long id;

	private String appId;

	@Column(index=true)
	private String idfa;

	private String deviceId;

	private long createTime;

	public IdfaDeviceData(String idfa, long createTime) {
		this.idfa = idfa;
		this.createTime = createTime;
	}

	public IdfaDeviceData() {
	}

	public String getIdfa() {
		return idfa;
	}

	public void setIdfa(String idfa) {
		this.idfa = idfa;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

}
