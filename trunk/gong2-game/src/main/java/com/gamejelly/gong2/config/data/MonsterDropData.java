package com.gamejelly.gong2.config.data;

import com.gamejelly.gong2.config.data.base.LData;
import com.gamejelly.gong2.config.data.base.LMap;

/**
 * 怪物掉落表.csv
 * 掉落编号 = id
 * 随机次数 = num
 * 掉落物品 = itemId
 * 掉落数量 = itemNum
 * 掉落几率 = prob
 * 首次必掉 = fstDrop
 * 首次必掉数量 = fstDropNum
 * 保底物品判断 = bdpdItem
 * 保底判断次数 = bdpdNum
 * 保底掉落物品 = bdDropItem
 * 保底掉落物品数量 = bdDropNum
 * 保底掉落概率 = bdDropProb
 */
public class MonsterDropData extends LData {
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
    10000, map("id", 10000),
    10001, map("id", 10001),
    10002, map("id", 10002),
    10003, map("id", 10003, "num", 1),
    10004, map("id", 10004)
);
    }

    private static void init1() {
data.map(
    10005, map("id", 10005, "num", 1),
    10006, map("id", 10006),
    10007, map("id", 10007, "num", 1),
    10100, map("id", 10100),
    10101, map("id", 10101, "num", 1)
);
    }

    private static void init2() {
data.map(
    10102, map("id", 10102),
    10103, map("id", 10103),
    10104, map("id", 10104),
    10105, map("id", 10105, "num", 1),
    10106, map("id", 10106)
);
    }

    private static void init3() {
data.map(
    10107, map("id", 10107),
    10108, map("id", 10108),
    10109, map("id", 10109, "num", 1),
    10110, map("id", 10110),
    10111, map("id", 10111)
);
    }

}