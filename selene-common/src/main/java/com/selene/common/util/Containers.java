package com.selene.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.selene.common.constants.DataTableConstants;
import com.selene.common.datatable.DataTable;
import com.selene.common.datatable.DataTableArray;

/**
 * The utilities for map transfer process
 * 
 * @author shaobo shih
 * @version 1.0
 */
public final class Containers {
	private Containers() {
	}

	/**
	 * The utilities for dataTable request parameter transfer {@code Map}
	 * 
	 * @param tableArrays
	 *            dataTable request parameter
	 * @return {@link Map}
	 */
	public static Map<String, String> transfer(DataTableArray[] tableArrays) {
		if (tableArrays == null || tableArrays.length == 0) {
			return null;
		}
		Map<String, String> result = new HashMap<String, String>();
		for (DataTableArray para : tableArrays) {
			result.put(para.name, String.valueOf(para.value));
		}
		return result;
	}

	/**
	 * The utilities for dataTable request parameter transfer {@code DataTable}
	 * 
	 * @param tableArrays
	 *            dataTable request parameter
	 * @return {@link DataTable}
	 */
	public static DataTable table(DataTableArray[] tableArrays) {
		if (tableArrays == null || tableArrays.length == 0) {
			return null;
		}
		DataTable result = new DataTable();
		Map<String, String> data = transfer(tableArrays);
		result.setsEcho(null != data.get(DataTableConstants.sEcho) ? Integer.valueOf(data.get(DataTableConstants.sEcho))
				: null);
		result.setiDisplayStart(null != data.get(DataTableConstants.iDisplayStart)
				? Integer.valueOf(data.get(DataTableConstants.iDisplayStart)) : null);
		result.setiDisplayLength(null != data.get(DataTableConstants.iDisplayLength)
				? Integer.valueOf(data.get(DataTableConstants.iDisplayLength)) : null);
		result.setsSearch(null != data.get(DataTableConstants.sSearch) ? data.get(DataTableConstants.sSearch) : null);
		result.setiType(null != data.get(DataTableConstants.iType) ? Integer.valueOf(data.get(DataTableConstants.iType))
				: null);
		return result;
	}

	/**
	 * The utilities for {@code Integer} list to {@code String} list
	 * 
	 * @param list
	 *            source {@code List}
	 * @return {@link List}
	 */
	public static List<String> toList(List<Integer> list) {
		if (list == null || list.size() == 0) {
			return null;
		}
		List<String> result = new ArrayList<String>();
		for (Integer value : list) {
			result.add(String.valueOf(value));
		}
		return result;
	}
}
