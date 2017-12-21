package com.gamejelly.gong2.dbs.dao;

import org.springframework.stereotype.Component;

import com.gamejelly.gong2.dbs.dao.base.BaseModelDao;
import com.gamejelly.gong2.meta.CycleOperateModel;;

@Component("cycleOperateModelDao")
public class CycleOperateModelDao extends BaseModelDao<CycleOperateModel> {

	@Override
	protected Class<CycleOperateModel> getModelClass() {
		return CycleOperateModel.class;
	}

}
