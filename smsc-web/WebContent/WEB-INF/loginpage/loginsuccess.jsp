<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id='topdiv'>
	<table>
		<tr>
			<td span style="color: #0c7fb0">${loginpageinfo.txtWelcome}
				${sessionScope.userid}</td>
		</tr>
		<tr>
			<td><a class="login"
				href='${pageContext.request.contextPath}/login/logout.jsp'>${loginpageinfo.txtLogOut}</a>
			</td>
		</tr>
	</table>
</div>