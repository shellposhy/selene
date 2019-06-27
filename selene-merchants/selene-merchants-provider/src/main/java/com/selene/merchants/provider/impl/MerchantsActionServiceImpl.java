package com.selene.merchants.provider.impl;

import java.util.List;

import javax.annotation.Resource;

import com.papaya.common.extension.Rpc;
import com.selene.common.tree.DefaultTreeNode;
import com.selene.common.tree.DefaultTreeNode.PropertySetter;
import com.selene.common.tree.support.MenuTreeNode;
import com.selene.merchants.model.MerchantsAction;
import com.selene.merchants.model.MerchantsAction.MerchantsActionPropertySetter;
import com.selene.merchants.model.service.MerchantsActionService;
import com.selene.merchants.provider.dao.MerchantsActionMapper;

import cn.com.lemon.base.util.Jsons;

/**
 * The {@code MerchantsActionService} service implementation
 * <p>
 * the {@code Rpc} annotation is the tag for {@code ZooKeeper}
 * 
 * @see MerchantsActionService
 * @author shaobo shih
 * @version 1.0
 */
@Rpc(MerchantsActionService.class)
public class MerchantsActionServiceImpl implements MerchantsActionService {
	@Resource
	private MerchantsActionMapper merchantsActionMapper;

	@Override
	public List<MerchantsAction> findByType(Integer type) {
		return merchantsActionMapper.findByType(type);
	}

	@Override
	public List<MerchantsAction> findByCode(String code) {
		return merchantsActionMapper.findByCode(code);
	}

	@Override
	public List<MerchantsAction> findByUserIdAndType(Integer userId, Integer type) {
		return merchantsActionMapper.findByUserIdAndType(userId, type);
	}

	@Override
	public List<MerchantsAction> findByIds(List<Integer> list) {
		return merchantsActionMapper.findByIds(list);
	}

	@Override
	public String findMenuTreeByUser(List<MerchantsAction> list, Boolean allAction) {
		final MenuTreeNode result = findTreeByUser(MenuTreeNode.class, list, allAction,
				new MerchantsActionPropertySetter<MenuTreeNode>() {
					@Override
					public void set(MenuTreeNode node, MerchantsAction entity) {
						if (entity != null) {
							if ("#".equals(entity.getUri())) {
								node.uri = entity.getUri();
							} else {
								node.setUri(entity.getUri());
							}
							node.iconSkin = entity.getIconSkin();
							if (null == node.iconSkin) {
								node.iconSkin = "";
							}
						}
					}
				});
		return Jsons.json(result);
	}

	@Override
	public <T extends DefaultTreeNode> T findTreeByUser(Class<T> clazz, final List<MerchantsAction> list,
			final Boolean allAction, final MerchantsActionPropertySetter<T> propertySetter) {
		T result = DefaultTreeNode.parseTree(clazz, list, new PropertySetter<T, MerchantsAction>() {
			@Override
			public void setProperty(T node, MerchantsAction entity) {
				if (allAction || (entity != null && list.contains(entity))) {
					node.checked = true;
				}
				propertySetter.set(node, entity);
			}
		});
		return result;
	}
}
