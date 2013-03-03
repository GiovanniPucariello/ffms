<%@ page contentType="text/html;charset=UTF-8" pageEncoding="GBK" %>
<%@ include file="/WEB-INF/struts-views/common/taglibs-include.jsp"%>
<html>
<head>
<title><s:text name="title.user" /></title>
<link rel="stylesheet" href="<s:url value='/styles/%{#session.layoutTheme}/displaytag.css' includeParams='none'/>" type="text/css">
<%@ include file="/WEB-INF/struts-views/common/jsCalendar.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/common.js' includeParams='none'/>"></script>

<script type="text/javascript">
    function initType()
    {
        document.getElementById('reportTypeD').checked=true;
        changeType();
    }

    function changeType()
    {
        if(document.getElementById('reportTypeN').checked)
        {
            document.getElementById("detailUser").style.display = "";
            document.getElementById("detailCat").style.display = "";
            document.getElementById("detailFrom").style.display = "";
            document.getElementById("detailTo").style.display = "";
            
            document.getElementById("dailyUser").style.display = "none";
            document.getElementById("dailyFrom").style.display = "none";
            document.getElementById("dailyTo").style.display = "none";
            
            document.getElementById("monthlyUser").style.display = "none";
            document.getElementById("monthlyFrom").style.display = "none";
            document.getElementById("monthlyTo").style.display = "none";
        }
        else if(document.getElementById('reportTypeD').checked)
        {
            document.getElementById("detailUser").style.display = "none";
            document.getElementById("detailCat").style.display = "none";
            document.getElementById("detailFrom").style.display = "none";
            document.getElementById("detailTo").style.display = "none";
            
            document.getElementById("dailyUser").style.display = "";
            document.getElementById("dailyFrom").style.display = "";
            document.getElementById("dailyTo").style.display = "";
            
            document.getElementById("monthlyUser").style.display = "none";
            document.getElementById("monthlyFrom").style.display = "none";
            document.getElementById("monthlyTo").style.display = "none";
        }
        else if(document.getElementById('reportTypeM').checked)
        {
            document.getElementById("detailUser").style.display = "none";
            document.getElementById("detailCat").style.display = "none";
            document.getElementById("detailFrom").style.display = "none";
            document.getElementById("detailTo").style.display = "none";
            
            document.getElementById("dailyUser").style.display = "none";
            document.getElementById("dailyFrom").style.display = "none";
            document.getElementById("dailyTo").style.display = "none";
            
            document.getElementById("monthlyUser").style.display = "";
            document.getElementById("monthlyFrom").style.display = "";
            document.getElementById("monthlyTo").style.display = "";
        }
        
    }

	function doPrintPdf()
	{
	   if(document.getElementById('reportTypeN').checked)
        {
            var url = '<c:url value="/printReportItem.action?reportType=N" />';
        
            var from=document.getElementById('detailFromDate').value;
            var to=document.getElementById('detailToDate').value;
        
            if (from > to)
            {
                alert('<s:text name="report.fromDate.lagethan.toDate"/>');
                
                return;
            }
            
            submitFormInNewWindow('detailForm',url);
        }
        else if(document.getElementById('reportTypeD').checked)
        {
            var url = '<c:url value="/printReportItem.action?reportType=D" />';
        
            var from=document.getElementById('dailyFromDate').value;
            var to=document.getElementById('dailyToDate').value;
        
            if (from > to)
            {
                alert('<s:text name="report.fromDate.lagethan.toDate"/>');
                
                return;
            }
            
            submitFormInNewWindow('dailyForm',url);
        }
        else if(document.getElementById('reportTypeM').checked)
        {
            var url = '<c:url value="/printReportItem.action?reportType=M" />';
        
            var from=document.getElementById('monthlyFromDate').value;
            var to=document.getElementById('monthlyToDate').value;
        
            if (from > to)
            {
                alert('<s:text name="report.fromDate.lagethan.toDate"/>');
                
                return;
            }
            
            submitFormInNewWindow('monthlyForm',url);
        }
	}
</script>
</head>

<body>
<div id="main">
<div id="mainWrapper">
 
<div class="buttonWrapper">
   <a href="javascript:doPrintPdf();" class="actionButton">
    <span><s:text name="button.print.pdf"/></span>
   </a>
</div>

<div class="pageTitle"> 
<div class="title">
	<s:text name="label.reportItem.summary" />				
</div>
</div>


<div class="dataWrapper">
<div class="inputForm">
<form id="backForm" name="backForm" action="" method="post">
<input type="hidden" name="<%= com.oyl.base.util.CommonConstants.REQ_PARAMETER_KEEP_SEARCH_CONDITION %>" value="Y"/>
</form>
  <div>
   <table width="100%"><tbody>
   <tr>
      <td class="labelCell">    
          <span class="label">
            <span>&nbsp;</span><s:text name="label.reportItem.reportType" /></span>
            <span class="seperator">:</span>
      </td>
      <td class="data"> 
      <div class="group">
            <table class="innerTable"><tbody>
            <tr>
                <td class="data">
                    <s:radio list="#{'N':'Detail','D':'Daily','M':'Monthly'}"
                        name="reportType" id="reportType" cssClass="radioButton" onclick="javascript:changeType();" />
                </td>
            </tr>
            </tbody></table>
      </div>         
      </td>
    </tr>
   
   <div id="detailReport">
   <form id="detailForm" name="detailForm" action="" method="post">
   <tr id="detailUser">
      <td class="labelCell">    
          <span class="label">
            <span>&nbsp;</span><s:text name="label.reportItem.userName"/></span>
            <span class="seperator">:</span>
      </td>
      <td class="data"> 
      <div class="group">
            <table class="innerTable"><tbody>
            <tr>
                <td class="data">
                    <s:select list="users" listKey="userOid" listValue="userAlias" headerKey="-1" headerValue="ALL"
                        name="selectedUserOid" />
                </td>
            </tr>
            </tbody></table>
      </div>         
      </td>
    </tr>
    
    <tr id="detailCat">
      <td class="labelCell">    
          <span class="label">
            <span>&nbsp;</span><s:text name="label.reportItem.categoryName"/></span>
            <span class="seperator">:</span>
      </td>
      <td class="data"> 
      <div class="group">
            <table class="innerTable"><tbody>
            <tr>
                <td class="data">
                    <s:select list="categories" listKey="categoryOid" listValue="categoryName" headerKey="-1" headerValue="ALL"
                        name="categoryOid" />
                </td>
            </tr>
            </tbody></table>
      </div>         
      </td>
    </tr>
    
    <tr id="detailFrom">
    <td class="labelCell">
            <span class="label">&nbsp;<s:text name="label.reportItem.fromDate" /></span>
            <span class="seperator">:</span>
    </td>
    <td class="data">
       <table class="innerTable">
         <tbody><tr>
             <td>                
                <input type="text" name="fromDate" id="detailFromDate" style="width:80px;" readonly
                        value="<fmt:formatDate value="${fromDate}" type="date" pattern="yyyy-MM-dd"/>"> 
                <span id="dateCalendarFromDate" style="cursor:hand;" onClick="return showCalendar('detailFromDate', '%Y-%m-%d', null, true);">
                    <img src="images/default/iconCalendar.gif">
                </span>   
             </td>              
         </tr></tbody>
       </table>
    </td>
    </tr>
    
    <tr id="detailTo">
    <td class="labelCell">
            <span class="label">&nbsp;<s:text name="label.reportItem.toDate" /></span>
            <span class="seperator">:</span>
    </td>
    <td class="data">
       <table class="innerTable">
         <tbody><tr>
             <td>                
                <input type="text" name="toDate" id="detailToDate" style="width:80px;" readonly
                        value="<fmt:formatDate value="${toDate}" type="date" pattern="yyyy-MM-dd"/>">   
                <span id="dateCalendarToDate" style="cursor:hand;" onClick="return showCalendar('detailToDate', '%Y-%m-%d', null, true);">
                    <img src="images/default/iconCalendar.gif">
                </span>   
             </td>              
         </tr></tbody>
       </table>
    </td>
    </tr>
   </form>
   </div>
   
   
   <div id="dailyReport">
   <form id="dailyForm" name="dailyForm" action="" method="post">
   <tr id="dailyUser">
      <td class="labelCell">    
          <span class="label">
            <span>&nbsp;</span><s:text name="label.reportItem.userName"/></span>
            <span class="seperator">:</span>
      </td>
      <td class="data"> 
      <div class="group">
            <table class="innerTable"><tbody>
            <tr>
                <td class="data">
                    <s:select list="users" listKey="userOid" listValue="userAlias" headerKey="-1" headerValue="ALL"
                        name="selectedUserOid" />
                </td>
            </tr>
            </tbody></table>
      </div>         
      </td>
    </tr>
    
    <tr id="dailyFrom">
    <td class="labelCell">
            <span class="label">&nbsp;<s:text name="label.reportItem.fromDate" /></span>
            <span class="seperator">:</span>
    </td>
    <td class="data">
       <table class="innerTable">
         <tbody><tr>
             <td>                
                <input type="text" name="fromDate" id="dailyFromDate" style="width:80px;" readonly
                        value="<fmt:formatDate value="${fromDate}" type="date" pattern="yyyy-MM-dd"/>"> 
                <span id="dateCalendarFromDate" style="cursor:hand;" onClick="return showCalendar('dailyFromDate', '%Y-%m-%d', null, true);">
                    <img src="images/default/iconCalendar.gif">
                </span>   
             </td>              
         </tr></tbody>
       </table>
    </td>
    </tr>
    
    <tr id="dailyTo">
    <td class="labelCell">
            <span class="label">&nbsp;<s:text name="label.reportItem.toDate" /></span>
            <span class="seperator">:</span>
    </td>
    <td class="data">
       <table class="innerTable">
         <tbody><tr>
             <td>                
                <input type="text" name="toDate" id="dailyToDate" style="width:80px;" readonly
                        value="<fmt:formatDate value="${toDate}" type="date" pattern="yyyy-MM-dd"/>">   
                <span id="dateCalendarToDate" style="cursor:hand;" onClick="return showCalendar('dailyToDate', '%Y-%m-%d', null, true);">
                    <img src="images/default/iconCalendar.gif">
                </span>   
             </td>              
         </tr></tbody>
       </table>
    </td>
    </tr>
   </form>
   </div>
   
   
   <div id="monthlyReport">
   <form id="monthlyForm" name="monthlyForm" action="" method="post">
   <tr id="monthlyUser">
      <td class="labelCell">    
          <span class="label">
            <span>&nbsp;</span><s:text name="label.reportItem.userName"/></span>
            <span class="seperator">:</span>
      </td>
      <td class="data"> 
      <div class="group">
            <table class="innerTable"><tbody>
            <tr>
                <td class="data">
                    <s:select list="users" listKey="userOid" listValue="userAlias" headerKey="-1" headerValue="ALL"
                        name="selectedUserOid" />
                </td>
            </tr>
            </tbody></table>
      </div>         
      </td>
    </tr>
    
    <tr id="monthlyFrom">
    <td class="labelCell">
            <span class="label">&nbsp;<s:text name="label.reportItem.fromDate" /></span>
            <span class="seperator">:</span>
    </td>
    <td class="data">
       <table class="innerTable">
         <tbody><tr>
             <td>                
                <input type="text" name="sFromDate" id="monthlyFromDate" style="width:80px;" readonly
                        value="<fmt:formatDate value="${fromDate}" type="date" pattern="yyyy-MM"/>"> 
                <span id="dateCalendarFromDate" style="cursor:hand;" onClick="return showCalendar('monthlyFromDate', '%Y-%m', null, true);">
                    <img src="images/default/iconCalendar.gif">
                </span>   
             </td>              
         </tr></tbody>
       </table>
    </td>
    </tr>
    
    <tr id="monthlyTo">
    <td class="labelCell">
            <span class="label">&nbsp;<s:text name="label.reportItem.toDate" /></span>
            <span class="seperator">:</span>
    </td>
    <td class="data">
       <table class="innerTable">
         <tbody><tr>
             <td>                
                <input type="text" name="sToDate" id="monthlyToDate" style="width:80px;" readonly
                        value="<fmt:formatDate value="${toDate}" type="date" pattern="yyyy-MM"/>">   
                <span id="dateCalendarToDate" style="cursor:hand;" onClick="return showCalendar('monthlyToDate', '%Y-%m', null, true);">
                    <img src="images/default/iconCalendar.gif">
                </span>   
             </td>              
         </tr></tbody>
       </table>
    </td>
    </tr>
   </form>
   </div>
    
    </tbody></table>
  </div>
  

</div>
</div>

</div>
</div>
<script type="text/javascript" >
initType();
</script>
</body>
</html>