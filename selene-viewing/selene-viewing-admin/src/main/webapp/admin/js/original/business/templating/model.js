/**
 * The script for template model .
 * 
 * @author shaobo shih
 * @version 1.0
 */
$(document).ready(function() {
	loadPageModelBillTree();
	loadModelBillEditTree();
	validateBillForm();
	validateModelForm();
	loadModelTemplateFileTree();
});

//Load model bill edit tree
function loadModelBillEditTree(){
	if ($("#bill_tree")[0]) {
		$.ajax({
			type : "POST",
			url : appPath + "/admin/templating/model/bill/tree",
			contentType : 'application/json',
			beforeSend: function(request) {//beforeSend
	            request.setRequestHeader("token", token);
	            request.setRequestHeader("refreshToken",refreshToken);
	         },
			success : function(data) {
				treeRadioCom($("#bill_tree .treeNew"), data.data);
				setTimeout("$('.treeSelId').click()", 800);
			}
		});
	}
}

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
					menuTreeCom($("#directoryModelTree"), data.data, true,bindBillModelList,appPath+ "/admin/templating/model/bill/",null, null, null, null);
				}
			}
		});
	}
}

//Before check where model bill check 
function validateBillForm() {
	$("#bill_form").validate({
		rules : {
			name : {required : true},
			code : {required : true},
			type : {required : true}
		},
		messages : {
			name : {required : "模板目录名称不能为空！"},
			code :  {required : "模板目录编码不能为空！"},
			type :  {required : "模板目录类型不能为空！"}
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

//Bind bill model list
function bindBillModelList(billId) {
	//Update add model bill href
	$("#addModelBill").attr("href",appPath+"/admin/templating/model/bill/"+billId+"/new");
	$.ajax({
		type : "POST",
		url : appPath + "/admin/templating/model/find/"+ billId,
		dataType : 'json',
		beforeSend: function(request) {//beforeSend
            request.setRequestHeader("token", token);
            request.setRequestHeader("refreshToken",refreshToken);
         },
		success : function(data) {
			var content = "";
					content += '<li id="add_model">';
					content += '	<a href="'+ appPath+ '/admin/templating/model/'+billId+'/new"><div class="addimg_subject"></div></a>';
					content += '	<div class="actions">';
					content += '		<a class="lh30 block db_repair mt10" href="'+ appPath + '/admin/templating/model/'+billId+'/new">添加模板</a>';
					content +='		</div>';
					content += '</li>';
			if(data.code==200&&data.data!=null){
				for (var i = 0; i < data.data.length; i++){
					content += "<li id='mdv_"+ data.data[i].id+ "'>";
					content += "	<a class='addimg_db' href='"+ appPath+"/admin/templating/model/"+ data.data[i].id+ "/template"+ "' target='_self' title='模板内容浏览'>";
					content += "		<div >";
					content += "			<img height='80' width='80' src='"+ appPath + "/admin/img/subject.png'>";
					content += "		</div >";
					content += "	</a>";
					content += "	<span class='dbname'><i class='dbname'>"+ data.data[i].modelName + "</i></span>";
					content	+= "	<span class='dbtime'>更新时间<br>"+ new Date(data.data[i].updateTime).format("yyyy-MM-dd hh:mm:ss") +"</span>";
					//content += "	<a class='btn btn-small' href='#' id='btnScan' onclick='scan("+ data.data[i].id+ ")' target='_self'><i class='icon-refresh'></i>同步</a>";
					content += "	<div class='actions' >";
					content += "		<a class='btn btn-small db_edit' href='"+ appPath+ "/admin/templating/model/"+data.data[i].id+"/edit' target='_self'><i class='icon-pencil'></i>修改</a>";
					content += "		<a class='btn btn-small ml5 db_del' href='#' onclick='deleteModel("+ data.data[i].id+ ")' target='_self'><i class='icon-trash'></i>删除</a>";
					content += "	</div>";
					content += "</li>";
				}
			}
			$("#modelList").html(content);
		}
	});
}


/**Model processing*/
//Before check where model bill check 
function validateModelForm() {
	$("#pageModelForm").validate({
		rules : {
			modelName : {required : true},
			modelCode : {required : true}
		},
		messages : {
			modelName : {required : "模板名称不能为空！"},
			modelCode :  {required : "模板编码不能为空！"}
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

//Load model template file tree
function loadModelTemplateFileTree() {
	$("#fileMng").hide();
	if ($("#filesTree").length > 0) {
		$.ajax({
			type : "POST",
			url : appPath + "/admin/templating/model/" + $('#modelId').val() + "/template/file",
			dataType : 'json',
			contentType : 'application/json',
			beforeSend: function(request) {//beforeSend
	            request.setRequestHeader("token", token);
	            request.setRequestHeader("refreshToken",refreshToken);
	         },
			success : function(data) {
				if (data!=null) {
					menuTreeCom($("#filesTree"), data, true, bindFileNode, null,null, null, null, null);
				}
			}
		});
	}
}

//bind file tree click event
function bindFileNode(nodeId, nodeType, isDir, node) {
	$('#fileMng').hide();
	if (!nodeType) {
		$('#fileName').val(node.name);
		if (/\.(gif|ico|jpg|jpeg|png|bmp|GIF|ICO|JPG|JPEG|PNG|BMP)$/.test(node.name)) {
			$('#fileContent').hide();
			$('#imgFile').attr("src", node.filePath)
			$('#imgFile').show();
		}else{
			$('#imgFile').hide();
			$('#fileContent').show();
			$('#fileContent').attr('readonly', true);
			var spData = {"name" : "para","value" : node.absolutePath};
			$.ajax({
				type : "post",
				url :  appPath + "/admin/templating/model/template/file/read",
				dataType : 'json',
				beforeSend: function(request) {//beforeSend
		            request.setRequestHeader("token", token);
		            request.setRequestHeader("refreshToken",refreshToken);
		         },
				contentType : 'application/json',
				data : JSON.stringify(spData),
				success : function(data) {
					if (data.code==200&&data.data!=null) {
						$('#fileContent').val(data.data);
					}
				},
				errors : function(data) {
					noty({"text" : data.msg,"layout" : "center","type" : "error","animateOpen" : {"opacity" : "show"}});
				}
			});
		}
		$('#fileMng').show();
	}
}

//Delete page model
function deleteModel(id) {
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