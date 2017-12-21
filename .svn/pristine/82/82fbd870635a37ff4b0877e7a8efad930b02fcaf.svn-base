package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.meta.AppleAccountRelation;
import com.gamejelly.game.gong2.login.meta.PlatformPayRecord;
import com.gamejelly.game.gong2.login.service.AppleAccountRelationService;
import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.service.sdk.SdkGroup;
import com.gamejelly.game.gong2.login.service.sdk.SdkService;
import com.gamejelly.game.gong2.login.service.sdk.pay.AlipayPayService;
import com.gamejelly.game.gong2.login.service.sdk.pay.WeixinPayService;
import com.gamejelly.game.gong2.login.utils.FsGameLoginConst;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.gamejelly.game.gong2.login.utils.SdkConst;
import com.google.gson.reflect.TypeToken;
import com.hadoit.game.common.framework.http.SimpleHttpRequest;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

@SdkGroup({ SdkConst.CC_SDK_IOS_APPLE, SdkConst.CC_SDK_IOS_APPLE_2, SdkConst.CC_SDK_IOS_APPLE_3,
		SdkConst.CC_SDK_IOS_APPLE_4, SdkConst.CC_SDK_IOS_APPLE_5, SdkConst.CC_SDK_IOS_APPLE_6,
		SdkConst.CC_SDK_IOS_APPLE_7, SdkConst.CC_SDK_IOS_APPLE_8, SdkConst.CC_SDK_IOS_APPLE_9,
		SdkConst.CC_SDK_IOS_APPLE_10, SdkConst.CC_SDK_IOS_APPLE_11, SdkConst.CC_SDK_IOS_APPLE_12,
		SdkConst.CC_SDK_IOS_APPLE_13, SdkConst.CC_SDK_IOS_APPLE_14, SdkConst.CC_SDK_IOS_APPLE_15,
		SdkConst.CC_SDK_IOS_APPLE_16, SdkConst.CC_SDK_IOS_APPLE_17, SdkConst.CC_SDK_IOS_APPLE_18,
		SdkConst.CC_SDK_IOS_APPLE_19, SdkConst.CC_SDK_IOS_APPLE_20, SdkConst.CC_SDK_IOS_APPLE_21,
		SdkConst.CC_SDK_IOS_APPLE_22, SdkConst.CC_SDK_IOS_APPLE_23, SdkConst.CC_SDK_IOS_APPLE_24,
		SdkConst.CC_SDK_IOS_APPLE_25,
		SdkConst.CC_SDK_IOS_APPLE_YW, SdkConst.CC_SDK_IOS_APPLE_4533 })
@Component("SdkAppleService")
public class SdkAppleService extends SdkBaseService implements SdkService {
	/**
	 * IAP充值状态
	 */
	public final static int AS_VERIFY_SUCCESS = 0;
	public final static int AS_VERIFY_ERROR_COMMON = -1;
	public final static int AS_VERIFY_ERROR_STATUS = -2;
	public final static int AS_VERIFY_ERROR_NETWORK = -3;
	public final static int AS_VERIFY_ERROR_VERIFIED_GIVED_ITEM = -4;

	/**
	 * 测试地址
	 */
	private String purchaseVerifyUrlSandBox = "https://sandbox.itunes.apple.com/verifyReceipt";

	/**
	 * 线上地址
	 */
	private String purchaseVerifyUrl = "https://buy.itunes.apple.com/verifyReceipt";

	private final static String SIGNATURE_KEY = "14ac591be4e70071f1f1ef6b4b721a08";
	
	private SdkBaseService sdkService;

	@Autowired
	private AppleAccountRelationService appleAccountRelationService;

	@Autowired
	private AlipayPayService alipayPayService;

	@Autowired
	private WeixinPayService weixinPayService;

	public static String PAY_SOURCE_ALIPAY = "iosalipay";
	public static String PAY_SOURCE_WEIXIN = "iosweixin";
	public static String PAY_SOURCE_APPLE = "applepay";

	@Override
	public PlatformRetData handleSdkData(Map<String, Object> requestParams) {
		LoggerUtils.info("SdkAppleService handleSdkData:", requestParams);
		super.handleSdkData(requestParams);
		String source = FsGameLoginUtils.getMapString(requestParams, "source", "");
		if (PAY_SOURCE_ALIPAY.equalsIgnoreCase(source)) {
			return alipayPayService.handleSdkData(requestParams);
		} else if (PAY_SOURCE_WEIXIN.equalsIgnoreCase(source)) {
			return weixinPayService.handleSdkData(requestParams);
		}
		return new PlatformRetData(false, null);
	}

	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		LoggerUtils.info("SdkAppleService payCallback", requestParams);
		// 支付宝和微信都有这个字段

		String out_trade_no = FsGameLoginUtils.getMapString(requestParams, "out_trade_no", "");
		String trade_no = FsGameLoginUtils.getMapString(requestParams, "trade_no", "");
		if (StringUtils.isEmpty(out_trade_no)) {
			// 微信直接可能获取不到参数
			LoggerUtils.info("SdkAppleService payCallback out_trade_no null");
			//若为微信支付，更新支付方式和商家订单号，字段
			return weixinPayService.payCallback(requestParams, this);
		}else{
			//若为支付宝支付，更新支付方式和商家订单号，字段
			platformPayRecordService.updateSource(out_trade_no, "iosalipay");
			platformPayRecordService.updatePayPlatformOrderId(out_trade_no, trade_no);
		}
		PlatformPayRecord ppr = platformPayRecordService.getByOrderSn(out_trade_no);
		if (ppr == null) {
			return null;
		}
		if (PAY_SOURCE_ALIPAY.equalsIgnoreCase(ppr.getSource())) {
			return alipayPayService.payCallback(requestParams, this);
		} else if (PAY_SOURCE_WEIXIN.equalsIgnoreCase(ppr.getSource())) {
			return weixinPayService.payCallback(requestParams, this);
		}
		return null;
	}

	@Override
	public Object addItemCallback(Map<String, Object> requestParams) {
		LoggerUtils.info("apple addItemCallback", requestParams);

		String avatarId = FsGameLoginUtils.getMapString(requestParams, "avatarId", "");
		int serverId = FsGameLoginUtils.getMapInteger(requestParams, "serverId", 0);
		String items = FsGameLoginUtils.getMapString(requestParams, "items", "");
		String content = FsGameLoginUtils.decodeParameter(FsGameLoginUtils.getMapString(requestParams, "content", ""));
		String timestamp = FsGameLoginUtils.getMapString(requestParams, "timestamp", "");
		String signature = FsGameLoginUtils.getMapString(requestParams, "signature", "");
		int sdkId = FsGameLoginUtils.getMapInteger(requestParams, "sdkId", 0);
		String opr = SdkConst.getOprFromSdk(sdkId);

		List<Object[]> itemData = new ArrayList<Object[]>();
		List<Map<String, Integer>> paramsExtra = GsonFactory.getDefault().fromJson(items,
				new TypeToken<List<Map<String, Integer>>>() {
				}.getType());
		for (Map<String, Integer> extra : paramsExtra) {
			itemData.add(new Object[] { extra.get("itemId"), extra.get("count") });
		}

		String mySign = FsGameLoginUtils.md5low(avatarId + serverId + items + timestamp + SIGNATURE_KEY);

		String result = "fail";
		try {
			if (signature.equalsIgnoreCase(mySign)) {
				Map<String, Object> retMap = doOutterAddItem(avatarId, serverId, opr, content, itemData.toArray());
				if (retMap != null) {
					if (FsGameLoginUtils.getMapBoolean(retMap, "result", false)) {
						result = "success";
					}
				}
			} else {
				result = "sign error";
			}
		} catch (Exception e) {
			result = "fail";
		}

		return result;
	}

	@Override
	public Object getUserCallback(Map<String, Object> requestParams) {
		String avatarId = FsGameLoginUtils.getMapString(requestParams, "avatarId", "");
		int serverId = FsGameLoginUtils.getMapInteger(requestParams, "serverId", 0);
		String signature = FsGameLoginUtils.getMapString(requestParams, "signature", "");
		String mySign = FsGameLoginUtils.md5low(avatarId + serverId + SIGNATURE_KEY);
		int sdkId = FsGameLoginUtils.getMapInteger(requestParams, "sdkId", 0);
		String opr = SdkConst.getOprFromSdk(sdkId);

		int returnCode = -1;
		String returnMsg = "fail";
		try {
			if (signature.equalsIgnoreCase(mySign)) { // 验签成功
				Map<String, Object> retMap = doOutterGetUser(avatarId, serverId, opr);
				if (retMap != null) {
					if (FsGameLoginUtils.getMapBoolean(retMap, "result", false)) {
						returnCode = 0;
						returnMsg = "success";
					}
				}
			} else {
				returnMsg = "sign error";
			}
		} catch (Exception e) {
			LoggerUtils.error(e);
		}

		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("returnCode", returnCode + "");
		retMap.put("returnMsg", returnMsg);

		return retMap;
	}

	@Override
	public PlatformRetData verifyOrder(Map<String, Object> requestParams) {
		String receipt = FsGameLoginUtils.getMapString(requestParams, "receipt", "");
		String transactionId = FsGameLoginUtils.getMapString(requestParams, "transactionId", "");
		String productId = FsGameLoginUtils.getMapString(requestParams, "productId", "");
		String avatarId = FsGameLoginUtils.getMapString(requestParams, "avatarId", "");
		String accountName = FsGameLoginUtils.getMapString(requestParams, "accountName", "");
		int serverId = FsGameLoginUtils.getMapInteger(requestParams, "serverId", 0);
		String opr = FsGameLoginUtils.getMapString(requestParams, "opr", "");

		if (StringUtils.isEmpty(receipt) || StringUtils.isBlank(transactionId) || StringUtils.isBlank(productId)) {
			LoggerUtils.info("Receipt is blank!");
			return new PlatformRetData(false, AS_VERIFY_ERROR_COMMON);
		}
		PlatformPayRecord ppr = platformPayRecordService.getByOrderSn(transactionId);
		if (ppr != null) {
			platformPayRecordService.updateSource(ppr.getOrderSn(), PAY_SOURCE_APPLE);
			if (ppr.getServerState() == FsGameLoginConst.AS_SERVER_STATE_GIVED_ITEM) {
				// 验证完成,并且给了物品
				return new PlatformRetData(true, AS_VERIFY_ERROR_VERIFIED_GIVED_ITEM);
			} else {
				// 验证完成
				postReyun(ppr.getOrderSn(),"apple");
				doGivePayItem(ppr);
				return new PlatformRetData(true, AS_VERIFY_SUCCESS);
			}
		} else {
			// 走验证流程, 先验证线上地址, 再验sandbox
			PlatformRetData prd = doVerifyReceipt(purchaseVerifyUrl, serverId, opr, avatarId, accountName,
					transactionId, productId, receipt, requestParams);
			if ((Integer) prd.getPtData() != AS_VERIFY_SUCCESS) {
				prd = doVerifyReceipt(purchaseVerifyUrlSandBox, serverId, opr, avatarId, accountName, transactionId,
						productId, receipt, requestParams);
				if ((Integer) prd.getPtData() != AS_VERIFY_SUCCESS) {
					// 沙盒的情况下如果非正常验证, 那就不要通知前台关单
					prd = new PlatformRetData(false, AS_VERIFY_ERROR_COMMON);
				}
			}
			return prd;
		}
	}

	private int getProductDataFromInApp(List<Map<String, Object>> inapps, String transactionId, String productId) {
		if (CollectionUtils.isEmpty(inapps)) {
			return 0;
		}
		for (Map<String, Object> m : inapps) {
			final String pId = FsGameLoginUtils.getMapString(m, "product_id", "");
			final String tId = FsGameLoginUtils.getMapString(m, "transaction_id", "");
			final int pCount = FsGameLoginUtils.getMapInteger(m, "quantity", 0);
			if (pId.equalsIgnoreCase(productId) && tId.equalsIgnoreCase(transactionId) && pCount > 0) {
				return pCount;
			}
		}
		return 0;

	}

	@SuppressWarnings("unchecked")
	private PlatformRetData doVerifyReceipt(String verifyUrl, int serverId, String opr, String avatarId,
			String accountName, String transactionId, String productId, String receipt,
			Map<String, Object> requestParams) {
		/**
		 * 苹果返回值 {"status":0, "environment":"Sandbox",
		 * "receipt":{"receipt_type":"ProductionSandbox", "adam_id":0,
		 * "bundle_id":"com.hadoit.FsGame", "application_version":"1.0",
		 * "download_id":0, "request_date":"2014-07-16 07:02:18 Etc/GMT",
		 * "request_date_ms":"1405494138838", "request_date_pst": "2014-07-16
		 * 00:02:18 America/Los_Angeles", "original_purchase_date": "2013-08-01
		 * 07:00:00 Etc/GMT", "original_purchase_date_ms":"1375340400000",
		 * "original_purchase_date_pst" : "2013-08-01 00:00:00
		 * America/Los_Angeles", "original_application_version":"1.0",
		 * "in_app":[ {"quantity":"1",
		 * "product_id":"com.hadoit.FsGame_AppStore_60yb",
		 * "transaction_id":"1000000116962924",
		 * "original_transaction_id":"1000000116962924", "purchase_date":
		 * "2014-07-16 07:00:38 Etc/GMT", "purchase_date_ms":"1405494038000",
		 * "purchase_date_pst":"2014-07-16 00:00:38 America/Los_Angeles",
		 * "original_purchase_date":"2014-07-16 07:00:38 Etc/GMT",
		 * "original_purchase_date_ms":"1405494038000",
		 * "original_purchase_date_pst" : "2014-07-16 00:00:38
		 * America/Los_Angeles", "is_trial_period":"false"}]}}
		 */
		String jsonData = GsonFactory.getDefault().toJson(Collections.singletonMap("receipt-data", receipt));
		try {
			// 1. 返回不正确或验证失败 直接关单
			// 2. 验证成功, 先拿老数据, 如果有判断状态有没有给过物品, 没有就给, 最后再设置状态
			SimpleHttpRequest shr = SimpleHttpRequest.createPost(verifyUrl).createBody(jsonData);
			String result = shr.sendGetString();
			LoggerUtils.logPayCallback("AppleVerifyReceipt", serverId, opr, avatarId, accountName, transactionId,
					productId, result);
			Map<String, Object> rm = GsonFactory.getDefault().fromJson(result, Map.class);
			int status = FsGameLoginUtils.getMapInteger(rm, "status", -1);
			if (status == 0) {
				// 成功验证, 给东西
				Map<String, Object> recm = (Map<String, Object>) FsGameLoginUtils.getMapData(rm, "receipt");
				List<Map<String, Object>> inapps = (List<Map<String, Object>>) recm.get("in_app");
				int pCount = getProductDataFromInApp(inapps, transactionId, productId);
				if (pCount == 0) {
					final String pId = FsGameLoginUtils.getMapString(recm, "product_id", "");
					final String tId = FsGameLoginUtils.getMapString(recm, "transaction_id", "");
					final int quantity = FsGameLoginUtils.getMapInteger(recm, "quantity", 0);
					if (pId.equalsIgnoreCase(productId) && tId.equalsIgnoreCase(transactionId) && quantity > 0) {
						pCount = quantity;
					}
				}

				PlatformPayRecord ppr = platformPayRecordService.getByOrderSn(transactionId);
				if (ppr != null) {
					LoggerUtils.logPayCallback("AppleVerifyReceipt duplicate transactionId", serverId, opr, avatarId,
							accountName, transactionId, productId, ppr.getServerState());
					if (ppr.getServerState() != FsGameLoginConst.AS_SERVER_STATE_GIVED_ITEM) {
						doGivePayItem(ppr);
					}
				} else {
					// 还没有消费记录,插入一条,并给物品, 之后改状态
					ppr = new PlatformPayRecord();
					ppr.setAccountName(accountName);
					ppr.setAvatarId(avatarId);
					ppr.setProductCount(pCount);
					ppr.setProductId(productId);
					ppr.setOrderSn(transactionId);
					ppr.setServerState(FsGameLoginConst.AS_SERVER_STATE_VERIFIED);
					ppr.setCreateTime(System.currentTimeMillis());
					ppr.setServerId(serverId);
					ppr.setOprGroup(opr);
					ppr.setDeviceId(FsGameLoginUtils.getMapString(requestParams, "deviceId", ""));
					ppr.setDeviceName(FsGameLoginUtils.getMapString(requestParams, "deviceName", ""));
					ppr.setPlatform(FsGameLoginUtils.getMapString(requestParams, "platform", ""));
					ppr.setIdfa(FsGameLoginUtils.getMapString(requestParams, "idfa", ""));
					platformPayRecordService.addPlatformPayRecord(ppr);
					doGivePayItem(ppr);
				}
				return new PlatformRetData(true, AS_VERIFY_SUCCESS);
			} else {
				// 验证失败,关单
				return new PlatformRetData(true, AS_VERIFY_ERROR_STATUS);
			}
		} catch (Exception e) {
			LoggerUtils.error(e);
			LoggerUtils.logPayCallback("Verify error! ", serverId, opr, avatarId, accountName, transactionId, productId,
					e.getMessage());
			// 网络异常, 不关单
			return new PlatformRetData(false, AS_VERIFY_ERROR_NETWORK);
		}
	}

	/**
	 * 现在支付宝要用
	 * 
	 * @param requestParams
	 * @return
	 */
	@Deprecated
	public PlatformRetData createOrder_old(Map<String, Object> requestParams) {
		throw new UnsupportedOperationException("Not support createOrder!");
	}

	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {
		LoggerUtils.info("apple loginAuth requestParams=", requestParams);
		String deviceId = FsGameLoginUtils.getMapString(requestParams, "deviceId", "");
		String gameCenterId = FsGameLoginUtils.getMapString(requestParams, "gameCenterId", "");
		String opr = FsGameLoginUtils.getMapString(requestParams, "opr", "");

		String finalOutName;
		if (StringUtils.isNotBlank(gameCenterId)) {
			// 先判断前台是否有gamecenter账号登陆过, 只要gamecenter账号登陆成功过一次就保存到前台的UserDefault
			AppleAccountRelation centerAar = appleAccountRelationService.getAppleAccountRelation(gameCenterId);
			if (centerAar != null) {
				// 存在gamecenter的账号, 直接登陆
				finalOutName = centerAar.getGameAccount();
			} else {
				finalOutName = appleAccountRelationService.getOutNameFromDeviceAar(opr, deviceId);
			}
		} else {
			// 用deviceId登陆
			finalOutName = appleAccountRelationService.getOutNameFromDeviceAar(opr, deviceId);
		}
		// 设置最终的账号
		requestParams.put("fullAccountName", finalOutName);
		return new PlatformRetData(true, null);
	}

}
