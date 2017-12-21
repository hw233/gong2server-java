package com.gamejelly.game.gong2.login.meta;

import com.hadoit.game.common.framework.dao.annotation.Column;
import com.hadoit.game.common.framework.dao.annotation.Index;
import com.hadoit.game.common.framework.dao.annotation.Table;

/**
 * 平台自由支付记录
 * 
 * @author apple
 * 
 */
@Table(recursion = true, indexs = { @Index(columns = { "avatarId", "serverId", "serverState" }) })
public class PlatformPayAmountRecord implements Bean {
	private static final long serialVersionUID = 1L;

	private long id;

	private String accountName;

	private String avatarId;

	private int payAmount;

	private int finalGetGold;

	private int originalGetGold;

	@Column(index = true)
	private String orderSn;

	/**
	 * 1成功验证,2成功验证后给物品
	 */
	private int serverState;

	private int serverId;

	/**
	 * 其实应该是opr, 这个值都是客户端传过来的, 这里命名有点问题
	 */
	private String oprGroup;

	private long successTime;

	private long createTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public int getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(int payAmount) {
		this.payAmount = payAmount;
	}

	public int getFinalGetGold() {
		return finalGetGold;
	}

	public void setFinalGetGold(int finalGetGold) {
		this.finalGetGold = finalGetGold;
	}

	public int getOriginalGetGold() {
		return originalGetGold;
	}

	public void setOriginalGetGold(int originalGetGold) {
		this.originalGetGold = originalGetGold;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public int getServerState() {
		return serverState;
	}

	public void setServerState(int serverState) {
		this.serverState = serverState;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public String getOprGroup() {
		return oprGroup;
	}

	public void setOprGroup(String oprGroup) {
		this.oprGroup = oprGroup;
	}

	public long getSuccessTime() {
		return successTime;
	}

	public void setSuccessTime(long successTime) {
		this.successTime = successTime;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getAvatarId() {
		return avatarId;
	}

	public void setAvatarId(String avatarId) {
		this.avatarId = avatarId;
	}

}
