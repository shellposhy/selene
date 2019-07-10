<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
	<body>
		<div id="content">
			<!-- breadcrumb -->
			<div class="breadcrumb_warp">
				<ul class="breadcrumb ind_f_tree" value="71">
					<li><a href="${appPath }/admin" target="_self">${appName}</a> <span class="divider">/</span></li>
					<li><a href="#">所有用户</a>
					</li>
				</ul>
			</div>
			<!-- content -->
			<div class="box-content">
				<!-- tab -->
				<ul id="tab" class="nav nav-tabs nav_tabs_for_datatables">
					<li class="active"><a href="#ulist_tab" data-toggle="tab">所有用户</a></li>
					<li><a href="#inputUsers_tab" data-toggle="tab">批量导入</a></li>
				</ul>
				<div id="myTabContent" class="tab-content">
					<div class="tab-pane clearfix fade in active" id="ulist_tab">
						<!-- org tree -->
						<div class="left_tree">
							<div>
								<a class="btn btn-small" href="${appPath}/admin/merchants/org/new" id="add_g_db_btn" target="_self"><i class="icon-plus"></i>添加机构</a>
								<input type="hidden" id="baseId" name="baseId" value="0">
							</div>
							<div>
								<ul id="merchantsOrg" class="ztree green_tree_line leveTree"></ul>
							</div>
						</div>
						<div class="right_con" id="merchantsList">
							<div class="padlrn span6 action_buttons" id="merchantsEdit">
								<a class="btn btn-small add_user" href="${appPath}/admin/user/new" target="_self"><i class="icon-plus"></i> 添加</a>
								<a class="btn btn-small delete_list" href="#"><i class="icon-trash"></i> 删除</a>
							</div>
							<table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered bootstrap-datatable trHoverEdit" id="user">
								<thead>
									<tr>
										<th><label class="checkbox inline"><input type="checkbox" class="selAll" />账户</label></th>
										<th>真实姓名</th>
										<th>性别</th>
										<th>手机号码</th>
										<th>电子邮箱</th>
									</tr>
								</thead>
								<tbody>
								</tbody>
								<tfoot>
									<tr>
										<th>账户</th>
										<th>真实姓名</th>
										<th>性别</th>
										<th>手机号码</th>
										<th>电子邮箱</th>
									</tr>
								</tfoot>
							</table>
						</div>
					</div>
					<div class="tab-pane clearfix fade" id="inputUsers_tab">
						<div class="pupdate_warp">
							<form id="inputUsers_form" action="${appPath}/admin/user/upload" method="post" enctype="multipart/form-data">
								<span class="pl20">选择文件:</span>
								<input class="input-file uniform_on" name="file" type="file" accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"/>
								<input class="btn" type="submit" value="上传" />
								<div class="error_div ml20"></div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	    <script src="${appPath}/admin/jscript/merchants/merchants.js"></script>
	</body>
</html>