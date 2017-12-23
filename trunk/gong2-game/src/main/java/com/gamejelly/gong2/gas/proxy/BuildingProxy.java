package com.gamejelly.gong2.gas.proxy;

import com.gamejelly.gong2.gas.entity.AvatarEntity;
import com.gamejelly.gong2.gas.proxy.UserProxy;
import com.gamejelly.gong2.gas.service.user.BuildingService;
import com.gamejelly.gong2.utils.GongCommonNotify;
import com.gamejelly.gong2.utils.GongRpcConstants;
import com.gamejelly.gong2.utils.SecurityUtils;
import com.hadoit.game.engine.core.rpc.base.annotation.Rpc;
import com.hadoit.game.engine.core.rpc.simple.context.RpcResult;
import com.hadoit.game.engine.core.rpc.simple.context.ServerChannelContext;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class BuildingProxy extends UserProxy {
    @Resource
    private BuildingService buildingService;

    @Rpc(fullAlias = GongRpcConstants.REQ_BUILDING_CREATE_BUILDING)
    public void createBuilding(final ServerChannelContext channelContext, final RpcResult rpcResult, int x, int y, int templateId, String buildingId, float scale, boolean flip, boolean canMoveBuild, boolean canFlip, boolean delete, boolean newPlayer, boolean openSpace) {
        AvatarEntity avatarEntity = SecurityUtils.getOwner(context, channelContext);
        if (avatarEntity == null) {
            GongCommonNotify.closeForNotLogin(channelContext);
            return;
        }
        GuardianLogger.info(" createBuilding ", avatarEntity, avatarEntity, rpcResult, x, y, templateId, buildingId, scale, flip, canMoveBuild, canFlip, delete, newPlayer, openSpace);
        buildingService.createBuilding(avatarEntity, rpcResult, x, y, templateId, buildingId, scale, flip, canMoveBuild, canFlip, delete, newPlayer, openSpace);
    }

    @Rpc(fullAlias = GongRpcConstants.REQ_BUILDING_FIND_BUILDING_LIST)
    public void findBuildingList(final ServerChannelContext channelContext, final RpcResult rpcResult, int dbId, String otherAvatarId) {
        AvatarEntity avatarEntity = SecurityUtils.getOwner(context, channelContext);
        if (avatarEntity == null) {
            GongCommonNotify.closeForNotLogin(channelContext);
            return;
        }
        GuardianLogger.info(" findBuildingList ", avatarEntity, avatarEntity, rpcResult, dbId, otherAvatarId);
        buildingService.findBuildingList(avatarEntity, rpcResult, dbId, otherAvatarId);
    }

    @Rpc(fullAlias = GongRpcConstants.REQ_BUILDING_UPGRADE_BUILDING)
    public void upgradeBuilding(final ServerChannelContext channelContext, final RpcResult rpcResult, String buildingId) {
        AvatarEntity avatarEntity = SecurityUtils.getOwner(context, channelContext);
        if (avatarEntity == null) {
            GongCommonNotify.closeForNotLogin(channelContext);
            return;
        }
        GuardianLogger.info(" upgradeBuilding ", avatarEntity,  rpcResult, buildingId);
        buildingService.upgradeBuilding(avatarEntity, rpcResult, buildingId);
    }

    @Rpc(fullAlias = GongRpcConstants.REQ_BUILDING_MOVE_BUILDING)
    public void moveBuilding(final ServerChannelContext channelContext, final RpcResult rpcResult, String buildingId, int x, int y) {
        AvatarEntity avatarEntity = SecurityUtils.getOwner(context, channelContext);
        if (avatarEntity == null) {
            GongCommonNotify.closeForNotLogin(channelContext);
            return;
        }
        GuardianLogger.info(" moveBuilding ", avatarEntity, avatarEntity, rpcResult, buildingId, x, y);
        buildingService.moveBuilding(avatarEntity, rpcResult, buildingId, x, y);
    }

    @Rpc(fullAlias = GongRpcConstants.REQ_BUILDING_FLIP_BUILDING)
    public void flipBuilding(final ServerChannelContext channelContext, final RpcResult rpcResult, String buildingId) {
        AvatarEntity avatarEntity = SecurityUtils.getOwner(context, channelContext);
        if (avatarEntity == null) {
            GongCommonNotify.closeForNotLogin(channelContext);
            return;
        }
        GuardianLogger.info(" flipBuilding ", avatarEntity, avatarEntity, rpcResult, buildingId);
        buildingService.flipBuilding(avatarEntity, rpcResult, buildingId);
    }

//    @Rpc(fullAlias = GongRpcConstants.REQ_BUILDING_REMOVE_BUILDING)
//    public void removeBuilding(final ServerChannelContext channelContext, final RpcResult rpcResult, String buildingId) {
//        AvatarEntity avatarEntity = SecurityUtils.getOwner(context, channelContext);
//        if (avatarEntity == null) {
//            GongCommonNotify.closeForNotLogin(channelContext);
//            return;
//        }
//        GuardianLogger.info(" removeBuilding ", avatarEntity, avatarEntity, buildingId);
//        buildingService.removeBuilding(avatarEntity, buildingId);
//    }

    @Rpc(fullAlias = GongRpcConstants.REQ_BUILDING_REMOVE_BUILDING)
    public void removeBuilding(final ServerChannelContext channelContext, final RpcResult rpcResult, String buildingId) {
        AvatarEntity avatarEntity = SecurityUtils.getOwner(context, channelContext);
        if (avatarEntity == null) {
            GongCommonNotify.closeForNotLogin(channelContext);
            return;
        }
        GuardianLogger.info(" removeBuilding ", avatarEntity, avatarEntity, rpcResult, buildingId);
        buildingService.removeBuilding(avatarEntity, rpcResult, buildingId);
    }

    @Rpc(fullAlias = GongRpcConstants.REQ_BUILDING_OPEN_MAKE_MATERIAL_NUMBER)
    public void openMakeMaterialNumber(final ServerChannelContext channelContext, final RpcResult rpcResult, String buildingId) {
        AvatarEntity avatarEntity = SecurityUtils.getOwner(context, channelContext);
        if (avatarEntity == null) {
            GongCommonNotify.closeForNotLogin(channelContext);
            return;
        }
        GuardianLogger.info(" openMakeMaterialNumber ", avatarEntity, avatarEntity, buildingId);
        buildingService.openMakeMaterialNumber(avatarEntity, buildingId);
    }

//    @Rpc(fullAlias = GongRpcConstants.REQ_BUILDING_INCR_BUILDING_PROSPERITY)
//    public void incrBuildingProsperity(final ServerChannelContext channelContext, final RpcResult rpcResult, long val, int sourceType, int sourceId1, int sourceId2, int type) {
//        AvatarEntity avatarEntity = SecurityUtils.getOwner(context, channelContext);
//        if (avatarEntity == null) {
//            GongCommonNotify.closeForNotLogin(channelContext);
//            return;
//        }
//        GuardianLogger.info(" incrBuildingProsperity ", avatarEntity, avatarEntity, val, sourceType, sourceId1, sourceId2, type);
//        buildingService.incrBuildingProsperity(avatarEntity, val, sourceType, sourceId1, sourceId2, type);
//    }
}