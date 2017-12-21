package com.gamejelly.gong2.config.data;

import com.gamejelly.gong2.config.data.base.LData;
import com.gamejelly.gong2.config.data.base.LMap;

/**
 * 升级经验惩罚.csv
 * 等级差间距 = id
 * 惩罚比例 = punishExp
 */
public class ExpPunishData extends LData {
    public static final LMap data = new LMap();

    static {
        init0();
        data.setFrozen(true);
    }

    private static void init0() {
data.map(
    list(1, 9), map("id", list(1, 9), "punishExp", -0.5f),
    list(10, 80), map("id", list(10, 80), "punishExp", -0.75f)
);
    }

}