package lv.itsms.web.page.report;

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.request.parameter.UserPageRequestParameter;

public class ReportStartDateRequestParameter extends UserPageRequestParameter {

	public static String DATE_FORMAT = "yyyy-MM-dd";
	
	public  static String URL_PARAMETER = "startdate";

	public ReportStartDateRequestParameter() {
		super(URL_PARAMETER);
	}

	@Override
	public void update(HttpServletRequest request) {
		parameterValue = request.getParameter(parameterKey);
	}

}
