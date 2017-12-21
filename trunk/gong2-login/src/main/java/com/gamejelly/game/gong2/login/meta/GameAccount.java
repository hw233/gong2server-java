package com.gamejelly.game.gong2.login.meta;

import java.util.List;

import com.hadoit.game.common.framework.dao.annotation.Column;
import com.hadoit.game.common.framework.dao.annotation.Table;
import com.hadoit.game.common.framework.dao.convert.impl.CollectionNumberOrStringColumnConvert;

@Table
public class GameAccount implements Bean {
	private static final long serialVersionUID = 1L;

	private long id;

	@Column(unique = true)
	private String account; // 账户 字幕+数字+_.

	private String salt;// salt也需要存储

	private String pass; // md5(md5(pass) + salt) 密码

	private long createTime; // 建号时间

	private long ticketCreateTime; // ticket有效时间

	@Column(convertClazz = CollectionNumberOrStringColumnConvert.class)
	private List<Integer> lastLoginServerIds; // 上次登录服务器(两个)

	private String platform;

	private String deviceName; // 设备名称

	private String deviceVersion; // 设备操作系统版本

	@Column(index = true)
	private String deviceId;

	private String ip; // IP地址

	private long lastLoginTime; // 上次登录时间

	@Column(index = true)
	private String phone; // 手机号

	private String name; // 真实姓名

	private String idCardNo;// 身份证

	private int smyzErrorCount;// 实名验证错误次数

	private long smyzErrorTime;// 实名验证错误时间

	private String lastLoginOpr;// 最后登录使用的opr

	private String idfa;
	@Column(defaultValue="0")
    private int  accountgroup;//此字段为封账号专用
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getTicketCreateTime() {
		return ticketCreateTime;
	}

	public void setTicketCreateTime(long ticketCreateTime) {
		this.ticketCreateTime = ticketCreateTime;
	}

	public List<Integer> getLastLoginServerIds() {
		return lastLoginServerIds;
	}

	public void setLastLoginServerIds(List<Integer> lastLoginServerIds) {
		this.lastLoginServerIds = lastLoginServerIds;
	}

	public long getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public int getSmyzErrorCount() {
		return smyzErrorCount;
	}

	public void setSmyzErrorCount(int smyzErrorCount) {
		this.smyzErrorCount = smyzErrorCount;
	}

	public long getSmyzErrorTime() {
		return smyzErrorTime;
	}

	public void setSmyzErrorTime(long smyzErrorTime) {
		this.smyzErrorTime = smyzErrorTime;
	}

	public String getLastLoginOpr() {
		return lastLoginOpr;
	}

	public void setLastLoginOpr(String lastLoginOpr) {
		this.lastLoginOpr = lastLoginOpr;
	}

	public String getIdfa() {
		return idfa;
	}

	public void setIdfa(String idfa) {
		this.idfa = idfa;
	}

	public int getAccountgroup() {
		return accountgroup;
	}

	public void setAccountgroup(int accountgroup) {
		this.accountgroup = accountgroup;
	}

}
