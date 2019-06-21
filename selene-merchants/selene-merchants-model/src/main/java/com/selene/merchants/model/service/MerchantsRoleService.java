package com.selene.merchants.model.service;

import java.util.List;

import com.selene.merchants.model.MerchantsRole;

/**
 * The {@code MerchantsRole} service for RPC
 * 
 * @author shaobo shih
 * @version 1.0
 */
public interface MerchantsRoleService {
	public int insert(MerchantsRole role);

	public MerchantsRole find(Integer id);

	public List<MerchantsRole> findByName(String name);

	public List<MerchantsRole> findByCode(String code);

	public List<MerchantsRole> findByUserId(Integer userId);

	public List<MerchantsRole> findPageByName(String name, Integer first, Integer size);

	public int countByName(String name);

	public int batchDelete(List<Integer> list);

	public int delete(Integer id);

	public int update(MerchantsRole role);
}
