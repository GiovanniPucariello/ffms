<%@ page contentType="text/html;charset=UTF-8" pageEncoding="GBK" %>
<%@ include file="/WEB-INF/struts-views/common/taglibs-include.jsp"%>
<html>
<head>
    <title><s:text name="title.login" /></title>
    
	<script type="text/javascript" src="<s:url value='/scripts/common.js' includeParams='none'/>"></script>
</head>

<body>
<div id="main">
    <form name="formLogin" method="post" action="login.action">
	<h1 align="center" style="color:#0066FF"><s:text name="label.project.name" /></h1>
   	<center>
	<table border=0>
		<tr>
			<td colspan="2"><h2 align="center"><s:text name="label.authentication.system.login" /></h2></td>
		</tr>
		<tr>
			<td ><span class="label"><s:text name="label.authentication.login.id" />:</span></td>
			<td><input type="text" name="<%= com.oyl.ffms.util.CommonConstants.REQUEST_PARAMETER_KEY_LOGIN_ID %>" 
				id="loginId" autocomplete="Off" size="25" value=""></td>
		</tr>
		<tr>
			<td align="right"><span class="label"><s:text name="label.authentication.login.password"/>:</span></td>
			<td><input type="password" name="<%= com.oyl.ffms.util.CommonConstants.REQUEST_PARAMETER_KEY_LOGIN_PASSWD %>" 
				id="password" autocomplete="off" size="25" value=""></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>
				<input type="submit" value="<s:text name="button.login" />" 
					class="button-normal" onclick="return validateInput();">&nbsp;
				<input type="reset" value="<s:text name="button.reset" />" 
					class="button-normal" >
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td><a href="#"><s:text name="label.authentication.forgot.password" /></a></td>
		</tr>
		
	</table>
	</center>
    </form>
</div>

<c:if test="${ not empty ERROR_LOGIN_FAILED }">
	<script language="javascript">
		var msg = "";
		    		    		
    	msg = '<c:out value="${ERROR_LOGIN_FAILED}" />';        	
    								
		alert(msg);
	</script>		
</c:if>
</body>
</html>
