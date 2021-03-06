<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<br>
<%-- form group description fields --%>
<table>
	<tr>
		<td>${smspanelinfo.txtGroupDescription}:</td>
		<td colspan='2'><input class="grey" type="text" name="g_common_name" value="${viewsmsgrouprec.smsGroupName}" readonly></td>
		<td colspan='3'><a class='report' href='<%= request.getContextPath() %>/smspanel.jsp?cusMenu=smsPanel&editGroup=true&groupID=${viewsmsgrouprec.smsGroupId}'>${smspanelinfo.txtEditGroup}</a></td>
	</tr>
	<tr>
	<td>${smspanelinfo.txtSender}:</td>
	<td colspan='4'><input class="grey" type="text" name="g_common_sender" value="${viewsmsgrouprec.sender}" readonly/>
	</tr>
	
<%-- form group message fields --%>
	<tr>
		<td>${smspanelinfo.txtGroupMessage}:</td>
		<td colspan='6'>
		<textarea name="g_common_message" rows="2" cols="35" disabled>${viewsmsgrouprec.groupMessage}</textarea>
		</td>
	</tr>
</table>
<%-- form send date and time fields --%>
<table>
	<tr>
		<td>
			<p>${smspanelinfo.txtSendDate}:</p>
		</td>
		<td><c:set var="datetime" value="${viewsmsgrouprec.sendTime}" scope="page" /> 
		<fmt:formatDate var="date" value="${datetime}" pattern="yyyy-MM-dd" /> 
		<fmt:formatDate var="hour" value="${datetime}" pattern="HH" /> 
		<fmt:formatDate var="minutes" value="${datetime}" pattern="mm" /> 
		<input class="grey" type="text" value="${date}" name="grp_send_date" id="grp_send_date"	data-lang="lv" data-years="2015-2035" data-format="YYYY-MM-DD" size="10" readonly>
		</td>
		<td>${smspanelinfo.txtSendTime}:</td>
		<td><input class="grey" type="time" value="${hour}" name="grp_send_time_hour" maxlength="2" size="2" readonly></td>
		<td>:</td>
		<td><input class="grey" type="time" value="${minutes}" name="grp_send_time_min" maxlength="2" size="2" readonly></td>
	</tr>
</table>

<%-- Phone numbers list and input buttons: deleteRow; addRow  --%>

<table id="phone_group">
	<thead>
		<tr>
		</tr>
	</thead>
	<tbody>
		<tr>
			<th>${smspanelinfo.txtPhonenumbers}</th>
		</tr>
		<c:set var="counter" value="0" scope="page" />
		<c:forEach items="${phonegroups}" var="phonegroup">
			<c:set var="counter" value="${counter + 1}" scope="page" />
			<c:choose>
				<c:when test="${counter eq 1}">
					<c:set var="phonenumber1" value="${phonegroup.phonenumber}"
						scope="page" />
					<tr>
						<td><input class="grey" type="text" name="phone" value="${phonenumber1}" readonly></td>
					</tr>
				</c:when>
			</c:choose>

			<c:if test="${counter gt 1}">
				<tr>
					<td><input class="grey" type="text" name="phone"
						value="<c:out value="${phonegroup.phonenumber}"/>" readonly></td>
				</tr>
			</c:if>

		</c:forEach>
	</tbody>
</table>