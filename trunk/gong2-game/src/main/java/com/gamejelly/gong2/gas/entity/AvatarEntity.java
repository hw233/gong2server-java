package com.gamejelly.gong2.gas.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.gamejelly.gong2.gas.service.shared.SharedDataService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;

import com.gamejelly.gong2.config.data.ExpLvData;
import com.gamejelly.gong2.config.data.InitData;
import com.gamejelly.gong2.config.data.SysConstData;
import com.gamejelly.gong2.config.data.VipData;
import com.gamejelly.gong2.config.data.base.LList;
import com.gamejelly.gong2.config.data.base.LMap;
import com.gamejelly.gong2.gas.service.user.ItemService;
import com.gamejelly.gong2.gas.service.user.UserService;
import com.gamejelly.gong2.meta.AvatarModel;
import com.gamejelly.gong2.meta.GongHuiMemberModel;
import com.gamejelly.gong2.meta.share.AvatarTempValueModel;
import com.gamejelly.gong2.meta.GongHuiModel;
import com.gamejelly.gong2.meta.CycleOperateModel;
import com.gamejelly.gong2.meta.GuankaModel;
import com.gamejelly.gong2.meta.ItemModel;
import com.gamejelly.gong2.meta.ServantModel;
import com.gamejelly.gong2.utils.GongLogConstants;
import com.gamejelly.gong2.utils.GongLogger;
import com.gamejelly.gong2.utils.GongRpcConstants;
import com.gamejelly.gong2.utils.GongRuntimeException;
import com.gamejelly.gong2.utils.GongUtils;
import com.gamejelly.gong2.utils.LogicUtils;
import com.gamejelly.gong2.vo.AvatarVO;
import com.gamejelly.gong2.vo.ServantVO;
import com.hadoit.game.common.lang.DataUtils;
import com.hadoit.game.common.lang.Pair;
import com.hadoit.game.engine.core.executor.TimerListener;
import com.hadoit.game.engine.guardian.gas.GasManager;
import com.hadoit.game.engine.guardian.gas.entity.Entity;
import com.hadoit.game.engine.guardian.gas.entity.EntityCallback;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;

public class AvatarEntity extends Entity {

	private Map<String, ItemModel> itemMap;

	private Map<String, ServantEntity> servantMap;

	private Map<Integer, CycleOperateModel> cycleOperateMap;

	private Map<Integer, GuankaModel> guankaMap;

	private Map<String, GongHuiModel> gongHuiMap;

	private boolean wudi;
	
	private long lastSayTime;
	
	private long lastSayGongHuiTime;

	
	public long getLastSayTime() {
		return lastSayTime;
	}

	public void setLastSayTime(long lastSayTime) {
		this.lastSayTime = lastSayTime;
	}

	public long getLastSayGongHuiTime() {
		return lastSayGongHuiTime;
	}

	public void setLastSayGongHuiTime(long lastSayGongHuiTime) {
		this.lastSayGongHuiTime = lastSayGongHuiTime;
	}

	@Override
	public String getId() {
		return getAvatarModel().getId();
	}

	@Override
	public String getEntityType() {
		return "AvatarEntity";
	}

	public AvatarModel getAvatarModel() {
		return (AvatarModel) super.getDbModel();
	}

	public int getLv() {
		return getAvatarModel().getLv();
	}

	public int getJob() {
		return getAvatarModel().getJob();
	}

	@Override
	protected void onInit() {
		initData();
	}

	@Override
	public void writeToDB(EntityCallback<Entity> callback) {
		beforeWriteToDB();
		super.writeToDB(callback);
	}

	@Override
	protected void onDestroy() {
		beforeWriteToDB();
		super.onDestroy();
	}

	protected void beforeWriteToDB() {
		syncReddData();
	}

	/**
	 * 缓存Entity数据到model
	 */
	public void syncReddData() {
		AvatarModel am = getAvatarModel();

	}

	public void onLogin() {
		startGlobalTimer();
	}

	private void startGlobalTimer() {
		addCycleTimer();
	}

	public void initCommonData(AvatarEntity entity) {

		// 初始化 发东西
		long money = InitData.data.getMap(1).getLong("money", 0l);
		long gold = InitData.data.getMap(1).getLong("gold", 0l);

		entity.getAvatarModel().setFreeGold(gold);
		entity.getAvatarModel().setMoney(money);

		LList listItem = InitData.data.getMap(1).getList("initItem");
		LList listItemCount = InitData.data.getMap(1).getList("itemNum");

		ItemService is = GongUtils.getItemService();
		if (listItem != null && listItemCount != null) {
			if (listItem.size() == listItemCount.size()) {
				for (int i = 0; i < listItem.size(); i++) {
					int itemID = listItem.getInt(i);
					int curCount = listItemCount.getInt(i);
					is.addItemOrCurrency(this, itemID, curCount, GongLogConstants.LOG_ITEM_CHANGE_AVATAR_CREATE, 0, 0);
				}
			} else {
				GuardianLogger.error("InitData  error  initItem Size=", listItem.size(), " itemNum Size=",
						listItemCount.size());
			}
		}

		// 初始化仆从
		int randomJobID = entity.getAvatarModel().getSex();
		String defaultServantTemplateIdKey = "JOB_DEFAULT_SERVANT_" + randomJobID;
		entity.getAvatarModel().setJob(randomJobID);
		Pair<ServantModel, List<ItemModel>> ret = LogicUtils
				.createServantModel(SysConstData.data.getInt(defaultServantTemplateIdKey), entity.getLv());
		ServantModel sm = ret.getFirst();
		sm.setAvatar(true);
		sm.setPos(SysConstData.data.getInt("AVATAR_FIGHT_INIT_POS", 2));
		ServantEntity se = new ServantEntity(entity, sm);
		se.calcAll();
		entity.addServant(se);
		if (ret.getSecond() != null) {
			entity.addAllItem(ret.getSecond());
		}

	}

	public void initDataAfterCreateAvatarModel(AvatarEntity entity) {
		initCommonData(entity);

	}

	protected void initData() {
		itemMap = new HashMap<String, ItemModel>();
		for (ItemModel im : getAvatarModel().getItemList()) {
			itemMap.put(im.getId(), im);
		}

		servantMap = new HashMap<String, ServantEntity>();
		for (ServantModel sm : getAvatarModel().getServantList()) {
			ServantEntity se = new ServantEntity(this, sm);
			servantMap.put(sm.getId(), se);
		}
		for (ServantEntity se : servantMap.values()) {
			se.calcAll();
		}

		cycleOperateMap = new HashMap<Integer, CycleOperateModel>();
		for (CycleOperateModel co : getAvatarModel().getCycleOperateList()) {
			cycleOperateMap.put(co.getOperateType(), co);
		}

		guankaMap = new HashMap<Integer, GuankaModel>();
		for (GuankaModel gkm : getAvatarModel().getGuankaList()) {
			guankaMap.put(gkm.getTemplateId(), gkm);
		}
		gongHuiMap=new HashMap<String, GongHuiModel>();
	}

	/**
	 * 只增加充值累计
	 * 
	 * @param val
	 */
	public void addLeijiOnly(long val) {
		if (val <= 0) {
			return;
		}
		getAvatarModel().incrLeijiGold(val);
		checkIncrVipLv();
	}

	public void checkIncrVipLv() {

		while (true) {
			LMap nextVipData = VipData.data.getMap(getAvatarModel().getVipLv() + 1);
			if (nextVipData == null) {
				// 升到顶了
				break;
			}
			if (nextVipData.getLong("payGold") > getAvatarModel().getLeijiGold()) {
				break;
			}
			getAvatarModel().incrVipLv(1);
		}
	}

	public void addMoney(long incr, int sourceType, int sourceId1, int sourceId2) {
		if (incr <= 0) {
			return;
		}
		long oldMoney = getAvatarModel().getMoney();
		getAvatarModel().incrMoney(incr);
		GongLogger.logMoneyChange(this, oldMoney, sourceType, sourceId1, sourceId2);
	}

	public void addGold(long incr, int sourceType, int sourceId1, boolean isAliBigPay, boolean isThgm,
			int productKind) {
		if (incr <= 0) {
			return;
		}

		long oldFreeGold = getAvatarModel().getFreeGold();
		long oldGold = getAvatarModel().getGold();
		getAvatarModel().incrGold(incr);
		getAvatarModel().incrHistoryGold(incr);
		getAvatarModel().incrLeijiGold(incr);

		GongLogger.logGoldChange(this, oldFreeGold, oldGold, sourceType, sourceId1, -1, "");
	}

	public void addFreeGold(long incr, int sourceType) {
		if (incr <= 0) {
			return;
		}
		long oldFreeGold = getAvatarModel().getFreeGold();
		long oldGold = getAvatarModel().getGold();
		getAvatarModel().incrFreeGold(incr);
		GongLogger.logGoldChange(this, oldFreeGold, oldGold, sourceType, -1, -1, "");
	}

	private void incrCurrencyValue(int key, long incr) {
		long oldVal = DataUtils.getMapLong(getAvatarModel().getCurrencys(), key, 0l);
		oldVal += incr;
		getAvatarModel().getCurrencys().put(key, oldVal);
	}

	public void addCurrencyValue(int key, long incr, int sourceType, int sourceId1, int sourceId2) {
		if (incr <= 0) {
			return;
		}
		long oldVal = DataUtils.getMapLong(getAvatarModel().getCurrencys(), key, 0l);
		incrCurrencyValue(key, incr);
		GongLogger.logCurrencyChange(this, key, oldVal, DataUtils.getMapLong(getAvatarModel().getCurrencys(), key, 0l),
				sourceType, sourceId1, sourceId2);
	}

	public List<ItemModel> getAllItem() {
		return new ArrayList<ItemModel>(itemMap.values());
	}

	public void addItem(ItemModel itemModel) {
		if (itemModel == null) {
			return;
		}
		itemMap.put(itemModel.getId(), itemModel);
		getAvatarModel().getItemList().add(itemModel);
	}

	public void addAllItem(Collection<ItemModel> items) {
		if (CollectionUtils.isEmpty(items)) {
			return;
		}
		for (ItemModel im : items) {
			addItem(im);
		}
	}

	public ItemModel removeItemByCount(String itemId, int count) {
		if (itemMap.containsKey(itemId)) {
			ItemModel im = itemMap.get(itemId);
			im.setCount(im.getCount() - count);
			if (im.getCount() == 0) {
				itemMap.remove(itemId);
				getAvatarModel().getItemList().remove(im);
			}

			return im;
		}
		return null;
	}

	public ItemModel removeItem(String itemId) {
		if (itemMap.containsKey(itemId)) {
			ItemModel im = itemMap.remove(itemId);
			getAvatarModel().getItemList().remove(im);

			return im;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<ItemModel> getItemsFromTemplateId(final int templateId) {
		List<ItemModel> ret = (List<ItemModel>) CollectionUtils.select(itemMap.values(), new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				return ((ItemModel) object).getTemplateId() == templateId;
			}
		});

		return ret;
	}

	public int getBagUsedGridCount() {
		int count = 0;
		for (int i = 0; i < getAvatarModel().getItemList().size(); i++) {
			ItemModel im = getAvatarModel().getItemList().get(i);
			if (im.isInBag()) {
				count++;
			}
		}
		return count;
	}

	/**
	 * 获取背包剩余格子数
	 * 
	 * @return
	 */
	public int getBagLeftGridCount() {
		return getAvatarModel().getBagOpenGridCount() - getBagUsedGridCount();
	}

	public List<ServantEntity> getAllServant() {
		return new ArrayList<ServantEntity>(servantMap.values());
	}

	public int getAllServantCount() {
		return getAvatarModel().getServantList().size();
	}

	public List<ServantVO> getAllServantVoList() {
		final List<ServantVO> serList = new ArrayList<ServantVO>(this.getAllServantCount());
		for (ServantEntity ser : this.getAllServant()) {
			serList.add(new ServantVO(ser));
		}
		return serList;
	}


	/**
	 *
	 * @param servantTempId
	 * @return 根据仆从模版ID查找servant
	 */
	public ServantEntity getServantByTempId(int servantTempId){
		for (Map.Entry<String,ServantEntity> entry : servantMap.entrySet()){
			ServantEntity servantEntity = entry.getValue();
			int curTempId = servantEntity.getModel().getTemplateId();
			if(curTempId == servantTempId){
				return entry.getValue();
			}
		}
		return null;
	}



	public ServantEntity getServantFromPos(final int pos) {
		ServantEntity ret = (ServantEntity) CollectionUtils.find(servantMap.values(), new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				return ((ServantEntity) object).getModel().getPos() == pos;
			}
		});
		return ret;
	}
	//根据servantid 查找servant
	public ServantEntity getServant(final String servantid) {
		return servantMap.get(servantid);
	}

	@SuppressWarnings("unchecked")
	public List<ServantEntity> getShangzhenServant() {
		List<ServantEntity> ret = (List<ServantEntity>) CollectionUtils.select(servantMap.values(), new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				return ((ServantEntity) object).getModel().getPos() > 0;
			}
		});
		return ret;
	}

	public void addServant(ServantEntity se) {
		if (se == null) {
			return;
		}
		servantMap.put(se.getId(), se);
		getAvatarModel().getServantList().add(se.getModel());
	}

	public ServantEntity removeServant(String servantId) {
		if (servantMap.containsKey(servantId)) {
			ServantEntity se = servantMap.remove(servantId);
			getAvatarModel().getServantList().remove(se.getModel());

			return se;
		}
		return null;
	}

	public int getCycleOptCdCount(int operateType) {
		int ret = 0;
		CycleOperateModel cModel = getCycleOperate(operateType);
		if (cModel == null) {
			return ret;
		}
		ret = (int) cModel.getCdCount();
		return ret;
	}

	public CycleOperateModel getCycleOperate(int operateType) {
		return cycleOperateMap.get(operateType);
	}

	public void addCycleOperate(CycleOperateModel co) {
		CycleOperateModel oldCo = cycleOperateMap.put(co.getOperateType(), co);
		if (oldCo != null) {
			getAvatarModel().getCycleOperateList().remove(oldCo);
		}
		getAvatarModel().getCycleOperateList().add(co);
	}

	public GuankaModel getGuanka(int templateId) {
		return guankaMap.get(templateId);
	}

	public GuankaModel addGuanka(int templateId) {
		GuankaModel gm = new GuankaModel();
		gm.setTemplateId(templateId);
		guankaMap.put(templateId, gm);
		getAvatarModel().getGuankaList().add(gm);
		return gm;
	}

	public List<GuankaModel> getGuankaByScene(int sceneTemplateId) {
		List<Integer> gks = GongUtils.getGkListByScene(sceneTemplateId);
		if (CollectionUtils.isEmpty(gks)) {
			return null;
		}
		List<GuankaModel> ret = new ArrayList<GuankaModel>();
		for (Integer gk : gks) {
			GuankaModel gkm = getGuanka(gk);
			if (gkm != null) {
				ret.add(gkm);
			}
		}
		return ret;
	}

	public List<GuankaModel> getAllGuanka() {

		List<GuankaModel> allGuanKamodel = getAvatarModel().getGuankaList();
		long curTime = System.currentTimeMillis();
		for (Iterator<GuankaModel> iterator = allGuanKamodel.iterator(); iterator.hasNext();) {
			GuankaModel guankaModel = (GuankaModel) iterator.next();
			if (!GongUtils.isSameDayForOffset(guankaModel.getLastFightTime(), curTime, 0)) {
				guankaModel.setLastFightTime(curTime);
				guankaModel.setFightCount(0);
			}
		}

		return allGuanKamodel;
	}

	private void addCycleTimer() {
		int randomSecs = 1;
		final String myAvatarId = this.getId();

		// 周期性数据, 每天0点重置
		addTimer(new TimerListener() {
			@Override
			public void onTimer(long id, Object[] params) throws Exception {
				GasManager myGasManager = GongUtils.getGasManager();
				if (myGasManager == null) {
					return;
				}
				AvatarEntity myAvatar = (AvatarEntity) myGasManager.getEntityManager().getEntity(myAvatarId);
				if (myAvatar == null || myAvatar.isUnused()) {
					return;
				}
				GuardianLogger.info("addCycleTimer myAvatarId=", myAvatarId, " name=",
						myAvatar.getAvatarModel().getRoleName());

				long curTime = System.currentTimeMillis();
				UserService userService = GongUtils.getUserService();
				// 每天计算重置周期性数据
				userService.resetData(myAvatar, curTime);

				myAvatar.notify(GongRpcConstants.RES_USER_AVATAR_CYCLE_CHANGE, curTime);
				myAvatar.notify(GongRpcConstants.RES_USER_AVATAR_CHANGE, new AvatarVO(myAvatar));

				addCycleTimer();
			}
		}, GongUtils.getOffsetTimeSecsToZero() + randomSecs, 0); // 往后推X秒
	}

	private void doAddExp(long val) {
		LMap nextLvData = ExpLvData.data.getMap(getAvatarModel().getLv() + 1);

		if (nextLvData == null) {
			// 升到顶了
			return;
		}
		LMap lvData = ExpLvData.data.getMap(this.getAvatarModel().getLv());
		if (getAvatarModel().getExp() + val < lvData.getInt("upExp")) {
			getAvatarModel().incrExp(val);
			return;
		}
		getAvatarModel().incrLv(1);
		getAvatarModel().incrExp(val - lvData.getInt("upExp"));
		doAddExp(0);
	}

	public boolean addExp(long val, int sourceType) {
		int oldLv = getAvatarModel().getLv();
		val = GongUtils.getExpAfterPunish(getLv(), val);

		doAddExp(val);
		int newLv = getAvatarModel().getLv();

		boolean changed = newLv != oldLv;
		if (changed) {
			notify(GongRpcConstants.RES_USER_AVATAR_LV_CHANGE, new Object[] { getAvatarModel().getLv() });
		}
		GongLogger.logExpChange(this, (int) val, oldLv, sourceType, -1, -1);
		return changed;
	}






	public boolean isWudi() {

		return wudi;
	}

	public void setWudi(boolean wudi) {

		this.wudi = wudi;
	}




	public ItemModel getUsedZhenfa() {
		if (MapUtils.isEmpty(itemMap)) {
			return null;
		}

		for (ItemModel zf : itemMap.values()) {
			if (zf.getTemplate() != null
					&& zf.getTemplate().getInt("kind", 0) == SysConstData.data.getInt("ITEM_KIND_4") && zf.isZfUsed()) {
				return zf;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<ItemModel> getListFromOwner(final String ownerId, final int targetKind) {
		return (List<ItemModel>) CollectionUtils.select(itemMap.values(), new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				if (((ItemModel) object).getTemplate() != null
						&& ((ItemModel) object).getTemplate().getInt("kind", 0) == targetKind) {
					return ownerId.equals(((ItemModel) object).getOwnerId());
				}
				return false;
			}
		});
	}

	/**
	 * 拿装备法宝
	 * 
	 * @param ownerId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ItemModel> getEquipFbListFromOwner(final String ownerId) {
		List<ItemModel> l = getListFromOwner(ownerId, SysConstData.data.getInt("ITEM_KIND_7"));
		List<ItemModel> ret = (List<ItemModel>) CollectionUtils.select(l, new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				return ((ItemModel) object).getFbTemplate().getInt("fbKind") == SysConstData.data.getInt("FB_KIND_4");
			}
		});

		return ret;
	}

	/**
	 * 拿主动法宝
	 * 
	 * @param ownerId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ItemModel> getInFbListFromOwner(final String ownerId) {
		List<ItemModel> l = getListFromOwner(ownerId, SysConstData.data.getInt("ITEM_KIND_7"));
		List<ItemModel> ret = (List<ItemModel>) CollectionUtils.select(l, new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				int fbKind = ((ItemModel) object).getFbTemplate().getInt("fbKind");
				return (fbKind == SysConstData.data.getInt("FB_KIND_1"))
						|| (fbKind == SysConstData.data.getInt("FB_KIND_3"));
			}
		});

		return ret;
	}

	/**
	 * 拿被动法宝
	 * 
	 * @param ownerId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ItemModel> getPaFbListFromOwner(final String ownerId) {
		List<ItemModel> l = getListFromOwner(ownerId, SysConstData.data.getInt("ITEM_KIND_7"));
		List<ItemModel> ret = (List<ItemModel>) CollectionUtils.select(l, new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				return ((ItemModel) object).getFbTemplate().getInt("fbKind") == SysConstData.data.getInt("FB_KIND_2");
			}
		});

		return ret;
	}

	public int getItemCountFromTemplateId(final int templateId) {
		int ret = 0;
		for (ItemModel im : itemMap.values()) {
			if (im.getTemplateId() == templateId) {
				ret += im.getCount();
			}
		}
		return ret;
	}

	public boolean canConsumeGold(long v) {
		if (v < 0) {
			return false;
		}
		long gold = getAvatarModel().getGold();
		long freeGold = getAvatarModel().getFreeGold();
		if (gold + freeGold < v) {
			return false;
		}
		return true;
	}

	// 元宝修改
	public boolean consumeGold(long v, int sourceType, String desc) {
		return consumeGold(v, sourceType, -1, -1, desc);
	}

	// 元宝修改
	public boolean consumeGold(long v, int sourceType, int sourceId1, int sourceId2, String desc) {
		if (v < 0) {
			throw new GongRuntimeException("!!!消费gold异常 v=" + v);
		}
		long gold = getAvatarModel().getGold();
		long freeGold = getAvatarModel().getFreeGold();
		if (gold + freeGold < v) {
			return false;
		}
		long oldFreeGold = getAvatarModel().getFreeGold();
		long oldGold = getAvatarModel().getGold();
		if (gold >= v) {
			getAvatarModel().incrGold(-v);
		} else {
			getAvatarModel().incrGold(-gold);
			getAvatarModel().incrFreeGold(gold - v);
		}

		if (v > 0) {
			getAvatarModel().incrLeijiConsumeGold(v);

			if (StringUtils.isNotEmpty(desc) && (desc.equals("thgm") || desc.equals("czjj"))) {
				// 成长基金的消费，和特惠购买的不触发

			} else {

			}

		}

		GongLogger.logGoldChange(this, oldFreeGold, oldGold, sourceType, sourceId1, sourceId2, desc);
		return true;
	}


	public int getTotalZhanli() {
		List<ServantEntity> szmts = getShangzhenServant();
		int totalZl = 0;
		for (ServantEntity mt : szmts) {
			if (mt.getModel().getPos() > 0) {
				totalZl += mt.getZhanli();
			}
		}
		return totalZl;
	}


	/**-------------------------------------------帮派-----------------------------------------------------*/

	// 工会相关
	public GongHuiModel getGongHuiModel(String ghId) {
		if (ghId == null) {
			ghId = getAvatarModel().getGongHuiId();
		}
		GongHuiModel ghModel = StringUtils.isBlank(ghId) ? null : gongHuiMap.get(ghId);
		if(ghModel != null){
			GuardianLogger.info("avatarentity  getGongHuiModel is not null and ghModel.isEnable() = " + ghModel.isEnable());
		}else{
			GuardianLogger.info("avatarentity  getGongHuiModel  ghModel is null ! " );
		}
		return ghModel != null && ghModel.isEnable() ? ghModel : null;
	}

	public void removeGongHuiModel(String ghId) {
		gongHuiMap.remove(ghId);
	}

	public void addGongHuiModel(GongHuiModel ghModel) {
		gongHuiMap.put(ghModel.getId(), ghModel);
	}

	public void setGongHuiMap(List<GongHuiModel> gongHuiList) {
			gongHuiMap.clear();
			for (GongHuiModel ghModel : gongHuiList) {
				gongHuiMap.put(ghModel.getId(), ghModel);
			}

	}

	public AvatarTempValueModel getAvatarTempValueModel(boolean needCreateIfNull) {
		SharedDataService sharedDataService = GongUtils.getSharedDataService();
		if (sharedDataService == null) {
			return null;
		}

		if (sharedDataService.getSharedEntity() == null) {
			return null;
		}

		AvatarTempValueModel avatarTempValueModel = sharedDataService.getSharedEntity().getAvatarTempVauleModel(getId(),
				needCreateIfNull);
		if (avatarTempValueModel == null) {
			return null;
		}
		return avatarTempValueModel;
	}

	public Map<String, GongHuiModel> getGongHuiMap() {
		return gongHuiMap;
	}


}