package com.gamejelly.game.gong2.login.dao;

import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.meta.Notice;

@Component("noticeDao")
public class NoticeDao extends BaseDomainDao<Notice> {

	@Override
	protected Class<Notice> getDomainClass() {
		return Notice.class;
	}

	public Notice getRecentlyNotice() {
		return getObject("select * from Notice order by createTime desc limit 1");
	}

}