<form method="post"
	action="${pageContext.request.contextPath}/login/loginrequest.jsp">
	<table class="a" border="0">
		<thead>
			<tr>
				<th colspan="2">${loginpageinfo.txtHeader}</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>${loginpageinfo.txtUsername}</td>
				<td><input type="text" name="uname" value="" /></td>
			</tr>
			<tr>
				<td>${loginpageinfo.txtPassword}</td>
				<td><input type="password" name="pass" value="" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="Login" /></td>
				<td><input type="reset" value="Reset" /></td>
			</tr>
			<tr>
				<td colspan="2">${loginpageinfo.errNotReg}<a
					href="<%= request.getContextPath() %>/login/reg.jsp">${loginpageinfo.txtRegisterHere}</a></td>
			</tr>
		</tbody>
	</table>
</form>