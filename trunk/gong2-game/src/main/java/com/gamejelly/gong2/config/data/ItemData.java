package com.gamejelly.gong2.config.data;

import com.gamejelly.gong2.config.data.base.LData;
import com.gamejelly.gong2.config.data.base.LMap;

/**
 * 物品总表.csv
 * 物品编号 = id
 * 名称 = name
 * 等级 = lv
 * 品质 = quality
 * 类别 = kind
 * 子类型 = subKind
 * 可否叠加 = stack
 * 使用方式 = useMode
 * 战斗掉落是否公告 = systemNotice
 * 收回银币 = money
 * 经验 = exp
 */
public class ItemData extends LData {
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
    1, map("id", 1, "name", "银两", "lv", 1, "quality", 1, "kind", 8, "subKind", 1, "stack", true, "useMode", 0, "money", 1),
    2, map("id", 2, "name", "钻石", "lv", 1, "quality", 1, "kind", 8, "subKind", 1, "stack", true, "useMode", 0, "money", 1),
    3, map("id", 3, "name", "经验丹绿", "lv", 1, "quality", 1, "kind", 2, "subKind", 1, "stack", true, "useMode", 0, "money", 1, "exp", 100),
    4, map("id", 4, "name", "经验丹蓝", "lv", 1, "quality", 2, "kind", 2, "subKind", 1, "stack", true, "useMode", 0, "money", 1, "exp", 300),
    5, map("id", 5, "name", "经验丹紫", "lv", 1, "quality", 3, "kind", 2, "subKind", 1, "stack", true, "useMode", 0, "money", 1, "exp", 500)
);
    }

    private static void init1() {
data.map(
    6, map("id", 6, "name", "经验丹橙", "lv", 1, "quality", 4, "kind", 2, "subKind", 1, "stack", true, "useMode", 0, "money", 1, "exp", 1000),
    7, map("id", 7, "name", "进阶丹", "lv", 1, "quality", 3, "kind", 2, "subKind", 1, "stack", true, "useMode", 0, "money", 1),
    8, map("id", 8, "name", "精炼石", "lv", 1, "quality", 3, "kind", 2, "subKind", 1, "stack", true, "useMode", 0, "money", 1),
    9, map("id", 9, "name", "铜锭", "lv", 1, "quality", 1, "kind", 2, "subKind", 1, "stack", true, "useMode", 0, "money", 1),
    10, map("id", 10, "name", "铁锭", "lv", 1, "quality", 2, "kind", 2, "subKind", 1, "stack", true, "useMode", 0, "money", 1)
);
    }

    private static void init2() {
data.map(
    11, map("id", 11, "name", "银锭", "lv", 1, "quality", 3, "kind", 2, "subKind", 1, "stack", true, "useMode", 0, "money", 1),
    12, map("id", 12, "name", "金锭", "lv", 1, "quality", 4, "kind", 2, "subKind", 1, "stack", true, "useMode", 0, "money", 1),
    13, map("id", 13, "name", "蓝宝石", "lv", 1, "quality", 1, "kind", 2, "subKind", 1, "stack", true, "useMode", 0, "money", 1),
    14, map("id", 14, "name", "红钻", "lv", 1, "quality", 2, "kind", 2, "subKind", 1, "stack", true, "useMode", 0, "money", 1),
    15, map("id", 15, "name", "紫水晶", "lv", 1, "quality", 3, "kind", 2, "subKind", 1, "stack", true, "useMode", 0, "money", 1)
);
    }

    private static void init3() {
data.map(
    16, map("id", 16, "name", "玛瑙", "lv", 1, "quality", 4, "kind", 2, "subKind", 1, "stack", true, "useMode", 0, "money", 1),
    17, map("id", 17, "name", "洗练石", "lv", 1, "quality", 2, "kind", 2, "subKind", 1, "stack", true, "useMode", 0, "money", 1),
    18, map("id", 18, "name", "攻击宝石", "lv", 1, "quality", 1, "kind", 5, "subKind", 1, "stack", true, "useMode", 0, "money", 1),
    19, map("id", 19, "name", "生命宝石", "lv", 1, "quality", 1, "kind", 5, "subKind", 1, "stack", true, "useMode", 0, "money", 1),
    20, map("id", 20, "name", "物防宝石", "lv", 1, "quality", 1, "kind", 5, "subKind", 1, "stack", true, "useMode", 0, "money", 1)
);
    }

    private static void init4() {
data.map(
    21, map("id", 21, "name", "法防宝石", "lv", 1, "quality", 1, "kind", 5, "subKind", 1, "stack", true, "useMode", 0, "money", 1),
    22, map("id", 22, "name", "暴击宝石", "lv", 1, "quality", 1, "kind", 5, "subKind", 1, "stack", true, "useMode", 0, "money", 1),
    23, map("id", 23, "name", "招架宝石", "lv", 1, "quality", 1, "kind", 5, "subKind", 1, "stack", true, "useMode", 0, "money", 1),
    24, map("id", 24, "name", "破招宝石", "lv", 1, "quality", 1, "kind", 5, "subKind", 1, "stack", true, "useMode", 0, "money", 1),
    25, map("id", 25, "name", "抗暴宝石", "lv", 1, "quality", 1, "kind", 5, "subKind", 1, "stack", true, "useMode", 0, "money", 1)
);
    }

    private static void init5() {
data.map(
    26, map("id", 26, "name", "时装1", "lv", 1, "quality", 2, "kind", 3, "subKind", 1, "stack", false, "useMode", 0, "money", 1),
    27, map("id", 27, "name", "时装2", "lv", 1, "quality", 3, "kind", 3, "subKind", 1, "stack", false, "useMode", 0, "money", 1),
    28, map("id", 28, "name", "时装3", "lv", 1, "quality", 4, "kind", 3, "subKind", 1, "stack", false, "useMode", 0, "money", 1),
    29, map("id", 29, "name", "护符1", "lv", 1, "quality", 2, "kind", 1, "subKind", 1, "stack", false, "useMode", 0, "money", 1),
    30, map("id", 30, "name", "护符2", "lv", 1, "quality", 3, "kind", 1, "subKind", 1, "stack", false, "useMode", 0, "money", 1)
);
    }

    private static void init6() {
data.map(
    31, map("id", 31, "name", "护符3", "lv", 1, "quality", 4, "kind", 1, "subKind", 1, "stack", false, "useMode", 0, "money", 1),
    201, map("id", 201, "name", "唐伯虎", "lv", 1, "quality", 3, "kind", 9, "subKind", 1, "stack", true, "useMode", 0, "money", 1),
    202, map("id", 202, "name", "荆轲", "lv", 1, "quality", 3, "kind", 9, "subKind", 1, "stack", true, "useMode", 0, "money", 1),
    203, map("id", 203, "name", "花木兰", "lv", 1, "quality", 3, "kind", 9, "subKind", 1, "stack", true, "useMode", 0, "money", 1),
    204, map("id", 204, "name", "红拂女", "lv", 1, "quality", 3, "kind", 9, "subKind", 1, "stack", true, "useMode", 0, "money", 1)
);
    }

    private static void init7() {
data.map(
    205, map("id", 205, "name", "李白", "lv", 1, "quality", 3, "kind", 9, "subKind", 1, "stack", true, "useMode", 0, "money", 1),
    206, map("id", 206, "name", "武媚娘", "lv", 1, "quality", 3, "kind", 9, "subKind", 1, "stack", true, "useMode", 0, "money", 1),
    207, map("id", 207, "name", "嬴政", "lv", 1, "quality", 3, "kind", 9, "subKind", 1, "stack", true, "useMode", 0, "money", 1),
    208, map("id", 208, "name", "孙思邈", "lv", 1, "quality", 3, "kind", 9, "subKind", 1, "stack", true, "useMode", 0, "money", 1),
    209, map("id", 209, "name", "貂蝉", "lv", 1, "quality", 3, "kind", 9, "subKind", 1, "stack", true, "useMode", 0, "money", 1)
);
    }

    private static void init8() {
data.map(
    210, map("id", 210, "name", "凤皇", "lv", 1, "quality", 3, "kind", 9, "subKind", 1, "stack", true, "useMode", 0, "money", 1),
    211, map("id", 211, "name", "高长恭", "lv", 1, "quality", 3, "kind", 9, "subKind", 1, "stack", true, "useMode", 0, "money", 1),
    212, map("id", 212, "name", "韩子高", "lv", 1, "quality", 3, "kind", 9, "subKind", 1, "stack", true, "useMode", 0, "money", 1),
    213, map("id", 213, "name", "大玉儿", "lv", 1, "quality", 3, "kind", 9, "subKind", 1, "stack", true, "useMode", 0, "money", 1),
    214, map("id", 214, "name", "孙悟空", "lv", 1, "quality", 3, "kind", 9, "subKind", 1, "stack", true, "useMode", 0, "money", 1)
);
    }

    private static void init9() {
data.map(
    215, map("id", 215, "name", "白骨夫人", "lv", 1, "quality", 3, "kind", 9, "subKind", 1, "stack", true, "useMode", 0, "money", 1),
    216, map("id", 216, "name", "孔明", "lv", 1, "quality", 3, "kind", 9, "subKind", 1, "stack", true, "useMode", 0, "money", 1),
    217, map("id", 217, "name", "钟无艳", "lv", 1, "quality", 3, "kind", 9, "subKind", 1, "stack", true, "useMode", 0, "money", 1),
    218, map("id", 218, "name", "飞燕", "lv", 1, "quality", 3, "kind", 9, "subKind", 1, "stack", true, "useMode", 0, "money", 1),
    219, map("id", 219, "name", "合德", "lv", 1, "quality", 3, "kind", 9, "subKind", 1, "stack", true, "useMode", 0, "money", 1)
);
    }

    private static void init10() {
data.map(
    220, map("id", 220, "name", "李耳", "lv", 1, "quality", 3, "kind", 9, "subKind", 1, "stack", true, "useMode", 0, "money", 1),
    221, map("id", 221, "name", "逍遥", "lv", 1, "quality", 3, "kind", 9, "subKind", 1, "stack", true, "useMode", 0, "money", 1),
    222, map("id", 222, "name", "鱼玄机", "lv", 1, "quality", 3, "kind", 9, "subKind", 1, "stack", true, "useMode", 0, "money", 1),
    223, map("id", 223, "name", "卓文君", "lv", 1, "quality", 3, "kind", 9, "subKind", 1, "stack", true, "useMode", 0, "money", 1),
    224, map("id", 224, "name", "公孙大娘", "lv", 1, "quality", 3, "kind", 9, "subKind", 1, "stack", true, "useMode", 0, "money", 1)
);
    }

    private static void init11() {
data.map(
    225, map("id", 225, "name", "姬熙来", "lv", 1, "quality", 3, "kind", 9, "subKind", 1, "stack", true, "useMode", 0, "money", 1),
    226, map("id", 226, "name", "南歌", "lv", 1, "quality", 3, "kind", 9, "subKind", 1, "stack", true, "useMode", 0, "money", 1),
    227, map("id", 227, "name", "清衡", "lv", 1, "quality", 3, "kind", 9, "subKind", 1, "stack", true, "useMode", 0, "money", 1),
    228, map("id", 228, "name", "洗砚", "lv", 1, "quality", 3, "kind", 9, "subKind", 1, "stack", true, "useMode", 0, "money", 1),
    229, map("id", 229, "name", "承影", "lv", 1, "quality", 3, "kind", 9, "subKind", 1, "stack", true, "useMode", 0, "money", 1)
);
    }

    private static void init12() {
data.map(
    10000, map("id", 10000, "name", "武器", "lv", 1, "quality", 1, "kind", 1, "subKind", 2, "stack", false, "useMode", 0, "money", 1),
    10001, map("id", 10001, "name", "头", "lv", 1, "quality", 1, "kind", 1, "subKind", 2, "stack", false, "useMode", 0, "money", 1),
    10002, map("id", 10002, "name", "手", "lv", 1, "quality", 1, "kind", 1, "subKind", 2, "stack", false, "useMode", 0, "money", 1),
    10003, map("id", 10003, "name", "胸", "lv", 1, "quality", 1, "kind", 1, "subKind", 2, "stack", false, "useMode", 0, "money", 1),
    10004, map("id", 10004, "name", "武器", "lv", 1, "quality", 1, "kind", 1, "subKind", 2, "stack", false, "useMode", 0, "money", 1)
);
    }

    private static void init13() {
data.map(
    10005, map("id", 10005, "name", "头", "lv", 1, "quality", 1, "kind", 1, "subKind", 2, "stack", false, "useMode", 0, "money", 1),
    10006, map("id", 10006, "name", "手", "lv", 1, "quality", 1, "kind", 1, "subKind", 2, "stack", false, "useMode", 0, "money", 1),
    10007, map("id", 10007, "name", "胸", "lv", 1, "quality", 1, "kind", 1, "subKind", 2, "stack", false, "useMode", 0, "money", 1),
    10008, map("id", 10008, "name", "武器", "lv", 1, "quality", 1, "kind", 1, "subKind", 2, "stack", false, "useMode", 0, "money", 1),
    10009, map("id", 10009, "name", "头", "lv", 1, "quality", 1, "kind", 1, "subKind", 2, "stack", false, "useMode", 0, "money", 1)
);
    }

    private static void init14() {
data.map(
    10010, map("id", 10010, "name", "手", "lv", 1, "quality", 1, "kind", 1, "subKind", 2, "stack", false, "useMode", 0, "money", 1),
    10011, map("id", 10011, "name", "胸", "lv", 1, "quality", 1, "kind", 1, "subKind", 2, "stack", false, "useMode", 0, "money", 1),
    10012, map("id", 10012, "name", "武器", "lv", 1, "quality", 1, "kind", 1, "subKind", 2, "stack", false, "useMode", 0, "money", 1),
    10013, map("id", 10013, "name", "头", "lv", 1, "quality", 1, "kind", 1, "subKind", 2, "stack", false, "useMode", 0, "money", 1),
    10014, map("id", 10014, "name", "手", "lv", 1, "quality", 1, "kind", 1, "subKind", 2, "stack", false, "useMode", 0, "money", 1)
);
    }

    private static void init15() {
data.map(
    10015, map("id", 10015, "name", "胸", "lv", 1, "quality", 1, "kind", 1, "subKind", 2, "stack", false, "useMode", 0, "money", 1),
    40000, map("id", 40000, "name", "普通阵", "lv", 1, "quality", 1, "kind", 4, "stack", false, "useMode", 0, "money", 1),
    60000, map("id", 60000, "name", "普通关卡1满星奖励", "lv", 1, "quality", 1, "kind", 2, "subKind", 1, "stack", true, "useMode", 2, "money", 1),
    70000, map("id", 70000, "name", "男主普攻", "lv", 1, "quality", 1, "kind", 7, "stack", true, "useMode", 0, "money", 1),
    70001, map("id", 70001, "name", "女主物攻", "lv", 1, "quality", 1, "kind", 7, "stack", true, "useMode", 0, "money", 1)
);
    }

    private static void init16() {
data.map(
    70002, map("id", 70002, "name", "直线物理", "lv", 1, "quality", 1, "kind", 7, "stack", true, "useMode", 0, "money", 1),
    70003, map("id", 70003, "name", "直线法术", "lv", 1, "quality", 1, "kind", 7, "stack", true, "useMode", 0, "money", 1),
    70004, map("id", 70004, "name", "横排物理", "lv", 1, "quality", 1, "kind", 7, "stack", true, "useMode", 0, "money", 1),
    70005, map("id", 70005, "name", "横排法术", "lv", 1, "quality", 1, "kind", 7, "stack", true, "useMode", 0, "money", 1),
    70006, map("id", 70006, "name", "清心咒", "lv", 1, "quality", 1, "kind", 7, "stack", true, "useMode", 0, "money", 1)
);
    }

    private static void init17() {
data.map(
    70007, map("id", 70007, "name", "潇湘夜雨", "lv", 1, "quality", 1, "kind", 7, "stack", true, "useMode", 0, "money", 1),
    70008, map("id", 70008, "name", "风花雪夜", "lv", 1, "quality", 1, "kind", 7, "stack", true, "useMode", 0, "money", 1),
    70009, map("id", 70009, "name", "一马当先", "lv", 1, "quality", 1, "kind", 7, "stack", true, "useMode", 0, "money", 1),
    70010, map("id", 70010, "name", "貂蝉拜月", "lv", 1, "quality", 1, "kind", 7, "stack", true, "useMode", 0, "money", 1),
    70011, map("id", 70011, "name", "横扫乾坤", "lv", 1, "quality", 1, "kind", 7, "stack", true, "useMode", 0, "money", 1)
);
    }

    private static void init18() {
data.map(
    70012, map("id", 70012, "name", "横扫千军", "lv", 1, "quality", 1, "kind", 7, "stack", true, "useMode", 0, "money", 1),
    70013, map("id", 70013, "name", "群切后排", "lv", 1, "quality", 1, "kind", 7, "stack", true, "useMode", 0, "money", 1),
    70014, map("id", 70014, "name", "单切后排", "lv", 1, "quality", 1, "kind", 7, "stack", true, "useMode", 0, "money", 1),
    70015, map("id", 70015, "name", "群体回血", "lv", 1, "quality", 1, "kind", 7, "stack", true, "useMode", 0, "money", 1),
    70016, map("id", 70016, "name", "群切后排", "lv", 1, "quality", 1, "kind", 7, "stack", true, "useMode", 0, "money", 1)
);
    }

    private static void init19() {
data.map(
    70017, map("id", 70017, "name", "单切后排", "lv", 1, "quality", 1, "kind", 7, "stack", true, "useMode", 0, "money", 1)
);
    }

}