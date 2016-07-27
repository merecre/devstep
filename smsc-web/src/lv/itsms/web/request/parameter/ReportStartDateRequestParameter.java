package lv.itsms.web.request.parameter;

import javax.servlet.http.HttpServletRequest;

public class ReportStartDateRequestParameter extends UserPageRequest {

	final static String URL_PARAMETER = "startdate";
	
	public ReportStartDateRequestParameter() {
		super(URL_PARAMETER);
	}

	@Override
	public void update(HttpServletRequest request) {
		parameterValue = request.getParameter(parameterKey);
	}

}
