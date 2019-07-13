/**
 * The script  for merchants and merchants organization.
 * @author shaobo shih
 * @version 1.0
 */
$(document).ready(function() {
	//merchants list and user
	loadMerchantsOrgTree();
	//merchants org
	loadRadioMerchantsOrgTree();
	loadOrgRoleOptions();
	checkMerchantsOrgForm();
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
					menuTreeCom($("#merchantsOrg"), data.data, true, bindTreeClickEvent, "merchants/org/",null, null, null, null);
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
		$(".selAll").parents("th").html('<label class="checkbox inline"><input type="checkbox" class="selAll" />用户名</label>')
	}
	loadMerchantsByOrgId(id);
	$("#merchantsList").show().find(".dataTable").attr("style", "width:100%");
	if(id>0){
		$('#add_g_db_btn').removeAttr("disabled"); 
		$("#add_g_db_btn").attr("href",appPath+"/admin/merchants/org/"+id+"/new");
	}
	var content="<a class='btn btn-small add_user' href='"+ appPath+ "/admin/merchants/"+ id+ "/new' target='_self'><i class='icon-plus'></i> 添加</a>";
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
		"sAjaxSource" : "merchants/" + orgId + "/s",
		"fnServerData" : retrieveData,
		"fnServerParams" : function(aoData) {aoData.push({"name" : "iType","value" : 0});},
		"iDisplayLength" : 20,
		"oLanguage" : {"sUrl" : appPath + "/admin/js/javascript/de_CN.js"},
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
};

/**
 *  merchants org process
 * */
//load merchants organization tree
function loadRadioMerchantsOrgTree() {
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
					treeRadioCom($("#merchantsOrgTree .treeNew"), data.data, false);
					setTimeout("$('.treeSelId').click()", 800);
				}
			}
		});
	}
}

//Load multiple selected user roles
function loadOrgRoleOptions() {
	if ($("#org_new_form #name").val()) {
		if ($("#treeSelId").val()) {
			var trSIdArry = ($("#treeSelId").val()).split(",");
		}
		if ($("#groupListJson").text()) {
			var groupListJson = $("#groupListJson").text();
			var groupListJsonO = new Function("return" + groupListJson)();
			var tmpOptions = "";
			for ( var i = 0; i < groupListJsonO.length; i++) {
				if ($("#treeSelId").val()) {
					var tmpOptionsTrue = "";
					for ( var j = 0; j < trSIdArry.length; j++) {
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
			$("#org_new_form #treeSelId_box").append(tmpOptions);
		}
	} else {
		if ($("#groupListJson").text()) {
			var groupListJson = $("#groupListJson").text();
			var groupListJsonO = new Function("return" + groupListJson)();
			var temOptions = "";
			for ( var i = 0; i < groupListJsonO.length; i++) {
				tmpOptions += "<option value='" + groupListJsonO[i].id + "'>" + groupListJsonO[i].name + "</option>";
			}
			$("#org_new_form #treeSelId_box").html(tmpOptions);
		}
	}
	var treeSelIds = $("#org_new_form #treeSelId_box").val();
	$("#treeSelId").val(treeSelIds);

	$("#org_new_form #treeSelId_box").change(function() {
		var treeSelIdsn = $("#org_new_form #treeSelId_box").val();
		$("#treeSelId").val(treeSelIdsn);
	});
}


//check merchants organization form
function checkMerchantsOrgForm() {
	$("#org_new_form").validate({
		rules : {
			name : {required : true},
			code : {required : true}
		},
		messages : {
			name : {required : "商户机构名称不能为空！"},
			code : {required : "商户机构编码不能为空！"}
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

/**
 *  merchants user add  process
 * */