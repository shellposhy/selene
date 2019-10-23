<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
	<body>
		<div id="content">
			<div class="mt10">
				<ul class="breadcrumb ind_f_tree" value="22">
					<li><a href="${appPath }/admin" target="_self">${appName}</a> <span class="divider">/</span></li>
					<li><a href="${appPath }/admin/templating/model" target="_self">模板管理</a><span class="divider">/</span></li>
					<li><a href="#">模板浏览</a></li>
				</ul>
			</div>
			<div class="box-content">
				<div class="tab-content">
					<div class="tab-pane clearfix  tree_separated fade in active" id="list_tab" style="min-height: 600px;">
						<div class="content_wrap clearfix">
							<div class="left_tree">
								<ul id=filesTree class="ztree green_tree_line"></ul>
							</div>
							<div class="right_con">
								<form id="fileMng" class="form-horizontal" method="post">
									<input type="hidden" id="modelId" value="${modelId}">
									<div class="control-group">
										<label class="control-label">文件名：</label>
										<div class="controls">
											<input type="text" id="fileName" name="fileName" readonly="readonly" value="">
										</div>
										<br>
										<label class="control-label">文件内容：</label>
										<div class="controls">
											<textarea style= width:1000px;height:600px;" name="content" id="fileContent"></textarea>
											<img alt="图片" src="#" id="imgFile" hidden="true">
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<script src="${appPath}/admin/js/min/business/templating/model.min.js"></script>
	</body>
</html>