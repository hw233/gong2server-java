package com.gamejelly.gong2.gas.proxy;


import com.gamejelly.gong2.gas.annotation.AutoGasProxy;
import com.gamejelly.gong2.gas.annotation.TestInterface;
import com.gamejelly.gong2.gas.entity.AvatarEntity;
import com.gamejelly.gong2.gas.util.SpringContextHolder;
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
import com.hadoit.game.engine.guardian.utils.GuardianLogger;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

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
		if (op.equals("testInterface")) {
			testInterface(owner,rpcResult,params);
			return;
		}
		args = ArrayUtils.addAll(new Object[] { rpcResult, owner }, args);
		scriptEngine.dispatch(scriptProcess, "handleGm", args);
	}

	/**
	 * 调用接口测试
	 * @param owner
	 * @param rpcResult
	 * @param params
	 */
	private void testInterface(AvatarEntity owner, RpcResult rpcResult,String[] params) {
		ApplicationContext applicationContext = SpringContextHolder.getApplicationContext();
		Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(TestInterface.class);
		Collection<Object> values = beansWithAnnotation.values();
		if (CollectionUtils.isNotEmpty(values)) {
			GuardianLogger.info("testInterface @TestInterface classes", ArrayUtils.toString(values));
			if (params.length > 0) {
				String param = params[0];
				if (StringUtils.isEmpty(param)) {
					GuardianLogger.error("testInterface param is blank");
					rpcResult.result();
					GongCommonNotify.notifyMsg(owner.getChannelContext(), "参数为空,");
					return;
				}
				Integer paramInteger = Integer.valueOf(param);
				for (Object value : values) {
					Class<?> clazz = value.getClass();
					Method[] declaredMethods = clazz.getDeclaredMethods();
					for (Method declaredMethod : declaredMethods) {
						TestInterface annotation = declaredMethod.getAnnotation(TestInterface.class);
						if (annotation != null && annotation.value() == paramInteger) {
							try {
								Object[] remove = ArrayUtils.remove(params, 0);
								Object[] pre = {owner,rpcResult};
								Object[] allParams = ArrayUtils.addAll(pre, remove);
								Class<?>[] parameterTypes = declaredMethod.getParameterTypes();
								int methodParameterLength = parameterTypes.length;
								int allParamLength = allParams.length;
								int preLength = pre.length;
								if (allParamLength != methodParameterLength) {
									StringBuilder stringBuilder = new StringBuilder();
									stringBuilder.append("调用").append(clazz.getSimpleName()).append("的").append(declaredMethod.getName()).append("参数错误,");
									int sub = methodParameterLength - preLength;
									stringBuilder.append("需要传入").append(sub).append("个参数");
									if (sub>0) {
										stringBuilder.append(",类型为 ");
										for (int i = 0; i < methodParameterLength; i++) {
											if (i>=preLength) {
												stringBuilder.append(parameterTypes[i].getSimpleName()) ;
												if (i!=methodParameterLength-1) {
													stringBuilder.append(",\n");
												}
											}
										}
									}
									GuardianLogger.error("testInterface length not equals", allParamLength, methodParameterLength);
									GongCommonNotify.notifyMsg(owner.getChannelContext(), stringBuilder.toString());
									rpcResult.result();
									return;
								}
								if (allParamLength >2) {
									for (int i = 0; i < allParamLength; i++) {
										if (i>1) {
											Object convert = ConvertUtils.convert(allParams[i], parameterTypes[i]);
											allParams[i] = convert;
										}
									}
								}
								GuardianLogger.info("testInterface invoke method",declaredMethod.getName(),ArrayUtils.toString(allParams));
								GongCommonNotify.notifyMsg(owner.getChannelContext(), "调用"+clazz.getSimpleName()+"的"+declaredMethod.getName()+"方法😡");
								declaredMethod.invoke(value, allParams);
							} catch (IllegalAccessException | InvocationTargetException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}else{
				rpcResult.result();
			}
		} else {
			rpcResult.result();
		}
	}
}
