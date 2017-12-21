package com.gamejelly.gong2.dbs.dao;

import org.springframework.stereotype.Component;

import com.gamejelly.gong2.dbs.dao.base.BaseModelDao;
import com.gamejelly.gong2.meta.JiejiaoModel;

@Component("jiejiaoModelDao")
public class JiejiaoModelDao extends BaseModelDao<JiejiaoModel>{

	@Override
	protected Class<JiejiaoModel> getModelClass() {
		return JiejiaoModel.class;
	}

}
