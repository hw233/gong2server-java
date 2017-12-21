<#macro pageNav page url_pattern = "" to_replace = "$$_NO_$$">
	
	<#assign current=page.curPage!(1) />
	<#assign limit=page.limit!(10) />
	<#assign total=page.total!(0) />
	<#assign totalPage=page.totalPage!(0) />
	
	
	<span class="pageIndex">
	<#if current == 1>
		<span class='d'>&laquo;</span> 
	<#else>
		<a href='#' value='${url_pattern?replace(to_replace, current-1)}' class='pagego' title='上一页'>&laquo;</a> 
	</#if>
	
	<#list (current - 5)..(current - 1) as i>
		<#if i gt 0>
			<a href='#'  value='${url_pattern?replace(to_replace, i)}' class='pagego'>${i}</a>
		</#if> 
	</#list>
	
	<span class='c'>${current}</span> 

	<#list (current + 1)..(current + 5) as i>
		<#if i lte totalPage>
		<a href='#'  value='${url_pattern?replace(to_replace, i)}' class='pagego'>${i}</a>
 		</#if>  
	</#list>
	 
	<#if current gte totalPage>
		<span class='d'>&raquo;</span> 
	<#else>
		<a href='#'  value='${url_pattern?replace(to_replace, current + 1)}' class='pagego' title='下一页'>&raquo;</a>
	</#if>
	
	
	
	<span class="t">${current}/${totalPage}共${total}条记录</span>
	
	<select id="limit" name="limit" onchange="document.getElementById('mainForm').submit();">
	    <option value="${page.limit}" checked="checked">每页显示${page.limit}行</option>
	    <option value="" disabled="disabled">---</option>
	    <option value="10">10行</option>
	    <option value="30">30行</option>
	    <option value="50">50行</option>
	    <option value="100">100行</option>
	    <option value="200">200行</option>
	</select>
	</span>
</#macro>