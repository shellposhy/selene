package com.selene.templating.provider.impl;

import java.util.List;

import javax.annotation.Resource;

import com.papaya.common.extension.Rpc;
import com.selene.templating.model.TemplatingContent;
import com.selene.templating.model.service.TemplatingContentService;
import com.selene.templating.provider.dao.TemplatingContentMapper;

/**
 * The {@code TemplatingContentService} service implementation
 * <p>
 * the {@code Rpc} annotation is the tag for {@code ZooKeeper}
 * 
 * @see TemplatingContentService
 * @author shaobo shih
 * @version 1.0
 */
@Rpc(TemplatingContentService.class)
public class TemplatingContentServiceImpl implements TemplatingContentService {

	@Resource
	private TemplatingContentMapper templatingContentMapper;

	@Override
	public TemplatingContent find(Integer id) {
		return templatingContentMapper.find(id);
	}

	@Override
	public List<TemplatingContent> findByPageId(Integer pageId) {
		return templatingContentMapper.findByPageId(pageId);
	}

	@Override
	public TemplatingContent findByPageIdAndItemId(Integer pageId, Integer itemId) {
		return templatingContentMapper.findByPageIdAndItemId(pageId, itemId);
	}

	@Override
	public int insert(TemplatingContent templatingContent) {
		return templatingContentMapper.insert(templatingContent);
	}

	@Override
	public int update(TemplatingContent templatingContent) {
		return templatingContentMapper.update(templatingContent);
	}

	@Override
	public int delete(Integer id) {
		return templatingContentMapper.delete(id);
	}

	@Override
	public int deleteByPageId(Integer pageId) {
		return templatingContentMapper.deleteByPageId(pageId);
	}

	@Override
	public int deleteByPageIdAndItemId(Integer pageId, Integer itemId) {
		return templatingContentMapper.deleteByPageIdAndItemId(pageId, itemId);
	}

}
