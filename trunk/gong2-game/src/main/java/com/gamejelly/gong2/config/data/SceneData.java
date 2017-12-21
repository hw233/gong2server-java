package com.gamejelly.gong2.config.data;

import com.gamejelly.gong2.config.data.base.LData;
import com.gamejelly.gong2.config.data.base.LMap;

/**
 * 场景表.csv
 * 场景编号 = id
 * 章节 = chaptername
 * 场景名称 = name
 * 场景类型 = kind
 * 前置场景 = preScene
 * 后续场景 = nextScene
 * 星数条件 = starCon
 * 满星奖励 = starAward
 */
public class SceneData extends LData {
    public static final LMap data = new LMap();

    static {
        init0();
        data.setFrozen(true);
    }

    private static void init0() {
data.map(
    10000, map("id", 10000, "chaptername", "第一章", "name", "初入王府", "kind", 1, "nextScene", 10001, "starCon", list(list(10, 20, 30), list(10, 20, 30)), "starAward", list(list(10000, 10000, 10000), list(10000, 10000, 10000))),
    10001, map("id", 10001, "chaptername", "第二章", "name", "深宫浮华", "kind", 1, "preScene", 10000, "starCon", list(list(10, 20, 30), list(10, 20, 30)), "starAward", list(list(10000, 10000, 10000), list(10000, 10000, 10000)))
);
    }

}