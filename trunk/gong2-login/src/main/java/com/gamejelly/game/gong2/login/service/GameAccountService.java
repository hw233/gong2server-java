package com.gamejelly.game.gong2.login.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.gamejelly.game.gong2.login.dao.GameAccountDao;
import com.gamejelly.game.gong2.login.dao.IdCardNameErrorDao;
import com.gamejelly.game.gong2.login.meta.GameAccount;
import com.gamejelly.game.gong2.login.meta.IdCardNameError;
import com.gamejelly.game.gong2.login.utils.FsGameLoginConst;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.hadoit.game.common.lang.Pair;
import com.hadoit.game.common.lang.codec.EncryptUtils;

@Transactional
@Component("gameAccountService")
public class GameAccountService {

	@Value("${config.account_security_key}")
	private String accountSecurityKey;

	@Autowired
	private GameAccountDao gameAccountDao;

	@Autowired
	private IdCardNameErrorDao idCardNameErrorDao;

	public GameAccount getGameAccountByAccount(String accountName) {
		return gameAccountDao.getGameAccountByAccount(accountName);
	}

	public GameAccount getGameAccountByAccountForUpdate(String accountName) {
		return gameAccountDao.getGameAccountByAccountForUpdate(accountName);
	}

	public void updateGameAccount(GameAccount gameAccount) {
		gameAccountDao.updateObject(gameAccount);
	}

	public String createTicket(String accountName, long createTime, String salt) {
		if (StringUtils.isEmpty(accountName)) {
			return null;
		}
		String input = accountName + "|" + createTime + "|" + StringUtils.defaultString(salt);
		try {
			return EncryptUtils.base64edEncryptDESede(input, accountSecurityKey);
		} catch (Exception e) {
			LoggerUtils.error(e, "create ticket failed!");
		}
		return null;
	}

	private int checkTicket(String ticket, String accountName, long createTime, long validTime, String salt) {
		if (StringUtils.isEmpty(ticket) || StringUtils.isEmpty(accountName)) {
			return FsGameLoginConst.ERROR_REGIST_NAME_OR_PASS_ILLEGAL;
		}
		try {
			String content = EncryptUtils.base64edDecryptDESedeString(ticket, accountSecurityKey);
			String[] tokens = StringUtils.split(content, "|");
			int len = ArrayUtils.getLength(tokens);
			String ticketAccountName = len > 0 ? tokens[0] : null;
			String ticketTimeStr = len > 1 ? tokens[1] : "0";
			String ticketSalt = len > 2 ? tokens[2] : "";
			if (!accountName.equalsIgnoreCase(ticketAccountName)) {
				return FsGameLoginConst.ERROR_REGIST_NAME_OR_PASS_ILLEGAL;
			}
			if (!StringUtils.defaultString(salt).equals(ticketSalt)) {
				return FsGameLoginConst.ERROR_REGIST_NAME_OR_PASS_ILLEGAL;
			}
			long ticketCreateTime = Long.parseLong(ticketTimeStr);
			if (createTime > 0) {
				if (ticketCreateTime < createTime) {
					return FsGameLoginConst.ERROR_REGIST_TICKET_EXPIRE;
				}
			}
			if (validTime > 0) {
				if (ticketCreateTime + validTime < System.currentTimeMillis()) {
					return FsGameLoginConst.ERROR_REGIST_TICKET_EXPIRE;
				}
			}
			return FsGameLoginConst.CODE_OK;
		} catch (Exception e) {
			LoggerUtils.error(e, "decrypt ticket failed!");
		}
		return FsGameLoginConst.ERROR_REGIST_NAME_OR_PASS_ILLEGAL;
	}

	public Pair<Integer, String> createAccount(String accountName, String pass, String platform, String deviceName,
			String deviceVersion, String deviceId, String ip) {
		// 客户端只做md5
		// 服务端收到后再salt md5一下保存
		GameAccount account = getGameAccountByAccount(accountName);
		if (account != null) {
			return Pair.newInstance(FsGameLoginConst.ERROR_REGIST_NAME_EXISTS, null);
		}
		account = new GameAccount();
		account.setAccount(accountName);
		String salt = EncryptUtils.genRandomSalt(8);
		account.setSalt(salt);
		account.setPass(DigestUtils.md5Hex(pass + salt));
		long ticketCreateTime = System.currentTimeMillis();
		account.setCreateTime(ticketCreateTime);
		account.setTicketCreateTime(ticketCreateTime);
		account.setPlatform(platform);
		account.setDeviceName(deviceName);
		account.setDeviceVersion(deviceVersion);
		account.setDeviceId(deviceId);
		account.setIp(ip);
		gameAccountDao.addObject(account);
		String ticket = createTicket(accountName, ticketCreateTime, salt);
		int retCode = FsGameLoginConst.CODE_OK;
		if (ticket == null) {
			retCode = FsGameLoginConst.ERROR_SYSTEM;
		}
		return Pair.newInstance(retCode, ticket);
	}

	private static List<Integer> mergeLastLoginServerId(List<Integer> lastLoginServerIds, int serverId) {
		ArrayList<Integer> ret = new ArrayList<Integer>();
		if (CollectionUtils.isEmpty(lastLoginServerIds)) {
			ret.add(serverId);
			return ret;
		}
		Iterator<Integer> llServ = lastLoginServerIds.iterator();
		while (llServ.hasNext()) {
			if (llServ.next().equals(serverId)) {
				llServ.remove();
			}
		}
		lastLoginServerIds.remove((Integer) serverId);

		ret.addAll(lastLoginServerIds);
		ret.add(0, serverId);
		if (ret.size() > 3) {
			int len = ret.size();
			for (int i = 3; i < len; i++) {
				ret.remove(i);
			}
		}
		return ret;
	}

	public int checkPassword(String accountName, String pass, String group) {
		if (!FsGameLoginUtils.isCompedName(accountName, group)) {
			accountName = FsGameLoginUtils.compAccountName(accountName, group);
		}
		GameAccount account = gameAccountDao.getGameAccountByAccount(accountName);
		if (account == null) {
			return FsGameLoginConst.ERROR_REGIST_NAME_OR_PASS_ILLEGAL;
		}
		if (!account.getPass().equals(DigestUtils.md5Hex(pass + account.getSalt()))) {
			// 密码不正确
			return FsGameLoginConst.ERROR_REGIST_NAME_OR_PASS_ILLEGAL;
		}
		return FsGameLoginConst.CODE_OK;
	}

	public Pair<Integer, String> reqTicketWithCreate(String accountName, boolean createIfAbsent, String platform,
			String deviceName, String deviceVersion, String deviceId, String ip) {
		GameAccount account = gameAccountDao.getGameAccountByAccount(accountName);
		if (account == null) {
			if (createIfAbsent) {
				return createAccount(accountName, FsGameLoginConst.ACCOUNT_PASS_DEFAULT, platform, deviceName,
						deviceVersion, deviceId, ip);
			} else {
				return Pair.newInstance(FsGameLoginConst.ERROR_SYSTEM, (String) null);
			}
		}
		long ticketCreateTime = System.currentTimeMillis();
		account.setTicketCreateTime(ticketCreateTime);
		gameAccountDao.updateObject(account);
		String ticket = createTicket(accountName, ticketCreateTime, account.getSalt());
		int retCode = FsGameLoginConst.CODE_OK;
		if (ticket == null) {
			retCode = FsGameLoginConst.ERROR_SYSTEM;
		}
		return Pair.newInstance(retCode, ticket);
	}

	public int checkTicket(String accountName, String ticket) {
		GameAccount account = gameAccountDao.getGameAccountByAccountForUpdate(accountName);
		if (account == null) {
			return FsGameLoginConst.ERROR_REGIST_NAME_OR_PASS_ILLEGAL;
		}
		return checkTicket(ticket, accountName, account.getTicketCreateTime(), 0, account.getSalt());
	}

	public Pair<Integer, String> loginByTicket(String accountName, String ticket, int serverId, String platform,
			String deviceName, String deviceVersion, String deviceId, String ip, String opr) {
		GameAccount account = gameAccountDao.getGameAccountByAccountForUpdate(accountName);
		if (account == null) {
			return Pair.newInstance(FsGameLoginConst.ERROR_REGIST_NAME_OR_PASS_ILLEGAL, null);
		}
		int code = checkTicket(ticket, accountName, account.getTicketCreateTime(), 0, account.getSalt());
		if (code == FsGameLoginConst.ERROR_REGIST_NAME_OR_PASS_ILLEGAL) {
			return Pair.newInstance(FsGameLoginConst.ERROR_REGIST_NAME_OR_PASS_ILLEGAL, null);
		}
		String newTicket;
		if (code == FsGameLoginConst.ERROR_REGIST_TICKET_EXPIRE) {
			long ticketCreateTime = System.currentTimeMillis();
			account.setTicketCreateTime(ticketCreateTime);
			newTicket = createTicket(accountName, ticketCreateTime, account.getSalt());
		} else {
			newTicket = ticket;
		}
		account.setPlatform(platform);
		account.setDeviceName(deviceName);
		account.setDeviceVersion(deviceVersion);
		account.setDeviceId(deviceId);
		account.setIp(ip);
		account.setLastLoginTime(System.currentTimeMillis());
		account.setLastLoginServerIds(mergeLastLoginServerId(account.getLastLoginServerIds(), serverId));

		if (StringUtils.isNotEmpty(opr)) {
			String lastLoginOpr = account.getLastLoginOpr();
			if (!StringUtils.equals(lastLoginOpr, opr)) {
				account.setLastLoginOpr(opr);
			}
		}

		gameAccountDao.updateObject(account);
		int retCode = FsGameLoginConst.CODE_OK;
		if (newTicket == null) {
			retCode = FsGameLoginConst.ERROR_SYSTEM;
		}
		return Pair.newInstance(retCode, newTicket);
	}

	public Pair<Integer, String> resetPass(String accountName, String oldPass, String newPass) {
		GameAccount account = gameAccountDao.getGameAccountByAccountForUpdate(accountName);
		if (account == null) {
			return Pair.newInstance(FsGameLoginConst.ERROR_REGIST_NAME_OR_PASS_ILLEGAL, null);
		}
		if (!account.getPass().equals(DigestUtils.md5Hex(oldPass + account.getSalt()))) {
			// 密码不正确
			return Pair.newInstance(FsGameLoginConst.ERROR_REGIST_NAME_OR_PASS_ILLEGAL, null);
		}
		String salt = EncryptUtils.genRandomSalt(8);
		account.setSalt(salt);
		account.setPass(DigestUtils.md5Hex(newPass + salt));
		long ticketCreateTime = System.currentTimeMillis();
		account.setTicketCreateTime(ticketCreateTime);
		gameAccountDao.updateObject(account);

		String ticket = createTicket(accountName, ticketCreateTime, salt);
		int retCode = FsGameLoginConst.CODE_OK;
		if (ticket == null) {
			retCode = FsGameLoginConst.ERROR_SYSTEM;
		}
		return Pair.newInstance(retCode, ticket);
	}

	public GameAccount getGameAccountByPhone(String phone) {
		return gameAccountDao.getGameAccountByPhone(phone);
	}

	public GameAccount getGameAccountByIdCardNo(String idCardNo) {
		return gameAccountDao.getGameAccountByIdCardNo(idCardNo);
	}

	public int unbindUserPhone(String accountName) {
		LoggerUtils.info("GameAccount unbindUserPhone accountName=", accountName);
		GameAccount account = gameAccountDao.getObjectByCondition("account = ? limit 1", accountName);
		if (account == null) {
			// 非法操作
			return -1;
		}
		if (StringUtils.isBlank(account.getPhone())) {
			// 本来就没有绑定
			return -2;
		}
		gameAccountDao.updateColumnValue(new String[] { "phone" }, new Object[] { "" }, account.getId());
		return 0;
	}

	public void addIdCardNameError(IdCardNameError model) {
		idCardNameErrorDao.addObject(model);
	}

	public IdCardNameError getIdCardNameError(String name, String idCardNo) {
		return idCardNameErrorDao.getIdCardNameError(name, idCardNo);
	}
}
