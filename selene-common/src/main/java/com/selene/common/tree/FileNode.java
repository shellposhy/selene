package com.selene.common.tree;

import java.io.Serializable;
import java.util.List;

/**
 * The class for file tree node
 * 
 * @author shaobo shih
 * @version 1.0
 */
public class FileNode implements Serializable {
	private static final long serialVersionUID = 3609199571828094707L;
	private String name;
	private boolean directory;
	private List<FileNode> children;
	private String absolutePath;
	private String filePath;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDirectory() {
		return directory;
	}

	public void setDirectory(boolean directory) {
		this.directory = directory;
	}

	public List<FileNode> getChildren() {
		return children;
	}

	public void setChildren(List<FileNode> children) {
		this.children = children;
	}

	public String getAbsolutePath() {
		return absolutePath;
	}

	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
