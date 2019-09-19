package com.selene.viewing.admin.service.searching;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.lucene.document.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nanshan.papaya.rpc.client.Client;
import com.pepper.lucene.common.PepperResult;
import com.pepper.lucene.comparator.base.PepperSortField;
import com.selene.common.HttpStatus;
import com.selene.common.config.service.ServiceConfiger;
import com.selene.common.constants.CommonConstants;
import com.selene.common.constants.FieldsConstants;
import com.selene.common.constants.ServiceConstants;
import com.selene.common.constants.util.EDataType;
import com.selene.common.result.ListResult;
import com.selene.common.util.DataUtil;
import com.selene.common.util.RedisClient;
import com.selene.dataing.model.jdbc.DataingData;
import com.selene.dataing.model.util.DataingUtil;
import com.selene.searching.model.service.SearchingIndexService;

import static cn.com.lemon.base.Strings.isNullOrEmpty;

@Service
public class SearchingService {
	private final static Logger LOG = LoggerFactory.getLogger(SearchingService.class.getName());
	private ServiceConfiger searchingConfiger;
	private Map<String, Object> services = new HashMap<String, Object>();
	@Resource
	private Client client;
	@Resource
	private RedisClient redisClient;

	@PostConstruct
	@SuppressWarnings("static-access")
	public void init() {
		LOG.info("[selene-viewing-admin] init " + ServiceConfiger.class.getName() + " service!");
		// Initialization searching service registry address
		searchingConfiger = new ServiceConfiger(
				SearchingService.class.getResource("/").getPath() + "selene.sevice.properties");
		String searchingService = searchingConfiger.value(ServiceConstants.SEARCHING_KEY);
		LOG.info("searching service address=" + searchingService);
		// Initialization searching service
		services.put(SearchingIndexService.class.getName(),
				client.create(SearchingIndexService.class, searchingService));
	}

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
		SearchingIndexService searchingIndexService = (SearchingIndexService) services
				.get(SearchingIndexService.class.getName());
		// Business process
		String[] paths = /* Index path */searchingIndexService.indexPaths(baseIds);
		PepperSortField[] /* Results sort by date decrease */ newSortFields = {
				new PepperSortField(FieldsConstants.DOC_TIME, DataUtil.sortLuceneType(EDataType.DateTime), true) };
		if (sortFields == null || sortFields.length == 0) {
			sortFields = newSortFields;
		}
		String[] newHightLightFields = /* Highlight the fields */ { FieldsConstants.AUTHORS, FieldsConstants.TITLE,
				FieldsConstants.CONTENT };
		if ((hightLightFields == null || hightLightFields.length == 0) && !isNullOrEmpty(queryString)) {
			hightLightFields = newHightLightFields;
		}
		queryString = isNullOrEmpty(queryString) ? /* Search all */CommonConstants.SEARCH_INDEX_ALL : queryString;
		PepperResult docResult = null;
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
		SearchingIndexService searchingIndexService = (SearchingIndexService) services
				.get(SearchingIndexService.class.getName());
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
		SearchingIndexService searchingIndexService = (SearchingIndexService) services
				.get(SearchingIndexService.class.getName());
		// Business process
		if (searchingIndexService.delete(uuid, searchingIndexService.indexPath(baseId))) {
			return searchingIndexService.add(doc, searchingIndexService.indexPath(baseId));
		}
		return false;
	}
}
