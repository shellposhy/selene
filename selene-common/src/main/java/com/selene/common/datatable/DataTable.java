package com.selene.common.datatable;

/**
 * Base model for Jquery Datatable {@code dataTables} plug-in components object
 * 
 * @author shaobo shih
 * @version 1.0
 */
public class DataTable {
	private Integer sEcho;// Information to generate by dataTable
	private Integer iDisplayStart;// Start page
	private Integer iDisplayLength;// Size of page
	private String sSearch;// Search field for result
	private Integer iType;// Customize the extension parameters

	public Integer getsEcho() {
		return sEcho;
	}

	public void setsEcho(Integer sEcho) {
		this.sEcho = sEcho;
	}

	public Integer getiDisplayStart() {
		return iDisplayStart;
	}

	public void setiDisplayStart(Integer iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}

	public Integer getiDisplayLength() {
		return iDisplayLength;
	}

	public void setiDisplayLength(Integer iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}

	public String getsSearch() {
		return sSearch;
	}

	public void setsSearch(String sSearch) {
		this.sSearch = sSearch;
	}

	public Integer getiType() {
		return iType;
	}

	public void setiType(Integer iType) {
		this.iType = iType;
	}

}
