package com.gamejelly.gong2.gas.service.user;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Component;


import com.gamejelly.gong2.utils.GongConstants;
import com.gamejelly.gong2.utils.GongDbConstants;
import com.gamejelly.gong2.utils.GongRpcConstants;
import com.gamejelly.gong2.utils.GongUtils;
import com.gamejelly.gong2.config.MessageConfig;
import com.gamejelly.gong2.gas.entity.AvatarEntity;
import com.gamejelly.gong2.meta.MessageAction;
import com.gamejelly.gong2.meta.UserMessageModel;
import com.hadoit.game.engine.core.rpc.simple.context.RpcResult;
import com.hadoit.game.engine.guardian.gas.GasManager;
import com.hadoit.game.engine.guardian.gas.entity.RawCommandCallback;


import com.gamejelly.gong2.config.MessageConfig;
import com.gamejelly.gong2.config.data.SysConstData;
import com.gamejelly.gong2.config.data.base.LMap;
import com.gamejelly.gong2.gas.entity.AvatarEntity;
import com.gamejelly.gong2.gas.entity.GongHuiEntity;
import com.gamejelly.gong2.meta.GongHuiModel;
import com.gamejelly.gong2.meta.MessageAction;
import com.gamejelly.gong2.meta.SystemRewardInfo;
import com.gamejelly.gong2.meta.UserMessageModel;
import com.gamejelly.gong2.utils.GongCommonNotify;
import com.gamejelly.gong2.utils.GongConstants;
import com.gamejelly.gong2.utils.GongDbConstants;
import com.gamejelly.gong2.utils.GongLogConstants;
import com.gamejelly.gong2.utils.GongRpcConstants;
import com.gamejelly.gong2.utils.GongUtils;
import com.gamejelly.gong2.utils.LogicUtils;
import com.gamejelly.gong2.utils.SecurityUtils;
import com.gamejelly.gong2.vo.AvatarVO;
import com.gamejelly.gong2.vo.InstanceChangeVO;
import com.google.common.collect.Maps;
import com.hadoit.game.engine.core.rpc.simple.context.RpcResult;
import com.hadoit.game.engine.core.rpc.simple.context.ServerChannelContext;
import com.hadoit.game.engine.guardian.gas.GasManager;
import com.hadoit.game.engine.guardian.gas.entity.Entity;
import com.hadoit.game.engine.guardian.gas.entity.RawCommandCallback;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;


@Component("messageService")
public class MessageService {


	@Autowired
	@Qualifier("gasManager")
	private GasManager gasManager;

    @Autowired
    @Qualifier("itemService")
    private ItemService itemService;

	
	
	public UserMessageModel createJiejiaoMsg(AvatarEntity entity, String toAvatarId) {
		String content = MessageConfig.getCommonMsg(GongConstants.MSG_ID_SJ_JJ, entity.getAvatarModel().getRoleName());
		
		List<MessageAction> actionList = new ArrayList<MessageAction>(2);
		HashMap<String, String> map = new HashMap<String, String>(Collections.singletonMap("fromId", entity.getId()));
		MessageAction ac = new MessageAction(1, GongConstants.MSG_OP_JIEJIAO_ACCEPT, map);
		actionList.add(ac);
		map = new HashMap<String, String>(Collections.singletonMap("fromId", entity.getId()));
		ac = new MessageAction(2, GongConstants.MSG_OP_JIEJIAO_REJECT, map);
		actionList.add(ac);
		
		UserMessageModel jjMsg = new UserMessageModel();
		jjMsg.setSenderId(entity.getAvatarModel().getId());
		jjMsg.setMajorType(GongConstants.MSG_MAJOR_TYPE_PLAYER);
		jjMsg.setChildType(GongConstants.MSG_CHILD_TYPE_JIEJIAO);
		jjMsg.setContent(content);
		jjMsg.setActionList(actionList);
		jjMsg.setReceiverId(toAvatarId);
		jjMsg.setSenderName(entity.getAvatarModel().getRoleName());
		jjMsg.setSenderLv(entity.getAvatarModel().getLv());
		return jjMsg;
	}
	
	public void sendJiejiaoMsg(final AvatarEntity entity, final String toAvatarId, final RpcResult rpcResult) {
		UserMessageModel userMsg = createJiejiaoMsg(entity, toAvatarId);
		gasManager.executeRawCommand(GongDbConstants.CMD_ADD_USER_MESSAGE, new Object[] { userMsg },
				new RawCommandCallback() {
					@Override
					public void onResult(Object result, int num, String error) {
						entity.notifyEntity(toAvatarId, GongRpcConstants.RES_USER_TISHI_FLAG_CHANGE,
								GongUtils.createHashMap(GongConstants.TISHI_FLAG_MSG_COUNT, 1));
						rpcResult.result(true);
					}
				});
	}

	public void sendJiejiaoAllMsg(final AvatarEntity entity, final List<String> retTarAvatarList,
			final RpcResult rpcResult) {

		List<UserMessageModel> msgList = new ArrayList<UserMessageModel>();
		for (int i = 0; i < retTarAvatarList.size(); i++) {
			UserMessageModel userMsg = createJiejiaoMsg(entity, retTarAvatarList.get(i));
			msgList.add(userMsg);
		}

		gasManager.executeRawCommand(GongDbConstants.CMD_ADD_ALL_USER_MESSAGE, new Object[] { msgList },
				new RawCommandCallback() {
					@Override
					public void onResult(Object result, int num, String error) {

						for (int i = 0; i < retTarAvatarList.size(); i++) {
							entity.notifyEntity(retTarAvatarList.get(i), GongRpcConstants.RES_USER_TISHI_FLAG_CHANGE,
									GongUtils.createHashMap(GongConstants.TISHI_FLAG_MSG_COUNT, 1));
						}
						rpcResult.result(true);
					}
				});
	}
	
	public void sendAcceptJiejiaoMsg(final AvatarEntity entity, final String toAvatarId, final RpcResult rpcResult) {
		UserMessageModel userMessageModel = createAcceptJiejiaoMsg(entity, toAvatarId);
		gasManager.executeRawCommand(GongDbConstants.CMD_ADD_USER_MESSAGE, new Object[] {userMessageModel}, 
				new RawCommandCallback() {
					
					@Override
					public void onResult(Object result, int num, String error) {
						if (error == null) {
							entity.notifyEntity(toAvatarId, GongRpcConstants.RES_USER_TISHI_FLAG_CHANGE,
									GongUtils.createHashMap(GongConstants.TISHI_FLAG_MSG_COUNT, 1));
							rpcResult.result();
						} else {
							rpcResult.error();
						}
						
					}
				});
	}
	
	public void sendRejectJiejiaoMsg(final AvatarEntity entity, final String toAvatarId, final RpcResult rpcResult) {
		UserMessageModel userMessageModel = createRejectJiejiaoMsg(entity, toAvatarId);
		gasManager.executeRawCommand(GongDbConstants.CMD_ADD_USER_MESSAGE, new Object[] {userMessageModel}, 
				new RawCommandCallback() {
					
					@Override
					public void onResult(Object result, int num, String error) {
						if (error == null) {
							entity.notifyEntity(toAvatarId, GongRpcConstants.RES_USER_TISHI_FLAG_CHANGE,
									GongUtils.createHashMap(GongConstants.TISHI_FLAG_MSG_COUNT, 1));
							rpcResult.result();
						} else {
							rpcResult.error();
						}
						
					}
				});
	}
	
	private UserMessageModel createAcceptJiejiaoMsg(AvatarEntity entity, String toAvatarId) {
		String content = MessageConfig.getCommonMsg(GongConstants.MSG_ID_SJ_JJ_ACCEPT,
				entity.getAvatarModel().getRoleName());
		UserMessageModel jjMsg = new UserMessageModel();
		jjMsg.setSenderId(entity.getAvatarModel().getId());
		jjMsg.setMajorType(GongConstants.MSG_MAJOR_TYPE_PLAYER);
		jjMsg.setChildType(GongConstants.MSG_CHILD_TYPE_JIEJIAO_ACCEPT);
		jjMsg.setContent(content);
		jjMsg.setReceiverId(toAvatarId);
		jjMsg.setSenderName(entity.getAvatarModel().getRoleName());
		jjMsg.setSenderLv(entity.getAvatarModel().getLv());
		return jjMsg;
	}
	
	private UserMessageModel createRejectJiejiaoMsg(AvatarEntity entity, String toAvatarId) {
		String content = MessageConfig.getCommonMsg(GongConstants.MSG_ID_SJ_JJ_REJECT, entity.getAvatarModel().getRoleName());
		UserMessageModel jjMsg = new UserMessageModel();
		jjMsg.setSenderId(entity.getAvatarModel().getId());
		jjMsg.setMajorType(GongConstants.MSG_MAJOR_TYPE_PLAYER);
		jjMsg.setChildType(GongConstants.MSG_CHILD_TYPE_JIEJIAO_REJECT);
		jjMsg.setContent(content);
		jjMsg.setReceiverId(toAvatarId);
		jjMsg.setSenderName(entity.getAvatarModel().getRoleName());
		jjMsg.setSenderLv(entity.getAvatarModel().getLv());
		return jjMsg;
		
	}


//    @Resource
//    private OptHuodongService optHuodongService;

    public void sendSystemCommonMsg(final List<String> recvs, String content) {
        gasManager.executeRawCommand(GongDbConstants.CMD_SEND_SYSTEM_COMMON_MSG, new Object[] { recvs, content },
                new RawCommandCallback() {
                    @Override
                    public void onResult(Object result, int num, String error) {
                        if (error != null) {
                            return;
                        }
                        if (CollectionUtils.isNotEmpty(recvs)) {
                            for (String aId : recvs) {
                                Entity e = gasManager.getEntityManager().getEntity(aId);
                                if (e != null) {
                                    e.notify(GongRpcConstants.RES_USER_TISHI_FLAG_CHANGE,
                                            GongUtils.createHashMap(GongConstants.TISHI_FLAG_MSG_COUNT, 1));
                                }
                            }
                        } else {
                            // 全服
                            broadcast(GongRpcConstants.RES_USER_TISHI_FLAG_CHANGE,
                                    GongUtils.createHashMap(GongConstants.TISHI_FLAG_MSG_COUNT, 1));
                        }
                    }
                });
    }
    private void broadcast(String op, Object... params) {
        AvatarEntity receiver;
        for (ServerChannelContext cctx : SecurityUtils.getChannelContexts()) {
            receiver = SecurityUtils.getOwner(gasManager, cctx);
            if (receiver != null) {
                receiver.notify(op, params);
            }
        }
    }

}
