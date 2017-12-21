package com.gamejelly.gong2.gas.service.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.aspectj.apache.bcel.generic.RET;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.gamejelly.gong2.utils.GongCommonNotify;
import com.gamejelly.gong2.utils.GongConstants;
import com.gamejelly.gong2.utils.GongDbConstants;
import com.gamejelly.gong2.utils.GongRpcConstants;
import com.gamejelly.gong2.utils.GongUtils;
import com.gamejelly.gong2.utils.LoggerHelper;
import com.gamejelly.gong2.utils.LogicUtils;
import com.gamejelly.gong2.utils.SecurityUtils;
import com.gamejelly.gong2.vo.AvatarVO;
import com.gamejelly.gong2.vo.SimplePlayerInfo;
import com.gamejelly.gong2.meta.RecentlyContactModel;
import com.gamejelly.gong2.meta.RelationModel;
import com.gamejelly.gong2.gas.entity.AvatarEntity;
import com.hadoit.game.common.lang.Pair;
import com.hadoit.game.common.lang.Triple;
import com.hadoit.game.engine.core.rpc.simple.context.RpcResult;
import com.hadoit.game.engine.core.rpc.simple.context.ServerChannelContext;
import com.hadoit.game.engine.guardian.gas.GasManager;
import com.hadoit.game.engine.guardian.gas.entity.Entity;
import com.hadoit.game.engine.guardian.gas.entity.RawCommandCallback;

@Component("shejiaoService")
public class ShejiaoService {

    @Autowired
    @Qualifier("gasManager")
    private GasManager gasManager;

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Autowired
    @Qualifier("messageService")
    private MessageService messageService;

    /**
     * 获取好友列表
     * @param entity
     * @param rpcResult
     */
    @SuppressWarnings("unchecked")
    public void getFriendList(final AvatarEntity entity, final RpcResult rpcResult) {
        // 全部好友未读消息提示，设置为已读
        entity.getAvatarModel().setIsReadMsg(GongConstants.MSG_READ);
        gasManager.executeRawCommand(GongDbConstants.CMD_GET_RELATION_LIST,
                new Object[] {entity.getAvatarModel().getId(), GongConstants.RELATION_TYPE_FRIEND},
                new RawCommandCallback() {

                    @Override
                    public void onResult(Object result, int num, String error) {
                        List<SimplePlayerInfo> spiList = (List<SimplePlayerInfo>) result;
                        if (error == null) {
                            if (spiList != null)
                                checkAvatarIsOnline(spiList);
                            rpcResult.result(new Object[] { true, result });
                        } else {
                            rpcResult.error();
                        }

                    }
                });
        entity.notify(GongRpcConstants.RES_USER_AVATAR_CHANGE, new AvatarVO(entity));
    }

    /**
     * 获取仇敌列表
     * @param entity
     * @param rpcResult
     */
    @SuppressWarnings("unchecked")
    public void getEnemyList(final AvatarEntity entity, final RpcResult rpcResult) {
        gasManager.executeRawCommand(GongDbConstants.CMD_GET_RELATION_LIST,
                new Object[]{entity.getAvatarModel().getIconIndex(), GongConstants.RELATION_TYPE_FRIEND},
                new RawCommandCallback() {


                    @Override
                    public void onResult(Object result, int num, String error) {
                        List<SimplePlayerInfo> spiList = (List<SimplePlayerInfo>) result;
                        if (error == null) {
                            if (spiList != null)
                                checkAvatarIsOnline(spiList);
                            rpcResult.result(new Object[] { true, result });
                        } else {
                            rpcResult.error();
                        }
                    }
                });

    }

    /**
     * 检查是否处于在线状态
     * @param spiList
     */
    public void checkAvatarIsOnline(List<SimplePlayerInfo> spiList) {
        for (SimplePlayerInfo spi : spiList) {
            AvatarEntity otherEntity = (AvatarEntity) gasManager.getEntityManager().getEntity(spi.getId());
            spi.setOnline(otherEntity != null);
        }
    }

    /**
     * 获取某个玩家的简单信息
     * @param entity
     * @param rpcResult
     * @param targetAvatarId
     */
    public void getSimplePlayerInfo(final AvatarEntity entity, final RpcResult rpcResult, final String targetAvatarId) {
        gasManager.executeRawCommand(GongDbConstants.CMD_GET_SIMPLE_PLAYER_INFO,
                new Object[] {entity.getAvatarModel().getId(), targetAvatarId},
                new RawCommandCallback() {

                    @Override
                    public void onResult(Object result, int num, String error) {
                        if (error == null) {
                            SimplePlayerInfo spi = (SimplePlayerInfo) result;
                            if (spi == null) {
                                rpcResult.result(false);
                                return;
                            }
                            checkAvatarIsOnline(Arrays.asList(spi));
                            rpcResult.result(new Object[] {true, spi});
                        } else {
                            rpcResult.error();
                        }
                    }
                });
    }

    /**
     * 移除好友
     * @param entity
     * @param targetAvatarId
     * @param rpcResult
     */
    public void removeFriend(final AvatarEntity entity, final String targetAvatarId, final RpcResult rpcResult) {
        gasManager.executeRawCommand(GongDbConstants.CMD_REMOVE_FRIEND,
                new Object[] {entity.getAvatarModel().getId(), targetAvatarId},
                new RawCommandCallback() {

                    @Override
                    public void onResult(Object result, int num, String error) {
                        rpcResult.result(true);
                        Entity e = gasManager.getEntityManager().getEntity(targetAvatarId);
                        if (e != null) {
                            // 在线发消息提示(只提示无需数量)
                            e.notify(GongRpcConstants.RES_USER_TISHI_FLAG_CHANGE,
                                    GongUtils.createHashMap(GongConstants.TISHI_FLAG_FRND, 0));
                        }

                    }
                });
    }

    /**
     * 移除仇敌
     * @param entity
     * @param targetAvatarId
     * @param rpcResult
     */
    public void removeEnemy(final AvatarEntity entity, final String targetAvatarId, final RpcResult rpcResult) {
        gasManager.executeRawCommand(GongDbConstants.CMD_REMOVE_ENEMY,
                new Object[] {entity.getAvatarModel().getId(), targetAvatarId},
                new RawCommandCallback() {

                    @Override
                    public void onResult(Object result, int num, String error) {
                        AvatarEntity myAvatarEntity = (AvatarEntity) gasManager.getEntityManager()
                                .getEntity(entity.getAvatarModel().getId());
                        if (myAvatarEntity == null || myAvatarEntity.isUnused()) {
                            rpcResult.error();
                            return;
                        }
                        rpcResult.result(true);
                        userService.notifyEnemyList(myAvatarEntity);
                    }
                });
    }

    /**
     * 添加仇敌
     * @param entity
     * @param rpcResult
     * @param targetAvatarId
     */
    public void addEnemy(final AvatarEntity entity, final RpcResult rpcResult, String targetAvatarId) {

        int myMaxFriendCount = GongUtils.getVipValue(entity.getAvatarModel().getVipLv(), "friend", 0);

        final String myAvatarId = entity.getId();
        gasManager.executeRawCommand(GongDbConstants.CMD_ADD_ENEMY,
                new Object[] { entity.getAvatarModel().getId(), targetAvatarId, myMaxFriendCount },
                new RawCommandCallback() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public void onResult(Object result, int num, String error) {
                        if (result == null) {
                            rpcResult.error();
                            return;
                        }

                        AvatarEntity myAvatarEntity = (AvatarEntity) gasManager.getEntityManager()
                                .getEntity(myAvatarId);
                        if (myAvatarEntity == null || myAvatarEntity.isUnused()) {
                            rpcResult.error();
                            return;
                        }

                        Pair<Boolean, String> p = (Pair<Boolean, String>) result;
                        if (!p.getFirst()) {
                            GongCommonNotify.notifyMsg(myAvatarEntity.getChannelContext(), p.getSecond());
                            rpcResult.result(false);
                            return;
                        }
                        rpcResult.result(true);

                        userService.notifyEnemyList(myAvatarEntity);
                    }
                });
    }

    /**
     * 发送结交信息
     * @param entity
     * @param targetAvatarIdList
     * @param rpcResult
     */
    public void sendJiejiaoMsg(final AvatarEntity entity, final String[] targetAvatarIdList, final RpcResult rpcResult) {

        int myMaxFriendCount = GongUtils.getVipValue(entity.getAvatarModel().getVipLv(), "friend", 0);

        gasManager.executeRawCommand(GongDbConstants.CMD_ADD_JIEJIAO,
                new Object[] {entity.getAvatarModel().getId(), targetAvatarIdList, myMaxFriendCount},
                new RawCommandCallback() {

                    @Override
                    public void onResult(Object result, int num, String error) {
                        if (error == null) {
                            AvatarEntity myAvatarEntity = (AvatarEntity) gasManager.getEntityManager().getEntity(entity.getAvatarModel().getId());
                            if (myAvatarEntity == null || myAvatarEntity.isUnused()) {
                                rpcResult.error();
                                return;
                            }
                            if (targetAvatarIdList.length > 1) {
                                List<String> ret = (List<String>) result;
                                if (!CollectionUtils.isEmpty(ret)) {
                                    for (String aid : ret) {
                                        myAvatarEntity.notifyEntity(aid, GongRpcConstants.RES_USER_TISHI_FLAG_CHANGE,
                                                GongUtils.createHashMap(GongConstants.TISHI_FLAG_FRND, 1));
                                    }
                                    rpcResult.result(true);
                                }

                            } else {
                                Pair<Boolean, String> p = (Pair<Boolean, String>) result;
                                if (p.getSecond() != null)
                                    GongCommonNotify.notify(myAvatarEntity.getChannelContext(), p.getSecond());
                                if (!p.getFirst()) {
                                    rpcResult.result(false);
                                    return;
                                }
                                myAvatarEntity.notifyEntity(targetAvatarIdList[0], GongRpcConstants.RES_USER_TISHI_FLAG_CHANGE,
                                        GongUtils.createHashMap(GongConstants.TISHI_FLAG_FRND, 1));
                                rpcResult.result(true);
                            }

                        } else {
                            rpcResult.error();
                        }

                    }
                });
    }

    /**
     * 直接向某个角色名的玩家发送好友结交申请
     * @param entity
     * @param rpcResult
     * @param roleName
     */
    public void sendJiejiaoByName(final AvatarEntity entity, final RpcResult rpcResult, final String roleName) {
    	gasManager.executeRawCommand(GongDbConstants.CMD_GET_AVATAR_UID_BY_ROLE, 
    			new Object[] {roleName}, 
    			new RawCommandCallback() {

					@Override
					public void onResult(Object result, int num, String error) {
						if (error == null) {
							AvatarEntity myAvatarEntity = (AvatarEntity) gasManager.getEntityManager().getEntity(entity.getAvatarModel().getId());
							if (myAvatarEntity == null || myAvatarEntity.isUnused()) {
								rpcResult.error();
                                return;
							}
							String targetAvatarId = (String)result;
							if (targetAvatarId == null) {
								rpcResult.result(false);
								return;
							}
							sendJiejiaoMsg(myAvatarEntity, new String[] {targetAvatarId}, rpcResult);
						} else {
							rpcResult.error();
						}

					}
				});
    }

    /**
     * 同意结交为好友
     * @param entity
     * @param fromAvatarId
     * @param rpcResult
     * @param userMsgId
     */
    public void acceptJiejiao(final AvatarEntity entity, final RpcResult rpcResult, final String fromAvatarId) {
        AvatarEntity fromAvatarEntity = (AvatarEntity) gasManager.getEntityManager().getEntity(fromAvatarId);
        int fromVipLv = -1;
        if (fromAvatarEntity != null)
            fromVipLv = fromAvatarEntity.getAvatarModel().getVipLv();
        int myMaxFriendCount = GongUtils.getVipValue(entity.getAvatarModel().getVipLv(), "friend", 0);
        gasManager.executeRawCommand(GongDbConstants.CMD_ACCEPT_JIEJIAO,
                new Object[] {entity.getAvatarModel().getId(), entity.getAvatarModel().getVipLv(), myMaxFriendCount, fromAvatarId, fromVipLv},
                new RawCommandCallback() {

                    @Override
                    public void onResult(Object result, int num, String error) {
                        if (error == null) {
                            if (result == null) {
                                rpcResult.result(false);
                                return;
                            }
                            AvatarEntity myAvatarEntity = (AvatarEntity) gasManager.getEntityManager()
                                    .getEntity(entity.getAvatarModel().getId());
                            if (myAvatarEntity == null || myAvatarEntity.isUnused()) {
                                rpcResult.error();
                                return;
                            }
                            Triple<Boolean, String, Pair<Integer, Integer>> t = (Triple<Boolean, String, Pair<Integer, Integer>>) result;
                            if (!t.getFirst()) {
                                GongCommonNotify.notifyMsg(myAvatarEntity.getChannelContext(), t.getSecond());
                                return;
                            }
                            myAvatarEntity.notify(GongRpcConstants.RES_USER_AVATAR_CHANGE, new AvatarVO(myAvatarEntity));
                            rpcResult.result();
                        } else {
                            rpcResult.error();
                        }

                    }
                });
    }

    /**
     * 拒绝结交好友
     * @param entity
     * @param rpcResult
     * @param fromAvatarId
     * @param userMsgId
     */
    public void rejectJiejiao(final AvatarEntity entity, final RpcResult rpcResult, final String fromAvatarId, final long userMsgId) {

        gasManager.executeRawCommand(GongDbConstants.CMD_REJECT_JIEJIAO,
                new Object[] {entity.getAvatarModel().getId(), fromAvatarId, userMsgId},
                new RawCommandCallback() {

                    @Override
                    public void onResult(Object result, int num, String error) {
                        if (error == null && (Boolean) result) {
                            AvatarEntity myAvatarEntity = (AvatarEntity) gasManager.getEntityManager().getEntity(entity.getAvatarModel().getId());
                            if (myAvatarEntity == null || myAvatarEntity.isUnused()) {
                                rpcResult.result(false);
                                return;
                            }
                            rpcResult.result();
                        } else {
                            rpcResult.error();
                        }

                    }
                });
    }

    /**
     * 根据关键字匹配同服务器的玩家，获取列表
     * @param entity
     * @param rpcResult
     * @param key
     * @param page
     */
    public void searchPlayer(final AvatarEntity entity, final RpcResult rpcResult, final String key, final int page) {

        gasManager.executeRawCommand(GongDbConstants.CMD_SEARCH_BY_NAME,
                new Object[] {key, page, GongConstants.SEARCH_PLAYER_COUNT, entity.getAvatarModel().getId()},
                new RawCommandCallback() {

                    @Override
                    public void onResult(Object result, int num, String error) {
                        if (error == null)
                            rpcResult.result(new Object[] { true, result });
                        else
                            rpcResult.error();
                    }
                });
    }

    /**
     * 随机显示一批在线玩家列表
     * @param entity
     * @param rpcResult
     */
    public void defaultSearchPlayer(final AvatarEntity  entity, final RpcResult rpcResult) {
        List<ServerChannelContext> randomContextList = GongUtils.getRandomsFromList(SecurityUtils.getChannelContexts(),
                GongConstants.SEARCH_PLAYER_COUNT, true);
        List<SimplePlayerInfo> spiList = new ArrayList<>();
        for (ServerChannelContext channelContext : randomContextList) {
            AvatarEntity otherEntity = SecurityUtils.getOwner(gasManager, channelContext);

            if (otherEntity == null || otherEntity.getId().equals(entity.getId()))
                continue;
            SimplePlayerInfo spi = LogicUtils.doConvertToSimplePlayerInfo(otherEntity.getAvatarModel(), null);
            spiList.add(spi);
            LoggerHelper.info("player roleName", null, spi.getName());
        }
        checkAvatarIsOnline(spiList);
        rpcResult.result(new Object[] {true, spiList});
    }

    /**
     * 获取待同意或拒绝的还有结交列表
     * @param entity
     * @param rpcResult
     */
    public void getJiejiaoList(final AvatarEntity entity, final RpcResult rpcResult) {

        gasManager.executeRawCommand(GongDbConstants.CMD_GET_JIEJIAO_LIST, new Object[] {entity.getAvatarModel().getId()},
                new RawCommandCallback() {

                    @Override
                    public void onResult(Object result, int num, String error) {
                        if (error == null) {
                            if (result != null)
                                checkAvatarIsOnline((List<SimplePlayerInfo>) result);

                            rpcResult.result(new Object[] { true, result });
                        } else {
                            rpcResult.error();
                        }

                    }
                });
    }

    /**
     * 获取最近联系人
     * @param entity
     * @param rpcResult
     */
    public void getRecentContact(final AvatarEntity entity, final RpcResult rpcResult) {

        gasManager.executeRawCommand(GongDbConstants.CMD_GET_RECENT_CONTACT,
                new Object[] { entity.getAvatarModel().getId() }, new RawCommandCallback() {
                    @Override
                    public void onResult(Object result, int num, String error) {
                        if (error == null) {
                            if (result != null) {
                                checkAvatarIsOnline((List<SimplePlayerInfo>) result);
                            }

                            rpcResult.result(new Object[] { true, result });
                        } else {
                            rpcResult.error();
                        }
                    }
                });
    }

    /**
     * 打开聊天窗口
     * @param entity
     * @param targetAvatarId
     * @param rpcResult
     */
    public void openMsgWindow(final AvatarEntity entity, final String targetAvatarId, final RpcResult rpcResult) {

        // 关闭其他聊天窗口
        gasManager.executeRawCommand(GongDbConstants.CMD_UPDATE_WINDOW_STATUS_BY_AVATAR_ID,
                new Object[] {entity.getAvatarModel().getId(), targetAvatarId},
                new RawCommandCallback() {
                    @Override
                    public void onResult(Object result, int num, String error) {

                    }
                });
        changeRelationStatusByopenMsgWindow(entity.getId(), targetAvatarId); 		// 打开聊天窗口
        // 改变关系
        changeRecentContactStatusByopenMsgWindow(entity.getId(), targetAvatarId); 	// 打开聊天窗口
        // 改变最近联系人

    }

    private void changeRecentContactStatusByopenMsgWindow(final String avatarId, final String openAvatarId) {
        // 获取最近联系人表，打开聊天窗口的玩家。
        gasManager.executeRawCommand(GongDbConstants.CMD_GET_RECENT_CONTACT_MODEL,
                new Object[] { avatarId, openAvatarId }, new RawCommandCallback() {

                    @Override
                    public void onResult(Object result, int num, String error) {
                        if (error == null) {
                            RecentlyContactModel recentlyContactModel = (RecentlyContactModel) result;
                            if (recentlyContactModel == null) {
                                return;
                            }

                            recentlyContactModel.setMsgWindowStatus(GongConstants.MSG_WINDOW_OPEN);// 设置聊天窗口状态
                            // 打开

                            recentlyContactModel.setMsgType(GongConstants.MSG_READ);// 设置信息读取状态
                            // 已读

                            // 更新关系表信息
                            gasManager.executeRawCommand(GongDbConstants.CMD_UPDATE_RECENT_CONTACT_MODEL,
                                    new Object[] { recentlyContactModel }, new RawCommandCallback() {
                                        @Override
                                        public void onResult(Object result, int num, String error) {

                                        }
                                    });
                        }
                    }
                });

    }

    private void changeRelationStatusByopenMsgWindow(final String avatarId, final String openAvatarId) {
        // 获取自己关系表中，打开窗口的好友。关系信息
        gasManager.executeRawCommand(GongDbConstants.CMD_GET_RELATION_MODEL, new Object[] { avatarId, openAvatarId },
                new RawCommandCallback() {

                    @Override
                    public void onResult(Object result, int num, String error) {
                        if (error == null) {
                            RelationModel relationModel = (RelationModel) result;
                            if (relationModel == null) {
                                return;
                            }
                            relationModel.setMsgWindowStatus(GongConstants.MSG_WINDOW_OPEN);// 设置聊天窗口状态
                            // 打开
                            relationModel.setMsgType(GongConstants.MSG_READ);// 设置信息读取状态
                            // 已读
                            // 更新关系表信息
                            gasManager.executeRawCommand(GongDbConstants.CMD_UPDATE_RELATION_MODEL,
                                    new Object[] { relationModel }, new RawCommandCallback() {
                                        @Override
                                        public void onResult(Object result, int num, String error) {

                                        }
                                    });
                        }
                    }
                });

    }

    public void closeAllMsgWindow(AvatarEntity owner) {
        if (owner == null)
            return;

        gasManager.executeRawCommand(GongDbConstants.CMD_UPDATE_WINDOW_STATUS_BY_AVATAR_ID, new Object[] {owner.getId(), null},
                new RawCommandCallback() {
                    @Override
                    public void onResult(Object result, int num, String error) {

                    }
                });
    }

}
