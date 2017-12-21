package com.gamejelly.gong2.vo;

import java.util.List;

import com.gamejelly.gong2.gas.entity.fight.Fighter;
import com.hadoit.game.engine.guardian.core.RemoteType;

public class FighterVO implements RemoteType {
	private static final long serialVersionUID = 1L;

	private String id;

	private int templateId;

	private boolean avatar;

	/**
	 * 人\怪
	 */
	private int type;

	private int index;

	/**
	 * 主动,被动方
	 */
	private int side;

	private long initFinalHp;

	private long finalHp;

	private long initFinalCp;

	private long finalCp;

	private int lv;

	private String name;

	/**
	 * 需要用到icon列表
	 */
	private List<Integer> iconList;

	/**
	 * 携带的被动法宝
	 */
	private List<Integer> paFbList;

	private int sex;

	private List<Integer> dress;

	public FighterVO(Fighter f) {
		this.id = f.getId();
		this.index = f.getIndex();
		this.side = f.getSide();
		this.finalHp = f.getFinalHp();
		this.initFinalHp = f.getInitFinalHp();
		this.finalCp = f.getFinalCp();
		this.initFinalCp = f.getInitFinalCp();

		this.iconList = f.getIconList();
		this.paFbList = f.getPaFbList();
		this.lv = f.getLv();
		this.avatar = f.isAvatar();
	}

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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getSide() {
		return side;
	}

	public void setSide(int side) {
		this.side = side;
	}

	public List<Integer> getIconList() {
		return iconList;
	}

	public void setIconList(List<Integer> iconList) {
		this.iconList = iconList;
	}

	public List<Integer> getPaFbList() {
		return paFbList;
	}

	public void setPaFbList(List<Integer> paFbList) {
		this.paFbList = paFbList;
	}

	public int getLv() {
		return lv;
	}

	public void setLv(int lv) {
		this.lv = lv;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public List<Integer> getDress() {
		return dress;
	}

	public void setDress(List<Integer> dress) {
		this.dress = dress;
	}

	public boolean isAvatar() {
		return avatar;
	}

	public void setAvatar(boolean avatar) {
		this.avatar = avatar;
	}

	public long getInitFinalHp() {
		return initFinalHp;
	}

	public void setInitFinalHp(long initFinalHp) {
		this.initFinalHp = initFinalHp;
	}

	public long getFinalHp() {
		return finalHp;
	}

	public void setFinalHp(long finalHp) {
		this.finalHp = finalHp;
	}

	public static long getSerialversionuid() {

		return serialVersionUID;
	}

	public long getFinalCp() {

		return finalCp;
	}

	public void setFinalCp(long finalCp) {

		this.finalCp = finalCp;
	}

	public long getInitFinalCp() {

		return initFinalCp;
	}

}
