package com.gamejelly.gong2.utils;

import java.util.HashMap;
import java.util.Map;

import javax.script.CompiledScript;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.gamejelly.gong2.config.data.PropData;
import com.gamejelly.gong2.config.data.SFormulaData;
import com.gamejelly.gong2.config.data.base.LList;
import com.gamejelly.gong2.config.data.base.LMap;
import com.gamejelly.gong2.gas.entity.MonsterEntity;
import com.gamejelly.gong2.gas.entity.ServantEntity;
import com.gamejelly.gong2.utils.aot.PropDataExecutor;
import com.gamejelly.gong2.utils.aot.SFormulaExecutor;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;

public class FormulaHelper {
	private static CompiledScript propDataScript = null;
	private static final Map<Integer, CompiledScript> formulaScriptMap = new HashMap<Integer, CompiledScript>();

	public static void prepare() {
		GuardianLogger.info("genPropDataScript!");
		genPropDataScript();
		for (Object obj : SFormulaData.data.keySet()) {
			GuardianLogger.info("genServerFormulaScript: " + obj);
			genServerFormulaScript((Integer) obj);
		}
	}

	private static void genPropDataScript() {
		// 门徒属性变化的时候调用
		LMap lmap;
		StringBuilder sb = new StringBuilder();
		for (Object obj : PropData.data.values()) {
			lmap = (LMap) obj;
			if (lmap.getInt("kind", 0) == 0) {
				sb.append("var ");
				sb.append(lmap.getString("funName"));
				sb.append(" = v.");
				sb.append(StringUtils.defaultIfBlank(lmap.getString("prop"), lmap.getString("funName")));
				sb.append(";\n");
			}
		}

		for (Object obj : PropData.data.values()) {
			lmap = (LMap) obj;
			if (lmap.getInt("kind", 0) == 1) {
				sb.append("v.");
				sb.append(lmap.getString("funName"));
				sb.append(" = ");
				sb.append(lmap.getString("prop"));
				sb.append(";\n");

				sb.append("var ");
				sb.append(lmap.getString("funName"));
				sb.append(" = v.");
				sb.append(lmap.getString("funName"));
				sb.append(";\n");
			}
		}
		if (StringUtils.isBlank(sb.toString())) {
			throw new GongRuntimeException("genPropDataScript not found!");
		}
		propDataScript = FormulaUtils.compile(sb.toString());
	}

	private static void genServerFormulaScript(int fId) {
		StringBuilder sb = new StringBuilder();
		LMap fm = SFormulaData.data.getMap(fId);
		genServerFormulaScript(sb, fm);
		sb.append(fm.getString("funName")).append(";\n");
		CompiledScript formulaScript = FormulaUtils.compile(sb.toString());
		formulaScriptMap.put(fId, formulaScript);
	}

	private static void genServerFormulaScript(StringBuilder sb, LMap fm) {
		LList refList = fm.getList("fmlaCite");
		if (CollectionUtils.isNotEmpty(refList)) {
			for (Object r : refList) {
				genServerFormulaScript(sb, SFormulaData.data.getMap((Integer) r));
			}
		}
		sb.append("var ").append(fm.getString("funName")).append(" = ").append(fm.getString("formula")).append(";\n");
	}

	public static void calcFightObjectProps(Object obj) {
		if (propDataScript == null) {
			throw new GongRuntimeException("propDataScript not found!");
		}

		if (GongConstants.isOpenAot()) {
			if (obj instanceof MonsterEntity) {
				PropDataExecutor.calcAll((MonsterEntity) obj);
			} else if (obj instanceof ServantEntity) {
				PropDataExecutor.calcAll((ServantEntity) obj);
			} else {
				throw new GongRuntimeException("obj not found!" + obj.getClass());
			}
		} else {
			FormulaUtils.evalValue(propDataScript, obj);
		}
	}

	public static Number calcSFormulaNumber(int fId, Map<String, Object> params) {
		CompiledScript script = formulaScriptMap.get(fId);
		if (script == null) {
			throw new GongRuntimeException("formula script: " + fId + " not found!");
		}

		Number r = 0;
		if (GongConstants.isOpenAot()) {
			r = (Number) SFormulaExecutor.calcSFormula(fId, params);
		} else {
			r = (Number) FormulaUtils.eval(script, params);
		}

		if (r == null) {
			throw new GongRuntimeException("calc formula script: " + fId + " error!");
		}
		return r;
	}

	public static Boolean calcSFormulaBoolean(int fId, Map<String, Object> params) {
		CompiledScript script = formulaScriptMap.get(fId);
		if (script == null) {
			throw new GongRuntimeException("formula script: " + fId + " not found!");
		}
		Boolean r = false;
		if (GongConstants.isOpenAot()) {
			r = (Boolean) SFormulaExecutor.calcSFormula(fId, params);
		} else {
			r = (Boolean) FormulaUtils.eval(script, params);
		}
		if (r == null) {
			throw new GongRuntimeException("calc formula script: " + fId + " error!");
		}
		return r;
	}

	public static Number calcSFormula(int fId, Map<String, Object> params) {
		CompiledScript script = formulaScriptMap.get(fId);
		if (script == null) {
			throw new GongRuntimeException("formula script: " + fId + " not found!");
		}
		Number r = 0;
		if (GongConstants.isOpenAot()) {
			r = (Number) SFormulaExecutor.calcSFormula(fId, params);
		} else {
			r = (Number) FormulaUtils.eval(script, params);
		}
		if (r == null) {
			throw new GongRuntimeException("calc formula script: " + fId + " error!");
		}
		return r;
	}
}
