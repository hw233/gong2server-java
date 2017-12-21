package com.gamejelly.gong2.meta;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import com.gamejelly.gong2.config.data.LeagueData;
import com.gamejelly.gong2.config.data.SysConstData;
import com.gamejelly.gong2.meta.base.BaseModel;
import com.gamejelly.gong2.utils.GongUtils;
import com.hadoit.game.common.framework.dao.annotation.Column;
import com.hadoit.game.common.framework.dao.annotation.Embedded;
import com.hadoit.game.common.framework.dao.annotation.Table;
import com.hadoit.game.common.framework.dao.convert.impl.CollectionNumberOrStringColumnConvert;
import com.hadoit.game.common.framework.dao.convert.impl.MapNumberOrStringColumnConvert;

@Table(recursion = true)
public class GongHuiModel extends BaseModel {
    private static final long serialVersionUID = 1L;

    /**
     * 表示唯一的id
     */
    @Column(value = "uid", index = true)
    private String id;

    /**
     * 公会名称
     */
    @Column(length = 50, unique = true)
    private String name;

    /**
     * 公会标志
     */
    private int icon;

    /**
     * 盟主Id
     */
    private String leaderId;

    /**
     * 盟主名字
     */
    private String leaderName;

    /**
     * 创始人名字
     */
    private String creatorName;

    /**
     * 创始人id
     */
    private String creatorAvatarId;

    /**
     * 公会等级(就是乾清宫的等级)
     */
    private int level;

    /**
     * 公告
     */
    private String notice;

    /**
     * 跨服战斗公告
     */
    private String noticeGhw;

    /**
     * 公会状态
     */
    private boolean enable;

    /**
     * 建立时间
     */
    private long createTime;

    /**
     * 解散时间
     */
    private long dismissTime;

    /**
     * 上次任务刷新时间
     */
    private long lastMissionRefreshTime;

    /**
     * 开启过的副本<副本ID, 通关时间>
     */
    @Column(convertClazz = MapNumberOrStringColumnConvert.class, length = 65535)
    private Map<Integer, Long> openFubenData = new HashMap<Integer, Long>();

    @Column(ignore = true)
    private long timerId;

    @Column(ignore = true)
    private List<GongHuiMsgModel> messageList = new ArrayList<GongHuiMsgModel>();

    @Column(ignore = true)
    private List<GongHuiMemberModel> memberList = new ArrayList<GongHuiMemberModel>();

    @Column(ignore = true)
    private List<GongHuiMissionModel> missionList = new ArrayList<GongHuiMissionModel>();

    @Column(ignore = true)
    private List<GongHuiFubenModel> openFubenModels = new ArrayList<GongHuiFubenModel>();

    @Column(ignore = true)
    private List<GongHuiApplyModel> applyList = new ArrayList<GongHuiApplyModel>();

    /**
     * 公会金砖, 注意这个值可以为负
     */
    private long gonghuiJinzhuan;

    /**
     * 藏书阁等级
     */
    private int cangshugeLv;

    /**
     * GongHuiModelDao.java
     */
    private int tianxianglouLv;

    /**
     * 多宝阁等级
     */
    private int duobaogeLv;

    /**
     * 敌对派系Id列表
     */
    @Column(convertClazz = CollectionNumberOrStringColumnConvert.class, length = 65535)
    private List<String> diduiGhList = new ArrayList<String>();

    /**
     * 敌对派系添加次数
     */
    private int diduiAddCount;

    /**
     * 上次扣维护费的时间
     */
    private long lastMaintainTime;

    /**
     * 每日邀请总次数
     */
    private int invitationCount;


    /**
     * 阵营
     */
    private int camp;

    /**
     * 最后一次发布邀请时间
     */
    private long invitationTime;

    @Embedded
    private GangBossModel gangBossModel = new GangBossModel();

    @Embedded
    private GangEndWeekBossModel endWeekBossModel = new GangEndWeekBossModel();

    public GongHuiModel() {

    }

    public GongHuiModel(String name, int icon, String leaderId, String leaderName, String notice,
                        String createrAvatarId) {
        this.name = name;
        this.icon = icon;
        this.leaderId = leaderId;
        this.leaderName = leaderName;
        this.creatorName = leaderName;
        this.creatorAvatarId = createrAvatarId;
        this.notice = notice;
        this.level = 1;
        this.cangshugeLv = 1;
        this.tianxianglouLv = 1;
        this.duobaogeLv = 1;
        this.enable = true;
        this.createTime = System.currentTimeMillis();
        this.lastMaintainTime = System.currentTimeMillis();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getDismissTime() {
        return dismissTime;
    }

    public void setDismissTime(long dismissTime) {
        this.dismissTime = dismissTime;
    }

    public long getTimerId() {
        return timerId;
    }

    public void setTimerId(long timerId) {
        this.timerId = timerId;
    }

    public List<GongHuiMsgModel> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<GongHuiMsgModel> messageList) {
        this.messageList = messageList;
    }

    public List<String> getMsgList() {
        List<String> msgList = new ArrayList<String>();
        for (GongHuiMsgModel model : messageList) {
            msgList.add(model.getMsg());
        }
        return msgList;
    }

    public List<Long> getMsgTimeList() {
        List<Long> msgTimeList = new ArrayList<Long>();
        for (GongHuiMsgModel model : messageList) {
            msgTimeList.add(model.getMsgTime());
        }
        return msgTimeList;
    }

    public List<GongHuiMemberModel> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<GongHuiMemberModel> memberList) {
        this.memberList = memberList;
    }

    public List<GongHuiFubenModel> getOpenFubenModels() {
        return openFubenModels;
    }

    public void setOpenFubenModels(List<GongHuiFubenModel> openFubenModels) {
        this.openFubenModels = openFubenModels;
    }

    public Map<Integer, Long> getOpenFubenData() {
        if (openFubenData == null) {
            openFubenData = new HashMap<Integer, Long>();
        }
        return openFubenData;
    }

    public void setOpenFubenData(Map<Integer, Long> openSceneData) {
        this.openFubenData = openSceneData;
    }

    public boolean replaceMaxSpeed(int fbId, long completionTime) {
        getOpenFubenData();
        if (openFubenData.containsKey(fbId)) {
            if (completionTime < openFubenData.get(fbId)) {
                openFubenData.put(fbId, completionTime);
                return true;
            }
        } else {
            openFubenData.put(fbId, completionTime);
            return true;
        }
        return false;
    }

    public GongHuiMemberModel getMember(final String avatarId) {
        GongHuiMemberModel ret = (GongHuiMemberModel) CollectionUtils.find(memberList, new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                return ((GongHuiMemberModel) object).getAvatarId().equals(avatarId);
            }
        });
        return ret;
    }

    public GongHuiFubenModel getFuben(final int fbId) {
        GongHuiFubenModel ret = (GongHuiFubenModel) CollectionUtils.find(openFubenModels, new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                return ((GongHuiFubenModel) object).getFbId() == fbId;
            }
        });
        return ret;
    }

    public void incrGonghuiJinzhuan(long incr) {
        this.setGonghuiJinzhuan(GongUtils.adjustNumberInRange(getGonghuiJinzhuan() + incr,
                SysConstData.data.getLong("PAIXI_MAX_NEG_JINZHUAN", 0l), Long.MAX_VALUE));
    }

    public void incrLevel(int incr) {
        this.setLevel(GongUtils.adjustNumberInRange(getLevel() + incr, 0, LeagueData.data.size()));
    }

    public List<GongHuiMissionModel> getMissionList() {
        return missionList;
    }

    public void setMissionList(List<GongHuiMissionModel> missionList) {
        this.missionList = missionList;
    }

    public long getLastMissionRefreshTime() {
        return lastMissionRefreshTime;
    }

    public void setLastMissionRefreshTime(long lastMissionRefreshTime) {
        this.lastMissionRefreshTime = lastMissionRefreshTime;
    }

    public int getCangshugeLv() {
        return cangshugeLv;
    }

    public void setCangshugeLv(int cangshugeLv) {
        this.cangshugeLv = cangshugeLv;
    }

    public long getGonghuiJinzhuan() {
        return gonghuiJinzhuan;
    }

    public void setGonghuiJinzhuan(long gonghuiJinzhuan) {
        this.gonghuiJinzhuan = gonghuiJinzhuan;
    }

    public int getTianxianglouLv() {
        return tianxianglouLv;
    }

    public void setTianxianglouLv(int tianxianglouLv) {
        this.tianxianglouLv = tianxianglouLv;
    }

    public int getDuobaogeLv() {
        return duobaogeLv;
    }

    public void setDuobaogeLv(int duobaogeLv) {
        this.duobaogeLv = duobaogeLv;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public List<GongHuiApplyModel> getApplyList() {
        if (applyList == null) {
            applyList = new ArrayList<GongHuiApplyModel>();
        }
        return applyList;
    }

    public void setApplyList(List<GongHuiApplyModel> applyList) {
        this.applyList = applyList;
    }

    public List<String> getDiduiGhList() {
        if (diduiGhList == null) {
            diduiGhList = new ArrayList<String>();
        }
        return diduiGhList;
    }

    public void setDiduiGhList(List<String> diduiGhList) {
        this.diduiGhList = diduiGhList;
    }

    public long getLastMaintainTime() {
        return lastMaintainTime;
    }

    public void setLastMaintainTime(long lastMaintainTime) {
        this.lastMaintainTime = lastMaintainTime;
    }

    public int getDiduiAddCount() {
        return diduiAddCount;
    }

    public void setDiduiAddCount(int diduiAddCount) {
        this.diduiAddCount = diduiAddCount;
    }

    public void incrDiduiAddCount(int incr) {
        this.diduiAddCount += incr;
    }

    public String getCreatorAvatarId() {
        return creatorAvatarId;
    }

    public void setCreatorAvatarId(String creatorAvatarId) {
        this.creatorAvatarId = creatorAvatarId;
    }

    public String getNoticeGhw() {
        return noticeGhw;
    }

    public void setNoticeGhw(String noticeGhw) {
        this.noticeGhw = noticeGhw;
    }

    public int getInvitationCount() {
        return invitationCount;
    }

    public void setInvitationCount(int invitationCount) {
        this.invitationCount = invitationCount;
    }

    public long getInvitationTime() {
        return invitationTime;
    }

    public void setInvitationTime(long invitationTime) {
        this.invitationTime = invitationTime;
    }

    public GangBossModel getGangBossModel() {
        if (gangBossModel == null) {
            gangBossModel = new GangBossModel();
        }
        return gangBossModel;
    }

    public void setGangBossModel(GangBossModel gangBossModel) {
        this.gangBossModel = gangBossModel;
    }


    public GangEndWeekBossModel getEndWeekBossModel() {
        if (endWeekBossModel == null) {
            endWeekBossModel = new GangEndWeekBossModel();
        }
        return endWeekBossModel;
    }

    public int getCamp() {
        return camp;
    }

    public void setCamp(int group) {
        this.camp = group;
    }

    public void setEndWeekBossModel(GangEndWeekBossModel endWeekBossModel) {
        this.endWeekBossModel = endWeekBossModel;
    }

    @Override
    public String toString() {
        return "GongHuiModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", icon=" + icon +
                ", leaderId='" + leaderId + '\'' +
                ", leaderName='" + leaderName + '\'' +
                ", creatorName='" + creatorName + '\'' +
                ", creatorAvatarId='" + creatorAvatarId + '\'' +
                ", level=" + level +
                ", notice='" + notice + '\'' +
                ", noticeGhw='" + noticeGhw + '\'' +
                ", enable=" + enable +
                ", createTime=" + createTime +
                ", dismissTime=" + dismissTime +
                ", lastMissionRefreshTime=" + lastMissionRefreshTime +
                ", openFubenData=" + openFubenData +
                ", timerId=" + timerId +
                ", messageList=" + messageList +
                ", memberList=" + memberList +
                ", missionList=" + missionList +
                ", openFubenModels=" + openFubenModels +
                ", applyList=" + applyList +
                ", gonghuiJinzhuan=" + gonghuiJinzhuan +
                ", cangshugeLv=" + cangshugeLv +
                ", tianxianglouLv=" + tianxianglouLv +
                ", duobaogeLv=" + duobaogeLv +
                ", diduiGhList=" + diduiGhList +
                ", diduiAddCount=" + diduiAddCount +
                ", lastMaintainTime=" + lastMaintainTime +
                ", invitationCount=" + invitationCount +
                ", camp=" + camp +
                ", invitationTime=" + invitationTime +
                ", gangBossModel=" + gangBossModel +
                ", endWeekBossModel=" + endWeekBossModel +
                '}';
    }
}
