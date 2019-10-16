package com.selene.templating.provider.impl;

import java.util.List;

import javax.annotation.Resource;

import com.papaya.common.extension.Rpc;
import com.selene.templating.model.TemplatingPage;
import com.selene.templating.model.constants.EModelType;
import com.selene.templating.model.constants.EPageStatus;
import com.selene.templating.model.service.TemplatingPageService;
import com.selene.templating.provider.dao.TemplatingPageMapper;

/**
 * The {@code TemplatingPageService} service implementation
 * <p>
 * the {@code Rpc} annotation is the tag for {@code ZooKeeper}
 * 
 * @see TemplatingPageService
 * @author shaobo shih
 * @version 1.0
 */
@Rpc(TemplatingPageService.class)
public class TemplatingPageServiceImpl implements TemplatingPageService {

	@Resource
	private TemplatingPageMapper templatingPageMapper;

	@Override
	public TemplatingPage find(Integer id) {
		return templatingPageMapper.find(id);
	}

	@Override
	public List<TemplatingPage> findByLicense(String license) {
		return templatingPageMapper.findByLicense(license);
	}

	@Override
	public List<TemplatingPage> findByModelId(String license, Integer modelId) {
		return templatingPageMapper.findByModelId(license, modelId);
	}

	@Override
	public List<TemplatingPage> findByType(String license, EModelType pageType) {
		return templatingPageMapper.findByType(license, pageType);
	}

	@Override
	public List<TemplatingPage> findByStatus(String license, EPageStatus status) {
		return templatingPageMapper.findByStatus(license, status);
	}

	@Override
	public List<TemplatingPage> findByTypeAndStatus(String license, EModelType pageType, EPageStatus status) {
		return templatingPageMapper.findByTypeAndStatus(license, pageType, status);
	}

	@Override
	public int insert(TemplatingPage templatingPage) {
		return templatingPageMapper.insert(templatingPage);
	}

	@Override
	public int update(TemplatingPage templatingPage) {
		return templatingPageMapper.update(templatingPage);
	}

	@Override
	public int delete(Integer id) {
		return templatingPageMapper.delete(id);
	}

}
