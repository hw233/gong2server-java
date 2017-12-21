package com.gamejelly.gong2.dbs.dao;

import org.springframework.stereotype.Component;

import com.gamejelly.gong2.dbs.dao.base.SimpleModelDao;
import com.gamejelly.gong2.meta.MessageCountModel;

@Component("messageCountModelDao")
public class MessageCountModelDao extends SimpleModelDao<MessageCountModel> {

	@Override
	protected Class<MessageCountModel> getModelClass() {
		return MessageCountModel.class;
	}

}
