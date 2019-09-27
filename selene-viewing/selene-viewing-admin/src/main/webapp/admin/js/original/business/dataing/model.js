/**
 * The script  for dataing model.
 * @author shaobo shih
 * @version 1.0
 */
$(document).ready(function() {
	//Init dataTable
	loadModelDataTable();
	//Edit data model
	init_model_field();
	model_form_validate();
});

//Load the database model data
var dataModelDataTable;
function loadModelDataTable() {
	dataModelDataTable = $("#dataTableModel").dataTable({
		"sDom" : "<'row-fluid'<'span6 alignr'f>r>t<'row-fluid'<'span12'i><'span12 center'p>>",
		"sPaginationType" : "full_numbers",
		"bProcessing" : true,
		"bServerSide" : true,
		"bDestroy" : true,
		"bRetrieve" : true,
		"bSort" : false,
		"sAjaxSource" :appPath+"/admin/dataing/model/s",
		"fnServerData" : retrieveData,
		"iDisplayLength" : 20,
		"oLanguage" : {"sUrl" : appPath + "/admin/js/min/util/zh_cn.min.js"},
		"aoColumns" : [{
			"mData" : "modelName",
			"fnRender" : function(obj) {
				var content='';
				if(!obj.aData.forSystem){
						content+='<label class="checkbox inline">';
						content+='		<input type="checkbox" id="inlineCheckbox'+ obj.aData.id+ '" name="idStr'+ obj.aData.id+ '" value="'+ obj.aData.id+ '" style="opacity: 0;" >';
						content+='</label>';
						content+='<span>'+ obj.aData.modelName + '</span>';
						content+='<button title="编辑" data-rel="tooltip" class="btn btn-mini editbtn padmbt floatr none"><i class="icon-edit"></i></button>';
				}else{
					content+=obj.aData.modelName ;
				}
				return content;
			}
		}, {
			"mData" : "modelType",
			"fnRender":function(obj){
				if(obj.aData.modelType=='Default'){
					return "普通库";
				}else if(obj.aData.modelType=='Image'){
					return "图片库";
				}else if(obj.aData.modelType=='File'){
					return "文件库";
				}else if(obj.aData.modelType=='Video'){
					return "视频库";
				}
			}
		},{
			"mData" : "modelCode"
		},{
			"mData" : "fieldsName"
		},{
			"mData" : "memo"
		}],
		"fnDrawCallback" : callback_model
	});
}

function callback_model() {
	docReady();
	trHoverEdit(bindModelEdit);
	trHoverModi();
	listDelete("model/delete", dataModelDataTable); 
};

function bindModelEdit() {
	$(".trHoverEdit tr .editbtn").live("click", function() {
		var id = $(this).parent().find("input[type='checkbox']").val();
		window.location.href = "model/" + id + "/edit";
	});
}


function init_model_field() {
	if ($("#modelFields").length > 0) {
		$("#modelFieldSelect").change(function() {
			$("#modelFields").val($(this).val());
		});
		var trSIdArry = ($("#modelFields").val()).split(",");
		var options = $("#modelFieldSelect option");
		options.each(function() {
			for ( var j = 0; j < trSIdArry.length; j++) {
				if ($(this).val() == trSIdArry[j]) {
					$(this).attr("selected", "true");
				}
			}
		});
	}
}

// 表单验证
function model_form_validate() {
	if($("#cl_new_form")){
		$("#cl_new_form").validate({
			rules : {
				modelName : {
					required : true
				},
				modelCode : {
					required : true
				}
			},
			messages : {
				modelName : {
					required : "数据模板名称不能为空！"
				},
				modelCode : {
					required : "数据模板编码不能为空！"
				}
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
}
