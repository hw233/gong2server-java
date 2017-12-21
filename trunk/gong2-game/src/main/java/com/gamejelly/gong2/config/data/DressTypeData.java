package com.gamejelly.gong2.config.data;

import com.gamejelly.gong2.config.data.base.LData;
import com.gamejelly.gong2.config.data.base.LMap;

/**
 * 装扮类型表.csv
 * 编号 = id
 * 名称 = name
 * 等级 = type
 */
public class DressTypeData extends LData {
    public static final LMap data = new LMap();

    static {
        init0();
        init1();
        init2();
        data.setFrozen(true);
    }

    private static void init0() {
data.map(
    1, map("id", 1, "name", "头饰", "type", 1),
    2, map("id", 2, "name", "发型", "type", 2),
    3, map("id", 3, "name", "衣服", "type", 3),
    4, map("id", 4, "name", "耳饰", "type", 4),
    5, map("id", 5, "name", "项链", "type", 5)
);
    }

    private static void init1() {
data.map(
    6, map("id", 6, "name", "手饰", "type", 6),
    7, map("id", 7, "name", "腰饰", "type", 7),
    8, map("id", 8, "name", "背饰", "type", 8),
    9, map("id", 9, "name", "妆容", "type", 9),
    10, map("id", 10, "name", "鞋子", "type", 10)
);
    }

    private static void init2() {
data.map(
    11, map("id", 11, "name", "手持", "type", 11),
    12, map("id", 12, "name", "背景", "type", 12),
    13, map("id", 13, "name", "动物", "type", 13),
    14, map("id", 14, "name", "特殊", "type", 14),
    15, map("id", 15, "name", "场景", "type", 15)
);
    }

}