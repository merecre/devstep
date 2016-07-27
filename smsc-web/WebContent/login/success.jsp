


<%
    if ((session.getAttribute("userid") == null) || (session.getAttribute("userid") == "")) {
%>
You are not logged in<br/>
<a href="<%= request.getContextPath() %>/login/login.jsp">Please Login</a>
<%} else {
%>
Welcome <%=session.getAttribute("userid")%>
<a href='<%= request.getContextPath() %>/login/logout.jsp'>Log out</a>
<%
    }
%>