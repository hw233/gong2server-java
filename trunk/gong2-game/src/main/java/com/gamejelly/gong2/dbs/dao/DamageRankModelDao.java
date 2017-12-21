package com.gamejelly.gong2.dbs.dao;

import org.springframework.stereotype.Component;

import com.gamejelly.gong2.dbs.dao.base.BaseModelDao;
import com.gamejelly.gong2.meta.DamageRankModel;

@Component("damageRankModelDao")
public class DamageRankModelDao extends BaseModelDao<DamageRankModel> {
	@Override
	protected Class<DamageRankModel> getModelClass() {
		return DamageRankModel.class;
	}

}
