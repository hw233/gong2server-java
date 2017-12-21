package com.gamejelly.game.gong2.login.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gamejelly.game.gong2.login.dao.ServerInfoDao;
import com.gamejelly.game.gong2.login.meta.GameAccount;
import com.gamejelly.game.gong2.login.meta.ServerInfo;
import com.gamejelly.game.gong2.login.net.GasAdminClientManager;
import com.gamejelly.game.gong2.login.utils.FsGameLoginConst;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.hadoit.game.common.framework.utils.IPRange;

@Transactional
@Component("serverInfoService")
public class ServerInfoService {

	@Autowired
	private ServerInfoDao serverInfoDao;

	@Autowired
	private GameAccountService gameAccountService;

	@Autowired
	private GasAdminClientManager gasAdminClientManager;

	@Autowired
	@Value("${config.list_server_visible_ips}")
	private String listServerVisibleIps;

	@Autowired
	@Value("${config.bad_update_res_version}")
	private String badUpdateResVersion;

	@Autowired
	@Value("${config.in_review_opr}")
	private String inReviewOPR;

	@Autowired
	@Value("${config.in_review_client_version}")
	private String inReviewClientVersion;

	@Autowired
	@Value("${config.third_pay_oprGroup_list}")
	private String thirdPayOprGroudList;

	private List<String> thirdPayList;

	private List<IPRange> rangeList;

	private List<String> badResVersionList;

	private Map<String, String> mapInReviewClientVersion;
	
	@Autowired
	//注意这里的表达式里面的冒号, 可以设置默认值, 参考 http://colobu.com/2015/09/09/set-null-for-a-string-property-by-Value/
	@Value("${config.opr_relation_list:}")
	private String oprRelationListStr;
	
	private List<List<String>> oprRelationList;

	@PostConstruct
	void init() {
		rangeList = new ArrayList<IPRange>();
		String[] allowIpList = StringUtils.splitByWholeSeparator(listServerVisibleIps, ",");
		if (ArrayUtils.isNotEmpty(allowIpList)) {
			IPRange range;
			for (String allowIp : allowIpList) {
				range = new IPRange(allowIp);
				if (range.isValid()) {
					rangeList.add(range);
				}
			}
		}

		badResVersionList = new ArrayList<String>();
		String[] badList = StringUtils.splitByWholeSeparator(badUpdateResVersion, ",");
		if (ArrayUtils.isNotEmpty(badList)) {
			for (String badVersion : badList) {
				badResVersionList.add(StringUtils.trim(badVersion));
			}
		}

		// 正在审核的包的配置
		List<String> inReviewOPRList = new ArrayList<String>();
		String[] inReviewOPRListTemp = StringUtils.splitByWholeSeparator(inReviewOPR, ",");
		if (ArrayUtils.isNotEmpty(inReviewOPRListTemp)) {
			for (String opr : inReviewOPRListTemp) {
				inReviewOPRList.add(StringUtils.trim(opr));
			}
		}

		List<String> inReviewClientVersionList = new ArrayList<String>();
		String[] inReviewClientVersionListTemp = StringUtils.splitByWholeSeparator(inReviewClientVersion, ",");
		if (ArrayUtils.isNotEmpty(inReviewClientVersionListTemp)) {
			for (String version : inReviewClientVersionListTemp) {
				inReviewClientVersionList.add(StringUtils.trim(version));
			}
		}

		mapInReviewClientVersion = new HashMap<String, String>();
		if (CollectionUtils.isNotEmpty(inReviewOPRList) && CollectionUtils.isNotEmpty(inReviewClientVersionList)
				&& (inReviewOPRList.size() == inReviewClientVersionList.size())) {
			for (int i = 0; i < inReviewOPRList.size(); i++) {
				String opr = inReviewOPRList.get(i);
				String clientVersion = inReviewClientVersionList.get(i);
				mapInReviewClientVersion.put(opr, clientVersion);
			}
		}

		thirdPayList = new ArrayList<String>();
		String[] allowThirdPayList = StringUtils.splitByWholeSeparator(thirdPayOprGroudList, ",");

		if (ArrayUtils.isNotEmpty(allowThirdPayList)) {
			for (String allowOpr : allowThirdPayList) {
				if (StringUtils.isNotEmpty(allowOpr)) {
					thirdPayList.add(allowOpr);
					LoggerUtils.info("thirdPayList ", allowOpr);
				}
			}
		}

		oprRelationList = new ArrayList<List<String>>();
		String[] strList1 = StringUtils.splitByWholeSeparator(oprRelationListStr, ";");
		for(String str1 : strList1){
			String[] strList2 = StringUtils.splitByWholeSeparator(str1, ",");
			List<String> list2 = Arrays.asList(strList2);
			oprRelationList.add(list2);
		}
		LoggerUtils.info("oprRelationList ", oprRelationList);
	}

	public List<ServerInfo> getServerListByGroup(String group, String sourceIp, boolean needFilter) {
		if (StringUtils.isBlank(group)) {
			return new ArrayList<ServerInfo>();
		}
		List<Integer> srvIds = gasAdminClientManager.getSrvIdsByOpr(group);
		List<ServerInfo> siList = serverInfoDao.getServerInfoList(srvIds);
		if (needFilter) {
			siList = filterServerInfoByIp(siList, sourceIp);
		}
		return siList;
	}

	public ServerInfo getServerInfoByServerId(int serverId) {
		return serverInfoDao.getObjectByCondition("serverId=?", serverId);
	}

	public ServerInfo getServerInfo(long id) {
		return serverInfoDao.getServerInfo(id);
	}

	public ServerInfo getServerInfoForUpdate(long id) {
		return serverInfoDao.getForUpdateById(id);
	}

	public List<ServerInfo> getLastServerList(String name, String oprGroup, String sourceIp, boolean needFilter) {
		List<ServerInfo> lastSrvList = new ArrayList<ServerInfo>();
		if (StringUtils.isBlank(name)) {
			return lastSrvList;
		}
		GameAccount gameAccount = gameAccountService.getGameAccountByAccount(name);
		if (gameAccount != null) {
			List<Integer> lastLoginSrvIds = gameAccount.getLastLoginServerIds();
			if (CollectionUtils.isNotEmpty(lastLoginSrvIds)) {
				LinkedHashSet<Integer> noSameSet = new LinkedHashSet<Integer>(lastLoginSrvIds);
				for (int srvId : noSameSet) {
					ServerInfo srvInfo = getServerInfoByServerId(srvId);
					if (srvInfo != null
							&& gasAdminClientManager.getSimpleRpcClient(srvInfo.getServerId(), srvInfo.getOprGroup())
									.getOprMatches().contains(oprGroup)) {
						lastSrvList.add(srvInfo);
					}
				}
			}
		}
		if (needFilter) {
			lastSrvList = filterServerInfoByIp(lastSrvList, sourceIp);
		}
		return lastSrvList;
	}

	public int getAllServerCount() {
		return serverInfoDao.getAllServerCount();
	}

	public List<ServerInfo> getAllServer(int limit, int offset) {
		return serverInfoDao.getAllServer(limit, offset);
	}

	public List<ServerInfo> getAllServer() {
		return serverInfoDao.getAll();
	}

	public ServerInfo getServerBy(int serverId, String oprGroup) {
		return serverInfoDao.getServerBy(serverId, oprGroup);
	}

	public boolean addServer(ServerInfo serverInfo) {
		ServerInfo si = getServerBy(serverInfo.getServerId(), serverInfo.getOprGroup());
		if (si != null) {
			return false;
		}
		serverInfoDao.addServer(serverInfo);
		return true;
	}

	public void updateServerState(int serverId, String oprGroup, int state) {
		serverInfoDao.updateServerState(serverId, oprGroup, state);
	}

	public void deleteServerAndRemoveConnect(int serverId, String oprGroup) {
		serverInfoDao.deleteServerBy(serverId, oprGroup);
		gasAdminClientManager.removeConnect(serverId, oprGroup);
	}

	public ServerInfo deleteServer(long id) {
		ServerInfo si = serverInfoDao.getById(id);
		serverInfoDao.deleteById(id);
		return si;
	}

	public void updateServer(ServerInfo serverInfo) {
		serverInfoDao.updateObject(serverInfo);
	}

	public List<ServerInfo> getServerByOprGroup(String oprGroup) {
		return serverInfoDao.getServerByOprGroup(oprGroup);
	}

	private boolean validateIp(String ip) {
		if (StringUtils.isNotBlank(ip) && rangeList != null) {
			for (IPRange range : rangeList) {
				if (range.isInRange(ip)) {
					return true;
				}
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public List<ServerInfo> filterServerInfoByIp(List<ServerInfo> siList, final String sourceIp) {
		if (CollectionUtils.isEmpty(siList)) {
			return siList;
		}
		return (List<ServerInfo>) CollectionUtils.select(siList, new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				ServerInfo si = (ServerInfo) object;
				if (si.getVisibleType() == FsGameLoginConst.SERVER_VISIBLE_TYPE_PART_CAN) {
					return validateIp(sourceIp);
				} else if (si.getVisibleType() == FsGameLoginConst.SERVER_VISIBLE_TYPE_ALL_CANT) {
					return false;
				} else {
					return true;
				}
			}
		});
	}

	public List<String> getBadResVersionList() {
		return badResVersionList;
	}

	public void setBadResVersionList(List<String> badResVersionList) {
		this.badResVersionList = badResVersionList;
	}

	public Map<String, String> getMapInReviewClientVersion() {
		return mapInReviewClientVersion;
	}

	public void setMapInReviewClientVersion(Map<String, String> mapInReviewClientVersion) {
		this.mapInReviewClientVersion = mapInReviewClientVersion;
	}

	public boolean curOprThirdPayState(String opr) {
		if (StringUtils.isNotBlank(opr) && thirdPayList != null) {
			if (thirdPayList.contains(opr)) {
				return true;
			}
		}
		return false;
	}
	
	private int doGetOprIdx(String opr) {
		int oprIdx = -1;
		for (int i = 0; i < oprRelationList.size(); i++) {
			List<String> or = oprRelationList.get(i);
			if (or.contains(opr)) {
				oprIdx = i;
			}
		}
		return oprIdx;
	}
	
	public boolean checkAccountAndOpr(String account, String opr) {
		if (CollectionUtils.isEmpty(oprRelationList)) {
			return true;
		}
		String accountOpr = FsGameLoginUtils.decompAccountNameToOpr(account);
		int oprIdx = doGetOprIdx(opr);
		int accountOprIdx = doGetOprIdx(accountOpr);
		if (oprIdx == accountOprIdx) {
			return true;
		}
		return false;
	}
	
	public static void test1(ServerInfoService s, String account, String opr){
		System.out.println("checkAccountAndOpr "+"账号="+account+ ", opr="+opr+ ", 可否登录="+s.checkAccountAndOpr(account, opr));
	}
	
	public static void main(String[] args) {
		ServerInfoService s = new ServerInfoService();
		s.oprRelationListStr="ios4533;ioschangba;iosyuwan";
		s.init();
		System.out.println(s.oprRelationList);
		test1(s, "xxxx@ios", "ios");
		test1(s, "xxxx@ios", "ios2");
		test1(s, "xxxx@ios", "ios4533");
		test1(s, "xxxx@iosyuwan", "ioschangba");
		test1(s, "xxxx@pp", "ioschangba");
		test1(s, "xxxx@pp", "ios2");
		test1(s, "xxxx@ios", "xx");
	}
	
	public boolean isShehe(String clientOpr,String clientVersion){
		if (getMapInReviewClientVersion().containsKey(clientOpr)
				&& getMapInReviewClientVersion().get(clientOpr).equals(clientVersion)) {
			return true;
		}
		return false;
	}

}
