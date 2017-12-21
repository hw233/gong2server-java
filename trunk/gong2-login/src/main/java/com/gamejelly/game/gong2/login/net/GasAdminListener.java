package com.gamejelly.game.gong2.login.net;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hadoit.game.common.lang.Pair;
import com.hadoit.game.engine.core.rpc.base.annotation.RpcEvent;
import com.hadoit.game.engine.core.rpc.base.constant.RpcEventType;
import com.hadoit.game.engine.core.rpc.base.handler.RpcHandler;
import com.hadoit.game.engine.core.rpc.simple.context.ClientChannelContext;
import com.gamejelly.game.gong2.login.service.ServerInfoService;
import com.gamejelly.game.gong2.login.utils.FsGameLoginConst;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;

@Scope("prototype")
@Component("gasAdminListener")
public class GasAdminListener implements RpcHandler {

	@Autowired
	private ServerInfoService serverInfoService;

	@Autowired
	private GasAdminClientManager gasAdminClientManager;

	private String host;

	private int port;

	public GasAdminListener() {

	}

	public GasAdminListener(String host, int port) {
		this.host = host;
		this.port = port;
	}

	private void updateServerState(int state) {
		List<Pair<Integer, String>> pList = gasAdminClientManager.getOprGroupAndServerIdListBy(host, port);
		for (Pair<Integer, String> p : pList) {
			serverInfoService.updateServerState(p.getFirst(), p.getSecond(), state);
		}
	}

	@RpcEvent(RpcEventType.CHANNEL_CONNECTED)
	public void onConnect(final ClientChannelContext channelContext) throws Exception {
		LoggerUtils.info("gas connected! channel=", channelContext.getChannel(), Thread.currentThread().toString());
		updateServerState(FsGameLoginConst.SERVER_STATE_OPEN);
	}

	@RpcEvent(RpcEventType.CHANNEL_DISCONNECTED)
	public void onDisconnected(final ClientChannelContext channelContext) throws Exception {
		LoggerUtils.warn("gas disconnected! channel=", channelContext.getChannel(), Thread.currentThread().toString());
		updateServerState(FsGameLoginConst.SERVER_STATE_CLOSE);
	}

}
