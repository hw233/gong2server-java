package com.gamejelly.gong2.config.data;

import com.gamejelly.gong2.config.data.base.LData;
import com.gamejelly.gong2.config.data.base.LMap;

/**
 * 主角升级表.csv
 * 等级 = lv
 * 升级所需经验 = upExp
 * 仆从升级经验 = upServantExp
 * 对应上阵人数 = upCz
 * 升级奖励银两 = rewardMoney
 * 升级奖励道具 = item
 * 道具数量 = num
 */
public class ExpLvData extends LData {
    public static final LMap data = new LMap();

    static {
        init0();
        init1();
        init2();
        init3();
        init4();
        init5();
        init6();
        init7();
        init8();
        init9();
        init10();
        init11();
        init12();
        init13();
        init14();
        init15();
        init16();
        init17();
        init18();
        init19();
        data.setFrozen(true);
    }

    private static void init0() {
data.map(
    1, map("lv", 1, "upExp", 400, "upServantExp", 400, "upCz", 3),
    2, map("lv", 2, "upExp", 500, "upServantExp", 500, "upCz", 3),
    3, map("lv", 3, "upExp", 500, "upServantExp", 500, "upCz", 3),
    4, map("lv", 4, "upExp", 600, "upServantExp", 600, "upCz", 3),
    5, map("lv", 5, "upExp", 800, "upServantExp", 800, "upCz", 3)
);
    }

    private static void init1() {
data.map(
    6, map("lv", 6, "upExp", 900, "upServantExp", 900, "upCz", 3),
    7, map("lv", 7, "upExp", 1200, "upServantExp", 1200, "upCz", 3),
    8, map("lv", 8, "upExp", 1400, "upServantExp", 1400, "upCz", 3),
    9, map("lv", 9, "upExp", 1700, "upServantExp", 1700, "upCz", 3),
    10, map("lv", 10, "upExp", 2000, "upServantExp", 2000, "upCz", 3)
);
    }

    private static void init2() {
data.map(
    11, map("lv", 11, "upExp", 2400, "upServantExp", 2400, "upCz", 3),
    12, map("lv", 12, "upExp", 2800, "upServantExp", 2800, "upCz", 3),
    13, map("lv", 13, "upExp", 3300, "upServantExp", 3300, "upCz", 3),
    14, map("lv", 14, "upExp", 3800, "upServantExp", 3800, "upCz", 3),
    15, map("lv", 15, "upExp", 4300, "upServantExp", 4300, "upCz", 3)
);
    }

    private static void init3() {
data.map(
    16, map("lv", 16, "upExp", 4900, "upServantExp", 4900, "upCz", 4),
    17, map("lv", 17, "upExp", 5400, "upServantExp", 5400, "upCz", 4),
    18, map("lv", 18, "upExp", 6100, "upServantExp", 6100, "upCz", 4),
    19, map("lv", 19, "upExp", 6800, "upServantExp", 6800, "upCz", 4),
    20, map("lv", 20, "upExp", 7500, "upServantExp", 7500, "upCz", 4)
);
    }

    private static void init4() {
data.map(
    21, map("lv", 21, "upExp", 8300, "upServantExp", 8300, "upCz", 4),
    22, map("lv", 22, "upExp", 9100, "upServantExp", 9100, "upCz", 4),
    23, map("lv", 23, "upExp", 10000, "upServantExp", 10000, "upCz", 4),
    24, map("lv", 24, "upExp", 10800, "upServantExp", 10800, "upCz", 4),
    25, map("lv", 25, "upExp", 11700, "upServantExp", 11700, "upCz", 4)
);
    }

    private static void init5() {
data.map(
    26, map("lv", 26, "upExp", 12700, "upServantExp", 12700, "upCz", 4),
    27, map("lv", 27, "upExp", 13700, "upServantExp", 13700, "upCz", 4),
    28, map("lv", 28, "upExp", 14800, "upServantExp", 14800, "upCz", 4),
    29, map("lv", 29, "upExp", 15900, "upServantExp", 15900, "upCz", 4),
    30, map("lv", 30, "upExp", 17000, "upServantExp", 17000, "upCz", 4)
);
    }

    private static void init6() {
data.map(
    31, map("lv", 31, "upExp", 18100, "upServantExp", 18100, "upCz", 4),
    32, map("lv", 32, "upExp", 19300, "upServantExp", 19300, "upCz", 4),
    33, map("lv", 33, "upExp", 20500, "upServantExp", 20500, "upCz", 4),
    34, map("lv", 34, "upExp", 21800, "upServantExp", 21800, "upCz", 4),
    35, map("lv", 35, "upExp", 23200, "upServantExp", 23200, "upCz", 4)
);
    }

    private static void init7() {
data.map(
    36, map("lv", 36, "upExp", 24500, "upServantExp", 24500, "upCz", 4),
    37, map("lv", 37, "upExp", 25900, "upServantExp", 25900, "upCz", 5),
    38, map("lv", 38, "upExp", 27300, "upServantExp", 27300, "upCz", 5),
    39, map("lv", 39, "upExp", 28800, "upServantExp", 28800, "upCz", 5),
    40, map("lv", 40, "upExp", 30300, "upServantExp", 30300, "upCz", 5)
);
    }

    private static void init8() {
data.map(
    41, map("lv", 41, "upExp", 31800, "upServantExp", 31800, "upCz", 5),
    42, map("lv", 42, "upExp", 33400, "upServantExp", 33400, "upCz", 5),
    43, map("lv", 43, "upExp", 35100, "upServantExp", 35100, "upCz", 5),
    44, map("lv", 44, "upExp", 36700, "upServantExp", 36700, "upCz", 5),
    45, map("lv", 45, "upExp", 38500, "upServantExp", 38500, "upCz", 5)
);
    }

    private static void init9() {
data.map(
    46, map("lv", 46, "upExp", 40200, "upServantExp", 40200, "upCz", 5),
    47, map("lv", 47, "upExp", 42000, "upServantExp", 42000, "upCz", 5),
    48, map("lv", 48, "upExp", 43800, "upServantExp", 43800, "upCz", 5),
    49, map("lv", 49, "upExp", 45600, "upServantExp", 45600, "upCz", 5),
    50, map("lv", 50, "upExp", 47500, "upServantExp", 47500, "upCz", 5)
);
    }

    private static void init10() {
data.map(
    51, map("lv", 51, "upExp", 49500, "upServantExp", 49500, "upCz", 5),
    52, map("lv", 52, "upExp", 51500, "upServantExp", 51500, "upCz", 5),
    53, map("lv", 53, "upExp", 53500, "upServantExp", 53500, "upCz", 5),
    54, map("lv", 54, "upExp", 55600, "upServantExp", 55600, "upCz", 5),
    55, map("lv", 55, "upExp", 57700, "upServantExp", 57700, "upCz", 5)
);
    }

    private static void init11() {
data.map(
    56, map("lv", 56, "upExp", 59800, "upServantExp", 59800, "upCz", 5),
    57, map("lv", 57, "upExp", 62000, "upServantExp", 62000, "upCz", 5),
    58, map("lv", 58, "upExp", 64200, "upServantExp", 64200, "upCz", 5),
    59, map("lv", 59, "upExp", 66400, "upServantExp", 66400, "upCz", 5),
    60, map("lv", 60, "upExp", 68700, "upServantExp", 68700, "upCz", 5)
);
    }

    private static void init12() {
data.map(
    61, map("lv", 61, "upExp", 71100, "upServantExp", 71100, "upCz", 6),
    62, map("lv", 62, "upExp", 73400, "upServantExp", 73400, "upCz", 6),
    63, map("lv", 63, "upExp", 75900, "upServantExp", 75900, "upCz", 6),
    64, map("lv", 64, "upExp", 78300, "upServantExp", 78300, "upCz", 6),
    65, map("lv", 65, "upExp", 80800, "upServantExp", 80800, "upCz", 6)
);
    }

    private static void init13() {
data.map(
    66, map("lv", 66, "upExp", 83300, "upServantExp", 83300, "upCz", 6),
    67, map("lv", 67, "upExp", 85900, "upServantExp", 85900, "upCz", 6),
    68, map("lv", 68, "upExp", 88500, "upServantExp", 88500, "upCz", 6),
    69, map("lv", 69, "upExp", 91100, "upServantExp", 91100, "upCz", 6),
    70, map("lv", 70, "upExp", 93800, "upServantExp", 93800, "upCz", 6)
);
    }

    private static void init14() {
data.map(
    71, map("lv", 71, "upExp", 96600, "upServantExp", 96600, "upCz", 6),
    72, map("lv", 72, "upExp", 99300, "upServantExp", 99300, "upCz", 6),
    73, map("lv", 73, "upExp", 102100, "upServantExp", 102100, "upCz", 6),
    74, map("lv", 74, "upExp", 104900, "upServantExp", 104900, "upCz", 6),
    75, map("lv", 75, "upExp", 107800, "upServantExp", 107800, "upCz", 6)
);
    }

    private static void init15() {
data.map(
    76, map("lv", 76, "upExp", 110700, "upServantExp", 110700, "upCz", 6),
    77, map("lv", 77, "upExp", 113700, "upServantExp", 113700, "upCz", 6),
    78, map("lv", 78, "upExp", 116700, "upServantExp", 116700, "upCz", 6),
    79, map("lv", 79, "upExp", 119800, "upServantExp", 119800, "upCz", 6),
    80, map("lv", 80, "upExp", 122800, "upServantExp", 122800, "upCz", 6)
);
    }

    private static void init16() {
data.map(
    81, map("lv", 81, "upExp", 125900, "upServantExp", 125900, "upCz", 6),
    82, map("lv", 82, "upExp", 129100, "upServantExp", 129100, "upCz", 6),
    83, map("lv", 83, "upExp", 132300, "upServantExp", 132300, "upCz", 6),
    84, map("lv", 84, "upExp", 135500, "upServantExp", 135500, "upCz", 6),
    85, map("lv", 85, "upExp", 138800, "upServantExp", 138800, "upCz", 6)
);
    }

    private static void init17() {
data.map(
    86, map("lv", 86, "upExp", 142100, "upServantExp", 142100, "upCz", 6),
    87, map("lv", 87, "upExp", 145500, "upServantExp", 145500, "upCz", 6),
    88, map("lv", 88, "upExp", 148800, "upServantExp", 148800, "upCz", 6),
    89, map("lv", 89, "upExp", 152300, "upServantExp", 152300, "upCz", 6),
    90, map("lv", 90, "upExp", 155700, "upServantExp", 155700, "upCz", 6)
);
    }

    private static void init18() {
data.map(
    91, map("lv", 91, "upExp", 159200, "upServantExp", 159200, "upCz", 6),
    92, map("lv", 92, "upExp", 162800, "upServantExp", 162800, "upCz", 6),
    93, map("lv", 93, "upExp", 166400, "upServantExp", 166400, "upCz", 6),
    94, map("lv", 94, "upExp", 170000, "upServantExp", 170000, "upCz", 6),
    95, map("lv", 95, "upExp", 173700, "upServantExp", 173700, "upCz", 6)
);
    }

    private static void init19() {
data.map(
    96, map("lv", 96, "upExp", 177400, "upServantExp", 177400, "upCz", 6),
    97, map("lv", 97, "upExp", 181100, "upServantExp", 181100, "upCz", 6),
    98, map("lv", 98, "upExp", 184900, "upServantExp", 184900, "upCz", 6),
    99, map("lv", 99, "upExp", 188700, "upServantExp", 188700, "upCz", 6),
    100, map("lv", 100, "upExp", 188700, "upServantExp", 188700, "upCz", 6)
);
    }

}