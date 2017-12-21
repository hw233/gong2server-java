package com.gamejelly.gong2.global.dbs.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gamejelly.gong2.global.dbs.annotation.AutoGlobalDbsCommandProxy;
import com.gamejelly.gong2.global.dbs.service.GlobalDbsService;
import com.gamejelly.gong2.global.dbs.service.GlobalJedisService;
import com.gamejelly.gong2.utils.GlobalServerDbConstants;
import com.hadoit.game.engine.guardian.dbs.base.CommandRpc;
import com.hadoit.game.engine.guardian.dbs.base.RawCommandResult;
import com.hadoit.game.engine.guardian.dbs.proxy.DbsCommandProxy;

@AutoGlobalDbsCommandProxy
@Component("globalCommandProxy")
public class GlobalCommandProxy implements DbsCommandProxy {
	@Autowired
	private GlobalDbsService globalDbsService;

	@Autowired
	private GlobalJedisService globalJedisService;

	@CommandRpc(fullAlias = GlobalServerDbConstants.CMD_TEST, unpack = true)
	public RawCommandResult test(String info) {
		return new RawCommandResult(globalDbsService.test(info));
	}

}
