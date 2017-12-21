package com.gamejelly.gong2.config.data;

import com.gamejelly.gong2.config.data.base.LData;
import com.gamejelly.gong2.config.data.base.LMap;

/**
 * 系统常量表.csv
 */
public class SysConstData extends LData {
    public static final LMap data = new LMap();

    static {
        init0();
        init1();
        init2();
        init3();
        init4();
        init5();
        init6();
        init7();
        init8();
        init9();
        data.setFrozen(true);
    }

    private static void init0() {
data.map(
    "MAX_LV", 160, // 当前版本等级上限
    "GOLD", 999999999, // 元宝
    "MONEY", 999999999, // 银两
    "ROLE_NAME_MIN_LEN", 2, // 名字最短2个字
    "ROLE_NAME_MAX_LEN", 7 // 名字最长7个字
);
    }

    private static void init1() {
data.map(
    "ITEM_KIND_1", 1, // 装备
    "ITEM_KIND_2", 2, // 消耗品
    "ITEM_KIND_3", 3, // 装扮
    "ITEM_KIND_4", 4, // 阵法
    "ITEM_KIND_5", 5 // 宝石
);
    }

    private static void init2() {
data.map(
    "ITEM_KIND_7", 7, // 技能
    "ITEM_KIND_8", 8, // 货币
    "JOB_DEFAULT_SERVANT_0", 1000, // 初始女
    "JOB_DEFAULT_SERVANT_1", 1001, // 初始男
    "AVATAR_FIGHT_INIT_POS", 2 // 角色初始阵法位置
);
    }

    private static void init3() {
data.map(
    "LV_KIND_GD", 1, // 等级固定
    "LV_KIND_SJ", 2, // 等级随机
    "FB_KIND_1", 1, // 主动技能
    "FB_KIND_2", 2, // 被动技能
    "FB_KIND_3", 3 // 普通攻击
);
    }

    private static void init4() {
data.map(
    "SIGNATURE", 50, // 签名
    "MAX_CP", 100, // 怒气值上限值
    "RANGE_KIND_1", 1, // 单体
    "RANGE_KIND_2", 2, // 直线
    "RANGE_KIND_3", 3 // 前排
);
    }

    private static void init5() {
data.map(
    "RANGE_KIND_4", 4, // 后排
    "RANGE_KIND_5", 5, // 随机
    "BUFF_KIND_1", 1, // 眩晕
    "BUFF_KIND_2", 2, // 沉默
    "BUFF_KIND_3", 3 // 无敌
);
    }

    private static void init6() {
data.map(
    "BUFF_KIND_4", 4, // 持续回复
    "BUFF_TGT_1", 1, // 自身
    "BUFF_TGT_2", 2, // 己方全体
    "BUFF_TGT_3", 3, // 受击方
    "BUFF_TGT_4", 4 // 敌方全体
);
    }

    private static void init7() {
data.map(
    "BUFF_TGT_5", 5, // 随机敌方
    "FRONT_OPEN", 70, // 阵法开启等级
    "SERVANT_UPGRADE_NEED_ITEMS", list(3, 4, 5, 6), // 升级丹
    "MAIOR_CITY", list(1000, 1001, 1002, 1003, 1004), // 主城
    "SEC_LIANG_REN_FREE_NUM", 5 // 抽良人每日最大免费次数
);
    }

    private static void init8() {
data.map(
    "SEC_TIAN_REN_FREE_NUM", 1, // 抽天人每日最大免费次数
    "SEC_LIANG_REN_CD_TIME", 300, // 抽良人CD时间(秒)
    "TRANSCRIPT_SIMPLE", 5, // 简单副本体力
    "TRANSCRIPT_DIFF", 10, // 困难副本体力
    "SEC_SERVANT_TYPE_1", 1 // 低级免费，随一次
);
    }

    private static void init9() {
data.map(
    "SEC_SERVANT_TYPE_2", 2, // 低级道具，随一次
    "SEC_SERVANT_TYPE_3", 3, // 高级免费，随一次
    "SEC_SERVANT_TYPE_4", 4, // 高级道具，随一次
    "SEC_SERVANT_TYPE_5", 5, // 高级单抽，随一次
    "SEC_SERVANT_TYPE_6", 6,// 高级10连抽,3个库
        "LMXT_SQLB",20//系统查询的分页数
);
    }

}