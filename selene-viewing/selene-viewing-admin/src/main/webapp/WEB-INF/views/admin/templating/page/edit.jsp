<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
	<body>
		<div id="content" class="span12 ml0">
			<div class="mt10">
				<ul class="breadcrumb ind_f_tree" value="21">
					<li><a href="${appPath}/admin" target="_self">${appName}</a> <span class="divider">/</span></li>
					<li><a href="${appPath}/admin/templating/page" target="_self">页面管理</a><span class="divider">/</span></li>
					<li><a href="#">页面编辑</a></li>
				</ul>
			</div>
			<form:form modelAttribute="templatingPage" class="form-horizontal viewPageForm active" 
				action="${appPath}/admin/templating/page/save" method="post" id="pageForm">
				<fieldset>
					<legend>
						<span class="icon32 icon-inbox floatl active"></span>页面编辑
					</legend>
					<div class="control-group">
						<label class="control-label" for="name">页面名称 </label>
						<div class="controls">
							<form:hidden path="id"/>
							<form:hidden path="parentId"/>
							<form:input path="name" id="name" class="typeahead"/>
							<label class="error"><form:errors path="name" cssClass="error" /></label>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="code">页面编码 </label>
						<div class="controls">
							<form:input path="code" id="code"  class="typeahead"/>
							<label class="error"><form:errors path="code" cssClass="error" /></label>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="pageType">页面类型</label>
						<div class="controls">
							<form:select path="pageType" id="pageType">
								<form:option value="">==请选择==</form:option>
								<form:option value="Site">站点</form:option>
								<form:option value="Home">首页模板</form:option>
								<form:option value="Subject">专题模板</form:option>
								<form:option value="List">列表模板</form:option>
								<form:option value="Detail">详情模板</form:option>
							</form:select>
							<label class="error"><form:errors path="pageType" cssClass="error" /></label>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="pageModelId">页面模板</label>
						<div class="controls">
							<form:select path="pageModelId" id="pageModelId"></form:select>
						</div>
					</div>
					<div class="form-actions">
						<button id="viewPage_btn" type="submit" class="btn btn-primary">保存</button>
						<button name="cancel" value="取消" class="btn backBtn">取消</button>
					</div>
				</fieldset>
			</form:form>
		</div>
		<script src="${appPath}/admin/js/original/business/templating/page.js"></script>
			<!-- <script src="${appPath}/admin/js/min/business/templating/page.min.js"></script>-->
	</body>
</html>