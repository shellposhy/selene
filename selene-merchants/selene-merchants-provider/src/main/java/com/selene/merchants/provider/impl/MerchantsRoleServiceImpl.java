package com.selene.merchants.provider.impl;

import java.util.List;

import javax.annotation.Resource;

import com.papaya.common.extension.Rpc;
import com.selene.merchants.model.MerchantsRole;
import com.selene.merchants.model.service.MerchantsRoleService;
import com.selene.merchants.provider.dao.MerchantsRoleMapper;

/**
 * The {@code MerchantsRoleService} service implementation
 * <p>
 * the {@code Rpc} annotation is the tag for {@code ZooKeeper}
 * 
 * @see MerchantsRoleService
 * @author shaobo shih
 * @version 1.0
 */
@Rpc(MerchantsRoleService.class)
public class MerchantsRoleServiceImpl implements MerchantsRoleService {
	@Resource
	private MerchantsRoleMapper merchantsRoleMapper;

	@Override
	public int insert(MerchantsRole role) {
		int result = merchantsRoleMapper.insert(role);
		return result > 0 ? role.getId() : result;
	}

	@Override
	public MerchantsRole find(Integer id) {
		return merchantsRoleMapper.find(id);
	}

	@Override
	public List<MerchantsRole> findByName(String name, String license) {
		return merchantsRoleMapper.findByName(name, license);
	}

	@Override
	public List<MerchantsRole> findByCode(String code, String license) {
		return merchantsRoleMapper.findByCode(code, license);
	}

	@Override
	public List<MerchantsRole> findByUserId(Integer userId, String license) {
		return merchantsRoleMapper.findByUserId(userId, license);
	}

	@Override
	public List<MerchantsRole> findPageByName(String name, String license, Integer first, Integer size) {
		return merchantsRoleMapper.findPageByName(name, license, first, size);
	}

	@Override
	public int countByName(String name, String license) {
		return merchantsRoleMapper.countByName(name, license);
	}

	@Override
	public List<MerchantsRole> findByLicense(String license) {
		return merchantsRoleMapper.findByLicense(license);
	}

	@Override
	public int batchDelete(List<Integer> list) {
		return merchantsRoleMapper.batchDelete(list);
	}

	@Override
	public int delete(Integer id) {
		return merchantsRoleMapper.delete(id);
	}

	@Override
	public int update(MerchantsRole role) {
		return merchantsRoleMapper.update(role);
	}

}
