/**
 * The script for template page .
 * 
 * @author shaobo shih
 * @version 1.0
 */
$(document).ready(function() {
	loadPageTree();
	//init_load_page_list(); 
	//page_form_validate();
	//load_page_model("Index");
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
		$('#page_content').show();
		//load page data
		var dataTitle=[{
			"mData" : "name",
			"fnRender" : function(obj) {
				return	'<h3>' + 
								'	<label class="checkbox inline mt0">' +
								'		<input type="checkbox" id="inlineCheckbox' + obj.aData.id + '" name="idStr' + obj.aData.id + '" value="' + obj.aData.id +'" style="opacity: 0;">'+
								'	</label>'+
								'	'+obj.aData.name+
								'</h3>';
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
		dataTablesCom($('#pageDatas'), "/admin/templating/page/search/" + libId,dataTitle, null, loadDataCallback, true,false,true);
	}
}

//page callback function
function loadDataCallback(otd) {
	docReady();
	trHoverEdit();
}

//Load page model data
function loadPageModelData(){
	$("#pageType").die().on('change',function(){
		var val=$(this).val();
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
						$("#pageModelId").append(content);
					}
				}
			});
		}
	});
}

