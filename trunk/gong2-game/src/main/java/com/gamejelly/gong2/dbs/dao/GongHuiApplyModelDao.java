package com.gamejelly.gong2.dbs.dao;

import org.springframework.stereotype.Component;

import com.gamejelly.gong2.dbs.dao.base.BaseModelDao;
import com.gamejelly.gong2.meta.GongHuiApplyModel;


@Component("gongHuiApplyModelDao")
public class GongHuiApplyModelDao extends BaseModelDao<GongHuiApplyModel> {
	@Override
	protected Class<GongHuiApplyModel> getModelClass() {
		return GongHuiApplyModel.class;
	}
}
