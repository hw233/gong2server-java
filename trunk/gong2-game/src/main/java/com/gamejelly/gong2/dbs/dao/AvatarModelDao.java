package com.gamejelly.gong2.dbs.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gamejelly.gong2.dbs.dao.base.BaseModelDao;
import com.gamejelly.gong2.meta.AvatarModel;

@Component("avatarModelDao")
public class AvatarModelDao extends BaseModelDao<AvatarModel> {

	@Autowired
	private ItemModelDao itemModelDao;

	@Autowired
	private ServantModelDao servantModelDao;

	@Autowired
	private CycleOperateModelDao cycleOperateModelDao;

	@Autowired
	private GuankaModelDao guankaModelDao;

	@Override
	protected Class<AvatarModel> getModelClass() {
		return AvatarModel.class;
	}

	@Override
	protected BaseModelDao<?>[] getChildModelDaos() {
		return new BaseModelDao<?>[] { itemModelDao, servantModelDao, cycleOperateModelDao, guankaModelDao };
	}

	@Override
	protected void writeModelChildren(AvatarModel model) {
		itemModelDao.saveModels(model.getDbId(), model.getItemList());
		servantModelDao.saveModels(model.getDbId(), model.getServantList());
		cycleOperateModelDao.saveModels(model.getDbId(), model.getCycleOperateList());
		guankaModelDao.saveModels(model.getDbId(), model.getGuankaList());
	}

	@Override
	protected void readModelChildren(AvatarModel model) {
		model.setItemList(itemModelDao.getModels(model.getDbId()));
		model.setServantList(servantModelDao.getModels(model.getDbId()));
		model.setCycleOperateList(cycleOperateModelDao.getModels(model.getDbId()));
		model.setGuankaList(guankaModelDao.getModels(model.getDbId()));
	}

}
