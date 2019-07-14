package com.selene.merchants.provider.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.selene.merchants.model.MerchantsRoleAction;

/**
 * Completes the mapping between the {@link MerchantsRoleAction} Object and the
 * XML
 * 
 * @see DataingDataField
 * @author shaobo shih
 * @version 1.0
 */
public interface MerchantsRoleActionMapper {
	public int batchInsert(List<MerchantsRoleAction> list);

	public List<Integer> findActionIdsByGroupIdAndType(@Param("groupId") Integer groupId, @Param("type") Integer type);

	public int deleteByGroupId(@Param("groupId") Integer groupId);
}
