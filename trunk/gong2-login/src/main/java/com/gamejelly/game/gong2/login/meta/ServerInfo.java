package com.gamejelly.game.gong2.login.meta;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.hadoit.game.common.framework.dao.annotation.Column;
import com.hadoit.game.common.framework.dao.annotation.Index;
import com.hadoit.game.common.framework.dao.annotation.Table;

@Table(indexs = { @Index(columns = { "serverId", "oprGroup" }, unique = true) })
public class ServerInfo implements Bean {
	private static final long serialVersionUID = 1L;

	private long id;

	private int serverId; // 服务器ID

	private int zone;// 区号

	private String serverName;// 服务器名字

	private String oprGroup;// (test,ios,sj91...)

	@Column(length = 1024)
	private String oprMatchStr; // 匹配字符串, 逗号分割, 用来匹配客户单传递过来的opr

	private String ip; // 服务器IP

	private int port;// 服务器端口

	@JsonIgnore
	private String adminIp; // 管理地址

	@JsonIgnore
	private int adminPort; // 管理端口

	private int state; // 状态(1开启,0关闭(维护中))

	private boolean newSrv; // 是否新服
	
	private int  isPush; // 是否推荐

	public int getIsPush() {
		return isPush;
	}

	public void setIsPush(int isPush) {
		this.isPush = isPush;
	}

	private String closedDesc; // 关服后的提示信息

	private long createTime;// 服务器建立时间

	private int visibleType; // 可见类型 0.全网可见(默认) 1.自定义网络可见

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getZone() {
		return zone;
	}

	public void setZone(int zone) {
		this.zone = zone;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getOprGroup() {
		return oprGroup;
	}

	public void setOprGroup(String oprGroup) {
		this.oprGroup = oprGroup;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getAdminIp() {
		return adminIp;
	}

	public void setAdminIp(String adminIp) {
		this.adminIp = adminIp;
	}

	public int getAdminPort() {
		return adminPort;
	}

	public void setAdminPort(int adminPort) {
		this.adminPort = adminPort;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public boolean isNewSrv() {
		return newSrv;
	}

	public void setNewSrv(boolean newSrv) {
		this.newSrv = newSrv;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public String getOprMatchStr() {
		return oprMatchStr;
	}

	public void setOprMatchStr(String oprMatchStr) {
		this.oprMatchStr = oprMatchStr;
	}

	public String getClosedDesc() {
		return closedDesc;
	}

	public void setClosedDesc(String closedDesc) {
		this.closedDesc = closedDesc;
	}

	public int getVisibleType() {
		return visibleType;
	}

	public void setVisibleType(int visibleType) {
		this.visibleType = visibleType;
	}

}