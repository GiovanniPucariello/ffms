<%@ page contentType="text/html;charset=UTF-8" pageEncoding="GBK" %>
<%@ include file="/WEB-INF/struts-views/common/taglibs-include.jsp"%>
<html>
<head>
<title><s:text name="title.family" /></title>
<link rel="stylesheet" href="<s:url value='/styles/%{#session.layoutTheme}/displaytag.css' includeParams='none'/>" type="text/css">

<script type="text/javascript" src="<s:url value='/scripts/common.js' includeParams='none'/>"></script>

<script type="text/javascript">
	function doEdit()
	{
		var form = document.forms['listForm'];
		var url = '<c:url value="/initEditFamily.action"/>';
		
		if(!isOnlyOneSelected(form, 'resOids'))
		{
			alert('<s:text name="family.select.one.edit"/>');
			return;
		}
		
		oid = getValueOfFirstSelectedCheckBox(form, 'resOids');
		document.getElementById('familyOid').value = oid;
		
		submitForm('listForm',url);
	}

	function doDelete()
	{	
		var form = document.forms['listForm'];
		var url = '<c:url value="/deleteFamily.action"/>';
		
		if (!checkIds(form,'resOids'))
		{
			alert('<s:text name="family.select.no.one"/>');
			return;
		}
		
		submitFormWithConfirm('listForm',url,'<s:text name="family.delete.confirm"/>');
	}
</script>
</head>

<body>
<div id="main">
<div id="mainWrapper">
 
<div class="buttonWrapper">
   <a href="javascript:submitForm('searchForm','<c:url value="/initFamily.action"/>')" class="actionButton" target="_self">
    <span><s:text name="button.search"/></span>
   </a>
   <a href='<c:url value="initAddFamily.action"/>' class="actionButton" target="_self">
    <span><s:text name="button.create"/></span>
   </a>
   <c:if test="${paginatedList.fullListSize > 0}">
   <a href="javascript:doEdit();" 
    class="actionButton" target="_self"><span><s:text name="button.edit"/></span></a>
   <a href="javascript:doDelete();" 
    class="actionButton" target="_self"><span><s:text name="button.delete"/></span></a>
   </c:if>   
</div>

<div class="pageTitle"> 
<div class="title">
	<s:text name="label.family.summary" />				
</div>
</div>

<!-- here is message area -->
<div class="info">
	<span class="mandatory">
    	<s:actionerror />
    	<s:actionmessage />
    	<s:fielderror />	    
	</span>
</div>

<!-- search area -->
<div id="search" class="search">
<div class="inputForm">
<table width="100%">
<form name="searchForm" id="searchForm" action="" method="post">
<tbody>
  <tr>
      <td class="labelCell">	
          <span class="label"><s:text name="label.family.familyName" /></span>
          <span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        <table class="innerTable"><tbody>
        <tr>
          <td class="data">
              <input type="text" size="20" name="param.familyName" class="inputBox" 
              value='<c:out value="${SEARCH_PARAMETER_FAMILY.familyName}" />' />
          </td>
        </tr>
        </tbody></table>
      </div>	     
      </td>
    </tr>
</tbody>
</form>
</table>
</div>
</div>

<div class="searchResultSeperator"><hr/></div>
					
<!-- list area -->
<div class="searchResult">
<div class="content">

<form id="listForm" name="listForm" onsubmit="return true;" action="" method="post">
<s:hidden name="param.familyOid" id="familyOid" />
<display:table htmlId="listTable" name="paginatedList" id="row" class="tablestyle1" requestURI="initFamily.action?keepSp=Y" 
	excludedParams="param.familyName keepSp" >
    
    <display:column  class="recordselect" 
		title="<input type='checkbox' name='chkAllIDs' onclick='toggleAll(\"listForm\",\"resOids\");'/>">
        <input type="checkbox" id="resOids" name="resOids" value="<c:out value="${row.familyOid}"/>" />
    </display:column>

    <display:column class="recordnumber">
        <c:out value="${pageCount+row_rowNum}" />
    </display:column>
    
    <display:column sortable="true" sortProperty="familyName" style="width:20%;"
        titleKey="label.family.familyName" headerClass="sortable">
        <a href="#" ><c:out value="${row.familyName}"/></a>
    </display:column>
    
    <display:column sortable="false" style="width:40%;"
        titleKey="label.family.address" headerClass="sortable">
        <c:out value="${row.address}"/>
    </display:column>
    
    <display:column sortable="false" style="width:30%;"
        titleKey="label.family.phone" headerClass="sortable">
        <c:out value="${row.phone}"/>
    </display:column>
    
    <display:column sortable="false" style="width:10%;"
        titleKey="label.family.numOfMember" headerClass="sortable">
        <c:out value="${row.numOfMember}"/>
    </display:column>
    
</display:table>

</form>
</div>
</div>
</div>
</div>
</body>
</html>