<%@ page contentType="text/html;charset=UTF-8" pageEncoding="GBK" %>
<%@ include file="/WEB-INF/struts-views/common/taglibs-include.jsp"%>    
<html>
<head>
<title><s:text name="title.bankCardRecord" /></title>
<script type="text/javascript" src="<s:url value='/scripts/common.js' includeParams='none'/>"></script>
<%@ include file="/WEB-INF/struts-views/common/jsCalendar.jsp"%>
<script type="text/javascript" >
	function doEdit()
	{
		if(!doCheck())
			return;
			
		var form = document.forms['contentForm'];
		var url = '<c:url value="/saveEditBankCardRecord.action"/>';
		
		submitForm('contentForm', url);
	}

	function doCheck()
	{
		var oldValue = document.getElementById('amount').value;
		
		if(isNaN(oldValue))
		{
			alert('<s:text name="bankCardRecord.amount.invalid" />');
			document.getElementById('amount').value="0.00";
			return false;
		}
		
		if(oldValue!="")
			document.getElementById('amount').value=parseFloat(oldValue).toFixed(2);
		return true;
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
	<a href="javascript:submitForm('resetForm','<c:url value="/initEditBankCardRecord.action"/>');" class="actionButton" target="_self">
	  <span><s:text name="button.reset"/></span>
  	</a>
	<a href="javascript:submitForm('backForm','<c:url value="/initBankCardRecord.action"/>');" class="actionButton" target="_self">
	  <span><s:text name="button.cancel"/></span>
	</a>
</div>
			
<!--here is the banner area-->
<div class="pageTitle"> 
<div class="title">
	<s:text name="label.bankCardRecord.summary" />
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
<s:hidden name="bankCardRecord.bcrOid" id="bcrOid" />
</form>

<form id="contentForm" name="contentForm" action="" method="post">
<s:hidden name="bankCardRecord.bcrOid" id="bcrOid" />
  <div>
   <table width="100%"><tbody>
   <tr>
      <td class="labelCell">	
          <span class="label">
          	<span>&nbsp;</span><s:text name="label.bankCardRecord.bcrType" /></span>
          	<span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        	<table class="innerTable"><tbody>
        	<tr>
          		<td class="data">
                	<s:select list="bcrTypes" listKey="key" listValue="value" 
              			name="bankCardRecord.bcrType" />
          		</td>
        	</tr>
        	</tbody></table>
      </div>	     
      </td>
    </tr>
   
   <tr>
      <td class="labelCell">	
          <span class="label">
          	<span>&nbsp;</span><s:text name="label.bankCardRecord.description" /></span>
          	<span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        	<table class="innerTable"><tbody>
        	<tr>
          		<td class="data">
                	<s:textfield name="bankCardRecord.description" maxlength="30"
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
          	<span>&nbsp;</span><s:text name="label.bankCardRecord.amount" /></span>
          	<span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        	<table class="innerTable"><tbody>
        	<tr>
          		<td class="data">
              		<s:textfield name="bankCardRecord.amount" maxlength="10" id="amount" onblur="javascript:doCheck();"
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
          	<span>&nbsp;</span><s:text name="label.bankCardRecord.createDate" /></span>
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
          	<span>&nbsp;</span><s:text name="label.bankCardRecord.createHour" /></span>
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
          	<span>&nbsp;</span><s:text name="label.bankCardRecord.createMinute" /></span>
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
   
    </tbody></table>
  </div>
  
</form>
</div>
</div>
</div>
</div>
</body>
</html>