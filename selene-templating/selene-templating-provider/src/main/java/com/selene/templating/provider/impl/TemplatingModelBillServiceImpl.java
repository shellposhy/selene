package com.selene.templating.provider.impl;

import java.util.List;

import javax.annotation.Resource;

import com.papaya.common.extension.Rpc;
import com.selene.templating.model.TemplatingModelBill;
import com.selene.templating.model.service.TemplatingModelBillService;
import com.selene.templating.provider.dao.TemplatingModelBillMapper;

/**
 * The {@code TemplatingModelBillService} service implementation
 * <p>
 * the {@code Rpc} annotation is the tag for {@code ZooKeeper}
 * 
 * @see TemplatingModelBillService
 * @author shaobo shih
 * @version 1.0
 */
@Rpc(TemplatingModelBillService.class)
public class TemplatingModelBillServiceImpl implements TemplatingModelBillService {
	@Resource
	private TemplatingModelBillMapper templatingModelBillMapper;

	@Override
	public TemplatingModelBill find(Integer id) {
		return templatingModelBillMapper.find(id);
	}

	@Override
	public List<TemplatingModelBill> findByParentId(String license, Integer parentId) {
		return templatingModelBillMapper.findByParentId(license, parentId);
	}

	@Override
	public List<TemplatingModelBill> findByLicense(String license) {
		return templatingModelBillMapper.findByLicense(license);
	}

	@Override
	public int insert(TemplatingModelBill modelBill) {
		return templatingModelBillMapper.insert(modelBill);
	}

	@Override
	public int update(TemplatingModelBill modelBill) {
		return templatingModelBillMapper.update(modelBill);
	}

	@Override
	public int delete(Integer id) {
		return templatingModelBillMapper.delete(id);
	}

}
