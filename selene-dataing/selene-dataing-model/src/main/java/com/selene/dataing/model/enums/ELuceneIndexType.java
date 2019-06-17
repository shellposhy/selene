package com.selene.dataing.model.enums;

/**
 * Enumeration of lucene index types
 * 
 * @author shaobo shih
 * @version 1.0
 */
public enum ELuceneIndexType {
	Analyzed("分析"), AnalyzedNoNorms("分析不打分"), No("不索引"), NotAnalyzed("不分析"), NotAnalyzedNoNorms("不分析不打分");
	private String title;

	public String getTitle() {
		return title;
	}

	private ELuceneIndexType(String title) {
		this.title = title;
	}
}
