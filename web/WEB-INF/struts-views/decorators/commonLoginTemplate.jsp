<%@ include file="/WEB-INF/struts-views/common/taglibs-include.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title><decorator:title /></title>
    
    <link rel="stylesheet" href="<s:url value='/styles/default/login-site.css' includeParams='none'/>" type="text/css" />
	
	<decorator:head/>
</head>
<body>
<div id="wrap">
    <div id="top-bg"></div>
    <div id="header-photo"></div>
   	<div id="content-wrap">
		<decorator:body />
	</div>
	
	<div id="footer-wrap">	
		<div id="footer-bottom">
			<p>
			<s:text name="label.copyright1" />
			<br/>
			<s:text name="label.copyright2" />
	   		</p>
		</div>
	</div>
</div>
</body>
</html>
