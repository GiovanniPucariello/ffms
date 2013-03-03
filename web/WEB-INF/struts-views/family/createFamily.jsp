<%@ page contentType="text/html;charset=UTF-8" pageEncoding="GBK" %>
<%@ include file="/WEB-INF/struts-views/common/taglibs-include.jsp"%>    
<html>
<head>
<title><s:text name="title.family" /></title>
    
<script type="text/javascript" src="<s:url value='/scripts/common.js' includeParams='none'/>"></script>
<script type="text/javascript" >
	function doCreate()
	{
		var form = document.forms['contentForm'];
		var url = '<c:url value="/saveAddFamily.action"/>';
		
		submitForm('contentForm', url);
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
	<a href="javascript:submitForm('backForm','<c:url value="/initAddFamily.action"/>');" class="actionButton" target="_self">
	  <span><s:text name="button.reset"/></span>
  	</a>
	<a href="javascript:submitForm('backForm','<c:url value="/initFamily.action"/>');" class="actionButton" target="_self">
	  <span><s:text name="button.cancel"/></span>
	</a>
</div>
			
<!--here is the banner area-->
<div class="pageTitle"> 
<div class="title">
	<s:text name="label.family.summary" />
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
<form id="contentForm" name="contentForm" action="" method="post">
  <div>
   <table width="100%"><tbody>
    <tr>
      <td class="labelCell">	
          <span class="label">
          		<span>&nbsp;</span>
          		<s:text name="label.family.familyName" />
          </span>
          <span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        <table class="innerTable"><tbody>
        <tr>
          <td class="data">
              <s:textfield name="param.familyName" maxlength="20"
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
          		<span>&nbsp;</span>
          		<s:text name="label.family.address" />
          </span>
          <span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        <table class="innerTable"><tbody>
        <tr>
          <td class="data">
              <s:textfield name="param.address" maxlength="150"
                cssClass="inputBox" size="50" />
          </td>
        </tr>
        </tbody></table>
      </div>	     
      </td>
    </tr>
    
    
    <tr>
      <td class="labelCell">	
          <span class="label">
          		<span>&nbsp;</span>
          		<s:text name="label.family.phone" />
          </span>
          <span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        <table class="innerTable"><tbody>
        <tr>
          <td class="data">
              <s:textfield name="param.phone" maxlength="20"
                cssClass="inputBox" size="20" />
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