<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<body>
		<div id="content" class="span12">
			<div class="mt10">
				<ul class="breadcrumb ind_f_tree" value="22">
					<li><a href="${appPath }/admin" target="_self">${appName}</a> <span class="divider">/</span></li>
					<li><a href="#" target="_self">模板管理</a><span class="divider">/</span></li>
					<li><a href="#">模板目录管理</a></li>
				</ul>
			</div>
			<div class="box-content">
				<div id="myTabContent" class="tab-content">
					<div class="tab-pane active" id="setting">
						<form:form modelAttribute="modelBill" class="form-horizontal u_group_form" id="bill_form" 
								action="${appPath}/admin/templating/model/bill/save" method="post" target="_self">
							<fieldset>
								<legend>
									<span title="" class="icon32 icon-inbox floatl"></span>模板目录编辑
								</legend>
								<div class="control-group">
									<label class="control-label" for="database_name">目录名称</label>
									<div class="controls">
										<form:hidden path="id" />
										<form:input path="name" name="name" class="typeahead" />
										<label class="error"><form:errors path="name" cssClass="error" /> </label>
									</div>
								</div>
								<div class="control-group backhide">
									<label class="control-label" for="database_name">目录编码</label>
									<div class="controls">
										<form:input path="code" name="code" class="typeahead"  />
										<label class="error"><form:errors path="code" cssClass="error" /> </label>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="type">目录类型</label>
									<div class="controls">
										<form:select path="type" class="autogrow" name="type">
											<c:forEach var="node" items="${typeList}">
												<form:option value="${node.id}">${node.name }</form:option>
											</c:forEach>
										</form:select>
										<label class="error"><form:errors path="type" cssClass="error" /></label>
									</div>
								</div>
								<div class="control-group backhide" class="tree_sel_box" id="bill_tree">
									<label class="control-label" for="u_group_memo">父目录</label>
									<div class="controls">
										<input class="treeRadio" type="text" readonly value="" />
										<label class="error"><form:errors path="parentId" cssClass="error" /> </label>
										<form:hidden path="parentId" class="treeSelId" name="parentId" value="" />
									</div>
									<div class="menuContent">
										<ul id="treeSel_1" class="ztree treeNew"></ul>
										<a class="selOk btn btn-small" href="#"><i class="icon-ok"></i>确定</a>
									</div>
								</div>
								<div class="form-actions">
									<button type="submit" class="btn btn-primary">保存</button>
								</div>
							</fieldset>
						</form:form>
					</div>
				</div>
			</div>
		</div>
		<script src="${appPath}/admin/js/min/business/templating/model.min.js"></script>
	</body>
</html>