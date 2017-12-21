package com.gamejelly.gong2.config.data;

import com.gamejelly.gong2.config.data.base.LData;
import com.gamejelly.gong2.config.data.base.LMap;
/**
 * 消耗表.csv
 * 编号 = id
 * 消费重置 = reset
 * 消耗类型 = kind
 * 消耗元宝 = consume
 * 影响值 = effect
 */
public class ConsumeData extends LData {
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
        data.setFrozen(true);
    }

    private static void init0() {
data.map(
    1000, map("id", 1000, "reset", false, "kind", 1, "consume", list(450)),
    1001, map("id", 1001, "reset", false, "kind", 1, "consume", list(100)),
    1002, map("id", 1002, "reset", false, "kind", 1, "consume", list(10)),
    1003, map("id", 1003, "reset", false, "kind", 1, "consume", list(50), "effect", 50),
    1004, map("id", 1004, "reset", false, "kind", 1, "consume", list(100), "effect", 10)
);
    }

    private static void init1() {
data.map(
    1005, map("id", 1005, "reset", false, "kind", 2, "consume", list(0, 50, 50, 50, 100, 100, 100, 200, 300, 400, 500, 1000)),
    1006, map("id", 1006, "reset", false, "kind", 1, "consume", list(20)),
    1007, map("id", 1007, "reset", true, "kind", 2, "consume", list(50, 50, 50, 50, 50, 50)),
    1008, map("id", 1008, "reset", true, "kind", 2, "consume", list(50, 50, 100, 100, 150, 150, 200, 200)),
    1009, map("id", 1009, "reset", false, "kind", 1, "consume", list(10))
);
    }

    private static void init2() {
data.map(
    1010, map("id", 1010, "reset", false, "kind", 1, "consume", list(1, 1, 4, 5, 8, 9, 10, 15, 20)),
    1011, map("id", 1011, "reset", false, "kind", 1, "consume", list(1, 2, 7, 9, 15, 18, 20, 29, 39)),
    1012, map("id", 1012, "reset", false, "kind", 1, "consume", list(2, 4, 13, 17, 29, 36, 39, 58, 79)),
    1013, map("id", 1013, "reset", false, "kind", 1, "consume", list(2, 3, 6, 11, 13, 15, 18, 24, 30, 36, 42, 48)),
    1014, map("id", 1014, "reset", false, "kind", 1, "consume", list(0, 0, 30, 200))
);
    }

    private static void init3() {
data.map(
    1015, map("id", 1015, "reset", true, "kind", 2, "consume", list(0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50)),
    1016, map("id", 1016, "reset", true, "kind", 2, "consume", list(10, 10, 20, 20, 30, 30, 40, 40, 50, 50, 80, 80, 100, 100)),
    1017, map("id", 1017, "reset", false, "kind", 1, "consume", list(10)),
    1018, map("id", 1018, "reset", false, "kind", 1, "consume", list(1)),
    1019, map("id", 1019, "reset", false, "kind", 1, "consume", list(4, 7, 12, 22, 28))
);
    }

    private static void init4() {
data.map(
    1020, map("id", 1020, "reset", false, "kind", 1, "consume", list(0, 0, 98, 980)),
    1021, map("id", 1021, "reset", false, "kind", 1, "consume", list(1, 2, 4, 7, 10)),
    1022, map("id", 1022, "reset", false, "kind", 1, "consume", list(10)),
    1023, map("id", 1023, "reset", false, "kind", 1, "consume", list(200)),
    1024, map("id", 1024, "reset", true, "kind", 2, "consume", list(300, 600))
);
    }

    private static void init5() {
data.map(
    1025, map("id", 1025, "reset", true, "kind", 1, "consume", list(2)),
    1026, map("id", 1026, "reset", true, "kind", 2, "consume", list(10, 10, 20, 20, 30, 30, 40, 40, 50, 50)),
    1027, map("id", 1027, "reset", false, "kind", 1, "consume", list(0, 0, 30, 200)),
    1028, map("id", 1028, "reset", false, "kind", 1, "consume", list(1, 10, 15, 20, 30, 35, 40, 45)),
    1029, map("id", 1029, "reset", false, "kind", 2, "consume", list(0, 50, 50, 50, 100, 100, 100, 200, 300, 400, 500))
);
    }

    private static void init6() {
data.map(
    1030, map("id", 1030, "reset", false, "kind", 1, "consume", 998),
    1031, map("id", 1031, "reset", true, "kind", 2, "consume", list(250, 500, 1000)),
    1032, map("id", 1032, "reset", true, "kind", 2, "consume", list(10, 10, 20, 20, 30, 30, 40, 40, 50, 50, 80, 80, 100, 100)),
    1033, map("id", 1033, "reset", true, "kind", 2, "consume", list(100), "effect", 10),
    1034, map("id", 1034, "reset", false, "kind", 1, "consume", list(10))
);
    }

    private static void init7() {
data.map(
    1035, map("id", 1035, "reset", true, "kind", 2, "consume", list(10, 15, 20, 25, 30, 35, 40, 45, 50)),
    1036, map("id", 1036, "reset", true, "kind", 2, "consume", list(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)),
    1037, map("id", 1037, "reset", false, "kind", 1, "consume", list(10)),
    1038, map("id", 1038, "reset", false, "kind", 1, "consume", list(1)),
    1039, map("id", 1039, "reset", false, "kind", 1, "consume", list(200))
);
    }

    private static void init8() {
data.map(
    1040, map("id", 1040, "reset", false, "kind", 1, "consume", list(10)),
    1041, map("id", 1041, "reset", false, "kind", 1, "consume", list(30), "effect", 100),
    1042, map("id", 1042, "reset", false, "kind", 1, "consume", list(1)),
    1043, map("id", 1043, "reset", false, "kind", 2, "consume", list(0, 100, 100, 100)),
    1044, map("id", 1044, "reset", true, "kind", 2, "consume", list(10, 15, 20, 25, 30, 35, 40, 45, 50))
);
    }

    private static void init9() {
data.map(
    1045, map("id", 1045, "reset", true, "kind", 1, "consume", list(20)),
    1046, map("id", 1046, "reset", false, "kind", 2, "consume", list(400, 1000, 2000)),
    1047, map("id", 1047, "reset", false, "kind", 1, "consume", list(300)),
    1048, map("id", 1048, "reset", false, "kind", 1, "consume", list(100)),
    1049, map("id", 1049, "reset", false, "kind", 1, "consume", list(0, 0, 0, 0, 0, 500, 500, 500, 500, 500))
);
    }

    private static void init10() {
data.map(
    1050, map("id", 1050, "reset", false, "kind", 1, "consume", list(2, 3, 5, 8, 12)),
    1051, map("id", 1051, "reset", false, "kind", 2, "consume", list(0, 0, 0, 0, 0, 0, 0, 0, 50, 50, 50, 50, 100, 100, 100, 100, 200, 200, 200, 200)),
    1052, map("id", 1052, "reset", false, "kind", 2, "consume", list(200, 500, 1500)),
    1053, map("id", 1053, "reset", false, "kind", 2, "consume", list(5, 10, 15, 20))
);
    }

}
