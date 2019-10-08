package com.selene.templating.model.service;

import java.util.List;

import com.selene.templating.model.TemplatingItem;

/**
 * The {@code TemplatingItem} service for RPC
 * 
 * @author shaobo shih
 * @version 1.0
 */
public interface TemplatingItemService {
	public TemplatingItem find(Integer id);

	public List<TemplatingItem> findByModelId(Integer modelId);

	public TemplatingItem findByModelIdAndName(Integer modelId, String name);

	public int insert(TemplatingItem templatingItem);

	public int batchInsert(List<TemplatingItem> list);

	public int update(TemplatingItem templatingItem);

	public int delete(Integer id);

	public int deleteByModelId(Integer modelId);
}
