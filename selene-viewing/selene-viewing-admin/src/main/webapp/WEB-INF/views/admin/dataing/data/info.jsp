<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<body>
	<div id="content">
		<div class="mt10">
			<ul class="breadcrumb ind_f_tree" id="info_page_src" value="42">
				<li><a href="${appPath }/admin">${appName}</a> <span class="divider">/</span></li>
				<li><a href="#">稿件预览</a></li>
			</ul>
		</div>
		<div class="container-fliud">
			<div class="row-fliud offset1">
				<div class="span12 well aticl_pve pricehover img_center">
					<div class="page-header center">
						<h2 class="">${data.title}</h2>
						<small>${data.authors} ${data.docTime}</small>
					</div>
					${data.content}
					<c:if test="${!empty attachList}">
						<c:if test="${fn:length(attachList) > 0 }">
							<div class='attach_div'>
								<h3>
									<span class='icon icon-blue icon-attachment'></span>附件
								</h3>
								<c:forEach var="fileName" items="${attachList }" varStatus="status">
									<p>
										<span>
											附件_${status.count}&nbsp;
											<a href="${appPath }/admin/dataing/library/data/download?fileName=${fileName}">下载</a>
										</span>
									</p>
								</c:forEach>
							</div>
						</c:if>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	</div>
</body>
</html>