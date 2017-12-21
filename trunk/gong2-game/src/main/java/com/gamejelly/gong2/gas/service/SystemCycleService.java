package com.gamejelly.gong2.gas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.hadoit.game.engine.guardian.gas.GasManager;

@Component("systemCycleService")
public class SystemCycleService {
	@Autowired
	@Qualifier("gasManager")
	private GasManager gasManager;

	public void onServerStart() {

	}

	public void onGlobalConnected() {

	}

	public void onGlobalDisConnected() {

	}
}
