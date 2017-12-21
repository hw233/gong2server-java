package com.gamejelly.gong2.config.data;

import com.gamejelly.gong2.config.data.base.LData;
import com.gamejelly.gong2.config.data.base.LMap;

/**
 * VIP表.csv
 * VIP等级 = id
 * 每日购买挑战次数 = buyFsNum
 * 充值元宝 = payGold
 * 好友数量 = friend
 * VIP跳过时间 = time
 * 挂机次数 = zdGuaji
 * 刷宝次数 = shuabao
 * 探宝重置次数 = tbChongzhi
 * 经验双倍点 = jyDoubleNum
 * 每日领取红包次数 = dayHbNum
 * 掌嘴 = zhangzui
 * 打太监每次获得银两 = daTaijian
 * 一键完成获得银两 = yjMoney
 * 连理枝每天免费次数 = llzNum
 * 试炼本次数 = slRewardNum
 */
public class VipData extends LData {
    public static final LMap data = new LMap();

    static {
        init0();
        init1();
        init2();
        init3();
        data.setFrozen(true);
    }

    private static void init0() {
data.map(
    0, map("id", 0, "buyFsNum", 2, "payGold", 0, "friend", 20, "time", 30, "zdGuaji", 50, "shuabao", 50, "tbChongzhi", 0, "jyDoubleNum", 200, "dayHbNum", 2, "zhangzui", 1, "daTaijian", 120, "llzNum", 10, "slRewardNum", 1),
    1, map("id", 1, "buyFsNum", 2, "payGold", 60, "friend", 35, "time", 20, "zdGuaji", 100, "shuabao", 50, "tbChongzhi", 0, "jyDoubleNum", 275, "dayHbNum", 4, "zhangzui", 1, "daTaijian", 130, "llzNum", 10, "slRewardNum", 1),
    2, map("id", 2, "buyFsNum", 2, "payGold", 300, "friend", 50, "time", 10, "zdGuaji", 150, "shuabao", 75, "tbChongzhi", 0, "jyDoubleNum", 350, "dayHbNum", 6, "zhangzui", 1, "daTaijian", 140, "llzNum", 11, "slRewardNum", 1),
    3, map("id", 3, "buyFsNum", 2, "payGold", 1000, "friend", 100, "time", 0, "zdGuaji", 200, "shuabao", 75, "tbChongzhi", 0, "jyDoubleNum", 425, "dayHbNum", 8, "zhangzui", 2, "daTaijian", 150, "llzNum", 11, "slRewardNum", 2),
    4, map("id", 4, "buyFsNum", 2, "payGold", 2000, "friend", 150, "time", 0, "zdGuaji", 250, "shuabao", 100, "tbChongzhi", 1, "jyDoubleNum", 500, "dayHbNum", 10, "zhangzui", 2, "daTaijian", 160, "llzNum", 12, "slRewardNum", 2)
);
    }

    private static void init1() {
data.map(
    5, map("id", 5, "buyFsNum", 2, "payGold", 5000, "friend", 150, "time", 0, "zdGuaji", 250, "shuabao", 100, "tbChongzhi", 1, "jyDoubleNum", 575, "dayHbNum", 12, "zhangzui", 2, "daTaijian", 170, "llzNum", 12, "slRewardNum", 2),
    6, map("id", 6, "buyFsNum", 2, "payGold", 10000, "friend", 150, "time", 0, "zdGuaji", 300, "shuabao", 125, "tbChongzhi", 1, "jyDoubleNum", 650, "dayHbNum", 14, "zhangzui", 3, "daTaijian", 180, "llzNum", 13, "slRewardNum", 3),
    7, map("id", 7, "buyFsNum", 2, "payGold", 25000, "friend", 150, "time", 0, "zdGuaji", 300, "shuabao", 125, "tbChongzhi", 1, "jyDoubleNum", 725, "dayHbNum", 16, "zhangzui", 3, "daTaijian", 190, "llzNum", 13, "slRewardNum", 3),
    8, map("id", 8, "buyFsNum", 2, "payGold", 50000, "friend", 150, "time", 0, "zdGuaji", 350, "shuabao", 150, "tbChongzhi", 1, "jyDoubleNum", 800, "dayHbNum", 18, "zhangzui", 3, "daTaijian", 200, "yjMoney", 24000, "yjGold", 8, "llzNum", 14, "slRewardNum", 3),
    9, map("id", 9, "buyFsNum", 2, "payGold", 80000, "friend", 150, "time", 0, "zdGuaji", 350, "shuabao", 150, "tbChongzhi", 1, "jyDoubleNum", 800, "dayHbNum", 20, "zhangzui", 4, "daTaijian", 210, "yjMoney", 25200, "yjGold", 8, "llzNum", 14, "slRewardNum", 4)
);
    }

    private static void init2() {
data.map(
    10, map("id", 10, "buyFsNum", 2, "payGold", 120000, "friend", 150, "time", 0, "zdGuaji", 400, "shuabao", 175, "tbChongzhi", 1, "jyDoubleNum", 800, "dayHbNum", 22, "zhangzui", 4, "daTaijian", 220, "yjMoney", 26400, "yjGold", 8, "llzNum", 15, "slRewardNum", 4),
    11, map("id", 11, "buyFsNum", 2, "payGold", 160000, "friend", 150, "time", 0, "zdGuaji", 400, "shuabao", 175, "tbChongzhi", 1, "jyDoubleNum", 800, "dayHbNum", 22, "zhangzui", 4, "daTaijian", 230, "yjMoney", 27600, "yjGold", 9, "llzNum", 15, "slRewardNum", 4),
    12, map("id", 12, "buyFsNum", 2, "payGold", 200000, "friend", 150, "time", 0, "zdGuaji", 400, "shuabao", 175, "tbChongzhi", 1, "jyDoubleNum", 800, "dayHbNum", 22, "zhangzui", 4, "daTaijian", 240, "yjMoney", 28800, "yjGold", 9, "llzNum", 16, "slRewardNum", 5),
    13, map("id", 13, "buyFsNum", 2, "payGold", 250000, "friend", 150, "time", 0, "zdGuaji", 450, "shuabao", 200, "tbChongzhi", 1, "jyDoubleNum", 1000, "dayHbNum", 25, "zhangzui", 5, "daTaijian", 280, "yjMoney", 33600, "yjGold", 10, "llzNum", 18, "slRewardNum", 5),
    14, map("id", 14, "buyFsNum", 2, "payGold", 350000, "friend", 150, "time", 0, "zdGuaji", 500, "shuabao", 225, "tbChongzhi", 1, "jyDoubleNum", 1200, "dayHbNum", 30, "zhangzui", 7, "daTaijian", 320, "yjMoney", 38400, "yjGold", 10, "llzNum", 20, "slRewardNum", 5)
);
    }

    private static void init3() {
data.map(
    15, map("id", 15, "buyFsNum", 2, "payGold", 500000, "friend", 150, "time", 0, "zdGuaji", 600, "shuabao", 250, "tbChongzhi", 1, "jyDoubleNum", 1500, "dayHbNum", 40, "zhangzui", 10, "daTaijian", 400, "yjMoney", 48000, "yjGold", 10, "llzNum", 25, "slRewardNum", 5)
);
    }

}