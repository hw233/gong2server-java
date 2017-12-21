package com.gamejelly.game.gong2.login.net;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.hadoit.game.common.lang.Pair;
import com.hadoit.game.engine.core.rpc.base.codec.factory.JsonCompatibleMessageCodecFactory;
import com.hadoit.game.engine.core.rpc.simple.SingleSimpleRpcClient;
import com.hadoit.game.engine.core.rpc.simple.handler.DefaultSimpleRpcClientHandler;
import com.gamejelly.game.gong2.login.meta.ServerInfo;
import com.gamejelly.game.gong2.login.service.ServerInfoService;
import com.gamejelly.game.gong2.login.utils.FsGameLoginConst;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;

public class GasAdminClientManager implements BeanFactoryAware {
	/**
	 * 重连时间间隔
	 */
	private static long RETRY_DELAY_TIEM = 10000;

	private static short SN_SCOPE_LOGIN = 0x0003;

	private BeanFactory beanFactory;

	private List<String> notConnectHostList;

	@Autowired
	private ServerInfoService serverInfoService;

	// opr+serverId -> RpcClientData
	private ConcurrentHashMap<String, RpcClientData> gasClientMap = new ConcurrentHashMap<String, RpcClientData>();

	// ip+port -> SingleSimpleRpcClient
	private ConcurrentHashMap<String, SingleSimpleRpcClient> connectorMap = new ConcurrentHashMap<String, SingleSimpleRpcClient>();

	// 创建和删除连接的时候需要锁
	private final Lock lock = new ReentrantLock();

	private ExecutorService disposeExecutor = Executors.newFixedThreadPool(5);

	private SingleSimpleRpcClient fetchOrCreateRpcClient(String host, int port) {
		String connectorId = genConnectorId(host, port);
		String clientName = "GasClient_" + connectorId;
		if (connectorMap.containsKey(connectorId)) {
			return connectorMap.get(connectorId);
		}
		SingleSimpleRpcClient rpcClient = new SingleSimpleRpcClient(clientName);
		rpcClient.setBigEndian(false);
		rpcClient.setIdleTime(60);
		rpcClient.setMessageCodecFactory(new JsonCompatibleMessageCodecFactory());
		rpcClient.setScope(SN_SCOPE_LOGIN);
		rpcClient.setClientHandler(new DefaultSimpleRpcClientHandler());
		rpcClient.setAutoReconnect(true);
		rpcClient.setAutoReconnectRetryTime(RETRY_DELAY_TIEM);
		((DefaultSimpleRpcClientHandler) rpcClient.getClientHandler()).registerRpcHandler("gasListener",
				(GasAdminListener) beanFactory.getBean("gasAdminListener", host, port));
		connectorMap.put(connectorId, rpcClient);
		return rpcClient;
	}

	private boolean containsSimpleRpcClient(SingleSimpleRpcClient ssrc) {
		for (RpcClientData rcd : gasClientMap.values()) {
			if (rcd.getSimpleRpcClient() == ssrc) {
				return true;
			}
		}
		return false;
	}

	private void handleConnectors() {
		List<String> disposeKeys = new ArrayList<String>();
		for (final Map.Entry<String, SingleSimpleRpcClient> me : connectorMap.entrySet()) {
			LoggerUtils.info("handleConnectors key=" + me.getKey());
			if (containsSimpleRpcClient(me.getValue())) {
				LoggerUtils.info("handleConnectors2 matched!");
				Pair<String, Integer> p = decompConnectorId(me.getKey());
				// 被使用中, 连一下, 这里的连接会判断一下不会重复连接
				me.getValue().singleConnect(p.getFirst(), p.getSecond());
			} else {
				LoggerUtils.info("handleConnectors2 dispose!");
				// 没有被使用
				disposeExecutor.execute(new Runnable() {
					// 开线程销毁, 这里dispose会很慢
					@Override
					public void run() {
						me.getValue().dispose();
					}
				});
				disposeKeys.add(me.getKey());
			}
		}
		for (String connectorId : disposeKeys) {
			connectorMap.remove(connectorId);
		}
	}

	public void onServerStart(List<ServerInfo> allSrv) {
		if (CollectionUtils.isEmpty(allSrv)) {
			return;
		}
		for (ServerInfo si : allSrv) {
			connectGasClient(si);
		}
	}

	public void connectGasClient(ServerInfo si) {
		String host = si.getAdminIp();
		int port = si.getAdminPort();
		if (CollectionUtils.isNotEmpty(notConnectHostList) && notConnectHostList.contains(host)) {
			return;
		}
		if (StringUtils.isBlank(host) || port <= 0) {
			return;
		}

		lock.lock();
		String rpcClientId = genRpcClientId(si.getServerId(), si.getOprGroup());
		try {
			RpcClientData rpcClientData = gasClientMap.get(rpcClientId);
			if (rpcClientData == null) {
				// 增加新服
				rpcClientData = new RpcClientData(fetchOrCreateRpcClient(host, port), host, port);
				gasClientMap.put(rpcClientId, rpcClientData);
			} else {
				// 修改老的服务器配置
				rpcClientData.setSimpleRpcClient(fetchOrCreateRpcClient(host, port));
				rpcClientData.setHost(host);
				rpcClientData.setPort(port);
			}
			rpcClientData.buildOprMatches(si.getOprMatchStr());
			rpcClientData.setServerId(si.getServerId());
			rpcClientData.setOprGroup(si.getOprGroup());
			handleConnectors();
		} finally {
			lock.unlock();
		}

		if (gasClientMap.get(rpcClientId).getSimpleRpcClient().isConnected()) {
			serverInfoService.updateServerState(si.getServerId(), si.getOprGroup(), FsGameLoginConst.SERVER_STATE_OPEN);
		} else {
			serverInfoService
					.updateServerState(si.getServerId(), si.getOprGroup(), FsGameLoginConst.SERVER_STATE_CLOSE);
		}
	}

	public void removeConnect(int serverId, String oprGroup) {
		lock.lock();
		try {
			RpcClientData rpcClientData = gasClientMap.remove(genRpcClientId(serverId, oprGroup));
			if (rpcClientData == null) {
				return;
			}
			handleConnectors();
		} finally {
			lock.unlock();
		}
	}

	public List<Integer> getSrvIdsByOpr(String opr) {
		List<Integer> ret = new ArrayList<Integer>();
		for (Map.Entry<String, RpcClientData> rcd : gasClientMap.entrySet()) {
			if (rcd.getValue().getOprMatches().contains(opr)) {
				ret.add(rcd.getValue().getServerId());
			}
		}
		return ret;
	}

	public RpcClientData getSimpleRpcClient(int serverId, String oprGroup) {
		return gasClientMap.get(genRpcClientId(serverId, oprGroup));
	}

	public RpcClientData getSimpleRpcClientFromSubOpr(int serverId, String subOpr) {
		if (gasClientMap == null) {
			return null;
		}
		for (RpcClientData rc : gasClientMap.values()) {
			if (rc.getServerId() == serverId && rc.getOprMatches().contains(subOpr)) {
				return rc;
			}
		}
		return null;
	}

	private String genRpcClientId(int serverId, String oprGroup) {
		return oprGroup + "_" + serverId;
	}

	private String genConnectorId(String host, int port) {
		return host + "_" + port;
	}

	private Pair<String, Integer> decompConnectorId(String connectorId) {
		String[] sa = StringUtils.splitByWholeSeparator(connectorId, "_");
		return Pair.newInstance(sa[0], Integer.valueOf(sa[1]));
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	public void dispose() {
		LoggerUtils.info("dispose mananger!");
	}

	public List<String> getNotConnectHostList() {
		return notConnectHostList;
	}

	public void setNotConnectHostList(List<String> notConnectHostList) {
		this.notConnectHostList = notConnectHostList;
	}

	public List<Pair<Integer, String>> getOprGroupAndServerIdListBy(String host, int port) {
		List<Pair<Integer, String>> ret = new ArrayList<Pair<Integer, String>>();
		for (RpcClientData rcd : gasClientMap.values()) {
			if (rcd.getHost().equals(host) && rcd.getPort() == port) {
				ret.add(Pair.newInstance(rcd.getServerId(), rcd.getOprGroup()));
			}
		}
		return ret;
	}

}
