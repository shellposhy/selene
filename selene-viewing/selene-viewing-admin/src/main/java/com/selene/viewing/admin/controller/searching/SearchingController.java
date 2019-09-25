package com.selene.viewing.admin.controller.searching;

import static cn.com.lemon.base.Strings.split;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.selene.common.HttpStatus;
import com.selene.common.constants.CommonConstants;
import com.selene.common.constants.util.EAccessType;
import com.selene.common.constants.util.EDataType;
import com.selene.common.constants.util.EDateSearchType;
import com.selene.common.constants.util.EFieldSearchType;
import com.selene.common.constants.util.ELogicType;
import com.selene.common.datatable.DataTable;
import com.selene.common.datatable.DataTableArray;
import com.selene.common.datatable.DataTableResult;
import com.selene.common.result.ListResult;
import com.selene.common.util.DataUtil;
import com.selene.common.util.RedisClient;
import com.selene.dataing.model.DataingDataField;
import com.selene.dataing.model.jdbc.DataingData;
import com.selene.viewing.admin.controller.BaseController;
import com.selene.viewing.admin.service.ResourceService;
import com.selene.viewing.admin.service.TokenService;
import com.selene.viewing.admin.service.dataing.DataService;
import com.selene.viewing.admin.service.searching.SearchingService;

import static com.selene.common.util.Containers.toInt;
import static com.selene.common.util.Containers.table;

import static cn.com.lemon.base.Strings.isNullOrEmpty;
import static cn.com.lemon.base.Strings.md5;
import static cn.com.lemon.base.Strings.nullToEmpty;

import static com.selene.common.util.Lucenes.jion;
import static com.selene.common.util.Lucenes.light;

@Controller
@RequestMapping("/admin/searching")
public class SearchingController extends BaseController {
	private static final Logger LOG = LoggerFactory.getLogger(SearchingController.class.getName());
	@Resource
	private DataService dataService;
	@Resource
	private SearchingService searchingService;
	@Resource
	private TokenService commonService;
	@Resource
	private ResourceService resourceService;
	@Resource
	private RedisClient redisClient;

	@RequestMapping(value = "/quick", method = RequestMethod.GET)
	public String quick() {
		return "/admin/searching/quick";
	}

	@RequestMapping(value = "/advance", method = RequestMethod.GET)
	public String advance() {
		return "/admin/searching/advance";
	}

	@RequestMapping(value = "/field", method = RequestMethod.POST)
	@ResponseBody
	public MappingJacksonValue field(@RequestBody DataTableArray value, String callback, HttpServletRequest request) {
		ListResult<DataingDataField> result = new ListResult<DataingDataField>(HttpStatus.OK.code(),
				HttpStatus.OK.message());
		List<Integer> /* Models need to deleted. */ baseIds = toInt(
				Arrays.asList(split(CommonConstants.COMMA_SEPARATOR, value.value)));
		List<Integer> list = dataService.findDataBaseNodes(baseIds);
		if (list == null || list.size() < 1) {
			result.setData(null);
		} else {
			result.setData(dataService.findFieldsInBasesByAccess(EAccessType.Modifiable, list));
		}
		MappingJacksonValue mv = new MappingJacksonValue(result);
		mv.setJsonpFunction(callback);
		return mv;
	}

	@RequestMapping(value = "/presearch", method = RequestMethod.POST)
	public String preSearch(HttpServletRequest request) {
		String range = request.getParameter("searchIdStr");
		String /* Query String */ queryString = "";
		List<String> /* And condition */ andList = new ArrayList<String>();
		List<String> /* Or condition */ orList = new ArrayList<String>();
		List<String> /* Not condition */ notList = new ArrayList<String>();
		List<Integer> /* All database */ baseIds = toInt(Arrays.asList(split(CommonConstants.COMMA_SEPARATOR, range)));
		List<Integer> list = dataService.findDataBaseNodes(baseIds);
		List<DataingDataField> mustFieldList = dataService.findFieldsInBasesByAccess(EAccessType.Modifiable, list);
		/** Processing the search condition */
		if (mustFieldList != null && mustFieldList.size() > 0) {
			for (DataingDataField dataField : mustFieldList) {
				String /* If exist field */ logicType = request.getParameter(dataField.getCode() + "_logic");
				if (!isNullOrEmpty(logicType)) {
					EDataType /* Database column field type */ dataType = dataField.getDataType();
					String queryTerm = "";
					switch (dataType) {
					case /* Date field */DateTime:
					case Date:
					case Time:
						String fieldValue = request.getParameter(dataField.getCode());
						EDateSearchType dateSearchType = (fieldValue.equals("today")) ? EDateSearchType.Day
								: (fieldValue.equals("month") ? EDateSearchType.Month : EDateSearchType.Range);
						String valueStart = dataField.getCode() + "_start";
						String valueEnd = dataField.getCode() + "_end";
						String[] dateValues = { request.getParameter(valueStart), request.getParameter(valueEnd) };
						queryTerm = DataUtil.query(dataField.getCode(), dataType, dateSearchType, dateValues);
						break;
					default/* Not date field */:
						String valueKey = dataField.getCode();
						String value = request.getParameter(valueKey);
						value = DataUtil./* Remove special char and XSS */remove(DataUtil.xss(value));
						String[] values = { value };
						queryTerm = DataUtil.query(dataField.getCode(), dataType, null, values);
						break;
					}
					if (/* Save search condition */!isNullOrEmpty(queryTerm)) {
						if (logicType.equals(ELogicType.And.getTitle())) {
							andList.add(queryTerm);
						} else if (logicType.equals(ELogicType.Or.getTitle())) {
							orList.add(queryTerm);
						} else if (logicType.equals(ELogicType.Not.getTitle())) {
							notList.add(queryTerm);
						}
					}
				}
			}
		}
		queryString = /* Query condition join */jion(andList, orList, notList);
		/** Return result */
		if (/* Query condition save redis */!isNullOrEmpty(queryString)) {
			String searchToken = md5(queryString);
			if (/* If exist delete */redisClient.hasKey(searchToken)) {
				redisClient.delete(searchToken);
			}
			if (redisClient.set(searchToken, queryString,
					/* Default live time:one hour */ CommonConstants.DEFAULT_SEARCH_TOKEN)) {
				return "redirect:/admin/searching?type=1&term=&formula=&searchToken=" + searchToken + "&range=" + range;
			}
		}
		return "redirect:/admin/searching?type=1&term=&formula=&searchToken=&range=" + range;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "type", required = true) int type,
			@RequestParam(value = "term", required = false) String term,
			@RequestParam(value = "searchToken", required = false) String searchToken,
			@RequestParam(value = "formula", required = false) String formula,
			@RequestParam(value = "range", required = false) String range, Model model) {
		searchToken = nullToEmpty(searchToken);
		if (/* Quick search */type == 0) {
			String queryString = "";
			if (!isNullOrEmpty(formula) && EFieldSearchType.codeOf(term) != null) {
				try {
					queryString = DataUtil.query(EFieldSearchType.codeOf(term),
							java.net.URLDecoder.decode(formula, "UTF-8"));
					if (!isNullOrEmpty(queryString)) {
						searchToken = md5(queryString);
						if (/* If exist delete */redisClient.hasKey(searchToken)) {
							redisClient.delete(searchToken);
						}
						if (!redisClient.set(searchToken, queryString, CommonConstants.DEFAULT_SEARCH_TOKEN)) {
							searchToken = /* if write redis fail,set null */ "";
						}
					}
				} catch (UnsupportedEncodingException e) {
					searchToken = "";
				}
			}
		}
		List<Integer> list = toInt(Arrays.asList(range.split(CommonConstants.COMMA_SEPARATOR)));
		/** Page parameter */
		model.addAttribute("jspPageId", type == 0 ? 51 : 52);
		model.addAttribute("rangeName", dataService.findDataBaseName(list));
		/** Search parameter */
		model.addAttribute("type", type);
		model.addAttribute("range", range);
		model.addAttribute("term", term);
		model.addAttribute("searchToken", searchToken);
		return "/admin/searching/list";
	}

	@RequestMapping(value = "/s", method = RequestMethod.POST)
	@ResponseBody
	public MappingJacksonValue search(@RequestParam(value = "type", required = true) int type,
			@RequestParam(value = "range", required = true) String range,
			@RequestParam(value = "term", required = false) String term,
			@RequestParam(value = "searchToken", required = false) String searchToken,
			@RequestBody DataTableArray[] dataArray, String callback) {
		/** Request parameter */
		DataTable dataTable = table(dataArray);
		Integer first = dataTable.getiDisplayStart();
		Integer size = dataTable.getiDisplayLength();
		String word = dataTable.getsSearch();
		word = DataUtil./* Remove special char and XSS */remove(DataUtil.xss(word));
		List<Integer> dbList = toInt(Arrays.asList(split(CommonConstants.COMMA_SEPARATOR, range)));
		List<Integer> dataNodeList = /* Only keep data node */ dataService.findDataBaseNodes(dbList);
		/** Search parameter */
		Integer[] baseIds = dataNodeList.toArray(new Integer[dataNodeList.size()]);
		String /* Query string */ queryString = "";
		if (!isNullOrEmpty(searchToken)) {
			queryString = (String) redisClient.get(searchToken);
			if (!isNullOrEmpty(word)) {
				if (!isNullOrEmpty(queryString)) {
					queryString = "(" + queryString + ") AND " + "(Title:" + word.trim() + ")";
				} else {
					queryString = "Title:" + word.trim();
				}
			}
		} else {
			if (!isNullOrEmpty(word)) {
				queryString = "Title:" + word.trim();
			} else {
				queryString = CommonConstants.SEARCH_INDEX_ALL;
			}
		}
		String[] /* High light */ highLightFields = null;
		if (type == 0) {
			if (!isNullOrEmpty(term)) {
				highLightFields = light(EFieldSearchType.codeOf(term));
			}
		} else {
			highLightFields = light(EFieldSearchType.All);
		}
		/** Result process */
		LOG.info("query=" + queryString);
		ListResult<DataingData> dataList = searchingService.search(queryString, highLightFields, first, size, baseIds);
		List<DataingData> list = (dataList.getData() != null && dataList.getData().size() > 0) ? dataList.getData()
				: new ArrayList<DataingData>();
		DataTableResult<DataingData> result = new DataTableResult<DataingData>(dataTable.getsEcho(),
				dataList.getTotal(), dataList.getTotal(), list);
		MappingJacksonValue mv = new MappingJacksonValue(result);
		mv.setJsonpFunction(callback);
		return mv;
	}
}
