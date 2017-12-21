package com.gamejelly.game.gong2.login.service.sdk;

import java.util.Map;

public interface SdkService {
	/**
	 * 平台回调
	 * 
	 * @param requestParams
	 * @return
	 */
	public Object payCallback(Map<String, Object> requestParams);

	public Object addItemCallback(Map<String, Object> requestParams);

	public Object subItemCallback(Map<String, Object> requestParams);

	public Object getUserCallback(Map<String, Object> requestParams);

	public Object getXmfbDataCallback(Map<String, Object> requestParams);

	public Object getCcuCallback(Map<String, Object> requestParams);

	public Object getServersCallback(Map<String, Object> requestParams);

	/**
	 * 验证订单
	 * 
	 * @param requestParams
	 * @return
	 */
	public PlatformRetData verifyOrder(Map<String, Object> requestParams);

	/**
	 * 创建订单
	 * 
	 * @param requestParams
	 * @return
	 */
	public PlatformRetData createOrder(Map<String, Object> requestParams);

	/**
	 * 登录
	 * 
	 * @param requestParams
	 * @return
	 */
	public PlatformRetData loginAuth(Map<String, Object> requestParams);

	/**
	 * 拿所有未验证的订单, 然后验证并且给
	 * 
	 * @param requestParams
	 * @return
	 */
	public PlatformRetData verifyAllOrderWithCheck(Map<String, Object> requestParams);

	/**
	 * 创建账号
	 * 
	 * @param requestParams
	 * @return
	 */
	public void createAccount(Map<String, Object> requestParams, String name, String pass, String platform,
			String deviceName, String deviceVersion, String deviceId, String ip);

	/**
	 * 修改密码
	 * 
	 * @param requestParams
	 * @param name
	 * @param oldPass
	 * @param newPass
	 */
	public void resetPassword(Map<String, Object> requestParams, String name, String oldPass, String newPass);

	public boolean createAccountIfAbsent();

	public PlatformRetData handleSdkData(Map<String, Object> requestParams);
	
	public void processOrder(String orderId, String money, boolean yuan);
}
