<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<body>
	<div id="content" class="">
		<div class="">
			<ul class="breadcrumb ind_f_tree" value="21">
				<li><a href="${appPath}/admin" target="_self">${appName}</a> <span class="divider">/</span></li>
				<li><a href="${appPath}/admin/templating/page" target="_self">页面管理</a><span class="divider">/</span></li>
				<li><a href="#">页面配置</a></li>
			</ul>
		</div>
		<div class="form-horizontal">
			<div class="control-group">
				<label class="control-label" for="pageConfigeName">页面名称</label>
				<div class="controls">
					<div class="input-append">
						<input type="text" size="16" id="pageConfigeName" name="pageConfigeName" value="${pageTitle }" readonly="readonly" />
						<a class="btn" href="${appPath}/admin/templating/page" target="_self">返回</a>
					</div>
				</div>
			</div>
		</div>
		<iframe id="iFramePageConf" name="iFramePageConf" width="100%" onload="iframeFix(this)" frameborder="0" scrolling="auto" 
			src="${appPath}/admin/templating/page/config/preview/${pageId}"></iframe>
	</div>
	
	<!-- Paging item -->
	<div class="modal hide fade form-horizontal areaConfModal" id="areaConfModal">
		<form:form modelAttribute="templatingContent" action="${appPath}/admin/templating/page/config/${pageId }/save" id="viewItemForm">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h3>配置</h3>
			</div>
			<div class="modal-body">
				<form:hidden path="id" id="id"/>
				<form:hidden path="itemCode" id="itemCode" name="itemCode" />
				<form:hidden path="pageId" name="pageId" value="${pageId }" />
				<table>
					<tr>
						<td><label class="control-label">配置区域标题：</label></td>
						<td><div class="relative_tree_box"><div class="inline"><form:input path="contentName" id="contentName" name="contentName" /></div></div></td>
					</tr>
					<tr>
						<td><label class="control-label">配置区域描述：</label></td>
						<td><div class="relative_tree_box"><div class="inline"><form:textarea path="contentSummary" id="contentSummary" name="contentSummary" /></div></div></td>
					</tr>
					<tr>
						<td><label class="control-label">区域数据来源：</label></td>
						<td>
							<div class="relative_tree_box" id="itemContentSrc">
								<div class="inline">
									<input class="treeRadio" type="text" value="" readonly="readonly" />
									<form:hidden path="baseId" id="baseId" name="baseId" class="treeSelId" />
								</div>
								<div class="menuContent">
									<ul id="treeSel_1" class="ztree treeNew"></ul>
									<a class="selOk btn btn-small" href="#"><i class="icon-ok"></i> 确定</a>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td><label class="control-label">过滤条件筛选：</label></td>
						<td>
								<form:hidden path="filterStatus" id="switch_value"/>
								<input type="checkbox" class="checkbox" id="filter_witch" />
						</td>
					</tr>
					<tr id="filter_dir" style="display: none">
						<td><label class="control-label">过滤条件分类：</label></td>
						<td>
							<div class="relative_tree_box">
								<div class="inline">
									<form:select path="filterDir" id="filterDir">
										<form:option value="Basic">基础搜索</form:option>
										<form:option value="Combine">组合搜索</form:option>
									</form:select>
								</div>
							</div>
						</td>
					</tr>
					<tr id="filter_type" style="display: none">
						<td><label class="control-label">过滤条件类型：</label></td>
						<td>
							<div class="relative_tree_box">
								<div class="inline">
									<form:select path="filterType" id="filterType">
									</form:select>
								</div>
							</div>
						</td>
					</tr>
					<tr id="filter_value" style="display: none">
						<td><label class="control-label">过滤条件值：</label></td>
						<td>
							<div class="relative_tree_box">
								<div class="inline">
									<form:input path="filterValue" id="filterValue" placeholder="如检索多词,空格分隔(例:中国 国家)"/>
								</div>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<div class="modal-footer">
				<a href="#" id="btn_save" class="btn btn-primary">保存</a>
				<a href="#" id="btn_cancel" class="btn" data-dismiss="modal">取消</a>
			</div>
		</form:form>
	</div>
	
	<script src="${appPath}/admin/js/original/business/templating/config.js"></script>
	<!-- <script src="${appPath}/admin/js/min/business/templating/config.min.js"></script>-->
</body>
</html>