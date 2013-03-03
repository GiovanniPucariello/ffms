<%@ page contentType="text/html;charset=UTF-8" pageEncoding="GBK" %>
<%@ include file="/WEB-INF/struts-views/common/taglibs-include.jsp"%>
<html>
<head>
<title><s:text name="title.category" /></title>
<link rel="stylesheet" href="<s:url value='/styles/%{#session.layoutTheme}/displaytag.css' includeParams='none'/>" type="text/css">
<%@ include file="/WEB-INF/struts-views/common/jsCalendar.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/common.js' includeParams='none'/>"></script>

<script type="text/javascript">
	function doEdit()
	{
		var form = document.forms['listForm'];
		var url = '<c:url value="/initEditCategory.action"/>';
		
		if(!isOnlyOneSelected(form, 'resOids'))
		{
			alert('<s:text name="category.select.one.edit"/>');
			return;
		}
		
		var oid = getValueOfFirstSelectedCheckBox(form, 'resOids');
		
		sendRequest(oid, url);
	}

	function doDelete()
	{	
		var form = document.forms['listForm'];
		var url = '<c:url value="/deleteCategory.action"/>';
		
		if (!checkIds(form,'resOids'))
		{
			alert('<s:text name="category.select.no.one"/>');
			return;
		}
		
		submitFormWithConfirm('listForm',url,'<s:text name="category.delete.confirm"/>');
	}
	
 	function sendRequest(categoryOid, oldUrl)
 	{
 		var xmlHttp = getXmlHttp();
 		xmlHttp.onreadystatechange=function()
      	{
      		if (xmlHttp.readyState == 4 &&
        			xmlHttp.status == 200)
			{
      			var status = xmlHttp.responseXML.getElementsByTagName('status').item(0).firstChild.nodeValue;
				
	      		if(status=="<%=com.oyl.base.util.CommonConstants.VALUE_NO%>")
	      		{
	      			alert('<s:text name="category.item.not.empty.not.edit"/>');
	      			return;
	      		}
	      		
	      		document.getElementById('categoryOid').value = categoryOid;
		
				submitForm('listForm', oldUrl);
			}
      	}
      	
 		var url = '<c:url value="/ajax/checkCategory.action"/>';

		var param = "paramOid="+categoryOid;
		
 		xmlHttp.open('POST', url, true);
 		xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
 		xmlHttp.setRequestHeader("Content-length", param.length);
      	xmlHttp.setRequestHeader("Connection", "close");
 		xmlHttp.send(param);
 	}
</script>
</head>

<body>
<div id="main">
<div id="mainWrapper">
 
<div class="buttonWrapper">
   <a href="javascript:submitForm('searchForm','<c:url value="/initCategory.action"/>')" class="actionButton" target="_self">
    <span><s:text name="button.search"/></span>
   </a>
   <a href='<c:url value="initAddCategory.action"/>' class="actionButton" target="_self">
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
	<s:text name="label.category.summary" />				
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
          <span class="label"><s:text name="label.category.categoryName" /></span>
          <span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        <table class="innerTable"><tbody>
        <tr>
          <td class="data">
              <input type="text" size="20" name="param.categoryName" class="inputBox" 
              value='<c:out value="${SEARCH_PARAMETER_CATEGORY.categoryName}" />' />
          </td>
        </tr>
        </tbody></table>
      </div>	     
      </td>
    </tr>
    
    <tr>
    <td class="labelCell">
            <span class="label"><s:text name="label.category.itemFromDate" /></span>
            <span class="seperator">:</span>
    </td>
    <td class="data">
       <table class="innerTable">
         <tbody><tr>
             <td>
                From       
                <input type="text" name="param.itemFromDate" id="itemFromDate" style="width:80px;" readonly
                        value="<fmt:formatDate value="${SEARCH_PARAMETER_CATEGORY.itemFromDate}" type="date" pattern="yyyy-MM-dd"/>"> 
                <span id="dateCalendarFromDate" style="cursor:hand;" onClick="return showCalendar('itemFromDate', '%Y-%m-%d', null, true);">
                    <img src="images/default/iconCalendar.gif">
                </span>
                &nbsp;&nbsp;
                To
                <input type="text" name="param.itemToDate" id="itemToDate" style="width:80px;" readonly
                        value="<fmt:formatDate value="${SEARCH_PARAMETER_CATEGORY.itemToDate}" type="date" pattern="yyyy-MM-dd"/>"> 
                <span id="dateCalendarFromDate" style="cursor:hand;" onClick="return showCalendar('itemToDate', '%Y-%m-%d', null, true);">
                    <img src="images/default/iconCalendar.gif">
                </span>
             </td>              
         </tr></tbody>
       </table>
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
<s:hidden name="param.categoryOid" id="categoryOid" />
<display:table htmlId="listTable" name="paginatedList" id="row" class="tablestyle1" requestURI="initCategory.action?keepSp=Y" 
	excludedParams="param.categoryName param.itemFromDate param.itemToDate keepSp" >	
    <display:column  class="recordselect" 
		title="<input type='checkbox' name='chkAllIDs' onclick='toggleAll(\"listForm\",\"resOids\");'/>">
        <input type="checkbox" id="resOids" name="resOids" value="<c:out value="${row.categoryOid}"/>" />
    </display:column>

    <display:column class="recordnumber">
        <c:out value="${pageCount+row_rowNum}" />
    </display:column>
    
    <display:column sortable="true" sortProperty="categoryName" style="width:40%;"
        titleKey="label.category.categoryName" headerClass="sortable">
        <a href="#" ><c:out value="${row.categoryName}"/></a>
    </display:column>
    
    <display:column sortable="true" sortProperty="itemNum" style="width:20%;"
        titleKey="label.category.item" headerClass="sortable">
        <c:out value="${row.itemNum}"/>
    </display:column>
    
    <display:column sortable="true" sortProperty="itemAmount" style="width:40%;"
        titleKey="label.category.totalPrice" headerClass="sortable">
        <c:out value="${row.itemAmount }"/>
    </display:column>
</display:table>

</form>
</div>
</div>
</div>
</div>
</body>
</html>