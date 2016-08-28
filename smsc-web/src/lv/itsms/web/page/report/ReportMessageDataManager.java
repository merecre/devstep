package lv.itsms.web.page.report;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lv.itsms.web.command.CommandTypeSingleton;
import lv.itsms.web.page.smspanel.CustomerPanelCommandFactory;
import lv.itsms.web.request.parameter.UserPageRequestParameter;
import transfer.Utils;
import transfer.domain.Sms;

public class ReportMessageDataManager {
		
	CustomerPanelCommandFactory factory;
	
	public ReportMessageDataManager(CustomerPanelCommandFactory factory) {
		this.factory = factory;
	}

	public List<Sms> prepareSmsesByRequest() throws ParseException {
		String reportStartDate = getReportStartDate();
		String reportEndDate = getReportEndDate();
		String smsStatus = getReportSmsStatus();
		
		List<Sms> smses = null;
		
		if (isSmsStatus(smsStatus)) {
			smses = findMessagesByPeriodAndStatus(reportStartDate, reportEndDate, smsStatus);
		} else {
			smses = findMessagesByPeriod(reportStartDate, reportEndDate);
		}
		
		return smses;
	}
	
	public String getReportStartDate() {
		UserPageRequestParameter reportStartDateUserParameter = 
				CommandTypeSingleton.getInstance().getUserPageRequestParameter(ReportStartDateRequestParameter.URL_PARAMETER);
		reportStartDateUserParameter.update(factory.getRequest());
		return reportStartDateUserParameter.getParameter();
	}

	public String getReportEndDate() {
		UserPageRequestParameter reportEndDateUserParameter = 
				CommandTypeSingleton.getInstance().getUserPageRequestParameter(ReportEndDateRequestParameter.URL_PARAMETER);
		reportEndDateUserParameter.update(factory.getRequest());
		return reportEndDateUserParameter.getParameter();		
	}
	
	public String getReportSmsStatus() {
		UserPageRequestParameter reportSmsStatusParameter = 
				CommandTypeSingleton.getInstance().getUserPageRequestParameter(ReportSmsStatusRequestParameter.URL_PARAMETER);
		reportSmsStatusParameter.update(factory.getRequest());
		return reportSmsStatusParameter.getParameter();		
	}
	
	private boolean isSmsStatus(String status) {
		return status != null && !status.isEmpty();
	}

	public List<Sms> findMessagesByPeriod(String reportStartDate, String reportEndDate) throws ParseException {

		SimpleDateFormat dateFormat = new SimpleDateFormat(ReportStartDateRequestParameter.DATE_FORMAT);
		java.util.Date parsedDate = dateFormat.parse(reportStartDate);
		Timestamp startDateTimestamp = new java.sql.Timestamp(parsedDate.getTime());

		parsedDate = dateFormat.parse(reportEndDate);
		parsedDate = Utils.addDay(parsedDate);
		
		Timestamp endDateTimestamp = new java.sql.Timestamp(parsedDate.getTime());

		List<Sms> smses = null;
		String sessionUserId = factory.getSession().getSessionCustomerId();	
		if (sessionUserId !=null ) {
			long userId = Integer.parseInt(sessionUserId);
			smses = factory.getRepository().findSmsByDatePeriod(userId, startDateTimestamp, endDateTimestamp);;
		}

		return smses;
	}
	
	public List<Sms> findMessagesByPeriodAndStatus(String reportStartDate, String reportEndDate, String smsStatus) throws ParseException {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(ReportStartDateRequestParameter.DATE_FORMAT);
		java.util.Date parsedDate = dateFormat.parse(reportStartDate);
		Timestamp startDateTimestamp = new java.sql.Timestamp(parsedDate.getTime());

		parsedDate = dateFormat.parse(reportEndDate);		
		parsedDate = Utils.addDay(parsedDate);
		
		Timestamp endDateTimestamp = new java.sql.Timestamp(parsedDate.getTime());		
		List<Sms> smses = null;
		String sessionUserId = factory.getSession().getSessionCustomerId();	
		if (sessionUserId !=null ) {
			long userId = Integer.parseInt(sessionUserId);
			smses = factory.getRepository().findSMSRecordsByCustomerIdAndSmsPeriodAndStatus
					(userId, 
					startDateTimestamp, 
					endDateTimestamp, 
					smsStatus);
		}
		return smses;
	}
	
}
