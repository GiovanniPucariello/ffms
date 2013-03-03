<%@ page contentType="text/html;charset=UTF-8" pageEncoding="GBK" %>
<%@ include file="/WEB-INF/struts-views/common/taglibs-include.jsp"%>    
<html>
<head>
<title><s:text name="title.bankCard" /></title>
<script type="text/javascript" src="<s:url value='/scripts/common.js' includeParams='none'/>"></script>
<script type="text/javascript" >
	function doEdit()
	{
		if(!doCheck())
			return;
			
		var form = document.forms['contentForm'];
		var url = '<c:url value="/saveEditCreditCard.action"/>';
		
		submitForm('contentForm', url);
	}

	function doCheck()
	{
		var oldQuota = document.getElementById('quota').value;
		var oldDebt  = document.getElementById('debt').value;
		var oldPoint = document.getElementById('point').value;
		var oldPointCondition  = document.getElementById('pointCondition').value;
		
		if(isNaN(oldQuota))
		{
			alert('<s:text name="creditCard.quota.invalid" />');
			document.getElementById('quota').value="0.00";
			return false;
		}
		
		if(isNaN(oldDebt))
		{
			alert('<s:text name="creditCard.debt.invalid" />');
			document.getElementById('debt').value="0.00";
			return false;
		}
		
		if(isNaN(oldPoint))
		{
			alert('<s:text name="creditCard.point.invalid" />');
			document.getElementById('point').value="0";
			return false;
		}
		
		if(isNaN(oldPointCondition))
		{
			alert('<s:text name="creditCard.pointCondition.invalid" />');
			document.getElementById('pointCondition').value="0.00";
			return false;
		}
		
		if(oldQuota!="")
		{
			document.getElementById('quota').value=parseFloat(oldQuota).toFixed(2);
		}
		
		if(oldPoint!="")
		{
			document.getElementById('point').value=parseFloat(oldPoint).toFixed(0);
		}
		
		if(oldDebt!="")
		{
			document.getElementById('debt').value=parseFloat(oldDebt).toFixed(2);
		}
		
		if(oldPointCondition!="")
		{
			document.getElementById('pointCondition').value=parseFloat(oldPointCondition).toFixed(2);
		}
			
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
	<a href="javascript:submitForm('resetForm','<c:url value="/initEditCreditCard.action"/>');" class="actionButton" target="_self">
	  <span><s:text name="button.reset"/></span>
  	</a>
	<a href="javascript:submitForm('backForm','<c:url value="/initCreditCard.action"/>');" class="actionButton" target="_self">
	  <span><s:text name="button.cancel"/></span>
	</a>
</div>
			
<!--here is the banner area-->
<div class="pageTitle"> 
<div class="title">
	<s:text name="label.creditCard.summary" />
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
<s:hidden name="creditCard.ccOid" />
</form>
<form id="contentForm" name="contentForm" action="" method="post">
<s:hidden name="creditCard.ccOid" />
  <div>
   <table width="100%"><tbody>
   <tr>
      <td class="labelCell">	
          <span class="label">
          	<span>&nbsp;</span><s:text name="label.creditCard.userName" /></span>
          	<span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        	<table class="innerTable"><tbody>
        	<tr>
          		<td class="data">
                	<s:select list="users" listKey="userOid" listValue="userName" 
                		id="users" name="creditCard.userOid" />
          		</td>
        	</tr>
        	</tbody></table>
      </div>	     
      </td>
    </tr>
   
   <tr>
      <td class="labelCell">	
          <span class="label">
          	<span>&nbsp;</span><s:text name="label.creditCard.cardNo" /></span>
          	<span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        	<table class="innerTable"><tbody>
        	<tr>
          		<td class="data">
                	<s:textfield name="creditCard.cardNo" maxlength="30"
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
          	<span>&nbsp;</span><s:text name="label.creditCard.quota" /></span>
          	<span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        	<table class="innerTable"><tbody>
        	<tr>
          		<td class="data">
              		<s:textfield name="creditCard.quota" maxlength="10" id="quota" onblur="javascript:doCheck();"
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
          	<span>&nbsp;</span><s:text name="label.creditCard.debt" /></span>
          	<span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        	<table class="innerTable"><tbody>
        	<tr>
          		<td class="data">
              		<s:textfield name="creditCard.debt" maxlength="10" id="debt" onblur="javascript:doCheck();"
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
          	<span>&nbsp;</span><s:text name="label.creditCard.point" /></span>
          	<span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        	<table class="innerTable"><tbody>
        	<tr>
          		<td class="data">
              		<s:textfield name="creditCard.point" maxlength="10" id="point" onblur="javascript:doCheck();"
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
          	<span>&nbsp;</span><s:text name="label.creditCard.pointCondition" /></span>
          	<span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        	<table class="innerTable"><tbody>
        	<tr>
          		<td class="data">
              		<s:textfield name="creditCard.pointCondition" maxlength="10" id="pointCondition" onblur="javascript:doCheck();"
                		cssClass="inputBox" size="10" />
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