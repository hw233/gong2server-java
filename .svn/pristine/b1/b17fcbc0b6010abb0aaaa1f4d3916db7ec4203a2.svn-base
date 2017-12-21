package com.gamejelly.gong2.config.data;

import com.gamejelly.gong2.config.data.base.LData;
import com.gamejelly.gong2.config.data.base.LMap;

/**
 * 任务表.csv
 * 编号 = id
 * 任务类型 = type
 * 版本号 = rewardVersion
 * 派系等级对
应任务等级 = openPaixiLevel
 * 显示开始时间 = beginTime
 * 显示结束时间 = endTime
 * 实际开始时间 = beginNotifyTime
 * 实际结束时间 = endNotifyTime
 * 达成条件组 = targetIds
 * 奖励银两 = money
 * 奖励元宝 = gold
 * 奖励经验 = exp
 * 奖励内丹 = neidanPoint
 * 奖励金砖 = jinzhuanExp
 * 奖励物品 = award
 * 物品数量 = num
 * 任务领取前物品 = topAward
 * 任务领取前物品数量 = topNum
 * 称号ID = chenghao
 */
public class MissionData extends LData {
    public static final LMap data = new LMap();

    static {
        init0();
        data.setFrozen(true);
    }

    private static void init0() {
data.map(
    20000, map("id", 20000, "type", 2, "targetIds", list(20000), "money", "lv*10+500", "exp", "lv*15+200", "jinzhuanExp", 15),
    200000, map("id", 200000, "type", 2, "targetIds", list(200000), "money", "lv*10+600", "exp", "lv*15+300", "jinzhuanExp", 20),
    200001, map("id", 200001, "type", 2, "targetIds", list(200001), "money", "lv*10+700", "exp", "lv*15+400", "jinzhuanExp", 25),
    200002, map("id", 200002, "type", 2, "targetIds", list(200002), "money", "lv*10+800", "exp", "lv*15+500", "jinzhuanExp", 30, "award", list(20505), "num", list(1)),
    200003, map("id", 200003, "type", 2, "targetIds", list(200003), "money", "lv*10+1000", "exp", "lv*15+800", "jinzhuanExp", 35, "award", list(20505), "num", list(1))
);
    }

}