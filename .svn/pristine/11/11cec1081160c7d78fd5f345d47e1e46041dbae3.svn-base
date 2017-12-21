package com.gamejelly.game.gong2.login.meta;

import com.hadoit.game.common.framework.dao.annotation.Table;

@Table
public class MeizuSy37Account implements Bean {
	private static final long serialVersionUID = 1L;

	private long id;

	/**
	 * 存放的是xxxx@meizu
	 */
	private String meizuAccountName;

	/**
	 * 存放的是最终对应到gameAccount表的account字段, xxxx@meizu or xxx@sy37
	 */
	private String mappingAccountName;

	private long createTime;

	public MeizuSy37Account() {

	}

	public MeizuSy37Account(String meizuUid, String mappingUid, long time) {
		this.meizuAccountName = meizuUid;
		this.mappingAccountName = mappingUid;
		this.createTime = time;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMeizuAccountName() {
		return meizuAccountName;
	}

	public void setMeizuAccountName(String meizuAccountName) {
		this.meizuAccountName = meizuAccountName;
	}

	public String getMappingAccountName() {
		return mappingAccountName;
	}

	public void setMappingAccountName(String mappingAccountName) {
		this.mappingAccountName = mappingAccountName;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
}
