package com.gamejelly.game.gong2.login.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import com.gamejelly.game.gong2.login.service.sdk.SdkGroup;
import com.gamejelly.game.gong2.login.service.sdk.SdkService;

@Component("sdkServiceGroups")
public class SdkServiceGroups implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	private Map<Integer, SdkService> serviceMap;

	@PostConstruct
	void init() {
		Map<String, Object> ags = applicationContext.getBeansWithAnnotation(SdkGroup.class);
		serviceMap = new HashMap<Integer, SdkService>(ags != null ? ags.size() : 0);
		if (ags != null) {
			for (Object ag : ags.values()) {
				Deprecated deprecated = AnnotationUtils.findAnnotation(ag.getClass(), Deprecated.class);
				if (deprecated != null) {
					continue;
				}
				SdkGroup group = AnnotationUtils.findAnnotation(ag.getClass(), SdkGroup.class);
				int[] ggs = group.value();
				if (ArrayUtils.isNotEmpty(ggs)) {
					for (int gg : ggs) {
						serviceMap.put(gg, (SdkService) ag);
					}
				}
			}
		}
	}

	public SdkService getService(Integer sdkId) {
		return serviceMap.get(sdkId);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
