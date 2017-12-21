package com.gamejelly.gong2.utils;

import org.apache.commons.lang.StringUtils;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;

import com.gamejelly.gong2.config.MessageConfig;
import com.hadoit.game.engine.core.rpc.simple.context.RpcResult;
import com.hadoit.game.engine.core.rpc.simple.context.ServerChannelContext;

/**
 * 通用 notify
 * 
 * @author bezy 2013-11-22
 * 
 */
public class GongCommonNotify {
	public static ChannelFuture notify(final ServerChannelContext channelContext, String op, Object... params) {
		return channelContext.notify(op, params);
	}

	public static void notifyError(final ServerChannelContext channelContext, RpcResult rpcResult, String op,
			Object... params) {
		rpcResult.error();
		channelContext.notify(op, params);
	}

	public static void notifyMsg(final ServerChannelContext channelContext, String msg) {
		notify(channelContext, GongRpcConstants.RES_COMMON_MSG, msg);
	}

	public static void notifyMsg(final ServerChannelContext channelContext, int msgId, Object... params) {
		notify(channelContext, GongRpcConstants.RES_COMMON_MSG, MessageConfig.getCommonMsg(msgId, params));
	}

	public static void notifyErrorMsg(final ServerChannelContext channelContext, RpcResult rpcResult, int msgId,
			Object... params) {
		rpcResult.error();
		notify(channelContext, GongRpcConstants.RES_COMMON_MSG, MessageConfig.getCommonMsg(msgId, params));
	}

	public static void notifyServerRefuse(final ServerChannelContext channelContext) {
		notify(channelContext, GongRpcConstants.RES_COMMON_SERVER_REFUSE).addListener(ChannelFutureListener.CLOSE);
	}

	public static void notifyServerBusy(final ServerChannelContext channelContext) {
		notify(channelContext, GongRpcConstants.RES_COMMON_SERVER_BUSY).addListener(ChannelFutureListener.CLOSE);
	}

	public static void notifyReady(final ServerChannelContext channelContext) {
		notify(channelContext, GongRpcConstants.RES_COMMON_READY);
	}

	public static void close(final ServerChannelContext channelContext) {
		if (!channelContext.getChannel().isConnected()) {
			return;
		}
		notify(channelContext, GongRpcConstants.RES_COMMON_CLOSE).addListener(ChannelFutureListener.CLOSE);
	}

	public static void closeForNotLogin(final ServerChannelContext channelContext) {
		notify(channelContext, GongRpcConstants.RES_COMMON_NOT_LOGIN).addListener(ChannelFutureListener.CLOSE);
	}

	public static void closeForVersionError(final ServerChannelContext channelContext) {
		notify(channelContext, GongRpcConstants.RES_COMMON_VERSION_ERROR).addListener(ChannelFutureListener.CLOSE);
	}

	public static void closeForLoginError(final ServerChannelContext channelContext) {
		notify(channelContext, GongRpcConstants.RES_COMMON_LOGIN_ERROR).addListener(ChannelFutureListener.CLOSE);
	}

	public static void closeForServerForbidden(final ServerChannelContext channelContext) {
		notify(channelContext, GongRpcConstants.RES_COMMON_SERVER_FORBIDDEN).addListener(ChannelFutureListener.CLOSE);
	}

	public static void notifyHotfixMd5(final ServerChannelContext channelContext) {
		if (StringUtils.isNotBlank(GongUtils.HOTFIX_MD5)) {
			notify(channelContext, GongRpcConstants.RES_COMMON_HOTFIX_MD5, GongUtils.HOTFIX_MD5);
		}
	}

	public static void notifyHotfixRun(final ServerChannelContext channelContext) {
		if (StringUtils.isNotBlank(GongUtils.HOTFIX_PATCH)) {
			notify(channelContext, GongRpcConstants.RES_COMMON_HOTFIX_RUN, GongUtils.HOTFIX_PATCH,
					GongUtils.HOTFIX_MD5);
		}
	}
}
