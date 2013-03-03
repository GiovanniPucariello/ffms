<%@ page contentType="text/html;charset=UTF-8" pageEncoding="GBK" %>
<%@ include file="/WEB-INF/struts-views/common/taglibs-include.jsp"%>
<html>
<head>
<title><s:text name="title.creditRecord" /></title>
<link rel="stylesheet" href="<s:url value='/styles/%{#session.layoutTheme}/displaytag.css' includeParams='none'/>" type="text/css">

<script type="text/javascript" src="<s:url value='/scripts/common.js' includeParams='none'/>"></script>
<script type="text/javascript">
	function doCreate()
	{
		var url = '<c:url value="/initAddCreditCardRecord.action"/>';
		
		submitForm('backForm2', url);
	}
	
	function doEdit()
	{
		var form = document.forms['listForm'];
		var url = '<c:url value="/initEditCreditCardRecord.action"/>';
		
		if(!isOnlyOneSelected(form, 'resOids'))
		{
			alert('<s:text name="creditCardRecord.select.one.edit"/>');
			return;
		}
		
		var oid = getValueOfFirstSelectedCheckBox(form, 'resOids');
		
		sendRequest(oid, url);
	}

	function doDelete()
	{	
		var form = document.forms['listForm'];
		var url = '<c:url value="/deleteCreditCardRecord.action"/>';
		
		if (!checkIds(form,'resOids'))
		{
			alert('<s:text name="creditCardRecord.select.no.one"/>');
			return;
		}
		
		submitFormWithConfirm('listForm',url,'<s:text name="creditCardRecord.delete.confirm"/>');
	}
	
	function doConfirm()
	{	
		var form = document.forms['listForm'];
		var url = '<c:url value="/confirmCreditCardRecord.action"/>';
		
		if (!checkIds(form,'resOids'))
		{
			alert('<s:text name="creditCardRecord.select.no.one"/>');
			return;
		}
		
		submitFormWithConfirm('listForm',url,'<s:text name="creditCardRecord.confirm.confirm"/>');
	}
	
	function sendRequest(recordOid, oldUrl)
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
	      			alert('<s:text name="creditCardRecord.edit.failed.confirm"/>');
	      			return;
	      		}
	      		
	      		document.getElementById('ccrOid').value = recordOid;
		
				submitForm('backForm3', oldUrl);
			}
      	}
      	
 		var url = '<c:url value="/ajax/checkCreditCardRecord.action"/>';

		var param = "paramOid="+recordOid;
		
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
   <a href='javascript:doCreate();' class="actionButton" target="_self">
    <span><s:text name="button.create"/></span>
   </a>
   <c:if test="${paginatedList.fullListSize > 0}">
   <a href="javascript:doEdit();" 
    class="actionButton" target="_self"><span><s:text name="button.edit"/></span></a>
   <a href="javascript:doDelete();" 
    class="actionButton" target="_self"><span><s:text name="button.delete"/></span></a>
    <a href="javascript:doConfirm();" 
    class="actionButton" target="_self"><span><s:text name="button.confirm"/></span></a>
   </c:if>
   <a href="javascript:submitForm('backForm','<c:url value="/initCreditCard.action"/>');" class="actionButton" target="_self">
	  <span><s:text name="button.cancel"/></span>
	</a>
</div>

<div class="pageTitle"> 
<div class="title">
	<s:text name="label.creditCardRecord.summary" />				
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
<tbody>
	<tr>
      <td class="labelCell">	
          <span class="label"><s:text name="label.creditCardRecord.userName" /></span>
          <span class="seperator">:</span>
      </td>
      <td class="data">	
      	<div class="group">
        <table class="innerTable"><tbody>
        <tr>
          <td class="data">
              <s:property value="creditCard.userName" />
          </td>
        </tr>
        </tbody></table>
      	</div>	     
      </td>
    </tr>

	<tr>
      <td class="labelCell">	
          <span class="label"><s:text name="label.creditCardRecord.cardNo" /></span>
          <span class="seperator">:</span>
      </td>
      <td class="data">	
      	<div class="group">
        <table class="innerTable"><tbody>
        <tr>
          <td class="data">
              <s:property value="creditCard.cardNo" />
          </td>
        </tr>
        </tbody></table>
      	</div>	     
      </td>
    </tr>
    
    
    <tr>
      <td class="labelCell">	
          <span class="label"><s:text name="label.creditCardRecord.quota" /></span>
          <span class="seperator">:</span>
      </td>
      <td class="data">	
      	<div class="group">
        <table class="innerTable"><tbody>
        <tr>
          <td class="data">
              <s:property value="creditCard.quota" />
          </td>
        </tr>
        </tbody></table>
      	</div>	     
      </td>
    </tr>
    
    
    <tr>
      <td class="labelCell">	
          <span class="label"><s:text name="label.creditCardRecord.debt" /></span>
          <span class="seperator">:</span>
      </td>
      <td class="data">	
      	<div class="group">
        <table class="innerTable"><tbody>
        <tr>
          <td class="data">
              <s:property value="creditCard.debt" />
          </td>
        </tr>
        </tbody></table>
      	</div>	     
      </td>
    </tr>
</tbody>

</table>
</div>
</div>


<div class="searchResultSeperator"><hr/></div>

<!-- list area -->
<div class="searchResult">
<div class="content">
<form id="backForm" name="backForm" onsubmit="return true;" action="" method="post">
<input type="hidden" name="<%= com.oyl.base.util.CommonConstants.REQ_PARAMETER_KEEP_SEARCH_CONDITION %>" value="Y"/>
</form>

<form id="backForm2" name="backForm2" onsubmit="return true;" action="" method="post">
<s:hidden name="creditCardRecord.ccOid" id="ccOid" />
</form>

<form id="backForm3" name="backForm3" onsubmit="return true;" action="" method="post">
<s:hidden name="creditCardRecord.ccrOid" id="ccrOid" />
</form>

<form id="listForm" name="listForm" onsubmit="return true;" action="" method="post">
<display:table htmlId="listTable" name="paginatedList" id="row" class="tablestyle1" requestURI="initCreditCardRecord.action?keepSp=Y" 
	excludedParams="creditCardRecord.ccOid keepSp" >
    <display:column  class="recordselect" 
		title="<input type='checkbox' name='chkAllIDs' onclick='toggleAll(\"listForm\",\"resOids\");'/>">
        <input type="checkbox" id="resOids" name="resOids" value="<c:out value="${row.ccrOid}"/>" />
    </display:column>

    <display:column class="recordnumber" >
        <c:out value="${pageCount+row_rowNum}" />
    </display:column>
    
    <display:column sortable="true" sortProperty="ccrType" style="width:15%;"
        titleKey="label.creditCardRecord.ccrType" headerClass="sortable">
        <c:choose>
        	<c:when test='${row.ccrType=="I"}' >
        		<s:text name="label.creditCardRecord.ccrType.repayment"/>
        	</c:when>
        	<c:otherwise>
        		<s:text name="label.creditCardRecord.ccrType.consumption"/>
        	</c:otherwise>
        </c:choose>
    </display:column>
    
    <display:column sortable="false" style="width:25%;"
        titleKey="label.creditCardRecord.description" headerClass="sortable">
        <c:out value="${row.description}"/>
    </display:column>
    
    <display:column sortable="true" sortProperty="amount" style="width:20%;"
        titleKey="label.creditCardRecord.amount" headerClass="sortable">
        <c:out value="${row.amount}"/>
    </display:column>
    
    <display:column sortable="true" sortProperty="createDate" style="width:20%;"
        titleKey="label.creditCardRecord.createDate" headerClass="sortable" property="createDate"
        decorator="com.oyl.ffms.conversion.DefaultDateWrapper" >
    </display:column>
    
    <display:column sortable="true" sortProperty="ctlStatus" style="width:20%;"
        titleKey="label.creditCardRecord.ctlStatus" headerClass="sortable">
        <c:choose>
        	<c:when test='${row.ctlStatus=="C"}' >
        		<s:text name="label.creditCardRecord.ctlStatus.complete"/>
        	</c:when>
        	<c:otherwise>
        		<s:text name="label.creditCardRecord.ctlStatus.draft"/>
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