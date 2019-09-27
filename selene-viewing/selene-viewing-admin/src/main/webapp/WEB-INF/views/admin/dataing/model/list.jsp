<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
	<body>
		<div id="content" class="">
			<div class="mt10">
				<ul class="breadcrumb ind_f_tree" value="61">
					<li><a href="${appPath}/admin" target="_self">${appName}</a> <span class="divider">/</span></li>
					<li><a href="#">数据模型</a></li>
				</ul>
			</div>
			<div class="padlrn span6 action_buttons">
				<a class="btn" href="${appPath}/admin/dataing/model/new" target="_self"><i class="icon-plus"></i> 添加</a>
				<a class="btn delete_list" href="#"><i class="icon-trash"></i> 删除</a>
			</div>
			<table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered bootstrap-datatable trHoverEdit trHoverModi" id="dataTableModel">
				<thead>
					<tr>
						<th><label class="checkbox inline"><input type="checkbox" class="selAll" />名称</label></th>
						<th>类型</th>
						<th>编码</th>
						<th>字段详情</th>
						<th>备注</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
				<tfoot>
					<tr>
						<th>名称</th>
						<th>类型</th>
						<th>编码</th>
						<th>字段详情</th>
						<th>备注</th>
					</tr>
				</tfoot>
			</table>
		</div>
		<script src="${appPath}/admin/js/min/business/dataing/model.min.js"></script>
	</body>
</html>