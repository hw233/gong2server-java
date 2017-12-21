package com.gamejelly.gong2.dbs.dao;

import org.springframework.stereotype.Component;

import com.gamejelly.gong2.dbs.dao.base.BaseModelDao;
import com.gamejelly.gong2.meta.GongHuiMsgModel;


@Component("gongHuiMsgModelDao")
public class GongHuiMsgModelDao extends BaseModelDao<GongHuiMsgModel> {
	@Override
	protected Class<GongHuiMsgModel> getModelClass() {
		return GongHuiMsgModel.class;
	}
}
