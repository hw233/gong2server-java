package com.gamejelly.gong2.config.data;

import com.gamejelly.gong2.config.data.base.LData;
import com.gamejelly.gong2.config.data.base.LMap;

/**
 * 主线任务表.csv
 * 编号 = id
 * 是否是最后一主线任务 = end
 * 引导ID = guideId
 * 背景ID = bjId
 * 背景音ID = bjVoiceid
 * 男前置任务编号 = mPrevid
 * 男后续任务编号 = mNextid
 * 男任务名称 = mName
 * 男任务描述 = mDescribe
 * 男任务ID = mMissionid
 * 男NPC对话组ID = mTalkid
 * 女前置任务编号 = fPrevid
 * 女后续任务编号 = fNextid
 * 女任务名称 = fName
 * 女任务描述 = fDescribe
 * 女任务ID = fMissionid
 * 女NPC对话组ID = fTalkid
 */
public class ZhuxianrenwuData extends LData {
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
        init22();
        init23();
        init24();
        init25();
        init26();
        init27();
        init28();
        init29();
        init30();
        init31();
        init32();
        init33();
        init34();
        init35();
        init36();
        init37();
        init38();
        init39();
        init40();
        init41();
        init42();
        init43();
        init44();
        init45();
        init46();
        init47();
        init48();
        init49();
        init50();
        init51();
        data.setFrozen(true);
    }

    private static void init0() {
data.map(
    10000, map("id", 10000, "guideId", 700, "mPrevid", 0, "mNextid", 10001, "mName", "采花", "mDescribe", "传太后娘娘懿旨，凡新选入宫的秀女即日起需在宫中同御膳房嬷嬷一同前去采集玫瑰花，为后宫娘娘酿制灵犀露。【请小主前去采摘玫瑰】", "mMissionid", 40000, "fPrevid", 0, "fNextid", 10001, "fName", "采花", "fDescribe", "传太后娘娘懿旨，凡新选入宫的秀女即日起需在宫中同御膳房嬷嬷一同前去采集玫瑰花，为后宫娘娘酿制灵犀露。【请小主前去采摘玫瑰】", "fMissionid", 40000),
    10001, map("id", 10001, "guideId", 701, "mPrevid", 10000, "mNextid", 10002, "mName", "种花", "mDescribe", "近日小主们采摘玫瑰花频繁，导致玫瑰花数量不足，小主需尝试种植玫瑰花，以便日后采摘。【请小主前去种植玫瑰花】", "mMissionid", 40001, "fPrevid", 10000, "fNextid", 10002, "fName", "种花", "fDescribe", "近日小主们采摘玫瑰花频繁，导致玫瑰花数量不足，小主需尝试种植玫瑰花，以便日后采摘。【请小主前去种植玫瑰花】", "fMissionid", 40001),
    10002, map("id", 10002, "guideId", 702, "mPrevid", 10001, "mNextid", 10003, "mName", "建尚衣局", "mDescribe", "老奴还请各位小主留心，不日皇上便会亲临储秀宫，秀女们可前去尚衣局选挑新衣前去面圣。【请小主前去建造尚衣局】", "mMissionid", 40002, "fPrevid", 10001, "fNextid", 10003, "fName", "建尚衣局", "fDescribe", "老奴还请各位小主留心，不日皇上便会亲临储秀宫，秀女们可前去尚衣局选挑新衣前去面圣。【请小主前去建造尚衣局】", "fMissionid", 40002),
    10003, map("id", 10003, "guideId", 703, "mPrevid", 10002, "mNextid", 10004, "mName", "换装", "mDescribe", "【小主向公公打赏一枚玉镯，公公喜上眉梢】由于小主赏赐了奴才，奴才便要告知小主一个秘密，娴妃娘娘告知老奴，皇上最喜小主们穿绿色，娴妃娘娘也给小主准备好了，小主可要记住了。【穿戴翠色菖蒲纹杭绸直裰】", "mMissionid", 40004, "fPrevid", 10002, "fNextid", 10004, "fName", "换装", "fDescribe", "【小主向公公打赏一枚玉镯，公公喜上眉梢】由于小主赏赐了奴才，奴才便要告知小主一个秘密，娴妃娘娘告知老奴，皇上最喜小主们穿绿色，娴妃娘娘也给小主准备好了，小主可要记住了。【穿戴珍珠璎珞轻罗百合裙】", "fMissionid", 40003),
    10004, map("id", 10004, "guideId", 704, "mPrevid", 10003, "mNextid", 10005, "mName", "建太和殿", "mDescribe", "皇上有旨，命储秀宫所有秀女梳洗后前去太和殿面圣。【请小主建造太和殿】", "mMissionid", 40005, "fPrevid", 10003, "fNextid", 10005, "fName", "建太和殿", "fDescribe", "皇上有旨，命储秀宫所有秀女梳洗后前去太和殿面圣。【请小主建造太和殿】", "fMissionid", 40005)
);
    }

    private static void init1() {
data.map(
    10005, map("id", 10005, "guideId", 705, "mPrevid", 10004, "mNextid", 10006, "mName", "面圣", "mDescribe", "那就请小主跟随老奴前去太和殿面圣吧。【请小主点击太和殿之后点击面圣】", "mMissionid", 40006, "fPrevid", 10004, "fNextid", 10006, "fName", "面圣", "fDescribe", "那就请小主跟随老奴前去太和殿面圣吧。【请小主点击太和殿之后点击面圣】", "fMissionid", 40006),
    10006, map("id", 10006, "guideId", 706, "mPrevid", 10005, "mNextid", 10007, "mName", "剧情战斗（打第一关）", "mDescribe", "小主可是想知晓关于娴妃娘娘的故事以便报答娴妃娘娘的恩情，那老奴悄悄告知小主，您可以前去剧情剧情查看娴妃娘娘从入宫前到入宫后的剧情。【请小主打通剧情第一章第一关卡】", "mMissionid", 40007, "fPrevid", 10005, "fNextid", 10007, "fName", "剧情战斗（打第一关）", "fDescribe", "小主可是想知晓关于娴妃娘娘的故事以便报答娴妃娘娘的恩情，那老奴悄悄告知小主，您可以前去剧情剧情查看娴妃娘娘从入宫前到入宫后的剧情。【请小主打通剧情第一章第一关卡】", "fMissionid", 40007),
    10007, map("id", 10007, "guideId", 707, "mPrevid", 10006, "mNextid", 10008, "mName", "穿装备", "mDescribe", "小主，后宫之中人心叵测，小主若想要提升自己的战力，您需要通过穿装备来提升自己。【请小主点击装备按钮后前去穿戴装备】", "mMissionid", 40008, "fPrevid", 10006, "fNextid", 10008, "fName", "穿装备", "fDescribe", "小主，后宫之中人心叵测，小主若想要提升自己的战力，您需要通过穿装备来提升自己。【请小主点击装备按钮后前去穿戴装备】", "fMissionid", 40008),
    10008, map("id", 10008, "guideId", 708, "mPrevid", 10007, "mNextid", 10009, "mName", "强化", "mDescribe", "小主，老奴依旧放心不下小主，老奴建议小主再前去强化一下筋骨才好应付那些心狠手辣之人。【请小前去仆从界面点击强化按钮】", "mMissionid", 40009, "fPrevid", 10007, "fNextid", 10009, "fName", "强化", "fDescribe", "小主，老奴依旧放心不下小主，老奴建议小主再前去强化一下筋骨才好应付那些心狠手辣之人。【请小前去仆从界面点击强化按钮】", "fMissionid", 40009),
    10009, map("id", 10009, "mPrevid", 10008, "mNextid", 10010, "mName", "剧情战斗（打第三关）", "mDescribe", "老奴看小主精神抖擞，小主可放心前去剧情战斗了。【请小主打通剧情第一章第三关卡】", "mMissionid", 40010, "fPrevid", 10008, "fNextid", 10010, "fName", "剧情战斗（打第三关）", "fDescribe", "老奴看小主精神抖擞，小主可放心前去剧情战斗了。【请小主打通剧情第一章第三关卡】", "fMissionid", 40010)
);
    }

    private static void init2() {
data.map(
    10010, map("id", 10010, "guideId", 710, "mPrevid", 10009, "mNextid", 10011, "mName", "招募", "mDescribe", "小主为何如此伤神，可要仔细玉体，娴妃娘娘给小主支招，让小主去寻觅一二贴心仆从，这样会对小主有极大的帮助。【请小主点击仆从按钮前去招募仆从】", "mMissionid", 40011, "fPrevid", 10009, "fNextid", 10011, "fName", "招募", "fDescribe", "小主为何如此伤神，可要仔细玉体，娴妃娘娘给小主支招，让小主去寻觅一二贴心仆从，这样会对小主有极大的帮助。【请小主点击仆从按钮前去招募仆从】", "fMissionid", 40011),
    10011, map("id", 10011, "guideId", 711, "mPrevid", 10010, "mNextid", 10012, "mName", "上阵", "mDescribe", "小主，既然已招募到忠心的仆从，那就让他们各司其职，尽忠职守吧。【请小主点击仆从按钮前去布阵】", "mMissionid", 40012, "fPrevid", 10010, "fNextid", 10012, "fName", "上阵", "fDescribe", "小主，既然已招募到忠心的仆从，那就让他们各司其职，尽忠职守吧。【请小主点击仆从按钮前去布阵】", "fMissionid", 40012),
    10012, map("id", 10012, "mPrevid", 10011, "mNextid", 10013, "mName", "剧情战斗（打通全关）", "mDescribe", "小主可是要再去一探究竟，现有仆从陪同，老奴也能放心了。【请小主打通剧情第一章全部关卡】", "mMissionid", 40013, "fPrevid", 10011, "fNextid", 10013, "fName", "剧情战斗（打通全关）", "fDescribe", "小主可是要再去一探究竟，现有仆从陪同，老奴也能放心了。【请小主打通剧情第一章全部关卡】", "fMissionid", 40013),
    10013, map("id", 10013, "guideId", 713, "mPrevid", 10012, "mNextid", 10014, "mName", "升官", "mDescribe", "奉天承运，皇帝诏曰，命储秀宫各位小主前去接旨受封号。【请小主点击官品按钮前去挑战】", "mMissionid", 40014, "fPrevid", 10012, "fNextid", 10014, "fName", "升官", "fDescribe", "奉天承运，皇帝诏曰，命储秀宫各位小主前去接旨受封号。【请小主点击官品按钮前去挑战】", "fMissionid", 40014),
    10014, map("id", 10014, "guideId", 714, "mPrevid", 10013, "mNextid", 10015, "mName", "礼单任务", "mDescribe", "恭喜小主获封，从今日起小主要与后宫妃嫔多走动，对日后的宫中生活也是极有益处的。【请小主点击礼单】", "mMissionid", 40015, "fPrevid", 10013, "fNextid", 10015, "fName", "礼单任务", "fDescribe", "恭喜小主获封，从今日起小主要与后宫妃嫔多走动，对日后的宫中生活也是极有益处的。【请小主点击礼单】", "fMissionid", 40015)
);
    }

    private static void init3() {
data.map(
    10015, map("id", 10015, "guideId", 716, "bjId", 1002, "mPrevid", 10014, "mNextid", 10016, "mName", "建造土壤达到8块", "mDescribe", "晴格格感激小主前几日为太后送玫瑰花，特意前来提示小主多多建造土壤可加大产量。【请小主购买土壤】", "mMissionid", 40016, "mTalkid", list(2017, 2018, 2019, 2020), "fPrevid", 10014, "fNextid", 10016, "fName", "建造土壤达到8块", "fDescribe", "晴格格感激小主前几日为太后送玫瑰花，特意前来提示小主多多建造土壤可加大产量。【请小主购买土壤】", "fMissionid", 40016, "fTalkid", list(2017, 2018, 2019, 2020)),
    10016, map("id", 10016, "bjId", 1002, "mPrevid", 10015, "mNextid", 10017, "mName", "种植玫瑰花6朵", "mDescribe", "福伽姑姑传话来，请小主种植6朵玫瑰花送去供太后早起使用。【请小主采摘玫瑰花】", "mMissionid", 40017, "mTalkid", list(2000, 2001, 2002, 2003), "fPrevid", 10015, "fNextid", 10017, "fName", "种植玫瑰花6朵", "fDescribe", "福伽姑姑传话来，请小主种植6朵玫瑰花送去供太后早起使用。【请小主采摘玫瑰花】", "fMissionid", 40017, "fTalkid", list(2000, 2001, 2002, 2003)),
    10017, map("id", 10017, "guideId", 612, "bjId", 1001, "mPrevid", 10016, "mNextid", 10018, "mName", "加速花朵2次", "mDescribe", "蕊心为小主准备了充足的元宝，使用元宝可为土壤加速，使花朵快速长成。【请小主加速鲜花】", "mMissionid", 40018, "mTalkid", list(2004, 2005), "fPrevid", 10016, "fNextid", 10018, "fName", "加速花朵2次", "fDescribe", "蕊心为小主准备了充足的元宝，使用元宝可为土壤加速，使花朵快速长成。【请小主加速鲜花】", "fMissionid", 40018, "fTalkid", list(2004, 2005)),
    10018, map("id", 10018, "guideId", 613, "mPrevid", 10017, "mNextid", 10019, "mName", "建造金元宝", "mDescribe", "公公见小主月银不够用了，好心提醒小主可以通过金元宝来生产银两供小主日常开销。【请小主点击皇宫→选择金元宝→拖拽到地图→建造成功】", "mMissionid", 40019, "fPrevid", 10017, "fNextid", 10019, "fName", "建造金元宝", "fDescribe", "公公见小主月银不够用了，好心提醒小主可以通过金元宝来生产银两供小主日常开销。【请小主点击皇宫→选择金元宝→拖拽到地图→建造成功】", "fMissionid", 40019),
    10019, map("id", 10019, "guideId", 614, "mPrevid", 10018, "mNextid", 10020, "mName", "生产银两", "mDescribe", "娴妃娘娘派三宝来，教小主如何打碎银。【请小主点击元宝→将银两拖到生产格子内→等待收获】", "mMissionid", 40020, "fPrevid", 10018, "fNextid", 10020, "fName", "生产银两", "fDescribe", "娴妃娘娘派三宝来，教小主如何打碎银。【请小主点击元宝→将银两拖到生产格子内→等待收获】", "fMissionid", 40020)
);
    }

    private static void init4() {
data.map(
    10020, map("id", 10020, "guideId", 615, "mPrevid", 10019, "mNextid", 10021, "mName", "加速生产银两", "mDescribe", "蕊心为小主准备了充足的元宝，让小主加快打碎银的速度，快快拿到更多的银两。【请小主点击元宝→选中在生产的格子→点击加速按钮】", "mMissionid", 40021, "fPrevid", 10019, "fNextid", 10021, "fName", "加速生产银两", "fDescribe", "蕊心为小主准备了充足的元宝，让小主加快打碎银的速度，快快拿到更多的银两。【请小主点击元宝→选中在生产的格子→点击加速按钮】", "fMissionid", 40021),
    10021, map("id", 10021, "guideId", 616, "mPrevid", 10020, "mNextid", 10022, "mName", "建造书案", "mDescribe", "娴妃娘娘派宫女告知小主可以通过书案进行学习来获取经验。【请小主点击皇宫→选择书案→拖拽到地图→建造成功】", "mMissionid", 40022, "fPrevid", 10020, "fNextid", 10022, "fName", "建造书案", "fDescribe", "娴妃娘娘派宫女告知小主可以通过书案进行学习来获取经验。【请小主点击皇宫→选择书案→拖拽到地图→建造成功】", "fMissionid", 40022),
    10022, map("id", 10022, "guideId", 617, "mPrevid", 10021, "mNextid", 10023, "mName", "生产经验", "mDescribe", "三宝前来传话，告知小主生产经验的方法。【请小主建造书案→点击书案→将经验拖到生产格子内→等待收获】", "mMissionid", 40023, "fPrevid", 10021, "fNextid", 10023, "fName", "生产经验", "fDescribe", "三宝前来传话，告知小主生产经验的方法。【请小主建造书案→点击书案→将经验拖到生产格子内→等待收获】", "fMissionid", 40023),
    10023, map("id", 10023, "guideId", 618, "mPrevid", 10022, "mNextid", 10024, "mName", "加速生产经验", "mDescribe", "蕊心为小主准备了充足的元宝，让小主加快学习速度来获取更多的经验。【请小主点击书案→选中在生产的格子→点击加速按钮】", "mMissionid", 40024, "fPrevid", 10022, "fNextid", 10024, "fName", "加速生产经验", "fDescribe", "蕊心为小主准备了充足的元宝，让小主加快学习速度来获取更多的经验。【请小主点击书案→选中在生产的格子→点击加速按钮】", "fMissionid", 40024),
    10024, map("id", 10024, "guideId", 500, "bjId", 1005, "mPrevid", 10023, "mNextid", 10025, "mName", "砍树2棵", "mDescribe", "皇后娘娘见小主近几日格外讨太后欢心，心生怨气，故借口责罚小主去荒地砍树以思过。【请小主点击任意一棵树→将锯子拖拽到所选树干上→砍树成功】", "mMissionid", 40025, "mTalkid", list(2006, 2007, 2008, 2009, 2010), "fPrevid", 10023, "fNextid", 10025, "fName", "砍树2棵", "fDescribe", "皇后娘娘见小主近几日格外讨太后欢心，心生怨气，故借口责罚小主去荒地砍树以思过。【请小主点击任意一棵树→将锯子拖拽到所选树干上→砍树成功】", "fMissionid", 40025, "fTalkid", list(2006, 2007, 2008, 2009, 2010))
);
    }

    private static void init5() {
data.map(
    10025, map("id", 10025, "mPrevid", 10024, "mNextid", 10026, "mName", "除草2块", "mDescribe", "皇后娘娘怒气依旧未消去，因此继续责罚小主去荒地除草以思过。【请小主点击任意一块草地→将镰刀拖拽到所选草地上→除草成功】", "mMissionid", 40026, "fPrevid", 10024, "fNextid", 10026, "fName", "除草2块", "fDescribe", "皇后娘娘怒气依旧未消去，因此继续责罚小主去荒地除草以思过。【请小主点击任意一块草地→将镰刀拖拽到所选草地上→除草成功】", "fMissionid", 40026),
    10026, map("id", 10026, "guideId", 611, "bjId", 1004, "mPrevid", 10025, "mNextid", 10027, "mName", "移动任意建筑", "mDescribe", "近几皇上日龙体抱恙，太后命钦天监观星卜卦得知原是小主寝宫的建筑布局风水不好冲撞了龙体，故命小主移动建筑改变寝宫的布局。【请小主选择任意建筑移动位置】", "mMissionid", 40027, "mTalkid", list(2011, 2012, 2013), "fPrevid", 10025, "fNextid", 10027, "fName", "移动任意建筑", "fDescribe", "近几皇上日龙体抱恙，太后命钦天监观星卜卦得知原是小主寝宫的建筑布局风水不好冲撞了龙体，故命小主移动建筑改变寝宫的布局。【请小主选择任意建筑移动位置】", "fMissionid", 40027, "fTalkid", list(2011, 2012, 2013)),
    10027, map("id", 10027, "guideId", 639, "bjId", 1009, "mPrevid", 10026, "mNextid", 10028, "mName", "建造装饰物", "mDescribe", "娴妃娘娘给小主出主意，在宫中建造一个大雄石狮子能够改善宫中的风水。【请小主点击皇宫处→选择第二个建筑标签→在其下拉菜单下找到大雄石狮子→拖拽到地图空地→建造成功】", "mMissionid", 40028, "mTalkid", list(2014, 2015, 2016), "fPrevid", 10026, "fNextid", 10028, "fName", "建造装饰物", "fDescribe", "娴妃娘娘给小主出主意，在宫中建造一个大雄石狮子能够改善宫中的风水。【请小主点击皇宫处→选择第二个建筑标签→在其下拉菜单下找到大雄石狮子→拖拽到地图空地→建造成功】", "fMissionid", 40028, "fTalkid", list(2014, 2015, 2016)),
    10028, map("id", 10028, "guideId", 610, "mPrevid", 10027, "mNextid", 10031, "mName", "查看繁荣度", "mDescribe", "请小主长按繁荣度按钮即可查看繁荣度。【请小主长按繁荣度显示框】", "mMissionid", 40029, "fPrevid", 10027, "fNextid", 10031, "fName", "查看繁荣度", "fDescribe", "请小主长按繁荣度按钮即可查看繁荣度。【请小主长按繁荣度显示框】", "fMissionid", 40029),
    10029, map("id", 10029, "guideId", 509, "mPrevid", 10058, "mNextid", 10030, "mName", "建造擂台", "mDescribe", "小主需先建造擂台方可开启擂台等功能进行比武。【请小主建造擂台】", "mMissionid", 40030, "fPrevid", 10058, "fNextid", 10030, "fName", "建造擂台", "fDescribe", "小主需先建造擂台方可开启擂台等功能进行比武。【请小主建造擂台】", "fMissionid", 40030)
);
    }

    private static void init6() {
data.map(
    10030, map("id", 10030, "guideId", 601, "bjId", 1008, "mPrevid", 10029, "mNextid", 10059, "mName", "引导比武", "mDescribe", "惠贵妃平时便与众妃嫔不和，现如今约小主前去擂台比武，可见其心怀不轨，小主还是小心为妙。【请小主点击擂台→选择比武对象】", "mMissionid", 40031, "mTalkid", list(2057, 2058, 2059, 2060), "fPrevid", 10029, "fNextid", 10059, "fName", "引导比武", "fDescribe", "惠贵妃平时便与众妃嫔不和，现如今约小主前去擂台比武，可见其心怀不轨，小主还是小心为妙。【请小主点击擂台→选择比武对象】", "fMissionid", 40031, "fTalkid", list(2057, 2058, 2059, 2060)),
    10031, map("id", 10031, "guideId", 609, "bjId", 1003, "mPrevid", 10028, "mNextid", 10032, "mName", "升官正九品", "mDescribe", "皇上下旨，将小主升官正九品。【请小主点击升官按钮→选择挑战→战斗胜利即可升官】", "mMissionid", 40032, "mTalkid", list(2021, 2022, 2023), "fPrevid", 10028, "fNextid", 10032, "fName", "升官正九品", "fDescribe", "皇上下旨，将小主升官正九品。【请小主点击升官按钮→选择挑战→战斗胜利即可升官】", "fMissionid", 40032, "fTalkid", list(2021, 2022, 2023)),
    10032, map("id", 10032, "guideId", 504, "bjId", 1008, "mPrevid", 10031, "mNextid", 10033, "mName", "添加好友一位", "mDescribe", "晴格格见小主近几日心情不好，特意提醒小主多交些好友，既能缓解小主心情他日还能为小主帮忙。【请小主→点击好友按钮→选择添加好友界面→加好友】", "mMissionid", 40033, "mTalkid", list(2042, 2043, 2044), "fPrevid", 10031, "fNextid", 10033, "fName", "添加好友一位", "fDescribe", "晴格格见小主近几日心情不好，特意提醒小主多交些好友，既能缓解小主心情他日还能为小主帮忙。【请小主→点击好友按钮→选择添加好友界面→加好友】", "fMissionid", 40033, "fTalkid", list(2042, 2043, 2044)),
    10033, map("id", 10033, "guideId", 505, "mPrevid", 10032, "mNextid", 10034, "mName", "向好友请安", "mDescribe", "公公提醒小主那日因穿错衣服冲撞了娴妃，建议小主通过请安来消除和娴妃娘娘之间的芥蒂。【请小主点击储秀宫→点击拜访→选择要拜访的好友】", "mMissionid", 40034, "fPrevid", 10032, "fNextid", 10034, "fName", "向好友请安", "fDescribe", "公公提醒小主那日因穿错衣服冲撞了娴妃，建议小主通过请安来消除和娴妃娘娘之间的芥蒂。【请小主点击储秀宫→点击拜访→选择要拜访的好友】", "fMissionid", 40034),
    10034, map("id", 10034, "guideId", 507, "mPrevid", 10033, "mNextid", 10035, "mName", "建造御花园", "mDescribe", "小主需先建造御花园方可开启花房等功能进行种花。【请小主建造御花园】", "mMissionid", 40035, "fPrevid", 10033, "fNextid", 10035, "fName", "建造御花园", "fDescribe", "小主需先建造御花园方可开启花房等功能进行种花。【请小主建造御花园】", "fMissionid", 40035)
);
    }

    private static void init7() {
data.map(
    10035, map("id", 10035, "guideId", 631, "bjId", 1001, "mPrevid", 10034, "mNextid", 10036, "mName", "花房造1朵暖阳初雪", "mDescribe", "娴妃娘娘得知是皇后挑拨她与小主二人关系，隧约小主前去花房种暖阳初雪以增进小主与她的感情。【请小主点击御花园→点击花房→选择暖阳初雪】", "mMissionid", 40036, "mTalkid", list(2050, 2051, 2052), "fPrevid", 10034, "fNextid", 10036, "fName", "花房造1朵暖阳初雪", "fDescribe", "娴妃娘娘得知是皇后挑拨她与小主二人关系，隧约小主前去花房种暖阳初雪以增进小主与她的感情。【请小主点击御花园→点击花房→选择暖阳初雪】", "fMissionid", 40036, "fTalkid", list(2050, 2051, 2052)),
    10036, map("id", 10036, "bjId", 1002, "mPrevid", 10035, "mNextid", 10037, "mName", "用化肥加速1次", "mDescribe", "小主陪同娴妃种花，娴妃甚是开心，特意命人送来化肥供小主使用。【请小主点击御花园→点击花房→选中花苗→点击化肥→施肥成功】", "mMissionid", 40037, "mTalkid", list(2053), "fPrevid", 10035, "fNextid", 10037, "fName", "用化肥加速1次", "fDescribe", "小主陪同娴妃种花，娴妃甚是开心，特意命人送来化肥供小主使用。【请小主点击御花园→点击花房→选中花苗→点击化肥→施肥成功】", "fMissionid", 40037, "fTalkid", list(2053)),
    10037, map("id", 10037, "guideId", 508, "bjId", 1001, "mPrevid", 10036, "mNextid", 10038, "mName", "合成1朵情有独钟", "mDescribe", "令嫔本是花房女婢，如今一跃成为妃嫔，这后宫之中没人看得起这婢女出身的令嫔，唯独小主心善愿意与其交往，因此令嫔时常约小主去花房练习插花。【请小主→击御花园→点击插花→选中情有独钟→点击合成】", "mMissionid", 40038, "mTalkid", list(2054, 2055, 2056), "fPrevid", 10036, "fNextid", 10038, "fName", "合成1朵情有独钟", "fDescribe", "令嫔本是花房女婢，如今一跃成为妃嫔，这后宫之中没人看得起这婢女出身的令嫔，唯独小主心善愿意与其交往，因此令嫔时常约小主去花房练习插花。【请小主→击御花园→点击插花→选中情有独钟→点击合成】", "fMissionid", 40038, "fTalkid", list(2054, 2055, 2056)),
    10038, map("id", 10038, "guideId", 506, "mPrevid", 10037, "mNextid", 10039, "mName", "赠送好友情有独钟", "mDescribe", "公公给小主出主意，建议小主将插好的情有独钟赠予好友，有利于增加小主在宫中的魅力值。【请小主赠送任意好友一朵情有独钟】", "mMissionid", 40039, "fPrevid", 10037, "fNextid", 10039, "fName", "赠送好友情有独钟", "fDescribe", "公公给小主出主意，建议小主将插好的情有独钟赠予好友，有利于增加小主在宫中的魅力值。【请小主赠送任意好友一朵情有独钟】", "fMissionid", 40039),
    10039, map("id", 10039, "guideId", 512, "mPrevid", 10038, "mNextid", 10040, "mName", "建造织布机", "mDescribe", "小主需先建造织布机方可进行织布哦。【请小主建造织布机】", "mMissionid", 40040, "fPrevid", 10038, "fNextid", 10040, "fName", "建造织布机", "fDescribe", "小主需先建造织布机方可进行织布哦。【请小主建造织布机】", "fMissionid", 40040)
);
    }

    private static void init8() {
data.map(
    10040, map("id", 10040, "guideId", 604, "bjId", 1002, "mPrevid", 10039, "mNextid", 10041, "mName", "收获棉布", "mDescribe", "皇后娘娘刁难小主，让小主前去负责打造装备，幸有公公提醒需准备好棉布等材料才可。【请小主织造相应数量的棉布】", "mMissionid", 40041, "mTalkid", list(2061, 2062, 2003), "fPrevid", 10039, "fNextid", 10041, "fName", "收获棉布", "fDescribe", "皇后娘娘刁难小主，让小主前去负责打造装备，幸有公公提醒需准备好棉布等材料才可。【请小主织造相应数量的棉布】", "fMissionid", 40041, "fTalkid", list(2061, 2062, 2003)),
    10041, map("id", 10041, "guideId", 632, "mPrevid", 10040, "mNextid", 10042, "mName", "建造矿洞", "mDescribe", "小主需先建造矿洞方可进行采矿哦。【请小主建造矿洞】", "mMissionid", 40042, "fPrevid", 10040, "fNextid", 10042, "fName", "建造矿洞", "fDescribe", "小主需先建造矿洞方可进行采矿哦。【请小主建造矿洞】", "fMissionid", 40042),
    10042, map("id", 10042, "guideId", 603, "mPrevid", 10041, "mNextid", 10043, "mName", "采矿石", "mDescribe", "矿石是打造装备必不可少的，小主一定要准备齐了再去打造装备。【请小主挖出相应数量的矿石】", "mMissionid", 40043, "fPrevid", 10041, "fNextid", 10043, "fName", "采矿石", "fDescribe", "矿石是打造装备必不可少的，小主一定要准备齐了再去打造装备。【请小主挖出相应数量的矿石】", "fMissionid", 40043),
    10043, map("id", 10043, "guideId", 501, "mPrevid", 10042, "mNextid", 10044, "mName", "升级技能5次", "mDescribe", "公公见小主武力尚有欠缺，好心提醒小主可通过升级技能来提升自己的能力。【请小主点击技能→升级技能】", "mMissionid", 40044, "fPrevid", 10042, "fNextid", 10044, "fName", "升级技能5次", "fDescribe", "公公见小主武力尚有欠缺，好心提醒小主可通过升级技能来提升自己的能力。【请小主点击技能→升级技能】", "fMissionid", 40044),
    10044, map("id", 10044, "bjId", 1001, "mPrevid", 10043, "mNextid", 10045, "mName", "上阵4个仆从", "mDescribe", "蕊心为小主出谋划策，建议小主招募仆从培养自己的势力才能不那么轻易被打败，方能站稳脚跟。【请小主点击仆从→点击第二个标签招募→选中所需仆从→点击招募】", "mMissionid", 40045, "mTalkid", list(2030, 2031), "fPrevid", 10043, "fNextid", 10045, "fName", "上阵4个仆从", "fDescribe", "蕊心为小主出谋划策，建议小主招募仆从培养自己的势力才能不那么轻易被打败，方能站稳脚跟。【请小主点击仆从→点击第二个标签招募→选中所需仆从→点击招募】", "fMissionid", 40045, "fTalkid", list(2030, 2031))
);
    }

    private static void init9() {
data.map(
    10045, map("id", 10045, "bjId", 1003, "mPrevid", 10044, "mNextid", 10046, "mName", "升级从八品", "mDescribe", "皇上下旨，将小主升官从八品。【请小主点击官职→点击提升官职】", "mMissionid", 40046, "mTalkid", list(2032, 2033), "fPrevid", 10044, "fNextid", 10046, "fName", "升级从八品", "fDescribe", "皇上下旨，将小主升官从八品。【请小主点击官职→点击提升官职】", "fMissionid", 40046, "fTalkid", list(2032, 2033)),
    10046, map("id", 10046, "guideId", 513, "mPrevid", 10045, "mNextid", 10047, "mName", "建造造办处", "mDescribe", "小主需先建造造办处方可开启打造等功能进行打造装备。【请小主建造造办处】", "mMissionid", 40047, "fPrevid", 10045, "fNextid", 10047, "fName", "建造造办处", "fDescribe", "小主需先建造造办处方可开启打造等功能进行打造装备。【请小主建造造办处】", "fMissionid", 40047),
    10047, map("id", 10047, "guideId", 605, "mPrevid", 10046, "mNextid", 10048, "mName", "造装备", "mDescribe", "材料准备齐全，即刻动身前去打造装备，以防皇后娘娘再找借口责罚小主。【请小主点击造办处→点击打造→选择要打造的武器装备】", "mMissionid", 40048, "fPrevid", 10046, "fNextid", 10048, "fName", "造装备", "fDescribe", "材料准备齐全，即刻动身前去打造装备，以防皇后娘娘再找借口责罚小主。【请小主点击造办处→点击打造→选择要打造的武器装备】", "fMissionid", 40048),
    10048, map("id", 10048, "mPrevid", 10047, "mNextid", 10049, "mName", "装备分解", "mDescribe", "公公见小主背包快要满了，好心提醒小主可以通过装备分解获取碎片来减少背包的库存。【请小主点击背包→选择要分解的装备→点击分解→分解成功】", "mMissionid", 40049, "fPrevid", 10047, "fNextid", 10049, "fName", "装备分解", "fDescribe", "公公见小主背包快要满了，好心提醒小主可以通过装备分解获取碎片来减少背包的库存。【请小主点击背包→选择要分解的装备→点击分解→分解成功】", "fMissionid", 40049),
    10049, map("id", 10049, "guideId", 502, "mPrevid", 10048, "mNextid", 10050, "mName", "兑换图纸", "mDescribe", "娴妃娘娘托人带话给小主，小主前几日分解的碎片可兑换成打造装备的图纸，真可谓大有用途。【请小主点击商城→点击兑换界面→选择要兑换的装备图纸】", "mMissionid", 40050, "fPrevid", 10048, "fNextid", 10050, "fName", "兑换图纸", "fDescribe", "娴妃娘娘托人带话给小主，小主前几日分解的碎片可兑换成打造装备的图纸，真可谓大有用途。【请小主点击商城→点击兑换界面→选择要兑换的装备图纸】", "fMissionid", 40050)
);
    }

    private static void init10() {
data.map(
    10050, map("id", 10050, "mPrevid", 10049, "mNextid", 10052, "mName", "通关普通关卡第二章", "mDescribe", "通关普通关卡第二章", "mMissionid", 40051, "fPrevid", 10049, "fNextid", 10052, "fName", "通关普通关卡第二章", "fDescribe", "通关普通关卡第二章", "fMissionid", 40051),
    10051, map("id", 10051, "guideId", 503, "mPrevid", 10071, "mNextid", 10072, "mName", "建造保和殿", "mDescribe", "小主需先建造保和殿方可开启官职等功能进行升官品。", "mMissionid", 40052, "fPrevid", 10071, "fNextid", 10072, "fName", "建造保和殿", "fDescribe", "小主需先建造保和殿方可开启官职等功能进行升官品。", "fMissionid", 40052),
    10052, map("id", 10052, "mPrevid", 10050, "mNextid", 10053, "mName", "战斗力达到12000", "mDescribe", "公公提醒小主，若想升官职就需战斗力达到12000。", "mMissionid", 40053, "fPrevid", 10050, "fNextid", 10053, "fName", "战斗力达到12000", "fDescribe", "公公提醒小主，若想升官职就需战斗力达到12000。", "fMissionid", 40053),
    10053, map("id", 10053, "bjId", 1009, "mPrevid", 10052, "mNextid", 10054, "mName", "在聊天综合频道中说一句话", "mDescribe", "皇后娘娘见小主独得恩宠心生嫉妒，召见小主并讥讽小主一番，强行命令小主跟各宫娘娘前去聊天说话。", "mMissionid", 40054, "mTalkid", list(2045, 2046, 2047, 2048, 2049), "fPrevid", 10052, "fNextid", 10054, "fName", "在聊天综合频道中说一句话", "fDescribe", "皇后娘娘见小主独得恩宠心生嫉妒，召见小主并讥讽小主一番，强行命令小主跟各宫娘娘前去聊天说话。", "fMissionid", 40054, "fTalkid", list(2045, 2046, 2047, 2048, 2049)),
    10054, map("id", 10054, "mPrevid", 10053, "mNextid", 10055, "mName", "升级正八品", "mDescribe", "皇上有旨，夸小主在后宫人缘极好，魅力值达到99，隧将小主升官正八品。", "mMissionid", 40055, "fPrevid", 10053, "fNextid", 10055, "fName", "升级正八品", "fDescribe", "皇上有旨，夸小主在后宫人缘极好，魅力值达到99，隧将小主升官正八品。", "fMissionid", 40055)
);
    }

    private static void init11() {
data.map(
    10055, map("id", 10055, "mPrevid", 10054, "mNextid", 10056, "mName", "刷宝", "mDescribe", "公公提醒小主，通过剧情刷宝可获取更多的经验和奖励。（点击剧情→选中主要关卡→点击刷宝按钮）", "mMissionid", 40056, "fPrevid", 10054, "fNextid", 10056, "fName", "刷宝", "fDescribe", "公公提醒小主，通过剧情刷宝可获取更多的经验和奖励。（点击剧情→选中主要关卡→点击刷宝按钮）", "fMissionid", 40056),
    10056, map("id", 10056, "mPrevid", 10055, "mNextid", 10057, "mName", "收30朵满天星", "mDescribe", "皇后身边的姑姑前来带话，请小主收满天星供皇后娘娘近日使用。", "mMissionid", 40057, "fPrevid", 10055, "fNextid", 10057, "fName", "收30朵满天星", "fDescribe", "皇后身边的姑姑前来带话，请小主收满天星供皇后娘娘近日使用。", "fMissionid", 40057),
    10057, map("id", 10057, "mPrevid", 10056, "mNextid", 10058, "mName", "合成1朵桃之妖妖", "mDescribe", "皇后娘娘命小主带上满天星前去花房种新品种花。", "mMissionid", 40058, "fPrevid", 10056, "fNextid", 10058, "fName", "合成1朵桃之妖妖", "fDescribe", "皇后娘娘命小主带上满天星前去花房种新品种花。", "fMissionid", 40058),
    10058, map("id", 10058, "mPrevid", 10057, "mNextid", 10029, "mName", "赠送好友桃之妖妖", "mDescribe", "小丫鬟给小主出主意，建议小主将插好的桃之妖妖送后宫小主。", "mMissionid", 40059, "fPrevid", 10057, "fNextid", 10029, "fName", "赠送好友桃之妖妖", "fDescribe", "小丫鬟给小主出主意，建议小主将插好的桃之妖妖送后宫小主。", "fMissionid", 40059),
    10059, map("id", 10059, "mPrevid", 10030, "mNextid", 10060, "mName", "购买任意一件装扮", "mDescribe", "小丫鬟为小主提议，前去尚衣局置办一件华美的装扮或许会让自己在太后的寿辰之时给皇上眼前一亮的感觉，赢得宠爱。", "mMissionid", 40060, "fPrevid", 10030, "fNextid", 10060, "fName", "购买任意一件装扮", "fDescribe", "小丫鬟为小主提议，前去尚衣局置办一件华美的装扮或许会让自己在太后的寿辰之时给皇上眼前一亮的感觉，赢得宠爱。", "fMissionid", 40060)
);
    }

    private static void init12() {
data.map(
    10060, map("id", 10060, "mPrevid", 10059, "mNextid", 10061, "mName", "穿戴新购买的饰品", "mDescribe", "小主，快将新置办的饰品穿戴上吧！", "mMissionid", 40061, "fPrevid", 10059, "fNextid", 10061, "fName", "穿戴新购买的饰品", "fDescribe", "小主，快将新置办的饰品穿戴上吧！", "fMissionid", 40061),
    10061, map("id", 10061, "mPrevid", 10060, "mNextid", 10066, "mName", "拜访好友", "mDescribe", "小主，近日多去各位小主家走走看看巩固一下感情。", "mMissionid", 40062, "fPrevid", 10060, "fNextid", 10066, "fName", "拜访好友", "fDescribe", "小主，近日多去各位小主家走走看看巩固一下感情。", "fMissionid", 40062),
    10062, map("id", 10062, "mPrevid", 10061, "mNextid", 10066, "mName", "建造太液池", "mDescribe", "公公提醒小主，近日好多小主在宫中修葺了太液池，小主您也建造一个吧。", "mMissionid", 40063, "fPrevid", 10061, "fNextid", 10066, "fName", "建造太液池", "fDescribe", "公公提醒小主，近日好多小主在宫中修葺了太液池，小主您也建造一个吧。", "fMissionid", 40063),
    10063, map("id", 10063, "mPrevid", 10061, "mNextid", 10066, "mName", "钓一次鱼", "mDescribe", "闲来无事，王爷约皇上前去太液池垂钓，皇上特命小主一同前去。", "mMissionid", 40064, "fPrevid", 10061, "fNextid", 10066, "fName", "钓一次鱼", "fDescribe", "闲来无事，王爷约皇上前去太液池垂钓，皇上特命小主一同前去。", "fMissionid", 40064),
    10064, map("id", 10064, "mPrevid", 10061, "mNextid", 10066, "mName", "建造一个造渔具的工作台", "mDescribe", "小主，您可以通过造渔具的工作台制造鱼钩哦~", "mMissionid", 40065, "fPrevid", 10061, "fNextid", 10066, "fName", "建造一个造渔具的工作台", "fDescribe", "小主，您可以通过造渔具的工作台制造鱼钩哦~", "fMissionid", 40065)
);
    }

    private static void init13() {
data.map(
    10065, map("id", 10065, "mPrevid", 10061, "mNextid", 10066, "mName", "造2枚铜鱼钩", "mDescribe", "小主，您可以自己打制一些鱼钩以备以后钓鱼时用哦~", "mMissionid", 40066, "fPrevid", 10061, "fNextid", 10066, "fName", "造2枚铜鱼钩", "fDescribe", "小主，您可以自己打制一些鱼钩以备以后钓鱼时用哦~", "fMissionid", 40066),
    10066, map("id", 10066, "mPrevid", 10065, "mNextid", 10067, "mName", "收获5块棉布", "mDescribe", "小主，奴婢听说您想打造些装备，小主要先行准备材料哦！", "mMissionid", 40067, "fPrevid", 10065, "fNextid", 10067, "fName", "收获5块棉布", "fDescribe", "小主，奴婢听说您想打造些装备，小主要先行准备材料哦！", "fMissionid", 40067),
    10067, map("id", 10067, "mPrevid", 10066, "mNextid", 10068, "mName", "收获5块铜矿", "mDescribe", "小主，除了棉布外，您还需要准备打造用的矿石哦~", "mMissionid", 40068, "fPrevid", 10066, "fNextid", 10068, "fName", "收获5块铜矿", "fDescribe", "小主，除了棉布外，您还需要准备打造用的矿石哦~", "fMissionid", 40068),
    10068, map("id", 10068, "mPrevid", 10067, "mNextid", 10069, "mName", "造装备缎带", "mDescribe", "小主，打造装备的材料都已备齐了，请小主前去打造装备吧~", "mMissionid", 40069, "fPrevid", 10067, "fNextid", 10069, "fName", "造装备缎带", "fDescribe", "小主，打造装备的材料都已备齐了，请小主前去打造装备吧~", "fMissionid", 40069),
    10069, map("id", 10069, "mPrevid", 10068, "mNextid", 10070, "mName", "任意技能升级", "mDescribe", "小主最近是否感觉比武力不从心了？小主快试着吃些内丹升升技能吧！", "mMissionid", 40070, "fPrevid", 10068, "fNextid", 10070, "fName", "任意技能升级", "fDescribe", "小主最近是否感觉比武力不从心了？小主快试着吃些内丹升升技能吧！", "fMissionid", 40070)
);
    }

    private static void init14() {
data.map(
    10070, map("id", 10070, "mPrevid", 10069, "mNextid", 10071, "mName", "招募仆从到6个", "mDescribe", "请小主招募仆到6个，心腹越多在后宫地位越稳固哦~", "mMissionid", 40071, "fPrevid", 10069, "fNextid", 10071, "fName", "招募仆从到6个", "fDescribe", "请小主招募仆到6个，心腹越多在后宫地位越稳固哦~", "fMissionid", 40071),
    10071, map("id", 10071, "mPrevid", 10070, "mNextid", 10051, "mName", "升官至从七品", "mDescribe", "恭喜小主晋升一级官品，请小主前去领旨谢恩吧！", "mMissionid", 40072, "fPrevid", 10070, "fNextid", 10051, "fName", "升官至从七品", "fDescribe", "恭喜小主晋升一级官品，请小主前去领旨谢恩吧！", "fMissionid", 40072),
    10072, map("id", 10072, "mPrevid", 10051, "mNextid", 10074, "mName", "换衣服", "mDescribe", "听闻小主即将娶亲，请小主购买灿金棉褂吉服（天子）", "mMissionid", 40073, "fPrevid", 10051, "fNextid", 10074, "fName", "换衣服", "fDescribe", "听闻小主即将娶亲，请小主购买灿金棉褂吉服（天子）", "fMissionid", 40073),
    10073, map("id", 10073, "mPrevid", 10072, "mNextid", 10074, "mName", "结婚", "mDescribe", "请小主向喜欢的人儿表白吧！快马加鞭送聘书吧！", "mMissionid", 40074, "fPrevid", 10072, "fNextid", 10074, "fName", "结婚", "fDescribe", "请小主向喜欢的人儿表白吧！快马加鞭送聘书吧！", "fMissionid", 40074),
    10074, map("id", 10074, "mPrevid", 10072, "mNextid", 10075, "mName", "送花一次", "mDescribe", "恭喜小主完成大婚，请为自己另一半送上美丽的飘花增加结拜双方的亲密度吧！", "mMissionid", 40075, "fPrevid", 10072, "fNextid", 10075, "fName", "送花一次", "fDescribe", "恭喜小主完成大婚，请为自己另一半送上美丽的飘花增加结拜双方的亲密度吧！", "fMissionid", 40075)
);
    }

    private static void init15() {
data.map(
    10075, map("id", 10075, "mPrevid", 10074, "mNextid", 10076, "mName", "收获10块棉布", "mDescribe", "请小主收获已纺织好的棉布吧！", "mMissionid", 40076, "fPrevid", 10074, "fNextid", 10076, "fName", "收获10块棉布", "fDescribe", "请小主收获已纺织好的棉布吧！", "fMissionid", 40076),
    10076, map("id", 10076, "mPrevid", 10075, "mNextid", 10077, "mName", "收获10瓶明黄色", "mDescribe", "请小主再去收取已经调制好的染料吧！", "mMissionid", 40077, "fPrevid", 10075, "fNextid", 10077, "fName", "收获10瓶明黄色", "fDescribe", "请小主再去收取已经调制好的染料吧！", "fMissionid", 40077),
    10077, map("id", 10077, "mPrevid", 10076, "mNextid", 10078, "mName", "制衣", "mDescribe", "小主，制衣的布匹和染料都已准备好了，请小主只做一件新衣吧！", "mMissionid", 40078, "fPrevid", 10076, "fNextid", 10078, "fName", "制衣", "fDescribe", "小主，制衣的布匹和染料都已准备好了，请小主只做一件新衣吧！", "fMissionid", 40078),
    10078, map("id", 10078, "mPrevid", 10077, "mNextid", 10079, "mName", "合成1枚2级宝石", "mDescribe", "请小主合成一个2级普通宝石。", "mMissionid", 40079, "fPrevid", 10077, "fNextid", 10079, "fName", "合成1枚2级宝石", "fDescribe", "请小主合成一个2级普通宝石。", "fMissionid", 40079),
    10079, map("id", 10079, "mPrevid", 10078, "mNextid", 10080, "mName", "镶嵌宝石1次", "mDescribe", "请小主将合成的宝石镶嵌到装备上。", "mMissionid", 40080, "fPrevid", 10078, "fNextid", 10080, "fName", "镶嵌宝石1次", "fDescribe", "请小主将合成的宝石镶嵌到装备上。", "fMissionid", 40080)
);
    }

    private static void init16() {
data.map(
    10080, map("id", 10080, "guideId", 738, "mPrevid", 10079, "mNextid", 10081, "mName", "探宝1次", "mDescribe", "小主，奴婢听说内务府新到一批宝物，请小主前去探宝吧！", "mMissionid", 40081, "fPrevid", 10079, "fNextid", 10081, "fName", "探宝1次", "fDescribe", "小主，奴婢听说内务府新到一批宝物，请小主前去探宝吧！", "fMissionid", 40081),
    10081, map("id", 10081, "mPrevid", 10080, "mNextid", 10082, "mName", "探宝3次", "mDescribe", "听闻探宝结束后会获得稀有大礼包，小主在探3次吧，加油哦~", "mMissionid", 40082, "fPrevid", 10080, "fNextid", 10082, "fName", "探宝3次", "fDescribe", "听闻探宝结束后会获得稀有大礼包，小主在探3次吧，加油哦~", "fMissionid", 40082),
    10082, map("id", 10082, "mPrevid", 10081, "mNextid", 10083, "mName", "升官至正七品", "mDescribe", "恭喜小主晋升一级官品，请小主前去领旨谢恩吧！", "mMissionid", 40083, "fPrevid", 10081, "fNextid", 10083, "fName", "升官至正七品", "fDescribe", "恭喜小主晋升一级官品，请小主前去领旨谢恩吧！", "fMissionid", 40083),
    10083, map("id", 10083, "mPrevid", 10082, "mNextid", 10084, "mName", "砍树5棵", "mDescribe", "小主，我们需要扩充宫内土地以便日后建造新的建筑哦~请小主砍除5棵杂树。", "mMissionid", 40084, "fPrevid", 10082, "fNextid", 10084, "fName", "砍树5棵", "fDescribe", "小主，我们需要扩充宫内土地以便日后建造新的建筑哦~请小主砍除5棵杂树。", "fMissionid", 40084),
    10084, map("id", 10084, "mPrevid", 10083, "mNextid", 10085, "mName", "除草5块", "mDescribe", "请小主继续清楚5块杂草。", "mMissionid", 40085, "fPrevid", 10083, "fNextid", 10085, "fName", "除草5块", "fDescribe", "请小主继续清楚5块杂草。", "fMissionid", 40085)
);
    }

    private static void init17() {
data.map(
    10085, map("id", 10085, "mPrevid", 10084, "mNextid", 10086, "mName", "炸小石头5块", "mDescribe", "如果有杂乱小石块，也请小主一并清除，这样我们宫中方能更加敞亮哟~", "mMissionid", 40086, "fPrevid", 10084, "fNextid", 10086, "fName", "炸小石头5块", "fDescribe", "如果有杂乱小石块，也请小主一并清除，这样我们宫中方能更加敞亮哟~", "fMissionid", 40086),
    10086, map("id", 10086, "mPrevid", 10085, "mNextid", 10087, "mName", "供养连理枝5次", "mDescribe", "小主，连理枝是结拜二人爱的象征，请小主供养连理枝5次。", "mMissionid", 40087, "fPrevid", 10085, "fNextid", 10087, "fName", "供养连理枝5次", "fDescribe", "小主，连理枝是结拜二人爱的象征，请小主供养连理枝5次。", "fMissionid", 40087),
    10087, map("id", 10087, "mPrevid", 10086, "mNextid", 10088, "mName", "提升技能1次", "mDescribe", "小主，适时地提升自己的技能有助于自己在后宫中根深蒂固哦~", "mMissionid", 40088, "fPrevid", 10086, "fNextid", 10088, "fName", "提升技能1次", "fDescribe", "小主，适时地提升自己的技能有助于自己在后宫中根深蒂固哦~", "fMissionid", 40088),
    10088, map("id", 10088, "mPrevid", 10087, "mNextid", 10089, "mName", "升官至从六品", "mDescribe", "恭喜小主晋升一级官品，请小主前去领旨谢恩吧！", "mMissionid", 40089, "fPrevid", 10087, "fNextid", 10089, "fName", "升官至从六品", "fDescribe", "恭喜小主晋升一级官品，请小主前去领旨谢恩吧！", "fMissionid", 40089),
    10089, map("id", 10089, "mPrevid", 10088, "mNextid", 10090, "mName", "升级1个花盆", "mDescribe", "小主，奴婢听闻皇宫御花园新进贡一批高级花盆，小主快去升级更换1个花盆吧！", "mMissionid", 40090, "fPrevid", 10088, "fNextid", 10090, "fName", "升级1个花盆", "fDescribe", "小主，奴婢听闻皇宫御花园新进贡一批高级花盆，小主快去升级更换1个花盆吧！", "fMissionid", 40090)
);
    }

    private static void init18() {
data.map(
    10090, map("id", 10090, "mPrevid", 10089, "mNextid", 10091, "mName", "合成一朵我心悦你", "mDescribe", "换上了华美的花盆，也请小主试着合成1朵少见的我心悦你吧！", "mMissionid", 40091, "fPrevid", 10089, "fNextid", 10091, "fName", "合成一朵我心悦你", "fDescribe", "换上了华美的花盆，也请小主试着合成1朵少见的我心悦你吧！", "fMissionid", 40091),
    10091, map("id", 10091, "mPrevid", 10090, "mNextid", 10092, "mName", "送花我心悦你", "mDescribe", "我心悦你送给最爱的人，请小主将我心悦你送给伴侣吧！", "mMissionid", 40092, "fPrevid", 10090, "fNextid", 10092, "fName", "送花我心悦你", "fDescribe", "我心悦你送给最爱的人，请小主将我心悦你送给伴侣吧！", "fMissionid", 40092),
    10092, map("id", 10092, "mPrevid", 10091, "mNextid", 10093, "mName", "进贡2次", "mDescribe", "小主，皇上下旨各宫小主均需向西域亲王进贡2件珍宝。", "mMissionid", 40093, "fPrevid", 10091, "fNextid", 10093, "fName", "进贡2次", "fDescribe", "小主，皇上下旨各宫小主均需向西域亲王进贡2件珍宝。", "fMissionid", 40093),
    10093, map("id", 10093, "mPrevid", 10092, "mNextid", 10094, "mName", "做5单礼单任务", "mDescribe", "小主，近日要与各宫妃嫔常常多走动哦~请小主完成2单礼单任务。", "mMissionid", 40094, "fPrevid", 10092, "fNextid", 10094, "fName", "做5单礼单任务", "fDescribe", "小主，近日要与各宫妃嫔常常多走动哦~请小主完成2单礼单任务。", "fMissionid", 40094),
    10094, map("id", 10094, "mPrevid", 10093, "mNextid", 10098, "mName", "做2单帮派任务", "mDescribe", "小主，帮派之间的联络也必不可少哦~请小主完成2单帮派任务。", "mMissionid", 40095, "fPrevid", 10093, "fNextid", 10098, "fName", "做2单帮派任务", "fDescribe", "小主，帮派之间的联络也必不可少哦~请小主完成2单帮派任务。", "fMissionid", 40095)
);
    }

    private static void init19() {
data.map(
    10095, map("id", 10095, "end", true, "mPrevid", 10094, "mNextid", 10098, "mName", "熔炼1枚高级宝石", "mDescribe", "小主五级普通宝石已不够我们使用了，请小主使用熔炼炉任意融合一枚5级的高级宝石。", "mMissionid", 40096, "fPrevid", 10094, "fNextid", 10098, "fName", "熔炼1枚高级宝石", "fDescribe", "小主五级普通宝石已不够我们使用了，请小主使用熔炼炉任意融合一枚五级的高级宝石。", "fMissionid", 40096),
    10096, map("id", 10096, "mPrevid", 10098, "mNextid", 10097, "mName", "收百合花", "mDescribe", "听闻御花房又有新进贡的香水百合，请小主移步御花房种植百合花。", "mMissionid", 40100, "fPrevid", 10098, "fNextid", 10097, "fName", "收百合花", "fDescribe", "听闻御花房又有新进贡的香水百合，请小主移步御花房种植百合花。", "fMissionid", 40100),
    10097, map("id", 10097, "mPrevid", 10096, "mNextid", 10099, "mName", "镶嵌1级宝石", "mDescribe", "小主，多谢小主体恤奴婢们，请为所有仆从镶嵌上宝石。", "mMissionid", 40097, "fPrevid", 10096, "fNextid", 10099, "fName", "镶嵌1级宝石", "fDescribe", "小主，多谢小主体恤奴婢们，请为所有仆从镶嵌上宝石。", "fMissionid", 40097),
    10098, map("id", 10098, "mPrevid", 20065, "mNextid", 10096, "mName", "登陆第二天", "mDescribe", "小主，请您检查登陆二天哦~勤劳的小主才会讨太后欢心。", "mMissionid", 40099, "fPrevid", 20065, "fNextid", 10096, "fName", "登陆第二天", "fDescribe", "小主，请您检查登陆二天哦~勤劳的小主才会讨太后欢心。", "fMissionid", 40099),
    10099, map("id", 10099, "mPrevid", 10097, "mNextid", 10100, "mName", "强化（2）", "mDescribe", "小主体力不是特别良好，请小主赶紧强化自己吧。", "mMissionid", 40098, "fPrevid", 10097, "fNextid", 10100, "fName", "强化（2）", "fDescribe", "小主体力不是特别良好，请小主赶紧强化自己吧。", "fMissionid", 40098)
);
    }

    private static void init20() {
data.map(
    10100, map("id", 10100, "mPrevid", 10099, "mNextid", 10101, "mName", "剧情战斗第三章", "mDescribe", "老奴恭迎小主，小主可是想继续了解娴妃娘娘过往，请打通剧情第三章全部关卡。", "mMissionid", 40101, "fPrevid", 10099, "fNextid", 10101, "fName", "剧情战斗第三章", "fDescribe", "老奴恭迎小主，小主可是想继续了解娴妃娘娘过往，请打通剧情第三章全部关卡。", "fMissionid", 40101),
    10101, map("id", 10101, "mPrevid", 10100, "mNextid", 10102, "mName", "礼单任务（2）", "mDescribe", "近日小主与各宫妃嫔罕有走动，请小主完成礼单任务拉进与后宫妃嫔的关系。", "mMissionid", 40102, "fPrevid", 10100, "fNextid", 10102, "fName", "礼单任务（2）", "fDescribe", "近日小主与各宫妃嫔罕有走动，请小主完成礼单任务拉进与后宫妃嫔的关系。", "fMissionid", 40102),
    10102, map("id", 10102, "mPrevid", 10101, "mNextid", 10103, "mName", "砍树（2）", "mDescribe", "小主在这后宫中已然小心翼翼，不知怎的还是得罪了贵妃娘娘，被罚来御花园砍树。", "mMissionid", 40103, "fPrevid", 10101, "fNextid", 10103, "fName", "砍树（2）", "fDescribe", "小主在这后宫中已然小心翼翼，不知怎的还是得罪了贵妃娘娘，被罚来御花园砍树。", "fMissionid", 40103),
    10103, map("id", 10103, "mPrevid", 10102, "mNextid", 10105, "mName", "除草（2）", "mDescribe", "小主都已思过，然而贵妃依旧怒气不消，让小主再去除些杂草方可息怒。", "mMissionid", 40104, "fPrevid", 10102, "fNextid", 10105, "fName", "除草（2）", "fDescribe", "小主都已思过，然而贵妃依旧怒气不消，让小主再去除些杂草方可息怒。", "fMissionid", 40104),
    10105, map("id", 10105, "mPrevid", 10103, "mNextid", 10106, "mName", "收布（2）", "mDescribe", "请小主收获已纺织好的布料吧！", "mMissionid", 40106, "fPrevid", 10103, "fNextid", 10106, "fName", "收布（2）", "fDescribe", "请小主收获已纺织好的布料吧！", "fMissionid", 40106)
);
    }

    private static void init21() {
data.map(
    10106, map("id", 10106, "mPrevid", 10105, "mNextid", 10107, "mName", "采矿石（2)", "mDescribe", "请小主再去收取已经开采好的矿石吧！", "mMissionid", 40107, "fPrevid", 10105, "fNextid", 10107, "fName", "采矿石（2)", "fDescribe", "请小主再去收取已经开采好的矿石吧！", "fMissionid", 40107),
    10107, map("id", 10107, "mPrevid", 10106, "mNextid", 10108, "mName", "升级技能（2）", "mDescribe", "小主，适时地提升自己的技能有助于自己在后宫中根深蒂固哦~", "mMissionid", 40108, "fPrevid", 10106, "fNextid", 10108, "fName", "升级技能（2）", "fDescribe", "小主，适时地提升自己的技能有助于自己在后宫中根深蒂固哦~", "fMissionid", 40108),
    10108, map("id", 10108, "mPrevid", 10107, "mNextid", 10109, "mName", "造装备（2）", "mDescribe", "俗话说的好，好马配好鞍，小主除了自身提升以外也要有好的装备相匹配。", "mMissionid", 40109, "fPrevid", 10107, "fNextid", 10109, "fName", "造装备（2）", "fDescribe", "俗话说的好，好马配好鞍，小主除了自身提升以外也要有好的装备相匹配。", "fMissionid", 40109),
    10109, map("id", 10109, "mPrevid", 10108, "mNextid", 10110, "mName", "战斗力（2）", "mDescribe", "老奴恭迎小主，小主可是想继续了解娴妃娘娘过往，请打通剧情第四章全部关卡。", "mMissionid", 40111, "fPrevid", 10108, "fNextid", 10110, "fName", "战斗力（2）", "fDescribe", "老奴恭迎小主，小主可是想继续了解娴妃娘娘过往，请打通剧情第四章全部关卡。", "fMissionid", 40111),
    10110, map("id", 10110, "mPrevid", 10109, "mNextid", 10111, "mName", "剧情战斗第四章", "mDescribe", "请小主努力提升战力吧！", "mMissionid", 40110, "fPrevid", 10109, "fNextid", 10111, "fName", "剧情战斗第四章", "fDescribe", "请小主努力提升战力吧！", "fMissionid", 40110)
);
    }

    private static void init22() {
data.map(
    10111, map("id", 10111, "mPrevid", 10110, "mNextid", 10112, "mName", "聊天一次（2）", "mDescribe", "请小主在聊天综合频道中聊天，近日多与各位小主家聊聊天巩固一下感情。", "mMissionid", 40112, "fPrevid", 10110, "fNextid", 10112, "fName", "聊天一次（2）", "fDescribe", "请小主在聊天综合频道中聊天，近日多与各位小主家聊聊天巩固一下感情。", "fMissionid", 40112),
    10112, map("id", 10112, "mPrevid", 10111, "mNextid", 10113, "mName", "刷宝（2）", "mDescribe", "小主，天降馅饼的好事情何不去拼拼手气，请小主前去任意剧情进行刷宝吧。", "mMissionid", 40113, "fPrevid", 10111, "fNextid", 10113, "fName", "刷宝（2）", "fDescribe", "小主，天降馅饼的好事情何不去拼拼手气，请小主前去任意剧情进行刷宝吧。", "fMissionid", 40113),
    10113, map("id", 10113, "mPrevid", 10112, "mNextid", 10114, "mName", "收10次蒲公英", "mDescribe", "听闻御花房又有新进贡的蒲公英，请小主移步御花房种植蒲公英。", "mMissionid", 40114, "fPrevid", 10112, "fNextid", 10114, "fName", "收10次蒲公英", "fDescribe", "听闻御花房又有新进贡的蒲公英，请小主移步御花房种植蒲公英。", "fMissionid", 40114),
    10114, map("id", 10114, "mPrevid", 10113, "mNextid", 10115, "mName", "合成我美人含笑", "mDescribe", "小主近日烦闷，可前去御花房种植美人含笑", "mMissionid", 40115, "fPrevid", 10113, "fNextid", 10115, "fName", "合成我美人含笑", "fDescribe", "小主近日烦闷，可前去御花房种植美人含笑", "fMissionid", 40115),
    10115, map("id", 10115, "mPrevid", 10114, "mNextid", 10116, "mName", "赠送好友美人含笑", "mDescribe", "小丫鬟给小主出主意，建议小主将插好的美人含笑送后宫小主。", "mMissionid", 40116, "fPrevid", 10114, "fNextid", 10116, "fName", "赠送好友美人含笑", "fDescribe", "小丫鬟给小主出主意，建议小主将插好的美人含笑送后宫小主。", "fMissionid", 40116)
);
    }

    private static void init23() {
data.map(
    10116, map("id", 10116, "mPrevid", 10115, "mNextid", 10117, "mName", "拜访好友（2）", "mDescribe", "小主，近日多去各位小主家走走看看，拜访各位小主有助于巩固感情。", "mMissionid", 40117, "fPrevid", 10115, "fNextid", 10117, "fName", "拜访好友（2）", "fDescribe", "小主，近日多去各位小主家走走看看，拜访各位小主有助于巩固感情。", "fMissionid", 40117),
    10117, map("id", 10117, "mPrevid", 10116, "mNextid", 10118, "mName", "连理枝（2）", "mDescribe", "在天愿作比翼鸟，在地愿为连理枝，小主可不要忘记了供养连理枝哦~", "mMissionid", 40118, "fPrevid", 10116, "fNextid", 10118, "fName", "连理枝（2）", "fDescribe", "在天愿作比翼鸟，在地愿为连理枝，小主可不要忘记了供养连理枝哦~", "fMissionid", 40118),
    10118, map("id", 10118, "mPrevid", 10117, "mNextid", 10119, "mName", "升级1个花盆（2）", "mDescribe", "小主，奴婢听闻升级花盆可大大缩短种植时间，还可以提高鲜花品质哦~", "mMissionid", 40119, "fPrevid", 10117, "fNextid", 10119, "fName", "升级1个花盆（2）", "fDescribe", "小主，奴婢听闻升级花盆可大大缩短种植时间，还可以提高鲜花品质哦~", "fMissionid", 40119),
    10119, map("id", 10119, "mPrevid", 10118, "mNextid", 10120, "mName", "进贡（2）", "mDescribe", "小主，皇上下旨各宫小主均需向西域亲王进贡珍宝。", "mMissionid", 40120, "fPrevid", 10118, "fNextid", 10120, "fName", "进贡（2）", "fDescribe", "小主，皇上下旨各宫小主均需向西域亲王进贡珍宝。", "fMissionid", 40120),
    10120, map("id", 10120, "mPrevid", 10119, "mNextid", 10121, "mName", "登陆第三天", "mDescribe", "小主，请您检查登陆三天哦~勤劳的小主才会讨太后欢心。", "mMissionid", 40202, "fPrevid", 10119, "fNextid", 10121, "fName", "登陆第三天", "fDescribe", "小主，请您检查登陆三天哦~勤劳的小主才会讨太后欢心。", "fMissionid", 40202)
);
    }

    private static void init24() {
data.map(
    10121, map("id", 10121, "mPrevid", 10120, "mNextid", 10122, "mName", "种薰衣草", "mDescribe", "福伽姑姑传话来，近日太后睡眠不是太好，请小主种植薰衣草送去供太后早起使用。", "mMissionid", 40203, "fPrevid", 10120, "fNextid", 10122, "fName", "种薰衣草", "fDescribe", "福伽姑姑传话来，近日太后睡眠不是太好，请小主种植薰衣草送去供太后早起使用。", "fMissionid", 40203),
    10122, map("id", 10122, "mPrevid", 10121, "mNextid", 10123, "mName", "宝石（3）", "mDescribe", "小主，多谢小主体恤奴婢们，请为所有仆从镶嵌上2级宝石。", "mMissionid", 40200, "fPrevid", 10121, "fNextid", 10123, "fName", "宝石（3）", "fDescribe", "小主，多谢小主体恤奴婢们，请为所有仆从镶嵌上2级宝石。", "fMissionid", 40200),
    10123, map("id", 10123, "mPrevid", 10122, "mNextid", 10124, "mName", "强化（3）", "mDescribe", "小主体力不是特别良好，请小主赶紧强化自己吧。", "mMissionid", 40201, "fPrevid", 10122, "fNextid", 10124, "fName", "强化（3）", "fDescribe", "小主体力不是特别良好，请小主赶紧强化自己吧。", "fMissionid", 40201),
    10124, map("id", 10124, "mPrevid", 10123, "mNextid", 10125, "mName", "剧情战斗第五章", "mDescribe", "老奴恭迎小主，小主可是想继续了解娴妃娘娘过往，请打通剧情第五章全部关卡。", "mMissionid", 40204, "fPrevid", 10123, "fNextid", 10125, "fName", "剧情战斗第五章", "fDescribe", "老奴恭迎小主，小主可是想继续了解娴妃娘娘过往，请打通剧情第五章全部关卡。", "fMissionid", 40204),
    10125, map("id", 10125, "mPrevid", 10124, "mNextid", 10126, "mName", "礼单任务（3）", "mDescribe", "近日小主与各宫妃嫔罕有走动，请小主完成1件礼单任务拉进与后宫妃嫔的关系。", "mMissionid", 40205, "fPrevid", 10124, "fNextid", 10126, "fName", "礼单任务（3）", "fDescribe", "近日小主与各宫妃嫔罕有走动，请小主完成1件礼单任务拉进与后宫妃嫔的关系。", "fMissionid", 40205)
);
    }

    private static void init25() {
data.map(
    10126, map("id", 10126, "mPrevid", 10125, "mNextid", 10127, "mName", "砍树（3）", "mDescribe", "小主在这后宫中已然小心翼翼，不知怎的还是得罪了贵妃娘娘，被罚来御花园砍树。", "mMissionid", 40206, "fPrevid", 10125, "fNextid", 10127, "fName", "砍树（3）", "fDescribe", "小主在这后宫中已然小心翼翼，不知怎的还是得罪了贵妃娘娘，被罚来御花园砍树。", "fMissionid", 40206),
    10127, map("id", 10127, "mPrevid", 10126, "mNextid", 10128, "mName", "除草（3）", "mDescribe", "小主都已思过，然而贵妃依旧怒气不消，让小主再去除些杂草方可息怒。", "mMissionid", 40207, "fPrevid", 10126, "fNextid", 10128, "fName", "除草（3）", "fDescribe", "小主都已思过，然而贵妃依旧怒气不消，让小主再去除些杂草方可息怒。", "fMissionid", 40207),
    10128, map("id", 10128, "mPrevid", 10127, "mNextid", 10129, "mName", "收布（3）", "mDescribe", "请小主收获已纺织好的布料吧！", "mMissionid", 40209, "fPrevid", 10127, "fNextid", 10129, "fName", "收布（3）", "fDescribe", "请小主收获已纺织好的布料吧！", "fMissionid", 40209),
    10129, map("id", 10129, "mPrevid", 10128, "mNextid", 10130, "mName", "采矿石（3)", "mDescribe", "请小主再去收取已经开采好的矿石吧！", "mMissionid", 40210, "fPrevid", 10128, "fNextid", 10130, "fName", "采矿石（3)", "fDescribe", "请小主再去收取已经开采好的矿石吧！", "fMissionid", 40210),
    10130, map("id", 10130, "mPrevid", 10129, "mNextid", 10131, "mName", "升级技能（3）", "mDescribe", "小主，适时地提升自己的技能有助于自己在后宫中根深蒂固哦~", "mMissionid", 40211, "fPrevid", 10129, "fNextid", 10131, "fName", "升级技能（3）", "fDescribe", "小主，适时地提升自己的技能有助于自己在后宫中根深蒂固哦~", "fMissionid", 40211)
);
    }

    private static void init26() {
data.map(
    10131, map("id", 10131, "mPrevid", 10130, "mNextid", 10132, "mName", "造装备（3）", "mDescribe", "俗话说的好，好马配好鞍，小主除了自身提升以外也要有好的装备相匹配。", "mMissionid", 40212, "fPrevid", 10130, "fNextid", 10132, "fName", "造装备（3）", "fDescribe", "俗话说的好，好马配好鞍，小主除了自身提升以外也要有好的装备相匹配。", "fMissionid", 40212),
    10132, map("id", 10132, "mPrevid", 10131, "mNextid", 10133, "mName", "剧情战斗第六章", "mDescribe", "老奴恭迎小主，小主可是想继续了解娴妃娘娘过往，请打通剧情第六章全部关卡。", "mMissionid", 40213, "fPrevid", 10131, "fNextid", 10133, "fName", "剧情战斗第六章", "fDescribe", "老奴恭迎小主，小主可是想继续了解娴妃娘娘过往，请打通剧情第六章全部关卡。", "fMissionid", 40213),
    10133, map("id", 10133, "mPrevid", 10132, "mNextid", 10134, "mName", "战斗力（3）", "mDescribe", "请小主努力提升战力吧！", "mMissionid", 40214, "fPrevid", 10132, "fNextid", 10134, "fName", "战斗力（3）", "fDescribe", "请小主努力提升战力吧！", "fMissionid", 40214),
    10134, map("id", 10134, "mPrevid", 10133, "mNextid", 10135, "mName", "聊天一次（3）", "mDescribe", "请小主在聊天综合频道中聊天，近日多与各位小主家聊聊天巩固一下感情。", "mMissionid", 40215, "fPrevid", 10133, "fNextid", 10135, "fName", "聊天一次（3）", "fDescribe", "请小主在聊天综合频道中聊天，近日多与各位小主家聊聊天巩固一下感情。", "fMissionid", 40215),
    10135, map("id", 10135, "mPrevid", 10134, "mNextid", 10136, "mName", "刷宝（3）", "mDescribe", "小主，天降馅饼的好事情何不去拼拼手气，请小主前去任意剧情进行刷宝吧。", "mMissionid", 40216, "fPrevid", 10134, "fNextid", 10136, "fName", "刷宝（3）", "fDescribe", "小主，天降馅饼的好事情何不去拼拼手气，请小主前去任意剧情进行刷宝吧。", "fMissionid", 40216)
);
    }

    private static void init27() {
data.map(
    10136, map("id", 10136, "mPrevid", 10135, "mNextid", 10137, "mName", "等级30级", "mDescribe", "等级越高，享有的权利越大，请小主努力升级吧！", "mMissionid", 40218, "fPrevid", 10135, "fNextid", 10137, "fName", "等级30级", "fDescribe", "等级越高，享有的权利越大，请小主努力升级吧！", "fMissionid", 40218),
    10137, map("id", 10137, "mPrevid", 10136, "mNextid", 10138, "mName", "种虞美人", "mDescribe", "姑姑传话来，近日皇后娘娘格外喜欢虞美人，请小主种植一些虞美人讨皇后娘娘喜欢。", "mMissionid", 40217, "fPrevid", 10136, "fNextid", 10138, "fName", "种虞美人", "fDescribe", "姑姑传话来，近日皇后娘娘格外喜欢虞美人，请小主种植一些虞美人讨皇后娘娘喜欢。", "fMissionid", 40217),
    10138, map("id", 10138, "mPrevid", 10137, "mNextid", 10139, "mName", "拜访好友（3）", "mDescribe", "小主，近日多去各位小主家走走看看，拜访各位小主有助于巩固感情。", "mMissionid", 40220, "fPrevid", 10137, "fNextid", 10139, "fName", "拜访好友（3）", "fDescribe", "小主，近日多去各位小主家走走看看，拜访各位小主有助于巩固感情。", "fMissionid", 40220),
    10139, map("id", 10139, "mPrevid", 10138, "mNextid", 10140, "mName", "连理枝（3）", "mDescribe", "在天愿作比翼鸟，在地愿为连理枝，小主可不要忘记了供养连理枝哦~", "mMissionid", 40221, "fPrevid", 10138, "fNextid", 10140, "fName", "连理枝（3）", "fDescribe", "在天愿作比翼鸟，在地愿为连理枝，小主可不要忘记了供养连理枝哦~", "fMissionid", 40221),
    10140, map("id", 10140, "mPrevid", 10139, "mNextid", 10141, "mName", "花盆（3）", "mDescribe", "小主，奴婢听闻升级花盆可大大缩短种植时间，还可以提高鲜花品质哦~", "mMissionid", 40222, "fPrevid", 10139, "fNextid", 10141, "fName", "花盆（3）", "fDescribe", "小主，奴婢听闻升级花盆可大大缩短种植时间，还可以提高鲜花品质哦~", "fMissionid", 40222)
);
    }

    private static void init28() {
data.map(
    10141, map("id", 10141, "mPrevid", 10140, "mNextid", 10142, "mName", "进贡（2）", "mDescribe", "小主，皇上下旨各宫小主均需向西域亲王进贡珍宝。", "mMissionid", 40223, "fPrevid", 10140, "fNextid", 10142, "fName", "进贡（2）", "fDescribe", "小主，皇上下旨各宫小主均需向西域亲王进贡珍宝。", "fMissionid", 40223),
    10142, map("id", 10142, "mPrevid", 10141, "mNextid", 10143, "mName", "阵法（1）", "mDescribe", "小主，您战力提升极高，但阵法可以为小主继续锦上添花哦~", "mMissionid", 40224, "fPrevid", 10141, "fNextid", 10143, "fName", "阵法（1）", "fDescribe", "小主，您战力提升极高，但阵法可以为小主继续锦上添花哦~", "fMissionid", 40224),
    10143, map("id", 10143, "mPrevid", 10142, "mNextid", 10144, "mName", "修炼（1）", "mDescribe", "小主，勤能补拙，要多多修炼哦~", "mMissionid", 40225, "fPrevid", 10142, "fNextid", 10144, "fName", "修炼（1）", "fDescribe", "小主，勤能补拙，要多多修炼哦~", "fMissionid", 40225),
    10144, map("id", 10144, "mPrevid", 10143, "mNextid", 10145, "mName", "砸蛋（1）", "mDescribe", "小主，奴婢听闻宫中砸金蛋可爆出不少稀罕物件呢，小主快去试试吧！", "mMissionid", 40226, "fPrevid", 10143, "fNextid", 10145, "fName", "砸蛋（1）", "fDescribe", "小主，奴婢听闻宫中砸金蛋可爆出不少稀罕物件呢，小主快去试试吧！", "fMissionid", 40226),
    10145, map("id", 10145, "mPrevid", 10144, "mNextid", 10146, "mName", "派系任务（1）", "mDescribe", "宫中妃嫔也是分派系的，强大的派系支持才是宫中强有力的依靠，小主快去派系中贡献一份力吧！", "mMissionid", 40227, "fPrevid", 10144, "fNextid", 10146, "fName", "派系任务（1）", "fDescribe", "宫中妃嫔也是分派系的，强大的派系支持才是宫中强有力的依靠，小主快去派系中贡献一份力吧！", "fMissionid", 40227)
);
    }

    private static void init29() {
data.map(
    10146, map("id", 10146, "mPrevid", 10145, "mNextid", 10147, "mName", "登陆第四天", "mDescribe", "小主，请您检查登陆四天哦~勤劳的小主才会讨太后欢心。", "mMissionid", 40300, "fPrevid", 10145, "fNextid", 10147, "fName", "登陆第四天", "fDescribe", "小主，请您检查登陆四天哦~勤劳的小主才会讨太后欢心。", "fMissionid", 40300),
    10147, map("id", 10147, "mPrevid", 10146, "mNextid", 10148, "mName", "种薰衣草", "mDescribe", "福伽姑姑传话来，近日太后睡眠不是太好，请小主种植薰衣草送去供太后早起使用。", "mMissionid", 40301, "fPrevid", 10146, "fNextid", 10148, "fName", "种薰衣草", "fDescribe", "福伽姑姑传话来，近日太后睡眠不是太好，请小主种植薰衣草送去供太后早起使用。", "fMissionid", 40301),
    10148, map("id", 10148, "mPrevid", 10147, "mNextid", 10149, "mName", "宝石（4）", "mDescribe", "小主，多谢小主体恤奴婢们，请为所有仆从镶嵌上宝石。", "mMissionid", 40302, "fPrevid", 10147, "fNextid", 10149, "fName", "宝石（4）", "fDescribe", "小主，多谢小主体恤奴婢们，请为所有仆从镶嵌上宝石。", "fMissionid", 40302),
    10149, map("id", 10149, "mPrevid", 10148, "mNextid", 10150, "mName", "强化（4）", "mDescribe", "小主体力不是特别良好，请小主前去强化自己吧！", "mMissionid", 40303, "fPrevid", 10148, "fNextid", 10150, "fName", "强化（4）", "fDescribe", "小主体力不是特别良好，请小主前去强化自己吧！", "fMissionid", 40303),
    10150, map("id", 10150, "mPrevid", 10149, "mNextid", 10151, "mName", "礼单任务（4）", "mDescribe", "近日小主与各宫妃嫔罕有走动，请小主完成礼单任务拉进与后宫妃嫔的关系。", "mMissionid", 40304, "fPrevid", 10149, "fNextid", 10151, "fName", "礼单任务（4）", "fDescribe", "近日小主与各宫妃嫔罕有走动，请小主完成礼单任务拉进与后宫妃嫔的关系。", "fMissionid", 40304)
);
    }

    private static void init30() {
data.map(
    10151, map("id", 10151, "mPrevid", 10150, "mNextid", 10152, "mName", "砍树（4）", "mDescribe", "小主在这后宫中已然小心翼翼，不知怎的还是得罪了贵妃娘娘，被罚来御花园砍树。", "mMissionid", 40305, "fPrevid", 10150, "fNextid", 10152, "fName", "砍树（4）", "fDescribe", "小主在这后宫中已然小心翼翼，不知怎的还是得罪了贵妃娘娘，被罚来御花园砍树。", "fMissionid", 40305),
    10152, map("id", 10152, "mPrevid", 10151, "mNextid", 10153, "mName", "除草（4）", "mDescribe", "小主都已思过，然而贵妃依旧怒气不消，让小主再去除些杂草方可息怒。", "mMissionid", 40306, "fPrevid", 10151, "fNextid", 10153, "fName", "除草（4）", "fDescribe", "小主都已思过，然而贵妃依旧怒气不消，让小主再去除些杂草方可息怒。", "fMissionid", 40306),
    10153, map("id", 10153, "mPrevid", 10152, "mNextid", 10154, "mName", "收布（4）", "mDescribe", "请小主收获已纺织好的布料吧！", "mMissionid", 40307, "fPrevid", 10152, "fNextid", 10154, "fName", "收布（4）", "fDescribe", "请小主收获已纺织好的布料吧！", "fMissionid", 40307),
    10154, map("id", 10154, "mPrevid", 10153, "mNextid", 10155, "mName", "采矿石（4）", "mDescribe", "请小主再去收取已经开采好的矿石吧！", "mMissionid", 40308, "fPrevid", 10153, "fNextid", 10155, "fName", "采矿石（4）", "fDescribe", "请小主再去收取已经开采好的矿石吧！", "fMissionid", 40308),
    10155, map("id", 10155, "mPrevid", 10154, "mNextid", 10156, "mName", "升级技能（4）", "mDescribe", "小主，适时地提升自己的技能有助于自己在后宫中根深蒂固哦~", "mMissionid", 40309, "fPrevid", 10154, "fNextid", 10156, "fName", "升级技能（4）", "fDescribe", "小主，适时地提升自己的技能有助于自己在后宫中根深蒂固哦~", "fMissionid", 40309)
);
    }

    private static void init31() {
data.map(
    10156, map("id", 10156, "mPrevid", 10155, "mNextid", 10157, "mName", "造装备（4）", "mDescribe", "俗话说的好，好马配好鞍，小主除了自身提升以外也要有好的装备相匹配。", "mMissionid", 40310, "fPrevid", 10155, "fNextid", 10157, "fName", "造装备（4）", "fDescribe", "俗话说的好，好马配好鞍，小主除了自身提升以外也要有好的装备相匹配。", "fMissionid", 40310),
    10157, map("id", 10157, "mPrevid", 10156, "mNextid", 10158, "mName", "等级32级", "mDescribe", "等级越高，享有的权利越大，请小主努力升级吧！", "mMissionid", 40311, "fPrevid", 10156, "fNextid", 10158, "fName", "等级32级", "fDescribe", "等级越高，享有的权利越大，请小主努力升级吧！", "fMissionid", 40311),
    10158, map("id", 10158, "mPrevid", 10157, "mNextid", 10159, "mName", "剧情精英第五章", "mDescribe", "老奴恭迎小主，小主可是想继续了解娴妃娘娘过往，请打通剧情精英第五章全部关卡。", "mMissionid", 40312, "fPrevid", 10157, "fNextid", 10159, "fName", "剧情精英第五章", "fDescribe", "老奴恭迎小主，小主可是想继续了解娴妃娘娘过往，请打通剧情精英第五章全部关卡。", "fMissionid", 40312),
    10159, map("id", 10159, "mPrevid", 10158, "mNextid", 10160, "mName", "战斗力（4）", "mDescribe", "请小主努力提升战力吧！", "mMissionid", 40313, "fPrevid", 10158, "fNextid", 10160, "fName", "战斗力（4）", "fDescribe", "请小主努力提升战力吧！", "fMissionid", 40313),
    10160, map("id", 10160, "mPrevid", 10159, "mNextid", 10161, "mName", "聊天一次（4）", "mDescribe", "请小主在聊天综合频道中聊天，近日多与各位小主家聊聊天巩固一下感情。", "mMissionid", 40314, "fPrevid", 10159, "fNextid", 10161, "fName", "聊天一次（4）", "fDescribe", "请小主在聊天综合频道中聊天，近日多与各位小主家聊聊天巩固一下感情。", "fMissionid", 40314)
);
    }

    private static void init32() {
data.map(
    10161, map("id", 10161, "mPrevid", 10160, "mNextid", 10162, "mName", "刷宝（4）", "mDescribe", "小主，天降馅饼的好事情何不去拼拼手气，请小主选择任意剧情前去刷宝哦。", "mMissionid", 40315, "fPrevid", 10160, "fNextid", 10162, "fName", "刷宝（4）", "fDescribe", "小主，天降馅饼的好事情何不去拼拼手气，请小主选择任意剧情前去刷宝哦。", "fMissionid", 40315),
    10162, map("id", 10162, "mPrevid", 10161, "mNextid", 10163, "mName", "种虞美人", "mDescribe", "姑姑传话来，近日皇后娘娘格外喜欢虞美人，请小主种植一些虞美人讨皇后娘娘喜欢。", "mMissionid", 40316, "fPrevid", 10161, "fNextid", 10163, "fName", "种虞美人", "fDescribe", "姑姑传话来，近日皇后娘娘格外喜欢虞美人，请小主种植一些虞美人讨皇后娘娘喜欢。", "fMissionid", 40316),
    10163, map("id", 10163, "mPrevid", 10162, "mNextid", 10164, "mName", "拜访好友（4）", "mDescribe", "小主，近日多去各位小主家走走看看，拜访各位小主有助于巩固感情。", "mMissionid", 40317, "fPrevid", 10162, "fNextid", 10164, "fName", "拜访好友（4）", "fDescribe", "小主，近日多去各位小主家走走看看，拜访各位小主有助于巩固感情。", "fMissionid", 40317),
    10164, map("id", 10164, "mPrevid", 10163, "mNextid", 10165, "mName", "连理枝（4）", "mDescribe", "在天愿作比翼鸟，在地愿为连理枝，小主可不要忘记了供养连理枝哦~", "mMissionid", 40318, "fPrevid", 10163, "fNextid", 10165, "fName", "连理枝（4）", "fDescribe", "在天愿作比翼鸟，在地愿为连理枝，小主可不要忘记了供养连理枝哦~", "fMissionid", 40318),
    10165, map("id", 10165, "mPrevid", 10164, "mNextid", 10166, "mName", "花盆（4）", "mDescribe", "小主，奴婢听闻升级花盆可大大缩短种植时间，还可以提高鲜花品质哦~", "mMissionid", 40319, "fPrevid", 10164, "fNextid", 10166, "fName", "花盆（4）", "fDescribe", "小主，奴婢听闻升级花盆可大大缩短种植时间，还可以提高鲜花品质哦~", "fMissionid", 40319)
);
    }

    private static void init33() {
data.map(
    10166, map("id", 10166, "mPrevid", 10165, "mNextid", 10167, "mName", "进贡（3）", "mDescribe", "小主，皇上下旨各宫小主均需向西域亲王进贡珍宝。", "mMissionid", 40320, "fPrevid", 10165, "fNextid", 10167, "fName", "进贡（3）", "fDescribe", "小主，皇上下旨各宫小主均需向西域亲王进贡珍宝。", "fMissionid", 40320),
    10167, map("id", 10167, "mPrevid", 10166, "mNextid", 10168, "mName", "阵法（2）", "mDescribe", "小主，您战力提升极高，但阵法可以为小主继续锦上添花哦~", "mMissionid", 40321, "fPrevid", 10166, "fNextid", 10168, "fName", "阵法（2）", "fDescribe", "小主，您战力提升极高，但阵法可以为小主继续锦上添花哦~", "fMissionid", 40321),
    10168, map("id", 10168, "mPrevid", 10167, "mNextid", 10169, "mName", "修炼（2）", "mDescribe", "小主，勤能补拙，要多多修炼哦~", "mMissionid", 40322, "fPrevid", 10167, "fNextid", 10169, "fName", "修炼（2）", "fDescribe", "小主，勤能补拙，要多多修炼哦~", "fMissionid", 40322),
    10169, map("id", 10169, "mPrevid", 10168, "mNextid", 10170, "mName", "砸蛋（2）", "mDescribe", "小主，奴婢听闻宫中砸金蛋可爆出不少稀罕物件呢，小主快去试试吧！", "mMissionid", 40323, "fPrevid", 10168, "fNextid", 10170, "fName", "砸蛋（2）", "fDescribe", "小主，奴婢听闻宫中砸金蛋可爆出不少稀罕物件呢，小主快去试试吧！", "fMissionid", 40323),
    10170, map("id", 10170, "mPrevid", 10169, "mNextid", 10171, "mName", "派系任务（2）", "mDescribe", "宫中妃嫔也是分派系的，强大的派系支持才是宫中强有力的依靠，小主快去派系中贡献一份力吧！", "mMissionid", 40324, "fPrevid", 10169, "fNextid", 10171, "fName", "派系任务（2）", "fDescribe", "宫中妃嫔也是分派系的，强大的派系支持才是宫中强有力的依靠，小主快去派系中贡献一份力吧！", "fMissionid", 40324)
);
    }

    private static void init34() {
data.map(
    10171, map("id", 10171, "mPrevid", 10170, "mNextid", 10172, "mName", "精炼（1)", "mDescribe", "精炼装备可大大提神装备属性，这才配得上小主尊贵的地位。", "mMissionid", 40325, "fPrevid", 10170, "fNextid", 10172, "fName", "精炼（1)", "fDescribe", "精炼装备可大大提神装备属性，这才配得上小主尊贵的地位。", "fMissionid", 40325),
    10172, map("id", 10172, "mPrevid", 10171, "mNextid", 10173, "mName", "登陆第五天", "mDescribe", "小主，请您检查登陆五天哦~勤劳的小主才会讨太后欢心。", "mMissionid", 40400, "fPrevid", 10171, "fNextid", 10173, "fName", "登陆第五天", "fDescribe", "小主，请您检查登陆五天哦~勤劳的小主才会讨太后欢心。", "fMissionid", 40400),
    10173, map("id", 10173, "mPrevid", 10172, "mNextid", 10174, "mName", "宝石（5）", "mDescribe", "小主，多谢小主体恤奴婢们，请为所有仆从镶嵌上宝石。", "mMissionid", 40402, "fPrevid", 10172, "fNextid", 10174, "fName", "宝石（5）", "fDescribe", "小主，多谢小主体恤奴婢们，请为所有仆从镶嵌上宝石。", "fMissionid", 40402),
    10174, map("id", 10174, "mPrevid", 10173, "mNextid", 10175, "mName", "强化（5）", "mDescribe", "小主体力不是特别良好，请小主前去强化自己吧！", "mMissionid", 40403, "fPrevid", 10173, "fNextid", 10175, "fName", "强化（5）", "fDescribe", "小主体力不是特别良好，请小主前去强化自己吧！", "fMissionid", 40403),
    10175, map("id", 10175, "mPrevid", 10174, "mNextid", 10176, "mName", "礼单任务（5）", "mDescribe", "近日小主与各宫妃嫔罕有走动，请小主完成礼单任务拉进与后宫妃嫔的关系。", "mMissionid", 40404, "fPrevid", 10174, "fNextid", 10176, "fName", "礼单任务（5）", "fDescribe", "近日小主与各宫妃嫔罕有走动，请小主完成礼单任务拉进与后宫妃嫔的关系。", "fMissionid", 40404)
);
    }

    private static void init35() {
data.map(
    10176, map("id", 10176, "mPrevid", 10175, "mNextid", 10177, "mName", "砍树（5）", "mDescribe", "小主在这后宫中已然小心翼翼，不知怎的还是得罪了贵妃娘娘，被罚来御花园砍树。", "mMissionid", 40405, "fPrevid", 10175, "fNextid", 10177, "fName", "砍树（5）", "fDescribe", "小主在这后宫中已然小心翼翼，不知怎的还是得罪了贵妃娘娘，被罚来御花园砍树。", "fMissionid", 40405),
    10177, map("id", 10177, "mPrevid", 10176, "mNextid", 10178, "mName", "除草（5）", "mDescribe", "小主都已思过，然而贵妃依旧怒气不消，让小主再去除些杂草方可息怒。", "mMissionid", 40406, "fPrevid", 10176, "fNextid", 10178, "fName", "除草（5）", "fDescribe", "小主都已思过，然而贵妃依旧怒气不消，让小主再去除些杂草方可息怒。", "fMissionid", 40406),
    10178, map("id", 10178, "mPrevid", 10177, "mNextid", 10179, "mName", "收布（5）", "mDescribe", "请小主收获已纺织好的布料吧！", "mMissionid", 40407, "fPrevid", 10177, "fNextid", 10179, "fName", "收布（5）", "fDescribe", "请小主收获已纺织好的布料吧！", "fMissionid", 40407),
    10179, map("id", 10179, "mPrevid", 10178, "mNextid", 10180, "mName", "采矿石（5）", "mDescribe", "请小主再去收取已经开采好的矿石吧！", "mMissionid", 40408, "fPrevid", 10178, "fNextid", 10180, "fName", "采矿石（5）", "fDescribe", "请小主再去收取已经开采好的矿石吧！", "fMissionid", 40408),
    10180, map("id", 10180, "mPrevid", 10179, "mNextid", 10181, "mName", "升级技能（5）", "mDescribe", "小主，适时地提升自己的技能有助于自己在后宫中根深蒂固哦~", "mMissionid", 40409, "fPrevid", 10179, "fNextid", 10181, "fName", "升级技能（5）", "fDescribe", "小主，适时地提升自己的技能有助于自己在后宫中根深蒂固哦~", "fMissionid", 40409)
);
    }

    private static void init36() {
data.map(
    10181, map("id", 10181, "mPrevid", 10180, "mNextid", 10182, "mName", "造装备（5）", "mDescribe", "俗话说的好，好马配好鞍，小主除了自身提升以外也要有好的装备相匹配。", "mMissionid", 40410, "fPrevid", 10180, "fNextid", 10182, "fName", "造装备（5）", "fDescribe", "俗话说的好，好马配好鞍，小主除了自身提升以外也要有好的装备相匹配。", "fMissionid", 40410),
    10182, map("id", 10182, "mPrevid", 10181, "mNextid", 10183, "mName", "等级35级", "mDescribe", "等级越高，享有的权利越大，请小主努力升级吧！", "mMissionid", 40411, "fPrevid", 10181, "fNextid", 10183, "fName", "等级35级", "fDescribe", "等级越高，享有的权利越大，请小主努力升级吧！", "fMissionid", 40411),
    10183, map("id", 10183, "mPrevid", 10182, "mNextid", 10184, "mName", "剧情战斗第七章", "mDescribe", "老奴恭迎小主，小主可是想继续了解娴妃娘娘过往，请打通剧情第七章全部关卡。", "mMissionid", 40412, "fPrevid", 10182, "fNextid", 10184, "fName", "剧情战斗第七章", "fDescribe", "老奴恭迎小主，小主可是想继续了解娴妃娘娘过往，请打通剧情第七章全部关卡。", "fMissionid", 40412),
    10184, map("id", 10184, "mPrevid", 10183, "mNextid", 10185, "mName", "战斗力（5）", "mDescribe", "请小主努力提升战力吧！", "mMissionid", 40413, "fPrevid", 10183, "fNextid", 10185, "fName", "战斗力（5）", "fDescribe", "请小主努力提升战力吧！", "fMissionid", 40413),
    10185, map("id", 10185, "mPrevid", 10184, "mNextid", 10186, "mName", "聊天一次（5）", "mDescribe", "请小主在聊天综合频道中聊天，近日多与各位小主家聊聊天巩固一下感情。", "mMissionid", 40414, "fPrevid", 10184, "fNextid", 10186, "fName", "聊天一次（5）", "fDescribe", "请小主在聊天综合频道中聊天，近日多与各位小主家聊聊天巩固一下感情。", "fMissionid", 40414)
);
    }

    private static void init37() {
data.map(
    10186, map("id", 10186, "mPrevid", 10185, "mNextid", 10187, "mName", "刷宝（5）", "mDescribe", "小主，天降馅饼的好事情何不去拼拼手气，请小主选择任意剧情前去刷宝哦。", "mMissionid", 40415, "fPrevid", 10185, "fNextid", 10187, "fName", "刷宝（5）", "fDescribe", "小主，天降馅饼的好事情何不去拼拼手气，请小主选择任意剧情前去刷宝哦。", "fMissionid", 40415),
    10187, map("id", 10187, "mPrevid", 10186, "mNextid", 10188, "mName", "种雏菊", "mDescribe", "小主，奴婢听说近日皇上专宠贵妃只因暖阁内种植了雏菊，请小主种植一些雏菊来吸引皇上。", "mMissionid", 40416, "fPrevid", 10186, "fNextid", 10188, "fName", "种雏菊", "fDescribe", "小主，奴婢听说近日皇上专宠贵妃只因暖阁内种植了雏菊，请小主种植一些雏菊来吸引皇上。", "fMissionid", 40416),
    10188, map("id", 10188, "mPrevid", 10187, "mNextid", 10189, "mName", "拜访好友（5）", "mDescribe", "小主，近日多去各位小主家走走看看，拜访各位小主有助于巩固感情。", "mMissionid", 40417, "fPrevid", 10187, "fNextid", 10189, "fName", "拜访好友（5）", "fDescribe", "小主，近日多去各位小主家走走看看，拜访各位小主有助于巩固感情。", "fMissionid", 40417),
    10189, map("id", 10189, "mPrevid", 10188, "mNextid", 10190, "mName", "连理枝（5）", "mDescribe", "在天愿作比翼鸟，在地愿为连理枝，小主可不要忘记了供养连理枝哦~", "mMissionid", 40418, "fPrevid", 10188, "fNextid", 10190, "fName", "连理枝（5）", "fDescribe", "在天愿作比翼鸟，在地愿为连理枝，小主可不要忘记了供养连理枝哦~", "fMissionid", 40418),
    10190, map("id", 10190, "mPrevid", 10189, "mNextid", 10191, "mName", "花盆（5）", "mDescribe", "小主，奴婢听闻升级花盆可大大缩短种植时间，还可以提高鲜花品质哦~", "mMissionid", 40419, "fPrevid", 10189, "fNextid", 10191, "fName", "花盆（5）", "fDescribe", "小主，奴婢听闻升级花盆可大大缩短种植时间，还可以提高鲜花品质哦~", "fMissionid", 40419)
);
    }

    private static void init38() {
data.map(
    10191, map("id", 10191, "mPrevid", 10190, "mNextid", 10192, "mName", "进贡（4）", "mDescribe", "小主，皇上下旨各宫小主均需向西域亲王进贡珍宝。", "mMissionid", 40420, "fPrevid", 10190, "fNextid", 10192, "fName", "进贡（4）", "fDescribe", "小主，皇上下旨各宫小主均需向西域亲王进贡珍宝。", "fMissionid", 40420),
    10192, map("id", 10192, "mPrevid", 10191, "mNextid", 10193, "mName", "阵法（3）", "mDescribe", "小主，您战力提升极高，但阵法可以为小主继续锦上添花哦~", "mMissionid", 40421, "fPrevid", 10191, "fNextid", 10193, "fName", "阵法（3）", "fDescribe", "小主，您战力提升极高，但阵法可以为小主继续锦上添花哦~", "fMissionid", 40421),
    10193, map("id", 10193, "mPrevid", 10192, "mNextid", 10194, "mName", "修炼（3）", "mDescribe", "小主，勤能补拙，要多多修炼哦~", "mMissionid", 40422, "fPrevid", 10192, "fNextid", 10194, "fName", "修炼（3）", "fDescribe", "小主，勤能补拙，要多多修炼哦~", "fMissionid", 40422),
    10194, map("id", 10194, "mPrevid", 10193, "mNextid", 10195, "mName", "砸蛋（3）", "mDescribe", "小主，奴婢听闻宫中砸金蛋可爆出不少稀罕物件呢，小主快去试试吧！", "mMissionid", 40423, "fPrevid", 10193, "fNextid", 10195, "fName", "砸蛋（3）", "fDescribe", "小主，奴婢听闻宫中砸金蛋可爆出不少稀罕物件呢，小主快去试试吧！", "fMissionid", 40423),
    10195, map("id", 10195, "mPrevid", 10194, "mNextid", 10196, "mName", "派系任务（3）", "mDescribe", "宫中妃嫔也是分派系的，强大的派系支持才是宫中强有力的依靠，小主快去派系中贡献一份力吧！", "mMissionid", 40424, "fPrevid", 10194, "fNextid", 10196, "fName", "派系任务（3）", "fDescribe", "宫中妃嫔也是分派系的，强大的派系支持才是宫中强有力的依靠，小主快去派系中贡献一份力吧！", "fMissionid", 40424)
);
    }

    private static void init39() {
data.map(
    10196, map("id", 10196, "mPrevid", 10195, "mNextid", 10197, "mName", "精炼（2)", "mDescribe", "精炼装备可大大提神装备属性，这才配得上小主尊贵的地位。", "mMissionid", 40425, "fPrevid", 10195, "fNextid", 10197, "fName", "精炼（2)", "fDescribe", "精炼装备可大大提神装备属性，这才配得上小主尊贵的地位。", "fMissionid", 40425),
    10197, map("id", 10197, "mPrevid", 10196, "mNextid", 10198, "mName", "黑市（1）", "mDescribe", "小主，奴婢听闻宫中妃嫔都是偷偷通过黑市购买来那些稀奇古怪的物件的，小主不如也去瞧瞧。", "mMissionid", 40426, "fPrevid", 10196, "fNextid", 10198, "fName", "黑市（1）", "fDescribe", "小主，奴婢听闻宫中妃嫔都是偷偷通过黑市购买来那些稀奇古怪的物件的，小主不如也去瞧瞧。", "fMissionid", 40426),
    10198, map("id", 10198, "mPrevid", 10197, "mNextid", 10199, "mName", "登陆第六天", "mDescribe", "小主，请您检查登陆六天哦~勤劳的小主才会讨太后欢心。", "mMissionid", 40500, "fPrevid", 10197, "fNextid", 10199, "fName", "登陆第六天", "fDescribe", "小主，请您检查登陆六天哦~勤劳的小主才会讨太后欢心。", "fMissionid", 40500),
    10199, map("id", 10199, "mPrevid", 10198, "mNextid", 10200, "mName", "宝石（6）", "mDescribe", "小主，多谢小主体恤奴婢们，请为所有仆从镶嵌上宝石。", "mMissionid", 40501, "fPrevid", 10198, "fNextid", 10200, "fName", "宝石（6）", "fDescribe", "小主，多谢小主体恤奴婢们，请为所有仆从镶嵌上宝石。", "fMissionid", 40501),
    10200, map("id", 10200, "mPrevid", 10199, "mNextid", 10201, "mName", "强化（6）", "mDescribe", "小主体力不是特别良好，请小主前去强化自己吧！", "mMissionid", 40502, "fPrevid", 10199, "fNextid", 10201, "fName", "强化（6）", "fDescribe", "小主体力不是特别良好，请小主前去强化自己吧！", "fMissionid", 40502)
);
    }

    private static void init40() {
data.map(
    10201, map("id", 10201, "mPrevid", 10200, "mNextid", 10202, "mName", "礼单任务（6）", "mDescribe", "近日小主与各宫妃嫔罕有走动，请小主完成礼单任务拉进与后宫妃嫔的关系。", "mMissionid", 40503, "fPrevid", 10200, "fNextid", 10202, "fName", "礼单任务（6）", "fDescribe", "近日小主与各宫妃嫔罕有走动，请小主完成礼单任务拉进与后宫妃嫔的关系。", "fMissionid", 40503),
    10202, map("id", 10202, "mPrevid", 10201, "mNextid", 10203, "mName", "砍树（6）", "mDescribe", "小主在这后宫中已然小心翼翼，不知怎的还是得罪了贵妃娘娘，被罚来御花园砍树。", "mMissionid", 40504, "fPrevid", 10201, "fNextid", 10203, "fName", "砍树（6）", "fDescribe", "小主在这后宫中已然小心翼翼，不知怎的还是得罪了贵妃娘娘，被罚来御花园砍树。", "fMissionid", 40504),
    10203, map("id", 10203, "mPrevid", 10202, "mNextid", 10204, "mName", "除草（6）", "mDescribe", "小主都已思过，然而贵妃依旧怒气不消，让小主再去除些杂草方可息怒。", "mMissionid", 40505, "fPrevid", 10202, "fNextid", 10204, "fName", "除草（6）", "fDescribe", "小主都已思过，然而贵妃依旧怒气不消，让小主再去除些杂草方可息怒。", "fMissionid", 40505),
    10204, map("id", 10204, "mPrevid", 10203, "mNextid", 10205, "mName", "收布（6）", "mDescribe", "请小主收获已纺织好的布料吧！", "mMissionid", 40506, "fPrevid", 10203, "fNextid", 10205, "fName", "收布（6）", "fDescribe", "请小主收获已纺织好的布料吧！", "fMissionid", 40506),
    10205, map("id", 10205, "mPrevid", 10204, "mNextid", 10206, "mName", "采矿石（6）", "mDescribe", "请小主再去收取已经开采好的矿石吧！", "mMissionid", 40507, "fPrevid", 10204, "fNextid", 10206, "fName", "采矿石（6）", "fDescribe", "请小主再去收取已经开采好的矿石吧！", "fMissionid", 40507)
);
    }

    private static void init41() {
data.map(
    10206, map("id", 10206, "mPrevid", 10205, "mNextid", 10207, "mName", "升级技能（6）", "mDescribe", "小主，适时地提升自己的技能有助于自己在后宫中根深蒂固哦~", "mMissionid", 40508, "fPrevid", 10205, "fNextid", 10207, "fName", "升级技能（6）", "fDescribe", "小主，适时地提升自己的技能有助于自己在后宫中根深蒂固哦~", "fMissionid", 40508),
    10207, map("id", 10207, "mPrevid", 10206, "mNextid", 10208, "mName", "造装备（6）", "mDescribe", "俗话说的好，好马配好鞍，小主除了自身提升以外也要有好的装备相匹配。", "mMissionid", 40509, "fPrevid", 10206, "fNextid", 10208, "fName", "造装备（6）", "fDescribe", "俗话说的好，好马配好鞍，小主除了自身提升以外也要有好的装备相匹配。", "fMissionid", 40509),
    10208, map("id", 10208, "mPrevid", 10207, "mNextid", 10209, "mName", "等级38级", "mDescribe", "等级越高，享有的权利越大，请小主努力升级吧！", "mMissionid", 40510, "fPrevid", 10207, "fNextid", 10209, "fName", "等级38级", "fDescribe", "等级越高，享有的权利越大，请小主努力升级吧！", "fMissionid", 40510),
    10209, map("id", 10209, "mPrevid", 10208, "mNextid", 10210, "mName", "剧情战斗第八章", "mDescribe", "老奴恭迎小主，小主可是想继续了解娴妃娘娘过往，请打通剧情第八章全部关卡。", "mMissionid", 40511, "fPrevid", 10208, "fNextid", 10210, "fName", "剧情战斗第八章", "fDescribe", "老奴恭迎小主，小主可是想继续了解娴妃娘娘过往，请打通剧情第八章全部关卡。", "fMissionid", 40511),
    10210, map("id", 10210, "mPrevid", 10209, "mNextid", 10211, "mName", "战斗力（6）", "mDescribe", "请小主努力提升战力吧！", "mMissionid", 40512, "fPrevid", 10209, "fNextid", 10211, "fName", "战斗力（6）", "fDescribe", "请小主努力提升战力吧！", "fMissionid", 40512)
);
    }

    private static void init42() {
data.map(
    10211, map("id", 10211, "mPrevid", 10210, "mNextid", 10212, "mName", "聊天一次（6）", "mDescribe", "请小主在聊天综合频道中聊天，近日多与各位小主家聊聊天巩固一下感情。", "mMissionid", 40513, "fPrevid", 10210, "fNextid", 10212, "fName", "聊天一次（6）", "fDescribe", "请小主在聊天综合频道中聊天，近日多与各位小主家聊聊天巩固一下感情。", "fMissionid", 40513),
    10212, map("id", 10212, "mPrevid", 10211, "mNextid", 10213, "mName", "刷宝（6）", "mDescribe", "小主，天降馅饼的好事情何不去拼拼手气，请小主选择任意剧情前去刷宝哦。", "mMissionid", 40514, "fPrevid", 10211, "fNextid", 10213, "fName", "刷宝（6）", "fDescribe", "小主，天降馅饼的好事情何不去拼拼手气，请小主选择任意剧情前去刷宝哦。", "fMissionid", 40514),
    10213, map("id", 10213, "mPrevid", 10212, "mNextid", 10214, "mName", "拜访好友（6）", "mDescribe", "小主，近日多去各位小主家走走看看，拜访各位小主有助于巩固感情。", "mMissionid", 40516, "fPrevid", 10212, "fNextid", 10214, "fName", "拜访好友（6）", "fDescribe", "小主，近日多去各位小主家走走看看，拜访各位小主有助于巩固感情。", "fMissionid", 40516),
    10214, map("id", 10214, "mPrevid", 10213, "mNextid", 10215, "mName", "连理枝（6）", "mDescribe", "在天愿作比翼鸟，在地愿为连理枝，小主可不要忘记了供养连理枝哦~", "mMissionid", 40517, "fPrevid", 10213, "fNextid", 10215, "fName", "连理枝（6）", "fDescribe", "在天愿作比翼鸟，在地愿为连理枝，小主可不要忘记了供养连理枝哦~", "fMissionid", 40517),
    10215, map("id", 10215, "mPrevid", 10214, "mNextid", 10216, "mName", "花盆（6）", "mDescribe", "小主，奴婢听闻升级花盆可大大缩短种植时间，还可以提高鲜花品质哦~", "mMissionid", 40518, "fPrevid", 10214, "fNextid", 10216, "fName", "花盆（6）", "fDescribe", "小主，奴婢听闻升级花盆可大大缩短种植时间，还可以提高鲜花品质哦~", "fMissionid", 40518)
);
    }

    private static void init43() {
data.map(
    10216, map("id", 10216, "mPrevid", 10215, "mNextid", 10217, "mName", "进贡（5）", "mDescribe", "小主，皇上下旨各宫小主均需向西域亲王进贡珍宝。", "mMissionid", 40519, "fPrevid", 10215, "fNextid", 10217, "fName", "进贡（5）", "fDescribe", "小主，皇上下旨各宫小主均需向西域亲王进贡珍宝。", "fMissionid", 40519),
    10217, map("id", 10217, "mPrevid", 10216, "mNextid", 10218, "mName", "阵法（4）", "mDescribe", "小主，您战力提升极高，但阵法可以为小主继续锦上添花哦~", "mMissionid", 40520, "fPrevid", 10216, "fNextid", 10218, "fName", "阵法（4）", "fDescribe", "小主，您战力提升极高，但阵法可以为小主继续锦上添花哦~", "fMissionid", 40520),
    10218, map("id", 10218, "mPrevid", 10217, "mNextid", 10219, "mName", "修炼（4）", "mDescribe", "小主，勤能补拙，要多多修炼哦~", "mMissionid", 40521, "fPrevid", 10217, "fNextid", 10219, "fName", "修炼（4）", "fDescribe", "小主，勤能补拙，要多多修炼哦~", "fMissionid", 40521),
    10219, map("id", 10219, "mPrevid", 10218, "mNextid", 10220, "mName", "砸蛋（4）", "mDescribe", "小主，奴婢听闻宫中砸金蛋可爆出不少稀罕物件呢，小主快去试试吧！", "mMissionid", 40522, "fPrevid", 10218, "fNextid", 10220, "fName", "砸蛋（4）", "fDescribe", "小主，奴婢听闻宫中砸金蛋可爆出不少稀罕物件呢，小主快去试试吧！", "fMissionid", 40522),
    10220, map("id", 10220, "mPrevid", 10219, "mNextid", 10221, "mName", "派系任务（4）", "mDescribe", "宫中妃嫔也是分派系的，强大的派系支持才是宫中强有力的依靠，小主快去派系中贡献一份力吧！", "mMissionid", 40523, "fPrevid", 10219, "fNextid", 10221, "fName", "派系任务（4）", "fDescribe", "宫中妃嫔也是分派系的，强大的派系支持才是宫中强有力的依靠，小主快去派系中贡献一份力吧！", "fMissionid", 40523)
);
    }

    private static void init44() {
data.map(
    10221, map("id", 10221, "mPrevid", 10220, "mNextid", 10222, "mName", "精炼（3)", "mDescribe", "精炼装备可大大提神装备属性，这才配得上小主尊贵的地位。", "mMissionid", 40524, "fPrevid", 10220, "fNextid", 10222, "fName", "精炼（3)", "fDescribe", "精炼装备可大大提神装备属性，这才配得上小主尊贵的地位。", "fMissionid", 40524),
    10222, map("id", 10222, "mPrevid", 10221, "mNextid", 10223, "mName", "黑市（2）", "mDescribe", "小主，奴婢听闻宫中妃嫔都是偷偷通过黑市购买来那些稀奇古怪的物件的，小主不如也去瞧瞧。", "mMissionid", 40525, "fPrevid", 10221, "fNextid", 10223, "fName", "黑市（2）", "fDescribe", "小主，奴婢听闻宫中妃嫔都是偷偷通过黑市购买来那些稀奇古怪的物件的，小主不如也去瞧瞧。", "fMissionid", 40525),
    10223, map("id", 10223, "mPrevid", 10222, "mNextid", 10224, "mName", "等级40级", "mDescribe", "等级越高，享有的权利越大，请小主努力升级吧！", "mMissionid", 40526, "fPrevid", 10222, "fNextid", 10224, "fName", "等级40级", "fDescribe", "等级越高，享有的权利越大，请小主努力升级吧！", "fMissionid", 40526),
    10224, map("id", 10224, "mPrevid", 10223, "mNextid", 10225, "mName", "登陆第七天", "mDescribe", "小主，请您检查登陆七天哦~勤劳的小主才会讨太后欢心。", "mMissionid", 40600, "fPrevid", 10223, "fNextid", 10225, "fName", "登陆第七天", "fDescribe", "小主，请您检查登陆六天哦~勤劳的小主才会讨太后欢心。", "fMissionid", 40600),
    10225, map("id", 10225, "mPrevid", 10224, "mNextid", 10226, "mName", "宝石（7）", "mDescribe", "小主，多谢小主体恤奴婢们，请为所有仆从镶嵌上宝石。", "mMissionid", 40601, "fPrevid", 10224, "fNextid", 10226, "fName", "宝石（7）", "fDescribe", "小主，多谢小主体恤奴婢们，请为所有仆从镶嵌上宝石。", "fMissionid", 40601)
);
    }

    private static void init45() {
data.map(
    10226, map("id", 10226, "mPrevid", 10225, "mNextid", 10227, "mName", "强化（7）", "mDescribe", "小主体力不是特别良好，请小主前去强化自己吧！", "mMissionid", 40602, "fPrevid", 10225, "fNextid", 10227, "fName", "强化（7）", "fDescribe", "小主体力不是特别良好，请小主前去强化自己吧！", "fMissionid", 40602),
    10227, map("id", 10227, "mPrevid", 10226, "mNextid", 10228, "mName", "礼单任务（7）", "mDescribe", "近日小主与各宫妃嫔罕有走动，请小主完成礼单任务拉进与后宫妃嫔的关系。", "mMissionid", 40603, "fPrevid", 10226, "fNextid", 10228, "fName", "礼单任务（7）", "fDescribe", "近日小主与各宫妃嫔罕有走动，请小主完成礼单任务拉进与后宫妃嫔的关系。", "fMissionid", 40603),
    10228, map("id", 10228, "mPrevid", 10227, "mNextid", 10229, "mName", "砍树（7）", "mDescribe", "小主在这后宫中已然小心翼翼，不知怎的还是得罪了贵妃娘娘，被罚来御花园砍树。", "mMissionid", 40604, "fPrevid", 10227, "fNextid", 10229, "fName", "砍树（7）", "fDescribe", "小主在这后宫中已然小心翼翼，不知怎的还是得罪了贵妃娘娘，被罚来御花园砍树。", "fMissionid", 40604),
    10229, map("id", 10229, "mPrevid", 10228, "mNextid", 10230, "mName", "除草（7）", "mDescribe", "小主都已思过，然而贵妃依旧怒气不消，让小主再去除些杂草方可息怒。", "mMissionid", 40605, "fPrevid", 10228, "fNextid", 10230, "fName", "除草（7）", "fDescribe", "小主都已思过，然而贵妃依旧怒气不消，让小主再去除些杂草方可息怒。", "fMissionid", 40605),
    10230, map("id", 10230, "mPrevid", 10229, "mNextid", 10231, "mName", "收布（7）", "mDescribe", "请小主收获已纺织好的布料吧！", "mMissionid", 40606, "fPrevid", 10229, "fNextid", 10231, "fName", "收布（7）", "fDescribe", "请小主收获已纺织好的布料吧！", "fMissionid", 40606)
);
    }

    private static void init46() {
data.map(
    10231, map("id", 10231, "mPrevid", 10230, "mNextid", 10232, "mName", "采矿石（7）", "mDescribe", "请小主再去收取已经开采好的矿石吧！", "mMissionid", 40607, "fPrevid", 10230, "fNextid", 10232, "fName", "采矿石（7）", "fDescribe", "请小主再去收取已经开采好的矿石吧！", "fMissionid", 40607),
    10232, map("id", 10232, "mPrevid", 10231, "mNextid", 10233, "mName", "升级技能（7）", "mDescribe", "小主，适时地提升自己的技能有助于自己在后宫中根深蒂固哦~", "mMissionid", 40608, "fPrevid", 10231, "fNextid", 10233, "fName", "升级技能（7）", "fDescribe", "小主，适时地提升自己的技能有助于自己在后宫中根深蒂固哦~", "fMissionid", 40608),
    10233, map("id", 10233, "mPrevid", 10232, "mNextid", 10234, "mName", "造装备（7）", "mDescribe", "俗话说的好，好马配好鞍，小主除了自身提升以外也要有好的装备相匹配。", "mMissionid", 40609, "fPrevid", 10232, "fNextid", 10234, "fName", "造装备（7）", "fDescribe", "俗话说的好，好马配好鞍，小主除了自身提升以外也要有好的装备相匹配。", "fMissionid", 40609),
    10234, map("id", 10234, "mPrevid", 10233, "mNextid", 10235, "mName", "等级41级", "mDescribe", "等级越高，享有的权利越大，请小主努力升级吧！", "mMissionid", 40610, "fPrevid", 10233, "fNextid", 10235, "fName", "等级41级", "fDescribe", "等级越高，享有的权利越大，请小主努力升级吧！", "fMissionid", 40610),
    10235, map("id", 10235, "mPrevid", 10234, "mNextid", 10236, "mName", "剧情战斗第八章", "mDescribe", "老奴恭迎小主，小主可是想继续了解娴妃娘娘过往，请打通剧情第九章全部关卡。", "mMissionid", 40611, "fPrevid", 10234, "fNextid", 10236, "fName", "剧情战斗第八章", "fDescribe", "老奴恭迎小主，小主可是想继续了解娴妃娘娘过往，请打通剧情第九章全部关卡。", "fMissionid", 40611)
);
    }

    private static void init47() {
data.map(
    10236, map("id", 10236, "mPrevid", 10235, "mNextid", 10237, "mName", "战斗力（7）", "mDescribe", "请小主努力提升战力吧！", "mMissionid", 40612, "fPrevid", 10235, "fNextid", 10237, "fName", "战斗力（7）", "fDescribe", "请小主努力提升战力吧！", "fMissionid", 40612),
    10237, map("id", 10237, "mPrevid", 10236, "mNextid", 10238, "mName", "聊天一次（7）", "mDescribe", "请小主在聊天综合频道中聊天，近日多与各位小主家聊聊天巩固一下感情。", "mMissionid", 40613, "fPrevid", 10236, "fNextid", 10238, "fName", "聊天一次（7）", "fDescribe", "请小主在聊天综合频道中聊天，近日多与各位小主家聊聊天巩固一下感情。", "fMissionid", 40613),
    10238, map("id", 10238, "mPrevid", 10237, "mNextid", 10239, "mName", "刷宝（7）", "mDescribe", "小主，天降馅饼的好事情何不去拼拼手气，请小主选择任意剧情前去刷宝哦。", "mMissionid", 40614, "fPrevid", 10237, "fNextid", 10239, "fName", "刷宝（7）", "fDescribe", "小主，天降馅饼的好事情何不去拼拼手气，请小主选择任意剧情前去刷宝哦。", "fMissionid", 40614),
    10239, map("id", 10239, "mPrevid", 10238, "mNextid", 10240, "mName", "拜访好友（7）", "mDescribe", "小主，近日多去各位小主家走走看看，拜访各位小主有助于巩固感情。", "mMissionid", 40615, "fPrevid", 10238, "fNextid", 10240, "fName", "拜访好友（7）", "fDescribe", "小主，近日多去各位小主家走走看看，拜访各位小主有助于巩固感情。", "fMissionid", 40615),
    10240, map("id", 10240, "mPrevid", 10239, "mNextid", 10241, "mName", "连理枝（7）", "mDescribe", "在天愿作比翼鸟，在地愿为连理枝，小主可不要忘记了供养连理枝哦~", "mMissionid", 40616, "fPrevid", 10239, "fNextid", 10241, "fName", "连理枝（7）", "fDescribe", "在天愿作比翼鸟，在地愿为连理枝，小主可不要忘记了供养连理枝哦~", "fMissionid", 40616)
);
    }

    private static void init48() {
data.map(
    10241, map("id", 10241, "mPrevid", 10240, "mNextid", 10242, "mName", "花盆（7）", "mDescribe", "小主，奴婢听闻升级花盆可大大缩短种植时间，还可以提高鲜花品质哦~", "mMissionid", 40617, "fPrevid", 10240, "fNextid", 10242, "fName", "花盆（7）", "fDescribe", "小主，奴婢听闻升级花盆可大大缩短种植时间，还可以提高鲜花品质哦~", "fMissionid", 40617),
    10242, map("id", 10242, "mPrevid", 10241, "mNextid", 10243, "mName", "进贡（6）", "mDescribe", "小主，皇上下旨各宫小主均需向西域亲王进贡珍宝。", "mMissionid", 40618, "fPrevid", 10241, "fNextid", 10243, "fName", "进贡（6）", "fDescribe", "小主，皇上下旨各宫小主均需向西域亲王进贡珍宝。", "fMissionid", 40618),
    10243, map("id", 10243, "mPrevid", 10242, "mNextid", 10244, "mName", "阵法（5）", "mDescribe", "小主，您战力提升极高，但阵法可以为小主继续锦上添花哦~", "mMissionid", 40619, "fPrevid", 10242, "fNextid", 10244, "fName", "阵法（5）", "fDescribe", "小主，您战力提升极高，但阵法可以为小主继续锦上添花哦~", "fMissionid", 40619),
    10244, map("id", 10244, "mPrevid", 10243, "mNextid", 10245, "mName", "修炼（5）", "mDescribe", "小主，勤能补拙，要多多修炼哦~", "mMissionid", 40620, "fPrevid", 10243, "fNextid", 10245, "fName", "修炼（5）", "fDescribe", "小主，勤能补拙，要多多修炼哦~", "fMissionid", 40620),
    10245, map("id", 10245, "mPrevid", 10244, "mNextid", 10246, "mName", "砸蛋（5）", "mDescribe", "小主，奴婢听闻宫中砸金蛋可爆出不少稀罕物件呢，小主快去试试吧！", "mMissionid", 40621, "fPrevid", 10244, "fNextid", 10246, "fName", "砸蛋（5）", "fDescribe", "小主，奴婢听闻宫中砸金蛋可爆出不少稀罕物件呢，小主快去试试吧！", "fMissionid", 40621)
);
    }

    private static void init49() {
data.map(
    10246, map("id", 10246, "mPrevid", 10245, "mNextid", 10247, "mName", "派系任务（5）", "mDescribe", "宫中妃嫔也是分派系的，强大的派系支持才是宫中强有力的依靠，小主快去派系中贡献一份力吧！", "mMissionid", 40622, "fPrevid", 10245, "fNextid", 10247, "fName", "派系任务（5）", "fDescribe", "宫中妃嫔也是分派系的，强大的派系支持才是宫中强有力的依靠，小主快去派系中贡献一份力吧！", "fMissionid", 40622),
    10247, map("id", 10247, "mPrevid", 10246, "mNextid", 10248, "mName", "精炼（4)", "mDescribe", "精炼装备可大大提神装备属性，这才配得上小主尊贵的地位。", "mMissionid", 40623, "fPrevid", 10246, "fNextid", 10248, "fName", "精炼（4)", "fDescribe", "精炼装备可大大提神装备属性，这才配得上小主尊贵的地位。", "fMissionid", 40623),
    10248, map("id", 10248, "mPrevid", 10247, "mNextid", 10249, "mName", "黑市（3）", "mDescribe", "小主，奴婢听闻宫中妃嫔都是偷偷通过黑市购买来那些稀奇古怪的物件的，小主不如也去瞧瞧。", "mMissionid", 40624, "fPrevid", 10247, "fNextid", 10249, "fName", "黑市（3）", "fDescribe", "小主，奴婢听闻宫中妃嫔都是偷偷通过黑市购买来那些稀奇古怪的物件的，小主不如也去瞧瞧。", "fMissionid", 40624),
    10249, map("id", 10249, "mPrevid", 10248, "mNextid", 20066, "mName", "等级42级", "mDescribe", "等级越高，享有的权利越大，请小主努力升级吧！", "mMissionid", 40625, "fPrevid", 10248, "fNextid", 20066, "fName", "等级42级", "fDescribe", "等级越高，享有的权利越大，请小主努力升级吧！", "fMissionid", 40625),
    20062, map("id", 20062, "mPrevid", 10095, "mNextid", 10098, "mName", "建造太液池", "mDescribe", "公公提醒小主，近日好多小主在宫中修葺了太液池，小主您也建造一个吧。", "mMissionid", 41063, "fPrevid", 10095, "fNextid", 10098, "fName", "建造太液池", "fDescribe", "公公提醒小主，近日好多小主在宫中修葺了太液池，小主您也建造一个吧。", "fMissionid", 41063)
);
    }

    private static void init50() {
data.map(
    20063, map("id", 20063, "mPrevid", 20062, "mNextid", 10098, "mName", "钓一次鱼", "mDescribe", "闲来无事，王爷约皇上前去太液池垂钓，皇上特命小主一同前去。", "mMissionid", 41064, "fPrevid", 20062, "fNextid", 10098, "fName", "钓一次鱼", "fDescribe", "闲来无事，王爷约皇上前去太液池垂钓，皇上特命小主一同前去。", "fMissionid", 41064),
    20064, map("id", 20064, "mPrevid", 20063, "mNextid", 10098, "mName", "建造一个造渔具的工作台", "mDescribe", "小主，您可以通过造渔具的工作台制造鱼钩哦~", "mMissionid", 41065, "fPrevid", 20063, "fNextid", 10098, "fName", "建造一个造渔具的工作台", "fDescribe", "小主，您可以通过造渔具的工作台制造鱼钩哦~", "fMissionid", 41065),
    20065, map("id", 20065, "mPrevid", 20064, "mNextid", 10098, "mName", "造2枚铜鱼钩", "mDescribe", "小主，您可以自己打制一些鱼钩以备以后钓鱼时用哦~", "mMissionid", 41066, "fPrevid", 20064, "fNextid", 10098, "fName", "造2枚铜鱼钩", "fDescribe", "小主，您可以自己打制一些鱼钩以备以后钓鱼时用哦~", "fMissionid", 41066),
    20066, map("id", 20066, "end", true, "mPrevid", 10249, "mNextid", 20067, "mName", "熔炼1枚高级宝石", "mDescribe", "小主五级普通宝石已不够我们使用了，请小主使用熔炼炉任意融合一枚5级的高级宝石。", "mMissionid", 41067, "fPrevid", 10249, "fNextid", 20067, "fName", "熔炼1枚高级宝石", "fDescribe", "小主五级普通宝石已不够我们使用了，请小主使用熔炼炉任意融合一枚五级的高级宝石。", "fMissionid", 41067),
    20067, map("id", 20067, "mPrevid", 20066, "mNextid", 20068, "mName", "建造太液池", "mDescribe", "公公提醒小主，近日好多小主在宫中修葺了太液池，小主您也建造一个吧。", "mMissionid", 41068, "fPrevid", 20066, "fNextid", 20068, "fName", "建造太液池", "fDescribe", "公公提醒小主，近日好多小主在宫中修葺了太液池，小主您也建造一个吧。", "fMissionid", 41068)
);
    }

    private static void init51() {
data.map(
    20068, map("id", 20068, "mPrevid", 20067, "mNextid", 20069, "mName", "钓一次鱼", "mDescribe", "闲来无事，王爷约皇上前去太液池垂钓，皇上特命小主一同前去。", "mMissionid", 41069, "fPrevid", 20067, "fNextid", 20069, "fName", "钓一次鱼", "fDescribe", "闲来无事，王爷约皇上前去太液池垂钓，皇上特命小主一同前去。", "fMissionid", 41069),
    20069, map("id", 20069, "mPrevid", 20068, "mNextid", 20070, "mName", "建造一个造渔具的工作台", "mDescribe", "小主，您可以通过造渔具的工作台制造鱼钩哦~", "mMissionid", 41070, "fPrevid", 20068, "fNextid", 20070, "fName", "建造一个造渔具的工作台", "fDescribe", "小主，您可以通过造渔具的工作台制造鱼钩哦~", "fMissionid", 41070),
    20070, map("id", 20070, "mPrevid", 20069, "mNextid", 0, "mName", "造2枚铜鱼钩", "mDescribe", "小主，您可以自己打制一些鱼钩以备以后钓鱼时用哦~", "mMissionid", 41071, "fPrevid", 20069, "fNextid", 0, "fName", "造2枚铜鱼钩", "fDescribe", "小主，您可以自己打制一些鱼钩以备以后钓鱼时用哦~", "fMissionid", 41071)
);
    }

}