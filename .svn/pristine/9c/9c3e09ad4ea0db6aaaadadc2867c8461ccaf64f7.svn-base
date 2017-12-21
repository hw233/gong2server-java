package com.gamejelly.gong2.config;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.gamejelly.gong2.config.data.MissionChufaData;
import com.gamejelly.gong2.config.data.MissionTargetData;
import com.gamejelly.gong2.config.data.base.LList;
import com.gamejelly.gong2.config.data.base.LMap;
import com.hadoit.game.common.framework.serialize.json.JsonSerialize;

public class MissionConfig {

	private final static String METHOD_EXPORTER_TEMPLATE = "this['$$AN'] = "
			+ "function() { var method = o['$$MN']; var args = []; for (var i = 0; i < arguments.length; ++i) { args.push(js2JavaObject(arguments[i])); } return method.apply(o, args); }; $$LineSep";

	private static String buildMethod(String mName, Object... params) {
		if (StringUtils.isBlank(mName)) {
			return "";
		}
		String paramStr = "";
		if (params == null) {
			return mName + "(entity)";
		}

		for (Object o : params) {
			if (o == null) {
				continue;
			}
			String pp = o.toString();
			int ctIdx = pp.indexOf("$context$");
			if (ctIdx >= 0) {
				pp = "c[" + pp.substring(ctIdx + "$context$".length()) + "]";
			}else{
				pp = JsonSerialize.getDefault().toJson(o);
			}
			paramStr += "," + pp;
		}

		if (StringUtils.isNotBlank(paramStr)) {
			paramStr = paramStr.substring(1);
		}

		return mName + "(entity, " + paramStr + ")";
	}

	private static String buildConditionScript(LMap mcData) {
		LList con = mcData.getList("conExpression");

		if (CollectionUtils.isEmpty(con)) {
			return "true";
		}

		StringBuilder ret = new StringBuilder();
		for (int i = 0; i < con.size(); i++) {
			int idx = i + 1;
			String m1 = buildMethod(mcData.getString("condition" + idx), mcData.get("condition" + idx + "Arg1"),
					mcData.get("condition" + idx + "Arg2"), mcData.get("condition" + idx + "Arg3"));
			if (!con.getBool(i, false)) {
				ret.append("!");
			}
			ret.append(m1);
			if (i < con.size() - 1) {
				ret.append(" && ");
			}
		}
		return ret.toString();
	}

	private static String buildActionScript(LMap mcData) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 3; i++) {
			int idx = i + 1;
			String mName = mcData.getString("action" + idx);
			if (StringUtils.isNotBlank(mName)) {
				String am1 = buildMethod(mName, mcData.get("action" + idx + "Arg1"),
						mcData.get("action" + idx + "Arg2"), mcData.get("action" + idx + "Arg3"));

				sb.append(am1 + "; ");
			}
		}
		return sb.toString();
	}

	public static String compileTriggerConScript(LMap mcData) {
		return buildConditionScript(mcData);
	}

	public static String compileTriggerActionScript(LMap mcData) {
		String ret = buildActionScript(mcData);

		// replace line seperator
		ret = StringUtils.replace(ret, "$$LineSep", IOUtils.LINE_SEPARATOR);

		return ret;
	}

	public static String compileTargetConScript(LMap mcData) {
		String con = buildConditionScript(mcData);

		// replace line seperator
		con = StringUtils.replace(con, "$$LineSep", IOUtils.LINE_SEPARATOR);

		return con;
	}

	public static String compileMethodExportScriptLine(String name, String methodName) {
		String ret = StringUtils.replace(METHOD_EXPORTER_TEMPLATE, "$$AN", name);
		ret = StringUtils.replace(ret, "$$MN", methodName);
		ret = StringUtils.replace(ret, "$$LineSep", IOUtils.LINE_SEPARATOR);
		return ret;
	}

	public static void main(String[] args) {
		for (Object o : MissionChufaData.data.values()) {
			System.out.println(compileTriggerConScript((LMap) o));
		}

		for (Object o : MissionChufaData.data.values()) {
			System.out.println(compileTriggerActionScript((LMap) o));
		}

		for (Object o : MissionTargetData.data.values()) {
			System.out.println(compileTargetConScript((LMap) o));
		}
	}
}
