<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/header.jsp"%>
<body>
	<%@ include file="/WEB-INF/menu/menu.jsp"%>
	<% 
Object sessionId = session.getAttribute("userid");
if (sessionId!=null) { %>
	<%@ include file="/WEB-INF/menu/customermenu.jsp"%>
	<%}%>

	<%@ page import="java.util.*"%>
	<%@ page import="java.sql.*"%>

	<%
Class.forName("com.mysql.jdbc.Driver");
Connection conSchema = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_schema",
        "root", "Rjvfyljh78");
Statement customerStatement = conSchema.createStatement();
String customerID = session.getAttribute("customerid").toString();
String sqlRequest = "select * from customer where customerId= '" + customerID + "'";
out.println("sql:"+sqlRequest);
ResultSet rsCustomer = customerStatement.executeQuery(sqlRequest);

String name = "";
String surname = "";
String login = "";

while (rsCustomer.next()) {
	name = rsCustomer.getString("name");
	surname = rsCustomer.getString("surname");
	login = rsCustomer.getString("login");
}
conSchema.close();
customerStatement.close();

%>
	<table align="center">
		<tr>
			<th>Customer</th>
		</tr>
		<tr>
			<td>Name:</td>
			<td><%= name %></td>
		</tr>
		<tr>
			<td>Surname:</td>
			<td><%= surname %></td>
		</tr>
		<tr>
			<td>Login:</td>
			<td><%= login %></td>
		</tr>
	</table>
</body>
</html>