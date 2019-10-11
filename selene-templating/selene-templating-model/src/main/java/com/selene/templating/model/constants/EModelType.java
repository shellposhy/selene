package com.selene.templating.model.constants;

import java.util.ArrayList;

import com.selene.common.Node;

/**
 * Enumeration of page model type
 * 
 * @author shaobo shih
 * @version 1.0
 */
public enum EModelType {

	Site("站点"), Home("首页模板"), Subject("专题模板"), List("列表模板"), Detail("详情模板");

	private final String title;

	EModelType(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}

	public static java.util.List<Node<String, String>> site() {
		java.util.List<Node<String, String>> result = new ArrayList<Node<String, String>>();
		result.add(new Node<String, String>(EModelType.Site.name(), EModelType.Site.getTitle()));
		result.add(new Node<String, String>(EModelType.Home.name(), EModelType.Home.getTitle()));
		result.add(new Node<String, String>(EModelType.Subject.name(), EModelType.Subject.getTitle()));
		result.add(new Node<String, String>(EModelType.List.name(), EModelType.List.getTitle()));
		result.add(new Node<String, String>(EModelType.Detail.name(), EModelType.Detail.getTitle()));
		return result;
	}

	public static java.util.List<Node<String, String>> all() {
		java.util.List<Node<String, String>> result = new ArrayList<Node<String, String>>();
		result.add(new Node<String, String>(EModelType.Home.name(), EModelType.Home.getTitle()));
		result.add(new Node<String, String>(EModelType.Subject.name(), EModelType.Subject.getTitle()));
		result.add(new Node<String, String>(EModelType.List.name(), EModelType.List.getTitle()));
		result.add(new Node<String, String>(EModelType.Detail.name(), EModelType.Detail.getTitle()));
		return result;
	}

	public static java.util.List<Node<String, String>> homeAndSubject() {
		java.util.List<Node<String, String>> result = new ArrayList<Node<String, String>>();
		result.add(new Node<String, String>(EModelType.List.name(), EModelType.List.getTitle()));
		result.add(new Node<String, String>(EModelType.Detail.name(), EModelType.Detail.getTitle()));
		return result;
	}

	public static java.util.List<Node<String, String>> list() {
		java.util.List<Node<String, String>> result = new ArrayList<Node<String, String>>();
		result.add(new Node<String, String>(EModelType.Detail.name(), EModelType.Detail.getTitle()));
		return result;
	}
}
