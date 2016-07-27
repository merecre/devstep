<%@ page language="java" contentType="text/html; utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/header.jsp" %>
<body>
<%@ include file="/WEB-INF/menu/menu.jsp" %>

<c:if test="${not empty sessionScope.userid}">
	<%@ include file="/WEB-INF/menu/customermenu.jsp" %>
</c:if>

<jsp:include page="/offerinfoloader.jsp" />

<table width = 100% border=0>
  <tr>
    <th>${offerpageinfo.title}</th>
  </tr>
  <tr>
    <td>${offerpageinfo.text}</td>
  </tr>
</table>
</body>
</html>