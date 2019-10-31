package com.selene.dataing.model.util;

import java.util.Date;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;

import com.selene.common.constants.CommonConstants;
import com.selene.common.constants.FieldsConstants;
import com.selene.common.page.Article;
import com.selene.common.util.DataUtil;
import com.selene.dataing.model.DataingDataField;
import com.selene.dataing.model.jdbc.DataingBaseData;
import com.selene.dataing.model.jdbc.DataingData;

import static cn.com.lemon.base.Strings.text;
import static cn.com.lemon.base.Strings.isNullOrEmpty;
import static cn.com.lemon.base.DateUtil.format;
import static cn.com.lemon.base.DateUtil.parse;

/**
 * Dataing module utilities
 * 
 * @see Document
 * @see DataingBaseData
 * @author shaobo shih
 * @version 1.0
 */
public final class DataingUtil {
	private DataingUtil() {
	}

	/**
	 * {@code DataingData} transfer {@code Article}
	 * 
	 * @param data
	 * @return {@code Article}
	 */
	public static Article data(DataingData data) {
		if (data != null) {
			Article article = new Article();
			article.setId(data.getId());
			article.setTableId(Integer.valueOf(data.getTableId()));
			article.setIntroTitle(data.getIntroTitle());
			article.setTitle(data.getTitle());
			article.setSubTitle(data.getSubTitle());
			article.setPageNum(data.getPageNum());
			article.setPageName(data.getPageName());
			article.setChannelName(data.getChannelName());
			article.setKeywords(data.getKeywords());
			article.setSummary(data.getSummary());
			article.setPubTime(data.getDocSimpleTime());
			article.setDocTime(data.getDocTime());
			article.setTimes(data.getTimes());
			article.setSource(data.getSource());
			article.setLikeTimes(data.getLikeTimes());
			article.setAttach(data.getAttach());
			return article;
		}
		return null;
	}

	/**
	 * {@code DataingBaseData} transfer {@code DataingData}
	 * 
	 * @param doc
	 * @return {@code DataingData}
	 */
	public static DataingData data(DataingBaseData data) {
		DataingData result = new DataingData();
		if (data != null) {
			// Basic properties
			result.setId((Integer) data.get(FieldsConstants.ID));
			result.setTableId(String.valueOf(data.get(FieldsConstants.TABLE_ID)));
			result.setBaseId(data.getBaseId());
			result.setUuid((String) data.get(FieldsConstants.UUID));
			result.setStatus(data.getDataStatus().ordinal());
			result.setDocTime((data.get(FieldsConstants.DOC_TIME) == null) ? ""
					: format((Date) data.get(FieldsConstants.DOC_TIME), CommonConstants.SHOW_DATE_FORMAT));
			result.setDocSimpleTime((data.get(FieldsConstants.DOC_TIME) == null) ? ""
					: format((Date) data.get(FieldsConstants.DOC_TIME), CommonConstants.SIMPLE_DATE_FORMAT));
			// Business properties
			result.setTitle((String) data.get(FieldsConstants.TITLE));
			result.setIntroTitle(data.get(FieldsConstants.INTRO_TITLE) == null ? ""
					: (String) data.get(FieldsConstants.INTRO_TITLE));
			result.setSubTitle(
					data.get(FieldsConstants.SUB_TITLE) == null ? "" : (String) data.get(FieldsConstants.SUB_TITLE));
			result.setAuthors(
					data.get(FieldsConstants.AUTHORS) == null ? "" : (String) data.get(FieldsConstants.AUTHORS));
			result.setSummary((String) data.get(FieldsConstants.SUMMARY));
			result.setKeywords((String) data.get(FieldsConstants.KEYWORDS));
			result.setContent((String) data.get(FieldsConstants.CONTENT));
			result.setImgs((Integer) data.get(FieldsConstants.IMGS));
			result.setSource(
					data.get(FieldsConstants.SOURCE) == null ? "#" : (String) data.get(FieldsConstants.SOURCE));
			result.setChannelName(data.get(FieldsConstants.CHANNEL_NAME) == null ? ""
					: (String) data.get(FieldsConstants.CHANNEL_NAME));
			result.setColum(data.get(FieldsConstants.COLUM) == null ? "" : (String) data.get(FieldsConstants.COLUM));
			result.setSubject(
					data.get(FieldsConstants.SUBJECT) == null ? "" : (String) data.get(FieldsConstants.SUBJECT));
			result.setPageNum(
					data.get(FieldsConstants.PAGE_NUM) == null ? 0 : (Integer) data.get(FieldsConstants.PAGE_NUM));
			result.setPageName(
					data.get(FieldsConstants.PAGE_NAME) == null ? "" : (String) data.get(FieldsConstants.PAGE_NAME));
			// Auxiliary attributes
			result.setStyle(data.get(FieldsConstants.STYLE) == null ? "" : (String) data.get(FieldsConstants.STYLE));
			result.setAttach(data.get(FieldsConstants.ATTACH) == null ? "" : (String) data.get(FieldsConstants.ATTACH));
			result.setPeoples(
					data.get(FieldsConstants.PEOPLES) == null ? "" : (String) data.get(FieldsConstants.PEOPLES));
			result.setPlaces(data.get(FieldsConstants.PLACES) == null ? "" : (String) data.get(FieldsConstants.PLACES));
			result.setOrgs(data.get(FieldsConstants.ORGS) == null ? "" : (String) data.get(FieldsConstants.ORGS));
			result.setTimes(data.get(FieldsConstants.TIMES) == null ? 0 : (Integer) data.get(FieldsConstants.TIMES));
			result.setLikeTimes(
					data.get(FieldsConstants.LIKE_TIMES) == null ? 0 : (Integer) data.get(FieldsConstants.LIKE_TIMES));
		}
		return result;
	}

	/**
	 * {@code Document} transfer {@code DataingData}
	 * 
	 * @param doc
	 * @return {@code DataingData}
	 */
	public static DataingData data(Document doc) {
		DataingData result = new DataingData();
		if (doc != null) {
			// Basic properties
			result.setId(Integer.valueOf(doc.get(FieldsConstants.ID)));
			result.setTableId(doc.get(FieldsConstants.TABLE_ID));
			result.setBaseId(Integer.valueOf(doc.get(FieldsConstants.BASE_ID)));
			result.setUuid(doc.get(FieldsConstants.UUID));
			result.setStatus(Integer.valueOf(doc.get(FieldsConstants.DATA_STATUS)));
			result.setDocTime(isNullOrEmpty(doc.get(FieldsConstants.DOC_TIME)) ? ""
					: format(parse(doc.get(FieldsConstants.DOC_TIME), CommonConstants.INDEX_DATE_FORMAT),
							CommonConstants.SHOW_DATE_FORMAT));
			result.setDocSimpleTime(isNullOrEmpty(doc.get(FieldsConstants.DOC_TIME)) ? ""
					: format(parse(doc.get(FieldsConstants.DOC_TIME), CommonConstants.INDEX_DATE_FORMAT),
							CommonConstants.SIMPLE_DATE_FORMAT));
			// Business properties
			result.setTitle(doc.get(FieldsConstants.TITLE));
			result.setIntroTitle(
					isNullOrEmpty(doc.get(FieldsConstants.INTRO_TITLE)) ? "" : doc.get(FieldsConstants.INTRO_TITLE));
			result.setSubTitle(
					isNullOrEmpty(doc.get(FieldsConstants.SUB_TITLE)) ? "" : doc.get(FieldsConstants.SUB_TITLE));
			result.setAuthors(isNullOrEmpty(doc.get(FieldsConstants.AUTHORS)) ? "" : doc.get(FieldsConstants.AUTHORS));
			result.setSummary(isNullOrEmpty(doc.get(FieldsConstants.SUMMARY))
					? (isNullOrEmpty(doc.get(FieldsConstants.CONTENT)) ? "" : text(doc.get(FieldsConstants.CONTENT)))
					: doc.get(FieldsConstants.SUMMARY));
			result.setKeywords(doc.get(FieldsConstants.KEYWORDS));
			result.setContent(doc.get(FieldsConstants.CONTENT));
			result.setImgs(Integer.valueOf(doc.get(FieldsConstants.IMGS)));
			result.setSource(isNullOrEmpty(doc.get(FieldsConstants.SOURCE)) ? "#" : doc.get(FieldsConstants.SOURCE));
			result.setChannelName(
					isNullOrEmpty(doc.get(FieldsConstants.CHANNEL_NAME)) ? "" : doc.get(FieldsConstants.CHANNEL_NAME));
			result.setColum(isNullOrEmpty(doc.get(FieldsConstants.COLUM)) ? "" : doc.get(FieldsConstants.COLUM));
			result.setSubject(isNullOrEmpty(doc.get(FieldsConstants.SUBJECT)) ? "" : doc.get(FieldsConstants.SUBJECT));
			result.setPageNum(isNullOrEmpty(doc.get(FieldsConstants.PAGE_NUM)) ? 0
					: Integer.valueOf(doc.get(FieldsConstants.PAGE_NUM)));
			result.setPageName(
					isNullOrEmpty(doc.get(FieldsConstants.PAGE_NAME)) ? "" : doc.get(FieldsConstants.PAGE_NAME));
			// Auxiliary attributes
			result.setStyle(isNullOrEmpty(doc.get(FieldsConstants.STYLE)) ? "" : doc.get(FieldsConstants.STYLE));
			result.setAttach(isNullOrEmpty(doc.get(FieldsConstants.ATTACH)) ? "" : doc.get(FieldsConstants.ATTACH));
			result.setPeoples(isNullOrEmpty(doc.get(FieldsConstants.PEOPLES)) ? "" : doc.get(FieldsConstants.PEOPLES));
			result.setPlaces(isNullOrEmpty(doc.get(FieldsConstants.PLACES)) ? "" : doc.get(FieldsConstants.PLACES));
			result.setOrgs(isNullOrEmpty(doc.get(FieldsConstants.ORGS)) ? "" : doc.get(FieldsConstants.ORGS));
			result.setTimes(isNullOrEmpty(doc.get(FieldsConstants.TIMES)) ? 0
					: Integer.valueOf(doc.get(FieldsConstants.TIMES)));
			result.setLikeTimes(isNullOrEmpty(doc.get(FieldsConstants.LIKE_TIMES)) ? 0
					: Integer.valueOf(doc.get(FieldsConstants.LIKE_TIMES)));
		}
		return result;
	}

	/**
	 * {@code DataingBaseData} transfer {@code Document}
	 * 
	 * @param data
	 * @param fieldList
	 * @return {@code Document}
	 */
	public static Document doc(DataingBaseData data, List<DataingDataField> fieldList) {
		Document doc = new Document();
		NumericField nField = null;
		nField = new NumericField(FieldsConstants.BASE_ID, Field.Store.YES, true);
		nField.setIntValue(data.getBaseId());
		doc.add(nField);
		nField = new NumericField(FieldsConstants.INDEX_TIME, Field.Store.YES, true);
		nField.setLongValue(Long.parseLong(format(new Date(), CommonConstants.INDEX_DATE_FORMAT)));
		doc.add(nField);
		nField = new NumericField(FieldsConstants.TABLE_ID, Field.Store.YES, true);
		nField.setIntValue(data.getTableId());
		doc.add(nField);
		// Business process
		Field.Index indexType = null;
		Field.Store store = null;
		String fieldCode = null;
		boolean toIndex = true;
		for (DataingDataField dateField : fieldList) {
			toIndex = true;
			switch (dateField.getIndexType()) {
			case Analyzed:
				indexType = Field.Index.ANALYZED;
				break;
			case AnalyzedNoNorms:
				indexType = Field.Index.ANALYZED_NO_NORMS;
				break;
			case No:
				indexType = Field.Index.NO;
				toIndex = false;
				break;
			case NotAnalyzed:
				indexType = Field.Index.NOT_ANALYZED;
				break;
			case NotAnalyzedNoNorms:
				indexType = Field.Index.NOT_ANALYZED_NO_NORMS;
				break;
			}
			if (dateField.isIndexStore()) {
				store = Field.Store.YES;
			} else {
				store = Field.Store.NO;
			}
			fieldCode = dateField.getCode();
			Object value = null;
			value = data.get(fieldCode);
			if (!(store == Field.Store.NO && indexType == Field.Index.NO) && value != null) {
				switch (dateField.getDataType()) {
				case UUID:
				case Char:
				case Varchar:
				case Bool:
					doc.add(new Field(dateField.getCode(), String.valueOf(value), store, indexType));
					break;
				case Blob:
				case MediumBlob:
					String content = String.valueOf(value);
					content = /* Unescapes a string */DataUtil.unescape(content);
					content = /* Remove html tags */text(content);
					doc.add(new Field(dateField.getCode(), content, store, indexType));
					break;
				case Short:
				case IntAutoIncrement:
				case Int:
					nField = new NumericField(fieldCode, store, toIndex);
					nField.setIntValue(Integer.parseInt(value.toString()));
					doc.add(nField);
					break;
				case Long:
					nField = new NumericField(fieldCode, store, toIndex);
					nField.setLongValue(Long.parseLong(value.toString()));
					doc.add(nField);
					break;
				case Float:
					nField = new NumericField(fieldCode, store, toIndex);
					nField.setFloatValue(Float.parseFloat(value.toString()));
					doc.add(nField);
					break;
				case Double:
				case Numeric:
					nField = new NumericField(fieldCode, store, toIndex);
					nField.setDoubleValue(Double.parseDouble(value.toString()));
					doc.add(nField);
					break;
				case Date:
				case Time:
				case DateTime:
					nField = new NumericField(fieldCode, store, toIndex);
					nField.setLongValue(Long.parseLong(format(((Date) value), CommonConstants.INDEX_DATE_FORMAT)));
					doc.add(nField);
					break;
				}
			}
		}
		return doc;
	}
}
