package com.selene.dataing.model.util;

import java.util.Date;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;

import com.selene.common.constants.CommonConstants;
import com.selene.common.constants.FieldsConstants;
import com.selene.common.util.DataUtil;
import com.selene.dataing.model.DataingDataField;
import com.selene.dataing.model.jdbc.DataingBaseData;

import static cn.com.lemon.base.Strings.text;

import static cn.com.lemon.base.DateUtil.format;

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
