package com.selene.merchants.model.service;

import java.util.List;

import com.selene.merchants.model.MerchantsUserRole;

/**
 * The {@code MerchantsUserRole} service for RPC
 * 
 * @author shaobo shih
 * @version 1.0
 */
public interface MerchantsUserRoleService {
	public int batchInsert(List<MerchantsUserRole> list);

	public List<Integer> findGroupIdsByUserId(Integer userId);

	public List<Integer> findUserIdsByGroupId(Integer groupId);

	public int deleteByUserId(Integer userId);

	public int deleteByGroupId(Integer groupId);
}
