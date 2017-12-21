package com.gamejelly.game.gong2.login.dao;

import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.meta.InMobiData;

@Component("inMobiDataDao")
public class InMobiDataDao extends BaseDomainDao<InMobiData> {

	@Override
	protected Class<InMobiData> getDomainClass() {
		return InMobiData.class;
	}

	public InMobiData getNotActiveInMobiDataRecently(String ip, String ua) {
		return getObjectByCondition("ip = ? and ua = ? and idfa = '' order by clickTime desc limit 1", ip, ua);
	}

	public InMobiData getInMobiDataByIdfa(String idfa) {
		return getObjectByCondition("idfa = ? limit 1", idfa);
	}

}
