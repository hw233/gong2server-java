package com.gamejelly.gong2.meta;

import com.gamejelly.gong2.config.data.EquipData;
import com.gamejelly.gong2.config.data.FabaoData;
import com.gamejelly.gong2.config.data.ItemData;
import com.gamejelly.gong2.config.data.base.LMap;
import com.gamejelly.gong2.meta.base.ChildModel;
import com.gamejelly.gong2.utils.GongUtils;
import com.hadoit.game.common.framework.dao.annotation.Column;
import com.hadoit.game.common.framework.dao.annotation.Embedded;
import com.hadoit.game.common.framework.dao.annotation.Index;
import com.hadoit.game.common.framework.dao.annotation.Table;

@Table(recursion = true, indexs = { @Index(columns = { "templateId", "parentId" }) })
public class ItemModel extends ChildModel {
	private static final long serialVersionUID = 1L;

	/**
	 * 表示唯一的id
	 */
	@Column(value = "uid", index = true)
	private String id;

	private int templateId;

	private int count;

	private long createTime;

	/**
	 * 是否应该放入背包
	 */
	private boolean inBag;

	/**
	 * 等级
	 */
	private int lv;

	/**
	 * 归属
	 */
	private String ownerId;

	/**
	 * 阵法已使用
	 */
	private boolean zfUsed;

	@Embedded
	private EquipModel equip;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public void incrCount(int v) {
		setCount(GongUtils.adjustNumberInRange(getCount() + v, 0, Integer.MAX_VALUE));
	}

	public boolean isInBag() {
		return inBag;
	}

	public void setInBag(boolean inBag) {
		this.inBag = inBag;
	}

	public int getLv() {
		return lv;
	}

	public void setLv(int lv) {
		this.lv = lv;
	}

	public EquipModel getEquip() {
		return equip;
	}

	public void setEquip(EquipModel equip) {
		this.equip = equip;
	}

	public String getOwnerId() {

		return ownerId;
	}

	public void setOwnerId(String ownerId) {

		this.ownerId = ownerId;
	}

	public LMap getTemplate() {
		return ItemData.data.getMap(templateId);
	}

	public LMap getEquipTemplate() {
		return EquipData.data.getMap(templateId);
	}

	public LMap getFbTemplate() {
		return FabaoData.data.getMap(templateId);
	}

	public boolean isZfUsed() {

		return zfUsed;
	}

	public void setZfUsed(boolean zfUsed) {

		this.zfUsed = zfUsed;
	}




}
