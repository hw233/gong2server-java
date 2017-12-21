package com.gamejelly.gong2.utils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.script.Bindings;
import javax.script.CompiledScript;
import javax.script.ScriptException;

import org.apache.commons.lang.StringUtils;

import com.hadoit.game.common.lang.ReflectionUtils;
import com.hadoit.game.engine.core.script.ScriptEngineCore;
import com.hadoit.game.engine.core.script.ScriptEngineRuntimeException;

/**
 * 简单公式运算，没有缓存
 * 
 * @author bezy 2012-10-25
 * 
 */
public class FormulaUtils {
	private static final ScriptEngineCore core = new ScriptEngineCore();
	private static final String COMPATIBLE_PREFIX = "var math;if(!math){math = {}};(function() {var keys = ['abs', 'acos', 'asin', 'atan', 'atan2', 'ceil', 'cos', 'exp', 'floor', 'log', 'max', 'min', 'pow', 'random', 'sin', 'sqrt', 'tan'];for(i in keys){math[keys[i]] = Math[keys[i]];}})(); \n"
			+ "var utils; if(!utils){utils = {}};";
	private static final String UTILS_SCRIPT_FORMATTER = "utils.%s = function(%s) { return %s.%s(%s) } \n";
	static {
		core.init();
		String compPreFix = COMPATIBLE_PREFIX + javaToJsExportUtils();
		core.evalScriptText(compPreFix);
	}

	private static String javaToJsExportUtils() {
		Method[] methods = GongUtils.class.getMethods();
		String className = GongUtils.class.getName();
		JsExportMethod jsExportMethod;
		StringBuilder methodExpSb = new StringBuilder();
		for (Method method : methods) {
			jsExportMethod = ReflectionUtils.findAnnotation(method, JsExportMethod.class);
			if (jsExportMethod != null && StringUtils.isNotBlank(jsExportMethod.value())) {
				int paramLen = method.getParameterTypes().length;
				StringBuilder paramStr = new StringBuilder();
				String param = "";
				String jsName = jsExportMethod.value().trim();
				String methodName = method.getName();
				for (int i = 0; i < paramLen; i++) {
					paramStr.append(", v").append(i);
				}
				if (paramLen > 0) {
					param = paramStr.substring(1);
				}
				methodExpSb.append(String.format(UTILS_SCRIPT_FORMATTER, jsName, param, className, methodName, param));
			}
		}
		return methodExpSb.toString();
	}

	private static Bindings createSandbox() {
		Bindings sandbox = core.createBindings();
		sandbox.put("math", core.getBindings().get("math"));
		sandbox.put("utils", core.getBindings().get("utils"));
		return sandbox;
	}

	public static CompiledScript compile(String expression) {
		return core.compileScriptText(expression);
	}

	public static Object eval(CompiledScript script, Map<String, Object> variables) {
		Bindings sandbox = createSandbox();
		if (variables != null) {
			sandbox.putAll(variables);
		}
		try {
			return script.eval(sandbox);
		} catch (ScriptException se) {
			throw new ScriptEngineRuntimeException(se);
		}
	}

	public static Object eval(String expression, Map<String, Object> variables) {
		Bindings sandbox = createSandbox();
		if (variables != null) {
			sandbox.putAll(variables);
		}
		return core.evalScriptText(expression, sandbox);
	}

	public static Object evalValue(CompiledScript script, Object value) {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("v", value);
		return eval(script, variables);
	}

	public static Object evalValue(String expression, Object value) {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("v", value);
		return eval(expression, variables);
	}

	public static void main(String[] args) {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("v", new Object[] { "测试", new int[] { 1, 2 }, "abc" });
		FormulaUtils.eval("println(java2JsObject(v));println(math.random())", variables);
		FormulaUtils.eval("println(java2JsObject(v));println(math.random())", variables);
		FormulaUtils.eval("println(utils.between(1,10))", variables);
		FormulaUtils.eval("println(utils.between(10.778,11.77))", variables);
		FormulaUtils.eval("println(utils.between(100,10000))", variables);
	}

}
