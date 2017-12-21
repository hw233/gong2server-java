package com.gamejelly.gong2.utils;

public class GongRpcConstants {


	/**
	 * gms -> admin
	 */
	public static final String ADMIN_REQ_HANDSHAKE = "admin.handshake";
	public static final String ADMIN_REQ_OPEN_SERVER = "admin.openServer";
	public static final String ADMIN_REQ_CLOSE_SERVER = "admin.closeServer";
	public static final String ADMIN_REQ_STOP_SERVER = "admin.stopServer";
	public static final String ADMIN_REQ_MAX_ONLINE = "admin.maxOnline";
	public static final String ADMIN_REQ_FLUSH_DATA = "admin.flushData";
	public static final String ADMIN_REQ_SERVER_CONFIG = "admin.serverConfig";
	public static final String ADMIN_REQ_RUN_SCRIPT = "admin.runScript";
	public static final String ADMIN_REQ_GET_PLAYER_LIST = "admin.getPlayerList";
	public static final String ADMIN_REQ_SEARCH_PLAYER_LIST = "admin.searchPlayerList";
	public static final String ADMIN_REQ_SEND_MAIL = "admin.sendMail";
	public static final String ADMIN_REQ_SEND_REWARD = "admin.sendReward";
	public static final String ADMIN_REQ_KICK_OUT = "admin.kickOut";
	public static final String ADMIN_REQ_FORBID_ACCOUNT = "admin.forbidAccount";
	public static final String ADMIN_REQ_ACTIVE_ACCOUNT = "admin.activeAccount";
	public static final String ADMIN_REQ_STOP_CHAT = "admin.stopChat";
	public static final String ADMIN_REQ_OPEN_CHAT = "admin.openChat";
	public static final String ADMIN_REQ_QUERY_FOR_LIST = "admin.queryForList";
	public static final String ADMIN_REQ_EXECUTE_UPDATE = "admin.executeUpdate";
	public static final String ADMIN_REQ_QUERY_FOR_LIST_TO_GLOBAL = "admin.queryForListToGlobal";
	public static final String ADMIN_REQ_EXECUTE_UPDATE_TO_GLOBAL = "admin.executeUpdateToGlobal";
	public static final String ADMIN_REQ_SEND_ROLL_MSG = "admin.sendRollMsg";
	public static final String ADMIN_REQ_HOT_FIX = "admin.hotfix";
	public static final String ADMIN_REQ_REFRESH_VERSION = "admin.refreshVersion";
	public static final String ADMIN_REQ_RESET_GUIDE = "admin.resetGuide";
	public static final String ADMIN_REQ_FORCE_SYNC_ZST_DATA = "admin.forceSyncZstData";
	public static final String ADMIN_REQ_SEND_HONGBAO = "admin.sendHongbao";
	public static final String ADMIN_REQ_JIN_YAN = "admin.jinyan";
	public static final String ADMIN_REQ_CHANGE_NAME = "admin.changename";
	public static final String ADMIN_REQ_CHANGE_AVATAR = "admin.changeavatar";
	public static final String ADMIN_REQ_STOP_JIN_YAN = "admin.stopJinyan";
	public static final String ADMIN_REQ_ZHUANPAN_EDIT = "admin.zhuanpanEdit";

	/**
	 * admin->gms
	 */
	public static final String ADMIN_GMS_RES_ON_SAY = "gms.onSay";

	/**
	 * login->admin
	 */
	public static final String ADMIN_LOGIN_REQ_PAY_ADD_ITEM = "admin.login.payAddItem";
	public static final String ADMIN_LOGIN_REQ_PAY_AMOUNT_ADD_ITEM = "admin.login.payAmountAddItem";
	public static final String ADMIN_LOGIN_REQ_COUPON_ADD_ITEM = "admin.login.couponAddItem";
	public static final String ADMIN_LOGIN_REQ_OUTTER_ADD_ITEM = "admin.login.outterAddItem";
	public static final String ADMIN_LOGIN_REQ_OUTTER_SUB_ITEM = "admin.login.outterSubItem";
	public static final String ADMIN_LOGIN_REQ_OUTTER_GET_USER = "admin.login.outterGetUser";
	public static final String ADMIN_LOGIN_REQ_OUTTER_GET_SERVER_INFO = "admin.login.outterGetServerInfo";
	public static final String ADMIN_LOGIN_REQ_SHARE_ADD_ITEM = "admin.login.shareAddItem";







	/**
	 * response
	 */
	public static final String RES_COMMON_NOT_LOGIN = "c.not_login";
	public static final String RES_COMMON_VERSION_ERROR = "c.version_error";
	public static final String RES_COMMON_LOGIN_ERROR = "c.login_error";
	public static final String RES_COMMON_SERVER_FORBIDDEN = "c.server_forbidden";
	public static final String RES_COMMON_SERVER_REFUSE = "c.server_refuse";
	public static final String RES_COMMON_SERVER_BUSY = "c.server_busy";
	public static final String RES_COMMON_READY = "c.ready";
	public static final String RES_COMMON_CLOSE = "c.close";
	public static final String RES_COMMON_HOTFIX_MD5 = "c.hotfix_md5";
	public static final String RES_COMMON_HOTFIX_RUN = "c.hotfix_run";
	public static final String RES_COMMON_MSG = "c.msg";

	public static final String RES_USER_NEED_CREATE = "u.need_create";
	public static final String RES_USER_ON_LOGIN = "u.on_login";
	public static final String RES_USER_ON_LOGIN_PRE = "u.on_login_pre";
	public static final String RES_USER_LOGIN_ENEMY_LIST = "u.loginEnemyList";
	public static final String RES_USER_ON_SAY = "u.on_say";
	public static final String RES_USER_AVATAR_CHANGE = "u.avatar_change";
	public static final String RES_USER_ITEM_CHANGE = "u.item_change";
	public static final String RES_USER_BUILDING_CHANGE = "u.building_change";
	public static final String RES_OTHER_BUILDING_CHANGE = "u.other_building_change";
	public static final String RES_USER_SERVANT_CHANGE = "u.servant_change";
	public static final String RES_USER_AVATAR_LV_CHANGE = "u.avatar_lv_change";
	public static final String RES_USER_ENTER_SCENE_RESULT = "u.enter_scene_result";
	public static final String RES_USER_GUANKA_CHANGE = "u.guanka_change";
	public static final String RES_USER_AVATAR_CYCLE_CHANGE = "u.avatar_cycle_change";
	public static final String RES_USER_FIGHT_RESULT = "u.fight_result";




	/**
	 * 帮派-推送
	 */
	public static final String RES_USER_FIGHT_GANG_BOSS = "u.avatarFightGangBoss";//挑战派系Boss推送
	public static final String RES_USER_GH_LOAD_OWNER = "u.loadOwnerGongHui"; // 推送自己的公会信息
	public static final String RES_USER_GH_LOAD_GONGHUI = "u.loadGongHuiData"; // 推送公会列表
	public static final String RES_USER_GH_LOAD_MEMBER = "u.resMemberList"; // 推送公会成员信息
	public static final String RES_USER_GH_LOAD_MESSAGE = "u.resMessageList"; // 推送公会动态信息
	public static final String RES_USER_GH_LOAD_MISSION = "u.resLoadMissionList"; // 推送公会任务
	public static final String RES_USER_CHECK_STONE_QUICKLY = "u.checkStoneQuickly"; // 计算宝石合成需要材料
	public static final String RES_USER_UPGRADE_STONE_QUICKLY = "u.UpgradeStoneQuickly"; // 快速升级宝石
	public static final String RES_USER_GH_LOAD_FUBEN = "u.resFubenDataList";
	public static final String RES_USER_GH_DAMAGE_RANK = "u.resDamageRankList";
	public static final String RES_USER_GH_TIME_RANK = "u.resTimeRankList";
	public static final String RES_USER_GH_FUBEN_RANK = "u.resFubenRankList";
	public static final String RES_USER_GH_GUANKA_INFO = "u.curGuankaInfo";
	public static final String RES_USER_GH_FUBEN_CHANGE = "u.curFubenChange";
	public static final String RES_USER_GH_LOAD_APPLY_LIST = "u.loadApplyList"; // 推送公会申请信息
	public static final String RES_USER_TISHI_FLAG_CHANGE = "u.tishi_flag_change";



	/**
	 * request
	 */
	public static final String REQ_USER_LOGIN = "u.login";
	public static final String REQ_USER_LOGOUT = "u.logout";
	public static final String REQ_USER_CREATE = "u.create";
	public static final String RES_USER_GET_ROLE_DATA_LIST = "u.getRoleDataList"; // 获取角色列表
	public static final String REQ_USER_SAY = "u.say";
	public static final String REQ_USER_CHANGE_NAME = "u.changeName";//更改名字
	public static final String REQ_USER_CHANGE_DESCRIBE = "u.changeDescribe";//修改备注
	public static final String REQ_USER_CHANGE_QIANMIN = "u.changeQianming";//更改签名
	public static final String REQ_USER_FIGHT_GUANKA = "u.fight_guanka";
	public static final String REQ_USER_CLAIM_SCENE_REWARD = "u.claimSceneReward";
	public static final String REQ_USER_CLAIM_GUANKA_BOX_REWARD = "u.claimGuankaBoxReward";
	public static final String REQ_USER_SERVANT_CHANGE_POS = "u.servantChangePos";//仆从换位置
	public static final String REQ_USER_SERVANT_UPGRADE = "u.servantUpgrade";//仆从升级
	public static final String REQ_USER_SERVANT_ADVANCE = "u.servantAdvance";//仆从进阶
	public static final String REQ_USER_SERVANT_ZHAO_MU = "u.servantZhaoMu";//仆从招募
	

	/**
	 * 帮派-接收
	 */
	public static final String REQ_USER_GH_LOAD = "u.loadGongHui"; // 加载公会，若没有公会则返回查找公会第一页的数据
	public static final String REQ_USER_GH_SEARCH = "u.searchGongHuis"; // 查找公会
	public static final String REQ_USER_GH_LOAD_MEMBER = "u.loadMemberList"; // 加载公会成员
	public static final String REQ_USER_GH_LOAD_MEMBER_BYID = "u.loadMemberListById"; // 根据公会ID加载公会成员
	public static final String REQ_USER_GH_LOAD_MESSAGE = "u.loadMsgList"; // 加载公会动态信息
	public static final String REQ_USER_GH_CREATE = "u.createGongHui"; // 创建公会
	public static final String REQ_USER_GH_JOIN_OR_EXIT = "u.applyJoinOrExit"; // 申请加入或退出公会
	public static final String REQ_USER_GH_JOIN_ALL = "u.applyJoinAll"; // 申请加入工会all
	public static final String REQ_USER_GH_APPLY_CANCEL = "u.applyCancel"; // 取消申请加入公会
	public static final String REQ_USER_GH_MSG_REVIEW = "u.msgReview"; // 消息审查
	public static final String REQ_USER_GH_CHANGE_NOTICE = "u.changeNotice"; // 编辑公告
	public static final String REQ_USER_GH_CHANGE_NOTICE_GHW = "u.changeNoticeGhw"; // 编辑派系战斗公告
	public static final String REQ_USER_GH_CHANGE_POST = "u.changePost"; // 改变职务
	public static final String REQ_USER_GH_DISBAND = "u.disband"; // 解散公会
	public static final String REQ_USER_GH_GET_MISSION_LIST = "u.gonghuiGetMissionList"; // 获取公会任务列表
	public static final String REQ_USER_GH_ACCEPT_MISSION = "u.gonghuiAcceptMission"; // 接公会任务
	public static final String REQ_USER_GH_REFRESH_MISSION = "u.gonghuiRefreshMission"; // 刷新公会任务
	public static final String REQ_USER_GH_SPEEDUP_MISSION = "u.gonghuiSpeedUpMission"; // 加速公会任务
	public static final String REQ_USER_GH_SEND_ALL_MESSAGE = "u.gonghuiSendAllMessage"; // 工会群发邮件
	public static final String REQ_USER_GH_CANCEL_DISBAND = "u.cancelDisband";
	public static final String REQ_USER_GH_FUBEN_LOAD_ = "u.loadFuben";
	public static final String REQ_USER_GH_FUBEN_OPEN = "u.openFuben";
	public static final String REQ_USER_GH_FUBEN_ENTER = "u.enterFuben";
	public static final String REQ_USER_GH_FUBEN_FIGHT = "u.doFightFuben";
	public static final String REQ_USER_GH_DAMAGE_RANK = "u.getDamageRank";
	public static final String REQ_USER_GH_TIME_RANK = "u.getTimeRank";
	public static final String REQ_USER_GH_FUBEN_RANK = "u.getFubenRank";
	public static final String REQ_USER_GH_UP_GONGHUI = "u.upGonghui";
	public static final String REQ_USER_GH_DONATE_JINZHUAN = "u.donateJinzhuan";
	public static final String REQ_USER_GH_UP_TIANXIANGGE = "u.upTianxianglou";
	public static final String REQ_USER_GH_UP_DUOBAOGE = "u.upDuobaoge";
	public static final String REQ_USER_GH_UP_CANGSHUGE = "u.upCangshuge";
	public static final String REQ_USER_GH_UP_PLAYER_KEJI = "u.upPlayerKeji";
	public static final String REQ_USER_GH_CLAIM_GONGHUI_FENHONG = "u.claimGonghuiFenhong";
	public static final String REQ_USER_GH_LOAD_APPLY_LIST = "u.loadApplyList"; // 加载公会申请信息
	public static final String REQ_USER_GH_CLEAR_APPLY_LIST = "u.clearApplyList"; // 清空公会申请信息
	public static final String REQ_USER_GH_GET_DIDUI_LIST = "u.getDiduiList"; // 加载敌对公会信息
	public static final String REQ_USER_GH_GET_DIDUI_MEMBER_LIST = "u.getDiduiMemberList"; // 加载敌对公会成员信息
	public static final String REQ_USER_GH_OPEN_DIDUI = "u.openDidui"; // 开启敌对
	public static final String REQ_USER_GH_RESET_DIDUI = "u.resetDidui"; // 重置敌对
	public static final String REQ_USER_GH_SPEED_UP_EXIT_CD_TIME = "u.ghSpeedUpCdTime"; // 加速派系退出时间
	public static final String REQ_USER_ACCEPT_MAIN_MISSION = "u.acceptMainMission"; // 接主线任务
	public static final String REQ_USER_CLIENT_MISSION = "u.clientMission"; // 客户端通知任务
	public static final String REQ_USER_TIAO_GUO_MISSION = "u.tiaoGuoMission"; // 跳过主线任务
	public static final String REQ_USER_LOAD_OTHER_PLAYER = "u.loadOtherPlayer"; // 获取其它玩家的家园信息
	public static final String REQ_USER_LOAD_OTHER_PLAYER_INFO = "u.loadOtherPlayerInfo"; // 获取其它玩家的信息
	public static final String REQ_USER_LOAD_OTHER_PLAYER_EQUIP = "u.loadOtherPlayerEquip"; // 获取其它玩家的装备信息



	/**
	 * 聊天
	 */
	public static final String REQ_USER_OPEN_MSG_WINDOW = "u.openMsgWindow"; // 打开聊天窗口
	public static final String REQ_USER_CLOSE_MSG_WINDOW = "u.closeMsgWindow"; // 打开聊天窗口
	public static final String REQ_USER_SL_SAY = "u.slSay";
	public static final String REQ_USER_GET_SL_SY = "u.getSlSay";


	/**
	 * 好友
	 */
	public static final String REQ_USER_SEND_JIEJIAO_MSG = "u.sendJiejiaoMsg";
	public static final String REQ_USER_SEND_JIEJIAO_MSG_BY_NAME = "u.sendJiejiaoMsgByName";
	public static final String REQ_USER_ACCEPT_JIEJIAO = "u.acceptJiejiao";
	public static final String REQ_USER_REJECT_JIEJIAO = "u.rejectJiejiao";
	public static final String REQ_USER_SEARCH_PLAYER = "u.searchPlayer";
	public static final String REQ_USER_DEFAULT_SEARCH_PLAYER = "u.defaultSearchPlayer";
	public static final String REQ_USER_GET_FRIEND_LIST = "u.getFriendList";
	public static final String REQ_USER_GET_ENEMY_LIST = "u.getEnemyList";
	public static final String REQ_USER_GET_JIEJIAO_LIST = "u.getJiejiaoList";
	public static final String REQ_USER_REMOVE_FRIEND = "u.removeFriend";
	public static final String REQ_USER_REMOVE_ENEMY = "u.removeEnemy";
	public static final String REQ_USER_ADD_ENEMY = "u.addEnemy";
	public static final String REQ_USER_GET_RECENT_CONTACT = "u.getRecentContact"; // 获取最近联系人
	public static final String REQ_USER_GET_SIMPLE_PLAYER_INFO = "u.getSimplePlayerInfo"; // 获取某人简单信息

	/**
	 * building
	 */
	public static final String REQ_BUILDING_INCR_BUILDING_PROSPERITY = "u.buildingIncrBuildingProsperity";
	public static final String REQ_BUILDING_CREATE_BUILDING = "u.buildingCreateBuilding";
	public static final String REQ_BUILDING_BUILDING_UPGRADE = "u.buildingBuildingUpgrade";
	public static final String REQ_BUILDING_MOVE_BUILDING = "u.buildingMoveBuilding";
	public static final String REQ_BUILDING_FLIP_BUILDING = "u.buildingFlipBuilding";
	public static final String REQ_BUILDING_REMOVE_BUILDING = "u.buildingRemoveBuilding";
	public static final String REQ_BUILDING_OPEN_MAKE_MATERIAL_NUMBER = "u.buildingOpenMakeMaterialNumber";

}
