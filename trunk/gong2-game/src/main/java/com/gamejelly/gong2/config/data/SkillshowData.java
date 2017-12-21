package com.gamejelly.gong2.config.data;

import com.gamejelly.gong2.config.data.base.LData;
import com.gamejelly.gong2.config.data.base.LMap;

/**
 * 技能表现表.csv
 * 技能释放位置 = skillMoveID
 */
public class SkillshowData extends LData {
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
    70000, map("id", 70000, "skillMoveID", 1),
    70001, map("id", 70001, "skillMoveID", 1),
    70002, map("id", 70002, "skillMoveID", 2),
    70003, map("id", 70003, "skillMoveID", 2),
    70004, map("id", 70004, "skillMoveID", 2)
);
    }

    private static void init1() {
data.map(
    70005, map("id", 70005, "skillMoveID", 2),
    70006, map("id", 70006, "skillMoveID", 2),
    70007, map("id", 70007, "skillMoveID", 2),
    70008, map("id", 70008, "skillMoveID", 2),
    70009, map("id", 70009, "skillMoveID", 3)
);
    }

    private static void init2() {
data.map(
    70010, map("id", 70010, "skillMoveID", 3),
    70011, map("id", 70011, "skillMoveID", 3),
    70012, map("id", 70012, "skillMoveID", 3),
    70013, map("id", 70013, "skillMoveID", 3),
    70014, map("id", 70014, "skillMoveID", 3)
);
    }

    private static void init3() {
data.map(
    70015, map("id", 70015, "skillMoveID", 3)
);
    }

}