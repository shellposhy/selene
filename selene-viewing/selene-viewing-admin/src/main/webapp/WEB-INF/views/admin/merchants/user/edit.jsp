<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<html>
	<body>
		<div id="content" class="span12 ml0">
			<div class="mt10">
				<ul class="breadcrumb ind_f_tree" value="53">
					<li><a href="${appPath }/admin" target="_self">${appName}</a> <span class="divider">/</span></li>
					<li><a href="${appPath }/admin/user" target="_self">用户管理</a><span class="divider">/</span></li>
					<li><a href="#">用户编辑</a></li>
				</ul>
			</div>
			<form:form modelAttribute="user" class="form-horizontal u_form" id="user_new_form" action="${appPath}/admin/merchants/user/save" method="post" target="_self">
				<fieldset>
					<legend>
						<span class="icon32 icon-user floatl"></span>用户编辑
					</legend>
					<div class="clearfix ml35">
						<div class="control-group">
							<label class="control-label" for="name">用户名 </label>
							<div class="controls">
								<form:input path="name" name="name" class="typeahead" id="name" />
								<form:hidden path="id" />
								<label class="error springerror"><form:errors path="name" cssClass="error" /> </label>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="userType">用户类型 </label>
							<div class="controls" id="userType">
								<label class="radio pt0"><form:radiobutton path="userType" value="0" checked="checked"/>密码 </label>
							</div>
						</div>
					</div>
					<div class="user_input_div active " id="psword">
						<div class="clearfix ml35">
							<div class="control-group">
								<label class="control-label" for="password">密码 </label>
								<div class="controls">
									<form:password path="userPassword" name="userPassword" class="typeahead" id="userPassword" />
									<form:hidden path="oldPassword" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="repassword">确认密码 </label>
								<div class="controls">
									<input type="password" name="repassword" class="typeahead" id="repassword" />
								</div>
							</div>
						</div>
					</div>
					<div class="span10">
						<div class="control-group" id="editUserOrg">
							<label class="control-label" for="orgID">所属机构</label>
							<div class="controls">
								<input class="treeRadio" type="text" value="" readonly="readonly" />
								<form:hidden path="orgId" id="orgId" name="orgId" class="treeSelId" />
							</div>
							<div class="menuContent">
								<ul id="treeSel_1" class="ztree treeNew"></ul>						
								<a class="selOk btn btn-small" href="#"><i class="icon-ok"></i>确定</a>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="treeSelId_box">所属角色 </label>
							<div class="controls">
								<select multiple="true" data-rel="chosen" data-placeholder="请选择角色" name="treeSelId_box" class="typeahead" id="treeSelId_box">
								</select>
								<form:hidden path="treeSelId" id="treeSelId" />
							</div>
							<div class="none" id="groupListJson">${groupListJson}</div>
						</div>
						<div class="control-group"  id="status_div">
							<label class="control-label" for="status">当前状态 </label>
							<div class="controls">
								<input data-no-uniform="true" checked="true" type="checkbox" class="iphone_toggle_user" id="status_check">
								<form:hidden path="status" name="status" value="" id="status" />
							</div>
						</div>
					</div>
				</fieldset>
				<div class="span10">
				<fieldset>
					<legend>
						<h4>扩展信息</h4>
					</legend>
					<div class="control-group">
						<label class="control-label" for="nickName">昵称 </label>
						<div class="controls">
							<form:input path="nickName" name="nickName" class="typeahead" id="nickName" />
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="realName">真实姓名 </label>
						<div class="controls">
							<form:input path="realName" name="realName" class="typeahead" id="realName" />
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="sex">性别</label>
						<div class="controls" id="sex">
							<label class="radio m110"><form:radiobutton path="sex" value="Man" checked="checked"/>男</label>
							<label class="radio m110"><form:radiobutton path="sex" value="Woman"/>女</label>
						</div>
					</div>
						<div class="control-group">
							<label class="control-label" for="idCard">身份证号</label>
							<div class="controls">
								<form:input path="idCard" name="idCard" class="typeahead" id="idCard"/>
							</div>
						</div>
						<div class="control-group" id="position_div">
							<label class="control-label" for="position">职位</label>
							<div class="controls">
								<form:input path="position" name="position" class="typeahead" id="position"/>
							</div>
						</div>
						<div class="control-group" id="phone_div">
							<label class="control-label" for="phoneNumber">手机号码</label>
							<div class="controls">
								<form:input path="phoneNumber" name="phoneNumber" class="typeahead active" id="phoneNumber"/>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="email">邮箱</label>
							<div class="controls">
								<form:input path="email" name="email" class="typeahead" id="email"/>
							</div>
						</div>
					<div class="span12 ml0">
						<div class="form-actions">
							<form:button type="submit" class="btn btn-primary">保存</form:button>
						</div>
					</div>
				</fieldset>
				</div>
			</form:form>
		</div>
		<!-- <script src="${appPath}/admin/js/min/business/merchants/user.min.js"></script> -->
		<script src="${appPath}/admin/js/original/business/merchants/user.js"></script>
	</body>
</html>