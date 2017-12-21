package com.gamejelly.gong2.config.data;

import com.gamejelly.gong2.config.data.base.LData;
import com.gamejelly.gong2.config.data.base.LMap;

/**
 * 特权表.csv
 * 编号 = id
 * 名称 = name
 * VIP等级 = vipLv
 * 金额 = gold
 * 道具 = item
 * 道具数量 = itemNum
 * 价格/元宝 = buyMoney
 * 购买次数 = num
 * 是否可使用大喇叭 = horn
 * 大喇叭可使用次数 = hornNum
 * 是否可使用自定义头像 = customIcon
 * 是否可使用自特殊表情 = customFace
 * 描述 = des
 */
public class PrivilegeData extends LData {
    public static final LMap data = new LMap();

    static {
        init0();
        init1();
        init2();
        data.setFrozen(true);
    }

    private static void init0() {
data.map(
    1001, map("id", 1001, "name", "VIP 1", "vipLv", 1, "gold", 60, "item", list(60100), "itemNum", list(2), "buyMoney", 6, "num", 1, "horn", true, "hornNum", 5, "customIcon", false, "customFace", true, "des", "1.[累计充值60元宝]\n2.好友上限35个\n3.战斗跳过时间为20秒\n4.每天整点挂机次数100次\n5.每周获得275点经验双倍点\n6.拥有领取红包机会4次"),
    1002, map("id", 1002, "name", "VIP 2", "vipLv", 2, "gold", 300, "item", list(94054, 94055, 94056, 94057, 94058, 94059), "itemNum", list(2, 2, 2, 2, 2, 2), "buyMoney", 100, "num", 1, "horn", true, "hornNum", 10, "customIcon", false, "customFace", true, "des", "1.[累计充值300元宝]\n2.好友上限50个\n3.战斗跳过时间为10秒\n4.每天整点挂机次数150次\n5.每周获得350点经验双倍点\n6.拥有领取红包机会6次"),
    1003, map("id", 1003, "name", "VIP 3", "vipLv", 3, "gold", 1000, "item", list(90311, 20206), "itemNum", list(1, 5), "buyMoney", 200, "num", 1, "horn", true, "hornNum", 20, "customIcon", true, "customFace", true, "des", "1.[累计充值1000元宝]\n2.好友上限100个\n3.可直接跳过战斗\n4.每天整点挂机次数200次\n5.每天刷宝次数75次\n6.每周获得425点经验双倍点\n7.清宫试炼剩余次数增加到2次\n8.每天拥有2次赐板子的权利\n9.拥有领取红包机会8次"),
    1004, map("id", 1004, "name", "VIP 4", "vipLv", 4, "gold", 2000, "item", list(20046), "itemNum", list(50), "buyMoney", 400, "num", 1, "horn", true, "hornNum", 50, "customIcon", true, "customFace", true, "des", "1.[累计充值2000元宝]\n2.好友上限150个\n3.每天整点挂机次数250次\n4.每天刷宝次数100次\n5.每天探宝增加1次\n6.每周获得500点经验双倍点\n7.拥有领取红包的次数机会10次"),
    1005, map("id", 1005, "name", "VIP 5", "vipLv", 5, "gold", 5000, "item", list(90507, 20632, 22024), "itemNum", list(2, 5, 5), "buyMoney", 800, "num", 1, "horn", true, "hornNum", 100, "customIcon", true, "customFace", true, "des", "1.[累计充值5000元宝]\n2.每周获得575点经验双倍点\n3.拥有领取红包机会12次")
);
    }

    private static void init1() {
data.map(
    1006, map("id", 1006, "name", "VIP 6", "vipLv", 6, "gold", 10000, "item", list(24113), "itemNum", list(100), "buyMoney", 800, "num", 1, "horn", true, "hornNum", 200, "customIcon", true, "customFace", true, "des", "1.[累计充值10000元宝]\n2.每周获得650点经验双倍点\n3.每天整点挂机次数300次\n4.每天刷宝次数125次\n5.清宫试炼剩余次数增加到3次\n6.每天拥有3次赐板子的权利\n7.拥有领取红包的机会14次"),
    1007, map("id", 1007, "name", "VIP 7", "vipLv", 7, "gold", 25000, "item", list(80023), "itemNum", list(25), "buyMoney", 800, "num", 1, "horn", true, "hornNum", 500, "customIcon", true, "customFace", true, "des", "1.[累计充值25000元宝]\n2.每周获得725点经验双倍点\n3.拥有领取红包的机会16次"),
    1008, map("id", 1008, "name", "VIP 8", "vipLv", 8, "gold", 50000, "item", list(94074, 94073), "itemNum", list(40, 50), "buyMoney", 800, "num", 1, "horn", true, "hornNum", 1000, "customIcon", true, "customFace", true, "des", "1.[累计充值50000元宝]\n2.每周获得专属VIPQQ服务 \n  VIPQQ：2881773558\n3.每周获得800点经验双倍点\n4.每天整点挂机次数350次\n5.每天刷宝次数150次\n6.拥有领取红包的机会18次\n7.可使用一键完成赐板子特权"),
    1009, map("id", 1009, "name", "VIP 9", "vipLv", 9, "gold", 80000, "item", list(20633, 25100), "itemNum", list(2, 30), "buyMoney", 800, "num", 1, "horn", true, "hornNum", 1000, "customIcon", true, "customFace", true, "des", "1.[累计充值80000元宝]\n2.道具误删操作、活动奖励未领取恢复特权\n(不包含月卡、季卡领取和游戏内玩法奖励)\n3.清宫试炼剩余次数增加到4次\n4.每天拥有4次赐板子的权利\n5.拥有领取红包的机会20次"),
    1010, map("id", 1010, "name", "VIP 10", "vipLv", 10, "gold", 120000, "item", list(20627, 90316), "itemNum", list(1, 3), "buyMoney", 800, "num", 1, "horn", true, "hornNum", 1000, "customIcon", true, "customFace", true, "des", "1.[累计充值120000元宝]\n2.每天整点挂机次数400次\n3.每天刷宝次数175次\n4.拥有领取红包的机会22次")
);
    }

    private static void init2() {
data.map(
    1011, map("id", 1011, "name", "VIP 11", "vipLv", 11, "gold", 160000, "item", list(94075, 94072), "itemNum", list(5, 150), "buyMoney", 1000, "num", 1, "horn", true, "hornNum", 1000, "customIcon", true, "customFace", true, "des", "1.[累计充值160000元宝]\n2.每天整点挂机次数400次\n3.每天刷宝次数175次\n4.拥有领取红包的机会22次"),
    1012, map("id", 1012, "name", "VIP 12", "vipLv", 12, "gold", 200000, "item", list(20634, 90318), "itemNum", list(1, 2), "buyMoney", 1000, "num", 1, "horn", true, "hornNum", 1000, "customIcon", true, "customFace", true, "des", "1.[累计充值200000元宝]\n2.每周获得800点经验双倍点\n3.每天整点挂机次数400次\n4.每天刷宝次数175次\n5.清宫试炼剩余次数增加到5次\n6.每天拥有4次赐板子的权利\n7.拥有领取红包的机会22次"),
    1013, map("id", 1013, "name", "VIP 13", "vipLv", 13, "gold", 250000, "item", list(20051, 20627), "itemNum", list(2, 1), "buyMoney", 650, "num", 1, "horn", true, "hornNum", 1000, "customIcon", true, "customFace", true, "des", "1.[累计充值250000元宝]\n2.每周获得1000点经验双倍点\n3.每天整点挂机次数450次\n4.每天刷宝次数200次\n5.每天拥有5次赐板子的权利\n6.拥有领取红包的机会25次"),
    1014, map("id", 1014, "name", "VIP 14", "vipLv", 14, "gold", 350000, "item", list(94076, 20627), "itemNum", list(2, 2), "buyMoney", 900, "num", 1, "horn", true, "hornNum", 1000, "customIcon", true, "customFace", true, "des", "1.[累计充值350000元宝]\n2.每周获得1200点经验双倍点\n3.每天整点挂机次数500次\n4.每天刷宝次数225次\n5.每天拥有7次赐板子的权利\n6.拥有领取红包的机会30次"),
    1015, map("id", 1015, "name", "VIP 15", "vipLv", 15, "gold", 500000, "item", list(94077, 90320), "itemNum", list(1, 2), "buyMoney", 1590, "num", 1, "horn", true, "hornNum", 1000, "customIcon", true, "customFace", true, "des", "1.[累计充值500000元宝]\n2.每周获得1500点经验双倍点\n3.每天整点挂机次数600次\n4.每天刷宝次数250次\n5.每天拥有10次赐板子的权利\n6.拥有领取红包的机会40次")
);
    }

}