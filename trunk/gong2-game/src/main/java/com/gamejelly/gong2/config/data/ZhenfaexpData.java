package com.gamejelly.gong2.config.data;

import com.gamejelly.gong2.config.data.base.LData;
import com.gamejelly.gong2.config.data.base.LMap;

/**
 * 阵法经验表.csv
 * 技能书道具 = zfBookId
 * 阵法编号 = id
 * 技能书经验 = itemExp
 * 同类型技能书经验 = itemSameExp
 */
public class ZhenfaexpData extends LData {
    public static final LMap data = new LMap();

    static {
        init0();
        init1();
        data.setFrozen(true);
    }

    private static void init0() {
data.map(
    20505, map("zfBookId", 20505, "id", 0, "itemExp", 100, "itemSameExp", 0),
    51000, map("zfBookId", 51000, "id", 50000, "itemExp", 1000, "itemSameExp", 2000),
    51001, map("zfBookId", 51001, "id", 50001, "itemExp", 1000, "itemSameExp", 2000),
    51002, map("zfBookId", 51002, "id", 50002, "itemExp", 1000, "itemSameExp", 2000),
    51003, map("zfBookId", 51003, "id", 50003, "itemExp", 1000, "itemSameExp", 2000)
);
    }

    private static void init1() {
data.map(
    51004, map("zfBookId", 51004, "id", 50004, "itemExp", 1000, "itemSameExp", 2000),
    51005, map("zfBookId", 51005, "id", 50005, "itemExp", 1000, "itemSameExp", 2000),
    51006, map("zfBookId", 51006, "id", 50006, "itemExp", 1000, "itemSameExp", 2000),
    51007, map("zfBookId", 51007, "id", 50007, "itemExp", 1000, "itemSameExp", 2000)
);
    }

}