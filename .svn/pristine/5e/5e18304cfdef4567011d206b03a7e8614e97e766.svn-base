package com.gamejelly.game.gong2.login.utils;

import java.util.List;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.gamejelly.game.gong2.login.meta.ServerInfo;
import com.gamejelly.game.gong2.login.net.GasAdminClientManager;
import com.gamejelly.game.gong2.login.service.ServerInfoService;

public class NPSContextLoaderListener extends ContextLoaderListener {

	@Override
	public void contextInitialized(ServletContextEvent ctxEvent) {
		super.contextInitialized(ctxEvent);
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(ctxEvent
				.getServletContext());
		ServerInfoService serverInfoService = (ServerInfoService)wac.getBean("serverInfoService");
		GasAdminClientManager gasAdminClientManager = (GasAdminClientManager)wac.getBean("gasAdminClientManager");
		List<ServerInfo> allSrv = serverInfoService.getAllServer();
		gasAdminClientManager.onServerStart(allSrv);
	}

	@Override
	public void contextDestroyed(ServletContextEvent ctxEvent) {
		super.contextDestroyed(ctxEvent);
	}
}
