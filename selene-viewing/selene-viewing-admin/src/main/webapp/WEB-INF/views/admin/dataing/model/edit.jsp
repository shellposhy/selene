<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
	<body>
		<div id="content" class="span12">
			<div class="mt10">
				<ul class="breadcrumb ind_f_tree" value="61">
					<li><a href="${appPath }/admin" target="_self">${appName}</a> <span class="divider">/</span></li>
					<li><a href="${appPath}/admin/dataing/model" target="_self">数据模板</a><span class="divider">/</span></li>
					<li><a href="#">编辑</a>
					</li>
				</ul>
			</div>
			<div class="box-content">
				<div id="myTabContent" class="tab-content">
					<div class="tab-pane active" id="setting">
						<form:form modelAttribute="dataModel" class="form-horizontal u_group_form" id="cl_new_form" action="${appPath}/admin/dataing/model/save" method="post" target="_self">
							<fieldset>
								<legend>
									<span class="icon32 icon-inbox floatl"></span>数据模板编辑
								</legend>
								<div class="control-group">
									<label class="control-label" for="cloumn_name">模板名称</label>
									<div class="controls">
										<form:hidden path="id" />
										<form:input path="modelName" name="modelName" class="typeahead" id="cl_name" />
										<label class="error"><form:errors path="modelName" cssClass="error" /> </label>
									</div>
								</div>
								<div class="control-group backhide">
									<label class="control-label" for="cloumn_code">编号</label>
									<div class="controls">
										<form:input path="modelCode" name="modelCode" class="typeahead" id="cl_code" />
										<label class="error"><form:errors path="modelCode" cssClass="error" /> </label>
									</div>
								</div>
								<div class="control-group" >
									<label class="control-label" for="cloumn_type">类型</label>
									<div class="controls">
										<form:select path="modelType" class="autogrow" name="modelType" id="cl_type">
											<form:options items="${typeOptions}" itemLabel="title" />
										</form:select>
										<label class="error"><form:errors path="modelType" cssClass="error" /> </label>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="cloumn_fields">字段组合</label>
									<div class="controls">
										<div class="feilds_form_box">
											<form:select id="modelFieldSelect" path="" multiple="true" class="typeahead span2 multiSelect">
												<form:options items="${allFields}" itemValue="id" itemLabel="fullName" />
											</form:select>
											<form:hidden id="modelFields" path="modelFieldIds" name="modelFieldIds" />
										</div>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="cloumn_memo">备注</label>
									<div class="controls">
										<form:textarea path="memo" name="memo" class="typeahead" id="cl_memo" />
										<label class="error"><form:errors path="memo" cssClass="error" /> </label>
									</div>
								</div>
								<div class="form-actions">
									<button id="cl_btn" type="submit" class="btn btn-primary">保存</button>
									<form:button name="cancel" value="取消" class="btn backBtn">取消</form:button>
								</div>
							</fieldset>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	<script src="${appPath}/admin/js/min/business/dataing/model.min.js"></script>
	</body>
</html>