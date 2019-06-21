package com.selene.merchants.model.service;

import java.util.List;

import com.selene.merchants.model.MerchantsRoleDataAuthority;

/**
 * The {@code MerchantsRoleDataAuthority} service for RPC
 * 
 * @author shaobo shih
 * @version 1.0
 */
public interface MerchantsRoleDataAuthorityService {
	public int batchInsert(List<MerchantsRoleDataAuthority> list);

	public List<MerchantsRoleDataAuthority> findByGroupId(Integer groupId, Integer type);

	public int deleteByGroupId(Integer groupId);
}
