<%@ page contentType="text/html;charset=UTF-8" pageEncoding="GBK" %>
<%@ include file="/WEB-INF/struts-views/common/taglibs-include.jsp"%>
<html>
<head>
	<title><s:text name="title.myprofile" /></title>
	
	<script type="text/javascript" src="<s:url value='/scripts/common.js' includeParams='none'/>"></script>
	
	<script type="text/javascript" >
		function changePwd()
		{
			var flag = document.forms["contentForm"].isPwdChanged;
			
			for(var i=0;i<flag.length;i++)
			{
				if(flag[i].checked && flag[i].value == "Y")
				{
					document.getElementById("trNewPwd").style.display = "";
					document.getElementById("trNewPwdCfm").style.display = "";
				}
				else if(flag[i].checked && flag[i].value == "N")
				{
					document.getElementById("trNewPwd").style.display = "none";
					document.getElementById("trNewPwdCfm").style.display = "none";
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
	<a href="javascript:submitForm('contentForm','<c:url value="/saveEditMyProfile.action"/>');" class="actionButton" target="_self">
	  <span><s:text name="button.edit"/></span>
	</a>
	<a href="javascript:submitForm('backForm','<c:url value="/initEditMyProfile.action"/>');" class="actionButton" target="_self">
	  <span><s:text name="button.reset"/></span>
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
</form>

<form id="contentForm" name="contentForm" action="" method="post">
<s:hidden name="param.userOid" />
<div class="subTitle"><s:text name="label.myprofile.normal.info" /></div>
  <div>
   <table width="100%"><tbody>
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
  
  
  
  <div class="subTitle"><s:text name="label.myprofile.login.info" /></div>
  <div>
   <table width="100%"><tbody>
    <tr>
      <td class="labelCell">	
          <span class="label">
          	<span>&nbsp;</span><s:text name="label.user.loginId" /></span>
          	<span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        	<table class="innerTable"><tbody>
        	<tr>
          		<td class="data">
                	<s:property value="param.loginId" />
          		</td>
        	</tr>
        	</tbody></table>
      </div>	     
      </td>
    </tr>
    
    
	<tr>
		<td class="labelCell">
			<span class="label"><span class="required">&nbsp;</span><s:text name="label.myprofile.changePwd" /></span>
			<span class="seperator">:</span>
		</td>
		<td class="data">
			<div class="group">						
			<table class="innerTable">
				<tbody>
				<tr>
					<td class="data">
						<s:radio list="#{'Y':'Yes','N':'No'}" name="isPwdChanged" id="isPwdChanged" cssClass="radioButton" onclick="javascript:changePwd();" />
					</td>
				</tr>
				</tbody>
			</table>
		   </div>	   
		</td>
	</tr>
	
	
	<tr id="trNewPwd" style="display:none;" >
		<td class="labelCell">													
			<span class="label"><span class="required">*</span><s:text name="label.myprofile.loginPasswd" /></span>
			<span class="seperator">:</span>
		</td>
		<td class="data">	
	  		<div class="group">
		
			<table class="innerTable">
				<tbody>
				<tr>
					<td class="data">
						<s:password name="param.loginPasswd" id="loginPasswd" size="10" maxlength="8" cssClass="inputBox" />
					</td>
				</tr>
				</tbody>
			</table>

		   </div>	     
		</td>
	</tr>
	
	<tr id="trNewPwdCfm" style="display:none;" >
		<td class="labelCell">	
								
			<span class="label"><span class="required">*</span><s:text name="label.myprofile.confirmPasswd" /></span>
			<span class="seperator">:</span>
		</td>
		<td class="data">	
	  		<div class="group">
		
			<table class="innerTable">
				<tbody>
				<tr>
					<td class="data">										
						<s:password name="param.confirmPasswd" id="confirmPasswd" size="10" maxlength="8" cssClass="inputBox" />
					</td>
				</tr>
				</tbody>
			</table>

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