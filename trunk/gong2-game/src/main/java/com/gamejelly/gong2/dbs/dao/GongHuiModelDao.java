package com.gamejelly.gong2.dbs.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gamejelly.gong2.dbs.dao.base.BaseModelDao;
import com.gamejelly.gong2.meta.GongHuiModel;

@Component("gongHuiModelDao")
public class GongHuiModelDao extends BaseModelDao<GongHuiModel> {
	@Autowired
	private GongHuiMsgModelDao gongHuiMsgModelDao;
	
	@Autowired
	private GongHuiApplyModelDao gongHuiApplyModelDao;
	
	@Autowired
	private GongHuiMemberModelDao gongHuiMemberModelDao;
	
	@Autowired
	private GongHuiFubenModelDao gongHuiFubenModelDao;
	
	@Autowired
	private GongHuiMissionModelDao gongHuiMissionModelDao;
	
	@Autowired
	private DamageRankModelDao damageRankModelDao;

	@Override
	protected Class<GongHuiModel> getModelClass() {
		return GongHuiModel.class;
	}

	@Override
	protected BaseModelDao<?>[] getChildModelDaos() {
		return new BaseModelDao<?>[] { gongHuiMsgModelDao, gongHuiApplyModelDao, gongHuiMemberModelDao, gongHuiFubenModelDao, gongHuiMissionModelDao,damageRankModelDao };
	}

	@Override
	protected void writeModelChildren(GongHuiModel model) {
		gongHuiMsgModelDao.saveModels(model.getDbId(), model.getMessageList());
		gongHuiApplyModelDao.saveModels(model.getDbId(), model.getApplyList());
		gongHuiMemberModelDao.saveModels(model.getDbId(), model.getMemberList());
		gongHuiFubenModelDao.saveModels(model.getDbId(), model.getOpenFubenModels());
		gongHuiMissionModelDao.saveModels(model.getDbId(), model.getMissionList());
		damageRankModelDao.saveModels(model.getDbId(), model.getGangBossModel().getDamageRankModel());
	}

	@Override
	protected void readModelChildren(GongHuiModel model) {
		model.setMessageList(gongHuiMsgModelDao.getModels(model.getDbId()));
		model.setApplyList(gongHuiApplyModelDao.getModels(model.getDbId()));
		model.setMemberList(gongHuiMemberModelDao.getModels(model.getDbId()));
		model.setOpenFubenModels(gongHuiFubenModelDao.getModels(model.getDbId()));
		model.setMissionList(gongHuiMissionModelDao.getModels(model.getDbId()));
		model.getGangBossModel().setDamageRankModel(damageRankModelDao.getModels(model.getDbId()));
	}

}
