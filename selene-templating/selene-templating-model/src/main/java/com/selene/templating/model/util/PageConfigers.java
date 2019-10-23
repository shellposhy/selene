package com.selene.templating.model.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.selene.common.constants.CommonConstants;
import com.selene.templating.model.TemplatingItem;
import com.selene.templating.model.constants.ECodeAttribute;
import com.selene.templating.model.constants.ECodeListType;
import com.selene.templating.model.constants.ESymbolPosition;
import com.selene.templating.model.constants.ESymbolType;

import static cn.com.lemon.base.Strings.isNullOrEmpty;

/**
 * Static utilities for page templates for configuration
 * 
 * @author shih shaobo
 * @version 1.0
 */
public final class PageConfigers {
	private static final Logger LOG = LoggerFactory.getLogger(PageConfigers.class.getName());
	private static final StringBuffer BUFFER = new StringBuffer();

	private PageConfigers() {
	}

	/**
	 * Scan template configurable area
	 * 
	 * @param modelId
	 * @param content
	 * @return {@link List}
	 */
	public static List<TemplatingItem> editable(Integer modelId, String content) {
		List<TemplatingItem> result = null;
		if (modelId != null && !isNullOrEmpty(content)) {
			try {
				Parser parser = new Parser(content);
				parser.setEncoding("utf-8");
				HasAttributeFilter[] filterAttribute = new HasAttributeFilter[] { new HasAttributeFilter("ecode") };
				NodeFilter nodeFilter = new AndFilter(filterAttribute);
				NodeList nodelist = parser.extractAllNodesThatMatch(nodeFilter);
				if (nodelist != null && nodelist.size() > 0) {
					result = new ArrayList<TemplatingItem>();
					for (Node node : nodelist.toNodeArray()) {
						TagNode tagNode = new TagNode();
						tagNode.setText(node.toHtml());
						String data = node.getChildren().toHtml().trim();
						String macro = /* The data for ftl macro */regex(data, CommonConstants.HTML_ECODE_TEXT);
						// Item processing
						TemplatingItem item = new TemplatingItem();
						item.setModelId(modelId);
						item.setItemCode(tagNode.getAttribute("ecode"));
						item./* Ftl template data */setItemContent(data);
						item.setItemMacro(isNullOrEmpty(macro) ? null : macro);
						item./* Set default data list */setItemType(ECodeListType.Default);
						if (!isNullOrEmpty(macro)) {
							for (ECodeListType ecode : ECodeListType.values()) {
								if (macro.contains(ecode.getName())) {
									item./* Set real data list */setItemType(ecode);
									break;
								}
							}
							// Macro attributes
							Map<String, String> /* Item attribute */ itemAttributes = attribute(macro,
									CommonConstants.HTML_ECODE_VALUE);
							if (itemAttributes != null && itemAttributes.size() > 0) {
								for (String key : itemAttributes.keySet()) {
									for (ECodeAttribute attribute : ECodeAttribute.values()) {
										if (key.equals(attribute.getName().trim())) {
											switch (attribute) {
											case Line:
												item./* Default 5 */setLineSize(null != itemAttributes.get(key)
														? Integer.valueOf(itemAttributes.get(key)) : 5);
												break;
											case Length:
												item./* Default 20 */setLengthSize(null != itemAttributes.get(key)
														? Integer.valueOf(itemAttributes.get(key)) : 20);
												break;
											case Width:
												item./* Default null */setPicWidth(null != itemAttributes.get(key)
														? Integer.valueOf(itemAttributes.get(key)) : null);
												break;
											case Height:
												item./* Default null */setPicHeight(null != itemAttributes.get(key)
														? Integer.valueOf(itemAttributes.get(key)) : null);
												break;
											case Symbol:
												item./* Default null */setAddSymbol(null != itemAttributes.get(key)
														&& itemAttributes.get(key).equals("是") ? true : false);
												break;
											case Place:
												if (null != itemAttributes.get(key)) {
													ESymbolPosition position = ESymbolPosition
															.nameOf(itemAttributes.get(key));
													item./* Default null */setSymbolPosition(position);
												}
												break;
											case Pattern:
											default:
												if (null != itemAttributes.get(key)) {
													ESymbolType symbolType = ESymbolType
															.nameOf(itemAttributes.get(key));
													item./* Default null */setSymbolType(symbolType);
												}
												break;
											}
											break;
										}
									}
								}
							}
							// Macro attributes end
						}
						// macro end
						result.add(item);
					}
				}
			} catch (ParserException e) {
				LOG.error("Parser template error!", e);
				return null;
			}
		}
		return result;
	}

	/**
	 * Gets the result from the given content through a regular expression.
	 * 
	 * @param content
	 * @param pattern
	 * @return {@code Map}
	 */
	public static Map<String, String> attribute(String content, Pattern pattern) {
		Matcher m = pattern.matcher(content);
		Map<String, String> result = new ConcurrentHashMap<String, String>();
		while (m.find()) {
			String value = m.group();
			if (!isNullOrEmpty(value)) {
				String[] values = value.split(CommonConstants.EQUAL_SEPARATOR);
				if (value.length() > 1) {
					result.put(values[0].trim(), values[1].trim());
				}
			}
		}
		return result;
	}

	/**
	 * Gets the result from the given content through a regular expression.
	 * 
	 * @param content
	 * @param pattern
	 * @return {@code List}
	 */
	public static String regex(String content, Pattern pattern) {
		BUFFER.setLength(0);
		Matcher m = pattern.matcher(content);
		while (m.find()) {
			BUFFER.append(m.group());
		}
		return BUFFER.toString();
	}
}
