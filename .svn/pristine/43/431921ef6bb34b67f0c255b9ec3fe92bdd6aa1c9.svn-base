package com.gamejelly.game.gong2.login.dao;

import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.meta.GameAccount;

@Component("gameAccountDao")
public class GameAccountDao extends BaseDomainDao<GameAccount> {

	@Override
	protected Class<GameAccount> getDomainClass() {
		return GameAccount.class;
	}

	public GameAccount getGameAccountByAccount(String accountName) {
		return getObjectByCondition("account=?", accountName);
	}

	public GameAccount getGameAccountByAccountForUpdate(String accountName) {
		return getObjectForUpdateByCondition("account=?", accountName);
	}

	public GameAccount getGameAccountByPhone(String phone) {
		return getObjectByCondition("phone = ? limit 1", phone);
	}
	
	public GameAccount getGameAccountBydevice(String device) {
		return getObjectByCondition("deviceId = ? limit 1", device);
	}

	public GameAccount getGameAccountByIdCardNo(String idCardNo) {
		return getObjectByCondition("idCardNo = ? limit 1", idCardNo);
	}

}
