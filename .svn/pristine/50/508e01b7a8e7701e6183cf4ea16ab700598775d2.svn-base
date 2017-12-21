package com.gamejelly.gong2.gas.proxy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.gamejelly.gong2.config.data.SysLvData;
import com.gamejelly.gong2.gas.service.user.*;
import com.gamejelly.gong2.gas.service.user.ShejiaoService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.gamejelly.gong2.config.data.UserGroupData;
import com.gamejelly.gong2.config.data.base.LMap;
import com.gamejelly.gong2.gas.admin.service.ServerConfigService;
import com.gamejelly.gong2.gas.annotation.AutoGasProxy;
import com.gamejelly.gong2.gas.entity.AvatarEntity;
import com.gamejelly.gong2.meta.AvatarModel;
import com.gamejelly.gong2.utils.GongCommonNotify;
import com.gamejelly.gong2.utils.GongConstants;
import com.gamejelly.gong2.utils.GongDbConstants;
import com.gamejelly.gong2.utils.GongRpcConstants;
import com.gamejelly.gong2.utils.GongUtils;
import com.gamejelly.gong2.utils.IdProvider;
import com.gamejelly.gong2.utils.LoggerHelper;
import com.gamejelly.gong2.utils.SecurityUtils;
import com.gamejelly.gong2.vo.AvatarVO;
import com.hadoit.game.common.lang.DataUtils;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;
import com.hadoit.game.engine.core.rpc.base.annotation.Rpc;
import com.hadoit.game.engine.core.rpc.base.annotation.RpcEvent;
import com.hadoit.game.engine.core.rpc.base.constant.RpcEventType;
import com.hadoit.game.engine.core.rpc.simple.context.RpcResult;
import com.hadoit.game.engine.core.rpc.simple.context.ServerChannelContext;
import com.hadoit.game.engine.guardian.gas.GasContext;
import com.hadoit.game.engine.guardian.gas.GasManager;
import com.hadoit.game.engine.guardian.gas.entity.Entity;
import com.hadoit.game.engine.guardian.gas.entity.EntityCallback;
import com.hadoit.game.engine.guardian.gas.entity.RawCommandCallback;
import com.hadoit.game.engine.guardian.gas.proxy.GasProxy;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;

@AutoGasProxy
@Component("userProxy")
public class UserProxy implements GasProxy {

    private final AtomicInteger curOnlineCount = new AtomicInteger(0);

    private GasContext context;

    @Value("${config.account_god_ticket}")
    private String godTicket;

    @Value("${config.account_security_key}")
    private String accountSecurityKey;

    @Autowired
    @Qualifier("serverConfigService")
    private ServerConfigService serverConfigService;

    @Autowired
    @Qualifier("itemService")
    private ItemService itemService;

    @Autowired
    @Qualifier("gasManager")
    private GasManager gasManager;
    @Autowired
    @Qualifier("gongHuiService")
    private GongHuiService gongHuiService;

    @Autowired
    @Qualifier("servantService")
    private ServantService servantService;

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Autowired
    @Qualifier("sceneService")
    private SceneService sceneService;

    @Autowired
    @Qualifier("shejiaoService")
    private ShejiaoService shejiaoService;

    @Override
    public void setContext(GasContext context) {
        this.context = context;
    }

    @RpcEvent(RpcEventType.CHANNEL_CONNECTED)
    public void onConnected(final ServerChannelContext channelContext) throws Exception {
        int c = curOnlineCount.addAndGet(1);
        LoggerHelper.info("open channel", null, channelContext.getChannel().getId(), "and now con", c);
        GongCommonNotify.notifyReady(channelContext);
    }

    @RpcEvent(RpcEventType.CHANNEL_DISCONNECTED)
    public void onDisconnected(final ServerChannelContext channelContext) throws Exception {
        int c = curOnlineCount.addAndGet(-1);
        LoggerHelper.info("close channel", null, channelContext.getChannel().getId(), "and now con", c);
        logout(channelContext);
    }

    private boolean doCheckAccount(final ServerChannelContext channelContext, final RpcResult rpcResult,
                                   final String accountName, final String ticket, final String clientAppVersion,
                                   final String clientResVersion) {

        if (!serverConfigService.checkClientVersion((String) channelContext.getAttribute("opr"), clientAppVersion,
                clientResVersion)) {
            GongCommonNotify.closeForVersionError(channelContext);
            return false;
        }

        if (!SecurityUtils.checkTicket(accountSecurityKey, ticket, accountName, 0)) {
            GuardianLogger.error("checkTicket! ticket=" + ticket + ", accountName=" + accountName);
            GongCommonNotify.closeForLoginError(channelContext);
            return false;
        }
        return true;
    }

    /**
     * 登录
     *
     * @param channelContext
     * @param accountName
     * @param ticket
     * @param clientResVersion
     */
    @SuppressWarnings("unchecked")
    @Rpc(fullAlias = GongRpcConstants.REQ_USER_LOGIN)
    public void login(final ServerChannelContext channelContext, final RpcResult rpcResult, final String accountName,
                      final String ticket, final String avatarId, final String clientAppVersion, final String clientResVersion,
                      final String extra) {

        LoggerHelper.info("login xxx 1", null, "accountName=", accountName);

        Map<String, Object> paramsExtra = GsonFactory.getDefault().fromJson(extra, Map.class);
        if (paramsExtra == null) {
            paramsExtra = new HashMap<String, Object>();
        }
        String platform = DataUtils.getMapString(paramsExtra, "platform", "");
        String deviceName = DataUtils.getMapString(paramsExtra, "deviceName", "");
        String deviceVersion = DataUtils.getMapString(paramsExtra, "deviceVersion", "");
        String deviceId = DataUtils.getMapString(paramsExtra, "deviceId", "");
        String opr = DataUtils.getMapString(paramsExtra, "opr", "");
        int serverId = DataUtils.getMapDouble(paramsExtra, "serverId", 0d).intValue();
        String loginUrl = DataUtils.getMapString(paramsExtra, "loginUrl", "");
        String ip = SecurityUtils.getClientHost(channelContext);
        channelContext.putAttribute("platform", platform);
        channelContext.putAttribute("deviceName", deviceName);
        channelContext.putAttribute("deviceVersion", deviceVersion);
        channelContext.putAttribute("deviceId", deviceId);
        channelContext.putAttribute("opr", opr);
        channelContext.putAttribute("ip", ip);
        channelContext.putAttribute("loginUrl", loginUrl);

        LoggerHelper.info("login xxx 2", null, "accountName=", accountName, " serverId=", serverId);

        LoggerHelper.info("login", null, accountName, ticket, avatarId, clientAppVersion, clientResVersion, platform,
                deviceName, deviceVersion, deviceId, ip, serverId, opr);

        if (StringUtils.isNotBlank(godTicket)) {
            if (godTicket.equals(ticket)) {
                // pass
                LoggerHelper.info("God login", null, accountName, ticket, platform, deviceName, deviceVersion, deviceId,
                        ip);
            } else {
                if (!doCheckAccount(channelContext, rpcResult, accountName, ticket, clientAppVersion,
                        clientResVersion)) {
                    return;
                }
            }
        } else {
            LoggerHelper.info("login xxx 3", null, "accountName=", accountName);
            if (!doCheckAccount(channelContext, rpcResult, accountName, ticket, clientAppVersion, clientResVersion)) {
                LoggerHelper.info("login xxx 4", null, "accountName=", accountName);
                return;
            }
        }

        LoggerHelper.info("login xxx 5", null, "accountName=", accountName);

        ServerChannelContext oldChannelContext = SecurityUtils.login(channelContext, accountName, ticket);
        if (oldChannelContext != null) {
            logout(oldChannelContext);
        }

        context.executeRawCommand(GongDbConstants.CMD_AVATAR_SELECT, new Object[]{accountName, avatarId, serverId},
                new RawCommandCallback() {
                    @Override
                    public void onResult(Object result, int num, String error) {

                        Object[] ret = (Object[]) result;
                        LoggerHelper.info("login xxx 6", null, "accountName=", accountName);
                        // avatar存在,则选出dbId
                        if (result != null && ret[0] != null) {
                            long dbId = (Long) ret[0];
                            String curAvatarId = (String) ret[1];
                            if (StringUtils.isNotEmpty(curAvatarId)) {
                                AvatarEntity ownerEntity = (AvatarEntity) gasManager.getEntityManager()
                                        .getEntity(curAvatarId);
                                if (ownerEntity != null && ownerEntity.isDestroying()) {
                                    LoggerHelper.info("isDestroying", null, "accountName=", accountName,
                                            " curAvatarId=", curAvatarId);
                                    return;
                                }
                            }

                            LoggerHelper.info("login xxx 7", null, "accountName=", accountName, "dbId=", dbId);
                            loginAvatar(channelContext, rpcResult, dbId);
                        } else {
                            LoggerHelper.info("login xxx 8", null, "accountName=", accountName);
                            // 检查账号权限
                            int userGroup = serverConfigService.getUserGroupDefault();
                            LMap groupData = UserGroupData.data.getMap(accountName);
                            if (groupData != null) {
                                userGroup = groupData.getInt("group", userGroup);
                            }
                            if (userGroup < serverConfigService.getUserGroupLogin()) {
                                GongCommonNotify.closeForServerForbidden(channelContext);
                                return;
                            }
                            GongCommonNotify.notify(channelContext, GongRpcConstants.RES_USER_NEED_CREATE);
                            rpcResult.result();
                        }
                    }
                });
    }

    /**
     * 下线
     *
     * @param channelContext
     */
    @Rpc(fullAlias = GongRpcConstants.REQ_USER_LOGOUT)
    public void logout(final ServerChannelContext channelContext) {
        userService.logout(channelContext);
    }

    private void loginAvatar(final ServerChannelContext channelContext, final RpcResult rpcResult, long dbId) {
        final long startTime = System.currentTimeMillis();
        context.getEntityManager().loadEntityFromDb(AvatarEntity.class, dbId, channelContext,
                new EntityCallback<AvatarEntity>() {
                    @Override
                    public void onResult(boolean r, AvatarEntity entity) {
                        if (r) {
                            SecurityUtils.updateAccountName(channelContext, entity.getAvatarModel().getAccountName());
                            onLogin(rpcResult, entity, false);
                        } else {
                            GongCommonNotify.close(channelContext);
                        }
                        LoggerHelper.slowTimeLocal("loginAvatar", startTime);
                    }
                });
    }

    /**
     * 建立角色
     *
     * @param channelContext
     * @param
     */
    @Rpc(fullAlias = GongRpcConstants.REQ_USER_CREATE)
    public void create(final ServerChannelContext channelContext, final RpcResult rpcResult, final int serverId,
                       String roleName, final int sex) {
        if (!SecurityUtils.isAccountLogined(channelContext)) {
            GongCommonNotify.closeForNotLogin(channelContext);
            return;
        }

        final AvatarEntity oldEntity = SecurityUtils.getOwner(context, channelContext);
        roleName = GongUtils.trimUnicode(roleName); // 去掉Unicode字符
        final String roleName2 = StringUtils.trimToEmpty(DataUtils.toDBC(roleName)); // toDBC
        // 去掉全角
        if (!GongUtils.checkRoleName(roleName2)) {
            GongCommonNotify.notifyErrorMsg(channelContext, rpcResult, GongConstants.MSG_ID_ROLE_NAME_ERROR);
            return;
        }

        final String accountName = SecurityUtils.getLoginAccount(channelContext);
        LoggerHelper.info("create role", null, serverId, accountName, roleName2);
        context.executeRawCommand(GongDbConstants.CMD_CHECK_CAN_CREATE_ROLE,
                new Object[]{accountName, serverId, roleName2}, new RawCommandCallback() {
                    @Override
                    public void onResult(Object result, int num, String error) {
                        if (result != null && (Integer) result < 0) {
                            // 无法创建
                            if ((Integer) result == GongConstants.CREATE_ROLE_NAME_EXISTS) {
                                GongCommonNotify.notifyErrorMsg(channelContext, rpcResult,
                                        GongConstants.MSG_ID_ROLE_NAME_EXISTS);
                            } else if ((Integer) result == GongConstants.CREATE_ROLE_COUNT_REACH_MAX) {
                                // 角色数量到达上限
                                GongCommonNotify.notifyErrorMsg(channelContext, rpcResult,
                                        GongConstants.MSG_ID_ROLE_REACH_MAX);
                            }
                        } else {
                            if (oldEntity != null) {
                                // 老角色销毁
                                userService.logoutRole(oldEntity, new EntityCallback<Entity>() {
                                    @Override
                                    public void onResult(boolean r, Entity entity) {
                                        if (r) {
                                            doCreate(channelContext, rpcResult, roleName2, accountName, serverId, sex);
                                        } else {
                                            GuardianLogger.warn("logoutRole has exception!");
                                        }
                                    }
                                });
                            } else {
                                doCreate(channelContext, rpcResult, roleName2, accountName, serverId, sex);
                            }
                        }
                    }
                });

    }

    private void doCreate(final ServerChannelContext channelContext, final RpcResult rpcResult, final String roleName,
                          String accountName, int serverId, int sex) {

        boolean god = false;
        if (StringUtils.isNotBlank(godTicket)) {
            String ticket = SecurityUtils.getLoginTicket(channelContext);
            if (godTicket.equals(ticket)) {
                god = true;
            }
        }
        final AvatarModel avatarModel = userService.createAvatarModel(accountName, serverId, roleName, sex, god);

        context.getEntityManager().createEntityFromDb(AvatarEntity.class, avatarModel, channelContext,
                new EntityCallback<AvatarEntity>() {
                    @Override
                    public void onResult(boolean r, AvatarEntity entity) {
                        if (r) {
                            // 生成全服唯一的id
                            entity.getAvatarModel().setGbId(IdProvider.genAvatarGbId(entity.getDbId()));
                            entity.initDataAfterCreateAvatarModel(entity);
                            onLogin(rpcResult, entity, true);
                            entity.writeToDB(); // 第一次建完角色立即write
                        }
                    }
                });
    }

    private void onLogin(final RpcResult rpcResult, final AvatarEntity entity, final boolean isNew) {
        boolean dailyFirstLogin = userService.onLogin(entity, isNew);
        gongHuiService.enterGongHui(entity);
        entity.notify(GongRpcConstants.RES_USER_ON_LOGIN_PRE, "itemList", entity.getAvatarModel().getItemList());// 推物品
        entity.notify(GongRpcConstants.RES_USER_ON_LOGIN_PRE, "servantList", entity.getAllServantVoList()); // 推仆从
        entity.notify(GongRpcConstants.RES_USER_ON_LOGIN_PRE, "guankaList", entity.getAllGuanka()); // 推关卡
        entity.notify(GongRpcConstants.RES_USER_ON_LOGIN, new AvatarVO(entity), isNew, dailyFirstLogin);// 推登陆角色


    }

    @Rpc(fullAlias = GongRpcConstants.REQ_USER_SAY)
    public void say(final ServerChannelContext channelContext, final RpcResult rpcResult, final String msg) {
        AvatarEntity owner = SecurityUtils.getOwner(context, channelContext);
        if (owner == null) {
            GongCommonNotify.closeForNotLogin(channelContext);
            return;
        }

        broadcast(GongRpcConstants.RES_USER_ON_SAY, msg);
        rpcResult.result();
    }

    /**
     * 广播
     *
     * @param op
     * @param params
     */
    private void broadcast(String op, Object... params) {
        AvatarEntity receiver;
        for (ServerChannelContext cctx : SecurityUtils.getChannelContexts()) {
            receiver = SecurityUtils.getOwner(context, cctx);
            if (receiver != null) {
                receiver.notify(op, params);
            }
        }
    }

    /**
     * 打关卡副本
     *
     * @param channelContext
     * @param rpcResult
     * @param guankaTemplateId 关卡模版ID void
     * @author: wacenn
     * @createTime: 2017年10月24日 下午5:20:00
     * @history:
     */
    @Rpc(fullAlias = GongRpcConstants.REQ_USER_FIGHT_GUANKA)
    public void fightGuanka(final ServerChannelContext channelContext, RpcResult rpcResult, int guankaTemplateId) {
        AvatarEntity owner = SecurityUtils.getOwner(context, channelContext);
        if (owner == null) {
            GongCommonNotify.closeForNotLogin(channelContext);
            return;
        }
        LoggerHelper.info("fightGuanka", owner.getAvatarModel(), guankaTemplateId);
        rpcResult.result(sceneService.fightGuanka(owner, guankaTemplateId));
    }


    /**
     * 领取章节奖励
     *
     * @param channelContext
     * @param rpcResult
     * @param sceneTemplateId
     * @param awardIdx
     * @param plan
     */
    @Rpc(fullAlias = GongRpcConstants.REQ_USER_CLAIM_SCENE_REWARD)
    public void claimSceneReward(final ServerChannelContext channelContext, final RpcResult rpcResult,
                                 int sceneTemplateId, int awardIdx, int plan) {
        AvatarEntity owner = SecurityUtils.getOwner(context, channelContext);
        if (owner == null) {
            GongCommonNotify.closeForNotLogin(channelContext);
            return;
        }
        LoggerHelper.info("claimSceneReward", owner.getAvatarModel(), sceneTemplateId, awardIdx, plan);

        rpcResult.result(sceneService.claimSceneReward(owner, sceneTemplateId, awardIdx, plan));
    }


    /**
     * 领取关卡奖励
     *
     * @param channelContext
     * @param rpcResult
     * @param guankaBaseTemplateId
     * @param plan
     */
    @Rpc(fullAlias = GongRpcConstants.REQ_USER_CLAIM_GUANKA_BOX_REWARD)
    public void claimGuankaBoxReward(final ServerChannelContext channelContext, final RpcResult rpcResult,
                                     int guankaBaseTemplateId, int plan) {
        AvatarEntity owner = SecurityUtils.getOwner(context, channelContext);
        if (owner == null) {
            GongCommonNotify.closeForNotLogin(channelContext);
            return;
        }
        LoggerHelper.info("claimGuankaBoxReward", owner.getAvatarModel(), guankaBaseTemplateId, plan);

        rpcResult.result(sceneService.claimGuankaBoxReward(owner, guankaBaseTemplateId, plan));
    }

    /**
     * 改名
     *
     * @param channelContext
     * @param rpcResult
     * @param name
     */
    @Rpc(fullAlias = GongRpcConstants.REQ_USER_CHANGE_NAME)
    public void changeName(final ServerChannelContext channelContext, final RpcResult rpcResult, final String name,
                           final boolean useProp) {
        AvatarEntity owner = SecurityUtils.getOwner(context, channelContext);
        if (owner == null) {
            GongCommonNotify.closeForNotLogin(channelContext);
            return;
        }
        LoggerHelper.info("changeName", owner.getAvatarModel(), name, useProp);
        userService.changeName(owner, rpcResult, name, useProp);
    }

    /**
     * @Title: changeQianming @Description: TODO(更改签名) @param @param
     * channelContext @param @param rpcResult @param @param msg
     * 设定文件 @return void 返回类型 @throws
     */
    @Rpc(fullAlias = GongRpcConstants.REQ_USER_CHANGE_QIANMIN)
    public void changeQianming(final ServerChannelContext channelContext, final RpcResult rpcResult, String msg) {
        AvatarEntity owner = SecurityUtils.getOwner(context, channelContext);
        if (owner == null) {
            GongCommonNotify.closeForNotLogin(channelContext);
            return;
        }
        LoggerHelper.info("changeQianming", owner.getAvatarModel(), msg);
        rpcResult.result(userService.changeQianming(owner, msg));
    }


    /**
     * 武将上下阵
     *
     * @param channelContext
     * @param rpcResult
     * @param servantId
     * @param pos
     */
    @Rpc(fullAlias = GongRpcConstants.REQ_USER_SERVANT_CHANGE_POS)
    public void changeServantpos(final ServerChannelContext channelContext, final RpcResult rpcResult, final String
            servantId, final int pos) {
        AvatarEntity owner = SecurityUtils.getOwner(context, channelContext);
        if (owner == null) {
            GongCommonNotify.closeForNotLogin(channelContext);
            return;
        }
        LoggerHelper.info("changeServantpos", owner.getAvatarModel(), pos, servantId);
        rpcResult.result(servantService.changeServantpos(owner, servantId, pos));
    }


    /**
     * @param channelContext
     * @param rpcResult
     * @param servantId
     * @param auto           是否一键升级 false 否 true 是
     */
    @Rpc(fullAlias = GongRpcConstants.REQ_USER_SERVANT_UPGRADE)
    public void servantUpgrade(final ServerChannelContext channelContext, final RpcResult rpcResult, final String
            servantId, boolean auto) {
        AvatarEntity owner = SecurityUtils.getOwner(context, channelContext);
        if (owner == null) {
            GongCommonNotify.closeForNotLogin(channelContext);
            return;
        }
        LoggerHelper.info("servantUpgrade", owner.getAvatarModel(), servantId, auto);
        rpcResult.result(servantService.upgradeServant(owner, servantId, auto));
    }


    /**
     * @param channelContext
     * @param rpcResult
     * @param servantId      武将进阶
     */
    @Rpc(fullAlias = GongRpcConstants.REQ_USER_SERVANT_ADVANCE)
    public void servantAdvance(final ServerChannelContext channelContext, final RpcResult rpcResult, final String servantId) {
        AvatarEntity owner = SecurityUtils.getOwner(context, channelContext);
        if (owner == null) {
            GongCommonNotify.closeForNotLogin(channelContext);
            return;
        }
        LoggerHelper.info("servantAdvance", owner.getAvatarModel(), servantId);
        rpcResult.result(servantService.upgradeAdvance(owner, servantId));
    }


    /**
     * @param channelContext
     * @param rpcResult
     * @param zhaoMuType     招募类型: 1.选良人 2.选天人 3.十连抽
     * @param usePropType    使用道具方式: 1 按免费次数 2 使用道具 3 使用元宝
     */

    @Rpc(fullAlias = GongRpcConstants.REQ_USER_SERVANT_ZHAO_MU)
    public void servantZhaoMu(final ServerChannelContext channelContext, final RpcResult rpcResult, int zhaoMuType,
                              int usePropType) {
        AvatarEntity owner = SecurityUtils.getOwner(context, channelContext);
        if (owner == null) {
            GongCommonNotify.closeForNotLogin(channelContext);
            return;
        }
        LoggerHelper.info("servantZhaoMu", owner.getAvatarModel(), zhaoMuType, usePropType);
        servantService.servantZhaoMu(rpcResult, owner, zhaoMuType, usePropType);
    }


    /** ------------------------------------------------帮派分割线(liangbo)---------------------------------------------------------*/
    /**
     * 派系列表
     */
    @Rpc(fullAlias = GongRpcConstants.REQ_USER_GH_LOAD)
    public void enterGongHui(final ServerChannelContext channelContext, final RpcResult rpcResult) {
        AvatarEntity owner = SecurityUtils.getOwner(context, channelContext);
        if (owner == null) {
            GongCommonNotify.closeForNotLogin(channelContext);
            return;
        }

        LoggerHelper.info("enterbangpai", owner.getAvatarModel());

        int openLv = 0;
        //若表中有限制等级，则走限制等级，没有配表就不限制
        LMap ghLvData = SysLvData.data.getMap(GongConstants.SYSTEM_LV_OPEN_GONGHUI);
        if (ghLvData != null) {
            openLv = ghLvData.getInt("openLv", 0);
        }
        if (owner.getAvatarModel().getLv() < openLv) {
            GongCommonNotify.notifyMsg(channelContext, GongConstants.MSG_ID_NEED_LV, "仙盟", openLv);
            rpcResult.result();// 等级不够
            return;
        }

        rpcResult.result(gongHuiService.enterGongHui(owner));
    }

    /**
     * 创建帮派
     */
    @Rpc(fullAlias = GongRpcConstants.REQ_USER_GH_CREATE)
    public void createGongHui(final ServerChannelContext channelContext, final RpcResult rpcResult, String name,
                              int icon) {
        final AvatarEntity owner = SecurityUtils.getOwner(context, channelContext);
        if (owner == null) {
            GongCommonNotify.closeForNotLogin(channelContext);
            return;
        }
        LoggerHelper.info("createGongHui", owner.getAvatarModel(), name, icon);

        gongHuiService.createGongHui(name, icon, owner, rpcResult);

    }
    /** ------------------------------------------------帮派分割线(end)---------------------------------------------------------*/



    /**
     * 获取好友列表
     * @param channelContext
     * @param rpcResult
     */
    @Rpc(fullAlias = GongRpcConstants.REQ_USER_GET_FRIEND_LIST)
    public void getFriendList(final ServerChannelContext channelContext, final RpcResult rpcResult) {
        AvatarEntity owner = SecurityUtils.getOwner(context, channelContext);
        if (owner == null) {
            GongCommonNotify.closeForNotLogin(channelContext);
            return;
        }
        LoggerHelper.info("getFriendList", owner.getAvatarModel(), owner.getAvatarModel().getRoleName());
        shejiaoService.getFriendList(owner, rpcResult);
    }

    /**
     * 获取仇敌列表
     * @param channelContext
     * @param rpcResult
     */
    @Rpc(fullAlias = GongRpcConstants.REQ_USER_GET_ENEMY_LIST)
    public void getEnemyList(final ServerChannelContext channelContext, final RpcResult rpcResult) {
        AvatarEntity owner = SecurityUtils.getOwner(context, channelContext);
        if (owner == null) {
            GongCommonNotify.closeForNotLogin(channelContext);
            return;
        }
        LoggerHelper.info("getEnemyList", owner.getAvatarModel(), owner.getAvatarModel().getRoleName());
        shejiaoService.getEnemyList(owner, rpcResult);
    }

    /**
     * 获取某个玩家的简单信息
     * @param channelContext
     * @param rpcResult
     * @param targetAvatarId
     */
    @Rpc(fullAlias = GongRpcConstants.REQ_USER_GET_SIMPLE_PLAYER_INFO)
    public void getSimplePlayInfo(final ServerChannelContext channelContext, final RpcResult rpcResult, final String targetAvatarId) {
        AvatarEntity owner = SecurityUtils.getOwner(context, channelContext);
        if (owner == null) {
            GongCommonNotify.closeForNotLogin(channelContext);
            return;
        }
        LoggerHelper.info("getSimplePlayInfo", owner.getAvatarModel(), targetAvatarId);
        shejiaoService.getSimplePlayerInfo(owner, rpcResult, targetAvatarId);
    }

    /**
     * 移除好友
     * @param channelContext
     * @param rpcReuslt
     * @param targetAvatarId
     */
    @Rpc(fullAlias = GongRpcConstants.REQ_USER_REMOVE_FRIEND)
    public void removeFriend(final ServerChannelContext channelContext, final RpcResult rpcReuslt, final String targetAvatarId) {
        AvatarEntity owner = SecurityUtils.getOwner(context, channelContext);
        if (owner == null) {
            GongCommonNotify.closeForNotLogin(channelContext);
            return;
        }
        LoggerHelper.info("removeFriend", owner.getAvatarModel(), targetAvatarId);
        shejiaoService.removeFriend(owner, targetAvatarId, rpcReuslt);
    }

    /**
     * 移除仇敌
     * @param channelContext
     * @param rpcResult
     * @param targetAvatarId
     */
    @Rpc(fullAlias = GongRpcConstants.REQ_USER_REMOVE_ENEMY)
    public void removeEnemy(final ServerChannelContext channelContext, final RpcResult rpcResult, final String targetAvatarId) {
        AvatarEntity owner = SecurityUtils.getOwner(context, channelContext);
        if (owner == null) {
            GongCommonNotify.closeForNotLogin(channelContext);
            return;
        }
        LoggerHelper.info("removeEnemy", owner.getAvatarModel(), targetAvatarId);
        shejiaoService.removeEnemy(owner, targetAvatarId, rpcResult);
    }

    /**
     * 增加仇敌
     * @param channelContext
     * @param rpcResult
     * @param targetAvatarId
     */
    @Rpc(fullAlias = GongRpcConstants.REQ_USER_ADD_ENEMY)
    public void addEnemy(final ServerChannelContext channelContext, final RpcResult rpcResult, String targetAvatarId) {
        AvatarEntity owner = SecurityUtils.getOwner(context, channelContext);
        if (owner == null) {
            GongCommonNotify.closeForNotLogin(channelContext);
            return;
        }
        LoggerHelper.info("AddEnemy", owner.getAvatarModel(), targetAvatarId);


        if (StringUtils.isBlank(targetAvatarId)) {
            rpcResult.result(false);
            return;
        }

        if (owner.getAvatarModel().getId().equals(targetAvatarId)) {
            GongCommonNotify.notifyErrorMsg(channelContext, rpcResult, GongConstants.MSG_ID_DONT_ENEMY_SELF);
            rpcResult.result();
            return;
        }

        shejiaoService.addEnemy(owner, rpcResult, targetAvatarId);
    }

    /**
     * 发送结交信息
     * @param channelContext
     * @param rpcResult
     * @param targetAvatarIdList
     */
    @Rpc(fullAlias = GongRpcConstants.REQ_USER_SEND_JIEJIAO_MSG)
    public void sendJiejiaoMsg(final ServerChannelContext channelContext, final RpcResult rpcResult,
                               String[] targetAvatarIdList) {
        AvatarEntity owner = SecurityUtils.getOwner(context, channelContext);
        if (owner == null) {
            GongCommonNotify.closeForNotLogin(channelContext);
            return;
        }
        if (targetAvatarIdList == null) {
            rpcResult.result(false);
            return;
        }
        if (targetAvatarIdList.length == 1 && targetAvatarIdList[0].equals(owner.getAvatarModel().getId())) {
            GongCommonNotify.notifyErrorMsg(channelContext, rpcResult, GongConstants.MSG_ID_SJ_DONT_JJ);
            rpcResult.result(false);
            return;
        }
        LoggerHelper.info("sendJiejiaoMsg", owner.getAvatarModel());
        shejiaoService.sendJiejiaoMsg(owner, targetAvatarIdList, rpcResult);
    }

    /**
	 * 直接向某个角色名的玩家发送好友结交申请
	 * @param channelContext
	 * @param rpcResult
	 * @param pos
	 * @param servantId
	 */
    @Rpc(fullAlias = GongRpcConstants.REQ_USER_SEND_JIEJIAO_MSG_BY_NAME)
    public void sendJiejiaoByName(final ServerChannelContext channelContext, final RpcResult rpcResult, final String roleName) {
    	AvatarEntity owner = SecurityUtils.getOwner(context, channelContext);
    	if (owner == null) {
    		GongCommonNotify.closeForNotLogin(channelContext);
    		return;
    	}

    	if (StringUtils.isNotBlank(roleName)) {
    		rpcResult.result(false);
    		return;
    	}

    	LoggerHelper.info("sendJiejiaobyName", owner.getAvatarModel(), roleName);

    	shejiaoService.sendJiejiaoByName(owner, rpcResult, roleName);
    }

    /**
     * 同意结交为好友
     * @param channelContext
     * @param rpcResult
     * @param fromAvatarId
     * @param userMsgId
     */
    @Rpc(fullAlias = GongRpcConstants.REQ_USER_ACCEPT_JIEJIAO)
    public void acceptJiejiao(final ServerChannelContext channelContext, final RpcResult rpcResult, final String fromAvatarId) {
        AvatarEntity owner = SecurityUtils.getOwner(context, channelContext);
        if (owner == null) {
            GongCommonNotify.closeForNotLogin(channelContext);
            return;
        }

        if (StringUtils.isBlank(fromAvatarId)) {
            rpcResult.result(false);
            return;
        }

        if (owner.getAvatarModel().getId().equals(fromAvatarId)) {
            GongCommonNotify.notifyErrorMsg(channelContext, rpcResult, GongConstants.MSG_ID_SJ_DONT_JJ);
            rpcResult.result();
            return;
        }
        LoggerHelper.info("acceptJiejiao", owner.getAvatarModel(), fromAvatarId);
        shejiaoService.acceptJiejiao(owner, rpcResult, fromAvatarId);
    }


    /**
     * 拒绝结交好友
     * @param channelContext
     * @param rpcResult
     * @param fromAvatarId
     * @param userMsgId
     */
    @Rpc(fullAlias = GongRpcConstants.REQ_USER_REJECT_JIEJIAO)
    public void rejectJiejiao(final ServerChannelContext channelContext, final RpcResult rpcResult, final String fromAvatarId,
                              final long userMsgId) {
        AvatarEntity owner = SecurityUtils.getOwner(context, channelContext);
        if (owner == null) {
            GongCommonNotify.closeForNotLogin(channelContext);
            return;
        }
        if (StringUtils.isBlank(fromAvatarId)) {
            rpcResult.result(false);
            return;
        }
        if (owner.getAvatarModel().getId().equals(fromAvatarId)) {
            GongCommonNotify.notifyErrorMsg(channelContext, rpcResult, GongConstants.MSG_ID_SJ_DONT_JJ);
            rpcResult.result();
            return;
        }
        LoggerHelper.info("rejectJiejiao", owner.getAvatarModel(), fromAvatarId);
        shejiaoService.rejectJiejiao(owner, rpcResult, fromAvatarId, userMsgId);
    }

    /**
     * 根据关键字模糊匹配角色名，获取玩家列表
     * @param channelContext
     * @param rpcResult
     * @param key
     * @param page
     */
    @Rpc(fullAlias = GongRpcConstants.REQ_USER_SEARCH_PLAYER)
    public void searchPlayer(final ServerChannelContext channelContext, final RpcResult rpcResult, String key, int page) {

        AvatarEntity owner = SecurityUtils.getOwner(context, channelContext);
        if (owner == null) {
            GongCommonNotify.closeForNotLogin(channelContext);
            return;
        }

        LoggerHelper.info("SearchPlayer", owner.getAvatarModel(), key, page);
        if (StringUtils.isBlank(key)) {
            rpcResult.result(false);
            return;
        }
        shejiaoService.searchPlayer(owner, rpcResult, key, page);
    }

    /**
     * 默认推荐一批可以加好友的玩家
     * @param channelContext
     * @param rpcResult
     */
    @Rpc(fullAlias = GongRpcConstants.REQ_USER_DEFAULT_SEARCH_PLAYER)
    public void defaultSearchPlayer(final ServerChannelContext channelContext, final RpcResult rpcResult) {
        AvatarEntity owner = SecurityUtils.getOwner(context, channelContext);
        if (owner == null) {
            GongCommonNotify.closeForNotLogin(channelContext);
            return;
        }
        LoggerHelper.info("defaultSearchPlayer", owner.getAvatarModel());
        shejiaoService.defaultSearchPlayer(owner, rpcResult);
    }

    /**
     * 获取对方申请自己为好友的玩家列表
     * @param channelContext
     * @param rpcResult
     */
    @Rpc(fullAlias = GongRpcConstants.REQ_USER_GET_JIEJIAO_LIST)
    public void getJiejiaoList(final ServerChannelContext channelContext, final RpcResult rpcResult) {
        AvatarEntity owner = SecurityUtils.getOwner(context, channelContext);
        if (owner == null) {
            GongCommonNotify.closeForNotLogin(channelContext);
            return;
        }
        LoggerHelper.info("getJiejiaoList", owner.getAvatarModel());
        shejiaoService.getJiejiaoList(owner, rpcResult);
    }

    /**
     * 获取最近联系人列表
     * @param channelContext
     * @param rpcResult
     */
    @Rpc(fullAlias = GongRpcConstants.REQ_USER_GET_RECENT_CONTACT)
    public void getRecentContact(final ServerChannelContext channelContext, final RpcResult rpcResult) {
        AvatarEntity owner = SecurityUtils.getOwner(context, channelContext);
        if (owner == null) {
            GongCommonNotify.closeForNotLogin(channelContext);
            return;
        }
        LoggerHelper.info("getRecentContact", owner.getAvatarModel());

        shejiaoService.getRecentContact(owner, rpcResult);
    }

    /**
     * 获取与指定玩家的私聊信息
     * @param channelContext
     * @param rpcResult
     * @param targetId
     */
    @Rpc(fullAlias = GongRpcConstants.REQ_USER_GET_SL_SY)
    public void getSlSay(final ServerChannelContext channelContext, final RpcResult rpcResult, final String targetId) {
        AvatarEntity owner = SecurityUtils.getOwner(context, channelContext);
        if(owner == null) {
            GongCommonNotify.closeForNotLogin(channelContext);
            return;
        }
        LoggerHelper.info("getSlInfomation", owner.getAvatarModel(), targetId);

        context.executeRawCommand(GongDbConstants.CMD_GET_SILIAO_CHAT, new Object[] {owner.getAvatarModel().getId(), targetId},
                new RawCommandCallback() {

                    @Override
                    public void onResult(Object result, int num, String error) {
                        if (result != null) {
                            rpcResult.result(new Object[] { true, result });
                        } else {
                            rpcResult.error();
                        }
                    }
                });
    }

    /**
     * 打开聊天窗口
     * @param channelContext
     * @param rpcResult
     * @param targetAvatarId
     */
    @Rpc(fullAlias = GongRpcConstants.REQ_USER_OPEN_MSG_WINDOW)
    public void openMsgWindow(final ServerChannelContext channelContext, final RpcResult rpcResult, final String targetAvatarId) {

        AvatarEntity owner = SecurityUtils.getOwner(context, channelContext);
        if(owner == null) {
            GongCommonNotify.closeForNotLogin(channelContext);
            return;
        }

        if (StringUtils.isEmpty(targetAvatarId)) {
            rpcResult.result(false);
            return;
        }
        LoggerHelper.info("openMsgWindow", owner.getAvatarModel(), targetAvatarId);
        shejiaoService.openMsgWindow(owner, targetAvatarId, rpcResult);
    }

    /**
     * 关闭所有聊天窗口
     * @param channelContext
     * @param rpcResult
     */
    @Rpc(fullAlias = GongRpcConstants.REQ_USER_CLOSE_MSG_WINDOW)
    public void closeMsgWindow(final ServerChannelContext channelContext, final RpcResult rpcResult) {
        AvatarEntity owner = SecurityUtils.getOwner(context, channelContext);
        if (owner == null) {
            GongCommonNotify.closeForNotLogin(channelContext);
            return;
        }
        LoggerHelper.info("colseMsgWindow", owner.getAvatarModel());
        shejiaoService.closeAllMsgWindow(owner);
    }

    //	@Rpc(fullAlias = GongRpcConstants.REQ_USER_FIGHT_GUANKA)
    public void test(final ServerChannelContext channelContext, final RpcResult rpcResult) {
//		getFriendList(channelContext, rpcResult);
//		searchPlay(channelContext, rpcResult, "司徒", 1);
//		defaultSearchPlayer(channelContext, rpcResult);
//		sendJiejiaoMsg(channelContext, rpcResult, new String[] {"2-1-1512541902-1"});
//		getJiejiaoList(channelContext, rpcResult);
//		acceptJiejiao(channelContext, rpcResult, "2-1-1511949180-1", 9);
//		rejectJiejiao(channelContext, rpcResult, "2-1-1511949180-1", 9);
    }



}
