<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<body>
<div id="content" class="span12">
	<input type="hidden" id="baseId" value="${baseId}" />
	 <input type="hidden" id="fieldsStr" value="${fieldsStr}" />
	<div class="row-fluid">
		<form:form modelAttribute="dataVo" class="form-horizontal span12" id="Article_new_form"
			action="${appPath}/admin/dataing/library/data/save" method="post" target="_self">
			<form:hidden path="id" />
			<form:hidden path="tableId"/>
			<form:hidden path="baseId"/>
			<form:hidden path="uuid" />
			<form:hidden path="createTime" />
			<form:hidden path="fieldsString"/>
			<fieldset>
				<legend>
					<span title="" class="icon32 icon-blue icon-note  floatl"></span>添加稿件
				</legend>
					<div class="postbox_left">
						<div class="control-group">
							<label class="control-label" for="Title">稿件标题</label>
							<div class="controls">
								<form:input path="fieldMap[Title]"  class="span12 typeahead" id="Title" name="Title" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Authors">稿件作者</label>
							<div class="controls">
								<form:input path="fieldMap[Authors]"  class="wds40 typeahead" id="Authors" name="Authors" />
								<span class="inline_label">稿件日期</span>
								<form:input path="fieldMap[Doc_Time]"  class="wds40 typeahead timepicker" id="Doc_Time" name="Doc_Time" placeholder="请选择稿件日期" readonly="true" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Start_Time">生效日期</label>
							<div class="controls ">
								<form:input path="fieldMap[Start_Time]" class="wds40 datepicker" id="Start_Time" name="Start_Time" />
								<span class="inline_label">失效日期</span>
								<form:input path="fieldMap[End_Time]" class="wds40 datepicker" id="End_Time" name="End_Time" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Intro_Title">肩标题</label>
							<div class="controls">
								<form:input path="fieldMap[Intro_Title]"  class="wds40 typeahead" id="Intro_Title" name="Intro_Title" />
								<span class="inline_label">副标题</span>
								<form:input path="fieldMap[Sub_Title]"   class="wds40 typeahead" id="Sub_Title" name="Sub_Title" />
							</div>
						</div>
						<div class="control-group" id=sort_tree>
							<label class="control-label" for="Sort_Ids">数据标签</label>
							<div class="controls ">
								<input class="treeSel " type="text" readonly value="" placeholder="数据标签最好选择到子节点"/>
								<form:hidden path="fieldMap[Sort_Ids]" id="Sort_Ids" name="Sort_Ids" class="treeSelId" />
								 <a class="menuBtn btn" href="#">选择</a>
							</div>
							<div class="menuContent">
								<ul id="treeSel_1" class="ztree treeNew"></ul>
								<a class="btn sel_all ml10">全选</a> <a class="btn sel_none">全不选</a>
								<a class="selOk btn btn-small" href="#"><i class="icon-ok"></i>确定</a>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Page_Num">稿件版次</label>
							<div class="controls ">
								<form:input path="fieldMap[Page_Num]" class="typeahead spinner" id="Page_Num" name="Page_Num"  min="1" step="1"/>
								<span class="inline_label">稿件版名</span>
								<form:input path="fieldMap[Page_Name]" class="wds40 typeahead" id="Page_Name" name="Page_Name"/>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Channel_Name">频道名称</label>
							<div class="controls">
								<form:input path="fieldMap[Channel_Name]" class="wds40 typeahead"  id="Channel_Name" name="Channel_Name" />
								<span class="inline_label">稿件栏目</span>
								<form:input path="fieldMap[Colum]" class="wds40 typeahead"  id="Colum" name="Colum" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Keywords">关键词</label>
							<div class="controls ">
								<form:textarea path="fieldMap[Keywords]" class="span12 typeahead" id="Keywords" name="Keywords"  rows="3"/>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Subject">稿件专题</label>
							<div class="controls">
								<form:input path="fieldMap[Subject]" class="wds40 typeahead"  id="Subject" name="Subject" />
								<span class="inline_label">专题分类</span>
								<form:input path="fieldMap[Subject_Class]" class="wds40 typeahead"  id="Subject_Class" name="Subject_Class" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Summary">稿件摘要</label>
							<div class="controls ">
								<form:textarea path="fieldMap[Summary]" class="span12 typeahead" id="Summary" name="Summary"  rows="5"/>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Gen_Issue">总期号</label>
							<div class="controls ">
								<form:input path="fieldMap[Gen_Issue]" class="typeahead spinner" id="Gen_Issue" name="Gen_Issue" />
								<span class="inline_label">期号</span>
								<form:input path="fieldMap[Issue]" class="typeahead spinner" id="Issue" name="Issue" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Source">稿件来源</label>
							<div class="controls ">
								<form:input path="fieldMap[Source]" class="span12 typeahead" id="Source" name="Source" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Style">稿件样式</label>
							<div class="controls ">
								<form:textarea path="fieldMap[Style]" class="span12 typeahead" id="Style" name="Style"  rows="3"/>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Places">稿件地区</label>
							<div class="controls ">
								<form:input path="fieldMap[Places]" class="span12 typeahead" id="Places" name="Places" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Peoples">稿件人物</label>
							<div class="controls ">
								<form:input path="fieldMap[Peoples]" class="span12 typeahead" id="Peoples" name="Peoples" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="Orgs">稿件机构</label>
							<div class="controls ">
								<form:input path="fieldMap[Orgs]" class="span12 typeahead" id="Orgs" name="Orgs" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="editor1">稿件正文</label>
							<div class="controls pr">
								<form:textarea path="fieldMap[Content]" class="areaEditor" id="contentArea" />
							</div>
						</div>
					</div>
					<div class="postbox_right">
						<div class="control-group">
							<label class="control-label" for="Attach">稿件附件</label>
							<div class="controls ">
								<form:input path="fieldMap[Attach]" name="Attach" id="Attach" />
								<p><button type="button" class="btn" onclick="javascript:$('#Attach').uploadify('upload','*')">上传</button></p>
								<c:if test="${!empty attachList}">
									<c:if test="${fn:length(attachList) > 0 }">
										<c:forEach var="fl" items="${attachList }">
											<p><span>${fl.fileName }</span>&nbsp;<a href="javascript:deleteFile('${fl.fileName}')">删除</a></p>
										</c:forEach>
									</c:if>
								</c:if>
							</div>
						</div>
				</div>	
				<div class="form-actions">
					<button type="submit" id="Article_sub" class="btn btn-primary">保存</button>
					<form:button name="cancel" value="取消" class="btn backBtn">取消</form:button>
				</div>
			</fieldset>
		</form:form>
	</div>
</div>
<script type="text/javascript">
	$(function() {
		if ($('#fieldsStr').val().indexOf('Attach') == -1)
			return;
		$('#Attach').uploadify({
			'height' : 20,
			'width' : 80,
			'fileTypeDesc':'Doc Files',
	        'fileTypeExts': '*.pdf;*.ppt;*.pptx;*.pptx;*.doc;*.docx;*.xlsx;*.xls;*.rar;*.rvt;*.dwg;*.rfa;*.nwd',
			'removeCompleted' : false,
			'auto' : false,
			'buttonText' : '选择文件...',
			'swf' : '${appPath}/admin/js/uploadify.swf',
			'uploader' : '${appPath}/admin/upload/file?baseId=${dataVo.baseId}&uuid=${dataVo.uuid}&dateTime=${dataVo.createTime}',
			'cancelImg' : '${appPath}/admin/img/uploadify-cancel.png',
		});
	});
</script>
<script type="text/javascript" 	src="${appPath}/admin/jscript/dataing/library.js"></script>
</body>
</html>