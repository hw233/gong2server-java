package com.gamejelly.gong2.config.data;

import com.gamejelly.gong2.config.data.base.LData;
import com.gamejelly.gong2.config.data.base.LMap;

/**
 * 礼包表.csv
 * 物品编号 = id
 * 抽取方式 = openKind
 * 所需物品 = needItem
 * 抽取次数 = extractNum
 * 物品组 = groupId
 * 物品组概率 = groupProb
 * 必得银两 = money
 * 必得元宝 = gold
 * VIP积分 = leijiGold
 * 保底次数 = bdNum
 * 保底奖励 = bdAward
 * 保底概率 = bdProb
 * 保底奖励判断 = bdpdAward
 */
public class GiftData extends LData {
    public static final LMap data = new LMap();

    static {
        init0();
        data.setFrozen(true);
    }

    private static void init0() {
data.map(
    60000, map("id", 60000, "openKind", 1, "groupId", list(10001), "groupProb", list(10000), "money", 10000)
);
    }

}