<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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

	<jsp:include page="/profileloader.jsp" />

	<table align="center">
	<tr>
		<th>${profileinfo.txtHeader}</th>
	</tr>
	<tr>
		<td>${profileinfo.txtName}:</td>
		<td>${profile.name}</td>
	</tr>
	<tr>
		<td>${profileinfo.txtSurname}:</td>
		<td>${profile.surname}</td>
	</tr>
	<tr>
		<td>${profileinfo.txtLogin}:</td>
		<td>${profile.userLogin}</td>
	</tr>
	<tr>
		<td>${profileinfo.txtEmail}:</td>
		<td>${profile.email}</td>
	</tr>	
	</table>
</body>
</html>