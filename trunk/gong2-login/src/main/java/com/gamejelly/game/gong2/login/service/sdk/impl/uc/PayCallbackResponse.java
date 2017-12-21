package com.gamejelly.game.gong2.login.service.sdk.impl.uc;

/**
 * 支付回调的响应内容类。
 */
public class PayCallbackResponse {
	String sign = "";
	PayCallbackResponseData data;

	public String getSign() {
		return this.sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public PayCallbackResponseData getData() {
		return this.data;
	}

	public void setData(PayCallbackResponseData data) {
		this.data = data;
	}

	public class PayCallbackResponseData {
		private String orderId;
		private int gameId;
		private int serverId;
		private String accountId;
		private int payWay;
		private String amount;
		private String callbackInfo;
		private String orderStatus;
		private String failedDesc = "";
		private String cpOrderId = "";
		private String creator = "";

		public String getCpOrderId() {
			return cpOrderId;
		}

		public void setCpOrderId(String cpOrderId) {
			this.cpOrderId = cpOrderId;
		}

		public String getOrderId() {
			return this.orderId;
		}

		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}

		public int getGameId() {
			return this.gameId;
		}

		public void setGameId(int gameId) {
			this.gameId = gameId;
		}

		public int getServerId() {
			return this.serverId;
		}

		public void setServerId(int serverId) {
			this.serverId = serverId;
		}

		public String getAccountId() {
			return this.accountId;
		}

		public void setAccountId(String accountId) {
			this.accountId = accountId;
		}

		public int getPayWay() {
			return this.payWay;
		}

		public void setPayWay(int payWay) {
			this.payWay = payWay;
		}

		public String getAmount() {
			return this.amount;
		}

		public void setAmount(String amount) {
			this.amount = amount;
		}

		public String getCallbackInfo() {
			return this.callbackInfo;
		}

		public void setCallbackInfo(String callbackInfo) {
			this.callbackInfo = callbackInfo;
		}

		public String getOrderStatus() {
			return this.orderStatus;
		}

		public void setOrderStatus(String orderStatus) {
			this.orderStatus = orderStatus;
		}

		public String getFailedDesc() {
			return this.failedDesc;
		}

		public void setFailedDesc(String failedDesc) {
			this.failedDesc = failedDesc;
		}

		public String getCreator() {
			return creator;
		}

		public void setCreator(String creator) {
			this.creator = creator;
		}
	}

}
