/**
 * The script for searching .
 * 
 * @author shaobo shih
 * @version 1.0
 */
$(document).ready(function() {
	loadLibraryTree();
	quickSearch();
	advanceSearch();
	fieldBindEvent();
	searchFieldAdd();
	logicSelectField();
	//load search data
	loadSearchData();
});

//Load library tree
function loadLibraryTree() {
	if($(".tree_sel_box").length > 0){
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
					if($("#multiExp").length > 0){
						treeSleCom($(".tree_sel_box .treeNew"), data.data,bindDataField);
					}else{
						treeSleCom($(".tree_sel_box .treeNew"), data.data);
					}
					setTimeout("$('.tree_sel_box .treeSelId').click()",500);
				}
			}
		});
	}
}

//Bind database event 
function bindDataField(ids){
	if(/*Search common fields*/ids!=null&&ids!=''){
		$("#dataFields").find("h3").remove();
		var baseIds = {"name" : "range","value" : ids};
		$.ajax({
			type : "POST",
			url : appPath + "/admin/searching/field",
			beforeSend: function(request) {//beforeSend
	            request.setRequestHeader("token", token);
	            request.setRequestHeader("refreshToken",refreshToken);
	         },
			contentType : "application/json",
			dataType : "JSON",
			data : JSON.stringify(baseIds),
			success : function(data) {
				var options = "";
				if (data.data) {
					for ( var i = 0; i < data.data.length; i++) {
						options += "<option value='" + data.data[i].code + "' type='"+ data.data[i].dataType + "' >" + data.data[i].name+ "</option>"
					}
					$("#dbFields").html(options);
				}
			}
		});
	}else {
		$("#dataFields").html("<h3 class='span12'><div class='alert span6'>请选择查询范围并添加检索字段</div></h3>")
		$("#dbFields option").remove();
	}
}

//Bind add field event
function fieldBindEvent() {
	$("#addShowDatafields").click(function() {
		if (!$("#searchConIdStr").val()) {
			$("#treeSelShow").click();
			return false;
		}
		$(".showDataFields").show();
	});
	$("#dataFields .remove").live("click", function() {
		$(this).parentsUntil("#dataFields").remove();
	})
}

//Add search field
function searchFieldAdd(){
	$("#addDataField").live("click",function() {
		var fieldType = /*Field type*/$("#dbFields option:selected").attr("type");
		var fieldName = /*Field name*/$("#dbFields option:selected").text();
		var fieldValue = /*Field value*/$("#dbFields option:selected").val();
		var fieldContent='';
		if(/*date field*/fieldType=="DateTime"||fieldType=="Date"||fieldType=="Time"){
			fieldContent+='<div class="control-group">';
			fieldContent+='		<label class="control-label" >'+fieldName+'：</label>';
			fieldContent+='		<div class="controls">';
			fieldContent+='			<div class="inline btn_group_as floatl" data-toggle="buttons-radio">';
			fieldContent+='				<a class="btn mr4 active" value="And">必含</a>';
			fieldContent+='				<a class="btn mr4" value="Or">选含</a>';
			fieldContent+='				<a class="btn mr4" value="Not">排除</a>';
			fieldContent+='				<input type="hidden" name="'+ fieldValue+ '_logic" value="And" />';
			fieldContent+='			</div>';
			fieldContent+='			<span class="floatl">';
			fieldContent+='				<div class="inline btn_group_as blue" data-toggle="buttons-radio">';
			fieldContent+='					<a class="btn mr4" value="today">本日</a>';
			fieldContent+='					<a class="btn mr4" value="month" >本月</a>';
			fieldContent+='					<a class="btn mr4 active" value="date_time">时间段</a>';
			fieldContent+='					<input type="hidden" name="'+ fieldValue+ '" value="date_time" />';
			fieldContent+='				</div>';
			fieldContent+='				<input class="input-medium timepicker mr4" type="text" name="'+ fieldValue+ '_start" />';
			fieldContent+='				<span class="mr4">——</span>';
			fieldContent+='				<input class="input-medium timepicker mr4"  type="text" name="'+ fieldValue+ '_end" />';
			fieldContent+='				<span class="btn remove mr4"><i class="icon-trash icon-color"></i></span>';
			fieldContent+='			</span>';
			fieldContent+='		</div>';
			fieldContent+='	</div>';
			$("#dataFields").append(fieldContent);
			timepickerReady();
		}else /*Not date field*/{
			fieldContent+='<div class="control-group">';
			fieldContent+='		<label class="control-label" >'+ fieldName+ '：</label>';
			fieldContent+='		<div class="controls">';
			fieldContent+='			<div class="inline btn_group_as" data-toggle="buttons-radio">';
			fieldContent+='				<a class="btn mr4 active" value="And">必含</a>';
			fieldContent+='				<a class="btn mr4" value="Or">选含</a>';
			fieldContent+='				<a class="btn mr4" value="Not">排除</a>';
			fieldContent+='				<input type="hidden" name="'+ fieldValue+ '_logic" value="And" />';
			fieldContent+='			</div>';
			fieldContent+='			<input class="input-xlarge mr4" type="text" name="'+ fieldValue+ '" />';
			fieldContent+='			<span class="btn remove mr4"><i class="icon-trash icon-color"></i></span>';
			fieldContent+='		</div>';
			fieldContent+='	</div>';
			$("#dataFields").append(fieldContent);
		}
		$(this).parents(".showDataFields").hide();
	});
}

//Add select event
function logicSelectField() {
	$(".btn_group_as a").live("click",function() {
		var curtVal = $(this).attr("value")
		$(this).parents(".btn_group_as").find("input[type='hidden']").val(curtVal)
	})
}

//Search quick check
function quickSearch() {
	$("#quickBtn").click(function() {
		if ($(".treeSelId").val() == null || $(".treeSelId").val() == "") {
			noty({
				"text" : "请先选择数据库！",
				"layout" : "center",
				"type" : "error",
				"animateOpen" : {"opacity" : "show"}
			});
			$("#treeSelShow").click();
			return false;
		}
		window.location.href = /*Type[0-quick search]*/
			appPath+ "/admin/searching?type=0&term="+$("#searchField").val()+"&formula="+
			encodeURI(encodeURI($("#queryString").val()))+"&range=" + $(".treeSelId").val();
	});
	
	$("#queryString").keyup(function(event) {
		if (event.keyCode == 13) {
			$("#quickBtn").trigger("click");
			$(this).blur();
		} else {
			return false;
		}
	});
}

//Search advanced check
function advanceSearch(){
	$("#advanceBtn").click(function() {
		if ($("#searchConIdStr").val() == null || $("#searchConIdStr").val() == "") {
			noty({
				"text" : "请先选择数据库！",
				"layout" : "center",
				"type" : "error",
				"animateOpen" : {"opacity" : "show"}
			});
			$("#treeSelShow").click();
			return false;
		}
		$("#advanceSearchForm").submit();
	});
}

//Load search data
function loadSearchData(){
	var tableHeader = [{
	        	"mData" : "title",
	        	"fnRender" : function(obj) {
					var result='';
					 		result += '<h3>'; 
							result += '	<a class="edit_pop_link" href="'+ appPath+ '/admin/dataing/library/data/info/'+ obj.aData.tableId+ '/'+ obj.aData.id+ '">'+obj.aData.title+'</a>';
							result += '	<a  class="padmbt btn floatr none data_title edit_pop_link" href="'+ appPath+ '/admin/dataing/library/data/info/'+ obj.aData.tableId+ '/'+ obj.aData.id+ '">';
							result += '		<i class="icon-eye-open" title = "文章预览"></i>';
							result += '	</a>';
							result += '</h3>';
							result += '<p class="summary clearfix" >'+ obj.aData.summary+ '</p>';
					return result;
	        	}
	        },
	        {"mData" : "docTime"},
	        {"mData" : "authors"}
	  ];
	dataTablesCom($('#dataTable'), appPath+"/admin/searching/s?type=" + $("#type").val() + "&term="+$("#term").val()+"&searchToken="+ $("#searchToken").val() 
			+ "&range=" + $("#range").val(), tableHeader, null,callbackSearchEvent, false, false, true);
}

//callback function
function callbackSearchEvent(oTableDataDb) {
	docReady();
	trHoverEdit();
	editPopWithDT(oTableDataDb);
};