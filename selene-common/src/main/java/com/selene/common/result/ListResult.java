package com.selene.common.result;

import java.util.List;

import com.selene.common.Result;

/**
 * ListResult {@code ListResult} information for response
 * 
 * @author shaobo shih
 * @version 1.0
 */
public class ListResult<T> extends Result {
	private static final long serialVersionUID = -8829200444894350340L;
	private int total = 0;// all data number
	private int pageSize = 10;// page size
	private int pageCount = 0;// all page count
	private int pageNo = 1;// current page number
	private List<T> data;// current data list

	public ListResult() {
	}

	public ListResult(int code, String msg) {
		super(code, msg);
	}

	public ListResult(int code, String msg, List<T> data) {
		super(code, msg);
		this.data = data;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
}
