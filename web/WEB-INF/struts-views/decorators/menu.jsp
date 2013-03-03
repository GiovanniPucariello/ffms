<div id="nav">
	<div id="dynamicMenu" class="noPrint">
	<ul class="menuList">
		<li class="menubar">
			<a href="<c:url value="/logout.action"/>" class="standalone"><s:text name="menu.logout"/></a>
		</li>
		<li class="menubar">
			<a href="<c:url value="/initEditMyProfile.action"/>" class="standalone"><s:text name="menu.edit.my.profile"/></a>
		</li>
		<c:forEach items="${SESSION_MENU}" var="parent" varStatus="status">
			<li class="menubar">
				<a href="<c:url value="${parent.moduleLink}" />" id="<c:out value="${status.index}" />" 
				<c:if test="${parent.moduleLink == null}" >class="actuator"</c:if>
				<c:if test="${parent.moduleLink != null}" >class="standalone"</c:if>				
				><c:out value="${parent.moduleName}" /></a>
				
				<c:if test="${parent.moduleLink == null}" >
				<ul id="<c:out value="${status.index}${status.index}" />" class="menu">
					<c:forEach items="${parent.subModules}" var="child">
						<li><a href="<c:url value="${child.moduleLink}" />"><c:out value="${child.moduleName}" /></a></li>
					</c:forEach>
				</ul>
				</c:if>
			</li>
		</c:forEach>
	</ul>   
	</div> 
</div>
