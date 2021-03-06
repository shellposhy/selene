/**
 * The script for merchants user and merchants role.
 * 
 * @author Shaobo Shih
 * @version 1.0
 */
$(document).ready(function() {
	//merchants list and user
	loadMerchantsOrgTree();
	//merchants user
	loadRadioMerchantsUserTree();
	bindUserStatus();
	loadUserRoleOptions();
	checkMerchantsUserForm();
});

/**
 *  merchants list process
 * */
// load merchants organization tree
function loadMerchantsOrgTree() {
	if ($("#merchantsList").length > 0) {
		$("#merchantsList").hide();
		$.ajax({
			type : "POST",
			url : appPath+ "/admin/merchants/org/s",
			beforeSend: function(request) {//beforeSend
                request.setRequestHeader("token", token);
                request.setRequestHeader("refreshToken",refreshToken);
             },
			contentType : 'application/json',
			success : function(data) {
				if (data.data.children != null) {
					menuTreeCom($("#merchantsOrg"), data.data, true, bindTreeClickEvent,null,null, null, null, null);
					$('#add_g_db_btn').attr('disabled',"true");
					$('#add_g_db_btn').removeAttr('href');
				} else {
					$("#merchantsOrg").html("<h3 class='alert alert-info' >暂无机构</h3>");
				}
			},
			error : function(msg) {
				$("#merchantsOrg").html("<h3 class='alert alert-info' >暂无机构</h3>");
			}
		});
	}
}

//bind merchants organization tree click event,and show this organization merchants user
var merchantsDataTable;
function bindTreeClickEvent(id) {
	if ($("#merchantsList .dataTable").length > 0) {
		merchantsDataTable.fnDestroy();//Destroy the history merchants user data
		$(".selAll").parents("th").html('<label class="checkbox inline">用户名</label>')
	}
	loadMerchantsByOrgId(id);
	$("#merchantsList").show().find(".dataTable").attr("style", "width:100%");
	var content="<a class='btn btn-small add_user' href='"+ appPath+ "/admin/merchants/user/"+ id+ "/new' target='_self'><i class='icon-plus'></i> 添加</a>";
			content+="<a	class='btn btn-small delete_list' href='#'>"+ "<i	class='icon-trash'></i> 删除</a>";
	$("#merchantsEdit").html(content);
}

//bind merchants organization data table
function loadMerchantsByOrgId(orgId) {
	merchantsDataTable = $('#user').dataTable({
		"sDom" : "<'row-fluid'<'span6 alignr'f>r>t<'row-fluid'<'span12'i><'span12 center'p>>",
		"sPaginationType" : "bootstrap",
		"bProcessing" : true,
		"bServerSide" : true,
		"bSort" : false,
		"sAjaxSource" : appPath+"/admin/merchants/user/" + orgId + "/s",
		"fnServerData" : retrieveData,
		"fnServerParams" : function(aoData) {aoData.push({"name" : "iType","value" : 0});},
		"iDisplayLength" : 20,
		"oLanguage" : {"sUrl" : appPath + "/admin/js/min/util/zh_cn.min.js"},
		"aoColumns" : [{
					"mData" : "name",
					"fnRender" : function(obj) {
						if (obj.aData.name == "sa") {
							return '<label class="checkbox inline">'+
												'<input type="checkbox" id="inlineCheckbox'+ obj.aData.id+ '" name="idStr' + obj.aData.id+ '" value="'+ obj.aData.id+ '" style="opacity: 0;" >'+
												obj.aData.name+ 
											'</label>';
							}
						return '<label class="checkbox inline">'+
											'<input type="checkbox" id="inlineCheckbox'+ obj.aData.id+ '" name="idStr'+ obj.aData.id+ '" value="'+ obj.aData.id+ '" style="opacity: 0;" >'+ obj.aData.name+
										'</label>'+
										'<button title="点击进行配置" data-rel="tooltip" class="btn btn-mini padmbt floatr editbtn none"><i class="icon-edit"></i></button>';
					}
				}, {
					"mData" : "realName"
				},{
					"mData" : "sex"
				}, {
					"mData" : "phoneNumber"
				}, {
					"mData" : "email"
				} ],
		"fnDrawCallback" : callback_user_list
	});
};

function callback_user_list() {
	docReady();
	trHoverEdit();
	trHoverBtn("wrenchbtn", "icon-wrench", "repair");
	listDelete("user/delete", merchantsDataTable);
}

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
				temOptions += "<option value='" + groupListJsonO[i].id + "'>" + groupListJsonO[i].name + "</option>";
			}
			$("#user_new_form #treeSelId_box").html(temOptions);
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