//<!--
//(function() {
//	
//	var _this = $.namespace('$.m.compensation');
//	var _module = 'compensation';
//	
//	$.m.compensation.listinit = function(){
//		$(".delcompensation").click(_this.delcompensation); 
//   	}
//	
//	$.m.compensation.delcompensation = function(event){
//		if (confirm("请再次确认")) {
//	 		var url = _this.formaturl('del');
//	 		url = url + "&id=" + $(event.target).attr("value");
//	  		$('#mainForm').attr("action",url);
//	 		$('#mainForm').submit();
//		}
//  	}
// 
//	$.m.compensation.getcheckids = function(){
//		var ids = new Array();
// 		$("input[type=checkbox]").each(function() {
//			if ($(this).attr("checked")==true){
//				if ($(this).val()!="")
//					ids.push($(this).val());
//  			}	
//		});
//		return ids;
//	};
//	
//	$.m.compensation.change = function(type){
// 		switch (parseInt(type)) {
//			case 1:
// 				_this.batchdel();
//				break;
//			default :
//				break;
//		}
//	};
//	
//	//批量操作
//	$.m.compensation.batchdel = function(){
//		var ids = _this.getcheckids();
// 		if (ids.length==0){
//			var content = "请至少选择一项"
//			alert(content);
//			return;
//		} 
//		var url = _this.formaturl('del') + "&id=" + ids;
//		if (confirm("请再次确认"))  {
//	 		$('#mainForm').attr("action",url);
//	 		$('#mainForm').submit();
//		}
//	};
//	
//	$.m.compensation.formaturl = function(action) {
// 		return $.m.base.formaturl(_module,action);
//	}
//	
//})()
//--> 				        