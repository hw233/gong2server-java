package com.gamejelly.game.gong2.login.meta;

import com.hadoit.game.common.framework.dao.annotation.Column;
import com.hadoit.game.common.framework.dao.annotation.Index;
import com.hadoit.game.common.framework.dao.annotation.Table;

/**
 * 平台支付记录
 * 
 * @author apple
 * 
 */
@Table(recursion = true, indexs = { @Index(columns = { "avatarId", "serverId", "serverState" }) })
public class PlatformPayRecord implements Bean {
	private static final long serialVersionUID = 1L;

	private long id;

	private String accountName;

	private String avatarId;

	private String productId;

	private int productCount;

	private int productAmount; // 价格，以分为单位

	/**
	 * 订单号
	 */
	@Column(index = true)
	private String orderSn;

	/**
	 * 渠道返回其他信息（譬如360接口的order_id）
	 */
	private String extData1;

	private String extData2;

	/**
	 * 1成功验证,2成功验证后给物品
	 */
	private int serverState;

	private int serverId;

	/**
	 * 其实应该是opr, 这个值都是客户端传过来的, 这里命名有点问题
	 */
	private String oprGroup;

	private String source; // 充值来源 appstore, ...

	private String ip;

	private String platform;

	private String deviceId;

	private String deviceName;

	private String idfa;

	private String imei;

	private long successTime;

	private long createTime;

	private String payPlatformOrderId;

	//发送过红包
	private boolean sendedRedPackage;

	public int getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(int productAmount) {
		this.productAmount = productAmount;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAvatarId() {
		return avatarId;
	}

	public void setAvatarId(String avatarId) {
		this.avatarId = avatarId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getProductCount() {
		return productCount;
	}

	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}

	public int getServerState() {
		return serverState;
	}

	public void setServerState(int serverState) {
		this.serverState = serverState;
	}

	public long getSuccessTime() {
		return successTime;
	}

	public void setSuccessTime(long successTime) {
		this.successTime = successTime;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getExtData1() {
		return extData1;
	}

	public void setExtData1(String extData1) {
		this.extData1 = extData1;
	}

	public String getExtData2() {
		return extData2;
	}

	public void setExtData2(String extData2) {
		this.extData2 = extData2;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getOprGroup() {
		return oprGroup;
	}

	public void setOprGroup(String oprGroup) {
		this.oprGroup = oprGroup;
	}

	public String getIdfa() {
		return idfa;
	}

	public void setIdfa(String idfa) {
		this.idfa = idfa;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getPayPlatformOrderId() {
		return payPlatformOrderId;
	}

	public void setPayPlatformOrderId(String payPlatformOrderId) {
		this.payPlatformOrderId = payPlatformOrderId;
	}

	public boolean isSendedRedPackage() {
		return sendedRedPackage;
	}

	public void setSendedRedPackage(boolean sendedRedPackage) {
		this.sendedRedPackage = sendedRedPackage;
	}

	@Override
	public String toString() {
		return "PlatformPayRecord{" +
				"id=" + id +
				", accountName='" + accountName + '\'' +
				", avatarId='" + avatarId + '\'' +
				", productId='" + productId + '\'' +
				", productCount=" + productCount +
				", productAmount=" + productAmount +
				", orderSn='" + orderSn + '\'' +
				", extData1='" + extData1 + '\'' +
				", extData2='" + extData2 + '\'' +
				", serverState=" + serverState +
				", serverId=" + serverId +
				", oprGroup='" + oprGroup + '\'' +
				", source='" + source + '\'' +
				", ip='" + ip + '\'' +
				", platform='" + platform + '\'' +
				", deviceId='" + deviceId + '\'' +
				", deviceName='" + deviceName + '\'' +
				", idfa='" + idfa + '\'' +
				", imei='" + imei + '\'' +
				", successTime=" + successTime +
				", createTime=" + createTime +
				", payPlatformOrderId='" + payPlatformOrderId + '\'' +
				", sendedRedPackage=" + sendedRedPackage +
				'}';
	}
}
