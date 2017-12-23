package com.gamejelly.gong2.dbs.service;

import com.gamejelly.gong2.dbs.dao.BuildingModelDao;
import com.gamejelly.gong2.meta.BuildingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional()
@Component()
public class BuildingService {
	@Autowired
	private BuildingModelDao buildingModelDao;

	public List<BuildingModel> findBuildingListByParentId(int parentId) {
		return buildingModelDao.getModels(parentId);
	}



}
