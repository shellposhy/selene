package com.selene.viewing.admin.controller.dataing.databse;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.selene.common.Operator;
import com.selene.common.constants.CommonConstants;
import com.selene.common.constants.FieldsConstants;
import com.selene.common.constants.util.EAccessType;
import com.selene.common.constants.util.EDataStatus;
import com.selene.common.constants.util.EResult;
import com.selene.common.datatable.DataTable;
import com.selene.common.datatable.DataTableArray;
import com.selene.common.datatable.DataTableResult;
import com.selene.common.result.ListResult;
import com.selene.common.util.Containers;
import com.selene.common.util.DataUtil;
import com.selene.dataing.model.DataingDataField;
import com.selene.dataing.model.DataingDataTable;
import com.selene.dataing.model.jdbc.DataingBaseData;
import com.selene.dataing.model.jdbc.DataingData;
import com.selene.dataing.model.util.DataingUtil;
import com.selene.viewing.admin.controller.BaseController;
import com.selene.viewing.admin.service.ResourceService;
import com.selene.viewing.admin.service.TokenService;
import com.selene.viewing.admin.service.dataing.DataService;
import com.selene.viewing.admin.service.searching.SearchingService;
import com.selene.viewing.admin.vo.merchants.MerchantsUserVO;

import cn.com.lemon.base.DateUtil;

import static cn.com.lemon.base.DateUtil.format;
import static cn.com.lemon.base.Strings.uuid;
import static cn.com.lemon.base.Strings.isNullOrEmpty;
import static cn.com.lemon.base.Preasserts.checkNotNull;

@Controller
@RequestMapping("/admin/dataing/library/data")
public class DataingDataController extends BaseController {
	@Resource
	private DataService dataService;
	@Resource
	private SearchingService searchingService;
	@Resource
	private TokenService commonService;
	@Resource
	private ResourceService resourceService;

	@RequestMapping(value = "/edit/{tableId}/{dataId}", method = RequestMethod.GET)
	public String edit(@PathVariable int tableId, @PathVariable int dataId, Model model) {
		DataingDataTable dataTable = dataService.findTable(tableId);
		DataingBaseData baseData = dataService.select(tableId, dataId);
		List<DataingDataField> libraryFieldList = dataService.findFieldsByBaseId(dataTable.getBaseId());
		DataingData dataVo = new DataingData();
		dataVo./* ID */setId((Integer) baseData.get(FieldsConstants.ID));
		dataVo./* Table ID */setTableId(String.valueOf(tableId));
		dataVo./* Create time */setCreateTime(
				DateUtil.format((Date) baseData.get(FieldsConstants.CREATE_TIME), CommonConstants.SIMPLE_DATE_FORMAT));
		dataVo./* Database ID */setBaseId(dataTable.getBaseId());
		dataVo./* UUID */setUuid((String) baseData.get(FieldsConstants.UUID));
		/** Current database data fields */
		StringBuilder sb = new StringBuilder(200);
		if (libraryFieldList != null && libraryFieldList.size() > 0) {
			for (DataingDataField dataField : libraryFieldList) {
				if (dataField.getAccessType() != EAccessType.System || dataField.getCode().equals("Keywords")) {
					dataVo.putFieldMap(dataField.getCode(),
							DataUtil.dataTypeString(baseData.get(dataField.getCode()), dataField.getDataType()));
					sb.append(dataField.getCode()).append(CommonConstants.COMMA_SEPARATOR);
				}
			}
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		dataVo./* Field list */setFieldsString(sb.toString());
		/** Current database attachs */
		if (baseData.get(FieldsConstants.ATTACH) != null
				&& !isNullOrEmpty((String) baseData.get(FieldsConstants.ATTACH))) {
			String[] attachsName = ((String) baseData.get(FieldsConstants.ATTACH))
					.split(CommonConstants.COMMA_SEPARATOR);
			if (attachsName != null && attachsName.length > 0) {
				List<String> attachsFile = new ArrayList<String>();
				for (String name : attachsName) {
					attachsFile.add(name);
				}
				model.addAttribute("attachList", attachsFile);
			}
		}
		model.addAttribute("baseId", dataTable.getBaseId());
		model.addAttribute("fieldsStr", sb.toString());
		model.addAttribute("dataVo", dataVo);
		return "/admin/dataing/data/edit";
	}

	@RequestMapping("/info/{tableId}/{dataId}")
	public String info(@PathVariable int tableId, @PathVariable int dataId, Model model) {
		DataingBaseData data = dataService.select(tableId, dataId);
		if (data != null) {
			data.setId(dataId);
			data.setBaseId(dataService.findTable(tableId).getBaseId());
			data.setTableId(tableId);
		}
		// Process for Attach
		if (data.get(FieldsConstants.ATTACH) != null && !isNullOrEmpty((String) data.get(FieldsConstants.ATTACH))) {
			String[] attachsName = ((String) data.get(FieldsConstants.ATTACH)).split(CommonConstants.COMMA_SEPARATOR);
			if (attachsName != null && attachsName.length > 0) {
				List<String> attachsFile = new ArrayList<String>();
				for (String name : attachsName) {
					attachsFile.add(name);
				}
				model.addAttribute("attachList", attachsFile);
			}
		}
		model.addAttribute("data", DataingUtil.data(data));
		return "/admin/dataing/data/info";
	}

	@RequestMapping(value = "/search/{libraryId}", method = RequestMethod.POST)
	@ResponseBody
	public MappingJacksonValue search(@PathVariable int libraryId, @RequestBody DataTableArray[] dataArray,
			String callback, HttpServletRequest request) {
		// Parameter process
		DataTable dataTable = Containers.table(dataArray);
		Integer pageStart = dataTable.getiDisplayStart();
		Integer pageSize = dataTable.getiDisplayLength();
		String word = dataTable./* Search words */getsSearch();
		word = DataUtil./* Remove special char and XSS */remove(DataUtil.xss(word));
		ListResult<DataingData> dataList = null;
		if (isNullOrEmpty(word)) {
			dataList = searchingService.search(null, pageStart, pageSize, libraryId);
		} else {
			String[] newHightLightFields = /* Highlight the fields */ { FieldsConstants.AUTHORS, FieldsConstants.TITLE,
					FieldsConstants.SUMMARY };
			StringBuffer queryString = new StringBuffer();
			queryString.append("Title:").append(word).append(" OR Summary:").append(word).append(" OR Authors:")
					.append(word);
			dataList = searchingService.search(queryString.toString(), newHightLightFields, pageStart, pageSize,
					libraryId);
		}
		// Data process
		List<DataingData> list = (dataList.getData() != null && dataList.getData().size() > 0) ? dataList.getData()
				: new ArrayList<DataingData>();
		DataTableResult<DataingData> result = new DataTableResult<DataingData>(dataTable.getsEcho(),
				dataList.getTotal(), dataList.getTotal(), list);
		MappingJacksonValue mv = new MappingJacksonValue(result);
		mv.setJsonpFunction(callback);
		return mv;
	}

	@RequestMapping("/head/{libraryId}")
	@ResponseBody
	public List<DataingDataField> head(@PathVariable int libraryId) {
		List<DataingDataField> showDataFieldList = dataService.findDisplayFieldsByBaseId(libraryId);
		for (DataingDataField dataField : showDataFieldList) {
			if (FieldsConstants.TITLE.equals(dataField.getCode())) {
				showDataFieldList.remove(dataField);
				break;
			}
		}
		return showDataFieldList;
	}

	@RequestMapping("/new/{libraryId}")
	public String preNew(@PathVariable Integer libraryId, Model model) {
		List<DataingDataField> libraryFieldList = dataService.findFieldsByBaseId(libraryId);
		DataingData dataVo = new DataingData();
		dataVo.setUuid(uuid());
		dataVo.setBaseId(libraryId);
		dataVo.setCreateTime(format(new Date(), "yyyyMMdd"));
		StringBuilder sb = new StringBuilder(200);
		if (libraryFieldList != null && libraryFieldList.size() > 0) {
			for (DataingDataField dataField : libraryFieldList) {
				if (dataField.getAccessType() != EAccessType.System || dataField.getCode().equals("Keywords")) {
					dataVo.putFieldMap(dataField.getCode(), "");
					sb.append(dataField.getCode()).append(CommonConstants.COMMA_SEPARATOR);
				}
			}
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		dataVo.setFieldsString(sb.toString());
		model.addAttribute("dataVo", dataVo);
		model.addAttribute("baseId", libraryId);
		model.addAttribute("fieldsStr", sb.toString());
		return "/admin/dataing/data/edit";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public String save(@Validated final DataingData dataVo, BindingResult result, final Model model,
			HttpServletRequest request) {
		checkNotNull(dataVo.getBaseId(), "Database not null before insert database!");
		final MerchantsUserVO vo = commonService.user(request);
		String content = /* Not escapes string */DataUtil.unescape(dataVo.getFieldMap().get(FieldsConstants.CONTENT));
		/** Copy the image files from the temporary folder to the real path */
		String tmpPicPath = resourceService.tmpPic(dataVo.getUuid());
		List<File> /* If exist upload image files */ picTmpFiles = resourceService.files(tmpPicPath);
		if (picTmpFiles != null && picTmpFiles.size() > 0) {
			EResult picResult = resourceService.copyDirectoryToDirectory(
					/* Temporary folder */tmpPicPath,
					/* Real folder */resourceService.realPic(dataVo.getBaseId(), dataVo.getCreateTime()), true);
			if (picResult == EResult.Success) {
				content = resourceService.replace(content,
						/* Temporary relative folder */resourceService.tmpRelativePic(dataVo.getUuid()),
						/* Real relative folder */resourceService.realRelativePic(dataVo.getBaseId(),
								dataVo.getCreateTime()),
						true);
				resourceService./* Delete temporary images folder */deleteFolder(tmpPicPath);
			}
		}
		final String /* Real content */ realContent = content;
		/** Copy the documents from temporary folder to the real path */
		String tmpDocPath = resourceService.tmpDoc(dataVo.getUuid());
		List<File> docTmpFiles = resourceService.files(tmpDocPath);
		if (/* If upload document files */docTmpFiles != null && docTmpFiles.size() > 0) {
			EResult docResult = resourceService.copyDirectoryToDirectory(
					/* Temporary document folder */tmpDocPath,
					/* Real document folder */resourceService.realDoc(dataVo.getBaseId(), dataVo.getUuid()), false);
			if (docResult == EResult.Success) {
				resourceService./* Delete temporary document folder */deleteFolder(tmpDocPath);
			}
		}
		return super.save(dataVo, result, model, new Operator() {
			@Override
			public void operate() {
				DataingDataTable dataTable = /* Data table */(dataVo.getId() != null && dataVo.getId().intValue() > 0)
						? dataService.findTable(Integer.valueOf(dataVo.getTableId()))
						: dataService.findDataTableByBaseId(dataVo.getBaseId());
				DataingBaseData baseData = new DataingBaseData(dataVo.getId(), dataTable.getId(), dataTable.getName(),
						dataVo.getBaseId(), EDataStatus.Normal, "", "1.0");
				// Initialization value
				baseData.put(FieldsConstants.UUID, dataVo.getUuid());
				baseData.put(FieldsConstants.FINGER_PRINT, dataVo.getUuid());
				baseData.put(FieldsConstants.CONTENT,
						isNullOrEmpty(realContent) ? dataVo.getFieldMap().get(FieldsConstants.CONTENT) : realContent);
				baseData.put(FieldsConstants.IMGS, 0);
				baseData.put(FieldsConstants.DATA_STATUS, EDataStatus.Normal.ordinal());
				baseData.put(FieldsConstants.UPDATER_ID, vo.getId());
				baseData.put(FieldsConstants.UPDATE_TIME, new Date());
				baseData.put(FieldsConstants.TABLE_ID, dataTable.getId());
				if (dataVo.getId() == null) {
					baseData.put(FieldsConstants.CREATOR_ID, vo.getId());
					baseData.put(FieldsConstants.CREATE_TIME, new Date());
				}
				Map<String, DataingDataField> fieldMap = dataService.findFieldMapByBaseId(dataVo.getBaseId());
				for (/* Content from form */String field : dataVo.getFieldMap().keySet()) {
					if (FieldsConstants.CONTENT.equals(field)) {
						List<String> imgs = DataUtil.imgs(realContent);
						if (imgs != null && imgs.size() > 0) {
							baseData.put(FieldsConstants.IMGS, imgs.size());
						}
					} else {
						/* Set value */baseData.put(field, DataUtil.dataTypeObject(dataVo.getFieldMap().get(field),
								fieldMap.get(field).getDataType()));
					}
				}
				if (/* Set doc time */baseData.get(FieldsConstants.DOC_TIME) == null) {
					baseData.put(FieldsConstants.DOC_TIME, new Date());
				}
				List<File> /* Set attach files */ docList = resourceService
						.files(resourceService.realDoc(dataVo.getBaseId(), dataVo.getUuid()));
				if (docList != null && docList.size() > 0) {
					baseData.put(FieldsConstants.ATTACH, DataUtil.names(docList));
				}
				if (/* Set keywords */baseData.get(FieldsConstants.KEYWORDS) == null
						|| isNullOrEmpty(String.valueOf(baseData.get(FieldsConstants.KEYWORDS)))) {
					baseData.put(FieldsConstants.KEYWORDS,
							DataUtil.keyword(String.valueOf(baseData.get(FieldsConstants.CONTENT))));
				}
				if (/* Set summary */baseData.get(FieldsConstants.SUMMARY) == null
						|| isNullOrEmpty(String.valueOf(baseData.get(FieldsConstants.SUMMARY)))) {
					baseData.put(FieldsConstants.SUMMARY,
							DataUtil.summary(String.valueOf(baseData.get(FieldsConstants.CONTENT))));
				}
				dataService./* Save data */saveData(baseData);
			}

			@Override
			public String success() {
				return "redirect:/admin/dataing/library/data/search/" + dataVo.getBaseId();
			}

			@Override
			public String fail() {
				model.addAttribute("dataVo", dataVo);
				model.addAttribute("baseId", dataVo.getBaseId());
				model.addAttribute("fieldsStr", dataVo.getFieldsString());
				return "/admin/dataing/data/edit";
			}

			@Override
			public void error() {
			}
		});
	}
}
