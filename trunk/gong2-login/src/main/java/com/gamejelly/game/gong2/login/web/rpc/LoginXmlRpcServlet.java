package com.gamejelly.game.gong2.login.web.rpc;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import redstone.xmlrpc.XmlRpcServlet;

import com.gamejelly.game.gong2.login.service.LoginXmlRpcService;
import com.hadoit.game.common.framework.utils.SimpleWebUtils;
import com.hadoit.game.common.framework.web.security.IPSecurityFailedException;
import com.hadoit.game.common.framework.web.security.IPSecurityInterceptor;

public class LoginXmlRpcServlet extends XmlRpcServlet {

	private static final long serialVersionUID = 1L;

	private LoginXmlRpcService loginXmlRpcService;

	private IPSecurityInterceptor ipSecurityInterceptor;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(config
				.getServletContext());
		loginXmlRpcService = (LoginXmlRpcService) wac.getBean("loginXmlRpcService");
		ipSecurityInterceptor = (IPSecurityInterceptor) wac.getBean("ipSecurityInterceptor");
		getXmlRpcServer().addInvocationHandler("loginXmlRpcService", loginXmlRpcService);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String ip = SimpleWebUtils.getRequestIp(req);
		if (!ipSecurityInterceptor.validateIp(ip)) {
			throw new IPSecurityFailedException("security check failed by IPSecurityInterceptor with ip: "
					+ StringUtils.defaultString(ip), ip);
		}
		super.doPost(req, res);
	}

}
