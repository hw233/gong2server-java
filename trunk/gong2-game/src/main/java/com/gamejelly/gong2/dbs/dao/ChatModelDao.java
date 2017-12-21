package com.gamejelly.gong2.dbs.dao;

import org.springframework.stereotype.Component;

import com.gamejelly.gong2.dbs.dao.base.BaseModelDao;
import com.gamejelly.gong2.meta.ChatModel;

@Component("chatModelDao")
public class ChatModelDao extends BaseModelDao<ChatModel>{

	@Override
	protected Class<ChatModel> getModelClass() {
		return ChatModel.class;
	}

}
