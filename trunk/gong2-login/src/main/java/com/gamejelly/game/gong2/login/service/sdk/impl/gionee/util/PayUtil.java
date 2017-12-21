package com.gamejelly.game.gong2.login.service.sdk.impl.gionee.util;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.gamejelly.game.gong2.login.service.sdk.impl.gionee.bean.Order;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

public class PayUtil {

	public static String wrapCreateOrder(Order order, String privateKey, String deliverType) throws InvalidKeyException,
			NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, IOException {
		Map<String, Object> jsonReq = new HashMap<String, Object>();

		StringBuilder signContent = new StringBuilder();
		signContent.append(order.getApiKey());
		jsonReq.put("api_key", order.getApiKey());

		signContent.append(order.getDealPrice());
		jsonReq.put("deal_price", order.getDealPrice().toString());

		signContent.append(order.getDeliverType());
		jsonReq.put("deliver_type", order.getDeliverType());

		if (!StringUtils.isBlank(order.getExpireTime())) {
			signContent.append(order.getExpireTime());
			jsonReq.put("expire_time", order.getExpireTime());
		}

		if (!StringUtils.isBlank(order.getExtInfo())) {
			signContent.append(order.getExtInfo());
			jsonReq.put("ext_info", order.getExtInfo());
		}

		if (!StringUtils.isBlank(order.getNotifyURL())) {
			signContent.append(order.getNotifyURL());
			jsonReq.put("notify_url", order.getNotifyURL());
		}

		signContent.append(order.getOutOrderNo());
		jsonReq.put("out_order_no", order.getOutOrderNo());

		if (!StringUtils.isBlank(order.getPlayerId())) {
			signContent.append(order.getPlayerId());
			jsonReq.put("player_id", order.getPlayerId());
		}

		if (!StringUtils.isBlank(order.getServiceData())) {
			signContent.append(order.getServiceData());
			jsonReq.put("service_data", order.getServiceData());
		}

		if (!StringUtils.isBlank(order.getServiceType())) {
			signContent.append(order.getServiceType());
			jsonReq.put("service_type", order.getServiceType());
		}

		signContent.append(order.getSubject());
		jsonReq.put("subject", order.getSubject());
		signContent.append(order.getSubmitTime());
		jsonReq.put("submit_time", order.getSubmitTime());
		signContent.append(order.getTotalFee());
		jsonReq.put("total_fee", order.getTotalFee().toString());
		signContent.append(order.getUserId());
		jsonReq.put("user_id", order.getUserId());

		String sign = RSASignature.sign(signContent.toString(), privateKey, "utf-8");
		jsonReq.put("sign", sign);

		String returnValue = GsonFactory.getDefault().toJson(jsonReq);
		return returnValue;
	}
}
