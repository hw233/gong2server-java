package com.gamejelly.game.gong2.login.meta;

import com.hadoit.game.common.framework.dao.annotation.Column;
import com.hadoit.game.common.framework.dao.annotation.Table;

/**
 * 苹果账号关系表
 * 
 * 示例:
 * 
 * gameCenterId G:1131362816 deviceId 4be75c7db4bb478fa43cd4547bcb85d8
 * gameAccount 4be75c7db4bb478fa43cd4547bcb85d8@ios
 * 
 * @author apple
 *
 */
@Table()
public class AppleAccountRelation implements Bean {
	private static final long serialVersionUID = 1L;

	private long id;

	/**
	 * game center拿到的playerId
	 */
	@Column(index = true)
	private String gameCenterId;

	private String gameCenterName;

	/**
	 * 客户端唯一设备id
	 */
	@Column(index = true)
	private String deviceId;

	/**
	 * 游戏账号, 和这两个值是一样的, AvatarModel.accountName, GameAccount.account
	 */
	@Column(index = true)
	private String gameAccount;

	/**
	 * 是否领取过奖励
	 */
	private boolean claimedReward;

	private long bindingTime;

	private long createTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getGameCenterId() {
		return gameCenterId;
	}

	public void setGameCenterId(String gameCenterId) {
		this.gameCenterId = gameCenterId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getGameAccount() {
		return gameAccount;
	}

	public void setGameAccount(String gameAccount) {
		this.gameAccount = gameAccount;
	}

	public long getBindingTime() {
		return bindingTime;
	}

	public void setBindingTime(long bindingTime) {
		this.bindingTime = bindingTime;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getGameCenterName() {
		return gameCenterName;
	}

	public void setGameCenterName(String gameCenterName) {
		this.gameCenterName = gameCenterName;
	}

	public boolean isClaimedReward() {
		return claimedReward;
	}

	public void setClaimedReward(boolean claimedReward) {
		this.claimedReward = claimedReward;
	}

	@Override
	public String toString() {
		return "[" + id + ", " + gameCenterId + ", " + gameCenterName + ", " + deviceId + ", " + gameAccount + ", "
				+ claimedReward + ", " + bindingTime + ", " + createTime + "]";
	}

}
