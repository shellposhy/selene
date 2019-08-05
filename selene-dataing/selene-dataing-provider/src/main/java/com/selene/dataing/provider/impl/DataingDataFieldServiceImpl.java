package com.selene.dataing.provider.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import com.papaya.common.extension.Rpc;
import com.selene.dataing.model.DataingDataField;
import com.selene.dataing.model.service.DataingDataFieldService;
import com.selene.dataing.provider.dao.DataingDataFieldMapper;

/**
 * The {@code DataingDataFieldService} service implementation
 * <p>
 * the {@code Rpc} annotation is the tag for {@code ZooKeeper}
 * 
 * @see DataingDataFieldService
 * @author shaobo shih
 * @version 1.0
 */
@Rpc(DataingDataFieldService.class)
public class DataingDataFieldServiceImpl implements DataingDataFieldService {
	@Resource
	private DataingDataFieldMapper dataFieldMapper;

	@Override
	public int insert(DataingDataField dataField) {
		return dataFieldMapper.insert(dataField);
	}

	@Override
	public DataingDataField find(Integer id) {
		return dataFieldMapper.find(id);
	}

	@Override
	public List<DataingDataField> findPageByName(String name, Integer first, Integer size) {
		return dataFieldMapper.findPageByName(name, first, size);
	}

	@Override
	public int countByName(String name) {
		return dataFieldMapper.countByName(name);
	}

	@Override
	public List<DataingDataField> findPage(Integer first, Integer size) {
		return dataFieldMapper.findPage(first, size);
	}

	@Override
	public int count(Boolean flag) {
		return dataFieldMapper.count(Boolean.TRUE);
	}

	@Override
	public List<DataingDataField> findByName(String name) {
		return dataFieldMapper.findByName(name);
	}

	@Override
	public List<DataingDataField> findByCode(String code) {
		return dataFieldMapper.findByCode(code);
	}

	@Override
	public List<DataingDataField> findFieldsByBaseId(Integer baseId) {
		return dataFieldMapper.findFieldsByBaseId(baseId);
	}

	@Override
	public List<DataingDataField> findByRequired(Boolean required) {
		return dataFieldMapper.findByRequired(required);
	}

	@Override
	public List<DataingDataField> findByType(Integer type) {
		return dataFieldMapper.findByType(type);
	}

	@Override
	public List<DataingDataField> findDisplayFieldsByModelId(Integer modelId) {
		return dataFieldMapper.findDisplayFieldsByModelId(modelId);
	}

	@Override
	public List<DataingDataField> findFieldsByModelId(Integer modelId) {
		return dataFieldMapper.findFieldsByModelId(modelId);
	}

	@Override
	public List<DataingDataField> compare(List<DataingDataField> source, List<DataingDataField> target) {
		if (source == null || source.size() == 0) {
			return null;
		}
		if (target == null || target.size() == 0) {
			return source;
		}
		List<DataingDataField> result = new ArrayList<DataingDataField>();
		DataingDataField[] targetArray = target.toArray(new DataingDataField[target.size()]);
		for (DataingDataField dataField : source) {
			if (Arrays.binarySearch(targetArray, dataField) < 0) {
				result.add(dataField);
			}
		}
		return result;
	}
}
