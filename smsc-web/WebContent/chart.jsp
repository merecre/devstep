<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<link href="css/ion.calendar.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<%@ include file="/header.jsp"%>
</head>

<body>
	<%@ include file="/WEB-INF/menu/menu.jsp"%>

	<c:if test="${not empty sessionScope.userid}">
		<%@ include file="/WEB-INF/menu/customermenu.jsp"%>
	</c:if>

	<form method="post" action="<%= request.getContextPath() %>/reportrequest.jsp">
		<table>
			<tr>
				<td>
					<p>Start Date:</p>
				</td>
				<td><input type="text" value="" name="startdate" id="startdate"
					data-lang="lv" data-years="2015-2035" data-format="YYYY-MM-DD" />
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
						<p>End Date:</p>
					</td>
					<td><input type="text" value="" name="enddate" id="enddate"	data-lang="lv" data-years="2015-2035" data-format="YYYY-MM-DD" />
					<script>
					$(function(){
    				$("#enddate").ionDatePicker();
					});
					</script></td>
				<td><input type="submit" value="Report" name="report"></td>
			</tr>
		</table>
	</form>

	<div id="linechart_material"></div>
	<div id="demo1"></div>
	<div id="result-1">...</div>

	<script type="text/javascript">
    google.load('visualization', '1.1', {packages: ['line'], 'language': 'en'});
    google.setOnLoadCallback(drawChart);

    function drawChart() {

      var data = new google.visualization.DataTable();
      data.addColumn('date', 'Day');
      data.addColumn('number', 'Quantity');

      data.addRows("${chartLines}");

      var options = {
        chart: {
          title: 'Number of SMS per day',
          subtitle: 'in quantity'
        },
        width: 900,
        height: 500,
        hAxis: {
            format: 'M/d/yy HH:mm',
            gridlines: {count: 15}
        },
        vAxis: {
            gridlines: {color: 'none'},
            minValue: 0
        }     
      };

      var chart = new google.charts.Line(document.getElementById('linechart_material'));
      var dateFormatter = new google.visualization.DateFormat({pattern: 'M/d/yy HH:mm'});
      dateFormatter.format(data, 0);
      chart.draw(data, options);
    }
</script>

</body>
</html>
