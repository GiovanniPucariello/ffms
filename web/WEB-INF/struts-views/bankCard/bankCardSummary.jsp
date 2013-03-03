<%@ page contentType="text/html;charset=UTF-8" pageEncoding="GBK" %>
<%@ include file="/WEB-INF/struts-views/common/taglibs-include.jsp"%>
<html>
<head>
<title><s:text name="title.bankCard" /></title>
<link rel="stylesheet" href="<s:url value='/styles/%{#session.layoutTheme}/displaytag.css' includeParams='none'/>" type="text/css">

<script type="text/javascript" src="<s:url value='/scripts/common.js' includeParams='none'/>"></script>
<%@ include file="/WEB-INF/struts-views/common/jsCalendar.jsp"%>

<script type="text/javascript">
	function doEdit()
	{
		var form = document.forms['listForm'];
		var url = '<c:url value="/initEditBankCard.action"/>';
		
		if(!isOnlyOneSelected(form, 'resOids'))
		{
			alert('<s:text name="bankCard.select.one.edit"/>');
			return;
		}
		
		var oid = getValueOfFirstSelectedCheckBox(form, 'resOids');
		
		document.getElementById('bcOid').value = oid;
		
		submitForm('backForm', url);
	}

	function doDelete()
	{	
		var form = document.forms['listForm'];
		var url = '<c:url value="/deleteBankCard.action"/>';
		
		if (!checkIds(form,'resOids'))
		{
			alert('<s:text name="bankCard.select.no.one"/>');
			return;
		}
		
		submitFormWithConfirm('listForm',url,'<s:text name="bankCard.delete.confirm"/>');
	}
	
	function doListRecord(bcOid)
	{
		var url = '<c:url value="/initBankCardRecord.action"/>';
	
		document.getElementById('bcOid2').value = bcOid;
		
		submitForm('backForm2', url);
	}
	
</script>
</head>

<body>
<div id="main">
<div id="mainWrapper">
 
<div class="buttonWrapper">
   <a href='<c:url value="initAddBankCard.action"/>' class="actionButton" target="_self">
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
	<s:text name="label.bankCard.summary" />				
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
<s:hidden name="bankCard.bcOid" id="bcOid" />
</form>

<form id="backForm2" name="backForm2" onsubmit="return true;" action="" method="post">
<s:hidden name="bankCardRecord.bcOid" id="bcOid2" />
</form>

<form id="listForm" name="listForm" onsubmit="return true;" action="" method="post">
<display:table htmlId="listTable" name="paginatedList" id="row" class="tablestyle1" requestURI="initBankCard.action?keepSp=Y" 
	excludedParams="keepSp" >
    <display:column  class="recordselect"
		title="<input type='checkbox' name='chkAllIDs' onclick='toggleAll(\"listForm\",\"resOids\");'/>">
        <input type="checkbox" id="resOids" name="resOids" value="<c:out value="${row.bcOid}"/>" />
    </display:column>

    <display:column class="recordnumber" >
        <c:out value="${pageCount+row_rowNum}" />
    </display:column>
    
    <display:column sortable="true" sortProperty="cardNo" style="width:50%;"
        titleKey="label.bankCard.cardNo" headerClass="sortable">
        <a href="javascript:doListRecord(<c:out value='${row.bcOid}' />);" ><c:out value="${row.cardNo}"/></a>
    </display:column>
    
    <display:column sortable="true" sortProperty="userName" style="width:25%;"
        titleKey="label.bankCard.userName" headerClass="sortable">
        <c:out value="${row.userName}"/>
    </display:column>
    
    <display:column sortable="true" sortProperty="balance" style="width:25%;"
        titleKey="label.bankCard.balance" headerClass="sortable">
        <c:out value="${row.balance}"/>
    </display:column>
</display:table>

</form>
</div>
</div>
</div>
</div>
</body>
</html>