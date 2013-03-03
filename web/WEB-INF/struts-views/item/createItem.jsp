<%@ page contentType="text/html;charset=UTF-8" pageEncoding="GBK" %>
<%@ include file="/WEB-INF/struts-views/common/taglibs-include.jsp"%>    
<html>
<head>
<title><s:text name="title.item" /></title>
<script type="text/javascript" src="<s:url value='/scripts/common.js' includeParams='none'/>"></script>
<%@ include file="/WEB-INF/struts-views/common/jsCalendar.jsp"%>
<script type="text/javascript" >
	function init()
 	{
 		var isFromCreditCard = '<s:property value="isFromCreditCard" />';
 		
 		if ("Y" == isFromCreditCard)
 		{
 			document.getElementById('isFromCreditCard').checked = true;
 		}
 		
 		var isFromBankCard = '<s:property value="isFromBankCard" />';
        
        if ("Y" == isFromBankCard)
        {
            document.getElementById('isFromBankCard').checked = true;
        }
 		
 		sendRequest();
 	}

	function doCreate()
	{
		if(!doCheck())
			return;
			
		var form = document.forms['contentForm'];
		var url = '<c:url value="/saveAddItem.action"/>';
		
		submitForm('contentForm', url);
	}
	
	function isShowCreditCardList()
	{
		var isFromCreditCard = document.getElementById('isFromCreditCard');

		if (isFromCreditCard.checked)
		{
			document.getElementById("creditCards").style.display = "";
		}
		else
		{
			document.getElementById("creditCards").style.display = "none";
		}
	}
	
	function isShowBankCardList()
    {
        var isFromBankCard = document.getElementById('isFromBankCard');

        if (isFromBankCard.checked)
        {
            document.getElementById("bankCards").style.display = "";
        }
        else
        {
            document.getElementById("bankCards").style.display = "none";
        }
    }

	function doCheck()
	{
        if (document.getElementById('isFromCreditCard').checked && 
            document.getElementById('isFromBankCard').checked)
        {
            alert('<s:text name="item.fail.both.bank.and.credit.card"/>');
            return false;
        }
        
		var oldValue = document.getElementById('cost').value;
		
		if(isNaN(oldValue))
		{
			alert('<s:text name="item.itemCost.invalid" />');
			document.getElementById('cost').value="0.00";
			return false;
		}
		
		if(oldValue!="")
			document.getElementById('cost').value=parseFloat(oldValue).toFixed(2);
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
      			var type = xmlHttp.responseXML.getElementsByTagName('categoryType').item(0).firstChild.nodeValue;

	      		if('<%=com.oyl.ffms.util.CommonConstants.VALUE_CATEGORY_TYPE_NORMAL%>'==type)
	      		{
	      			document.getElementById('quantity').style.display="";
	      		}
	      		else if('<%=com.oyl.ffms.util.CommonConstants.VALUE_CATEGORY_TYPE_SPECIAL%>'==type)
	      		{
	      			document.getElementById('quantity').style.display="none";
	      		}
	      		
                isShowCreditCardList();
                isShowBankCardList();
			}
      	}
      	
 		var url = '<c:url value="/ajax/requestCategoryType.action"/>';

		var param = "paramOid="+document.getElementById('categoryOid').value;
		
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
	
<!-- here is the button area -->
<div class="buttonWrapper">
	<!--change here: your button -->
	<a href="javascript:doCreate();" class="actionButton" target="_self">
	  <span><s:text name="button.create"/></span>
	</a>
	<a href="javascript:submitForm('resetForm','<c:url value="/initAddItem.action"/>');" class="actionButton" target="_self">
	  <span><s:text name="button.reset"/></span>
  	</a>
	<a href="javascript:submitForm('backForm','<c:url value="/initItem.action"/>');" class="actionButton" target="_self">
	  <span><s:text name="button.cancel"/></span>
	</a>
</div>
			
<!--here is the banner area-->
<div class="pageTitle"> 
<div class="title">
	<s:text name="label.user.summary" />
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
</form>
<form id="contentForm" name="contentForm" action="" method="post">
  <div>
   <table width="100%"><tbody>
   <tr>
      <td class="labelCell">	
          <span class="label">
          	<span>&nbsp;</span><s:text name="label.item.categoryName" /></span>
          	<span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        	<table class="innerTable"><tbody>
        	<tr>
          		<td class="data">
                	<s:select list="categories" listKey="categoryOid" listValue="categoryName" 
                		id="categoryOid" onchange="javascript:sendRequest();"
              			name="param.categoryOid" />
          		</td>
        	</tr>
        	</tbody></table>
      </div>	     
      </td>
    </tr>
   
   <tr>
      <td class="labelCell">	
          <span class="label">
          	<span>&nbsp;</span><s:text name="label.item.itemDesc" /></span>
          	<span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        	<table class="innerTable"><tbody>
        	<tr>
          		<td class="data">
                	<s:textfield name="param.itemDesc" maxlength="25"
                		cssClass="itemDesc" size="30" />
          		</td>
        	</tr>
        	</tbody></table>
      </div>	     
      </td>
    </tr>
    
    <tr>
      <td class="labelCell">	
          <span class="label">
          	<span>&nbsp;</span><s:text name="label.item.createDate" /></span>
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
          	<span>&nbsp;</span><s:text name="label.item.createHour" /></span>
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
          	<span>&nbsp;</span><s:text name="label.item.createMinute" /></span>
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
          	<span>&nbsp;</span><s:text name="label.item.itemCost" /></span>
          	<span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        	<table class="innerTable"><tbody>
        	<tr>
          		<td class="data">
              		<s:textfield name="param.itemCost" maxlength="10" id="cost" onblur="javascript:doCheck();"
                		cssClass="inputBox" size="10" />
          		</td>
        	</tr>
        	</tbody></table>
      </div>	     
      </td>
    </tr>
    
    <tr id="quantity" >
      <td class="labelCell">	
          <span class="label">
          	<span>&nbsp;</span><s:text name="label.item.itemQuantity" /></span>
          	<span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        	<table class="innerTable"><tbody>
        	<tr>
          		<td class="data">
              		<s:select list="itemQuantity" name="param.itemQuantity" />
          		</td>
        	</tr>
        	</tbody></table>
      </div>	     
      </td>
    </tr>
    
    
    <tr id="fromCreditCard">
      <td class="labelCell">	
          <span class="label">
          	<span>&nbsp;</span><s:text name="label.item.fromCreditCard" /></span>
          	<span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        	<table class="innerTable"><tbody>
        	<tr>
          		<td class="data">
              		<input type="checkbox" name="isFromCreditCard" id="isFromCreditCard" value="Y" onChange="javascript:isShowCreditCardList();"/>
          		</td>
        	</tr>
        	</tbody></table>
      </div>	     
      </td>
    </tr>
    
    
    <tr id="creditCards" style="display:none;">
      <td class="labelCell">
          <span class="label">
          	<span>&nbsp;</span><s:text name="label.item.creditCardNo" /></span>
          	<span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        	<table class="innerTable"><tbody>
        	<tr>
          		<td class="data">
              		<s:select list="creditCards" listKey="key" listValue="value" 
              			name="param.ccOid" />
          		</td>
        	</tr>
        	</tbody></table>
      </div>	     
      </td>
    </tr>
    
    
    <tr id="fromBankCard">
      <td class="labelCell">    
          <span class="label">
            <span>&nbsp;</span><s:text name="label.item.fromBankCard" /></span>
            <span class="seperator">:</span>
      </td>
      <td class="data"> 
      <div class="group">
            <table class="innerTable"><tbody>
            <tr>
                <td class="data">
                    <input type="checkbox" name="isFromBankCard" id="isFromBankCard" value="Y" onChange="javascript:isShowBankCardList();"/>
                </td>
            </tr>
            </tbody></table>
      </div>         
      </td>
    </tr>
    
    
    <tr id="bankCards" style="display:none;">
      <td class="labelCell">
          <span class="label">
            <span>&nbsp;</span><s:text name="label.item.bankCardNo" /></span>
            <span class="seperator">:</span>
      </td>
      <td class="data"> 
      <div class="group">
            <table class="innerTable"><tbody>
            <tr>
                <td class="data">
                    <s:select list="bankCards" listKey="key" listValue="value" 
                        name="param.bcOid" />
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
init();
</script>
</body>
</html>