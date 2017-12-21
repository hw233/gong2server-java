package com.gamejelly.gong2.dbs.dao;

import org.springframework.stereotype.Component;

import com.gamejelly.gong2.dbs.dao.base.BaseModelDao;
import com.gamejelly.gong2.meta.ServantModel;

@Component("servantModelDao")
public class ServantModelDao extends BaseModelDao<ServantModel> {

	@Override
	protected Class<ServantModel> getModelClass() {
		return ServantModel.class;
	}

}
