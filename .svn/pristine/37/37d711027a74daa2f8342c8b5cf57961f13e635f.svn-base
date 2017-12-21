package com.gamejelly.gong2.meta;

import java.io.Serializable;
import java.util.HashMap;

public class MessageAction implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * index
	 */
	private int index;

	/**
	 * 操作类型
	 */
	private int op;

	/**
	 * 参数
	 */
	private HashMap<String, String> paramMap;

	public MessageAction() {
	}

	public MessageAction(int index, int op, HashMap<String, String> paramMap) {
		this.index = index;
		this.op = op;
		this.paramMap = paramMap;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getOp() {
		return op;
	}

	public void setOp(int op) {
		this.op = op;
	}

	public HashMap<String, String> getParamMap() {
		return paramMap;
	}

	public void setParamMap(HashMap<String, String> paramMap) {
		this.paramMap = paramMap;
	}

}
