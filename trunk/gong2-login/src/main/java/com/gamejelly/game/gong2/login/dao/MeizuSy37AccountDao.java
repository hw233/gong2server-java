package com.gamejelly.game.gong2.login.dao;

import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.meta.MeizuSy37Account;

@Component("meizuSy37Account")
public class MeizuSy37AccountDao extends BaseDomainDao<MeizuSy37Account> {

	@Override
	protected Class<MeizuSy37Account> getDomainClass() {
		return MeizuSy37Account.class;
	}

	public MeizuSy37Account getMeizuSy37AccountByMeizuAccountName(String meizuAccountName) {
		return this.getObjectByCondition("meizuAccountName = ? limit 1", meizuAccountName);
	}

}
