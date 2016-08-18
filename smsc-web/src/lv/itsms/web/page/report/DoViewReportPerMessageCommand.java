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

public class DoViewReportPerMessageCommand implements PageRequestCommand {

	final static String REPORT_START_DATE = "startDate";
	final static String REPORT_END_DATE = "endDate";


	CustomerPanelCommandFactory factory;

	public DoViewReportPerMessageCommand(CustomerPanelCommandFactory factory) {
		this.factory = factory;
	}

	public DoViewReportPerMessageCommand() {}

	@Override
	public void execute() throws ParseException {

		String reportStartDate = getReportStartDate();
		String reportEndDate = getReportEndDate();

		Map<String, String> reportDates = new HashMap<>(2);
		reportDates.put(REPORT_START_DATE, reportStartDate);
		reportDates.put(REPORT_END_DATE, reportEndDate);

		List<Sms> smses = findMessagesByPeriod(reportStartDate, reportEndDate);

		factory.getSession().updateSessionAttribute(Session.SESSION_REPORTDATA_PARAMETER, smses);
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

	private List<Sms> findMessagesByPeriod(String reportStartDate, String reportEndDate) throws ParseException {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date parsedDate = dateFormat.parse(reportStartDate);
		Timestamp startDateTimestamp = new java.sql.Timestamp(parsedDate.getTime());

		parsedDate = dateFormat.parse(reportEndDate);
		Timestamp endDateTimestamp = new java.sql.Timestamp(parsedDate.getTime());

		List<Sms> smses = null;
		String sessionUserId = factory.getSession().getSessionCustomerId();	
		if (sessionUserId !=null ) {
			long userId = Integer.parseInt(sessionUserId);
			smses = factory.getRepository().findSmsByDatePeriod(userId, startDateTimestamp, endDateTimestamp);
			System.out.println("smses:"+smses);
		}

		return smses;
	}
}
