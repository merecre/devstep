package lv.itsms.web.request.parameter.smspanel;

import javax.servlet.http.HttpServletRequest;

import lv.itsms.web.request.parameter.UserPageRequestParameter;

public class SmsGroupIdPostRequestParameter extends UserPageRequestParameter {
	public final static String GROUP_ID_POST_PARAMETER = "group_name"; 

	public SmsGroupIdPostRequestParameter() {
		super(GROUP_ID_POST_PARAMETER );
	}

	@Override
	public void update(HttpServletRequest request) {
		parameterValues = request.getParameterValues(parameterKey);		
	}	
}
