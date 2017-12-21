package com.gamejelly.gong2.gas.admin;

import javax.annotation.PreDestroy;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hadoit.game.engine.core.rpc.base.codec.factory.JsonCompatibleMessageCodecFactory;
import com.hadoit.game.engine.core.rpc.simple.SimpleRpcServer;
import com.hadoit.game.engine.core.rpc.simple.handler.DefaultSimpleRpcServerHandler;
import com.hadoit.game.engine.guardian.gas.GasContext;
import com.hadoit.game.engine.guardian.gas.GasManager;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;

/**
 * @author bezy 2014-2-26
 * 
 */
@Component("adminServer")
public class AdminServer {
	private SimpleRpcServer adminServer;

	@Autowired
	@Qualifier("adminProxy")
	private AdminProxy adminProxy;

	@Autowired
	@Value("${config.admin_listen_host}")
	private String listenHost;

	@Autowired
	@Value("${config.admin_listen_port}")
	private int listenPort;

	public void start(GasContext context) {
		if (adminServer != null) {
			return;
		}
		adminServer = new SimpleRpcServer("AdminServer");
		adminServer.setIdleTime(60);
		adminServer.setBigEndian(false);
		adminServer.setMessageCodecFactory(new JsonCompatibleMessageCodecFactory());
		adminServer.setHost(listenHost);
		adminServer.setPort(listenPort);
		adminServer.setMainExecutor(((GasManager) context).getMainExecutor());
		DefaultSimpleRpcServerHandler serverHandler = new DefaultSimpleRpcServerHandler();
		adminServer.setServerHandler(serverHandler);
		adminProxy.setContext(context);
		serverHandler.registerRpcHandler("adminProxy", adminProxy);
		adminServer.start();
		GuardianLogger.info("start admin server@" + (StringUtils.isNotBlank(listenHost) ? (listenHost + ":") : "")
				+ listenPort + "...");
	}

	@PreDestroy
	public void dispose() {
		if (adminServer != null) {
			adminServer.dispose();
			GuardianLogger.info("dispose admin server...");
		}
	}

}
