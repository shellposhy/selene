package com.selene.dataing.model.jdbc;

import java.util.HashMap;
import java.util.Map;

public class DataingData {
	private Integer /* 主键 */ id;
	private String /* UUID */ uuid;
	private String /* 标题 */ title;
	private String /* 频道名称 */ channelName;
	private String /* 栏目 */ colum;
	private Integer /* 版次 */ pageNum;
	private String /* 版名 */ pageName;
	private String /* 专题 */ subject;
	private String /* 肩标题 */ introTitle;
	private String /* 副标题 */ subTitle;
	private String /* 作者 */ authors;
	private String /* 文档时间 */ docTime;
	private String /* 摘要 */ summary;
	private String /* 正文 */ content;
	private int /* 文章状态 */ status;
	private int /* 图片数量 */ imgs;
	private String /* 关键词 */ keywords;
	private String /* 标签 */ tags;
	private String /* 创建日期 */ createTime;
	private String /* 更新日期 */ updateTime;
	private String /* 创建人 */ creator;
	private String /* 更新人 */ updater;
	private String /* 数据表号 */ tableId;
	private String /* 年 */ year;
	private String /* 月 */ month;
	private String /* 日 */ day;
	private String /* 附件 */ attach;
	private Map<String, String> fieldMap = new HashMap<String, String>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getColum() {
		return colum;
	}

	public void setColum(String colum) {
		this.colum = colum;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getIntroTitle() {
		return introTitle;
	}

	public void setIntroTitle(String introTitle) {
		this.introTitle = introTitle;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public String getDocTime() {
		return docTime;
	}

	public void setDocTime(String docTime) {
		this.docTime = docTime;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getImgs() {
		return imgs;
	}

	public void setImgs(int imgs) {
		this.imgs = imgs;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public Map<String, String> getFieldMap() {
		return fieldMap;
	}

	public void setFieldMap(Map<String, String> fieldMap) {
		this.fieldMap = fieldMap;
	}

	public void putFieldMap(String key, String value) {
		this.fieldMap.put(key, value);
	}
}
