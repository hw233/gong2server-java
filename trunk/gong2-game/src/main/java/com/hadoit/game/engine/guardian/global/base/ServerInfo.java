package com.hadoit.game.engine.guardian.global.base;

import com.hadoit.game.engine.core.rpc.simple.context.ServerChannelContext;
import com.hadoit.game.engine.guardian.core.RemoteType;

/**
 * @author bezy 2015-3-9
 * 
 */
public class ServerInfo implements RemoteType {
	private static final long serialVersionUID = 1L;

	private String identify;

	private String addr;

	private ServerChannelContext channelContext;

	public ServerInfo() {
	}

	public ServerInfo(String identify, String addr, ServerChannelContext channelContext) {
		this.identify = identify;
		this.addr = addr;
		this.channelContext = channelContext;
	}

	public String getIdentify() {
		return identify;
	}

	public void setIdentify(String identify) {
		this.identify = identify;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public ServerChannelContext getChannelContext() {
		return channelContext;
	}

	public void setChannelContext(ServerChannelContext channelContext) {
		this.channelContext = channelContext;
	}

}
