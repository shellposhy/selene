package com.selene.templating.provider.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.selene.templating.model.TemplatingModel;
import com.selene.templating.model.constants.EModelType;

/**
 * Completes the mapping between the {@link TemplatingModel} Object and the XML
 * 
 * @see DataingDataField
 * @author shaobo shih
 * @version 1.0
 */
public interface TemplatingModelMapper {
	public TemplatingModel find(@Param("id") Integer id);

	public List<TemplatingModel> findByLicense(@Param("license") String license);

	public List<TemplatingModel> findByLicenseAndBillId(@Param("license") String license,
			@Param("billId") Integer billId);

	public List<TemplatingModel> findByLicenseAndType(@Param("license") String license, @Param("type") EModelType type);

	public List<TemplatingModel> findByPage(@Param("license") String license, @Param("first") Integer first,
			@Param("size") Integer size);

	public int count(@Param("license") String license);

	public List<TemplatingModel> findByNameAndPage(@Param("license") String license, @Param("name") String name,
			@Param("first") Integer first, @Param("size") Integer size);

	public int countByName(@Param("license") String license, @Param("name") String name);

	public int insert(TemplatingModel model);

	public int update(TemplatingModel model);

	public int delete(@Param("id") Integer id);
}
