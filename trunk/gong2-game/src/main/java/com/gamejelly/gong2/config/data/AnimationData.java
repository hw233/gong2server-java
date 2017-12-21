package com.gamejelly.gong2.config.data;

import com.gamejelly.gong2.config.data.base.LData;
import com.gamejelly.gong2.config.data.base.LMap;

/**
 * 动物表.csv
 * 编号 = id
 * 所属建筑编号 = buildId
 */
public class AnimationData extends LData {
    public static final LMap data = new LMap();

    static {
        init0();
        data.setFrozen(true);
    }

    private static void init0() {
data.map(
    1000, map("id", 1000),
    1001, map("id", 1001),
    1002, map("id", 1002),
    1003, map("id", 1003),
    1004, map("id", 1004),
    1005, map("id", 1005),
    1006, map("id", 1006)
);
    }

}