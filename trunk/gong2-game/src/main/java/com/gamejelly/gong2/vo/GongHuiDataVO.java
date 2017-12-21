package com.gamejelly.gong2.vo;

import com.gamejelly.gong2.gas.entity.AvatarEntity;
import com.gamejelly.gong2.meta.GangBossModel;
import com.gamejelly.gong2.meta.GongHuiMemberModel;
import com.gamejelly.gong2.meta.GongHuiModel;

public class GongHuiDataVO {

	private String id;

	private String name;

	private int icon;// 公会标志

	private String leaderId;// 盟主Id

	private String creatorAvatarId;// 创始人ID

	private String leaderName;// 盟主名字

	private String creatorName;// 创始人名字

	private int level;// 公会等级

	private String notice;// 公告

	private String noticeGhw;// 派系战公告

	private int buildCount;

	private boolean enable;// 公会状态

	private long createTime;// 建立时间

	private long dismissTime;// 解散时间

	private int memberCount;// 正式成员人数

	private int position;// 0-非成员，1-普通成员，2-副盟主，3-盟主

	private int state;// -1-未申请，0-申请，1-成员，2-被踢/被拒，3-退出
	/**
	 * 每日邀请总次数
	 */
	private int invitationCount;

	/**
	 * 最后一次发布邀请时间
	 */
	private long invitationTime;
	/**
	 * 公会金砖
	 */
	private long gonghuiJinzhuan;

	/**
	 * 藏书阁等级
	 */
	private int cangshugeLv;

	/**
	 * 天香楼等级
	 */
	private int tianxianglouLv;

	/**
	 * 多宝阁等级
	 */
	private int duobaogeLv;

	/**
	 * 敌对添加次数
	 */
	private int diduiAddCount;
	
	/**
	 * 派系boss
	 */
	private GangBossModel gangBossModel;

	public GongHuiDataVO(AvatarEntity owner, GongHuiModel ghModel) {
		this.id = ghModel.getId();
		this.name = ghModel.getName();
		this.icon = ghModel.getIcon();
		this.leaderId = ghModel.getLeaderId();
		this.leaderName = ghModel.getLeaderName();
		this.level = ghModel.getLevel();
		this.notice = ghModel.getNotice();
		this.enable = ghModel.isEnable();
		this.createTime = ghModel.getCreateTime();
		this.dismissTime = ghModel.getDismissTime();
		this.noticeGhw = ghModel.getNoticeGhw();
		this.invitationCount = ghModel.getInvitationCount();
		this.invitationTime = ghModel.getInvitationTime();
		this.gangBossModel = ghModel.getGangBossModel();
		setMemberCount(ghModel);

		GongHuiMemberModel member = ghModel.getMember(owner.getAvatarModel().getId());
		if (member == null) {
			this.position = 0;
			this.state = -1;
		} else {
			this.position = member.getPosition();
			this.state = member.getState();
		}
		this.gonghuiJinzhuan = ghModel.getGonghuiJinzhuan();
		this.cangshugeLv = ghModel.getCangshugeLv();
		this.tianxianglouLv = ghModel.getTianxianglouLv();
		this.duobaogeLv = ghModel.getDuobaogeLv();
		this.creatorName = ghModel.getCreatorName();
		this.creatorAvatarId = ghModel.getCreatorAvatarId();
		this.diduiAddCount = ghModel.getDiduiAddCount();
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

	public int getBuildCount() {
		return buildCount;
	}

	public void setBuildCount(int buildCount) {
		this.buildCount = buildCount;
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

	public int getMemberCount() {
		return memberCount;
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

	public void setMemberCount(GongHuiModel ghModel) {
		int count = 0;
		for (GongHuiMemberModel member : ghModel.getMemberList()) {
			if (member.getState() == 1) {
				count++;
			}
		}
		this.memberCount = count;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public long getGonghuiJinzhuan() {
		return gonghuiJinzhuan;
	}

	public void setGonghuiJinzhuan(long gonghuiJinzhuan) {
		this.gonghuiJinzhuan = gonghuiJinzhuan;
	}

	public int getCangshugeLv() {
		return cangshugeLv;
	}

	public void setCangshugeLv(int cangshugeLv) {
		this.cangshugeLv = cangshugeLv;
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

	public int getDiduiAddCount() {
		return diduiAddCount;
	}

	public void setDiduiAddCount(int diduiAddCount) {
		this.diduiAddCount = diduiAddCount;
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

}
