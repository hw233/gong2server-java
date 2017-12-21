<#include "/lib/workbench.ftl"/>
<#include "/lib/pageNav.ftl">

<#escape x as x?html>
<@workbench>
<div id="mContent">
    <div class="title"><span class="buttons"> </span> 增加公告    </div>
    <form action="?module=notice&action=addnotice" id="mainForm" method="post">
     
	    <div class="tableWrapper">

							<label>内容:</label>
							<div>
								<textarea style="width:480px; height:320px" id="newcontent" name="newcontent" maxlength="20000"></textarea>

							</div>
							<input type="hidden"  value="" name="cntId" id="cntId">
							<input type="submit" value="确定" class="bigBtn" id="okbtn" name="okbtn">

	    			<br></br>
					<div>
						<table class="grid">
							<thead>
								<tr class="heading" role="row">
				                    <th>内容</th>
				                    <th>创建时间</th>
				                    <th>操作</th>
								</tr>
							</thead>
				
							<tbody>
							<#list listnotice as sp>
				                <tr>
				                    <td id="c_${sp.id}">${sp.content?default("")}</td>
				                    <td>${FsGameLoginUtils.formatDate(sp.createTime, "yyyy年M月d日 HH:mm:ss")}</td>
				                    <td>
				                    	<a href="javascript:if(confirm('请再次确认')){$('#mainForm').attr('action','manager?module=notice&action=delnotice&id=${sp.id}');$('#mainForm').submit();}">删除</a>&nbsp;
				                    	<a name="scriptLoad" href="#" idVal="${sp.id}" idText="${sp.content?default("")}" >编辑</a>&nbsp;
				                    </td>
				                 </tr>
				            </#list>
							</tbody>
						</table>
					</div>
					
	    </div>
	</form>
</div>
<@js>
<script type="text/javascript" src="${jsRoot}/manage/notice.js" charset="utf-8"></script>
<script type="text/javascript">
	$(document).ready(function(){$.m.addnotice.init();});
</script>
</@js>
</@workbench>
</#escape>
