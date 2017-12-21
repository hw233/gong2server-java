package com.gamejelly.gong2.gas.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.gamejelly.gong2.config.MessageConfig;
import com.gamejelly.gong2.config.data.SysConstData;
import com.gamejelly.gong2.gas.service.user.MessageService;
import com.gamejelly.gong2.meta.GongHuiMemberModel;
import com.gamejelly.gong2.meta.GongHuiModel;
import com.gamejelly.gong2.meta.GongHuiMsgModel;
import com.gamejelly.gong2.utils.GongConstants;
import com.gamejelly.gong2.utils.GongRpcConstants;
import com.gamejelly.gong2.utils.GongUtils;
import com.hadoit.game.common.lang.DataUtils;
import com.hadoit.game.engine.core.executor.TimerListener;
import com.hadoit.game.engine.guardian.gas.entity.Entity;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;

public class GongHuiEntity extends Entity {

    private Long timerId;

    public Long getTimerId() {
        return timerId;
    }

    public void setTimerId(Long timerId) {
        this.timerId = timerId;
    }

    @Override
    public String getId() {
        return getGongHuiModel().getId();
    }

    @Override
    public String getEntityType() {
        return "GongHuiEntity";
    }

    public GongHuiModel getGongHuiModel() {
        return (GongHuiModel) super.getDbModel();
    }

    /**
     * 计时器替代首领
     *
     * @param logoutTime
     */
    public void startTransferTimer(long logoutTime) {
        if (logoutTime <= 0) {
            return;
        }
        long endTime = SysConstData.data.getInt("LMXT_ZR_CD") * 24 * 3600
                - (System.currentTimeMillis() - logoutTime) / 1000;
        long tId = addTimer(new TimerListener() {
            @Override
            public void onTimer(long id, Object[] params) throws Exception {
                long curTime = System.currentTimeMillis();
                GongHuiMemberModel leader = null;
                GongHuiMemberModel agent = null;

                for (GongHuiMemberModel member : getGongHuiModel().getMemberList()) {
                    if (member.getState() == 1) {
                        long time = curTime - member.getLogoutTime();
                        if (member.getPosition() == GongConstants.GONGHUI_POSITION_BANGZHU) {
                            leader = member;
                        } else if (member.isOnline() || time / 1000 < 48 * 60 * 60) {
                            if (agent == null) {
                                agent = member;
                            } else {
                                if (member.getPosition() > agent.getPosition()) {
                                    agent = member;
                                } else if (member.getPosition() == agent.getPosition()) {
                                    if (member.getTotalDonate() > agent.getTotalDonate()) {
                                        agent = member;
                                    } else if (member.getTotalDonate() == agent.getTotalDonate()) {
                                        if (member.getDfExp() > agent.getDfExp()) {
                                            agent = member;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                if (agent != null && leader != null) {
                    int post = agent.getPosition();
                    leader.setPosition(post);
                    agent.setPosition(GongConstants.GONGHUI_POSITION_BANGZHU);
                    getGongHuiModel().setLeaderId(agent.getAvatarId());
                    getGongHuiModel().setLeaderName(agent.getName());

                    MessageService messageService = GongUtils.getMessageService();
                    messageService.sendSystemCommonMsg(Arrays.asList(leader.getAvatarId()), MessageConfig.getCommonMsg(
                            GongConstants.MSG_ID_GH_MAIL_TRANSFER, SysConstData.data.getInt("LMXT_ZR_CD")));
                    messageService.sendSystemCommonMsg(Arrays.asList(agent.getAvatarId()), MessageConfig.getCommonMsg(
                            GongConstants.MSG_ID_GH_MAIL_EXTENDS, SysConstData.data.getInt("LMXT_ZR_CD")));

                    addMessage(MessageConfig.getCommonMsg(GongConstants.MSG_ID_GH_TRANSFER, leader.getName(),
                            agent.getName()));
                    addMessage(MessageConfig.getCommonMsg(GongConstants.MSG_ID_GH_EXTENDS, agent.getName()));
                    if (!agent.isOnline()) {
                        startTransferTimer(System.currentTimeMillis());
                    }
                }
            }
        }, endTime > 0 ? endTime : 0, 0);
        getGongHuiModel().setTimerId(tId);
    }

    public void startDisbandTimer(long btime) {
        if (btime > 0) {
            long endTime = SysConstData.data.getInt("LMXT_JS_CD") - (System.currentTimeMillis() - btime) / 1000;
            long newTimerId = addTimer(new TimerListener() {
                public void onTimer(long id, Object[] params) throws Exception {
                    getGongHuiModel().setEnable(false);

                    List<String> mbList = new ArrayList<String>();
                    for (GongHuiMemberModel member : getGongHuiModel().getMemberList()) {
                        if (member.getState() == 1) {
                            mbList.add(member.getAvatarId());
                        }
                    }

                    getGongHuiModel().getMemberList().clear();
                    GongUtils.getGongHuiService().removeGongHuiEntity(GongHuiEntity.this);

                    if (CollectionUtils.isNotEmpty(mbList)) {
                        MessageService messageService = GongUtils.getMessageService();
                        messageService.sendSystemCommonMsg(mbList, MessageConfig
                                .getCommonMsg(GongConstants.MSG_ID_GH_MAIL_DISBAND, getGongHuiModel().getName()));
                    }
                }
            }, endTime > 0 ? endTime : 0, 0);
            setTimerId(newTimerId);
        }
    }

    public void startDailyTimer() {
        long currTime = System.currentTimeMillis();
        if (!GongUtils.isSameDayForOffset(currTime, this.getGongHuiModel().getLastMaintainTime(), 0)) {
            // 处理公会维护费用
            this.getGongHuiModel().setLastMaintainTime(currTime);
            GongUtils.getGongHuiService().handleGonghuiMaintain(this);
        }
        addTimer(new TimerListener() {
            public void onTimer(long id, Object[] params) throws Exception {
                startDailyTimer();
            }
        }, GongUtils.getOffsetTimeSecsToZero() + 5, 0);
    }

    public void addMessage(String msg) {
        msg = GongUtils.trimUnicode(msg); // 去掉Unicode字符
        final String newMsg = StringUtils.trimToEmpty(DataUtils.toDBC(msg));

        List<GongHuiMsgModel> messageList = getGongHuiModel().getMessageList();
        messageList.add(new GongHuiMsgModel(newMsg, System.currentTimeMillis()));

        if (msg.length() > 500) {
            GuardianLogger.warn("gonghui addMessage length=", msg.length(), " size=", messageList.size(), " msg=", msg,
                    " newMsg=", newMsg);
        }

        while (messageList.size() > 50) {
            messageList.remove(0);
        }

        List<String> msgList = getGongHuiModel().getMsgList();
        List<Long> msgTimeList = getGongHuiModel().getMsgTimeList();

        for (GongHuiMemberModel member : getGongHuiModel().getMemberList()) {
            if (member.getState() == 1 && member.isOnline()) {
                AvatarEntity olEntity = (AvatarEntity) GongUtils.getGasManager().getEntityManager()
                        .getEntity(member.getAvatarId());
                if (olEntity != null) {
                    olEntity.notify(GongRpcConstants.RES_USER_GH_LOAD_MESSAGE, new Object[] { msgList, msgTimeList });
                }
            }
        }
    }
}
