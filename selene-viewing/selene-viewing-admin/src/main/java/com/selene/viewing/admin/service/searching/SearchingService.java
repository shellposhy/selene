package com.selene.viewing.admin.service.searching;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.lucene.document.Document;
import org.springframework.stereotype.Service;

import com.pepper.lucene.common.PepperResult;
import com.pepper.lucene.comparator.base.PepperSortField;
import com.selene.common.HttpStatus;
import com.selene.common.constants.CommonConstants;
import com.selene.common.constants.FieldsConstants;
import com.selene.common.constants.ServiceConstants;
import com.selene.common.constants.util.EDataType;
import com.selene.common.result.ListResult;
import com.selene.common.util.DataUtil;
import com.selene.common.util.RedisClient;
import com.selene.dataing.model.jdbc.DataingData;
import com.selene.dataing.model.util.DataingUtil;
import com.selene.logging.model.Logger;
import com.selene.logging.model.LoggerFactory;
import com.selene.searching.model.service.SearchingIndexService;
import com.selene.viewing.admin.service.config.ServiceConfigure;

import static cn.com.lemon.base.Strings.isNullOrEmpty;

@Service
public class SearchingService {
	private final static Logger LOG = LoggerFactory.getLogger(SearchingService.class.getName());
	@Resource
	private RedisClient redisClient;
	@Resource
	private ServiceConfigure serviceConfigure;

	/**
	 * Query search index
	 * 
	 * @param queryString
	 * @param numHits
	 * @param sortFields
	 * @param hightLightFields
	 * @param first
	 * @param size
	 * @param baseIds
	 * @return {@code ListResult}
	 */
	public ListResult<DataingData> search(String queryString, Integer first, Integer size, Integer... baseIds) {
		return search(queryString, CommonConstants.DEFAULT_SEARCH_INDEX_NUMBER_HITS, null, null, first, size, baseIds);
	}

	public ListResult<DataingData> search(String queryString, PepperSortField[] sortFields, Integer first, Integer size,
			Integer... baseIds) {
		return search(queryString, CommonConstants.DEFAULT_SEARCH_INDEX_NUMBER_HITS, sortFields, null, first, size,
				baseIds);
	}

	public ListResult<DataingData> search(String queryString, String[] hightLightFields, Integer first, Integer size,
			Integer... baseIds) {
		return search(queryString, CommonConstants.DEFAULT_SEARCH_INDEX_NUMBER_HITS, null, hightLightFields, first,
				size, baseIds);
	}

	public ListResult<DataingData> search(String queryString, PepperSortField[] sortFields, String[] hightLightFields,
			Integer first, Integer size, Integer... baseIds) {
		return search(queryString, CommonConstants.DEFAULT_SEARCH_INDEX_NUMBER_HITS, sortFields, hightLightFields,
				first, size, baseIds);
	}

	public ListResult<DataingData> search(String queryString, Integer numHits, PepperSortField[] sortFields,
			String[] hightLightFields, Integer first, Integer size, Integer... baseIds) {
		ListResult<DataingData> result = new ListResult<DataingData>(HttpStatus.OK.code(), HttpStatus.OK.message());
		result.setPageSize(size);
		// Initialize the required services
		SearchingIndexService searchingIndexService = serviceConfigure.service(SearchingIndexService.class,
				SearchingIndexService.class.getName(), ServiceConstants.SEARCHING_KEY);
		// Business process
		String[] paths = /* Index path */searchingIndexService.indexPaths(baseIds);
		PepperSortField[] /* Results sort by date decrease */ newSortFields = {
				new PepperSortField(FieldsConstants.DOC_TIME, DataUtil.sortLuceneType(EDataType.DateTime), true) };
		if (sortFields == null || sortFields.length == 0) {
			sortFields = newSortFields;
		}
		String[] newHightLightFields = /* Highlight the fields */ { FieldsConstants.AUTHORS, FieldsConstants.TITLE,
				FieldsConstants.CONTENT, FieldsConstants.SUMMARY };
		if ((hightLightFields == null || hightLightFields.length == 0) && !isNullOrEmpty(queryString)) {
			hightLightFields = newHightLightFields;
		}
		queryString = isNullOrEmpty(queryString) ? /* Search all */CommonConstants.SEARCH_INDEX_ALL : queryString;
		PepperResult docResult = null;
		LOG.info("queryString=" + queryString);
		try {
			docResult = /* Searching result */searchingIndexService.search(queryString, numHits, newSortFields,
					newHightLightFields, first, size, paths);
			if (docResult != null && docResult.documents != null && docResult.documents.length > 0) {
				result.setTotal(docResult.totalHits);
				List<DataingData> dataList = new ArrayList<DataingData>();
				for (Document doc : /* Result process */docResult.documents) {
					DataingData data = DataingUtil.data(doc);
					dataList.add(data);
				}
				result.setData(dataList);
			} else {
				result.setTotal(0);
				result.setData(null);
			}
		} catch (Exception e) {
			result.setTotal(0);
			result.setData(null);
		}
		return result;
	}

	/**
	 * Save index
	 * 
	 * @param doc
	 * @param baseId
	 * @return
	 */
	public boolean index(Document doc, Integer baseId) {
		// Initialize the required services
		SearchingIndexService searchingIndexService = serviceConfigure.service(SearchingIndexService.class,
				SearchingIndexService.class.getName(), ServiceConstants.SEARCHING_KEY);
		// Business process
		return searchingIndexService.add(doc, searchingIndexService.indexPath(baseId));
	}

	/**
	 * Update index
	 * 
	 * @param doc
	 * @param baseId
	 * @param uuid
	 * @return
	 */
	public boolean update(Document doc, Integer baseId, String uuid) {
		// Initialize the required services
		SearchingIndexService searchingIndexService = serviceConfigure.service(SearchingIndexService.class,
				SearchingIndexService.class.getName(), ServiceConstants.SEARCHING_KEY);
		// Business process
		if (searchingIndexService.delete(uuid, searchingIndexService.indexPath(baseId))) {
			return searchingIndexService.add(doc, searchingIndexService.indexPath(baseId));
		}
		return false;
	}
}
