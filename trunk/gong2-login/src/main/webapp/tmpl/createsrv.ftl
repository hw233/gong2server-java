<#include "/lib/workbench.ftl"/>
<#include "/lib/pageNav.ftl">

<#escape x as x?html>
<@workbench>
<div id="mContent">
  	    <div class="title"><span class="buttons">
            </span>
      添加服务器    </div>
    <div class="tableWrapper">
  <form action="?module=server&action=docreatesrv" id="mainForm"  method="post">
    <table class="grid js_GRIDVIEW_TABLE_0">
      <input type="hidden"  value=${si.id} name="data[id]" >
      <tbody>
      <tr bgcolor="#efefef">
        <td class="label">服务器ID：</td>
        <td><input  name="data[serverId]" class="required number" value="<#if si.serverId==0 ><#else>${si.serverId}</#if>" maxlength="6" size="6">
        </td>
      </tr>
	  <tr bgcolor="#ffffff">
        <td class="label">区号：</td>
        <td><input  name="data[zone]" class="required number" value="<#if si.zone==0 ><#else>${si.zone}</#if>" maxlength="6" size="6">
        </td>
      </tr>
	  <tr bgcolor="#efefef">
        <td class="label">名称：</td>
        <td><input  name="data[serverName]" class="required" value="${si.serverName!('')}" maxlength="50" size="50">
        </td>
      </tr>
	  <tr bgcolor="#ffffff">
        <td class="label">渠道组：</td>
        <td><input  name="data[oprGroup]" class="required" value="${si.oprGroup!('')}" maxlength="20" size="20">
        </td>
      </tr>
      <tr bgcolor="#ffffff">
        <td class="label">渠道标示(渠道组带的标示, 以半角逗号分隔, 不是混服跟渠道组一致即可)：</td>
        <td><input  name="data[oprMatchStr]" class="required" value="${si.oprMatchStr!('')}" maxlength="10000" size="100">
        </td>
      </tr>
	  <tr bgcolor="#efefef">
        <td class="label">地址：</td>
        <td><input name="data[ip1]" class="required number" value="" maxlength="3" size="3">.
        	<input name="data[ip2]" class="required number" value="" maxlength="3" size="3">.
        	<input name="data[ip3]" class="required number" value="" maxlength="3" size="3">.
        	<input name="data[ip4]" class="required number" value="" maxlength="3" size="3">
        	<input type="hidden"  value="${si.ip!('')}" name="data[ip]" >
        </td>
      </tr>
	  <tr bgcolor="#ffffff">
        <td class="label">端口：</td>
        <td><input name="data[port]" class="required" value="${si.port!('')}" maxlength="5" size="5">
        </td>
      </tr>
      <tr bgcolor="#ffffff">
        <td class="label">管理地址：</td>
        <td><input name="data[adminIp1]" class="required number" value="" maxlength="3" size="3">.
        	<input name="data[adminIp2]" class="required number" value="" maxlength="3" size="3">.
        	<input name="data[adminIp3]" class="required number" value="" maxlength="3" size="3">.
        	<input name="data[adminIp4]" class="required number" value="" maxlength="3" size="3">
        	<input type="hidden"  value="${si.adminIp!('')}" name="data[adminIp]" >
        </td>
      </tr>
	  <tr bgcolor="#ffffff">
        <td class="label">管理端口：</td>
        <td><input name="data[adminPort]" class="required" value="${si.adminPort!('')}" maxlength="5" size="5">
        </td>
      </tr>
	  <tr bgcolor="#efefef">
        <td class="label">是否新服：</td>
        <td><select name="data[newSrv]"><option <#if si.newSrv> selected </#if> value="true">是</option><option  <#if !si.newSrv> selected </#if> value="false">否</option></select></td>
      </tr>
      <tr bgcolor="#efefef">
        <td class="label">关服提示：</td>
        <td><input  name="data[closedDesc]" value="${si.closedDesc!('')}" maxlength="200" size="50">
        </td>
      </tr>
      <tr bgcolor="#ffffff">
        <td class="label"></td>
        <td><input type="button" value="提交" class="bigBtn" id="addsrvbtn" name="addsrvbtn">
          <input type="button" onclick="location.replace('?module=server&amp;action=listsrv');" value="返回" class="bigBtn"></td>
      </tr>
    </tbody></table>
  </form>
</div>
  </div>
  <div id="colorPickerDiv"></div>
<@js>
<script type="text/javascript" src="${jsRoot}/ColorPicker.js" charset="gbk"></script> 
<script type="text/javascript" src="${jsRoot}/manage/listsrv.js" charset="gbk"></script> 
<script type="text/javascript">
$(document).ready(function(){$.m.listsrv.initcreate();});
</script>
</@js>
</@workbench>
</#escape>