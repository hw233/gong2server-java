var gm_cmd = require('./gm_cmd');
var $context = process.value('$context');
var $beanFactory = process.value('$beanFactory');

function getBean(name) {
	return $beanFactory.getBean(name, $beanFactory.getType(name));
}

importClass(com.hadoit.game.common.lang.DataUtils);
importClass(com.hadoit.game.common.framework.utils.SystemOPUtils);

// service
var gmService = getBean("gmService");
var messageService = getBean("messageService");
var GongCommonNotify = com.gamejelly.gong2.utils.GongCommonNotify;
var GongConstants = com.gamejelly.gong2.utils.GongConstants;
var GongUtils = com.gamejelly.gong2.utils.GongUtils;
var StringUtils = org.apache.commons.lang.StringUtils;
var USER_GROUP_GM = GongConstants.USER_GROUP_GM;
var USER_GROUP_DEV = GongConstants.USER_GROUP_DEV;
var USER_GROUP_GOD = GongConstants.USER_GROUP_GOD;

// init
exports.init = function() {
//	gm_cmd.reg(setAotState, "$setAotState", "", "设置aot开关", USER_GROUP_DEV);
//	gm_cmd.reg(nextMainMission, "$nextMainMission", "", "跳过主线任务", USER_GROUP_DEV);
    gm_cmd.reg(testInterface, "testInterface", "", "测试接口(后端使用)",USER_GROUP_DEV);
    gm_cmd.reg(addGold, "$addYb", "数量", "增加元宝", USER_GROUP_DEV);
	gm_cmd.reg(addItem, "$addItem", "id, 数量", "增加物品", USER_GROUP_DEV);
	gm_cmd.reg(addExp, "$addJy", "数量", "增加经验", USER_GROUP_DEV);
	gm_cmd.reg(addMoney, "$addYl", "数量", "增加银两", USER_GROUP_DEV);
	gm_cmd.reg(addServant,"$addServant","模版ID,位置","增加武将",USER_GROUP_DEV)
//	gm_cmd.reg(changeJob, "$changeJob", "仆从模板ID", "转职", USER_GROUP_DEV);
//	gm_cmd.reg(mapText, "$mapText", "", "打开/关闭地图坐标", USER_GROUP_DEV);
	gm_cmd.reg(wudi, "$wd", "", "打开/关闭无敌", USER_GROUP_DEV);
//	gm_cmd.reg(gbSysMsg, "$gb", "消息内容", "广播系统消息", USER_GROUP_GM);
//	gm_cmd.reg(jumpToGuanka, "$gkjs", "关卡ID", "解锁指定关卡", USER_GROUP_DEV);
//	gm_cmd.reg(sendSystemReward, "$sysRward", ",消息,物品ID|...,物品数量|...,银两,元宝,VIP积分", "系统发奖", USER_GROUP_DEV);
//	gm_cmd.reg(sendSystemCommonMsg, "$sysCommon", ",消息", "系统普通消息", USER_GROUP_DEV);
//	gm_cmd.reg(addLTPoint, "$addLTPoint", "数量", "增加擂台积分", USER_GROUP_DEV);
//	gm_cmd.reg(addZBSP, "$addZBSP", "数量", "增加装备碎片", USER_GROUP_DEV);
//	gm_cmd.reg(resetPlayGuide, "$resetPlayGuide", "引导ID", "设置新手引导", USER_GROUP_DEV);
//	gm_cmd.reg(maxProsperity, "$maxProsperity", "等级(默认为1级最大80)", "建筑最大繁荣度", USER_GROUP_DEV);
//	gm_cmd.reg(calcGrade, "$calcGrade", "", "模拟跨天（重新计算武举、官品等）", USER_GROUP_DEV);
//	gm_cmd.reg(chongzhi, "$chongzhi", "元宝", "充值", USER_GROUP_DEV);
//	gm_cmd.reg(chongzhiType, "$chongzhiType", "2季卡/3月卡", "充值季卡月卡", USER_GROUP_DEV);
//	gm_cmd.reg(setGradeLv, "$setGradeLv", "等级(1-20级)", "设置官品等级", USER_GROUP_DEV);
//	gm_cmd.reg(setSjBossLv, "$setSjBossLv", "等级", "世界boss等级", USER_GROUP_DEV);
//	gm_cmd.reg(setVipLv, "$setVipLv", "设置VIP等级", "等级", USER_GROUP_DEV);
//	gm_cmd.reg(setCurMainMission, "$mainMission", "设置当前主线任务", "任务ID", USER_GROUP_DEV);
//	gm_cmd.reg(resetMainMission, "$resetMainMission", "重置当前主线任务", "任务ID", USER_GROUP_DEV);
//	gm_cmd.reg(addHuoBiById, "$addHuoBiById", "增加货币", "ID,数量", USER_GROUP_DEV);
//	gm_cmd.reg(resetFubenTzNum, "$resetFubenTzNum", "", "重置副本挑战次数", USER_GROUP_DEV);
//	gm_cmd.reg(addShiLi, "$addShiLi", "增加势力值", "增加个人势力值", USER_GROUP_DEV);
//	gm_cmd.reg(batchRegister, "batchRegister", "gbIds", "gbId以-分隔", USER_GROUP_DEV);
//	gm_cmd.reg(xjzbRegister, "xjzbRegister", "", "派系战报名", USER_GROUP_DEV);
//	gm_cmd.reg(clearXjzb, "clearXjzb", "", "清空仙界争霸数据", USER_GROUP_DEV);
//	gm_cmd.reg(clearXjzbPxSign, "clearXjzbPxSign", "", "重置派系战斗报名数据", USER_GROUP_DEV);
//	gm_cmd.reg(clearQxCount, "clearQxCount", "", "重置抢妾.反抗次数", USER_GROUP_DEV);
//	gm_cmd.reg(clearXiaoQie, "clearXiaoQie", "", "清空小妾", USER_GROUP_DEV);
//	gm_cmd.reg(forceSyncZstData, "$forceSyncZstData", "", "强制同步跨服数据", USER_GROUP_DEV);
//	gm_cmd.reg(resetZstFightCount, "$resetZstFightCount", "", "清战神坛次数", USER_GROUP_DEV);
//	gm_cmd.reg(resetFqhdCount, "$resetFqhdCount", "", "夫妻互动次数重置", USER_GROUP_DEV);
//	gm_cmd.reg(batchRegisterGlobal, "batchRegisterGlobal", "gbIds", "gbId以-分隔", USER_GROUP_DEV);
//	gm_cmd.reg(clearXjzbGlobal, "clearXjzbGlobal", "", "清空跨服仙界争霸数据", USER_GROUP_DEV);
//	gm_cmd.reg(setRoleTempValue, "setRoleTempValue", "人物ID,字段（shiLi 势力),值", "人物临时值修改", USER_GROUP_DEV);
//	gm_cmd.reg(resetTanHeCount, "resetTanHeCount", "", "重置弹劾次数", USER_GROUP_DEV);
//	gm_cmd.reg(resetShareRewardCount, "resetShareRewardCount", "", "重置分享奖励次数", USER_GROUP_DEV);
//	gm_cmd.reg(resetWuJuCount, "resetWuJuCount", "", "重置武举挑战次数", USER_GROUP_DEV);
//	gm_cmd.reg(resetLeiTaiCount, "resetLeiTaiCount", "", "重置擂台次数", USER_GROUP_DEV);
//	gm_cmd.reg(resetZhangZuiCount, "resetZhangZuiCount", "", "重置掌嘴次数", USER_GROUP_DEV);
//	gm_cmd.reg(addLoveValueHs, "addLoveValueHs", "", "增加与皇上的恩爱值", USER_GROUP_DEV);
//	gm_cmd.reg(aliBigPayTest, "aliBigPayTest", "", "支付宝3600充值", USER_GROUP_DEV);
//	gm_cmd.reg(zhanshenSyncData, "zhanshenSyncData", "", "同步玩家到跨服", USER_GROUP_DEV);
//	gm_cmd.reg(zlmtSyncData, "zlmtSyncData", "", "灵骨塔同步玩家", USER_GROUP_DEV);
//	gm_cmd.reg(syncAvatarCache, "syncAvatarCacheModel", "", "灵骨塔AvatarCacheModel", USER_GROUP_DEV);
//	gm_cmd.reg(clearBag, "clearBag", "", "清空背包", USER_GROUP_DEV);
//	gm_cmd.reg(clearGhWarData, "clearGhWarData", "", "清空派系战数据", USER_GROUP_DEV);
//	gm_cmd.reg(applyGhWar, "applyGhWar", "", "报名跨服派系战/帮主", USER_GROUP_DEV);
//	gm_cmd.reg(checkGhWarIsApply, "checkGhWarIsApply", "", "帮主是否报名派系战", USER_GROUP_DEV);
//	gm_cmd.reg(registerGhWar, "registerGhWar", "", "报名跨服派系战/成员", USER_GROUP_DEV);
//	gm_cmd.reg(unSaveLineUpGhWar, "unSaveLineUpGhWar", "", "取消报名跨服派系战/成员", USER_GROUP_DEV);
//	gm_cmd.reg(saveLineUpGhWar, "saveLineUpGhWar", "", "保存跨服派系战/成员", USER_GROUP_DEV);
//	gm_cmd.reg(enterGhWar, "enterGhWar", "", "进入派系战", USER_GROUP_DEV);
//	gm_cmd.reg(getGhWarRecord, "getGhWarRecord", "", "派系战斗记录", USER_GROUP_DEV);
//	gm_cmd.reg(registGhwAllGhMember, "registGhwAllGhMember", "", "当前派系所有成员报名", USER_GROUP_DEV);
//	gm_cmd.reg(sendChongzhiChenghao, "sendChongzhiChenghao", "称号ID", "发充值触发的称号", USER_GROUP_DEV);
//	gm_cmd.reg(chongzhiThgm, "chongzhiThgm", "", "特惠购买", USER_GROUP_DEV);
//	gm_cmd.reg(chongzhiByProductId, "chongzhiByProductId", "", "根据productID充值", USER_GROUP_DEV);
//	gm_cmd.reg(resetSlCount, "resetSlCount", "", "重置试炼奖励次数", USER_GROUP_DEV);
//	gm_cmd.reg(sendRedPackage, "sendRedPackage", "", "发红包", USER_GROUP_DEV);

}

// commands
function js2JavaStringArray(arr){
	var r = java.lang.reflect.Array.newInstance(java.lang.String,
			arr.length);
	for(var i=0;i<arr.length;++i){
		if(arr[i] != null){
			r[i] = arr[i];
		}
	}
	return r;
}

function string2IntegerArray(arr){
	var r = java.lang.reflect.Array.newInstance(java.lang.Integer,
			arr.length);
	for(var i=0;i<arr.length;++i){
		if(arr[i] != null){
			r[i] = java.lang.Integer.valueOf(arr[i]);
		}
	}
	return r;
}

function setDate(entity, v1, v2) {
	println("setDate, entity="+entity+", v1="+v1+", v2="+v2)
	if(v1 != null && v1 !="") {
		SystemOPUtils.exec(js2JavaStringArray(["cmd","/c","date",v1]));		
	}
	if(v2 != null && v2 !="") {
		SystemOPUtils.exec(js2JavaStringArray(["cmd","/c","time",v2]));		
	}
	return DataUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm");
}

function gbSysMsg(owner, msg) {
	GongCommonNotify.sysSayToAll(owner.getContext(), msg,GongConstants.SAY_MSG_SYSTEM)
}
//增加元宝
function addGold(entity, incr) {
	gmService.addGoldForce(entity, incr)
}
//增加物品
function addItem(entity, templateId, count) {
	gmService.addItem(entity, templateId, count)
}
//增加经验
function addExp(entity, incr) {
	gmService.addExpForce(entity, incr)
}
//增加银两
function addMoney(entity, incr) {
	gmService.addMoneyForce(entity, incr)
}


//增加武将
function addServant(entity, templateId,pos) {
	gmService.addServant(entity, templateId,pos)
}


function changeJob(entity, incr) {
	gmService.changeJob(entity, incr)
}

function mapText(entity) {
	
}

function wudi(entity) {
	if (entity.isWudi()) {
		entity.setWudi(false)
		return "关闭无敌模式"
	} else {
		entity.setWudi(true)
		return "打开无敌模式"
	}
}

function jumpToGuanka(owner, gkId) {
	gmService.jumpToGuanka(owner, gkId);	
}

function sendSystemReward(entity, recvs, content, itemIds, itemCounts, money, gold, leijiGold) {
	recvs = !recvs ? "" : recvs
	itemIds = !itemIds ? "" : itemIds
	itemCounts = !itemCounts ? "" : itemCounts	
	money = !money ? 0 : money
	gold = !gold ? 0 : gold
	leijiGold = !leijiGold ? 0 : leijiGold

	var rv = StringUtils.splitByWholeSeparator(recvs, "|")
	var ii = string2IntegerArray(StringUtils.splitByWholeSeparator(itemIds, "|"))
	var ic = string2IntegerArray(StringUtils.splitByWholeSeparator(itemCounts, "|"))
	rv = java.util.Arrays.asList(rv)
	ii = java.util.Arrays.asList(ii)
	ic = java.util.Arrays.asList(ic)
	messageService.sendSystemReward(rv, content, ii, ic, money, gold, leijiGold)
}

function sendSystemCommonMsg(entity, recvs, content) {
	recvs = !recvs ? "" : recvs
	var rv = StringUtils.splitByWholeSeparator(recvs, "|")
	rv = java.util.Arrays.asList(rv)
	messageService.sendSystemCommonMsg(rv, content)
}

function addLTPoint(entity, val) {
	gmService.addLTPoint(entity, val)
}

function addZBSP(entity, val) {
	gmService.addZBSP(entity, val)
}

function resetPlayGuide(entity, val) {
	gmService.resetPlayGuide(entity, val)
}

function maxProsperity(entity,val) {
	gmService.maxProsperity(entity,val)
}

function calcGrade(entity) {
	gmService.calcGrade(entity)
}

function chongzhi(entity, incr) {
	gmService.chongzhi(entity, incr)
}

function chongzhiType(entity, type) {
	gmService.chongzhiType(entity, type)
}


function setGradeLv(entity, val) {
	gmService.setGradeLv(entity, val)
}

function setSjBossLv(entity, val) {
	gmService.setSjBossLv(entity, val)
}

function setVipLv(entity, value) {
	gmService.setVipLv(entity, value)
}

function setCurMainMission(entity, value) {
	gmService.setCurMainMission(entity, value)
}


function resetMainMission(entity) {
	gmService.resetMainMission(entity)
}

function addHuoBiById(entity,itemID,value) {
	gmService.addHuoBiById(entity,itemID,value)
}

function resetFubenTzNum(entity) {
	gmService.resetFubenTzNum(entity)
}

function addShiLi(entity, value) {
	gmService.addShiLi(entity,value)
}

function batchRegister(entity, gbIds) {
	gmService.batchRegister(entity, gbIds)
}



function xjzbRegister(entity) {
	gmService.xjzbRegister(entity)
}

function clearXjzb(entity){
	gmService.clearXjzb(entity)
}

function clearXjzbPxSign(entity){
	gmService.clearXjzbPxSign(entity)
}

function clearQxCount(entity){
	gmService.clearQxCount(entity)
}

function clearXiaoQie(entity){
	gmService.clearXiaoQie(entity)
}

function forceSyncZstData(entity) {
    gmService.forceSyncZstData(entity)
}

function resetZstFightCount(entity) {
    gmService.resetZstFightCount(entity)
}

function resetFqhdCount(entity) {
    gmService.resetFqhdCount(entity)
}



function batchRegisterGlobal(entity, gbIds) {
	gmService.batchRegisterGlobal(entity, gbIds)
}


function clearXjzbGlobal(entity){
	gmService.clearXjzbGlobal(entity)
}

function setRoleTempValue(entity,avatarId,field,value) {
	gmService.setRoleTempValue(entity,avatarId,field,value)
}


function resetTanHeCount(entity){
	gmService.resetTanHeCount(entity)
}

function resetShareRewardCount(entity){
	gmService.resetShareRewardCount(entity)
}

function resetWuJuCount(entity){
	gmService.resetWuJuCount(entity)
}

function resetLeiTaiCount(entity){
	gmService.resetLeiTaiCount(entity)
}

function resetZhangZuiCount(entity){
	gmService.resetZhangZuiCount(entity)
}


function addLoveValueHs(entity, value){
	gmService.addLoveValueHs(entity, value)
}

function aliBigPayTest(entity){
	gmService.aliBigPay(entity)
}

function clearBag(entity){
	gmService.clearBag(entity)
}


function zhanshenSyncData(entity){
	gmService.zhanshenSyncData(entity)
}


function zlmtSyncData(entity){
	gmService.zlmtSyncData(entity)
}

function syncAvatarCache(entity){
	gmService.syncAvatarCacheModel(entity)
}

function applyGhWar(entity){
	gmService.applyGhWar(entity)
}


function checkGhWarIsApply(entity){
	gmService.checkGhWarIsApply(entity)
}


function registerGhWar(entity){
	gmService.registerGhWar(entity)
}

function saveLineUpGhWar(entity){
	gmService.saveLineUpGhWar(entity)
}

function unSaveLineUpGhWar(entity){
	gmService.unSaveLineUpGhWar(entity)
}

function enterGhWar(entity){
	gmService.enterGhWar(entity)
}

function clearGhWarData(entity){
	gmService.clearGhWarData(entity)
}

function getGhWarRecord(entity){
	gmService.getGhWarRecord(entity)
}

function clearGhWarData(entity){
	gmService.clearGhWarData(entity)
}

function registGhwAllGhMember(entity){
	gmService.registGhwAllGhMember(entity)
}

function sendChongzhiChenghao(entity,value){
	gmService.sendChongzhiChenghao(entity,value)
}


function chongzhiThgm(entity){
	gmService.chongzhiThgm(entity)
}

function chongzhiByProductId(entity,value){
	gmService.chongzhiByProductId(entity,value)
}

function resetSlCount(entity){
	gmService.resetSlCount(entity)
}

function sendRedPackage(entity,value,orderSn){
    gmService.sendRedPackage(entity,value,orderSn)
}


function setAotState(entity) {
	if (GongConstants.isOpenAot()){
		GongConstants.setOpenAotState(false);
		return "关闭aot模式"
	} else {
		GongConstants.setOpenAotState(true);
		return "打开aot模式"
	}
}

function nextMainMission(entity) {
	gmService.nextMainMission(entity)
}
function testInterface(entity,value){
}