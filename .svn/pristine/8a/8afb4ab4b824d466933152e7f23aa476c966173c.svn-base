package com.gamejelly.game.gong2.login.dao;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.gamejelly.game.gong2.login.meta.Bean;
import com.hadoit.game.common.framework.dao.domain.SimpleDomainConfig;
import com.hadoit.game.common.framework.dao.domain.SimpleDomainJdbcDaoSupport;

public abstract class BaseDomainDao<T extends Bean> extends SimpleDomainJdbcDaoSupport<T> {

	@Autowired
	public void setDomainConfig(SimpleDomainConfig domainConfig) {
		super.setDomainConfig(domainConfig);
	}

	@PostConstruct
	public void init() {
		initTable(getDomainClass());
	}

	protected abstract Class<T> getDomainClass();
}
