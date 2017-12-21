package com.gamejelly.gong2.meta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gamejelly.gong2.config.data.SysConstData;
import com.gamejelly.gong2.meta.base.BaseModel;
import com.gamejelly.gong2.utils.GongUtils;
import com.hadoit.game.common.framework.dao.annotation.Column;
import com.hadoit.game.common.framework.dao.annotation.Embedded;
import com.hadoit.game.common.framework.dao.annotation.Index;
import com.hadoit.game.common.framework.dao.annotation.Table;
import com.hadoit.game.common.framework.dao.convert.impl.CollectionNumberOrStringColumnConvert;
import com.hadoit.game.common.framework.dao.convert.impl.MapNumberOrStringColumnConvert;

/**
 * @author wcc 2017-06-24
 * 
 */
@Table(recursion = true, autoIncrement = 10000, indexs = { @Index(columns = { "accountName", "serverId" }) })
public class AvatarModel extends BaseModel implements Cloneable {
	private static final long serialVersionUID = 1L;

	/**
	 * 表示唯一的id
	 */
	@Column(value = "uid", index = true)
	private String id;

	/**
	 * 用户组
	 */
	private int userGroup;

	/**
	 * 账号, 长度放宽, 因为这个账号长度取决于sdk返回的唯一标示的长度
	 */
	@Column(length = 200, index = true)
	private String accountName;

	/**
	 * 服务器ID(解决一个号在多个服有角色的问题)
	 */
	private int serverId;

	/**
	 * 角色名
	 */
	@Column(length = 50, unique = true)
	private String roleName;

	/**
	 * 全服唯一的id
	 */
	@Column(index = true)
	private long gbId;

	@Column(index = true)
	private int lv;

	private long exp;

	/**
	 * 性别，0女，1男
	 */
	private int sex;

	/**
	 * 头像
	 */
	private int iconIndex;


	/**
	 * 公会科技最大等级限制
	 */
	private int gongHuiKjMaxLv;

	/**
	 * 银两
	 */
	private long money;

	/**
	 * 充值元宝
	 */
	private long gold;

	/**
	 * 免费元宝
	 */
	private long freeGold;

	/**
	 * 累计元宝数(发奖会发这个字段)
	 */
	private long leijiGold;

	/**
	 * 官品，参加官品计算后官品以gradeDAO的数据为准
	 */
	private int grade;

	/**
	 * 历史充值数(真正的历史充值)
	 */
	private long historyGold;

	/**
	 * 累计花费元宝
	 */
	private long leijiConsumeGold;

	/**
	 * vip等级
	 */
	private int vipLv;

	/**
	 * 所属阵营
	  */
	private int camp;
	
	/**
	 * 签名
	 */
	@Column(length = 200)
	private String qianming;

	private long loginTime;

	private long createTime;

	private long logoutTime;

	/**
	 * 登录次数(每天只记一次)
	 */
	private int loginCount;

	/**
	 * 背包开启格子数
	 */
	private int bagOpenGridCount;

	/**
	 * 背包开启次数
	 */
	private int bagOpenCount;

	/**
	 * 职业
	 */
	private int job;

	/**
	 * 冗余总战力
	 */
	@Column(index = true)
	private int zhanli;

	/**
	 * 历史最高总战力
	 */
	private int maxZhanli;

	/**
	 * 物品列表
	 */
	@Column(ignore = true)
	private List<ItemModel> itemList = new ArrayList<ItemModel>();

	/**
	 * 随从列表
	 */
	@Column(ignore = true)
	private List<ServantModel> servantList = new ArrayList<ServantModel>();

	/**
	 * 货币
	 */
	@Column(convertClazz = MapNumberOrStringColumnConvert.class, length = 65535)
	private Map<Integer, Long> currencys = new HashMap<Integer, Long>();

	/**
	 * 周期数据
	 */
	@Column(ignore = true)
	private List<CycleOperateModel> cycleOperateList = new ArrayList<CycleOperateModel>();

	/**
	 * 关卡列表
	 */
	@Column(ignore = true)
	private List<GuankaModel> guankaList = new ArrayList<GuankaModel>();


	/**
	 * 普通场景奖励是否领取
	 */
	@Column(convertClazz = MapNumberOrStringColumnConvert.class, length = 65535)
	private Map<Integer, Integer> sceneNorAwards = new HashMap<Integer, Integer>();


	/**
	 * 困难场景奖励是否领取
	 */
	@Column(convertClazz = MapNumberOrStringColumnConvert.class, length = 65535)
	private Map<Integer, Integer> sceneDifAwards = new HashMap<Integer, Integer>();

	/**
	 * 冗余性别男当前穿上的装扮
	 */
	@Column(convertClazz = CollectionNumberOrStringColumnConvert.class, length = 65535)
	private List<Integer> dressOnMale;

	/**
	 * 冗余性别女当前穿上的装扮
	 */
	@Column(convertClazz = CollectionNumberOrStringColumnConvert.class, length = 65535)
	private List<Integer> dressOnFemale;

	/**
	 * 开礼包保底数据<礼包ID, 开启次数>
	 */
	@Column(convertClazz = MapNumberOrStringColumnConvert.class, length = 65535)
	private Map<Integer, Integer> baodiData = new HashMap<Integer, Integer>();

	/**
	 * 充值信息
	 */
	@Embedded
	private PayInfoModel payInfoModel = new PayInfoModel();
	/**
	 * 所属派系Id
	 */
	private String gongHuiId;


	/*
	 * 好友未读消息提示 已读：0 未读：1
	 */
	private int isReadMsg;
	
	/**
	 * 是否为机器人 否：0 是：1
	 */
	private int robotId;

	/**
	 * 所属派系名字
	 */
	private String gongHuiName;


	/**
	 * 所属派系职位
	 */
	private String gongHuiPositon;

	/**
	 * 上次退出派系的时间
	 */
	private long lastExitGongHuiTime;

	/**
	 * 抽良人CD时间
	 */
	private long secServantLiangRenCDTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(int userGroup) {
		this.userGroup = userGroup;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
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

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getIconIndex() {
		return iconIndex;
	}

	public void setIconIndex(int iconIndex) {
		this.iconIndex = iconIndex;
	}

	public long getMoney() {
		return money;
	}

	public void setMoney(long money) {
		this.money = money;
	}

	public int getVipLv() {
		return vipLv;
	}

	public void setVipLv(int vipLv) {
		this.vipLv = vipLv;
	}

	public long getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(long loginTime) {
		this.loginTime = loginTime;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(long logoutTime) {
		this.logoutTime = logoutTime;
	}

	public int getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}

	public int getJob() {

		return job;
	}

	public void setJob(int job) {

		this.job = job;
	}

	public List<ItemModel> getItemList() {
		return itemList;
	}

	public void setItemList(List<ItemModel> itemList) {
		this.itemList = itemList;
	}

	public List<ServantModel> getServantList() {
		return servantList;
	}

	public void setServantList(List<ServantModel> servantList) {
		this.servantList = servantList;
	}

	public int getBagOpenGridCount() {
		return bagOpenGridCount;
	}

	public void setBagOpenGridCount(int bagOpenGridCount) {
		this.bagOpenGridCount = bagOpenGridCount;
	}

	public int getBagOpenCount() {
		return bagOpenCount;
	}

	public void setBagOpenCount(int bagOpenCount) {
		this.bagOpenCount = bagOpenCount;
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

	public long getLeijiConsumeGold() {
		return leijiConsumeGold;
	}

	public void setLeijiConsumeGold(long leijiConsumeGold) {
		this.leijiConsumeGold = leijiConsumeGold;
	}

	public Map<Integer, Long> getCurrencys() {
		if (currencys == null) {
			currencys = new HashMap<Integer, Long>();
		}
		return currencys;
	}

	public void setCurrencys(Map<Integer, Long> currencys) {
		this.currencys = currencys;
	}

	public List<GuankaModel> getGuankaList() {

		return guankaList;
	}

	public void setGuankaList(List<GuankaModel> guankaList) {

		this.guankaList = guankaList;
	}

	public List<CycleOperateModel> getCycleOperateList() {

		return cycleOperateList;
	}

	public void setCycleOperateList(List<CycleOperateModel> cycleOperateList) {

		this.cycleOperateList = cycleOperateList;
	}

	public List<Integer> getDressOnMale() {
		if (dressOnMale == null) {
			dressOnMale = new ArrayList<Integer>();
		}
		return dressOnMale;
	}

	public void setDressOnMale(List<Integer> dressOnMale) {

		this.dressOnMale = dressOnMale;
	}

	public List<Integer> getDressOnFemale() {
		if (dressOnFemale == null) {
			dressOnMale = new ArrayList<Integer>();
		}
		return dressOnFemale;
	}

	public void setDressOnFemale(List<Integer> dressOnFemale) {

		this.dressOnFemale = dressOnFemale;
	}

	public PayInfoModel getPayInfoModel() {
		return payInfoModel;
	}

	public void setPayInfoModel(PayInfoModel payInfoModel) {
		this.payInfoModel = payInfoModel;
	}

	public void incrVipLv(int v) {
		setVipLv(GongUtils.adjustNumberInRange(getVipLv() + v, 0, SysConstData.data.getInt("VIP")));
	}

	public void incrMoney(long v) {
		setMoney(GongUtils.adjustNumberInRange(getMoney() + v, 0, SysConstData.data.getLong("MONEY")));
	}

	public void incrGold(long v) {
		setGold(GongUtils.adjustNumberInRange(getGold() + v, 0, SysConstData.data.getLong("GOLD")));
	}

	public void incrFreeGold(long v) {
		setFreeGold(GongUtils.adjustNumberInRange(getFreeGold() + v, 0, SysConstData.data.getLong("GOLD")));
	}

	public void incrLeijiGold(long v) {
		setLeijiGold(GongUtils.adjustNumberInRange(getLeijiGold() + v, 0, Long.MAX_VALUE));
	}

	public void incrHistoryGold(long v) {
		setHistoryGold(GongUtils.adjustNumberInRange(getHistoryGold() + v, 0, Long.MAX_VALUE));
	}

	public void incrLeijiConsumeGold(long v) {
		setLeijiConsumeGold(GongUtils.adjustNumberInRange(getLeijiConsumeGold() + v, 0, Long.MAX_VALUE));
	}

	public void incrLv(int v) {
		setLv(GongUtils.adjustNumberInRange(getLv() + v, 0, Integer.MAX_VALUE));
	}

	public void incrExp(long val) {
		setExp(GongUtils.adjustNumberInRange(getExp() + val, 0, Long.MAX_VALUE));
	}

	public void incrLoginCount(int incr) {
		setLoginCount(GongUtils.adjustNumberInRange(getLoginCount() + incr, 0, Integer.MAX_VALUE));
	}

	public String getQianming() {
		return qianming;
	}

	public void setQianming(String qianming) {
		this.qianming = qianming;
	}



	public Map<Integer, Integer> getBaodiData() {
		if (baodiData == null) {
			baodiData = new HashMap<Integer, Integer>();
		}
		return baodiData;
	}

	public void setBaodiData(Map<Integer, Integer> baodiData) {
		this.baodiData = baodiData;
	}


	public Map<Integer, Integer> getSceneNorAwards() {
		if (sceneNorAwards == null) {
			sceneNorAwards = new HashMap<Integer, Integer>();
		}
		return sceneNorAwards;
	}

	public void setSceneNorAwards(Map<Integer, Integer> sceneNorAwards) {
		this.sceneNorAwards = sceneNorAwards;
	}



	public Map<Integer, Integer> getSceneDifAwards() {
		if (sceneDifAwards == null) {
			sceneDifAwards = new HashMap<Integer, Integer>();
		}
		return sceneDifAwards;
	}

	public void setSceneDifAwards(Map<Integer, Integer> sceneDifAwards) {
		this.sceneDifAwards = sceneDifAwards;
	}


	public int getIsReadMsg() {
		return isReadMsg;
	}

	public void setIsReadMsg(int isReadMsg) {
		this.isReadMsg = isReadMsg;
	}

	public int getRobotId() {
		return robotId;
	}

	public void setRobotId(int robotId) {
		this.robotId = robotId;
	}

	public String getGongHuiId() {
		return gongHuiId;
	}

	public void setGongHuiId(String gongHuiId) {
		this.gongHuiId = gongHuiId;
	}

	public String getGongHuiName() {
		return gongHuiName;
	}

	public void setGongHuiName(String gongHuiName) {
		this.gongHuiName = gongHuiName;
	}

	public String getGongHuiPositon() {
		return gongHuiPositon;
	}

	public void setGongHuiPositon(String gongHuiPositon) {
		this.gongHuiPositon = gongHuiPositon;
	}

	public int getZhanli() {
		return zhanli;
	}

	public void setZhanli(int zhanli) {
		this.zhanli = zhanli;
	}

	public int getMaxZhanli() {
		return maxZhanli;
	}

	public void setMaxZhanli(int maxZhanli) {
		this.maxZhanli = maxZhanli;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getGongHuiKjMaxLv() {
		return gongHuiKjMaxLv;
	}

	public void setGongHuiKjMaxLv(int gongHuiKjMaxLv) {
		this.gongHuiKjMaxLv = gongHuiKjMaxLv;
	}

	public long getLastExitGongHuiTime() {
		return lastExitGongHuiTime;
	}

	public void setLastExitGongHuiTime(long lastExitGongHuiTime) {
		this.lastExitGongHuiTime = lastExitGongHuiTime;
	}

	public long getSecServantLiangRenCDTime() {
		return secServantLiangRenCDTime;
	}

	public void setSecServantLiangRenCDTime(long secServantLiangRenCDTime) {
		this.secServantLiangRenCDTime = secServantLiangRenCDTime;
	}

	public int getCamp() {
		return camp;
	}

	public void setCamp(int camp) {
		this.camp = camp;
	}
}
