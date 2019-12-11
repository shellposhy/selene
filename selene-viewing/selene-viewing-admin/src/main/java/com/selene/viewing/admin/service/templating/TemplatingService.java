package com.selene.viewing.admin.service.templating;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.selene.common.constants.CommonConstants;
import com.selene.common.constants.FieldsConstants;
import com.selene.common.constants.ServiceConstants;
import com.selene.common.page.Article;
import com.selene.common.page.ListArticle;
import com.selene.common.result.ListResult;
import com.selene.common.tree.support.PageTreeNode;
import com.selene.common.util.RedisClient;
import com.selene.dataing.model.DataingDatabase;
import com.selene.dataing.model.jdbc.DataingData;
import com.selene.dataing.model.util.DataingUtil;
import com.selene.templating.model.TemplatingContent;
import com.selene.templating.model.TemplatingItem;
import com.selene.templating.model.TemplatingModel;
import com.selene.templating.model.TemplatingModelBill;
import com.selene.templating.model.TemplatingPage;
import com.selene.templating.model.constants.EFilterStatus;
import com.selene.templating.model.constants.EModelType;
import com.selene.templating.model.service.TemplatingContentService;
import com.selene.templating.model.service.TemplatingItemService;
import com.selene.templating.model.service.TemplatingModelBillService;
import com.selene.templating.model.service.TemplatingModelService;
import com.selene.templating.model.service.TemplatingPageService;
import com.selene.templating.model.util.PageConfigers;
import com.selene.viewing.admin.service.ResourceService;
import com.selene.viewing.admin.service.config.ServiceConfigure;
import com.selene.viewing.admin.service.dataing.DataService;
import com.selene.viewing.admin.service.searching.SearchingService;
import com.selene.common.tree.DefaultTreeNode.PropertySetter;

import static cn.com.lemon.base.Strings.isNullOrEmpty;

@Service
public class TemplatingService {
	private final static Logger LOG = LoggerFactory.getLogger(TemplatingService.class.getName());
	private final static String TEMPLATING_KEY = ServiceConstants.TEMPLATING_KEY;
	@Resource
	private RedisClient redisClient;
	@Resource
	private SearchingService searchingService;
	@Resource
	private DataService dataService;
	@Resource
	private ResourceService resourceService;
	@Resource
	private ServiceConfigure serviceConfigure;

	/* ======Templating content process====== */

	/**
	 * Save {@code TemplatingContent}
	 * 
	 * @param content
	 * @return
	 */
	public int saveContent(TemplatingContent content) {
		LOG.debug("");
		// Initialize the required services
		TemplatingContentService contentService = serviceConfigure.service(TemplatingContentService.class,
				TemplatingContentService.class.getName(), TEMPLATING_KEY);
		// Business process
		if (content.getId() != null) {
			return contentService.update(content);
		} else {
			return contentService.insert(content);
		}
	}

	/**
	 * Find {@code TemplatingContent} by page number and item number.
	 * 
	 * @param pageId
	 * @param itemId
	 * @return {@code TemplatingItem}
	 */
	public TemplatingContent findContentByPageIdAndItemId(Integer pageId, Integer itemId) {
		// Initialize the required services
		TemplatingContentService contentService = serviceConfigure.service(TemplatingContentService.class,
				TemplatingContentService.class.getName(), TEMPLATING_KEY);
		// Business process
		return contentService.findByPageIdAndItemId(pageId, itemId);
	}

	/* ======Templating item process====== */
	/**
	 * Package data list content
	 * 
	 * @param pageId
	 * @param itemId
	 * @return {@link List}
	 */
	public ListArticle packagePageData(Integer pageId, Integer itemId) {
		ListArticle result = new ListArticle();
		// Initialize the required services
		TemplatingContentService contentService = serviceConfigure.service(TemplatingContentService.class,
				TemplatingContentService.class.getName(), TEMPLATING_KEY);
		TemplatingItemService itemService = /**/ serviceConfigure.service(TemplatingItemService.class,
				TemplatingItemService.class.getName(), TEMPLATING_KEY);
		// Business process
		TemplatingContent content = contentService.findByPageIdAndItemId(pageId, itemId);
		TemplatingPage page = findPageById(pageId);
		if (content != null) {
			DataingDatabase database = content.getBaseId() != null ? dataService.findDatabase(content.getBaseId())
					: null;
			/** Init article list header */
			result.setTitle(isNullOrEmpty(content.getContentName()) ? "" : content.getContentName());
			result.setImg(isNullOrEmpty(content.getContentThumb()) ? "" : content.getContentThumb());
			result.setSummary(isNullOrEmpty(content.getContentSummary()) ? "" : content.getContentSummary());
			switch (page.getPageType()) {
			case Site:
			case Home:
			case Subject:
				if (database != null) {
					String filePath = resourceService.realRelativePage(database.getPathCode());
					filePath = filePath.replace('\\', '/').replace("//", "/");
					result.setHref(filePath.endsWith("/") ? filePath + CommonConstants.DEFAULT_FREEMARKER_INDEX_HTML
							: filePath + "/" + CommonConstants.DEFAULT_FREEMARKER_INDEX_HTML);
				} else {
					result.setHref("#");
				}
				break;
			case List:
				result.setHref("#");
				break;
			case Detail:
			default:
				result.setHref("#");
				break;
			}
			/** Init article data list */
			/** Find data on searching index */
			if (database != null) {
				String queryString = ((null != content.getFilterCondition()
						&& content.getFilterStatus() == EFilterStatus.Normal) ? content.getFilterCondition()
								: CommonConstants.SEARCH_INDEX_ALL);
				TemplatingItem item = itemService.find(itemId);
				int start = 0;
				int size = item.getLineSize();
				Integer[] baseIds = { database.getId() };
				ListResult<DataingData> listResult = null;
				switch (item.getItemType()) {
				case Default:
					listResult = searchingService.search(queryString, start, size, baseIds);
					if (listResult != null && listResult.getData() != null && listResult.getData().size() > 0) {
						for (DataingData data : listResult.getData()) {
							Article article = DataingUtil.data(data);
							result.addArticle(article);
						}
					}
					break;
				case Image:
				case Mixed:
					queryString = isNullOrEmpty(queryString) ? "NOT (#int#" + FieldsConstants.IMGS + ":0)"
							: "(" + queryString + ") NOT (#int#" + FieldsConstants.IMGS + ":0)";
					listResult = searchingService.search(queryString, start, size, baseIds);
					if (listResult != null && listResult.getData() != null && listResult.getData().size() > 0) {
						for (DataingData data : listResult.getData()) {
							Article article = DataingUtil.data(data);
							result.addArticle(article);
							article.setImg(dataService.image(Integer.valueOf(data.getTableId()), data.getId()));
						}
					}
					break;
				case Catalog:
				case Banner:
				case Ad:
				case Video:
				default:
					break;
				}
			}
		}
		return result;
	}

	/**
	 * Find {@code TemplatingItem} by model number and code.
	 * 
	 * @param modelId
	 * @param code
	 * @return {@code TemplatingItem}
	 */
	public TemplatingItem findItemByModelIdAndCode(Integer modelId, String code) {
		// Initialize the required services
		TemplatingItemService itemService = serviceConfigure.service(TemplatingItemService.class,
				TemplatingItemService.class.getName(), TEMPLATING_KEY);
		// Business process
		return itemService.findByModelIdAndCode(modelId, code);
	}

	/**
	 * Find {@code TemplatingItem} by id.
	 * 
	 * @param id
	 * @return {@code List}
	 */
	public List<TemplatingItem> findModelItems(Integer modelId) {
		// Initialize the required services
		TemplatingItemService itemService = serviceConfigure.service(TemplatingItemService.class,
				TemplatingItemService.class.getName(), TEMPLATING_KEY);
		// Business process
		return itemService.findByModelId(modelId);
	}

	/* ======Templating page process====== */
	/**
	 * Save {@code TemplatingPage}
	 * 
	 * @param page
	 * @return
	 */
	public int savePage(TemplatingPage page) {
		// Initialize the required services
		TemplatingPageService pageService = serviceConfigure.service(TemplatingPageService.class,
				TemplatingPageService.class.getName(), TEMPLATING_KEY);
		// Business process
		if (page.getId() == null) {
			return pageService.insert(page);
		} else {
			return pageService.update(page);
		}
	}

	/**
	 * Find {@code TemplatingPage} by id.
	 * 
	 * @param id
	 * @return {@code TemplatingPage}
	 */
	public TemplatingPage findPageById(Integer id) {
		// Initialize the required services
		TemplatingPageService pageService = serviceConfigure.service(TemplatingPageService.class,
				TemplatingPageService.class.getName(), TEMPLATING_KEY);
		// Business process
		return pageService.find(id);
	}

	/**
	 * Find {@code TemplatingPage} tree by license.
	 * 
	 * @param license
	 * @return {@code List}
	 */
	public PageTreeNode pageTree(String license) {
		List<TemplatingPage> list = findByLicense(license);
		return PageTreeNode.parseTree(PageTreeNode.class, list, new PropertySetter<PageTreeNode, TemplatingPage>() {
			@Override
			public void setProperty(PageTreeNode node, TemplatingPage entity) {
				if (entity != null) {
					node.setPageType(entity.getPageType().ordinal());
				}
			}
		});
	}

	/**
	 * Find {@code TemplatingPage} by license.
	 * 
	 * @param license
	 * @return {@code List}
	 */
	public List<TemplatingPage> findByLicense(String license) {
		// Initialize the required services
		TemplatingPageService pageService = serviceConfigure.service(TemplatingPageService.class,
				TemplatingPageService.class.getName(), TEMPLATING_KEY);
		// Business process
		return pageService.findByLicense(license);
	}

	/**
	 * Find {@code TemplatingPage} by model id.
	 * 
	 * @param license
	 * @param modelId
	 * @return {@code List}
	 */
	public List<TemplatingPage> findByModelId(String license, Integer modelId) {
		// Initialize the required services
		TemplatingPageService pageService = serviceConfigure.service(TemplatingPageService.class,
				TemplatingPageService.class.getName(), TEMPLATING_KEY);
		// Business process
		return pageService.findByModelId(license, modelId);
	}

	/* ======Templating model process====== */
	/**
	 * Scan the model content and find the configuration area
	 * 
	 * @param modelId
	 * @return int
	 */
	public synchronized int scanModel(Integer modelId, File template) {
		// Initialize the required services
		TemplatingItemService itemService = /**/ serviceConfigure.service(TemplatingItemService.class,
				TemplatingItemService.class.getName(), TEMPLATING_KEY);
		TemplatingContentService contentService = serviceConfigure.service(TemplatingContentService.class,
				TemplatingContentService.class.getName(), TEMPLATING_KEY);
		// Business process
		if (template.exists() && template.isFile()) {
			try {
				String content = FileUtils.readFileToString(template, Charset.forName("UTF-8"));
				List<TemplatingItem> newItemList = PageConfigers.editable(modelId, content);
				List<TemplatingItem> oldItemList = itemService.findByModelId(modelId);
				List<Integer> updateItemId = new ArrayList<Integer>();
				// Update old item or insert new item
				if (newItemList != null && newItemList.size() > 0) {
					for (TemplatingItem item : newItemList) {
						if (!isNullOrEmpty(item.getItemCode())) {
							TemplatingItem oldItem = itemService.findByModelIdAndCode(modelId, item.getItemCode());
							if (oldItem != null && oldItem.getId() != null) {
								updateItemId.add(oldItem.getId());
								item.setId(oldItem.getId());
								itemService.update(item);
							} else {
								itemService.insert(item);
							}
						}
					}
				}
				// Delete old not used item
				if (
				/* Exist history scan */ (oldItemList != null && oldItemList.size() > 0) && updateItemId.size() > 0) {
					Integer[] updatedItems = updateItemId.toArray(new Integer[updateItemId.size()]);
					Arrays.sort(updatedItems);
					for (TemplatingItem delItem : oldItemList) {
						Integer delItemId = delItem.getId();
						if (/* Not find,Need delete */Arrays.binarySearch(updatedItems, delItemId) < 0) {
							if (/* Delete old item */itemService.delete(delItemId) > 0) {
								contentService.deleteByItemId(delItemId);
							}
						}
					}
				}
				return 1;
			} catch (Exception e) {
				return 0;
			}
		}
		return 0;
	}

	/**
	 * Find {@code TemplatingModel} by license and {@code EModelType} type
	 * 
	 * @param license
	 * @param type
	 * @return {@code List}
	 */
	public List<TemplatingModel> findModelByType(String license, EModelType type) {
		// Initialize the required services
		TemplatingModelService modelService = serviceConfigure.service(TemplatingModelService.class,
				TemplatingModelService.class.getName(), TEMPLATING_KEY);
		// Business process
		return modelService.findByLicenseAndType(license, type);
	}

	/**
	 * Delete {@code TemplatingModel}
	 * 
	 * @param modelId
	 * @return
	 */
	public int deleteModel(Integer modelId) {
		// Initialize the required services
		TemplatingModelService modelService = serviceConfigure.service(TemplatingModelService.class,
				TemplatingModelService.class.getName(), TEMPLATING_KEY);
		// Business process
		return modelService.delete(modelId);
	}

	/**
	 * Save {@code TemplatingModel}
	 * 
	 * @param modelBill
	 * @return
	 */
	public int saveModel(TemplatingModel model) {
		// Initialize the required services
		TemplatingModelService modelService = serviceConfigure.service(TemplatingModelService.class,
				TemplatingModelService.class.getName(), TEMPLATING_KEY);
		// Business process
		if (model.getId() == null) {
			int result = modelService.insert(model);
			model.setId(result);
			return result;
		} else {
			return modelService.update(model);
		}
	}

	/**
	 * Find {@code TemplatingModel} by primary key
	 * 
	 * @param id
	 * @return {@code TemplatingModel}
	 */
	public TemplatingModel findModelById(Integer id) {
		// Initialize the required services
		TemplatingModelService modelService = serviceConfigure.service(TemplatingModelService.class,
				TemplatingModelService.class.getName(), TEMPLATING_KEY);
		// Business process
		return modelService.find(id);
	}

	/**
	 * Query {@code TemplatingModel} by {@code String} and {@code Integer}
	 * 
	 * @param license
	 * @param billId
	 * @return
	 */
	public List<TemplatingModel> findModelByLicenseAndBillId(String license, Integer billId) {
		// Initialize the required services
		TemplatingModelService modelService = serviceConfigure.service(TemplatingModelService.class,
				TemplatingModelService.class.getName(), TEMPLATING_KEY);
		// Business process
		return modelService.findByLicenseAndBillId(license, billId);
	}

	/* ======Templating model bill process====== */
	/**
	 * Save {@code TemplatingModelBill}
	 * 
	 * @param modelBill
	 * @return
	 */
	public int saveModelBill(TemplatingModelBill modelBill) {
		// Initialize the required services
		TemplatingModelBillService modelBillService = serviceConfigure.service(TemplatingModelBillService.class,
				TemplatingModelBillService.class.getName(), TEMPLATING_KEY);
		// Business process
		if (modelBill.getId() == null) {
			int result = modelBillService.insert(modelBill);
			modelBill.setId(result);
			return result;
		} else {
			return modelBillService.update(modelBill);
		}
	}

	/**
	 * Find {@code TemplatingModelBill} by primary key
	 * 
	 * @param id
	 * @return {@code TemplatingModelBill}
	 */
	public TemplatingModelBill findModelBillById(Integer id) {
		// Initialize the required services
		TemplatingModelBillService modelBillService = serviceConfigure.service(TemplatingModelBillService.class,
				TemplatingModelBillService.class.getName(), TEMPLATING_KEY);
		// Business process
		return modelBillService.find(id);
	}

	/**
	 * Query all {@code TemplatingModelBill} by {@code String} license
	 * 
	 * @param license
	 * @return {@code List}
	 */
	public List<TemplatingModelBill> findModelBillByLicense(String license) {
		// Initialize the required services
		TemplatingModelBillService modelBillService = serviceConfigure.service(TemplatingModelBillService.class,
				TemplatingModelBillService.class.getName(), TEMPLATING_KEY);
		// Business process
		return modelBillService.findByLicense(license);
	}
}
