<%@ page contentType="text/html;charset=UTF-8" pageEncoding="GBK" %>
<%@ include file="/WEB-INF/struts-views/common/taglibs-include.jsp"%>
<html>
<head>
<title><s:text name="title.creditCard" /></title>
<link rel="stylesheet" href="<s:url value='/styles/%{#session.layoutTheme}/displaytag.css' includeParams='none'/>" type="text/css">

<script type="text/javascript" src="<s:url value='/scripts/common.js' includeParams='none'/>"></script>
<%@ include file="/WEB-INF/struts-views/common/jsCalendar.jsp"%>

<script type="text/javascript">
	function doEdit()
	{
		var form = document.forms['listForm'];
		var url = '<c:url value="/initEditCreditCard.action"/>';
		
		if(!isOnlyOneSelected(form, 'resOids'))
		{
			alert('<s:text name="creditCard.select.one.edit"/>');
			return;
		}
		
		var oid = getValueOfFirstSelectedCheckBox(form, 'resOids');
		
		document.getElementById('ccOid').value = oid;
		
		submitForm('backForm', url);
	}

	function doDelete()
	{	
		var form = document.forms['listForm'];
		var url = '<c:url value="/deleteCreditCard.action"/>';
		
		if (!checkIds(form,'resOids'))
		{
			alert('<s:text name="creditCard.select.no.one"/>');
			return;
		}
		
		submitFormWithConfirm('listForm',url,'<s:text name="creditCard.delete.confirm"/>');
	}
	
	function doListRecord(ccOid)
	{
		var url = '<c:url value="/initCreditCardRecord.action"/>';
	
		document.getElementById('ccOid2').value = ccOid;
		
		submitForm('backForm2', url);
	}
	
</script>
</head>

<body>
<div id="main">
<div id="mainWrapper">
 
<div class="buttonWrapper">
   <a href='<c:url value="initAddCreditCard.action"/>' class="actionButton" target="_self">
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
	<s:text name="label.creditCard.summary" />				
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

<!-- list area -->
<div class="searchResult">
<div class="content">
<form id="backForm" name="backForm" onsubmit="return true;" action="" method="post">
<s:hidden name="creditCard.ccOid" id="ccOid" />
</form>

<form id="backForm2" name="backForm2" onsubmit="return true;" action="" method="post">
<s:hidden name="creditCardRecord.ccOid" id="ccOid2" />
</form>

<form id="listForm" name="listForm" onsubmit="return true;" action="" method="post">
<display:table htmlId="listTable" name="paginatedList" id="row" class="tablestyle1" requestURI="initCreditCard.action?keepSp=Y" 
	excludedParams="keepSp" >
	
    <display:column  class="recordselect"
		title="<input type='checkbox' name='chkAllIDs' onclick='toggleAll(\"listForm\",\"resOids\");'/>">
        <input type="checkbox" id="resOids" name="resOids" value="<c:out value="${row.ccOid}"/>" />
    </display:column>


    <display:column class="recordnumber" >
        <c:out value="${pageCount+row_rowNum}" />
    </display:column>
    
    
    <display:column sortable="true" sortProperty="cardNo" style="width:40%;"
        titleKey="label.creditCard.cardNo" headerClass="sortable">
        <a href="javascript:doListRecord(<c:out value='${row.ccOid}' />);" ><c:out value="${row.cardNo}"/></a>
    </display:column>
    
    
    <display:column sortable="true" sortProperty="userName" style="width:15%;"
        titleKey="label.creditCard.userName" headerClass="sortable">
        <c:out value="${row.userName}"/>
    </display:column>
    
    
    <display:column sortable="false" style="width:15%;"
        titleKey="label.creditCard.quota" headerClass="sortable">
        <c:out value="${row.quota}"/>
    </display:column>
    
    
    <display:column sortable="false" style="width:15%;"
        titleKey="label.creditCard.debt" headerClass="sortable">
        <c:out value="${row.debt}"/>
    </display:column>
    
    
    <display:column sortable="false" style="width:15%;"
        titleKey="label.creditCard.point" headerClass="sortable">
        <c:out value="${row.point}"/>
    </display:column>
</display:table>

</form>
</div>
</div>
</div>
</div>
</body>
</html>