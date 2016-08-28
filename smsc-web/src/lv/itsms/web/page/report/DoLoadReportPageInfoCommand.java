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
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.command.CommandTypeSingleton;
import lv.itsms.web.command.PageRequestCommand;
import lv.itsms.web.page.info.ReportInfo;
import lv.itsms.web.page.smspanel.CustomerPanelCommandFactory;
import lv.itsms.web.request.parameter.UserPageRequestParameter;
import lv.itsms.web.service.Repository;
import lv.itsms.web.session.Session;
import transfer.domain.Sms;
import transfer.domain.SmsGroup;

public class DoLoadReportPageInfoCommand implements PageRequestCommand {

	final static String reportInfoData[] = {
			"startDate",
			"endDate",
			"reportSmsDesc",
			"subtitle",
			"date",
			"number",
			"hrefPeriodReport",
			"hrefPerMessageReport",
			"headerPhoneNumber",
			"headerStatus",
			"headerMessage",
			"headerDatetime",
			"txtMessageStatus"
	};

	CustomerPanelCommandFactory factory;

	public DoLoadReportPageInfoCommand(CustomerPanelCommandFactory factory) {
		this.factory = factory;
	}

	public DoLoadReportPageInfoCommand() {}

	@Override
	public void execute() throws ParseException {

		Map<String, String> reportInfo = new HashMap<>();

		String lng = factory.getSession().getSessionLanguage(); 
		Locale currentLocale = new Locale(lng, lng.toUpperCase());
		ResourceBundle messages = ResourceBundle.getBundle("MessagesBundle", currentLocale);

		for (String reportPageInfoKey : reportInfoData) {
			if (messages.containsKey(reportPageInfoKey)) {
				String reportPageMessage = messages.getString(reportPageInfoKey);
				reportInfo.put(reportPageInfoKey, reportPageMessage);
			}
		}

		factory.getSession().updateSessionAttribute(Session.SESSION_REPORTINFO_PARAMETER, reportInfo);
		factory.getSession().updateSessionAttribute(Session.SESSION_SMSSTATTUS_PARAMETER, Sms.STATUSES);
	}	
}
