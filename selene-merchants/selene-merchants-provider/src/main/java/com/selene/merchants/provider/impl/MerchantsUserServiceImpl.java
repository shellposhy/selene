package com.selene.merchants.provider.impl;

import java.util.List;

import javax.annotation.Resource;

import com.papaya.common.extension.Rpc;
import com.selene.merchants.model.MerchantsUser;
import com.selene.merchants.model.enums.EActionUserType;
import com.selene.merchants.model.service.MerchantsUserService;
import com.selene.merchants.provider.dao.MerchantsUserMapper;

/**
 * The {@code MerchantsUserService} service implementation
 * <p>
 * the {@code Rpc} annotation is the tag for {@code ZooKeeper}
 * 
 * @see MerchantsUserService
 * @author shaobo shih
 * @version 1.0
 */
@Rpc(MerchantsUserService.class)
public class MerchantsUserServiceImpl implements MerchantsUserService {
	@Resource
	private MerchantsUserMapper merchantsUserMapper;

	@Override
	public int insert(MerchantsUser user) {
		return merchantsUserMapper.insert(user);
	}

	@Override
	public MerchantsUser find(Integer id) {
		return merchantsUserMapper.find(id);
	}

	@Override
	public MerchantsUser findByName(String name, EActionUserType type) {
		return merchantsUserMapper.findByName(name, type.ordinal());
	}

	@Override
	public MerchantsUser findByNamePassword(String name, String pass, EActionUserType type) {
		return merchantsUserMapper.findByNamePassword(name, pass, type.ordinal());
	}

	@Override
	public List<MerchantsUser> findByOrgId(Integer orgId, EActionUserType type, Integer firstSize, Integer size) {
		return merchantsUserMapper.findByOrgId(orgId, type.ordinal(), firstSize, size);
	}

	@Override
	public int countByOrgId(Integer orgId, EActionUserType type) {
		return merchantsUserMapper.countByOrgId(orgId, type.ordinal());
	}

	@Override
	public List<MerchantsUser> findAllByOrgId(Integer orgId, EActionUserType type) {
		return merchantsUserMapper.findAllByOrgId(orgId, type.ordinal());
	}

	@Override
	public List<MerchantsUser> findByOrgAndName(Integer orgId, String name, EActionUserType type, Integer firstSize,
			Integer size) {
		return merchantsUserMapper.findByOrgAndName(orgId, name, type.ordinal(), firstSize, size);
	}

	@Override
	public int countByOrgAndName(Integer orgId, String name, EActionUserType type) {
		return merchantsUserMapper.countByOrgAndName(orgId, name, type.ordinal());
	}

	@Override
	public List<Integer> findHaveRoleIdByUserAndOrg(Integer orgId) {
		return merchantsUserMapper.findHaveRoleIdByUserAndOrg(orgId);
	}

	@Override
	public int update(MerchantsUser user) {
		return merchantsUserMapper.update(user);
	}

	@Override
	public int petrify(MerchantsUser user) {
		return merchantsUserMapper.petrify(user);
	}

}
