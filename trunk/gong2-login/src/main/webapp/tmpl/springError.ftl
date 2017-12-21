<#include "/lib/workbench.ftl"/>

<#escape x as x?html>
<@workbench>
<body>
<div id="wrapper">
  <div id="header">
    	 管理后台 
  </div>  
  <div id="memu">
  </div>
  <div id="mContent">
  	<div class="title"><span class="buttons"></span>错误信息 </div>
    <div class="tableWrapper">
        <table class="grid">
            <tr>
			    <td>摘要</td>
            </tr>
            <tr bgcolor="#ffffff"><td height="100">
                  <div style="width: 90%; margin: 50px auto; font-size: 14px;">
                     ${exception}         
                  </div>
            </td></tr>
            <tr><td>${.now?string("yyyy-MM-dd HH:mm:ss")}</td></tr>
        </table>
    </div>
</div>
</div>
<@js />
</@workbench>
</#escape>