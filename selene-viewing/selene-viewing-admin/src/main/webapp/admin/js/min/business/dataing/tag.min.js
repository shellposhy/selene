/**
 * The script for dataing tags
 * 
 * @author shaobo shih
 * @version 1.0
 */
$(document).ready(function() {
	loadAllTagTree();
	loadEditAllTagTree();
	validateTagForm();
});

/**
 * 加载全局数据分类
 */
function loadAllTagTree() {
	$.ajax({
		type : "POST",
		url : appPath+"/admin/dataing/tag/s",
		data : '[{"name":"sSearch","value":""}]',
		dataType : 'json',
		contentType : 'application/json',
		beforeSend: function(request) {//beforeSend
            request.setRequestHeader("token", token);
            request.setRequestHeader("refreshToken",refreshToken);
         },
		success : function(data) {
			if (data != null) {
				menuTreeCom($("#dataTagsTree"), data, true, bindTagClickEvent,"/admin/dataing/tag/");
				$('#add_g_db_btn').attr('disabled',"true");
			} else {
				$("#dataTagsTree").html("<h3 class='alert alert-info' >暂无全局分类</h3>");
			}
		}
	});
	bindTagsSearchByWords();
}

function bindTagClickEvent(id){
	if(/*If the id is greater than 129, customize the data label for the user.*/id>129){
		$('#add_g_db_btn').removeAttr("disabled"); 
		$("#add_g_db_btn").attr("href",appPath+"/admin/dataing/tag/"+id+"/new");
	}
}

/**
 * 全局数据分类关键词查询
 * */
function bindTagsSearchByWords() {
	$("#searchWordsButton").bind("click",function() {
		if ($("#searchWords").val()) {
			var sdata = '[{"name":"sSearch","value":"'+ $("#searchWords").val() + '"}]';
			$.ajax({
				type : "post",
				url : appPath+"/admin/dataing/tag/s",
				dataType : 'json',
				contentType : 'application/json',
				data : sdata,
				success : function(data) {
					if (data != null) {
						var searchdbSortU = "<h3 class='alert alert-info'> 查询结果: </h3>";
						menuTreeCom($("#dataTagsTree"),data, false, null,"../dataSort/");
						$("#dataTagsTree").prepend(searchdbSortU);
					} else {
						$("#dataTagsTree").html("<h3 class='alert alert-info' >无匹配的搜索结果！</h3>");
					}
				}
			});
		} else {
			$("#searchWords").focus();
		}
	});
	$("#searchWords").keyup(function(event) {
		if ($("#searchWords").val()) {
			if (event.keyCode == 13) {
				$("#searchWordsButton").trigger("click");
				$(this).blur();
			} else {
				return false;
			}
		}
	});
}

function loadEditAllTagTree() {
	if ($("#tagsTree")[0]) {
		$.ajax({
			type : "POST",
			url : appPath+"/admin/dataing/tag/custom/s",
			contentType : 'application/json',
			beforeSend: function(request) {//beforeSend
	            request.setRequestHeader("token", token);
	            request.setRequestHeader("refreshToken",refreshToken);
	         },
			success : function(data) {
				treeRadioCom($("#tagsTree .treeNew"), data);
				setTimeout("$('.treeSelId').click()", 800);
			}
		});
	}
}

/**
 * 表单验证
 * */
function validateTagForm() {
	$("#db_sort_new_form").validate({
		ignore : "",
		rules : {
			name : "required",
			code : "required",
			parentId : "required"
		},
		messages : {
			name : "请填写分类名称",
			code : "请填写分类编号",
			parentId : "请选择父级分类"
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