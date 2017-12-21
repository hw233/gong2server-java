package com.gamejelly.gong2.global.app.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gamejelly.gong2.global.app.annotation.AutoGlobalProxy;
import com.gamejelly.gong2.utils.GlobalServerDbConstants;
import com.gamejelly.gong2.utils.GlobalServerRpcConstants;
import com.hadoit.game.engine.core.rpc.base.annotation.Rpc;
import com.hadoit.game.engine.guardian.gas.entity.RawCommandCallback;
import com.hadoit.game.engine.guardian.gas.entity.RawCommandFutureCallback;
import com.hadoit.game.engine.guardian.global.GlobalContext;
import com.hadoit.game.engine.guardian.global.GlobalManager;
import com.hadoit.game.engine.guardian.global.proxy.GlobalProxy;

@AutoGlobalProxy
@Component("globalAppProxy")
public class GlobalAppProxy implements GlobalProxy {

	private GlobalContext context;

	@Autowired
	private GlobalManager globalManager;

	@Override
	public void setContext(GlobalContext context) {
		this.context = context;
	}

	public void executeRawCommand(String command, Object[] args, RawCommandCallback callback) {
		context.executeRawCommand(null, command, args, new RawCommandFutureCallback(callback));
	}

	public void executeRawCommandBalance(String balanceId, String command, Object[] args, RawCommandCallback callback) {
		context.executeRawCommand(balanceId, command, args, new RawCommandFutureCallback(callback));
	}

	@Rpc(fullAlias = GlobalServerRpcConstants.REQ_TEST)
	public void test(String info) {
		executeRawCommand(GlobalServerDbConstants.CMD_TEST, new Object[] { info }, new RawCommandCallback() {
			@Override
			public void onResult(Object result, int num, String error) {
				System.out.println(result);
			}
		});
	}

}
