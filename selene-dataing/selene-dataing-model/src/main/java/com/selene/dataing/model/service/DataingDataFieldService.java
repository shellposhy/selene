package com.selene.dataing.model.service;

import java.util.List;

import com.selene.dataing.model.DataingDataField;

/**
 * The {@code DataingDataField} service for RPC
 * 
 * @author shaobo shih
 * @version 1.0
 */
public interface DataingDataFieldService {
	public int insert(DataingDataField dataField);

	public DataingDataField find(Integer id);

	public List<DataingDataField> findPageByName(String name, Integer first, Integer size);

	public int countByName(String name);

	public List<DataingDataField> findPage(Integer first, Integer size);

	public int count(Boolean flag);

	public List<DataingDataField> findByName(String name);

	public List<DataingDataField> findByCode(String code);

	public List<DataingDataField> findFieldsByBaseId(Integer baseId);

	public List<DataingDataField> findByRequired(Boolean required);

	public List<DataingDataField> findByType(Integer type);

	public List<DataingDataField> findDisplayFieldsByModelId(Integer modelId);

	public List<DataingDataField> findFieldsByModelId(Integer modelId);

	public List<DataingDataField> compare(List<DataingDataField> source, List<DataingDataField> target);
}
