package com.gamejelly.gong2.gas.cross.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.gamejelly.gong2.gas.cross.annotation.AutoCrossListener;
import com.gamejelly.gong2.utils.GlobalServerRpcConstants;
import com.hadoit.game.engine.core.rpc.base.annotation.Rpc;
import com.hadoit.game.engine.guardian.gas.GasManager;
import com.hadoit.game.engine.guardian.global.client.GlobalClient;
import com.hadoit.game.engine.guardian.global.client.listener.GlobalListener;

@AutoCrossListener
@Component("crossServerProxy")
public class CrossServerListener implements GlobalListener {

	protected GlobalClient context;

	@Autowired
	@Qualifier("gasManager")
	private GasManager gasManager;

	@Override
	public void setContext(GlobalClient context) {
		this.context = context;
	}

	@Rpc(fullAlias = GlobalServerRpcConstants.CALL_OTHER_TEST)
	public void test(String from) {
		System.out.println("call from:" + from);
	}

}
