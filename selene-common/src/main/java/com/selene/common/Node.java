package com.selene.common;

/**
 * The base class for node
 * 
 * @author shaobo shih
 * @version 1.0
 */
public class Node<I, N> {
	public I id;
	public N name;

	public Node() {
	}

	public Node(I id, N name) {
		this.id = id;
		this.name = name;
	}

	public I getId() {
		return id;
	}

	public N getName() {
		return name;
	}

	public void setName(N name) {
		this.name = name;
	}
}