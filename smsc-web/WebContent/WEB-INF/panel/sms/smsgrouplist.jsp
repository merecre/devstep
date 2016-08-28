<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<form method="post" action="smscontroller.jsp">
<table>
	<tr><td colspan=2 align='center'><strong>${smspanelinfo.txtTitleGroups}</strong></td></tr>
	<tr>
		<td colspan=2><input type="checkbox" onClick="toggle(this)" />
				${smspanelinfo.txtAllCheckbox}
		</td>
	</tr>

	<c:forEach items="${smsgroups}" var="smsgroup">
		<tr>
			<td>
				<input type="checkbox" name="group_name" value="${smsgroup.smsGroupId}">
			</td>
			<td>
				<a class='report' href='<%= request.getContextPath() %>/smspanel.jsp?cusMenu=smsPanel&viewGroup=true&groupID=${smsgroup.smsGroupId}'>${smsgroup.smsGroupName}</a>
			</td>
			</tr>
		</c:forEach>
	<tr>
		<td align="center" colspan=2><input type="submit" value="${smspanelinfo.txtDeleteSelected}" name="delete"></td>
	</tr>
</table>
</form>