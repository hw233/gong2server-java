package com.gamejelly.gong2.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.spi.LoggingEvent;

import com.gamejelly.gong2.gas.entity.AvatarEntity;
import com.gamejelly.gong2.utils.GongConstants;
import com.hadoit.game.common.framework.dao.annotation.Column;

public class AvatarVO {

	/**
	 * ID
	 */
	private String id;

	/**
	 * 账号
	 */
	private String accountName;

	private String roleName;

	/**
	 * 全服id
	 */
	private long gbId;

	private int lv;

	private long exp;

	private int sex;

	private int grade;

	private int job;

	private long gold;
	
	private long money;

	private long freeGold;

	private long leijiGold;

	private long historyGold;

	private int vipLv;
	
	private String qianming;//签名

	private Map<Integer, Long> currencys;

	private long zhanLi;//战力

	/**
	 * 周期性操作数据
	 */
	private Map<Integer, Integer> operateDayMap = new HashMap<Integer, Integer>();


	/**
	 * 普通关卡奖励领取状态
	 */
	private Map<Integer, String> sceneNorAwards;
	/**
	 * 困难关卡奖励领取状态
	 */
	private Map<Integer, String> sceneDifAwards;


	private long secServantLiangRenCDTime;



	public AvatarVO() {
	}

	public AvatarVO(AvatarEntity entity) {
		this(entity, true);
	}

	public AvatarVO(AvatarEntity entity, boolean my) {
		this.id = entity.getAvatarModel().getId();
		this.accountName = entity.getAvatarModel().getAccountName();
		this.roleName = entity.getAvatarModel().getRoleName();
		this.gbId = entity.getAvatarModel().getGbId();
		this.gold=entity.getAvatarModel().getGold()+entity.getAvatarModel().getFreeGold();
		this.exp = entity.getAvatarModel().getExp();
		this.lv = entity.getAvatarModel().getLv();
		this.sex = entity.getAvatarModel().getSex();
		this.vipLv = entity.getAvatarModel().getVipLv();
		this.money=entity.getAvatarModel().getMoney();
		this.qianming=entity.getAvatarModel().getQianming();
		this.zhanLi = entity.getTotalZhanli();

		// 别人不需要看的字段
		if (my) {

			initOperateDataCount(entity);
			this.sceneNorAwards = convertMap(entity.getAvatarModel().getSceneNorAwards());
			this.sceneDifAwards = convertMap(entity.getAvatarModel().getSceneDifAwards());
			this.secServantLiangRenCDTime = entity.getAvatarModel().getSecServantLiangRenCDTime();

		}

	}

	private static Map<Integer,String> convertMap(Map<Integer, Integer> curMap){
		Map<Integer,String> retMap = new HashMap<Integer,String>();
		for(Entry<Integer, Integer> entry : curMap.entrySet()){
			retMap.put(entry.getKey(), StringUtils.reverse(String.format("%03d", entry.getValue())));
		}
		return retMap;
	}


	public void initOperateDataCount(final AvatarEntity entity) {

		// 周期性数据每日
		for (int operateId : GongConstants.OPERATE_TYPE_DAY_LIST) {
			operateDayMap.put(operateId, entity.getCycleOptCdCount(operateId));
		}

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public long getGbId() {
		return gbId;
	}

	public void setGbId(long gbId) {
		this.gbId = gbId;
	}

	public int getLv() {
		return lv;
	}

	public void setLv(int lv) {
		this.lv = lv;
	}

	public long getExp() {
		return exp;
	}

	public void setExp(long exp) {
		this.exp = exp;
	}

	public Map<Integer, Long> getCurrencys() {
		return currencys;
	}

	public void setCurrencys(Map<Integer, Long> currencys) {
		this.currencys = currencys;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getJob() {
		return job;
	}

	public void setJob(int job) {
		this.job = job;
	}

	public long getGold() {
		return gold;
	}

	public void setGold(long gold) {
		this.gold = gold;
	}

	public long getFreeGold() {
		return freeGold;
	}

	public void setFreeGold(long freeGold) {
		this.freeGold = freeGold;
	}

	public long getLeijiGold() {
		return leijiGold;
	}

	public void setLeijiGold(long leijiGold) {
		this.leijiGold = leijiGold;
	}

	public long getHistoryGold() {
		return historyGold;
	}

	public void setHistoryGold(long historyGold) {
		this.historyGold = historyGold;
	}

	public int getVipLv() {
		return vipLv;
	}

	public void setVipLv(int vipLv) {
		this.vipLv = vipLv;
	}

	public long getMoney() {
		return money;
	}

	public void setMoney(long money) {
		this.money = money;
	}

	public String getQianming() {
		return qianming;
	}

	public void setQianming(String qianming) {
		this.qianming = qianming;
	}


	public Map<Integer, String> getSceneNorAwards() {
		return sceneNorAwards;
	}

	public void setSceneNorAwards(Map<Integer, String> sceneNorAwards) {
		this.sceneNorAwards = sceneNorAwards;
	}

	public Map<Integer, String> getSceneDifAwards() {
		return sceneDifAwards;
	}

	public void setSceneDifAwards(Map<Integer, String> sceneDifAwards) {
		this.sceneDifAwards = sceneDifAwards;
	}

}
