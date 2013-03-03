<%@ include file="/WEB-INF/struts-views/common/taglibs-include.jsp"%>
<html>
<head>
    <link rel="stylesheet" href="<s:url value='/styles/%{#session.layoutTheme}/admin-content.css' includeParams='none'/>" type="text/css">
    <link rel="stylesheet" href="<s:url value='/styles/%{#session.layoutTheme}/site.css' includeParams='none'/>" type="text/css">
    
	<script type="text/javascript" src="<s:url value='/scripts/common.js' includeParams='none'/>"></script> 
</head>
<body>
	<div id="main">
		<div id="mainWrapper">
			<!-- here is the button area -->
			<div class="buttonWrapper">
				<!--change here: your button -->
				<div>
				<c:forEach items="${msg.targets}" var="target" varStatus="targetIndex">
					<a href="javascript:submitForm('backForm<c:out value="${targetIndex.index}" />','<c:url value="${target.targetURI}.action" />');" 
						class="actionButton" target="_self">
						<span><c:out value="${target.targetBtnTitle}" /></span>
					</a>
				</c:forEach>
				</div>
			</div>
			
			<!--here is the banner area-->
		  	<div class="pageTitle"> 
				<div class="title"><c:out value="${msg.title}" /></div>
		  	</div>
			
			<div class="spacer"></div>
			
			<!--here is the result message area -->
			<div class="info">
				<span class="bigMessage">
					<c:if test="${msg.contents != null}">
                    <c:forEach items="${msg.contents}" var="content" varStatus="rowCount">
                        <c:out value="${content.MSG_CONTENT}" /><br>
                    </c:forEach>
                </c:if>
				</span>
			</div>
		</div>
	</div>
	
	<c:forEach items="${msg.targets}" var="target" varStatus="targetIndex">
	<form id="backForm<c:out value="${targetIndex.index}" />" name="backForm<c:out value="${targetIndex.index}" />" action="" method="post">
  	<c:if test="${target.parameters != null}">
    <c:forEach items="${target.parameters}" var="parameter" varStatus="rowCount">
	   <input type="hidden" name='<c:out value="${parameter.paramKey}" />' value='<c:out value="${parameter.paramValue}" />'/>
    </c:forEach>
  	</c:if>
  	</form>
  	</c:forEach>
</body>
</html>

