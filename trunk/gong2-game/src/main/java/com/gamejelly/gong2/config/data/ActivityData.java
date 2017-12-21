package com.gamejelly.gong2.config.data;

import com.gamejelly.gong2.config.data.base.LData;
import com.gamejelly.gong2.config.data.base.LMap;

/**
 * 活动表.csv
 * 编号 = id
 * 开启等级 = openLv
 * 开放时间 = openTime
 * 每日免费次数 = freeNum
 */
public class ActivityData extends LData {
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
    1000, map("id", 1000, "openLv", 1, "freeNum", 1),
    1001, map("id", 1001, "openLv", 22, "openTime", list("20:00:00,20:15:00||"), "freeNum", 1),
    1002, map("id", 1002, "openLv", 10, "openTime", list("10:00:00,22:00:00||"), "freeNum", 2),
    1003, map("id", 1003, "openLv", 13, "openTime", list("10:00:00,13:00:00||", "17:00:00,20:00:00||"), "freeNum", 1),
    1004, map("id", 1004, "openLv", 24)
);
    }

    private static void init1() {
data.map(
    1005, map("id", 1005, "openLv", 28, "openTime", list("00:00:00,24:00:00|1,2,3,4|")),
    1006, map("id", 1006, "openLv", 28, "openTime", list("20:30:00,21:00:00|5|")),
    1007, map("id", 1007, "openLv", 9, "openTime", list("00:00:00,17:30:00|3|")),
    1008, map("id", 1008, "openLv", 9, "openTime", list("18:10:00,20:30:00|3|")),
    1009, map("id", 1009, "openLv", 9, "openTime", list("20:00:00,22:30:00|4|"))
);
    }

    private static void init2() {
data.map(
    1010, map("id", 1010, "openLv", 40, "openTime", list("00:00:00,24:00:00||")),
    1011, map("id", 1011, "openLv", 1, "openTime", list("00:00:00,24:00:00||")),
    1012, map("id", 1012, "openLv", 60, "openTime", list("00:00:00,17:30:00|6|")),
    1013, map("id", 1013, "openLv", 60, "openTime", list("18:10:00,20:30:00|6|")),
    1014, map("id", 1014, "openLv", 60, "openTime", list("20:00:00,22:30:00|7|"))
);
    }

    private static void init3() {
data.map(
    1015, map("id", 1015, "openLv", 1, "openTime", list("00:00:00,24:00:00|1,2,3,4,5,6,7|")),
    1016, map("id", 1016, "openLv", 45, "openTime", list("00:00:00,24:00:00|1,2,3,4,5,6,7|")),
    1017, map("id", 1017, "openLv", 18, "openTime", list("00:00:00,24:00:00|5,6|"))
);
    }

}