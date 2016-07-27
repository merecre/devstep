<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="/logininfoloader.jsp" />

<c:choose>
	<c:when test="${not empty sessionScope.userid}">
		<jsp:include page="/WEB-INF/loginpage/loginsuccess.jsp" />
	</c:when>
	<c:otherwise>
		<jsp:include page="/WEB-INF/loginpage/loginwelcomepage.jsp" />
	</c:otherwise>
</c:choose>