package lv.itsms.web.page.report;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.command.CommandTypeSingleton;
import lv.itsms.web.command.PageRequestCommand;
import lv.itsms.web.page.smspanel.CustomerPanelCommandFactory;
import lv.itsms.web.request.parameter.UserPageRequestParameter;
import lv.itsms.web.service.Repository;
import lv.itsms.web.session.Session;
import transfer.domain.Sms;
import transfer.domain.SmsGroup;

public class DoPrepareReportDiagramCommand implements PageRequestCommand {

	final static String REPORT_START_DATE = "startDate";
	final static String REPORT_END_DATE = "endDate";

	Session session;

	HttpServletRequest request;

	Repository repository;

	CustomerPanelCommandFactory factory;

	public DoPrepareReportDiagramCommand(Session session, HttpServletRequest request, Repository repository) {
		this.session = session;
		this.request = request;
		this.repository = repository;
	}

	public DoPrepareReportDiagramCommand(CustomerPanelCommandFactory factory) {
		this.factory = factory;
	}

	public DoPrepareReportDiagramCommand() {}

	@Override
	public void execute() throws ParseException {

		String reportStartDate = getReportStartDate();
		String reportEndDate = getReportEndDate();

		Map<String, String> reportDates = new HashMap<>(2);
		reportDates.put(REPORT_START_DATE, reportStartDate);
		reportDates.put(REPORT_END_DATE, reportEndDate);

		String chartLines = prepareChartDiagramData(reportStartDate, reportEndDate);


		factory.getSession().updateSessionAttribute(Session.SESSION_CHARTLINE_PARAMETER, chartLines);
		factory.getSession().updateSessionAttribute(Session.SESSION_REPORTDATES_PARAMETER, reportDates);
	}

	private String getReportStartDate() {
		UserPageRequestParameter reportStartDateUserParameter = 
				CommandTypeSingleton.getInstance().getUserPageRequestParameter(ReportStartDateRequestParameter.URL_PARAMETER);
		reportStartDateUserParameter.update(factory.getRequest());
		return reportStartDateUserParameter.getParameter();
	}

	private String getReportEndDate() {
		UserPageRequestParameter reportEndDateUserParameter = 
				CommandTypeSingleton.getInstance().getUserPageRequestParameter(ReportEndDateRequestParameter.URL_PARAMETER);
		reportEndDateUserParameter.update(factory.getRequest());
		return reportEndDateUserParameter.getParameter();		
	}

	private String prepareChartDiagramData(String reportStartDate, String reportEndDate) throws ParseException {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date parsedDate = dateFormat.parse(reportStartDate);
		Timestamp startDateTimestamp = new java.sql.Timestamp(parsedDate.getTime());

		parsedDate = dateFormat.parse(reportEndDate);
		Timestamp endDateTimestamp = new java.sql.Timestamp(parsedDate.getTime());

		String chartLines = "";
		String sessionUserId = factory.getSession().getSessionCustomerId();	
		if (sessionUserId !=null ) {
			long userId = Integer.parseInt(sessionUserId);
			List<Sms> smses = factory.getRepository().findSmsByDatePeriod(userId, startDateTimestamp, endDateTimestamp);
			System.out.println("smses:"+smses);
			if (smses != null && smses.size() > 0) {
				List<String> smsRecordList = formatSMSendDate(smses);
				chartLines = prepareChartDiagram(smsRecordList);
			}
		}

		return chartLines;
	}



	private List<String> formatSMSendDate(List<Sms> smses) {
		List<String> smsRecordList = new ArrayList<String>();

		java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");

		for (Sms sms : smses) {
			smsRecordList.add(df.format(sms.getSendTime()));
		}

		return smsRecordList;
	}

	private String prepareChartDiagram (List<String> smsRecordList) {
		String chartLines = "";

		Set<String> smsRecordsCountSet = new LinkedHashSet<String>(smsRecordList);
		for(String s: smsRecordsCountSet){
			if (!chartLines.equals("")) chartLines += ","; 
			chartLines += "[new Date('"+s+"'), "+Collections.frequency(smsRecordList,s)+"]";
		}

		chartLines = "["+ chartLines +"]";

		return chartLines;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}


}
