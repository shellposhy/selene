<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.slf4j.LoggerFactory"%>
<%@page import="org.slf4j.Logger"%>
<%@page import="com.selene.viewing.admin.service.ResourceService"%>
<%@ page import="com.selene.dataing.model.util.DataingUploader"%>
<%
	Logger LOG = LoggerFactory.getLogger(DataingUploader.class.getName());
	request./*Set character encoding */setCharacterEncoding("utf-8");
	response./*Set character encoding */setCharacterEncoding("utf-8");
	String uuid = request.getParameter("uuid");

	ApplicationContext ac = /*Spring bean*/WebApplicationContextUtils
			.getRequiredWebApplicationContext(request.getSession().getServletContext());
	ResourceService pathService = (ResourceService) ac.getBean("resourceService");
	String tmpUploadPath = /*Upload temp file path*/pathService.tmpPic();
	LOG.info("Temp upload path=" + tmpUploadPath);

	DataingUploader uploader = /*Business process*/ new DataingUploader(request);
	uploader.setSavePath(tmpUploadPath + "/" + uuid);
	String[] fileType = { ".jpg", ".png", ".jpeg", ".gif", ".bmp", ".webp" };
	uploader.setAllowFiles(fileType);
	uploader.setMaxSize(10000);
	uploader.upload();
	response.getWriter()
			.print("{'original':'" + uploader.getOriginalName() + "','url':'" + "tmp/pic/" + uuid + "/"
					+ uploader.getFileName() + "','title':'" + uploader.getTitle() + "','state':'"
					+ uploader.getState() + "'}");
%>
