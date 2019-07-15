package com.selene.merchants.provider.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.selene.merchants.model.MerchantsOrgRole;

/**
 * Completes the mapping between the {@link MerchantsOrgRole} Object and the XML
 * 
 * @see DataingDataField
 * @author shaobo shih
 * @version 1.0
 */
public interface MerchantsOrgRoleMapper {
	public int insert(MerchantsOrgRole orgRole);

	public int batchInsert(List<MerchantsOrgRole> list);

	public List<Integer> findGroupIdsByOrgId(@Param("orgId") Integer orgId);

	public List<MerchantsOrgRole> findByGroupId(@Param("groupId") Integer groupId);

	public int deleteByOrgId(@Param("orgId") Integer orgId);

	public int deleteByGroupId(@Param("groupId") Integer groupId);
}
