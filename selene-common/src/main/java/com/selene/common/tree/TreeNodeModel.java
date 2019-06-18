package com.selene.common.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.selene.common.BaseModel;

/**
 * The base class for tree node
 * 
 * @see BaseModel
 * @see Serializable
 * @see Cloneable
 * @author shaobo shih
 * @version 1.0
 */
public class TreeNodeModel<T> extends BaseModel implements Serializable, Cloneable {
	private static final long serialVersionUID = 3084221640969248066L;
	protected String name;
	protected Integer parentId;
	private String path;
	private int level;
	private T oneself;
	private TreeNodeModel<T> parent;
	private List<TreeNodeModel<T>> children;

	/* ========Constructors======== */
	public TreeNodeModel() {
	}

	public TreeNodeModel(T t, TreeNodeModel<T> parent) {
		this.oneself = t;
		this.parent = parent;
	}

	/**
	 * Get all the child nodes by recursively.
	 */
	public List<T> childrenList() {
		List<T> result = new ArrayList<T>();
		recurve(result, children);
		return result;
	}

	private void recurve(List<T> result, List<TreeNodeModel<T>> childrenList) {
		if (childrenList != null && childrenList.size() > 0) {
			for (TreeNodeModel<T> node : childrenList) {
				result.add(node.oneself);
				recurve(result, node.children);
			}
		}
	}

	/**
	 * Get all the child nodes by recursively and level.
	 */
	public List<T> childrenList(int level) {
		List<T> result = new ArrayList<T>();
		recurve(result, children, level);
		return result;
	}

	private void recurve(List<T> result, List<TreeNodeModel<T>> childrenList, int level) {
		if (childrenList != null && childrenList.size() > 0 && level > 0) {
			for (TreeNodeModel<T> node : childrenList) {
				result.add(node.oneself);
				recurve(result, node.children, level - 1);
			}
		}
	}

	/* ========public methods======== */
	@SuppressWarnings("unchecked")
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return ((TreeNodeModel<T>) super.clone());
	}

	public boolean isRoot() {
		return parent == null ? true : false;
	}

	public boolean isLeaf() {
		return children == null ? true : false;
	}

	public int childrenSize() {
		return children != null ? children.size() : 0;
	}

	/* ========Setter and getter======== */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public T getOneself() {
		return oneself;
	}

	public void setOneself(T oneself) {
		this.oneself = oneself;
	}

	public TreeNodeModel<T> getParent() {
		return parent;
	}

	public void setParent(TreeNodeModel<T> parent) {
		this.parent = parent;
	}

	public List<TreeNodeModel<T>> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNodeModel<T>> children) {
		this.children = children;
	}

}
