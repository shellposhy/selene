package com.selene.merchants.provider.impl;

import java.util.List;

import javax.annotation.Resource;

import com.papaya.common.extension.Rpc;
import com.selene.merchants.model.MerchantsOrg;
import com.selene.merchants.model.service.MerchantsOrgService;
import com.selene.merchants.provider.dao.MerchantsOrgMapper;

/**
 * The {@code MerchantsOrgService} service implementation
 * <p>
 * the {@code Rpc} annotation is the tag for {@code ZooKeeper}
 * 
 * @see MerchantsOrgService
 * @author shaobo shih
 * @version 1.0
 */
@Rpc(MerchantsOrgService.class)
public class MerchantsOrgServiceImpl implements MerchantsOrgService {
	@Resource
	private MerchantsOrgMapper merchantsOrgMapper;

	@Override
	public int insert(MerchantsOrg org) {
		return merchantsOrgMapper.insert(org);
	}

	@Override
	public MerchantsOrg find(Integer id) {
		return merchantsOrgMapper.find(id);
	}

	@Override
	public List<MerchantsOrg> findByParentId(Integer parentId) {
		return merchantsOrgMapper.findByParentId(parentId);
	}

	@Override
	public List<MerchantsOrg> findByCodeAndParentId(Integer parentId, String code) {
		return merchantsOrgMapper.findByCodeAndParentId(parentId, code);
	}

	@Override
	public int update(MerchantsOrg org) {
		return merchantsOrgMapper.update(org);
	}

	@Override
	public int delete(Integer id) {
		return merchantsOrgMapper.delete(id);
	}

}
