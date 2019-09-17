package com.selene.searching.model.service;

import java.util.Collection;

import org.apache.lucene.document.Document;

import com.pepper.lucene.common.PepperResult;
import com.pepper.lucene.comparator.base.PepperSortField;

/**
 * Search engine basic service for RPC
 * 
 * @author shaobo shih
 * @version 1.0
 */
public interface SearchingIndexService {
	public boolean add(Document doc, String... paths);

	public boolean adds(Collection<Document> docs, String... paths);

	public boolean delete(String queryString, String... paths);

	public boolean delete(String uuid, String path);

	public PepperResult search(String queryString, Integer numHits, PepperSortField[] sortFields,
			String[] hightLightFields, Integer firstResult, Integer maxResults, String... paths);

	public String indexPath(Integer baseId);

	public String[] indexPaths(Integer... baseIds);
}
