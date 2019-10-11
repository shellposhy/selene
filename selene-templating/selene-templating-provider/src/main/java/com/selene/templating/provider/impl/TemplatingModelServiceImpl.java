package com.selene.templating.provider.impl;

import java.util.List;

import javax.annotation.Resource;

import com.papaya.common.extension.Rpc;
import com.selene.templating.model.TemplatingModel;
import com.selene.templating.model.constants.EModelType;
import com.selene.templating.model.service.TemplatingModelService;
import com.selene.templating.provider.dao.TemplatingModelMapper;

/**
 * The {@code TemplatingModelService} service implementation
 * <p>
 * the {@code Rpc} annotation is the tag for {@code ZooKeeper}
 * 
 * @see TemplatingModelService
 * @author shaobo shih
 * @version 1.0
 */
@Rpc(TemplatingModelService.class)
public class TemplatingModelServiceImpl implements TemplatingModelService {

	@Resource
	private TemplatingModelMapper templatingModelMapper;

	@Override
	public TemplatingModel find(Integer id) {
		return templatingModelMapper.find(id);
	}

	@Override
	public List<TemplatingModel> findByLicense(String license) {
		return templatingModelMapper.findByLicense(license);
	}

	@Override
	public List<TemplatingModel> findByLicenseAndBillId(String license, Integer billId) {
		return templatingModelMapper.findByLicenseAndBillId(license, billId);
	}

	@Override
	public List<TemplatingModel> findByLicenseAndType(String license, EModelType type) {
		return templatingModelMapper.findByLicenseAndType(license, type);
	}

	@Override
	public List<TemplatingModel> findByPage(String license, Integer first, Integer size) {
		return templatingModelMapper.findByPage(license, first, size);
	}

	@Override
	public int count(String license) {
		return templatingModelMapper.count(license);
	}

	@Override
	public List<TemplatingModel> findByNameAndPage(String license, String name, Integer first, Integer size) {
		return templatingModelMapper.findByNameAndPage(license, name, first, size);
	}

	@Override
	public int countByName(String license, String name) {
		return templatingModelMapper.countByName(license, name);
	}

	@Override
	public int insert(TemplatingModel model) {
		int result = templatingModelMapper.insert(model);
		return result > 0 ? /* Return model ID */model.getId() : result;
	}

	@Override
	public int update(TemplatingModel model) {
		return templatingModelMapper.update(model);
	}

	@Override
	public int delete(Integer id) {
		return templatingModelMapper.delete(id);
	}

}
