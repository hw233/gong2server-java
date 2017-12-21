package com.hadoit.game.engine.guardian.dbs.base;

import java.lang.reflect.Array;
import java.lang.reflect.Method;

import com.hadoit.game.engine.core.utils.method.MethodInvoationWrap;
import com.hadoit.game.engine.guardian.core.exception.GuardianServerException;

/**
 * @author bezy
 * 
 */
public class MethodCommandRpcService extends MethodInvoationWrap implements CommandRpcService {

	private Object context;
	private boolean unpack;

	public MethodCommandRpcService(String name, Method method, Object context, boolean unpack) {
		super(name, method);
		this.context = context;
		this.unpack = unpack;
		if (!RawCommandResult.class.isAssignableFrom(method.getReturnType())
				&& !Object[].class.isAssignableFrom(method.getReturnType())) {
			throw new GuardianServerException("method " + method.getName()
					+ " must return RawCommandResult or new Object[]{ret, num}");
		}
	}

	@Override
	public RawCommandResult onService(Object[] params) throws Exception {
		Object r;
		if (unpack) {
			r = invoke(context, params);
		} else {
			r = invoke(context, new Object[] { params });
		}
		if (r instanceof RawCommandResult) {
			return (RawCommandResult) r;
		} else if (r != null && Array.getLength(r) == 2) {
			Object num = Array.get(r, 1);
			if (num instanceof Integer) {
				return new RawCommandResult(Array.get(r, 0), (Integer) num);
			}
		}
		return null;
	}

}
