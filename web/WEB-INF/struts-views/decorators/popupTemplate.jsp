<%@ page contentType="text/html;charset=UTF-8" pageEncoding="GBK" %>
<%@ include file="/WEB-INF/struts-views/common/taglibs-include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html class="dj_gecko">
<head>
<title><decorator:title default="" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="<s:url value='/styles/%{#session.layoutTheme}/site.css' includeParams='none'/>" type="text/css" />
<link rel="stylesheet" href="<s:url value='/styles/%{#session.layoutTheme}/admin-content.css' includeParams='none'/>" type="text/css" />
<link rel="stylesheet" href="<s:url value='/styles/%{#session.layoutTheme}/admin-header.css' includeParams='none'/>" media="screen" type="text/css" />
<style media="print"> 
	.noPrint{display:none;}
	.PageNext{page-break-after:   always;}
</style>
<decorator:head />
</head>
<body>
<div id="container">
	<!-- page title -->
	<div id="masthead">	
	<div id="mastheadWrapper">
		<div class="logoInfoPopup">
			<h1><s:text name="label.header.subject"/></h1>
		</div>
	</div> 
	</div>
	<!-- end page title -->

	<!-- main area -->
	<decorator:body />
	<!-- end main area -->
</div>
	
<div class="clear"></div>
</body>
</html>