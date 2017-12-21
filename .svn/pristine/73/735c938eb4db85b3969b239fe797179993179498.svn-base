package com.gamejelly.gong2.meta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gamejelly.gong2.meta.base.EmbedModel;
import com.hadoit.game.common.framework.dao.annotation.Column;
import com.hadoit.game.common.framework.dao.annotation.Embeddable;
import com.hadoit.game.common.framework.dao.convert.impl.CollectionNumberOrStringColumnConvert;
import com.hadoit.game.common.framework.dao.convert.impl.MapNumberOrStringColumnConvert;

@Embeddable(recursion = true)
public class EquipModel implements EmbedModel {
	private static final long serialVersionUID = 1L;

	/**
	 * 主属性
	 */
	@Column(convertClazz = CollectionNumberOrStringColumnConvert.class)
	private List<Integer> mainProps;

	/**
	 * 主属性值
	 */
	@Column(convertClazz = CollectionNumberOrStringColumnConvert.class)
	private List<Float> mainPropVals;

	public List<Integer> getMainProps() {
		return mainProps;
	}

	public void setMainProps(List<Integer> mainProps) {
		this.mainProps = mainProps;
	}

	public List<Float> getMainPropVals() {
		return mainPropVals;
	}

	public void setMainPropVals(List<Float> mainPropVals) {
		this.mainPropVals = mainPropVals;
	}
}
