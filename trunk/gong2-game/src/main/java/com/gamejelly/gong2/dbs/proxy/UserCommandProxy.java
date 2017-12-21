package com.gamejelly.gong2.dbs.proxy;

import java.util.List;


import com.gamejelly.gong2.dbs.service.GameService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gamejelly.gong2.meta.UserMessageModel;
import com.gamejelly.gong2.meta.RelationModel;
import com.gamejelly.gong2.dbs.annotation.AutoDbsCommandProxy;
import com.gamejelly.gong2.dbs.service.DbsService;
import com.gamejelly.gong2.dbs.service.LogService;
import com.gamejelly.gong2.meta.log.BaseLog;
import com.gamejelly.gong2.utils.GongDbConstants;
import com.gamejelly.gong2.utils.LoggerHelper;
import com.hadoit.game.engine.guardian.dbs.base.CommandRpc;
import com.hadoit.game.engine.guardian.dbs.base.RawCommandResult;
import com.hadoit.game.engine.guardian.dbs.proxy.DbsCommandProxy;

import java.util.List;

@AutoDbsCommandProxy
@Component("userCommandProxy")
public class UserCommandProxy implements DbsCommandProxy {

	@Autowired
	private DbsService dbsService;

	@Autowired
	private LogService logService;
	@Autowired
	private GameService gameService;

	@CommandRpc(fullAlias = GongDbConstants.CMD_LOG, unpack = true)
	public RawCommandResult log(BaseLog baseLog) {
		LoggerHelper.info("log", null);
		logService.dbLog(baseLog);
		return new RawCommandResult();
	}

	@CommandRpc(fullAlias = GongDbConstants.CMD_AVATAR_SELECT, unpack = true)
	public RawCommandResult avatarSelect(String accountName, String avatarId, int serverId) {
		return new RawCommandResult(dbsService.selectAvatarModelId(accountName, avatarId, serverId));
	}

	@CommandRpc(fullAlias = GongDbConstants.CMD_CHECK_CAN_CREATE_ROLE, unpack = true)
	public RawCommandResult checkCanCreateRole(String accountName, int serverId, String roleName) {
		LoggerHelper.info("checkCanCreateRole", null, accountName, serverId, roleName);
		return new RawCommandResult(dbsService.checkCanCreateRole(accountName, serverId, roleName));
	}

	@CommandRpc(fullAlias = GongDbConstants.CMD_GET_AVARAE_ID_BY_ROLE, unpack = true)
	public RawCommandResult getAvatarIdByDf(String roleName) {
		LoggerHelper.info("getAvatarIdByDf", null, roleName);
		return new RawCommandResult(dbsService.getAvatarIdByRole(roleName));
	}

	@CommandRpc(fullAlias = GongDbConstants.CMD_GET_AVATAR_UID_BY_ROLE) 
	public RawCommandResult getAvatarUIdByRoleName(String roleName) {
		return new RawCommandResult(dbsService.getAvatarUIdByRole(roleName));
	}
	
	@CommandRpc(fullAlias = GongDbConstants.CMD_GET_SHARED_DATA, unpack = true)
	public RawCommandResult getSharedData() {
		LoggerHelper.info("getSharedData", null);
		return new RawCommandResult(dbsService.getSharedData());
	}


	@CommandRpc(fullAlias = GongDbConstants.CMD_SELECT_SHARE_MODEL_ID, unpack = true)
	public RawCommandResult selectSharedModelId(String uid) {

		LoggerHelper.info("selectSharedModelId", null, uid);
		return new RawCommandResult(dbsService.selectSharedModelId(uid));
	}

	@CommandRpc(fullAlias = GongDbConstants.CMD_GET_RELATION_MODEL, unpack = true)
	public RawCommandResult getRelationModel(String avatarId, String targetAvatarId) {
		LoggerHelper.info("getRelationModel", null, avatarId, targetAvatarId);
		return new RawCommandResult(dbsService.getRelationModel(avatarId, targetAvatarId));
	}
	
	@CommandRpc(fullAlias = GongDbConstants.CMD_UPDATE_RELATION_MODEL, unpack = true)
	public RawCommandResult updateRelationModel(RelationModel relationModel) {
		LoggerHelper.info("updateRelationModel", null);
		dbsService.updateRelationModel(relationModel);
		return new RawCommandResult();
	}
	
	@CommandRpc(fullAlias = GongDbConstants.CMD_GET_RELATION_LIST, unpack = true)
	public RawCommandResult selectRelationList(String avatarId, int relationType) {
		LoggerHelper.info("selectRelationList", null, avatarId);
		return new RawCommandResult(dbsService.getRelationList(avatarId, relationType));
	}
	
	@CommandRpc(fullAlias = GongDbConstants.CMD_GET_SIMPLE_PLAYER_INFO, unpack = true)
	public RawCommandResult getSimplePlayerInfo(String avatarId, String targetAvatarId) {
		return new RawCommandResult(dbsService.getSimplePlayInfo(avatarId, targetAvatarId));
	}
	
	@CommandRpc(fullAlias = GongDbConstants.CMD_REMOVE_FRIEND, unpack = true)
	public RawCommandResult removeFriend(String avatarId, String targetAvatarId) {
		dbsService.removeFriend(avatarId, targetAvatarId);
		return new RawCommandResult();
	}
	
	@CommandRpc(fullAlias = GongDbConstants.CMD_ADD_ENEMY, unpack = true)
	public RawCommandResult addEnemy(String avatarId, String targetAvatarId, int maxCount) {
		return new RawCommandResult(dbsService.addEnemy(avatarId, targetAvatarId, maxCount));
	}
	
	@CommandRpc(fullAlias = GongDbConstants.CMD_ADD_JIEJIAO, unpack = true)
	public RawCommandResult addJiejiao(String avatarId, String[] targetAvatarIdList, int myMaxFriendCount) {
		if (targetAvatarIdList.length == 1) 
			return new RawCommandResult(dbsService.addJiejiao(avatarId, targetAvatarIdList[0], myMaxFriendCount));
		return new RawCommandResult(dbsService.addJiejiaoAll(avatarId, targetAvatarIdList, myMaxFriendCount));
	}
	
	@CommandRpc(fullAlias = GongDbConstants.CMD_ADD_ALL_USER_MESSAGE, unpack = true)
	public RawCommandResult addUserMessageAll(List<UserMessageModel> msgList) {
		dbsService.addALLUserMessage(msgList);
		return new RawCommandResult();
	}
	
	@CommandRpc(fullAlias = GongDbConstants.CMD_ADD_USER_MESSAGE, unpack = true)
	public RawCommandResult addUserMessage(UserMessageModel userMsg) {
		LoggerHelper.info("addUserMessage", null);
		dbsService.addUserMessage(userMsg);
		return new RawCommandResult();
	}
	
	@CommandRpc(fullAlias = GongDbConstants.CMD_ACCEPT_JIEJIAO, unpack = true)
	public RawCommandResult acceptJiejiao(String avatarId, int vipLv, int myMaxFriendCount, String fromAvatarId, int fromVipLv, long userMsgId) {
		return new RawCommandResult(dbsService.acceptJiejiao(avatarId, vipLv, myMaxFriendCount, fromAvatarId, fromVipLv));
	}
	
	@CommandRpc(fullAlias = GongDbConstants.CMD_REJECT_JIEJIAO, unpack = true)
	public RawCommandResult rejectJiejiao(String avatarId, String fromAvatarId) {
		return new RawCommandResult(dbsService.rejectJiejiao(avatarId, fromAvatarId));
	}
	
	@CommandRpc(fullAlias = GongDbConstants.CMD_SEARCH_BY_NAME, unpack = true) 
	public RawCommandResult searchPlay(String key, int page, int count, String avatarId) {
		return new RawCommandResult(dbsService.searchByName(key, page, count, avatarId));
	}
	
	@CommandRpc(fullAlias = GongDbConstants.CMD_GET_JIEJIAO_LIST, unpack = true)
	public RawCommandResult getJiejiaoList(String avatarId) {
		return new RawCommandResult(dbsService.getJiejiaoList(avatarId));
	}
	
	@CommandRpc(fullAlias = GongDbConstants.CMD_GET_RECENT_CONTACT, unpack = true)
	public RawCommandResult getRecentContact(String avatarId) {
		LoggerHelper.info("getRecentContact", null, avatarId);
		return new RawCommandResult(dbsService.getRecentContact(avatarId));
	}

	@CommandRpc(fullAlias = GongDbConstants.CMD_SEND_SYSTEM_COMMON_MSG, unpack = true)
	public RawCommandResult sendSystemCommonMsg(List<String> recvs, String content) {
		LoggerHelper.info("sendSystemCommonMsg", null, content);
		gameService.sendSystemCommonMsg(recvs, content);
		return new RawCommandResult();
	}
	
	@CommandRpc(fullAlias = GongDbConstants.CMD_GET_SILIAO_CHAT, unpack = true)
	public RawCommandResult getSiliaoChat(String avatarId, String targetAvatarId) {
		LoggerHelper.info("getSiliaoChat", null, avatarId, targetAvatarId);
		return new RawCommandResult(dbsService.getSiliaoChat(avatarId, targetAvatarId));
	}
	
	@CommandRpc(fullAlias = GongDbConstants.CMD_UPDATE_WINDOW_STATUS_BY_AVATAR_ID, unpack = true)
	public RawCommandResult updateWindowStatus(String avatarId, String targetAvatarId) {
		LoggerHelper.info("updateWindowStatus", null, avatarId, targetAvatarId);
		dbsService.updateWindowStatus(avatarId, targetAvatarId);
		return new RawCommandResult();
	}
	
	@CommandRpc(fullAlias = GongDbConstants.CMD_GET_RECENT_CONTACT_MODEL, unpack = true)
	public RawCommandResult getRelationContactModel(String avatarId, String targetAvatarId) {
		LoggerHelper.info("getRelationContactModel", null, avatarId, targetAvatarId);
		return new RawCommandResult(dbsService.getRelationContactModel(avatarId, targetAvatarId));
	}


}