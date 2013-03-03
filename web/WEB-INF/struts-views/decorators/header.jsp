<div id="masthead">
	<div id="mastheadWrapper">
		<div class="logoInfo">
			<h1><s:text name="label.project.name"/></h1>
		</div>
		<div id="headerContent">
			<div class="group">
				<span class="label"><s:text name="label.header.current.user"/></span>
				<span class="seperator">:</span>
				<span class="data"><c:out value="${SESSION_CURRENT_USER_PROFILE.userName}"/></span>
			</div>
		</div>
	</div>
</div>