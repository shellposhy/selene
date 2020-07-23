<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>${appName}-首次安装</title>
		<link href="${appPath}/admin/css/bootstrap-responsive.min.css" rel="stylesheet" />
		<link href="${appPath}/admin/css/charisma-app.css" rel="stylesheet" />
		<link href="${appPath}/admin/css/bootstrap-cerulean.css" rel="stylesheet" />
		<link rel="stylesheet" href="${appPath}/admin/css/main.css" type="text/css" />
		<script type="text/javascript" src="${appPath}/admin/js/min/jquery/1.8.2.min.js"></script>
		<script type="text/javascript" src="${appPath}/admin/js/min/jquery/form/1.10.0.validate.min.js"></script>
		<script type="text/javascript">var appPath = "${appPath}"</script>
	</head>
	<body>
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="row-fluid">
					<div class="span12 center top-block login-header" style="background: #fff">
						<h1 style="color: #c00" class="mt30">首次安装</h1>
					</div>
				</div>
				<div class="row-fluid">
					<div class="well span5 center login-box">
						<div class="">
							<form:form modelAttribute="merchants" id="install_form" action="${appPath}/admin/install/save" method="post" class="mt10">
								<fieldset>
									<div class="input-prepend" title="orgName" style="margin-bottom: 20px;">
										<span class="add-on floatl">机构名称</span>
										<form:input path="orgName"  class="input-large floatl" id="orgName" name="orgName" placeholder="机构名称" />
									</div>
									<div class="clearfix"></div>
									<div class="input-prepend" title="orgType" style="margin-bottom: 20px;">
										<span class="add-on floatl">机构类型</span>
										<form:select path="orgType" class="floatl" name="orgType" id="orgType">
											<form:option value="0">国家机关/党群组织/事业单位</form:option>
											<form:option value="1">教育/培训/科研/院校</form:option>
											<form:option value="2">媒体/出版/文化传播</form:option>
											<form:option value="3">银行/投资/基金/证券/保险</form:option>
											<form:option value="4" >国有企业/外资企业/合资企业/民营企业</form:option>
											<form:option value="5">房地产/建筑/建材/工程</form:option>
											<form:option value="6">其他</form:option>
										</form:select>
									</div>
									<div class="clearfix"></div>
									<div class="input-prepend" title="license" style="margin-bottom: 20px;">
										<span class="add-on floatl">许可证号</span>
										<form:input path="license" class="input-large floatl" name="license" id="license" placeholder="许可证号" />
									</div>
									<div class="clearfix"></div>
									<div class="input-prepend" title="account" style="margin-bottom: 20px;">
										<span class="add-on floatl">申请账户</span>
										<form:input path="account" class="input-large floatl" name="account" id="account"  placeholder="申请账户" />
									</div>
									<div class="clearfix"></div>
									<div class="input-prepend" title="realname" style="margin-bottom: 20px;">
										<span class="add-on floatl">真实姓名</span>
										<form:input path="realname" class="input-large floatl" name="realname" id="realname"  placeholder="真实姓名" />
									</div>
									<div class="clearfix"></div>
									<div class="input-prepend" title="sex" style="margin-bottom: 20px;">
										<span class="add-on floatl">真实性别</span>
										<form:select path="sex" class="floatl" name="sex" id="sex" >
											<form:option value="0">男</form:option>
											<form:option value="1">女</form:option>
										</form:select>
									</div>
									<div class="clearfix"></div>
									<div class="input-prepend" title="phone" style="margin-bottom: 20px;">
										<span class="add-on floatl">手机号码</span>
										<form:input path="phone" class="input-large floatl" name="phone" id="phone"  placeholder="手机号码" />
									</div>
									<div class="clearfix"></div>
									<div class="input-prepend" title="email" style="margin-bottom: 20px;">
										<span class="add-on floatl">电子邮箱</span>
										<form:input path="email" class="input-large floatl" name="email" id="email"  placeholder="电子邮箱" />
									</div>
									<div class="clearfix"></div>
									<div class="input-prepend" title="password" style="margin-bottom: 20px;">
										<span class="add-on floatl">账户密码</span>
										<form:password path="password" class="input-large floatl" name="password" id="password" placeholder="账户密码" />
									</div>
									<div class="clearfix"></div>
									<div class="input-prepend" title="repeatPassword" style="margin-bottom: 20px;">
										<span class="add-on floatl">重复密码</span>
										<input class="input-large floatl" name="repeatPassword" id="repeatPassword" type="password" placeholder="重复密码" />
									</div>
									<div class="clearfix"></div>
									<p class="center span3">
										<button type="submit" class="btn btn-success" style="margin-top: 0px">申请使用</button>
									</p>
								</fieldset>
							</form:form>
						</div>
					</div>
					<div class="row-fluid" style="position: fixed; bottom: 0px;">
						<div class="span12 center">
							<p>Copyright © 2020, All Rights Reserved</p>
						</div>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			$(function(){jQuery.validator.addMethod("namecheck",function(value,element){var reg=/^[A-Za-z0-9]+$/;return this.optional(element)||reg.test(value)},"账户只能由数字和26个英文字母组成");jQuery.validator.addMethod("phonecheck",function(value,element){var reg=/^1(3|4|5|7|8)\d{9}$/;return this.optional(element)||reg.test(value)},"手机号码格式错误！");$("#install_form").validate({rules:{orgName:{required:true},orgType:{required:true},license:{required:true,namecheck:true},account:{required:true,minlength:5,namecheck:true},realname:{required:true},sex:{required:true},phone:{required:true,phonecheck:true},email:{required:true,email:true},password:{required:true,minlength:8},repeatPassword:{required:true,equalTo:"#password"}},messages:{orgName:{required:"机构名称不能为空！"},orgType:{required:"机构类型不能为空！"},license:{required:"机构申请的许可证号不能为空！",namecheck:"许可证只能由数字和26个英文字母组成！"},account:{required:"机构管理员账户不能为空！",minlength:"账户长度不小于5位！",namecheck:"账户只能由数字和26个英文字母组成！"},realname:{required:"真实姓名不能为空！"},sex:{required:"性别不能为空！"},phone:{required:"手机号码不能为空！",phonecheck:"手机号码格式错误！"},email:{required:"电子邮箱不能为空！",email:"输入的电子邮箱格式不匹配！"},password:{required:"账户密码不能为空！",minlength:"密码长度不小于8位！",},repeatPassword:{required:"确认密码不能为空！",equalTo:"两次输入的密码不同！"}}})});
		</script>
	</body>
</html>