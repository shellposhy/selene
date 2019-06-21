package com.selene.merchants.provider.impl;

import java.util.List;

import javax.annotation.Resource;

import com.papaya.common.extension.Rpc;
import com.selene.merchants.model.MerchantsRoleDataAuthority;
import com.selene.merchants.model.service.MerchantsRoleDataAuthorityService;
import com.selene.merchants.provider.dao.MerchantsRoleDataAuthorityMapper;

/**
 * The {@code MerchantsRoleDataAuthorityService} service implementation
 * <p>
 * the {@code Rpc} annotation is the tag for {@code ZooKeeper}
 * 
 * @see MerchantsRoleDataAuthorityService
 * @author shaobo shih
 * @version 1.0
 */
@Rpc(MerchantsRoleDataAuthorityService.class)
public class MerchantsRoleDataAuthorityServiceImpl implements MerchantsRoleDataAuthorityService {
	@Resource
	private MerchantsRoleDataAuthorityMapper merchantsRoleDataAuthorityMapper;

	@Override
	public int batchInsert(List<MerchantsRoleDataAuthority> list) {
		return merchantsRoleDataAuthorityMapper.batchInsert(list);
	}

	@Override
	public List<MerchantsRoleDataAuthority> findByGroupId(Integer groupId, Integer type) {
		return merchantsRoleDataAuthorityMapper.findByGroupId(groupId, type);
	}

	@Override
	public int deleteByGroupId(Integer groupId) {
		return merchantsRoleDataAuthorityMapper.deleteByGroupId(groupId);
	}

}
