package com.gamejelly.gong2.dbs.proxy;

import com.gamejelly.gong2.dbs.service.DbsService;
import com.gamejelly.gong2.meta.AvatarModel;
import com.gamejelly.gong2.meta.share.SharedModel;
import com.gamejelly.gong2.utils.LoggerHelper;
import com.hadoit.game.engine.guardian.core.Persistable;
import com.hadoit.game.engine.guardian.dbs.base.RawCommandResult;
import com.hadoit.game.engine.guardian.dbs.proxy.DbsServerProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dbsServerProxySql")
public class DbsServerProxySqlImpl implements DbsServerProxy {

	@Autowired
	private DbsService dbsService;

	@Override
	public void onRegistered() {
	}

	@Override
	public void onUnregistered() {
	}

	@Override
	public void saveGameObject(String entityType, Persistable entityData) {
		if ("AvatarEntity".endsWith(entityType)) {
			long startTime = System.currentTimeMillis();
			try {
				dbsService.saveAvatarModel((AvatarModel) entityData);
			} finally {
				LoggerHelper.slowTimeLocal("saveGameObject", startTime);
			}
			return;
		}else if ("SharedEntity".endsWith(entityType)) {
			long startTime = System.currentTimeMillis();
			try {
				dbsService.saveSharedModel((SharedModel) entityData);
			} finally {
				LoggerHelper.slowTimeLocal("saveGameObject(SharedEntity)", startTime);
			}
			return;
		}
		throw new UnsupportedOperationException("DbsServerProxySqlImpl.saveGameObject");
	}

	@Override
	public Persistable loadGameObject(String entityType, long dbId) {
		if ("AvatarEntity".endsWith(entityType)) {
			long startTime = System.currentTimeMillis();
			try {
				return dbsService.getAvatarModel(dbId);
			} finally {
				LoggerHelper.slowTimeLocal("loadGameObject", startTime);

			}
		}else if ("SharedEntity".endsWith(entityType)) {
			long startTime = System.currentTimeMillis();
			try {
				return dbsService.getSharedModel(dbId);
			} finally {
				LoggerHelper.slowTimeLocal("loadGameObject(SharedEntity)", startTime);
			}
		}
		throw new UnsupportedOperationException("DbsServerProxySqlImpl.loadGameObject");
	}

	@Override
	public boolean deleteGameObject(String entityType, long dbId) {
		if ("AvatarEntity".endsWith(entityType)) {
			dbsService.deleteAvatarModel(dbId);
			return true;
		}




		throw new UnsupportedOperationException("DbsServerProxySqlImpl.deleteGameObject");
	}

	@Override
	public RawCommandResult executeRawCommand(String command, Object[] args) {
		throw new UnsupportedOperationException("DbsServerProxySqlImpl.executeRawCommand");
	}

}
