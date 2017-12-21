package com.gamejelly.game.gong2.login.meta;

public class Page {

	private int total;
	private int limit;
	private int totalPage;
	private int curPage;

	public Page(int total, int limit, int curPage) {
		this.total = total;
		int totalPage = total % limit == 0 ? total / limit : (total / limit + 1);
		this.totalPage = totalPage;
		this.curPage = curPage <= 1 ? 1 : curPage > totalPage ? totalPage : curPage;
		this.limit = limit;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

}
