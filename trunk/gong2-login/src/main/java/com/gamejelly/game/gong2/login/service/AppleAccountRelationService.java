package com.gamejelly.game.gong2.login.service;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gamejelly.game.gong2.login.dao.AppleAccountRelationDao;
import com.gamejelly.game.gong2.login.meta.AppleAccountRelation;
import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;

@Transactional
@Component("appleAccountRelationService")
public class AppleAccountRelationService {
	@Autowired
	private AppleAccountRelationDao appleAccountRelationDao;

	public AppleAccountRelation getAppleAccountRelationByGameAccount(String gameAccount) {
		return appleAccountRelationDao.getObjectByCondition("gameAccount = ? limit 1", gameAccount);
	}

	public boolean checkAndClaimReward(String gameAccount) {
		AppleAccountRelation obj = getAppleAccountRelationByGameAccount(gameAccount);
		if (obj == null) {
			return false;
		}
		if (obj.isClaimedReward()) {
			return false;
		}
		if (obj.getBindingTime() <= 0) {
			return false;
		}
		appleAccountRelationDao.updateColumnValue(new String[] { "claimedReward" }, new Object[] { true }, obj.getId());
		return true;
	}

	/**
	 * 进游戏主界面后拿到gamecenter信息后调用, 如果gamecenter未登陆就不调
	 * 
	 * @param gameCenterId
	 * @param deviceId
	 * @param accountName
	 * @return
	 */
	public PlatformRetData bindGameCenter(Map<String, Object> requestParams) {
		LoggerUtils.info("apple bindGameCenter requestParams=", requestParams);

		// G:1131362816
		String gameCenterId = FsGameLoginUtils.getMapString(requestParams, "gameCenterId", "");

		// martian.ma
		String gameCenterName = FsGameLoginUtils.getMapString(requestParams, "gameCenterName", "");
		gameCenterName = FsGameLoginUtils.trimUnicode(gameCenterName); //去掉unicode字符
		

		// 761FF2DE-042F-4FE6-80EA-836ACA6D4C32@ios
		String accountName = FsGameLoginUtils.getMapString(requestParams, "accountName", "");

		AppleAccountRelation rawAar = appleAccountRelationDao.getObjectByCondition("gameAccount = ? limit 1",
				accountName);
		if (rawAar == null) {
			// 非法操作
			return new PlatformRetData(false, -1);
		}

		AppleAccountRelation gcAar = appleAccountRelationDao.getObjectByCondition("gameCenterId=? limit 1",
				gameCenterId);
		if (gcAar != null) {
			if (!accountName.equals(gcAar.getGameAccount())) {
				// 绑了别的游戏账号, 要求切换账号
				return new PlatformRetData(true, 1);
			}
		} else {
			if (StringUtils.isEmpty(rawAar.getGameCenterId())) {
				// 如果设备没有绑定过
				// 绑定本机成功
				appleAccountRelationDao.updateColumnValue(new String[] { "gameCenterId", "gameCenterName",
						"bindingTime" }, new Object[] { gameCenterId, gameCenterName, System.currentTimeMillis() },
						rawAar.getId());

				return new PlatformRetData(true, 2);
			} else {
				// gameCenterName没有绑定账号，无法切换
				return new PlatformRetData(true, 3);
			}
		}
		// 正常登陆, 什么事情也没发生
		return new PlatformRetData(true, 0);
	}

	public String getOutNameFromDeviceAar(String opr, String deviceId) {
		AppleAccountRelation deviceAar = appleAccountRelationDao.getObjectByCondition("deviceId = ? limit 1", deviceId);
		if (deviceAar == null) {
			// 新建号
			deviceAar = new AppleAccountRelation();
			deviceAar.setCreateTime(System.currentTimeMillis());
			deviceAar.setDeviceId(deviceId);
			deviceAar.setGameAccount(FsGameLoginUtils.compAccountName(deviceId, opr));
			appleAccountRelationDao.addObject(deviceAar);
		}
		return deviceAar.getGameAccount();
	}

	public AppleAccountRelation getAppleAccountRelation(String gameCenterId) {
		return appleAccountRelationDao.getObjectByCondition("gameCenterId = ? limit 1", gameCenterId);
	}
	
	/**
	 * @param gameCenterId
	 * @param deviceId
	 * @param accountName
	 * @return
	 */
	public int unbindGameCenter(String accountName) {
		LoggerUtils.info("apple unbindGameCenter accountName=", accountName);
		// 761FF2DE-042F-4FE6-80EA-836ACA6D4C32@ios
		AppleAccountRelation rawAar = appleAccountRelationDao.getObjectByCondition("gameAccount = ? limit 1",
				accountName);
		if (rawAar == null) {
			// 非法操作
			return -1;
		}
		if (StringUtils.isBlank(rawAar.getGameCenterId())) {
			// 本来就没有绑定
			return -2;
		}
		appleAccountRelationDao.updateColumnValue(new String[] { "gameCenterId", "gameCenterName", "bindingTime" },
				new Object[] { "", "", 0l }, rawAar.getId());
		return 0;
	}
}
