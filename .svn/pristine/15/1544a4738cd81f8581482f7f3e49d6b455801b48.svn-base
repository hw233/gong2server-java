package com.hadoit.game.engine.guardian.dbs.base;

/**
 * raw command result 代表返回的结果，以及被影响的条目数；如果result != null，则num = 0
 * 
 * @author bezy 2013-11-25
 * 
 */
public class RawCommandResult {
	private Object result;
	private int num;

	public RawCommandResult() {
	}

	public RawCommandResult(Object result) {
		this(result, 0);
	}

	public RawCommandResult(Object result, int num) {
		this.result = result;
		this.num = num;
	}

	/**
	 * 查询类结果
	 * 
	 * @param result
	 * @return
	 */
	public static RawCommandResult newResult(Object result) {
		return new RawCommandResult(result);
	}

	/**
	 * 更新类结果
	 * 
	 * @param num
	 * @return
	 */
	public static RawCommandResult newUpdateResult(int num) {
		return new RawCommandResult(null, num);
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public Object[] getResults() {
		return new Object[] { result, num };
	}

}
