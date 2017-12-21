package com.gamejelly.gong2.config.data;

import com.gamejelly.gong2.config.data.base.LData;
import com.gamejelly.gong2.config.data.base.LMap;

/**
 * 系统等级表.csv
 * 编号 = id
 * 开放等级 = openLv
 * 对应引导 = ydId
 */
public class SysLvData extends LData {
    public static final LMap data = new LMap();

    static {
        init0();
        data.setFrozen(true);
    }

    private static void init0() {
data.map(
    101, map("id", 101, "openLv", 1)
);
    }

}