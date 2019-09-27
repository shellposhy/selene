<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<body>
	<div id="content">
		<div class="mt10">
			<ul class="breadcrumb ind_f_tree" value="74">
				<li><a href="${appPath }/admin">${appName}</a> <span class="divider">/</span></li>
				<li><a href="#">角色管理</a></li>			
			</ul>
		</div>
		<div class="padlrn span6 action_buttons">
			<a class="btn btn-small" href="${appPath}/admin/merchants/role/new" target="_self">
				<i class="icon-plus"></i> 添加</a> 
				<a class="btn btn-small delete_list" href="#"><i class="icon-trash"></i>删除</a>
		</div>
		<table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered bootstrap-datatable trHoverEdit" id="user_group">
			<thead>
				<tr>
					<th><label class="checkbox inline"><input type="checkbox" class="selAll" />角色名称</label></th>
					<th>全部后台权限</th>
					<th>全部数据权限</th>
					<th>全部前台权限</th>
					<th>权限描述</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
			<tfoot>
				<tr>
					<th>角色名称</th>
					<th>全部后台权限</th>
					<th>全部数据权限</th>
					<th>全部前台权限</th>
					<th>权限描述</th>
				</tr>
			</tfoot>
		</table>
	</div>
	<script src="${appPath}/admin/js/min/business/merchants/role.min.js"></script>
	</body>
</html>