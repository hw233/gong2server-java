package com.gamejelly.gong2.utils;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.gamejelly.gong2.gas.entity.AvatarEntity;
import com.hadoit.game.common.lang.codec.EncryptUtils;
import com.hadoit.game.engine.core.rpc.simple.context.ServerChannelContext;
import com.hadoit.game.engine.guardian.gas.GasContext;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;

/**
 * 安全相关
 * 
 * @author bezy 2013-11-22
 * 
 */
public class SecurityUtils {
	private static final String ATTR_ACCOUNT_NAME = "$accountName";
	private static final String ATTR_TICKET = "$ticket";
	private static final Map<String, ServerChannelContext> channelContexMap = new ConcurrentHashMap<String, ServerChannelContext>();
	private static final Map<String, ServerChannelContext> internalChannelContexMap = new ConcurrentHashMap<String, ServerChannelContext>();

	public static boolean checkTicket(final String accountSecurityKey, String ticket, String accountName,
			long validTime) {
		if (accountSecurityKey == null || StringUtils.isEmpty(ticket) || StringUtils.isEmpty(accountName)) {
			return false;
		}
		try {
			String content = EncryptUtils.base64edDecryptDESedeString(ticket, accountSecurityKey);
			String[] tokens = StringUtils.split(content, "|");
			int len = ArrayUtils.getLength(tokens);
			String ticketAccountName = len > 0 ? tokens[0] : null;
			String ticketTimeStr = len > 1 ? tokens[1] : "0";
			if (!accountName.equalsIgnoreCase(ticketAccountName)) {
				return false;
			}
			if (validTime > 0) {
				long ticketCreateTime = Long.parseLong(ticketTimeStr);
				if (ticketCreateTime + validTime < System.currentTimeMillis()) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			GuardianLogger.error(e, "decrypt ticket failed!");
		}
		return false;
	}

	public static ServerChannelContext login(final ServerChannelContext channelContext, String accountName,
			String ticket) {
		channelContext.putAttribute(ATTR_ACCOUNT_NAME, accountName);
		channelContext.putAttribute(ATTR_TICKET, ticket);
		ServerChannelContext oldChannelContext = channelContexMap.put(accountName.toLowerCase(), channelContext);
		if (oldChannelContext != null && oldChannelContext != channelContext) {
			GuardianLogger.info("remove old channel", oldChannelContext.getChannel().getId(), "for account",
					accountName);
			return oldChannelContext;
		}
		return null;
	}

	public static void logout(final ServerChannelContext channelContext) {
		String accountName = (String) channelContext.removeAttribute(ATTR_ACCOUNT_NAME);
		if (accountName != null) {
			if (channelContexMap.get(accountName.toLowerCase()) == channelContext) {
				channelContexMap.remove(accountName.toLowerCase());
			}
		}
	}

	public static void updateAccountName(final ServerChannelContext channelContext, String accountName) {
		channelContext.putAttribute(ATTR_ACCOUNT_NAME, accountName);
	}

	public static String getLoginAccount(final ServerChannelContext channelContext) {
		return (String) channelContext.getAttribute(ATTR_ACCOUNT_NAME);
	}

	public static String getLoginTicket(final ServerChannelContext channelContext) {
		return (String) channelContext.getAttribute(ATTR_TICKET);
	}

	public static boolean isAccountLogined(final ServerChannelContext channelContext) {
		return getLoginAccount(channelContext) != null;
	}

	public static AvatarEntity getOwner(final GasContext context, final ServerChannelContext channelContext) {
		if (isAccountLogined(channelContext)) {
			return (AvatarEntity) context.getEntityManager().getEntity(channelContext);
		}
		return null;
	}

	public static Collection<ServerChannelContext> getChannelContexts() {
		return channelContexMap.values();
	}

	public static ServerChannelContext getChannelContext(String accountName) {
		return channelContexMap.get(accountName.toLowerCase());
	}

	public static String getClientHost(final ServerChannelContext channelContext) {
		String ip = null;
		SocketAddress sa = channelContext.getChannel().getRemoteAddress();
		if (sa instanceof InetSocketAddress) {
			ip = ((InetSocketAddress) sa).getAddress().getHostAddress();
		}
		return ip;
	}

	public static String getClientHost(String accountName) {
		ServerChannelContext channelContext = getChannelContext(accountName);
		if (channelContext != null) {
			return getClientHost(channelContext);
		}
		return null;
	}

	public static AvatarEntity getLoginAvatar(final GasContext context, String accountName) {
		ServerChannelContext channelContext = getChannelContext(accountName);
		if (channelContext != null) {
			return (AvatarEntity) context.getEntityManager().getEntity(channelContext);
		}
		return null;
	}

	public static AvatarEntity getLoginAvatarByAccountOrId(final GasContext context, String accountOrId) {
		if (accountOrId.contains("@") || !IdProvider.isAvatar(accountOrId)) {
			return getLoginAvatar(context, accountOrId);
		} else {
			return (AvatarEntity) context.getEntityManager().getEntity(accountOrId);
		}
	}

	public static String getLoginUrl(final ServerChannelContext channelContext) {
		return (String) channelContext.getAttribute("loginUrl");
	}

	public static void internalServerLogin(final ServerChannelContext channelContext, String identify) {
		ServerChannelContext oldChannelContext = internalChannelContexMap.put(identify, channelContext);
		if (oldChannelContext != null && oldChannelContext != channelContext) {
			GuardianLogger.info("remove old internal channel", oldChannelContext.getChannel().getId(), "for identify",
					identify);
		}
	}

	public static Collection<ServerChannelContext> getAllInternalServerChannel() {
		return internalChannelContexMap.values();
	}
}
