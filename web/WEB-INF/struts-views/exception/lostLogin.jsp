<%@ include file="/WEB-INF/struts-views/common/taglibs-include.jsp"%>

<html>
<head>
	<script type="text/javascript">
		function exe()
		{
			alert('<s:text name="message.exception.lost.login" />');
			window.location.href = "home.action";
		}
		exe();
	</script> 
</head>
<body>
</body>
</html>