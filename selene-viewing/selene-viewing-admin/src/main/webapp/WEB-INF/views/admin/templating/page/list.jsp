<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
	<body>
		<div id="content" class="span12 tree_separated">
			<div class="breadcrumb_warp">
				<ul class="breadcrumb ind_f_tree" value="21">
					<li><a href="${appPath}/admin" target="_self">${appName}</a> <span class="divider">/</span></li>
					<li><a href="#">页面管理</a></li>
				</ul>
			</div>
			<div class="box-content">
				<div class="padlrn span12 action_buttons">
					<a class="btn btn-small" href="${appPath}/admin/templating/page/0/new" id="addPage" target="_self">
						<i class="icon-plus"></i>添加布发页面
					</a>
				</div>
				<div class="content_wrap mt30 clearfix">
					<div class="left_tree">
						<ul id="pageTree" class="ztree green_tree_line"></ul>
					</div>
					<div class="right_con">
						<ul id="pages" class="show_sel_itme mt10 clearfix">
							<h3 class='alert alert-info'>请选择左侧目录以发布页面情况</h3>
						</ul>
						<div id="page_content" hidden="hidden">
							<div class="btn-toolbar floatl" id="addbtn">
								<div class="btn-group">
									<a class="btn btn-small" id="indexPublish"><i class="icon-plus"></i> 首页发布</a>
									<a class="btn btn-small" id="pageUp"><i class=" icon-chevron-up"></i>页面上架</a>
									<a class="btn btn-small" id="pageDown"><i class=" icon-chevron-down"></i>页面下架</a>
								</div>
							</div>
							<table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered bootstrap-datatable trHoverEdit " id="pageDatas">
								<thead>
									<th><label class="checkbox inline"><input type="checkbox" class="selAll" />页面名称</label></th>
									<th>页面编码</th><th>页面类型</th><th>上架状态</th><th>发布状态</th><th>发布时间</th><th>页面预览</th>
								</thead>
								<tbody></tbody>
								<tfoot>
									<tr>
										<th>页面名称</th><th>页面编码</th><th>页面类型</th><th>上架状态</th><th>发布状态</th><th>发布时间</th><th>页面预览</th>
									</tr>
								</tfoot>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- Notice -->
		<div class="modal hide fade form-horizontal" id="noticeModal">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h3>确定发布</h3>
			</div>
			<div class="modal-body">
				<p>您确定要发布这些页面吗？发布时间比较耗时，请耐心等待！</p>
			</div>
			<div class="modal-footer">
				<span class="loading floatl none"></span>
				<a href="#" class="btn btn-primary">发布</a>
				<a href="#" class="btn" data-dismiss="modal">取消</a>
			</div>
		</div>
		<script src="${appPath}/admin/js/original/business/templating/page.js"></script>
		<!-- <script src="${appPath}/admin/js/min/business/templating/page.min.js"></script>-->
	</body>
</html>