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
<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>

<%
Class.forName("com.mysql.jdbc.Driver");
Connection conSchema = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_schema",
        "root", "Rjvfyljh78");
Statement groupStatement = conSchema.createStatement();
String groupID = session.getAttribute("customerid").toString();
String sqlRequest = "select * from group where groupId= '" + groupID + "'";
out.println("sql:"+sqlRequest);
//ResultSet rsgroup = groupStatement.executeQuery(sqlRequest);
%>

<%-- form group description fields --%>

<table>
	<tr>
		<td>Group description:</td>
		<td><input type="text" name="g_common_name" value="" /></td>
		<td><input type="submit" value="save group" name="save">
		</td>
	</tr>
</table>

<br>

<%-- form group message fields --%>

<table>
	<tr>
		<td>Message:</td>
		<td><input type="text" name="g_common_message" value="" size="60" /></td>
	</tr>
</table>

<%-- form send date and time fields --%>

<table>
	<tr>
		<td>
			<p>Send date:</p>
		</td>
		<td><input type="text" value="" id="startdate" data-lang="lv"
			data-years="2015-2035" data-format="YYYY-MM-DD" size="10" /> <script
				src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
			<script src="js/moment-with-locales.min.js"></script> <script
				src="js/ion.calendar.js"></script> <script>
$(function(){
    $("#startdate").ionDatePicker();
});
</script></td>
		<td>Time:</td>
		<td><input type="time" name="grp_send_time_hour" maxlength="2"
			size="2"></td>
		<td>:</td>
		<td><input type="time" name="grp_send_time_min" maxlength="2"
			size="2"></td>
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
			<th>Phone</th>
		</tr>
		<tr>
			<td><input type="text" name="phone" value="" /></td>
			<td><input type="button" id="more_fields"
				onclick="add_fields('phone_group', 'phone', 'Delete', 'delete');"
				value="Add row" /></td>
			<td><input type="button" id="delete"
				onclick="deleteLastRow('phone_group');" value="Delete row" /></td>
		</tr>
		<tr>
			<td><input type="text" name="phone" value="" /></td>
			<td><input type="button" id="delete"
				onClick="deleteThisRow(this, 'phone_group')" value="Delete" /></td>
		</tr>
	</tbody>
</table>