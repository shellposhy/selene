/**
 * The script for dataing database.
 * 
 * @author shaobo shih
 * @version 1.0
 */
$(document).ready(function() {
	//load database tree
	loadDatabaseTree();
	loadDirectoryTree();
	loadLibraryTree();
	//library need show fields
	loadLibraryNeedShowFields();
	//Search library
	bindLibrarySearch();
	// edit
	validateDatabase();
});

//load default database tree
function loadDatabaseTree() {
	if ($("#directoryTree").length > 0) {
		$.ajax({
			type : "POST",
			url : appPath + "/admin/dataing/directory/tree",
			beforeSend: function(request) {//beforeSend
                request.setRequestHeader("token", token);
                request.setRequestHeader("refreshToken",refreshToken);
             },
             contentType : 'application/json',
			success : function(data) {
				if (data.data != null) {
					menuTreeCom($("#directoryTree"), data.data, true, bindTreeNodeEvent);
				}
			}
		});
	}
}

//load directory tree by add or edit 
function loadDirectoryTree() {
	if($("#type_tree")[0]){
		$.ajax({
			type : "POST",
			url :  appPath+"/admin/dataing/directory/emptyTree",
			beforeSend: function(request) {//beforeSend
                request.setRequestHeader("token", token);
                request.setRequestHeader("refreshToken",refreshToken);
             },
			success : function(data) {
				treeRadioCom($("#type_tree .treeNew"),data.data);
				setTimeout("$('.treeSelId').click()",800);
			}
		});	
	}
}

//load library tree by add or edit 
function loadLibraryTree() {
	if ($("#categories_tree_radio").length > 0) {
		if ($("#categories_tree_radio")[0]) {
			$.ajax({
				type : "POST",
				url : appPath + "/admin/dataing/directory/tree",
				beforeSend: function(request) {//beforeSend
	                request.setRequestHeader("token", token);
	                request.setRequestHeader("refreshToken",refreshToken);
	             },
				success : function(data) {
					treeRadioCom($("#categories_tree_radio .treeNew"),data.data, true);
					setTimeout("$('.treeSelId').click()", 800);
				}
			});
		}
	}
}

//bind the database tree click event
function bindTreeNodeEvent(nodeId, /*Is leaf node*/nodeType, /*Node type*/isDir) {
	$("#libId").val(nodeId);
	var treeObject = $.fn.zTree.getZTreeObj("directoryTree");
	var treeNode = treeObject.getNodeByParam("id", nodeId, null);
	$("#colname").html(treeNode.name);
	if (/*Directory*/isDir) {
		$('#search_u_db').hide();
		$('#search_u_db_btn').hide();
		$('#libraries').show();
		$('#data_content').hide();
		if (/*Tree parent node*/nodeType) {
			loadParentLibrary(nodeId);
		} else/*Tree leaf node*/ {
			loadLeafLibrary(nodeId);
		}
	} else/*Library and search data*/ {
		$('#search_u_db').hide();
		$('#search_u_db_btn').hide();
		$('#libraries').hide();
		$('#data_content').show();
		loadLibraryData(nodeId);
	}
}

//add database directory
function addNewDirectory(selid) {
	selid = selid > 0 ? selid : 0;
	window.location.href = appPath + "/admin/dataing/directory/"+selid+"/new";
}

//when the current database node is the parent node,loading the database node
//what the parent node is the current database node
function loadParentLibrary(parentId) {
	$("#libId").val(parentId);
	var treeObject = $.fn.zTree.getZTreeObj("directoryTree");
	var treeNode = treeObject.getNodeByParam("id", parentId, null);
	$("#colname").html(treeNode.name);
	$.ajax({
		type : "POST",
		url : appPath + "/admin/dataing/library/find/" + parentId,
		dataType : 'json',
		beforeSend: function(request) {//beforeSend
            request.setRequestHeader("token", token);
            request.setRequestHeader("refreshToken",refreshToken);
         },
		success : function(data) {
			$("#libraries").html("");
			if (data.data != null) {
				var data_node = data.data[0];
				var node_type = data_node.nodeType;
				/*For develop alert(node_type)*/
				if (node_type == "Lib") {
					var libMenu = "";
					libMenu += '<li id="add_lib">';
					libMenu += ' <a href="' + appPath + "/admin/dataing/library/" + $("#libId").val() + '/new"><div class="addimg_db"></div></a>';
					libMenu += '	 <div class="actions"><a class="lh30 block db_repair mt10" href="'+ appPath + '/admin/dataing/library/' + $("#libId").val() + '/new">添加数据库</a></div>';
					libMenu += '</li>';
					$("#libraries").html(libMenu);
					for (var i = 0; i < data.data.length; i++) {
						var editLink = "";
						editLink += "<li id='dbv_" + data.data[i].id + "'>";
						editLink += "	<a class='' target='_blank' href='javascript:loadLinkLibraryEvent("+ data.data[i].id + ",false)' target='_self' ><div class='dbimg'></div></a>";
						editLink += "	<span class='dbname'><i class='dbname'>" + data.data[i].name + "</i></span>";
						editLink += "	<span class='dbtime'>更新时间<br />" + new Date(data.data[i].updateTime).format("yyyy-MM-dd hh:mm:ss") + "</span>";
						editLink += "	<div class='actions' >";
						editLink += "		<a title='修改数据库' class='btn btn-small db_edit' href='" + appPath + "/admin/dataing/library/" + data.data[i].id +"/edit' target='_self'><i class='icon-pencil'></i></a>";
						editLink += "		<a title='删除数据库' class='btn btn-small ml3 db_del' href='#' onclick='deleteLibrary("+ data.data[i].id+ ")' target='_self'><i class='icon-trash'></i></a>";
						editLink += "		<a class='lh30 block db_repair mt10' href='#' onclick='repair_lib(" + data.data[i].id + ")' target='_self'>修复数据库</a>";
						editLink += "	</div>";
						editLink += "	<div class='progress progress-striped progress-success active none'><div class='bar'></div></div>";
						editLink += "</li>";
						$("#libraries").append(editLink);
					}
				} else if (node_type == "Directory") {
					var libMenu = "";
					libMenu += '<li id="add_library">';
					libMenu += '		<a href="' + appPath + "/admin/dataing/directory/" + $("#libId").val() + '/new"><div class="addimg_db"></div></a>';
					libMenu += '		<div class="actions"><a class="lh30 block  mt10" href="' + 'javascript:addNewDirectory(' + $("#libId").val() + ')' + '">添加目录</a></div>';
					libMenu += '</li>';
					$("#libraries").html(libMenu);
					for (var i = 0; i < data.data.length; i++) {
						var editLink = "";
						editLink += "<li id='dbv_" + data.data[i].id + "'>";
						editLink += "	<a class='' target='_blank' href='javascript:loadLinkLibraryEvent(" + data.data[i].id + ",true" + ")" + "' target='_self' ><div class='dbimg'></div></a>";
						editLink += "	<span class='dbname'><i class='dbname'>" + data.data[i].name + "</i></span>";
						editLink += "	<span class='dbtime'>创建时间<br />" + new Date(data.data[i].createTime).format("yyyy-MM-dd hh:mm:ss") + "</span>";
						editLink += "	<div class='actions' >";
						editLink += "		<a title='修改目录' class='btn btn-small db_edit' href='" + appPath + "/admin/dataing/directory/" + data.data[i].id + "/edit" + "' target='_self'><i class='icon-pencil'></i></a>";
						editLink += "		<a title='删除目录' class='btn btn-small ml3 db_del' href='#' onclick='deleteDirectory(" + data.data[i].id + ")' target='_self'><i class='icon-trash'></i></a>";
						editLink += "	</div>";
						editLink += "	<div class='progress progress-striped progress-success active none'><div class='bar'></div></div>";
						editLink += "</li>";
						$("#libraries").append(editLink);
					}
				}
				$('#search_u_db').show();
				$('#search_u_db_btn').show();
			}
		}
	});
}

//when the current database node has not database library,add two buttons
function loadLeafLibrary(parentId) {
	var libMenu = "";
	libMenu += '<li id="add_library">';
	libMenu += ' 	<a href="' + appPath + "/admin/dataing/directory/" + $("#libId").val() + '/new"><div class="addimg_db"></div></a>';
	libMenu += '	 <div class="actions"><a class="lh30 block  mt10" href="' + 'javascript:addNewDirectory(' + $("#libId").val() + ')' + '">添加目录</a></div>';
	libMenu += '</li>';
	libMenu += '<li id="add_lib">';
	libMenu += ' 	<a href="' + appPath + "/admin/dataing/library/" + $("#libId").val() + '/new"><div class="addimg_db"></div></a>';
	libMenu += '	 <div class="actions"><a class="lh30 block db_repair mt10" href="' + appPath + '/admin/dataing/library/' + $("#libId").val() + '/new">添加数据库</a></div>';
	libMenu += '</li>';
	$("#libraries").html(libMenu);
}

//bind database node
function loadLinkLibraryEvent(parentId, isDir) {
	var treeObject = $.fn.zTree.getZTreeObj("directoryTree");
	var treeNode = treeObject.getNodeByParam("id", parentId, null);
	treeObject.expandNode(treeNode, true, false, false);
	treeObject.selectNode(treeNode);
	$("#colname").html(treeNode.name);
	var nodeType = treeNode.isParent;
	$("#libId").val(parentId);
	if (isDir == true) {
		$('#libraries').show();
		$('#data_content').hide();
		if (nodeType) {
			loadParentLibrary(parentId);
		} else {
			loadLeafLibrary(parentId);
		}
	} else {
		$('#libraries').hide();
		$('#data_content').show();
		loadLibraryData(parentId);
	}
}

//delete directory
function deleteDirectory(id) {
	if (confirm("确定删除?")) {
		$.ajax({
			url : appPath + "/admin/dataing/directory/" + id + "/delete",
			type : 'POST',
			beforeSend: function(request) {//beforeSend
	            request.setRequestHeader("token", token);
	            request.setRequestHeader("refreshToken",refreshToken);
	         },
			success : function(data) {
				if(data.code==100){
					noty({"text" :data.msg ,"layout" : "center","type" : "error"});
				}else if(data.code==200){
					noty({"text" : "删除成功！","layout" : "center","type" : "success"});
					$('#dbv_' + id).remove();
					//reload database tree
					loadDatabaseTree();
				}
			}
		});
	} else{
		return false;
	}
}

// delete library
function deleteLibrary(id) {
	if (confirm("确定删除?")) {
		$.ajax({
			url : appPath + "/admin/dataing/library/"+id+"/delete",
			type : 'POST',
			beforeSend: function(request) {//beforeSend
	            request.setRequestHeader("token", token);
	            request.setRequestHeader("refreshToken",refreshToken);
	         },
			success : function(data) {
				if(data.code==100){
					noty({"text" :data.msg ,"layout" : "center","type" : "error"});
				}else if(data.code==200){
					noty({"text" : "删除成功！","layout" : "center","type" : "success"});
					$('#dbv_' + id).remove();
					//reload database tree
					loadDatabaseTree();
				}
			}
		});
	} else{
		return false;
	}
}

//The showing database fields before the database created
function loadLibraryNeedShowFields() {
	var columnModelId = $("#db_columnModelId").val();
	var dataFieldIdStr = $('#dataFieldsStr').val();
	var fieldsIds = new Array();
	if (null != dataFieldIdStr && "" != dataFieldIdStr) {
		fieldsIds = dataFieldIdStr.split(',');
	}
	if (null != columnModelId && "" != columnModelId) {
		$.ajax({
			type : "GET",
			url : appPath+"/admin/dataing/library/show/"+ columnModelId,
			dataType : 'json',
			contentType : 'application/json',
			beforeSend: function(request) {//beforeSend
	            request.setRequestHeader("token", token);
	            request.setRequestHeader("refreshToken",refreshToken);
	         },
			success : function(data) {
				var content = "";
				var item = data.data;
				var dataSize = data.data.length;
				if (dataSize == fieldsIds.length && fieldsIds.length == 0) {
					for (var i = 0; i < dataSize; i++) {
						content += "<label>";
						content += "	<input  type='checkbox'   onclick='libraryShowFieldEvent()'   rel='Reason' name='chk'   value='" + item[i].id + "'  >" + item[i].name;
						content += "</label> ";
					}
				} else if (dataSize == fieldsIds.length && fieldsIds.length > 0) {
					for (var i = 0; i < dataSize; i++) {
						content += "<label>";
						content +="		<input  type='checkbox'   onclick='libraryShowFieldEvent()'  checked  rel='Reason' name='chk'   value='"+ item[i].id+ "'  >"+ item[i].name;
						content	+="</label> ";
					}
				} else {
					for (var i = 0; i < dataSize; i++) {
						var dataFd = item[i].id;
						var flag = false;
						for (var j = 0; j < fieldsIds.length; j++) {
							if (dataFd == fieldsIds[j]) {
								flag = true;
								break;
							}
						}
						if (flag == false) {
							content += "<label>";
							content += "		<input  type='checkbox' onclick='libraryShowFieldEvent()'  rel='Reason' name='chk'   value='"+ item[i].id+ "'  >"+ item[i].name;
							content	+= "</label> ";
						} else {
							content += "<label>";
							content +="		<input  type='checkbox' onclick='libraryShowFieldEvent()'    rel='Reason' name='chk'  value='" + item[i].id + "'   checked >" + item[i].name;
							content += "</label> ";
						}
					}
				}
				$('#columnModelFileds').html("");
				content += '<label>';
				content += '	<div class="checker" id="uniform-undefined">';
				content += '		<span class="checked">';
				content += '			<input type="checkbox" onclick="libraryShowFieldEvent()" rel="Reason" name="chk" value="16" style="opacity: 0;">';
				content += '		</span>';
				content += '	</div>文档时间';
				content += '</label>';
				$('#columnModelFileds').append(content);
				
				var /*Init select fields*/fieldIds = "";
				var checkVals = $('#columnModelFileds :checkbox:checked');
				for (var i = 0; i < checkVals.length; i++) {
					fieldIds += checkVals[i].value + ",";
				}
				$("input:checkbox").uniform();
				fieldIds = fieldIds.substring(0, fieldIds.length - 1);
				$('#dataFieldsStr').val(fieldIds);
			}
		});
	}
}

//Library show fields( or column) for data list
function libraryShowFieldEvent() {
	var checkElem = document.getElementsByName("chk");
	var temVal = "";
	for (var i = 0; i < checkElem.length; i++) {
		if (checkElem[i].checked) {
			temVal += checkElem[i].value + ",";
		}
	}
	temVal = temVal.substring(0, temVal.length - 1);
	$('#dataFieldsStr').val(temVal);
}

//database node search
function bindLibrarySearch() {
	$("#search_u_db_btn").click(function() {
		if ($("#search_u_db").val()) {
			$("#categoryTree .curSelectedNode").removeClass("curSelectedNode");
			var sdata = '[{"name":"sSearch","value":"'+ $("#search_u_db").val() + '"}]';
			$.ajax({
				type : "post",
				url : appPath + "/admin/dataing/library/search",
				dataType : 'json',
				contentType : 'application/json',
				data : sdata,
				beforeSend: function(request) {//beforeSend
		            request.setRequestHeader("token", token);
		            request.setRequestHeader("refreshToken",refreshToken);
		         },
				success : function(data) {
					if (data.data.length != 0) {
						for (var i = 0; i < data.data.length; i++) {
							var nodeType=data.data[i].nodeType=='Lib' ? "false" :"true";
							var content = "<li id='dbv_" + data.data[i].id + "'>";
									content += "	<a class='' target='_blank' href='javascript:loadLinkLibraryEvent(" + data.data[i].id + ","+nodeType + ")' target='_self' ><div class='dbimg'></div></a>";
									content += "	<span class='dbname'><i class='dbname'>" + data.data[i].name + "</i></span>";
									if(data.data[i].nodeType=='Lib'){
										content += "	<span class='dbtime'>更新时间<br />" +  new Date(data.data[i].updateTime).format("yyyy-MM-dd hh:mm:ss")  + "</span>";
									}else{
										content += "	<span class='dbtime'>创建时间<br />" +  new Date(data.data[i].createTime).format("yyyy-MM-dd hh:mm:ss")  + "</span>";
									}
									content += "	<div class='actions' >";
									if(data.data[i].nodeType=='Lib'){
										content += "		<a class='btn btn-small db_edit' href='" + appPath + "/admin/dataing/library/" + data.data[i].id +"/edit' target='_self'><i class='icon-pencil'></i></a>";
										content += "		<a class='btn btn-small ml5 db_del' href='#' onclick='deleteLibrary(" + data.data[i].id + ")' target='_self'><i class='icon-trash'></i></a>";
									}else{
										content += "		<a class='btn btn-small db_edit' href='" +appPath + "/admin/dataing/directory/" + data.data[i].id + "/edit' target='_self'><i class='icon-pencil'></i></a>";
										content += "		<a class='btn btn-small ml5 db_del' href='#' onclick='deleteDirectory(" + data.data[i].id + ")' target='_self'><i class='icon-trash'></i></a>";
									}
									if(data.data[i].nodeType=='Lib'){
										content += "		<a class='lh30 block db_repair mt10' href='#' onclick='repair_lib(" + data.data[i].id + ")' target='_self'>修复数据库</a>";
									}
									content += "	</div>";
									content += "	<div class='progress progress-striped progress-success active none'><div class='bar'></div></div>";
									content += "</li>";
							$("#libraries").html(content);
						}
					} else {
						$("#libraries").html("<h3 class='alert alert-info' >无匹配的搜索结果！</h3>");
					}
				}
			});
		} else {
			if ($("#libraries .alert-info:contains(请选择)").length) {
				$("#search_u_db").focus();
			} else {
				$("#directoryTree .curSelectedNode").removeClass("curSelectedNode");
				$("#libraries").html("<h3 class='alert alert-info'>请选择左侧分类以查看数据库</h3>");
			}
		}
	});
	$("#search_u_db").keyup(function(event) {
		if (event.keyCode == 13) {
			$("#search_u_db_btn").trigger("click");
			$(this).blur();
		} else {
			return false;
		}
	});
}

// when the current database node is the library,loading the data
function loadLibraryData(libId) {
	$("#libId").val(libId);
	$('#search_u_db').hide();
	$('#search_u_db_btn').hide();
	var treeObject = $.fn.zTree.getZTreeObj("directoryTree");
	var treeNode = treeObject.getNodeByParam("id", libId, null);
	$("#colname").html(treeNode.name);
	$('#into_as_search').attr("href", appPath + '/admin/data/as');
	$('#colDatas thead tr th, #colDatas tfoot tr th').remove();
	$('#colDatas thead tr').append('<th><label class="checkbox inline"><input type="checkbox" class="selAll" />标题</label></th>');
	$('#colDatas tfoot tr').append('<th>标题</th>');
	var headTitle = [ {
		"mData" : "title",
		"fnRender" : function(obj) {
			var sumImg = '', attach = '';
			if (obj.aData.img){
				sumImg = '<div class="sum_img_div"><img class="list_sum_img" src="'+ obj.aData.img + '"/></div>';
			}
			if (obj.aData.attach){
				attach = '<span class="icon icon-blue icon-attachment"></span>';
			}
			return		'<h3>' 
							+ '	<label class="checkbox inline mt0">' 
							+ '		<input type="checkbox" id="inlineCheckbox' + obj.aData.id + '" name="idStr' + obj.aData.id + '" value="' + obj.aData.id + '_' + obj.aData.tableId + '" style="opacity: 0;">'
							+ '	</label>'
							+ '	<a class="data_title edit_pop_link" href="'+ appPath+ '/admin/dataing/library/data/edit/'+ obj.aData.tableId+ '/'+ obj.aData.id+ '" target="_blank">'+ obj.aData.title+ '</a>'
							+ '	<a class="padmbt btn floatr none edit_pop_link" href = "'+ appPath+ '/admin/dataing/library/data/info/'+ obj.aData.tableId+ '/'+ obj.aData.id+ '" target="_blank"><i class="icon-eye-open" title="稿件预览"></i></a>'
							+ '</h3>'
							+ '<p class="summary clearfix" >'+ sumImg+ obj.aData.summary + attach + '</p>';
		}
	} ];
	$('#add_to_dsu').attr('href', appPath+"/admin/dataing/library/data/"+libId+"/new");
	$.ajax({
		url : appPath + "/admin/system/library/data/title/" + libId,
		async : false,
		beforeSend: function(request) {//beforeSend
            request.setRequestHeader("token", token);
            request.setRequestHeader("refreshToken",refreshToken);
         },
		success : function(data) {
			for (var i = 0; i < data.length; i++) {
				var mData = {
					"mData" : data[i].codeName
				};
				headTitle.push(mData);
				$('#colDatas thead tr,#colDatas tfoot tr').append("<th>" + data[i].name + "</th>");
			}
		}
	});
	dataTablesCom($('#colDatas'), "/admin/dataing/library/data/search/" + libId,headTitle, null, callback_library_data, true);
}

// datatable callback function
function callback_library_data(otd) {
	docReady();
	trHoverEdit();
	$(".action_buttons").show();
	listDelete(appPath + "/admin/dataing/library/data/delete", otd);
	editPopWithDT(otd);
}

// repair the database index
function repair_lib(id) {
	function repair(id) {
		$.ajax({
			url : appPath + "/admin/dataing/library/data/repair/" + id,
			type : 'POST',
			success : function(data) {
				var thisact = $('#dbv_' + id).find(".actions");
				thisact.hide();
				var thispro = $('#dbv_' + id).find(".progress");
				var thisbar = $('#dbv_' + id).find(".progress .bar");
				thispro.show();
				thisbar.css("width", "0%");
				var oShowProgress = function showProgress() {
					$.ajax({
						type : "GET",
						url : appPath + "/admin/task/progress/" + data,
						cache : false,
						dataType : "json",
						success : function(data) {
							var barpres = data.progress + "%";
							if (data.progress < 100) {
								thisbar.css("width", barpres);
							} else if (data.progress == 100) {
								thisbar.css("width", "100%");
								thispro.hide();
								thisact.show();
								clearInterval(intInterval);
								noty({
									"text" : "修复成功！",
									"layout" : "center",
									"type" : "success",
									"animateOpen" : {
										"opacity" : "show"
									}
								});
							}
						}
					});
				};
				var intInterval = setInterval(oShowProgress, 5000);
			}
		});
	}
	comConfirmModel(repair, id, "确定修复", "修复将耗时较长，确定修复?");
}

// validate the form
function validateDatabase() {
	$("#db_new_form").validate({
		ignore : "",
		rules : {
			name : "required",
			code : "required",
			parentId : "required"
		},
		messages : {
			name : "请填写数据库名称",
			code : "请填写数据库编号",
			parentId : "请选择数据库所属分类"
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

