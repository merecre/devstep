<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<table>
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
				<a href='<%= request.getContextPath() %>/smspanel.jsp?cusMenu=smsPanel&viewGroup=true&groupID=${smsgroup.smsGroupId}'>${smsgroup.smsGroupName}</a>
			</td>
			</tr>
		</c:forEach>
	<tr>
		<td align="center" colspan=2><input type="submit" value="delete selected" name="delete"></td>
	</tr>
</table>