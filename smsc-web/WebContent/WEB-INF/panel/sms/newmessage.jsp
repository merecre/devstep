<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%-- form send date and time fields --%>
<form method="post" action="smscontroller.jsp">
<table>
	<tr><td colspan='6' align="center"><h3>${smspanelinfo.txtNewMessage}</h3></td></tr>
	<tr>
		<td>
			${smspanelinfo.txtSendDate}:
		</td>
		
		<td>
		<c:set var="datetime" value="${smsrec.sendTime}" scope="page" /> 
		<fmt:formatDate var="date" value="${datetime}" pattern="yyyy-MM-dd" /> 
		<fmt:formatDate var="hour" value="${datetime}" pattern="HH" /> 
		<fmt:formatDate var="minutes" value="${datetime}" pattern="mm" /> 
		<input type="text"	value="${date}" name="sms_send_date" id="sms_send_date"	data-lang="lv" data-years="2015-2035" data-format="YYYY-MM-DD" size="10" /> 
		<script	src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
		<script src="js/moment-with-locales.min.js"></script> 
		<script	src="js/ion.calendar.js"></script> 
		<script>$(function(){ $("#sms_send_date").ionDatePicker();});</script>
		</td>
		
		<td>${smspanelinfo.txtSendTime}:</td>
		<td><input type="time" value="${hour}" name="sms_send_time_hour" maxlength="2" size="2"></td>
		<td>:</td>
		<td><input type="time" value="${minutes}" name="sms_send_time_min" maxlength="2" size="2"></td>
	</tr>



<%-- new message description fields --%>

	<tr>
		<td>${smspanelinfo.txtSender}:</td>
		<td colspan='5'><input type="text" name="sms_sender" value="${smsrec.sender}" /></td>
	</tr>
	<tr>		
		<td>${smspanelinfo.txtPhoneNumber}:</td>
		<td colspan='5'><input type="text" name="phone"	value="${smsrec.phoneNumber}" /></td>
	</tr>
<%-- form sms message fields --%>

	<tr>
		<td>${smspanelinfo.txtGroupMessage}:</td>
		<td colspan='5'>
		<textarea id="sms_message" name="sms_message" rows="2" cols="35" maxlength= "800">${smsrec.message}</textarea>
		<div style="font-size: small; font-family: sans-serif; color:#636260" id="counter"></div>
		</td>
	</tr>
	<tr><td colspan='6'>
		<div style="font-size: small; font-family: sans-serif; color:#636260">
			${smspanelinfo.txtCharInformation}
		</div>
	</td></tr>
</table>

<script language = "JavaScript">
var area = document.getElementById("sms_message");
var message = document.getElementById("counter");
var maxLength = 800;
var checkLength = function() {
    if(area.value.length <= maxLength) {
        message.innerHTML = (area.value.length) + " "+ "${smspanelinfo.txtCharCounterMsg}.";
    }
}
setInterval(checkLength, 300);
</script>

<table>
<tr><td><input type="submit" value="${smspanelinfo.btnSend}" name="savemessage"></td></tr>
</table>
</form>