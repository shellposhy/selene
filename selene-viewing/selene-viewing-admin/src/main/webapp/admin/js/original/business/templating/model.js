/**
 * The script for template model .
 * 
 * @author shaobo shih
 * @version 1.0
 */
$(document).ready(function() {
	loadPageModelBillTree();
	//init_files_tree();
	//initFileMng();
	//init_directory_edit();
});

//Load page template bill tree
function loadPageModelBillTree() {
	if ($("#directoryModelTree").length > 0) {
		$.ajax({
			type : "POST",
			url : appPath + "/admin/templating/model/bill/tree",
			contentType : 'application/json',
			beforeSend: function(request) {//beforeSend
	            request.setRequestHeader("token", token);
	            request.setRequestHeader("refreshToken",refreshToken);
	         },
			success : function(data) {
				if (data.data != null) {
					menuTreeCom($("#directoryModelTree"), data.data, true,bindBillModelList,
							appPath+ "/admin/templating/model/bill/",$("#addModelBill"), bindModelBillAddEvent);
				}
			}
		});
	}
}

//Bind bill model list
function bindBillModelList(billId) {
	$.ajax({
		type : "POST",
		url : appPath + "/admin/templating/model/find/"+ billId,
		dataType : 'json',
		success : function(data) {
			var content = "";
					content += '<li id="add_model">';
					content += '	<a href="'+ appPath+ "/admin/templating/model/bill/new/"+ billId+ '"><div class="addimg_db"></div></a>';
					content += '	<div class="actions">';
					content += '		<a class="lh30 block db_repair mt10" href="'+ appPath + '/admin/templating/model/new/' + billId+ '">添加模板</a>';
					content +='		</div>';
					content += '</li>';
			if(data.code=200&&data.data!=null){
				for (var i = 0; i < data.data.length; i++){
					content += "<li id='mdv_"+ data.data[i].id+ "'>";
					content += "	<a class='addimg_db' href='"+ appPath+"/admin/templating/model/"+ data[i].id+ "/readFiles"+ "' target='_self' >";
					content += "		<div >";
					content += "			<img height='80' width='80' src='"+ appPath + "/admin/img/database.png'>";
					content += "		</div >";
					content += "	</a>";
					content += "	<span class='dbname'><i class='dbname'>"+ data.data[i].name + "</i></span>";
					content += "	<a class='btn btn-small' href='#' id='btnScan' onclick='scan("+ data.data[i].id+ ")' target='_self'><i class='icon-refresh'></i>同步</a>";
					content += "	<div class='actions' >";
					content += "		<a class='btn btn-small db_edit' href='"+ appPath+ "/admin/templating/model/"+data.data[i].id+"/edit' target='_self'><i class='icon-pencil'></i>修改</a>";
					content += "		<a class='btn btn-small ml5 db_del' href='#' onclick='delete_lib("+ data.data[i].id+ ")' target='_self'><i class='icon-trash'></i>删除</a>";
					content += "	</div>";
					content += "</li>";
				}
			}
			$("#modelList").html(content);
		}
	});
}

//Bind bill add event
function bindModelBillAddEvent(selid) {
	window.location.href = appPath+"/admin/templating/model/bill/"+selid+"/new";
}


//扫描模板目录
function scan(id) {
	if (confirm("确定同步吗?")) {
		$.ajax({
			url : thisPath + "scan/" + id,
			type : 'POST',
			success : function(response) {
				noty({"text" : "模板同步成功","layout" : "center","type" : "success"});
			}
		});
	} else{
		return false;
	}
}

// 删除模板
function delete_lib(id) {
	if (confirm("确定删除?")) {
		$.ajax({
			url : thisPath + "delete/" + id,
			type : 'POST',
			success : function(response) {
				$('#mdv_' + id).remove();
			}
		});
	} else{
		return false;
	}
}

// 初始化目录编辑页
function init_directory_edit() {
	if ($("#type_tree")[0]) {
		$.ajax({
			type : "POST",
			url : appPath + "/admin/view/model/directory/tree",
			success : function(data) {
				treeRadioCom($("#type_tree .treeNew"), data);
				setTimeout("$('.treeSelId').click()", 800);
			}
		});
	}
	//表单验证
	$("#db_new_form").validate({
		rules : {
			name : {required : true},
			code : {required : true}
		},
		messages : {
			name : {required : "模板目录名称不能为空！"},
			code : {required : "模板目录编码不能为空！"}
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

// 初始化化表内容
function view_model_tb() {
	var aoColumnsArry = [{
				"mData" : "name",
				"fnRender" : function(obj) {
					return '<label class="checkbox inline"><input type="checkbox" id="inlineCheckbox'+ obj.aData.id+ '" name="idStr'+ obj.aData.id+ '" value="'+ obj.aData.id+ '" style="opacity: 0;" >'+ obj.aData.name+ '</label><a  class="padmbt btn floatr none" href="'+ appPath+ "/admin/view/model/"+ obj.aData.id+ '/readFiles"><i class="icon-file" title = "文件管理"></i></a><button title="点击进行配置" data-rel="tooltip" class="btn btn-mini editbtn padmbt floatr none"><i class="icon-edit"></i></button>';
				}
			}, {
				"mData" : "code"
			}, {
				"mData" : "modelType"
			} ];
	var otherField = null;
	dataTablesCom($('#viewModel'), "/admin/view/model/s", aoColumnsArry,otherField, callback_model, true, true, true);
}

//回调函数
function callback_model(otd) {
	docReady();
	trHoverEdit();
	listDelete("view/model/delete", otd);
	batchOperation("#btnScan", "view/model/scan", otd, "#comOperModal");
}

//批量操作
function batchOperation(btnId, sUrl, oTable, noticeId) {
	$(btnId).unbind();
	$(btnId).click(function() {
		if ($(".table input[type='checkbox']").length > 0) {
			var count = 0;
			var idsVal = new Array();
			$(".table input[type='checkbox']").each(function() {
				if ($(this).attr("checked")&& $(this).val() != null&& $(this).val().length > 0) {
					idsVal.push($(this).val());
					count++;
				}
			});
			var sData = idsVal.join(",");
			var spData = {
				name : "ids",
				value : sData
			};
			if (count > 0) {
				$(noticeId).modal('show');
				$(noticeId).find('.btn-primary').unbind();
				$(noticeId).find('.btn-primary').click(function() {
					$.ajax({
						type : 'post',
						contentType : "application/json",
						url : sUrl,
						data : JSON.stringify(spData),
						success : function(resp) {
							oTable.fnDraw();
							setTimeout("isCheckboxStyle();", 300);
							$(noticeId).modal('hide');
						},
						error : function(data) {
							noty({"text" : "操作出错","layout" : "top","type" : "information"});
						}
					});
				});
			}
			return false;
		}
	});
}

//文件编辑页面
function initFileMng() {
	$('#fileMng').hide();
}

// 保存修改文件
$("#fileSave").click(function() {
	var tem = $('#newfileContent').val();
	var url = appPath + "/admin/view/model/saveFile";
	$('#fileContent').val(tem);
	$.post(url, {
		Action : "post",
		fileContent : ($('#fileContent').val()),
		filePath : filePath
	}, function(data, textStatus) {
		if ('success' == data) {
			noty({
				"text" : "保存成功！",
				"layout" : "center",
				"type" : "alert",
				"animateOpen" : {
					"opacity" : "show"
				}
			});
		} else {
			noty({
				"text" : "保存失败！",
				"layout" : "center",
				"type" : "alert",
				"animateOpen" : {
					"opacity" : "show"
				}
			});
		}
	});
});

// 初始化文件树
function init_files_tree() {
	var viewModelId = $('#viewModelId').val();
	if ($("#filesTree").length > 0) {
		var url = appPath + "/admin/view/model/" + viewModelId + "/fileTree";
		$.ajax({
			type : "get",
			url : url,
			dataType : 'json',
			contentType : 'application/json',
			success : function(data) {
				if (data != null) {
					menuTreeCom($("#filesTree"), data, true, node_click, null,null, null, null, null);
				}
			}
		});
	}
}

var filePath;
// 节点被点击触发的事件
function node_click(nodeId, nodeType, isDir, node) {
	if (!nodeType) {
		filePath = "";
		filePath = node.absolutePath; 
		var fileName = node.name; 
		var showForm = true;
		if (/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(fileName)) {
			showForm = false;
		}
		if (null != fileName.match(".rar")) {
			showForm = false;
		}
		$('#fileName').val(fileName);
		if (showForm) {
			$('#newfileContent').show();
			$('#imgFile').hide();
			$('#newfileContent').attr('readonly', false);
			$('#fileSave').show();
			var url = appPath + "/admin/view/model/path/readFile";
			var spData = {
				"name" : "para",
				"value" : filePath
			};
			$.ajax({
				type : "post",
				url : url,
				dataType : 'json',
				contentType : 'application/json',
				data : JSON.stringify(spData),
				success : function(data) {
					if (data != null) {
						$('#fileContent').val(data);
						$('#newfileContent').val(data);
					}
				},
				errors : function(data) {
				}
			});
		} else {
			$('#fileSave').hide();
			$('#fileContent').val(filePath);
			$('#newfileContent').attr('readonly', true);
			if (/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(fileName)) {
				$('#newfileContent').hide();
				$('#imgFile').attr("src", node.fileUrl)
				$('#imgFile').show();
			} else {
				$('#newfileContent').show();
				$('#imgFile').hide();
				$('#newfileContent').val(filePath);
			}
		}
		$('#fileMng').show();
	} else {
		$('#fileMng').hide();
	}
}