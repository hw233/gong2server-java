package com.gamejelly.gong2.dbs.dao;

import org.springframework.stereotype.Component;

import com.gamejelly.gong2.dbs.dao.base.BaseModelDao;
import com.gamejelly.gong2.meta.SayMessageModel;

@Component("sayMessageModelDao")
public class SayMessageModelDao extends BaseModelDao<SayMessageModel>{

	@Override
	protected Class<SayMessageModel> getModelClass() {
		return SayMessageModel.class;
	}

}
