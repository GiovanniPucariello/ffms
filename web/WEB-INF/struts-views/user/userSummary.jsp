<%@ page contentType="text/html;charset=UTF-8" pageEncoding="GBK" %>
<%@ include file="/WEB-INF/struts-views/common/taglibs-include.jsp"%>
<html>
<head>
<title><s:text name="title.user" /></title>
<link rel="stylesheet" href="<s:url value='/styles/%{#session.layoutTheme}/displaytag.css' includeParams='none'/>" type="text/css">

<script type="text/javascript" src="<s:url value='/scripts/common.js' includeParams='none'/>"></script>

<script type="text/javascript">
	function doEdit()
	{
		var form = document.forms['listForm'];
		var url = '<c:url value="/initEditUser.action"/>';
		
		if(!isOnlyOneSelected(form, 'resOids'))
		{
			alert('<s:text name="user.select.one.edit" />');
			return;
		}
		
		oid = getValueOfFirstSelectedCheckBox(form, 'resOids');
		document.getElementById('userOid').value = oid;
		
		submitForm('listForm',url);
	}

	function doDelete()
	{	
		var form = document.forms['listForm'];
		var url = '<c:url value="/deleteUser.action"/>';
		
		if (!checkIds(form,'resOids'))
		{
			alert('<s:text name="user.select.no.one"/>');
			return;
		}
		
		submitFormWithConfirm('listForm',url,'<s:text name="user.delete.confirm"/>');
	}
</script>
</head>

<body>
<div id="main">
<div id="mainWrapper">
 
<div class="buttonWrapper">
   <a href="javascript:submitForm('searchForm','<c:url value="/initUser.action"/>')" class="actionButton" target="_self">
    <span><s:text name="button.search"/></span>
   </a>
   <a href='<c:url value="initAddUser.action"/>' class="actionButton" target="_self">
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
	<s:text name="label.user.summary" />				
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
          <span class="label"><s:text name="label.user.userName" /></span>
          <span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        <table class="innerTable"><tbody>
        <tr>
          <td class="data">
              <input type="text" size="20" name="param.userName" class="inputBox" 
              value='<c:out value="${SEARCH_PARAMETER_USER_PROFILE.userName}" />' />
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
<s:hidden name="param.userOid" id="userOid" />
<display:table htmlId="listTable" name="paginatedList" id="row" class="tablestyle1" requestURI="initUser.action?keepSp=Y"
	excludedParams="param.userName keepSp" >	
    <display:column  class="recordselect" 
		title="<input type='checkbox' name='chkAllIDs' onclick='toggleAll(\"listForm\",\"resOids\");'/>">
        <input type="checkbox" id="resOids" name="resOids" value="<c:out value="${row.userOid}"/>" />
    </display:column>

    <display:column class="recordnumber" >
        <c:out value="${pageCount+row_rowNum}" />
    </display:column>
    
    <display:column sortable="true" sortProperty="userName" style="width:14%;"
        titleKey="label.user.userName" headerClass="sortable">
        <a href="#" ><c:out value="${row.userName}"/></a>
    </display:column>
    
    <display:column sortable="true" sortProperty="familyName" property="familyName" style="width:14%;"
        titleKey="label.user.familyName" headerClass="sortable">
    </display:column>
    
    <display:column sortable="true" sortProperty="phone" property="phone" style="width:17%;"
        titleKey="label.user.phone" headerClass="sortable">
    </display:column>
    
    <display:column sortable="true" sortProperty="email" property="email" style="width:23%;"
        titleKey="label.user.email" headerClass="sortable">
    </display:column>
    
    <display:column sortable="true" sortProperty="gender" style="width:8%;"
        titleKey="label.user.gender" headerClass="sortable">
        <c:choose>
        	<c:when test='${row.gender=="M"}' >
        		<s:text name="label.male"/>
        	</c:when>
        	<c:otherwise>
        		<s:text name="label.female"/>
        	</c:otherwise>
        </c:choose>
    </display:column>
    
    <display:column sortable="true" sortProperty="loginId" property="loginId" style="width:14%;"
        titleKey="label.user.loginId" headerClass="sortable">
    </display:column>
    
    <display:column sortable="true" sortProperty="ctlStatus" style="width:10%;"
        titleKey="label.user.ctlStatus" headerClass="sortable">
        <c:choose>
        	<c:when test='${row.ctlStatus=="A"}' >
        		<s:text name="label.user.ctlStatus.active"/>
        	</c:when>
        	<c:otherwise>
        		<s:text name="label.user.ctlStatus.inactive"/>
        	</c:otherwise>
        </c:choose>
    </display:column>
    
</display:table>

</form>
</div>
</div>
</div>
</div>
</body>
</html>