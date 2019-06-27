package com.selene.merchants.provider.impl;

import java.util.List;

import javax.annotation.Resource;

import com.papaya.common.extension.Rpc;
import com.selene.merchants.model.MerchantsUserRole;
import com.selene.merchants.model.service.MerchantsUserRoleService;
import com.selene.merchants.provider.dao.MerchantsUserRoleMapper;

/**
 * The {@code MerchantsUserRoleService} service implementation
 * <p>
 * the {@code Rpc} annotation is the tag for {@code ZooKeeper}
 * 
 * @see MerchantsUserRoleService
 * @author shaobo shih
 * @version 1.0
 */
@Rpc(MerchantsUserRoleService.class)
public class MerchantsUserRoleServiceImpl implements MerchantsUserRoleService {
	@Resource
	private MerchantsUserRoleMapper merchantsUserRoleMapper;

	@Override
	public int batchInsert(List<MerchantsUserRole> list) {
		return merchantsUserRoleMapper.batchInsert(list);
	}

	@Override
	public List<Integer> findGroupIdsByUserId(Integer userId) {
		return merchantsUserRoleMapper.findGroupIdsByUserId(userId);
	}

	@Override
	public List<Integer> findAdminGroupIdsByUserId(Integer userId) {
		return merchantsUserRoleMapper.findAdminGroupIdsByUserId(userId);
	}

	@Override
	public List<Integer> findUserIdsByGroupId(Integer groupId) {
		return merchantsUserRoleMapper.findUserIdsByGroupId(groupId);
	}

	@Override
	public int deleteByUserId(Integer userId) {
		return merchantsUserRoleMapper.deleteByUserId(userId);
	}

	@Override
	public int deleteByGroupId(Integer groupId) {
		return merchantsUserRoleMapper.deleteByGroupId(groupId);
	}

}
