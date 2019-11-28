package com.selene.common.tree.support;

import com.selene.common.tree.DefaultTreeNode;

/**
 * A tree node object based on a page node or directory structure
 * 
 * @author shaobo shih
 * @version 1.0
 */
public class PageTreeNode extends DefaultTreeNode {
	private Integer pageType;

	public Integer getPageType() {
		return pageType;
	}

	public void setPageType(Integer pageType) {
		this.pageType = pageType;
	}

}
