package com.gamejelly.gong2.config.data;

import com.gamejelly.gong2.config.data.base.LData;
import com.gamejelly.gong2.config.data.base.LMap;

/**
 * 服务端公式表.csv
 * 公式编号 = sid
 * 公式名称 = describe
 * 公式命名 = funName
 * 相关公式 = formula
 * 公式引用 = fmlaCite
 */
public class SFormulaData extends LData {
    public static final LMap data = new LMap();

    static {
        init0();
        init1();
        data.setFrozen(true);
    }

    private static void init0() {
data.map(
    99001, map("sid", 99001, "describe", "最终暴击率公式", "funName", "finalCriRate", "formula", "o.totCri+o.bufCri-t.totDcri-t.bufDcri"),
    99002, map("sid", 99002, "describe", "最终格挡率公式", "funName", "finalParryRate", "formula", "o.totParry+o.bufParry-t.totDparry-t.bufDparry"),
    99014, map("sid", 99014, "describe", "总战力公式", "funName", "totalZl", "formula", "math.rint(o.disAtk*29.3+o.disDef*21.1+o.disDpower*21.1+o.disHp*1+o.disCri*4085+o.disDcri*4085+o.disFocus*817+o.disDparry*2458.3+o.disParry*2458.3+o.disTen*2458.3+o.disPen*4219.4+o.disDeHIT*26.3+o.disEahit*26.3+o.disPreDeepen*8169.9+o.disPreEase*8169.9)"),
    99015, map("sid", 99015, "describe", "物理伤害公式", "funName", "PhyInjury", "formula", "((math.max(o.finalAtk-t.finalDef*(1-o.finalPen),0)+o.finalAtk*0.11)*o.skillDamegeProb+o.skillDamegeAdd+o.finalDeHIT-t.finalEahit)*(1+o.finalPreDeepen-t.finalPreEase)"),
    99016, map("sid", 99016, "describe", "法术伤害公式", "funName", "MagInjury", "formula", "((math.max(o.finalAtk-t.finalDpower*(1-o.finalPen),0)+o.finalAtk*0.11)*o.skillDamegeProb+o.skillDamegeAdd+o.finalDeHIT-t.finalEahit)*(1+o.finalPreDeepen-t.finalPreEase)")
);
    }

    private static void init1() {
data.map(
    99017, map("sid", 99017, "describe", "物理暴击公式", "funName", "criPhyInjury", "formula", "((math.max(o.finalAtk-t.finalDef*(1-o.finalPen),0)+o.finalAtk*0.11)*o.skillDamegeProb+o.skillDamegeAdd+o.finalDeHIT-t.finalEahit)*(1+o.finalPreDeepen-t.finalPreEase+o.criProb)"),
    99018, map("sid", 99018, "describe", "法术暴击公式", "funName", "criMagInjury", "formula", "((math.max(o.finalAtk-t.finalDpower*(1-o.finalPen),0)+o.finalAtk*0.11)*o.skillDamegeProb+o.skillDamegeAdd+o.finalDeHIT-t.finalEahit)*(1+o.finalPreDeepen-t.finalPreEase+o.criProb)"),
    99019, map("sid", 99019, "describe", "物理格挡公式", "funName", "parryPhyInjury", "formula", "((math.max(o.finalAtk-t.finalDef*(1-o.finalPen),0)+o.finalAtk*0.11)*o.skillDamegeProb+o.skillDamegeAdd+o.finalDeHIT-t.finalEahit)*(1+o.finalPreDeepen-t.finalPreEase-t.parryProb)"),
    99020, map("sid", 99020, "describe", "法术格挡公式", "funName", "parryMagInjury", "formula", "((math.max(o.finalAtk-t.finalDpower*(1-o.finalPen),0)+o.finalAtk*0.11)*o.skillDamegeProb+o.skillDamegeAdd+o.finalDeHIT-t.finalEahit)*(1+o.finalPreDeepen-t.finalPreEase-t.parryProb)"),
    99301, map("sid", 99301, "describe", "怪物等级计算公式", "funName", "calcMonsterLv", "formula", "o.lv")
);
    }

}