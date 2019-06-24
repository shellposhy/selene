package com.selene.merchants.provider.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.selene.merchants.model.MerchantsRole;

/**
 * Completes the mapping between the {@link MerchantsRole} Object and the XML
 * 
 * @see DataingDataField
 * @author shaobo shih
 * @version 1.0
 */
public interface MerchantsRoleMapper {
	public int insert(MerchantsRole role);

	public MerchantsRole find(@Param("id") Integer id);

	public List<MerchantsRole> findByName(@Param("name") String name, @Param("license") String license);

	public List<MerchantsRole> findByCode(@Param("code") String code, @Param("license") String license);

	public List<MerchantsRole> findByUserId(@Param("userId") Integer userId, @Param("license") String license);

	public List<MerchantsRole> findPageByName(@Param("name") String name, @Param("license") String license,
			@Param("first") Integer first, @Param("size") Integer size);

	public int countByName(@Param("name") String name, @Param("license") String license);

	public int batchDelete(List<Integer> list);

	public int delete(@Param("id") Integer id);

	public int update(MerchantsRole role);

}
