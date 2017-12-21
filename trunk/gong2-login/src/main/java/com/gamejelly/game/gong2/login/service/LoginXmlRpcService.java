package com.gamejelly.game.gong2.login.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.dao.GameAccountDao;
import com.gamejelly.game.gong2.login.dao.IdfaDeviceDataDao;
import com.gamejelly.game.gong2.login.meta.AppstorePurchasedRecord;
import com.gamejelly.game.gong2.login.meta.CouponRecord;
import com.gamejelly.game.gong2.login.meta.Feedback;
import com.gamejelly.game.gong2.login.meta.GameAccount;
import com.gamejelly.game.gong2.login.meta.GlobalCouponRecord;
import com.gamejelly.game.gong2.login.meta.IdfaDeviceData;
import com.gamejelly.game.gong2.login.meta.Notice;
import com.gamejelly.game.gong2.login.meta.PlatformPayRecord;
import com.gamejelly.game.gong2.login.meta.ServerInfo;
import com.gamejelly.game.gong2.login.net.GasAdminClientManager;
import com.gamejelly.game.gong2.login.service.sdk.SdkServiceHelper;
import com.gamejelly.game.gong2.login.utils.FsGameLoginConst;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.google.gson.reflect.TypeToken;
import com.hadoit.game.common.lang.Pair;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

@Component("loginXmlRpcService")
public class LoginXmlRpcService {

	@Autowired
	private ServerInfoService serverInfoService;

	@Autowired
	private GasAdminClientManager gasAdminClientManager;

	@Autowired
	private NoticeService noticeService;

	@Autowired
	private SystemDataService systemDataService;

	@Autowired
	private CouponRecordService couponRecordService;

	@Autowired
	private FeedbackService feedbackService;

	@Autowired
	private PlatformPayRecordService platformPayRecordService;

	@Autowired
	private SdkServiceHelper sdkServiceHelper;

	@Autowired
	private GameAccountDao gameAccountDao;

	@Autowired
	private AppstorePurchasedRecordService appstorePurchasedRecordService;

	@Autowired
	private AppleAccountRelationService appleAccountRelationService;

	@Autowired
	private GameAccountService gameAccountService;
	@Autowired
	private IdfaDeviceDataDao idfaDeviceDataDao;

	@Autowired
	@Value("${db_log.url}")
	private String dbLogUrl;

	@Autowired
	@Value("${db_log.username}")
	private String dbLogUserName;

	@Autowired
	@Value("${db_log.password}")
	private String dbLogPassword;

	public boolean touch() {
		return true;
	}

	public String saveServerInfo(String serverInfo) {
		ServerInfo si = GsonFactory.getDefault().fromJson(serverInfo, ServerInfo.class);
		ServerInfo oldSi = serverInfoService.getServerBy(si.getServerId(), si.getOprGroup());
		if (oldSi == null) {
			si.setState(FsGameLoginConst.SERVER_STATE_CLOSE);
			si.setCreateTime(System.currentTimeMillis());
			boolean ret = serverInfoService.addServer(si);
			if (!ret) {
				return "渠道和服务器ID重复了!";
			}
		} else {
			si.setId(oldSi.getId());
			si.setState(oldSi.getState());
			si.setCreateTime(oldSi.getCreateTime());
			serverInfoService.updateServer(si);
		}
		gasAdminClientManager.connectGasClient(si);
		return "SUCCESS";
	}

	public String updateOprMatchStr(String oprGroup, String oprMatchStr) {
		List<ServerInfo> siList = serverInfoService.getServerByOprGroup(oprGroup);
		if (CollectionUtils.isNotEmpty(siList)) {
			for (ServerInfo si : siList) {
				si.setOprMatchStr(oprMatchStr);
				serverInfoService.updateServer(si);
				gasAdminClientManager.connectGasClient(si);
			}
		}
		return "SUCCESS";
	}

	public String delServerInfo(String oprGroup, int serverId) {
		serverInfoService.deleteServerAndRemoveConnect(serverId, oprGroup);
		return "SUCCESS";
	}

	public String addNotice(String notice) {
		noticeService.addNewNotice(notice);
		return "SUCCESS";
	}

	public String updateNotice(int id, String notice) {
		noticeService.updateNotice(id, notice);
		return "SUCCESS";
	}

	public String delNotice(int id) {
		noticeService.delNotice(id);
		return "SUCCESS";
	}

	public String getNoticeList() {
		List<Notice> listNotice = noticeService.getNoticeList();
		String jsonNotice = GsonFactory.getDefault().toJson(listNotice);
		return jsonNotice;
	}

	public String openRegister() {
		systemDataService.setOpenRegist(true);
		return "SUCCESS";
	}

	public String closeRegister() {
		systemDataService.setOpenRegist(false);
		return "SUCCESS";
	}

	public String openshiming() {
		systemDataService.setOpenShiming(true);
		return "SUCCESS";
	}

	public String closeshiming() {
		systemDataService.setOpenShiming(false);
		return "SUCCESS";
	}

	public String openshimingshow() {
		systemDataService.setOpenShimingShow(true);
		return "SUCCESS";
	}

	public String closeshimingshow() {
		systemDataService.setOpenShimingShow(false);
		return "SUCCESS";
	}

	public boolean getRegisterState() {
		return systemDataService.isOpenRegist();
	}

	public boolean getOpenShiming() {
		return systemDataService.isOpenShiMing();
	}

	public boolean getOpenShimingShow() {
		return systemDataService.isOpenShiMingShow();
	}

	public String getLogDbInfo() {
		Map<String, String> m = new HashMap<String, String>();
		m.put("db_log.url", dbLogUrl);
		m.put("db_log.username", dbLogUserName);
		m.put("db_log.password", dbLogPassword);
		return GsonFactory.getDefault().toJson(m);
	}

	public int addCoupons(String cpTmpMapStr, int cpLen) {
		Map<String, Object> cpTmpMap = GsonFactory.getDefault().fromJson(cpTmpMapStr,
				new TypeToken<Map<String, Object>>() {
				}.getType());
		if (MapUtils.isEmpty(cpTmpMap)) {
			return 0;
		}
		long start = System.currentTimeMillis();
		int ret = couponRecordService.addCouponRecordsByBatch(cpTmpMap);
		System.out.println("time:" + (System.currentTimeMillis() - start) / 1000f);
		return ret;
	}

	public String enableByBatchCode(String batchCode) {
		couponRecordService.enableByBatchCode(batchCode);
		return "SUCCESS";
	}

	public String diableByBatchCode(String batchCode) {
		couponRecordService.diableByBatchCode(batchCode);
		return "SUCCESS";
	}

	public String exportCoupon(String batchCode) {
		List<CouponRecord> crList = couponRecordService.getByBatchCode(batchCode);
		return GsonFactory.getDefault().toJson(crList);
	}

	public String delCoupons(String batchCode) {
		couponRecordService.deleteByBatchCode(batchCode);
		return "SUCCESS";
	}

	public int getUsedCouponsCount(String batchCode) {
		return couponRecordService.getUsedCouponsCount(batchCode);
	}

	public String geCouponsByCode(String couponCode) {
		CouponRecord coupon = couponRecordService.getCouponsByCode(couponCode);
		return GsonFactory.getDefault().toJson(coupon);
	}

	public int addOrUpGlobalCoupon(String cpStr) {
		GlobalCouponRecord glCoupon = GsonFactory.getDefault().fromJson(cpStr, new TypeToken<GlobalCouponRecord>() {
		}.getType());
		if (glCoupon == null) {
			return 0;
		}
		int affectRecordCount = 0;
		if (glCoupon.getId() > 0) {
			GlobalCouponRecord glCouponRecord = couponRecordService.getGlobalCouponByCode(glCoupon.getCouponCode());
			glCouponRecord.setEnable(glCoupon.getEnable());
			glCouponRecord.setGiftId(glCoupon.getGiftId());
			couponRecordService.updateGlobalCoupon(glCouponRecord);
			affectRecordCount = 1;
		} else {
			boolean suc = couponRecordService.addAndCheckGlobalCouponRecord(glCoupon);
			if (suc) {
				affectRecordCount = 1;
			}
		}
		return affectRecordCount;
	}

	public int delGlobalCoupon(String couponCode) {
		int affectRecordCount = 0;
		GlobalCouponRecord glCouponRecord = couponRecordService.getGlobalCouponByCode(couponCode);
		if (glCouponRecord != null) {
			boolean delSuc = couponRecordService.deleteGlobalCoupon(glCouponRecord);
			if (delSuc) {
				affectRecordCount = 1;
			}
		}
		return affectRecordCount;
	}

	public int enableGlobalCouponByCode(String couponCode, boolean enable) {
		int affectRecordCount = 0;
		GlobalCouponRecord glCouponRecord = couponRecordService.getCouponRecordByCodeForUpdate(couponCode);
		if (glCouponRecord != null) {
			glCouponRecord.setEnable(enable ? 1 : 0);
			couponRecordService.updateGlobalCoupon(glCouponRecord);
			affectRecordCount = 1;
		}
		return affectRecordCount;
	}

	public String getFeedbackList(int limit, String offsetStr) {
		long offset = Long.valueOf(offsetStr);
		List<Feedback> fbs = feedbackService.getFeedbackList(limit, offset);
		int count = feedbackService.getFeedbackCount();
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("count", count);
		m.put("fbs", fbs);
		return GsonFactory.getDefault().toJson(m);
	}

	public String replyFeedback(String idStr, String replyContent) {
		long id = Long.valueOf(idStr);
		feedbackService.replyFeedback(id, replyContent);
		return "SUCCESS";
	}

	public String removeFeedback(String idStr) {
		long id = Long.valueOf(idStr);
		feedbackService.removeFeedback(id);
		return "SUCCESS";
	}

	public String redoOrder(String orderId) {
		LoggerUtils.warn("redoOrder ", orderId);
		PlatformPayRecord ppr = platformPayRecordService.getByOrderSn(orderId);
		if (ppr == null || ppr.getServerState() == FsGameLoginConst.AS_SERVER_STATE_GIVED_ITEM) {
			return "Order[" + orderId + "] not exists or committed!";
		}
		try {
			sdkServiceHelper.givePayItem(ppr);
		} catch (Exception e) {
			LoggerUtils.error(e);
			return "Order[" + orderId + "] dont pay item!";
		}
		return "SUCCESS";
	}

	public String queryForList(String sql) {
		LoggerUtils.info("queryForList from GMS sql=", sql);
		List<Map<String, Object>> m = gameAccountDao.getCommonJdbcTemplate().queryForList(sql, new Object[0]);
		return GsonFactory.getDefault().toJson(m);
	}

	public int executeUpdate(String sql) {
		LoggerUtils.warn("executeUpdate from GMS sql=", sql);
		return gameAccountDao.getCommonJdbcTemplate().update(sql, new Object[0]);
	}

	public String resetPassword(String accountName, String password) {
		GameAccount ga = gameAccountDao.getObjectByCondition("account = ? limit 1", accountName);
		if (ga == null) {
			return "FAILURE";
		}
		String newPass = DigestUtils.md5Hex(FsGameLoginUtils.md5low(password) + ga.getSalt());
		gameAccountDao.updateColumnValue("pass", newPass, ga.getId());
		return "SUCCESS";
	}

	public String getAppstorePurchasedRecordList(int serverId, String gbIdStr, String accountName, String roleName,
			int limit, String offsetStr) {
		long offset = Long.valueOf(offsetStr);
		long gbId = Long.valueOf(gbIdStr);
		Pair<Integer, List<AppstorePurchasedRecord>> ds = appstorePurchasedRecordService
				.searchAppstorePurchasedRecord(serverId, gbId, accountName, roleName, limit, offset);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("count", ds.getFirst());
		m.put("aprs", ds.getSecond());
		return GsonFactory.getDefault().toJson(m);
	}

	public int unbindGameCenter(String accountName) {
		LoggerUtils.warn("unbindGameCenter ", accountName);
		int ret = appleAccountRelationService.unbindGameCenter(accountName);
		return ret;
	}

	public String getGameAccount(String idfa) {

		Map<Integer, GameAccount> m = new HashMap<Integer, GameAccount>();
		Map<Integer, String> idfaarray = GsonFactory.getDefault().fromJson(idfa, new TypeToken<Map<Integer, String>>() {
		}.getType());
		int i = 0;
		for (String value : idfaarray.values()) {
			IdfaDeviceData idfadata = idfaDeviceDataDao.getIdfa(value);
			if (idfadata != null) {
				String device = idfadata.getDeviceId();
				GameAccount account = gameAccountDao.getGameAccountBydevice(device);
				if (account != null) {
					account.setIdfa(value);
					m.put(i, account);
					i++;
				}
			}

		}
		return GsonFactory.getDefault().toJson(m);
	}

}
