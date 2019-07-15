package com.selene.merchants.provider.impl;

import java.util.List;

import javax.annotation.Resource;

import com.papaya.common.extension.Rpc;
import com.selene.merchants.model.MerchantsOrgRole;
import com.selene.merchants.model.service.MerchantsOrgRoleService;
import com.selene.merchants.provider.dao.MerchantsOrgRoleMapper;

/**
 * The {@code MerchantsOrgRoleService} service implementation
 * <p>
 * the {@code Rpc} annotation is the tag for {@code ZooKeeper}
 * 
 * @see MerchantsOrgRoleService
 * @author shaobo shih
 * @version 1.0
 */
@Rpc(MerchantsOrgRoleService.class)
public class MerchantsOrgRoleServiceImpl implements MerchantsOrgRoleService {

	@Resource
	private MerchantsOrgRoleMapper merchantsOrgRoleMapper;

	@Override
	public int insert(MerchantsOrgRole orgRole) {
		return merchantsOrgRoleMapper.insert(orgRole);
	}

	@Override
	public int batchInsert(List<MerchantsOrgRole> list) {
		return merchantsOrgRoleMapper.batchInsert(list);
	}

	@Override
	public List<Integer> findGroupIdsByOrgId(Integer orgId) {
		return merchantsOrgRoleMapper.findGroupIdsByOrgId(orgId);
	}

	@Override
	public List<MerchantsOrgRole> findByGroupId(Integer groupId) {
		return merchantsOrgRoleMapper.findByGroupId(groupId);
	}

	@Override
	public int deleteByOrgId(Integer orgId) {
		return merchantsOrgRoleMapper.deleteByOrgId(orgId);
	}

	@Override
	public int deleteByGroupId(Integer groupId) {
		return merchantsOrgRoleMapper.deleteByGroupId(groupId);
	}

}
