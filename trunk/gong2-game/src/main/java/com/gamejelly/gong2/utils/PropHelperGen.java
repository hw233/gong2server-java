package com.gamejelly.gong2.utils;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.gamejelly.gong2.config.data.PropData;
import com.gamejelly.gong2.config.data.base.LMap;
import com.gamejelly.gong2.gas.entity.MonsterEntity;
import com.gamejelly.gong2.gas.entity.ServantEntity;
import com.gamejelly.gong2.gas.entity.fight.Fightable;
import com.hadoit.game.common.framework.dao.utils.BaseUtils;
import com.hadoit.game.common.lang.DataUtils;

public class PropHelperGen {
	private static final String GEN_PACKAGE = "com.gamejelly.gong2.utils.gen";

	private static PropertyDescriptor getPropertyDescriptor(Class<?> clazz, String propName) {
		PropertyDescriptor[] pds = BaseUtils.getBeanUtilsInstance().getPropertyUtils().getPropertyDescriptors(clazz);
		for (PropertyDescriptor pd : pds) {
			if (propName.equals(pd.getName())) {
				return pd;
			}
		}
		return null;
	}

	public static String genNumPropCode(Class<?> clazz) {
		String propName;
		PropertyDescriptor pd;
		Class<?> propType;
		String vStr;
		StringBuilder readSb = new StringBuilder();
		StringBuilder writeSb = new StringBuilder();
		String clazzName = clazz.getSimpleName();
		readSb.append("	private static Number get").append(clazzName).append("Prop(");
		readSb.append(clazzName).append(" o, String propName) {\n");
		writeSb.append("	private static void set").append(clazzName).append("Prop(");
		writeSb.append(clazzName).append(" o, String propName, Number v) {\n");
		for (Object propMetaObj : PropData.data.values()) {
			propName = ((LMap) propMetaObj).getString("funName");
			pd = getPropertyDescriptor(clazz, propName);
			if (pd == null) {
				continue;
			}
			if (pd.getReadMethod() != null) {
				propType = pd.getPropertyType();
				if (Short.TYPE.equals(propType) || Short.class.equals(propType)) {
				} else if (Integer.TYPE.equals(propType) || Integer.class.equals(propType)) {
				} else if (Long.TYPE.equals(propType) || Long.class.equals(propType)) {
				} else if (Float.TYPE.equals(propType) || Float.class.equals(propType)) {
				} else {
					throw new UnsupportedOperationException(
							"Unsupported read property " + propName + " type " + propType);
				}
				readSb.append("		if (\"" + propName + "\".equals(propName)) {\n");
				readSb.append("			return o." + pd.getReadMethod().getName() + "();\n");
				readSb.append("		}\n");
			}
			if (pd.getWriteMethod() != null) {
				propType = pd.getPropertyType();
				if (Short.TYPE.equals(propType) || Short.class.equals(propType)) {
					vStr = "v.shortValue()";
				} else if (Integer.TYPE.equals(propType) || Integer.class.equals(propType)) {
					vStr = "v.intValue()";
				} else if (Long.TYPE.equals(propType) || Long.class.equals(propType)) {
					vStr = "v.longValue()";
				} else if (Float.TYPE.equals(propType) || Float.class.equals(propType)) {
					vStr = "v.floatValue()";
				} else {
					throw new UnsupportedOperationException(
							"Unsupported write property " + propName + " type " + propType);
				}
				writeSb.append("		if (\"" + propName + "\".equals(propName)) {\n");
				writeSb.append("			o." + pd.getWriteMethod().getName() + "(" + vStr + ");\n");
				writeSb.append("			return;\n");
				writeSb.append("		}\n");
			}
		}
		readSb.append("		throw new UnsupportedOperationException(\"Unknown read property \" + propName + \" in "
				+ clazzName + "\");\n");
		readSb.append("	}\n\n");
		writeSb.append("		throw new UnsupportedOperationException(\"Unknown write property \" + propName + \" in "
				+ clazzName + "\");\n");
		writeSb.append("	}\n\n");
		StringBuilder sb = new StringBuilder(readSb);
		sb.append(writeSb);
		return sb.toString();
	}

	public static void genPropHelperCode(Class<?> baseClazz, Class<?>[] clazzs) throws IOException {
		StringBuilder code = new StringBuilder();
		String baseName = baseClazz.getSimpleName();
		String propHelperName = baseName + "PropHelper";
		code.append("package ").append(GEN_PACKAGE).append(";\n\n");
		for (Class<?> clazz : clazzs) {
			code.append("import ").append(clazz.getName()).append(";\n");
		}
		code.append("import ").append(baseClazz.getName()).append(";\n");
		code.append("import com.gamejelly.gong2.utils.GongUtils;").append(";\n");

		code.append("\n");
		code.append("public class ").append(propHelperName).append(" {\n\n");
		code.append("	public static Number getNumProp(").append(baseName).append(" o, String propName) {\n");
		for (Class<?> clazz : clazzs) {
			code.append("		if (o instanceof ").append(clazz.getSimpleName()).append(") {\n");
			code.append("			Number r = get").append(clazz.getSimpleName()).append("Prop((")
					.append(clazz.getSimpleName()).append(") o, propName);\n");
			code.append("			return r != null ? r : 0;\n");
			code.append("		}\n");
		}
		code.append("		throw new UnsupportedOperationException(\"Unknown read ").append(baseName)
				.append(" bean \" + o);\n");
		code.append("	}\n\n");
		code.append("	public static void setNumProp(").append(baseName).append(" o, String propName, Number v) {\n");
		for (Class<?> clazz : clazzs) {
			code.append("		if (o instanceof ").append(clazz.getSimpleName()).append(") {\n");
			code.append("			set").append(clazz.getSimpleName()).append("Prop((").append(clazz.getSimpleName())
					.append(") o, propName, v);\n");
			code.append("			return;\n");
			code.append("		}\n");
		}
		code.append("		throw new UnsupportedOperationException(\"Unknown write ").append(baseName)
				.append(" bean \" + o);\n");
		code.append("	}\n\n");
		code.append("	public static void incrNumProp(").append(baseName).append(" o, String propName, Number v) {\n");
		code.append("		float old = getNumProp(o, propName).floatValue();\n");
		code.append("		setNumProp(o, propName, old + v.floatValue());\n");
		code.append("	}\n\n");

		code.append("	public static void incrNumProp(").append(baseName)
				.append(" o, String propName, Number v, Number maxV) {\n");
		code.append("		float old = getNumProp(o, propName).floatValue();\n");
		code.append("		float newV = old + v.floatValue();\n");
		code.append("		if (maxV != null) {\n");
		code.append(
				"			newV = GongUtils.adjustNumberInRange(old + v.floatValue(), Float.MIN_VALUE, maxV.floatValue());\n");
		code.append("		}\n");
		code.append("		setNumProp(o, propName, newV);\n");
		code.append("	}\n\n");

		for (Class<?> clazz : clazzs) {
			code.append(genNumPropCode(clazz));
		}
		code.append("}");

		String genPath = DataUtils.loadFileFromClassLoader("").getPath() + "/../../src/main/java/";
		genPath += GEN_PACKAGE.replaceAll("\\.", "/");
		File propHelperFile = new File(new File(genPath), propHelperName + ".java");
		FileUtils.writeStringToFile(propHelperFile, code.toString(), "UTF-8");
	}

	public static void main(String[] args) throws IOException {
		genPropHelperCode(Fightable.class, new Class<?>[] { ServantEntity.class, MonsterEntity.class });
	}
}