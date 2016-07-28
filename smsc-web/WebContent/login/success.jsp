<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:choose>
<c:when test="${not empty sessionScope.userid}">
	You are not logged in
	<br />
	<a href="<%= request.getContextPath() %>/login/login.jsp">Please Login</a>	
</c:when>
<c:otherwise>
	Welcome <%=session.getAttribute("userid")%><a href='<%= request.getContextPath() %>/login/logout.jsp'>Log out</a>
</c:otherwise>
</c:choose>