package com.gamejelly.gong2.gas.admin;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.script.Bindings;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.gamejelly.gong2.gas.admin.service.ServerConfigService;
import com.gamejelly.gong2.gas.entity.AvatarEntity;
import com.gamejelly.gong2.gas.service.user.UserService;
import com.gamejelly.gong2.utils.GongRpcConstants;
import com.gamejelly.gong2.utils.LoggerHelper;
import com.gamejelly.gong2.utils.SecurityUtils;
import com.hadoit.game.common.lang.DataUtils;
import com.hadoit.game.engine.core.rpc.base.annotation.Rpc;
import com.hadoit.game.engine.core.rpc.base.annotation.RpcEvent;
import com.hadoit.game.engine.core.rpc.base.constant.RpcEventType;
import com.hadoit.game.engine.core.rpc.base.handler.RpcHandler;
import com.hadoit.game.engine.core.rpc.simple.context.RpcResult;
import com.hadoit.game.engine.core.rpc.simple.context.ServerChannelContext;
import com.hadoit.game.engine.core.script.ScriptEngineCore;
import com.hadoit.game.engine.guardian.gas.GasContext;
import com.hadoit.game.engine.guardian.gas.GasManager;
import com.hadoit.game.engine.guardian.gas.entity.Entity;

@Component("adminProxy")
public class AdminProxy implements RpcHandler, BeanFactoryAware {

	private BeanFactory beanFactory;

	private ScriptEngineCore scriptEngine;

	private GasContext context;

	@Autowired
	@Qualifier("serverConfigService")
	private ServerConfigService serverConfigService;

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	@Qualifier("gasManager")
	private GasManager gasManager;

	public void setContext(GasContext context) {
		this.context = context;
		scriptEngine = new ScriptEngineCore();
		scriptEngine.init();
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	@RpcEvent(RpcEventType.CHANNEL_CONNECTED)
	public void onConnected(final ServerChannelContext channelContext) throws Exception {
		LoggerHelper.info("Admin server open channel", null, channelContext.getChannel().getId());
	}

	@RpcEvent(RpcEventType.CHANNEL_DISCONNECTED)
	public void onDisconnected(final ServerChannelContext channelContext) throws Exception {
		LoggerHelper.info("Admin server close channel", null, channelContext.getChannel().getId());
	}

	@Rpc(fullAlias = GongRpcConstants.ADMIN_REQ_HANDSHAKE)
	public void handshake(final ServerChannelContext channelContext) {
		LoggerHelper.info("Admin server handshake", null, channelContext.getChannel().getId());
		SecurityUtils.internalServerLogin(channelContext, "GMS");
	}

	@Rpc(fullAlias = GongRpcConstants.ADMIN_REQ_OPEN_SERVER)
	public void openServer(final RpcResult rpcResult) {
		serverConfigService.setOpen(true);
		rpcResult.result("开服成功!");
	}

	@Rpc(fullAlias = GongRpcConstants.ADMIN_REQ_CLOSE_SERVER)
	public void closeServer(final RpcResult rpcResult) {
		serverConfigService.setOpen(false);
		rpcResult.result("关服成功!");
	}

	@Rpc(fullAlias = GongRpcConstants.ADMIN_REQ_STOP_SERVER)
	public void stopServer(final RpcResult rpcResult) {
		serverConfigService.setOpen(false);
		// 这里还要踢人
		for (Entity e : context.getEntityManager().getAllEntities()) {
			if (e instanceof AvatarEntity) {
				ServerChannelContext scc = SecurityUtils
						.getChannelContext(((AvatarEntity) e).getAvatarModel().getAccountName());
				userService.logout(scc);
			}
		}
		rpcResult.result("停服成功!");
	}

	@Rpc(fullAlias = GongRpcConstants.ADMIN_REQ_MAX_ONLINE)
	public void setMaxOnlineCount(final RpcResult rpcResult, int maxOnlineCount) {
		serverConfigService.setMaxOnlineCount(maxOnlineCount);
		rpcResult.result("设置最大在线上限: " + maxOnlineCount);
	}

	@Rpc(fullAlias = GongRpcConstants.ADMIN_REQ_FLUSH_DATA)
	public void flushData(final RpcResult rpcResult) {
		context.getEntityManager().flushAll();
		rpcResult.result("成功flushData!");
	}

	@Rpc(fullAlias = GongRpcConstants.ADMIN_REQ_SERVER_CONFIG)
	public void getServerConfig(final RpcResult rpcResult) {
		rpcResult.result(Collections.singletonMap("result", serverConfigService.getServerConfig()));
	}

	@Rpc(fullAlias = GongRpcConstants.ADMIN_REQ_RUN_SCRIPT)
	public void runScript(final RpcResult rpcResult, String script) {
		Map<String, Object> result = new HashMap<String, Object>();
		long startTime = System.currentTimeMillis();
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		scriptEngine.getScriptEngineFactory().getScriptEngine().getContext().setWriter(pw);
		scriptEngine.getScriptEngineFactory().getScriptEngine().getContext().setErrorWriter(pw);

		Bindings bindings = scriptEngine.createBindings();
		bindings.put("$context", context);
		bindings.put("$beanFactory", beanFactory);
		bindings.put("$em", context.getEntityManager());
		try {
			Object o = scriptEngine.evalScriptText(script, bindings);
			if (o != null) {
				result.put("result", DataUtils.toString(o));
			}
		} catch (Exception e) {
			result.put("error", DataUtils.getStackTrace(e));
		}
		result.put("output", sw.toString());
		result.put("costTime", System.currentTimeMillis() - startTime);
		rpcResult.result(Collections.singletonMap("result", result));
	}
}
