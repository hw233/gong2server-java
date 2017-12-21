package com.gamejelly.game.gong2.login.service.sdk.impl.gionee.bean;

import java.math.BigDecimal;
import java.util.Date;

import com.gamejelly.game.gong2.login.service.sdk.impl.gionee.util.SubmitTimeUtil;

public class Order {
	private final String apiKey; // 商户apiKey

	private final String outOrderNo;// 商户订单号

	private final String userId; // 用户ID，由客户端登录后获取该参数

	private final String subject;// 商品名称

	private final BigDecimal totalFee;// 需支付金额，dealPrice值需要等于totalFee

	private final BigDecimal dealPrice;// 商户总金额，dealPrice值需要等于totalFee

	private final String deliverType; // 交货类型, 填1

	private final String submitTime;// 订单提交时间，格式为yyyyMMddHHmmss，由商户服务器提供，客户端调起支付收银台时需要使用这个值

	private final String expireTime;// 订单失效时间，可选字段，格式为yyyyMMddHHmmss，如果有值必须参与签名

	private final String notifyURL;// 服务器通知地址，可选字段，不能超过1024个字符，如果有该字段，必须参与签名

	private final String serviceType; // 业务类型, 可选字段, 如果有值必须参与签名

	private final String serviceData; // 业务参数, 可选字段, 如果有值必须参与签名

	private final String extInfo; // 备注信息, 可选字段, 如果有值必须参与签名

	private final String playerId; // 玩家playerId, 可选字段, 如果有值必须参与签名

	/**
	 * @param apiKey
	 *            商户apiKey [必填]
	 * @param outOrderNo
	 *            商户订单号 [必填]
	 * @param userId
	 *            用户账号id [必填]
	 * @param subject
	 *            商品名称 [必填]
	 * @param totalFee
	 *            需支付金额，dealPrice值需要等于totalFee [必填]
	 * @param dealPrice
	 *            商户总金额，dealPrice值需要等于totalFee [必填]
	 * @param deliverType
	 *            交货类型，接入时deliverType值为1 [必填]
	 * @param submitTime
	 *            订单提交时间，格式为yyyyMMddHHmmss，由商户服务器提供，客户端调起支付收银台时需要使用这个值 [必填]
	 * @param expireTime
	 *            订单失效时间，可选，格式为yyyyMMddHHmmss，如果有值必须参与签名 [可选]
	 * @param notifyURL
	 *            服务器通知地址，不能超过1024个字符，如果有该字段，必须参与签名(如果商户需要自定义参数，可以在创建订单时以
	 *            "http://www.partner.com/notifyReceiver?param1=value1&param2=value2"
	 *            的形式定义url) [可选]
	 * @param serviceType
	 *            订单提交时间，格式为yyyyMMddHHmmss，由商户服务器提供，客户端调起支付收银台时需要使用这个值 [必填]
	 * @param serviceData
	 *            订单失效时间，可选，格式为yyyyMMddHHmmss，如果有值必须参与签名 [可选]
	 * @param extInfo
	 *            订单提交时间，格式为yyyyMMddHHmmss，由商户服务器提供，客户端调起支付收银台时需要使用这个值 [必填]
	 * @param playerId
	 *            订单失效时间，可选，格式为yyyyMMddHHmmss，如果有值必须参与签名 [可选]
	 */

	public Order(String apiKey, String outOrderNo, String userId, String subject, BigDecimal totalFee,
			BigDecimal dealPrice, String deliverType, Date submitTime, Date expireTime, String notifyURL,
			String serviceType, String serviceData, String extInfo, String playerId) {
		this.apiKey = apiKey;
		this.outOrderNo = outOrderNo;
		this.userId = userId;
		this.subject = subject;
		this.totalFee = totalFee;
		this.dealPrice = dealPrice;
		this.deliverType = deliverType;
		this.submitTime = SubmitTimeUtil.toString(submitTime);
		this.expireTime = SubmitTimeUtil.toString(expireTime);
		this.notifyURL = notifyURL;
		this.serviceType = serviceType;
		this.serviceData = serviceData;
		this.extInfo = extInfo;
		this.playerId = playerId;
	}

	public String getApiKey() {
		return apiKey;
	}

	public String getOutOrderNo() {
		return outOrderNo;
	}

	public String getUserId() {
		return userId;
	}

	public String getSubject() {
		return subject;
	}

	public BigDecimal getTotalFee() {
		return totalFee;
	}

	public BigDecimal getDealPrice() {
		return dealPrice;
	}

	public String getDeliverType() {
		return deliverType;
	}

	public String getSubmitTime() {
		return submitTime;
	}

	public String getExpireTime() {
		return expireTime;
	}

	public String getNotifyURL() {
		return notifyURL;
	}

	public String getServiceType() {
		return serviceType;
	}

	public String getServiceData() {
		return serviceData;
	}

	public String getExtInfo() {
		return extInfo;
	}

	public String getPlayerId() {
		return playerId;
	}

	@Override
	public String toString() {
		return "Order [apiKey=" + apiKey + ", outOrderNo=" + outOrderNo + ", userId=" + userId + ", subject=" + subject
				+ ", totalFee=" + totalFee + ", dealPrice=" + dealPrice + ", deliverType=" + deliverType
				+ ", submitTime=" + submitTime + ", expireTime=" + expireTime + ", notifyURL=" + notifyURL
				+ ", serviceType=" + serviceType + ", serviceData=" + serviceData + ", extInfo=" + extInfo
				+ ", playerId=" + playerId + "]";
	}

}
