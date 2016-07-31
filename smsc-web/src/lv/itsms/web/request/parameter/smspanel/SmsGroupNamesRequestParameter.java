package lv.itsms.web.request.parameter.smspanel;

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.request.parameter.UserPageRequestParameter;

public class SmsGroupNamesRequestParameter extends UserPageRequestParameter{

	final static String GROUP_NAMES_PARAMETER = "group_name";

	public SmsGroupNamesRequestParameter() {
		super(GROUP_NAMES_PARAMETER);
	}

	@Override
	public void update(HttpServletRequest request) {	
		this.parameterValues = request.getParameterValues(parameterKey);
	}
}
