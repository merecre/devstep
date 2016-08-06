package lv.itsms.web.page.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.command.CommandTypeParameter;
import lv.itsms.web.command.CommandTypeSingleton;
import lv.itsms.web.command.PageRequestCommand;
import lv.itsms.web.page.smspanel.CustomerPanelCommandFactory;
import lv.itsms.web.request.parameter.UserPageRequestParameter;
import lv.itsms.web.service.Repository;
import lv.itsms.web.session.Session;
import transfer.domain.Sms;

public class DoPrepareReportDiagramCommand implements PageRequestCommand {

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
		System.out.println("execute"+this.toString());
	}

	public DoPrepareReportDiagramCommand() {}

	@Override
	public void execute() {

		UserPageRequestParameter reportStartDateUserParameter = 
				CommandTypeSingleton.getInstance().getUserPageRequestParameter(ReportStartDateRequestParameter.URL_PARAMETER);
		reportStartDateUserParameter.update(factory.getRequest());
		String reportStartDate = reportStartDateUserParameter.getParameter();

		UserPageRequestParameter reportEndDateUserParameter = 
				CommandTypeSingleton.getInstance().getUserPageRequestParameter(ReportEndDateRequestParameter.URL_PARAMETER);
		reportEndDateUserParameter.update(factory.getRequest());
		String reportEndDate = reportEndDateUserParameter.getParameter();

		String chartLines = "";
		String sessionUserId = factory.getSession().getSessionCustomerId();	
		if (sessionUserId !=null ) {
			long userId = Integer.parseInt(sessionUserId);
			List<Sms> smsGroup = factory.getRepository().findSmsGroupsByDatePeriod(userId, reportStartDate, reportEndDate);

			if (smsGroup != null && smsGroup.size() > 0) {
				List<String> smsRecordList = formatSMSendDate(smsGroup);
				chartLines = prepareChartDiagram(smsRecordList);
			}
		}
		factory.getSession().updateSessionAttribute(Session.SESSION_CHARTLINE_PARAMETER, chartLines);
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
