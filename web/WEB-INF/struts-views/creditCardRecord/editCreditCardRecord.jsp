<%@ page contentType="text/html;charset=UTF-8" pageEncoding="GBK" %>
<%@ include file="/WEB-INF/struts-views/common/taglibs-include.jsp"%>    
<html>
<head>
<title><s:text name="title.creditCardRecord" /></title>
<script type="text/javascript" src="<s:url value='/scripts/common.js' includeParams='none'/>"></script>
<%@ include file="/WEB-INF/struts-views/common/jsCalendar.jsp"%>
<script type="text/javascript" >
	function doEdit()
	{
		if(!doCheck())
			return;
			
		var form = document.forms['contentForm'];
		var url = '<c:url value="/saveEditCreditCardRecord.action"/>';
		
		submitForm('contentForm', url);
	}

	function doCheck()
	{
		var oldValue = document.getElementById('amount').value;
		
		if(isNaN(oldValue))
		{
			alert('<s:text name="creditCardRecord.amount.invalid" />');
			document.getElementById('amount').value="0.00";
			return false;
		}
		
		if(oldValue!="")
			document.getElementById('amount').value=parseFloat(oldValue).toFixed(2);
		return true;
	}
	
	function checkSave()
	{
		var fromBankCard = document.getElementById('fromBankCard');
		
		if (fromBankCard.checked)
		{
			document.getElementById("bankCards").style.display = "";
		}
		else
		{
			document.getElementById("bankCards").style.display = "none";
		}
	}
	
	function checkType()
	{
		var ccrType = document.getElementById('ccrType').value;
		
		if (ccrType == 'I')
		{
			document.getElementById("isCheckBox").style.display = "";
			checkSave();
		}
		else
		{
			document.getElementById("isCheckBox").style.display = "none";
			document.getElementById("bankCards").style.display = "none";
		}
	}
	
	function checkall()
	{
		var fromBankCard = '<s:property value="fromBankCard" />';
		
		if ('Y' == fromBankCard)
		{
			document.getElementById('fromBankCard').checked = true;
		}
		
		checkSave();
		
		checkType();
	}
	
</script>
</head>
<body>
<div id="main">
<div id="mainWrapper">
	
<!-- here is the button area -->
<div class="buttonWrapper">
	<!--change here: your button -->
	<a href="javascript:doEdit();" class="actionButton" target="_self">
	  <span><s:text name="button.edit"/></span>
	</a>
	<a href="javascript:submitForm('resetForm','<c:url value="/initEditCreditCardRecord.action"/>');" class="actionButton" target="_self">
	  <span><s:text name="button.reset"/></span>
  	</a>
	<a href="javascript:submitForm('backForm','<c:url value="/initCreditCardRecord.action"/>');" class="actionButton" target="_self">
	  <span><s:text name="button.cancel"/></span>
	</a>
</div>
			
<!--here is the banner area-->
<div class="pageTitle"> 
<div class="title">
	<s:text name="label.creditCardRecord.summary" />
</div>
</div>

<!-- here is message area -->
<div class="info">
	<span class="errorMessage">
    <s:actionerror />
    <s:actionmessage />
    <s:fielderror />	
	</span>
</div>							

<div class="dataWrapper">
<div class="inputForm">
<form id="backForm" name="backForm" action="" method="post">
<input type="hidden" name="<%= com.oyl.base.util.CommonConstants.REQ_PARAMETER_KEEP_SEARCH_CONDITION %>" value="Y"/>
</form>

<form id="resetForm" name="resetForm" action="" method="post">
<s:hidden name="creditCardRecord.ccrOid" id="ccrOid" />
</form>

<form id="contentForm" name="contentForm" action="" method="post">
<s:hidden name="creditCardRecord.ccrOid" id="ccrOid" />
  <div>
   <table width="100%"><tbody>
   <tr>
      <td class="labelCell">	
          <span class="label">
          	<span>&nbsp;</span><s:text name="label.creditCardRecord.ccrType" /></span>
          	<span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        	<table class="innerTable"><tbody>
        	<tr>
          		<td class="data">
                	<s:select list="ccrTypes" listKey="key" listValue="value" id="ccrType" onchange="javascript:checkType();"
              			name="creditCardRecord.ccrType" />
          		</td>
        	</tr>
        	</tbody></table>
      </div>	     
      </td>
    </tr>
   
   <tr>
      <td class="labelCell">	
          <span class="label">
          	<span>&nbsp;</span><s:text name="label.creditCardRecord.description" /></span>
          	<span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        	<table class="innerTable"><tbody>
        	<tr>
          		<td class="data">
                	<s:textfield name="creditCardRecord.description" maxlength="30"
                		cssClass="inputBox" size="30" />
          		</td>
        	</tr>
        	</tbody></table>
      </div>	     
      </td>
    </tr>
    
    
    <tr>
      <td class="labelCell">	
          <span class="label">
          	<span>&nbsp;</span><s:text name="label.creditCardRecord.amount" /></span>
          	<span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        	<table class="innerTable"><tbody>
        	<tr>
          		<td class="data">
              		<s:textfield name="creditCardRecord.amount" maxlength="10" id="amount" onblur="javascript:doCheck();"
                		cssClass="inputBox" size="10" />
          		</td>
        	</tr>
        	</tbody></table>
      </div>	     
      </td>
    </tr>
    
    <tr>
      <td class="labelCell">	
          <span class="label">
          	<span>&nbsp;</span><s:text name="label.creditCardRecord.createDate" /></span>
          	<span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        	<table class="innerTable"><tbody>
        	<tr>
          		<td class="data">
                	<input type="text" name="paramDate" id="createDate" style="width:80px;" readonly
						value="<fmt:formatDate value="${paramDate}" type="date" pattern="yyyy-MM-dd"/>">	
					<span id="dateCalendarFromDate" style="cursor:hand;" onClick="return showCalendar('createDate', '%Y-%m-%d', null, true);">
						<img src="images/default/iconCalendar.gif">
					</span>	  
          		</td>
        	</tr>
        	</tbody></table>
      </div>	     
      </td>
    </tr>
    
    <tr>
      <td class="labelCell">	
          <span class="label">
          	<span>&nbsp;</span><s:text name="label.creditCardRecord.createHour" /></span>
          	<span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        	<table class="innerTable"><tbody>
        	<tr>
          		<td class="data">
              		<s:select list="hourList" name="paramHour" />
          		</td>
        	</tr>
        	</tbody></table>
      </div>	     
      </td>
    </tr>
    
    <tr>
      <td class="labelCell">	
          <span class="label">
          	<span>&nbsp;</span><s:text name="label.creditCardRecord.createMinute" /></span>
          	<span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        	<table class="innerTable"><tbody>
        	<tr>
          		<td class="data">
              		<s:select list="minuteList" name="paramMinute" />
          		</td>
        	</tr>
        	</tbody></table>
      </div>	     
      </td>
    </tr>
    
    
    <tr id="isCheckBox" style="display:none;">
      <td class="labelCell">	
          <span class="label">
          	<span>&nbsp;</span><s:text name="label.creditCardRecord.fromBankCard" /></span>
          	<span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        	<table class="innerTable"><tbody>
        	<tr>
          		<td class="data">
              		<input type="checkbox" name="fromBankCard" id="fromBankCard" value="Y" onChange="javascript:checkSave();"/>
          		</td>
        	</tr>
        	</tbody></table>
      </div>	     
      </td>
    </tr>
    
    <tr id="bankCards" style="display:none;">
      <td class="labelCell">	
          <span class="label">
          	<span>&nbsp;</span><s:text name="label.creditCardRecord.bankCardNo" /></span>
          	<span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        	<table class="innerTable"><tbody>
        	<tr>
          		<td class="data">
              		<s:select list="bankCardNos" listKey="key" listValue="value" 
              			name="creditCardRecord.bcOid" />
          		</td>
        	</tr>
        	</tbody></table>
      </div>	     
      </td>
    </tr>
   
    </tbody></table>
  </div>
  
</form>
</div>
</div>
</div>
</div>
<script type="text/javascript" >
checkall();
</script>
</body>
</html>