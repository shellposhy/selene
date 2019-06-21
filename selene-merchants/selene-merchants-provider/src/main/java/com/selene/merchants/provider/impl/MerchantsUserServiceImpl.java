package com.selene.merchants.provider.impl;

import java.util.List;

import javax.annotation.Resource;

import com.papaya.common.extension.Rpc;
import com.selene.merchants.model.MerchantsUser;
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
	public MerchantsUser findByName(String name) {
		return merchantsUserMapper.findByName(name);
	}

	@Override
	public MerchantsUser findByNamePassword(String name, String pass) {
		return merchantsUserMapper.findByNamePassword(name, pass);
	}

	@Override
	public List<MerchantsUser> findByOrgId(Integer orgId) {
		return merchantsUserMapper.findByOrgId(orgId);
	}

	@Override
	public int countByOrgAndName(Integer orgId, String name) {
		return merchantsUserMapper.countByOrgAndName(orgId, name);
	}

	@Override
	public int findByOrgAndName(Integer orgId, String name, Integer firstSize, Integer size) {
		return merchantsUserMapper.findByOrgAndName(orgId, name, firstSize, size);
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
