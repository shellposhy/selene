<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="java.util.*"%>
<html>
	<body>
		<div id="content" class="span12">
			<div class="mt10">
				<ul class="breadcrumb ind_f_tree" value="52">
					<li><a href="${appPath}/admin" target="_self">${appName}</a> <span class="divider">/</span></li>
					<li><a href="#">高级查询</a></li>
				</ul>
			</div>
			<div id="TabContent" class="tab-content visible">
				<div class="tab-pane fade in active " id="multiExp">
					<form class="form-horizontal" id="advanceSearchForm" action="${appPath}/admin/searching/presearch" method="post">
						<div class="span12">
							<div class="ml35 alert alert-info"><strong>查询范围</strong></div>
						</div>
						<div class="control-group">
							<div class="control-group tree_sel_box">
								<div class="ml35">
									<input id="treeSelShow" class="treeSel" type="text" readonly value="选择数据库范围" />
									<input id="searchConIdStr" type="hidden" class="treeSelId" name="searchIdStr" value="" />
								</div>
								<div class="menuContent">
									<ul id="treeSel_1" class="ztree treeNew"></ul>
									<a class="btn sel_all ml10">全选</a> <a class="btn sel_none">全不选</a>
									<a class="selOk btn btn-small" href="#"><i class="icon-ok"></i>确定</a>
								</div>
							</div>
						</div>
						<div class="control-group">
							<div class="ml35"><div class="alert alert-info"><strong>查询条件</strong></div></div>
						</div>
						<div id="dataFields" class="ml35"></div>
						<div class="control-group ml35">
							<a id="addShowDatafields" class="btn btn-small" href="#"><i class="icon-plus"></i> 添加字段</a>
						</div>
						<div class="showDataFields clearfix ml35 none">
							<div class="control-group">
								<span>选择字段：</span>
								<select id="dbFields" class="input-xlarge" name="dbFields"></select>
								<a id="addDataField" href="#" class="btn">确定</a>
							</div>
						</div>
						<div class="span12 ml0">
							<div class="form-actions">
								<input id="advanceBtn" type="button" class="btn btn-primary" value="搜索" />
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<script src="${appPath}/admin/js/min/business/searching/search.min.js"></script>
	</body>
</html>