package com.gamejelly.game.gong2.login.service.sdk.impl.uc;

/**
 * sid验证结果响应类。
 */
public class SidInfoResponse {
	long id;
	SidInfoResponseState state;
	SidInfoResponseData data;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public SidInfoResponseState getState() {
		return this.state;
	}

	public void setState(SidInfoResponseState state) {
		this.state = state;
	}

	public SidInfoResponseData getData() {
		return this.data;
	}

	public void setData(SidInfoResponseData data) {
		this.data = data;
	}

	public class SidInfoResponseState {
		int code;
		String msg;

		public int getCode() {
			return this.code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getMsg() {
			return this.msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}
	}

	public class SidInfoResponseData {
		private String accountId;
		private String nickName;

		public String getAccountId() {
			return this.accountId;
		}

		public void setAccountId(String accountId) {
			this.accountId = accountId;
		}

		public String getNickName() {
			return this.nickName;
		}

		public void setNickName(String nickName) {
			this.nickName = nickName;
		}
	}

}
