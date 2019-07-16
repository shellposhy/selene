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
				<div id="myTabContent" class="tab-content">
					<div class="tab-pane clearfix fade in active" id="ulist_tab">
						<!-- org tree -->
						<div class="left_tree">
							<div><ul id="merchantsOrg" class="ztree green_tree_line leveTree"></ul></div>
						</div>
						<div class="right_con" id="merchantsList">
							<table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered bootstrap-datatable trHoverEdit" id="user">
								<thead>
									<tr>
										<th><label class="checkbox inline">账户</label></th>
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
				</div>
			</div>
		</div>
	    <script src="${appPath}/admin/jscript/merchants/merchants.js"></script>
	</body>
</html>