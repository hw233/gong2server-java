package com.gamejelly.game.gong2.login.dao;

import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.meta.AppVersion;

@Component("appVersionDao")
public class AppVersionDao extends BaseDomainDao<AppVersion> {

	@Override
	protected Class<AppVersion> getDomainClass() {
		return AppVersion.class;
	}

}
