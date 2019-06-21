package com.selene.merchants.provider.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.selene.merchants.model.MerchantsUser;

/**
 * Completes the mapping between the {@link MerchantsUser} Object and the XML
 * 
 * @see DataingDataField
 * @author shaobo shih
 * @version 1.0
 */
public interface MerchantsUserMapper {
	public int insert(MerchantsUser user);

	public MerchantsUser find(@Param("id") Integer id);

	public MerchantsUser findByName(@Param("name") String name);

	public MerchantsUser findByNamePassword(@Param("name") String name, @Param("pass") String pass);

	public List<MerchantsUser> findByOrgId(@Param("orgId") Integer orgId);

	public int countByOrgAndName(@Param("orgId") Integer orgId, @Param("name") String name);

	public int findByOrgAndName(@Param("orgId") Integer orgId, @Param("name") String name,
			@Param("firstSize") Integer firstSize, @Param("size") Integer size);

	public int update(MerchantsUser user);

	public int petrify(MerchantsUser user);
}
