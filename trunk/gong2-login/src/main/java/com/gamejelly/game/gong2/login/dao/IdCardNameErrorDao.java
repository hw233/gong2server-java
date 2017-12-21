package com.gamejelly.game.gong2.login.dao;

import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.meta.IdCardNameError;

@Component("idCardNameErrorDao")
public class IdCardNameErrorDao extends BaseDomainDao<IdCardNameError> {

	@Override
	protected Class<IdCardNameError> getDomainClass() {
		return IdCardNameError.class;
	}

	public IdCardNameError getIdCardNameError(String name, String idCardNo) {
		return getObjectByCondition("name=? and idCardNo=? limit 1", name, idCardNo);
	}
}