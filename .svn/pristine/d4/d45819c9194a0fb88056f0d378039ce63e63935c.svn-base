package com.gamejelly.game.gong2.login.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gamejelly.game.gong2.login.dao.SystemDataDao;
import com.gamejelly.game.gong2.login.meta.SystemData;
import com.gamejelly.game.gong2.login.utils.FsGameLoginConst;

@Transactional
@Component("systemDataService")
public class SystemDataService {

	@Autowired
	private SystemDataDao systemDataDao;

	@PostConstruct
	void init() {
		SystemData sd = systemDataDao.getObjectByCondition("id=?", FsGameLoginConst.REGISTER_DEFAULT_ID);
		if (sd == null) {
			sd = new SystemData();
			sd.setId(FsGameLoginConst.REGISTER_DEFAULT_ID);
			sd.setOpenRegist(true);
			systemDataDao.addObject(sd);
		}
	}

	public boolean isOpenRegist() {
		SystemData sd = systemDataDao.getObjectByCondition("id=?", FsGameLoginConst.REGISTER_DEFAULT_ID);
		if (sd == null) {
			return true;
		}

		return sd.isOpenRegist();
	}

	public void setOpenRegist(boolean value) {
		SystemData sd = systemDataDao.getObjectByCondition("id=?", FsGameLoginConst.REGISTER_DEFAULT_ID);
		if (sd == null) {
			return;
		}

		sd.setOpenRegist(value);
		systemDataDao.updateObject(sd);
	}

	public void setOpenShiming(boolean value) {
		SystemData sd = systemDataDao.getObjectByCondition("id=?", FsGameLoginConst.REGISTER_DEFAULT_ID);
		if (sd == null) {
			return;
		}

		sd.setOpenShiming(value);
		systemDataDao.updateObject(sd);
	}

	public void setOpenShimingShow(boolean value) {
		SystemData sd = systemDataDao.getObjectByCondition("id=?", FsGameLoginConst.REGISTER_DEFAULT_ID);
		if (sd == null) {
			return;
		}

		sd.setOpenShimingShow(value);
		systemDataDao.updateObject(sd);
	}

	public boolean isOpenShiMing() {
		SystemData sd = systemDataDao.getObjectByCondition("id=?", FsGameLoginConst.REGISTER_DEFAULT_ID);
		if (sd == null) {
			return true;
		}

		return sd.isOpenShiming();
	}

	public boolean isOpenShiMingShow() {
		SystemData sd = systemDataDao.getObjectByCondition("id=?", FsGameLoginConst.REGISTER_DEFAULT_ID);
		if (sd == null) {
			return true;
		}

		return sd.isOpenShimingShow();
	}

}
