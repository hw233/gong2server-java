package com.gamejelly.gong2.config.data;

import com.gamejelly.gong2.config.data.base.LData;
import com.gamejelly.gong2.config.data.base.LMap;

/**
 * 属性表.csv
 * 属性编号 = id
 * 属性命名 = name
 * 属性名称 = funName
 * 是否战斗内属性 = inFight
 * 引用属性 = prop
 * 类型 = kind
 */
public class PropData extends LData {
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
        init20();
        init21();
        data.setFrozen(true);
    }

    private static void init0() {
data.map(
    1, map("id", 1, "name", "等级", "funName", "lv", "kind", 0),
    2, map("id", 2, "name", "银两", "funName", "money", "kind", 0),
    3, map("id", 3, "name", "元宝", "funName", "gold", "kind", 0),
    10001, map("id", 10001, "name", "基础攻击值", "funName", "basAtk", "kind", 0),
    10002, map("id", 10002, "name", "基础攻击百分比", "funName", "basPreAtk", "kind", 0)
);
    }

    private static void init1() {
data.map(
    10003, map("id", 10003, "name", "基础物防值", "funName", "basDef", "kind", 0),
    10004, map("id", 10004, "name", "基础物防百分比", "funName", "basPreDef", "kind", 0),
    10005, map("id", 10005, "name", "基础法防值", "funName", "basDpower", "kind", 0),
    10006, map("id", 10006, "name", "基础法防百分比", "funName", "basPreDpower", "kind", 0),
    10007, map("id", 10007, "name", "基础生命值", "funName", "basHp", "kind", 0)
);
    }

    private static void init2() {
data.map(
    10008, map("id", 10008, "name", "基础生命百分比", "funName", "basPreHp", "kind", 0),
    10009, map("id", 10009, "name", "基础怒气值", "funName", "basCp", "kind", 0),
    10010, map("id", 10010, "name", "基础暴击率", "funName", "basCri", "kind", 0),
    10011, map("id", 10011, "name", "基础抗暴率", "funName", "basDcri", "kind", 0),
    10012, map("id", 10012, "name", "基础暴击伤害百分比", "funName", "basFocus", "kind", 0)
);
    }

    private static void init3() {
data.map(
    10013, map("id", 10013, "name", "基础无视格挡率", "funName", "basDparry", "kind", 0),
    10014, map("id", 10014, "name", "基础格挡率", "funName", "basParry", "kind", 0),
    10015, map("id", 10015, "name", "基础格挡伤害百分比", "funName", "basTen", "kind", 0),
    10016, map("id", 10016, "name", "基础无视防御百分比", "funName", "basPen", "kind", 0),
    10017, map("id", 10017, "name", "基础增伤值", "funName", "basDeHIT", "kind", 0)
);
    }

    private static void init4() {
data.map(
    10018, map("id", 10018, "name", "基础减伤值", "funName", "basEahit", "kind", 0),
    10019, map("id", 10019, "name", "基础增伤百分比", "funName", "basPreDeepen", "kind", 0),
    10020, map("id", 10020, "name", "基础减伤百分比", "funName", "basPreEase", "kind", 0),
    10021, map("id", 10021, "name", "附加攻击值", "funName", "bufAtk", "inFight", 1, "kind", 0),
    10022, map("id", 10022, "name", "附加攻击百分比", "funName", "bufPreAtk", "inFight", 1, "kind", 0)
);
    }

    private static void init5() {
data.map(
    10023, map("id", 10023, "name", "附加物防值", "funName", "bufDef", "inFight", 1, "kind", 0),
    10024, map("id", 10024, "name", "附加物防百分比", "funName", "bufPreDef", "inFight", 1, "kind", 0),
    10025, map("id", 10025, "name", "附加法防值", "funName", "bufDpower", "inFight", 1, "kind", 0),
    10026, map("id", 10026, "name", "附加法防百分比", "funName", "bufPreDpower", "inFight", 1, "kind", 0),
    10027, map("id", 10027, "name", "附加生命值", "funName", "bufHp", "inFight", 1, "kind", 0)
);
    }

    private static void init6() {
data.map(
    10028, map("id", 10028, "name", "附加生命百分比", "funName", "bufPreHp", "inFight", 1, "kind", 0),
    10029, map("id", 10029, "name", "附加怒气值", "funName", "bufCp", "inFight", 1, "kind", 0),
    10030, map("id", 10030, "name", "附加暴击率", "funName", "bufCri", "inFight", 1, "kind", 0),
    10031, map("id", 10031, "name", "附加抗暴率", "funName", "bufDcri", "inFight", 1, "kind", 0),
    10032, map("id", 10032, "name", "附加暴击伤害百分比", "funName", "bufFocus", "inFight", 1, "kind", 0)
);
    }

    private static void init7() {
data.map(
    10033, map("id", 10033, "name", "附加无视格挡率", "funName", "bufDparry", "inFight", 1, "kind", 0),
    10034, map("id", 10034, "name", "附加格挡率", "funName", "bufParry", "inFight", 1, "kind", 0),
    10035, map("id", 10035, "name", "附加格挡伤害百分比", "funName", "bufTen", "inFight", 1, "kind", 0),
    10036, map("id", 10036, "name", "附加无视防御百分比", "funName", "bufPen", "inFight", 1, "kind", 0),
    10037, map("id", 10037, "name", "附加增伤值", "funName", "bufDeHIT", "inFight", 1, "kind", 0)
);
    }

    private static void init8() {
data.map(
    10038, map("id", 10038, "name", "附加减伤值", "funName", "bufEahit", "inFight", 1, "kind", 0),
    10039, map("id", 10039, "name", "附加增伤百分比", "funName", "bufPreDeepen", "inFight", 1, "kind", 0),
    10040, map("id", 10040, "name", "附加减伤百分比", "funName", "bufPreEase", "inFight", 1, "kind", 0),
    10041, map("id", 10041, "name", "攻击", "funName", "rankAtk", "kind", 0),
    10042, map("id", 10042, "name", "物防", "funName", "rankDef", "kind", 0)
);
    }

    private static void init9() {
data.map(
    10043, map("id", 10043, "name", "法防", "funName", "rankDpower", "kind", 0),
    10044, map("id", 10044, "name", "生命", "funName", "rankHp", "kind", 0),
    10045, map("id", 10045, "name", "攻击", "funName", "equAtk", "kind", 0),
    10046, map("id", 10046, "name", "物防", "funName", "equDef", "kind", 0),
    10047, map("id", 10047, "name", "法防", "funName", "equDpower", "kind", 0)
);
    }

    private static void init10() {
data.map(
    10048, map("id", 10048, "name", "生命", "funName", "equHp", "kind", 0),
    10049, map("id", 10049, "name", "技能伤害值", "funName", "skillDamegeAdd", "inFight", 1, "kind", 0),
    10050, map("id", 10050, "name", "技能伤害百分比", "funName", "skillDamegeProb", "inFight", 1, "kind", 0),
    10051, map("id", 10051, "name", "成长攻击", "funName", "atkLv", "kind", 0),
    10052, map("id", 10052, "name", "成长物防", "funName", "defLv", "kind", 0)
);
    }

    private static void init11() {
data.map(
    10053, map("id", 10053, "name", "成长法防", "funName", "dpowerLv", "kind", 0),
    10054, map("id", 10054, "name", "成长生命", "funName", "hpLv", "kind", 0),
    10055, map("id", 10055, "name", "攻击百分比", "funName", "equPreAtk", "kind", 0),
    10056, map("id", 10056, "name", "物防百分比", "funName", "equPreDef", "kind", 0),
    10057, map("id", 10057, "name", "法防百分比", "funName", "equPreDpower", "kind", 0)
);
    }

    private static void init12() {
data.map(
    10058, map("id", 10058, "name", "生命百分比", "funName", "equPreHp", "kind", 0),
    80027, map("id", 80027, "name", "总攻击值", "funName", "totAtk", "prop", "basAtk+atkLv*lv+rankAtk+equAtk", "kind", 1),
    80028, map("id", 80028, "name", "总攻击百分比", "funName", "totPreAtk", "prop", "basPreAtk+equPreAtk+1", "kind", 1),
    80029, map("id", 80029, "name", "总物防值", "funName", "totDef", "prop", "basDef+defLv*lv+rankDef+equDef", "kind", 1),
    80030, map("id", 80030, "name", "总物防百分比", "funName", "totPreDef", "prop", "basPreDef+equPreDef+1", "kind", 1)
);
    }

    private static void init13() {
data.map(
    80031, map("id", 80031, "name", "总法防值", "funName", "totDpower", "prop", "basDpower+rankDpower+equDpower+dpowerLv*lv", "kind", 1),
    80032, map("id", 80032, "name", "总法防百分比", "funName", "totPreDpower", "prop", "basPreDpower+equPreDpower+1", "kind", 1),
    80033, map("id", 80033, "name", "总生命值", "funName", "totHp", "prop", "basHp+rankHp+equHp+lv*hpLv", "kind", 1),
    80034, map("id", 80034, "name", "总生命百分比", "funName", "totPreHp", "prop", "basPreHp+equPreHp+1", "kind", 1),
    80035, map("id", 80035, "name", "怒气值", "funName", "totCp", "prop", "basCp", "kind", 1)
);
    }

    private static void init14() {
data.map(
    80036, map("id", 80036, "name", "暴击率", "funName", "totCri", "prop", "basCri", "kind", 1),
    80037, map("id", 80037, "name", "抗暴率", "funName", "totDcri", "prop", "basDcri", "kind", 1),
    80038, map("id", 80038, "name", "暴击伤害百分比", "funName", "totFocus", "prop", "basFocus+0.5", "kind", 1),
    80039, map("id", 80039, "name", "无视格挡率", "funName", "totDparry", "prop", "basDparry", "kind", 1),
    80040, map("id", 80040, "name", "格挡率", "funName", "totParry", "prop", "basParry", "kind", 1)
);
    }

    private static void init15() {
data.map(
    80041, map("id", 80041, "name", "格挡伤害百分比", "funName", "totTen", "prop", "basTen+0.3", "kind", 1),
    80042, map("id", 80042, "name", "无视防御百分比", "funName", "totPen", "prop", "basPen", "kind", 1),
    80043, map("id", 80043, "name", "增伤值", "funName", "totDeHIT", "prop", "basDeHIT", "kind", 1),
    80044, map("id", 80044, "name", "减伤值", "funName", "totEahit", "prop", "basEahit", "kind", 1),
    80045, map("id", 80045, "name", "增伤百分比", "funName", "totPreDeepen", "prop", "basPreDeepen", "kind", 1)
);
    }

    private static void init16() {
data.map(
    80046, map("id", 80046, "name", "减伤百分比", "funName", "totPreEase", "prop", "basPreEase", "kind", 1),
    80047, map("id", 80047, "name", "面板攻击值", "funName", "disAtk", "prop", "totAtk*totPreAtk", "kind", 1),
    80048, map("id", 80048, "name", "面板物防值", "funName", "disDef", "prop", "totDef*totPreDef", "kind", 1),
    80049, map("id", 80049, "name", "面板法防值", "funName", "disDpower", "prop", "totDpower*totPreDpower", "kind", 1),
    80050, map("id", 80050, "name", "面板生命值", "funName", "disHp", "prop", "totHp*totPreHp", "kind", 1)
);
    }

    private static void init17() {
data.map(
    80051, map("id", 80051, "name", "面板暴击率", "funName", "disCri", "prop", "totCri", "kind", 1),
    80052, map("id", 80052, "name", "面板抗暴率", "funName", "disDcri", "prop", "totDcri", "kind", 1),
    80053, map("id", 80053, "name", "面板暴击伤害百分比", "funName", "disFocus", "prop", "totFocus", "kind", 1),
    80054, map("id", 80054, "name", "面板无视格挡率", "funName", "disDparry", "prop", "totDparry", "kind", 1),
    80055, map("id", 80055, "name", "面板格挡率", "funName", "disParry", "prop", "totParry", "kind", 1)
);
    }

    private static void init18() {
data.map(
    80056, map("id", 80056, "name", "面板格挡伤害百分比", "funName", "disTen", "prop", "totTen", "kind", 1),
    80057, map("id", 80057, "name", "面板无视防御百分比", "funName", "disPen", "prop", "totPen", "kind", 1),
    80058, map("id", 80058, "name", "面板增伤值", "funName", "disDeHIT", "prop", "totDeHIT", "kind", 1),
    80059, map("id", 80059, "name", "面板减伤值", "funName", "disEahit", "prop", "totEahit", "kind", 1),
    80060, map("id", 80060, "name", "面板增伤百分比", "funName", "disPreDeepen", "prop", "totPreDeepen", "kind", 1)
);
    }

    private static void init19() {
data.map(
    80061, map("id", 80061, "name", "面板减伤百分比", "funName", "disPreEase", "prop", "totPreEase", "kind", 1),
    80062, map("id", 80062, "name", "暴击伤害率", "funName", "criProb", "prop", "totFocus+bufFocus", "kind", 1),
    80063, map("id", 80063, "name", "格挡减伤率", "funName", "parryProb", "prop", "totTen+bufTen", "kind", 1),
    80064, map("id", 80064, "name", "最终攻击力", "funName", "finalAtk", "prop", "(disAtk+bufAtk)*(1+bufPreAtk)", "kind", 1),
    80065, map("id", 80065, "name", "最终物防", "funName", "finalDef", "prop", "(disDef+bufDef)*(1+bufPreDef)", "kind", 1)
);
    }

    private static void init20() {
data.map(
    80066, map("id", 80066, "name", "最终法防", "funName", "finalDpower", "prop", "(disDpower+bufDpower)*(1+bufPreDpower)", "kind", 1),
    80067, map("id", 80067, "name", "最终生命值", "funName", "finalHp", "prop", "(disHp+bufHp)*(1+bufPreHp)", "kind", 1),
    80068, map("id", 80068, "name", "最终无视防御百分比", "funName", "finalPen", "prop", "disPen+bufPen", "kind", 1),
    80069, map("id", 80069, "name", "最终增伤值", "funName", "finalDeHIT", "prop", "disDeHIT+bufDeHIT", "kind", 1),
    80070, map("id", 80070, "name", "最终减伤值", "funName", "finalEahit", "prop", "disEahit+bufEahit", "kind", 1)
);
    }

    private static void init21() {
data.map(
    80071, map("id", 80071, "name", "最终增伤百分比", "funName", "finalPreDeepen", "prop", "disPreDeepen+bufPreDeepen", "kind", 1),
    80072, map("id", 80072, "name", "最终减伤百分比", "funName", "finalPreEase", "prop", "disPreEase+bufPreEase", "kind", 1),
    80073, map("id", 80073, "name", "最终怒气值", "funName", "finalCp", "prop", "totCp", "kind", 1)
);
    }

}