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

import com.selene.common.HttpStatus;
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
import com.selene.viewing.admin.controller.BaseController;
import com.selene.viewing.admin.service.ResourceService;
import com.selene.viewing.admin.service.TokenService;
import com.selene.viewing.admin.service.dataing.DataService;
import com.selene.viewing.admin.vo.merchants.MerchantsUserVO;

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
	private TokenService commonService;
	@Resource
	private ResourceService resourceService;

	@SuppressWarnings("unused")
	@RequestMapping(value = "/search/{libraryId}", method = RequestMethod.POST)
	@ResponseBody
	public MappingJacksonValue search(@PathVariable int libraryId, @RequestBody DataTableArray[] dataArray,
			String callback, HttpServletRequest request) {
		// Parameter process
		DataTable dataTable = Containers.table(dataArray);
		Integer pageSize = dataTable.getiDisplayLength();
		Integer pageStart = dataTable.getiDisplayStart();
		// Data process
		ListResult<DataingData> dataList = new ListResult<DataingData>(HttpStatus.OK.code(), HttpStatus.OK.message());
		DataTableResult<DataingData> result = new DataTableResult<>(dataTable.getsEcho(), 0, 0,
				new ArrayList<DataingData>());
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
		/** Copy the image files from the temporary folder to the real path */
		String tmpPicPath = resourceService.tmpPic(dataVo.getUuid());
		EResult picResult = resourceService.copyDirectoryToDirectory(
				/* Temporary folder */tmpPicPath,
				/* Real folder */resourceService.realPic(dataVo.getBaseId(), dataVo.getCreateTime()), true);
		String content = "";
		if (picResult == EResult.Success) {
			content = resourceService.replace(dataVo.getFieldMap().get(FieldsConstants.CONTENT),
					/* Temporary relative folder */resourceService.tmpRelativePic(dataVo.getUuid()),
					/* Real relative folder */resourceService.realRelativePic(dataVo.getBaseId(),
							dataVo.getCreateTime()),
					true);
			resourceService./* Delete temporary images folder */deleteFolder(tmpPicPath);
		}
		final String /* Real content */ realContent = content;

		/** Copy the documents from temporary folder to the real path */
		String tmpDocPath = resourceService.tmpDoc(dataVo.getUuid());
		List<File> docTmpFiles = resourceService.files(tmpDocPath);
		if (/* If upload document files */docTmpFiles != null && docTmpFiles.size() > 0) {
			EResult docResult = resourceService.copyDirectoryToDirectory(
					/* Temporary document folder */tmpDocPath,
					/* Real document folder */resourceService.realDoc(dataVo.getBaseId(), dataVo.getCreateTime()),
					false);
			if (docResult == EResult.Success) {
				resourceService./* Delete temporary document folder */deleteFolder(tmpDocPath);
			}
		}
		return super.save(dataVo, result, model, new Operator() {
			@Override
			public void operate() {
				DataingDataTable dataTable = /* Data table */dataService.findDataTableByBaseId(dataVo.getBaseId());
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
						.files(resourceService.realDoc(dataVo.getBaseId(), dataVo.getCreateTime()));
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