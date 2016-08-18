package lv.itsms.web.page.report;

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.request.parameter.UserPageRequestParameter;

public class LoadReportPageInfoParameter extends UserPageRequestParameter {

	public final static String URL_PARAMETER = "cusMenu";

	private final static String URL_PARAMETER_VALUE = "Sms";

	public LoadReportPageInfoParameter() {
		super(URL_PARAMETER);
	}

	@Override
	public void update(HttpServletRequest request) {
		parameterValue = request.getParameter(parameterKey);
	}

	@Override
	public boolean isRequested() {
		return parameterValue!= null && parameterValue.equals(URL_PARAMETER_VALUE);
	}

}
