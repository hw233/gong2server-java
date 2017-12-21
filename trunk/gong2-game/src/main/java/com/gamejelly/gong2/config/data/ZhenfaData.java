package com.gamejelly.gong2.config.data;

import com.gamejelly.gong2.config.data.base.LData;
import com.gamejelly.gong2.config.data.base.LMap;

/**
 * 阵法表.csv
 * 阵法编号 = id
 * 排序 = pxId
 * 技能书道具 = zfItemId
 * 开启阵位 = openPosition
 * 伤害加成 = zfDmgIncrease
 * 物理减伤 = zfDefAdd
 * 法术减伤 = zfDpowerAdd
 * 属性提升类型 = propKind
 * 基础属性数值 = propValue
 * 升级所需经验 = zfExp
 * 阵法战力 = zfZl
 */
public class ZhenfaData extends LData {
    public static final LMap data = new LMap();

    static {
        init0();
        data.setFrozen(true);
    }

    private static void init0() {
data.map(
    40000, map("id", 40000, "pxId", 1, "zfItemId", 0, "openPosition", list(1, 2, 3, 4, 5, 6), "zfDmgIncrease", list(list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0)), "zfDefAdd", list(list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0)), "zfDpowerAdd", list(list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0), list(0, 0, 0, 0, 0, 0)), "propKind", list(0, 0, 0, 0, 0, 0), "propValue", list(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), "propKind2", list(0, 0, 0, 0, 0, 0), "propValue2", list(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), "zfExp", list(0), "zfZl", list(0))
);
    }

}