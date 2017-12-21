package com.gamejelly.gong2.config.data;

import com.gamejelly.gong2.config.data.base.LData;
import com.gamejelly.gong2.config.data.base.LMap;

/**
 * 关卡怪物表.csv
 * 关卡编号 = id
 * 关卡怪物 = monsterGroupId
 * 关卡基础表ID = guankaBaseId
 * 推荐战力 = tjzl
 * 门徒经验奖励 = basExp
 * 银两奖励 = basMoney
 * 关卡掉落 = drop
 */
public class GuankaMonsterData extends LData {
    public static final LMap data = new LMap();

    static {
        init0();
        init1();
        init2();
        init3();
        data.setFrozen(true);
    }

    private static void init0() {
data.map(
    10000, map("id", 10000, "monsterGroupId", 10000, "guankaBaseId", 10000, "tjzl", 2000, "basExp", 100, "basMoney", 1000),
    10001, map("id", 10001, "monsterGroupId", 10001, "guankaBaseId", 10001, "tjzl", 2200, "basExp", 101, "basMoney", 1000),
    10002, map("id", 10002, "monsterGroupId", 10002, "guankaBaseId", 10002, "tjzl", 2500, "basExp", 102, "basMoney", 1000),
    10003, map("id", 10003, "monsterGroupId", 10003, "guankaBaseId", 10003, "tjzl", 4100, "basExp", 103, "basMoney", 1000),
    10004, map("id", 10004, "monsterGroupId", 10004, "guankaBaseId", 10004, "tjzl", 4400, "basExp", 104, "basMoney", 1000)
);
    }

    private static void init1() {
data.map(
    10005, map("id", 10005, "monsterGroupId", 10005, "guankaBaseId", 10005, "tjzl", 2000, "basExp", 105, "basMoney", 1000),
    10006, map("id", 10006, "monsterGroupId", 10006, "guankaBaseId", 10006, "tjzl", 2200, "basExp", 105, "basMoney", 1000),
    10007, map("id", 10007, "monsterGroupId", 10007, "guankaBaseId", 10007, "tjzl", 2500, "basExp", 105, "basMoney", 1000),
    10100, map("id", 10100, "monsterGroupId", 10100, "guankaBaseId", 10100, "tjzl", 4400, "basExp", 105, "basMoney", 1000),
    10101, map("id", 10101, "monsterGroupId", 10101, "guankaBaseId", 10101, "tjzl", 4400, "basExp", 105, "basMoney", 1000)
);
    }

    private static void init2() {
data.map(
    10102, map("id", 10102, "monsterGroupId", 10102, "guankaBaseId", 10102, "tjzl", 4400, "basExp", 105, "basMoney", 1000),
    10103, map("id", 10103, "monsterGroupId", 10103, "guankaBaseId", 10103, "tjzl", 4400, "basExp", 105, "basMoney", 1000),
    10104, map("id", 10104, "monsterGroupId", 10104, "guankaBaseId", 10104, "tjzl", 4400, "basExp", 105, "basMoney", 1000),
    10105, map("id", 10105, "monsterGroupId", 10105, "guankaBaseId", 10105, "tjzl", 4400, "basExp", 105, "basMoney", 1000),
    10106, map("id", 10106, "monsterGroupId", 10106, "guankaBaseId", 10106, "tjzl", 4400, "basExp", 105, "basMoney", 1000)
);
    }

    private static void init3() {
data.map(
    10107, map("id", 10107, "monsterGroupId", 10107, "guankaBaseId", 10107, "tjzl", 4400, "basExp", 105, "basMoney", 1000),
    10108, map("id", 10108, "monsterGroupId", 10108, "guankaBaseId", 10108, "tjzl", 4400, "basExp", 105, "basMoney", 1000),
    10109, map("id", 10109, "monsterGroupId", 10109, "guankaBaseId", 10109, "tjzl", 4400, "basExp", 105, "basMoney", 1000),
    10110, map("id", 10110, "monsterGroupId", 10110, "guankaBaseId", 10110, "tjzl", 4400, "basExp", 105, "basMoney", 1000),
    10111, map("id", 10111, "monsterGroupId", 10111, "guankaBaseId", 10111, "tjzl", 4400, "basExp", 105, "basMoney", 1000)
);
    }

}