package com.gamejelly.gong2.global.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hadoit.game.engine.guardian.gas.entity.RawCommandCallback;
import com.hadoit.game.engine.guardian.gas.entity.RawCommandFutureCallback;
import com.hadoit.game.engine.guardian.global.GlobalManager;

@Component("globalService")
public class GlobalService {

	@Autowired
	private GlobalManager globalManager;

	public void executeRawCommand(String command, Object[] args, RawCommandCallback callback) {
		globalManager.executeRawCommand(null, command, args, new RawCommandFutureCallback(callback));
	}

	public void executeRawCommandBalance(String balanceId, String command, Object[] args, RawCommandCallback callback) {
		globalManager.executeRawCommand(balanceId, command, args, new RawCommandFutureCallback(callback));
	}

	public void onServerStart() {

	}

}
