<#include "/lib/workbench.ftl"/>
<#include "/lib/pageNav.ftl">

<#escape x as x?html>
<@workbench>
<div id="mContent">
        <div class="title"><span class="buttons"> </span> 开放关闭注册    </div>
    <form action="?module=register&action=registerstate" id="mainForm" method="post">
     
    <div class="tableWrapper">
    
    	<table class="grid">
    		<tbody>
    			<td width="150" >
    				<div class="col-md-2" style="text-align:left;">
						<select id="val" name="val" class="bs-select form-control" >
						    <option value="1" <#if val=="1">selected</#if>>开放注册</option>
						    <option value="0" <#if val=="0">selected</#if>>关闭注册</option>
						</select>
					</div>
    			</td>
    			
    			<td>
    				<input type="submit" value="确定" class="bigBtn" id="okbtn" name="okbtn">
    			</td>
    		</tbody>
    	</table>

				
    </div>
</form>
</div>
<@js>
<script type="text/javascript">

</script>
</@js>
</@workbench>
</#escape>
