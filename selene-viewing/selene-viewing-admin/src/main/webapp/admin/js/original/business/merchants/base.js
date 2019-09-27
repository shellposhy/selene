/**
 * The script  for merchants and merchants organization.
 * @author shaobo shih
 * @version 1.0
 */
$(document).ready(function() {
	//merchants list and user
	loadMerchantsOrgTree();
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
					menuTreeCom($("#merchantsOrg"), data.data, true, bindTreeClickEvent, null,null, null, null, null);
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
					"mData" : "name"
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
};