<%@ page contentType="text/html;charset=UTF-8" pageEncoding="GBK" %>
<%@ include file="/WEB-INF/struts-views/common/taglibs-include.jsp"%>    
<html>
<head>
<title><s:text name="title.bankCard" /></title>
<script type="text/javascript" src="<s:url value='/scripts/common.js' includeParams='none'/>"></script>
<script type="text/javascript" >
	function doCreate()
	{
		if(!doCheck())
			return;
			
		var form = document.forms['contentForm'];
		var url = '<c:url value="/saveAddBankCard.action"/>';
		
		submitForm('contentForm', url);
	}

	function doCheck()
	{
		var oldValue = document.getElementById('balance').value;
		
		if(isNaN(oldValue))
		{
			alert('<s:text name="bankCard.balance.invalid" />');
			document.getElementById('balance').value="0.00";
			return false;
		}
		
		if(oldValue!="")
			document.getElementById('balance').value=parseFloat(oldValue).toFixed(2);
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
	<a href="javascript:doCreate();" class="actionButton" target="_self">
	  <span><s:text name="button.create"/></span>
	</a>
	<a href="javascript:submitForm('resetForm','<c:url value="/initAddBankCard.action"/>');" class="actionButton" target="_self">
	  <span><s:text name="button.reset"/></span>
  	</a>
	<a href="javascript:submitForm('backForm','<c:url value="/initBankCard.action"/>');" class="actionButton" target="_self">
	  <span><s:text name="button.cancel"/></span>
	</a>
</div>
			
<!--here is the banner area-->
<div class="pageTitle"> 
<div class="title">
	<s:text name="label.bankCard.summary" />
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
          	<span>&nbsp;</span><s:text name="label.bankCard.userName" /></span>
          	<span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        	<table class="innerTable"><tbody>
        	<tr>
          		<td class="data">
                	<s:select list="users" listKey="userOid" listValue="userName" 
                		id="users" name="bankCard.userOid" />
          		</td>
        	</tr>
        	</tbody></table>
      </div>	     
      </td>
    </tr>
   
   <tr>
      <td class="labelCell">	
          <span class="label">
          	<span>&nbsp;</span><s:text name="label.bankCard.cardNo" /></span>
          	<span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        	<table class="innerTable"><tbody>
        	<tr>
          		<td class="data">
                	<s:textfield name="bankCard.cardNo" maxlength="30"
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
          	<span>&nbsp;</span><s:text name="label.bankCard.balance" /></span>
          	<span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        	<table class="innerTable"><tbody>
        	<tr>
          		<td class="data">
              		<s:textfield name="bankCard.balance" maxlength="10" id="balance" onblur="javascript:doCheck();"
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