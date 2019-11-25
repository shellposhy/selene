<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<body>
<div id="content">
	<div class="mt10">
		<ul class="breadcrumb ind_f_tree" value="1">
			<li><a href="${appPath}/admin" target="_self">${appName}</a> <span class="divider">/</span></li>
			<li><a href="#">首页</a></li>
		</ul>
	</div>
	<!--  first -->
	<div class="sortable row-fluid ui-sortable">
		<a data-rel="tooltip" class="well span4 top-block padtb20" href="${appPath}/admin/merchants" target="_self"  data-original-title="商户管理">
			<span class="icon32 icon-red icon-user"></span>
			<div>商户管理</div>
			<div class="fontlt">查看</div>
			<span class="notification red">查看</span> 
		</a>
		<a data-rel="tooltip" class="well span4 top-block padtb20" href="${appPath}/admin/templating/page" target="_self" data-original-title="页面发布管理">
			<span class="icon32 icon-orange icon-book"></span>
			<div>页面管理</div>
			<div class="fontlt">查看</div> 
			<span class="notification yellow">查看</span>
		</a>
		<a data-rel="tooltip" class="well span4 top-block padtb20" href="${appPath}/admin/templating/model" target="_self" data-original-title="页面模板管理">
			<span class="icon32 icon-green icon-book-empty"></span>
			<div>模板管理</div>
			<div class="fontlt">查看</div>
			 <span class="notification green">查看</span>
		</a>
	</div>
	<!-- second -->
	<div class="sortable row-fluid ui-sortable mt20">
		<a data-rel="tooltip" class="well span4 top-block padtb20" href="${appPath}/admin/dataing/model" target="_self" data-original-title="数据模板管理">
			<span class="icon32 icon-red icon-document"></span>
			<div>数据模板管理</div>
			<div class="fontlt">查看</div>
			<span class="notification red">查看</span> 
		</a>
		<a data-rel="tooltip" class="well span4 top-block padtb20" href="${appPath}/admin/dataing/library" target="_self" data-original-title="数据管理">
			<span class="icon32 icon-orange icon-note"></span>
			<div>数据管理</div>
			<div class="fontlt">查看</div>
			<span class="notification yellow">查看</span> 
		</a>
		<a data-rel="tooltip" class="well span4 top-block padtb20" href="${appPath}/admin/dataing/tag" target="_self" data-original-title="数据标签">
			<span class="icon32 icon-green icon-bookmark"></span>
			<div>数据标签</div>
			<div class="fontlt">查看</div> 
			<span class="notification green">查看</span>
		</a>
	</div>
	<!-- three -->
	<div class="sortable row-fluid ui-sortable mt20">
		<a data-rel="tooltip" class="well span4 top-block padtb20" href="${appPath}/admin/searching/quick" target="_self" data-original-title="快捷查询">
			<span class="icon32 icon-red icon-search"></span>
			<div>快捷查询</div>
			<div class="fontlt">查看</div>
			<span class="notification red">查看</span> 
		</a>
		<a data-rel="tooltip" class="well span4 top-block padtb20" href="${appPath}/admin/searching/advance" target="_self" data-original-title="组合查询">
			<span class="icon32 icon-orange icon-search"></span>
			<div>组合查询</div>
			<div class="fontlt">查看</div>
			<span class="notification yellow">查看</span> 
		</a>
		<a data-rel="tooltip" class="well span4 top-block padtb20" href="${appPath}/admin/merchants/org" target="_self" data-original-title="机构管理">
			<span class="icon32 icon-green icon-home"></span>
			<div>机构管理</div>
			<div class="fontlt">查看</div> 
			<span class="notification green">查看</span>
		</a>		
	</div>
	<!-- four -->
	<div class="sortable row-fluid ui-sortable mt20">
		<a data-rel="tooltip" class="well span4 top-block padtb20" href="#" target="_self"  data-original-title="系统任务管理">
			<span class="icon32 icon-red icon-add"></span>
			<div>系统任务</div>
			<div class="fontlt">查看</div>
			<span class="notification red">查看</span> 
		</a>
		<a data-rel="tooltip" class="well span4 top-block padtb20" href="/admin/merchants/role" target="_self" data-original-title="角色权限管理">
			<span class="icon32 icon-orange icon-users"></span>
			<div>角色权限管理</div>
			<div class="fontlt">查看组</div> 
			<span class="notification yellow">查看</span>
		</a>
		<a data-rel="tooltip" class="well span4 top-block padtb20" href="#" target="_self" data-original-title="日志报告">
			<span class="icon32 icon-green icon-star-on"></span>
			<div>日志报告</div>
			<div class="fontlt">查看</div>
			<span class="notification green">查看</span>
		</a>
	</div>
	<!-- / 首页九宫格导航 -->
	<div class="index_tip_box">
		<div class="box ">
			<p class="h200 p20">
				更多帮助，请参见<a href="#">帮助文档</a>
			</p>
		</div>
	</div>
</div>
</body>
</html>
