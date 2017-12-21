package com.gamejelly.game.gong2.login.dao;

import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.meta.InvitationCodeRecord;

@Component("invitationCodeRecordDao")
public class InvitationCodeRecordDao extends BaseDomainDao<InvitationCodeRecord> {


	@Override
	protected Class<InvitationCodeRecord> getDomainClass() {
		return InvitationCodeRecord.class;
	}

}
