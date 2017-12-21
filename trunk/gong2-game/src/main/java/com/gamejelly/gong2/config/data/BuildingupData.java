package com.gamejelly.gong2.config.data;

import com.gamejelly.gong2.config.data.base.LData;
import com.gamejelly.gong2.config.data.base.LMap;

/**
 * 建筑升级表.csv
 * 建筑编号编号 = id
 * 建筑名 = name
 * 升级后建筑ID = upgradeId
 * 升级建筑需要繁荣度 = glory
 * 升级建筑需要官品 = officerProduct
 * 升级建筑所需银两 = gold
 * 升级建筑木材 = wood
 * 升级耗时 = time
 */
public class BuildingupData extends LData {
    public static final LMap data = new LMap();

    static {
        init0();
        init1();
        init2();
        init3();
        init4();
        data.setFrozen(true);
    }

    private static void init0() {
data.map(
    1000, map("id", 1000, "name", "主城", "upgradeId", 1001, "glory", 1000, "gold", 1000, "wood", 1000, "time", 3000),
    1001, map("id", 1001, "name", "主城", "upgradeId", 1002, "glory", 2000, "gold", 1000, "wood", 1000, "time", 3000),
    1002, map("id", 1002, "name", "主城", "upgradeId", 1003, "glory", 3000, "gold", 1000, "wood", 1000, "time", 3000),
    1003, map("id", 1003, "name", "主城", "upgradeId", 1004, "glory", 4000, "gold", 1000, "wood", 1000, "time", 3000),
    1004, map("id", 1004, "name", "主城", "gold", 1000, "wood", 1000, "time", 3000)
);
    }

    private static void init1() {
data.map(
    1010, map("id", 1010, "name", "鲜花地", "upgradeId", 1011, "glory", 1000, "gold", 1000, "wood", 1000, "time", 3000),
    1011, map("id", 1011, "name", "鲜花地", "upgradeId", 1012, "glory", 2000, "gold", 1000, "wood", 1000, "time", 3000),
    1012, map("id", 1012, "name", "鲜花地", "upgradeId", 1013, "glory", 3000, "gold", 1000, "wood", 1000, "time", 3000),
    1013, map("id", 1013, "name", "鲜花地", "upgradeId", 1014, "glory", 4000, "gold", 1000, "wood", 1000, "time", 3000),
    1014, map("id", 1014, "name", "鲜花地", "gold", 1000, "wood", 1000, "time", 3000)
);
    }

    private static void init2() {
data.map(
    1020, map("id", 1020, "name", "摇钱树", "upgradeId", 1021, "glory", 1000, "gold", 1000, "wood", 1000, "time", 3000),
    1021, map("id", 1021, "name", "摇钱树", "upgradeId", 1022, "glory", 2000, "gold", 1000, "wood", 1000, "time", 3000),
    1022, map("id", 1022, "name", "摇钱树", "upgradeId", 1023, "glory", 3000, "gold", 1000, "wood", 1000, "time", 3000),
    1023, map("id", 1023, "name", "摇钱树", "upgradeId", 1024, "glory", 4000, "gold", 1000, "wood", 1000, "time", 3000),
    1024, map("id", 1024, "name", "摇钱树", "gold", 1000, "wood", 1000, "time", 3000)
);
    }

    private static void init3() {
data.map(
    1030, map("id", 1030, "name", "采石场", "upgradeId", 1031, "glory", 1000, "gold", 1000, "wood", 1000, "time", 3000),
    1031, map("id", 1031, "name", "采石场", "upgradeId", 1032, "glory", 2000, "gold", 1000, "wood", 1000, "time", 3000),
    1032, map("id", 1032, "name", "采石场", "upgradeId", 1033, "glory", 3000, "gold", 1000, "wood", 1000, "time", 3000),
    1033, map("id", 1033, "name", "采石场", "upgradeId", 1034, "glory", 4000, "gold", 1000, "wood", 1000, "time", 3000),
    1034, map("id", 1034, "name", "采石场", "gold", 1000, "wood", 1000, "time", 3000)
);
    }

    private static void init4() {
data.map(
    1040, map("id", 1040, "name", "伐木场", "upgradeId", 1041, "glory", 1000, "gold", 1000, "wood", 1000, "time", 3000),
    1041, map("id", 1041, "name", "伐木场", "upgradeId", 1042, "glory", 2000, "gold", 1000, "wood", 1000, "time", 3000),
    1042, map("id", 1042, "name", "伐木场", "upgradeId", 1043, "glory", 3000, "gold", 1000, "wood", 1000, "time", 3000),
    1043, map("id", 1043, "name", "伐木场", "upgradeId", 1044, "glory", 4000, "gold", 1000, "wood", 1000, "time", 3000),
    1044, map("id", 1044, "name", "伐木场", "gold", 1000, "wood", 1000, "time", 3000)
);
    }

}