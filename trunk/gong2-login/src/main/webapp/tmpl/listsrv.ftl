<#include "/lib/workbench.ftl"/>
<#include "/lib/pageNav.ftl">

<#escape x as x?html>
<@workbench>
<div id="mContent">
        <div class="title"><span class="buttons"> </span> 服务器列表    </div>
    <form id="mainForm" method="post">
     <input type="hidden" id="currPage" value="${page.curPage!(1)}"  />
    <div class="searchPanel">
        共 <span style="color:#f30; font:bold 14px/20px georgia;">${page.total}</span> 服&nbsp;&nbsp;
        &nbsp;&nbsp;  <input id="crt_srv" type="button" value="新建服务器">
    </div>
    <div class="tableWrapper">
        <table class="grid" id="gridview">
            <thead>
                <tr>
                    <td style="text-align:center;width:20px;"><input id="checkAll" name="checkAllName" type="checkbox" value=""></td>
                    <td>服务器ID</td>
                    <td>区号</td>
                    <td>名字</td>
                    <td>渠道组</td>
                    <td>渠道标示</td>
                    <td>地址</td>
                    <td>端口</td>
                    <td>管理地址</td>
                    <td>管理端口</td>
                    <td>状态</td>
                    <td>是否新服</td>
                    <td>关服提示</td>
                    <td>建立时间</td>
                    <td></td>
                 </tr>
            </thead>
            <tbody>

            <#list srvs as srv>
                <tr>
                    <td style="text-align:center;"><input name="id[]" type="checkbox" value="${srv.id}"></td>
                    <td>${srv.serverId}</td>
                    <td>${srv.zone?default("")}</td>
                    <td>${srv.serverName?html}</td>
                    <td>${srv.oprGroup}</td>
                    <td>${srv.oprMatchStr}</td>
                    <td>${srv.ip}</td>
                    <td>${srv.port}</td>
                    <td>${srv.adminIp?default("")}</td>
                    <td>${srv.adminPort}</td>
                    <td>
                        <#if srv.state==FsGameLoginConst.SERVER_STATE_CLOSE>
                            <span style="color:#FF0000">关闭</span>
                        <#else>
                            <span style="color:#00FF00">开启</span>
                        </#if>  
                    </td>
                    <td>
                    	<#if srv.newSrv>
                            <span >新服</span>
                        <#else>
                            <span >老服</span>
                        </#if>
                    </td>
                    <td>${slim(srv.closedDesc?default(""), 10)}</td>
                    <td>${FsGameLoginUtils.formatDate(srv.createTime, "yyyy年M月d日 HH:mm")}</td>
                    
                    <td>
                    	<a href="javascript:if(confirm('请再次确认')){$('#mainForm').attr('action','manager?module=server&action=delsrv&id=${srv.id}');$('#mainForm').submit();}">删除</a>&nbsp;
                    	<a href="manager?module=server&action=createsrv&id=${srv.id}">编辑</a>&nbsp;
                    </td>
                   </tr>
            </#list>
            </tbody>

            <tfoot>
                <tr>
                    <td style="text-align:center;">↑</td>
                    <td colspan="16">
                        <@pageNav page=page url_pattern='?module=server&action=listsrv&curPage=$$_NO_$$' />
                    </td>
                 </tr>
              </tfoot>
           </table>
    </div>
</form>
</div>
<@js>
<script type="text/javascript" src="${jsRoot}/manage/listsrv.js" charset="utf-8"></script> 
<script type="text/javascript">
$(document).ready(function(){$.m.listsrv.init();});
</script>
</@js>
</@workbench>
</#escape>
