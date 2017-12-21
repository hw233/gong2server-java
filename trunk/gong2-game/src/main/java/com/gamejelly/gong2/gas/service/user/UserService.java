package com.gamejelly.gong2.gas.service.user;

import java.util.ArrayList;
import java.util.List;

import com.gamejelly.gong2.meta.GuankaModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.gamejelly.gong2.config.data.GuankaBaseData;
import com.gamejelly.gong2.config.data.ConsumeData;
import com.gamejelly.gong2.config.data.InitData;
import com.gamejelly.gong2.config.data.SysConstData;
import com.gamejelly.gong2.config.data.UserGroupData;
import com.gamejelly.gong2.config.data.base.LMap;
import com.gamejelly.gong2.gas.admin.service.ServerConfigService;
import com.gamejelly.gong2.gas.entity.AvatarEntity;
import com.gamejelly.gong2.gas.entity.ServantEntity;
import com.gamejelly.gong2.gas.proxy.UserProxy;
import com.gamejelly.gong2.meta.AvatarModel;
import com.gamejelly.gong2.meta.CycleOperateModel;
import com.gamejelly.gong2.meta.ItemModel;
import com.gamejelly.gong2.utils.GongCommonNotify;
import com.gamejelly.gong2.utils.GongConstants;
import com.gamejelly.gong2.utils.GongDbConstants;
import com.gamejelly.gong2.utils.GongLogConstants;
import com.gamejelly.gong2.utils.GongRpcConstants;
import com.gamejelly.gong2.utils.GongUtils;
import com.gamejelly.gong2.utils.IdProvider;
import com.gamejelly.gong2.utils.LoggerHelper;
import com.gamejelly.gong2.utils.LogicUtils;
import com.gamejelly.gong2.utils.SecurityUtils;
import com.gamejelly.gong2.vo.AvatarVO;
import com.gamejelly.gong2.vo.InstanceChangeVO;
import com.gamejelly.gong2.vo.SimplePlayerInfo;
import com.hadoit.game.common.lang.DataUtils;
import com.hadoit.game.engine.core.rpc.simple.context.RpcResult;
import com.hadoit.game.engine.core.rpc.simple.context.ServerChannelContext;
import com.hadoit.game.engine.core.utils.callback.CallbackFuture;
import com.hadoit.game.engine.core.utils.callback.FutureCallback;
import com.hadoit.game.engine.guardian.gas.GasManager;
import com.hadoit.game.engine.guardian.gas.entity.Entity;
import com.hadoit.game.engine.guardian.gas.entity.EntityCallback;
import com.hadoit.game.engine.guardian.gas.entity.RawCommandCallback;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;

@Component("userService")
public class UserService {

    @Autowired
    @Qualifier("serverConfigService")
    private ServerConfigService serverConfigService;
    @Autowired
    @Qualifier("itemService")
    private ItemService itemService;
    @Autowired
    @Qualifier("gasManager")
    protected GasManager gasManager;
    @Autowired
    @Qualifier("shejiaoService")
    protected ShejiaoService shejiaoService;

    public AvatarModel createAvatarModel(String accountName, int serverId, String roleName, int sex, boolean god) {
        AvatarModel avatarModel = new AvatarModel();
        avatarModel.setId(IdProvider.genId(GongConstants.ID_TYPE_AVATAR));
        avatarModel.setAccountName(accountName);
        avatarModel.setServerId(serverId);
        int userGroup = serverConfigService.getUserGroupDefault();
        LMap groupData = UserGroupData.data.getMap(accountName);
        if (groupData != null) {
            userGroup = groupData.getInt("group", userGroup);
        }
        if (god) {
            userGroup = GongConstants.USER_GROUP_GOD;
        }

        avatarModel.setUserGroup(userGroup);
        avatarModel.setRoleName(roleName);
        avatarModel.setLv(1);
        avatarModel.setExp(0);
        avatarModel.setSex(sex);

        if (sex == GongConstants.SEX_FEMALE) {
            avatarModel.setIconIndex(SysConstData.data.getInt("AVATAR_ICON_DEFAULT_0,", 0));
        } else if (sex == GongConstants.SEX_MALE) {
            avatarModel.setIconIndex(SysConstData.data.getInt("AVATAR_ICON_DEFAULT_1", 0));
        }

        avatarModel.setCreateTime(System.currentTimeMillis());


        // 初始化关卡
        LMap initData = InitData.data.getMap(avatarModel.getLv());
        int initScene = initData.getInt("initScene", 0);
        if (initScene > 0) {
            for (Object key : GuankaBaseData.data.keySet()) {
                LMap val = GuankaBaseData.data.getMap((Integer) key);
                if (val.getInt("scene") == initScene) {
                    List<GuankaModel> gks = new ArrayList<GuankaModel>();
                    GuankaModel initGk = new GuankaModel();
                    initGk.setTemplateId(val.getInt("id"));
                    gks.add(initGk);
                    avatarModel.setGuankaList(gks);
                    break;
                }
            }
        }

        return avatarModel;
    }

    private void checkAndClearData(AvatarEntity entity) {
        List<ServantEntity> allSer = entity.getAllServant();
        List<ItemModel> allItems = entity.getAllItem();
        if (CollectionUtils.isNotEmpty(allItems)) {
            for (ItemModel it : allItems) {

                if (!itemService.checkItemType(it.getTemplateId())) {
                    itemService.removeItem(entity, it.getId(), GongLogConstants.LOG_ITEM_CHANGE_SYSTEM_FIX_ITEM, 0, 0);
                    continue;
                }

                if (it.getCount() <= 0) {
                    GuardianLogger.error("CheckItemType item count is invalid! templateId=", it.getTemplateId(),
                            ", count=", it.getCount(), ", id=", it.getId());
                    itemService.removeItem(entity, it.getId(), GongLogConstants.LOG_ITEM_CHANGE_SYSTEM_FIX_ITEM, 0, 0);
                }
            }

            if (entity.getUsedZhenfa() == null) {
                int zfTmpId = InitData.data.getMap(1).getInt("initZf", 0);
                if (zfTmpId > 0) {
                    ItemModel zfItme = LogicUtils.createItem(zfTmpId, 1).get(0);
                    zfItme.setZfUsed(true);
                    itemService.addItem(entity, zfItme, -1, -1, -1);

                    // 第一个阵法需要自动放上仆从,第一个仆从放2号位
                    if (allSer.size() > 0 && entity.getShangzhenServant().size() == 0) {
                        allSer.get(0).getModel().setPos(SysConstData.data.getInt("AVATAR_FIGHT_INIT_POS"));
                    }
                }
            }
        }

    }

    public boolean onLogin(final AvatarEntity entity, boolean isNew) {
        long curTime = System.currentTimeMillis();

        // 每次登陆检测身上的物品是否在配置表里面
        // 检测仆从模板是否成为了物品, 如果有就删除
        checkAndClearData(entity);

        fixCycleOperateModelList(entity, isNew);
        resetData(entity, curTime); // 重置周期性数据

        // 每天是否第一次登陆
        boolean dailyFirstLogin = false;
        if (!GongUtils.isSameDayForOffset(curTime, entity.getAvatarModel().getLoginTime(), 0)) {
            dailyFirstLogin = true;
            entity.getAvatarModel().incrLoginCount(1);
            LoggerHelper.info("firstLogin onLogin", entity.getAvatarModel());
        }
        entity.getAvatarModel().setLoginTime(curTime);
        entity.onLogin();

        return dailyFirstLogin;
    }

    public void logout(final ServerChannelContext channelContext) {
        AvatarEntity owner = SecurityUtils.getOwner(gasManager, channelContext);
        String accountName;
        if (owner != null) {
            accountName = owner.getAvatarModel().getAccountName();
            logoutRole(owner, null);
        } else {
            accountName = SecurityUtils.getLoginAccount(channelContext);
            if (accountName != null) {
                LoggerHelper.info("logout", null, SecurityUtils.getLoginAccount(channelContext));
            }
        }
        SecurityUtils.logout(channelContext);
        GongCommonNotify.close(channelContext);
    }

    public void logoutRole(AvatarEntity owner, EntityCallback<Entity> callback) {
        owner.getAvatarModel().setLogoutTime(System.currentTimeMillis());
        
        // 断线或者离线时关闭将所有对话窗口置为关闭
        shejiaoService.closeAllMsgWindow(owner);
        
        owner.destroy(callback);
        LoggerHelper.info("logoutRole", owner.getAvatarModel());
    }


    public boolean canCycleOperate(CycleOperateModel cycleOperateModel, long incrCdCount, long maxCdCount) {
        if (maxCdCount < 1 || incrCdCount > maxCdCount) {
            return false;
        }

        long curTime = System.currentTimeMillis();
        if (cycleOperateModel.isInSameCd(curTime)) {
            if (cycleOperateModel.isInPeriodGap(curTime)) {
                return false;
            }
            return cycleOperateModel.getCdCount() + incrCdCount <= maxCdCount;
        } else {
            cycleOperateModel.setLastCdTime(curTime);
            cycleOperateModel.setCdCount(0);
        }
        return true;
    }

    public void updateCycleOperate(CycleOperateModel cycleOperateModel, long incrCdCount, long incrCdDuration) {
        long curTime = System.currentTimeMillis();
        if (!cycleOperateModel.isInSameCd(curTime)) {
            cycleOperateModel.setLastCdTime(curTime);
            cycleOperateModel.setCdCount(0);
        }

        cycleOperateModel.incrCdCount(incrCdCount);
        cycleOperateModel.setCdPeriod(incrCdDuration);
        cycleOperateModel.setCdPeriodGapTime(curTime + incrCdDuration);
    }

    private void updateCycleOperateOnLogin(CycleOperateModel cycleOperateModel, long curTime, long cdPeriod,
                                           long offsetTime) {
        if (offsetTime > -1) {
            cycleOperateModel.setOffsetTime(offsetTime);
        }
        if (cdPeriod > 0) {
            long cdPeriodGapTime = cycleOperateModel.getCdPeriodGapTime() - cycleOperateModel.getCdPeriod() + cdPeriod;
            cycleOperateModel.setCdPeriod(cdPeriod);
            cycleOperateModel.setCdPeriodGapTime(Math.max(cdPeriodGapTime, 0));
        }
        if (!cycleOperateModel.isInSameCd(curTime)) {
            cycleOperateModel.setLastCdTime(curTime);
            cycleOperateModel.setCdCount(0);
            cycleOperateModel.setCdPeriodGapTime(0);
        }
    }

    private void updateCycleOperateOnLogin(CycleOperateModel cycleOperateModel, long curTime, long cdPeriod) {
        updateCycleOperateOnLogin(cycleOperateModel, curTime, cdPeriod, -1);
    }

    public void resetData(AvatarEntity entity, long curTime) {

        // 每日周期性数据
        for (int operateId : GongConstants.OPERATE_TYPE_DAY_LIST) {
            updateCycleOperateOnLogin(entity.getCycleOperate(operateId), curTime, 0);
        }

    }

    public void fixCycleOperateModelList(AvatarEntity entity, boolean isNew) {
        long curTime = System.currentTimeMillis();
        List<CycleOperateModel> oldList = entity.getAvatarModel().getCycleOperateList();

        // 每日周期性数据
        for (int operateId : GongConstants.OPERATE_TYPE_DAY_LIST) {
            entity.addCycleOperate(defaultOpt(curTime, operateId, GongConstants.OPERATE_CD_TYPE_DAY, oldList));
        }

    }

    private CycleOperateModel optExists(List<CycleOperateModel> oldList, final int optType) {
        return (CycleOperateModel) CollectionUtils.find(oldList, new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                return ((CycleOperateModel) object).getOperateType() == optType;
            }
        });
    }

    // 通用的
    private CycleOperateModel defaultOpt(long curTime, int optId, int cdType, List<CycleOperateModel> oldList) {
        CycleOperateModel co = optExists(oldList, optId);
        if (co != null)
            return co;
        return new CycleOperateModel(optId, cdType, curTime);
    }

    /**
     * @Title: changeName @Description: TODO(改名字服务逻辑) @param @param
     * entity @param @param rpcResult @param @param name @param @param
     * useProp 设定文件 @return void 返回类型 @throws
     */
    public void changeName(final AvatarEntity entity, final RpcResult rpcResult, String name, final boolean useProp) {
        name = GongUtils.trimUnicode(name); // 去掉Unicode字符
        final String roleName = StringUtils.trimToEmpty(DataUtils.toDBC(name));

        if (!GongUtils.checkRoleName(roleName)) {
            GongCommonNotify.notifyMsg(entity.getChannelContext(), GongConstants.MSG_ID_ROLE_NAME_ERROR);
            rpcResult.result(false);
            return;
        }
        if (GongUtils.isSpecialChar(roleName)) {
            GongCommonNotify.notifyMsg(entity.getChannelContext(), GongConstants.MSG_ID_ROLE_NAME_ERROR);
            rpcResult.result(false);
            return;
        }
        LMap consumeData = ConsumeData.data.getMap(GongConstants.CONSUME_ID_CHANGE_NAME);
        final long consumeRmb = consumeData.getList("consume").getLong(0);

        if (useProp) {
            int materialID = GongConstants.ITEM_ID_JUE_SE_GAI_MING_KA;
            int itemHaveCount = entity.getItemCountFromTemplateId(materialID);
            if (itemHaveCount < 1) {
                // 数量不足
                GongCommonNotify.notifyMsg(entity.getChannelContext(), GongConstants.MSG_ID_ITEM_NOT_ENOUGH);
                rpcResult.result(false);
                return;
            }
        } else {
            if (!entity.canConsumeGold(consumeRmb)) {
                GongCommonNotify.notifyMsg(entity.getChannelContext(), GongConstants.MSG_ID_GOLD_NOT_ENOUGH);
                rpcResult.result(false);
                return;
            }
        }

        gasManager.executeRawCommand(GongDbConstants.CMD_GET_AVARAE_ID_BY_ROLE, new Object[]{roleName},
                new RawCommandCallback() {
                    @Override
                    public void onResult(Object result, int num, String error) {
                        if (result != null && (Long) result > 0) {
                            // 名字已经存在
                            GongCommonNotify.notifyMsg(entity.getChannelContext(),
                                    GongConstants.MSG_ID_ROLE_NAME_EXISTS);
                            rpcResult.result(false);
                        } else {
                            updateRoleName(entity, rpcResult, roleName, consumeRmb, useProp);
                        }
                    }
                });
    }

    /**
     * @Title: updateRoleName @Description: TODO(更新角色名字) @param @param
     * entity @param @param rpcResult @param @param
     * roleName @param @param consumeRmb @param @param useProp
     * 设定文件 @return void 返回类型 @throws
     */
    private void updateRoleName(final AvatarEntity entity, final RpcResult rpcResult, final String roleName,
                                final long consumeRmb, final boolean useProp) {
        entity.getAvatarModel().setRoleName(roleName);
        // 这里保存整个对象是为了顺序保存
        gasManager.saveGameObject(entity.getEntityType(), entity.getDbModel(), new FutureCallback<Long>() {
            @Override
            public void onComplete(CallbackFuture<Long> future) throws Exception {
                if (future.isSuccess()) {

                    if (useProp) {
                        List<InstanceChangeVO> allChanges = new ArrayList<InstanceChangeVO>();
                        int materialID = GongConstants.ITEM_ID_JUE_SE_GAI_MING_KA;
                        List<InstanceChangeVO> r1 = itemService.deductItem(entity, materialID, 1,
                                GongLogConstants.LOG_ITEM_CHANGE_CHANGE_NAME, 0, 0);
                        if (r1 != null) {
                            allChanges.addAll(r1);
                            // 通知
                            itemService.notifyItemChanges(entity, allChanges);
                        }
                    } else {
                        entity.consumeGold(consumeRmb, GongLogConstants.LOG_GOLD_CHANGE_CHANGE_NAME, "");
                    }

                    entity.notify(GongRpcConstants.RES_USER_AVATAR_CHANGE, new AvatarVO(entity));
                    rpcResult.result(true);
                } else {
                    // 名字已经存在
                    GongCommonNotify.notifyMsg(entity.getChannelContext(), GongConstants.MSG_ID_ROLE_NAME_EXISTS);
                    rpcResult.result(false);
                }
            }
        });
    }


    // 签名修改
    public boolean changeQianming(AvatarEntity entity, String msg) {
        // if (StringUtils.isEmpty(msg)) {
        // return false;
        // }
        msg = GongUtils.trimUnicode(msg); // 去掉Unicode字符
        msg = StringUtils.trimToEmpty(DataUtils.toDBC(msg)); // toDBC
        if (GongUtils.checkTaboo(msg)) {
            return false;
        }
        entity.getAvatarModel().setQianming(msg);
        entity.notify(GongRpcConstants.RES_USER_AVATAR_CHANGE, new AvatarVO(entity));
        return true;
    }
    
    public void notifyEnemyList(final AvatarEntity entity) {

		final String myAvatarId = entity.getId();
		gasManager.executeRawCommand(GongDbConstants.CMD_GET_RELATION_LIST,
				new Object[] { entity.getAvatarModel().getId(), GongConstants.RELATION_TYPE_ENEMY },
				new RawCommandCallback() {
					@Override
					public void onResult(Object result, int num, String error) {
						if (error == null) {

							AvatarEntity myAvatarEntity = (AvatarEntity) gasManager.getEntityManager()
									.getEntity(myAvatarId);
							if (myAvatarEntity == null || myAvatarEntity.isUnused()) {
								return;
							}
							myAvatarEntity.notify(GongRpcConstants.RES_USER_LOGIN_ENEMY_LIST, result);
						}
					}
				});
	}
}
