/**
 * The script for template page config.
 * 
 * @author shaobo shih
 * @version 1.0
 */
//After the iframe loads the page, the binding page initializes the event.
function iframeFix(obj) {
	var hbody = $(iFramePageConf.document).find("#container").height();
	if(null==hbody||hbody==''||typeof(hbody) == "undefined"){
		//default height 1000px
		hbody=1000;
	}
	obj.height = hbody;
	var bodyObj = $(iFramePageConf.document).find("body");
	PageConfBuind(bodyObj);
}

// Displays the "ecode" button for the configurable area
function PageConfBuind(bodyObj) {
	bodyObj.find("[ecode]").hover(function() {
		var tWidth = $(this).outerWidth(), theight = $(this).outerHeight() - 1;
		if ($(this).find(".editable_btn").length < 1) {
			$(this).css({"position" : "relative"}).append('<div class="editable_warp" style="width:'+ tWidth+ 'px;height:'+ theight+ 'px;background:#fff;opacity:0.8;left:0;top:0;position: absolute;z-index:100"></div><a class="editable_btn" style="display:inline-block;padding:3px 10px; border:1px solid #ccc;border-radius:4px; position:absolute;right:10px;top:5px;z-index:101;background:#f6f6f6;line-height: 18px;font-size: 12px;font-weight: normal;color: #303030;width: auto;" href="#"><i class="icon-edit"></i> 配置</a>');
		} else {
			$(this).css({"position" : "relative"}).find(".editable_btn,.editable_warp").show();
		}
	},function() {
		if ($(this).find(".editable_btn").length > 0) {
			$(this).find(".editable_btn,.editable_warp").hide();
		}
	});

	$('#areaConfModal').on('show', function() {});
	$('#areaConfModal').on('hidden', function() {
		// Clean content
		$("#itemContentDb input").each(function() {
			$(this).val("");
		});
		$("#itemContentDb textarea").each(function() {
			$(this).val("");
		});
	});
	
	// "ecode" button for the configurable area,load click event
	bodyObj.find("[ecode]").find(".editable_btn").live("click",function(e) {
		stopDefault(e);
		$('#areaConfModal').modal('show');
		$("#itemCode").val($(this).parents("[ecode]").attr("ecode"));
		// validate form
		itemFormValidate();
		//Load initialization data
		$.ajax({
			url : appPath + "/admin/templating/page/config/" + $("#pageId").val() + "/"+ $("#itemCode").val(),
			cache:false,
			beforeSend: function(request) {//beforeSend
	            request.setRequestHeader("token", token);
	            request.setRequestHeader("refreshToken",refreshToken);
	         },
			success : function(data) {
				if(data.code==200){
					if(null != data.key){
						$("#id").val(data.key.id);
						$("#contentName").val(data.key.contentName);
						$("#contentSummary").val(data.key.contentSummary);
						$("#filterDir").val(data.key.filterDir);
						var filterDirValue=data.key.filterDir;
						//Load filter type
						if(filterDirValue!=null&&filterDirValue!=''){
							$.ajax({
								url : appPath + "/admin/templating/page/config/filter/"+ filterDirValue,
								cache:false,
								beforeSend: function(request) {//beforeSend
						            request.setRequestHeader("token", token);
						            request.setRequestHeader("refreshToken",refreshToken);
						         },
								success : function(data) {
									if(data.code==200&&data.total>0){
										var optionContent="";
										for (var i = 0; i < data.data.length; i++){
											optionContent +='<option value ="'+data.data[i].id+'">'+data.data[i].name+'</option>';
										}
										$("#filterType").html(optionContent);
									}
								}
							});
						}
						$("#filterType").val(data.key.filterType);
						$("#filterValue").val(data.key.filterValue);
						$(".treeSelId").val(data.key.baseId);
					}else{
						$("#contentName").val("");
						$("#contentSummary").val("");
						$("#filterDir").val("");
						var filterDirValue="Basic";
						$.ajax({
							url : appPath + "/admin/templating/page/config/filter/"+ filterDirValue,
							cache:false,
							beforeSend: function(request) {//beforeSend
					            request.setRequestHeader("token", token);
					            request.setRequestHeader("refreshToken",refreshToken);
					         },
							success : function(data) {
								if(data.code==200&&data.total>0){
									var optionContent="";
									for (var i = 0; i < data.data.length; i++){
										optionContent +='<option value ="'+data.data[i].id+'">'+data.data[i].name+'</option>';
									}
									$("#filterType").html(optionContent);
								}
							}
						});
						$("#filterType").val("");
						$("#filterValue").val("");
						$(".treeSelId").val("");
					}
					//Bind (filterDir) change event
					$("#filterDir").on("change",function(){
						$.ajax({
							url : appPath + "/admin/templating/page/config/filter/"+ $("#filterDir").val(),
							cache:false,
							beforeSend: function(request) {//beforeSend
					            request.setRequestHeader("token", token);
					            request.setRequestHeader("refreshToken",refreshToken);
					         },
							success : function(data) {
								if(data.code==200&&data.total>0){
									var optionContent="";
									for (var i = 0; i < data.data.length; i++){
										optionContent +='<option value ="'+data.data[i].id+'">'+data.data[i].name+'</option>';
									}
									$("#filterType").html(optionContent);
								}
							}
						});
					})
					//Other event
					filterSwitch();
					treeRadioCom($("#itemContentSrc .treeNew"), data.value.children,true);
					setTimeout("$('.treeSelId').click()", 800);
				}
			}
		});
	});

	$("#btn_save").click(function() {
		$("#viewItemForm").submit();
	});
}

//Loading data condition
function filterSwitch() {
	if ($("#filterCondition").val() != "") {
		$("#filter_witch").attr("checked", true);
		$("#filter_witch").parent().attr("class", "checked");
	}
	$("#filter_witch").change();
}
$("#filter_witch").change(function() {
	if($("#filter_witch").attr("checked")){
		$("#filter_dir").show();
		$("#filter_type").show();
		$("#filter_value").show();
		$("#switch_value").val("Normal");
	}else{
		$("#filter_dir").hide();
		$("#filter_type").hide();
		$("#filter_value").hide();
		$("#switch_value").val("Stop");
	}
});

// Form check
function itemFormValidate() {
	$("#viewItemForm").validate({
		ignore : [],
		rules : {
			baseId : {required : true},
			contentName:{required : true}
		},
		messages : {
			baseId : {required : "请选择配置区域数据来源！"},
			contentName:{required : "区域配置标题不能为空！"}
		},
		errorPlacement : function(error, element) {
			error.insertAfter(element);
		},
		onkeyup : false
	});
}