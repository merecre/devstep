<%
session.setAttribute("userid", null);
session.invalidate();
response.sendRedirect(request.getContextPath()+"/index.jsp");
%>