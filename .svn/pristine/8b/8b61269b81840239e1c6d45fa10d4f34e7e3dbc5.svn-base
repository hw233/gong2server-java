package com.gamejelly.gong2.global.app.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gamejelly.gong2.global.app.service.GlobalService;
import com.hadoit.game.engine.guardian.global.GlobalContext;
import com.hadoit.game.engine.guardian.global.proxy.GlobalServerProxy;

@Component("globalServerProxy")
public class GlobalServerProxyImpl implements GlobalServerProxy {

	private GlobalContext context;

	@Autowired
	private GlobalService globalService;

	@Override
	public void setContext(GlobalContext context) {
		this.context = context;
	}

	@Override
	public void onRegistered() {
		globalService.onServerStart();
	}

	@Override
	public void onUnregistered() {

	}

}
