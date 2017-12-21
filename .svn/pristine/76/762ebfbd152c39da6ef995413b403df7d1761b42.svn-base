package com.gamejelly.gong2.dbs.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hadoit.game.common.framework.nosql.redis.JedisCallback;
import com.hadoit.game.common.framework.nosql.redis.JedisTemplate;
import com.hadoit.game.common.lang.DataUtils;
import com.hadoit.game.common.lang.Pair;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

@Component("gameService")
public class GameService {

	@Autowired
	private JedisTemplate jedisTemplate;

	@Autowired
	private DbsService dbsService;

	public void sendSystemCommonMsg(List<String> recvs, String content) {
		long currTime = System.currentTimeMillis();
		List<String> rwList = recvs;
		if (CollectionUtils.isNotEmpty(rwList)) {
			for (String aId : rwList) {
				dbsService.addSystemMsg(aId, content, null, currTime);
			}
		} else {
			long baseId = 0;
			while (true) {
				Pair<Long, List<String>> lps = dbsService.createAllSystemMsgList(baseId);
				if (lps == null) {
					break;
				}
				baseId = lps.getFirst();
				for (String aId : lps.getSecond()) {
					dbsService.addSystemMsg(aId, content, null, currTime);
				}
			}
		}
	}

}
