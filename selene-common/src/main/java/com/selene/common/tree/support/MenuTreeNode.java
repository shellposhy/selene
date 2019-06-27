package com.selene.common.tree.support;

import java.util.List;

import com.selene.common.tree.DefaultTreeNode;

/**
 * A tree node object based on a menu or directory structure
 * 
 * @author shaobo shih
 * @version 1.0
 */
public class MenuTreeNode extends DefaultTreeNode {
	public String uri;
	public String iconSkin;

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getIconSkin() {
		return iconSkin;
	}

	public void setIconSkin(String iconSkin) {
		this.iconSkin = iconSkin;
	}

	public DefaultTreeNode parent() {
		return super.parent();
	}

	public void addChildNode(DefaultTreeNode node) {
		super.addChildNode(node);
	}

	public List<DefaultTreeNode> getChildren() {
		return super.getChildren();
	}
}
