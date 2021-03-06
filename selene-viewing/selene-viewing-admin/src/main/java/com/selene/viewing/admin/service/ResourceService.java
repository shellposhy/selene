package com.selene.viewing.admin.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import com.selene.common.config.service.BaseConfiger;
import com.selene.common.constants.ResourceConstants;
import com.selene.common.constants.util.EResult;
import com.selene.common.util.DataUtil;

import static cn.com.lemon.base.Strings.isNullOrEmpty;

@Service
public class ResourceService {
	private BaseConfiger configer;

	@PostConstruct
	public void init() {
		configer = /* Properties profile */new BaseConfiger(
				ResourceService.class.getResource("/").getPath() + "config.properties");
	}

	/**
	 * Get value by key
	 * 
	 * @param key
	 * @return {@code String}
	 */
	public String path(String key) {
		return configer.value(key);
	}

	/**
	 * Get resource root path
	 * 
	 * @return {@code String}
	 */
	public String root() {
		return path(ResourceConstants.RESOURCE_BASE_PATH_KEY);
	}

	/* The path for resource images */
	/**
	 * Get temporary images path
	 */
	public String tmpPic() {
		return root() + ResourceConstants.TEMP_PIC_PATH;
	}

	public String tmpPic(String path) {
		return tmpPic() + "/" + path;
	}

	public String tmpRelativePic(String path) {
		return ResourceConstants.TEMP_PIC_PATH + "/" + path;
	}

	/**
	 * Get real images path
	 */
	public String realPic() {
		return root() + ResourceConstants.BASE_PIC_PATH;
	}

	/**
	 * Get real images path
	 * 
	 * @param baseId
	 * @param createTime
	 *            <P>
	 *            The create time format for yyyyMMdd
	 * @return {@code String}
	 */
	public String realPic(Integer baseId, String createTime) {
		return realPic() + "/" + baseId + "/" + createTime.substring(0, 4) + "/"
				+ Integer.parseInt(createTime.substring(4, 6));
	}

	/**
	 * Get real images path
	 * 
	 * @param baseId
	 * @param createTime
	 *            <P>
	 *            The create time format for yyyyMMdd
	 * @return {@code String}
	 */
	public String realRelativePic(Integer baseId, String createTime) {
		return ResourceConstants.BASE_PIC_PATH + "/" + baseId + "/" + createTime.substring(0, 4) + "/"
				+ Integer.parseInt(createTime.substring(4, 6));
	}

	/* The path for resource document */
	/**
	 * Get temporary documents path
	 */
	public String tmpDoc() {
		return root() + ResourceConstants.TEMP_DOC_PATH;
	}

	public String tmpDoc(String path) {
		return tmpDoc() + "/" + path;
	}

	/**
	 * Get real documents path
	 */
	public String realDoc() {
		return root() + ResourceConstants.BASE_DOC_PATH;
	}

	/**
	 * Get real images path
	 * 
	 * @param baseId
	 * @param createTime
	 *            <P>
	 *            The create time format for yyyyMMdd
	 * @return {@code String}
	 */
	public String realDoc(Integer baseId, String uuid) {
		return realDoc() + "/" + baseId + "/" + uuid;
	}

	/* The path for resource template */
	/**
	 * Get temporary template path
	 */
	public String tmpTemplate() {
		return root() + ResourceConstants.TEMP_TEMPLATE_PATH;
	}

	public String tmpTemplate(String path) {
		return tmpTemplate() + "/" + path;
	}

	/**
	 * Get real template path
	 */
	public String realTemplate() {
		return root() + ResourceConstants.BASE_TEMPLATE_PATH;
	}

	public String realTemplate(String path) {
		return realTemplate() + "/" + path;
	}

	/* The path for resource static */
	/**
	 * Get real static path
	 */
	public String realStatic() {
		return root() + ResourceConstants.BASE_STATIC_PATH;
	}

	public String realStatic(String path) {
		return realStatic() + "/" + path;
	}

	/**
	 * Get real relative static path
	 */
	public String realRelativeStatic() {
		return ResourceConstants.BASE_STATIC_PATH;
	}

	public String realRelativeStatic(String path) {
		return ResourceConstants.BASE_STATIC_PATH + "/" + path;
	}

	/* The path for resource page */
	/**
	 * Get real page path
	 */
	public String realPage() {
		return root() + ResourceConstants.BASE_PAGE_PATH;
	}

	public String realPage(String path) {
		return realPage() + "/" + path;
	}

	/**
	 * Get real relative static path
	 */
	public String realRelativePage() {
		return ResourceConstants.BASE_PAGE_PATH;
	}

	public String realRelativePage(String path) {
		return ResourceConstants.BASE_PAGE_PATH + "/" + path;
	}

	/**
	 * Delete folder files and folder
	 * 
	 * @param path
	 * @return {@code Boolean}
	 */
	public boolean deleteFolder(String path) {
		try {
			FileUtils./* Copy files success and delete temporary folder */deleteDirectory(new File(path));
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Replace the image path in the text ，from the temporary path to the real
	 * path.
	 * 
	 * @param content
	 * @param source
	 * @param relativeTarget
	 * @param containOldPath
	 *            <p>
	 *            if true,{@code String} Source path is "/pic" and {@code File}
	 *            path is "/pic/m/1.jpg",file copy path is {@code String}
	 *            target+/m/1.jpg
	 *            <p>
	 *            if false,{@code String} Source path is "/pic" and {@code File}
	 *            path is "/pic/m/1.jpg",file copy path is {@code String}
	 *            target+/1.jpg
	 */
	public String replace(String content, String relativeSource, String relativeTarget, boolean containOldPath) {
		if (isNullOrEmpty(relativeTarget) || isNullOrEmpty(relativeSource)) {
			return content;
		}
		List<String> contentPathList = DataUtil.imgs(content);
		if (contentPathList != null && contentPathList.size() > 0) {
			for (String path : contentPathList) {
				int length = 0;
				if (/* Match user uploaded image */path.startsWith(relativeSource)) {
					if (/* Contain old path */containOldPath) {
						length = relativeSource.length();
					} else /* Not contain old path */ {
						length = path.lastIndexOf("/");
					}
					content = content.replaceAll(path, (relativeTarget.endsWith("/"))
							? relativeTarget + path.substring(length + 1) : relativeTarget + path.substring(length));
				}
			}
		}
		return content;
	}

	/**
	 * Copy files from source directory to target directory
	 * 
	 * @param source
	 * @param target
	 * @param containOldPath
	 *            <p>
	 *            if true,{@code String} Source path is "/pic" and {@code File}
	 *            path is "/pic/m/1.jpg",file copy path is {@code String}
	 *            target+/m/1.jpg
	 *            <p>
	 *            if false,{@code String} Source path is "/pic" and {@code File}
	 *            path is "/pic/m/1.jpg",file copy path is {@code String}
	 *            target+/1.jpg
	 * @return {@code Boolean}
	 */
	public EResult copyDirectoryToDirectory(String source, String target, boolean containOldPath) {
		if (isNullOrEmpty(source) || isNullOrEmpty(target)) {
			return EResult.Null;
		}
		File sourceDirectory = new File(source);
		if (sourceDirectory.exists() && sourceDirectory.isDirectory()) {
			List<File> fileList = files(source);
			if (fileList != null && fileList.size() > 0) {
				try {
					for (File file : fileList) {
						if (containOldPath) {
							FileUtils.copyFile(file, (target.endsWith("/"))
									? new File(target + file.getAbsolutePath().substring(source.length() + 1))
									: new File(target + file.getAbsolutePath().substring(source.length())));
						} else {
							FileUtils.copyFileToDirectory(file, new File(target));
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				return EResult.Success;
			} else {
				return EResult.No;
			}
		} else {
			return EResult.Fail;
		}
	}

	/**
	 * Get all files on folder path
	 * 
	 * @param directory
	 *            folder
	 * @return {@link List}
	 */
	public List<File> files(String directory) {
		File folder = new File(directory);
		if (folder.exists() && folder.isDirectory()) {
			return new ArrayList<File>(FileUtils.listFiles(folder, null, true));
		}
		return null;
	}
}
