//<!--
//(function() {
//	
//	var _this = $.namespace('$.m.listsrv');
//	var _module = 'server';
//	
//	$.m.listsrv.init = function(){
//		$("#checkAll").click(_this.checkall); 
//		$("#crt_srv").click(_this.createsrv)
//	}
//	
//	$.m.listsrv.initcreate = function(){
//		var ipVal = $("input[name='data[ip]']").val();
//		if (ipVal!="") {
//			var ips = ipVal.split(".");
//			if(ips.length==4){
//				$("input[name='data[ip1]']").val(ips[0]);
//				$("input[name='data[ip2]']").val(ips[1]);
//				$("input[name='data[ip3]']").val(ips[2]);
//				$("input[name='data[ip4]']").val(ips[3]);
//			}
//		}
//		
//		var adminIpVal = $("input[name='data[adminIp]']").val();
//		if (adminIpVal!="") {
//			var ips2 = adminIpVal.split(".");
//			if(ips2.length==4){
//				$("input[name='data[adminIp1]']").val(ips2[0]);
//				$("input[name='data[adminIp2]']").val(ips2[1]);
//				$("input[name='data[adminIp3]']").val(ips2[2]);
//				$("input[name='data[adminIp4]']").val(ips2[3]);
//			}
//		}
//		
//		$("#addsrvbtn").click(_this.docreatesrv);
//	}
//	
//	$.m.listsrv.createsrv = function(event){
//		location.href= _this.formaturl("createsrv");
//	}
//	
//	$.m.listsrv.checkall = function(){
//		if ($(this).attr("checked") == true) { // 全选
//			   $("input[type=checkbox]").each(function() {
//				   $(this).attr("checked", true);
//			   });
//			} else { // 取消全选
//			   $("input[type=checkbox]").each(function() {
//				   $(this).attr("checked", false);
//			   });
//			}
//	};
//	
//	$.m.listsrv.getcheckids = function(){
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
//	$.m.listsrv.docreatesrv = function(){
//		$('#mainForm').validate();
//
//		var ip1 = $("input[name='data[ip1]']").val();
//		var ip2 = $("input[name='data[ip2]']").val();
//		var ip3 = $("input[name='data[ip3]']").val();
//		var ip4 = $("input[name='data[ip4]']").val();
//		$("input[name='data[ip]']").val(ip1+"."+ip2+"."+ip3+"."+ip4);
//		$("input[name='data[adminIp]']").val( $("input[name='data[adminIp1]']").val()+"."
//				+$("input[name='data[adminIp2]']").val()+"."
//				+$("input[name='data[adminIp3]']").val()+"."+$("input[name='data[adminIp4]']").val());
//  		$('#mainForm').submit();
//  		
// 		return true;
//	}
//	
//	$.m.listsrv.formaturl = function(action) {
//     	return $.m.base.formaturl(_module,action);
//	}
//})()
//--> 				        