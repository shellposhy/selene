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
	public int insert(MerchantsOrg merchantsOrg) {
		int result = merchantsOrgMapper.insert(merchantsOrg);
		return result > 0 ? merchantsOrg.getId() : result;
	}

	@Override
	public MerchantsOrg find(Integer id) {
		return merchantsOrgMapper.find(id);
	}

	@Override
	public List<MerchantsOrg> findByParentId(Integer parentId, String license) {
		return merchantsOrgMapper.findByParentId(parentId, license);
	}

	@Override
	public List<MerchantsOrg> findByCodeAndParentId(Integer parentId, String code, String license) {
		return merchantsOrgMapper.findByCodeAndParentId(parentId, code, license);
	}

	@Override
	public List<MerchantsOrg> findByLicense(String license) {
		return merchantsOrgMapper.findByLicense(license);
	}

	@Override
	public String findOrgLicenseByUserId(Integer userId) {
		return merchantsOrgMapper.findOrgLicenseByUserId(userId);
	}

	@Override
	public int update(MerchantsOrg merchantsOrg) {
		return merchantsOrgMapper.update(merchantsOrg);
	}

	@Override
	public int delete(Integer id) {
		return merchantsOrgMapper.delete(id);
	}

	@Override
	public List<MerchantsOrg> findAll(Boolean type) {
		return merchantsOrgMapper.findAll();
	}

}
