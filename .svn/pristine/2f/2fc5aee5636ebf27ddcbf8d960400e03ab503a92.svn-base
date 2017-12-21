package com.gamejelly.gong2.config.data;

import com.gamejelly.gong2.config.data.base.LData;
import com.gamejelly.gong2.config.data.base.LMap;

/**
 * 初始化配置表.csv
 * 初始等级 = lv
 * 初始VIP = vipLv
 * 初始银两 = money
 * 初始元宝 = gold
 * 初始阵法 = initZf
 * 初始场景 = initScene
 * 初始化携带物品 = initItem
 * 物品数量 = itemNum
 */
public class InitData extends LData {
    public static final LMap data = new LMap();

    static {
        init0();
        data.setFrozen(true);
    }

    private static void init0() {
data.map(
    1, map("lv", 1, "vipLv", 0, "money", 100, "gold", 100, "initZf", 40000, "initScene", 10000, "initItem", list(10000, 10001), "itemNum", list(1, 6))
);
    }

}