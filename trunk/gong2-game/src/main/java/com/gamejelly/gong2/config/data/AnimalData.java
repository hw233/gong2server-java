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
 * 对应建筑id = bulidId
 * 购买价格 = needMoney
 * 价格类型 = needType
 * 是否可建造 = canBuild
 * 繁荣度 = prosperity
 * 建筑最大数量 = maxCount
 * 经验值 = exp
 * 随机道具奖励 = item
 * 随机道具概率 = stochastic
 * 生产时间 = time
 * 开放等级 = needLv
 * 宠物繁荣度 = animalProsperity
 * 饲养道具 = itemRaise
 * 购买需要道具 = needItem
 * 需要道具数量 = needNum
 * 动物最大数量 = maxCount
 */
public class AnimalData extends LData {
    public static final LMap data = new LMap();

    static {
        init0();
        init1();
        data.setFrozen(true);
    }

    private static void init0() {
data.map(
    100000, map("id", 100000, "name", "西伯利亚喵", "buildDesc", "可爱的小喵星人，为皇宫添加生机。", "type", 4, "kind", 1, "bulidId", 2034, "canBuild", true, "prosperity", 480, "maxCount", 1, "exp", 100, "item", list(20201, 20202, 20203, 20204, 20205, 26001, 20642, 20643, 20644, 20645, 20646, 20649, 20655, 20660), "stochastic", 0.2f, "time", 3600, "needLv", 10, "animalProsperity", 5, "itemRaise", 20611, "needItem", list(80009, 80010), "needNum", list(8, 3), "maxCount", 1),
    100001, map("id", 100001, "name", "斗牛犬", "buildDesc", "可爱的小汪星人，可以和小喵作伴哦。", "type", 4, "kind", 2, "bulidId", 2035, "canBuild", true, "prosperity", 640, "maxCount", 1, "exp", 100, "item", list(20201, 20202, 20203, 20204, 20205, 26001, 20642, 20643, 20644, 20645, 20646, 20649, 20655, 20660), "stochastic", 0.2f, "time", 3600, "needLv", 10, "animalProsperity", 6, "itemRaise", 20612, "needItem", list(80009, 80010, 80011), "needNum", list(7, 3, 1), "maxCount", 1),
    100002, map("id", 100002, "name", "小马驹", "buildDesc", "小小马驹，俏皮可爱。", "type", 4, "kind", 3, "bulidId", 2026, "canBuild", true, "prosperity", 1460, "maxCount", 1, "exp", 300, "item", list(20201, 20202, 20203, 20204, 20205, 26001, 20642, 20643, 20644, 20645, 20646, 20649, 20655, 20660), "stochastic", 0.3f, "time", 3600, "needLv", 20, "animalProsperity", 15, "itemRaise", 20613, "needItem", list(80009, 80010, 80011), "needNum", list(5, 6, 5), "maxCount", 1),
    100008, map("id", 100008, "name", "奶油斗牛", "buildDesc", "可爱的小汪星人，可以和小喵作伴哦。", "type", 4, "kind", 2, "bulidId", 2035, "canBuild", true, "prosperity", 3680, "maxCount", 1, "exp", 500, "item", list(20201, 20202, 20203, 20204, 20205, 26001, 20642, 20643, 20644, 20645, 20646, 20649, 20655, 20660), "stochastic", 0.3f, "time", 3600, "needLv", 20, "animalProsperity", 37, "itemRaise", 20612, "needItem", list(80009, 80010, 80011, 80012), "needNum", list(13, 9, 3, 3), "maxCount", 1),
    100010, map("id", 100010, "name", "汗血马", "buildDesc", "汗血宝马，不可多得哦！", "type", 4, "kind", 3, "bulidId", 2026, "canBuild", true, "prosperity", 4360, "maxCount", 1, "exp", 1000, "item", list(20201, 20202, 20203, 20204, 20205, 26001, 20642, 20643, 20644, 20645, 20646, 20649, 20655, 20660), "stochastic", 0.4f, "time", 3600, "needLv", 20, "animalProsperity", 44, "itemRaise", 20613, "needItem", list(80009, 80010, 80011, 80012), "needNum", list(5, 5, 5, 4), "maxCount", 1)
);
    }

    private static void init1() {
data.map(
    100011, map("id", 100011, "name", "大狸猫", "buildDesc", "可爱的小喵星人，为皇宫添加生机。", "type", 4, "kind", 1, "bulidId", 2034, "canBuild", true, "prosperity", 2600, "maxCount", 1, "exp", 300, "item", list(20201, 20202, 20203, 20204, 20205, 26001, 20642, 20643, 20644, 20645, 20646, 20649, 20655, 20660), "stochastic", 0.3f, "time", 3600, "needLv", 20, "animalProsperity", 26, "itemRaise", 20611, "needItem", list(80009, 80010, 80011, 80012), "needNum", list(10, 4, 4, 1), "maxCount", 1),
    100012, map("id", 100012, "name", "白龙马", "buildDesc", "通身雪白，北欧混血马，气宇轩昂，非常帅。", "type", 4, "kind", 3, "bulidId", 2026, "canBuild", true, "prosperity", 4900, "maxCount", 1, "exp", 1000, "item", list(20201, 20202, 20203, 20204, 20205, 26001, 20642, 20643, 20644, 20645, 20646, 20649, 20655, 20660), "stochastic", 0.4f, "time", 3600, "needLv", 20, "animalProsperity", 49, "itemRaise", 20613, "needItem", list(80009, 80010, 80011, 80012), "needNum", list(22, 8, 8, 1), "maxCount", 1)
);
    }

}