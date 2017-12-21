package com.gamejelly.gong2.config.data;

import com.gamejelly.gong2.config.data.base.LData;
import com.gamejelly.gong2.config.data.base.LMap;

/**
 * 装备解锁.csv
 * 上阵数量 = num
 * 解锁等级 = lv
 */
public class SysEquipData extends LData {
    public static final LMap data = new LMap();

    static {
        init0();
        init1();
        data.setFrozen(true);
    }

    private static void init0() {
data.map(
    1, map("num", 1, "lv", 1),
    2, map("num", 2, "lv", 1),
    3, map("num", 3, "lv", 1),
    4, map("num", 4, "lv", 1),
    5, map("num", 5, "lv", 15)
);
    }

    private static void init1() {
data.map(
    6, map("num", 6, "lv", 30)
);
    }

}