package com.gamejelly.gong2.config.data;

import com.gamejelly.gong2.config.data.base.LData;
import com.gamejelly.gong2.config.data.base.LMap;

/**
 * 阵眼表.csv
 * id = id
 * 等级 = lv
 * 限制等级 = lvlimit
 * 升级需要消耗道具id = itemId
 * 升级需要道具数量 = itemNum
 * 属性id = propId
 * 增加属性 = propValue
 */
public class ZhenyanData extends LData {
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
        data.setFrozen(true);
    }

    private static void init0() {
data.map(
    1000, map("id", 1000, "lv", 1, "lvlimit", 50, "itemId", 20309, "itemNum", 1, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 0, 0, 0, 0, 0)),
    1001, map("id", 1001, "lv", 2, "lvlimit", 50, "itemId", 20309, "itemNum", 2, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 0, 0, 0, 0, 0)),
    1002, map("id", 1002, "lv", 3, "lvlimit", 50, "itemId", 20309, "itemNum", 3, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 0, 0, 0, 0, 0)),
    1003, map("id", 1003, "lv", 4, "lvlimit", 50, "itemId", 20309, "itemNum", 4, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 0, 0, 0, 0, 0)),
    1004, map("id", 1004, "lv", 5, "lvlimit", 50, "itemId", 20309, "itemNum", 5, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 0, 0, 0, 0, 0))
);
    }

    private static void init1() {
data.map(
    1005, map("id", 1005, "lv", 6, "lvlimit", 50, "itemId", 20309, "itemNum", 8, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 14, 6, 0, 0, 0)),
    1006, map("id", 1006, "lv", 7, "lvlimit", 50, "itemId", 20309, "itemNum", 11, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 24, 16, 0, 0, 0)),
    1007, map("id", 1007, "lv", 8, "lvlimit", 50, "itemId", 20309, "itemNum", 14, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 24, 16, 0, 0, 0)),
    1008, map("id", 1008, "lv", 9, "lvlimit", 50, "itemId", 20309, "itemNum", 17, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 24, 16, 0, 0, 0)),
    1009, map("id", 1009, "lv", 10, "lvlimit", 50, "itemId", 20309, "itemNum", 20, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 24, 16, 0, 0, 0))
);
    }

    private static void init2() {
data.map(
    1010, map("id", 1010, "lv", 11, "lvlimit", 60, "itemId", 20309, "itemNum", 27, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 24, 16, 0.005f, 0.005f, 0)),
    1011, map("id", 1011, "lv", 12, "lvlimit", 60, "itemId", 20309, "itemNum", 34, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 24, 16, 0.005f, 0.005f, 0)),
    1012, map("id", 1012, "lv", 13, "lvlimit", 60, "itemId", 20309, "itemNum", 41, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 24, 16, 0.005f, 0.005f, 0)),
    1013, map("id", 1013, "lv", 14, "lvlimit", 60, "itemId", 20309, "itemNum", 48, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 24, 16, 0.005f, 0.005f, 0)),
    1014, map("id", 1014, "lv", 15, "lvlimit", 60, "itemId", 20309, "itemNum", 55, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 24, 16, 0.005f, 0.005f, 0))
);
    }

    private static void init3() {
data.map(
    1015, map("id", 1015, "lv", 16, "lvlimit", 60, "itemId", 20309, "itemNum", 65, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 24, 16, 0.005f, 0.005f, 0.005f)),
    1016, map("id", 1016, "lv", 17, "lvlimit", 60, "itemId", 20309, "itemNum", 75, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 24, 16, 0.005f, 0.005f, 0.005f)),
    1017, map("id", 1017, "lv", 18, "lvlimit", 60, "itemId", 20309, "itemNum", 85, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 24, 16, 0.005f, 0.005f, 0.005f)),
    1018, map("id", 1018, "lv", 19, "lvlimit", 60, "itemId", 20309, "itemNum", 95, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 24, 16, 0.005f, 0.005f, 0.005f)),
    1019, map("id", 1019, "lv", 20, "lvlimit", 60, "itemId", 20309, "itemNum", 105, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 24, 16, 0.005f, 0.005f, 0.005f))
);
    }

    private static void init4() {
data.map(
    1020, map("id", 1020, "lv", 21, "lvlimit", 70, "itemId", 20309, "itemNum", 115, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 24, 16, 0.005f, 0.005f, 0.005f)),
    1021, map("id", 1021, "lv", 22, "lvlimit", 70, "itemId", 20309, "itemNum", 125, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 24, 16, 0.005f, 0.005f, 0.005f)),
    1022, map("id", 1022, "lv", 23, "lvlimit", 70, "itemId", 20309, "itemNum", 135, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 24, 16, 0.005f, 0.005f, 0.005f)),
    1023, map("id", 1023, "lv", 24, "lvlimit", 70, "itemId", 20309, "itemNum", 145, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 24, 16, 0.005f, 0.005f, 0.005f)),
    1024, map("id", 1024, "lv", 25, "lvlimit", 70, "itemId", 20309, "itemNum", 155, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 24, 16, 0.005f, 0.005f, 0.005f))
);
    }

    private static void init5() {
data.map(
    1025, map("id", 1025, "lv", 26, "lvlimit", 80, "itemId", 20309, "itemNum", 165, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 24, 16, 0.005f, 0.005f, 0.005f)),
    1026, map("id", 1026, "lv", 27, "lvlimit", 80, "itemId", 20309, "itemNum", 175, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 24, 16, 0.005f, 0.005f, 0.005f)),
    1027, map("id", 1027, "lv", 28, "lvlimit", 80, "itemId", 20309, "itemNum", 185, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 24, 16, 0.005f, 0.005f, 0.005f)),
    1028, map("id", 1028, "lv", 29, "lvlimit", 80, "itemId", 20309, "itemNum", 195, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 24, 16, 0.005f, 0.005f, 0.005f)),
    1029, map("id", 1029, "lv", 30, "lvlimit", 80, "itemId", 20309, "itemNum", 205, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 24, 16, 0.005f, 0.005f, 0.005f))
);
    }

    private static void init6() {
data.map(
    1030, map("id", 1030, "lv", 31, "lvlimit", 90, "itemId", 20309, "itemNum", 215, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 24, 16, 0.005f, 0.005f, 0.005f)),
    1031, map("id", 1031, "lv", 32, "lvlimit", 90, "itemId", 20309, "itemNum", 225, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 24, 16, 0.005f, 0.005f, 0.005f)),
    1032, map("id", 1032, "lv", 33, "lvlimit", 90, "itemId", 20309, "itemNum", 235, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 24, 16, 0.005f, 0.005f, 0.005f)),
    1033, map("id", 1033, "lv", 34, "lvlimit", 90, "itemId", 20309, "itemNum", 245, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 24, 16, 0.005f, 0.005f, 0.005f)),
    1034, map("id", 1034, "lv", 35, "lvlimit", 90, "itemId", 20309, "itemNum", 255, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 24, 16, 0.005f, 0.005f, 0.005f))
);
    }

    private static void init7() {
data.map(
    1035, map("id", 1035, "lv", 36, "lvlimit", 100, "itemId", 20309, "itemNum", 265, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 24, 16, 0.005f, 0.005f, 0.005f)),
    1036, map("id", 1036, "lv", 37, "lvlimit", 100, "itemId", 20309, "itemNum", 275, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 24, 16, 0.005f, 0.005f, 0.005f)),
    1037, map("id", 1037, "lv", 38, "lvlimit", 100, "itemId", 20309, "itemNum", 285, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 24, 16, 0.005f, 0.005f, 0.005f)),
    1038, map("id", 1038, "lv", 39, "lvlimit", 100, "itemId", 20309, "itemNum", 295, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 24, 16, 0.005f, 0.005f, 0.005f)),
    1039, map("id", 1039, "lv", 40, "lvlimit", 100, "itemId", 20309, "itemNum", 305, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 24, 16, 0.005f, 0.005f, 0.005f))
);
    }

    private static void init8() {
data.map(
    1040, map("id", 1040, "lv", 41, "lvlimit", 110, "itemId", 20309, "itemNum", 315, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 24, 16, 0.005f, 0.005f, 0.005f)),
    1041, map("id", 1041, "lv", 42, "lvlimit", 110, "itemId", 20309, "itemNum", 325, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 24, 16, 0.005f, 0.005f, 0.005f)),
    1042, map("id", 1042, "lv", 43, "lvlimit", 110, "itemId", 20309, "itemNum", 335, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 24, 16, 0.005f, 0.005f, 0.005f)),
    1043, map("id", 1043, "lv", 44, "lvlimit", 110, "itemId", 20309, "itemNum", 345, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 24, 16, 0.005f, 0.005f, 0.005f)),
    1044, map("id", 1044, "lv", 45, "lvlimit", 110, "itemId", 20309, "itemNum", 355, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 24, 16, 0.005f, 0.005f, 0.005f))
);
    }

    private static void init9() {
data.map(
    1045, map("id", 1045, "lv", 46, "lvlimit", 120, "itemId", 20309, "itemNum", 365, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 24, 16, 0.005f, 0.005f, 0.005f)),
    1046, map("id", 1046, "lv", 47, "lvlimit", 120, "itemId", 20309, "itemNum", 375, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 24, 16, 0.005f, 0.005f, 0.005f)),
    1047, map("id", 1047, "lv", 48, "lvlimit", 120, "itemId", 20309, "itemNum", 385, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 24, 16, 0.005f, 0.005f, 0.005f)),
    1048, map("id", 1048, "lv", 49, "lvlimit", 120, "itemId", 20309, "itemNum", 395, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 24, 16, 0.005f, 0.005f, 0.005f)),
    1049, map("id", 1049, "lv", 50, "lvlimit", 120, "itemId", 20309, "itemNum", 405, "propId", list(20021, 24020, 25020, 10021, 10022, 20022), "propValue", list(80, 34, 26, 0.005f, 0.005f, 0.005f))
);
    }

}