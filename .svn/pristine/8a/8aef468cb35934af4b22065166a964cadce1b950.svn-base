package com.gamejelly.gong2.config.data;

import com.gamejelly.gong2.config.data.base.LData;
import com.gamejelly.gong2.config.data.base.LMap;

/**
 * 抽将表.csv
 * 抽卡库类型 = id
 * 掉落库ID = dropID
 * 随机次数 = dropNum
 * 备注 = explain
 * 消耗道具 = item
 * 消耗元宝 = gold
 */
public class ServantSelectData extends LData {
    public static final LMap data = new LMap();

    static {
        init0();
        init1();
        data.setFrozen(true);
    }

    private static void init0() {
data.map(
    1, map("id", 1, "dropID", list(2), "dropNum", list(1), "explain", "低级免费，随一次"),
    2, map("id", 2, "dropID", list(2), "dropNum", list(1), "explain", "低级道具，随一次", "item", 32),
    3, map("id", 3, "dropID", list(2), "dropNum", list(1), "explain", "高级免费，随一次"),
    4, map("id", 4, "dropID", list(2), "dropNum", list(1), "explain", "高级道具，随一次", "item", 33),
    5, map("id", 5, "dropID", list(2), "dropNum", list(1), "explain", "高级单抽，随一次", "gold", 300)
);
    }

    private static void init1() {
data.map(
    6, map("id", 6, "dropID", list(2, 2, 2), "dropNum", list(3, 6, 1), "explain", "高级10连抽,3个库", "gold", 2888)
);
    }

}