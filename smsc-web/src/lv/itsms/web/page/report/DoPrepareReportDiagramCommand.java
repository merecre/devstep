package lv.itsms.web.page.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.page.PageRequestCommand;
import lv.itsms.web.request.parameter.ReportEndDateRequestParameter;
import lv.itsms.web.request.parameter.UserPageRequest;
import lv.itsms.web.service.Repository;
import lv.itsms.web.session.Session;
import transfer.domain.Sms;

public class DoPrepareReportDiagramCommand implements PageRequestCommand {

	Session session;
	
	HttpServletRequest request;
	
	Repository repository;
		
	public DoPrepareReportDiagramCommand(Session session, HttpServletRequest request, Repository repository) {
		this.session = session;
		this.request = request;
		this.repository = repository;
	}

	@Override
	public void execute() {

		UserPageRequest reportStartDateUserParameter = new ReportEndDateRequestParameter();
		reportStartDateUserParameter.update(request);
		String reportStartDate = reportStartDateUserParameter.getParameter();
		
		UserPageRequest reportEndDateUserParameter = new ReportEndDateRequestParameter();
		reportEndDateUserParameter.update(request);
		String reportEndDate = reportEndDateUserParameter.getParameter();
		
		String chartLines = "";
		String sessionUserId = session.getSessionCustomerId();	
		if (sessionUserId !=null ) {
			long userId = Integer.parseInt(sessionUserId);
			List<Sms> smsGroup = repository.findSmsGroupsByDatePeriod(userId, reportStartDate, reportEndDate);
		
			if (smsGroup != null && smsGroup.size() > 0) {
				List<String> smsRecordList = formatSMSendDate(smsGroup);
				chartLines = prepareChartDiagram(smsRecordList);
			}
		}
		session.updateSessionAttribute(Session.SESSION_CHARTLINE_PARAMETER, chartLines);
	}

	private List<String> formatSMSendDate(List<Sms> smsGroup) {
		List<String> smsRecordList = new ArrayList<String>();

		java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy,MM,dd");
	
		for (Sms sms : smsGroup) {
			smsRecordList.add(df.format(sms.getSendTime()));
		}
		
		return smsRecordList;
	}
	
	private String prepareChartDiagram (List<String> smsRecordList) {
		String chartLines = "";
		
		Set<String> smsRecordsCountSet = new HashSet<String>(smsRecordList);
		for(String s: smsRecordsCountSet){
			if (!chartLines.equals("")) chartLines += ","; 
			chartLines += "[new Date("+s+"), "+Collections.frequency(smsRecordList,s)+"]";
		}
		
		chartLines = "["+ chartLines +"]";
		
		return chartLines;
	}
}
