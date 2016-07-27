<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="/companyinfoloader.jsp" />
<table class="t">
	<tr>
		<th>${companyinfo.title}</th>
	</tr>
	<tr>
		<td>${companyinfo.about}</td>
	<tr>
</table>