package com.selene.common.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.selene.common.Node;

/**
 * The class for default tree node
 * 
 * @author shaobo shih
 * @version 1.0
 */
public class DefaultTreeNode extends Node<Integer, String> {
	protected Integer parentId;
	protected DefaultTreeNode parent;
	protected List<DefaultTreeNode> children;
	public boolean checked;
	public boolean nocheck;
	public int level;

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public DefaultTreeNode parent() {
		return parent;
	}

	public void addChildNode(DefaultTreeNode node) {
		if (children == null) {
			children = new ArrayList<DefaultTreeNode>();
		}
		children.add(node);
	}

	public List<DefaultTreeNode> getChildren() {
		return children;
	}

	// parse tree
	public static <E extends TreeNodeModel<E>> DefaultTreeNode parseTree(List<E> list) {
		return parseTree(DefaultTreeNode.class, list, null);
	}

	public static <T extends DefaultTreeNode, E extends TreeNodeModel<E>> T parseTree(Class<T> clazz, List<E> list,
			PropertySetter<T, E> propertySetter) {
		T rootNode = null;
		try {
			rootNode = clazz.newInstance();
			rootNode.id = 0;
			rootNode.name = "root";
			rootNode.level = 0;
			if (null != propertySetter) {
				propertySetter.setProperty(rootNode, null);
			}
			generateTree(clazz, rootNode, list, propertySetter);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return rootNode;
	}

	// parse tree
	private static <T extends DefaultTreeNode, E extends TreeNodeModel<E>> void generateTree(Class<T> clazz, T root,
			List<E> list, PropertySetter<T, E> propertySetter) throws InstantiationException, IllegalAccessException {
		List<T> nodeList = new ArrayList<T>();
		for (E element : list) {
			if (element.getParentId().equals(root.id)) {
				T node = clazz.newInstance();
				node.id = element.getId();
				node.name = element.getName();
				if (null != propertySetter) {
					propertySetter.setProperty(node, element);
				}
				node.parent = root;
				node.parentId = root.id;
				node.level = root.level + 1;
				root.addChildNode(node);
				nodeList.add(node);
			}
		}
		if (nodeList.size() > 0) {
			for (T node : nodeList) {
				generateTree(clazz, node, list, propertySetter);
			}
		}
	}

	// find parent node
	public static DefaultTreeNode findParentNode(DefaultTreeNode root, Integer parentId) {
		if (root == null)
			return null;
		if (root.id.equals(parentId))
			return root;
		if (root.getChildren() != null) {
			for (DefaultTreeNode child : root.getChildren()) {
				DefaultTreeNode parent = findParentNode(child, parentId);
				if (parent != null)
					return parent;
			}
		}
		return null;
	}

	// Find the part tree
	public static <T extends DefaultTreeNode> T partTree(T treeNode) {
		if (treeNode == null)
			return null;
		if (treeNode.children != null) {
			for (int i = 0; i < treeNode.children.size(); i++) {
				DefaultTreeNode childPartTree = partTree(treeNode.children.get(i));
				if (childPartTree == null) {
					treeNode.children.remove(i--);
				}
			}
			if (treeNode.children.size() == 0)
				treeNode.children = null;
		}
		if (treeNode.children != null || treeNode.checked)
			return treeNode;
		else
			return null;
	}

	// Find parse tree
	public static <T extends DefaultTreeNode, E extends TreeNodeModel<E>> T parseTree(Class<T> clazz, List<E> list,
			Map<Integer, E> map, PropertySetter<T, E> propertySetter) {
		List<E> resultList = new ArrayList<E>();
		for (E item : list) {
			List<E> parents = findParentNodes(item, map, resultList);
			for (E e : parents) {
				boolean hasElement = false;
				for (E result : resultList) {
					if (e.getId().equals(result.getId()))
						hasElement = true;
				}
				if (!hasElement)
					resultList.add(e);
			}
			boolean hasElement = false;
			for (E result : resultList) {
				if (item.getId().equals(result.getId()))
					hasElement = true;
			}
			if (!hasElement)
				resultList.add(item);
		}
		return parseTree(clazz, resultList, propertySetter);
	}

	private static <T extends TreeNodeModel<T>> List<T> findParentNodes(T node, Map<Integer, T> map, List<T> list) {
		List<T> result = new ArrayList<T>();
		if (null == list) {
			list = new ArrayList<T>();
		}
		if (node.getParentId() == null)
			return result;
		T parent = map.get(node.getParentId());
		if (null != parent && !list.contains(parent)) {
			result.addAll(findParentNodes(parent, map, list));
			result.add(parent);
		}
		return result;
	}

	// The interface for property setter
	public static interface PropertySetter<N extends DefaultTreeNode, E extends TreeNodeModel<E>> {
		void setProperty(N node, E entity);
	}
}
