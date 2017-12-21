package com.gamejelly.gong2.gas.proxy;

import com.gamejelly.gong2.dbs.service.DbsService;
import com.gamejelly.gong2.gas.admin.AdminServer;
import com.gamejelly.gong2.gas.admin.service.ServerConfigService;
import com.gamejelly.gong2.gas.entity.SharedEntity;
import com.gamejelly.gong2.gas.service.SystemCycleService;
import com.gamejelly.gong2.gas.service.shared.SharedDataService;
import com.gamejelly.gong2.utils.*;
import com.hadoit.game.common.framework.http.HttpResponseCallback;
import com.hadoit.game.common.framework.http.SimpleHttpRequest;
import com.hadoit.game.engine.core.executor.TimerListener;
import com.hadoit.game.engine.guardian.gas.GasContext;
import com.hadoit.game.engine.guardian.gas.entity.EntityCallback;
import com.hadoit.game.engine.guardian.gas.entity.RawCommandCallback;
import com.hadoit.game.engine.guardian.gas.proxy.GasServerProxy;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author bezy 2013-11-25
 * 
 */
@Component("gasServerProxy")
public class GasServerProxyImpl implements GasServerProxy {
	private GasContext context;

	@Autowired
	@Value("${config.version_update}")
	private boolean versionUpdate;

	@Autowired
	@Qualifier("adminServer")
	private AdminServer adminServer;

	@Autowired
	@Qualifier("serverConfigService")
	private ServerConfigService serverConfigService;

	@Autowired
	@Qualifier("systemCycleService")
	private SystemCycleService systemCycleService;

	@Autowired
	@Qualifier("sharedDataService")
	private SharedDataService sharedDataService;


	@Override
	public void setContext(GasContext context) {
		this.context = context;
	}

	@Override
	public void onRegistered() {
		Map<String, String> versionUrlMap = GongUtils.loadVersionUrlMap();
		if (MapUtils.isNotEmpty(versionUrlMap)) {
			for (Map.Entry<String, String> me : versionUrlMap.entrySet()) {
				final String opr = me.getKey();
				final String url = me.getValue();
				if (StringUtils.isBlank(url)) {
					continue;
				}
				final String versionUrl = url + "?" + System.currentTimeMillis();
				try {
					LoggerHelper.infoParams("record version from:", versionUrl);
					String versionJson = SimpleHttpRequest.createGet(versionUrl).sendGetString();
					// 记录版本号
					recordVersion(opr, versionJson);
				} catch (Exception e) {
					GuardianLogger.error(e, "record version exception from:" + versionUrl);
				}
				// 周期性记录版本号
				if (versionUpdate) {
					context.addTimer(new TimerListener() {
						@Override
						public void onTimer(long id, Object[] params) throws Exception {
							final String versionUrl = url + "?" + System.currentTimeMillis();
							try {
								SimpleHttpRequest.createGet(versionUrl)
										.sendGetString(new HttpResponseCallback<String>() {
											@Override
											public void callback(int code, String result, Exception e) {
												if (code >= 200 && code < 300) {
													recordVersion(opr, result);
												} else {
													GuardianLogger.error("update version error from:" + versionUrl);
												}
											}
										});
							} catch (Exception e) {
								GuardianLogger.error(e, "update version exception from:" + versionUrl);
							}
						}
					}, 60, 60);// 每分钟检查一次版本号
				}
			}
		}

		// 记录在线人数 5分钟一次
		logOnlineCount();

		context.executeRawCommand(GongDbConstants.CMD_SELECT_SHARE_MODEL_ID, new Object[] { DbsService.SHARED_MODEL_ID }, new RawCommandCallback() {
			@Override
			public void onResult(Object result, int num, String error) {
				// 读取共享数据
				context.getEntityManager().loadEntityFromDb(SharedEntity.class, (Long)result,
						new EntityCallback<SharedEntity>() {
							@Override
							public void onResult(boolean r, SharedEntity entity) {
								if (r) {
									sharedDataService.setSharedEntity(entity);
								}
							}
						});
			}
		});




		LoggerHelper.infoParams("GasServerProxy onRegistered", "start admin server!");
		adminServer.start(context);
	}

	private void recordVersion(String opr, String versionJson) {
		LoggerHelper.infoParams("GasServerProxy record", "opr:", opr, "versionJson:", versionJson);
		serverConfigService.addVersionData(opr, versionJson);
	}

	@Override
	public void onUnregistered() {

	}

	private void logOnlineCount() {
		float cycle = 5 * 60;
		context.addTimer(new TimerListener() {
			@Override
			public void onTimer(long id, Object[] params) throws Exception {
				GongLogger.logOnline(SecurityUtils.getChannelContexts().size());
			}
		}, cycle, cycle);
	}
}
