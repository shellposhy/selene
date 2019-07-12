/**
 * The script for merchants user and merchants role.
 * 
 * @author Shaobo Shih
 * @version 1.0
 */
$(document).ready(function() {
	loadRadioMerchantsUserTree();
	bindUserStatus();
	loadUserRoleOptions();
	checkMerchantsUserForm();
});

//load organization tree for merchants user
function loadRadioMerchantsUserTree() {
	if ($(".treeSelId").length > 0){
		$.ajax({
			type : "POST",
			url : appPath + "/admin/merchants/org/s",
			beforeSend: function(request) {//beforeSend
                request.setRequestHeader("token", token);
                request.setRequestHeader("refreshToken",refreshToken);
             },
			contentType : 'application/json',
			success : function(data) {
				if (data.data.children != null) {
					treeRadioCom($("#editUserOrg .treeNew"), data.data, false);
					setTimeout("$('.treeSelId').click()", 800);
				}
			}
		});
	}
}

//bind the event for new or edit user status
function bindUserStatus() {
	if ($("#user_new_form #name").val()) {
		if ($("#user_new_form #status").val() == "Normal") {
			$("#status_check").attr("checked", true);
		} else if ($("#user_new_form #status").val() == "Stop") {
			$("#status_check").attr("checked", false);
		}
	} else {
		$("#user_new_form #status").val("Normal");
	}
	$('.iphone_toggle_user').iphoneStyle({
		checkedLabel : '启用',
		uncheckedLabel : '禁用',
		onChange : function() {
			var userSval = $("#user_new_form #status").val();
			if (userSval == "Normal") {
				$("#user_new_form #status").val("Stop");
			} else if (userSval == "Stop") {
				$("#user_new_form #status").val("Normal");
			}
		}
	});
}

function user_init() { // 用户编辑页面部分
	if ($("#user_new_form").length > 0) {
		userStatus();
		addNewUser();
		intOrgUserEidt();
	}
}


//Load multiple selected user roles
function loadUserRoleOptions() {
	if ($("#user_new_form #name").val()) {
		if ($("#treeSelId").val()) {
			var trSIdArry = ($("#treeSelId").val()).split(",");
		}
		if ($("#groupListJson").text()) {
			var groupListJson = $("#groupListJson").text();
			var groupListJsonO = new Function("return" + groupListJson)();
			var tmpOptions = "";
			for (var i = 0; i < groupListJsonO.length; i++) {
				if ($("#treeSelId").val()) {
					var tmpOptionsTrue = "";
					for (var j = 0; j < trSIdArry.length; j++) {
						if (trSIdArry[j] == groupListJsonO[i].id) {
							tmpOptionsTrue = "<option  selected='true' value='"+ groupListJsonO[i].id + "'>"+ groupListJsonO[i].name + "</option>";
							break;
						}
					}
					if (tmpOptionsTrue != "") {
						tmpOptions += tmpOptionsTrue;
					} else {
						tmpOptions += "<option  value='" + groupListJsonO[i].id+ "'>" + groupListJsonO[i].name + "</option>";
					}
				} else {
					tmpOptions += "<option value='" + groupListJsonO[i].id + "'>" + groupListJsonO[i].name + "</option>";
				}
			}
			$("#user_new_form #treeSelId_box").append(tmpOptions);
		}
	} else {
		if ($("#groupListJson").text()) {
			var groupListJson = $("#groupListJson").text();
			var groupListJsonO = new Function("return" + groupListJson)();
			var temOptions = "";
			for (var i = 0; i < groupListJsonO.length; i++) {
				tmpOptions += "<option value='" + groupListJsonO[i].id + "'>" + groupListJsonO[i].name + "</option>";
			}
			$("#user_new_form #treeSelId_box").html(tmpOptions);
		}
	}
	var treeSelIds = $("#user_new_form #treeSelId_box").val();
	$("#treeSelId").val(treeSelIds);
	$("#user_new_form #treeSelId_box").change(function() {
		var treeSelIdsn = $("#user_new_form #treeSelId_box").val();
		$("#treeSelId").val(treeSelIdsn);
	});
}

//check merchants user form
function checkMerchantsUserForm() {
	//Custom validation for name
	jQuery.validator.addMethod("namecheck",
			function(value, element) {
				var reg = /^[A-Za-z0-9]+$/;
				return this.optional(element) || reg.test(value);
			}, "账户只能由数字和26个英文字母组成");
	//Form validate
	$("#user_new_form").validate({
		onkeyup : false,
		rules : {
			name : {
				required : true,
				namecheck:true
			},
			userPassword : {
				required : function() {
					if ($("#user_new_form #psword").hasClass("active")) {
						return true;
					} else {
						return false;
					}
				},
				minlength : 6
			},
			repassword : {
				required : function() {
					if ($("#user_new_form #psword").hasClass("active")) {
						return true;
					} else {
						return false;
					}
				},
				minlength : 6,
				equalTo : '#userPassword'
			},
			phoneNumber : {
				required : function() {
					if ($("#user_new_form #phoneNumber").hasClass("active")) {
						return true;
					} else {
						return false;
					}
				},
				minlength : 11
			}
		},
		messages : {
			name : {
				required : "请输入账户",
				namecheck:"账户只能由数字和26个英文字母组成"
			},
			userPassword : {
				required : "请输入密码",
				minlength : "请最少输入6位密码"
			},
			repassword : {
				required : "请再次输入密码",
				minlength : "请最少输入6位密码",
				equalTo : "请保证两次输入密码一致"
			},
			phoneNumber:{
				required : "请输入手机号码",
				minlength : "手机号码最短为11位"
			}
		},
		errorPlacement : function(error, element) {
			error.insertAfter(element);
		},
		submitHandler : function() {
			form.submit();
		}
	});
}