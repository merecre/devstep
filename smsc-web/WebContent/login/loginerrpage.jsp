<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset='utf-8'>
</head>
<body>
${logininfo.txtInvalidPassword}<a href="${pageContext.servletContext.contextPath}/index.jsp">"${logininfo.txtRepeat}"</a>
</body>
</html>
