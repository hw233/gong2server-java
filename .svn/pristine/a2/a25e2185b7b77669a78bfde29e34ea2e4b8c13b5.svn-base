package com.gamejelly.gong2.meta.share;

import java.util.HashMap;
import java.util.Map;

import com.gamejelly.gong2.meta.base.ChildModel;
import com.gamejelly.gong2.utils.GongUtils;
import com.hadoit.game.common.framework.dao.annotation.Column;
import com.hadoit.game.common.framework.dao.annotation.Table;
import com.hadoit.game.common.framework.dao.convert.impl.MapNumberOrStringColumnConvert;

@Table(recursion = true)
public class AvatarTempValueModel extends ChildModel {

	private static final long serialVersionUID = 1L;

	private String avatarId;

	/**
	 * 临时繁荣度 Map<类型, 值>
	 */
	@Column(convertClazz = MapNumberOrStringColumnConvert.class, length = 65535)
	private Map<Integer, Long> buildTempProsperity = new HashMap<Integer, Long>();

	/**
	 * 鲜花魅力值
	 */
	private long flowerCharm;

	/**
	 * 官品魅力值
	 */
	private long gradeCharm;

	/**
	 * 车位魅力值
	 */
	private long cheweiCharm;

	/**
	 * 小妾互动魅力值
	 */
	private long xiaoQieCharm;

	/**
	 * 宫廷事务魅力值
	 */
	private long gtswCharm;

	/**
	 * 官品临时积分
	 */
	private long gradeInterimPoint;

	/**
	 * 是否使用过大赦天下
	 */
	private boolean useDaSheTianXia;

	/**
	 * 个人势力值
	 */
	private long shiLi;

	/**
	 * 个人任职点
	 */
	private long renZhiPoint;

	/**
	 * 固定的繁荣度 建筑和动物
	 */
	private long gudingProsperity;

	/**
	 * 固定的魅力值 衣服
	 */
	private long gudingMeiLi;

	/**
	 * 双倍经验次数
	 */
	private int doubleExpCount;

	/**
	 * 被日照次数
	 */
	private int riZhaoCount;

	/**
	 * 是否刷新过
	 */
	private boolean refresh;
	
	/**
	 * 上次夫妻互动的时间
	 */
	private long lastFqHudongTime;
	
	public AvatarTempValueModel() {

	}

	public Map<Integer, Long> getBuildTempProsperity() {
		if (buildTempProsperity == null) {
			buildTempProsperity = new HashMap<Integer, Long>();
		}
		return buildTempProsperity;
	}

	public void setBuildTempProsperity(Map<Integer, Long> buildTempProsperity) {
		this.buildTempProsperity = buildTempProsperity;
	}

	public long getFlowerCharm() {
		return flowerCharm;
	}

	public void setFlowerCharm(long flowerCharm) {
		this.flowerCharm = flowerCharm;
	}

	public long getGradeCharm() {
		return gradeCharm;
	}

	public void setGradeCharm(long gradeCharm) {
		this.gradeCharm = gradeCharm;
	}

	public long getCheweiCharm() {
		return cheweiCharm;
	}

	public void setCheweiCharm(long cheweiCharm) {
		this.cheweiCharm = cheweiCharm;
	}

	public long getXiaoQieCharm() {
		return xiaoQieCharm;
	}

	public void setXiaoQieCharm(long xiaoQieCharm) {
		this.xiaoQieCharm = xiaoQieCharm;
	}

	public String getAvatarId() {
		return avatarId;
	}

	public void setAvatarId(String avatarId) {
		this.avatarId = avatarId;
	}

	public long getGradeInterimPoint() {
		return gradeInterimPoint;
	}

	public void setGradeInterimPoint(long gradeInterimPoint) {
		this.gradeInterimPoint = gradeInterimPoint;
	}

	public boolean isUseDaSheTianXia() {
		return useDaSheTianXia;
	}

	public void setUseDaSheTianXia(boolean useDaSheTianXia) {
		this.useDaSheTianXia = useDaSheTianXia;
	}

	public long getShiLi() {
		return shiLi;
	}

	public void setShiLi(long shiLi) {
		this.shiLi = shiLi;
	}

	public void clearTempValueData() {

		this.flowerCharm = 0;
		this.gradeCharm = 0;
		this.cheweiCharm = 0;
		this.xiaoQieCharm = 0;
		this.gtswCharm = 0;

		this.buildTempProsperity = null;

		this.useDaSheTianXia = false;

		this.gradeInterimPoint = 0;
		this.shiLi = 0;
	}

	public void clearDoubleExpData() {
		this.doubleExpCount = 0;
		this.refresh = false;
	}

	public void clearRiZhaoCount() {
		this.riZhaoCount = 0;
	}
	
	public void clearFqInteractiveCd() {
		this.lastFqHudongTime = 0;
	}

	public long getGudingProsperity() {
		return gudingProsperity;
	}

	public void setGudingProsperity(long gudingProsperity) {
		this.gudingProsperity = gudingProsperity;
	}

	public long getGudingMeiLi() {
		return gudingMeiLi;
	}

	public void setGudingMeiLi(long gudingMeiLi) {
		this.gudingMeiLi = gudingMeiLi;
	}

	public long getRenZhiPoint() {
		return renZhiPoint;
	}

	public void setRenZhiPoint(long renZhiPoint) {
		this.renZhiPoint = renZhiPoint;
	}

	public void incrRenZhiPoint(int v) {
		setRenZhiPoint(GongUtils.adjustNumberInRange(getRenZhiPoint() + v, 0, Long.MAX_VALUE));
	}

	public int getDoubleExpCount() {
		return doubleExpCount;
	}

	public void setDoubleExpCount(int doubleExpCount) {
		this.doubleExpCount = doubleExpCount;
	}

	public boolean isRefresh() {
		return refresh;
	}

	public void setRefresh(boolean refresh) {
		this.refresh = refresh;
	}

	public int getRiZhaoCount() {
		return riZhaoCount;
	}

	public void setRiZhaoCount(int riZhaoCount) {
		this.riZhaoCount = riZhaoCount;
	}

	public long getGtswCharm() {
		return gtswCharm;
	}

	public void setGtswCharm(long gtswCharm) {
		this.gtswCharm = gtswCharm;
	}

	public long getLastFqHudongTime() {
		return lastFqHudongTime;
	}

	public void setLastFqHudongTime(long lastFqHudongTime) {
		this.lastFqHudongTime = lastFqHudongTime;
	}
}