<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<body>
<div id="content" class="span12 ml0">
	<div class="mt10">
		<ul class="breadcrumb ind_f_tree" value="71">
			<li><a href="${appPath }/admin" target="_self">${appName}</a><span class="divider">/</span></li>
			<li><a href="#">商户机构</a><span class="divider">/</span></li>
			<li><a href="#">编辑</a></li>
		</ul>
	</div>
	<form:form modelAttribute="org" class="form-horizontal u_form" id="org_new_form" action="${appPath}/admin/merchants/org/save" method="post" target="_self">
		<fieldset>
			<legend>
				<span class=" floatl ml20">商户机构编辑</span>
			</legend>
			<div class="clearfix ml35">
				<div class="control-group">
					<label class="control-label" for="name">商户机构名称 </label>
					<div class="controls">
						<form:input path="name" name="name" class="typeahead" id="name" />
						<form:hidden path="id" />
						<label class="error springerror"><form:errors path="name" cssClass="error" /> </label>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="code">商户机构编码 </label>
					<div class="controls">
						<form:input path="code" name="code" class="typeahead" id="code" />
						<label class="error springerror"><form:errors path="code" cssClass="error" /> </label>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="code">商户机构类型 </label>
					<div class="controls">
						<label class="radio pt0"><form:radiobutton path="orgType" value="Gov"/>国家机关/党群组织/事业单位</label>
						<label class="radio m110"><form:radiobutton path="orgType" value="Edu"/>教育/培训/科研/院校</label>
						<label class="radio m110"><form:radiobutton path="orgType" value="Media"/>媒体/出版/文化传播</label>
						<label class="radio m110"><form:radiobutton path="orgType" value="Fin"/>银行/投资/基金/证券/保险</label>
						<label class="radio m110"><form:radiobutton path="orgType" value="Com" checked="checked"/>国有企业/外资企业/合资企业/民营企业</label>
						<label class="radio m110"><form:radiobutton path="orgType" value="Pro"/>房地产/建筑/建材/工程</label>
						<label class="radio m110"><form:radiobutton path="orgType" value="Other"/>其他</label>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="treeSelId_box">商户所属角色 </label>
					<div class="controls">
						<select multiple="true" data-rel="chosen" data-placeholder="请选择角色" name="treeSelId_box" class="typeahead" id="treeSelId_box"></select>
						<form:hidden path="treeSelId" id="treeSelId" />
					</div>
					<div class="none" id="groupListJson">${groupListJson}</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="inherit">角色继承到子机构</label>
					<div class="controls">
						<form:checkbox path="inherit"/>
					</div>
				</div>
			</div>
			<div class="span10">
				<div class="control-group" id="merchantsOrgTree">
					<label class="control-label" for="parentID">所属商户机构</label>
					<div class="controls">
						<input class="treeRadio" type="text" value="" readonly="readonly" />
						<form:hidden path="parentId" id="parentId" name="parentId" class="treeSelId" />
					</div>
					<div class="menuContent">
						<ul id="treeSel_1" class="ztree treeNew"></ul>
						<a class="selOk btn btn-small" href="#"><i class="icon-ok"></i>确定</a>
					</div>
				</div>
			</div>
			<div class="span12 ml0">
				<div class="form-actions">
					<form:button type="submit" class="btn btn-primary">保存</form:button>
				</div>
			</div>
		</fieldset>
	</form:form>
</div>
<script src="${appPath}/admin/jscript/merchants/merchants.js"></script>
</body>
</html>