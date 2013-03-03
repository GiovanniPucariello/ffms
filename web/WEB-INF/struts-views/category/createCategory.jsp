<%@ page contentType="text/html;charset=UTF-8" pageEncoding="GBK" %>
<%@ include file="/WEB-INF/struts-views/common/taglibs-include.jsp"%>    
<html>
<head>
<title><s:text name="title.category" /></title>
    
<script type="text/javascript" src="<s:url value='/scripts/common.js' includeParams='none'/>"></script>
</head>

<body>
<div id="main">
<div id="mainWrapper">
	
<!-- here is the button area -->
<div class="buttonWrapper">
	<!--change here: your button -->
	<a href="javascript:submitForm('contentForm','<c:url value="/saveAddCategory.action"/>');" class="actionButton" target="_self">
	  <span><s:text name="button.create"/></span>
	</a>
	<a href="javascript:submitForm('backForm','<c:url value="/initAddCategory.action"/>');" class="actionButton" target="_self">
	  <span><s:text name="button.reset"/></span>
  	</a>
	<a href="javascript:submitForm('backForm','<c:url value="/initCategory.action"/>');" class="actionButton" target="_self">
	  <span><s:text name="button.cancel"/></span>
	</a>
</div>
			
<!--here is the banner area-->
<div class="pageTitle"> 
<div class="title">
	<s:text name="label.category.summary" />
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
          		<s:text name="label.category.categoryName" />
          </span>
          <span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        <table class="innerTable"><tbody>
        <tr>
          <td class="data">
              <s:textfield name="param.categoryName" maxlength="20"
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
          		<s:text name="label.category.type" />
          </span>
          <span class="seperator">:</span>
      </td>
      <td class="data">	
      <div class="group">
        <table class="innerTable"><tbody>
        <tr>
          <td class="data">
              <s:select name="param.categoryType" list="type" 
                		listKey="key" listValue="value" cssClass="inputBox"/>
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