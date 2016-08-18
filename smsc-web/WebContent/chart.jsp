<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<link href="css/ion.calendar.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<%@ include file="/header.jsp"%>
</head>

<body>
	<%@ include file="/WEB-INF/menu/menu.jsp"%>

	<c:if test="${not empty sessionScope.userid}">
		<%@ include file="/WEB-INF/menu/customermenu.jsp"%>
	</c:if>
	
	<jsp:include page="/reportrequest.jsp" />
	
	<table align="center" width="25%" border="0">
		<tr>
			<td align="center"><a	href='<%= request.getContextPath() %>/chart.jsp?id=Home&cusMenu=Sms&report=Period'>${reportinfo['hrefPeriodReport']}</a></td>
			<td align="center"><a	href='<%= request.getContextPath() %>/chart.jsp?id=Home&cusMenu=Sms&report=Message'>${reportinfo['hrefPerMessageReport']}</a></td>
		</tr>
	</table>
	
	<c:if test="${param.cusMenu == 'Sms' && param.report == 'Period'}">	
		<%@ include file="/WEB-INF/report/reportbyperiod.jsp"%>
	</c:if>
	
	<c:if test="${param.cusMenu == 'Sms' && param.report == 'Message'}">	
		<%@ include file="/WEB-INF/report/reportpermessage.jsp"%>
	</c:if>
	
</body>
</html>
