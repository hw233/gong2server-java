package com.gamejelly.gong2.dbs.dao.base;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.gamejelly.gong2.meta.base.SimpleModel;
import com.hadoit.game.common.framework.dao.domain.SimpleDomainConfig;
import com.hadoit.game.common.framework.dao.domain.SimpleDomainJdbcDaoSupport;

/**
 * @author bezy 2013-12-9
 * 
 * @param <T>
 */
public abstract class SimpleModelDao<T extends SimpleModel> extends SimpleDomainJdbcDaoSupport<T> {

	@Autowired
	public void setDomainConfig(SimpleDomainConfig domainConfig) {
		super.setDomainConfig(domainConfig);
	}

	@PostConstruct
	public void init() {
		initTable(getModelClass(), getTblPrefix());
	}

	protected abstract Class<T> getModelClass();

	protected String getTblPrefix() {
		return null;
	}

}
