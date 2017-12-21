package com.gamejelly.gong2.global.app;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gamejelly.gong2.global.app.annotation.AutoGlobalProxy;
import com.gamejelly.gong2.utils.FormulaHelper;
import com.hadoit.game.common.lang.DataUtils;
import com.hadoit.game.engine.guardian.global.GlobalManager;
import com.hadoit.game.engine.guardian.global.proxy.GlobalProxy;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;

public class GlobalApp {
	public static void main(String[] args) {
		PropertyConfigurator.configure(DataUtils.getResourceFromClassLoader("global/app/app-log4j.properties"));
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				new String[] { "classpath*:global/app/application-context-app*.xml" });
		ctx.registerShutdownHook();
		FormulaHelper.prepare();
		GlobalManager globalManager = ctx.getBean(GlobalManager.class);
		globalManager.init();
		Map<String, Object> beans = ctx.getBeansWithAnnotation(AutoGlobalProxy.class);
		for (Entry<String, Object> entry : beans.entrySet()) {
			GuardianLogger.info("register global proxy: " + entry.getKey());
			globalManager.registerGlobalProxy(entry.getKey(), (GlobalProxy) entry.getValue());
		}
		globalManager.start();
	}
}
