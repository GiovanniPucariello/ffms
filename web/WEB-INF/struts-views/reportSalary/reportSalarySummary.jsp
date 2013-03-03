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
        document.getElementById('reportTypeN').checked=true;
        changeType();
    }

    function changeType()
    {
        if(document.getElementById('reportTypeN').checked)
        {
            document.getElementById("detailUser").style.display = "";
            document.getElementById("detailSalaryType").style.display = "";
            document.getElementById("detailFrom").style.display = "";
            document.getElementById("detailTo").style.display = "";
            
            document.getElementById("yearlyUser").style.display = "none";
            document.getElementById("yearlyFrom").style.display = "none";
            document.getElementById("yearlyTo").style.display = "none";
        }
        else if(document.getElementById('reportTypeY').checked)
        {
            document.getElementById("detailUser").style.display = "none";
            document.getElementById("detailSalaryType").style.display = "none";
            document.getElementById("detailFrom").style.display = "none";
            document.getElementById("detailTo").style.display = "none";
            
            document.getElementById("yearlyUser").style.display = "";
            document.getElementById("yearlyFrom").style.display = "";
            document.getElementById("yearlyTo").style.display = "";
        }
    }

    function doPrintPdf()
    {
       if(document.getElementById('reportTypeN').checked)
        {
            var url = '<c:url value="/printReportSalary.action?reportType=N" />';
        
            var from=document.getElementById('detailFromDate').value;
            var to=document.getElementById('detailToDate').value;
        
            if (from > to)
            {
                alert('<s:text name="report.fromDate.lagethan.toDate"/>');
                
                return;
            }
        
            submitFormInNewWindow('detailForm',url);
        }
        else if(document.getElementById('reportTypeY').checked)
        {
            var url = '<c:url value="/printReportSalary.action?reportType=Y" />';
        
            var from=document.getElementById('yearlyFromDate').value;
            var to=document.getElementById('yearlyToDate').value;
            
            if (from > to)
            {
                alert('<s:text name="report.fromDate.lagethan.toDate"/>');
                
                return;
            }
        
            submitFormInNewWindow('yearlyForm',url);
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
    <s:text name="label.reportSalary.summary" />              
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
            <span>&nbsp;</span><s:text name="label.reportSalary.reportType" /></span>
            <span class="seperator">:</span>
      </td>
      <td class="data"> 
      <div class="group">
            <table class="innerTable"><tbody>
            <tr>
                <td class="data">
                    <s:radio list="#{'N':'Detail', 'Y':'Yearly'}" 
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
            <span>&nbsp;</span><s:text name="label.reportSalary.userName"/></span>
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
    
    <tr id="detailSalaryType">
      <td class="labelCell">    
          <span class="label">
            <span>&nbsp;</span><s:text name="label.reportSalary.srType" /></span>
            <span class="seperator">:</span>
      </td>
      <td class="data"> 
      <div class="group">
            <table class="innerTable"><tbody>
            <tr>
                <td class="data">
                    <s:select list="salaryTypes" listKey="key" listValue="value" headerKey="-1" headerValue="ALL"
                        name="srType" />
                </td>
            </tr>
            </tbody></table>
      </div>         
      </td>
    </tr>
    
    <tr id="detailFrom">
    <td class="labelCell">
            <span class="label">&nbsp;<s:text name="label.reportSalary.fromDate" /></span>
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
            <span class="label">&nbsp;<s:text name="label.reportSalary.toDate" /></span>
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
   
   
   <div id="yearlyReport">
   <form id="yearlyForm" name="yearlyForm" action="" method="post">
   <tr id="yearlyUser">
      <td class="labelCell">    
          <span class="label">
            <span>&nbsp;</span><s:text name="label.reportSalary.userName"/></span>
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
    
    <tr id="yearlyFrom">
    <td class="labelCell">
            <span class="label">&nbsp;<s:text name="label.reportSalary.fromDate" /></span>
            <span class="seperator">:</span>
    </td>
    <td class="data">
       <table class="innerTable">
         <tbody><tr>
             <td>                
                <input type="text" name="sFromDate" id="yearlyFromDate" style="width:80px;" readonly
                        value="<fmt:formatDate value="${fromDate}" type="date" pattern="yyyy"/>"> 
                <span id="dateCalendarFromDate" style="cursor:hand;" onClick="return showCalendar('yearlyFromDate', '%Y', null, true);">
                    <img src="images/default/iconCalendar.gif">
                </span>   
             </td>              
         </tr></tbody>
       </table>
    </td>
    </tr>
    
    <tr id="yearlyTo">
    <td class="labelCell">
            <span class="label">&nbsp;<s:text name="label.reportSalary.toDate" /></span>
            <span class="seperator">:</span>
    </td>
    <td class="data">
       <table class="innerTable">
         <tbody><tr>
             <td>                
                <input type="text" name="sToDate" id="yearlyToDate" style="width:80px;" readonly
                        value="<fmt:formatDate value="${toDate}" type="date" pattern="yyyy"/>">   
                <span id="dateCalendarToDate" style="cursor:hand;" onClick="return showCalendar('yearlyToDate', '%Y', null, true);">
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