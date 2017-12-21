package com.gamejelly.gong2.config.data;

import com.gamejelly.gong2.config.data.base.LData;
import com.gamejelly.gong2.config.data.base.LMap;

/**
 * 掉落库表.csv
 * 掉落库ID = id
 * 掉落方式 = type
 * 掉落库类型 = kind
 * 掉落道具 = items
 * 掉落数量下限制 = itemNumMin
 * 掉落数量上限 = itemNumMax
 * 掉落权重 = props
 */
public class DropStoreData extends LData {
    public static final LMap data = new LMap();

    static {
        init0();
        data.setFrozen(true);
    }

    private static void init0() {
data.map(
    1, map("id", 1, "type", 1, "kind", 1, "items", list(1, 2), "itemNumMin", list(100, 200), "itemNumMax", list(100, 200), "props", list(5000, 4000)),
    2, map("id", 2, "type", 1, "kind", 2, "items", list(101, 102, 103), "itemNumMin", list(1, 1, 1), "itemNumMax", list(1, 1, 1), "props", list(5000, 5000, 5000)),
    3, map("id", 3, "type", 1, "kind", 3, "items", list(1, 2), "props", list(5000, 5000)),
    4, map("id", 4, "type", 2, "kind", 3, "items", list(1, 2), "props", list(5000, 5000))
);
    }

}