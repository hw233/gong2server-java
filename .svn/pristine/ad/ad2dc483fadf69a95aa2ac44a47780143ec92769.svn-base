package com.gamejelly.gong2.global.dbs.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gamejelly.gong2.global.dbs.service.GlobalJedisService;
import com.hadoit.game.engine.guardian.core.Persistable;
import com.hadoit.game.engine.guardian.dbs.base.RawCommandResult;
import com.hadoit.game.engine.guardian.dbs.proxy.DbsServerProxy;

@Component("globalDbsServerProxySql")
public class GlobalDbsServerProxySqlImpl implements DbsServerProxy {
	@Autowired
	private GlobalJedisService globalJedisService;

	@Override
	public void onRegistered() {
		globalJedisService.touchJedis();
		globalJedisService.initRank();
	}

	@Override
	public void onUnregistered() {
	}

	@Override
	public void saveGameObject(String entityType, Persistable entityData) {
		throw new UnsupportedOperationException("GlobalDbsServerProxySqlImpl.saveGameObject");
	}

	@Override
	public Persistable loadGameObject(String entityType, long dbId) {
		throw new UnsupportedOperationException("GlobalDbsServerProxySqlImpl.loadGameObject");
	}

	@Override
	public boolean deleteGameObject(String entityType, long dbId) {
		throw new UnsupportedOperationException("GlobalDbsServerProxySqlImpl.deleteGameObject");
	}

	@Override
	public RawCommandResult executeRawCommand(String command, Object[] args) {
		throw new UnsupportedOperationException("DbsServerProxySqlImpl.executeRawCommand");
	}

}
