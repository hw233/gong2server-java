package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.gamejelly.game.gong2.login.meta.PlatformPayRecord;
import com.gamejelly.game.gong2.login.net.GasAdminClientManager;
import com.gamejelly.game.gong2.login.service.GameAccountService;
import com.gamejelly.game.gong2.login.service.PlatformPayRecordService;
import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.service.sdk.SdkService;
import com.gamejelly.game.gong2.login.service.sdk.SdkServiceHelper;
import com.gamejelly.game.gong2.login.utils.FsGameLoginConst;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.hadoit.game.common.framework.http.SimpleHttpRequest;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

public abstract class SdkBaseService implements SdkService {

	/**
	 * 热云账号信息
	 */
	private static String HIappkey = "9deb671596127f3afa06d8f2cd753afa";
	private static String YDappkey = "ee17e2753b3baef0f5ae88ba42d4ce46";// 游动热云key
	private static String HAappkey = "88105298504512c096429f2ed17ff5fc";
	private static String HIwho = "我的宫廷iOS";
	private static String HAwho = "我的宫廷Android";

	@Autowired
	protected PlatformPayRecordService platformPayRecordService;

	@Autowired
	protected GameAccountService gameAccountService;

	@Autowired
	protected GasAdminClientManager gasAdminClientManager;

	@Autowired
	protected SdkServiceHelper sdkServiceHelper;

	protected void doGivePayItem(PlatformPayRecord ppr) {
		sdkServiceHelper.givePayItem(ppr);
	}

	public PlatformRetData verifyAllOrderWithCheck(Map<String, Object> requestParams) {
		String avatarId = FsGameLoginUtils.getMapString(requestParams, "avatarId", "");
		int serverId = FsGameLoginUtils.getMapInteger(requestParams, "serverId", 0);
		List<PlatformPayRecord> noCommits = platformPayRecordService.getNoCommitOrderBy(avatarId, serverId);
		if (CollectionUtils.isEmpty(noCommits)) {
			return new PlatformRetData(true, "No pending order!");
		}
		boolean b;
		for (PlatformPayRecord ppr : noCommits) {
			if (ppr.getServerState() == FsGameLoginConst.AS_SERVER_STATE_VERIFIED) {
				// 内部处理掉单,直接给东西
				doGivePayItem(ppr);
			} else {
				// 平台处理掉单, 这个接口不一定所有渠道都有, 如果没有对应接口则返回false
				b = verifyOrderInternal(ppr.getOrderSn());
				if (!b) {
					LoggerUtils.warn("Order [", ppr.getOrderSn(), "] is not handle! ");
				}
			}
		}
		return new PlatformRetData(true, "All pending order handled!");
	}

	public Object addItemCallback(Map<String, Object> requestParams) {
		throw new UnsupportedOperationException("Not support addItemCallback!");
	}

	public Object subItemCallback(Map<String, Object> requestParams) {
		throw new UnsupportedOperationException("Not support subItemCallback!");
	}

	public Object getUserCallback(Map<String, Object> requestParams) {
		throw new UnsupportedOperationException("Not support getUserCallback!");
	}

	public Object getXmfbDataCallback(Map<String, Object> requestParams) {
		throw new UnsupportedOperationException("Not support getXmfbDataCallback!");
	}

	public Object getCcuCallback(Map<String, Object> requestParams) {
		throw new UnsupportedOperationException("Not support getCcuCallback!");
	}

	public Object getServersCallback(Map<String, Object> requestParams) {
		throw new UnsupportedOperationException("Not support getServersCallback!");
	}

	protected Map<String, Object> doOutterAddItem(String avatarId, int serverId, String opr, String mail,
			Object[] items) {
		Object gasRes = null;
		try {
			gasRes = gasAdminClientManager.getSimpleRpcClientFromSubOpr(serverId, opr).getSimpleRpcClient()
					.invokeRpc(FsGameLoginConst.ADMIN_LOGIN_REQ_OUTTER_ADD_ITEM,
							new Object[] { avatarId, serverId, mail, items })
					.get();
		} catch (Exception e) {
			LoggerUtils.error(e, "Outter add item failed!", avatarId, serverId, opr);
			return null;
		}
		return GsonFactory.getDefault().fromJson((JsonElement) gasRes, new TypeToken<Map<String, Object>>() {
		}.getType());
	}

	protected Map<String, Object> doOutterGetUser(String avatarId, int serverId, String opr) {
		Object gasRes = null;
		try {
			gasRes = gasAdminClientManager.getSimpleRpcClientFromSubOpr(serverId, opr).getSimpleRpcClient()
					.invokeRpc(FsGameLoginConst.ADMIN_LOGIN_REQ_OUTTER_GET_USER, new Object[] { avatarId, serverId })
					.get();
		} catch (Exception e) {
			LoggerUtils.error(e, "Outter get user failed!", avatarId, serverId, opr);
			return null;
		}
		return GsonFactory.getDefault().fromJson((JsonElement) gasRes, new TypeToken<Map<String, Object>>() {
		}.getType());
	}

	@Override
	public PlatformRetData verifyOrder(Map<String, Object> requestParams) {
		throw new UnsupportedOperationException("Not support verifyOrder!");
	}

	public PlatformRetData createOrder(Map<String, Object> requestParams) {
		String avatarId = FsGameLoginUtils.getMapString(requestParams, "avatarId", "");
		String accountName = FsGameLoginUtils.getMapString(requestParams, "accountName", "");
		String productId = FsGameLoginUtils.getMapString(requestParams, "productId", "");
		String productAmountStr = FsGameLoginUtils.getMapString(requestParams, "productAmount", "0");
		int productCount = FsGameLoginUtils.getMapInteger(requestParams, "productCount", 1);
		int serverId = FsGameLoginUtils.getMapInteger(requestParams, "serverId", 0);
		String platform = FsGameLoginUtils.getMapString(requestParams, "platform", "");
		String deviceId = FsGameLoginUtils.getMapString(requestParams, "deviceId", "");
		String deviceName = FsGameLoginUtils.getMapString(requestParams, "deviceName", "");
		String opr = FsGameLoginUtils.getMapString(requestParams, "opr", "");
		String ip = FsGameLoginUtils.getMapString(requestParams, "ip", "");
		String idfa = FsGameLoginUtils.getMapString(requestParams, "idfa", "");
		String source = FsGameLoginUtils.getMapString(requestParams, "source", "");
		String imei = FsGameLoginUtils.getMapString(requestParams, "imei", "");
		String payLoad = FsGameLoginUtils.getMapString(requestParams, "payLoad", "");

		LoggerUtils.info("service createOrder", accountName, productId, productAmountStr, productCount, serverId, opr,
				source, imei);

		if (StringUtils.isEmpty(avatarId) || StringUtils.isEmpty(accountName) || StringUtils.isEmpty(productId)
				|| serverId < 0) {
			return null;
		}
		int productAmount = 0;
		BigDecimal paBase = new BigDecimal(productAmountStr);
		BigDecimal productAmountBd = paBase.multiply(new BigDecimal(Integer.toString(productCount * 100)));
		productAmount = productAmountBd.intValue();
		PlatformPayRecord ppr = new PlatformPayRecord();
		String orderSn = FsGameLoginUtils.getUUID();
		ppr.setAccountName(accountName);
		ppr.setAvatarId(avatarId);
		ppr.setProductId(productId);
		ppr.setProductCount(productCount);
		ppr.setProductAmount(productAmount);
		ppr.setOrderSn(orderSn);
		ppr.setCreateTime(System.currentTimeMillis());
		ppr.setServerId(serverId);
		ppr.setOprGroup(opr);
		ppr.setIp(ip);
		ppr.setDeviceId(deviceId);
		ppr.setDeviceName(deviceName);
		ppr.setPlatform(platform);
		ppr.setIdfa(idfa);
		ppr.setImei(imei);
		ppr.setSource(source);

		if (!"".equals(payLoad)) {
			ppr.setExtData2(payLoad);
		}

		platformPayRecordService.addPlatformPayRecord(ppr);
		PlatformRetData data = new PlatformRetData(true, ppr.getOrderSn());
		if (!"".equals(payLoad)) {
			data.setPayLoad(payLoad);
		}
		return data;
	}

	protected boolean verifyOrderInternal(String orderSn) {
		return false;
	}

	public void createAccount(Map<String, Object> requestParams, String name, String pass, String platform,
			String deviceName, String deviceVersion, String deviceId, String ip) {
		throw new UnsupportedOperationException("Not support createAccount!");
	}

	public void resetPassword(Map<String, Object> requestParams, String name, String oldPass, String newPass) {
		throw new UnsupportedOperationException("Not support resetPassword!");
	}

	public boolean createAccountIfAbsent() {
		return true;
	}

	public PlatformRetData handleSdkData(Map<String, Object> requestParams) {
		return new PlatformRetData(true, null);
	}

	public boolean checkPrice(PlatformPayRecord ppr, String ptMoney, boolean yuan) {
		int myPrice = ppr.getProductAmount();// 这里是分
		int ptPrice;
		BigDecimal bd1 = new BigDecimal(ptMoney);
		if (yuan) {
			ptPrice = bd1.multiply(new BigDecimal(100)).intValue();
		} else {
			ptPrice = bd1.intValue();
		}
		if (ptPrice >= myPrice) {
			return true;
		}
		LoggerUtils.error("check price error!", ppr.getAccountName(), ppr.getServerId(), ppr.getOprGroup(),
				ppr.getProductId(), ppr.getOrderSn(), myPrice, ptPrice);
		return false;
	}

	public void processOrder(String orderId, String money, boolean yuan) {
		PlatformPayRecord ppr = platformPayRecordService.getByOrderSn(orderId);
		if (ppr != null && checkPrice(ppr, money, yuan)) {
			if (ppr.getServerState() != FsGameLoginConst.AS_SERVER_STATE_GIVED_ITEM) {
				platformPayRecordService.updateServerState(ppr.getId(), FsGameLoginConst.AS_SERVER_STATE_VERIFIED);
				doGivePayItem(ppr);
			}
		}
	}

	public void processOrder(String orderId, String money, boolean yuan, String paytype) {
		PlatformPayRecord ppr = platformPayRecordService.getByOrderSn(orderId);
		if (ppr != null && checkPrice(ppr, money, yuan)) {
			if (ppr.getServerState() != FsGameLoginConst.AS_SERVER_STATE_GIVED_ITEM) {
				platformPayRecordService.updateServerState(ppr.getId(), FsGameLoginConst.AS_SERVER_STATE_VERIFIED);
				postReyun(orderId, paytype);
				doGivePayItem(ppr);
			}
		}
	}

	public void postYdong(String orderId, String money, boolean yuan) {
		Map<String, Object> data = new HashMap<String, Object>();
		PlatformPayRecord ppr = platformPayRecordService.getByOrderSn(orderId);
		if (ppr != null) {
			data.put("appid", 188);
			data.put("paytype", "zfb-ydxcm");
			data.put("trade_no", orderId);
			data.put("money", ppr.getProductAmount() / 100);
			data.put("paytime", ppr.getCreateTime() / 1000);
			data.put("extrainfo", orderId);
			data.put("serverid", ppr.getServerId());
			data.put("roleid", ppr.getAvatarId());
			try {
				String getUrl = "http://usercenter.tuziyouxi.com/payment/zhichong.php?" + "appid=" + data.get("appid")
						+ "&paytype=" + data.get("paytype") + "&trade_no=" + data.get("trade_no") + "&money="
						+ data.get("money") + "&paytime=" + data.get("paytime") + "&extrainfo=" + data.get("extrainfo")
						+ "&serverid=" + data.get("serverid") + "&roleid=" + data.get("roleid");
				SimpleHttpRequest shr = SimpleHttpRequest.createPost(getUrl.toString());
				shr.sendGetString();

			} catch (Exception e) {
				LoggerUtils.error("推送到游动服务器失败", e);
			}

		}

	}

	/**
	 * 传送给热云的统计用户充值方法
	 * 
	 * @url=http:// log.reyun.com/receive/track/payment
	 * @appid 应用appid
	 * @who 账户ID
	 * @context{deviceid,transactionid,paymenttype,currencytype,currencyamount,channelid,idfa,idfv,imei}
	 */
	public void postReyun(String orderId, String paytype) {
		String url = "http://log.reyun.com/receive/track/payment";
		PlatformPayRecord ppr = platformPayRecordService.getByOrderSn(orderId);
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> context = new HashMap<String, Object>();
		if (ppr != null) {

			if (ppr.getPlatform().equals("ios")) {
				context.put("currencyAmount", String.valueOf(ppr.getProductAmount() / 100));
				context.put("deviceid", ppr.getIdfa());
				context.put("transactionid", orderId);
				context.put("paymenttype", paytype);
				context.put("currencytype", "CNY");
				context.put("idfa", ppr.getIdfa());
				context.put("idfv", "");
				if (ppr.getOprGroup().equals("ios11")) {
					data.put("appid", YDappkey);
				} else {
					data.put("appid", HIappkey);
				}

				data.put("who", ppr.getAccountName());
				data.put("context", context);

				try {
					String json = GsonFactory.getDefault().toJson(data);
					SimpleHttpRequest shr = SimpleHttpRequest.createPost(url.toString()).createBody(json);
					shr.setHeader("Content-Type", "application/json");
					shr.setCharset("utf-8");
					shr.sendGetString();
					LoggerUtils.info("postReyun:" + orderId);

				} catch (Exception e) {
					LoggerUtils.error("同步到热云失败", e);

				}

			}
			// else if (ppr.getPlatform().equals("android")) {
			// context.put("currencyAmount",
			// String.valueOf(ppr.getProductAmount() / 100));
			// context.put("deviceid", ppr.getImei());
			// context.put("transactionid", orderId);
			// context.put("paymenttype", paytype);
			// context.put("currencytype", "CNY");
			// context.put("imei", ppr.getImei());
			// data.put("appid", HAappkey);
			// data.put("who", ppr.getAccountName());
			// data.put("context", context);
			// try {
			// String json = GsonFactory.getDefault().toJson(data);
			// SimpleHttpRequest shr =
			// SimpleHttpRequest.createPost(url.toString()).createBody(json);
			// shr.setHeader("Content-Type", "application/json");
			// shr.setCharset("utf-8");
			// shr.sendGetString();
			// LoggerUtils.info("postReyun:" + orderId);
			//
			// } catch (Exception e) {
			// LoggerUtils.error("同步到热云失败", e);
			//
			// }
			// }
		}

	}

	protected Map<String, Object> doOutterGetXmfbData(int fbId, int serverId, String opr) {
		Object gasRes = null;
		try {
			gasRes = gasAdminClientManager.getSimpleRpcClientFromSubOpr(serverId, opr).getSimpleRpcClient()
					.invokeRpc(FsGameLoginConst.ADMIN_LOGIN_REQ_OUTTER_GET_XMFB_DATA, new Object[] { fbId }).get();
		} catch (Exception e) {
			LoggerUtils.error(e, "Outter get user failed!", fbId, serverId, opr);
			return null;
		}
		return GsonFactory.getDefault().fromJson((JsonElement) gasRes, new TypeToken<Map<String, Object>>() {
		}.getType());
	}

	protected boolean verifyOrderInternal(String orderSn, Map<String, Object> requestParams) {
		return false;
	}

}
