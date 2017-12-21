package com.gamejelly.gong2.dbs.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.gamejelly.gong2.dbs.dao.LogDao;
import com.gamejelly.gong2.meta.log.BaseLog;
import com.gamejelly.gong2.meta.log.BehaviorLog;
import com.gamejelly.gong2.meta.log.CriticalLog;
import com.gamejelly.gong2.meta.log.CurrencyChangeLog;
import com.gamejelly.gong2.meta.log.ExpChangeLog;
import com.gamejelly.gong2.meta.log.GoldChangeLog;
import com.gamejelly.gong2.meta.log.GoldRechargeLog;
import com.gamejelly.gong2.meta.log.GoldRemainLog;
import com.gamejelly.gong2.meta.log.ItemChangeLog;
import com.gamejelly.gong2.meta.log.LoginLog;
import com.gamejelly.gong2.meta.log.LogoutLog;
import com.gamejelly.gong2.meta.log.MoneyChangeLog;
import com.gamejelly.gong2.meta.log.OnlineLog;
import com.gamejelly.gong2.utils.LoggerHelper;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;

@Component("logService")
public class LogService {

	private ExecutorService logExecutor;

	@Value("${config.dbs_log_threads}")
	private int logThreads;

	@Autowired
	private LogDao logDao;

	@PostConstruct
	void init() {
		logExecutor = Executors.newFixedThreadPool(logThreads);

		logDao.registTableMeta(ExpChangeLog.class);
		logDao.registTableMeta(GoldChangeLog.class);
		logDao.registTableMeta(GoldRechargeLog.class);
		logDao.registTableMeta(ItemChangeLog.class);
		logDao.registTableMeta(LoginLog.class);
		logDao.registTableMeta(LogoutLog.class);
		logDao.registTableMeta(MoneyChangeLog.class);
		logDao.registTableMeta(OnlineLog.class);
		logDao.registTableMeta(CurrencyChangeLog.class);
		logDao.registTableMeta(BehaviorLog.class);
		logDao.registTableMeta(CriticalLog.class);
		logDao.registTableMeta(GoldRemainLog.class);
	}

	public void dbLog(final BaseLog baseLog) {
		if (logExecutor instanceof ThreadPoolExecutor) {
			int remainingLogSize = ((ThreadPoolExecutor) logExecutor).getQueue().size();
			GuardianLogger.info("dbLog remainingLogSize=", remainingLogSize);
		}
		logExecutor.execute(new Runnable() {
			@Override
			public void run() {
				long startTime = System.currentTimeMillis();
				logDao.addObject(baseLog);
				LoggerHelper.slowTimeLocal("dbLog", startTime);
			}
		});
	}

	public void awaitTermination(long seconds) throws InterruptedException {
		logExecutor.shutdown();
		logExecutor.awaitTermination(seconds, TimeUnit.SECONDS);
	}

}
