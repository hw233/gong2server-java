package com.gamejelly.game.gong2.login.service.sdk;

import java.util.Map;

public class PlatformRetData {
	private boolean success;

	private Object ptData;
	
	private String payLoad;

	private Map<String, Object> otherData;

	public PlatformRetData() {
	}

	public PlatformRetData(boolean success, Object ptData) {
		this.success = success;
		this.ptData = ptData;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getPtData() {
		return ptData;
	}

	public void setPtData(Object ptData) {
		this.ptData = ptData;
	}

	public Map<String, Object> getOtherData() {
		return otherData;
	}

	public void setOtherData(Map<String, Object> otherData) {
		this.otherData = otherData;
	}

	public String getPayLoad() {
		return payLoad;
	}

	public void setPayLoad(String payLoad) {
		this.payLoad = payLoad;
	}

}
