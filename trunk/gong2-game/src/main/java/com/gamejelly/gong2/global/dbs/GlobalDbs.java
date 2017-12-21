package com.gamejelly.gong2.global.dbs;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gamejelly.gong2.global.dbs.annotation.AutoGlobalDbsCommandProxy;
import com.gamejelly.gong2.utils.FormulaHelper;
import com.hadoit.game.common.lang.DataUtils;
import com.hadoit.game.engine.guardian.dbs.DbsManager;
import com.hadoit.game.engine.guardian.dbs.proxy.DbsCommandProxy;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;

public class GlobalDbs {
	public static void main(String[] args) {
		PropertyConfigurator.configure(DataUtils.getResourceFromClassLoader("global/dbs/dbs-log4j.properties"));
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				new String[] { "classpath*:global/dbs/application-context-dbs*.xml" });
		ctx.registerShutdownHook();
		FormulaHelper.prepare();
		DbsManager dbsManager = ctx.getBean(DbsManager.class);
		dbsManager.init();
		Map<String, Object> beans = ctx.getBeansWithAnnotation(AutoGlobalDbsCommandProxy.class);
		for (Entry<String, Object> entry : beans.entrySet()) {
			GuardianLogger.info("register global dbs command proxy: " + entry.getKey());
			dbsManager.registerDbsCommandProxy(entry.getKey(), (DbsCommandProxy) entry.getValue());
		}
		dbsManager.start();
	}
}
