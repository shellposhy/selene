<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>${appName}</title>
		<!-- ztree -->
		<link rel="stylesheet" href="${appPath}/admin/css/zTree/zTree.css" type="text/css" />
		<!-- bootstrap -->
		<link id="bs-css" href="${appPath}/admin/css/bootstrap-cerulean.css" rel="stylesheet" />
		<link href="${appPath}/admin/css/bootstrap-responsive.min.css" rel="stylesheet" />
		<!-- jquery -->
		<link href="${appPath}/admin/css/jquery.ui.custom.min.css" rel="stylesheet" />
		<link href='${appPath}/admin/css/jquery.multiselect.css' rel='stylesheet' />
		<link href='${appPath}/admin/css/jquery.cleditor.css' rel='stylesheet' />
		<link href='${appPath}/admin/css/jquery.noty.css' rel='stylesheet' />
		<link href='${appPath}/admin/css/jquery.noty.theme.css' rel='stylesheet' />
		<link href='${appPath}/admin/css/jquery.iphone.toggle.css' rel='stylesheet' />
		<link href='${appPath}/admin/css/treetable/jquery.treetable.css' rel='stylesheet' />
		<link href='${appPath}/admin/css/treetable/jquery.treetable.theme.css' rel='stylesheet' />
		<!-- utilities -->
		<link href="${appPath}/admin/css/charisma-app.css" rel="stylesheet" />
		<link href='${appPath}/admin/css/fullcalendar.css' rel='stylesheet' />
		<link href='${appPath}/admin/css/fullcalendar.print.css' rel='stylesheet' media='print' />
		<link href='${appPath}/admin/css/chosen.css' rel='stylesheet' />
		<link href='${appPath}/admin/css/uniform.default.css' rel='stylesheet' />
		<link href='${appPath}/admin/css/colorbox.css' rel='stylesheet' />
		<link href='${appPath}/admin/css/elfinder.min.css' rel='stylesheet' />
		<link href='${appPath}/admin/css/elfinder.theme.css' rel='stylesheet' />
		<link href='${appPath}/admin/css/opa-icons.css' rel='stylesheet' />
		<link href='${appPath}/admin/css/uploadify.css' rel='stylesheet' />
		<!-- main -->
		<link href="${appPath}/admin/css/main.css" type="text/css"  rel="stylesheet"/>
		
		<!-- Top scripts -->
		<script type="text/javascript" src="${appPath}/admin/js/min/jquery/1.8.2.min.js"></script>
		<script type="text/javascript" src="${appPath}/admin/js/min/jquery/ui-1.9.0.custom.min.js"></script>
		<script type="text/javascript" src="${appPath}/admin/js/min/jquery/table/dataTables.min.js"></script>
		<script type="text/javascript" src="${appPath}/admin/js/min/jquery/ztree/core-3.4.min.js"></script>
		<script type="text/javascript" src="${appPath}/admin/js/min/jquery/ztree/excheck-3.4.min.js"></script>
		<script type="text/javascript" src="${appPath}/admin/js/min/jquery/ztree/exedit-3.4.min.js"></script>
		<script type="text/javascript" src="${appPath}/admin/js/min/jquery/form/3.2.min.js"></script>
		<script type="text/javascript" src="${appPath}/admin/js/min/jquery/colorbox.min.js"></script>
		<script type="text/javascript" src="${appPath}/admin/js/min/jquery/form/1.10.0.validate.min.js"></script>
		<script type="text/javascript" src="${appPath}/admin/js/min/jquery/cookie.min.js"></script>
		<!-- Top utilities -->
		<script type="text/javascript" src="${appPath}/admin/js/min/util/cmstree.min.js"></script>
		<script type="text/javascript" src="${appPath}/admin/js/min/util/commen.min.js"></script>
		<script type="text/javascript" src="${appPath}/admin/js/min/util/charisma.min.js"></script>
		<!-- Top plugins -->
		<script type="text/javascript" src="${appPath}/admin/js/min/plugin/date.min.js"></script>
		<script type="text/javascript" src="${appPath}/admin/js/min/plugin/excanvas.min.js"></script>
		
		<!-- Global parameters -->
		<script type="text/javascript">
			var appPath = "${appPath}";
			var token="${login_session_user.token}";
			var refreshToken="${login_session_user.refreshToken}";
		</script>
		<!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
		<!--[if lt IE 9]>
			  <script type="text/javascript" src="${appPath}/admin/js/min/util/html5shiv.min.js"></script>
			  <script type="text/javascript" src="${appPath}/admin/js/min/util/respond.min.js"></script>
		<![endif]-->
		<sitemesh:head />
	</head>
	<body>
		<!--content-->
		<div class="content_wrap container-fluid">
			<div class="row-fluid">
				<%@include file="left.jsp"%>
				<div class="content_main">
					<sitemesh:body />
				</div>
			</div>
		</div>
		<%@include file="footer.jsp"%>
		<!--/content-->

		<!-- bootstrap -->
		<script type="text/javascript" src="${appPath}/admin/js/min/bootstrap/transition.min.js"></script>
		<script type="text/javascript" src="${appPath}/admin/js/min/bootstrap/alert.min.js"></script>
		<script type="text/javascript" src="${appPath}/admin/js/min/bootstrap/modal.min.js"></script>
		<script type="text/javascript" src="${appPath}/admin/js/min/bootstrap/dropdown.min.js"></script>
		<script type="text/javascript" src="${appPath}/admin/js/min/bootstrap/scrollspy.min.js"></script>
		<script type="text/javascript" src="${appPath}/admin/js/min/bootstrap/tab.min.js"></script>
		<script type="text/javascript" src="${appPath}/admin/js/min/bootstrap/tooltip.min.js"></script>
		<script type="text/javascript" src="${appPath}/admin/js/min/bootstrap/popover.min.js"></script>
		<script type="text/javascript" src="${appPath}/admin/js/min/bootstrap/button.min.js"></script>
		<script type="text/javascript" src="${appPath}/admin/js/min/bootstrap/collapse.min.js"></script>
		<script type="text/javascript" src="${appPath}/admin/js/min/bootstrap/carousel.min.js"></script>
		<script type="text/javascript" src="${appPath}/admin/js/min/bootstrap/typeahead.min.js"></script>
		<script type="text/javascript" src="${appPath}/admin/js/min/bootstrap/tour.min.js"></script>
		<!-- JSON -->
		<script type="text/javascript" src="${appPath}/admin/js/min/plugin/json2.min.js"></script>
		<!-- calendar -->
		<script type="text/javascript" src='${appPath}/admin/js/min/jquery/timepicker/fullcalendar.min.js'></script>
		<script type="text/javascript" src="${appPath}/admin/js/min/jquery/timepicker/0.9.8.min.js"></script>
		<script type="text/javascript" src="${appPath}/admin/js/min/jquery/timepicker/zh-CN.min.js"></script>
		<!-- chart -->
		<!--<script src="${appPath}/admin/js/min/jquery/flot/flot.min.js"></script>-->
		<!--<script src="${appPath}/admin/js/min/jquery/flot/flot.pie.min.js"></script>-->
		<!--<script src="${appPath}/admin/js/min/jquery/flot/flot.resize.min.js"></script>-->
		<!--<script src="${appPath}/admin/js/min/jquery/flot/flot.stack.min.js"></script>-->
		<!-- select -->
		<script type="text/javascript" src="${appPath}/admin/js/min/jquery/select/chosen.min.js"></script>
		<script type="text/javascript" src="${appPath}/admin/js/min/jquery/select/uniform.min.js"></script>
		<script type="text/javascript" src="${appPath}/admin/js/min/jquery/select/multiselect.min.js"></script>
		<!-- noty -->
		<script type="text/javascript" src="${appPath}/admin/js/min/jquery/noty/1.2.1.min.js"></script>
		<!-- utilities -->
		<script type="text/javascript" src="${appPath}/admin/js/min/jquery/iphone.toggle.min.js"></script>
		<script type="text/javascript" src="${appPath}/admin/js/min/jquery/autogrow.textarea.min.js"></script>
		<!-- <script src="${appPath}/admin/js/min/jquery/uploadify/3.1.min.js"></script> -->
		<script type="text/javascript" src="${appPath}/admin/js/min/jquery/metadata.min.js"></script>
		<script type="text/javascript" src="${appPath}/admin/js/min/jquery/contextmenu.min.js"></script>
		<!--<script type="text/javascript" src="${appPath}/admin/js/min/jquery/treetable.min.js"></script>-->
	</body>
</html>