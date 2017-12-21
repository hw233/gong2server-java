package com.gamejelly.gong2.meta.log;

import com.hadoit.game.common.framework.dao.annotation.Index;
import com.hadoit.game.common.framework.dao.annotation.Table;

@Table(recursion = true, indexs = { @Index(columns = { "serverId", "dayCreateTime" }),
		@Index(columns = { "opr", "dayCreateTime" }) })
public class LoginLog extends AvatarBaseLog {
	private static final long serialVersionUID = 1L;

	private boolean newAvatar;

	private long avatarCreateTime;

	private String platform;

	private String deviceName;

	private String deviceVersion;

	private String deviceId;

	private String ip;

	private String source;

	public boolean isNewAvatar() {
		return newAvatar;
	}

	public void setNewAvatar(boolean newAvatar) {
		this.newAvatar = newAvatar;
	}

	public long getAvatarCreateTime() {
		return avatarCreateTime;
	}

	public void setAvatarCreateTime(long avatarCreateTime) {
		this.avatarCreateTime = avatarCreateTime;
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
