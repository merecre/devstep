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
	final static String REPORT_MESSAGE_STATUS = "selectedStatus";
	
	CustomerPanelCommandFactory factory;

	public DoViewReportPerMessageCommand(CustomerPanelCommandFactory factory) {
		this.factory = factory;
	}

	public DoViewReportPerMessageCommand() {}

	@Override
	public void execute() throws ParseException {

		ReportMessageDataManager reportDataManager = 
				new ReportMessageDataManager(factory);
		
		Map<String, String> reportInfo = new HashMap<>(2);
		reportInfo.put(REPORT_START_DATE, reportDataManager.getReportStartDate());
		reportInfo.put(REPORT_END_DATE, reportDataManager.getReportEndDate());
		reportInfo.put(REPORT_MESSAGE_STATUS, reportDataManager.getReportSmsStatus());
		
		List<Sms> smses = reportDataManager.prepareSmsesByRequest(); 
		
		factory.getSession().updateSessionAttribute(Session.SESSION_REPORTDATA_PARAMETER, smses);
		factory.getSession().updateSessionAttribute(Session.SESSION_REPORTDATES_PARAMETER, reportInfo);
	}

	
}
