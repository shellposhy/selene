/**
 * The script for merchants  role.
 * 
 * @author Shaobo Shih
 * @version 1.0
 */
$(document).ready(function() {
	bindMerchantsRoleData();
	loadMerchantsActionTree();
	//role edit
	bindAdminProcess();
	userGroup_add();
});

//bind merchants role
var merchantsRoleDataTable;
function bindMerchantsRoleData() {
	merchantsRoleDataTable = $('#user_group').dataTable({
		"sDom" : "<'row-fluid'<'span6 alignr'f>r>t<'row-fluid'<'span12'i><'span12 center'p>>",
		"sPaginationType" : "bootstrap",
		"bProcessing" : true,
		"bServerSide" : true,
		"bSort" : false,
		"sAjaxSource" : appPath+"/admin/merchants/role/s",
		"fnServerData" : retrieveData,
		"iDisplayLength" : 20,
		"oLanguage" : {"sUrl" : appPath + "/admin/js/javascript/de_CN.js"},
		"aoColumns" : [{
					"mData" : "name",
					"fnRender" : function(obj) {
						return '<label class="checkbox inline">'
										+'<input type="checkbox" id="inlineCheckbox'+ obj.aData.id+ '" name="idStr'+ obj.aData.id+ '" value="'+ obj.aData.id+ '" style="opacity: 0;" >'
										+ obj.aData.name
									+ '</label>'
									+'<button title="点击进行配置" data-rel="tooltip" class="btn btn-mini editbtn padmbt floatr none"><i class="icon-edit"></i></button>';
					}
				},
				{
					"mData" : "allAdminAuthority",
					"fnRender" : function(obj){
						if(obj.aData.allDataAuthority){
							return "是";
						}else{
							return "否";
						}
					}
				},
				{
					"mData" : "allDataAuthority",
					"fnRender" : function(obj){
						if(obj.aData.allDataAuthority){
							return "是";
						}else{
							return "否";
						}
					}
				},
				{
					"mData" : "allFrontAuthority",
					"fnRender" : function(obj){
						if(obj.aData.allDataAuthority){
							return "是";
						}else{
							return "否";
						}
					}
				},{
					"mData" : "memo"
				} ],
		"fnDrawCallback" : callback_role
	});
}

function callback_role() {
	docReady();
	trHoverEdit();
	listDelete("role/delete", merchantsRoleDataTable);
}

/**
 *  merchants role edit process
 * */
//load merchants action tree
function loadMerchantsActionTree() {
	if ($(".treeSelId").length > 0){
		$.ajax({
			type : "POST",
			url : appPath + "/admin/merchants/role/action/s",
			beforeSend: function(request) {//beforeSend
                request.setRequestHeader("token", token);
                request.setRequestHeader("refreshToken",refreshToken);
             },
			contentType : 'application/json',
			success : function(data) {
				if (data.data.children != null) {
					treeSleCom($("#use_group_tree_sel .treeNew"), data.data);
					var delayRsel = setTimeout("$('#use_group_tree_sel .treeSelId').click()",800);
				}
			}
		});
	}
}

//bind administrator manage
function bindAdminProcess() {
	if ($("input[name='allAdminAuthority']").val() == "false") {
		$(".notAllAuthor").addClass("disabled").siblings(".allAuthor").removeClass("disabled").end().siblings(".treeSel,.menuBtn").show();
	} else {
		$(".allAuthor").addClass("disabled").siblings(".notAllAuthor").removeClass("disabled").end().siblings(".treeSel,.menuBtn").hide();
	}
	$(".allAuthor").click(function() {
		if (!$(this).hasClass("disabled")) {
			$(this).addClass("disabled").siblings(".notAllAuthor").removeClass("disabled");
			$(this).siblings(".treeSel,.menuBtn").hide();
			$("input[name='allAdminAuthority']").attr("value", true);
		}
		return false;
	});
	$(".notAllAuthor").click(function() {
		if (!$(this).hasClass("disabled")) {
			$(this).addClass("disabled").siblings(".allAuthor").removeClass("disabled");
			$(this).siblings(".treeSel,.menuBtn").show();
			$("input[name='allAdminAuthority']").attr("value", false);
		}
		return false;
	});
}



//新增角色
function userGroup_add(){
	if ($("#group_new_form").length > 0) {
		userGroup_validate();
		//userGroup_admin_authority();
		userGroup_data_authority();
		//usergroup_tree_menu();
		userGroup_page_deault();
	}
}

/* 提交用户组 表单验证 */
function userGroup_validate() {
	$("#group_new_form").validate({
		rules : {
			name : "required",
			code : "required"
		},
		messages : {
			name : "请填写用户组名称",
			code : "请填写用户组编号"
		},
		errorPlacement : function(error, element) {
			error.insertAfter(element);
		},
		submitHandler : function() {
			form.submit();
		},
		onkeyup : false
	});
}

//后台管理权限切换


//数据库权限切换
function userGroup_data_authority() {
	if ($("input[name='allDataAuthority']").val() == "false") {
		$(".notAllAuthorDb").addClass("disabled").siblings(".allAuthorDb").removeClass("disabled");
		$("#user_group_readOnly_tree").show();
	} else {
		$(".allAuthorDb").addClass("disabled").siblings(".notAllAuthorDb").removeClass("disabled");
		$("#user_group_readOnly_tree").hide();
	}
	$(".allAuthorDb").click(function() {
		if (!$(this).hasClass("disabled")) {
			$(this).addClass("disabled").siblings(".notAllAuthorDb").removeClass("disabled");
			$("#user_group_readOnly_tree").hide();
			$("input[name='allDataAuthority']").attr("value", true);
		}
		return false;
	});
	$(".notAllAuthorDb").click(function() {
		if (!$(this).hasClass("disabled")) {
			$(this).addClass("disabled").siblings(".allAuthorDb").removeClass("disabled");
			$("#user_group_readOnly_tree").show();
			$("input[name='allDataAuthority']").attr("value", false);
		}
		return false;
	});
}

//系统默认首页处理
function userGroup_page_deault() {
	var initTypeVal = $("#defaultPageType").val();
	if (initTypeVal == null || initTypeVal == "" || initTypeVal == "SysPage") {
		$("#defaultPageUrl").val("");
		$('#para').val("SysPage");
		$("#defaultPageType").val("SysPage");
		$("#defaultPageSel_Area").show();
		$("#defaultPageUrl_area").hide();
	} else if (initTypeVal == "UserPage") {
		$('#para').val("UserPage");
		$("#defaultPageID").val("");
		$("#pageSel").val("");
		$("#defaultPageSel_Area").hide();
		$("#defaultPageUrl_area").show();
	}
	$("#para").change(function() {
		var defaultPageTypeVal = $('#para').val();
		$("#defaultPageType").val(defaultPageTypeVal);
		if (defaultPageTypeVal == "UserPage") {
			$("#defaultPageSel_Area").hide();
			$("#defaultPageUrl_area").show();
			$("#defaultPageID").val("");
		} else if (defaultPageTypeVal == "SysPage") {
			$("#defaultPageUrl").val("");
			$("#defaultPageUrl_area").hide();
			$("#defaultPageSel_Area").show();
		}
	});
}