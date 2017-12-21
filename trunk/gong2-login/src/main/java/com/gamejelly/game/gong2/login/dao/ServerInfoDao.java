package com.gamejelly.game.gong2.login.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.meta.ServerInfo;

@Component("serverInfoDao")
public class ServerInfoDao extends BaseDomainDao<ServerInfo> {

	@Override
	protected Class<ServerInfo> getDomainClass() {
		return ServerInfo.class;
	}

	public ServerInfo getServerInfo(long id) {
		return getById(id);
	}

	public int getAllServerCount() {
		return getCount();
	}

	public List<ServerInfo> getAllServer(int limit, int offset) {
		return getListByCondition((String) null, "id", false, limit, offset, new Object[0]);
	}

	public void addServer(ServerInfo serverInfo) {
		addObject(serverInfo);
	}

	

	private List<Object[]> convertRepeatParams(List<?> pList) {
		if (CollectionUtils.isEmpty(pList)) {
			return Collections.emptyList();
		}
		List<Object[]> ret = new ArrayList<Object[]>();
		for (Object o : pList) {
			ret.add(new Object[] { o });
		}
		return ret;
	}

	public List<ServerInfo> getServerInfoList(List<Integer> srvIds) {
		return getListByConditionRepeat("serverId = ?", convertRepeatParams(srvIds));
	}
	
	public ServerInfo getServerBy(int serverId, String oprGroup) {
		return getObjectByCondition("serverId=? and oprGroup=?", serverId, oprGroup);
	}

	public List<ServerInfo> getServerByOprGroup(String oprGroup) {
		return getListByFullCondition("oprGroup=?", oprGroup);
	}

	public void updateServerState(int serverId, String oprGroup, int state) {
		updateColumnValueByCondition("serverId=? and oprGroup=?", "state", state, serverId, oprGroup);
	}
	
	public void deleteServerBy(int serverId, String oprGroup) {
		this.deleteByCondition("serverId=? and oprGroup=?", serverId, oprGroup);
	}
}
