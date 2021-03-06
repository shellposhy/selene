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
	checkMerchantsRoleForm();
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
		"oLanguage" : {"sUrl" : appPath + "/admin/js/min/util/zh_cn.min.js"},
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

//check role form
function checkMerchantsRoleForm() {
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