package com.gamejelly.gong2.utils.aot.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.gamejelly.gong2.config.data.SFormulaData;
import com.gamejelly.gong2.config.data.base.LList;
import com.gamejelly.gong2.config.data.base.LMap;
import com.gamejelly.gong2.utils.aot.tools.ast.JSErrorReporter;
import com.gamejelly.gong2.utils.aot.tools.ast.JSNodeVisitor;
import com.gamejelly.gong2.utils.aot.tools.ast.JSSymbolVisitor;
import com.hadoit.game.common.lang.DataUtils;

import sun.org.mozilla.javascript.internal.CompilerEnvirons;
import sun.org.mozilla.javascript.internal.IRFactory;
import sun.org.mozilla.javascript.internal.ast.AstRoot;

public class SFormulaGen {
	private static final String GEN_PACKAGE = "com.gamejelly.gong2.utils.aot";
	static String sFormulaExecutorName = "SFormulaExecutor";

	final static String commonFuncStr = "" + "	static class math{\n"
			+ "		static double min(Number a, Number b){\n"
			+ "			return Math.min(a.doubleValue(), b.doubleValue());\n" + "		}\n" + "		\n"
			+ "		static double rint(Number a){\n"
			+ "			return Math.rint(a.doubleValue());\n" + "		}\n" + "		\n"
			+ "		static double max(Number a, Number b){\n"
			+ "			return Math.max(a.doubleValue(), b.doubleValue());\n" + "		}\n" + "		\n"
			+ "		static double floor(Number a){\n" + "			return Math.floor(a.doubleValue());\n" + "		}\n"
			+ "		\n" + "		static double random(){\n" + "			return Math.random();\n" + "		}\n"
			+ "	}\n" + "	\n" + "	static int parseInt(Number a) {\n" + "		return a.intValue();\n" + "	}\n"
			+ "	static BaseFightProperty bfp(Map<String, Object> params, String p){\n" +
			// " BaseFightProperty r = (BaseFightProperty)params.get(p);\n"+
			// " if(r instanceof ServantEntity){\n"+
			// " r.lv = ((ServantEntity)r).getLv();\n"+
			// " }else if(r instanceof MonsterEntity){\n"+
			// " r.lv = ((MonsterEntity)r).getLv();\n"+
			// " }\n"+
			"		return (BaseFightProperty)params.get(p);\n" + "	}\n" + "	\n"
			+ "	static double d(Map<String, Object> params, String p){\n"
			+ "		return Double.valueOf(params.get(p).toString());\n" + "	}			\n";

	static Map<String, String> allFunc = new HashMap<String, String>();

	static void parseJS(String code, Set<String> vList) {
		CompilerEnvirons env = new CompilerEnvirons();
		env.setRecoverFromErrors(true);

		IRFactory factory = new IRFactory(env, new JSErrorReporter());
		AstRoot rootNode = factory.parse(code, null, 0);

		JSNodeVisitor nodeVisitor = new JSNodeVisitor();

		rootNode.visit(nodeVisitor);

		nodeVisitor.getRoot().visit(new JSSymbolVisitor(vList));
	}

	private static void genServerFormulaScript(Map<String, String> lhm, LMap fm) {
		LList refList = fm.getList("fmlaCite");
		if (CollectionUtils.isNotEmpty(refList)) {
			for (Object r : refList) {
				genServerFormulaScript(lhm, SFormulaData.data.getMap((Integer) r));
			}
		}
		lhm.put(fm.getString("funName"), fm.getString("formula"));
	}

	private static void genServerFormulaScript(int fId) {
		LMap fm = SFormulaData.data.getMap(fId);
		LinkedHashMap<String, String> lhm = new LinkedHashMap<String, String>();
		genServerFormulaScript(lhm, fm);
		Map.Entry<String, String> lastE = null;
		for (Map.Entry<String, String> e : lhm.entrySet()) {
			if (lastE != null) {
				e.setValue(e.getValue().replaceAll(lastE.getKey(), "(" + lastE.getValue() + ")"));
			}
			lastE = e;
		}

		if (lastE != null)
			allFunc.put(lastE.getKey(), lastE.getValue());

	}

	private static String genCode() {
		for (Object obj : SFormulaData.data.keySet()) {
			genServerFormulaScript((Integer) obj);
		}

		StringBuilder expCodeStr = new StringBuilder();
		StringBuilder facadeFuncCoreStr = new StringBuilder();

		for (Object obj : SFormulaData.data.values()) {
			LMap lm = (LMap) obj;
			String funcName = lm.getString("funName");
			String code = allFunc.get(funcName);
			int fid = lm.getInt("sid");

			Set<String> varList = new HashSet<String>();
			parseJS(code, varList);
			varList.remove("math");

			StringBuilder sb = new StringBuilder();
			for (String var : varList) {
				sb.append(", ");
				if (var.equals("o") || var.equals("t")) {
					sb.append("BaseFightProperty " + var);
				} else {
					sb.append("double " + var);
				}
			}

			String exp = "	private static Object " + funcName + "("
					+ (sb.length() == 0 ? sb.toString() : sb.substring(2)) + "){";
			exp += "		return " + code.replaceAll("\\.lv", ".getLv()") + ";";
			exp += "	}\n";
			expCodeStr.append(exp);

			facadeFuncCoreStr.append("			case " + fid + ": \n");
			List<String> redVarList = new ArrayList<String>();
			for (String var : varList) {
				String newVar = var + "_" + fid;
				if (var.equals("o") || var.equals("t")) {
					facadeFuncCoreStr.append(
							"				BaseFightProperty " + newVar + " = bfp(params, \"" + var + "\"); \n");
				} else {
					facadeFuncCoreStr.append("				double " + newVar + " = d(params, \"" + var + "\"); \n");
				}
				redVarList.add(newVar);
			}

			facadeFuncCoreStr.append("				return " + funcName + "("
					+ StringUtils.join(redVarList.toArray(new String[] {}), ",") + "); \n");
		}

		StringBuilder facadeFuncStr = new StringBuilder();
		facadeFuncStr.append("	public static Object calcSFormula(int fId, Map<String, Object> params) { \n");
		facadeFuncStr.append("		switch (fId) { \n");
		facadeFuncStr.append(facadeFuncCoreStr.toString());
		facadeFuncStr.append("			default: \n");
		facadeFuncStr.append("			break; \n");
		facadeFuncStr.append("		} \n");
		facadeFuncStr.append("		return 0; \n");
		facadeFuncStr.append("	} \n");

		StringBuilder code = new StringBuilder();
		code.append("package ").append(GEN_PACKAGE).append(";\n\n");

		code.append("import java.util.Map;\n");
		code.append("import com.gamejelly.gong2.gas.entity.fight.BaseFightProperty;\n");
		code.append("import com.gamejelly.gong2.gas.entity.ServantEntity;\n");
		code.append("import com.gamejelly.gong2.gas.entity.MonsterEntity;\n");

		code.append("public class ").append(sFormulaExecutorName).append(" {\n\n");
		code.append(commonFuncStr);
		code.append("\n");
		code.append(expCodeStr);
		code.append("\n");
		code.append(facadeFuncStr);
		code.append("\n");
		code.append("}");

		return code.toString();
	}

	public static void gen() {
		try {
			String code = genCode();
			System.out.println(code);
			String genPath = DataUtils.loadFileFromClassLoader("").getPath() + "/../../src/main/java/";
			genPath += GEN_PACKAGE.replaceAll("\\.", "/");
			File propHelperFile = new File(new File(genPath), sFormulaExecutorName + ".java");
			FileUtils.writeStringToFile(propHelperFile, code.toString(), "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		gen();
	}

}
