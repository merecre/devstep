package lv.itsms.web.request.parameter;

import javax.servlet.http.HttpServletRequest;

public class ReportEndDateRequestParameter extends UserPageRequestParameter {

	final static String URL_PARAMETER = "enddate";

	public ReportEndDateRequestParameter() {
		super(URL_PARAMETER);
	}

	@Override
	public void update(HttpServletRequest request) {
		parameterValue = request.getParameter(parameterKey);
	}

}
