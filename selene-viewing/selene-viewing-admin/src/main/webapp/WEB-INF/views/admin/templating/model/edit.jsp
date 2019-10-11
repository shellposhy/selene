<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
	<body>
		<div id="content" class="span12">
			<div class="mt10">
				<ul class="breadcrumb ind_f_tree" value="22">
					<li><a href="${appPath }/admin" target="_self">${appName}</a> <span class="divider">/</span></li>
					<li><a href="${appPath }/admin/view/model" target="_self">模板管理</a><span class="divider">/</span></li>
					<li><a href="#">编辑模板</a></li>
				</ul>
			</div>
			<fieldset><legend> 编辑模板 </legend></fieldset>
			<iframe name="aa" src="" style="display: none"></iframe>
			<form:form modelAttribute="templatingModel" enctype="multipart/form-data" class="form-horizontal u_group_form" id="pageModelForm"
				action="${appPath}/admin/templating/model/save" method="post" target="_self">
				<form:hidden path="id" />
				<form:hidden path="modelType"/>
				<form:hidden path="billId"/>
				<fieldset>
					<div class="control-group">
						<label class="control-label" for="modelName">模板名称 </label>
						<div class="controls">
							<form:input path="modelName" name="modelName" class="typeahead"/>
							<label class="error"><form:errors path="modelName" cssClass="error" /> </label>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="modelCode">模板编码 </label>
						<div class="controls">
							<form:input path="modelCode" name="modelCode" class="typeahead"/>
							<label class="error"><form:errors path="modelCode" cssClass="error" /> </label>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">上传模板 </label>
						<div class="controls">
							<form id="pupdate_form" enctype="multipart/form-data">
								<input name="file" type="file" accept="application/zip,application/x-zip,application/x-zip-compressed"/>
							</form>
						</div>
					</div>
					<div class="form-actions">
						<button type="submit" class="btn btn-primary">保存</button>
					</div>
				</fieldset>
			</form:form>
		</div>
		<script src="${appPath}/admin/js/original/business/templating/model.js"></script>
		<!-- <script src="${appPath}/admin/js/min/business/templating/model.min.js"></script>-->
	</body>
</html>