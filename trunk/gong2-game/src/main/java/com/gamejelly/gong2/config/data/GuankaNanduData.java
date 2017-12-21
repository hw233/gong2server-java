package com.gamejelly.gong2.config.data;

import com.gamejelly.gong2.config.data.base.LData;
import com.gamejelly.gong2.config.data.base.LMap;

/**
 * 关卡难度表.csv
 * 关卡难度 = nandu
 * 洞府经验系数 = dfNdFactor
 */
public class GuankaNanduData extends LData {
    public static final LMap data = new LMap();

    static {
        init0();
        data.setFrozen(true);
    }

    private static void init0() {
data.map(
    1, map("nandu", 1, "dfNdFactor", 1.0f),
    2, map("nandu", 2, "dfNdFactor", 1.5f)
);
    }

}