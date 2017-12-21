package com.gamejelly.gong2.dbs.dao;

import org.springframework.stereotype.Component;

import com.gamejelly.gong2.dbs.dao.base.BaseModelDao;
import com.gamejelly.gong2.meta.GongHuiFubenModel;

@Component("gongHuiFubenModelDao")
public class GongHuiFubenModelDao extends BaseModelDao<GongHuiFubenModel> {
	@Override
	protected Class<GongHuiFubenModel> getModelClass() {
		return GongHuiFubenModel.class;
	}
}
