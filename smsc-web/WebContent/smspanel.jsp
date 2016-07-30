
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/header.jsp"%>
<body>
	<%@ include file="/WEB-INF/menu/menu.jsp"%>

	<c:if test="${not empty sessionScope.userid}">
		<%@ include file="/WEB-INF/menu/customermenu.jsp"%>
	</c:if>

	<script language="JavaScript">
	function toggle(source) {
	  checkboxes = document.getElementsByName('group_name');
	  for(var i=0, n=checkboxes.length;i<n;i++) {
	    checkboxes[i].checked = source.checked;
	  }
	}
	</script>

	<jsp:include page="/smscontroller.jsp" />

	<form method="post" action="smscontroller.jsp">
		<table width="100%" border="1">
			<tr>
				<td align="center" width="15%"><a	href='<%= request.getContextPath() %>/smspanel.jsp?cusMenu=smsPanel'>Groups</a></td>
				<td><a	href='<%= request.getContextPath() %>/smspanel.jsp?newGroup=true'>New group</a></td>
			</tr>

			<tr>
				<td valign="top">
					<%-- User SMS Group list --%>
					<c:if test="${param.cusMenu == 'smsPanel' || param.viewGroup == 'true'}">
						<%@	include file="WEB-INF/panel/sms/smsgrouplist.jsp"%>
					</c:if> 
				</td>
				<td>
					<%-- Whether was clicked link to create a new group --%> 
					<c:if test="${param.newGroup == 'true'}">
						<%@	include file="WEB-INF/panel/sms/newgroup.jsp"%>
					</c:if> 
					<c:if test="${param.viewGroup == 'true'}">
						<%@	include file="WEB-INF/panel/sms/viewgroup.jsp"%>
					</c:if> 
				</td>
			</tr>
		</table>

	</form>

</body>
</html>