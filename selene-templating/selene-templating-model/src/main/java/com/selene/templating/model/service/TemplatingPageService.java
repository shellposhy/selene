package com.selene.templating.model.service;

import java.util.List;

import com.selene.templating.model.TemplatingPage;
import com.selene.templating.model.constants.EModelType;
import com.selene.templating.model.constants.EPageStatus;

/**
 * The {@code TemplatingPage} service for RPC
 * 
 * @author shaobo shih
 * @version 1.0
 */
public interface TemplatingPageService {
	public TemplatingPage find(Integer id);

	public List<TemplatingPage> findByLicense(String license);

	public List<TemplatingPage> findByModelId(String license, Integer modelId);

	public List<TemplatingPage> findByType(String license, EModelType pageType);

	public List<TemplatingPage> findByStatus(String license, EPageStatus status);

	public List<TemplatingPage> findByTypeAndStatus(String license, EModelType pageType, EPageStatus status);

	public int insert(TemplatingPage templatingPage);

	public int update(TemplatingPage templatingPage);

	public int delete(Integer id);
}
