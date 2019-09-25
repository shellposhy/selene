<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<body>
		<div id="content" class="span12">
			<div class="mt10">
				<ul class="breadcrumb ind_f_tree" value="${jspPageId}">
					<li><a href="${appPath}/admin" target="_self">${appName}</a> <span class="divider">/</span></li>
					<li><a href="#"><font color="red">${rangeName}</font> 数据</a></li>
				</ul>
			</div>
			<div id="godatadiv" class="mb20"></div>
			<div class="box-content">
				<div class="btn-toolbar floatl">
					<a class="btn btn-small" id="into_as_search" href="${appPath }/admin/searching/advance"><i></i> 进入高级查询</a>
				</div>
				<table cellspacing="0" border="0" class="table table-striped table-bordered bootstrap-datatable trHoverEdit trHoverModi" id="dataTable">
					<thead>
						<tr>
							<th>标题</th><th>稿件日期</th><th>稿件作者</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
					<tfoot>
						<tr>
							<th>标题</th><th>稿件日期</th><th>稿件作者</th>
						</tr>
					</tfoot>
				</table>
			</div>
		</div>
		<input type="hidden" id="type" value="${type }">
		<input type="hidden" id="range" value="${range }">
		<input type="hidden" id="term" value="${term }">
		<input type="hidden" id="searchToken" value="${searchToken}" />
		<script src="${appPath}/admin/jscript/searching/min/search.js"></script>
	</body>
</html>