package com.gamejelly.gong2.config.data;

import com.gamejelly.gong2.config.data.base.LData;
import com.gamejelly.gong2.config.data.base.LMap;

/**
 * 模型表.csv
 * 编号 = id
 */
public class ModelData extends LData {
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
    1003, map("id", 1003)
);
    }

}