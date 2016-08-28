<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	
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
					<td><input type="text" value="${reportdates['endDate']}" name="enddate" id="enddate" data-lang="${sessionScope.language}" data-years="2015-2035" data-format="YYYY-MM-DD" required/>
					<script>
					$(function(){
    				$("#enddate").ionDatePicker();
					});
					</script></td>
				<td><input type="submit" value="Report" name="periodreportpost"></td>
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

	<div id="linechart_material"></div>

	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
	<script type="text/javascript">
	var lngg = "${language}"
    google.charts.load('visualization', '1.1', {packages: ['line'], 'language': lngg});
    google.charts.setOnLoadCallback(drawChart);

    function drawChart() {
      var data = new google.visualization.DataTable();
      var date = "${reportinfo['date']}";
      data.addColumn('date', date);
      var number = "${reportinfo['number']}";
      data.addColumn('number', number);

      var dataset = ${chartLines};
      data.addRows(dataset);
      
      var options = {
        chart: {
          title: "${reportinfo['reportSmsDesc']}",
          subtitle: "${reportinfo['subtitle']}"
        },
        width: 900,
        height: 500,
        hAxis: {
            format: 'M/d/yyyy',
            gridlines: {count: 15}
        },
        vAxis: {
            gridlines: {color: 'none'},
            minValue: 0
        }     
      };

      var chart = new google.charts.Line(document.getElementById('linechart_material'));
      var dateFormatter = new google.visualization.DateFormat({pattern: 'M/d/yyyy'});
      dateFormatter.format(data, 0);
      chart.draw(data, options);
    }
</script>
