<%@ page import ="java.sql.*" %>

<%
    String user = request.getParameter("uname");    
    String pwd = request.getParameter("pass");
    String fname = request.getParameter("fname");
    String lname = request.getParameter("lname");
    String email = request.getParameter("email");
    Class.forName("com.mysql.jdbc.Driver");
    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_schema",
            "root", "Rjvfyljh78");
    Statement st = con.createStatement();
    //ResultSet rs;
    int i = st.executeUpdate("insert into customer(name, surname, login, password) values ('" + fname + "','" + lname + "','" + user + "','" + pwd + "')");
    if (i > 0) {
        //session.setAttribute("userid", user);
        response.sendRedirect(request.getContextPath()+"/login/welcome.jsp");
       // out.print("Registration Successfull!"+"<a href='index.jsp'>Go to Login</a>");
    } else {
        response.sendRedirect(request.getContextPath()+"/login/login.jsp");
    }
%>