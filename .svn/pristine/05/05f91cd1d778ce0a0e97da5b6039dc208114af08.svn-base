package com.gamejelly.gong2.gas.proxy;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

import com.gamejelly.gong2.gas.annotation.AutoGasProxy;
import com.gamejelly.gong2.gas.entity.AvatarEntity;
import com.gamejelly.gong2.utils.GongCommonNotify;
import com.gamejelly.gong2.utils.GongConstants;
import com.gamejelly.gong2.utils.LoggerHelper;
import com.gamejelly.gong2.utils.SecurityUtils;
import com.hadoit.game.engine.core.rpc.base.annotation.Rpc;
import com.hadoit.game.engine.core.rpc.simple.context.RpcResult;
import com.hadoit.game.engine.core.rpc.simple.context.ServerChannelContext;
import com.hadoit.game.engine.core.script.MmScriptEngine;
import com.hadoit.game.engine.guardian.gas.GasContext;
import com.hadoit.game.engine.guardian.gas.GasManager;
import com.hadoit.game.engine.guardian.gas.proxy.GasProxy;

@AutoGasProxy
@Component("gmProxy")
public class GmProxy implements GasProxy, BeanFactoryAware {

	private GasContext context;

	private BeanFactory beanFactory;

	private MmScriptEngine scriptEngine;

	private Object scriptProcess;

	@Override
	public void setContext(GasContext context) {
		this.context = context;
		scriptEngine = new MmScriptEngine();
		scriptEngine.setBossExecutor(((GasManager) context).getMainExecutor());
		scriptEngine.addValue("$context", context);
		scriptEngine.addValue("$beanFactory", beanFactory);
		scriptEngine.init();
		runScript();
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	private void runScript() {
		scriptProcess = scriptEngine.exec("gas/script/gm/gm.js");
	}

	@Rpc(fullAlias = "gm.list")
	public void listCommands(final ServerChannelContext channelContext, final RpcResult rpcResult,
			final String filter) {
		AvatarEntity owner = SecurityUtils.getOwner(context, channelContext);
		if (owner == null) {
			GongCommonNotify.closeForNotLogin(channelContext);
			return;
		}
		if (owner.getAvatarModel().getUserGroup() < GongConstants.USER_GROUP_GM) {
			rpcResult.error();
			return;
		}
		LoggerHelper.info("gm.list", owner.getAvatarModel(), filter);

		scriptEngine.dispatch(scriptProcess, "handleGmList", rpcResult, owner, filter);
	}

	@Rpc(fullAlias = "gm.reset")
	public void reloadCommand(final ServerChannelContext channelContext, final RpcResult rpcResult) {
		AvatarEntity owner = SecurityUtils.getOwner(context, channelContext);
		if (owner == null) {
			GongCommonNotify.closeForNotLogin(channelContext);
			return;
		}
		if (owner.getAvatarModel().getUserGroup() < GongConstants.USER_GROUP_GOD) {
			rpcResult.error();
			return;
		}
		LoggerHelper.info("gm.reset", owner.getAvatarModel());

		runScript();
		rpcResult.result("GM指令reload");
	}

	@Rpc(fullAlias = "gm.call")
	public void invokeCommand(final ServerChannelContext channelContext, final RpcResult rpcResult, String op,
			String[] params) {
		AvatarEntity owner = SecurityUtils.getOwner(context, channelContext);
		if (owner == null) {
			GongCommonNotify.closeForNotLogin(channelContext);
			return;
		}
		if (owner.getAvatarModel().getUserGroup() < GongConstants.USER_GROUP_GM) {
			rpcResult.error();
			return;
		}
		Object[] args = ArrayUtils.add(params, 0, op);
		LoggerHelper.info("gm.call", owner.getAvatarModel(), args);

		args = ArrayUtils.addAll(new Object[] { rpcResult, owner }, args);
		scriptEngine.dispatch(scriptProcess, "handleGm", args);
	}

}
