
package com.gamejelly.gong2.gas.service.user;

import org.springframework.stereotype.Component;

import com.gamejelly.gong2.config.data.MonsterData;
import com.gamejelly.gong2.config.data.SysConstData;
import com.gamejelly.gong2.config.data.base.LMap;
import com.gamejelly.gong2.gas.entity.MonsterEntity;
import com.gamejelly.gong2.utils.FormulaHelper;
import com.gamejelly.gong2.utils.GongConstants;
import com.gamejelly.gong2.utils.GongUtils;
import com.gamejelly.gong2.utils.IdProvider;

@Component("monsterService")
public class MonsterService {

	/**
	 * 
	 * @param monsterTemplateId
	 * @param outLv
	 *            角色Lv
	 * @return
	 */
	public MonsterEntity createMonster(int monsterTemplateId, int outLv) {
		LMap mData = MonsterData.data.getMap(monsterTemplateId);
		int lvKind = mData.getInt("lvKind", SysConstData.data.getInt("LV_KIND_GD", 0));
		int lv = mData.getInt("lv", 1);
		if (lvKind == SysConstData.data.getInt("LV_KIND_SJ", 0)) {
			lv = FormulaHelper.calcSFormula(GongConstants.FORMULA_CALC_MONSTER_LV, GongUtils.createHashMap("lv", outLv))
					.intValue();
		}
		MonsterEntity m = new MonsterEntity();
		m.setTemplateId(monsterTemplateId);
		m.setId(IdProvider.genId(GongConstants.ID_TYPE_MONSTER));
		m.setLv(lv);
		m.calcAll();
		return m;
	}

}
