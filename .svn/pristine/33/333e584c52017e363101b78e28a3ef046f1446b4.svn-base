package com.gamejelly.gong2.gas;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gamejelly.gong2.gas.annotation.AutoGasProxy;
import com.gamejelly.gong2.gas.cross.CrossServer;
import com.gamejelly.gong2.utils.FormulaHelper;
import com.gamejelly.gong2.utils.GongConstants;
import com.gamejelly.gong2.utils.GongUtils;
import com.hadoit.game.common.lang.DataUtils;
import com.hadoit.game.engine.guardian.gas.GasManager;
import com.hadoit.game.engine.guardian.gas.proxy.GasProxy;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;

public class GasApp {

	public static void main(String[] args) {
		try {
			PropertyConfigurator.configure(DataUtils.getResourceFromClassLoader("gas/gas-log4j.properties"));
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
					new String[] { "classpath*:gas/application-context-gas*.xml" });
			ctx.registerShutdownHook();
			FormulaHelper.prepare();
			GasManager gasManager = ctx.getBean(GasManager.class);
			gasManager.init();
			Map<String, Object> beans = ctx.getBeansWithAnnotation(AutoGasProxy.class);
			for (Entry<String, Object> entry : beans.entrySet()) {
				GuardianLogger.info("register gas proxy: " + entry.getKey());
				gasManager.registerGasProxy(entry.getKey(), (GasProxy) entry.getValue());
			}
			GongUtils.setBeanFactory(ctx);
			gasManager.start();

			if (GongConstants.isOpenCross()) {
				CrossServer crossServer = ctx.getBean(CrossServer.class);
				crossServer.init();
			}
		} catch (Throwable e) {
			GuardianLogger.error(e);
		}

	}

}
