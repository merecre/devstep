package lv.itsms.web.page.report;

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.request.parameter.UserPageRequestParameter;

public class ReportEndDateRequestParameter extends UserPageRequestParameter {

	public final static String URL_PARAMETER = "enddate";

	public ReportEndDateRequestParameter() {
		super(URL_PARAMETER);
	}

	@Override
	public void update(HttpServletRequest request) {
		parameterValue = request.getParameter(parameterKey);
	}

}
