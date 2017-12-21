package com.gamejelly.gong2.gas.cross;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;

import com.gamejelly.gong2.gas.cross.annotation.AutoCrossListener;
import com.hadoit.game.engine.guardian.gas.GasManager;
import com.hadoit.game.engine.guardian.global.client.GlobalClient;
import com.hadoit.game.engine.guardian.global.client.listener.GlobalListener;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;

@Component("crossServer")
public class CrossServer implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Autowired
	@Qualifier("globalClient")
	private GlobalClient globalClient;

	@Autowired
	@Qualifier("gasManager")
	private GasManager gasManager;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public void init() {
		globalClient.setMainExecutor(gasManager.getMainExecutor());
		globalClient.init();
		Map<String, Object> beans = ((AbstractApplicationContext) applicationContext)
				.getBeansWithAnnotation(AutoCrossListener.class);
		for (Entry<String, Object> entry : beans.entrySet()) {
			GuardianLogger.info("register cross listener: " + entry.getKey());
			globalClient.registerGlobalListener(entry.getKey(), (GlobalListener) entry.getValue());
		}
		globalClient.connect();
	}

}
