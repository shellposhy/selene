package com.selene.dataing.provider.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.selene.dataing.model.DataingDataField;

/**
 * Completes the mapping between the {@link DataingDataField} Object and the XML
 * 
 * @see DataingDataField
 * @author shaobo shih
 * @version 1.0
 */
public interface DataingDataFieldMapper {

	public int insert(DataingDataField dataField);

	public DataingDataField find(@Param("id") Integer id);

	public List<DataingDataField> findPageByName(@Param("name") String name, @Param("first") Integer first,
			@Param("size") Integer size);

	public int countByName(@Param("name") String name);

	public List<DataingDataField> findPage(@Param("first") Integer first, @Param("size") Integer size);

	public int count(@Param("flag") Boolean flag);

	public List<DataingDataField> findByName(@Param("name") String name);

	public List<DataingDataField> findByCode(@Param("code") String code);

	public List<DataingDataField> findFieldsByBaseId(@Param("baseId") Integer baseId);

	public List<DataingDataField> findDisplayFieldsByBaseId(@Param("baseId") Integer baseId);

	public List<DataingDataField> findByRequired(@Param("required") Boolean required);

	public List<DataingDataField> findByType(@Param("type") Integer type);

	public List<DataingDataField> findDisplayFieldsByModelId(@Param("modelId") Integer modelId);

	public List<DataingDataField> findFieldsByModelId(@Param("modelId") Integer modelId);

	public List<DataingDataField> findFieldsInBasesByAccess(@Param("type") Integer type,
			@Param("list") List<Integer> list, @Param("size") Integer size);
}
