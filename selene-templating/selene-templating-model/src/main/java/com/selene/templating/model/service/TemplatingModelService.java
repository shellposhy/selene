package com.selene.templating.model.service;

import java.util.List;

import com.selene.templating.model.TemplatingModel;
import com.selene.templating.model.constants.EModelType;

/**
 * The {@code TemplatingModel} service for RPC
 * 
 * @author shaobo shih
 * @version 1.0
 */
public interface TemplatingModelService {
	public TemplatingModel find(Integer id);

	public List<TemplatingModel> findByLicense(String license);

	public List<TemplatingModel> findByLicenseAndBillId(String license, Integer billId);

	public List<TemplatingModel> findByLicenseAndType(String license, EModelType type);

	public List<TemplatingModel> findByPage(String license, Integer first, Integer size);

	public int count(String license);

	public List<TemplatingModel> findByNameAndPage(String license, String name, Integer first, Integer size);

	public int countByName(String license, String name);

	public int insert(TemplatingModel model);

	public int update(TemplatingModel model);

	public int delete(Integer id);
}
