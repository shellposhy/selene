package com.selene.common.page;

import java.util.ArrayList;
import java.util.List;

/**
 * Generic article list object
 * 
 * @author shaobo shih
 * @version 1.0
 */
public class ListArticle {
	private String title = "";
	private String href = "#";
	private String img = "";
	private Integer length = 0;
	private String summary = "";
	private List<Article> list = new ArrayList<Article>();

	public void addArticle(Article article) {
		this.list.add(article);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public List<Article> getList() {
		return list;
	}

	public void setList(List<Article> list) {
		this.list = list;
	}

}
