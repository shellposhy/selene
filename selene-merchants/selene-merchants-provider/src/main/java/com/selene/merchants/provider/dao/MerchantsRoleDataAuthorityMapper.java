package com.selene.merchants.provider.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.selene.merchants.model.MerchantsRoleDataAuthority;

/**
 * Completes the mapping between the {@link MerchantsRoleDataAuthority} Object
 * and the XML
 * 
 * @see DataingDataField
 * @author shaobo shih
 * @version 1.0
 */
public interface MerchantsRoleDataAuthorityMapper {
	public int batchInsert(List<MerchantsRoleDataAuthority> list);

	public List<MerchantsRoleDataAuthority> findByGroupId(@Param("groupId") Integer groupId,
			@Param("type") Integer type);

	public int deleteByGroupId(@Param("groupId") Integer groupId);
}
