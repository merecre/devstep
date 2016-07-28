package lv.itsms.web.request.parameter;

import javax.servlet.http.HttpServletRequest;

public class SmsGroupIdPostRequestParameter extends UserPageRequest {
	public final static String GROUP_ID_POST_PARAMETER = "group_name"; 

	public SmsGroupIdPostRequestParameter() {
		super(GROUP_ID_POST_PARAMETER );
	}

	@Override
	public void update(HttpServletRequest request) {
		parameterValues = request.getParameterValues(parameterKey);		
	}	
}
