package com.gamejelly.gong2.config.data;

import com.gamejelly.gong2.config.data.base.LData;
import com.gamejelly.gong2.config.data.base.LMap;

/**
 * 开场战斗配置表.csv
 * 编号 = id
 * 左边阵容 = left
 * 右边阵容 = right
 * 开场前字幕 = beginSubtitle
 * 战斗结束后字幕 = endSubtitle
 */
public class StartFightData extends LData {
    public static final LMap data = new LMap();

    static {
        init0();
        data.setFrozen(true);
    }

    private static void init0() {
data.map(
    101, map("id", 101, "left", 90001, "right", 91001, "beginSubtitle", 101, "endSubtitle", 102)
);
    }

}