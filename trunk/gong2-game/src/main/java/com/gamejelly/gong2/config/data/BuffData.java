package com.gamejelly.gong2.config.data;

import com.gamejelly.gong2.config.data.base.LData;
import com.gamejelly.gong2.config.data.base.LMap;

/**
 * 状态表.csv
 * 状态编号 = id
 * 战斗内外 = inside
 * 修正属性类型 = propKind
 * 修正量 = propValue
 * 持续回合 = lastTime
 * 生效时机 = effectTime
 * 目标是否跳过回合 = skip
 * 是否需要每回合计算 = every
 */
public class BuffData extends LData {
    public static final LMap data = new LMap();

    static {
        init0();
        data.setFrozen(true);
    }

    private static void init0() {
data.map(
    10000, map("id", 10000, "inside", 0, "propKind", list(10004), "lastTime", 1, "effectTime", 0, "skip", 0, "every", true),
    10001, map("id", 10001, "inside", 0, "lastTime", 1, "effectTime", 0, "skip", 0, "every", true),
    10002, map("id", 10002, "inside", 0, "lastTime", 1, "effectTime", 0, "skip", 0, "every", true),
    10003, map("id", 10003, "inside", 0, "lastTime", 3, "effectTime", 0, "skip", 0, "every", true)
);
    }

}