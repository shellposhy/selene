<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="org.slf4j.LoggerFactory"%>
<%@page import="org.slf4j.Logger"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="com.selene.viewing.admin.service.ResourceService"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<%@ page import="javax.servlet.ServletContext"%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>
<%
	Logger LOG = LoggerFactory.getLogger("Image uploader");
	String baseId = request.getParameter("baseId");
	LOG.debug("baseId=" + baseId);
	
	ApplicationContext ac = /*Spring bean*/WebApplicationContextUtils
			.getRequiredWebApplicationContext(request.getSession().getServletContext());
	ResourceService pathService = (ResourceService) ac.getBean("resourceService");
	String realPath = /*Real images directory*/pathService.realPic() + "/" + baseId;
	String rootPath = /*Root directory*/pathService.root();

	List<File> files = /*Find all images files*/ getFiles(realPath, new ArrayList());
	String imgStr = "";
	for (File file : files) {
		imgStr += file.getPath().substring(rootPath.length()) + "ue_separate_ue";
	}
	if (imgStr != "") {
		imgStr = imgStr.substring(0, imgStr.lastIndexOf("ue_separate_ue")).replace(File.separator, "/").trim();
	}
	LOG.debug("imgStr=" + imgStr);
	
	out.print(imgStr);
%>

<%!
	public List getFiles(String realpath, List files) {
		File realFile = new File(realpath);
		if (realFile.isDirectory()) {
			File[] subfiles = realFile.listFiles();
			for (File file : subfiles) {
				if (file.isDirectory()) {
					getFiles(file.getAbsolutePath(), files);
				} else {
					if (!getFileType(file.getName()).equals("")) {
						files.add(file);
					}
				}
			}
		}
		return files;
	}

	public String getFileType(String fileName) {
		String[] fileType = { ".jpg", ".png", ".jpeg", ".gif", ".bmp", ".webp" };
		Iterator<String> type = Arrays.asList(fileType).iterator();
		while (type.hasNext()) {
			String t = type.next();
			if (fileName.endsWith(t)) {
				return t;
			}
		}
		return "";
	}
%>