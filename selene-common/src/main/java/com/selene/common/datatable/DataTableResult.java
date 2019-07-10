package com.selene.common.datatable;

import java.util.List;

/**
 * Base model for Jquery Datatable {@code dataTables} plug-in components object
 * 
 * @author shaobo shih
 * @version 1.0
 */
public class DataTableResult<T> {
	private int sEcho;
	private int iTotalRecords;
	private int iTotalDisplayRecords;
	private List<T> aaData;

	public DataTableResult(int sEcho, int iTotalRecords, int iTotalDisplayRecords, List<T> aaData) {
		super();
		this.sEcho = sEcho;
		this.iTotalRecords = iTotalRecords;
		this.iTotalDisplayRecords = iTotalDisplayRecords;
		this.aaData = aaData;
	}

	public int getsEcho() {
		return sEcho;
	}

	public void setsEcho(int sEcho) {
		this.sEcho = sEcho;
	}

	public int getiTotalRecords() {
		return iTotalRecords;
	}

	public void setiTotalRecords(int iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}

	public int getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	public List<T> getAaData() {
		return aaData;
	}

	public void setAaData(List<T> aaData) {
		this.aaData = aaData;
	}

}
