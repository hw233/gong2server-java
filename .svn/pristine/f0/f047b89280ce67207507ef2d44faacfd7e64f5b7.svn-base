package com.gamejelly.gong2.gas.cross.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.gamejelly.gong2.gas.service.SystemCycleService;
import com.hadoit.game.engine.guardian.gas.GasManager;
import com.hadoit.game.engine.guardian.global.client.GlobalClient;
import com.hadoit.game.engine.guardian.global.client.listener.GlobalClientListener;

@Component("crossServerClientListener")
public class CrossServerClientListener implements GlobalClientListener {

	private GlobalClient context;

	@Autowired
	@Qualifier("gasManager")
	private GasManager gasManager;

	@Autowired
	@Qualifier("systemCycleService")
	private SystemCycleService systemCycleService;

	@Override
	public void setContext(GlobalClient context) {
		this.context = context;
	}

	@Override
	public void callClient(int clientChannelId, String op, Object[] params) {
		gasManager.callClient(clientChannelId, op, params);
	}

	@Override
	public void closeClient(int clientChannelId) {
		gasManager.closeClient(clientChannelId);
	}

	@Override
	public void onConnect() {
		systemCycleService.onGlobalConnected();
		// context.callGlobal(GlobalServerRpcConstants.REQ_TEST, "hello");
		// context.callOtherServer("globalClient-cross$test$1",
		// GlobalServerRpcConstants.CALL_OTHER_TEST, "hello globalClient-1");
	}

	@Override
	public void onDisconnect() {
		systemCycleService.onGlobalDisConnected();
	}
}
