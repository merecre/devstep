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

	<jsp:include page="/registrationloader.jsp" />

	<font color="green">${information}</font>
	<c:remove var="information" scope="session" />

	<form method="post"
		action="<%= request.getContextPath() %>/login/registration.jsp">
		<center>
		<font color="red">${error}</font>
		<c:remove var="error" scope="session" />
			<table border="0" width="30%" cellpadding="5">
				<thead>
					<tr>
						<th colspan="2" span style="color: #0c7fb0">${registrationinfo.txtHeader}</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td bgcolor="#0fa1e0"><FONT COLOR=WHITE>${registrationinfo.txtFirstname}</FONT></td>
						<td bgcolor="#0fa1e0"><input type="text" name="fname"
							value="${customerdata.name}" /></td>
					</tr>
					<tr>
						<td bgcolor="#a3d4a0"><FONT COLOR=WHITE>${registrationinfo.txtLastname}</FONT></td>
						<td bgcolor="#a3d4a0"><input type="text" name="lname"
							value="${customerdata.surname}" /></td>
					</tr>
					<tr>
						<td bgcolor="#0fa1e0"><FONT COLOR=WHITE>${registrationinfo.txtEmail}</FONT></td>
						<td bgcolor="#0fa1e0"><input type="text" name="email"
							value="${customerdata.email}" /></td>
					</tr>
					<tr>
						<td bgcolor="#a3d4a0"><FONT COLOR=WHITE>${registrationinfo.txtLogin}</FONT></td>
						<td bgcolor="#a3d4a0"><input type="text" name="uname"
							value="${customerdata.userLogin}" /></td>
					</tr>
					<tr>
						<td bgcolor="#0fa1e0"><FONT COLOR=WHITE>${registrationinfo.txtPassword}</FONT></td>
						<td bgcolor="#0fa1e0"><input type="password" name="pass"
							value="" /></td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<table>
								<tr>
									<td><input type="submit"
										value="${registrationinfo.txtBtnSubmit}" /></td>
									<td><input type="reset"
										value="${registrationinfo.txtBtnReset}" /></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td colspan="2" span style="color: #0c7fb0" align="center">${registrationinfo.txtLoginDesr}
							<a href="<%= request.getContextPath() %>/index.jsp">${registrationinfo.txtLoginHref}</a>
						</td>
					</tr>
				</tbody>
			</table>
		</center>
	</form>
</body>
</html>