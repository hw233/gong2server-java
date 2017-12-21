package com.gamejelly.gong2.gas.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.gamejelly.gong2.gas.admin.meta.ServerConfig;
import com.gamejelly.gong2.gas.admin.meta.VersionData;
import com.gamejelly.gong2.utils.GongConstants;
import com.google.gson.JsonObject;
import com.hadoit.game.common.framework.serialize.json.JsonSerialize;
import com.hadoit.game.common.framework.utils.IPRange;

@Component("serverConfigService")
public class ServerConfigService {
	private final AtomicInteger curOnlineCount = new AtomicInteger(0);

	private Map<String, VersionData> versionMap = new HashMap<String, VersionData>();

	@Autowired
	@Value("${config.open_default}")
	private boolean open;

	@Autowired
	@Value("${config.max_online_count}")
	private int maxOnlineCount;

	@Autowired
	@Value("${config.user_group_default}")
	private int userGroupDefault;

	@Autowired
	@Value("${config.user_group_login}")
	private int userGroupLogin;

	@Autowired
	@Value("${config.list_server_visible_ips}")
	private String listServerVisibleIps;

	private List<IPRange> rangeList;

	@PostConstruct
	void init() {
		rangeList = new ArrayList<IPRange>();
		String[] allowIpList = StringUtils.splitByWholeSeparator(listServerVisibleIps, ",");
		if (ArrayUtils.isNotEmpty(allowIpList)) {
			IPRange range;
			for (String allowIp : allowIpList) {
				range = new IPRange(allowIp);
				if (range.isValid()) {
					rangeList.add(range);
				}
			}
		}
	}

	public ServerConfig getServerConfig() {
		ServerConfig serverConfig = new ServerConfig();
		serverConfig.setOpen(open);
		serverConfig.setMaxOnlineCount(maxOnlineCount);
		serverConfig.setCurOnlineCount(curOnlineCount.get());
		serverConfig.setUserGroupDefault(userGroupDefault);
		serverConfig.setUserGroupLogin(userGroupLogin);
		serverConfig.setOpenServerDate(GongConstants.getOpenServerTimeStr());
		serverConfig.setVersionDatas(new ArrayList<VersionData>(versionMap.values()));
		return serverConfig;
	}

	public boolean checkClientVersion(String opr, String clientAppVersion, String clientResVersion) {
		if (!versionMap.containsKey(opr)) {
			return true;
		}

		if (StringUtils.isBlank(clientResVersion)) {
			return true;
		}

		VersionData vd = versionMap.get(opr);

		Long lResVersion = Long.valueOf(vd.getResVersion()); // 服务器取到的资源版本号
		Long lClientResVersion = Long.valueOf(clientResVersion); // 客户端资源版本号

		if (StringUtils.isNotBlank(vd.getResVersion()) && StringUtils.isNotBlank(clientResVersion)
				&& lResVersion > lClientResVersion) {
			return false;
		}

		return true;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public int getMaxOnlineCount() {
		return maxOnlineCount;
	}

	public void setMaxOnlineCount(int maxOnlineCount) {
		this.maxOnlineCount = maxOnlineCount;
	}

	public int getCurOnlineCount() {
		return curOnlineCount.get();
	}

	public int addAndGetCurOnlineCount(int delta) {
		return curOnlineCount.addAndGet(delta);
	}

	public int getUserGroupDefault() {
		return userGroupDefault;
	}

	public void setUserGroupDefault(int userGroupDefault) {
		this.userGroupDefault = userGroupDefault;
	}

	public int getUserGroupLogin() {
		return userGroupLogin;
	}

	public void setUserGroupLogin(int userGroupLogin) {
		this.userGroupLogin = userGroupLogin;
	}

	public Map<String, VersionData> getVersionMap() {
		return versionMap;
	}

	public void setVersionMap(Map<String, VersionData> versionMap) {
		this.versionMap = versionMap;
	}

	public void addVersionData(String opr, String versionJson) {
		JsonObject versionObj = JsonSerialize.getDefault().parse(versionJson).getAsJsonObject();
		String resVersion = versionObj.get("version").getAsString();
		VersionData vd = new VersionData();
		vd.setResVersion(resVersion);
		vd.setOpr(opr);
		this.versionMap.put(opr, vd);
	}

	public boolean validateWhiteIp(String ip) {
		if (StringUtils.isNotBlank(ip) && rangeList != null) {
			for (IPRange range : rangeList) {
				if (range.isInRange(ip)) {
					return true;
				}
			}
		}
		return false;
	}

}
