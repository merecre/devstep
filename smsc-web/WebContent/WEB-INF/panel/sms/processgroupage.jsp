
<% 

String[] checkBoxes = request.getParameterValues("group_name");

if (request.getParameter("delete")!=null) {
	if (checkBoxes!=null) {
	    for(int i =0; i<checkBoxes.length; i++){
		   out.println(checkBoxes[i]+ "<br>");
	    }
	}
}

if (request.getParameter("save")!=null) {
	String[] phones = request.getParameterValues("phone");
	out.println("Group saved: " + request.getParameter("g_common_name") );
	out.println("Message: " + request.getParameter("g_common_message") );
	if (phones!=null) {
	    for(int i =0; i<phones.length; i++){
		   out.println("Phones:" + phones[i]+ "<br>");
	    }
	}
}
%>