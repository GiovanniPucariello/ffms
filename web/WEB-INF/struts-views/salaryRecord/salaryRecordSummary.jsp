<%@ page contentType="text/html;charset=UTF-8" pageEncoding="GBK" %>
<%@ include file="/WEB-INF/struts-views/common/taglibs-include.jsp"%>
<html>
<head>
<title><s:text name="title.salaryRecord" /></title>
<link rel="stylesheet" href="<s:url value='/styles/%{#session.layoutTheme}/displaytag.css' includeParams='none'/>" type="text/css">

<script type="text/javascript" src="<s:url value='/scripts/common.js' includeParams='none'/>"></script>
<%@ include file="/WEB-INF/struts-views/common/jsCalendar.jsp"%>

<script type="text/javascript">
	function doEdit()
	{
		var form = document.forms['listForm'];
		var url = '<c:url value="/initEditSalaryRecord.action"/>';
		
		if(!isOnlyOneSelected(form, 'resOids'))
		{
			alert('<s:text name="salaryRecord.select.one.edit"/>');
			return;
		}
		
		var oid = getValueOfFirstSelectedCheckBox(form, 'resOids');
		
		sendRequest(oid, url);
	}

	function doDelete()
	{	
		var form = document.forms['listForm'];
		var url = '<c:url value="/deleteSalaryRecord.action"/>';
		
		if (!checkIds(form,'resOids'))
		{
			alert('<s:text name="salaryRecord.select.no.one"/>');
			return;
		}
		
		submitFormWithConfirm('listForm',url,'<s:text name="salaryRecord.delete.confirm"/>');
	}
	
	function doConfirm()
	{	
		var form = document.forms['listForm'];
		var url = '<c:url value="/confirmSalaryRecord.action"/>';
		
		if (!checkIds(form,'resOids'))
		{
			alert('<s:text name="salaryRecord.select.no.one"/>');
			return;
		}
		
		submitFormWithConfirm('listForm',url,'<s:text name="salaryRecord.confirm.confirm"/>');
	}
	
	function sendRequest(srOid, oldUrl)
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
	      			alert('<s:text name="salaryRecord.edit.error.complete"/>');
	      			return;
	      		}
	      		
	      		document.getElementById('srOid').value = srOid;
				
				submitForm('backForm', oldUrl);
			}
      	}
      	
 		var url = '<c:url value="/ajax/checkSalaryRecord.action"/>';

		var param = "paramOid="+srOid;
		
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
   <a href="javascript:submitForm('searchForm','<c:url value="/initSalaryRecord.action"/>')" class="actionButton" target="_self">
    <span><s:text name="button.search"/></span>
   </a>
   <a href='<c:url value="initAddSalaryRecord.action"/>' class="actionButton" target="_self">
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
	<s:text name="label.salaryRecord.summary" />
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
          <span class="label"><s:text name="label.salaryRecord.userName" /></span>
          <span class="seperator">:</span>
      </td>
      <td class="data">	
      	<div class="group">
        <table class="innerTable"><tbody>
        <tr>
          <td class="data">
              <s:select name="salaryRecord.userOid" list="users" 
              	listKey="userOid" listValue="userName" headerKey="-1" headerValue="ALL" cssClass="inputBox"/>
          </td>
        </tr>
        </tbody></table>
      	</div>	     
      </td>
    </tr>


	<tr>
      <td class="labelCell">	
          <span class="label"><s:text name="label.salaryRecord.srType" /></span>
          <span class="seperator">:</span>
      </td>
      <td class="data">	
      	<div class="group">
        <table class="innerTable"><tbody>
        <tr>
          <td class="data">
              <s:select name="salaryRecord.srType" list="srTypes" 
              	listKey="key" listValue="value" headerKey="-1" headerValue="ALL" cssClass="inputBox"/>
          </td>
        </tr>
        </tbody></table>
      	</div>	     
      </td>
    </tr>

	<tr>
      <td class="labelCell">	
          <span class="label"><s:text name="label.salaryRecord.description" /></span>
          <span class="seperator">:</span>
      </td>
      <td class="data">	
      	<div class="group">
        <table class="innerTable"><tbody>
        <tr>
          <td class="data">
              <input type="text" size="30" name="salaryRecord.description" class="inputBox" 
              	value='<c:out value="${SEARCH_PARAMETER_SALARY_RECORD.description}" />' />
          </td>
        </tr>
        </tbody></table>
      	</div>	     
      </td>
    </tr>

    
    <tr>
      <td class="labelCell">	
          <span class="label"><s:text name="label.salaryRecord.ctlStatus" /></span>
          <span class="seperator">:</span>
      </td>
      <td class="data">	
      	<div class="group">
        <table class="innerTable"><tbody>
        <tr>
          <td class="data">
              <s:select name="salaryRecord.ctlStatus" list="salaryRecordStatus" 
              	listKey="key" listValue="value" headerKey="-1" headerValue="ALL" cssClass="inputBox"/>
          </td>
        </tr>
        </tbody></table>
      	</div>	     
      </td>
    </tr>
    
    
    <tr>
    	<td class="labelCell">
			<span class="label"><s:text name="label.salaryRecord.createDate" /></span>
			<span class="seperator">:</span>
    	</td>
		<td class="data">
       		<table class="innerTable">
         	<tbody><tr>
           	<td>
                From              
                <input type="text" name="salaryRecord.fromDate" id="fromDate" style="width:80px;" readonly
						value="<fmt:formatDate value="${SEARCH_PARAMETER_SALARY_RECORD.fromDate}" type="date" pattern="yyyy-MM-dd"/>">
				<span id="dateCalendarFromDate" style="cursor:hand;" onClick="return showCalendar('fromDate', '%Y-%m-%d', null, true);">
					<img src="images/default/iconCalendar.gif">
				</span>	  
				&nbsp;&nbsp;
				To
                <input type="text" name="salaryRecord.toDate" id="toDate" style="width:80px;" readonly
						value="<fmt:formatDate value="${SEARCH_PARAMETER_SALARY_RECORD.toDate}" type="date" pattern="yyyy-MM-dd"/>">	
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
<s:hidden name="salaryRecord.srOid" id="srOid" />
</form>
<form id="listForm" name="listForm" onsubmit="return true;" action="" method="post">
<display:table htmlId="listTable" name="paginatedList" id="row" class="tablestyle1" requestURI="initSalaryRecord.action?keepSp=Y" 
	excludedParams="salaryRecord.userOid salaryRecord.srType salaryRecord.description salaryRecord.ctlStatus salaryRecord.fromDate salaryRecord.toDate keepSp" >	
    <display:column  class="recordselect"
		title="<input type='checkbox' name='chkAllIDs' onclick='toggleAll(\"listForm\",\"resOids\");'/>">
        <input type="checkbox" id="resOids" name="resOids" value="<c:out value="${row.srOid}"/>" />
    </display:column>

    <display:column class="recordnumber" >
        <c:out value="${pageCount+row_rowNum}" />
    </display:column>
    
    <display:column sortable="true" sortProperty="userName" style="width:10%;"
        titleKey="label.salaryRecord.userName" headerClass="sortable">
        <a href="#" ><c:out value="${row.userName}"/></a>
    </display:column>
    
    <display:column sortable="true" sortProperty="srType" style="width:10%;"
        titleKey="label.salaryRecord.srType" headerClass="sortable">
        <c:choose>
        	<c:when test='${row.srType=="S"}' >
        		<s:text name="label.salaryRecord.srType.salary"/>
        	</c:when>
        	<c:when test='${row.srType=="I"}' >
        		<s:text name="label.salaryRecord.srType.incentive"/>
        	</c:when>
        	<c:otherwise>
        		<s:text name="label.salaryRecord.srType.other"/>
        	</c:otherwise>
        </c:choose>
    </display:column>
    
    <display:column sortable="false" style="width:15%;"
        titleKey="label.salaryRecord.amount" headerClass="sortable">
        <c:out value="${row.amount}"/>
    </display:column>
    
    <display:column sortable="false" style="width:35%;"
        titleKey="label.salaryRecord.description" headerClass="sortable">
        <c:out value="${row.description}"/>
    </display:column>
    
    <display:column sortable="true" sortProperty="createDate" style="width:20%;"
        titleKey="label.salaryRecord.createDate" headerClass="sortable" property="createDate"
        decorator="com.oyl.ffms.conversion.DefaultDateWrapper" >
    </display:column>
    
    <display:column sortable="true" sortProperty="ctlStatus" style="width:10%;"
        titleKey="label.salaryRecord.ctlStatus" headerClass="sortable" >
        <c:choose>
        	<c:when test='${row.ctlStatus=="C"}' >
        		<s:text name="label.salaryRecord.ctlStatus.complete"/>
        	</c:when>
        	<c:otherwise>
        		<s:text name="label.salaryRecord.ctlStatus.draft"/>
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