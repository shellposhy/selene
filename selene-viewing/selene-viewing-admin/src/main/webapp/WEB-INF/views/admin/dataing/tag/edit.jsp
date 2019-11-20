<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<body>
		<div id="content" class="span12">
			<div class="mt10">
				<ul class="breadcrumb ind_f_tree" value="43">
					<li><a href="${appPath}/admin" target="_self">${appName}</a> <span class="divider">/</span></li>
					<li><a href="${appPath}/admin/dataing/tag" target="_self">数据标签</a><span class="divider">/</span></li>
					<li><a href="#">数据标签编辑</a></li>
				</ul>
			</div>
			<div class="box-content">
				<form:form modelAttribute="dataTag" class="form-horizontal u_group_form" id="db_sort_new_form"
					action="${appPath}/admin/dataing/tag/save" method="post" target="_self">
					<fieldset>
						<legend>
							<span title="" class="icon32 icon-inbox floatl"> </span>数据标签编辑
						</legend>
						<div class="control-group">
							<label class="control-label" for="db_name">名称</label>
							<div class="controls">
								<form:hidden path="id" />
								<form:input path="name" class="typeahead" id="db_name" />
								<label class="error"><form:errors path="name" cssClass="error" /></label>
							</div>
						</div>
						<div class="control-group backhide">
							<label class="control-label" for="db_code">编码</label>
							<div class="controls">
								<form:input path="code" class="typeahead" id="db_code" />
								<label class="error"><form:errors path="code" cssClass="error" /></label>
							</div>
						</div>
						<div class="control-group backhide" class="tree_sel_box" id="tagsTree">
							<label class="control-label" for="u_group_memo">父分类</label>
							<div class="controls">
								<input class="treeRadio" type="text" readonly value="" />
								<form:hidden path="parentId" class="treeSelId" name="parentId" value="" />
								<label class="error"><form:errors path="parentId" cssClass="error" /></label>
							</div>
							<div class="menuContent">
								<ul id="treeSel_1" class="ztree treeNew"></ul>
								<a class="selOk btn btn-small" href="#"><i class="icon-ok"></i>确定</a>
							</div>
						</div>
						<div class="form-actions">
							<button type="submit" class="btn btn-primary">保存</button>
							<form:button name="cancel" value="取消" class="btn backBtn">取消</form:button>
						</div>
					</fieldset>
				</form:form>
			</div>
		</div>
		<script type="text/javascript" 	src="${appPath}/admin/js/min/business/dataing/tag.min.js"></script>
	</body>
</html>