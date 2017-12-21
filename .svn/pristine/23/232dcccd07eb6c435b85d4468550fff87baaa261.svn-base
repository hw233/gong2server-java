package com.gamejelly.gong2.dbs.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.gamejelly.gong2.dbs.dao.base.AutoMetaDaoSupport;
import com.gamejelly.gong2.meta.log.BaseLog;

@Component("logDao")
public class LogDao extends AutoMetaDaoSupport {

	@Autowired
	public void setDataSource(@Qualifier("logDataSource") DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	public void addObject(BaseLog baseLog) {
		if (baseLog == null) {
			return;
		}
		addObject(baseLog.getClass(), baseLog);
	}

}
