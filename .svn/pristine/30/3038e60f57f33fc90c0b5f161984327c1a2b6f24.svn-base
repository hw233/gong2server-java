package com.gamejelly.gong2.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import com.hadoit.game.common.framework.property.PropertyLoader;
import com.hadoit.game.common.lang.DataUtils;
import com.hadoit.game.common.lang.Pair;
import com.hadoit.game.common.lang.Triple;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;

/**
 * @author bezy 2013-11-22
 */
public class GongConstants {
    public static int SERVER_ID = 1;
    public static String CROSS_SERVER_ID = "";
    private static long OPEN_SERVER_TIME = 0;
    private static boolean IS_TEST = false;
    private static boolean ENABLE_DB_LOG = true;
    private static boolean ENABLE_LOG_MORE = false;
    private static String HOTFIX_FILE = null;
    private static String SERVER_OPR = "";
    private static boolean OPEN_CROSS = false;
    private static boolean OPEN_AOT = false;
    public static int SYSTEM_LV_OPEN_GONGHUI = 107;//系统开发的帮派等级

    static {
        Properties srvProps = PropertyLoader.loadProperties("srv.properties");
        SERVER_ID = PropertyLoader.getIntProperty(srvProps, "config.server_id", 0);
        String openServerDate = PropertyLoader.getStringProperty(srvProps, "config.open_server_date", "");
        if (StringUtils.isNotBlank(openServerDate)) {
            OPEN_SERVER_TIME = DataUtils.parseDateFromText(openServerDate, "yyyy-MM-dd").getTime();
        } else {
            OPEN_SERVER_TIME = GongUtils.getTimeInDay00(new Date(System.currentTimeMillis()));
        }

        Properties configProps = PropertyLoader.loadProperties("config.properties");
        IS_TEST = PropertyLoader.getBooleanProperty(configProps, "config.test", false);
        ENABLE_DB_LOG = PropertyLoader.getBooleanProperty(configProps, "config.server_db_log", true);
        ENABLE_LOG_MORE = PropertyLoader.getBooleanProperty(configProps, "config.server_log_more", true);
        HOTFIX_FILE = PropertyLoader.getStringProperty(configProps, "config.hotfix_file", null);
        SERVER_OPR = PropertyLoader.getStringProperty(configProps, "config.oprGroup", null);

        CROSS_SERVER_ID = "cross$" + SERVER_OPR + "$" + SERVER_ID;
        OPEN_AOT = PropertyLoader.getBooleanProperty(configProps, "config.open_aot", false);
        OPEN_CROSS = PropertyLoader.getBooleanProperty(configProps, "config.open_cross", false);
    }

    public static int getServerId() {
        return SERVER_ID;
    }

    public static String getServerOpr() {
        return SERVER_OPR;
    }

    public static String getCrossServerId() {
        return CROSS_SERVER_ID;
    }

    public static boolean isOpenAot() {
        return OPEN_AOT;
    }

    public static void setOpenAotState(boolean state) {
        OPEN_AOT = state;
        GuardianLogger.info("setOpenAotState OPEN_AOT = ", OPEN_AOT);
    }

    public static boolean validCrossServerId(String crossServerId) {
        return crossServerId != null && crossServerId.startsWith("cross$") && crossServerId.split("\\$").length >= 3;
    }

    public static Pair<String, Integer> parseCrossServerId(String crossServerId) {
        String[] ss = crossServerId.split("\\$");
        return Pair.newInstance(ss[1], Integer.parseInt(ss[2]));
    }

    public static long getOpenServerTime() {
        return OPEN_SERVER_TIME;
    }

    public static String getOpenServerTimeStr() {
        return DataUtils.formatDate(OPEN_SERVER_TIME, "yyyy-MM-dd");
    }

    public static boolean isTest() {
        return IS_TEST;
    }

    public static boolean enableDbLog() {
        return ENABLE_DB_LOG;
    }

    public static void enableDbLog(boolean enable) {
        ENABLE_DB_LOG = enable;
    }

    public static boolean enableLogMore() {
        return ENABLE_LOG_MORE;
    }

    public static void enableLogMore(boolean enable) {
        ENABLE_LOG_MORE = enable;
    }

    public static String getHotfixFile() {
        return HOTFIX_FILE;
    }

    public static String getCrossServerIdentifier(String crossServerId) {
        return "globalClient-" + crossServerId;
    }

    public static String getCrossPlayerIdentify(String avatarId) {
        return getCrossPlayerIdentify(getCrossServerId(), avatarId);
    }

    public static String getCrossPlayerIdentify(String crossServerId, String avatarId) {
        return crossServerId + "_" + avatarId;
    }

    public static boolean validCrossPlayerIdentify(String identify) {
        return identify != null && identify.split("_").length >= 2;
    }

    public static Pair<String, String> parseCrossPlayerIdentify(String identify) {
        String[] ss = identify.split("_");
        return Pair.newInstance(ss[0], ss[1]);
    }

    public static String getCrossPlayerYbkIdentify(String crossServerId, String avatarId, int ybkTemplateId) {
        return crossServerId + "_" + avatarId + "_" + ybkTemplateId;
    }

    public static boolean validCrossPlayerYbkIdentify(String identify) {
        return identify != null && identify.split("_").length >= 3;
    }

    public static Triple<String, String, Integer> parseCrossPlayerYbkIdentify(String identify) {
        String[] ss = identify.split("_");
        return Triple.newInstance(ss[0], ss[1], Integer.parseInt(ss[2]));
    }

    public static boolean isMyYbkIdentify(String ybkIdentify, String crossServerId, String avatarId) {
        return ybkIdentify.startsWith(crossServerId + "_" + avatarId + "_");
    }

    public static boolean isOpenCross() {
        return OPEN_CROSS;
    }

    public static int getOprGroupInt() {
        if (SERVER_OPR.equals("iosyy")) {
            return 1;
        } else if (SERVER_OPR.equals("anzhuo")) {
            return 2;
        } else if (SERVER_OPR.equals("ios")) {
            return 3;
        } else if (SERVER_OPR.equals("test")) {
            return 4;
        }
        return 0;
    }


    public final static int USER_GROUP_FORBIDDEN = -10;// 禁止
    public final static int USER_GROUP_NORMAL = 0;// 没有权限的普通人
    public final static int USER_GROUP_SUPER = 10;// 没有权限的super人
    public final static int USER_GROUP_GM = 100;// 有管理权限的GM
    public final static int USER_GROUP_DEV = 1000;// 有管理权限的开发团队
    public final static int USER_GROUP_GOD = 10000;// 最高权限


    //ID类型
    public final static int ID_TYPE_AVATAR = 1;
    public final static int ID_TYPE_BUILDING = 2;
    public final static int ID_TYPE_ITEM = 3;
    public final static int ID_TYPE_MONSTER = 4;
    public final static int ID_TYPE_SERVATE = 5;
    public final static int ID_TYPE_GONGHUI = 7;


    /**
     * 最大角色数量
     */
    public static int MAX_ROLE_COUNT = 3;
    public static int CREATE_ROLE_NAME_EXISTS = -1;
    public static int CREATE_ROLE_COUNT_REACH_MAX = -2;

    public final static int SEX_FEMALE = 0; // 女
    public final static int SEX_MALE = 1; // 男


    // 系统消息操作
    public final static int MSG_OP_JIEJIAO_ACCEPT = 1;
    public final static int MSG_OP_JIEJIAO_REJECT = 2;
    public final static int MSG_OP_LINGQU = 3;
    public final static int MSG_OP_LIAOJIE = 4;
    public final static int MSG_OP_PVP_CHAKANZHANDOU = 5;
    public final static int MSG_OP_PVP_ADD_ENEMY = 6;
    public final static int MSG_OP_GONGHUI_ACCEPT = 7;
    public final static int MSG_OP_GONGHUI_REJECT = 8;
    public final static int MSG_OP_PVP_FANJI = 9;
    public final static int MSG_OP_CONSORTIA_INVITATION_ACCEPT = 10;
    public final static int MSG_OP_CONSORTIA_INVITATION_REJECT = 11;
    // 消息主类型
    public final static int MSG_MAJOR_TYPE_SYSTEM = 1;
    public final static int MSG_MAJOR_TYPE_PLAYER = 2;
    public final static int MSG_MAJOR_TYPE_FIGHT = 3;
    // 系统消息子类
    public final static int MSG_CHILD_TYPE_SYSTEM_REWARD = 4; // 系统奖励
    public final static int MSG_CHILD_TYPE_SYSTEM_COMMON = 5; // 系统信息
    public final static int MSG_CHILD_TYPE_SYSTEM_CHEWEI = 6; // 拜访信息


    // 服务端提示
    public final static int MSG_ID_ROLE_NAME_ERROR = 10000;
    public final static int MSG_ID_GOLD_NOT_ENOUGH = 10001;
    public final static int MSG_ID_ROLE_NAME_EXISTS = 10002;
    public final static int MSG_ID_MONEY_NOT_ENOUGH = 10006;

    public final static int MSG_ID_ROLE_REACH_MAX = 10136;

    public final static int MSG_ID_ALREADY_ENEMY = 10019;
    public final static int MSG_ID_ENEMY_REACH_MAX_COUNT = 10020;
    public final static int MSG_ID_DONT_ENEMY_SELF = 10021;
    public final static int MSG_ID_SJ_DONT_JJ = 10022;
    public final static int MSG_ID_SJ_JJ = 10023;
    public final static int MSG_ID_SJ_JJ_REJECT = 10024;
    public final static int MSG_ID_SJ_JJ_ACCEPT = 10025;
    public final static int MSG_ID_CONSORTIA_INVITATION_ACCEPT = 10259;
    public final static int MSG_ID_CONSORTIA_INVITATION_REJECT = 10258;
    public final static int MSG_ID_ALREADY_FRIEND = 10026;
    public final static int MSG_ID_FRIEND_REACH_MAX_COUNT = 10027;
    public final static int MSG_ID_OTHER_FRIEND_REACH_MAX_COUNT = 10028;
    public final static int MSG_ID_ALREADY_SEND_JIEJIAO = 10029;
    public final static int MSG_ID_SUCCESS_SEND_JIEJIAO = 10030;
    public final static int MSG_ID_PLAYER_NOT_EXISTS = 10031;
    public final static int MSG_ID_SAY_PLAYER_OFFLINE = 10032;

    public final static int MSG_ID_NEED_LV = 10016;//等级不够


    //公会服务端提示
    public final static int MSG_ID_GHW_NOT_ENABLE = 20483;
    public final static int MSG_ID_GH_BUILD_SUCCESS = 50002;
    public final static int MSG_ID_GH_JOIN = 60001;
    public final static int MSG_ID_GH_EXIT = 60002;
    public final static int MSG_ID_GH_TIBA = 60003;
    public final static int MSG_ID_GH_BIANZHI = 60004;
    public final static int MSG_ID_GH_LEVEL_UP = 60005;
    public final static int MSG_ID_GH_TRANSFER = 60006;
    public final static int MSG_ID_GH_EXTENDS = 60007;
    public final static int MSG_ID_GH_BUILD_MONEY = 60008;
    public final static int MSG_ID_GH_BUILD_YUANBAO = 60009;
    public final static int MSG_ID_GH_TI_CHU = 60010;
    public final static int MSG_ID_GH_AGREE = 60011;
    public final static int MSG_ID_GH_MEMBER_SHILI_CHANGE = 60012;
    public final static int MSG_ID_GH_NOTICE_DEFAULT = 70001;
    public final static int MSG_ID_GH_ALREADY_JOINED_OTHER = 20374;//角色存在帮派信息
    public final static int MSG_ID_AVATAR_UNBIND_GROUP = 20375;//角色不存在阵营信息
    public final static int MSG_ID_GH_NAME_NOT_NULL = 20389;//帮派名不能为空
    public final static int MSG_ID_GH_NAME_TOO_LONG = 20388;//帮派名太长
    public final static int MSG_ID_GH_NAME_NOT_CONFORM = 20365;//榜排名不合法
    public final static int MSG_ID_GH_NAME_REPETITION = 20400;//帮派名重复
    public final static int MSG_ID_GH_ICON_NOT_NULL = 20366;//帮派icon不能为空

    // 工会邮件
    public final static int MSG_ID_GH_MAIL_APPLY = 30020;
    public final static int MSG_ID_GH_MAIL_ACCEPT = 30021;
    public final static int MSG_ID_GH_MAIL_REJECT = 30022;
    public final static int MSG_ID_GH_MAIL_TICHU = 30023;
    public final static int MSG_ID_GH_MAIL_EXTENDS = 30024;
    public final static int MSG_ID_GH_MAIL_TRANSFER = 30025;
    public final static int MSG_ID_GH_MAIL_DISBAND = 30026;
    public final static int MSG_ID_GH_NO_RESULT = 20362;
    public final static int MSG_ID_GH_WEIFUFEI = 10140;
    public final static int MSG_ID_FB_RANK_REWARD = 30027;
    public final static int MSG_ID_GH_DISBAND = 30028;
    public final static int MSG_ID_GH_CANCEL_DISBAND = 30029;
    public final static int MSG_ID_GH_CHANGE_NOTICE = 30030;
    public final static int MSG_ID_GH_CHANGE_NAME = 30031;


    /**
     * 0-非成员，1-普通成员，2-长老, 3-副盟主，4-盟主
     */
    public static int GONGHUI_POSITION_NO = 0;
    public static int GONGHUI_POSITION_NORMAL = 1;
    public static int GONGHUI_POSITION_ZHANGLAO = 2;
    public static int GONGHUI_POSITION_FUBANGZHU = 3;
    public static int GONGHUI_POSITION_BANGZHU = 4;


    public final static String TISHI_FLAG_MSG_COUNT = "msgCount";


    /**
     * 资源对应的物品ID
     */
    public final static int ITEM_ID_JUE_SE_GAI_MING_KA = 22044; // 角色改名卡
    public final static int ITEM_ID_MONEY = 80001;
    public final static int ITEM_ID_GOLD = 80002;
    public final static int ITEM_ID_EXP = 80003;
    public final static int ITEM_ID_LEIJI_GOLD = 80006; // vip积分



    // 玩家消息子类
    public final static int MSG_CHILD_TYPE_JIEJIAO = 1; // 结交好友
    public final static int MSG_CHILD_TYPE_JIEJIAO_ACCEPT = 2; // 同意结交
    public final static int MSG_CHILD_TYPE_JIEJIAO_REJECT = 3; // 拒绝结交
    public final static int MSG_CHILD_TYPE_SILIAO = 4; // 私聊
    public final static int MSG_CHILD_TYPE_GONGHUI = 5; // 申请加入公会
    public final static int MSG_CHILD_TYPE_GONGHUI_ACCEPT = 6; // 同意申请加入公会
    public final static int MSG_CHILD_TYPE_GONGHUI_REJECT = 7; // 拒绝申请加入公会
    public final static int MSG_CHILD_TYPE_QUN_FA = 8; // 盟主群发
    public final static int MSG_CHILD_TYPE_CONSORTIA_INVITATION = 9; // 邀请好友加入公会
    public final static int MSG_CHILD_TYPE_CONSORTIA_INVITATION_ACCEPT = 10; // 同意好友发起的加入帮派邀请
    public final static int MSG_CHILD_TYPE_CONSORTIA_INVITATION_REJECT = 11; // 同意好友发起的加入帮派邀请


    public final static int MSG_WINDOW_CLOSE = 0; // 聊天窗口关闭
    public final static int MSG_WINDOW_OPEN = 1; // 聊天窗口打开

    public final static int MSG_NOT_READ = 1;// 聊天信息未读
    public final static int MSG_READ = 0;// 聊天信息已读

    // 战斗
    public final static int FIGHT_MAX_ROUND = 10;
    public final static int SIDE_INITIATIVE = 1;
    public final static int SIDE_PASSIVE = 2;

    // fight
    public final static int FIGHT_ACTION_ATTACK = 1;
    // public final static int FIGHT_ACTION_BUFF = 2;
    public final static int FIGHT_ACTION_REMOVE_BUFF = 2;

    public final static int FIGHTER_TYPE_MENTU = 1;
    public final static int FIGHTER_TYPE_MONSTER = 2;


    // 副本星级
    public final static int FUBEN_ONE_STAR = 1;
    public final static int FUBEN_TWO_STAR = 2;
    public final static int FUBEN_THREE_STAR = 3;


    // 战斗类型
    public final static int FIGHT_TYPE_GK = 1;

    public final static int FIGHT_DEFAULT_FABAO_ID_ATK = 70000; // 默认物理攻击id
    public final static int FIGHT_DEFAULT_FABAO_ID_POWER = 70001; // 默认法术攻击id

    // 仆从类型
    public static int SERVANT_TYPE_DEFAULT_0 = 0; // 默认

    // 公式
    public final static int FORMULA_CRI_RATE = 99001; // 暴击
    public final static int FORMULA_PAR_RATE = 99002;// 格挡
    public final static int FORMULA_TOTAL_SERVANT_ZL = 99014;
    public final static int FORMULA_CALC_MONSTER_LV = 99301;


    public final static int MSG_ID_ITEM_NOT_ENOUGH = 10009;

    // 攻击状态
    public final static int CUR_FIGHTER_ATTACK_STATE_NOR = 0; // 普攻
    public final static int CUR_FIGHTER_ATTACK_STATE_CRI = 1;// 暴击
    public final static int CUR_FIGHTER_ATTACK_STATE_PAR = 2;// 格挡

    // 仆从属性类型
    public final static int SERVANT_PROP_TYPE_1 = 1; // 物理
    public final static int SERVANT_PROP_TYPE_2 = 2;// 法术

    // 关系类型
    public final static int RELATION_TYPE_STRANGER = 0; // 陌生人
    public final static int RELATION_TYPE_FRIEND = 1; // 好友
    public final static int RELATION_TYPE_ENEMY = 2; // 仇敌

    public final static String TISHI_FLAG_FRND = "friendCount";

	
	public final static int SEARCH_PLAYER_COUNT = 6;
	
	public final static int SAY_MSG_WORLD = 1; // 世界消息
	public final static int SAY_MSG_PRIVATE = 2; // 私聊
	public final static int SAY_MSG_SYSTEM = 3; // 系统公告
	public final static int SAY_MSG_GONGHUI = 4;// 派系消息
	public final static int SAY_MSG_HORN = 5;// 玩家喇叭
	public final static int SAY_MSG_SYSTEM_MSG = 6;// 系统消息
	
	



    public static int PROSPERITY_TYPE_LINSHI_0 = 0; // 临时的繁荣度
    public static int PROSPERITY_TYPE_GUDING_1 = 1; // 固定的繁荣度


    public final static int CONSUME_TYPE_1 = 1; // 银两
    public final static int CONSUME_TYPE_2 = 2; // 元宝

    //消耗ID
    public final static int CONSUME_ID_CHANGE_NAME = 1000;
    public final static int CONSUME_ID_CRATE_GONGHUI = 1047;


    // 轮询操作
    public final static int OPERATE_CD_TYPE_DAY = 1;
    public final static int OPERATE_CD_TYPE_WEEK = 2;
    public final static int OPERATE_CD_TYPE_FOREVER = 3;
    public final static int OPERATE_CD_TYPE_HOUR = 4;

    public final static int OPERATE_TYPE_DAY_TEST_COUNT = 1;//测试
    public final static int OPERATE_TYPE_DAY_ZHAOMU_LIANG_REN_COUNT = 2;//良人次数
    public final static int OPERATE_TYPE_DAY_ZHAOMU_TIAN_REN_COUNT = 3;//天人次数


    /**
     * OPERATE_TYPE_DAY_LIST 周期性数据每日重制列表
     */
    public final static int[] OPERATE_TYPE_DAY_LIST = {OPERATE_TYPE_DAY_TEST_COUNT, OPERATE_TYPE_DAY_ZHAOMU_LIANG_REN_COUNT, OPERATE_TYPE_DAY_ZHAOMU_TIAN_REN_COUNT};


    /**
     * 阵营
     */
    public final static int[] GROUP_LIST = {1,2};



}



