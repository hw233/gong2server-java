package com.gamejelly.game.gong2.login.meta;

import com.hadoit.game.common.framework.dao.annotation.Table;

@Table
public class IdCardNameError implements Bean {
	private static final long serialVersionUID = 1L;

	private long id;

	private String account; // 账户 字幕+数字+_.

	private String name; // 真实姓名

	private String idCardNo;// 身份证

	public IdCardNameError() {

	}

	public IdCardNameError(String account, String name, String idCardNo) {
		this.account = account;
		this.name = name;
		this.idCardNo = idCardNo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
}