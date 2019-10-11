package com.selene.common.util;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.HTML.Attribute;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.parser.ParserDelegator;

import org.apache.oro.text.perl.Perl5Util;

import static com.hankcs.hanlp.summary.TextRankKeyword.getKeywordList;
import static com.hankcs.hanlp.summary.TextRankSentence.getSummary;

import com.pepper.lucene.comparator.base.PepperSortField.FieldType;
import com.selene.common.constants.CommonConstants;
import com.selene.common.constants.util.EDataType;
import com.selene.common.constants.util.EDateSearchType;
import com.selene.common.constants.util.EFieldSearchType;

import cn.com.lemon.base.DateUtil;

import static cn.com.lemon.base.DateUtil.parse;
import static cn.com.lemon.base.Strings.isNullOrEmpty;
import static cn.com.lemon.base.Strings.text;
import static cn.com.lemon.base.Strings.join;
import static cn.com.lemon.base.Strings.toArray;

import static org.apache.commons.lang.StringEscapeUtils.unescapeHtml;

/**
 * The utilities for data transfer process
 * 
 * @author shaobo shih
 * @version 1.0
 */
public final class DataUtil {

	private DataUtil() {
	}

	/**
	 * Search engines query term processing
	 * 
	 * @param field
	 * @param dataType
	 * @param value
	 * @param dateSearchType
	 * @return
	 */
	public static String query(EFieldSearchType searchType, String value) {
		StringBuffer sb = new StringBuffer("");
		sb.setLength(0);
		if (!isNullOrEmpty(value)) {
			value = /* Remove special char and XSS */remove(xss(value));
			switch (searchType) {
			case All:
				sb.append(EFieldSearchType.Title.getCode()).append(":").append(value);
				sb.append(" OR ").append(EFieldSearchType.Content.getCode()).append(":").append(value);
				sb.append(" OR ").append(EFieldSearchType.Keywords.getCode()).append(":").append(value);
				sb.append(" OR ").append(EFieldSearchType.Summary.getCode()).append(":").append(value);
				sb.append(" OR ").append(EFieldSearchType.Authors.getCode()).append(":").append(value);
				break;
			case Title:
				sb.append(EFieldSearchType.Title.getCode()).append(":").append(value);
				break;
			case Content:
				sb.append(EFieldSearchType.Content.getCode()).append(":").append(value);
				break;
			case Keywords:
				sb.append(EFieldSearchType.Keywords.getCode()).append(":").append(value);
				break;
			case Summary:
				sb.append(EFieldSearchType.Summary.getCode()).append(":").append(value);
				break;
			default:
			case Authors:
				sb.append(EFieldSearchType.Authors.getCode()).append(":").append(value);
				break;
			}
		}
		return sb.toString();
	}

	/**
	 * Search engines query term processing
	 * 
	 * @param field
	 * @param dataType
	 * @param value
	 * @param dateSearchType
	 * @return
	 */
	public static String query(String field, EDataType dataType, EDateSearchType dateSearchType, String... value) {
		StringBuffer sb = new StringBuffer("");
		sb.setLength(0);
		switch (dataType) {
		case Date:
		case Time:
		case DateTime:
			sb.append("#long#").append(field).append(":").append("[");
			switch (dateSearchType) {
			case Day:
				sb.append(DateUtil.format(new Date(), "yyyyMMdd") + "000000");
				sb.append(" TO ");
				sb.append(DateUtil.format(new Date(), "yyyyMMdd") + "235959");
				sb.append("]");
				break;
			case Month:
				sb.append(DateUtil.format(new Date(), "yyyyMM") + "01000000");
				sb.append(" TO ");
				sb.append(DateUtil.format(new Date(), "yyyyMMdd") + "235959");
				sb.append("]");
				break;
			default:
			case Range:
				if (value != null && value.length == 2) {
					sb.append(DateUtil.format(DateUtil.parse(value[0], CommonConstants.COMMON_DATE_FORMAT),
							CommonConstants.INDEX_DATE_FORMAT));
					sb.append(" TO ");
					sb.append(DateUtil.format(DateUtil.parse(value[1], CommonConstants.COMMON_DATE_FORMAT),
							CommonConstants.INDEX_DATE_FORMAT));
					sb.append("]");
				} else {
					sb.setLength(0);
					sb.append("");
				}
				break;
			}
			break;
		case Long:
			if (!isNullOrEmpty(value[0])) {
				sb.append("#long#").append(field).append(":").append(value[0]);
			} else {
				sb.append("");
			}
			break;
		case UUID:
		case Char:
		case Varchar:
		case Bool:
		case Blob:
		case MediumBlob:
			if (!isNullOrEmpty(value[0])) {
				sb.append(field).append(":").append(value[0]);
			} else {
				sb.append("");
			}
			break;
		case Short:
		case IntAutoIncrement:
		case Int:
			if (!isNullOrEmpty(value[0])) {
				sb.append("#int#").append(field).append(":").append(value[0]);
			} else {
				sb.append("");
			}
			break;
		case Float:
			if (!isNullOrEmpty(value[0])) {
				sb.append("#float#").append(field).append(":").append(value[0]);
			} else {
				sb.append("");
			}
			break;
		default:
		case Double:
		case Numeric:
			if (!isNullOrEmpty(value[0])) {
				sb.append("#double#").append(field).append(":").append(value[0]);
			} else {
				sb.append("");
			}
			break;
		}
		return sb.toString();
	}

	/**
	 * Remove XSS characters
	 * 
	 * @param content
	 * @return {@code String}
	 */
	public static String xss(String content) {
		if (isNullOrEmpty(content)) {
			return "";
		}
		Pattern pattern = /* Remove script execution */Pattern.compile(
				"<[\r\n| | ]*script[\r\n| | ]*>(.*?)</[\r\n| | ]*script[\r\n| | ]*>", Pattern.CASE_INSENSITIVE);
		content = pattern.matcher(content).replaceAll("");
		pattern = Pattern./* Remove (src="http://...")e-xpression */compile(
				"src[\r\n| | ]*=[\r\n| | ]*[\\\"|\\\'](.*?)[\\\"|\\\']",
				Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		content = pattern.matcher(content).replaceAll("");
		pattern = /* Remove(</script>) tag */ Pattern.compile("</[\r\n| | ]*script[\r\n| | ]*>",
				Pattern.CASE_INSENSITIVE);
		content = pattern.matcher(content).replaceAll("");
		pattern = /* Remove(<script>) tag */Pattern.compile("<[\r\n| | ]*script(.*?)>",
				Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		content = pattern.matcher(content).replaceAll("");
		pattern = Pattern./* Remove (eval(...)) expressions */compile("eval\\((.*?)\\)",
				Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		content = pattern.matcher(content).replaceAll("");
		pattern = Pattern./* Remove (e-xpression(...)) expressions */compile("e-xpression\\((.*?)\\)",
				Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		content = pattern.matcher(content).replaceAll("");
		pattern = Pattern./* Remove (javascript:...) expressions */compile("javascript[\r\n| | ]*:[\r\n| | ]*",
				Pattern.CASE_INSENSITIVE);
		content = pattern.matcher(content).replaceAll("");
		pattern = Pattern./* Remove (vbscript:...) expressions */compile("vbscript[\r\n| | ]*:[\r\n| | ]*",
				Pattern.CASE_INSENSITIVE);
		content = pattern.matcher(content).replaceAll("");
		pattern = Pattern./* Remove (onload= )expressions */compile("onload(.*?)=",
				Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		content = pattern.matcher(content).replaceAll("");
		return content;
	}

	/**
	 * Remove special characters
	 * 
	 * @param content
	 * @return {@code String}
	 */
	public static String remove(String content) {
		if (isNullOrEmpty(content)) {
			return "";
		}
		return Pattern.compile(CommonConstants.DEFAULT_REMOVE_CHAR, Pattern.CASE_INSENSITIVE).matcher(content)
				.replaceAll("").trim();
	}

	/**
	 * Remove HTML tags and keep p img span tags
	 * 
	 * @param html
	 * @return {@code String}
	 */
	public static String filter(String html) {
		if (isNullOrEmpty(html)) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		Perl5Util preg = new Perl5Util();
		preg.substitute(sb, "s/<script[^>]*?>.*?<\\/script>//gmi", html);
		html = sb.toString();
		sb.setLength(0);
		preg.substitute(sb, "s#<[/]*?(?!p|img|span)[^<>]*?>##gmi", html);
		html = sb.toString();
		sb.setLength(0);
		return html;
	}

	/**
	 * Remove HTML tags and keep p img span tags
	 * 
	 * @param html
	 * @return {@code String}
	 */
	public static String clear(String html) {
		if (isNullOrEmpty(html)) {
			return "";
		}
		String regEx = "(?!<(img|p|span).*?>)<.*?>";
		Pattern pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(html);
		return matcher.replaceAll("");
	}

	/**
	 * Get keywords from the text.
	 * <p>
	 * get 5 keywords
	 * 
	 * @param content
	 */
	public static String keyword(String content) {
		if (!isNullOrEmpty(content)) {
			try {
				content = /* Unescapes a string */ unescape(content);
				content = /* Remove HTML tags */ text(content);
				List<String> words = getKeywordList(content, CommonConstants.DEFAULT_KEYWORDS_SIZE);
				if (words != null && words.size() > 0) {
					return join(CommonConstants.SPACE_SEPARATOR, toArray(words));
				}
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

	/**
	 * Get summary from the text.
	 * <p>
	 * get 200 summary
	 * 
	 * @param content
	 */
	public static String summary(String content) {
		if (!isNullOrEmpty(content)) {
			try {
				content = /* Unescapes a string */unescape(content);
				content = /* Remove HTML tags */ text(content);
				return getSummary(content, CommonConstants.DEFAULT_SUMMARY_LENGTH);
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

	/**
	 * Unescapes a string containing entity escapes to a string containing the
	 * actual Unicode characters corresponding to the escapes. Supports HTML 4.0
	 * entities.
	 * 
	 * @param content
	 * @return {@code String}
	 */
	public static String unescape(String content) {
		if (!isNullOrEmpty(content)) {
			try {
				return unescapeHtml(content);
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

	/**
	 * Get data{@code Object} objects based on values,if value null,return
	 * default {@code String} value
	 * 
	 * @param object
	 * @param dataType
	 * @return {@code String}
	 */
	public static String dataTypeString(Object object, EDataType dataType) {
		switch (dataType) {
		case Date:
		case DateTime:
		case Time:
			if ("".equals(object)) {
				return "";
			} else {
				return DateUtil.format((Date) object, CommonConstants.COMMON_DATE_FORMAT);
			}
		default:
			return null == object ? "" : object.toString();
		}
	}

	/**
	 * Get data{@code Object} objects based on values,if value null,return
	 * default {@code Object} value
	 * 
	 * @param dataType
	 * @param isNotNull
	 * @return {@code Object}
	 */
	public static Object defaultValue(EDataType dataType, boolean isNotNull) {
		switch (dataType) {
		case Date:
		case DateTime:
		case Time:
			return isNotNull ? new Date() : null;
		case Short:
			return (short) 0;
		case Int:
			return 0;
		case Long:
			return (long) 0;
		case Float:
			return (float) 0;
		case Double:
		case Numeric:
			return (double) 0;
		case Bool:
			return false;
		case Blob:
		case MediumBlob:
		case Char:
		case Varchar:
		case UUID:
		default:
			return "";
		}
	}

	/**
	 * Data type reverse sort type
	 * 
	 * @param dataType
	 * @return {@code FieldType}
	 */
	public static FieldType sortLuceneType(EDataType dataType) {
		switch (dataType) {
		case Int:
		case IntAutoIncrement:
			return FieldType.Int;
		case Long:
			return FieldType.Long;
		case Float:
			return FieldType.Float;
		case Double:
			return FieldType.Double;
		case Time:
		case DateTime:
			return FieldType.StringVal;
		// return FieldType.Long;
		default:
			return FieldType.StringVal;
		}
	}

	/**
	 * Get data{@code Object} objects based on values and data types
	 * 
	 * @param value
	 * @param dataType
	 * @return {@code Object}
	 */
	public static Object dataTypeObject(String value, EDataType dataType) {
		switch (dataType) {
		case Date:
		case DateTime:
		case Time:
			if (isNullOrEmpty(value)) {
				return null;
			} else {
				return value.trim().length() > 10 ? parse(value, "yyyy-MM-dd HH:mm:ss") : parse(value, "yyyy-MM-dd");
			}
		case Short:
			return isNullOrEmpty(value) ? null : Short.valueOf(value);
		case Int:
			return isNullOrEmpty(value) ? 0 : Integer.valueOf(value);
		case Long:
			return isNullOrEmpty(value) ? null : Long.valueOf(value);
		case Float:
			return isNullOrEmpty(value) ? null : Float.valueOf(value);
		case Double:
		case Numeric:
			return isNullOrEmpty(value) ? null : Double.valueOf(value);
		case Bool:
			return isNullOrEmpty(value) ? null : Boolean.valueOf(value);
		case Blob:
		case MediumBlob:
			// return new SerialBlob(value.getBytes("utf-8"));
		case Char:
		case Varchar:
		case UUID:
		default:
			return value;
		}
	}

	/**
	 * Get {@code List} list files name
	 * 
	 * @param files
	 * @return {@code String}
	 */
	public static String names(List<File> files) {
		if (files != null && files.size() > 0) {
			StringBuffer result = new StringBuffer(200);
			for (File file : files) {
				result.append(file.getName()).append(CommonConstants.COMMA_SEPARATOR);
			}
			result.deleteCharAt(result.length() - 1);
			return result.toString();
		}
		return "";
	}

	/**
	 * Get all image paths for rich text.
	 * 
	 * @param content
	 * @return
	 */
	public static List<String> imgs(String content) {
		final List<String> imgs = new LinkedList<String>();
		if (null == content || content.isEmpty()) {
			return null;
		}
		final Reader reader = new StringReader(content);
		ParserDelegator pd = new ParserDelegator();
		try {
			pd.parse(reader, new HTMLEditorKit.ParserCallback() {
				@Override
				public void handleSimpleTag(Tag t, MutableAttributeSet a, int pos) {
					if (Tag.IMG.equals(t)) {
						imgs.add((String) a.getAttribute(Attribute.SRC));
					}
				}
			}, false);
		} catch (IOException e) {
			return null;
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return imgs.size() > 0 ? imgs : null;
	}

	/**
	 * Get all css paths for rich text.
	 * 
	 * @param content
	 * @return
	 */
	public static List<String> css(String content) {
		final List<String> cssList = new LinkedList<String>();
		if (null == content || content.isEmpty()) {
			return null;
		}
		final Reader reader = new StringReader(content);
		ParserDelegator pd = new ParserDelegator();
		try {
			pd.parse(reader, new HTMLEditorKit.ParserCallback() {
				@Override
				public void handleSimpleTag(Tag t, MutableAttributeSet a, int pos) {
					if (Tag.LINK.equals(t)) {
						cssList.add((String) a.getAttribute(Attribute.HREF));
					}
				}
			}, false);
		} catch (IOException e) {
			return null;
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return cssList.size() > 0 ? cssList : null;
	}

	/**
	 * Get all Javascript paths for rich text.
	 * 
	 * @param content
	 * @return
	 */
	public static List<String> js(String content) {
		final List<String> jsList = new LinkedList<String>();
		if (null == content || content.isEmpty()) {
			return null;
		}
		final Reader reader = new StringReader(content);
		ParserDelegator pd = new ParserDelegator();
		try {
			pd.parse(reader, new HTMLEditorKit.ParserCallback() {
				@Override
				public void handleSimpleTag(Tag t, MutableAttributeSet a, int pos) {
					if (Tag.SCRIPT.equals(t)) {
						jsList.add((String) a.getAttribute(Attribute.SRC));
					}
				}
			}, false);
		} catch (IOException e) {
			return null;
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return jsList.size() > 0 ? jsList : null;
	}
}
