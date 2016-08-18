package lv.itsms.web.page.report;

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.request.parameter.UserPageRequestParameter;

public class ViewReportByDateRequestPostParameter extends UserPageRequestParameter {

	public final static String URL_PARAMETER = "periodreportpost";

	//private final static String URL_PARAMETER_VALUE = "doReport";
	
	public ViewReportByDateRequestPostParameter() {
		super(URL_PARAMETER);
	}

	@Override
	public void update(HttpServletRequest request) {
		parameterValue = request.getParameter(parameterKey);
	}

	@Override
	public boolean isRequested() {
		return parameterValue!= null;
	}

}
