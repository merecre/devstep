package lv.itsms.web.request.parameter.smspanel;

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.request.parameter.UserPageRequestParameter;

public class LoadSmsGroupPageInfoRequestParameter extends UserPageRequestParameter {

	public final static String URL_PARAMETER = "cusMenu";

	public final static String URL_PARAMETER_VALUE = "smsPanel";
	
	public LoadSmsGroupPageInfoRequestParameter() {
		super(URL_PARAMETER);
	}

	@Override
	public void update(HttpServletRequest request) {

		String customerMenuId = request.getParameter(parameterKey);

		if (customerMenuId == null) {
			customerMenuId = "";
		}

		parameterValue = customerMenuId;
	}

	@Override
	public boolean isRequested() {		
		return this.parameterValue.equals(URL_PARAMETER_VALUE);
	}
}
