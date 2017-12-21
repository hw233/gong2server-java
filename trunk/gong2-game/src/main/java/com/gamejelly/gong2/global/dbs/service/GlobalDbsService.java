package com.gamejelly.gong2.global.dbs.service;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional()
@Component("globalDbsService")
public class GlobalDbsService {

	@PostConstruct
	public void init() {
		// init data

	}

	public String test(String info) {
		return info + ":" + System.currentTimeMillis();
	}

}
