package com.gamejelly.gong2.dbs.dao;

import org.springframework.stereotype.Component;

import com.gamejelly.gong2.dbs.dao.base.BaseModelDao;
import com.gamejelly.gong2.meta.RecentlyContactModel;

@Component("recentlyContactModelDao")
public class RecentlyContactModelDao extends BaseModelDao<RecentlyContactModel> {

	@Override
	protected Class<RecentlyContactModel> getModelClass() {
		return RecentlyContactModel.class;
	}

}
