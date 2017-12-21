package com.gamejelly.gong2.global.dbs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hadoit.game.common.framework.nosql.redis.JedisTemplate;

@Component("globalJedisService")
public class GlobalJedisService {

	@Autowired
	private JedisTemplate jedisTemplate;

	@Autowired
	private GlobalDbsService globalDbsService;

	public void initRank() {

	}

	public void touchJedis() {
		jedisTemplate.get("TEST1");
	}

}
