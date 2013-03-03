<%@ page contentType="text/html;charset=UTF-8" pageEncoding="GBK" %>
<%@ include file="/WEB-INF/struts-views/common/taglibs-include.jsp"%>    
<html>
<head>
<title><s:text name="title.salaryRecord" /></title>
<script type="text/javascript" src="<s:url value='/scripts/common.js' includeParams='none'/>"></script>
<%@ include file="/WEB-INF/struts-views/common/jsCalendar.jsp"%>

<script type="text/javascript" >
	var flag = true;

	function doEdit()
	{
		if(!doCheck())
			return;
			
		var form = document.forms['contentForm'];
		var url = '<c:url value="/saveEditSalaryRecord.action"/>';
		
		submitForm('contentForm', url);
	}
	

	function doCheck()
	{
		var oldValue = document.getElementById('amount').value;
		
		if(isNaN(oldValue))
		{
			alert('<s:text name="salaryRecord.amount.invalid" />');
			document.getElementById('amount').value="0.00";
			return false;
		}
		
		if(oldValue!="")
			document.getElementById('amount').value=parseFloat(oldValue).toFixed(2);
		return true;
	}
	
 	function sendRequest()
 	{
 		var xmlHttp = getXmlHttp();
 		xmlHttp.onreadystatechange=function()
      	{
      		if (xmlHttp.readyState == 4 &&
        			xmlHttp.status == 200)
			{
			
				var bankCardSelect = document.getElementById("bankCardList");
				var length = bankCardSelect.options.length -1;
				for (var i = length; i >= 0; i--)
				{
					bankCardSelect.options[i] = null;
				}
			
      			var cards = xmlHttp.responseXML.getElementsByTagName('card');
      			
      			for (var i=0; i<cards.length; i++)
      			{
      				var value = cards[i].firstChild.nodeValue;
      				
      				var oid = value.split("---")[0];
      				var desc= value.split("---")[1];
      				
      				bankCardSelect.options.add(new Option(desc, oid));
      			}

	      		if (flag)
				{
					doSelect();
				}
			}
      	}
      	
 		var url = '<c:url value="/ajax/requestBankCards.action"/>';

		var param = "paramOid="+document.getElementById('userOid').value;
		
 		xmlHttp.open('POST', url, true);
 		xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
 		xmlHttp.setRequestHeader("Content-length", param.length);
      	xmlHttp.setRequestHeader("Connection", "close");
 		xmlHttp.send(param);
 	}
 	
 	function doCheckIsSaveToBankCard()
 	{
 		var oldvalue = '<s:property value="isSaveToBankCard" />';
 		
 		if ("Y" == oldvalue)
 		{
 			var isSaveToBankCard = document.getElementById('isSaveToBankCard').checked = true;
 			
 			checkSave();
 		}
 		else
 		{
 			flag = false;
 		}
 	}
 	
 	
 	function checkSave()
	{
		var isSaveToBankCard = document.getElementById('isSaveToBankCard');
		
		if (isSaveToBankCard.checked)
		{
			sendRequest();
			
			document.getElementById("bankCards").style.display = "";
		}
		else
		{
			document.getElementById("bankCards").style.display = "none";
		}
	}
 	
 	
 	function doSelect()
 	{
 		flag = false;
 		
 		var oldCard = '<s:property value="salaryRecord.bcOid" />';
 		
 		if ('' == oldCard)
 		{
 			return;
		}
		
		
 		var bankCardSelect = document.getElementById("bankCardList");
 		
 		var length = bankCardSelect.options.length;
 		
 		for (var i=0; i< length; i++)
 		{
 			if (bankCardSelect.options[i].value == oldCard)
 			{
 				bankCardSelect.options[i].selected = true;
 				
 				return;
 			}
 		}
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
	<a href="javascript:submitForm('resetForm','<c:url value="/initEditSalaryRecord.action"/>');" class="actionButton" target="_self">
	  <span><s:text name="button.reset"/></span>
  	</a>
	<a href="javascript:submitForm('backForm','<c:url value="/initSalaryRecord.action"/>');" class="actionButton" target="_self">
	  <span><s:text name="button.cancel"/></span>
	</a>
</div>
			
<!--here is the banner area-->
<div class="pageTitle"> 
<div class="title">
	<s:text name="label.salaryRecord.summary" />
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
<s:hidden name="salaryRecord.srOid" />
</form>

<form id="contentForm" name="contentForm" action="" method="post">
<s:hidden name="salaryRecord.srOid" />
  <div>
   <table width="100%"><tbody>
   <tr>
      <td class="labelCell">	
          <span class="label">
          	<span>&nbsp;</span><s:text name="label.salaryRecord.userName" /></span>
          	<span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        	<table class="innerTable"><tbody>
        	<tr>
          		<td class="data">
                	<s:select list="users" listKey="userOid" listValue="userName" 
                		id="userOid" onchange="javascript:sendRequest();"
              			name="salaryRecord.userOid" />
          		</td>
        	</tr>
        	</tbody></table>
      </div>
      </td>
   </tr>
   
   <tr>
      <td class="labelCell">	
          <span class="label">
          	<span>&nbsp;</span><s:text name="label.salaryRecord.srType" /></span>
          	<span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        	<table class="innerTable"><tbody>
        	<tr>
          		<td class="data">
                	<s:select list="srTypes" listKey="key" listValue="value" 
              			name="salaryRecord.srType" />
          		</td>
        	</tr>
        	</tbody></table>
      </div>	     
      </td>
    </tr>
    
    <tr>
      <td class="labelCell">	
          <span class="label">
          	<span>&nbsp;</span><s:text name="label.salaryRecord.description" /></span>
          	<span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        	<table class="innerTable"><tbody>
        	<tr>
          		<td class="data">
              		<s:textfield name="salaryRecord.description" maxlength="30"
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
          	<span>&nbsp;</span><s:text name="label.salaryRecord.amount" /></span>
          	<span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        	<table class="innerTable"><tbody>
        	<tr>
          		<td class="data">
              		<s:textfield name="salaryRecord.amount" maxlength="10" id="amount" onblur="javascript:doCheck();"
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
          	<span>&nbsp;</span><s:text name="label.salaryRecord.createDate" /></span>
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
          	<span>&nbsp;</span><s:text name="label.salaryRecord.createHour" /></span>
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
          	<span>&nbsp;</span><s:text name="label.salaryRecord.createMinute" /></span>
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
    
    <tr>
      <td class="labelCell">	
          <span class="label">
          	<span>&nbsp;</span><s:text name="label.salaryRecord.saveToBankCard" /></span>
          	<span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        	<table class="innerTable"><tbody>
        	<tr>
          		<td class="data">
              		<input type="checkbox" name="isSaveToBankCard" id="isSaveToBankCard" value="Y" onChange="javascript:checkSave();"/>
          		</td>
        	</tr>
        	</tbody></table>
      </div>	     
      </td>
    </tr>
    
    <tr id="bankCards" style="display:none;">
      <td class="labelCell">	
          <span class="label">
          	<span>&nbsp;</span><s:text name="label.salaryRecord.bankCardNo" /></span>
          	<span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        	<table class="innerTable"><tbody>
        	<tr>
          		<td class="data">
              		<select id="bankCardList" name="salaryRecord.bcOid" >
              		</select>
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
doCheckIsSaveToBankCard();
</script>
</body>
</html>