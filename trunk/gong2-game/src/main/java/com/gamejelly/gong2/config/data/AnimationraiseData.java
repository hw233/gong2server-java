package com.gamejelly.gong2.config.data;

import com.gamejelly.gong2.config.data.base.LData;
import com.gamejelly.gong2.config.data.base.LMap;

/**
 * 动物饲养表.csv
 * 宠物号 = id
 * 宠物名 = name
 * 描述 = buildDesc
 * 宠物类型 = type
 * 动物类型 = kind
 * 繁荣度 = prosperity
 * 经验值 = exp
 * 随机道具奖励 = item
 * 随机道具概率 = stochastic
 * 生产时间 = time
 * 开放等级 = lv
 * 鲜花繁荣度 = prosperity
 * 饲养道具 = itemRaise
 * 购买需要道具 = needItem
 * 需要道具数量 = needItem
 */
public class AnimationraiseData extends LData {
    public static final LMap data = new LMap();

    static {
        init0();
        data.setFrozen(true);
    }

    private static void init0() {
data.map(
    100000, map("id", 100000, "name", "猫", "buildDesc", "喵喵喵", "type", 4, "kind", 1, "prosperity", 180, "exp", 100, "item", list(10000), "stochastic", 0.5f, "time", 5760, "lv", 10, "prosperity", 180, "itemRaise", 20611, "needItem", list(1, 1, 1, 1), "needItem", list(1, 1, 1, 1)),
    100001, map("id", 100001, "name", "狗", "buildDesc", "汪汪汪", "type", 4, "kind", 2, "prosperity", 250, "exp", 200, "item", list(10001), "stochastic", 0.5f, "time", 5760, "lv", 15, "prosperity", 250, "itemRaise", 20612, "needItem", list(2, 2, 2, 2), "needItem", list(2, 2, 2, 2)),
    100002, map("id", 100002, "name", "马", "buildDesc", "不知道怎么叫", "type", 4, "kind", 3, "prosperity", 320, "exp", 300, "item", list(10002), "stochastic", 0.5f, "time", 5760, "lv", 20, "prosperity", 320, "itemRaise", 20613, "needItem", list(5, 5, 5, 5), "needItem", list(5, 5, 5, 5)),
    100003, map("id", 100003, "name", "鹿", "buildDesc", "不知道怎么叫", "type", 4, "kind", 4, "prosperity", 390, "exp", 400, "item", list(10003), "stochastic", 0.5f, "time", 5760, "lv", 25, "prosperity", 390, "itemRaise", 20613, "needItem", list(10, 10, 10, 10), "needItem", list(10, 10, 10, 10))
);
    }

}