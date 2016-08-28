<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script language="JavaScript">
function add_fields(divName, inpName, bntName, btnDelId) {
	var tableRef = document.getElementById(divName).getElementsByTagName('tbody')[0];

	// Insert a row in the table at the last row
	var newRow   = tableRef.insertRow(tableRef.rows.length);

	// Insert a cell in the row at index 0
	var newCell  = newRow.insertCell(0);
	
	// Append a text node to the cell
	var element  = document.createElement("input");
	//Assign different attributes to the element.
    element.setAttribute("type", "text");
    element.setAttribute("value", "");
    element.setAttribute("name", inpName);
	newCell.appendChild(element);
	
	// Insert a cell in the row at index 0
	var newCell1  = newRow.insertCell(1);
	
	// Append a delete button to the cell
	var element1  = document.createElement("button");
	//Assign different attributes to the element.
    element1.setAttribute("id", btnDelId);
    element1.setAttribute("onclick", "deleteThisRow(this,'"+divName+ "')");
    element1.innerHTML = bntName;
	newCell1.appendChild(element1);	
	}
	
    function deleteLastRow(tableId) {
        var table = document.getElementById(tableId);
        var rowCount = table.rows.length;
        if (rowCount>3) {
            table.deleteRow(rowCount -1);
        }
	}
    
    function deleteThisRow(row, tableID){
        var d = row.parentNode.parentNode.rowIndex;
        document.getElementById(tableID).deleteRow(d);
     }
</script>

<%-- form group description fields --%>
<form method="post" enctype="multipart/form-data" action="smscontroller.jsp">
<table>
	<tr><td colspan='6' align="center"><h3>${smspanelinfo.txtNewGroup}</h3></td></tr>
	<tr>
		<td>${smspanelinfo.txtGroupDescription}:</td>
		<td colspan='2'><input type="text" name="g_common_name"	value="${smsgrouprec.smsGroupName}" /></td>
		<td colspan='3'><input type="submit" value="${smspanelinfo.btnSend}" name="save">
		</td>
	</tr>
	<tr>
	
	<td>${smspanelinfo.txtSender}:</td>
	<td colspan='4'><input type="text" name="g_common_sender" value="${smsgrouprec.sender}" />
	</tr>

<%-- form group message fields --%>

	<tr>
		<td>${smspanelinfo.txtGroupMessage}:</td>
		<td colspan='5'>
		<textarea id="sms_message" name="g_common_message" rows="2" cols="35" maxlength= "800">${smsgrouprec.groupMessage}</textarea>
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
<%-- form send date and time fields --%>
	<tr>
		<td>
			<p>${smspanelinfo.txtSendDate}:</p>
		</td>
		<td>
		<c:set var="datetime" value="${smsgrouprec.sendTime}" scope="page" /> 
		<fmt:formatDate var="date" value="${datetime}" pattern="yyyy-MM-dd" /> 
		<fmt:formatDate var="hour" value="${datetime}" pattern="HH" /> 
		<fmt:formatDate var="minutes" value="${datetime}" pattern="mm" /> 
		<input type="text" value="${date}" name="grp_send_date" id="grp_send_date" data-lang="lv" data-years="2015-2035" data-format="YYYY-MM-DD" size="10" /> 
		<script	src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
		<script src="js/moment-with-locales.min.js"></script> 
		<script	src="js/ion.calendar.js"></script> 
		<script> $(function(){ $("#grp_send_date").ionDatePicker();});</script>
		</td>
		<td>${smspanelinfo.txtSendTime}:</td>
		<td><input type="time" value="${hour}" name="grp_send_time_hour"
			maxlength="2" size="2"></td>
		<td>:</td>
		<td><input type="time" value="${minutes}"
			name="grp_send_time_min" maxlength="2" size="2"></td>
	</tr>
</table>


<%-- Phone numbers list and input buttons: deleteRow; addRow  --%>

<script language="JavaScript" type="text/javascript">

function HandleBrowseClick()
{
    var fileinput = document.getElementById("browse");
    fileinput.click();
}

function Handlechange()
{
var fileinput = document.getElementById("browse");
var textinput = document.getElementById("filename");
textinput.value = fileinput.value;
}
</script>



<table>
	<tr><td align='center' colspan='3'>${smspanelinfo.txtTitleImport}:</td></tr>
	<tr>
	<td><input type="file" id="browse" name="contacts" style="display: none" accept=".csv" onChange="Handlechange();">
	<input type="text" id="filename" readonly/></td>
	<td><input type="button" value="${smspanelinfo.txtBtnToSelectFile}" id="fakeBrowse" onclick="HandleBrowseClick();"/></td>
	<td><input type="submit" value="${smspanelinfo.txtBtnImport}" name="importfile"></td>

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
		<c:if test="${empty phone}">
							<tr>
						<td><input type="text" name="phone" value="" /></td>
						<td><input type="button" id="more_fields"
							onclick="add_fields('phone_group', 'phone', '${smspanelinfo.txtRemoveRow}', 'delete');"
							value="${smspanelinfo.txtAddRow}" /></td>
						<td><input type="button" id="delete"
							onclick="deleteLastRow('phone_group');" value="${smspanelinfo.txtRemoveRow}" /></td>
					</tr>
		
		</c:if>
		<c:forEach items="${phone}" var="phonegroup">
			<c:set var="counter" value="${counter + 1}" scope="page" />
			<c:choose>
				<c:when test="${counter eq 1}">
					<c:set var="phonenumber1" value="${phonegroup}"
						scope="page" />
					<tr>
						<td><input type="text" name="phone" value="${phonenumber1}" /></td>
						<td><input type="button" id="more_fields"
							onclick="add_fields('phone_group', 'phone', '${smspanelinfo.txtRemoveRow}', 'delete');"
							value="${smspanelinfo.txtAddRow}" /></td>
						<td><input type="button" id="delete"
							onclick="deleteLastRow('phone_group');" value="${smspanelinfo.txtRemoveRow}" /></td>
					</tr>
				</c:when>
			</c:choose>

			<c:if test="${counter gt 1}">
				<tr>
					<td><input type="text" name="phone"
						value="<c:out value="${phonegroup}"/>" /></td>
					<td><input type="button" id="delete"
						onClick="deleteThisRow(this, 'phone_group')" value="${smspanelinfo.txtRemoveRow}" /></td>
				</tr>
			</c:if>

		</c:forEach>
	</tbody>
</table>
</form>