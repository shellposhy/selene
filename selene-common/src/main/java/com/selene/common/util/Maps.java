package com.selene.common.util;

import java.util.HashMap;
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
public final class Maps {
	private Maps() {
	}

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
}
