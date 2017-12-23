package com.gamejelly.gong2.dbs.dao;

import com.gamejelly.gong2.dbs.dao.base.BaseModelDao;
import com.gamejelly.gong2.meta.BuildingModel;
import org.springframework.stereotype.Component;

@Component("buildingModelDao")
public class BuildingModelDao extends BaseModelDao<BuildingModel> {

	@Override
	protected Class<BuildingModel> getModelClass() {
		return BuildingModel.class;
	}

}
