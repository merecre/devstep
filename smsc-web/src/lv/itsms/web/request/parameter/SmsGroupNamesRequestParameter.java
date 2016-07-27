package lv.itsms.web.request.parameter;

import javax.servlet.http.HttpServletRequest;

public class SmsGroupNamesRequestParameter extends UserPageRequest{

	final static String GROUP_NAMES_PARAMETER = "group_name";
	
	public SmsGroupNamesRequestParameter() {
		super(GROUP_NAMES_PARAMETER);
	}

	@Override
	public void update(HttpServletRequest request) {	
		this.parameterValues = request.getParameterValues(parameterKey);
	}
}
