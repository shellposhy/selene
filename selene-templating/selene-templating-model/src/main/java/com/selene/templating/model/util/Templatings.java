package com.selene.templating.model.util;

import static cn.com.lemon.base.Strings.isNullOrEmpty;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.selene.common.constants.CommonConstants;
import com.selene.templating.model.constants.EReplaceType;

import cn.com.lemon.base.zip.Zip4Js;

import net.lingala.zip4j.exception.ZipException;

/**
 * Static utilities for page templates
 * 
 * @author shih shaobo
 * @version 1.0
 */
public final class Templatings {
	private static final Logger LOG = LoggerFactory.getLogger(Templatings.class.getName());

	private Templatings() {
	}

	/**
	 * Path processing
	 * <p>
	 * if (/data/resource/name)</br>
	 * delete path (/data/resource)</br>
	 * and then (/name)
	 * 
	 * @param totalPath
	 * @param delPath
	 * @return
	 */
	public static String path(String totalPath, String delPath) {
		if (isNullOrEmpty(delPath)) {
			return totalPath;
		}
		return totalPath.substring(delPath.length());
	}

	/**
	 * Replace the static resource path of the page template {@code HTML}.
	 * 
	 * <pre>
	 * href="css/common.css"
	 * </br>
	 * href="/static/css/common.css"
	 * </pre>
	 * 
	 * @param content
	 * @param type
	 * @param path
	 * @return {@code String}
	 */
	public static void replace(File file, EReplaceType type, String path) {
		if (file == null || !file.exists() || !file.isFile() || type == null) {
			return;
		}
		try {
			String content = /* Read file content */ FileUtils.readFileToString(file, Charset.forName("UTF-8"));
			List<String> replaceList = null;
			String prefix = "";
			switch (type) {
			case IMG:
				replaceList = regex(content, CommonConstants.HTML_IMG_SRC);
				prefix = "images";
				break;
			case CSS:
				replaceList = regex(content, CommonConstants.HTML_LINK_HREF);
				prefix = "css";
				break;
			case JSCRIPT:
			default:
				replaceList = regex(content, CommonConstants.HTML_SCRIPT_SRC);
				prefix = "js";
				break;
			}
			if (replaceList != null && replaceList.size() > 0) {
				path = path.replace('\\', '/').replace("//", "/");
				for (String repace : replaceList) {
					if (!isNullOrEmpty(repace) && repace.startsWith(prefix)) {
						String tmpPath = path.endsWith("/") ? path : path + "/";
						String realPath = tmpPath + repace;
						content = content.replaceAll(repace, realPath.replace('\\', '/').replace("//", "/"));
						/* Write file the new content */FileUtils.write(file, content, Charset.forName("UTF-8"));
					}
				}
			}
		} catch (IOException e) {
			LOG.error("Read file  fail!", e);
		}
	}

	/**
	 * Unzip the template file
	 * 
	 * @param zipFile
	 * @param target
	 * @return
	 */
	public static void unzip(File zipFile, String target) {
		if (isNullOrEmpty(target)) {
			return;
		}
		if (zipFile.exists() && zipFile.isFile() && zipFile.getName().endsWith(".zip")) {
			String /* Temporary unzip path */ tmpUnzipPath = zipFile.getParentFile().getAbsolutePath();
			String path = null;
			if (!isNullOrEmpty(tmpUnzipPath)) {
				try {
					/** File path processing */
					File[] files = Zip4Js.unzip(zipFile, tmpUnzipPath, null);
					if (/* Exist file,not all file is directory */files.length > 0) {
						List<File> htmlFileList = new ArrayList<File>();
						for (File file : files) {
							if (file.getName().endsWith(".html")) {
								htmlFileList.add(file);
							}
						}
						if (/* Template files allow only one HTML file */htmlFileList.size() == 1) {
							path = htmlFileList.get(0).getParentFile().getAbsolutePath();
						}
					}
					/** Copy template files to target path */
					if (!isNullOrEmpty(path)) {
						if (!copy(path, target, true)) {
							LOG.error("Copy template zip files fail!");
						}
					}
				} catch (ZipException e) {
					LOG.error(zipFile.getName() + " unzip fail!", e);
				}
			}
		}
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
	public static boolean copy(String source, String target, boolean containOldPath) {
		if (isNullOrEmpty(source) || isNullOrEmpty(target)) {
			return false;
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
					LOG.error("Copy files fail!", e);
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * Get all files on folder path
	 * 
	 * @param directory
	 *            folder
	 * @return {@link List}
	 */
	public static List<File> files(String directory) {
		File folder = new File(directory);
		if (folder.exists() && folder.isDirectory()) {
			return new ArrayList<File>(FileUtils.listFiles(folder, null, true));
		}
		return null;
	}

	/**
	 * Gets the result from the given content through a regular expression.
	 * 
	 * @param content
	 * @param pattern
	 * @return {@code List}
	 */
	public static List<String> regex(String content, Pattern pattern) {
		List<String> result = new ArrayList<String>();
		Matcher m = pattern.matcher(content);
		String quote = null;
		String src = null;
		while (m.find()) {
			quote = m.group(1);
			src = (quote == null || quote.trim().length() == 0) ? m.group(2).split("\\s+")[0] : m.group(2);
			result.add(src);
		}
		return result.size() > 0 ? result : null;
	}
}
