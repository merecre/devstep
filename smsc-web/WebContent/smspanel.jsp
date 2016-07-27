<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/header.jsp" %>
<body>
<%@ include file="/WEB-INF/menu/menu.jsp" %>

<c:if test="${not empty sessionScope.userid}">
	<%@ include file="/WEB-INF/menu/customermenu.jsp" %>
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
<td align="center" width="15%">Groups</td>
<td>
<a href='<%= request.getContextPath() %>/smspanel.jsp?id=newGroup'>New group</a>
</td>
</tr>

<tr>
<td valign="top">

<%-- User SMS Group list --%>

<table>
<tr>
<td colspan=2 >
<input type="checkbox" onClick="toggle(this)" /> Toggle All
</td>
</tr>

<c:forEach items="${smsgroups}" var="smsgroup">
<tr><td><input type="checkbox" name="group_name" value="${smsgroup.smsGroupId}"></td><td><a href='<%= request.getContextPath() %>/smspanel.jsp?cusMenu=groupList&groupID=${smsgroup.smsGroupId}'>${smsgroup.smsGroupName}</a></td></tr>
</c:forEach>
   
<tr>
<td align="center" colspan=2>
<input type="submit" value="delete selected" name="delete">
</td>
</tr>
</table>
</td>
<td>

<%-- Whether was clicked link to create a new group --%>
<c:if test="${sessionScope.id == 'newGroup'}">
<%@	include file="WEB-INF/panel/sms/newgroup.jsp" %>
</c:if>

<c:if test="${sessionScope.cusMenu == 'groupList'}">
<%@	include file="WEB-INF/panel/sms/newgroup.jsp" %>
</c:if>

<%-- Whether clicked link to view a group --%>
<c:if test="${sessionScope.id == 'groupList'}">
<%@	include file="WEB-INF/panel/sms/groupage.jsp" %>
</c:if>
</td>
</tr>
</table>

</form>

</body>
</html>