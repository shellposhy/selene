package com.selene.templating.provider.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.selene.templating.model.TemplatingPage;
import com.selene.templating.model.constants.EModelType;
import com.selene.templating.model.constants.EPageStatus;

/**
 * Completes the mapping between the {@link TemplatingPage} Object and the XML
 * 
 * @see DataingDataField
 * @author shaobo shih
 * @version 1.0
 */
public interface TemplatingPageMapper {
	public TemplatingPage find(@Param("id") Integer id);

	public List<TemplatingPage> findByLicense(@Param("license") String license);

	public List<TemplatingPage> findByModelId(@Param("license") String license, @Param("modelId") Integer modelId);

	public List<TemplatingPage> findByType(@Param("license") String license, @Param("pageType") EModelType pageType);

	public List<TemplatingPage> findByStatus(@Param("license") String license,
			@Param("pageStatus") EPageStatus pageStatus);

	public List<TemplatingPage> findByTypeAndStatus(@Param("license") String license,
			@Param("pageType") EModelType pageType, @Param("pageStatus") EPageStatus pageStatus);

	public List<TemplatingPage> findByParentId(@Param("license") String license, @Param("parentId") Integer parentId);

	public int insert(TemplatingPage templatingPage);

	public int update(TemplatingPage templatingPage);

	public int delete(@Param("id") Integer id);
}
