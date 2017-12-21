package com.gamejelly.gong2.utils.aot.tools;

import java.io.File;
import java.lang.reflect.Method;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.ReflectionUtils;

import com.gamejelly.gong2.config.data.PropData;
import com.gamejelly.gong2.config.data.base.LMap;
import com.gamejelly.gong2.gas.entity.ServantEntity;
import com.gamejelly.gong2.utils.GongRuntimeException;
import com.hadoit.game.common.lang.DataUtils;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;

public class PropDataGen {
	private static final String GEN_PACKAGE = "com.gamejelly.gong2.utils.aot";
	static String propDataExecutorName = "PropDataExecutor";

	static String commonFuncStr = "	private static int parseInt(double d){ \n"
			+ "		return ((Number)d).intValue(); \n" + "	} \n" + "\n"
			+ "	private static long toLong(Number d){ \n" + "		return d.longValue(); \n" + "	}\n" + ""
			+ "	private static float toFloat(Number d){\n" + "		return d.floatValue();\n" + "	}\n" + "\n"
			+ "	private static int toInt(Number d){\n" + "		return d.intValue();\n" + "	}\n" + "\n"
			+ "	private static double toDouble(Number d){\n" + "		return d.doubleValue();\n" + "	}\n";

	public static String getTypeFromReturn(String mName) {
		try {
			Method m = ReflectionUtils.findMethod(ServantEntity.class, mName);
			Class clazz = m.getReturnType();
			if (Integer.TYPE.equals(clazz) || Integer.class.equals(clazz)) {
				return "int";
			} else if (Long.TYPE.equals(clazz) || Long.class.equals(clazz)) {
				return "long";
			} else if (Float.TYPE.equals(clazz) || Float.class.equals(clazz)) {
				return "float";
			} else if (Double.TYPE.equals(clazz) || Double.class.equals(clazz)) {
				return "double";
			}
		} catch (Exception e) {
			GuardianLogger.warn(mName + " is not Exists!", e.getMessage());
		}

		return "";
	}

	public static String getConvertFromParam(String mName) {
		try {
			Method m = ReflectionUtils.findMethod(ServantEntity.class, mName, null);
			if (m != null) {
				Class clazz = m.getParameterTypes()[0];
				if (Integer.TYPE.equals(clazz) || Integer.class.equals(clazz)) {
					return "toInt";
				} else if (Long.TYPE.equals(clazz) || Long.class.equals(clazz)) {
					return "toLong";
				} else if (Float.TYPE.equals(clazz) || Float.class.equals(clazz)) {
					return "toFloat";
				} else if (Double.TYPE.equals(clazz) || Double.class.equals(clazz)) {
					return "toDouble";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			GuardianLogger.warn(mName + " is not Exists!", e.getMessage());
		}

		return "";
	}

	public static String genPropDataJava() {
		// 门徒属性变化的时候调用
		LMap lmap;
		StringBuilder sb = new StringBuilder();
		for (Object obj : PropData.data.values()) {
			lmap = (LMap) obj;
			if (lmap.getInt("kind", 0) == 0) {
				String pName = lmap.getString("funName");
				String pGetName = "get" + pName.toUpperCase().charAt(0) + pName.substring(1);
				String pType = getTypeFromReturn(pGetName);
				if (StringUtils.isNotBlank(pType)) {
					sb.append("		" + pType + " ");
					sb.append(lmap.getString("funName"));
					sb.append(" = v.");
					sb.append(pGetName + "()");
					sb.append(";\n");
				}
			}
		}

		for (Object obj : PropData.data.values()) {
			lmap = (LMap) obj;
			if (lmap.getInt("kind", 0) == 1) {
				sb.append("		v.");
				String fName = lmap.getString("funName");
				String fSetName = "set" + fName.toUpperCase().charAt(0) + fName.substring(1);
				String convertName = getConvertFromParam(fSetName);
				sb.append(fSetName + "( " + convertName + "(" + lmap.getString("prop") + ") )");
				sb.append(";\n");

				String pName = lmap.getString("funName");
				String pGetName = "get" + pName.toUpperCase().charAt(0) + pName.substring(1);
				String pType = getTypeFromReturn(pGetName);

				sb.append("		" + pType + " ");
				sb.append(lmap.getString("funName"));
				sb.append(" = v.");
				sb.append(pGetName + "()");
				sb.append(";\n");
			}
		}
		if (StringUtils.isBlank(sb.toString())) {
			throw new GongRuntimeException("genPropDataScript not found!");
		}

		String methodStr1 = "	public static void calcAll(ServantEntity v){ \n";
		methodStr1 += sb.toString();
		methodStr1 += "	}";

		String methodStr2 = "	public static void calcAll(MonsterEntity v){ \n";
		methodStr2 += sb.toString();
		methodStr2 += "	}";

		StringBuilder code = new StringBuilder();
		code.append("package ").append(GEN_PACKAGE).append(";\n\n");

		code.append("import com.gamejelly.gong2.gas.entity.ServantEntity;\n");
		code.append("import com.gamejelly.gong2.gas.entity.MonsterEntity;\n");
		code.append("\n");
		code.append("public class ").append(propDataExecutorName).append(" {\n\n");
		code.append(commonFuncStr);
		code.append(methodStr1);
		code.append("\n");
		code.append(methodStr2);
		code.append("}");

		return code.toString();
	}

	public static void gen() {
		try {
			String code = genPropDataJava();
			System.out.println(code);
			String genPath = DataUtils.loadFileFromClassLoader("").getPath() + "/../../src/main/java/";
			genPath += GEN_PACKAGE.replaceAll("\\.", "/");
			File propHelperFile = new File(new File(genPath), propDataExecutorName + ".java");
			FileUtils.writeStringToFile(propHelperFile, code.toString(), "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		gen();
	}

}
