/**
 * The script for template page .
 * 
 * @author shaobo shih
 * @version 1.0
 */
$(document).ready(function() {
	loadPageTree();
	loadPageModelData();
	pageFormValidate();
});

//Load pages tree
function loadPageTree(){
	if ($("#pageTree").length > 0) {
		$.ajax({
			type : "POST",
			url : appPath + "/admin/templating/page/tree",
			beforeSend: function(request) {//beforeSend
                request.setRequestHeader("token", token);
                request.setRequestHeader("refreshToken",refreshToken);
             },
             contentType : 'application/json',
			success : function(data) {
				if (data.data != null) {
					menuTreeCom($("#pageTree"), data.data, true, bindPageNodeEvent);
				}
			}
		});
	}
}

//bind the page tree click event
function bindPageNodeEvent(nodeId) {
	if(nodeId>0){
		$("#addPage").attr("href",appPath+"/admin/templating/page/"+nodeId+"/new")
		$("#pages").hide();
		$('#page_content').show();
		//load page data
		var dataTitle=[{
			"mData" : "name",
			"fnRender" : function(obj) {
				return	'	<label class="checkbox inline mt0">' +
								'		<input type="checkbox" id="inlineCheckbox' + obj.aData.id + '" name="idStr' + obj.aData.id + '" value="' + obj.aData.id +'" style="opacity: 0;">'+
								'	</label>'+
								'<span>'+obj.aData.name+'</span>'+
								'<button title="页面配置" data-rel="tooltip" class="btn btn-mini editbtn padmbt floatr none"><i class="icon-edit"></i>配置</button>';
			}
		},{
			"mData" : "code"
		},{
			"mData" : "pageType",
			"fnRender" : function(obj){
				if(obj.aData.pageType=='Site'){
					return "站点";
				}else if(obj.aData.pageType=='Home'){
					return "首页";
				}else if(obj.aData.pageType=='Subject'){
					return "专题首页";
				}else if(obj.aData.pageType=='List'){
					return "列表页";
				}else{
					return "详情页";
				}
			}
		},{
			"mData" : "status",
			"fnRender" : function(obj){
				if(obj.aData.status){
					return "是";
				}else{
					return "否";
				}
			}
		},{
			"mData" : "pageStatus",
			"fnRender" : function(obj){
				if(obj.aData.pageStatus=='Unpublish'){
					return "未发布";
				}else if(obj.aData.pageStatus=='Publishing'){
					return "发布中";
				}else{
					return "已发布";
				}
			}
		},{
			"mData" : "publishTime",
			"fnRender" : function(obj){
				if(obj.aData.publishTime!=null&&obj.aData.publishTime!=''){
					return new Date(obj.aData.publishTime).format("yyyy-MM-dd hh:mm:ss");
				}else{
					return "";
				}
			}
		},{
			"mData" : "pageHtmlPath",
			"fnRender" : function(obj){
				if(obj.aData.pageHtmlPath!=null&&obj.aData.pageHtmlPath!=''){
					return "<a href='"+obj.aData.pageHtmlPath+"' target='_blank'>预览</a>";
				}else{
					return "";
				}
			}
		}];
		dataTablesCom($('#pageDatas'), "/admin/templating/page/search/" + nodeId,dataTitle, null, loadDataCallback, true,true,true);
	}
}

//page callback function
function loadDataCallback(otd) {
	docReady();
	trHoverEdit(showItemEdit);
	trHoverModi();
	indexPublish(otd);
}
function showItemEdit() {
	$(".trHoverEdit tr .editbtn").live("click", function() {
		var pageId = $(this).parent().find("input[type='checkbox']").val();
		window.location.href = "page/" + "config/" + pageId;
	})
}
function indexPublish(otd){
	$("#indexPublish").live("click",function(){
		if ($(this).parent().parent().nextAll(".dataTables_wrapper").find("tbody input[type='checkbox']").length > 0){
			var count = 0;
			var idsVal = new Array();
			$(this).parent().parent().nextAll(".dataTables_wrapper").find("tbody input[type='checkbox']").each(function() {
				if ($(this).attr("checked")&& $(this).val() != null&& $(this).val().length > 0) {
					idsVal.push($(this).val());
					count++;
				}
			});
			var sData = idsVal.join(",");
			if(count>0){
				$('#noticeModal').modal('show');
				$("#noticeModal").find('.btn-primary').click(function() {
					var btnPrimary = $(this);
					btnPrimary.attr("disabled", true).siblings(".loading").show();
					var spData = {name : "ids",value : sData};
					$.ajax({
						type : 'POST',
						contentType : "application/json",
						url : appPath + "/admin/templating/page/publish",
						data : JSON.stringify(spData),
						beforeSend: function(request) {//beforeSend
			                request.setRequestHeader("token", token);
			                request.setRequestHeader("refreshToken",refreshToken);
			             },
						success : function(resp) {
							btnPrimary.attr("disabled", false).siblings(".loading").hide();
							otd.fnDraw();
							setTimeout("isCheckboxStyle();",300);
							$('#noticeModal').modal('hide');
							noty({"text" : "发布成功","layout" : "center","type" : "alert","animateOpen" : {"opacity" : "show"}});
						},
						error : function(data) {
							btnPrimary.attr("disabled", false).siblings(".loading").hide();
							noty({"text" : "操作失败，请重试","layout" : "center","type" : "error"});
						}
					});
				});
			} else {
				noty({"text" : "请选择要发布的页面","layout" : "center","type" : "error"});
			}
		}
	});
}

//Load page model data
function loadPageModelData(){
	$("#pageType").die().on('change',function(){
		var val=$(this).val();
		$("#pageModelId").html();
		if(val!=null&&val!=''){
			$.ajax({
				type : "POST",
				url : appPath+"/admin/templating/page/model/"+val,
				dataType : 'json',
				contentType : 'application/json',
				beforeSend: function(request) {//beforeSend
	                request.setRequestHeader("token", token);
	                request.setRequestHeader("refreshToken",refreshToken);
	             },
				success : function(data) {
					if(data.code==200&&data.data!=null){
						var content="";
						for(var i=0;i<data.data.length;i++){
							content+="<option value='" + data.data[i].id + "'>" + data.data[i].modelName+ "</option>";
						}
						$("#pageModelId").html(content);
					}
				}
			});
		}
	});
}

//Page form prevalidate
function pageFormValidate() {
	$("#pageForm").validate({
		rules : {
			name : {required : true},
			code : {
				required : true,
				seleneExtLetter:true
			},
			pageType : {required : true},
			pageModelId : {required : true}
		},
		messages : {
			name : {required : "页面名称不能为空！"},
			code : {
				required : "页面编码不能为空！",
				seleneExtLetter:"页面编码只能输入字母，不区分大小写！"
			},
			pageType : {required : "页面类型不能为空！"},
			pageModelId : {required : "页面模板不能为空！"}
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
