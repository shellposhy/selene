package com.selene.templating.provider.impl;

import java.util.List;

import javax.annotation.Resource;

import com.papaya.common.extension.Rpc;
import com.selene.templating.model.TemplatingItem;
import com.selene.templating.model.service.TemplatingItemService;
import com.selene.templating.provider.dao.TemplatingItemMapper;

/**
 * The {@code TemplatingItemService} service implementation
 * <p>
 * the {@code Rpc} annotation is the tag for {@code ZooKeeper}
 * 
 * @see TemplatingItemService
 * @author shaobo shih
 * @version 1.0
 */
@Rpc(TemplatingItemService.class)
public class TemplatingItemServiceImpl implements TemplatingItemService {

	@Resource
	private TemplatingItemMapper templatingItemMapper;

	@Override
	public TemplatingItem find(Integer id) {
		return templatingItemMapper.find(id);
	}

	@Override
	public List<TemplatingItem> findByModelId(Integer modelId) {
		return templatingItemMapper.findByModelId(modelId);
	}

	@Override
	public TemplatingItem findByModelIdAndCode(Integer modelId, String code) {
		return templatingItemMapper.findByModelIdAndCode(modelId, code);
	}

	@Override
	public int insert(TemplatingItem templatingItem) {
		return templatingItemMapper.insert(templatingItem);
	}

	@Override
	public int batchInsert(List<TemplatingItem> list) {
		return templatingItemMapper.batchInsert(list);
	}

	@Override
	public int update(TemplatingItem templatingItem) {
		return templatingItemMapper.update(templatingItem);
	}

	@Override
	public int delete(Integer id) {
		return templatingItemMapper.delete(id);
	}

	@Override
	public int deleteByModelId(Integer modelId) {
		return templatingItemMapper.deleteByModelId(modelId);
	}

}
