package com.gamejelly.game.gong2.login.dao;

import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.meta.SdkAccountRelation;


@Component("sdkAccountRelationDao")
public class SdkAccountRelationDao extends BaseDomainDao<SdkAccountRelation> {

	@Override
	protected Class<SdkAccountRelation> getDomainClass() {
		return SdkAccountRelation.class;
	}

}