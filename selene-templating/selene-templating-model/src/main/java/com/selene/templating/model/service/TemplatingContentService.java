package com.selene.templating.model.service;

import java.util.List;

import com.selene.templating.model.TemplatingContent;

/**
 * The {@code TemplatingContent} service for RPC
 * 
 * @author shaobo shih
 * @version 1.0
 */
public interface TemplatingContentService {
	public TemplatingContent find(Integer id);

	public List<TemplatingContent> findByPageId(Integer pageId);

	public TemplatingContent findByPageIdAndItemId(Integer pageId, Integer itemId);

	public int insert(TemplatingContent templatingContent);

	public int update(TemplatingContent templatingContent);

	public int delete(Integer id);

	public int deleteByPageId(Integer pageId);

	public int deleteByItemId(Integer itemId);

	public int deleteByPageIdAndItemId(Integer pageId, Integer itemId);
}
