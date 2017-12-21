package com.gamejelly.gong2.dbs.dao;

import org.springframework.stereotype.Component;

import com.gamejelly.gong2.dbs.dao.base.BaseModelDao;
import com.gamejelly.gong2.meta.GongHuiMemberModel;

@Component("gongHuiMemberModelDao")
public class GongHuiMemberModelDao extends BaseModelDao<GongHuiMemberModel> {

	@Override
	protected Class<GongHuiMemberModel> getModelClass() {
		return GongHuiMemberModel.class;
	}

}
