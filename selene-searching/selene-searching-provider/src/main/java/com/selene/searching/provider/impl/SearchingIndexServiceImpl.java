package com.selene.searching.provider.impl;

import java.util.Collection;

import javax.annotation.Resource;

import org.apache.lucene.document.Document;

import com.papaya.common.extension.Rpc;
import com.pepper.lucene.common.PepperResult;
import com.pepper.lucene.comparator.base.PepperSortField;
import com.pepper.spring.service.PepperService;
import com.selene.searching.model.service.SearchingIndexService;

@Rpc(SearchingIndexService.class)
public class SearchingIndexServiceImpl implements SearchingIndexService {

	@Resource
	private PepperService pepperService;

	@Override
	public boolean add(Document doc, String... paths) {
		return pepperService.getIndexer().index(doc, paths);
	}

	@Override
	public boolean adds(Collection<Document> docs, String... paths) {
		try {
			for (Document doc : docs) {
				pepperService.getIndexer().index(doc, paths);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean delete(String queryString, String... paths) {
		return pepperService.getIndexer().delete(queryString, paths);
	}

	@Override
	public boolean delete(String uuid, String path) {
		return pepperService.getIndexer().delete(uuid, path);
	}

	@Override
	public PepperResult search(String queryString, Integer numHits, PepperSortField[] sortFields,
			String[] hightLightFields, Integer first, Integer size, String... paths) {
		return pepperService.getIndexer().search(queryString, numHits, sortFields, hightLightFields, first, size,
				paths);
	}

	@Override
	public String indexPath(Integer baseId) {
		String rootPath = pepperService.getIndexAddress();
		return rootPath.endsWith("/") ? rootPath + baseId : rootPath + "/" + baseId;
	}

	@Override
	public String[] indexPaths(Integer... baseIds) {
		String[] result = new String[baseIds.length];
		String rootPath = pepperService.getIndexAddress();
		for (int i = 0; i < baseIds.length; i++) {
			result[i] = rootPath.endsWith("/") ? rootPath + baseIds[i] : rootPath + "/" + baseIds[i];
		}
		return result;
	}
}
