<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<body>
	<div id="content" class="span12">
		<div class="mt10">
			<ul class="breadcrumb ind_f_tree" value="43">
				<li><a href="${appPath}/admin" target="_self">${appName}</a> <span class="divider">/</span></li>
				<li><a href="#">数据标签</a></li>
			</ul>
		</div>
		<div class="box-content">
			<ul class="nav nav-tabs clearfix">
				<li class="active"><a href="#" target="_self">数据标签分类</a></li>
			</ul>
			<div id="globdb">
				<input type="hidden" name="baseId" id="baseId" value="0"></input>
				<div class="action_buttons">
					<a class="btn btn-small" href="${appPath}/admin/dataing/tag/new" id="add_g_db_btn" target="_self"><i class="icon-plus"></i>添加分类</a>
					<!-- <div class="input-append floatr">
						<input id="searchWords" size="16" type="text" />
						<button id="searchWordsButton" class="btn" type="button">搜索</button>
					</div> -->
				</div>
				<div class="content_wrap clearfix">
					<div class="span3">
						<ul id="dataTagsTree" class="ztree green_tree_line"></ul>
					</div>
					<div class="span9"></div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" 	src="${appPath}/admin/js/min/business/dataing/tag.min.js"></script>
</body>
</html>