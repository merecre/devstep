<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<form method="post" action="<%= request.getContextPath() %>/reportrequest.jsp">
		<table>
			<tr>
				<td>
					<p>${reportinfo['startDate']}:</p>
				</td>
				<td><input type="text" value="${reportdates['startDate']}" name="startdate" id="startdate" data-lang="${sessionScope.language}" data-years="2015-2035" data-format="YYYY-MM-DD" required />
					<script
						src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
						<script src="js/moment-with-locales.min.js"></script> <script
						src="js/ion.calendar.js"></script> 
					<script>
					$(function(){
    				$("#startdate").ionDatePicker();
					});
					</script></td>
					<td>
						<p>${reportinfo['endDate']}:</p>
					</td>
					<td><input type="text" value="${reportdates['endDate']}" name="enddate" id="enddate"	data-lang="${sessionScope.language}" data-years="2015-2035" data-format="YYYY-MM-DD" required/>
					<script>
					$(function(){
    				$("#enddate").ionDatePicker();
					});
					</script></td>
				<td><input type="submit" value="Report" name="permessagereportpost"></td>
			</tr>
			<tr><td>${reportinfo['txtMessageStatus']}:</td>
			<td> 
			<select name="status">
				<option value="${reportdates['selectedStatus']}" selected>${reportdates['selectedStatus']}</option>
    			<c:forEach items="${smsstatus}" var="status">
    				<c:if test="${status != reportdates['selectedStatus']}">
        				<option value="${status}">${status}</option>
        			</c:if>
   			 	</c:forEach>
   			 	<option value="">...</option>
			</select>
			</td>
			</tr>
		</table>
</form>
<table class="smsreport" >
<thead>
<tr>
	<th>${reportinfo['headerPhoneNumber']}:</th>
	<th>${reportinfo['headerStatus']}:</th>
	<th>${reportinfo['headerDatetime']}:</th>
	<th>${reportinfo['headerMessage']}:</th>
</tr>
</thead>

<tbody>
	<c:forEach var="sms" items="${reportdata}">
		<tr><td>${sms.phoneNumber}</td>
			<td>${sms.status}</td>
			<td><fmt:formatDate value="${sms.sendTime}" pattern="yyyy.MM.dd. HH:mm"/></td>
			<td>${sms.message}</td>
		</tr>
	</c:forEach>
</tbody>
</table>