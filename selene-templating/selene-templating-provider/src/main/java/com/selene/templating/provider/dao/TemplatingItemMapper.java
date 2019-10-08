package com.selene.templating.provider.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.selene.templating.model.TemplatingItem;

/**
 * Completes the mapping between the {@link TemplatingItem} Object and the XML
 * 
 * @see DataingDataField
 * @author shaobo shih
 * @version 1.0
 */
public interface TemplatingItemMapper {
	public TemplatingItem find(@Param("id") Integer id);

	public List<TemplatingItem> findByModelId(@Param("modelId") Integer modelId);

	public TemplatingItem findByModelIdAndName(@Param("modelId") Integer modelId, @Param("name") String name);

	public int insert(TemplatingItem templatingItem);

	public int batchInsert(List<TemplatingItem> list);

	public int update(TemplatingItem templatingItem);

	public int delete(@Param("id") Integer id);

	public int deleteByModelId(@Param("modelId") Integer modelId);
}
