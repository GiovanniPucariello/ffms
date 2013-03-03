<%@ include file="/WEB-INF/struts-views/common/taglibs-include.jsp"%>
<html class="dj_gecko">
<head>
<title><decorator:title default="" /></title>
<link rel="stylesheet" href="<s:url value='/styles/%{#session.layoutTheme}/site.css' includeParams='none'/>" type="text/css" />
<link rel="stylesheet" href="<s:url value='/styles/%{#session.layoutTheme}/admin-content.css' includeParams='none'/>" type="text/css" />
<link rel="stylesheet" href="<s:url value='/styles/%{#session.layoutTheme}/menu.css' includeParams='none'/>" media="screen" type="text/css" />
<link rel="stylesheet" href="<s:url value='/styles/%{#session.layoutTheme}/footer.css' includeParams='none'/>" type="text/css" />
<link rel="stylesheet" href="<s:url value='/styles/%{#session.layoutTheme}/admin-header.css' includeParams='none'/>" media="screen" type="text/css" />
<style media="print"> 
	.noPrint{display:none;}
	.PageNext{page-break-after:   always;}
</style>
<script type="text/javascript" src="<c:url value="/scripts/menu.js"/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/menuExpandable.js"/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/menubar.js"/>"></script>
<decorator:head />
</head>

<body>
<div id="container">
	<!-- page title -->
	<%@ include file="/WEB-INF/struts-views/decorators/header.jsp"%>
	<!-- end page title -->

	<!-- menu page -->
	<%@ include file="/WEB-INF/struts-views/decorators/menu.jsp"%>
	<!-- end menu page -->

	<!-- main area -->
	<decorator:body />
	<!-- end main area -->

	<!-- page bottom -->
	<%@ include file="/WEB-INF/struts-views/decorators/footer.jsp"%>
	<!-- end page bottom -->	
</div>
		
<div class="clear"></div>
</body>
</html>