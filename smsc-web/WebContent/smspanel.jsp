<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/header.jsp"%>
<body>
	<%@ include file="/WEB-INF/menu/menu.jsp"%>

	<c:if test="${not empty sessionScope.userid}">
		<%@ include file="/WEB-INF/menu/customermenu.jsp"%>
	</c:if>

	<jsp:include page="/smscontroller.jsp" />

	<table  width="100%">
	<tr><td>
		<table align='center' border="0">
		<tr>
			<td align="center" width="15%"><a class='report' href='<%= request.getContextPath() %>/smspanel.jsp?cusMenu=smsPanel&viewGroups=true'>${smspanelinfo.txtGroupList}</a></td>
			<td align="center"><a class='report' href='<%= request.getContextPath() %>/smspanel.jsp?cusMenu=smsPanel&newGroup=true'>${smspanelinfo.txtNewGroup}</a></td>
			<td align="center"><a class='report' href='<%= request.getContextPath() %>/smspanel.jsp?cusMenu=smsPanel&newMessage=true'>${smspanelinfo.txtNewMessage}</a></td>
		</tr>
		</table>
	</td></tr>
	</table>

	<script language="JavaScript">
	function toggle(source) {
	  checkboxes = document.getElementsByName('group_name');
	  for(var i=0, n=checkboxes.length;i<n;i++) {
	    checkboxes[i].checked = source.checked;
	  }
	}
	</script>

		<table width="100%" border="1">
			<tr>

			</tr>

			<tr>
				<td valign="top" width="15%">
					<%-- User SMS Group list --%>
					<c:if test="${param.viewGroups == 'true' || param.viewGroup == 'true'}">
						<%@	include file="WEB-INF/panel/sms/smsgrouplist.jsp"%>
					</c:if> 
				</td>
				<td>
					<font color="red">${error}</font> 
					<c:remove var="error" scope="session" />

					<font color="green">${information}</font>
					<c:remove var="information" scope="session" />
					<%-- Whether was clicked link to create a new group --%> 
					<c:if test="${param.newGroup == 'true'}">
						<%@	include file="WEB-INF/panel/sms/newgroup.jsp"%>
					</c:if> 
					<c:if test="${param.viewGroup == 'true'}">
						<%@	include file="WEB-INF/panel/sms/viewgroup.jsp"%>
					</c:if> 
					<c:if test="${param.editGroup == 'true'}">
						<%@	include file="WEB-INF/panel/sms/editgroup.jsp"%>
					</c:if>
					<c:if test="${param.newMessage == 'true'}">
						<%@	include file="WEB-INF/panel/sms/newmessage.jsp"%>
					</c:if>  
				</td>
			</tr>
		</table>
</body>
</html>