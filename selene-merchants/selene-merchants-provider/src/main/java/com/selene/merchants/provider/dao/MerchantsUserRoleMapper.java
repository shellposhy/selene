package com.selene.merchants.provider.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.selene.merchants.model.MerchantsUserRole;

/**
 * Completes the mapping between the {@link MerchantsUserRole} Object and the
 * XML
 * 
 * @see DataingDataField
 * @author shaobo shih
 * @version 1.0
 */
public interface MerchantsUserRoleMapper {
	public int batchInsert(List<MerchantsUserRole> list);

	public List<Integer> findGroupIdsByUserId(@Param("userId") Integer userId);

	public List<Integer> findUserIdsByGroupId(@Param("groupId") Integer groupId);

	public List<Integer> findAdminGroupIdsByUserId(@Param("userId") Integer userId);

	public int deleteByUserId(@Param("userId") Integer userId);

	public int deleteByGroupId(@Param("groupId") Integer groupId);

}
