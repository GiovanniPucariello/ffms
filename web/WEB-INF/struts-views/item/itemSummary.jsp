<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/struts-views/common/taglibs-include.jsp"%>
<html>
<head>
<title><s:text name="title.item" /></title>
<link rel="stylesheet" href="<s:url value='/styles/%{#session.layoutTheme}/displaytag.css' includeParams='none'/>" type="text/css">

<script type="text/javascript" src="<s:url value='/scripts/common.js' includeParams='none'/>"></script>
<%@ include file="/WEB-INF/struts-views/common/jsCalendar.jsp"%>

<script type="text/javascript">
	function doEdit()
	{
		var form = document.forms['listForm'];
		var url = '<c:url value="/initEditItem.action"/>';
		
		if(!isOnlyOneSelected(form, 'resOids'))
		{
			alert('<s:text name="item.select.one.edit"/>');
			return;
		}
		
		var oid = getValueOfFirstSelectedCheckBox(form, 'resOids');
		
		sendRequest(oid, url);
	}

	function doDelete()
	{	
		var form = document.forms['listForm'];
		var url = '<c:url value="/deleteItem.action"/>';
		
		if (!checkIds(form,'resOids'))
		{
			alert('<s:text name="item.select.no.one"/>');
			return;
		}
		
		submitFormWithConfirm('listForm',url,'<s:text name="item.delete.confirm"/>');
	}
	
	function doConfirm()
	{	
		var form = document.forms['listForm'];
		var url = '<c:url value="/confirmItem.action"/>';
		
		if (!checkIds(form,'resOids'))
		{
			alert('<s:text name="item.select.no.one"/>');
			return;
		}
		
		submitFormWithConfirm('listForm',url,'<s:text name="item.confirm.confirm"/>');
	}
	
	function sendRequest(itemOid, oldUrl)
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
	      			alert('<s:text name="item.edit.error.complete"/>');
	      			return;
	      		}
	      		
	      		document.getElementById('itemOid').value = itemOid;
		
				submitForm('backForm', oldUrl);
			}
      	}
      	
 		var url = '<c:url value="/ajax/checkItem.action"/>';

		var param = "paramOid="+itemOid;
		
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
   <a href="javascript:submitForm('searchForm','<c:url value="/initItem.action"/>')" class="actionButton" target="_self">
    <span><s:text name="button.search"/></span>
   </a>
   <a href='<c:url value="initAddItem.action"/>' class="actionButton" target="_self">
    <span><s:text name="button.create"/></span>
   </a>
   <c:if test="${paginatedList.fullListSize > 0}">
   <a href="javascript:doEdit();" 
    class="actionButton" target="_self"><span><s:text name="button.edit"/></span></a>
    <a href="javascript:doConfirm();" 
    class="actionButton" target="_self"><span><s:text name="button.confirm"/></span></a>
   <a href="javascript:doDelete();" 
    class="actionButton" target="_self"><span><s:text name="button.delete"/></span></a>
   </c:if>   
</div>

<div class="pageTitle"> 
<div class="title">
	<s:text name="label.item.summary" />				
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
<form name="searchForm" id="searchForm" action="" method="post">
<table width="100%">
<tbody>
	<tr>
      <td class="labelCell">	
          <span class="label"><s:text name="label.item.userName" /></span>
          <span class="seperator">:</span>
      </td>
      <td class="data">	
      	<div class="group">
        <table class="innerTable"><tbody>
        <tr>
          <td class="data">
              <s:select name="param.selectedUserOid" list="users" 
              	listKey="userOid" listValue="userAlias" headerKey="-1" headerValue="ALL" cssClass="inputBox"/>
          </td>
        </tr>
        </tbody></table>
      	</div>	     
      </td>
    </tr>

	<tr>
      <td class="labelCell">	
          <span class="label"><s:text name="label.item.itemDesc" /></span>
          <span class="seperator">:</span>
      </td>
      <td class="data">	
      	<div class="group">
        <table class="innerTable"><tbody>
        <tr>
          <td class="data">
              <input type="text" size="20" name="param.itemDesc" class="inputBox" 
              value='<c:out value="${SEARCH_PARAMETER_ITEM.itemDesc}" />' />
          </td>
        </tr>
        </tbody></table>
      	</div>	     
      </td>
    </tr>

  	<tr>
      <td class="labelCell">	
          <span class="label"><s:text name="label.item.categoryName" /></span>
          <span class="seperator">:</span>
      </td>
      <td class="data">	
      	<div class="group">
        <table class="innerTable"><tbody>
        <tr>
          <td class="data">
              <input type="text" size="20" name="param.categoryName" class="inputBox" 
              value='<c:out value="${SEARCH_PARAMETER_ITEM.categoryName}" />' />
          </td>
        </tr>
        </tbody></table>
      	</div>	     
      </td>
    </tr>
    
    <tr>
      <td class="labelCell">	
          <span class="label"><s:text name="label.item.ctlStatus" /></span>
          <span class="seperator">:</span>
      </td>
      <td class="data">	
      	<div class="group">
        <table class="innerTable"><tbody>
        <tr>
          <td class="data">
              <s:select name="param.ctlStatus" list="itemStatusList" 
              	listKey="key" listValue="value" headerKey="-1" headerValue="ALL" cssClass="inputBox"/>
          </td>
        </tr>
        </tbody></table>
      	</div>	     
      </td>
    </tr>
    
    
    <tr>
    	<td class="labelCell">
			<span class="label"><s:text name="label.item.createDate" /></span>
			<span class="seperator">:</span>
    	</td>
		<td class="data">
       		<table class="innerTable">
         	<tbody><tr>
           	<td>
                From              
                <input type="text" name="param.fromDate" id="fromDate" style="width:80px;" readonly
						value="<fmt:formatDate value="${SEARCH_PARAMETER_ITEM.fromDate}" type="date" pattern="yyyy-MM-dd"/>">
				<span id="dateCalendarFromDate" style="cursor:hand;" onClick="return showCalendar('fromDate', '%Y-%m-%d', null, true);">
					<img src="images/default/iconCalendar.gif">
				</span>	  
				&nbsp;&nbsp;
				To
                <input type="text" name="param.toDate" id="toDate" style="width:80px;" readonly
						value="<fmt:formatDate value="${SEARCH_PARAMETER_ITEM.toDate}" type="date" pattern="yyyy-MM-dd"/>">	
				<span id="dateCalendarToDate" style="cursor:hand;" onClick="return showCalendar('toDate', '%Y-%m-%d', null, true);">
					<img src="images/default/iconCalendar.gif">
				</span>				              
             </td>              
     		</tr></tbody>
       		</table>
		</td>
	</tr>
</tbody>

</table>
</form>
</div>
</div>

<div class="searchResultSeperator"><hr/></div>
					
<!-- list area -->
<div class="searchResult">
<div class="content">
<form id="backForm" name="backForm" action="" method="post">
<s:hidden name="param.itemOid" id="itemOid" />
</form>
<form id="listForm" name="listForm" onsubmit="return true;" action="" method="post">
<display:table htmlId="listTable" name="paginatedList" id="row" class="tablestyle1" requestURI="initItem.action?keepSp=Y" 
	excludedParams="param.categoryName param.itemDesc param.selectedUserOid param.ctlStatus param.fromDate param.toDate keepSp" >	
    <display:column  class="recordselect"
		title="<input type='checkbox' name='chkAllIDs' onclick='toggleAll(\"listForm\",\"resOids\");'/>">
        <input type="checkbox" id="resOids" name="resOids" value="<c:out value="${row.itemOid}"/>" />
    </display:column>

    <display:column class="recordnumber" >
        <c:out value="${pageCount+row_rowNum}" />
    </display:column>
    
    <display:column sortable="true" sortProperty="itemDesc" style="width:25%;"
        titleKey="label.item.itemDesc" headerClass="sortable">
        <a href="#" ><c:out value="${row.itemDesc}"/></a>
    </display:column>
    
    <display:column sortable="true" sortProperty="categoryName" style="width:16%;"
        titleKey="label.item.categoryName" headerClass="sortable">
        <c:out value="${row.categoryName}"/>
    </display:column>
    
    <display:column sortable="true" sortProperty="userAlias" style="width:12%;"
        titleKey="label.item.userName" headerClass="sortable">
        <c:out value="${row.userAlias}"/>
    </display:column>
    
    <display:column sortable="true" sortProperty="itemTotalCost" style="width:16%;"
        titleKey="label.item.itemTotalCost" headerClass="sortable">
        <c:out value="${row.itemTotalCost}"/>
    </display:column>
    
    <display:column sortable="true" sortProperty="ctlStatus" style="width:10%;"
        titleKey="label.item.ctlStatus" headerClass="sortable" >
        <c:choose>
        	<c:when test='${row.ctlStatus=="C"}' >
        		<s:text name="label.item.ctlStatus.complete"/>
        	</c:when>
        	<c:otherwise>
        		<s:text name="label.item.ctlStatus.draft"/>
        	</c:otherwise>
        </c:choose>
    </display:column>
    
    <display:column sortable="true" sortProperty="createDate" style="width:21%;"
        titleKey="label.item.createDate" headerClass="sortable" property="createDate"
        decorator="com.oyl.ffms.conversion.DefaultDateWrapper" >
    </display:column>
</display:table>

</form>
</div>
</div>
</div>
</div>
</body>
</html>