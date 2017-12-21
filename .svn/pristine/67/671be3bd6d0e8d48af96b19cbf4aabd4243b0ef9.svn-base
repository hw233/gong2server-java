package com.gamejelly.gong2.dbs.dao.base;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.hadoit.game.common.framework.dao.domain.SimpleDomainConfig;
import com.hadoit.game.common.framework.dao.domain.model.IBaseModel;
import com.hadoit.game.common.framework.dao.domain.model.ModelJdbcDaoSupport;

/**
 * @author bezy 2013-12-9
 * 
 * @param <T>
 */
public abstract class BaseModelDao<T extends IBaseModel> extends ModelJdbcDaoSupport<T> {

	@Autowired
	public void setDomainConfig(SimpleDomainConfig domainConfig) {
		super.setDomainConfig(domainConfig);
	}

	@PostConstruct
	public void init() {
		super.init();
	}

}
