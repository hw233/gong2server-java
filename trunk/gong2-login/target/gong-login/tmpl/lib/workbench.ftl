<#macro html>
<!DOCTYPE html>
<html>
<#nested>
</html>
</#macro>

<#macro head>
  <head>
  	<title>我的宫廷-后台管理</title>
	<meta http-equiv="Content-type" content="text/html;charset=utf-8"/>
	<meta name="keywords" content="关键字"/>
	<meta name="description" content="网页描述"/>
	<meta name="author" content="hadoit"/>
	<meta name="version" content="1.0"/>
	<#include "/lib/corecss.ftl">
	<#nested>
  </head>
</#macro>

<#macro body>
	<body>
	<div id="wrapper">
	  <div id="header">
	  	<span style="position: absolute; right: 5px; top: 2px;">
		    <input type="button" onclick="location.replace('${basePath}/logout')" class="bigBtn" value="退出">
	    </span> 
	    	你好，<#if principal?has_content>${principal.userName?default("")}！</#if>欢迎登录 <strong>FSGAME - 汉动</strong> 管理后台 
	  </div>
	  <div id="memu">
	    <table height="100%" cellspacing="0" cellpadding="0" border="0" width="171">
	    <tbody><tr>
	      <td align="center" valign="top">
	        <table cellspacing="0" cellpadding="0" border="0" width="100%">
	          <tbody><tr>
	            <td height="10"></td>
	          </tr>
	        </tbody></table>
	        <table cellspacing="0" cellpadding="0" border="0" width="151">
	          <tbody><tr height="22">
	            <td align="left" class="menuPtd" style="padding-left: 30px;"><a onclick="expand(1);" href="javascript:void(0);" class="menuParent">管理后台</a></td>
	          </tr>
	          <tr height="4">
	            <td></td>
	          </tr>
	        </tbody></table>	
			<table cellspacing="0" cellpadding="0" border="0" width="150" id="child1" class="js_menu_child" style="display: none;">
	          <tbody>
	          <tr height="20">
	          	<td align="left"><a href="${basePath}/manager?module=server&amp;action=listsrv" class="menuChild" id="js_menu_1_1">查看所有服务器</a></td>
	          </tr>
	          <tr height="20">
	          	<td align="left"><a href="${basePath}/manager?module=register&amp;action=registerstate" class="menuChild" id="js_menu_1_2">开放关闭注册</a></td>
	          </tr>
	          <tr height="20">
	          	<td align="left"><a href="${basePath}/manager?module=notice&amp;action=addnotice" class="menuChild" id="js_menu_1_3">公告增加后台</a></td>
	          </tr>
	        </tbody></table>
	        </td></tr></tbody></table>
	  </div>
	  <#nested>
	</div>
	</body>
 </#macro>

<#macro workbench>
<@html>
<@head></@head>
<@body>
	<#nested/>
</@body>
</@html>
</#macro>

<#macro js>
<#include "/lib/corejs.ftl">
<#nested />
</#macro>

<#function slim str len>
	<#if str?length lt 10>
		<#return str?default("")>
	<#else>
		<#if len gt str?length>
			<#assign __len = str?length>
		<#else>
			<#assign __len = len>
		</#if>
		<#return (str[0..__len-1]?default("")) + "...">
	</#if>
</#function>