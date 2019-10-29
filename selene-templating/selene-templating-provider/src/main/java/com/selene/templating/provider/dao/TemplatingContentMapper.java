package com.selene.templating.provider.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.selene.templating.model.TemplatingContent;

/**
 * Completes the mapping between the {@link TemplatingContent} Object and the
 * XML
 * 
 * @see DataingDataField
 * @author shaobo shih
 * @version 1.0
 */
public interface TemplatingContentMapper {
	public TemplatingContent find(@Param("id") Integer id);

	public List<TemplatingContent> findByPageId(@Param("pageId") Integer pageId);

	public TemplatingContent findByPageIdAndItemId(@Param("pageId") Integer pageId, @Param("itemId") Integer itemId);

	public int insert(TemplatingContent templatingContent);

	public int update(TemplatingContent templatingContent);

	public int delete(@Param("id") Integer id);

	public int deleteByPageId(@Param("pageId") Integer pageId);

	public int deleteByPageIdAndItemId(@Param("pageId") Integer pageId, @Param("itemId") Integer itemId);
}
