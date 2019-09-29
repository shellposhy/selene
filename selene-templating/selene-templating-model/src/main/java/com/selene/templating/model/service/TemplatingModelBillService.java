package com.selene.templating.model.service;

import java.util.List;

import com.selene.templating.model.TemplatingModelBill;

/**
 * The {@code TemplatingModelBill} service for RPC
 * 
 * @author shaobo shih
 * @version 1.0
 */
public interface TemplatingModelBillService {
	public TemplatingModelBill find(Integer id);

	public List<TemplatingModelBill> findByParentId(String license, Integer parentId);

	public List<TemplatingModelBill> findByLicense(String license);

	public int insert(TemplatingModelBill modelBill);

	public int update(TemplatingModelBill modelBill);

	public int delete(Integer id);
}
