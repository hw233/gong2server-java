package com.hadoit.game.engine.guardian.global.base;

import java.util.HashMap;
import java.util.Map;

import com.hadoit.game.engine.core.rpc.simple.context.ServerChannelContext;
import com.hadoit.game.engine.guardian.core.exception.GuardianServerException;
import com.hadoit.game.engine.guardian.utils.GuardianConstants;

/**
 * @author bezy 2015-3-10
 * 
 */
public class ServerInfoManager {

	private final Map<String, ServerInfo> serverInfoMap = new HashMap<String, ServerInfo>();

	public ServerInfo registerServer(String identify, String addr, ServerChannelContext channelContext) {
		if (serverInfoMap.containsKey(identify)) {
			throw new GuardianServerException("Server: " + identify + " registered duplicate!");
		}
		channelContext.putAttribute(GuardianConstants.ATTR_GLOBAL_CLIENT_IDENTIFY, identify);
		ServerInfo serverInfo = new ServerInfo(identify, addr, channelContext);
		serverInfoMap.put(serverInfo.getIdentify(), serverInfo);

		return serverInfo;
	}

	public String getServerIdentify(ServerChannelContext channelContext) {
		return (String) channelContext.getAttribute(GuardianConstants.ATTR_GLOBAL_CLIENT_IDENTIFY);
	}

	public ServerInfo unregisterServer(String identify) {
		ServerInfo serverInfo = serverInfoMap.remove(identify);
		if (serverInfo != null && serverInfo.getChannelContext() != null) {
			serverInfo.getChannelContext().removeAttribute(GuardianConstants.ATTR_GLOBAL_CLIENT_IDENTIFY);
		}
		return serverInfo;
	}

	public ServerInfo getServer(String identify) {
		return serverInfoMap.get(identify);
	}

	public Map<String, ServerInfo> getServerInfoMap() {
		return serverInfoMap;
	}

}
