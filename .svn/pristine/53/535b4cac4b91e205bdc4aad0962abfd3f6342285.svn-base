package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.meta.PlatformPayAmountRecord;
import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.service.sdk.SdkGroup;
import com.gamejelly.game.gong2.login.service.sdk.SdkService;
import com.gamejelly.game.gong2.login.utils.FsGameLoginConst;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.gamejelly.game.gong2.login.utils.SdkConst;
import com.google.gson.reflect.TypeToken;
import com.hadoit.game.common.framework.http.SimpleHttpRequest;
import com.hadoit.game.common.lang.DataUtils;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

@SdkGroup({ SdkConst.CC_SDK_ANDROID_YNGP, SdkConst.CC_SDK_IOS_YNIOS })
@Component("sdkYnService")
public class SdkYnService extends SdkBaseAmountService implements SdkService {

	private final static String TOKEN_URL = "http://graph.mobo.vn/?control=user&func=verify_access_token&access_token=";

	private final static String SIGNATURE_KEY = "05f8f90b1afca6b62edd9e91ad534622";

	private final static String OPR = "yn";

	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		LoggerUtils.info("yn payCallback", requestParams);

		String mobo_service_account = FsGameLoginUtils.getMapString(requestParams, "mobo_service_account", "");
		String mobo_service_id = FsGameLoginUtils.getMapString(requestParams, "mobo_service_id", "");
		String order_id = FsGameLoginUtils.getMapString(requestParams, "order_id", "");
		String time_stamp = FsGameLoginUtils.getMapString(requestParams, "time_stamp", "");
		String pay_way = FsGameLoginUtils.getMapString(requestParams, "pay_way", "");
		int pay_amount = FsGameLoginUtils.getMapInteger(requestParams, "pay_amount", 0);
		int final_get_money = FsGameLoginUtils.getMapInteger(requestParams, "final_get_money", 0);
		int original_get_money = FsGameLoginUtils.getMapInteger(requestParams, "original_get_money", 0);
		int server_id = FsGameLoginUtils.getMapInteger(requestParams, "server_id", 0);
		String sign = FsGameLoginUtils.getMapString(requestParams, "sign", "");

		StringBuilder signSb = new StringBuilder();
		signSb.append(mobo_service_account).append(mobo_service_id).append(order_id).append(time_stamp).append(pay_way).append(pay_amount)
				.append(final_get_money).append(original_get_money).append(server_id).append(SIGNATURE_KEY);
		String mySign = FsGameLoginUtils.md5low(signSb.toString());
		int code = 3;
		String message = "Unknown Error";
		try {
			if (mySign.equalsIgnoreCase(sign)) {
				// 还没有消费记录,插入一条,并给物品, 之后改状态
				PlatformPayAmountRecord oldPpr = platformPayAmountRecordService.getByOrderSn(order_id);
				if (oldPpr != null) {
					if (oldPpr.getServerState() == FsGameLoginConst.AS_SERVER_STATE_GIVED_ITEM) {
						// 验证完成,并且给了物品
						code = 1;
						message = "Transaction duplicated";
					} else {
						// 验证完成
						doGivePayItem(oldPpr);
						code = 0;
						message = "Successful";
					}
				} else {
					PlatformPayAmountRecord ppr = new PlatformPayAmountRecord();
					ppr.setAccountName(FsGameLoginUtils.compAccountName(mobo_service_account, OPR));
					ppr.setAvatarId(mobo_service_id);
					ppr.setPayAmount(pay_amount);
					ppr.setFinalGetGold(final_get_money);
					ppr.setOriginalGetGold(original_get_money);
					ppr.setOrderSn(order_id);
					ppr.setServerState(FsGameLoginConst.AS_SERVER_STATE_VERIFIED);
					ppr.setCreateTime(System.currentTimeMillis());
					ppr.setServerId(server_id);
					ppr.setOprGroup(OPR);
					platformPayAmountRecordService.addPlatformPayRecord(ppr);
					doGivePayItem(ppr);
					code = 0;
					message = "Successful";
				}
			} else {
				code = 2;
				message = "Invalid Sign";
			}
		} catch (Exception e) {
			code = 3;
			message = "Unknown Error";
		}

		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("code", code);
		retMap.put("message", message);
		return GsonFactory.getDefault().toJson(retMap);
	}

	@Override
	public Object addItemCallback(Map<String, Object> requestParams) {
		//mobo_service_id == avatarId
		String mobo_service_id = FsGameLoginUtils.getMapString(requestParams, "mobo_service_id", "");
		String mail_content = FsGameLoginUtils.decodeParameter(FsGameLoginUtils.getMapString(requestParams,
				"mail_content", ""));
		String items = FsGameLoginUtils.getMapString(requestParams, "items", "");
		String time_stamp = FsGameLoginUtils.getMapString(requestParams, "time_stamp", "");
		int server_id = FsGameLoginUtils.getMapInteger(requestParams, "server_id", 0);
		String sign = FsGameLoginUtils.getMapString(requestParams, "sign", "");

		List<Object[]> itemData = new ArrayList<Object[]>();
		List<Map<String, Integer>> paramsExtra = GsonFactory.getDefault().fromJson(items,
				new TypeToken<List<Map<String, Integer>>>() {
				}.getType());
		for (Map<String, Integer> extra : paramsExtra) {
			itemData.add(new Object[] { extra.get("item_id"), extra.get("count") });
		}

		StringBuilder signSb = new StringBuilder();
		signSb.append(mobo_service_id).append(time_stamp).append(server_id).append(items).append(SIGNATURE_KEY);
		String mySign = FsGameLoginUtils.md5low(signSb.toString());
		int code = 3;
		String message = "Unknown Error";
		try {
			if (mySign.equalsIgnoreCase(sign)) {
				Map<String, Object> retMap = doOutterAddItem(mobo_service_id,
						server_id, OPR, mail_content, itemData.toArray());
				if (retMap != null) {
					if (FsGameLoginUtils.getMapBoolean(retMap, "result", false)) {
						code = 0;
						message = "Successful";
					} else {
						code = FsGameLoginUtils.getMapInteger(retMap, "code", 3);
						message = FsGameLoginUtils.getMapString(retMap, "message", "Unknown Error");
					}
				} else {
					code = 3;
					message = "Connect to gas error";
				}
			} else {
				code = 2;
				message = "Invalid Sign";
			}
		} catch (Exception e) {
			code = 3;
			message = e.getMessage();
		}

		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("code", code);
		retMap.put("message", message);
		return GsonFactory.getDefault().toJson(retMap);
	}

	@Override
	public Object subItemCallback(Map<String, Object> requestParams) {
		String mobo_service_id = FsGameLoginUtils.getMapString(requestParams, "mobo_service_id", "");
		String items = FsGameLoginUtils.getMapString(requestParams, "items", "");
		String time_stamp = FsGameLoginUtils.getMapString(requestParams, "time_stamp", "");
		int server_id = FsGameLoginUtils.getMapInteger(requestParams, "server_id", 0);
		String sign = FsGameLoginUtils.getMapString(requestParams, "sign", "");

		List<Object[]> itemData = new ArrayList<Object[]>();
		List<Map<String, Integer>> paramsExtra = GsonFactory.getDefault().fromJson(items,
				new TypeToken<List<Map<String, Integer>>>() {
				}.getType());
		for (Map<String, Integer> extra : paramsExtra) {
			itemData.add(new Object[] { extra.get("item_id"), extra.get("count") });
		}

		StringBuilder signSb = new StringBuilder();
		signSb.append(mobo_service_id).append(time_stamp).append(server_id).append(items).append(SIGNATURE_KEY);
		String mySign = FsGameLoginUtils.md5low(signSb.toString());
		int code = 3;
		String message = "Unknown Error";
		try {
			if (mySign.equalsIgnoreCase(sign)) {
				Map<String, Object> retMap = doOutterSubItem(mobo_service_id,
						server_id, OPR, itemData.toArray());
				if (retMap != null) {
					if (FsGameLoginUtils.getMapBoolean(retMap, "result", false)) {
						code = 0;
						message = "Successful";
					} else {
						code = FsGameLoginUtils.getMapInteger(retMap, "code", 3);
						message = FsGameLoginUtils.getMapString(retMap, "message", "Unknown Error");
					}
				} else {
					code = 3;
					message = "Connect to gas error";
				}
			} else {
				code = 2;
				message = "Invalid Sign";
			}
		} catch (Exception e) {
			code = 3;
			message = e.getMessage();
		}

		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("code", code);
		retMap.put("message", message);
		return GsonFactory.getDefault().toJson(retMap);
	}

	@Override
	public Object getUserCallback(Map<String, Object> requestParams) {
		String mobo_service_id = FsGameLoginUtils.getMapString(requestParams, "mobo_service_id", "");
		int server_id = FsGameLoginUtils.getMapInteger(requestParams, "server_id", 0);
		String sign = FsGameLoginUtils.getMapString(requestParams, "sign", "");

		StringBuilder signSb = new StringBuilder();
		signSb.append(mobo_service_id).append(server_id).append(SIGNATURE_KEY);
		String mySign = FsGameLoginUtils.md5low(signSb.toString());
		int code = 3;
		String message = "Unknown Error";
		String data = null;
		try {
			if (mySign.equalsIgnoreCase(sign)) {
				Map<String, Object> retMap = doOutterGetUser(FsGameLoginUtils.compAccountName(mobo_service_id, OPR),
						server_id, OPR);
				if (retMap != null) {
					if (FsGameLoginUtils.getMapBoolean(retMap, "result", false)) {
						code = 0;
						message = "Successful";
						data = FsGameLoginUtils.getMapString(retMap, "data");
					} else {
						code = FsGameLoginUtils.getMapInteger(retMap, "code", 3);
						message = FsGameLoginUtils.getMapString(retMap, "message", "Unknown Error");
					}
				} else {
					code = 3;
					message = "Connect to gas error";
				}
			} else {
				code = 2;
				message = "Invalid Sign";
			}
		} catch (Exception e) {
			code = 3;
			message = e.getMessage();
		}

		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("code", code);
		retMap.put("message", message);
		retMap.put("data", data);
		return GsonFactory.getDefault().toJson(retMap);
	}

	public Object getCcuCallback(Map<String, Object> requestParams) {
		int server_id = FsGameLoginUtils.getMapInteger(requestParams, "server_id", 0);

		String result = "error";
		Map<String, Object> data = null;
		try {
			Map<String, Object> retMap = doOutterGetServerInfo(server_id, OPR);
			if (retMap != null) {
				if (FsGameLoginUtils.getMapBoolean(retMap, "result", false)) {
					result = "ok";
					int ccu = FsGameLoginUtils.getMapInteger(retMap, "ccu");
					long date = FsGameLoginUtils.getMapLong(retMap, "date");
					data = FsGameLoginUtils.createHashMap(DataUtils.formatDate(date, "yyyy-MM-dd HH:mm"), ccu);
				}
			}
		} catch (Exception e) {
			result = e.getMessage();
			data = null;
		}

		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("result", result);
		retMap.put("data", data);
		return GsonFactory.getDefault().toJson(retMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {
		LoggerUtils.info("yn loginAuth", requestParams);
		String SessionId = FsGameLoginUtils.getMapString(requestParams, "SessionId", "");
		String url = TOKEN_URL + SessionId;
		SimpleHttpRequest shr = SimpleHttpRequest.createGet(url);
		String resultGet = shr.sendGetString();
		LoggerUtils.info("yn resultGet", resultGet);
		boolean result = false;
		Map<String, Object> rm = null;
		if (resultGet != null) {
			rm = GsonFactory.getDefault().fromJson(resultGet, Map.class);
			int code = Double.valueOf(String.valueOf(rm.get("code"))).intValue();
			if (code == 500040) {
				Map<String, Object> data = (Map<String, Object>) rm.get("data");
				if (data != null) {
					String mobo_service_id = String.valueOf(data.get("mobo_service_id"));
					if (StringUtils.isNotBlank(mobo_service_id)) {
						requestParams.put("name", mobo_service_id);
						result = true;
					}
				}
			}
		}
		return new PlatformRetData(result, rm);
	}

}
