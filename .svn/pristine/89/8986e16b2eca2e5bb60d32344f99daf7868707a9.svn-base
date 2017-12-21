package com.gamejelly.game.gong2.login.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.hadoit.game.engine.core.rpc.simple.SingleSimpleRpcClient;

public class RpcClientData {
	private SingleSimpleRpcClient simpleRpcClient;

	private String host;

	private int port;

	private int serverId;

	private String oprGroup;

	private List<String> oprMatches;

	public RpcClientData() {
	}

	public RpcClientData(SingleSimpleRpcClient simpleRpcClient, String host, int port) {
		this.simpleRpcClient = simpleRpcClient;
		this.host = host;
		this.port = port;
	}

	public SingleSimpleRpcClient getSimpleRpcClient() {
		return simpleRpcClient;
	}

	public void setSimpleRpcClient(SingleSimpleRpcClient simpleRpcClient) {
		this.simpleRpcClient = simpleRpcClient;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getOprGroup() {
		return oprGroup;
	}

	public void setOprGroup(String oprGroup) {
		this.oprGroup = oprGroup;
	}

	public List<String> getOprMatches() {
		return oprMatches;
	}

	public void setOprMatches(List<String> oprMatches) {
		this.oprMatches = oprMatches;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public void buildOprMatches(String oprMatchStr) {
		if (StringUtils.isBlank(oprMatchStr)) {
			return;
		}
		String[] aga = StringUtils.splitByWholeSeparator(oprMatchStr, ",");
		if (aga != null) {
			this.oprMatches = new ArrayList<String>(aga.length);
			for (String ag : aga) {
				this.oprMatches.add(StringUtils.trimToEmpty(ag));
			}
		}
	}

}
