package lv.itsms.web.page.report;

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.request.parameter.UserPageRequestParameter;

public class ViewReportPerMessagePostParameter extends UserPageRequestParameter {

	public final static String URL_PARAMETER = "permessagereportpost";
	
	public ViewReportPerMessagePostParameter() {
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
