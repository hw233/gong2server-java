package com.gamejelly.gong2.config.data;

import com.gamejelly.gong2.config.data.base.LData;
import com.gamejelly.gong2.config.data.base.LMap;

/**
 * 阵法相克表.csv
 * 阵法编号 = id
 * 克制阵法id3% = tZfId3
 * 对手属性降低3% = tProp3
 * 克制阵法id6% = tZfId6
 * 对手属性降低6% = tProp6
 * 被克阵法id3% = oZfId3
 * 我方属性降低3% = oProp3
 * 被克阵法id6% = oZfId6
 */
public class ZhenfarestraintData extends LData {
    public static final LMap data = new LMap();

    static {
        init0();
        init1();
        data.setFrozen(true);
    }

    private static void init0() {
data.map(
    50000, map("id", 50000, "tZfId3", list(50008, 50001, 50005), "tProp3", 0.03f, "tZfId6", list(50007), "tProp6", 0.06f, "oZfId3", list(50006, 50002), "oProp3", 0.03f, "oZfId6", list(50003, 50004), "oProp6", 0.06f),
    50001, map("id", 50001, "tZfId3", list(50008, 50007), "tProp3", 0.03f, "tZfId6", list(50005, 50002), "tProp6", 0.06f, "oZfId3", list(50000, 50004), "oProp3", 0.03f, "oZfId6", list(50003, 50006), "oProp6", 0.06f),
    50002, map("id", 50002, "tZfId3", list(50000, 50007), "tProp3", 0.03f, "tZfId6", list(50003, 50006), "tProp6", 0.06f, "oZfId3", list(50004), "oProp3", 0.03f, "oZfId6", list(50005, 50001), "oProp6", 0.06f),
    50003, map("id", 50003, "tZfId3", list(50008, 50004), "tProp3", 0.03f, "tZfId6", list(50000, 50001), "tProp6", 0.06f, "oZfId3", list(50006, 50007), "oProp3", 0.03f, "oZfId6", list(50005, 50002), "oProp6", 0.06f),
    50004, map("id", 50004, "tZfId3", list(50008, 50007, 50002, 50001), "tProp3", 0.03f, "tZfId6", list(50000), "tProp6", 0.06f, "oZfId3", list(50005, 50003), "oProp3", 0.03f, "oZfId6", list(50006), "oProp6", 0.06f)
);
    }

    private static void init1() {
data.map(
    50005, map("id", 50005, "tZfId3", list(50008, 50006, 50004), "tProp3", 0.03f, "tZfId6", list(50003, 50002), "tProp6", 0.06f, "oZfId3", list(50007), "oProp3", 0.03f, "oZfId6", list(50000, 50001), "oProp6", 0.06f),
    50006, map("id", 50006, "tZfId3", list(50008, 50000, 50003), "tProp3", 0.03f, "tZfId6", list(50001, 50004), "tProp6", 0.06f, "oZfId3", list(50005), "oProp3", 0.03f, "oZfId6", list(50007, 50002), "oProp6", 0.06f),
    50007, map("id", 50007, "tZfId3", list(50008, 50005, 50003), "tProp3", 0.03f, "tZfId6", list(50006), "tProp6", 0.06f, "oZfId3", list(50002, 50001, 50004), "oProp3", 0.03f, "oZfId6", list(50000), "oProp6", 0.06f),
    50008, map("id", 50008, "oZfId3", list(50000, 50001, 50002, 50003, 50004, 50005, 50006, 50007), "oProp3", 0.03f)
);
    }

}