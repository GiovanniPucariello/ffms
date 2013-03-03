<%@ page contentType="text/html;charset=UTF-8" pageEncoding="GBK" %>
<%@ include file="/WEB-INF/struts-views/common/taglibs-include.jsp"%>    
<html>
<head>
<title><s:text name="title.user" /></title>
<script type="text/javascript" src="<s:url value='/scripts/common.js' includeParams='none'/>"></script>
</head>
<body>
<div id="main">
<div id="mainWrapper">
	
<!-- here is the button area -->
<div class="buttonWrapper">
	<!--change here: your button -->
	<a href="javascript:submitForm('contentForm','<c:url value="/saveEditUser.action"/>');" class="actionButton" target="_self">
	  <span><s:text name="button.edit"/></span>
	</a>
	<a href="javascript:submitForm('backForm','<c:url value="/initEditUser.action"/>');" class="actionButton" target="_self">
	  <span><s:text name="button.reset"/></span>
  	</a>
	<a href="javascript:submitForm('backForm','<c:url value="/initUser.action"/>');" class="actionButton" target="_self">
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
<s:hidden name="param.userOid" />
</form>
<form id="contentForm" name="contentForm" action="" method="post">
<s:hidden name="param.userOid" />
<div class="subTitle"><s:text name="label.user.normal.info" /></div>
  <div>
   <table width="100%"><tbody>
   <tr>
      <td class="labelCell">	
          <span class="label">
          	<span>&nbsp;</span><s:text name="label.user.familyName" /></span>
          	<span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        	<table class="innerTable"><tbody>
        	<tr>
          		<td class="data">
                	<s:property value="param.familyName" />
          		</td>
        	</tr>
        	</tbody></table>
      </div>	     
      </td>
    </tr>
   
    <tr>
      <td class="labelCell">	
          <span class="label">
          	<span>&nbsp;</span><s:text name="label.user.userName" /></span>
          	<span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        	<table class="innerTable"><tbody>
        	<tr>
          		<td class="data">
              		<s:textfield name="param.userName" maxlength="10"
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
          	<span>&nbsp;</span><s:text name="label.user.userAlias" /></span>
          	<span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        	<table class="innerTable"><tbody>
        	<tr>
          		<td class="data">
              		<s:textfield name="param.userAlias" maxlength="10"
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
          	<span>&nbsp;</span><s:text name="label.user.gender" /></span>
          	<span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        	<table class="innerTable"><tbody>
        	<tr>
          		<td class="data">
                	<s:select name="param.gender" list="genderMap" 
                		listKey="key" listValue="value" cssClass="inputBox"/> 
          		</td>
        	</tr>
        	</tbody></table>
      </div>	     
      </td>
    </tr>
    
    <tr>
      <td class="labelCell">	
          <span class="label">
          	<span>&nbsp;</span><s:text name="label.user.phone" /></span>
          	<span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        	<table class="innerTable"><tbody>
        	<tr>
          		<td class="data">
              		<s:textfield name="param.phone" maxlength="11"
                		cssClass="inputBox" size="15" />
          		</td>
        	</tr>
        	</tbody></table>
      </div>	     
      </td>
    </tr>
    
    <tr>
      <td class="labelCell">	
          <span class="label">
          	<span>&nbsp;</span><s:text name="label.user.email" /></span>
          	<span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        	<table class="innerTable"><tbody>
        	<tr>
          		<td class="data">
              		<s:textfield name="param.email" maxlength="25"
                		cssClass="inputBox" size="30" />
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