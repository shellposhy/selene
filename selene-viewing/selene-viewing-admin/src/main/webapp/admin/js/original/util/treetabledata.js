$(document).ready(function() {
	intTreeTable();
});

//设置树状表格
function intTreeTable() {
	if ((".tree_table").length > 0) {
		var cboxs = $(".tree_table tbody td input[type='checkbox']");
		$(".tree_table").treetable({
			expandable : true,
			onInitialized : function() {},
			onNodeInitialized : function() {	}
		});
		$(".tree_table tbody").on("mousedown", "tr", function() {
			$(".selected").not(this).removeClass("selected");
			$(this).addClass("selected");
		});
		cboxs.click(function() {
			var thisTr = $(this).parents("tr");
			var thisId = thisTr.attr("data-tt-id");
			var index = thisTr.find("td").index($(this).parents("td"))
			var inputArry = new Array();
			treeTableTran($(this), inputArry)
			if ($(this).attr("checked")) {
				$(inputArry).each(function() {
					$(this).attr("checked", "checked").parent("span").addClass("checked");
				})
			} else {
				$(inputArry).each(function() {
					$(this).attr("checked", false).parent("span").removeClass("checked");
				})
			}
			treeTableParents($(this))
		})
	}
}

function treeTableParents(obj) {
	var thisTr = obj.parents("tr");
	var thisPId = thisTr.attr("data-tt-parent-id");
	var index = thisTr.find("td").index(obj.parents("td"));
	var parent = thisTr.siblings("[data-tt-id='" + thisPId + "']").find("td:eq(" + index + ") input[type='checkbox']");
	var unCheckLen = thisTr.parent().children("[data-tt-parent-id='" + thisPId + "']").find("td:eq(" + index + ") input[type='checkbox']").not(":checked").length;
	unCheckLen > 0 ? parent.attr("checked", false).parent("span").removeClass("checked") : parent.attr("checked", "checked").parent("span").addClass("checked");
	if (parent.parents("tr").attr("data-tt-parent-id")) {
		treeTableParents(parent)
	}
}

function treeTableTran(obj, nodes) {
	if (obj.length > 0) {
		var thisTr = obj.parents("tr");
		var thisId = thisTr.attr("data-tt-id");
		var index = thisTr.find("td").index(obj.parents("td"));
		var children_tr = thisTr.siblings("[data-tt-parent-id='" + thisId+ "']");
		var children_input = children_tr.find("td:eq(" + index+ ") input[type='checkbox']");
		if (children_input.length > 0) {
			for (var i = 0; i < children_input.length; i++) {
				nodes.push(children_input.eq(i)[0]);
				if (children_input.eq(i).parents("tr").siblings("[data-tt-parent-id='"+ children_input.eq(i).parents("tr").attr("data-tt-id") + "']") != null&& children_input.eq(i).parents("tr").siblings("[data-tt-parent-id='"+ children_input.eq(i).parents("tr").attr("data-tt-id") + "']").length > 0) {
					treeTableTran(children_input.eq(i), nodes);
				}
			}
		}
	}
	return nodes;
}